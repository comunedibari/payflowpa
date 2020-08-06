package it.tasgroup.iris.dto.confpagamenti;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.StringEscapeUtils;

public class CfgCommissionePagamentoDTO implements Serializable{

	private final static String EMPTY_STRING = "";
	
	private BigDecimal importoA;
	private BigDecimal importoDa;
	private String divisa;
	private BigDecimal valore;
	private CfgGatewayPagamentoDTO cfgGatewayPagamento;
	private CfgTipoCommissioneDTO cfgTipoCommissione;
	private BigDecimal importoCalcolato;
	private String descrizione;
	private String cfgTipoCommissioneVideo = EMPTY_STRING;
	private String descrizioneVideo = EMPTY_STRING;
	
	public BigDecimal getImportoA() {
		return importoA;
	}
	public void setImportoA(BigDecimal importoA) {
		this.importoA = importoA;
	}
	public BigDecimal getImportoDa() {
		return importoDa;
	}
	public void setImportoDa(BigDecimal importoDa) {
		this.importoDa = importoDa;
	}
	public CfgGatewayPagamentoDTO getCfgGatewayPagamento() {
		return cfgGatewayPagamento;
	}
	public void setCfgGatewayPagamento(CfgGatewayPagamentoDTO cfgGatewayPagamento) {
		this.cfgGatewayPagamento = cfgGatewayPagamento;
	}
	public CfgTipoCommissioneDTO getCfgTipoCommissione() {
		return cfgTipoCommissione;
	}
	public void setCfgTipoCommissione(CfgTipoCommissioneDTO cfgTipoCommissione) {
		this.cfgTipoCommissione = cfgTipoCommissione;
	}
	public String getDivisa() {
		return divisa;
	}
	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}
	public BigDecimal getValore() {
		return valore;
	}
	public void setValore(BigDecimal valore) {
		this.valore = valore;
	}
	public BigDecimal getImportoCalcolato() {
		return importoCalcolato;
	}
	public void setImportoCalcolato(BigDecimal importoCalcolato) {
		this.importoCalcolato = importoCalcolato;
	}
	
	public String getCfgTipoCommissioneVideo() {
		if(getCfgTipoCommissione() != null)
			cfgTipoCommissioneVideo =  getCfgTipoCommissione().getDescrizione();
					
		return cfgTipoCommissioneVideo;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getDescrizioneVideo() {
		
		if(getDescrizione() != null)
			descrizioneVideo =  StringEscapeUtils.escapeHtml(descrizione);
		return descrizioneVideo;
	}
	
	
}
