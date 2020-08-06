package it.tasgroup.idp.timer;

import it.tasgroup.idp.monitoring.TimerActivationControllerLocal;
import it.tasgroup.idp.monitoring.TimerInterceptor;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.interceptor.Interceptors;

@Stateless
public class EventNotificationTimer extends AbstractNotificationTimer implements ITimerLocal, ITimerRemote {

	public EventNotificationTimer() {
		setNotifFrequence(NotificationFrequence.AD_EVENTO);
	}
	
	@EJB(beanName = "TimerActivationController")
	private TimerActivationControllerLocal activationController;

	@Timeout
	// @Interceptors(TimerInterceptor.class)
	public void handleTimeout(Timer timer) {
		if(!activationController.isTimerActive(this.getClass().getSimpleName()))
			return;

		super.handleTimeout(timer);
		super.cleanUpNotifichePagamenti();
	}
}
