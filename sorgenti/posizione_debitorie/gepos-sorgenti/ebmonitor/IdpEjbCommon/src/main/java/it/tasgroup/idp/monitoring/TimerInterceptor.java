package it.tasgroup.idp.monitoring;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TimerInterceptor extends AbstractTimerActivationController {
	
	private static Log logger = LogFactory.getLog(AbstractTimerActivationController.class);

	@Override
	protected Log getLog() {
		return logger;
	}
	
	@AroundInvoke
	public Object logCall(InvocationContext context) throws Exception {

		Object returnData = null;
		
		String timerName = context.getMethod().getDeclaringClass().getSimpleName();
	    String target = timerName + "." +  context.getMethod().getName();		
	    
		logger.info(" Executing Timer =  " + timerName + " on target " + target);
		
		try {
			if (isTimerActive(timerName)) {
		    	logger.debug("TimerInterceptor, Entering" + target);
			    // Calling business method
		    	returnData = context.proceed();				
			} else {
				logger.debug("The Timer " + timerName + " isn't configured to perform its job on this node");
			}		

		} catch (Exception e) {
			logger.error(this.getClass().getSimpleName() + e.getMessage());
		}
	    
	    return returnData;
	}

}
