package it.tasgroup.ge.helpers;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.tasgroup.ge.CfgDestinatari;
import it.tasgroup.ge.CfgEventi;
import it.tasgroup.ge.Eventi;
import it.tasgroup.ge.bean.GestoreEventiInterface;
import it.tasgroup.ge.enums.EnumStatoConfigurazioneEvento;
import it.tasgroup.ge.pojo.CommunicationEvent;
import it.tasgroup.ge.util.JobReport;
import it.tasgroup.ge.util.PropertyUtil;


public class GestoreBatchEventiHelperImpl implements GestoreBatchEventiHelper{
	/*** Resources ***/
	private EntityManager manager;
	private GestoreEventiInterface gestoreEventiProxy;
	private final Log log = LogFactory.getLog(this.getClass());

	private int errCount = 0;
	private int skipCount=0;
	private int countNotifiche = 0;
	private HashMap<String,CfgEventi> eventi;
	
	
	public GestoreBatchEventiHelperImpl(EntityManager manager,
			GestoreEventiInterface gestoreEventiProxy) {
		super();
		this.manager = manager;
		this.gestoreEventiProxy = gestoreEventiProxy;
	}

	public JobReport executeJob() {
		// carico la configurazione degli eventi
		Query queryConfigurazioneEventi = manager.createQuery ("SELECT cfgEventi FROM CfgEventi cfgEventi inner join cfgEventi.cfgDestinatari " );
		
		List<CfgEventi> resultEventi = queryConfigurazioneEventi.getResultList();
		eventi = new HashMap<String,CfgEventi>();
		
		for (CfgEventi cfg : resultEventi) {
			eventi.put(cfg.getCodiceEvento(), cfg);
		}
		// recupero la dimensione del lotto da caricare da file di properties
		int dimensioneLotto = PropertyUtil.getDimensioneLotto();
		return executeGestioneEventi(dimensioneLotto);
	}

	public void setManager(EntityManager manager) {
		this.manager = manager;
	}


	public void setGestoreEventiProxy(GestoreEventiInterface gestoreEventiProxy) {
		this.gestoreEventiProxy = gestoreEventiProxy;
	}


	// eseguo il job
	private JobReport executeGestioneEventi(int dimensioneLotto) {
		JobReport report = JobReport.startReport("GESTIONE BATCH EVENTI");
        errCount = 0;
        countNotifiche = 0;
    	
    	skipCount = 0;
        long startTransaction = System.currentTimeMillis();
        try {
			
			//Caricamento records da EVENTI
			long startSelectDBObjectsFromEventi = System.currentTimeMillis();
			log.debug( "---> executeGestioneEventi TABELLA EVENTI: REPERIMENTO RECORDS DAL DB --->");
			List list = selectDBObjectsFromEventi(dimensioneLotto);
			
			log.debug( "--->  executeGestioneEventi TABELLA EVENTI: REPERIMENTO RECORDS DAL DB ---> elapsed time: ".concat( String.valueOf( System.currentTimeMillis()-startSelectDBObjectsFromEventi ) )
																																		.concat( " millisecondi " )
																																		.concat( " ---> QUANTITA' RECORD EVENTI: ")
																																		.concat( String.valueOf( list.size() ) ) );
			report.addMessage("Numero di record estratti da EVENTI = " + list.size());
			//Se nessun record caricato --> return
			if( list == null || list.size() == 0 ) {
				log.info( "---> Fine executeGestioneEventi ---> Nessun record EVENTI caricato ---> elapsed time: ".concat( String.valueOf( System.currentTimeMillis()-startTransaction ) ).concat( " millisecondi" ) );
				//System.out.println("---> Fine executeGestioneEventi ---> Nessun record EVENTI caricato ---> elapsed time: ".concat( String.valueOf( System.currentTimeMillis()-startTransaction ) ).concat( " millisecondi" ));
				report.addMessage("Nessun record estratto da EVENTI, fine esecuzione");
				return report.closeReport();
			}

			//Inizio ciclo su ogni record e aggiorno lo stato in modo da non venire preso in carico da un altro job
			long startInizioCiclo = System.currentTimeMillis();
			for(int i = 0; i < list.size(); i++) {
				
				int count = i + 1;
				Eventi recordEventi = (Eventi)list.get(i);
				
				// aggiorno lo stato
				recordEventi.setNumeroTentativi(recordEventi.getNumeroTentativi()+1);
				gestoreEventiProxy.eventUpdate(STATO_EVENTO_IN_ESECUZIONE, "", recordEventi.getId(),true, recordEventi.getNumeroTentativi());
			}//Fine ciclo su ogni record
			
			for(int i = 0; i < list.size(); i++) {
				int count = i + 1;
				Eventi recordEventi = (Eventi)list.get(i);
				report.addMessage(JobReport.HR3);
				
				report.addMessage("Ciclo # .... " + count + " di " + list.size());
				report.addMessage("CodiceEvento ..... " + recordEventi.getCodiceEvento());
				report.addMessage("Dati Evento .... " + recordEventi.getDatiEvento());
				report.addMessage("Record Eventi = " + recordEventi);
				//Esecuzione Azione
				executeGestioneSingoloEvento( recordEventi , report );
			}

			log.debug( "---> FINE CICLO SU SET EVENTI ---> elapsed time: ".concat( String.valueOf( System.currentTimeMillis()-startInizioCiclo ) ).concat( " millisecondi" ) );
			log.debug( "---> QTA NOTIFICHE EVENTI: ".concat( String.valueOf(countNotifiche) ) );
			log.debug( "---> QTA ERRORI: ".concat( String.valueOf(errCount) ) );
			
			log.info( "---> Fine executeGestioneEventi ---> elapsed time: ".concat( String.valueOf( System.currentTimeMillis()-startTransaction ) ).concat( " millisecondi" ) );
			System.out.println("---> Fine executeGestioneEventi ---> elapsed time: ".concat( String.valueOf( System.currentTimeMillis()-startTransaction ) ).concat( " millisecondi" ));

			report.addMessage(JobReport.HR2);
			report.addMessage("Numero di record processati = " + list.size());
			report.addMessage("Eventi notificati ........ " + countNotifiche);
			report.addMessage("Errori .................... " + errCount);
			report.addMessage("Variazioni saltate ........ " + skipCount);
			
			report.numRecordProcessati = list.size();
			report.numInserimenti = countNotifiche;
			report.numErrori = errCount;
			report.numSkipped = skipCount;

        } catch (Exception e) {
			log.error( e.toString() );
			//e.printStackTrace();
		}
		return report.closeReport();
	}
	
	// estraggo i record dalla tabella GEV_EVENTI con stato in attesa o in errore per riprocessarli
	public List selectDBObjectsFromEventi(int maxRows) throws Exception {
		Query queryEventi = manager.createQuery ("from it.tasgroup.ge.Eventi as evt where evt.stato =:stato or evt.stato=:stato2 " );
		
		queryEventi.setParameter("stato", STATO_EVENTO_IN_ATTESA);
		queryEventi.setParameter("stato2", STATO_EVENTO_IN_ERRORE);
		if (maxRows > 0) {
			queryEventi.setMaxResults(maxRows);
		}
		List<Eventi> resultEventi = queryEventi.getResultList();
		
		return resultEventi;
	}
	
	public void executeGestioneSingoloEvento( Eventi recordEventi, JobReport report  ) {
		String esito = STATO_EVENTO_NOTIFICATO;
    	String descrEsito = "";
		
    	try {
    		// eseguo la creazione del singolo messaggio legato ad un evento
    		try {
				report.addMessage("	GESTIONE SINGOLO EVENTO");
				// se la gestione dell'evento non � attiva non faccio niente
				CfgEventi cfg = eventi.get(recordEventi.getCodiceEvento());
				if (cfg.getAttivo()!=null && !cfg.getAttivo().equals("")){
					if (cfg.getAttivo().equals(EnumStatoConfigurazioneEvento.DISATTIVO.getChiave())){
						log.info( "---> la gestione dell'evento non e' attiva--- EVENTO NON GESTITO" );
						esito = STATO_EVENTO_IN_ATTESA;
					}else{
						HashMap<String, String> info = executeInvioMessaggio( recordEventi , report );
						if (info.containsKey("OK")) {
							descrEsito = info.get("OK");
						}
					}
				}
				countNotifiche++;
			} catch(Exception e) {
				log.error("Errore creazione del singolo messaggio legato ad un evento", e);
				errCount++;
				int numTent = PropertyUtil.getNumeroTentativi();
				if (recordEventi.getNumeroTentativi() < numTent)
					esito = STATO_EVENTO_IN_ERRORE;
				else
					esito = STATO_EVENTO_ABORTITO;
				if( e.getMessage() != null &&  !e.getMessage().equalsIgnoreCase("") ) {
					descrEsito = e.getMessage(); 
				} else {
					descrEsito = e.getClass().getName();
				}
				report.addError("Errore nell'esecuzione dell'evento " + recordEventi.getId() + " con tipo evento " + recordEventi.getCodiceEvento() + ": " + descrEsito + "; Eccezione: " + e.getClass().getName() + " - " + e.getMessage());// + "\r\n" + JobControllerUtil.serializeException(e));
			}
			
			if (descrEsito != null && descrEsito.length() > 200) {
				descrEsito = descrEsito.substring(0, 199);
			}
			
			recordEventi.setStato(esito);
			recordEventi.setDescrStato( descrEsito );
			recordEventi.setDataUltimaEsecuz(new Timestamp(new Date().getTime()));
			
			gestoreEventiProxy.eventUpdate(esito, descrEsito, recordEventi.getId(),false, recordEventi.getNumeroTentativi());
			
		} catch(Exception e) {
			errCount++;
			log.error( "---> Errore nel tentativo di aggiornare tabella Eventi: " + e.toString() );
			//e.printStackTrace();
			report.addError("Errore nella conclusione dell'evento " + recordEventi.getId() + " con tipo evento " + recordEventi.getCodiceEvento() + ": " + descrEsito + "; Eccezione (nell'aggiornamento della tabella Eventi): " + e.getClass().getName() + " - " + e.getMessage());// + "\r\n" + JobControllerUtil.serializeException(e));
			
			//
			//	Rilancio l'eccezione per evitare loop
			//
			throw new RuntimeException("Errore nella conclusione dell'evento " + recordEventi.getId() + " con tipo evento" + recordEventi.getCodiceEvento() + ": " + descrEsito + "; Eccezione (nell'aggiornamento della tabella GEV_EVENTI): " + e.getClass().getName() + " - " + e.getMessage());
		}
	}
	

	
	private HashMap<String, String> executeInvioMessaggio( Eventi recordEventi, JobReport report ) throws Exception {
		CfgEventi cfg = eventi.get(recordEventi.getCodiceEvento());
		CfgEventi cfgCopia = new CfgEventi();
		cfgCopia.setAttivo(cfg.getAttivo());
		cfgCopia.setCodiceEvento(cfg.getCodiceEvento());
		cfgCopia.setSeverity(cfg.getSeverity());
		cfgCopia.setTemplate(cfg.getTemplate());
		for (CfgDestinatari dest : cfg.getCfgDestinatari()) {
			CfgDestinatari newDest = new CfgDestinatari();
			newDest.setTipoDestinatario(dest.getTipoDestinatario());
			newDest.setTipoInvio(dest.getTipoInvio());
		}
		CommunicationEvent ce = new CommunicationEvent(recordEventi.getCodiceEvento(),recordEventi.getDatiEvento(),cfg);
		
		return gestoreEventiProxy.fireCommunicationEvt(ce);
		
	}
}
