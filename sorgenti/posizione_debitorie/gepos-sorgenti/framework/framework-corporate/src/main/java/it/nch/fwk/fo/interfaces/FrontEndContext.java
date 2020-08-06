/*
 * Creato il 22-nov-2005
 *
 * TODO Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
package it.nch.fwk.fo.interfaces;

import it.nch.fwk.fo.base.handler.profile.ContextProfile;
import it.nch.fwk.fo.common.CommonBusinessObject;
import it.nch.fwk.fo.dto.DTO;
import it.nch.fwk.fo.dto.DTOInfoList;

import java.util.Collection;
import java.util.Locale;


/**
 * Interfaccia che rappresenta il contenitore di tutte le informazioni dell'utente che si è autenticato.
 * Si tratta di un oggetto che deve essere utilizzato nei soli livelli di FrontEnd.
 * Ad esempio, può essere mantenuto nella HTTPSession per tutta la durata di una sessione utente.
 *
 * @author EE10057
 *
 * TODO Per modificare il modello associato al commento di questo tipo generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
public interface FrontEndContext extends CommonBusinessObject,ContextProfile {

	public String getHttpSessionID();

	public DTO getMenu();

	/**
	 * Restituisce l'intestatario per cui l'operatore si è autenticato.
	 *
	 * @return l'intestatario per cui l'operatore si è autenticato.
	 */
	public DTO getAziendaCorrente();

	public String getUsername();

	public void setUsername(String username);


	public String getPathMenuCorrente();

	public void setPathMenuCorrente(String pathMenuCorrente);
	//TODO da implementare per permettere invalidazione da f.e.
	//public boolean invalidateSession();

	public String getApplicationCode();

	public String getAreaCode();

	public String getServiceCode();

	public String getFunctionCode();

	public String getName();

	public void setName(String username);

	public void setCurrentAccount(DTO senderAccount);
	public DTO getCurrentAccount();

	public String getIpAddress();
	public void setIpAddress(String s);
	public DTOInfoList getInfo();
	public void setInfo(DTOInfoList dtoinfolist);
	public void setHttpSessionID(String httpSessionID);

	/**
	 * Imposta l'operatore.
	 *
	 * @param operatore
	 */
	public void setOperatore(DTO operatore);

	/**
	 * Restituisce l'operatore che si è autenticato.
	 *
	 * @return l'operatore che si è autenticato.
	 */
	public DTO getOperatore();

	/**
	 * Restituisce l'operatore originale.
	 *
	 * @return l'operatore originale.
	 */
	public DTO getOperatoreOriginale();

	/**
	 * Imposta l'operatore originale.
	 *
	 * @param operatore
	 */
	public void setOperatoreOriginale(DTO operatore);


	/**
	 * Restituisce l'associazione tra intestatario e operatore con cui l'utente si è autenticato.
	 *
	 * @return l'associazione tra intestatario e operatore con cui l'utente si è autenticato.
	 */
	public DTO getIntestatarioperatoriCorrente();

	/**
	 * Imposta l'associazione tra intestatario e operatore con cui l'utente si è autenticato.
	 *
	 * @param intestatarioperatoriCorrente l'associazione tra intestatario e operatore con cui
	 * l'utente si è autenticato.
	 */
	public void setIntestatarioperatoriCorrente(DTO intestatarioperatoriCorrente);

	public String getTipoIntestatarioperatore();

	public boolean isAdminitratorLoggedIn();

	public boolean isGenericOperatorLoggedIn();

	public boolean isSSOLogin();
	public void setSSOLogin(boolean ssoLogin);
	
	public Locale userLocale();

	public void setUserLocale(Locale locale);
	
	public Collection getDeleganti();
	public void setDeleganti(Collection deleganti);
	
}
