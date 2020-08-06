/**
 * 
 */
package it.regioneveneto.mygov.payment.mypivot.web.filter;

import java.io.IOException;
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

import it.cefriel.icar.inf3.ICARConstants;
import it.cefriel.icar.inf3.web.beans.AuthenticationSessionBean;
import it.regioneveneto.mygov.payment.mypivot.domain.po.Utente;
import it.regioneveneto.mygov.payment.mypivot.domain.to.UtenteTO;
import it.regioneveneto.mygov.payment.mypivot.service.EnteService;
import it.regioneveneto.mygov.payment.mypivot.service.SecurityService;
import it.regioneveneto.mygov.payment.mypivot.service.utils.ModelMapperUtil;
import it.regioneveneto.mygov.payment.mypivot.utils.SecurityContext;

/**
 * @author Luigi Bellio
 * @author Pier Paolo Monaco
 * 
 */
@Service("federaAccountFilter")
public class FederaAccountFilter implements Filter {

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
	
	private static Log logger = LogFactory.getLog(FederaAccountFilter.class);
	
	private String loginUrl;
	private String loginLandingPage;
	private String logoutUrl;
	private String logoutLandingPage;
	private String unauthorizedUserLandingPage;

	@Autowired
	private SecurityService securityService;
	@Autowired
	private ModelMapperUtil modelMapperUtil;

	/**
	 * 
	 */
	@Autowired
	private EnteService enteService;
	
	/**
	 * 
	 */
	public FederaAccountFilter() {
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

		Map serviceContextMap = (Map) session.getAttribute(ICARConstants.SERVICE_CONTEXT_MAP);

		String serviceURLPrefix = (String) session.getAttribute(ICARConstants.SERVICE_URL_PREFIX);

		AuthenticationSessionBean authBean = null;

		if ((serviceContextMap != null) && StringUtils.isNotBlank(serviceURLPrefix))
			authBean = (AuthenticationSessionBean) serviceContextMap.get(serviceURLPrefix);

		if (authBean == null) {
			logger.error("Federa authentication data not available");

			request.setAttribute("errorMessage", "Federa authentication data not available");

			request.getRequestDispatcher("/resources/error.jsp").forward(request, response);

			return;
		}

		String requestUri = httpRequest.getRequestURI();
		if (requestUri.indexOf(httpRequest.getContextPath() + this.logoutUrl) != -1) {
			httpResponse.sendRedirect(httpRequest.getContextPath() + this.logoutLandingPage);

			session.invalidate();

			return;
		}

		Map<String, List<String>> attributesMap = (Map<String, List<String>>) session.getAttribute(FEDERA_PROFILE_SESSION_KEY);
		if ((attributesMap == null) || (requestUri.indexOf(httpRequest.getContextPath() + this.loginUrl) != -1)) {
			attributesMap = authBean.getAttributesMap();
			Assert.notNull(attributesMap, "'attributesMap' Federa profile map not valid");
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

			List<String> trustLevels = attributesMap.get("trustLevel");
			Assert.isTrue((trustLevels != null) && (trustLevels.size() == 1) && StringUtils.isNotBlank(trustLevels.get(0)),
					"'trustLevel' Federa profile attribute not valid");
			String trustLevel = trustLevels.get(0);

			if (!"Alto".equals(trustLevel)) {
				logger.debug("user [" + codiceFiscale + "] not trusted");

				httpResponse.sendRedirect(httpRequest.getContextPath() + this.unauthorizedUserLandingPage);

				session.invalidate();

				return;
			}

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
