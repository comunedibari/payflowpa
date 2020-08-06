package it.regioneveneto.mygov.payment.mypivotsb.exception.generic.accessUnauthorized;

import it.regioneveneto.mygov.payment.mypivotsb.exception.generic.BaseException;

public class AccessUnauthorizedException extends BaseException  {

	private static final long serialVersionUID = 1L;

	public AccessUnauthorizedException() {
		super();
	}

	public AccessUnauthorizedException(String message) {
		super(message);
	}
}