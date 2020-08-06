/*
 * Created on 25-ott-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package it.nch.fwk.fo.locator;

import it.nch.fwk.fo.util.Tracer;

/**
 * Eccezione lanciata in caso non si riesca a recuperare la Home di un EJB.
 *
 * @author EE10056
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ServiceLocatorException extends Exception {

	/**
	 * Costruttore che consente di specificare il messaggio
	 * il messaggio di questa eccezione.
	 *
	 * @param msg il messaggio di questa eccezione.
	 */
	public ServiceLocatorException(String msg) {
		Tracer.info("ServiceLocatorExc","costruttore"," ",msg);
	}

	/**
	 * Costruttore che consente di specificare il messaggio e la causa
	 * il messaggio di questa eccezione.
	 *
	 * @param msg il messaggio di questa eccezione.
	 * @param e la causa di questa eccezione.
	 */
	public ServiceLocatorException(String msg,Exception e) {
		Tracer.info("ServiceLocatorExc","costruttore"," ",msg);
	}
}