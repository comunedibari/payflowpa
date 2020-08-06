/*
 * Creato il 18-nov-2005
 *
 * TODO Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
package it.nch.fwk.fo.das.exception;

import it.nch.fwk.fo.exception.NestedCheckedException;


/**
 * @author EE10057
 *
 * Superclasse di tutte le eccezioni <b>checked</b> provenienti dal pacchetto <b>das</b>
 */
public class DasCheckedException extends NestedCheckedException {


	/**
	 * @see NestedCheckedException
	 */
	public DasCheckedException(String msg, Throwable nestedException) {
		super(msg, nestedException);
		
	}
	/**
	 * @see NestedCheckedException
	 */
	public DasCheckedException(String msg) {
		super(msg);
		
	}

}
