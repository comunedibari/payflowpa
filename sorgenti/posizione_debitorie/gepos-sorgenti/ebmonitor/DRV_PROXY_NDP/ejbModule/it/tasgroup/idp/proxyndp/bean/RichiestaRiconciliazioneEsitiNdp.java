package it.tasgroup.idp.proxyndp.bean;

import it.tasgroup.idp.bean.AbstractExecutorRemote;
import it.tasgroup.idp.bean.ExecutorLocal;
import it.tasgroup.idp.bean.ExecutorRemote;
import it.tasgroup.idp.pojo.StoricoData;
import it.tasgroup.idp.proxyndp.utils.PostTimerBusinessMethodInterceptor;
import it.tasgroup.idp.util.IrisProperties;
import it.tasgroup.idp.util.ServiceLocalMapper;
import it.tasgroup.iris2.pagamenti.MovimentiAccredito;
import it.tasgroup.iris2.pagamenti.Rendicontazioni;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.StringTokenizer;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/* 
 * Questa classe permette di riconciliare gli esiti_ndp senza l'incrocio dei dati con la tabella MOVIMENTI_ACCREDITO
 * 
 */

@SuppressWarnings("unchecked")
@Stateless
public class RichiestaRiconciliazioneEsitiNdp extends AbstractExecutorRemote implements ExecutorLocal, ExecutorRemote {
	
	private static final String LISTA_ENTI_RICONCILIAZIONE_COMPLETA = "iris.riconciliazioni.ndp.lista_enti_riconciliazione_completa";
	private static final String LISTA_ENTE_PSP_RICONCILIAZIONE_PARZIALE ="iris.riconciliazioni.ndp.lista_psp_riconciliazione_parziale.";
	
	private static List<String> listaEntiRiconciliazioneCompleta = new ArrayList<String>();  //Default Value: Lista Vuota
	private static Map <String,String[]> listaEntiPSPRiconcliazioneParziale = new HashMap<String,String[]>();//Default Value: Lista Vuota
	private boolean soloRiconciliazioneCompleta = false;  
		
	@EJB(beanName = "ElaboraRiconciliazioneMovimentiNdp")
	private IElaboraRiconciliazioneMovimentiNdp elaboraRiconciliazioneMovimentiNdp;
	
	@PersistenceContext(unitName=ServiceLocalMapper.IdpBTJta)
	private EntityManager em;
	
	private final Log logger = LogFactory.getLog(this.getClass());	

	@PostConstruct
	private void init() {
		// Lettura da file di properties del range temporale (in ore) di visibilita' del flusso
		try {
			String listaEntiRiconciliazioneCompletaString = IrisProperties.getProperty(LISTA_ENTI_RICONCILIAZIONE_COMPLETA);
			logger.info("Acquisito da file di properties il valore associato al parametro " + LISTA_ENTI_RICONCILIAZIONE_COMPLETA);
			if (listaEntiRiconciliazioneCompletaString!=null) {
				if ("*".equals(listaEntiRiconciliazioneCompletaString.trim())) {
					soloRiconciliazioneCompleta = true;
					logger.info("Tutti gli enti hanno la riconciliazione del nodo, completa. I flussi FR verranno riconciliati solo se presente un movimento accredito relativo.");
				} else {
					listaEntiRiconciliazioneCompleta = Arrays.asList(listaEntiRiconciliazioneCompletaString.split(";"));
					logger.info("I seguenti enti: " + listaEntiRiconciliazioneCompleta + " hanno la riconciliazione del nodo, completa. "
							+"Solo per questi enti i flussi FR verranno riconciliati solo se presente un movimento accredito relativo. Per tutti gli altri verranno riconciliati senza tenere conto "
							+ " Della rendicontazione del conto corrente di tesoreria.");
					
					logger.info("Verifico su file di properties se esiste per un ente configurato per la riconciliazione completa"
							+ "anche la configurazione per gestire la riconciliazione parziale su una lista di PSP");
							
					for (String cfEnte : listaEntiRiconciliazioneCompleta) {
						
						String listaEntePSPRiconcliazioneParzialeString = null;
						try {
							listaEntePSPRiconcliazioneParzialeString = IrisProperties.getProperty(LISTA_ENTE_PSP_RICONCILIAZIONE_PARZIALE+cfEnte);
							logger.info("Verificato da file di properties il valore associato al parametro " +LISTA_ENTE_PSP_RICONCILIAZIONE_PARZIALE+cfEnte);
						} catch (Exception e) {
							logger.info("Non e'presente o non e' stato possibile acquisire da file di properties il valore associato al parametro " + LISTA_ENTE_PSP_RICONCILIAZIONE_PARZIALE+cfEnte );
						}
						
						if ((listaEntePSPRiconcliazioneParzialeString) != null && 
							(! "".equals(listaEntePSPRiconcliazioneParzialeString.trim()))) {
							listaEntiPSPRiconcliazioneParziale.put(cfEnte, listaEntePSPRiconcliazioneParzialeString.split(";"));						
							logger.info("Acquisito da file di properties il valore associato al parametro " + LISTA_ENTE_PSP_RICONCILIAZIONE_PARZIALE+cfEnte);
						}
					}		

				}
			}
			
		} catch (Exception e) {
			logger.info("Non e' stato possibile acquisire da file di properties il valore associato al parametro " + LISTA_ENTI_RICONCILIAZIONE_COMPLETA + " - Assunto default: lista vuota.");
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	@Interceptors(PostTimerBusinessMethodInterceptor.class)
	public StoricoData executeApplicationTransaction() {
		StoricoData output = null;
		
		if (soloRiconciliazioneCompleta) {
			// Se la piattaforma è configurata per riconciliazione completa su tutti 
			// gli enti, salto proprio la riconciliazione diretta dei flussi
			return output;
		}
		
		// In genere eseguo la riconciliazione degli FR per:
		// - tutti gli enti che non hanno riconciliazione completa.
		// - gli enti che hanno la riconciliazione completa ma hanno configurato l'esclusione di alcuni PSP
		
		List<Rendicontazioni> flussiNdpToProcess = null; 
		
		logger.info("Ricerca flussi NDP  da riconciliare per gli enti che non hanno la riconciliazione completa");
		flussiNdpToProcess = em.createNamedQuery("findRendicontazioniFRToProcess").
				setParameter("riceventeExcludeList", listaEntiRiconciliazioneCompleta).getResultList();
		
		try{
			logger.info("Ricerca flussi NDP  da riconciliare per i PSP esclusi dagli enti che hanno la riconciliazione completa");
			for (Map.Entry<String, String[]> entry : listaEntiPSPRiconcliazioneParziale.entrySet()) {
			    String cfEnte = entry.getKey();
				List<String> pspList=Arrays.asList(entry.getValue()); 				
			    logger.info("Ricerca flussi NDP  da riconciliare per gli enti che  hanno la riconciliazione completa ma hanno PSP esclusi");
			    List<Rendicontazioni> flussiNdpToProcessForEntePSP = em.createNamedQuery("findRendicontazioniFRToProcessByEnteRiceventeAndPSPMittente").
					setParameter("enteRicevente", cfEnte).
					setParameter("pspMittenti", pspList ).getResultList();
			
			    //aggiungo alla lista dei flussi da processari quelli appena estratti
			    if (!(flussiNdpToProcessForEntePSP == null || flussiNdpToProcessForEntePSP.size() == 0)) {
			    	flussiNdpToProcess.addAll(flussiNdpToProcessForEntePSP);
			    }
			}
		} catch (Exception e) {
			logger.error("Errore nella ricerca dei flussi NDP  da riconciliare per i PSP esclusi dagli enti che hanno la riconciliazione completa");
		}

		
		if (flussiNdpToProcess == null || flussiNdpToProcess.size() == 0) {
			logger.info("Nessun Flusso FR  da riconciliare");
			return output;
		}
		
		for(Rendicontazioni flussoNdp:flussiNdpToProcess) {	
			elaboraRiconciliazioneMovimentiNdp.doRiconciliazioneFlussoFR(flussoNdp);
		}
		
		return output;
		
	}
}
