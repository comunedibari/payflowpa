package it.nch.idp.posizionedebitoria;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/*
 *  Bean che contiene dati relativi all'incasso (in particolare pagopa)
 *  Usato dalla funzione di export 
 */
public class PagamentoDettaglioIncassoVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3519306237240338726L;
	// Campi informativi relativi all'incasso (usati nelle funzioni di export)
	private String idPsp;
	private String descrizionePSP;
	private String istitutoAttestante;
	private String idAttestante;
	private String tipoIdAttestante;
	private String iuv;
	private String iur;
	private String riferimentoContabile;
	private String trnRiversamento;
	private Date dataRegolamento;
	private String idFlussoRiversamento;
	private BigDecimal totaleMovimentoAccredito;
	private String numeroLista;
	
	public String getIdPsp() {
		return idPsp;
	}
	public void setIdPsp(String idPsp) {
		this.idPsp = idPsp;
	}
	public String getDescrizionePSP() {
		return descrizionePSP;
	}
	public void setDescrizionePSP(String descrizionePSP) {
		this.descrizionePSP = descrizionePSP;
	}
	public String getIstitutoAttestante() {
		return istitutoAttestante;
	}
	public void setIstitutoAttestante(String istitutoAttestante) {
		this.istitutoAttestante = istitutoAttestante;
	}
	public String getIdAttestante() {
		return idAttestante;
	}
	public void setIdAttestante(String idAttestante) {
		this.idAttestante = idAttestante;
	}
	public String getTipoIdAttestante() {
		return tipoIdAttestante;
	}
	public void setTipoIdAttestante(String tipoIdAttestante) {
		this.tipoIdAttestante = tipoIdAttestante;
	}
	public String getIuv() {
		return iuv;
	}
	public void setIuv(String iuv) {
		this.iuv = iuv;
	}
	public String getIur() {
		return iur;
	}
	public void setIur(String iur) {
		this.iur = iur;
	}
	public String getRiferimentoContabile() {
		return riferimentoContabile;
	}
	public void setRiferimentoContabile(String riferimentoContabile) {
		this.riferimentoContabile = riferimentoContabile;
	}
	public String getTrnRiversamento() {
		return trnRiversamento;
	}
	public void setTrnRiversamento(String trnRiversamento) {
		this.trnRiversamento = trnRiversamento;
	}
	public Date getDataRegolamento() {
		return dataRegolamento;
	}
	public void setDataRegolamento(Date dataRegolamento) {
		this.dataRegolamento = dataRegolamento;
	}
	public String getIdFlussoRiversamento() {
		return idFlussoRiversamento;
	}
	public void setIdFlussoRiversamento(String idFlussoRiversamento) {
		this.idFlussoRiversamento = idFlussoRiversamento;
	}

	public BigDecimal getTotaleMovimentoAccredito() {
		return totaleMovimentoAccredito;
	}
	public void setTotaleMovimentoAccredito(BigDecimal totaleMovimentoAccredito) {
		this.totaleMovimentoAccredito = totaleMovimentoAccredito;
	}
	public String getNumeroLista() {
		return numeroLista;
	}
	public void setNumeroLista(String numeroLista) {
		this.numeroLista = numeroLista;
	}
	
	
	
}
