package it.tasgroup.idp.billerservices.api;

public class ValidationException extends Exception {
	
	EnumReturnValues errorCode;
	String description;
	Long  progressivo;
	String idDebito;
	String idPagamento; 
	boolean warning=false;

	
	public  ValidationException() {
		super();
	}

	
	public  ValidationException(EnumReturnValues errorCode) {
		super();
		this.errorCode=errorCode;
	}
	public  ValidationException(EnumReturnValues errorCode, Exception e) {
		super(e);
		this.errorCode=errorCode;
	}

	public  ValidationException(EnumReturnValues errorCode, String description) {
		super(description);
		this.errorCode=errorCode;
		this.description=description;
	}
	
	public  ValidationException(EnumReturnValues errorCode, String description, Exception e) {
		super(description,e);
		this.errorCode=errorCode;
		this.description=description;
	}
	
	public  ValidationException(EnumReturnValues errorCode, String description, Long progressivo) {
		super(description);
		this.errorCode=errorCode;
		this.description=description;
		this.progressivo=progressivo;
	}

	public  ValidationException(EnumReturnValues errorCode, String description, Long progressivo, Exception e) {
		super(description,e);
		this.errorCode=errorCode;
		this.description=description;
		this.progressivo=progressivo;
	}

	public  ValidationException(EnumReturnValues errorCode, String description, Long progressivo, String idDebito, String idCondizione) {
		super(description);
		this.errorCode=errorCode;
		this.description=description;
		this.progressivo=progressivo;
		this.idDebito=idDebito;
		this.idPagamento=idCondizione;
	}

	public  ValidationException(EnumReturnValues errorCode, String description, Long progressivo, String idDebito, String idCondizione,Exception e) {
		super(description, e);
		this.errorCode=errorCode;
		this.description=description;
		this.progressivo=progressivo;
	}
	


	public EnumReturnValues getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(EnumReturnValues errorCode) {
		this.errorCode = errorCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getProgressivo() {
		return progressivo;
	}
	public void setProgressivo(Long progressivo) {
		this.progressivo = progressivo;
	}
	public String getIdDebito() {
		return idDebito;
	}
	public void setIdDebito(String idDebito) {
		this.idDebito = idDebito;
	}
	public String getIdPagamento() {
		return idPagamento;
	}
	public void setIdPagamento(String idPagamento) {
		this.idPagamento = idPagamento;
	}
	public boolean isWarning() {
		return warning;
	}
	public void setWarning(boolean warning) {
		this.warning = warning;
	}
	
	
	
}
