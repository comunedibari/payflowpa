package it.nch.is.fo.delegate.proxy;

import it.nch.fwk.fo.util.ObjectUtilities;
import it.nch.is.fo.util.log.LogInitUtils;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;

public class ArgumentLoggingInterceptor implements MethodInterceptor {
	private static Logger log = LogInitUtils.getLogger("it.nch.is.fo.tracer");
    
	
	public Object invoke(MethodInvocation arg0) throws Throwable {
		String className = arg0.getThis().getClass().getName();
		String methodName = arg0.getMethod().getName();
		Object result = null;
		try {
			if (log.isDebugEnabled()) {
				Object[] argu = arg0.getArguments();
				StringBuffer sb = new StringBuffer();
				sb.append("[").append(className)
				.append("::").append(methodName)
				
				.append("] BEGIN");
				if (argu==null || argu.length==0)
					log.debug(sb.toString());
				else
					for (int i = 0; i<argu.length;i++) {
						if (i==0) {
							sb.append(" Arguments are: ");
							log.debug(sb.toString());
						}
						sb=new StringBuffer().append("arg[").append(i)
						.append("] = ").append(ObjectUtilities.objectToString(argu[i]));
						log.debug(sb.toString());
					}
				
			}
			result = arg0.proceed();
			return result;
		}finally {
			if (log.isDebugEnabled()) {
				if (result==null)
					log.debug("[" +className+ "::" + methodName+ "] END");
				else
					log.debug("[" +className+ "::" + methodName+ "] END result is: " + ObjectUtilities.objectToString(result));
			
		    }
		}
	}

}
