package it.tasgroup.rest.utils;


import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.StringTokenizer;


public class BasicAuthenticationFilter implements Filter {
    /**
     * Logger
     */
    private static final Logger LOGGER = LogManager.getLogger(BasicAuthenticationFilter.class);

    // TODO questo è solo a scopo di DEMO !!! : le password non dovrebbero stare qui inoltre dovrebbero essere diverse a seconda dall'utente che si collega
    // TODO a regime SARA NECESSARIO passare meccanismo di autenticazione sicuro (per es. OAuth) o mettere in sicurezza questo (Basic Authentication)
    private String username = "Raa3JbhQCwZ68yhu";
    private String password = "5ZbvkkKny38X39ba";

    private String realm = "Protected";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        final String requestMethod = httpServletRequest.getMethod();
        System.out.println("requestMethod = " + requestMethod);

        boolean proceed = false;
        if (requestMethod.equals("OPTIONS")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }


        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            StringTokenizer st = new StringTokenizer(authHeader);
            if (st.hasMoreTokens()) {
                String basic = st.nextToken();

                if (basic.equalsIgnoreCase("Basic")) {
                    try {
                        String credentials = new String(Base64.decodeBase64(st.nextToken()), "UTF-8");
                        int p = credentials.indexOf(":");
                        if (p != -1) {
                            String _username = credentials.substring(0, p).trim();
                            String _password = credentials.substring(p + 1).trim();

                            if (!username.equals(_username) || !password.equals(_password)) {
                                unauthorized(response, "Bad credentials");
                                return;
                            }

                            filterChain.doFilter(servletRequest, servletResponse);
                        } else {
                            unauthorized(response, "Invalid authentication token");
                        }
                    } catch (UnsupportedEncodingException e) {
                        throw new Error("Couldn't retrieve authentication", e);
                    }
                }
            }
        } else {
            unauthorized(response);
        }
    }


    @Override
    public void destroy() {

    }


    private void unauthorized(HttpServletResponse response, String message) throws IOException {
        response.setHeader("WWW-Authenticate", "Basic realm=\"" + realm + "\"");
//        response.sendError(401, message);
        response.setStatus  (Response.Status.UNAUTHORIZED.getStatusCode());
        response.getWriter().println("{}");
    }

    private void unauthorized(HttpServletResponse response) throws IOException {
        unauthorized(response, "Unauthorized");
    }

}
