/*
 * File: MinimalContextProfile.java
 * Package: com.etnoteam.service.handler.profile
 *
 * Revision: $Revision: 1.1.1.1 $
 * Last revision by: $Author: CattaniA $
 * Last revised on: $Date: 2006/05/03 11:06:45 $
 * Created on: 11-ott-03 - 16.22.00
 * Created by: finsaccanebbia (Etnoteam)
 */
package it.nch.fwk.fo.base.handler.profile;

/**
 *
 * Questa classe deve essere usata solo per facilitare la
 * migrazione da situazioni in cui ci sono le triple di
 * dati di contesto (banca, canale, lingua) all'uso
 * dell'interfaccia ContextProfile che e' esposta dalla
 * UserContext
 *
 * @deprecated
 */
public class MinimalContextProfile implements ContextProfile {

	/** [Bancaintesa_Common] MinimalContextProfile.java
	 * =============================================
	 * it.nch.fwk.fo.base.handler.profile.MinimalContextProfile.java
	 *		.serialVersionUID [long]
	 * =============================================
	 * @todo	:TODO
	 * @since	:14-dic-2005
	 * @author	:GennaroR2005
	 */
	private static final long	serialVersionUID	= - 5086716299303115320L;
	String bank;
	String language;
	String channel;

	/**
	 * Costruttore pubblico del contesto minimale
	 * @param bank codice banca
	 * @param channel codice canale
	 * @param language codice lingua
	 */
	public MinimalContextProfile(String bank, String channel, String language) {
		super();
		this.bank=bank;
		this.language=language;
		this.channel=channel;
	}

	/**
	 * Recupera il codice della banca
	 * @return il codice della banca
	 */
	public String getBank() {
		return bank;
	}

	/**
	 * Recupera il codice del canale
	 * @return il codice del canale
	 */
	public String getChannel() {
		return channel;
	}

	/**
	 * Recupera il codice della lingua
	 * @return il codice della lingua
	 */
	public String getLanguage() {
		return language;
	}

}
