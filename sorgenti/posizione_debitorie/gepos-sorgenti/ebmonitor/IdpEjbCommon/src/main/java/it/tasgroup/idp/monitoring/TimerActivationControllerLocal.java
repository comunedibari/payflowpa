package it.tasgroup.idp.monitoring;

import javax.ejb.Local;

@Local
public interface TimerActivationControllerLocal {
	
	public boolean isTimerActive(String timerName);
	
}
