package it.tasgroup.idp.cart.core.exception;

public class MessaggioDuplicatoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public MessaggioDuplicatoException(String msg) {
		super(msg);
	}

	public MessaggioDuplicatoException(Throwable e) {
		super(e);
	}

	public MessaggioDuplicatoException(String msg, Throwable e) {
		super(msg, e);
	}

}
