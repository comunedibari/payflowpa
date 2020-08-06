package it.tasgroup.idp.autorizzazionepagamento.servlet;

import java.math.BigDecimal;

public class AutorizzazionePagamentoVO {
	 private String identificativoDominio;
	 private String identificativoUnivocoVersamento;
	 private String codiceContestoPagamento;
	 private BigDecimal importoPagamento;
	 private String causaleVersamento;
	 private String codiceIdentificativoUnivocoDebitore;
	 private String anagraficaDebitore;
	 private String tipoDebito;
	 private String idDebito;
	 private String annoRiferimento;
	 private String idMittente;
	 private String descrizioneMittente;
	 private String idRiscossore;
	 private String riferimentoRiscossore;
	 private String tipoOperazione;
	 private String urlWebServices;
	 
	public String getUrlWebServices() {
		return urlWebServices;
	}
	public void setUrlWebServices(String urlWebServices) {
		this.urlWebServices = urlWebServices;
	}
	public String getIdentificativoDominio() {
		return identificativoDominio;
	}
	public void setIdentificativoDominio(String identificativoDominio) {
		this.identificativoDominio = identificativoDominio;
	}
	public String getIdentificativoUnivocoVersamento() {
		return identificativoUnivocoVersamento;
	}
	public void setIdentificativoUnivocoVersamento(String identificativoUnivocoVersamento) {
		this.identificativoUnivocoVersamento = identificativoUnivocoVersamento;
	}
	public String getCodiceContestoPagamento() {
		return codiceContestoPagamento;
	}
	public void setCodiceContestoPagamento(String codiceContestoPagamento) {
		this.codiceContestoPagamento = codiceContestoPagamento;
	}
	public BigDecimal getImportoPagamento() {
		return importoPagamento;
	}
	public void setImportoPagamento(BigDecimal importoPagamento) {
		this.importoPagamento = importoPagamento;
	}
	public String getCausaleVersamento() {
		return causaleVersamento;
	}
	public void setCausaleVersamento(String causaleVersamento) {
		this.causaleVersamento = causaleVersamento;
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
	public String getTipoOperazione() {
		return tipoOperazione;
	}
	public void setTipoOperazione(String tipoOperazione) {
		this.tipoOperazione = tipoOperazione;
	}
	 
	 

}
