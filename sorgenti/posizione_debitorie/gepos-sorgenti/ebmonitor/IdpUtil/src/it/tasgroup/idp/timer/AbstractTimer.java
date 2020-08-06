package it.tasgroup.idp.timer;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Timer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AbstractTimer implements ITimer {

    private static Log logger = LogFactory.getLog(AbstractTimer.class);

    @Resource
    private SessionContext ctx;

    @Override
    @Deprecated
    public void startTimer() {

    }

    @Override
    @Deprecated
    public void stopTimer() {
        Timer timer = null;
        @SuppressWarnings("unchecked")
        Collection<Timer> timers = ctx.getTimerService().getTimers();
        Iterator<Timer> iter = timers.iterator();
        while (iter.hasNext()) {
            timer = (Timer) iter.next();
            if (timer != null) {
                logger.debug("Stopping Timer " + timer.getClass().getName());
                if (timer.getInfo() != null) {
                    logger.debug("Stopping Timer, Info = " + timer.getInfo().toString());
                }

            }
            timer.cancel();
        }
    }

    public String checkStatus() {
        Timer timer = null;
        @SuppressWarnings("unchecked")
        Collection<Timer> timers = ctx.getTimerService().getTimers();
        Iterator<Timer> iter = timers.iterator();
        while (iter.hasNext()) {
            timer = (Timer) iter.next();
            logger.debug("1)Timer will expire after " + timer.getTimeRemaining() + " milliseconds." + timer.getInfo());

            return ("Timer " + timer.getInfo() + " will expire after " + (timer.getTimeRemaining() / 1000) + " seconds.");
        }
        return ("No timer found");
    }

    @Override
    @Deprecated
    public void startTimer(int startFrom, int interval) {
        ctx.getTimerService().createTimer(startFrom, interval, null);
        logger.debug(this.getClass().getName() + " set, interval " + interval);
    }

    /**
     *
     */
    public void scheduleTimer(Date expiration, Serializable info) {
        logger.info("Create single action timer [info=" + info + "]");
        ctx.getTimerService().createTimer(expiration, info);
    }

    /**
     *
     */
    public void scheduleTimer(long initialDuration, long intervalDuration, Serializable info) {
        logger.info("Create initial+interval timer [info=" + info + "], interval: " + initialDuration + ", period: "
                + intervalDuration);
        ctx.getTimerService().createTimer(initialDuration, intervalDuration, info);
    }


    public void scheduleTimer(Date initialExpiration, long intervalDuration, Serializable info) {
        logger.info("Create initial ('point in time') +interval timer [info=" + info + "], first occurs (date) : " + initialExpiration + ", period: " + intervalDuration);
        Date newInitialExpiration = computeExpirationDate(initialExpiration, intervalDuration, info);
        ctx.getTimerService().createTimer(newInitialExpiration, intervalDuration, info);
    }

    public void cancelTimer(Serializable info) {
        logger.info("Cancel timer [info=" + info + "]");
        @SuppressWarnings("unchecked") Collection<Timer> timers = getTimers();
        logger.info(timers.size() + " timers has been found, info : " + info);
        for (Timer timer : timers) {
            if (timer.getInfo().equals(info)) {
                logger.info("Timer[info=" + info + "] found, cancelling...");
                timer.cancel();
                logger.info("Timer[info=" + info + "] cancelled");
            }
        }
    }

    @Override
    public Collection<Timer> getTimers() {
        return ctx.getTimerService().getTimers();
    }

    /**
     * Nel caso in cui l'initialExpiration (specificata in configurazione) sia antecedente l'istante in cui questo metodo
     * viene invocato, viene ricalcolata una new initial expiration tale che :
     * newInitialExpiration = (initialExpiration + (n * period)), in modo tale che
     * now < newInitialiExpiration < now + period
     * @param initialExpiration initial expiration
     * @param period periodo
     * @param info informazioni legate al timer (tipicamente il nome del timer)
     * @return la new initial expiration ricalcolata (newInitialExpiration)
     */
    private static Date computeExpirationDate(Date initialExpiration, long period, Serializable info) {
        Calendar expirationCalendar = new GregorianCalendar();
        expirationCalendar.setTime(initialExpiration);
        Calendar now = new GregorianCalendar();

        if (initialExpiration.before(now.getTime())) {
            long expirationMsec = initialExpiration.getTime();

            long nowMsec = now.getTimeInMillis();
            long deltaToNextExpiration = (nowMsec + period) - expirationMsec;

            long nPeriods = (deltaToNextExpiration / period);
            logger.debug("nPeriods = " + nPeriods);

            long intervalToFirstExpiration = nPeriods * period;
            expirationCalendar.setTime(new Date(now.get(Calendar.MILLISECOND) + intervalToFirstExpiration));
            logger.warn(String.format("La data e l'ora specificate come initial expiration (%s), sono antecedenti ad adesso (%s), il timer %s sarà schedulato a partire da (%s)", initialExpiration, now.getTime(), info, expirationCalendar.getTime()));
        } else {
            return initialExpiration;
        }

        return expirationCalendar.getTime();

    }


}
