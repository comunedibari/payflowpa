/*
 * Creato il 23-gen-2006
 *
 * TODO Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
package it.nch.fwk.fo.web.request.processor;

import it.nch.fwk.fo.action.FrameworkAction;
import it.nch.fwk.fo.util.Tracer;
import it.nch.fwk.fo.web.session.WebSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.struts.tiles.TilesRequestProcessor;

/**
 * @author Menegaz
 * 
 *         classe utilizzata per il controllo della session
 */
public class FrameworkRequestProcessor extends TilesRequestProcessor {

	protected boolean processPreprocess(HttpServletRequest request, HttpServletResponse response) {
		boolean retVal = false;
		Tracer.debug(this.getClass().getName(), "processPreprocess", request.getServletPath(), null);
		Collection ignoreList = defineListOfSessionExpiredIgnoreActions();
		Iterator it = ignoreList.iterator();
		boolean isInIgnoreList = false;
		String current = request.getServletPath();
		while (it.hasNext()) {
			String cur = (String) it.next();
			Tracer.debug(this.getClass().getName(), "processPreprocess", "Checking if current action (" + current + ") is in the ignore list. Compare with " + cur);
			if (current.endsWith(cur)) {
				isInIgnoreList = true;
				break;
			}
		}
		Tracer.debug(this.getClass().getName(), "processPreprocess", "Current action (" + current + ") is in the ignore list? " + isInIgnoreList);
		if (isInIgnoreList) {
			HttpSession session = request.getSession(true);
			retVal = true;
		} else {
			HttpSession session = request.getSession(false);
			WebSession webSession = null;
			try {
				webSession = (WebSession) request.getSession().getAttribute(FrameworkAction.WEB_SESSION_PROPERTY);
			} catch (Throwable t) {
				// do nothing
			}
			if (!session.isNew() && webSession != null) {
					Tracer.debug(this.getClass().getName(), "processPreprocess", "sessione OK", null);
					retVal = true;
			} else {
				Tracer.debug(this.getClass().getName(), "processPreprocess", request.getServletPath(), null);
				try {
					// e se uso il FWK in un altro progetto che non è IRIS?
					// Devo essere obbligato ad avere
					// una JSP che si chiama /commons/sessionExpired.jsp?
					// request.getRequestDispatcher("/commons/sessionExpired.jsp").forward(request,response);
					String sessionExpiredPage = getSessionExpiredRedirect();
					Tracer.debug(this.getClass().getName(), "processPreprocess", "sessionExpiredPage = " + sessionExpiredPage, null);
					session.invalidate();
					request.getRequestDispatcher(sessionExpiredPage).forward(request, response);
				} catch (Exception ex) {
					Tracer.info(this.getClass().getName(), "processPreprocess ", " Exception " + ex.getMessage(), null);
				}
			}
		}
		Tracer.debug(this.getClass().getName(), "processPreprocess", "return " + retVal, null);
		return retVal;
	}

	/**
	 * Questo metodo sarà overridato, e serve per definire le action che non
	 * devono avere il controllo della 'session expired' Meglio chiamare il
	 * super, per prendersi dei valori generali (tipo login.do)
	 * 
	 * Esempio di override:
	 * 
	 * protected Collection defineListOfSessionExpiredIgnoreActions() {
	 * Collection out = super.defineListOfSessionExpiredIgnoreActions();
	 * out.add("myAction.do"); return out; }
	 * 
	 * @return
	 */
	protected Collection defineListOfSessionExpiredIgnoreActions() {
		Collection out = new ArrayList();

		out.add("login.do");

		return out;
	}

	/**
	 * Di questo si DEVE fare l'override (senza la chiamata al super),
	 * altrimenti mi dà una jsp che non ha senso (ce l'ho messa a bella posta)
	 * 
	 * @return
	 */
	protected String getSessionExpiredRedirect() {
		return "youMustOverrideMethod_getSessionExpiredRedirect_inYourRequestProcessor.jsp";
	}
}