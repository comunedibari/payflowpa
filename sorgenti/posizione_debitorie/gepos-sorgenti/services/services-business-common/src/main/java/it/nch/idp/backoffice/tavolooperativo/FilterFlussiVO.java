package it.nch.idp.backoffice.tavolooperativo;

import java.io.Serializable;
import java.util.Date;

public class FilterFlussiVO implements Serializable{


	private Date dataCreazioneDa;
	private Date dataCreazioneA;
	private String tipoFlusso;
	private boolean isFilterEmpty;
	private String statoFlusso;
	private String nomeSupporto;
	private String ordinamento;
	private String tipoOrdinamento;
	private String idEnte;

	public Date getDataCreazioneDa() {
		return dataCreazioneDa;
	}
	public void setDataCreazioneDa(Date dataCreazioneDa) {
		this.dataCreazioneDa = dataCreazioneDa;
	}
	public Date getDataCreazioneA() {
		return dataCreazioneA;
	}
	public void setDataCreazioneA(Date dataCreazioneA) {
		this.dataCreazioneA = dataCreazioneA;
	}
	public String getTipoFlusso() {
		return tipoFlusso;
	}
	public void setTipoFlusso(String tipoFlusso) {
		this.tipoFlusso = tipoFlusso;
	}
	public boolean isFilterEmpty() {
		return isFilterEmpty;
	}
	public void setFilterEmpty(boolean isFilterEmpty) {
		this.isFilterEmpty = isFilterEmpty;
	}
	public String getStatoFlusso() {
		return statoFlusso;
	}
	public void setStatoFlusso(String statoFlusso) {
		this.statoFlusso = statoFlusso;
	}
	public String getNomeSupporto() {
		return nomeSupporto;
	}
	public void setNomeSupporto(String nomeSupporto) {
		this.nomeSupporto = nomeSupporto;
	}
	public String getOrdinamento() {
		return ordinamento;
	}
	public void setOrdinamento(String ordinamento) {
		this.ordinamento = ordinamento;
	}
	public String getTipoOrdinamento() {
		return tipoOrdinamento;
	}
	public void setTipoOrdinamento(String tipoOrdinamento) {
		this.tipoOrdinamento = tipoOrdinamento;
	}
	public String getIdEnte() {
		return idEnte;
	}
	public void setIdEnte(String idEnte) {
		this.idEnte = idEnte;
	}
	
	

}