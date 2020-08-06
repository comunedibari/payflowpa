package it.nch.eb.xsqlcmd.dbtrpos.gen.model;

import java.sql.Timestamp;

public class TributoStrutturatoModel implements java.io.Serializable {
	
	Integer id;
	String tipoTributo;
	String idPendenza;
	String cfSoggettoVersante;
	String noteVersante;
	String opInserimento;
	Timestamp tsInserimento;
	String opAggiornamento;
	Timestamp tsAggiornamento;
	Integer version;
	//emailVersante non gestito da BE
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTipoTributo() {
		return tipoTributo;
	}
	public void setTipoTributo(String tipoTributo) {
		this.tipoTributo = tipoTributo;
	}
	public String getIdPendenza() {
		return idPendenza;
	}
	public void setIdPendenza(String idPendenza) {
		this.idPendenza = idPendenza;
	}
	public String getCfSoggettoVersante() {
		return cfSoggettoVersante;
	}
	public void setCfSoggettoVersante(String cfSoggettoVersante) {
		this.cfSoggettoVersante = cfSoggettoVersante;
	}
	public String getNoteVersante() {
		return noteVersante;
	}
	public void setNoteVersante(String noteVersante) {
		this.noteVersante = noteVersante;
	}
	public String getOpInserimento() {
		return opInserimento;
	}
	public void setOpInserimento(String opInserimento) {
		this.opInserimento = opInserimento;
	}
	public Timestamp getTsInserimento() {
		return tsInserimento;
	}
	public void setTsInserimento(Timestamp tsInserimento) {
		this.tsInserimento = tsInserimento;
	}
	public String getOpAggiornamento() {
		return opAggiornamento;
	}
	public void setOpAggiornamento(String opAggiornamento) {
		this.opAggiornamento = opAggiornamento;
	}
	public Timestamp getTsAggiornamento() {
		return tsAggiornamento;
	}
	public void setTsAggiornamento(Timestamp tsAggiornamento) {
		this.tsAggiornamento = tsAggiornamento;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}

	
}
