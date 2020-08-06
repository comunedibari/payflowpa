package it.nch.profile;

import it.nch.fwk.fo.interfaces.FrontEndContext;
import it.nch.fwk.fo.locator.ServiceLocatorException;
import it.nch.fwk.fo.web.util.WebUtil;
import it.nch.utility.exception.SessionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 * 
 * @author RepettiL
 * 
 * La classe fornisce un IProfileManger di tipo IrisProfileManger recuperandolo dalla sessione,
 * oppure istanziandone uno nuovo settando il servizio ProfileService. 
 *
 */
public class FactoryProfileManagerClient {
	
	static Logger logger = Logger.getLogger(FactoryProfileManagerClient.class);

	/**
	 * 
	 * @param req
	 * @param sessionId - mantenuto per compatibilit� con erbewb
	 * @return
	 * @throws SessionException
	 * @throws ServiceLocatorException
	 */
	static public IProfileManager getProfileManager(HttpServletRequest req,String sessionId) throws SessionException,
			ServiceLocatorException {

		
		IrisProfileManager profileManager = null;
		HttpSession sess = req.getSession();

		// verifica sessione
		if (sess == null) {
			throw new SessionException(null, "-1", "Sessione Insesistente");
		}

		FrontEndContext fec = WebUtil.getLocatedFrontEndContext(req);
		
		String fecSessionCode = "";
		try {
			if (fec.getHttpSessionID() == null)  {
				//TODO: controllare se questo fix puo' andare.
				String httpSessionId=req.getRequestedSessionId();
				if (httpSessionId == null) {
					httpSessionId = req.getSession().getId();
				}
				fec.setHttpSessionID(httpSessionId);
			}
			fecSessionCode = IProfileManager.PROFILE_SESSION_PREFIX.concat(fec.getHttpSessionID());
			profileManager = (IrisProfileManager) req.getSession().getAttribute(fecSessionCode);
		} catch (Throwable t) {
			logger.warn("no profileManager found in session - a new one is created [fec.getHttpSessionID(): " + fec.getHttpSessionID() + " - fecSessionCode: " + fecSessionCode + "]");
		}

		//recupera da sessione, se non trovato crea nuovo e salva in sessione
			
		if (profileManager == null) {

			profileManager = new IrisProfileManager(fec);
			// N.B. eliminata la parte in cui veniva recuperato il ProfiloBusinessDelegate da spring
			//profileManager.setProfiloService(profiloserviceService);
			sess.setAttribute(fecSessionCode, profileManager);
		}

		return profileManager;
	}
	
	static public void invalidateProfileManager(HttpServletRequest req){
		
		HttpSession sess = req.getSession();

		if (sess == null) return;
		
		FrontEndContext fec = WebUtil.getLocatedFrontEndContext(req);
		
		if (fec != null && fec.getHttpSessionID() != null){
			String fecSessionCode = IProfileManager.PROFILE_SESSION_PREFIX.concat(fec.getHttpSessionID());
			sess.setAttribute(fecSessionCode, null);
		}
		
	}
	
}
