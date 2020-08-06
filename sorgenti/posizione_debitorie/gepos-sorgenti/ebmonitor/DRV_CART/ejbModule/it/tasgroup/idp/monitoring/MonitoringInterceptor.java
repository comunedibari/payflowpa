package it.tasgroup.idp.monitoring;

import it.tasgroup.idp.pojo.MonitoringData;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MonitoringInterceptor {

	
	private final Log logger = LogFactory.getLog(this.getClass());	
	
	@AroundInvoke
	public Object logCall(InvocationContext context) throws Exception {
//		Map mappa = context.getContextData();
//		//temporaneo, da eliminare
//		try {
//			Iterator set = mappa.keySet().iterator();
//			while (set.hasNext()) {
//				String key =(String) set.next();
//				logger.info("MAPPA : " + key + " - " + mappa.get(key));			
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		Object[] obj = context.getParameters();
//		for (int i = 0; i < obj.length; i++) {
//			logger.info("PARAMETERS : " + obj[i].toString());
//		}
		
//		logger.info("Invoking method:" + context.getMethod());
		
		//calling datastorage as EJB 3
	    String beanClassName = context.getMethod().getDeclaringClass().getSimpleName();
	    String businessMethodName = context.getMethod().getName();
	    String target = beanClassName + "." + businessMethodName;
	    	    
	    long startTime = System.currentTimeMillis();
	    logger.debug("MonitoringInterceptor, Invoking " + target);	
	    MonitoringData monData = new MonitoringData();
	    monData.setBeanClassName(beanClassName);
	    monData.setBusinessMethodName(businessMethodName);
	    monData.setTimestamp_start(startTime);
	    
	    MonitoringData monData2 = null;
	    Object returnData = null;
	    try { 
		    //calling real method   
//	        return context.proceed();
	    	returnData = context.proceed();
	    	if (returnData instanceof MonitoringData) {
	    		monData2 = (MonitoringData)returnData;	
			} 
	    	
	        
	      } finally {
	    	logger.debug("MonitoringInterceptor, Exiting " + target);
	        long totalTime = System.currentTimeMillis() - startTime;
	        //recupero i dati settati dagli ejb di biz logic
	        if (monData2!=null) {
		        monData.setE2emsgid(monData2.getE2emsgid());
		        monData.setSenderid(monData2.getSenderid());
		        monData.setSendersys(monData2.getSendersys());
		        monData.setReceiverid(monData2.getReceiverid());
		        monData.setReceiversys(monData2.getReceiversys());
		        monData.setNumRecord(monData2.getNumRecord());				
			}
	        monData.setTotal_time(totalTime);
	        logger.debug("Business method " + businessMethodName + "in " + beanClassName + "takes " + totalTime + "ms to execute");
	        
	        //saving data
//	        MonitoringProcessiProxy.executeApplicationTransaction(monData);
	        
			//Log monitoring data
			String logghe = monData.getBeanClassName() + " - " + monData.getE2emsgid() + " - " + monData.getSenderid()
							+ " - " + monData.getSendersys()  + " - " + monData.getProdotto() + " - " + monData.getTipoOperazione() 
							+ " - " + monData.getNumRecord() + " - " + monData.getMessaggioErrore() + " - " + monData.getTotal_time()
							+ " - " + monData.getEsito() + " - " + monData.getReceiverid() + " - " + monData.getReceiversys();
			logger.debug("Executed (E2eMsgi-SndId-SndSys-Prodotto-TipoOp-NumRec-MexErrore-TotalTime-Esito-RcvrId-ReceiverSys) ");
			logger.debug("Executed " + logghe);
	        
	    }
	      
	    
//		return context.proceed();
	    return returnData;
	}
	
}
