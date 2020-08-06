/*
 * Creato il 5-mar-2006
 *
 * TODO Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
package it.nch.fwk.fo.common;

import it.nch.fwk.fo.dto.DTO;

import java.io.Serializable;


/**
 * Sia Form che Pojo discendono da questo BusinessObject
 * che pertanto si definisce comune in quanto espone i metodi comuni
 * sia agli oggetti contenitori di FrontEnd che a quelli di BackEnd.
 *
 * @author FelicettiA
 *
 * TODO Per modificare il modello associato al commento di questo tipo generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
public interface CommonBusinessObject extends Serializable{

	/**
	 * Copia questo CommonBusinessObject in un altro che restituisce.
	 *
	 * @return un CommonBusinessObject copia di questo.
	 */
	public abstract CommonBusinessObject copy();

	public abstract void show();

	/**
	 * Restituisce un nuovo DTO che incapsula questo CommonBusinessObject come BusinessObject.
	 *
	 * @return un nuovo DTO che incapsula questo CommonBusinessObject come BusinessObject.
	 */
	public abstract DTO incapsulateBO();

}
