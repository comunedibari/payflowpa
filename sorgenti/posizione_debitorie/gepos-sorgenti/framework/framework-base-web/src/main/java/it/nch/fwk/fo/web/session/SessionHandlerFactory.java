/*
 * Created on 27-ott-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package it.nch.fwk.fo.web.session;


import it.nch.fwk.fo.base.config.Configurations;
import it.nch.fwk.fo.base.constants.BaseConfigSources;

import java.lang.reflect.Constructor;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 * @author ee07869
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SessionHandlerFactory {

	/* logger (non serializzato) */
	private static transient Logger logger = Logger.getLogger(SessionHandlerFactory.class);	
    
	/**
	* Recupera l'oggetto SessionHandler dalla sessione Http.
	* Se l'oggetto non e' ancora creato, crea una istanza dell'oggetto SessionHandler
	* e lo memorizza nella sessione http: in caso la sessione non sia nuova,
	* solleva una eccezione di stato non valido (es. nel caso di migrazione
	* di una sessione da un clone ad un'altro)
	* 
	* @return SessionHandler
	* @param session oggetto di sessione istanziato dal web container	
	* 
	**/
	public static BaseSessionHandler getSessionHandler(HttpSession session) {
				
		BaseSessionHandler sh =	(BaseSessionHandler) session.getAttribute(BaseSessionHandler.SESSION_ATTRIBUTE);
				
		if (sh == null) {
			synchronized (session) {
			    String className=null;
		        try {
		            className=Configurations.getStringProperty(
		                    BaseConfigSources.IMPL,
		            	    "SessionHandlerImplementation");

		            Class clazz = Class.forName(className);
		            Constructor constructor=clazz.getConstructor(new Class[] {HttpSession.class});

		            sh = (BaseSessionHandler)constructor.newInstance(new Object[] {session});
		        } catch (Exception e) {
		            logger.error("Errore durante il recupero dell'istanza di SessionHandler ("+className+")", e);            
		        }
			}
		}
		return sh;
	}
    
}
