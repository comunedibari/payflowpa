package it.tasgroup.idp.avvisi.digitali;

import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

import it.tasgroup.idp.avvisi.digitali.impl.AvvisiDigitaliDefaultPlugin;

public class AvvisiDigitaliManager {
	
	private static Properties avvisiDigitaliFactoryProperties = null;
	
	public final static String AVVISI_DIGITALI_MANAGER_PLUGIN = "PAYTAS";
	
	private static IAvvisiDigitaliPlugin avvisiDigitaliPlugin;
    
	/**
	 * Ritorna un'istanza del plugin il cui nome è stato passato come parametro.
	 * L'associazione tra nome e plugin deve stare in un file avvisidigitali-factory.properties nel classpath.
	 * 
	 * Ogni plugin deve essere registrato come:
	 * avvisi.digitali.online.plugin.default=
	 * 
	 * 
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public static IAvvisiDigitaliPlugin getInstance() throws Exception {
		
		if (avvisiDigitaliPlugin==null) {
		   /*loadProperties() ;
		   String pluginClassName = avvisiDigitaliFactoryProperties.getProperty("avvisi.digitali.online.plugin.default");
		   if (StringUtils.isBlank(pluginClassName)) {
		    	throw new Exception(" not found in avvisidigitali-factory.properties in classpath. Error looking up property: avvisi.digitali.online.plugin.default");
		   }
		
		   avvisiDigitaliPlugin = (IAvvisiDigitaliPlugin)Class.forName(pluginClassName).newInstance();
		   */
			avvisiDigitaliPlugin = new AvvisiDigitaliDefaultPlugin();
		}
		return avvisiDigitaliPlugin;
	
	}
	
	// Caricamento properties
	private static synchronized void loadProperties() throws Exception {
		if (avvisiDigitaliFactoryProperties == null) {
			InputStream propertyFile = Thread.currentThread().getContextClassLoader().getResourceAsStream("avvisidigitali-factory.properties");
			Properties props = new Properties();			
			props.load(propertyFile);
			avvisiDigitaliFactoryProperties = props;
		} 
	}	

}
