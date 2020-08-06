/*
 * File: ChainedException.java
 *
 * Revision: $Revision: 1.1.1.1 $
 * Last revision by: $Author: CattaniA $
 * Last revised on: $Date: 2006/05/03 11:06:45 $
 * Created on: 9-lug-03 - 18.42.25
 */
package it.nch.fwk.fo.exceptions;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 *
 * <br>
 * La classe ChainedException implementa la logica di eccezione
 * propria della 1.4. I metodi utilizzati sono consistenti con
 * le chiamate della 1.4 (anche se nella presente versione e' stato
 * utilizzato a livello di Exception e non di Throwable).
 *
**/
public class ChainedException extends Exception{

	private static final long	serialVersionUID	= 33103022793548663L;

	Exception causeException=null;

	/**
	 * Constructor for ChainedException.
	 */
	public ChainedException() {
		super();
	}

	/**
	 * Constructor for ChainedException.
	 * @param s
	 */
	public ChainedException(String s) {
		super(s);
	}

	/**
	 * Costruttore con eccezione inclusa
	 *
	 * @param e eccezione "padre"
	 */
	public ChainedException(Exception e) {
		causeException=e;
	}

	/**
	 * Metodo di inizializzazione dell'eccezione "padre"
	 *
	 * @param e eccezione "padre"
	 * @return ChainedException l'istanza della eccezione "figlia"
	 */
	public ChainedException initCause(Exception e) {
		causeException=e;
		return this;
	}

	/**
	 * Metodo "get" della eccezione "padre".
	 * @return Exception
	 */
	public Throwable getCause() {
		return causeException;
	}

	/**
	 * @see java.lang.Throwable#printStackTrace()
	 */
	public void printStackTrace() {

		if (causeException != null) {
			causeException.printStackTrace();
		}
		super.printStackTrace();
	}

	/**
	 * @see java.lang.Throwable#printStackTrace(PrintStream)
	 */
	public void printStackTrace(PrintStream s) {
		if (causeException != null) {
			causeException.printStackTrace(s);
		}
		super.printStackTrace(s);
	}

	/**
	 * @see java.lang.Throwable#printStackTrace(PrintWriter)
	 */
	public void printStackTrace(PrintWriter s) {
		if (causeException != null) {
			causeException.printStackTrace(s);
		}
		super.printStackTrace(s);
	}

}
