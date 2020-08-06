package it.nch.is.fo.delegate.proxy;



import it.nch.fwk.fo.util.StopWatchLogger;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class LoggingInterceptor implements MethodInterceptor {

	public Object invoke(MethodInvocation arg0) throws Throwable {
		
		StopWatchLogger st = new StopWatchLogger(arg0.getThis().getClass().getName(),arg0.getMethod().getName(),"");
		try {
			st.start();
			return arg0.proceed();
		}finally {
			st.stop();
		}
	}

}
