package it.nch.is.fo.stati.pagamenti;

public enum StatiMyBank {
	AUTHORISED("AUTHORISED"),
	AUTHORISINGPARTYABORTED("AUTHORISINGPARTYABORTED"),
	TIMEOUT("TIMEOUT"),
	ERROR("ERROR"),
	PENDING("PENDING");
	
	private final String stato;
	
	StatiMyBank(String stato) {
	    this.stato = stato;
	}
	
	public String getStato() {
		return stato;
	}
}
