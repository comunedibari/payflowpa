/*
 * Creato il 18-nov-2005
 *
 * TODO Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
package it.nch.fwk.fo.exception;

/**
 * @author EE10057
 *
 * TODO Per modificare il modello associato al commento di questo tipo generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
public interface NestedException {

	/**
	 * @param target tipo di eccezione da cercare
	 * @return <b>this</b> se il parametro target casta alla classe corrente, <b>this.getCause()</b>
	 *  se il parametro target casta alla classe rappresentata dall'eccezione annidata, <b>null</b>
	 *  altrimenti
	 */
	public Throwable contains(Class target);

	public Throwable getCause();
}
