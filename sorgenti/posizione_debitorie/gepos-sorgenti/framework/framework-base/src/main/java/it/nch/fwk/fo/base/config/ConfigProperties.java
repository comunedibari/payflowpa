/*
 * File: ConfigProperties.java
 * Package: it.nch.fwk.fo.base.config
 * 
 * Revision: $Revision: 1.1.1.1 $ 
 * Last revision by: $Author: CattaniA $
 * Last revised on: $Date: 2006/05/03 11:06:45 $ 
 * Created on: 26-ott-2005 - 9.44.22
 * Created by: liacono (Etnoteam)
 */
package it.nch.fwk.fo.base.config;

import java.math.BigDecimal;
import java.util.Iterator;

/**
 * @author liacono
 *
 * Interfaccia per l'esposizione di propriet&agrave; di configurazione
 * 
 */
public interface ConfigProperties {

    /**
     * Restituisce una propriet&agrave; come stringa. 
     * Nel caso non esista restituisce il valore di default.
     * 
     * @param name - il nome della propriet&agrave;
     * @param defaultValue - il valore di default
     */
    public String getStringProperty(String name, String defaultValue);
    
    /**
     * Restituisce una propriet&agrave; come stringa. 
     * Nel caso non esista restituisce <code>null</code>.
     * 
     * @param name - il nome della propriet&agrave;
     */
    public String getStringProperty(String name);
    
    /**
     * Restituisce una propriet&agrave; come boolean. 
     * Nel caso non esista restituisce il valore di default.
     * 
     * @param name - il nome della propriet&agrave;
     * @param defaultValue - il valore di default
     */
    public boolean getBooleanProperty(String name);

    /**
     * Restituisce una propriet&agrave; come boolean. 
     * Nel caso non esista restituisce <code>false</code>.
     * 
     * @param name - il nome della propriet&agrave;
     */
    public boolean getBooleanProperty(String name, boolean defaultValue);
    
    /**
     * Restituisce una propriet&agrave; come int. 
     * Nel caso non esista restituisce 0.
     * 
     * @param name - il nome della propriet&agrave;
     */
    public int getIntProperty(String name);

    /**
     * Restituisce una propriet&agrave; come int. 
     * Nel caso non esista restituisce 0.
     * 
     * @param name - il nome della propriet&agrave;
     * @param defaultValue - il valore di default
     */
    public int getIntProperty(String name, int defaultValue);

    /**
     * Restituisce una propriet&agrave; come long. 
     * Nel caso non esista restituisce 0.
     * 
     * @param name - il nome della propriet&agrave;
     */
    public long getLongProperty(String name);

    /**
     * Restituisce una propriet&agrave; come long. 
     * Nel caso non esista restituisce 0.
     * 
     * @param name - il nome della propriet&agrave;
     * @param defaultValue - il valore di default
     */
    public long getLongProperty(String name, long defaultValue);
    
    
    /**
     * Restituisce una propriet&agrave; come BigDecimal. 
     * Nel caso non esista restituisce il valore di default.
     * 
     * @param name - il nome della propriet&agrave;
     * @param defaultValue - il valore di default
     */
    public BigDecimal getBigDecimalProperty(String name, BigDecimal defaultValue);
    
    /**
     * Restituisce una propriet&agrave; come stringa. 
     * Nel caso non esista restituisce un BigDecimal con valore 0.
     * 
     * @param name - il nome della propriet&agrave;
     */
    public BigDecimal getBigDecimalProperty(String name);
    
    /**
     * Restituisce un Iterator su i nomi delle propriet&agrave;
     * 
     * @return - Iterator su i nomi della propriet&agrave;
     */
    public Iterator propertyNamesIterator();
    
}
