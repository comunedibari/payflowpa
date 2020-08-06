package it.tasgroup.idp.monitoring;

import javax.ejb.Stateless;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Stateless
public class TimerActivationController extends AbstractTimerActivationController implements TimerActivationControllerLocal {
	
	private static Log logger = LogFactory.getLog(AbstractTimerActivationController.class);

	@Override
	protected Log getLog() {
		return logger;
	}
	
}
