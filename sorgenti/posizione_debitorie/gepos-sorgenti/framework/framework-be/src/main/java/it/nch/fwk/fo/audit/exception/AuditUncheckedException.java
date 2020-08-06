/*
 * Creato il 21-nov-2005
 *
 * TODO Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
package it.nch.fwk.fo.audit.exception;

import it.nch.fwk.fo.exception.NestedUncheckedException;


/**
 * @author EE10057
 *
 * Superclasse di tutte le eccezioni <b>unchecked</b> provenienti dal pacchetto <b>audit</b>
 */
public class AuditUncheckedException extends NestedUncheckedException {

	/**
	 * @see NestedUncheckedException
	 */
	public AuditUncheckedException(String msg) {
		super(msg);
		
	}

	/**
	 * @see NestedUncheckedException
	 */
	public AuditUncheckedException(String msg, Throwable nestedException) {
		super(msg, nestedException);
		
	}

}
