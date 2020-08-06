package it.regioneveneto.mygov.payment.mypivotsb.exception.generic.resourceConflict;

import it.regioneveneto.mygov.payment.mypivotsb.exception.generic.BaseException;

public class ResourceConflictException extends BaseException  {

	private static final long serialVersionUID = 1L;

	public ResourceConflictException() {
		super();
	}

	public ResourceConflictException(String message) {
		super(message);
	}
}