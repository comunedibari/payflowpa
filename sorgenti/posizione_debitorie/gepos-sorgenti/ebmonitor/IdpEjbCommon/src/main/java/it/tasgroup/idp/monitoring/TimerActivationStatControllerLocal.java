package it.tasgroup.idp.monitoring;

import javax.ejb.Local;

@Local
public interface TimerActivationStatControllerLocal {
	
	public boolean isTimerActive(String timerName);
	
}
