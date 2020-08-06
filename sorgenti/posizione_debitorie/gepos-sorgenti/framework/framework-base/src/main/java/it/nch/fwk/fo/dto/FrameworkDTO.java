/*
 * Creato il 5-dic-2005
 *
 * TODO Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
package it.nch.fwk.fo.dto;

import java.util.List;

/**
 * @author sberisso
 *
 * TODO Per modificare il modello associato al commento di questo tipo generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
public abstract class FrameworkDTO  implements FrameworkDTOInterface {

	/**
	 * variabili
	 */
	protected DTOInfoList  infoList = null;
	protected List infoList2 = null;
	
	/**
	 * metodi
	 */
	public DTOInfoList getInfoList() {
		return infoList;
	}

	public void setInfoList(DTOInfoList infoList) {
		this.infoList = infoList;
	}
	public DTOInfoInterface getDTOInfo() {
        DTOInfoInterface result = null;
        if (infoList.size()>0) {
            result = (DTOInfoInterface)infoList2.get(0);
        } 
        return result;
    }
	
}
