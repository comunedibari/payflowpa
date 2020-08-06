package it.tasgroup.idp.monitoring;

import javax.ejb.Stateless;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.tasgroup.idp.monitoring.AbstractTimerActivationController;

@Stateless
public class TimerActivationStatController extends AbstractTimerActivationController implements TimerActivationStatControllerLocal {
	
	private static Log logger = LogFactory.getLog(TimerActivationStatController.class);

	@Override
	protected Log getLog() {
		return logger;
	}
	
}
