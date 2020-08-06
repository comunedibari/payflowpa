package it.tasgroup.idp.proxyndp.utils;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PostTimerBusinessMethodInterceptor {
	private final Log logger = LogFactory.getLog(this.getClass());

	@Resource
	private EJBContext ctx;
	
	@AroundInvoke
	public Object businessMethod (InvocationContext context) throws Exception {
		try {
			return context.proceed();
		} catch (Exception e) {
			logger.error("Errore in esecuzione del metodo %1 della classe %2".replace("%1", context.getMethod().getName()).replace("%2", context.getTarget().getClass().getSimpleName()), e);
			try {
				this.ctx.setRollbackOnly();
				logger.info("Eseguito il rollback della transazione");
			} catch (IllegalStateException rollbackNotAllowedException) {
				// Il metodo invocato non girava in un contesto transazionale: farne rollback non aveva senso  				
			} catch (Exception afterRollbackException) {
				logger.info("Errore in fase di rollback della transazione", afterRollbackException);
				throw(e);
			}
			return null;
		}
	}
}
