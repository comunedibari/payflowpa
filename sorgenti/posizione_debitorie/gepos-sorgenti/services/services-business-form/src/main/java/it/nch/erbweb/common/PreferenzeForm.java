/*
 * Created on Sep 23, 2008 by Riccardo Cannas
 *
 */
package it.nch.erbweb.common;

import org.apache.struts.validator.ValidatorForm;

/**
 * @author Riccardo
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PreferenzeForm extends ValidatorForm {
	private String elencoPreferenze;
	private String tipoServizio;

	/**
	 * @return
	 */
	public String getElencoPreferenze() {
		return elencoPreferenze;
	}

	/**
	 * @return
	 */
	public String getTipoServizio() {
		return tipoServizio;
	}

	/**
	 * @param string
	 */
	public void setElencoPreferenze(String string) {
		elencoPreferenze = string;
	}

	/**
	 * @param string
	 */
	public void setTipoServizio(String string) {
		tipoServizio = string;
	}

}
