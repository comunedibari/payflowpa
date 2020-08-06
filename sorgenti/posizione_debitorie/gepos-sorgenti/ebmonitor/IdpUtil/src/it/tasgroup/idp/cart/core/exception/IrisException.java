package it.tasgroup.idp.cart.core.exception;

public class IrisException extends Exception {

	private static final long serialVersionUID = 1L;

	private String codiceErrore;
	private String message;
	private String faultString;
	private Throwable throwable;
	private String componenteResponsabile;
	
    public IrisException(String codiceErrore, String message) {
        super( message );
        this.message = message;
        this.codiceErrore = codiceErrore;
    }

    public IrisException(String codiceErrore, String message, Throwable cause) {
        super( message , cause );
        this.message = message;
        this.throwable = cause;
        this.codiceErrore = codiceErrore;
    }
    
    public IrisException(String codiceErrore, String message, String faultString,String componenteResponsabile, Throwable cause) {
        super( message , cause );
        this.message = message;
        this.faultString = faultString;
        this.throwable = cause;
        this.codiceErrore = codiceErrore;
        this.componenteResponsabile = componenteResponsabile;
    }
    
    public IrisException(String codiceErrore, String message, String faultString, String componenteResponsabile) {
        super( message );
        this.message = message;
        this.faultString = faultString;
        this.codiceErrore = codiceErrore;
        this.componenteResponsabile = componenteResponsabile;
    }
    
    public IrisException(String codiceErrore, String message, String faultString, Throwable cause) {
        super( message , cause );
        this.message = message;
        this.faultString = faultString;
        this.throwable = cause;
        this.codiceErrore = codiceErrore;
    }
    
    public IrisException(String codiceErrore, String message, String faultString) {
        super( message );
        this.message = message;
        this.faultString = faultString;
        this.codiceErrore = codiceErrore;
    }

    public IrisException(String codiceErrore, Throwable cause) {
        super( codiceErrore , cause );
        this.throwable = cause;
        this.codiceErrore = codiceErrore;
    }
    
    public IrisException(String codiceErrore) {
        super( codiceErrore );
        this.codiceErrore = codiceErrore;
    }

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Throwable getThrowable() {
		return this.throwable;
	}

	public void setThrowable(Throwable throwable) {
		this.throwable = throwable;
	}

	public String getCodiceErrore() {
		return codiceErrore;
	}

	public void setCodiceErrore(String codiceErrore) {
		this.codiceErrore = codiceErrore;
	}

	public String getFaultString() {
		if(faultString != null) return faultString;
		if(throwable != null && throwable.getMessage() != null && !throwable.getMessage().trim().equals("")) return throwable.getMessage();
		return message;
	}

	public void setFaultString(String faultString) {
		this.faultString = faultString;
	}

	public String getComponenteResponsabile() {
		return componenteResponsabile;
	}

	public void setComponenteResponsabile(String componenteResponsabile) {
		this.componenteResponsabile = componenteResponsabile;
	}
	

}
