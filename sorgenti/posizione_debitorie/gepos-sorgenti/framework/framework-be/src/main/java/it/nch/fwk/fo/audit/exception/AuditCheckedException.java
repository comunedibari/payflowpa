/*
 * Creato il 21-nov-2005
 *
 * TODO Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
package it.nch.fwk.fo.audit.exception;

import it.nch.fwk.fo.exception.NestedCheckedException;


/**
 * @author EE10057
 *
 * Superclasse di tutte le eccezioni <b>checked</b> provenienti dal pacchetto <b>audit</b>
 */
public class AuditCheckedException extends NestedCheckedException {

	/**
	 * @see NestedCheckedException
	 */
	public AuditCheckedException(String msg) {
		super(msg);
		
	}

	/**
	  * @see NestedCheckedException
	 */
	public AuditCheckedException(String msg, Throwable nestedException) {
		super(msg, nestedException);
		
	}

}
