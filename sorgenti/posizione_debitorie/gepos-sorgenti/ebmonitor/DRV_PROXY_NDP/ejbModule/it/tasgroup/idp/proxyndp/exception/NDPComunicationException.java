package it.tasgroup.idp.proxyndp.exception;

import gov.telematici.pagamenti.ws.FaultBean;

public class NDPComunicationException extends Exception {

	private static final long serialVersionUID = 1L;

	private FaultBean fault;

	public NDPComunicationException(FaultBean fault) {
		super(fault.getFaultCode() + ": " + fault.getFaultString());
		this.fault = fault;
	}

	public String getFaultId() {
		return fault.getId();
	}

	public String getFaultCode() {
		return fault.getFaultCode();
	}

	public String getFaultString() {
		return fault.getFaultString();
	}

	public String getFaultDescription() {
		return fault.getDescription();
	}

}
