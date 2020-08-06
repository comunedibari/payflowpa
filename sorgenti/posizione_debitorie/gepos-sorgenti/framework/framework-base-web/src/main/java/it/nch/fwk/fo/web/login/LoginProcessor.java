/*
 * File: LoginProcessor.java
 * Package: it.nch.fwk.fo.web.login
 * 
 * Revision: $Revision: 1.1.1.1 $ 
 * Last revision by: $Author
 * Last revised on: $Date: 2006/05/03 11:06:46 $ 
 * Created on: 30-nov-05 - 19:28:33
 */
package it.nch.fwk.fo.web.login;

import it.nch.fwk.fo.base.handler.profile.UserContext;
import it.nch.fwk.fo.base.handler.profile.UserContextInterface;
import it.nch.fwk.fo.exceptions.FrontEndException;
import it.nch.fwk.fo.web.session.BaseSessionHandler;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * Interfaccia entrypoint per i meccanismi di login e profilazione
 * 
**/
public interface LoginProcessor {

	/**
	 * @param login
	 * @param reverseProxyBankId
	 * @param sessHandler
	 * @param paramMap
	 * @return lo UserContext
	 * @throws FrontEndException
	 * 
	 * @deprecated usa la versione con HttpSession
	 */
	public UserContext doLoginProcess(String login, String bankId,
            BaseSessionHandler sessHandler, Map paramMap)
            throws FrontEndException;
	
	public UserContextInterface doLoginProcess(String login, String bankId,
            HttpSession sessHandler, HttpServletRequest request)
            throws FrontEndException;	
}
