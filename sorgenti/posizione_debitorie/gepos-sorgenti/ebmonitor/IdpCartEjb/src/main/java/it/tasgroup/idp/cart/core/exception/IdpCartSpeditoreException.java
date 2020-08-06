package it.tasgroup.idp.cart.core.exception;

public class IdpCartSpeditoreException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String idEgov;
	
	public IdpCartSpeditoreException(String msg, String idEgov) {
		super(msg);
		this.idEgov = idEgov;
	}

	public IdpCartSpeditoreException(Throwable e, String idEgov) {
		super(e);
		this.idEgov = idEgov;
	}

	public IdpCartSpeditoreException(String msg, Throwable e, String idEgov) {
		super(msg, e);
		this.idEgov = idEgov;
	}

	public String getIdEgov() {
		return idEgov;
	}
	
}
