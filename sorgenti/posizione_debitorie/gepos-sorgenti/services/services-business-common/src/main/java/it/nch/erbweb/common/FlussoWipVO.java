/*
 * Created on Aug 25, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package it.nch.erbweb.common;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author BattagliL
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class FlussoWipVO extends DistintaVO implements Serializable {
	
	private Timestamp last_update = null;
	private Number idFlusso = null;
	private List disposizioni = null;
	
	private String divisa;	
	private String msia;	 
	private String primoFirmatario;
	private String secondoFirmatario;	
	
	//ad uso esclusivo dei RID
	private Character tipoIncassoRid;
	private Character tipoFormaTecnica;

	/**
	 * @return
	 */
	public Number getIdFlusso() {
		return idFlusso;
	}

	/**
	 * @return
	 */
	public Timestamp getLast_update() {
		return last_update;
	}

	/**
	 * @param string
	 */
	public void setIdFlusso(Number id) {
		idFlusso = id;
	}

	/**
	 * @param timestamp
	 */
	public void setLast_update(Timestamp timestamp) {
		last_update = timestamp;
	}

	/**
	 * @return
	 */
	public List getDisposizioni() {
		return disposizioni;
	}

	/**
	 * @param list
	 */
	public void setDisposizioni(List list) {
		disposizioni = list;
	}

	/**
	 * @return
	 */
	public String getDivisa() {
		return divisa;
	}

	/**
	 * @return
	 */
	public String getMsia() {
		return msia;
	}

	/**
	 * @param string
	 */
	public void setDivisa(String string) {
		divisa = string;
	}

	/**
	 * @param string
	 */
	public void setMsia(String string) {
		msia = string;
	}

	/**
	 * @return
	 */
	public String getPrimoFirmatario() {
		return primoFirmatario;
	}

	/**
	 * @param string
	 */
	public void setPrimoFirmatario(String string) {
		primoFirmatario = string;
	}

	/**
	 * @return
	 */
	public String getSecondoFirmatario() {
		return secondoFirmatario;
	}

	/**
	 * @param string
	 */
	public void setSecondoFirmatario(String string) {
		secondoFirmatario = string;
	}



	/**
	 * @return
	 */
	public Character getTipoFormaTecnica() {
		return tipoFormaTecnica;
	}

	/**
	 * @return
	 */
	public Character getTipoIncassoRid() {
		return tipoIncassoRid;
	}

	/**
	 * @param string
	 */
	public void setTipoFormaTecnica(Character string) {
		tipoFormaTecnica = string;
	}

	/**
	 * @param string
	 */
	public void setTipoIncassoRid(Character string) {
		tipoIncassoRid = string;
	}

}
