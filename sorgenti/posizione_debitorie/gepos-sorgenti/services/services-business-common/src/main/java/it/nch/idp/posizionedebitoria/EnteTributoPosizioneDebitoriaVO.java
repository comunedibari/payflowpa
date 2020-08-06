package it.nch.idp.posizionedebitoria;

import java.io.Serializable;

public class EnteTributoPosizioneDebitoriaVO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String idEnte;
	private String denomEnte;
	private String codTribEnte;
	private String desTributo;
	
	public String getIdEnte() {
		return idEnte;
	}
	public void setIdEnte(String idEnte) {
		this.idEnte = idEnte;
	}
	public String getDenomEnte() {
		return denomEnte;
	}
	public void setDenomEnte(String denomEnte) {
		this.denomEnte = denomEnte;
	}
	public String getCodTribEnte() {
		return codTribEnte;
	}
	public void setCodTribEnte(String codTribEnte) {
		this.codTribEnte = codTribEnte;
	}
	public String getDesTributo() {
		return desTributo;
	}
	public void setDesTributo(String desTributo) {
		this.desTributo = desTributo;
	}


	public String getCodice() {
		return idEnte + "|" + codTribEnte;
	}
	public String getDescrizione() {
		return denomEnte + " - " + desTributo;
	}
	
}
