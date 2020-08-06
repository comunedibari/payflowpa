package it.nch.idp.posizionedebitoria;

import java.io.Serializable;


public class EntePosizioneDebitoriaVO implements Serializable
{
	private String idEnte;
	private String cdEnte;
	private String denom;

	public String getCdEnte() {
		return cdEnte;
	}

	/**
	 * @return
	 */
	public String getDenom() {
		return denom;
	}

	/**
	 * @return
	 */
	public String getIdEnte() {
		return idEnte;
	}

	/**
	 * @param string
	 */
	public void setCdEnte(String string) {
		cdEnte = string;
	}

	/**
	 * @param string
	 */
	public void setDenom(String string) {
		denom = string;
	}

	/**
	 * @param string
	 */
	public void setIdEnte(String string) {
		idEnte = string;
	}
}
