package it.nch.iris.business.custom.util.audit;

import it.nch.is.fo.util.log.LogInitUtils;
import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Iterator;

import org.apache.log4j.Logger;

public class LogManager {
	
	private static Logger log = LogInitUtils.getLogger("it.nch.is.fo.audit.param");
	private final static String INPUT_LOG = "input";
    private final static String OUTPUT_LOG = "output";
    
    private static String serverName;
    
    private static String getServerName() {
    	if (serverName == null) {
    		try {
    			serverName = Inet4Address.getLocalHost().getHostName();
			} catch (UnknownHostException e) {
				serverName = e.getMessage();
			}
    	}
    	return serverName;
    }
	
	public static void prepareLog(ParamLogging logParam, String type, StringBuffer sb){
		ConfigurationPropertyLoader beProperties = ConfigurationPropertyLoader.getInstance("iris-be.properties");
		String logToFileEnabled = beProperties.getProperty("iris.log.file.enabled");
		boolean isLogToFileEnabled = Boolean.valueOf(logToFileEnabled).booleanValue();
		if (isLogToFileEnabled){
			if (type.equals(INPUT_LOG))
				sb.append("[" + getServerName() + "][").append(logParam.getMethodName()).append("] "+type.toUpperCase()+": ");
			else
				sb.append(type.toUpperCase()+": ");
			if (logParam.getLogParam()!=null){
				Iterator iterParam = logParam.getLogParam().iterator();
				while(iterParam.hasNext()){
					ParamToDisplay nextParamName = (ParamToDisplay)iterParam.next();
					
					sb.append(nextParamName.getDisplayName()).append("=").append(nextParamName.getDisplayValue()).append("|");
				}
				sb.append(" ");
			}
		}
	}
	
	public static void writeLog(StringBuffer sb){
		ConfigurationPropertyLoader beProperties = ConfigurationPropertyLoader.getInstance("iris-be.properties");
		String logToFileEnabled = beProperties.getProperty("iris.log.file.enabled");
		boolean isLogToFileEnabled = Boolean.valueOf(logToFileEnabled).booleanValue();
		if (isLogToFileEnabled){
			if (log.isInfoEnabled())
				log.info(sb.toString());
		}
		
	}
	
	public static void debug(String what) {
		log.debug(what);
	}
	
	public static void info(String what) {
		log.info(what);
	}
	
	public static void error(String what) {
		log.error(what);
	}

}
