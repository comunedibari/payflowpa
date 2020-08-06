package it.regioneveneto.mygov.payment.mypivotsb.exception.generic;

public class BaseException extends Exception  {

	private static final long serialVersionUID = -5174422862767207510L;

	public BaseException() {
		super();
	}

	public BaseException(final String message) {
		super(message);
	}
}