/*
 * Creato il 21-nov-2005
 *
 * TODO Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
package it.nch.fwk.fo.core;

import it.nch.fwk.fo.common.CommonBusinessObject;
import it.nch.fwk.fo.dto.DTO;
import it.nch.fwk.fo.dto.DTOInfoList;
import it.nch.fwk.fo.interfaces.FrontEndContext;

import java.io.Serializable;

/**
 * Classe astratta che contiene tutte le informazioni dell'utente che si è autenticato.
 *
 *
 * @author EE10057
 *
 * TODO Per modificare il modello associato al commento di questo tipo generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
public abstract class AbstractCorporateContext implements BackEndContext,FrontEndContext, Serializable {

	/**
	 *
	 */


	protected transient StatelessSessionManager statelessSM;
	private String ipAddress = null;
	private DTOInfoList infoList = null;

	public AbstractCorporateContext() {
		super();
		// TODO Stub di costruttore generato automaticamente
	}

	public  abstract void setMenu(DTO menu);
	public  abstract void setAziendaCorrente(DTO aziendaCorrente);
	public  abstract void setCurrentAccount(DTO senderAccount);

	public abstract void setIpAddress(String s);
	public abstract void setOperatore(DTO operatore);
	public abstract DTO getOperatore();
	public abstract String getIpAddress();
	public abstract DTOInfoList getInfo();
	public abstract void setInfo(DTOInfoList dtoinfolist);


	public CommonBusinessObject copy(){
		throw new UnsupportedOperationException("copy operation unsupported");
	}

	public void show(){
		throw new UnsupportedOperationException("show operation unsupported");
	}

	public DTO incapsulateBO(){
		throw new UnsupportedOperationException("incapsulateBO operation unsupported");
	}
}
