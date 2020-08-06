/**
 * 
 */
package it.regioneveneto.mygov.payment.mypivot.web.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import it.regioneveneto.mygov.payment.mypivot.domain.po.Utente;
import it.regioneveneto.mygov.payment.mypivot.domain.to.UtenteTO;
import it.regioneveneto.mygov.payment.mypivot.service.EnteService;
import it.regioneveneto.mygov.payment.mypivot.service.SecurityService;
import it.regioneveneto.mygov.payment.mypivot.service.utils.ModelMapperUtil;
import it.regioneveneto.mygov.payment.mypivot.utils.SecurityContext;

/**
 * @author Giorgio Vallini
 */
@Service("debugAccountFilter")
public class DebugAccountFilter implements Filter {

	private static final String FEDERA_DEFAULT_LOGIN_URL = "/protected/login";
	private static final String FEDERA_LOGIN_URL_PARAMETER = "loginUrl";
	private static final String FEDERA_DEFAULT_LOGIN_LANDING_PAGE = "/index.jsp";
	private static final String FEDERA_LOGIN_LANDING_PAGE_PARAMETER = "loginLandingPage";
	
	protected static final String FEDERA_PROFILE_SESSION_KEY = "it.regioneveneto.mygov.payment.pa.web.filter.federaProfile";
	
	private static final String FEDERA_DEFAULT_LOGOUT_URL = "/protected/logout";
	private static final String FEDERA_LOGOUT_URL_PARAMETER = "logoutUrl";
	private static final String FEDERA_DEFAULT_LOGOUT_LANDING_PAGE = "/index.jsp";
	private static final String FEDERA_LOGOUT_LANDING_PAGE_PARAMETER = "logoutLandingPage";
	private static final String FEDERA_DEFAULT_UNAUTHORIZED_USER_LANDING_PAGE = "/403.jsp";
	private static final String FEDERA_UNAUTHORIZED_USER_LANDING_PAGE_PARAMETER = "unauthorizedUserLandingPage";
	
	private static final String FEDERA_DEBUG_USER_ID_PARAMETER = "debugUserId";
	private static final String FEDERA_DEBUG_CODICE_FISCALE_PARAMETER = "debugCodiceFiscale";
	private static final String FEDERA_DEBUG_FIRST_NAME_PARAMETER = "debugFirstName";
	private static final String FEDERA_DEBUG_LAST_NAME_PARAMETER = "debugLastName";
	private static final String FEDERA_DEBUG_EMAIL_ADDRESS_PARAMETER = "debugEmailAddress";
	private static final String FEDERA_DEBUG_AUTHORIZED_PARAMETER = "debugAuthorized";
	private static final String FEDERA_DEBUG_LEGAL_ENTITY_PARAMETER = "debugLegalEntity";
	
	private static final String FEDERA_DEBUG_STATO_RESIDENZA_PARAMETER = "debugStatoResidenza";
	private static final String FEDERA_DEBUG_PROVINCIA_RESIDENZA_PARAMETER = "debugProvinciaResidenza";
	private static final String FEDERA_DEBUG_COMUNE_RESIDENZA_PARAMETER = "debugComuneResidenza";
	private static final String FEDERA_DEBUG_INDIRIZZO_RESIDENZA_PARAMETER = "debugIndirizzoResidenza";
	private static final String FEDERA_DEBUG_CAP_RESIDENZA_PARAMETER = "debugCapResidenza";
	
	private static Log logger = LogFactory.getLog(DebugAccountFilter.class);
	
	private String loginUrl;
	private String loginLandingPage;
	private String logoutUrl;
	private String logoutLandingPage;
	private String unauthorizedUserLandingPage;
	
	
	private String debugUserId;
	private String debugCodiceFiscale;
	private String debugFirstName;
	private String debugLastName;
	private String debugEmailAddress;
	private String debugAuthorized;
	private String debugLegalEntity;
	
	private String debugStatoResidenza;
	private String debugProvinciaResidenza;
	private String debugComuneResidenza;
	private String debugIndirizzoResidenza;
	private String debugCapResidenza;
	

	@Autowired
	private SecurityService securityService;

	/**
	 * 
	 */
	@Autowired
	private EnteService enteService;
	
	@Autowired
	private ModelMapperUtil modelMapperUtil;
	/**
	 * 
	 */
	public DebugAccountFilter() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.loginUrl = filterConfig.getInitParameter(FEDERA_LOGIN_URL_PARAMETER);
		if (StringUtils.isBlank(this.loginUrl))
			this.loginUrl = FEDERA_DEFAULT_LOGIN_URL;

		this.loginLandingPage = filterConfig.getInitParameter(FEDERA_LOGIN_LANDING_PAGE_PARAMETER);
		if (StringUtils.isBlank(this.loginLandingPage))
			this.loginLandingPage = FEDERA_DEFAULT_LOGIN_LANDING_PAGE;

		this.logoutUrl = filterConfig.getInitParameter(FEDERA_LOGOUT_URL_PARAMETER);
		if (StringUtils.isBlank(this.logoutUrl))
			this.logoutUrl = FEDERA_DEFAULT_LOGOUT_URL;

		this.logoutLandingPage = filterConfig.getInitParameter(FEDERA_LOGOUT_LANDING_PAGE_PARAMETER);
		if (StringUtils.isBlank(this.logoutLandingPage))
			this.logoutLandingPage = FEDERA_DEFAULT_LOGOUT_LANDING_PAGE;

		this.unauthorizedUserLandingPage = filterConfig.getInitParameter(FEDERA_UNAUTHORIZED_USER_LANDING_PAGE_PARAMETER);
		if (StringUtils.isBlank(this.unauthorizedUserLandingPage))
			this.unauthorizedUserLandingPage = FEDERA_DEFAULT_UNAUTHORIZED_USER_LANDING_PAGE;
		
		
		this.debugUserId = filterConfig.getInitParameter(FEDERA_DEBUG_USER_ID_PARAMETER);
		this.debugCodiceFiscale = filterConfig.getInitParameter(FEDERA_DEBUG_CODICE_FISCALE_PARAMETER);
		this.debugFirstName = filterConfig.getInitParameter(FEDERA_DEBUG_FIRST_NAME_PARAMETER);
		this.debugLastName = filterConfig.getInitParameter(FEDERA_DEBUG_LAST_NAME_PARAMETER);
		this.debugEmailAddress = filterConfig.getInitParameter(FEDERA_DEBUG_EMAIL_ADDRESS_PARAMETER);
		this.debugAuthorized = filterConfig.getInitParameter(FEDERA_DEBUG_AUTHORIZED_PARAMETER);
		this.debugLegalEntity = filterConfig.getInitParameter(FEDERA_DEBUG_LEGAL_ENTITY_PARAMETER);
		
		this.debugStatoResidenza = filterConfig.getInitParameter(FEDERA_DEBUG_STATO_RESIDENZA_PARAMETER);
		this.debugProvinciaResidenza = filterConfig.getInitParameter(FEDERA_DEBUG_PROVINCIA_RESIDENZA_PARAMETER);
		this.debugComuneResidenza = filterConfig.getInitParameter(FEDERA_DEBUG_COMUNE_RESIDENZA_PARAMETER);
		this.debugIndirizzoResidenza = filterConfig.getInitParameter(FEDERA_DEBUG_INDIRIZZO_RESIDENZA_PARAMETER);
		this.debugCapResidenza = filterConfig.getInitParameter(FEDERA_DEBUG_CAP_RESIDENZA_PARAMETER);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if (!(request instanceof HttpServletRequest))
			throw new ServletException("FederaAccountFilter filter only supports HTTP request");

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession(true);

		String requestUri = httpRequest.getRequestURI();
		if (requestUri.indexOf(httpRequest.getContextPath() + this.logoutUrl) != -1) {
			httpResponse.sendRedirect(httpRequest.getContextPath() + this.logoutLandingPage);

			session.invalidate();

			return;
		}

		Map<String, List<String>> attributesMap = (Map<String, List<String>>) session.getAttribute(FEDERA_PROFILE_SESSION_KEY);
		if ((attributesMap == null) || (requestUri.indexOf(httpRequest.getContextPath() + this.loginUrl) != -1)) {
			
			attributesMap = new HashMap<String, List<String>>();
			attributesMap.put("userid", Arrays.asList(this.debugUserId));
			attributesMap.put("CodiceFiscale", Arrays.asList(this.debugCodiceFiscale));
			attributesMap.put("firstname", Arrays.asList(this.debugFirstName));
			attributesMap.put("lastname", Arrays.asList(this.debugLastName));
			attributesMap.put("emailAddress", Arrays.asList(this.debugEmailAddress));
			attributesMap.put("authorized", Arrays.asList(this.debugAuthorized));
			attributesMap.put("legalEntity", Arrays.asList(this.debugLegalEntity));
			
			attributesMap.put("statoResidenza", Arrays.asList(this.debugStatoResidenza));
			attributesMap.put("provinciaResidenza", Arrays.asList(this.debugProvinciaResidenza));
			attributesMap.put("comuneResidenza", Arrays.asList(this.debugComuneResidenza));
			attributesMap.put("indirizzoResidenza", Arrays.asList(this.debugIndirizzoResidenza));
			attributesMap.put("capResidenza", Arrays.asList(this.debugCapResidenza));

			logger.debug(attributesMap);

			List<String> userids = attributesMap.get("userid");
			Assert.isTrue((userids != null) && (userids.size() == 1) && StringUtils.isNotBlank(userids.get(0)), "'userid' Federa profile attribute not valid");
			String userid = userids.get(0);

			List<String> codiceFiscales = attributesMap.get("CodiceFiscale");
			Assert.isTrue((codiceFiscales != null) && (codiceFiscales.size() == 1) && StringUtils.isNotBlank(codiceFiscales.get(0)),
					"'CodiceFiscale' Federa profile attribute not valid");
			String codiceFiscale = codiceFiscales.get(0);

			List<String> firstNames = attributesMap.get("firstname");
			Assert.isTrue((firstNames != null) && (firstNames.size() == 1) && StringUtils.isNotBlank(firstNames.get(0)),
					"'firstname' Federa profile attribute not valid");
			String firstName = firstNames.get(0);

			List<String> lastNames = attributesMap.get("lastname");
			Assert.isTrue((lastNames != null) && (lastNames.size() == 1) && StringUtils.isNotBlank(lastNames.get(0)),
					"'lastname' Federa profile attribute not valid");
			String lastName = lastNames.get(0);

			List<String> emailAddresses = attributesMap.get("emailAddress");
			Assert.isTrue((emailAddresses != null) && (emailAddresses.size() == 1) && StringUtils.isNotBlank(emailAddresses.get(0)),
					"'emailAddress' Federa profile attribute not valid");
			String emailAddress = emailAddresses.get(0);

			List<String> authorizeds = attributesMap.get("authorized");
			Assert.isTrue((authorizeds != null) && (authorizeds.size() == 1) && StringUtils.isNotBlank(authorizeds.get(0)),
					"'authorized' Federa profile attribute not valid");
			boolean authorized = "true".equals(authorizeds.get(0));

			List<String> legalEntities = attributesMap.get("legalEntity");
			Assert.isTrue((legalEntities != null) && (legalEntities.size() == 1) && StringUtils.isNotBlank(legalEntities.get(0)),
					"'legalEntity' Federa profile attribute not valid");
			String legalEntity = legalEntities.get(0);

//			List<String> trustLevels = attributesMap.get("trustLevel");
//			Assert.isTrue((trustLevels != null) && (trustLevels.size() == 1) && StringUtils.isNotBlank(trustLevels.get(0)),
//					"'trustLevel' Federa profile attribute not valid");
//			String trustLevel = trustLevels.get(0);
//
//			if (!"Alto".equals(trustLevel)) {
//				logger.debug("user [" + codiceFiscale + "] not trusted");
//
//				httpResponse.sendRedirect(httpRequest.getContextPath() + this.unauthorizedUserLandingPage);
//
//				session.invalidate();
//
//				return;
//			}

			Utente utente = null;

			try {
				utente = this.securityService.getUtenteByCodFedUserId(userid);
				if (utente == null) {
					//					utente = this.securityService.insertUtente(userid, codiceFiscale, authorized, emailAddress, firstName, lastName, legalEntity);

					List<String> provinciaResidenzas = attributesMap.get("provinciaResidenza");
					String provinciaResidenza = null;
					if ((provinciaResidenzas != null) && (provinciaResidenzas.size() == 1) && StringUtils.isNotBlank(provinciaResidenzas.get(0)))
						provinciaResidenza = provinciaResidenzas.get(0);

					List<String> statoResidenzas = attributesMap.get("statoResidenza");
					String statoResidenza = null;
					if ((statoResidenzas != null) && (statoResidenzas.size() == 1) && StringUtils.isNotBlank(statoResidenzas.get(0)))
						statoResidenza = statoResidenzas.get(0);

					List<String> comuneResidenzas = attributesMap.get("comuneResidenza");
					String comuneResidenza = null;
					if ((comuneResidenzas != null) && (comuneResidenzas.size() == 1) && StringUtils.isNotBlank(comuneResidenzas.get(0)))
						comuneResidenza = comuneResidenzas.get(0);

					List<String> indirizzoResidenzas = attributesMap.get("indirizzoResidenza");
					String indirizzoResidenza = null;
					if ((indirizzoResidenzas != null) && (indirizzoResidenzas.size() == 1) && StringUtils.isNotBlank(indirizzoResidenzas.get(0)))
						indirizzoResidenza = indirizzoResidenzas.get(0);

					List<String> capResidenzas = attributesMap.get("capResidenza");
					String capResidenza = null;
					if ((capResidenzas != null) && (capResidenzas.size() == 1) && StringUtils.isNotBlank(capResidenzas.get(0)))
						capResidenza = capResidenzas.get(0);

					utente = this.securityService.insertUtente(userid, codiceFiscale, emailAddress, firstName, lastName);

					logger.debug("user [" + codiceFiscale + "] provisioned");
				} else
					utente = this.securityService.updateUtenteByCodFedUserId(userid, codiceFiscale, emailAddress, firstName, lastName);
			} catch (Exception ex) {
				logger.error("error provisioning user [" + codiceFiscale + "]", ex);

				request.setAttribute("errorMessage", "Error provisioning user [" + codiceFiscale + "]");

				request.getRequestDispatcher("/resources/error.jsp").forward(request, response);

				return;
			}

			session.setAttribute(FEDERA_PROFILE_SESSION_KEY, attributesMap);
			
			UtenteTO utenteTO = modelMapperUtil.map(utente, UtenteTO.class);

			SecurityContext.setUtente(utenteTO);
		}

		if (requestUri.indexOf(httpRequest.getContextPath() + this.loginUrl) != -1) {
			httpResponse.sendRedirect(httpRequest.getContextPath() + this.loginLandingPage);

			return;
		}

		chain.doFilter(request, response);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
	}
}
