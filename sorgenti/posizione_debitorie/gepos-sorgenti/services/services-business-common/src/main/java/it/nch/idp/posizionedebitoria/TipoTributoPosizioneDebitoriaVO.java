package it.nch.idp.posizionedebitoria;

import java.io.Serializable;


public class TipoTributoPosizioneDebitoriaVO implements Serializable
{
	private String idTributo;
	private String desTributo;
	private String cdTribEnte;

	public String getDesTributo() {
		return desTributo;
	}

	public String getIdTributo() {
		return idTributo;
	}

	public void setDesTributo(String string) {
		desTributo = string;
	}

	public void setIdTributo(String string) {
		idTributo = string;
	}

	public String getCdTribEnte() {
		return cdTribEnte;
	}

	public void setCdTribEnte(String string) {
		cdTribEnte = string;
	}

}
