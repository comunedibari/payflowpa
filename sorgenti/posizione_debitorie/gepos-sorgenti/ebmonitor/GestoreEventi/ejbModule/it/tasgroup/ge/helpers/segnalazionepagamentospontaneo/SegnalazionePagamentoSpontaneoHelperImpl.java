package it.tasgroup.ge.helpers.segnalazionepagamentospontaneo;


import it.tasgroup.ge.CfgEventi;
import it.tasgroup.ge.helpers.GestoreEventiMailHelper;
import it.tasgroup.ge.pojo.SegnalazionePagamentoSpontaneo;
import it.tasgroup.ge.pojo.MessaggioLogico;
import it.tasgroup.idp.domain.enti.TributiEnti;
import it.tasgroup.iris2.pagamenti.DistintePagamento;
import it.tasgroup.iris2.pagamenti.Pagamenti;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;



public class SegnalazionePagamentoSpontaneoHelperImpl extends GestoreEventiMailHelper {
	
	public SegnalazionePagamentoSpontaneoHelperImpl(EntityManager mgr) {
		manager = mgr;
	}
	
	/**
	 * Metodo che crea il messaggio logico
	 * @param datiEvento identificativo dell'oggetto che ha generato evento(ID_DISTINTA)
	 * @param confEvento contiene la configurazione dell'evento letta da db
	 * @return L'oggetto il messaggio logico valorizzato
	 * @throws Exception Eccezione sollevata nel caso in cui non si riesca a valorizzare correttamente il messaggio logico
	 */
	@Override
	public MessaggioLogico getMessaggioLogico(String datiEvento, CfgEventi confEvento) throws Exception {
		
		logger.debug(this.getClass().getSimpleName() + " recupero dati dalla tabella delle distinte di pagamento"
				+ "  per la segnalazione di Avviso Pagamento Spontaneo con id distinta: "
				+ datiEvento);
		
		MessaggioLogico ml = null;
	
		DistintePagamento distintaPag = (DistintePagamento)manager.find(DistintePagamento.class, new Long(datiEvento));
		
		if (distintaPag !=null){
			logger.debug(this.getClass().getSimpleName() + " trovato record sulle DISTINTE PAGAMENTO con id " + datiEvento );
		
			Set<Pagamenti> pagam = distintaPag.getPagamentis();
				
			//preparo un messaggio logico per ogni pagamento spontaneo della distinta
			Iterator<Pagamenti> iterPag = pagam.iterator();
			Query queryTrib = null;
			SegnalazionePagamentoSpontaneo datiPagamento= null;
			
			while (iterPag.hasNext()) {	
				
				Pagamenti pag = (Pagamenti)iterPag.next();
				String descEnte="";
				
				//recupero i dati del tributo
				queryTrib = manager.createNamedQuery("TributiEntiByIdEnte");
				queryTrib.setParameter("idEnte", pag.getIdEnte());
				queryTrib.setParameter("cdTrbEnte", pag.getCdTrbEnte());
				TributiEnti tribEnti = (TributiEnti) queryTrib.getSingleResult();	
				
						
				//recupero la descrizione dell'ente
				Query utenteQuery =  manager.createNativeQuery("SELECT E.DENOM FROM JLTENTI E WHERE E.ID_ENTE=:idEnte");
							
				Object res= null;
				try {
				utenteQuery.setParameter("idEnte", pag.getIdEnte());
				res = utenteQuery.getSingleResult();
				
				if (res!=null ){
					Object[] result = (Object[]) res;
					descEnte = (String) result[1];
				}
				} catch (Exception e) {
					logger.info(this.getClass().getSimpleName() + " eccezione "+ e.getMessage() + e.getStackTrace());
					
					throw e;
				}
				

				if (tribEnti != null){
				
					String isPredeterminato = tribEnti.getFlPredeterm();
					
					if (isPredeterminato!= null && isPredeterminato.equals("N")) {
						
						
						DecimalFormat    df = (DecimalFormat)NumberFormat.getInstance(Locale.ITALY);	 
						df.applyPattern("#,##0.00");

						datiPagamento = new SegnalazionePagamentoSpontaneo();
						datiPagamento.setCdTribEntePagamento(pag.getCdTrbEnte());
						datiPagamento.setDescTribEntePagamento(tribEnti.getDeTrb());
						datiPagamento.setDataPagamento(new SimpleDateFormat("dd-MM-yyyy").format(pag.getTsInserimento()));
						datiPagamento.setImportoPagamento(df.format(pag.getImPagato()).toString());   			;
						datiPagamento.setCfPagante(pag.getCoPagante());
						datiPagamento.setIdPagamentoEnte(pag.getIdPendenzaente());
						datiPagamento.setDescrizioneEnte(descEnte);
						
					}
							
				}
				if (datiPagamento != null) {
					ArrayList<String> destinatariTo = creaListaDestinatari(confEvento, pag.getIdEnte(), TIPO_INVIO_TO);
					ArrayList<String> destinatariCc = creaListaDestinatari(confEvento, pag.getIdEnte(), TIPO_INVIO_CC);
					logger.debug(this.getClass().getSimpleName() + "creazione del messaggio logico");
					ml = creaMessaggioLogico(confEvento, datiPagamento, destinatariTo, destinatariCc);
				}
				
				if (ml != null) {
					logger.info(this.getClass().getSimpleName() + "messaggio Logico Creato ");
					logger.info(this.getClass().getSimpleName() + "soggetto: " + ml.getSubject());
					logger.info(this.getClass().getSimpleName() + "testo: " + ml.getContent());
				}					
											
			}			
		
		}	
		
		return ml;
	}
	
}

