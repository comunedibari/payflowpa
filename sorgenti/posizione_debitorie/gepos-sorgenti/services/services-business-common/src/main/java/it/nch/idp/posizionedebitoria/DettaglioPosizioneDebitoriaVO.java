package it.nch.idp.posizionedebitoria;

import java.io.Serializable;

public class DettaglioPosizioneDebitoriaVO implements Serializable {
   
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean found=false;
	
	private String dataRichiesta;
	private String descTipoRichiesta;
	private String descConvenzione;
	private String brancaPrestazione;
	private String descPrestazioneLunga;
	private String dataAppuntamento;
	private String descStrutturaSanitaria;
	
	public String getDataRichiesta() {
		return dataRichiesta;
	}
	public void setDataRichiesta(String dataRichiesta) {
		this.dataRichiesta = dataRichiesta;
	}
	public String getDescTipoRichiesta() {
		return descTipoRichiesta;
	}
	public void setDescTipoRichiesta(String descTipoRichiesta) {
		this.descTipoRichiesta = descTipoRichiesta;
	}
	public String getDescConvenzione() {
		return descConvenzione;
	}
	public void setDescConvenzione(String descConvenzione) {
		this.descConvenzione = descConvenzione;
	}
	public String getBrancaPrestazione() {
		return brancaPrestazione;
	}
	public void setBrancaPrestazione(String brancaPrestazione) {
		this.brancaPrestazione = brancaPrestazione;
	}
	public String getDescPrestazioneLunga() {
		return descPrestazioneLunga;
	}
	public void setDescPrestazioneLunga(String descPrestazioneLunga) {
		this.descPrestazioneLunga = descPrestazioneLunga;
	}
	public String getDataAppuntamento() {
		return dataAppuntamento;
	}
	public void setDataAppuntamento(String dataAppuntamento) {
		this.dataAppuntamento = dataAppuntamento;
	}
	public boolean isFound() {
		return found;
	}
	public void setFound(boolean found) {
		this.found = found;
	}
	public String getDescStrutturaSanitaria() {
		return descStrutturaSanitaria;
	}
	public void setDescStrutturaSanitaria(String descStrutturaSanitaria) {
		this.descStrutturaSanitaria = descStrutturaSanitaria;
	}

}
