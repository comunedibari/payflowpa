/*
 * Creato il 21-nov-2005
 *
 * TODO Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
package it.nch.fwk.fo.core.exception;

import it.nch.fwk.fo.exception.NestedCheckedException;


/**
 * @author EE10057
 *
 * Superclasse di tutte le eccezioni <b>checked</b> provenienti dal pacchetto <b>core</b>
 */
public class CoreCheckedException extends NestedCheckedException {

	/**
	 * @see NestedCheckedException
	 */
	public CoreCheckedException(String msg) {
		super(msg);
		
	}

	/**
	 * @see NestedCheckedException
	 */
	public CoreCheckedException(String msg, Throwable nestedException) {
		super(msg, nestedException);
		
	}

}
