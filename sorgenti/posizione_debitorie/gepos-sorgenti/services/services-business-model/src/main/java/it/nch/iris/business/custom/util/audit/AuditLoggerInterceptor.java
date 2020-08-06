package it.nch.iris.business.custom.util.audit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class AuditLoggerInterceptor implements MethodInterceptor {
	
    private ArgumentLoggingHelper argHelper = new ArgumentLoggingHelper();
    private final static String INPUT_LOG = "input";
    private final static String OUTPUT_LOG = "output";
    private final static String COMMON_PARAMETER_TO_LOG = "AllClass.allMethod";
    
	public Object invoke(MethodInvocation arg0) throws Throwable {
		Object result = null;
		StringBuffer sb = null;
		String methodName=null;
		String className = null;
		try {
			
			methodName = arg0.getMethod().getName();
		    className = arg0.getThis().getClass().getName();
		    
		    //
		    //	Se il trace è a debug loggo anche classe.metodo, SEMPRE
		    //
		    LogManager.debug(className + "." + methodName);
		    
			int pos = className.lastIndexOf('.');
			if (pos > 0 && pos < className.length() - 1)
			{
				className = className.substring(pos + 1);
			}
			sb = new StringBuffer();
			//ParamLogging pLog = _prepareDataForLogging(arg0.getArguments(), INPUT_LOG,className,methodName);
			ParamLogging pLog = createDataForLogging(arg0.getArguments(), INPUT_LOG,className,methodName);
			if (pLog !=null){
				LogManager.prepareLog(pLog, INPUT_LOG,sb);
			}
		
			result = arg0.proceed();
			return result;
		}finally {
			ParamLogging pLog=null;
			if (result !=null){
				Object[] argu = new Object[1];
				argu[0] = result;
				//pLog = _prepareDataForLogging(argu, OUTPUT_LOG,className,methodName);
				pLog = createDataForLogging(argu, OUTPUT_LOG,className,methodName);
			}
			if (pLog !=null){
				LogManager.prepareLog(pLog, OUTPUT_LOG,sb);
			}
			if(sb!=null && !sb.toString().equals(""))
				LogManager.writeLog(sb);
		}
			
	}
	
	private ParamLogging createDataForLogging(Object[] argu, String type,String className,String methodName){
		ParamLogging returnLogParam= new ParamLogging();
		LoggerConfigurationData configData = prepareConfigurationForLogging(argu,type,className,methodName);
		if (configData==null)
			return null;
		
		returnLogParam = prepareDataForLogging(argu,type,className,methodName,configData);
		returnLogParam.setMethodName(configData.getMethodName());
		
		return returnLogParam;
	}
	
	private LoggerConfigurationData prepareConfigurationForLogging(Object[] argu, String type,String className,String methodName){
		LoggerConfigurationData configData = new LoggerConfigurationData();
		
		boolean isMethodToLog = false;
		// carico la configurazione dei parametri da loggare per il metodo corrente
		String key = className+"."+methodName+"."+type;
		ParamLogging businessLogParam = argHelper.getParam(key,false);
		
		if(businessLogParam != null && (businessLogParam.getLogParam()!=null || businessLogParam.isLogAllParams())){
			if (businessLogParam.getLogParam()!=null) {
				configData.setBusinessLogParameter(businessLogParam.getLogParam());
			} else {
				//
				//	Caso 'ALL_PARAMETERS', valorizzo BusinessLogParameter con un array vuoto
				//
				configData.setBusinessLogParameter(new ArrayList());
			}
			isMethodToLog=true;
			configData.setMethodName(businessLogParam.getMethodName());
			if (businessLogParam.isLogAllParams()) {
				configData.setLogAllParams(true);
			}
		}
		// se non esiste la configurazione per il log dei dati di business vuol dire che non devo loggare niente
		if (!isMethodToLog)
			return null;
		
		ParamLogging comParam = argHelper.getParam(COMMON_PARAMETER_TO_LOG+"."+type,true);
		
		if(comParam != null && comParam.getLogParam()!=null){
			configData.setCommonLogParameter(comParam.getLogParam());
		}
		
		return configData;
		
	}
	
	private ParamLogging prepareDataForLogging(Object[] argu, String type,String className,String methodName,LoggerConfigurationData configData){
		ParamLogging logParam= new ParamLogging();
		
		// preparo i valori da stampare
		if (argu!=null && argu.length>0){
			HashMap inputParam = new HashMap();
			String orderKey = new String();
			// metto i dati in una hashmap
			for (int i = 0; i<argu.length;i++) {
				IrisObjectUtilities.objectToMap(argu[i],inputParam,null);
			}
			int i = 0;
			Collection commonsLogParams = new ArrayList();
			HashMap orderMap = new HashMap();
			if (configData!=null){
				commonsLogParams = setCommonData(inputParam, configData);
				// recupero i dati di input di business da loggare e li metto in una hashmap per poterli 
				// poi stampare in modo ordinato(potrebbero essere collezioni e non oggetti singoli
				
				i = preparedOrderdData(inputParam,configData,orderMap);
				setBusinessLogData( orderMap, commonsLogParams,  i,  configData,  orderKey );
				
				logParam.setLogParam(commonsLogParams);
			}
			
			
		}
		return logParam;
	}
	
	private void setBusinessLogData(HashMap orderMap,Collection commonsLogParams, int i, LoggerConfigurationData configData, String orderKey ){
		Collection returnParamList = new ArrayList();
		for (int j=0; j<i; j++){
			Iterator newBusinessParamIterator = configData.getBusinessLogParameter().iterator();
			while(newBusinessParamIterator.hasNext()){
				ParamToDisplay nextParam = (ParamToDisplay)newBusinessParamIterator.next();
				
				ParamToDisplay newParam = new ParamToDisplay();
				newParam.setInternalName(nextParam.getInternalName());
				newParam.setDisplayName(nextParam.getDisplayName());
				orderKey = nextParam.getInternalName()+"."+j;
				newParam.setDisplayValue(orderMap.get(orderKey));
				returnParamList.add(newParam);
			}
		}
		if (!returnParamList.isEmpty()){
			Iterator returnParamListIterator = returnParamList.iterator();
			while(returnParamListIterator.hasNext()){
				commonsLogParams.add(returnParamListIterator.next());
			}
				
		}
	}
	private int preparedOrderdData(HashMap inputParam,LoggerConfigurationData configData, HashMap orderMap){
		
		String orderKey = new String();
		int i=0;
		if (configData.isLogAllParams()) {
			//
			//	Questo ramo dell'IF si attiva se (per la coppia classe.metodo) è stata
			//	impostata la chiave 'ALL_PARAMETERS' (ad esempio:
			//		FlowserviceImpl.updateFlow.input=aggiornamentoFlusso(cambio di stato)|ALL_PARAMETERS
			//	). Questo serve solo per avere un'idea dei parametri loggabili, poi andranno filtrati
			//	Vista la provvisorietà, li stampo sul System.out
			//
			Set keys = inputParam.keySet();
			for (Iterator kIt = keys.iterator(); kIt.hasNext(); ) {
				Object key = kIt.next();
				Collection paramValue = (Collection)inputParam.get(key);
				if (paramValue != null){
					Iterator parValIter = paramValue.iterator();
					// devo recuperare in ordine tutti i valori da stampare
					i=0;
						
					while (parValIter.hasNext()){
						Object val = parValIter.next();
						System.out.println(key + "\t\t" + val);
						orderMap.put(key, val);
						i++;
					}
				}
			}
		} else {
			if ( configData.getBusinessLogParameter()!=null){
				Iterator businessConfigParam = configData.getBusinessLogParameter().iterator();
				
				while(businessConfigParam.hasNext()){
					ParamToDisplay nextParamToDisplay = (ParamToDisplay)businessConfigParam.next();
					Collection paramValue = (Collection)inputParam.get(nextParamToDisplay.getInternalName());
					
					// nella hash map ci sono delle Collection di Valori
					
					if (paramValue != null){
						Iterator parValIter = paramValue.iterator();
						// devo recuperare in ordine tutti i valori da stampare
						i=0;
							
						while (parValIter.hasNext()){
							orderKey = nextParamToDisplay.getInternalName()+"."+i;
							orderMap.put(orderKey, parValIter.next());
							i++;
						}
					}
				}
			}
		}
		return i;
	}
	private Collection setCommonData(HashMap inputParam,LoggerConfigurationData configData){
		Collection commonsLogParams = new ArrayList();
		if ( configData.getCommonLogParameter()!=null){
			Iterator iterParam = configData.getCommonLogParameter().iterator();
			
			while(iterParam.hasNext()){
				ParamToDisplay nextParamToDisplay = (ParamToDisplay)iterParam.next();
				Collection paramValue = (Collection)inputParam.get(nextParamToDisplay.getInternalName());
				
				// nella hash map ci sono delle Collection di Valori
				// recupero i valori dei parametri comuni a tutte le action da loggare
				if (paramValue != null){
					Iterator parValIter = paramValue.iterator();
					if (parValIter.hasNext()){
						ParamToDisplay newParam = new ParamToDisplay();
						newParam.setInternalName(nextParamToDisplay.getInternalName());
						newParam.setDisplayName(nextParamToDisplay.getDisplayName());
						newParam.setDisplayValue(parValIter.next());
						commonsLogParams.add(newParam);
								
					}
				}
			}
		}
		return commonsLogParams;
	}
	
}
