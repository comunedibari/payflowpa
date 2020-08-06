/*
 * Created on 16-nov-2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package it.nch.fwk.fo.core;

import it.nch.fwk.fo.das.exception.DasUncheckedException;
import it.nch.fwk.fo.dto.DTO;
import it.nch.fwk.fo.dto.DTOInfoList;

import java.io.Serializable;
import java.util.Collection;
import java.util.Locale;

/**
 * @author ffratoni
 *
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public abstract class ProxyContextImpl extends AbstractCorporateContext implements Serializable {

	private DTO menu;
	private DTO aziendaCorrente;
	private String username;
	private String pathMenuCorrente;
	private String name;
	private String ipAddress;
	private DTO currentAccount;
	private DTOInfoList infoList;
	private String httpSessionID;
	private Locale userLocale;
	

	public Locale getUserLocale() {
		return userLocale;
	}

	public void setUserLocale(Locale userLocale) {
		this.userLocale = userLocale;
	}



	/**
	 * Contiene l'operatore correntemente loggato.
	 */
	private DTO operatore;
	
	/**
	 * Contiene l'operatore originale.
	 */
	private DTO operatoreOriginale;

	/**
	 * Contiene l'associazione tra l'operatore e l'intestatario per cui si è loggato.
	 */
	protected DTO intestatarioperatoriCorrente;

	private boolean ssoLogin;

	public ProxyContextImpl()  {



	}

	/**
	 * @see it.nch.fwk.fo.interfaces.FrontEndContext#setHttpSessionID(java.lang.String)
	 */
	public void setHttpSessionID(String httpSessionID) {
		this.httpSessionID = httpSessionID;
	}



	/**
	 * @see it.nch.fwk.fo.core.FrontEndContext#getHttpSessionID()
	 */
	public String getHttpSessionID() {
		return httpSessionID;
	}


	/**
	 * @see it.nch.fwk.fo.base.handler.profile.ContextProfile#getBank()
	 */
	public String getBank() {
		// TODO Stub di metodo generato automaticamente
		return null;
	}


	/**
	 * @see it.nch.fwk.fo.base.handler.profile.ContextProfile#getChannel()
	 */
	public String getChannel() {
		// TODO Stub di metodo generato automaticamente
		return null;
	}


	/**
	 * @see it.nch.fwk.fo.base.handler.profile.ContextProfile#getLanguage()
	 */
	public String getLanguage() {
		// TODO Stub di metodo generato automaticamente
		return null;
	}


	/**
	 * @see it.nch.fwk.fo.core.AbstractCorporateContext#setMenu(it.nch.fwk.fo.dto.DTOCollection)
	 */
	public void setMenu(DTO menu) {
		// TODO Stub di metodo generato automaticamente
		this.menu = menu;
	}


	/**
	 * @see it.nch.fwk.fo.interfaces.FrontEndContext#getMenu()
	 */
	public DTO getMenu() {
		// TODO Stub di metodo generato automaticamente
		return this.menu;
	}

	/**
	 * @see it.nch.fwk.fo.interfaces.FrontEndContext#setUser()
	 */
	public void setUsername(String username) {
		// TODO Stub di metodo generato automaticamente
		this.username = username;
	}


	/**
	 * @see it.nch.fwk.fo.interfaces.FrontEndContext#getUser()
	 */
	public String getUsername() {
		// TODO Stub di metodo generato automaticamente
		return this.username;
	}

	/**
	 * @return Restituisce il valore aziendaCorrente.
	 * @see it.nch.fwk.fo.interfaces.FrontEndContext#getAziendaCorrente()
	 */
	public DTO getAziendaCorrente() {
		return aziendaCorrente;
	}
	/**
	 * @param aziendaCorrente Il valore aziendaCorrente da impostare.
	 * @see it.nch.fwk.fo.core.AbstractCorporateContext#setAziendaCorrente(it.nch.fwk.fo.dto.DTO)
	 */
	public void setAziendaCorrente(DTO aziendaCorrente) {
		this.aziendaCorrente = aziendaCorrente;
	}

	/* (non-Javadoc)
	 * @see it.nch.fwk.fo.core.BackEndContext#getStatefulSessionManager()
	 */


	/**
	 * @see it.nch.fwk.fo.core.BackEndContext#getStatelessSessionManager()
	 */
	  public abstract StatelessSessionManager getStatelessSessionManager() throws DasUncheckedException ;


	  public String getPathMenuCorrente() {
		return pathMenuCorrente;
	}

	/**
	 * @param pathMenuCorrente The pathMenuCorrente to set.
	 * @see it.nch.fwk.fo.interfaces.FrontEndContext#setPathMenuCorrente(java.lang.String)
	 */
	public void setPathMenuCorrente(String pathMenuCorrente) {
		this.pathMenuCorrente = pathMenuCorrente;
	}

	/**
	 * @see it.nch.fwk.fo.interfaces.FrontEndContext#getApplicationCode()
	 */
	public String getApplicationCode(){
		return pathMenuCorrente.substring(0,6);
	}

	/**
	 * @see it.nch.fwk.fo.interfaces.FrontEndContext#getAreaCode()
	 */
	public String getAreaCode(){
		return pathMenuCorrente.substring(7,13);
	}

	/**
	 * @see it.nch.fwk.fo.interfaces.FrontEndContext#getServiceCode()
	 */
	public String getServiceCode(){
		return pathMenuCorrente.substring(14,20);
	}

	/**
	 * @see it.nch.fwk.fo.interfaces.FrontEndContext#getFunctionCode()
	 */
	public String getFunctionCode(){
		return pathMenuCorrente.substring(21,27);
	}

	/**
	 * @return Restituisce il valore name.
	 * @see it.nch.fwk.fo.interfaces.FrontEndContext#getName()
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name Il valore name da impostare.
	 * @see it.nch.fwk.fo.interfaces.FrontEndContext#setName(java.lang.String)
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Restituisce il valore senderAccount.
	 * @see it.nch.fwk.fo.interfaces.FrontEndContext#getCurrentAccount()
	 */
	public DTO getCurrentAccount() {
		return currentAccount;
	}

	/**
	 * @param senderAccount Il valore senderAccount da impostare.
	 * @see it.nch.fwk.fo.core.AbstractCorporateContext#setCurrentAccount(it.nch.fwk.fo.dto.DTO)
	 */
	public void setCurrentAccount(DTO currentAccount) {
		this.currentAccount = currentAccount;
	}

	/**
	 * @see it.nch.fwk.fo.core.AbstractCorporateContext#getIpAddress()
	 */
	public String getIpAddress() {
		return ipAddress;
	}

	/**
	 * @see it.nch.fwk.fo.core.AbstractCorporateContext#setIpAddress(java.lang.String)
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;

	}

	/**
	 * @see it.nch.fwk.fo.core.AbstractCorporateContext#getInfo()
	 */
	public DTOInfoList getInfo() {
		return infoList;
	}

	/**
	 * @see it.nch.fwk.fo.core.AbstractCorporateContext#setInfo(it.nch.fwk.fo.dto.DTOInfoList)
	 */
	public void setInfo(DTOInfoList info) {
		this.infoList = info;
	}

	/**
	 * @see it.nch.fwk.fo.core.AbstractCorporateContext#getOperatore()
	 */
	public DTO getOperatore() {
		return operatore;
	}

	/**
	 * @see it.nch.fwk.fo.core.AbstractCorporateContext#setOperatore(it.nch.fwk.fo.dto.DTO)
	 */
	public void setOperatore(DTO operatore) {
		this.operatore = operatore;
	}

	/**
	 * @see it.nch.fwk.fo.core.AbstractCorporateContext#getOperatore()
	 */
	public DTO getOperatoreOriginale() {
		return operatoreOriginale;
	}

	/**
	 * @see it.nch.fwk.fo.core.AbstractCorporateContext#setOperatore(it.nch.fwk.fo.dto.DTO)
	 */
	public void setOperatoreOriginale(DTO operatore) {
		this.operatoreOriginale = operatore;
	}

	/**
	 * @see it.nch.fwk.fo.interfaces.FrontEndContext#getIntestatarioperatoriCorrente()
	 */
	public DTO getIntestatarioperatoriCorrente() {
		return intestatarioperatoriCorrente;
	}



	/**
	 * @see it.nch.fwk.fo.interfaces.FrontEndContext#setIntestatarioperatoriCorrente(it.nch.fwk.fo.dto.DTO)
	 */
	public void setIntestatarioperatoriCorrente(DTO intestatarioperatoriCorrente) {
		this.intestatarioperatoriCorrente = intestatarioperatoriCorrente;
	}


	/**
	 * @see it.nch.fwk.fo.interfaces.FrontEndContext#isSSOLogin()
	 */
	public boolean isSSOLogin() {
		return ssoLogin;
	}



	/**
	 * @see it.nch.fwk.fo.interfaces.FrontEndContext#setSSOLogin(boolean)
	 */
	public void setSSOLogin(boolean ssoLogin) {
		this.ssoLogin = ssoLogin;
	}
	
	private Collection deleganti;
	
	public Collection getDeleganti(){
		return this.deleganti;
	}
	public void setDeleganti(Collection deleganti){
		this.deleganti = deleganti;
	}

}
