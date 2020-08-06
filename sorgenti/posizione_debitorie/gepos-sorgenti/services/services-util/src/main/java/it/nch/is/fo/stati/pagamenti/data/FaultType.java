package it.nch.is.fo.stati.pagamenti.data;

public class FaultType {
	
	private String faultCode;
	private String faultString;
	private String faultDescription;
	
	public String getFaultCode() {
		return faultCode;
	}
	public String getFaultString() {
		return faultString;
	}
	public String getFaultDescription() {
		return faultDescription;
	}
	public void setFaultCode(String faultCode) {
		this.faultCode = faultCode;
	}
	public void setFaultString(String faultString) {
		this.faultString = faultString;
	}
	public void setFaultDescription(String faultDescription) {
		this.faultDescription = faultDescription;
	}
	
}
