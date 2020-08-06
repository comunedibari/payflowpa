/**
 * Created on 28/set/07
 */
package it.nch.eb.xsqlcmd.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * File che legge le properties da un banale file di properties (evitando tutto
 * lo springame complicatissimo)
 * 
 * @author RepettiS
 */
public class DseProperties {

	private static Properties conf = null;

	public static final String FILE_NAME = "dse.properties";
	public final static String FILE_LOCATION_SYSTEM_PROP_NAME = "it.tasgroup.dse.conf.location";
	public final static String CONFIG_PATH_SYSTEM_PROP_NAME = "it.tasgroup.be.conf.path";

//	private static final Log logger = LogFactory.getLog(DseProperties.class);

	public static String getProperty(String name, boolean realTime) {
		return getProperty(name, null, realTime);
	}

	public static String getProperty(String name, String def) {
		return getProperty(name, def, false);
	}

	public static String getProperty(String name) {
		return getProperty(name, null, false);
	}

	
	public static String getProperty(String name, String def, boolean realTime) {

		if (conf == null || realTime) {
			loadProperties();
		}
		if (conf != null && conf.size() > 0) {
			return conf.getProperty(name, def);
		} else {
			return def;
		}

	}

	private static synchronized void loadProperties() {
		String configPath = System.getProperty(CONFIG_PATH_SYSTEM_PROP_NAME);
		
		String fileLocation = System.getProperty(FILE_LOCATION_SYSTEM_PROP_NAME);
		String fileName = fileLocation + "/" + FILE_NAME;
		
		if(configPath != null) {
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
		}
		return p;
	}


}
