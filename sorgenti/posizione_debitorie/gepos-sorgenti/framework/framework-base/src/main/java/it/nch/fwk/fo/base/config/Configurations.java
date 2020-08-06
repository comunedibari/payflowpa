package it.nch.fwk.fo.base.config;
import java.math.BigDecimal;

import org.apache.log4j.Logger;


/**
* <br>
* $Author: CattaniA $
* <br>
* $Revision: 1.1.1.1 $
* <br>
* <br>
* Interfaccia per la lettura (e caching) file di Properties.
**/
public class Configurations {

    private static Logger logger = Logger.getLogger(Configurations.class);

    static
    { logger.info(Configurations.class); }

    public static ConfigProperties getProperties(String filename) {
        //OPT: Invece di creare ogni volta un oggetto si potrebbe cache-are lo stesso oggetto,
        //     tanto dovrebbe essere read-only
        return new ConfigPropertiesImpl(filename);
    }

    /**
    * Legge dal file specificato l'oggetto Properties relativo e ritorna il valore dell'elemento specificato
    */
    public static String getStringProperty(String filename, String property, String defaultValue) {
        ConfigProperties prop = getProperties(filename);
		return prop.getStringProperty(property, defaultValue);
    }

    /**
     * Legge dal file specificato l'oggetto Properties relativo e ritorna il valore dell'elemento specificato
     */
     public static String getStringProperty(String filename, String property) {
         return getStringProperty(filename, property, null);
     }


    /**
     * Legge dal file specificato l'oggetto Properties relativo e ritorna il valore dell'elemento specificato
     */
    public static boolean getBooleanProperty(String filename, String property, boolean defaultValue) {
	    ConfigProperties prop = getProperties(filename);
	    return prop.getBooleanProperty(property, defaultValue);
    }

    /**
     * Legge dal file specificato l'oggetto Properties relativo e ritorna il valore dell'elemento specificato
     */
    public static boolean getBooleanProperty(String filename, String property) {
        return getBooleanProperty(filename, property, false);
    }


    /**
     * Legge dal file specificato l'oggetto Properties relativo e ritorna il valore dell'elemento specificato
     */
    public static int getIntProperty(String filename, String property, int defaultValue) {
        ConfigProperties prop = getProperties(filename);
        return prop.getIntProperty(property, defaultValue);
    }

    /**
     * Legge dal file specificato l'oggetto Properties relativo e ritorna il valore dell'elemento specificato
     */
    public static int getIntProperty(String filename, String property) {
        return getIntProperty(filename, property, 0);
    }


    /**
     * Legge dal file specificato l'oggetto Properties relativo e ritorna il valore dell'elemento specificato
     */
    public static long getLongProperty(String filename, String property, long defaultValue) {
        ConfigProperties prop = getProperties(filename);
        return prop.getLongProperty(property, defaultValue);
    }

    /**
     * Legge dal file specificato l'oggetto Properties relativo e ritorna il valore dell'elemento specificato
     */
    public static long getLongProperty(String filename, String property) {
        return getLongProperty(filename, property, 0);
    }

	/**
	 * Legge dal file specificato l'oggetto Properties relativo e ritorna il valore dell'elemento specificato
	 */
    public static BigDecimal getBigDecimalProperty(String filename, String property, BigDecimal defaultValue) {
        ConfigProperties prop = getProperties(filename);
        return prop.getBigDecimalProperty(property, defaultValue);
    }

	/**
	 * Legge dal file specificato l'oggetto Properties relativo e ritorna il valore dell'elemento specificato
	 */
    public static BigDecimal getBigDecimalProperty(String filename, String property) {
        return getBigDecimalProperty(filename, property, null);
    }

}

class ConfigPropertiesImpl extends AbstractConfigProperties implements ConfigProperties {

    private String sourceName;

    ConfigPropertiesImpl(String sourceName) {
        this.sourceName = sourceName;
    }

    protected String getConfigSourceName() {
        return sourceName;
    }


}