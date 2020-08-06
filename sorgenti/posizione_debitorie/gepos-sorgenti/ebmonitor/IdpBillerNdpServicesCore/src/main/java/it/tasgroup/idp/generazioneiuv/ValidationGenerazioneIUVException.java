package it.tasgroup.idp.generazioneiuv;

import it.tasgroup.idp.util.EnumTipiErrori;

public class ValidationGenerazioneIUVException extends Exception {
	
	EnumTipiErrori errorCode;
	String description;
	
	
	public  ValidationGenerazioneIUVException() {
		super();
	}

	
	public  ValidationGenerazioneIUVException(EnumTipiErrori errorCode) {
		super();
		this.errorCode=errorCode;
	}
	public  ValidationGenerazioneIUVException(EnumTipiErrori errorCode, Exception e) {
		super(e);
		this.errorCode=errorCode;
	}

	public  ValidationGenerazioneIUVException(EnumTipiErrori errorCode, String description) {
		super(description);
		this.errorCode=errorCode;
		this.description=description;
	}
	
	public  ValidationGenerazioneIUVException(EnumTipiErrori errorCode, String description, Exception e) {
		super(description,e);
		this.errorCode=errorCode;
		this.description=description;
	}
	
	


	public EnumTipiErrori getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(EnumTipiErrori errorCode) {
		this.errorCode = errorCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
}
