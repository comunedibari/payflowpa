package it.tasgroup.idp.proxyndp.timer;

import it.tasgroup.idp.monitoring.TimerActivationControllerLocal;
import it.tasgroup.idp.proxyndp.bean.TestJob;
import it.tasgroup.idp.proxyndp.bean.TestJobRemote;
import it.tasgroup.idp.timer.AbstractTimer;
import it.tasgroup.idp.timer.ITimerLocal;
import it.tasgroup.idp.timer.ITimerRemote;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
//import org.jboss.ejb3.annotation.Clustered;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;

/**
 *
 */
@Stateless
//@Clustered
public class TestTimer extends AbstractTimer implements ITimerLocal, ITimerRemote {
    @EJB(name = "TestJob")
    private TestJobRemote testJob;

    @EJB(beanName = "TimerActivationController")
    private TimerActivationControllerLocal activationController;

    private final Log logger = LogFactory.getLog(this.getClass());

    @Timeout
    public void handleTimeout(Timer timer) throws InterruptedException {
        if (!activationController.isTimerActive(this.getClass().getSimpleName()))
            return;

        logger.info("Invoking job on remote ejb " + timer.getInfo());
        testJob.performOperation(timer.getInfo());
        logger.info("Job on remote ejb has been completed -> ");
    }
}
