/*
 * Creato il 11-mar-2009
 */
package it.tasgroup.dse.exception;

/**
 * @author agostino
 */
public class ReaderException extends Exception {

	private static final long serialVersionUID = 6381433336529399934L;
	/**
	 * 
	 */
	public ReaderException() {
		super();
	}
	/**
	 * @param message
	 */
	public ReaderException(String message) {
		super(message);
	}
	/**
	 * @param message
	 * @param cause
	 */
	public ReaderException(String message, Throwable cause) {
		super(message, cause);
	}
	/**
	 * @param cause
	 */
	public ReaderException(Throwable cause) {
		super(cause);
	}
}
