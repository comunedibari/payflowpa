package it.regioneveneto.mygov.payment.mypivotsb.exception.generic;

public class PqException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public PqException(String msg) {
		super(msg);
	}
	
	public PqException(String msg, Exception e) {
		super(msg, e);
	}
}