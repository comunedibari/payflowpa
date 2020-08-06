package it.tasgroup.iris.sso.exception;

public class SSOException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public SSOException() {
		super();
	}

	public SSOException(String message, Exception cause) {
		super(message, cause);
	}

	public SSOException(String message) {
		super(message);
	}

	public SSOException(Exception cause) {
		super(cause);
	}

}
