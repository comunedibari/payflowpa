package it.regioneveneto.mygov.payment.mypivotsb.exception.generic.accessForbidden;

import it.regioneveneto.mygov.payment.mypivotsb.exception.generic.BaseException;

public class AccessForbiddenException extends BaseException  {

	private static final long serialVersionUID = 1L;

	public AccessForbiddenException() {
		super();
	}

	public AccessForbiddenException(String message) {
		super(message);
	}
}