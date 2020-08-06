package it.tasgroup.iris.dto.flussi;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class CasellarioDTO implements Serializable{

	private Long id;
	private String nomeSupporto;
	private Integer numeroRecord;
	private Integer dimensione;
	private String descErrore;
	private Integer tipoErrore;
	private String tipoFlusso;
	
	private String flagElaborazione;
	private Timestamp dataElaborazione;
	
	private String stato_virtuale;
	
	public String getFlagElaborazione() {
		return flagElaborazione;
	}
	public void setFlagElaborazione(String flagElaborazione) {
		this.flagElaborazione = flagElaborazione;
	}
	public Timestamp getDataElaborazione() {
		return dataElaborazione;
	}
	public void setDataElaborazione(Timestamp dataElaborazione) {
		this.dataElaborazione = dataElaborazione;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNomeSupporto() {
		return nomeSupporto;
	}
	public void setNomeSupporto(String nomeSupporto) {
		this.nomeSupporto = nomeSupporto;
	}
	public Integer getNumeroRecord() {
		return numeroRecord;
	}
	public void setNumeroRecord(Integer numeroRecord) {
		this.numeroRecord = numeroRecord;
	}
	public Integer getDimensione() {
		return dimensione;
	}
	public void setDimensione(Integer dimensione) {
		this.dimensione = dimensione;
	}
	public String getDescErrore() {
		return descErrore;
	}
	public void setDescErrore(String descErrore) {
		this.descErrore = descErrore;
	}
	public Integer getTipoErrore() {
		return tipoErrore;
	}
	public void setTipoErrore(Integer tipoErrore) {
		this.tipoErrore = tipoErrore;
	}
	public String getTipoFlusso() {
		return tipoFlusso;
	}
	public void setTipoFlusso(String tipoFlusso) {
		this.tipoFlusso = tipoFlusso;
	}
	public void setStato_virtuale(String stato_virtuale) {
		this.stato_virtuale = stato_virtuale;
	}
	public String getStato_virtuale() {
		return stato_virtuale;
	}

	
}
