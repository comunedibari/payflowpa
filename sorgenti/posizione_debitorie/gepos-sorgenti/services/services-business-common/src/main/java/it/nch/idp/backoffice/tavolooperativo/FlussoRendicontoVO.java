package it.nch.idp.backoffice.tavolooperativo;

import java.math.BigDecimal;
import java.util.Date;

public class FlussoRendicontoVO{

	private String idRendicontazione;
	private String idRiconciliazione;
	private Date dataValuta;
	private Date dataContabile;
	private String tipoRecord;
	private String causale;
	private BigDecimal importo;
	private String canalePagamento;
	private String riferimentoBanca;


	public Date getDataContabile() {
		return dataContabile;
	}
	public void setDataContabile(Date dataContabile) {
		this.dataContabile = dataContabile;
	}
	public String getTipoRecord() {
		return tipoRecord;
	}
	public void setTipoRecord(String tipoRecord) {
		this.tipoRecord = tipoRecord;
	}
	public String getCausale() {
		return causale;
	}
	public void setCausale(String causale) {
		this.causale = causale;
	}
	public String getIdRiconciliazione() {
		return idRiconciliazione;
	}
	public void setIdRiconciliazione(String idRiconciliazione) {
		this.idRiconciliazione = idRiconciliazione;
	}
	public Date getDataValuta() {
		return dataValuta;
	}
	public void setDataValuta(Date dataValuta) {
		this.dataValuta = dataValuta;
	}
	public String getIdRendicontazione() {
		return idRendicontazione;
	}
	public void setIdRendicontazione(String idRendicontazione) {
		this.idRendicontazione = idRendicontazione;
	}
	public BigDecimal getImporto() {
		return importo;
	}
	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}
	public String getCanalePagamento() {
		return canalePagamento;
	}
	public void setCanalePagamento(String canalePagamento) {
		this.canalePagamento = canalePagamento;
	}
	public String getRiferimentoBanca() {
		return riferimentoBanca;
	}
	public void setRiferimentoBanca(String riferimentoBanca) {
		this.riferimentoBanca = riferimentoBanca;
	}

}
