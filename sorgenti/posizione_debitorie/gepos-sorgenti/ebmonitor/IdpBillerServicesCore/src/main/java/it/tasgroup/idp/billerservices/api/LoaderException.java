package it.tasgroup.idp.billerservices.api;

public class LoaderException extends Exception {
	private static final long serialVersionUID = -7663676817842805708L;
	
	private EnumReturnValues errorCode;
	private String description;
	private Long progressivo;
	
	public LoaderException(EnumReturnValues errorCode) {
		super();
		this.errorCode=errorCode;
	}
	
	public LoaderException(EnumReturnValues errorCode, Exception e) {
		super(e);
		this.errorCode=errorCode;
	}

	public LoaderException(EnumReturnValues errorCode, String description) {
		super(description);
		this.errorCode=errorCode;
		this.description=description;
	}

	public LoaderException(EnumReturnValues errorCode, String description, Long progressivo) {
		super(description);
		this.errorCode=errorCode;
		this.description=description;
		this.progressivo=progressivo;
	}
	
	public LoaderException(EnumReturnValues errorCode, String description, Exception e) {
		super(description,e);
		this.errorCode=errorCode;
		this.description=description;
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
}
