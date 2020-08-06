package it.nch.fwk.fo.web.action.log;

import java.util.Enumeration;
import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HttpDebugger {

    private static StringBuffer printAttributes(HttpServletRequest request) {
        StringBuffer log = new StringBuffer();

        log.append("REQUEST ATTRIBUTES:\r\n");
        Enumeration attributes = request.getAttributeNames();

        if ((attributes == null) || (!attributes.hasMoreElements())) {
            log.append("No Attributes found.\r\n");
        } else {
            while (attributes.hasMoreElements()) {
                String attributeName = (String) attributes.nextElement();
                Object value = request.getAttribute(attributeName);
                log.append(attributeName + " = <" + value + ">\r\n");

            }
        }
        log.append("\r\n");
        return log;
    }

    private static StringBuffer printHeaders(HttpServletRequest request) {
        StringBuffer log = new StringBuffer();

        log.append("\r\n");
        log.append("REQUEST HEADERS:\r\n");
        Enumeration headers = request.getHeaderNames();
        if ((headers == null) || (!headers.hasMoreElements())) {
            log.append("No Headers found.\r\n");
        } else {
            while (headers.hasMoreElements()) {
                String header = (String) headers.nextElement();
                Enumeration values = request.getHeaders(header);

                while (values.hasMoreElements()) {
                    String value = (String) values.nextElement();
                    log.append(header + " = <" + value + ">\r\n");
                }
            }
        }
        log.append("\r\n");
        return log;
    }

    private static StringBuffer printParameters(HttpServletRequest request) {
        StringBuffer log = new StringBuffer();

        log.append("\r\n");
        log.append("REQUEST PARAMETERS:\r\n");
        Enumeration parameters = request.getParameterNames();
        if ((parameters == null) || (!parameters.hasMoreElements())) {
            log.append("No Parameters found.\r\n");
        } else {
            while (parameters.hasMoreElements()) {
                String param = (String) parameters.nextElement();
                String[] values = request.getParameterValues(param);
                for (int i = 0; i < values.length; i++) {
                    log.append(param + " = <" + values[i] + ">\r\n");
                }
            }
        }
        log.append("\r\n");
        return log;
    }

    private static StringBuffer printCookies(HttpServletRequest request) {
        StringBuffer log = new StringBuffer();

        log.append("\r\n");
        log.append("REQUEST COOKIES:\r\n");
        Cookie[] cookies = request.getCookies();
        int numCookies = 0;
        if (cookies != null) {
            numCookies = cookies.length;
        }
        if (cookies == null || numCookies == 0) {
            log.append("No Cookies found.\r\n");
        } else {
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                log.append(cookie.getName() + " = <" + cookie.getValue() + ">\r\n");
            }
        }
        log.append("\r\n");
        return log;
    }

    private static StringBuffer printRequestInfo(HttpServletRequest request) {
        StringBuffer log = new StringBuffer();
        log.append("\r\n");
        log.append("REQUEST INFO:\r\n");
        log.append("Protocol: " + request.getProtocol() + "\r\n ");
        log.append("ServerName: " + request.getServerName() + "\r\n ");
        log.append("ServerPort: " + request.getServerPort() + "\r\n ");
        log.append("Secure: " + request.isSecure() + "\r\n ");
        log.append("ContextPath: " + request.getContextPath() + "\r\n ");
        log.append("ServletPath: " + request.getServletPath() + "\r\n ");
        log.append("QueryString: " + request.getQueryString() + "\r\n ");
        log.append("PathInfo: " + request.getPathInfo() + "\r\n ");
        log.append("PathTranslated: " + request.getPathTranslated() + "\r\n ");
        log.append("RequestURI: " + request.getRequestURI() + "\r\n ");
        log.append("AuthType: " + request.getAuthType() + "\r\n ");
        log.append("ContentType: " + request.getContentType() + "\r\n ");
        log.append("Locale: " + request.getLocale() + "\r\n ");
        log.append("Method: " + request.getMethod() + "\r\n ");
        log.append("Session: " + request.getSession() + "\r\n ");
        log.append("RequestedSessionId: " + request.getRequestedSessionId() + "\r\n ");
        log.append("RequestedSessionIdFromCookie: " + request.isRequestedSessionIdFromCookie() + "\r\n ");
        log.append("RequestedSessionIdFromURL: " + request.isRequestedSessionIdFromURL() + "\r\n ");
        log.append("RemoteUser: " + request.getRemoteUser() + "\r\n ");
        log.append("RemoteAddr: " + request.getRemoteAddr() + "\r\n ");
        log.append("RemoteHost: " + request.getRemoteHost() + "\r\n ");
        log.append("\r\n");
        return log;
    }

    /**Logs all <code>HttpServletRequest</code> information.
     * @param request the current <code>HttpServletRequest</code>
     * @param log the logger
     */
    public static String traceHttpRequest(HttpServletRequest request) {
        StringBuffer log = new StringBuffer();
        try {
            log.append(printRequestInfo(request));
            log.append(printHeaders(request));
            log.append(printAttributes(request));
            log.append(printParameters(request));
            log.append(printCookies(request));
        } catch (Throwable e) {
            log.append("[HttpDebugger::traceHttpRequest] error recovering request information ->").append(
                    e.getMessage());
        }
        return log.toString();
    }

    /**Logs all <code>HttpServletResponse</code> information.
     * @param response the current <code>HttpServletResponse</code>
     * @param log the logger
     */
    public static String traceHttpResponse(HttpServletResponse response) {
        StringBuffer log = new StringBuffer();
        try {

            log.append("/r/n");
            log.append("RESPONSE INFO: /r/n");
            log.append("buffer size: " + response.getBufferSize() + "/r/n");
            log.append("character encoding:" + response.getCharacterEncoding() + "/r/n");
            log.append(printLocale(response.getLocale()));
            log.append("");

        } catch (Throwable e) {
            log.append("[HttpDebugger::traceHttpResponse] error recovering response information ->").append(
                    e.getMessage());
        }
        return log.toString();
    }

    private static StringBuffer printLocale(Locale l) {
        StringBuffer log = new StringBuffer();
        log.append("/r/n");
        log.append("LOCALE INFO:/r/n");
        log.append("country: " + l.getCountry() + "/r/n");
        log.append("language: " + l.getLanguage() + "/r/n");
        log.append("variant: " + l.getVariant() + "/r/n");
        log.append("ISO:/r/n");
        log.append("ISO country: " + l.getISO3Country() + "/r/n");
        log.append("ISO language: " + l.getISO3Language() + "/r/n");
        log.append("display:/r/n");
        log.append("display country: " + l.getDisplayCountry() + "/r/n");
        log.append("display language: " + l.getDisplayLanguage() + "/r/n");
        log.append("display name: " + l.getDisplayName() + "/r/n");
        log.append("display variant: " + l.getDisplayVariant() + "/r/n");
        log.append("/r/n");
        return log;
    }
}