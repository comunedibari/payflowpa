/*
 * Creato il 5-dic-2005
 *
 * TODO Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
package it.nch.fwk.fo.dto;

import java.io.Serializable;

/**
 * @author sberisso
 *
 * Interfaccia che identifica un DTO in grado di tenere traccia di una lista di informazioni
 * e di restituire anche una informazione singola.
 *
 * TODO Per modificare il modello associato al commento di questo tipo generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
public interface FrameworkDTOInterface extends Serializable {

	public DTOInfoList getInfoList();

	public void setInfoList(DTOInfoList infoList);

	public DTOInfoInterface getDTOInfo();

}
