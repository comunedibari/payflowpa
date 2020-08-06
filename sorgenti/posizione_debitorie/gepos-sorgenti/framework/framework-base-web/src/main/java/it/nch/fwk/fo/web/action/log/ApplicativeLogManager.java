/*
 * Created on 1-dic-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package it.nch.fwk.fo.web.action.log;

import it.nch.fwk.fo.web.action.user.UserIdentifier;

import javax.servlet.http.HttpServletRequest;

/**
 * @author finsaccanebbia
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface ApplicativeLogManager {

    public void setupLogging(HttpServletRequest request, UserIdentifier user);
    public void tearDownLogging(HttpServletRequest request);
}
