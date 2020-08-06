/*
 * Created on 26-giu-08
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package it.nch.erbweb.profilo;

import it.tasgroup.services.util.enumeration.EnumTipoExport;

import java.io.Serializable;

/**
 * @author simone
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ProfiloInputVO implements Serializable {

	String intestatario;
	
	String operatore;
	
	String tipoServizio;
	
	private EnumTipoExport tipoEsportazione;
	
	/**
	 * @return
	 */
	public String getIntestatario() {
		return intestatario;
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
	public void setIntestatario(String string) {
		intestatario = string;
	}

	/**
	 * @param string
	 */
	public void setTipoServizio(String string) {
		tipoServizio = string;
	}

	public EnumTipoExport getTipoEsportazione() {
		return tipoEsportazione;
	}

	public void setTipoEsportazione(EnumTipoExport tipoEsportazione) {
		this.tipoEsportazione = tipoEsportazione;
	}

	public String getOperatore() {
		return operatore;
	}

	public void setOperatore(String operatore) {
		this.operatore = operatore;
	}

}
