package it.tasgroup.idp.plugin.api;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public abstract class DettaglioStrutturatoModel implements Serializable {
	
	Integer id;
	String idEnte;
	BigDecimal importo;
	String cfSoggettoPassivo;
	Integer annoRiferimento;
	String opInserimento;
	Timestamp tsInserimento;
	String opAggiornamento;
	Timestamp tsAggiornamento;
	Integer version;
	
	
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}



	public String getOpInserimento() {
		return opInserimento;
	}


	public Timestamp getTsInserimento() {
		return tsInserimento;
	}


	public String getOpAggiornamento() {
		return opAggiornamento;
	}


	public Timestamp getTsAggiornamento() {
		return tsAggiornamento;
	}


	public Integer getVersion() {
		return version;
	}


	public void setOpInserimento(String opInserimento) {
		this.opInserimento = opInserimento;
	}


	public void setTsInserimento(Timestamp tsInserimento) {
		this.tsInserimento = tsInserimento;
	}


	public void setOpAggiornamento(String opAggiornamento) {
		this.opAggiornamento = opAggiornamento;
	}


	public void setTsAggiornamento(Timestamp tsAggiornamento) {
		this.tsAggiornamento = tsAggiornamento;
	}


	public void setVersion(Integer version) {
		this.version = version;
	}


	public String getIdEnte() {
		return idEnte;
	}


	public BigDecimal getImporto() {
		return importo;
	}


	public String getCfSoggettoPassivo() {
		return cfSoggettoPassivo;
	}


	public Integer getAnnoRiferimento() {
		return annoRiferimento;
	}


	public void setIdEnte(String idEnte) {
		this.idEnte = idEnte;
	}


	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}


	public void setCfSoggettoPassivo(String cfSoggettoPassivo) {
		this.cfSoggettoPassivo = cfSoggettoPassivo;
	}


	public void setAnnoRiferimento(Integer annoRiferimento) {
		this.annoRiferimento = annoRiferimento;
	}


	public abstract String getQueryName();
	
	
}
