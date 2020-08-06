/*
 * Creato il 8-mar-2006
 *
 * TODO Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
package it.nch.ebweb.generate.db.util;

import java.util.Collection;

/**
 * @author FelicettiA
 * 
 * TODO Per modificare il modello associato al commento di questo tipo generato,
 * aprire Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
public class BusinessForm {
	
	private String _packageCommon;
	private String nameService;
	private Collection attributes;
	public boolean commonReference=false;


	/**
	 * @return Restituisce il valore attributes.
	 */
	public Collection getAttributes() {
		return attributes;
	}

	/**
	 * @param attributes
	 *            Il valore attributes da impostare.
	 */
	public void setAttributes(Collection attributes) {
		this.attributes = attributes;
	}

	/**
	 * @return Restituisce il valore nameService.
	 */
	public String getNameService() {
		return nameService;
	}

	/**
	 * @param nameService
	 *            Il valore nameService da impostare.
	 */
	public void setNameService(String nameService) {
		this.nameService = nameService;
	}

	/**
	 * @return Restituisce il valore _packageCommon.
	 */
	public String get_packageCommon() {
		return _packageCommon;
	}

	/**
	 * @param common
	 *            Il valore _packageCommon da impostare.
	 */
	public void set_packageCommon(String common) {
		_packageCommon = common;
	}
	
	

	/**
	 * @return Restituisce il valore commonReference.
	 */
	public boolean isCommonReference() {
		return commonReference;
	}
	/**
	 * @param commonReference Il valore commonReference da impostare.
	 */
	public void setCommonReference(boolean commonReference) {
		this.commonReference = commonReference;
	}
}
