package it.nch.is.fo.delegate.proxy;

import java.lang.reflect.Method;

import it.nch.fwk.fo.util.Tracer;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.ejb.access.SimpleRemoteStatelessSessionProxyFactoryBean;

/**
 * estensione della SimpleRemoteStatelessSessionProxyFactoryBean di Spring per eseguire log prima e dopo la chiamata
 * in questo modo evidenzio il tempo di esecuzione della chiamata remota
 */
public class SimpleRemoteStatelessSessionLoggerProxyFactoryBean extends SimpleRemoteStatelessSessionProxyFactoryBean {

    public Object invoke(MethodInvocation invocation) throws Throwable {
    	Method methodInvoked = invocation.getMethod();
        String methodName = methodInvoked.getName();
        
        long startTime = System.currentTimeMillis();
        
        Object retVal = super.invoke(invocation);
        
        long endTime = System.currentTimeMillis();
        long gapTime = endTime - startTime;
        if (Tracer.isDebugEnabled(getClass().getName()))
        	Tracer.debug(this.getClass().getName(), "invoke", "\nLOGGER-> DELEGATE (REMOTE): method invoked: "+methodName+" invocation time ms: " + gapTime);
        
        return retVal;
    }
    
}

