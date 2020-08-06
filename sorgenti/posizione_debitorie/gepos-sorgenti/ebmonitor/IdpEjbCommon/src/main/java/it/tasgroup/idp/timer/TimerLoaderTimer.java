package it.tasgroup.idp.timer;

import it.tasgroup.idp.domain.TimersIris;
import it.tasgroup.idp.util.IdpServiceLocator;
import it.tasgroup.idp.util.ServiceLocalMapper;

import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Stateless
public class TimerLoaderTimer  extends AbstractTimer implements ITimerLocal, ITimerRemote {


    private final Log logger = LogFactory.getLog(this.getClass());

    private HashMap<String, TimersIris> timersIrisSet;


    @PersistenceContext(unitName = ServiceLocalMapper.IdpBTJta)
    private EntityManager manager;

    @PostConstruct
    private void init() {
        logger.info ("Loading timers from database..");
        this.timersIrisSet = loadIrisTimers();
    }


    @Timeout
    public void handleTimeout(Timer timer) {
        List<TimersIris> timersIrisList = getTimersIrisList();

        for (TimersIris timerEntry : timersIrisList) {
            String timerName = timerEntry.getTimer();
            TimersIris previousTimerIris = this.timersIrisSet.get(timerName);

            ITimer t = (ITimer) IdpServiceLocator.getInstance().getServiceByName(timerName);

            if (!timerEntry.equals(previousTimerIris)) {
                logger.info ("Timer " + timerEntry.getTimer() + " has been modified, cancelling and reloading");
                t.cancelTimer(timerName);


                // TODO eventually check consistency of timerEntry object
                if (!timerEntry.getNodeActive().equals("STOPPED")) {
                    if (timerEntry.getStartup() != null) {
                        t.scheduleTimer(timerEntry.getStartup(), timerEntry.getPeriod(), timerName);
                    } else {
                        t.scheduleTimer(timerEntry.getInterval(), timerEntry.getPeriod(), timerName);
                    }
                }
                this.timersIrisSet.put(timerName, timerEntry); // replace entry with new timer scheduling
            } else {
                logger.debug ("Timer " + timerEntry.getTimer() + " has not changed");
            }

        }

    }

    private HashMap<String, TimersIris> loadIrisTimers() {
        HashMap<String,TimersIris> timers = new HashMap<String, TimersIris>();
        @SuppressWarnings("unchecked") List<TimersIris> timersList = getTimersIrisList();
        for (TimersIris timerIris : timersList) {
            timers.put(timerIris.getTimer(), timerIris);
        }

        return timers;
    }

    private List<TimersIris> getTimersIrisList() {
        Query query = manager.createNamedQuery("ListaTimersIris");
        return query.getResultList();
    }

}
