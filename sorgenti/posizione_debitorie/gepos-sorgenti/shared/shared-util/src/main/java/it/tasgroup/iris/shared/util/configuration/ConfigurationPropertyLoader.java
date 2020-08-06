package it.tasgroup.iris.shared.util.configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author unknown
 * 
 * Classe che legge le properties da file di configurazione presente su filesystem o sul classpath dell'applicazione 
 * in esecuzione.
 *
 */
public class ConfigurationPropertyLoader {

	private static Map<String, ConfigurationPropertyLoader> propertyMap = new HashMap<String, ConfigurationPropertyLoader>();

	private Properties p;
	private static Logger log = LogManager.getLogger(ConfigurationPropertyLoader.class);

	public static String getAbsoluteConfPath() {
		String configurationAbsolutePath = System.getProperty("tas.properties.file.configuration");
		if (configurationAbsolutePath != null && !configurationAbsolutePath.endsWith("/")) {
			configurationAbsolutePath = configurationAbsolutePath + "/";
		}
		return configurationAbsolutePath;
	}

	public static ConfigurationPropertyLoader getInstance(String fileName) {
		return ConfigurationPropertyLoader.getInstance(fileName, false);
	}
	
	public static ConfigurationPropertyLoader getInstance(String fileName, boolean reload) {
		ConfigurationPropertyLoader instance = propertyMap.get(fileName);
		if (instance == null || reload) {
			if (loadPropertyFile(fileName))
				instance = propertyMap.get(fileName);
		}
		return instance;
	}

	
	public static Properties getProperties(String fileName) {
		return getProperties(fileName, false);
	}

	public static Properties getProperties(String fileName, boolean reload) {
		return ConfigurationPropertyLoader.getInstance(fileName, reload).p;
	}

	
	public String getProperty(String key) {
		return p.getProperty(key);
	}

	public boolean getBooleanProperty(String key) {
		String s = p.getProperty(key);
		if (s == null)
			return false;
		Boolean x = new Boolean(s.trim());
		return x.booleanValue();
	}

	public int getIntProperty(String key) {
		String s = p.getProperty(key);
		if (s == null)
			return 0;
		Integer x = new Integer(s);
		return x.intValue();
	}

	public String getProperty(String key, String defaultV) {
		return p.getProperty(key, defaultV);
	}


	
	
	
	private static boolean loadPropertyFile(String fileName) {
		if (log.isDebugEnabled()) {
			log.debug("[ConfigurationPropertyLoader::loadPropertyFile] looking for file: " + fileName);
		}
		boolean initialized = false;
		String propertyConfigurationFileName = getAbsoluteConfPath();
		if (propertyConfigurationFileName != null) {
			propertyConfigurationFileName = propertyConfigurationFileName + fileName;
		} else {
			propertyConfigurationFileName = fileName;
		}

		try {
			log.debug("[ConfigurationPropertyLoader::loadPropertyFile] trying from filesystem");
			initialized = loadFile(propertyConfigurationFileName, fileName);
			if (!initialized) {
				log.debug("[ConfigurationPropertyLoader::loadPropertyFile] trying from classpath");
				initialized = loadFileFromClassPath(fileName);
			}

		} catch (IOException ex) {
			// se non trovo il file nel path dato dalla system property provo
			// nel classpath
			try {
				initialized = loadFileFromClassPath(fileName);
			} catch (Exception e) {
				initialized = false;
				if (log.isInfoEnabled()) {
					log.info("[ConfigurationPropertyLoader::loadPropertyFile] file: " + fileName + " not found!!!");
				}
			}
		}

		return initialized;
	}

	private static boolean loadFileFromClassPath(String fileName) throws IOException {
		InputStream propInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
		if (propInputStream == null) {
			log.info("[ConfigurationPropertyLoader::loadFileFromClassPath] Could not read file: " + fileName + " from classpath");
			return false;
		}
		Properties p = new Properties();
		p.load(propInputStream);
		if (log.isInfoEnabled()) {
			log.info("[ConfigurationPropertyLoader::loadFileFromClassPath] Got file: " + fileName + " from classpath");
		}
		ConfigurationPropertyLoader cpl = new ConfigurationPropertyLoader();
		cpl.p = p;
		propertyMap.put(fileName, cpl);
		return true;
	}

	private static boolean loadFile(String fileNameWithPath, String fileName) {
		Properties p = new Properties();
		try {
			FileInputStream istream = new FileInputStream(fileNameWithPath);
			p.load(istream);
			istream.close();
		} catch (IOException e) {
			log.info("[ConfigurationPropertyLoader::loadFile] Could not read file: " + fileNameWithPath + " from filesystem");
			return false;
		}

		if (log.isInfoEnabled()) {
			log.info("[ConfigurationPropertyLoader::loadFile] Got file: " + fileNameWithPath + " from filesystem");
		}
		ConfigurationPropertyLoader cpl = new ConfigurationPropertyLoader();
		cpl.p = p;
		propertyMap.put(fileName, cpl);
		return true;
	}

	public static void removeByMap(String fileName) {
		Object o = propertyMap.get(fileName);
		if (o == null) {
			propertyMap.remove(fileName);

		}
	}

	
	
	public static ConfigurationPropertyLoader getInstance(String fileName, Locale locale, boolean reload) {
		
		if(locale == null)
			return getInstance(fileName, reload);
		
		String prefilename = fileName.replace(".properties", "");
		ConfigurationPropertyLoader instance = null;
		
		if(instance == null) 
			instance = getInstance(prefilename + "_" + locale.getLanguage() + "_" + locale.getCountry() + "_" + locale.getVariant() + ".properties", reload);
		
		if(instance == null) 
			instance = getInstance(prefilename + "_" + locale.getLanguage() + "_" + locale.getCountry() + ".properties", reload);
			
		if(instance == null) 
			instance = getInstance(prefilename + "_" + locale.getLanguage() + ".properties", reload);
		
		if(instance == null) 
			instance = getInstance(prefilename + ".properties", reload);

		return instance;
		
	}

	
	public static Map<String, Properties> getLoadedPropertiesMap() {
		Map<String, Properties> map = new HashMap<String, Properties>();
		for (String fileName : propertyMap.keySet()) {
			map.put(fileName, propertyMap.get(fileName).p);
		}
		return map;
	}
	
	public static void snoop() {
		Map<String, Properties> propertiesMap = getLoadedPropertiesMap();
		for (String fileName : propertiesMap.keySet()) {
			System.out.println("FILE: " + fileName);
			Properties prop = propertiesMap.get(fileName);
			for (String chiave : prop.stringPropertyNames()) {
				String valore = prop.getProperty(chiave);
				System.out.println(chiave + ": " + valore);
			}
			System.out.println("-----------------------------------------------------------------------------------------------------");

		}
	}
}
