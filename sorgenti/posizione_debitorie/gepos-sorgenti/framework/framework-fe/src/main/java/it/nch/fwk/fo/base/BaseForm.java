/*
 * Creato il 7-mar-2006
 *
 * TODO Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
package it.nch.fwk.fo.base;


import it.nch.fwk.fo.common.CommonBusinessObject;

import org.apache.struts.validator.ValidatorActionForm;

/**
 * Classe utilizzata per contenere le informazioni dei ValidatorActionForm provenienti dalla request attraverso Struts
 * Viene utilizzata anche come filtro di ricerca.
 * Implementa in modo astratto IBaseForm.
 *
 * @author FelicettiA
 *
 * TODO Per modificare il modello associato al commento di questo tipo generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
public abstract class BaseForm extends ValidatorActionForm implements CommonBusinessObject{

	/**
	 * Il pojo da cui questo form è stato derivato per copia.
	 */
	public Object nativePojo = null;

	/**
	 * Restituisce il pojo da cui questo oggetto Form è derivato per copia.
	 *
	 * @return il pojo da cui questo oggetto Form è derivato per copia.
	 */
	public Object getNativePojo() {
		return nativePojo;
	}

	/**
	 * Imposta su questo Form il pojo da cui è stato derivato per copia.
	 *
	 * @param nativePojo il pojo da cui questo Form è stato derivato per copia..
	 */

	public abstract void setNativePojo(Object nativePojo);

	//this.nativePojo = nativePojo;
	//}

}
