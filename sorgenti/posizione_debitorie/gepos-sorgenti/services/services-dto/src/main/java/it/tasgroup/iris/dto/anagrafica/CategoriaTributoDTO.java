package it.tasgroup.iris.dto.anagrafica;

import java.io.Serializable;


public class CategoriaTributoDTO implements Serializable {
	
	private String idTributo;
	private String deTrb;
	private String cdAde;
	private String tpEntrata;
	private String flPredeterm;
	private String flIniziativa;
	private String stato;
	private String cdPagamentoSpontaneo;
	private String soggEsclusi;
	private boolean isNew;
	
	private String opInserimento;
	private String opAggiornamento;
	 
	private String entiTributiSize; 
	private String tassonomia; 
	
	
	public String getTassonomia() { 
		return tassonomia; 
	} 

	public void setTassonomia(String tassonomia) { 
		this.tassonomia = tassonomia; 
	} 

	public String getEntiTributiSize() {
		return  entiTributiSize;
	}
	
	public void setEntiTributiSize(String entiTributis){
		 entiTributiSize = entiTributis;
	  	
	}
	
	public String getIdTributo() {
		return idTributo;
	}
	public void setIdTributo(String idTributo) {
		this.idTributo = idTributo;
	}
	public String getDeTrb() {
		return deTrb;
	}
	public void setDeTrb(String deTrb) {
		this.deTrb = deTrb;
	}
	public String getCdAde() {
		return cdAde;
	}
	public void setCdAde(String cdAde) {
		this.cdAde = cdAde;
	}
	public String getTpEntrata() {
		return tpEntrata;
	}
	public void setTpEntrata(String tpEntrata) {
		this.tpEntrata = tpEntrata;
	}
	public String getFlPredeterm() {
		return flPredeterm;
	}
	public void setFlPredeterm(String flPredeterm) {
		this.flPredeterm = flPredeterm;
	}
	public String getFlIniziativa() {
		return flIniziativa;
	}
	public void setFlIniziativa(String flIniziativa) {
		this.flIniziativa = flIniziativa;
	}
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	public String getSoggEsclusi() {
		return soggEsclusi;
	}
	public void setSoggEsclusi(String soggEsclusi) {
		this.soggEsclusi = soggEsclusi;
	}
	public boolean isNew() {
		return isNew;
	}
	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}
	
	public String getCdPagamentoSpontaneo() {
		return cdPagamentoSpontaneo;
	}

	public void setCdPagamentoSpontaneo(String cdPagamentoSpontaneo) {
		this.cdPagamentoSpontaneo = cdPagamentoSpontaneo;
	}

	public String getOpInserimento() {
		return opInserimento;
	}

	public void setOpInserimento(String opInserimento) {
		this.opInserimento = opInserimento;
	}

	public String getOpAggiornamento() {
		return opAggiornamento;
	}

	public void setOpAggiornamento(String opAggiornamento) {
		this.opAggiornamento = opAggiornamento;
	}
	
}
