package it.regioneveneto.mygov.payment.mypivot.xmlparser;

public class NotAValidXMLException extends RuntimeException {

	private static final long serialVersionUID = -7413501596636772171L;

	public NotAValidXMLException() {
	}

	public NotAValidXMLException(String message) {
		super(message);
	}

	public NotAValidXMLException(Throwable cause) {
		super(cause);
	}

	public NotAValidXMLException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotAValidXMLException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
