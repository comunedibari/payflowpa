package it.nch.iris.business.custom.util.audit;

import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

public class ArgumentLoggingHelper {
	
	private static Map ConfigurationLogMap = new HashMap();
	private boolean isConfigurationLoaded = false;
	public void loadConfiguration(boolean areCommonParams, boolean reload) {
		
		Properties loggingProperties = ConfigurationPropertyLoader.getProperties("auditLogging.properties", reload);
		
		if (loggingProperties!=null){
			try {
				Enumeration keys = loggingProperties.keys();
				String key;
				String propertyValue;
				ParamLogging param;
				while(keys.hasMoreElements()){
					key=(String)keys.nextElement();
					propertyValue = loggingProperties.getProperty(key);
					if (propertyValue != null){
						param = createConfigParam(propertyValue);
						ConfigurationLogMap.put(key, param);
					}
						
				}
				isConfigurationLoaded = true;
			}
			catch (Exception e){
				e.printStackTrace();
				isConfigurationLoaded = false;
			}
			
		}
		
	}
	
	
	public ParamLogging getParam(String key, boolean areCommonParams){
		if (!isConfigurationLoaded || isTestMode()){
			//	Se sono in modalit� TEST ricarico COMUNQUE la configurazione
			//	Questo mi permette di cambiare il file auditLogging.properties al volo
			loadConfiguration(areCommonParams, isTestMode());
		}
		if  (isConfigurationLoaded){
			ParamLogging pLoggin = (ParamLogging)ConfigurationLogMap.get(key);
			return pLoggin;
		}
		return null;
	}
	
	/**
	 * Dovrebbe tornare true SOLO per ambienti di test
	 * @return
	 */
	private static boolean isTestMode() {
		ConfigurationPropertyLoader cpl = ConfigurationPropertyLoader.getInstance("iris-be.properties");
		boolean isTestMode = cpl.getBooleanProperty("iris.audit.is.test.mode");
		if (isTestMode) {
			System.err.println("Attenzione! La variabile iris.audit.is.test.mode (in iris-be.properties) vale true! Questo puo' accadere solo in ambienti STRETTAMENTE di test perch� causa degrado delle prestazioni!");
		}
		return isTestMode;
	}
	
	private static ParamLogging createConfigParam(String propertyValue){
		ParamLogging param = new ParamLogging();
		StringTokenizer st = new StringTokenizer(propertyValue,"|");
		String methodName = st.nextToken();
		param.setMethodName(methodName);
		
		Collection listParam = new ArrayList();
		String firstToken;
		while (st.hasMoreTokens()) {
			ParamToDisplay pDisp = new ParamToDisplay();
			String s = st.nextToken();
			if (s.equalsIgnoreCase("ALL_PARAMETERS")) {
				param.setLogAllParams(true);
				break;
			} else {
				int pos = s.lastIndexOf("=");
				if (pos > 0 && pos < s.length() - 1) {
					firstToken = s.substring(0,1);
					pDisp.setDisplayName(s.substring(pos + 1));
					//pDisp.setInternalName(s.substring(0,pos));
					if (firstToken.equals("*"))
						pDisp.setInternalName("businessObject."+s.substring(1,pos));
					else
						pDisp.setInternalName(s.substring(0,pos));
				}
			}
	        listParam.add(pDisp);
	    }
		if (!listParam.isEmpty()){
			param.setLogParam(listParam);
		}

		return param;
		
	}
	

}
