/*
 * Created on 18-feb-09
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package it.nch.utility.web;

import it.nch.fwk.fo.util.Tracer;
import it.nch.utility.IbisConfigurationFromDB;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.struts.Globals;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.util.MessageResources;

/**
 * @author stefano
 * 
 */
public class WebUtility {


	public static String getResourceMessage(PageContext pageContext, String key) throws JspException {
		Object[] args = { null, null, null, null };
		String s_code = (String) pageContext.getRequest().getAttribute("codiceServizio");// prendo
		// il
		// codice
		// servizio
		String userLocaleKey = Globals.LOCALE_KEY;
		return getResourceMessage(pageContext, s_code, userLocaleKey, key, s_code, args);
	}

	public static String getResourceMessage(PageContext pageContext, String key, String s_code) throws JspException {
		Object[] args = { null, null, null, null };
		return getResourceMessage(pageContext, null, null, key, s_code, args);
	}

	public static boolean isNotEmpty(String field) {
		return field != null && !"".equals(field.trim());
	}

	public static String getResourceMessage(PageContext pageContext, String bundle, String localeKey, String key, String s_code,
			Object[] args) throws JspException {
		String message = null;
		// Tracer.info("WebUtility", "getResourceMessage",
		// "Searching for key: "+key);
		if (key == null)
			return "";

		if (key.trim().indexOf(" ") > 0) {// se la chiave contiene spazi allora
			// none e' una chiave
			// Tracer.info("WebUtility", "getResourceMessage",
			// "it's not a key, returning: "+key);
			return key;
		}

		if (bundle != null && !bundle.trim().equals("")) {
			message = TagUtils.getInstance().message(pageContext, bundle, localeKey, key, args);
			// Tracer.info("WebUtility", "getResourceMessage",
			// "using bundle found: "+message);

			if (message != null) {
				return message;// ok, trovato testo
			}
		}

		if (s_code != null) {// nuova logica

			message = TagUtils.getInstance().message(pageContext, s_code, localeKey, key, args);
			// Tracer.info("WebUtility", "getResourceMessage",
			// "using codiceServizio found: "+message);

			if (message != null) {
				return message;// ok, trovato testo
			}
		}

		message = TagUtils.getInstance().message(pageContext, null, localeKey, key, args);
		// Tracer.info("WebUtility", "getResourceMessage", " found: "+message);

		if (message == null) {// testo non trovato
			message = "";
			// Tracer.error("WebUtility", "getResourceMessage",
			// "Unknown resource key: "+key);
			message = "#" + key + "#";// TODO riga da commentare in produzione
		}

		return message;

	}

	public static String message(MessageResources resources, Locale userLocale, String key, Object args[]) throws JspException {
		String message = null;
		if (args == null)
			message = resources.getMessage(userLocale, key);
		else
			message = resources.getMessage(userLocale, key, args);

		return message;
	}

	// -------------------------------------------------------------------------
	// Ritorna il valore associato alla variabile corrente
	// N.B. utilizzato nelle form:
	// value"<%=par>"
	// diventa
	// value"<%=getTextValue(par)>"
	// -------------------------------------------------------------------------
	public static String getTextValue(String par) {
		return (par == null) ? "" : par;
	}

	// -------------------------------------------------------------------------
	// Ritorna lo username contenuto nel certificato client
	// -------------------------------------------------------------------------
	public static String getCertificateUser() {
		return null;
		// return Request.ClientCertificate("SubjectCN");
	}

	public static String getContextPath(PageContext pageContext) {
		return ((HttpServletRequest) pageContext.getRequest()).getContextPath();
	}

	public static String formatIbanItalia(String iban) {
		String ibanFormatted = "";
		if (iban != null) {
			if (iban.trim().length() == 27) {
				try {
					ibanFormatted = iban.trim().substring(0, 2) + " " + iban.trim().substring(2, 4) + " " + iban.trim().substring(4, 5)
							+ " " + iban.trim().substring(5, 10) + " " + iban.trim().substring(10, 15) + " " + iban.trim().substring(15);
				} catch (Exception e) {
					ibanFormatted = "error formatting iban";
				}
			} else {
				ibanFormatted = iban;
			}
		}
		return ibanFormatted;
	}

	// -------------------------------------------------------------------------
	// Restituisce la data corrente sul server formattata come g/m/a.
	// -------------------------------------------------------------------------
	public static String getTodayDate() {
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("dd/MM/yyyy");
		return sdf.format(new Date());
	}

	public static Date composeDate(String GG, String MM, String YY, int numberOfDays) throws ParseException {
		DateTool dateTool = new DateTool(DateTool.US_DATETIME_FORMAT);
		Date newDate = null;
		if (MM != null && !"".equals(MM.trim()) && GG != null && !"".equals(GG.trim()) && YY != null && !"".equals(YY.trim())) {
			newDate = dateTool.parse(YY, MM, GG);
			if (numberOfDays > 0) {
				newDate = dateTool.incrementDays(newDate, numberOfDays);
			}
		}
		return newDate;
	}

	public static Double parseImportoAsDouble(String importo) {
		Double newImporto = null;
		if (importo != null && !"".equals(importo.trim())) {
			newImporto = new Double(importo);
		}
		return newImporto;
	}

	public static BigDecimal parseImportoAsBigDecimal(String importo) {
		BigDecimal newImporto = null;
		if (importo != null && !"".equals(importo.trim())) {
			newImporto = new BigDecimal(importo);
		}
		return newImporto;
	}

	public static String createAbiPath(String ABI) {
		if (IbisConfigurationFromDB.getOverriddenAbiDirForCss() != null && !"".equals(IbisConfigurationFromDB.getOverriddenAbiDirForCss())) {
			return IbisConfigurationFromDB.getOverriddenAbiDirForCss() + "/";
		} else {
			if (ABI == null || ABI.equals("")) {
				return "";
			} else {
				return ABI + "/";
			}
		}
	}

	public static void removeSessionAttributes(Class sessionConstants, HttpServletRequest request) throws IllegalArgumentException,
			IllegalAccessException {
		Field[] fields = sessionConstants.getFields();
		HttpSession curHttpSession = request.getSession();
		for (int i = 0; i < fields.length; i++) {
			String value = new String();
			curHttpSession.removeAttribute((String) fields[i].get(value));
			Tracer.info(WebUtility.class.getName(), "removeSessionAttributes", " RIMOSSO ATTRIBUTO " + fields[i].getName());
		}
	}

	// -------------------------------------------------------------------------
	// Ritorna il sessionId della richiesta
	// -------------------------------------------------------------------------
	public static String getSessionId(HttpServletRequest request) {
		String sessionId = getCookieValueByName("sessionId", request);
		if (sessionId == null)
			sessionId = request.getParameter("sessionId");
		return sessionId;
	}

	public static String getCookieValueByName(String cookieName, HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		String name = null;
		String value = null;
		String valueOut = null;
		Cookie c = null;
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				c = cookies[i];
				name = c.getName();
				value = c.getValue();
				if (name.equals(cookieName)) {
					valueOut = value;
					break;
				}
			}
		}
		return valueOut;
	}

	public static void printRequest(HttpServletRequest request) {

		Enumeration enAttr = request.getAttributeNames();
		while (enAttr.hasMoreElements()) {
			String aName = (String) enAttr.nextElement();
			Tracer.debug(WebUtility.class.getName(), " printRequest ", " Attribute name : " + aName + " value : "
					+ request.getAttribute(aName));
		}

		Enumeration enPar = request.getParameterNames();
		while (enPar.hasMoreElements()) {
			String pName = (String) enPar.nextElement();
			Tracer.debug(WebUtility.class.getName(), " printRequest ", " Parameter name : " + pName + " value : "
					+ request.getParameter(pName));
		}
	}
		
}
