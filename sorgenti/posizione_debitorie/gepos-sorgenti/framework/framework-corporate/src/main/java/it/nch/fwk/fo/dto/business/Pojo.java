/*
 * Creato il 22-dic-2005
 *
 * TODO Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
package it.nch.fwk.fo.dto.business;

import it.nch.fwk.fo.common.BaseBusinessObject;

import java.io.Serializable;

/**
 * @author FelicettiA
 *
 * TODO Per modificare il modello associato al commento di questo tipo generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
public interface Pojo extends BaseBusinessObject,Serializable{
	
	public Long getId();	
	/**
	 * @param id Il valore id da impostare.
	 */
	public void setId(Long id);
	
}
