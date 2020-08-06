/*
 * File: ErrorManagerFactory.java
 * Package: com.etnoteam.service.datacache
 * 
 * Revision: $Revision: 1.1.1.1 $ 
 * Last revision by: $Author: CattaniA $
 * Last revised on: $Date: 2006/05/03 11:06:45 $ 
 * Created on: 11-ott-2005 - 14.32.27
 * Created by: liacono (Etnoteam)
 */
package it.nch.fwk.fo.exceptions.handler;

import it.nch.fwk.fo.base.config.Configurations;
import it.nch.fwk.fo.base.constants.BaseConfigSources;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;

/**
 * @author liacono
 *
 * <br>
 * La classe ErrorManagerFactory &egrave; ...
 * 
 **/
public class ErrorManagerFactory {

    private static Logger logger = Logger.getLogger(ErrorManagerFactory.class);
    
    /**
     * Non ha senso instanziarlo
     */
    private ErrorManagerFactory() {
        //NOP
    }
    
    public static ErrorManager getInstance() {
        ErrorManager errMgr = null;
        
        try {
            String className=Configurations.getStringProperty(
                    BaseConfigSources.IMPL,
            	    "ErrorManagerImplementation");
            Class clazz = Class.forName(className);
            Method method = clazz.getDeclaredMethod("getInstance", null);
            errMgr = (ErrorManager)method.invoke(clazz, null);
        } catch (Exception e) {
            logger.error("Errore durante il recupero dell'istanza di ErrorManager", e);
        }
        return errMgr;
    }
    
}
