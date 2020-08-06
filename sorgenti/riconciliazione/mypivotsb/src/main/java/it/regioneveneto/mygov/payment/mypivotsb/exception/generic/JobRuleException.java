package it.regioneveneto.mygov.payment.mypivotsb.exception.generic;

public class JobRuleException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public JobRuleException(String msg) {
		super(msg);
	}
	
	public JobRuleException(String msg, Exception e) {
		super(msg, e);
	}
}