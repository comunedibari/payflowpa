package it.tasgroup.idp.autorizzazionepagamento.exception;

public class AutorizzazionePagamentoException extends Exception {
	private static final long serialVersionUID = 1L;

	private String codiceErrore;
	private String message;
	private String faultString;
	private Throwable throwable;
	private String componenteResponsabile;
	
    public AutorizzazionePagamentoException(String codiceErrore, String message) {
        super( message );
        this.message = message;
        this.codiceErrore = codiceErrore;
    }

    public AutorizzazionePagamentoException(String codiceErrore, String message, Throwable cause) {
        super( message , cause );
        this.message = message;
        this.throwable = cause;
        this.codiceErrore = codiceErrore;
    }
    
    public AutorizzazionePagamentoException(String codiceErrore, String message, String faultString, Throwable cause) {
        super( message , cause );
        this.message = message;
        this.faultString = faultString;
        this.throwable = cause;
        this.codiceErrore = codiceErrore;
    }

	public String getCodiceErrore() {
		return codiceErrore;
	}

	public void setCodiceErrore(String codiceErrore) {
		this.codiceErrore = codiceErrore;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getFaultString() {
		return faultString;
	}

	public void setFaultString(String faultString) {
		this.faultString = faultString;
	}

	public Throwable getThrowable() {
		return throwable;
	}

	public void setThrowable(Throwable throwable) {
		this.throwable = throwable;
	}

	public String getComponenteResponsabile() {
		return componenteResponsabile;
	}

	public void setComponenteResponsabile(String componenteResponsabile) {
		this.componenteResponsabile = componenteResponsabile;
	}
    
    
}
