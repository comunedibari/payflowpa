package it.nch.fwk.fo.web.action;

import it.nch.fwk.fo.common.constants.CommonConstants;
import it.nch.fwk.fo.util.Tracer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

/**
 * XXX da cancellare se non utilizzata!!!
 */
public class DispostiveAction extends CorporateAction {

//	/**
//	 * @deprecated NON UTILIZZARE!!!
//	 */
//	public ActionForward init(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
//		actionForm.reset(actionMapping, httpServletRequest);
//		return actionMapping.findForward("init");
//	}
//
//	/**
//	 * @deprecated NON UTILIZZARE!!!
//	 */
//	public boolean initializeSingleAction(HttpServletRequest httpServletRequest) {
//		// Devo svuotare tutte le collection in memoria alla websession
//		getWebSession(httpServletRequest).resetAllCollectionsDTO();
//		return false;
//	}
//
//	/**
//	 * @deprecated NON UTILIZZARE!!!
//	 */
//	public boolean finalizeSingleAction(HttpServletRequest httpServletRequest) {
//		return false;
//	}
//
//	/**
//	 * Controlla il token in corrispondenza di una disposizione. Da inserire
//	 * come prima cosa in fase dispositiva per evitare di effettuare una doppia
//	 * disposizione ricaricando la pagina.
//	 * 
//	 * @deprecated NON UTILIZZARE!!!
//	 */
//	protected ActionForward checkTokenValid(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
//
//		Tracer.debug(this.getClass().getName(), "checkTokenValid", "BEGIN", null);
//		ActionForward actionForward = null;
//		boolean removeToken = true;
//		if (!isTokenValid(httpServletRequest, removeToken)) {
//			Tracer.debug(this.getClass().getName(), "checkTokenValid", "warning: token is not valid", null);
//			actionForward = actionMapping.findForward(CommonConstants.FORWARD_ERROR_TOKEN);
//			Tracer.debug(this.getClass().getName(), "checkTokenValid", "actionForward= " + actionForward, null);
//
//			ActionMessages actionMessages = new ActionMessages();
//			ActionMessage msg = new ActionMessage("errors.token");
//			actionMessages.add(ActionMessages.GLOBAL_MESSAGE, msg);
//			saveErrors(httpServletRequest, actionMessages);
//		} else
//			Tracer.debug(this.getClass().getName(), "checkTokenValid", "token ok: remove token from session", null);
//		Tracer.debug(this.getClass().getName(), "checkTokenValid", "END", null);
//		return actionForward;
//	}

}