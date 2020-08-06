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
public class NestedUncheckedException extends RuntimeException implements NestedException {

	private java.lang.String msg;
	private java.lang.Throwable nestedException;

	/**
	 * Questo costruttore è da utilizzare nel caso in cui venga generata l'eccezione 
	 * direttamente dall'applicazione.
	 * @param msg
	 */
	public NestedUncheckedException(java.lang.String msg) {
		super();
		this.msg = msg;
	}
	/**
	 * Questo costruttore è da utilizzare nel caso in cui venga generata l'eccezione
	 * all'interno della clausola <b>catch</b> come forward di un'eccezione ricevuta dai 
	 * livelli sottostanti all'applicazione
	 * @param msg
	 * @param nestedException Eccezione catturata
	 */
	public NestedUncheckedException(java.lang.String msg,
			java.lang.Throwable nestedException) {
		super();
		this.msg = msg;
		this.nestedException = nestedException;
	}
	
	/**
	 * @param target tipo di eccezione da cercare
	 * @return <b>this</b> se il parametro target casta alla classe corrente, <b>this.getCause()</b>
	 *  se il parametro target casta alla classe rappresentata dall'eccezione annidata, <b>null</b>
	 *  altrimenti
	 */
	public Throwable contains(Class target) {

		if (this.getClass().isAssignableFrom(target)) {
			return this;
		} else {
			if ((this.getCause().getClass()).isAssignableFrom(target)) {
				return this.getCause();
			} else
				return null;
		}

	}

	public Throwable getCause() {
		return this.nestedException;
	}
}
