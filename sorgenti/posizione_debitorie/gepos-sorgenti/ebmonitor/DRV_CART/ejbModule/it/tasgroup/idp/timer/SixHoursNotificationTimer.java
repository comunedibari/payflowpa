package it.tasgroup.idp.timer;

import it.tasgroup.idp.monitoring.TimerActivationControllerLocal;
import it.tasgroup.idp.monitoring.TimerInterceptor;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.interceptor.Interceptors;

@Stateless
public class SixHoursNotificationTimer extends AbstractNotificationTimer implements ITimerLocal, ITimerRemote {

	public SixHoursNotificationTimer() {
		setNotifFrequence(NotificationFrequence.OGNI_6_ORE);
	}

	@EJB(beanName = "TimerActivationController")
	private TimerActivationControllerLocal activationController;

	@Timeout
	// @Interceptors(TimerInterceptor.class)
	public void handleTimeout(Timer timer) {
		if (!activationController.isTimerActive(this.getClass().getSimpleName()))
			return;

		super.handleTimeout(timer);
	}
}
