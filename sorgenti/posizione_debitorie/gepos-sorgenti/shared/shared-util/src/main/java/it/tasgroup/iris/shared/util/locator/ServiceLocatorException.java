/*
 * Created on 25-ott-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package it.tasgroup.iris.shared.util.locator;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Eccezione lanciata in caso non si riesca a recuperare la Home di un EJB.
 *
 * @author EE10056
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ServiceLocatorException extends Exception {
	
	private static Logger LOGGER = LogManager.getLogger(ServiceLocatorException.class);

	/**
	 * Costruttore che consente di specificare il messaggio
	 * il messaggio di questa eccezione.
	 *
	 * @param msg il messaggio di questa eccezione.
	 */
	public ServiceLocatorException(String msg) {
		LOGGER.info("ServiceLocatorException: "+msg);
	}

	/**
	 * Costruttore che consente di specificare il messaggio e la causa
	 * il messaggio di questa eccezione.
	 *
	 * @param msg il messaggio di questa eccezione.
	 * @param e la causa di questa eccezione.
	 */
	public ServiceLocatorException(String msg,Exception e) {
		LOGGER.info("ServiceLocatorException: ",e);
	}
}