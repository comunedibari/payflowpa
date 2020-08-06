package it.tasgroup.idp.plugin.api;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class BackEndPluginFactory {


	static public BackEndPlugin getPlugin(String codTributo) {
		Properties addOnConfiguration = null;

		try {
			addOnConfiguration = getPropertiesFromClasspath("addon/addon.properties");
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Error resolving property file addon.properties: check classpath");
		}

		String concretePluginClassName = addOnConfiguration.getProperty("it.tasgroup.backend.plugin." + codTributo.toLowerCase());
		if (concretePluginClassName == null) {
			//throw new RuntimeException("addon.properties non contiene la configurazione per il plugin del tributo " + codTributo
			//		+ ". Il file deve contenere una property che si chiama: it.tasgroup.addon." + codTributo.toLowerCase());
			return null;
		}

		try {
			// suppress unchecked warnings (...!!is not possible to avoid unsafe cast here!!)
			@SuppressWarnings("unchecked")
			Class<BackEndPlugin> clazz = (Class<BackEndPlugin>) Class.forName(concretePluginClassName);
			return clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}



	private static final Properties getPropertiesFromClasspath(String propFileName) throws IOException {
		Properties props = new Properties();
		InputStream inputStream = BackEndPluginFactory.class.getClassLoader().getResourceAsStream(propFileName);
	
		if (inputStream == null) {
			throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
		}
	
		props.load(inputStream);
		return props;
	}

}
