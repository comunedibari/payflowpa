/*
 * Creato il 21-nov-2005
 *
 * TODO Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
package it.nch.fwk.fo.core.exception;

import it.nch.fwk.fo.exception.NestedUncheckedException;


/**
 * @author EE10057
 *
 * Superclasse di tutte le eccezioni <b>unchecked</b> provenienti dal pacchetto <b>core</b>
 */
public class CoreUncheckedException extends NestedUncheckedException {

	/**
	 * @see NestedUncheckedException
	 */
	public CoreUncheckedException(String msg) {
		super(msg);
		
	}

	/**
	 * @see NestedUncheckedException
	 */
	public CoreUncheckedException(String msg, Throwable nestedException) {
		super(msg, nestedException);
		
	}

}
