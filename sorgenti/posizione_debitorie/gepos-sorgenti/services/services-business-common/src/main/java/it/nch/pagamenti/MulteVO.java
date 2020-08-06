package it.nch.pagamenti;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class MulteVO implements Serializable {

	private String idPagamento;
	private String targa;
	private String numeroVerbale;
	private Date dataVerbale;
	private String annoVerbale;
	private String serie;
	private String note;
	private String opInserimento;
	private Date tsInserimento;
	private String opAggiornamento;
	private String tipoVerbale;
	private BigDecimal importo;

	public BigDecimal getImporto() {
		return importo;
	}

	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}

	public String getTipoVerbale() {
		return tipoVerbale;
	}

	public void setTipoVerbale(String tipoVerbale) {
		this.tipoVerbale = tipoVerbale;
	}

	public String getIdPagamento() {
		return idPagamento;
	}

	public void setIdPagamento(String idPagamento) {
		this.idPagamento = idPagamento;
	}

	public String getTarga() {
		return targa;
	}

	public void setTarga(String targa) {
		this.targa = targa;
	}

	public String getNumeroVerbale() {
		return numeroVerbale;
	}

	public void setNumeroVerbale(String numeroVerbale) {
		this.numeroVerbale = numeroVerbale;
	}

	public Date getDataVerbale() {
		return dataVerbale;
	}

	public void setDataVerbale(Date dataVerbale) {
		this.dataVerbale = dataVerbale;
	}

	public String getAnnoVerbale() {
		return annoVerbale;
	}

	public void setAnnoVerbale(String annoVerbale) {
		this.annoVerbale = annoVerbale;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getOpInserimento() {
		return opInserimento;
	}

	public void setOpInserimento(String opInserimento) {
		this.opInserimento = opInserimento;
	}

	public Date getTsInserimento() {
		return tsInserimento;
	}

	public void setTsInserimento(Date tsInserimento) {
		this.tsInserimento = tsInserimento;
	}

	public String getOpAggiornamento() {
		return opAggiornamento;
	}

	public void setOpAggiornamento(String opAggiornamento) {
		this.opAggiornamento = opAggiornamento;
	}

	public Date getTsAggiornamento() {
		return tsAggiornamento;
	}

	public void setTsAggiornamento(Date tsAggiornamento) {
		this.tsAggiornamento = tsAggiornamento;
	}

	private Date tsAggiornamento;



}
