/*
 * File: UserDataFactory.java
 * Package: com.etnoteam.service.handler.profile
 * 
 * Revision: $Revision: 1.1.1.1 $ 
 * Last revision by: $Author: CattaniA $
 * Last revised on: $Date: 2006/05/03 11:06:45 $ 
 * Created on: 12-ott-2005 - 12.09.49
 * Created by: liacono (Etnoteam)
 */
package it.nch.fwk.fo.base.handler.profile;

import it.nch.fwk.fo.base.config.Configurations;
import it.nch.fwk.fo.base.constants.BaseConfigSources;

import org.apache.log4j.Logger;

/**
 * @author liacono
 *
 * <br>
 * La classe UserDataFactory si occupa di recuperare da un file di configurazione
 * qual e' la classe che contiene l'implementazione richiesta dell'interfaccia
 * UserData
 * 
 * @see UserData
 * 
 **/
public class UserDataFactory {
    
    private static Logger logger = Logger.getLogger(UserDataFactory.class);

    private UserDataFactory() {
        //NOP
    }
    
    /**
     * Metodo Factory dello UserData che viene utilizzato per trasportare i
     * dati di profilo e applicativi legati all'utenet
     * 
     * @return l'istanza della classe che implementa UserData
     */
    public static UserData getInstance() {
        UserData ud = null;
        try {
            ud = (UserData) Class.forName(
                    Configurations.getStringProperty(
                            BaseConfigSources.IMPL,
                            "UserDataImplementation")).newInstance();
        } catch (Exception e) {
            logger.error("Eccezione durante la creazione dell istanza di UserData", e);
        }
        return ud;
    }
}
