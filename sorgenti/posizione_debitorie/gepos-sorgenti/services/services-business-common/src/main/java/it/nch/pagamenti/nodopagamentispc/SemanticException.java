package it.nch.pagamenti.nodopagamentispc;

public class SemanticException extends Exception {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String reason;
	
	public String getReason() {
		return reason;
	}
	
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	
	public SemanticException(String reason) {
		this.reason = reason;
	}

}
