package it.tasgroup.ge.helpers.avvisoricezioneRT;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.codec.binary.Base64;

import it.tasgroup.ge.CfgEventi;
import it.tasgroup.ge.helpers.GestoreEventiMailHelper;
import it.tasgroup.ge.pojo.AvvisoRicezioneRT;
import it.tasgroup.ge.pojo.InvioQuietanza;
import it.tasgroup.ge.pojo.MessaggioLogico;
import it.tasgroup.idp.crypt.DownloadRicevutaParametersEncrypter;
import it.tasgroup.idp.crypt.SharedConstants;
import it.tasgroup.idp.domain.enti.TributiEnti;
import it.tasgroup.idp.domain.enti.TributiEntiPK;
import it.tasgroup.idp.util.IrisProperties;
import it.tasgroup.iris2.pagamenti.DistintePagamento;
import it.tasgroup.iris2.pagamenti.Pagamenti;



public class AvvisoRicezioneRTHelperImpl extends GestoreEventiMailHelper {
  
	public AvvisoRicezioneRTHelperImpl(EntityManager mgr) {
		manager = mgr;
	}
	/**
	 * Metodo che crea il messaggio logico
	 * @param datiEvento identificativo dell'oggetto che ha generato evento(es. ID_BONIFICO, ID_DISTINTA)
	 * @param confEvento contiene la configurazione dell'evento letta da db
	 * @return L'oggetto il messaggio logico valorizzato
	 * @throws Exception Eccezione sollevata nel caso in cui non si riesca a valorizzare correttamente il messaggio logico
	 */
	@Override
	public MessaggioLogico getMessaggioLogico(String datiEvento,
			 CfgEventi confEvento) throws Exception {
		logger.debug(this.getClass().getSimpleName() + " recupero dati dalla tabella delle distinte di pagamento" );
		MessaggioLogico ml = null;
		AvvisoRicezioneRT avvRicez = new AvvisoRicezioneRT();
		DistintePagamento distintaPag = (DistintePagamento)manager.find(DistintePagamento.class, new Long(datiEvento));
		if (distintaPag !=null){
			logger.debug(this.getClass().getSimpleName() + " trovato record sulle DISTINTE PAGAMENTO con id " + datiEvento );
			logger.debug(this.getClass().getSimpleName() + " data pagamento"+ distintaPag.getDataPagamento() );
			logger.debug(this.getClass().getSimpleName() + " importo"+ distintaPag.getImporto() );
			logger.debug(this.getClass().getSimpleName() + " token "+ distintaPag.getTsInserimento() );
			logger.debug(this.getClass().getSimpleName() + " locale "+ distintaPag.getLocale());
			
			
			
			//***** inizio gestione invio mail unica carrello multibeneficiario ********
			if (distintaPag.getIdGruppo()!=null) {
				logger.debug(this.getClass().getSimpleName() + " ricevuta relativa a carrello multibeneficiario idGruppo = " + distintaPag.getIdGruppo() );
				
				if (!checkUltimaRTMultibeneficiario(distintaPag.getIdGruppo())) {					
					logger.debug(this.getClass().getSimpleName() + " non tutte le ricevute telematiche relative al pagamento sono state ricevute... non mando nessuna mail " );
					return null;	
				}
			}
			if (!"ESEGUITO".equals(distintaPag.getStato())) {
				logger.warn(this.getClass().getSimpleName() + " esito operazione " + distintaPag.getStato() + ": non invio la mail....");
				return null;
			} 
			//**************************************************************************
			avvRicez.setDataPagamento(distintaPag.getTsInserimento());
			avvRicez.setImporto(distintaPag.getImporto());
			avvRicez.setToken(distintaPag.getCodPagamento());
			String url = IrisProperties.getProperty(GESTORE_EVENTI_URL_HOME_PAGE, "www.iris.it", false);
			avvRicez.setUrl_Home_Page(url);
			//**************************************************************************
			String frontEndUrl = url;// "http://localhost:8080"; // da inserire 
			DownloadRicevutaParametersEncrypter downRicEncr = new DownloadRicevutaParametersEncrypter(distintaPag.getCodPagamento(),distintaPag.getUtenteCreatore(),distintaPag.getId()+""  );
			String cParam = null;
			try {
				cParam = downRicEncr.encrypt();
			} catch (Throwable t) {
				logger.error(this.getClass().getSimpleName() + " errore encrypting ",t );
				System.out.println(Base64.class.getProtectionDomain().getCodeSource().getLocation()); 
				throw new Exception(t); 
				
			}
			String downloadReceipt=frontEndUrl+"/gateway/documentiPagamento.do?method=downloadRicevuta&" + SharedConstants.CRYPTEDPARAMS + "=" + cParam;
			avvRicez.setDownloadUrl(downloadReceipt);
			
			logger.info(this.getClass().getSimpleName() + "url download ricevuta = "+ downloadReceipt);
			//per l'invio della quietanza non va creata la lista dei destinatari,
			//ma va fatta la sottoscrizione "al volo" in modo da poter gestire anche gli anonimi
			//
			//quindi come destinatario imposto i dati evento (id. distinta)
			
			ArrayList<String> destinatariTo = new ArrayList<String>();
			destinatariTo.add(datiEvento);
			destinatariTo.addAll(creaListaDestinatari(confEvento, null, TIPO_INVIO_TO));
			ArrayList<String> destinatariCc = creaListaDestinatari(confEvento, null, TIPO_INVIO_CC);

			
			Set<Pagamenti> pagam = distintaPag.getPagamentis();
			
			//Query pendenzaQuery =  manager.createNativeQuery("");
			
			//mando una mail per ogni pagamento
			Iterator iterPag = pagam.iterator();
			while (iterPag.hasNext()) {	
			
				Pagamenti pag = (Pagamenti)iterPag.next();
				// BUG FIX: invece di effettuare la query nativa recupero le info
				try {
				    TributiEntiPK tepk = new TributiEntiPK();
				    tepk.setCdTrbEnte(pag.getCdTrbEnte());
				    tepk.setIdEnte(pag.getIdEnte());
				    TributiEnti te =manager.find(TributiEnti.class, tepk);
				    avvRicez.setCausale(te.getDeTrb());
				} catch (Throwable t) {
					logger.error(this.getClass().getSimpleName() + "errore nel recupero della causale di pagamento",t);
					avvRicez.setCausale("");
				}
				avvRicez.setLocale(distintaPag.getLocale());
				logger.debug(this.getClass().getSimpleName() + "creazione del messaggio logico");
				ml = creaMessaggioLogico(confEvento,avvRicez,destinatariTo,destinatariCc);
			}
			
			
		
		}
		logger.info("InvioQuietanzaHelperImpl.createEmail" + ml);
		return ml;
	}
	/**
	 * Metodo che recupera l'indirizzo email dell'utente
	 * @param idUtente identificativo dell'utente
	 * @return email utente valorizzato
	 */
	@Override
	public String getEmailUtente(String idUtente) throws Exception {
		String email = null;
		if (idUtente != null) {
			try {
			  DistintePagamento distPag= manager.find(DistintePagamento.class, new Long(idUtente));
			  email= distPag.getEmailVersante(); 
			} catch (Throwable t) {
				email = null;
			}
			logger.debug(this.getClass().getSimpleName() + " email utente recuperata: " + email);
			if (email == null || email.isEmpty())
				throw new Exception("La distinta non contiene indirizzo email per la notifica!!!!");

		} else {
			logger.error(" Non è stato possibile recuperare EMAIL DELL'UTENTE:" + idUtente);
			throw new Exception("Non è stato possibile recuperare ID_UTENTE_IRIS" + idUtente);

		}
		return email;
	}
	
	private boolean checkUltimaRTMultibeneficiario(String idGruppo) {
		
		Query q = manager.createNamedQuery("DistintePagamentoByIdGruppo");	
		q.setParameter("idGruppo", idGruppo);
		List<DistintePagamento> l = q.getResultList();
		Iterator<DistintePagamento> iter = l.iterator();
		while (iter.hasNext()) {
			DistintePagamento d = iter.next();
			if (!"ESEGUITO".equals(d.getStato())) {
				return false;
			}
		}
		return true;
	}
}
