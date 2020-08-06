package it.nch.fwk.fo.exceptions;

public class GenericException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6343709441211156600L;

	private Exception cause;

	public GenericException(Exception cause) {
		this.cause = cause;
	}

	public Throwable getCause() {
		Throwable throwable = new Throwable(cause);
		return throwable;
	}

	/**
	 * @param cause
	 *            The cause to set.
	 */
	public void setCause(Exception cause) {
		this.cause = cause;
	}

	/**
	 * @param args
	 */
}