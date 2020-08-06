package it.tasgroup.iris.dto.anonymous.payment;

import java.io.Serializable;

public class AnonymousTributoEnteDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private int index;
	private String categoria;
	private String idEnte;
	private String cdTrbEnte;
	private String deTrb;
	private String denomEnte;
	private String flPredeterm;
	private String flIniziativa;
	private String infoUrl;
	private String updServiceUrl;
	private String istruzioniPagamento;
	
	private String cdPlugin;
	private byte[] datiCfgPlugin;
	
	
	public int getIndex() {
		return index;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public String getDenomEnte() {
		return denomEnte;
	}

	public void setDenomEnte(String denomEnte) {
		this.denomEnte = denomEnte;
	}

	public String getDeTrb() {
		String res = deTrb;
		if (res.indexOf("&&") != -1) {
			res = res.substring(0, res.indexOf("&&")).trim();
		}
		return res;
	}
	
	public String getSubDeTrb() {
		String res = "";
		if (deTrb.indexOf("&&") != -1) {
			res = deTrb.substring(deTrb.indexOf("&&") + 2).trim();
		}
		return res;
	}
	
	public void setDeTrb(String deTrb) {
		this.deTrb = deTrb;
	}
	
	public String getFlPredeterm() {
		return flPredeterm;
	}
	
	public void setFlPredeterm(String flPredeterm) {
		this.flPredeterm = flPredeterm;
		
	}
	
	public String getInfoUrl() {
		return infoUrl;
	}
	
	public void setInfoUrl(String infoUrl) {
		this.infoUrl = infoUrl;
	}
	
	public boolean isPredeterminato() {
		return "Y".equals(this.flPredeterm);
	}
	
	public boolean isDefaultSearch() {
		
		return isPredeterminato() && isSpontaneo();
	}
	
	public boolean isDefaultInsert() {
		return !isPredeterminato() && isSpontaneo();
	}
	
	public String getUpdServiceUrl() {
		return updServiceUrl;
	}
	
	public void setUpdServiceUrl(String updServiceUrl) {
		this.updServiceUrl = updServiceUrl;
	}
	
	public String getIstruzioniPagamento() {
		return istruzioniPagamento;
	}
	
	public void setIstruzioniPagamento(String istruzioniPagamento) {
		this.istruzioniPagamento = istruzioniPagamento;
	}
	
	public boolean isSpontaneo() {
		
		return "Y".equals(this.flIniziativa);
		
	}

	public boolean isShowInCarousel() {
		 return true;
	}

	public String getIdEnte() {
		return idEnte;
	}

	public void setIdEnte(String idEnte) {
		this.idEnte = idEnte;
	}

	public String getFlIniziativa() {
		return flIniziativa;
	}

	public void setFlIniziativa(String flIniziativa) {
		this.flIniziativa = flIniziativa;
	}

	public String getCdTrbEnte() {
		
		return cdTrbEnte;
	}

	public void setCdTrbEnte(String cdTrbEnte) {
		this.cdTrbEnte = cdTrbEnte;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public byte[] getDatiCfgPlugin() {
		return datiCfgPlugin;
	}

	public void setDatiCfgPlugin(byte[] datiCfgPlugin) {
		this.datiCfgPlugin = datiCfgPlugin;
	}

	public String getCdPlugin() {
		return cdPlugin;
	}

	public void setCdPlugin(String cdPlugin) {
		this.cdPlugin = cdPlugin;
	}
	
	
}