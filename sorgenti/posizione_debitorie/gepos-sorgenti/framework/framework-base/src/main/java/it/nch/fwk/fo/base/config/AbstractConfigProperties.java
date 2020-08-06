/*
 * File: AbstractConfigProperties.java
 * Package: it.nch.fwk.fo.base.config
 * 
 * Revision: $Revision: 1.1.1.1 $ 
 * Last revision by: $Author: CattaniA $
 * Last revised on: $Date: 2006/05/03 11:06:45 $ 
 * Created on: 27-ott-2005 - 17.59.59
 * Created by: liacono (Etnoteam)
 */
package it.nch.fwk.fo.base.config;

import it.nch.fwk.fo.base.constants.MathConstants;

import java.math.BigDecimal;
import java.util.Iterator;

import org.apache.log4j.Logger;

/**
 * @author liacono
 *
 * Classe astratta per l'esposizione di propriet&agrave; di configurazione
 */
public abstract class AbstractConfigProperties implements ConfigProperties {

    private static Logger logger = Logger.getLogger(AbstractConfigProperties.class);
    
    protected abstract String getConfigSourceName();
    
    protected ConfigSource findConfigSource() {
        return ConfigSourceFactory.getConfigSource(getConfigSourceName());
    }
    
    /**
     * Restituisce una propriet&agrave; come stringa. 
     * Nel caso non esista restituisce il valore di default.
     * 
     * @param name - il nome della propriet&agrave;
     * @param defaultValue - il valore di default
     * @see it.nch.fwk.fo.base.config.ConfigProperties
     */
    public String getStringProperty(String name, String defaultValue) {
        String val = null;
        ConfigSource source;
        
        source = findConfigSource();
        if (source != null) {
	        val = findConfigSource().readProperty(name);
	        if (val == null && defaultValue != null) {
	        	if (logger.isDebugEnabled())
	        		logger.debug("non esiste la proprieta' " + name + " in "
                        + source.getDescription()
                        + ". Viene allora usato il default:" + defaultValue);
	            val = defaultValue;
	        }
        } else {
            logger.warn("Non riesco a trovare la sorgente per la configurazione di nome " + 
                    getConfigSourceName());
        }
        return val;
    }

    /**
     * Restituisce una propriet&agrave; come stringa. 
     * Nel caso non esista restituisce <code>null</code>.
     * 
     * @param name - il nome della propriet&agrave;
     * @see it.nch.fwk.fo.base.config.ConfigProperties
     */
    public String getStringProperty(String name) {
        return getStringProperty(name, null);
    }


    /**
     * Restituisce una propriet&agrave; come boolean. 
     * Nel caso non esista restituisce il valore di default.
     * 
     * @param name - il nome della propriet&agrave;
     * @param defaultValue - il valore di default
     * @see it.nch.fwk.fo.base.config.ConfigProperties
     */
    public boolean getBooleanProperty(String name, boolean defaultValue) {
        boolean val = defaultValue;
        String str = getStringProperty(name);
        if (str != null) {
            val = Boolean.valueOf(str).booleanValue();
        }
        return val;
    }

    /**
     * Restituisce una propriet&agrave; come boolean. 
     * Nel caso non esista restituisce false.
     * 
     * @param name - il nome della propriet&agrave;
     * @see it.nch.fwk.fo.base.config.ConfigProperties
     */
    public boolean getBooleanProperty(String name) {
        return getBooleanProperty(name, false);
    }
    
    
    /**
     * Restituisce una propriet&agrave; come int. 
     * Nel caso non esista restituisce il valore di default.
     * 
     * @param name - il nome della propriet&agrave;
     * @param defaultValue - il valore di default
     * @see it.nch.fwk.fo.base.config.ConfigProperties
     */
    public int getIntProperty(String name, int defaultValue) {
        int val = defaultValue;
        String str = getStringProperty(name);
        if (str != null) {
            val = Integer.parseInt(str);
        }
        return val;
    }

    /**
     * Restituisce una propriet&agrave; come int. 
     * Nel caso non esista restituisce il valore 0.
     * 
     * @param name - il nome della propriet&agrave;
     * @see it.nch.fwk.fo.base.config.ConfigProperties
     */
    public int getIntProperty(String name) {
        return getIntProperty(name, 0);
    }


    /**
     * Restituisce una propriet&agrave; come long. 
     * Nel caso non esista restituisce il valore di default.
     * 
     * @param name - il nome della propriet&agrave;
     * @param defaultValue - il valore di default
     * @see it.nch.fwk.fo.base.config.ConfigProperties
     */
    public long getLongProperty(String name, long defaultValue) {
        long val = defaultValue;
        String str = getStringProperty(name);
        if (str != null) {
            val = Long.parseLong(str);
        }
        return val;
    }

    /**
     * Restituisce una propriet&agrave; come long. 
     * Nel caso non esista restituisce il valore 0.
     * 
     * @param name - il nome della propriet&agrave;
     * @see it.nch.fwk.fo.base.config.ConfigProperties
     */
    public long getLongProperty(String name) {
        return getLongProperty(name, 0l);
    }

    /**
     * Restituisce una propriet&agrave; come BigDecimal. 
     * Nel caso non esista restituisce il valore di default.
     * 
     * @param name - il nome della propriet&agrave;
     * @param defaultValue - il valore di default
     * @see it.nch.fwk.fo.base.config.ConfigProperties
     */
    public BigDecimal getBigDecimalProperty(String name, BigDecimal defaultValue) {
        BigDecimal val = defaultValue;
        String str = getStringProperty(name);
        if (str != null) {
            val = new BigDecimal(str);
        }
        return val;
    }

    /**
     * Restituisce una propriet&agrave; come BigDecimal. 
     * Nel caso non esista restituisce un BigDecimal di valore 0.
     * 
     * @param name - il nome della propriet&agrave;
     * @see it.nch.fwk.fo.base.config.ConfigProperties
     */
    public BigDecimal getBigDecimalProperty(String name) {
        return getBigDecimalProperty(name, MathConstants.BIG_DEC_ZERO);
    }
    
    public Iterator propertyNamesIterator() {
        return findConfigSource().propertyNamesIterator();
    }
    

}
