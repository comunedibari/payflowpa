package it.nch.fwk.fo.util;


import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Properties;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Tracer {
	private static final String separator = " | ";
	static boolean initialized = false;
	private static String propertyName = "tas.properties.file.configuration";
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
	public static void setPropertyName(String name){
		propertyName = name;
	}
	public static void setInitialized(boolean state){
		initialized = state;
	}
	private static synchronized void initSynch() {
		String message = null;
		if (!initialized) {
			
			String log4jConfigurationFileName =  System.getProperty(propertyName);
			
			if (log4jConfigurationFileName != null){
				if (!log4jConfigurationFileName.endsWith("/"))
					log4jConfigurationFileName = log4jConfigurationFileName + "/";
				log4jConfigurationFileName = log4jConfigurationFileName + "log4j.properties";
				message = "Got log configuration file from SystemProperty ";
			}
			int watch=0;
			if (log4jConfigurationFileName == null) {
				try {
					InputStream i =Thread.currentThread().getContextClassLoader().getResourceAsStream("loginit.properties");
					Properties p = new Properties();
					if (i!=null) {
						p.load(i);
						log4jConfigurationFileName = p.getProperty("log4j.filename");
						String watchTime = p.getProperty("log4j.watch.time");
						message = "Got log configuration file from property file loginit.properties: " + log4jConfigurationFileName;
						
						try {
							if (watchTime!=null)
								watch = Integer.parseInt(watchTime);
						} 
						catch(Throwable e) {
							e.printStackTrace();
						}
					}
				}
				
				catch (Throwable e) {
					e.printStackTrace();
				}
			}
			if (log4jConfigurationFileName!= null && !log4jConfigurationFileName.startsWith("{")) {
				try {
					if (watch>0)
						PropertyConfigurator.configureAndWatch(log4jConfigurationFileName,watch);
					else
						PropertyConfigurator.configure(log4jConfigurationFileName);
				} 
				catch (Throwable e) {
					e.printStackTrace();
				}
			} else message = "Searching default log configuration file into classpath";
			Logger log = Logger.getLogger(Tracer.class);
			log.info("[Tracer::init] " + "log initialized!!!!!!!!!!!!!!!!!!");
			if (log.isDebugEnabled())
				log.debug("[Tracer::init] " + message);
			initialized=true;
		} 
		
	}
	/**
	 * @deprecated
	 */	
	public static void write(String className,	String methodName,String msg) {
		String row ="Class : "+ className + " | Method : " 
		+ methodName + " | Message :" + msg;
	}
	
	/**
	 * @deprecated
	 */
	public static void writeWithTime(String className,	String methodName,String msg) {
		
		String row ="TIME="+(new Timestamp(System.currentTimeMillis())).toString()+" Class : "+ className + " | Method : " 
		+ methodName + " | Message :" + msg;
	}
	
	/**
	 * @deprecated
	 */
	public static Tracer getInstance(){
		return new Tracer();
	}
	
	public static void debug(String className, String methodName, String msg)
	throws TracerException {
		debug(className, methodName, msg, null);
	}

	public static void debug(String className, String methodName, String msg,
			Object optionalObject) throws TracerException {
		
		try {
			//Logger logger = Logger.getLogger(className);
			Logger logger = getLogger(className);
			if (logger.isDebugEnabled()) {
				logger.debug(createOutput(className, methodName, msg));
				if (optionalObject != null) {
					if(optionalObject instanceof Throwable){
						logger.debug(optionalObject,(Throwable)optionalObject);
					} else {
						logger.debug(optionalObject);
					}
				}
			}
		} catch (Exception e) {
			throw new TracerException();
		}
	}
	
	public static void info(String className, String methodName, String msg)
	throws TracerException {
		info(className, methodName, msg, null);
	}
	
	public static void info(String className, String methodName, String msg,
			Object optionalObject) throws TracerException {
		
		try {
			//Logger logger = Logger.getLogger(className);
			Logger logger = getLogger(className);
			if (logger.isInfoEnabled()) {
				logger.info(createOutput(className, methodName, msg));
				if (optionalObject != null) {
					if(optionalObject instanceof Throwable){
						logger.info(optionalObject,(Throwable)optionalObject);
					} else {
						logger.info(optionalObject);
					}
				}
			}
		} catch (Exception e) {
			throw new TracerException();
		}
	}
	
	public static void warn(String className, String methodName, String msg)
	throws TracerException {
		warn(className, methodName, msg, null);
	}
	
	public static void warn(String className, String methodName, String msg,
			Object optionalObject) throws TracerException {
		
		try {
			//Logger logger = Logger.getLogger(className);
			Logger logger = getLogger(className);
			if(logger.isEnabledFor(Level.WARN)){
				logger.warn(createOutput(className, methodName, msg));
				if(optionalObject instanceof Throwable){
					logger.warn(optionalObject,(Throwable)optionalObject);
				} else {
					logger.warn(optionalObject);
				}
			}
		} catch (Exception e) {
			throw new TracerException();
		}
	}
	
	public static void error(String className, String methodName, String msg)
	throws TracerException {
		error(className, methodName, msg, null);
	}
	
	public static void error(String className, String methodName, String msg,Object optionalObject) throws TracerException {
		
		try {
			//Logger logger = Logger.getLogger(className);
			Logger logger = getLogger(className);
			if(logger.isEnabledFor(Level.ERROR)){
				logger.error(createOutput(className, methodName, msg));
				if (optionalObject != null) {
					if(optionalObject instanceof Throwable){
						logger.error(optionalObject,(Throwable)optionalObject);
					} else {
						logger.error(optionalObject);
					}
				}
			}
		} catch (Exception e) {
			throw new TracerException();
		}
	}
	
	public static void fatal(String className, String methodName, String msg)
	throws TracerException {
		fatal(className, methodName, msg, null);
	}
	
	public static void fatal(String className, String methodName, String msg,
			Object optionalObject) throws TracerException {
		
		try {
			//Logger logger = Logger.getLogger(className);
			Logger logger = getLogger(className);
			if(logger.isEnabledFor(Level.FATAL)){
				logger.fatal(createOutput(className, methodName, msg));
				if(optionalObject instanceof Throwable){
					logger.fatal(optionalObject,(Throwable)optionalObject);
				} else {
					logger.fatal(optionalObject);
				}
			}
		} catch (Exception e) {
			throw new TracerException();
		}
	}
	
	private static StringBuffer createOutput(String className,
			String methodName, String msg) throws TracerException {
		StringBuffer sb = new StringBuffer(className);
		sb.append(separator);
		sb.append(methodName);
		sb.append(separator);
		return sb.append(msg);
	}
	
	public static boolean isDebugEnabled(String className) {
		return getLogger(className).isDebugEnabled();
		//return Logger.getLogger(className).isDebugEnabled();
	}
	
	public static boolean isInfoEnabled(String className) {
		//return Logger.getLogger(className).isInfoEnabled();
		return getLogger(className).isInfoEnabled();
	}
	
}