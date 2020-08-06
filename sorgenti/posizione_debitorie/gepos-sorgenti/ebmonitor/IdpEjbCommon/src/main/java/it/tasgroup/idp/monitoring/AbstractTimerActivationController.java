package it.tasgroup.idp.monitoring;

import it.tasgroup.idp.domain.TimersIris;
import it.tasgroup.idp.timer.TimerCommandExecutor;
import it.tasgroup.idp.timer.TimerCommandExecutorLocal;
import it.tasgroup.idp.util.ServiceLocalMapper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AbstractTimerActivationController implements TimerActivationControllerLocal {

	

	@PersistenceContext(unitName=ServiceLocalMapper.IdpBTJta)
	private EntityManager manager;

	@EJB(beanName = "TimerController")
	TimerCommandExecutorLocal timerCommandExecutorLocal;
	
	protected abstract Log getLog();
	
	@Override
	public boolean isTimerActive(String timerName) {

		if (!timerCommandExecutorLocal.isInitialized()) {
			getLog().info("Timer " + timerName + " non puo' essere eseguito : timers ancora in fase in inizializzazione");
			return false;
		}

		String failoverStrategy = System.getProperty("it.tasgroup.timers.failover.strategy");

		getLog().debug("Property it.tasgroup.timers.failover.strategy is " + failoverStrategy);

		if ("AUTO".equals(failoverStrategy)) {
			getLog().info("Timer " + timerName + " verifica sospensione ... ");
			// verifico che non sia nel periodo di sospensione
			if (!isTimerSuspended(timerName)) {
				getLog().info("Timer " + timerName + " sta per essere eseguito");
				return true;
			} else {
				getLog().info("Timer " + timerName + " � stato sospeso");
				return false;
			}
		}

		// MANUALE (o default) eseguo sempre il controllo
		getLog().debug("Verifica attivazione del timer: " + timerName);
		String currentNode = System.getProperty("it.tasgroup.timer.currentNode");
		getLog().debug("nodo corrente: " + currentNode);

		getLog().debug("   estrazione configurazione timers");
		Query queryFindGw = manager.createNamedQuery("ListaTimersIris");
		@SuppressWarnings("unchecked")
		List<TimersIris> timers = queryFindGw.getResultList();
		getLog().debug("   n. timers estratti: " + timers.size() );

		for (TimersIris timer : timers) {
			getLog().debug(" TimerName/NodeActive : " + timer.getTimer() + "/" + timer.getNodeActive());
			if (timer.getTimer().equals(timerName)) {
				getLog().debug("trovato TimerName/NodeActive : " + timer.getTimer() + "/" + timer.getNodeActive());
				if(currentNode.equals(timer.getNodeActive())) {
					getLog().info("il timer '" + timerName + "' e' ATTIVO sul nodo: " + currentNode);
					getLog().info("Timer " + timerName + " verifica sospensione ... ");
					// verifico che non sia nel periodo di sospensione
					if (!isTimerSuspended(timerName)) {
						getLog().info("Timer " + timerName + " sta per essere eseguito");
						return true;
					} else {
						getLog().info("Timer " + timerName + " � stato sospeso");
						return false;
					}
				} else {
					getLog().debug("il timer '" + timerName + "' NON E' ATTIVO sul nodo: " + currentNode);
					return false;
				}
			}
		}
		getLog().info("NON TROVO la configurazione del timer '" + timerName + "' --> NON ATTIVO");
		return false;
	}

	private boolean isTimerSuspended(String timerName) {
		try {
			
			String cfgSospensioniTimersAsString = null;
			SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm",Locale.ITALY);
			List<String> cfgSospensioneTimer = new ArrayList<String>(); // Default Value: Lista Vuota
			
			getLog().info("estrazione configurazione timers ....");
			Query queryTimer = manager.createNamedQuery("getTimerByName");
			queryTimer.setParameter("timer", timerName);
			List<TimersIris> timersList = queryTimer.getResultList();

			if (timersList!=null && timersList.size()==1) {
				//ho trovato il timer, posso procedere
				getLog().info("trovata la configurazione del timer " + timerName  );
			
				TimersIris timer = timersList.get(0);

				//verifico le configurazioni di sospensione impostate 
				cfgSospensioniTimersAsString = timer.getSuspend();

				getLog().debug("Acquisito da DB il valore di SUSPEND " + timer.getSuspend());
					
				if (cfgSospensioniTimersAsString != null && !cfgSospensioniTimersAsString.isEmpty()) {
					//sono presenti configurazioni per il timer
					getLog().info("Per il  timer: " + timerName+ "sono presenti le seguenti configurazioni di sospensione "+ cfgSospensioniTimersAsString );

					//verifico se siamo nel periodo di sospensione
					cfgSospensioneTimer = Arrays.asList(cfgSospensioniTimersAsString.split(";"));
	
					for (Iterator<String> iter = cfgSospensioneTimer.iterator(); iter.hasNext(); ) {	
					//scompongo la stringa per recuperare le configurazioni
					//il formato � il seguente	
					//day(from-to) 
					//day � il dayOfWeek
					//form-to -->(h1:m1-h2:m2) � sospeso dalle h1:m1 alle h2:m2, ora locale del server)
					//es: 2(09:00-14:00)+3(09:00-14:00)
	
						String element =iter.next();
						int dayOfWeek = Integer.parseInt(element.substring(0,1));
						String from=element.substring(2,7);
						String to=element.substring(8,13);
							
						Calendar now = Calendar.getInstance();
						Date startTime = dateFormat.parse(from);
						Date endTime = dateFormat.parse(to);
						Date currentTime = dateFormat.parse(dateFormat.format(now.getTime()));
						// dayOfWeek = 0 significa 'tutti i giorni'
						boolean checkDay = dayOfWeek != 0 ? (now.get(Calendar.DAY_OF_WEEK) == dayOfWeek) : true;
						
						if (checkDay  && (startTime.before(currentTime) && endTime.after(currentTime))) {
							getLog().info("il Timer: "+ timerName + " � nel periodo di sospensione");
							return true;
						} 
					}
					// non sono valorizzate correttamente le configurazioni di sospensione
					getLog().info("il Timer: "+ timerName + " NON � nel periodo di sospensione");
					return false;
				} else {
					// non sono presenti configurazioni per il timer
					getLog().info("Per il  timer: " + timerName+ " non sono presenti  configurazioni di sospensione   ");
					return false;
				}		
			} else {
				getLog().info("NON TROVO il record relativo al  timer '" + timerName  );
				return false;
			}
		} catch (Exception e) {
			getLog().info("Non e' stato possibile verificare la sospensione del timer " + timerName +": " + e.getMessage());
			e.printStackTrace();
			return false;

		}
	}

}
