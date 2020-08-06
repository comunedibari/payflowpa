package it.tasgroup.ge.helpers.invioquietanza;

import it.tasgroup.ge.CfgEventi;
import it.tasgroup.ge.helpers.GestoreEventiMailHelper;
import it.tasgroup.ge.pojo.InvioQuietanza;
import it.tasgroup.ge.pojo.MessaggioLogico;
import it.tasgroup.idp.domain.enti.TributiEnti;
import it.tasgroup.idp.domain.enti.TributiEntiPK;
import it.tasgroup.idp.util.IrisProperties;
import it.tasgroup.iris2.pagamenti.DistintePagamento;
import it.tasgroup.iris2.pagamenti.Pagamenti;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;



public class InvioQuietanzaHelperImpl extends GestoreEventiMailHelper {

	public InvioQuietanzaHelperImpl(EntityManager mgr) {
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
		InvioQuietanza inQuiet = new InvioQuietanza();
		DistintePagamento distintaPag = (DistintePagamento)manager.find(DistintePagamento.class, new Long(datiEvento));
		if (distintaPag !=null){
			logger.debug(this.getClass().getSimpleName() + " trovato record sulle DISTINTE PAGAMENTO con id " + datiEvento );
			logger.debug(this.getClass().getSimpleName() + " data pagamento"+ distintaPag.getDataPagamento() );
			logger.debug(this.getClass().getSimpleName() + " importo"+ distintaPag.getImporto() );
			logger.debug(this.getClass().getSimpleName() + " token "+ distintaPag.getTsInserimento() );
			
			
			inQuiet.setDataPagamento(distintaPag.getTsInserimento());
			inQuiet.setImporto(distintaPag.getImporto());
			inQuiet.setToken(distintaPag.getCodPagamento());
			String url = IrisProperties.getProperty(GESTORE_EVENTI_URL_HOME_PAGE, "www.iris.it", false);
			inQuiet.setUrl_Home_Page(url);
			
			
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
			Query pendenzaQuery = null;
			String causale = null;
			while (iterPag.hasNext()) {	
				
				Pagamenti pag = (Pagamenti)iterPag.next();
				//fix: invece di effettuare la query nativa recupero le info
				try {
				    TributiEntiPK tepk = new TributiEntiPK();
				    tepk.setCdTrbEnte(pag.getCdTrbEnte());
				    tepk.setIdEnte(pag.getIdEnte());
				    TributiEnti te =manager.find(TributiEnti.class, tepk);
				    inQuiet.setCausale(te.getDeTrb());
				} catch (Throwable t) {
					logger.error(this.getClass().getSimpleName() + "errore nel recupero della causale di pagamento",t);
					inQuiet.setCausale("");
				}
					
				logger.debug(this.getClass().getSimpleName() + "creazione del messaggio logico");
				ml = creaMessaggioLogico(confEvento,inQuiet,destinatariTo,destinatariCc);
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
	public String getEmailUtente(String idUtente) throws Exception{
		String email=null;
		if (idUtente!=null){
			logger.debug(this.getClass().getSimpleName() + " recupero email dell'utente " + idUtente);
			
			try {
				  DistintePagamento distPag= manager.find(DistintePagamento.class, new Long(idUtente));
				  email= distPag.getEmailVersante(); 
				} catch (Throwable t) {
					email = null;
				}
			logger.debug(this.getClass().getSimpleName() + " email utente recuperata: " + email );
			if (email == null || email.isEmpty()) {
				throw new Exception("La distinta non contiene indirizzo email per la notifica!!!!");
			}
		}else{
			logger.error(" Non è stato possibile recuperare EMAIL DELL'UTENTE:" + idUtente);
			throw new Exception("Non è stato possibile recuperare ID_UTENTE_IRIS"+ idUtente);
			
		}
		return email;
	}
	
}
