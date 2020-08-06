package it.regioneveneto.mygov.payment.mypivotsb.exception.generic.methodNotAllowed;

import it.regioneveneto.mygov.payment.mypivotsb.exception.generic.BaseException;

public class MethodNotAllowedException extends BaseException  {

	private static final long serialVersionUID = 1L;

	public MethodNotAllowedException() {
		super();
	}

	public MethodNotAllowedException(String message) {
		super(message);
	}
}