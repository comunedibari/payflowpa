/*
 * Created on 1-dic-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package it.nch.fwk.fo.web.action.log;

import it.nch.fwk.fo.web.action.user.UserIdentifier;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.NDC;

/**
 * @author finsaccanebbia
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Log4jApplicativeLogManager implements ApplicativeLogManager {

	/**
	 * Imposta informazioni di base per il logging applicativo 
	 * L'implementazione di default e' basata sull'oggetto
	 * Nested Diagnostic Context di log4j
	 * 
	 */
	public void setupLogging(HttpServletRequest request, UserIdentifier identifier) {
		String userId=null;
		String bankId=null;
	
		try {
            userId=identifier.getUserId(request);
        } catch (NoSuchFieldException e) {
            userId="<no user>";
        }

		try {
			bankId=identifier.getBankId(request);
		} catch (NoSuchFieldException e) {
            userId="<no bank>";
        }
		
		NDC.remove();
		
		NDC.push("[UserId = " + userId + " - BankId - " + bankId + " - SessionId = " + request.getSession().getId()	+ "]");
	}

	/**
     * @param request
     */
    public void tearDownLogging(HttpServletRequest request) {
        NDC.remove();
    }

}
