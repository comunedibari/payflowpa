/*
 * Created on 2-nov-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package it.nch.fwk.fo.action;

import it.nch.fwk.fo.base.handler.profile.UserContext;
import it.nch.fwk.fo.base.text.DataFormatter;
import it.nch.fwk.fo.web.session.BaseSessionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author finsaccanebbia
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface ActionPermissionManager {

	public ActionForward executeBlockingAction(
			ActionMapping mapping,
			ActionForm form,
			ActionErrors errors,
			HttpServletRequest request,
			HttpServletResponse response,
			BaseSessionHandler sh,
			UserContext userActionContext,
			DataFormatter df) throws Exception;    
}
