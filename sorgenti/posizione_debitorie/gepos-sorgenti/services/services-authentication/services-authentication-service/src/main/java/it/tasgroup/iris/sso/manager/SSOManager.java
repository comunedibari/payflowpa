package it.tasgroup.iris.sso.manager;

import javax.servlet.http.HttpServletRequest;

import it.tasgroup.iris.sso.exception.SSOException;

public interface SSOManager {

	SSOUser getAuthenticatedUser(HttpServletRequest httpServletRequest) throws SSOException;
	boolean checkSSOUserInSession(HttpServletRequest httpServletRequest);
	String manageLogout(HttpServletRequest httpServletRequest);
}
