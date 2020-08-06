/*
 * Created on 7-ott-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package it.nch.fwk.fo.exceptions.handler;

import it.nch.fwk.fo.base.handler.profile.ContextProfile;

/**
 * @author EE07869
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface ErrorManager {
	public abstract ErrorDetails getErrorDetails(String code,
			ContextProfile contextProfile);

	/**
	 * Method getErrorDetails. Ritorna un dto specifico con il dettaglio dell'errore in multi-lingua/banca/canale,
	 * null in caso non venga trovata nessuna occorrenza.
	 * @param code codice di errore
	 * @param bank banca di riferimento utente
	 * @param ch canale di riferimento utente
	 * @param lang lingua di riferimento utente
	 * @deprecated se avete uno UserContext a disposizione usate la forma compressa getErrorDetails(String code,ContextProfile contextProfile) 
	 * @return ErrorDetails, null se il codice dell'errore non e' presente nel Db
	 */
	public abstract ErrorDetails getErrorDetails(String code, String bank,
			String ch, String lang);

	/**
	 * @param code
	 * @param bank
	 * @param ch
	 * @param lang
	 * @param area
	 * @return
	 * @deprecated usa getErrorDetail
	 */
	public abstract ErrorDetails getErrorDetails(String code, String bank, String ch, String lang, int area);

	/**
	 * @param code
	 * @param userContext
	 * @param productArea
	 * @return
	 * @deprecated usa getErrorDetail
	 */
	public abstract ErrorDetails getErrorDetails(String code, ContextProfile userContext, int productArea);

	/**
	 * @param code
	 * @param bank
	 * @param ch
	 * @param lang
	 * @param area
	 * @param actionType
	 * @return
	 * @deprecated usa getErrorDetail
	 */
	public abstract ErrorDetails getErrorDetails(String code, String bank, String ch, String lang, int area, int actionType);


	/**
	 * @param code
	 * @param contextProfile
	 * @return
	 */
	public abstract ErrorDetailInterface getErrorDetail(String code,
			ContextProfile contextProfile);

	/**
	 * @param code
	 * @param userContext
	 * @param productArea
	 * @return
	 */
	public abstract ErrorDetailInterface getErrorDetail(String code, ContextProfile userContext, int productArea);

	
}