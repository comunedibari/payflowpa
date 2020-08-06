package it.tasgroup.idp.timer;

import it.tasgroup.idp.domain.TimersIris;
import it.tasgroup.idp.pojo.MonitoringData;
import it.tasgroup.idp.pojo.TimerData;
import it.tasgroup.idp.util.IdpServiceLocator;
import it.tasgroup.idp.util.ServiceLocalMapper;

import java.util.*;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Stateless
@Remote(TimerCommandExecutor.class)
public class TimerController implements TimerCommandExecutor, TimerCommandExecutorLocal {

	private final Log logger = LogFactory.getLog(this.getClass());

	@PersistenceContext(unitName = ServiceLocalMapper.IdpBTJta)
	private EntityManager manager;




	private static boolean initialized;

	@Override
	public MonitoringData executeApplicationTransaction() {
		stopTimers();
		return startTimers();
	}

	/**
	 * Ritorna lo stato di inizializzazione del TimerController, in particolare il TimerController è inizializzato se i
	 * timer persistenti sono stati rimossi e sono stati creati i nuovi timer presenti sul database.
	 * Viene utilizzato in particolare per semaforizzare l'esecuzione dei timer. I timer non sono eseguiti finchè
	 * il processo di inizializzazione del TimerController non è stato completato. (isInitialized() == true)
	 * Essendo i timer persistenti al riavvio potrebbe accadere che parta qualche timer prima che i timer persistenti siano stati cancellati,
	 * è responsabilità del timer invocare questo metodo per capire se è possibile eseguire il task associato al timer o meno.
	 * In EJB 3.1 si potrà disabilitare la persistenza in modo JEE compliant e rendere di fatto inutile questa verifica.
	 * Notare che TimerController in caso di cluster è istanziato su ogni nodo, ma questo non pregiudica il funzionamento dato che :
	 * caso MANUALE  : i timer partono su tutte le macchine, di conseguenza la verifica deve essere fatta su tutte la macchine.
	 * caso AUTOMATICO : i timer partono solo su una macchina (nodo master), di conseguenza la verifica sarà fatta solo sulla macchina master.
	 * E' comunque necessario che sia un attributo static,  altrimenti il metodo isInitialized() potrebbe essere invocato su
	 * EJB diversi del pool con conseguenti valori non corretti. (dato che solo una istanza per nodo di TimerController inizializza i timer, le altre istanze
	 * ritornerebbero sempre isInitialized() == false)
	 * @return true se è inizializzato, fale se non è inizializzato
	 */
	@Override
	public boolean isInitialized() {
		return initialized;
	}

	@Override
	public MonitoringData executeApplicationTransaction(String data) {
		if (data.equalsIgnoreCase("STOP")) {
			this.stopTimers();
			return null;
		} else if (data.equalsIgnoreCase("START")) {
			return this.startTimers();
		} else if (data.equalsIgnoreCase("INIT")) {
			return executeApplicationTransaction();
		} else {
			logger.info("Command " + data + " not recognized: no action performed on timers.");
			return null;
		}
	}

	private List<TimersIris> getTimersList() {
		Query query = manager.createNamedQuery("ListaTimersIris");
		@SuppressWarnings("unchecked")
		List<TimersIris> timersList = query.getResultList();
		return timersList;
	}

	private MonitoringData startTimers() {

		MonitoringData monitorData = new MonitoringData();
		List<String> timersStatus = new ArrayList<String>();

		logger.debug("============= START LOCAL TIMERS===========");

		try {
			logger.info(" START TIMERS DA TABELLA (CACHED)");
			List<TimersIris> timersList = getTimersList();

			logger.info(" N. timers trovati: " + timersList.size());
			for (TimersIris timerEntry : timersList) {
				logger.info("starting " + timerEntry);
				timersStatus.add(this.startTimer(timerEntry.getTimer(), timerEntry.getInterval(),timerEntry.getStartup(), timerEntry.getPeriod(), timerEntry.getNodeActive()));
			}
			logger.debug(" FINE LETTURA LISTA TIMERS DA TABELLA (CACHED)");

		} catch (Exception e) {
			logger.error(this.getClass().getSimpleName() + e.getMessage());
		}

		monitorData.setFilesList(timersStatus);
		return monitorData;
	}


	private void stopTimers() {
		try {
			logger.info(" STOP TIMERS DA TABELLA");
			List<TimersIris> timersList = getTimersList();

			logger.info(" N. timers trovati: " + timersList.size());
			for (TimersIris timer : timersList) {
				logger.debug("stopping " + timer);
				String timerName = (String) timer.getTimer();
				this.stopTimer(timerName);
				logger.debug(timerName + " STOPPED !!");
			}
			logger.debug(" FINE LETTURA LISTA TIMERS DA TABELLA");

		} catch (Exception e) {
			logger.error(this.getClass().getSimpleName() + e.getMessage());
		}

		initialized = true;
	}

	private String startTimer(String timerName, int interval, Date initialExpiration, int period, String nodeActive) {
		String status = "";
		try {
			logger.info("STARTING TIMER [" + timerName + "] - interval: " + interval + ", initialExpiration :" + initialExpiration + ", period:" + period);


			ITimer timer = (ITimer) IdpServiceLocator.getInstance().getServiceByName(timerName);

			if (!"STOPPED".equals(nodeActive)) {
				if (initialExpiration != null) {
					timer.scheduleTimer(initialExpiration, period, timerName);
				} else {
					timer.scheduleTimer(interval, period, timerName);
				}
			}

			logger.info("started " + timerName);

			status = timer.checkStatus();
			logger.debug("checkstatus = " + status + " on current JVM");



		/*	if (timerName.equals("TestTimer")) {
				for (int i = 0; i < 100; i++) {
					// Timer creation on local JVM
					String timerNameIndexed = timerName + "_" + i;
					timer.scheduleTimer(interval, period, timerNameIndexed);
					logger.info("scheduled timer " + timerNameIndexed);
					status = timer.checkStatus();
					logger.debug(status);
				}
			}
*/
		} catch (Throwable e) {
			logger.error("ERROR starting: " + timerName, e);
			status = "ERRORE: " + e.getMessage();
		}
		return status;
	}

	private String startTimer(String timerName, int interval, int period) {
		return this.startTimer(timerName,interval,null,period, "-");
	}


	private String startTimer(String timerName,  Date initialExpiration, int period) {
		String status = "";
		try {
			logger.info("STARTING TIMER [" + timerName + "] - initial expiration : " + initialExpiration + ", period:" + period);

			ITimer timer = (ITimer) IdpServiceLocator.getInstance().getServiceByName(timerName);

			// Timer creation on local JVM
			timer.scheduleTimer(initialExpiration, period, timerName);
			logger.info("started " + timerName);

			status = timer.checkStatus();
			logger.debug("checkstatus = " + status + " on current JVM");

		} catch (Throwable e) {
			logger.error("ERROR starting: " + timerName, e);
			status = "ERRORE: " + e.getMessage();
		}
		return status;
	}



	private String stopTimer(String timerName) {
		String status = "";
		try {
			logger.info("STOPPING TIMER [" + timerName + "]");

			ITimer timer = (ITimer) IdpServiceLocator.getInstance().getServiceByName(timerName);

			logger.info("checking timer status for " + timerName);
			status = timer.checkStatus();
			logger.info("checkstatus on " + timerName + " = " + status);

			timer.cancelTimer(timerName);
			logger.info("stopped " + timerName + " on current JVM ");

			status = timer.checkStatus();
			logger.info("checkstatus on " + timerName + " = " + status);

		} catch (Throwable e) {
			logger.error("ERROR stopping: " + timerName, e);
			status = "ERRORE: " + e.getMessage();
		}
		return status;
	}

	private String checkTimer(String timerName) {
		String status = "";
		try {
			logger.info("CHECKING TIMER [" + timerName + "]");

			ITimer timer = (ITimer) IdpServiceLocator.getInstance().getServiceByName(timerName);

			logger.info("checking timer status for " + timerName);
			status = timer.checkStatus();
			logger.info("checkstatus on " + timerName + " = " + status);

		} catch (Throwable e) {
			logger.error("ERROR checking: " + timerName, e);
			status = "ERRORE: " + e.getMessage();
		}
		return status;
	}


	@Override
	public String executeHtml() throws Exception {
		String table = "";
		try {
			MonitoringData data1 = this.executeApplicationTransaction();

			table += "<TABLE border=\"\1\">";
			table += "<TR>";
			table += "<TD>File Name</TD>";
			table += "</TR>";
			List<String> filesList1 = data1.getFilesList();
			for (Iterator<String> iterator = filesList1.iterator(); iterator.hasNext();) {
				String fileName = iterator.next();
				table += "<TR>";
				table += "<TD>";
				table += (fileName);
				table += "</TD>";
				table += "</TR>";
			}
			table += "</TABLE>";

		} catch (Exception e) {
			logger.info(this.getClass().getSimpleName() + " ERROR EXECUTEHTML " + e.getMessage());
		}
		return table;
	}

	@Override
	public String executeHtml(String timerName, String intervallo, String periodo, String operazione) {
		String timerStatus;
		try {
			// eseguo operazione richiesta
			if ("START".equals(operazione)) {
				timerStatus = this.startTimer(timerName, Integer.parseInt(intervallo), Integer.parseInt(periodo));
			} else if ("STOP".equals(operazione)) {
				timerStatus = this.stopTimer(timerName);
			} else {
				timerStatus = "Non conosco l'operazione: " + operazione;
			}
		} catch (Exception e) {
			logger.info(this.getClass().getSimpleName() + " ERROR EXECUTEHTML " + e.getMessage());
			timerStatus = "ERRORE: " + e.getMessage();
		}
		return timerStatus;
	}

	@Override
	public MonitoringData executeApplicationTransaction(TimerData timer) {

		String status;
		switch (timer.getAction()) {
		case START:
			status = startTimer(timer.getTimerName(), timer.getInterval(), timer.getPeriod());
			break;
		case STOP:
			status = stopTimer(timer.getTimerName());
			break;
		case CHECK:
			status = checkTimer(timer.getTimerName());
			break;
		default:
			status = "NADA";
			break;
		}

		MonitoringData mData = new MonitoringData();
		mData.setEsito(status);
		return mData;
	}

}
