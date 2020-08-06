package it.tasgroup.idp.monitoring;

import it.tasgroup.idp.pojo.StoricoData;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StoricoFlussiInterceptor {


	private final Log logger = LogFactory.getLog(this.getClass());

	@AroundInvoke
	public Object logCall(InvocationContext context) throws Exception {

		Object returnData = null;
		boolean isError = false;
		//try {
			// Get wrapped method references
			String beanClassName = context.getMethod().getDeclaringClass().getSimpleName();
			String businessMethodName = context.getMethod().getName();
			String target = beanClassName + "." + businessMethodName;	 
			long startTime = System.currentTimeMillis();
			long totalTime = 0;

				logger.debug("StoricoFlussiInterceptor, Invoking " + target);
				// Call business method 
				startTime = System.currentTimeMillis();			
				returnData = context.proceed();
				totalTime = System.currentTimeMillis() - startTime;
				logger.debug("Business method " + businessMethodName + "in " + beanClassName + "takes " + totalTime + "ms to execute");


				// Collect monitoring data 
				StoricoData storicoData = new StoricoData();				
				storicoData.setBeanClassName(beanClassName);
				storicoData.setBusinessMethodName(businessMethodName);
				storicoData.setTimestamp_start(startTime);
				storicoData.setTotal_time(totalTime);

					storicoData.setEsito("OK");
					
				if (returnData instanceof StoricoData) {
					StoricoData returnStoricoData = (StoricoData)returnData;
					storicoData.setTipoOperazione(returnStoricoData.getTipoOperazione());
			        storicoData.setProdotto(returnStoricoData.getProdotto());
			        storicoData.setSupporto(returnStoricoData.getSupporto()!=null ? returnStoricoData.getSupporto() : "NOT DEFINED");
				}
				// Save monitoring data
				//storicoFlussiProxy.executeApplicationTransaction(storicoData);
				
				//Log monitoring data
				String logghe = storicoData.getBeanClassName() + " - " + storicoData.getProcesso() + " - " + storicoData.getProdotto()
								+ " - " + storicoData.getSupporto()  + " - " + storicoData.getTipoOperazione() + " - " + storicoData.getNumRecord()
								+ " - " + storicoData.getEsito() + " - " + storicoData.getReceiversys();
				logger.debug("Executed (Processo-Prodotto-Supporto-TipoOp-NumRec-Esito-ReceiverSys) ");
				logger.info("Executed " + logghe);

				logger.debug("StoricoFlussiInterceptor, Exiting " + target);

		return returnData;
	}

}
