package it.nch.fwk.fo.exceptions;

public class BackEndException extends Exception {

	private static final long	serialVersionUID	= - 8969960715496935730L;

	private String code;

	private Exception cause;

	public BackEndException(String code, Exception cause) {
		this.code = code;
		this.cause = cause;
	}

	/**
	 * Returns the cause.
	 * @return Exception
	 */
	public Throwable getCause() {
		return cause;
	}

	/**
	 * Returns the code.
	 * @return String
	 */
	public String getErrorCode() {
		return code;
	}

}