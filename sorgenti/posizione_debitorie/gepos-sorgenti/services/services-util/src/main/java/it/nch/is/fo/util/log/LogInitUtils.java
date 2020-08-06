package it.nch.is.fo.util.log;



import it.nch.is.fo.CommonConstant;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LogInitUtils {
	
	static boolean initialized = false;
	
	public  static void init() {
		if (!initialized)
			initSynch();
	}
	
	public static Logger getLogger(Class c){
		init();
		return Logger.getLogger(c);
	}
	public static Logger getLogger(String c){
		init();
		return Logger.getLogger(c);
	}
	private static synchronized void initSynch() {
		String message = null;
		if (!initialized) {
			
			String log4jConfigurationFileName =  System.getProperty(CommonConstant.PROPERTY_FILE_PATH);
			if (log4jConfigurationFileName != null){
				if (!log4jConfigurationFileName.endsWith("/"))
					log4jConfigurationFileName = log4jConfigurationFileName + "/";
				log4jConfigurationFileName = log4jConfigurationFileName + "log4j.properties";
				message = "Got log configuration file from SystemProperty "+ CommonConstant.PROPERTY_FILE_PATH+" value: " + log4jConfigurationFileName;
			}
			int watch=0;
			if (log4jConfigurationFileName == null) {
				try {
					InputStream i =Thread.currentThread().getContextClassLoader().getResourceAsStream("loginit.properties");
					Properties p = new Properties();
					if (i!=null) {
						p.load(i);
						log4jConfigurationFileName = p.getProperty("log4j.filename");
						
						try {
							InputStream j = Thread.currentThread().getContextClassLoader().getResourceAsStream(log4jConfigurationFileName);
							Properties props = new Properties();
							props.load(j);
						}
						catch(Throwable e) {
							//log4jConfigurationFileName = "log4j.properties";
							message = e.getMessage() + "  --> Using default log configuration file into classpath";
							log4jConfigurationFileName = null;
						}
						String watchTime = p.getProperty("log4j.watch.time");
						message = "Got log configuration file from property file loginit.properties: " + log4jConfigurationFileName;
						
						try {
							if (watchTime!=null)
								watch = Integer.parseInt(watchTime);
						} 
						catch(Throwable e) {
							
						}
					}
				}
				catch (FileNotFoundException e) {
					// se non trovo il path del log4j da nessuna parte prendo quello che si trova nel classpath
					//log4jConfigurationFileName = "log4j.properties";
					message = e.getMessage() + "  --> Using default log configuration file into classpath";
				}
				catch (Throwable e) {
					//e.printStackTrace();
				}
			}
			// controllo che sia stato risolto il valore della variabile
			if (log4jConfigurationFileName!= null && !log4jConfigurationFileName.startsWith("{")) {
				if (watch>0)
					PropertyConfigurator.configureAndWatch(log4jConfigurationFileName,watch);
				else
					PropertyConfigurator.configure(log4jConfigurationFileName);
				
				initialized=true;
			} 
			Logger log = Logger.getLogger(LogInitUtils.class);
			if (log.isDebugEnabled())
				log.debug("[LogInitUtils::init] " + message);
			
		} 
		
	}
}
