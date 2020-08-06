/**
 * 
 */
package it.regioneveneto.mygov.payment.mypivot.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.OperatoreEnteTipoDovutoTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.UtenteTO;

/**
 * @author Luigi Bellio
 * @author Giorgio Vallini
 * 
 */
public class SecurityContext {

	private static final String UTENTE_SESSION_KEY = "UTENTE_SESSION_KEY";
	private static final String ROLES_SESSION_KEY = "ROLES_SESSION_KEY";
	private static final String CURRENT_ROLE_SESSION_KEY = "CURRENT_ROLE";
	private static final String ENTE_SESSION_KEY = "ENTE_SESSION_KEY";
	private static final String FEDERA_PROFILE_SESSION_KEY = "it.regioneveneto.mygov.payment.pa.web.filter.federaProfile";

	private static final Log logger = LogFactory.getLog(SecurityContext.class);

	private static final String ALL_ENTI_SESSION_KEY = "ALL_ENTI_SESSION_KEY";
	private static final String OPERATORE_ENTE_TIPO_DOVUTO_SESSION_KEY = "OPERATORE_ENTE_TIPO_DOVUTO_SESSION_KEY";
	
	private SecurityContext() {
		super();
	}

	/**
	 * Legge il parametro dalla sessione.
	 * 
	 * @author Alessandro Paolillo
	 */
	@SuppressWarnings("unchecked")
	public static synchronized Object getFromSession(String sessionKey) {
		return (List<String>) getSecurityObject(sessionKey);
	}

	/**
	 * Aggiunge il parametro in sessione.
	 * 
	 * @author Alessandro Paolillo
	 */
	public static synchronized void addToSession(String sessionKey, Object value) {
		setSecurityObject(sessionKey, value);
	}
	
	/**
	 * @return
	 */
	public static synchronized UtenteTO getUtente() {
		return (UtenteTO) getSecurityObject(UTENTE_SESSION_KEY);
	}

	/**
	 * @param utente
	 */
	public static synchronized void setUtente(final UtenteTO utente) {
		setSecurityObject(UTENTE_SESSION_KEY, utente);
	}
	
	/**
	 * @return
	 */
	public static synchronized List<String> getRoles() {
		return (List<String>) getSecurityObject(ROLES_SESSION_KEY);
	}

	/**
	 * @param utente
	 */
	public static synchronized void setRoles(final List<String> roles) {
		setSecurityObject(ROLES_SESSION_KEY, roles);
	}
	
	/**
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static synchronized List<EnteTO> getAllEnti() {
		return (List<EnteTO>) getSecurityObject(ALL_ENTI_SESSION_KEY);
	}

	/**
	 * @param operatoreEnti
	 */
	public static synchronized void setAllEnti(final List<EnteTO> allEnti) {
		List<EnteTO> allEntiCopy = null;

		if (allEnti != null)
			allEntiCopy = new ArrayList<EnteTO>(allEnti);
		
		Collections.sort(allEntiCopy, new Comparator<EnteTO>() {
		    public int compare(EnteTO ente1, EnteTO ente2) {
		    	return ente1.getDeNomeEnte().compareTo(ente2.getDeNomeEnte());
		    }
		});
		
		
		setSecurityObject(ALL_ENTI_SESSION_KEY, allEntiCopy);
	}

	/**
	 * @return
	 */
	public static synchronized EnteTO getEnte() {
		return (EnteTO) getSecurityObject(ENTE_SESSION_KEY);
	}

	/**
	 * @param ente
	 */
	public static synchronized void setEnte(final EnteTO ente) {
		if (ente == null) {
			setSecurityObject(ENTE_SESSION_KEY, null);

			return;
		}

		setEnte(ente.getCodIpaEnte());
	}

	/**
	 * @param ente
	 */
	public static synchronized void setEnte(final String codIpaEnte) {
		if (codIpaEnte == null) {
			setSecurityObject(ENTE_SESSION_KEY, null);

			return;
		}

		EnteTO foundEnte = null;

		if (foundEnte == null) {
			List<EnteTO> allEnti = getAllEnti();
			for (EnteTO ente : allEnti) {
				if (codIpaEnte.equals(ente.getCodIpaEnte())) {
					foundEnte = ente;

					break;
				}
			}
		}

		if (foundEnte == null) {
			logger.error("try to set unavailable ente [" + codIpaEnte + "]");

			throw new IllegalArgumentException("try to set unavailable ente ["
					+ codIpaEnte + "]");
		}

		setSecurityObject(ENTE_SESSION_KEY, foundEnte);
	}
	
	
	public static synchronized void setEnteViewFilters(final String codIpaEnte, String action, Map<String,Object> filtersMap) {

		String key = codIpaEnte + "_"+ action +"_VIEW_FILTERS";

		setSecurityObject(key, filtersMap);
	}
	
	public static synchronized Map<String,Object> getEnteViewFilters(final String codIpaEnte, String action) {

		String key = codIpaEnte + "_" + action + "_VIEW_FILTERS";

		return getSecurityObject(key) == null ? null : (Map<String,Object>)getSecurityObject(key);
	}

	/**
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static synchronized Map<String, List<String>> getFederaUserProfile() {
		Map<String, List<String>> securityObject = (Map<String, List<String>>) getSecurityObject(FEDERA_PROFILE_SESSION_KEY);
		return securityObject;
	}

	/**
	 * @param securityObjectSessionKey
	 * @return
	 */
	private static Object getSecurityObject(
			final String securityObjectSessionKey) {
		Object securityObject = null;

		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder
				.currentRequestAttributes();
		HttpServletRequest request = attr.getRequest();

		if (request != null) {
			HttpSession session = request.getSession();
			if (session != null) {
				synchronized (session) {
					securityObject = session
							.getAttribute(securityObjectSessionKey);
				}
			}
		}

		if (securityObject == null) {
			logger.debug("securityObject [" + securityObjectSessionKey
					+ "] not binded to current session");

			return null;
		}

		return securityObject;
	}

	/**
	 * @param securityObjectSessionKey
	 * @param securityObject
	 */
	private static void setSecurityObject(
			final String securityObjectSessionKey, final Object securityObject) {

		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder
				.currentRequestAttributes();
		HttpServletRequest request = attr.getRequest();

		if (request == null) {
			logger.error("http servlet request not binded to current thread");

			throw new RuntimeException(
					"http servlet request not binded to current thread");
		}

		HttpSession session = request.getSession(true);
		synchronized (session) {
			if (securityObject == null) {
				session.removeAttribute(securityObjectSessionKey);

				logger.debug("securityObject [" + securityObjectSessionKey
						+ "] unbinded from current session");
			} else {
				session.setAttribute(securityObjectSessionKey, securityObject);

				logger.debug("securityObject [" + securityObjectSessionKey
						+ "] binded to current session");
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public static synchronized List<OperatoreEnteTipoDovutoTO> getAllOperatoreEnteTipoDovuto() {
		return (List<OperatoreEnteTipoDovutoTO>) getSecurityObject(OPERATORE_ENTE_TIPO_DOVUTO_SESSION_KEY);
	}

	public static synchronized void setAllOperatoreEnteTipoDovuto(final List<OperatoreEnteTipoDovutoTO> allOperatoreEnteTipoDovuto) {
		List<OperatoreEnteTipoDovutoTO> allOperatoreEnteTipoDovutoCopy = null;

		if (allOperatoreEnteTipoDovuto != null)
			allOperatoreEnteTipoDovutoCopy = new ArrayList<OperatoreEnteTipoDovutoTO>(allOperatoreEnteTipoDovuto);
		
		Collections.sort(allOperatoreEnteTipoDovutoCopy, new Comparator<OperatoreEnteTipoDovutoTO>() {
		    public int compare(OperatoreEnteTipoDovutoTO operatoreEnteTipoDovuto1, OperatoreEnteTipoDovutoTO operatoreEnteTipoDovuto2) {
		    	return operatoreEnteTipoDovuto1.getEnteTipoDovuto().getDeTipo().compareTo(operatoreEnteTipoDovuto2.getEnteTipoDovuto().getDeTipo());
		    }
		});
		
		setSecurityObject(OPERATORE_ENTE_TIPO_DOVUTO_SESSION_KEY, allOperatoreEnteTipoDovutoCopy);
	}
}
