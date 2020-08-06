package it.nch.pagamenti.nodopagamentispc;

public class WrongRptException extends Exception {

	public WrongRptException() {}
	
	private String reason;
	
	public String getReason() {
		return reason;
	}
	
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	
	public WrongRptException(String reason) {
		this.reason = reason;
	}
	
}
