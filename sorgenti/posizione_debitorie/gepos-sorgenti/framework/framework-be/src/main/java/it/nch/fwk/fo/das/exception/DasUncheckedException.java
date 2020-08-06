/*
 * Creato il 18-nov-2005
 *
 * TODO Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
package it.nch.fwk.fo.das.exception;

import it.nch.fwk.fo.exception.NestedUncheckedException;


/**
 * @author EE10057
 *
 * Superclasse di tutte le eccezioni <b>unchecked</b> provenienti dal pacchetto <b>core</b>
 */
public class DasUncheckedException extends NestedUncheckedException {

	/**
	 * @see NestedUncheckedException
	 */
	public DasUncheckedException(String msg, Throwable nestedException) {
		super(msg, nestedException);
		
	}
	/**
	 * @see NestedUncheckedException
	 */
	public DasUncheckedException(String msg) {
		super(msg);
		
	}
	
}
