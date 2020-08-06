package it.nch.pagamenti.nodopagamentispc;

import it.nch.is.fo.stati.pagamenti.StatiPagamentiIris;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DatiSingoloPagamentoVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int progressivo;
	private String identificativoUnivocoRiscossione;
	private String esitoSingoloPagamento;
	private BigDecimal singoloImportoPagato;
	private Date dataEsitoSingoloPagamento;
	private BigDecimal commissioniApplicatePSP;
	private String     tipoAllegatoRicevuta;
	private byte[]     allegatoRicevuta;
	private String     datiricevutaMBD;
	
	
	public String getIdentificativoUnivocoRiscossione() {
		return identificativoUnivocoRiscossione;
	}
	public void setIdentificativoUnivocoRiscossione(String identificativoUnivocoRiscossione) {
		this.identificativoUnivocoRiscossione = identificativoUnivocoRiscossione;
	}
	public String getEsitoSingoloPagamento() {
		return esitoSingoloPagamento;
	}
	public void setEsitoSingoloPagamento(String esitoSingoloPagamento) {
		this.esitoSingoloPagamento = esitoSingoloPagamento;
	}
	public BigDecimal getSingoloImportoPagato() {
		return singoloImportoPagato;
	}
	public void setSingoloImportoPagato(BigDecimal singoloImportoPagato) {
		this.singoloImportoPagato = singoloImportoPagato;
	}
	public Date getDataEsitoSingoloPagamento() {
		return dataEsitoSingoloPagamento;
	}
	public void setDataEsitoSingoloPagamento(Date dataEsitoSingoloPagamento) {
		this.dataEsitoSingoloPagamento = dataEsitoSingoloPagamento;
	}
	
	public StatiPagamentiIris getStatoPagamentoIris() {
		
		if (singoloImportoPagato != null && singoloImportoPagato.compareTo(BigDecimal.ZERO) != 0) {
			return StatiPagamentiIris.ESEGUITO;
		} else {
			return StatiPagamentiIris.NON_ESEGUITO;
		}
		
	}
	public int getProgressivo() {
		return progressivo;
	}
	public void setProgressivo(int progressivo) {
		this.progressivo = progressivo;
	}
	public BigDecimal getCommissioniApplicatePSP() {
		return commissioniApplicatePSP;
	}
	public void setCommissioniApplicatePSP(BigDecimal commissioniApplicatePSP) {
		this.commissioniApplicatePSP = commissioniApplicatePSP;
	}
	public String getTipoAllegatoRicevuta() {
		return tipoAllegatoRicevuta;
	}
	public void setTipoAllegatoRicevuta(String tipoAllegatoRicevuta) {
		this.tipoAllegatoRicevuta = tipoAllegatoRicevuta;
	}
	public byte[] getAllegatoRicevuta() {
		return allegatoRicevuta;
	}
	public void setAllegatoRicevuta(byte[] allegatoRicevuta) {
		this.allegatoRicevuta = allegatoRicevuta;
	}
	public String getDatiricevutaMBD() {
		return datiricevutaMBD;
	}
	public void setDatiricevutaMBD(String datiricevutaMBD) {
		this.datiricevutaMBD = datiricevutaMBD;
	}

}
