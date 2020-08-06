/*
 * File: BaseConfigSources.java
 * Package: it.nch.fwk.fo.base.config
 * 
 * Revision: $Revision: 1.1.1.1 $ 
 * Last revision by: $Author: CattaniA $
 * Last revised on: $Date: 2006/05/03 11:06:45 $ 
 * Created on: 28-ott-2005 - 17.38.19
 * Created by: liacono (Etnoteam)
 */
package it.nch.fwk.fo.base.constants;

/**
 * @author liacono
 *
 * Classe che espone i tipi di configurazione di base necessari per
 * il framework
 */
public interface BaseConfigSources {

    public static final String DBCONF     = "dbconf";
    
    /**
     * Implentazione dei servizi di caching dei dati
     */
    public static final String CACHEDATA  = "cachedata";

    public static final String USER       = "user";
    public static final String WEB        = "web";

    /**
     * Implentazione dei servizi di basso livello
     * (ErrorManager, DictionaryManager, etc)
     */
    public static final String IMPL       = "impl";

    /**
     * Configurazioni per i BusinessDelegate
     */
    public static final String NIB = "nib";
    public static final String BUSINESS_DELEGATE = "businessdelegate";
    
    /**
     * Configurazioni per i ping
     */
    public static final String PING = "ping";
    
}
