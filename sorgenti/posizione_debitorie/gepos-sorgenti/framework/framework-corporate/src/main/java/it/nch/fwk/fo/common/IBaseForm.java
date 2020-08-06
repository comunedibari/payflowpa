/*
 * Creato il 12-mar-2006
 *
 * TODO Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
package it.nch.fwk.fo.common;

import java.io.Serializable;

/**
 * Interfaccia da cui discendono tutte le altre interfacce di Form.
 * Il contratto espone i metodi per l'accesso ad un oggetto denominato 'NativePojo'
 * che rappresenta il pojo da cui questo oggetto Form è derivato per copia.
 *
 * @author FelicettiA
 *
 * TODO Per modificare il modello associato al commento di questo tipo generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
public interface IBaseForm extends Serializable{

	/**
	 * Restituisce il pojo da cui questo oggetto Form è derivato per copia.
	 *
	 * @return il pojo da cui questo oggetto Form è derivato per copia.
	 */
	public Object getNativePojo();

	/**
	 * Imposta su Questo Form il pojo da cui è derivato per copia.
	 *
	 * @param nativePojo il pojo da cui questo oggetto Form è derivato per copia..
	 */
	public void setNativePojo(Object nativePojo);

}
