/**
 * 
 */
package it.nch.fwk.fo.web;

import it.nch.fwk.fo.web.session.SessionConstants;

/**
 * @author GardiniG
 * 
 */
public interface FrameworkWebInterface {

	/**
	 * E' il nome da usare in una form di login manuale per permettere di
	 * effettuare una login senza avere a disposizione l'ambiente di SSO
	 */
	public final static String MANUAL_LOGIN_PROPERTY = "AUTOLOGIN";

	public final static String MANUAL_BANK_PROPERTY = "Bank";

	public final static String USER_CONTEXT_PROPERTY = SessionConstants.USER_CONTEXT_PROPERTY;

	public final static String WEB_SESSION_PROPERTY = SessionConstants.WEB_SESSION_PROPERTY;

	public final static String STRUTS_PROPERTY = SessionConstants.STRUTS_PROPERTY;
}