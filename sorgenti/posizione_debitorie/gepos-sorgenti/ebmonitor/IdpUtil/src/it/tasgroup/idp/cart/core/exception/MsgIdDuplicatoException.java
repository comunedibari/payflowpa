package it.tasgroup.idp.cart.core.exception;

public class MsgIdDuplicatoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public MsgIdDuplicatoException(String msg) {
		super(msg);
	}

	public MsgIdDuplicatoException(Throwable e) {
		super(e);
	}

	public MsgIdDuplicatoException(String msg, Throwable e) {
		super(msg, e);
	}

}
