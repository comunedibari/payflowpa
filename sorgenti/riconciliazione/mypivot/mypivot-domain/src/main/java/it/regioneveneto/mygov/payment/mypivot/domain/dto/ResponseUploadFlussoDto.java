package it.regioneveneto.mygov.payment.mypivot.domain.dto;

public class ResponseUploadFlussoDto {
	
	private String code;
	
	private String message;

//	private String esito;
	private String nomeFile;
	private String dimensioneFile;
	private String codRequestToken;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
//	public String getEsito() {
//		return esito;
//	}
//
//	public void setEsito(String esito) {
//		this.esito = esito;
//	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public String getDimensioneFile() {
		return dimensioneFile;
	}

	public void setDimensioneFile(String dimensioneFile) {
		this.dimensioneFile = dimensioneFile;
	}

	public String getCodRequestToken() {
		return codRequestToken;
	}

	public void setCodRequestToken(String codRequestToken) {
		this.codRequestToken = codRequestToken;
	}
	
}
