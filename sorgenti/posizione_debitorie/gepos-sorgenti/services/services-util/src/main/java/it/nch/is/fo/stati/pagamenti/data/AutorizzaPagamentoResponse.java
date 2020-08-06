package it.nch.is.fo.stati.pagamenti.data;

import java.math.BigDecimal;

public class AutorizzaPagamentoResponse {
	
	private BigDecimal importoPagamento;
	private String causaleVersamento;
	private FaultType fault = null;
	
	
	   
	private String tipoIdentificativoUnivocoDebitore;
	   
	private String codiceIdentificativoUnivocoDebitore;
	   
	private String anagraficaDebitore;
	   
	private String tipoDebito;
	  
	private String idDebito;
	    
	private String annoRiferimento;
	   
	private String idMittente;
	   
	private String descrizioneMittente;
	    
	private String idRiscossore;
	 
	private String riferimentoRiscossore;


	public BigDecimal getImportoPagamento() {
		return importoPagamento;
	}
	public String getCausaleVersamento() {
		return causaleVersamento;
	}
	public void setImportoPagamento(BigDecimal importoPagamento) {
		this.importoPagamento = importoPagamento;
	}
	public void setCausaleVersamento(String causaleVersamento) {
		this.causaleVersamento = causaleVersamento;
	}
	public FaultType getFault() {
		return fault;
	}
	public void setFault(FaultType fault) {
		this.fault = fault;
	}
	
	public String getTipoIdentificativoUnivocoDebitore() {
		return tipoIdentificativoUnivocoDebitore;
	}
	public void setTipoIdentificativoUnivocoDebitore(String tipoIdentificativoUnivocoDebitore) {
		this.tipoIdentificativoUnivocoDebitore = tipoIdentificativoUnivocoDebitore;
	}
	public String getCodiceIdentificativoUnivocoDebitore() {
		return codiceIdentificativoUnivocoDebitore;
	}
	public void setCodiceIdentificativoUnivocoDebitore(String codiceIdentificativoUnivocoDebitore) {
		this.codiceIdentificativoUnivocoDebitore = codiceIdentificativoUnivocoDebitore;
	}
	public String getAnagraficaDebitore() {
		return anagraficaDebitore;
	}
	public void setAnagraficaDebitore(String anagraficaDebitore) {
		this.anagraficaDebitore = anagraficaDebitore;
	}
	public String getTipoDebito() {
		return tipoDebito;
	}
	public void setTipoDebito(String tipoDebito) {
		this.tipoDebito = tipoDebito;
	}
	public String getIdDebito() {
		return idDebito;
	}
	public void setIdDebito(String idDebito) {
		this.idDebito = idDebito;
	}
	public String getAnnoRiferimento() {
		return annoRiferimento;
	}
	public void setAnnoRiferimento(String annoRiferimento) {
		this.annoRiferimento = annoRiferimento;
	}
	public String getIdMittente() {
		return idMittente;
	}
	public void setIdMittente(String idMittente) {
		this.idMittente = idMittente;
	}
	public String getDescrizioneMittente() {
		return descrizioneMittente;
	}
	public void setDescrizioneMittente(String descrizioneMittente) {
		this.descrizioneMittente = descrizioneMittente;
	}
	public String getIdRiscossore() {
		return idRiscossore;
	}
	public void setIdRiscossore(String idRiscossore) {
		this.idRiscossore = idRiscossore;
	}
	public String getRiferimentoRiscossore() {
		return riferimentoRiscossore;
	}
	public void setRiferimentoRiscossore(String riferimentoRiscossore) {
		this.riferimentoRiscossore = riferimentoRiscossore;
	}

	
}
