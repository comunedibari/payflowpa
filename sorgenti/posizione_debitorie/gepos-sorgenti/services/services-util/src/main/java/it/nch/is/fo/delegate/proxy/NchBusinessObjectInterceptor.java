package it.nch.is.fo.delegate.proxy;

import it.nch.fwk.fo.interfaces.FrontEndContext;
import it.nch.is.fo.util.log.LogInitUtils;
//import it.nch.fwk.fo.util.Tracer;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;
/**
 * proxy dell'oggetto di business in modo da visualizzare il tempo di esecuzione del metodo l'utente e la ragione sociale 
 */
public class NchBusinessObjectInterceptor implements MethodInterceptor {
	/*
	 public Object invoke(MethodInvocation invocation) throws Throwable {
	        Method methodInvoked = invocation.getMethod();
	        String methodName = methodInvoked.getName();
	                
	        Object[] args = invocation.getArguments();
	        FrontEndContext fec = null;
	        String userName = null;
	        String ragioneSociale = null;
	        String sessionId = null;
	        if(args != null){
	         fec = (FrontEndContext)args[0];
	         if(fec != null){
	          userName = fec.getUsername();
	         }
	        }
	        
	        long startTime = System.currentTimeMillis();
	        
	        // esegue la logica del metodo
	        Object retVal = invocation.proceed();
	        
	        long endTime = System.currentTimeMillis();
	        long gapTime = endTime - startTime;
	        Tracer.debug(this.getClass().getName(), "invoke", "\nLOGGER-> BUSINESS IMPL: method invoked: " + methodName + " userName: "+userName + " sessionId: " +sessionId+ " invocation time ms: " + gapTime);
	        
	        return retVal;
	    }
*/
	
	private static final Logger log = LogInitUtils.getLogger("it.nch.is.fo.audit");
    
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Method methodInvoked = invocation.getMethod();
		String methodName = methodInvoked.getName();
		Object[] args = invocation.getArguments();
		FrontEndContext fec = null;
		String userName = null;
		String path = null;
		String sessionId = null;
		if(args != null && args.length > 0){
			fec = (FrontEndContext)args[0];
			if(fec != null){
				userName = fec.getUsername();
				sessionId = fec.getHttpSessionID();
				path = fec.getPathMenuCorrente();
			}
		}
		
		// esegue la logica del metodo
		Object retVal = invocation.proceed();
		
		//if (log.isInfoEnabled())
		//	log.info("["+invocation.getClass().getName()+"::"+methodName+"] userName: "+userName + " sessionId: " +sessionId+ " path " + path);
		
		return retVal;
	}
	
}
