/**
 * Created on 28/set/07
 */
package it.tasgroup.idp.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class IrisProperties  {
	public static final String FILE_NAME = "iris-be.properties";
	public static final String FILE_LOCATION_SYSTEM_PROP_NAME = SystemPropertiesNames.ENV_CONFIG_PROPERTY_NAME;
	public static final String CONFIG_PATH_SYSTEM_PROP_NAME = "it.tasgroup.be.conf.path";

	private static Properties conf= null;
	
	private final static Log logger = LogFactory.getLog(IrisProperties.class.getName());


	public static String getProperty(String name, boolean forceReload) throws IOException {
		return getProperty(name, "", forceReload);
	}

	public static String getProperty(String name) {
		return getProperty(name, null, false);
	}
	
	public static String getProperty(String name, String defaultValue) {
		String val = getProperty(name);
		return val == null ? defaultValue : val;
	}
	
	public static Long getLongProperty(String name, Long defaultValue) {
		String valString = getProperty(name);

		if (valString == null) {
			logger.warn("Property '"+name+"' non valorizzata. Si usa il default "+defaultValue);
			return defaultValue;
		} 
				
		return Long.parseLong(valString);  //se formattata male.. raise exception

	}
	
	
	
	public static String getProperty(String name, String defaultValue, boolean forceReload) {
		boolean propsLoadedRightNow = false;
		
		if (conf == null) {
			synchronized (IrisProperties.class) {
				if (conf == null) {
					loadProperties();
					propsLoadedRightNow = true;
				}
			}
		}
		if (forceReload && !propsLoadedRightNow) {
			loadProperties();
		}
		if (!conf.isEmpty()) {
			return conf.getProperty(name, defaultValue);
		} else {
			return defaultValue;
		}
	}
	
	public static Properties getPropertiesHavingPrefix(String prefix) {
		if (conf == null) {
			synchronized (IrisProperties.class) {
				if (conf == null) {
					loadProperties();
				}
			}
		}
		Properties outProps = new Properties();
		if (!conf.isEmpty()) {
			synchronized (IrisProperties.class) {
				Iterator<Object> keyIterator = conf.keySet().iterator();
				while (keyIterator.hasNext()) {
					String key = (String) keyIterator.next();
					if (key.startsWith(prefix)) {
						outProps.setProperty(key, conf.getProperty(key));
					}
				}
			}
		}
		return outProps;
	}

	
	private static synchronized void loadProperties() {
		String configPath = System.getProperty(CONFIG_PATH_SYSTEM_PROP_NAME);
		String fileLocation = System.getProperty(FILE_LOCATION_SYSTEM_PROP_NAME);
		String fileName = fileLocation + "/" + FILE_NAME;
		
		if (configPath != null) {
			String fileNameWithPath = configPath.endsWith("/") ? configPath + fileName : configPath + "/" + fileName;
			conf = loadPropertiesFromFileSystem(fileNameWithPath);
		} else {
			conf = loadPropertiesFromClasspath(fileName);
		}
	}

	private static Properties loadPropertiesFromClasspath(String fileName) {
		Properties p = new Properties();
		try {
			System.out.println("Carico il file: " + fileName + " da ClassPath");
			InputStream istream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
			p.load(istream);
			istream.close();
		} catch (IOException e) {
			System.err.println("Eccezione nel caricare il file: " + fileName + " da ClassPath");
			throw new RuntimeException(e);
		}
		return p;
	}

	private static Properties loadPropertiesFromFileSystem(String fileNameWithPath) {
		Properties p = new Properties();
		try {
			System.out.println("Carico il file: " + fileNameWithPath + " da Filesystem");
			FileInputStream istream = new FileInputStream(fileNameWithPath);
			p.load(istream);
			istream.close();
		} catch (IOException e) {
			System.err.println("Eccezione nel caricare il file: " + fileNameWithPath+ " da Filesystem");
			throw new RuntimeException(e);
		}
		return p;
	}
	
	private IrisProperties() {}
}
