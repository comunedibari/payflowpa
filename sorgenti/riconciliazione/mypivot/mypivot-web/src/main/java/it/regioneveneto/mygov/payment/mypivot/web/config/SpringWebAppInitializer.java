package it.regioneveneto.mygov.payment.mypivot.web.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.core.io.ClassPathResource;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class SpringWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {

		// servletContext.setSessionTrackingModes(of(COOKIE));

		ClassPathResource resource = new ClassPathResource("mypivot.properties");
		Properties p = new Properties();
		InputStream inputStream = null;
		try {
			inputStream = resource.getInputStream();
			p.load(inputStream);
			inputStream.close();
		} catch (IOException e) {

		}
		
		
		
		Boolean useDebugFilter = Boolean.parseBoolean(p.getProperty("federa.debugFilter"));
		
		ServletRegistration servletRegistration;
		FilterRegistration filterRegistration;
		
		if (useDebugFilter) {
			filterRegistration = servletContext.addFilter("DebugAccountFilter", org.springframework.web.filter.DelegatingFilterProxy.class);
			filterRegistration.setInitParameter("targetBeanName", "debugAccountFilter");
			filterRegistration.setInitParameter("targetFilterLifecycle", "true");
			filterRegistration.setInitParameter("loginUrl", "/protected/login");
			filterRegistration.setInitParameter("loginLandingPage", "/index.jsp");
			filterRegistration.setInitParameter("logoutUrl", "/protected/logout");
			filterRegistration.setInitParameter("logoutLandingPage", "/index.jsp");
			filterRegistration.setInitParameter("unauthorizedUserLandingPage", "/403.jsp");

//			filterRegistration.setInitParameter("debugUserId", "BLLLGU69R01L736J@regione.veneto.it");
//			filterRegistration.setInitParameter("debugCodiceFiscale", "BLLLGU69R01L736J");
//			filterRegistration.setInitParameter("debugFirstName", "Luigi");
//			filterRegistration.setInitParameter("debugLastName", "Bellio");
//			filterRegistration.setInitParameter("debugEmailAddress", "luigi.bellio@gmail.com");
			
//			filterRegistration.setInitParameter("debugUserId", "TTTGLI52T24D530V@Feltre");
//			filterRegistration.setInitParameter("debugCodiceFiscale", "TTTGLI52T24D530V");
//			filterRegistration.setInitParameter("debugFirstName", "Giulio");
//			filterRegistration.setInitParameter("debugLastName", "Tatto");
//			filterRegistration.setInitParameter("debugEmailAddress", "supporto.mypay@eng.it");

			filterRegistration.setInitParameter("debugUserId", "utente.globale");
			filterRegistration.setInitParameter("debugCodiceFiscale", "UTNGLB52T24D530V");
			filterRegistration.setInitParameter("debugFirstName", "Utente");
			filterRegistration.setInitParameter("debugLastName", "Globale");
			filterRegistration.setInitParameter("debugEmailAddress", "test@test.test");

			filterRegistration.setInitParameter("debugAuthorized", "no");
			filterRegistration.setInitParameter("debugLegalEntity", "fisica");

			filterRegistration.setInitParameter("debugStatoResidenza", "ITALIA");
			filterRegistration.setInitParameter("debugProvinciaResidenza", "BL");
			filterRegistration.setInitParameter("debugComuneResidenza", "FELTRE");
			filterRegistration.setInitParameter("debugIndirizzoResidenza", "VIA MOLAN");
			filterRegistration.setInitParameter("debugCapResidenza", "32032");

			filterRegistration.addMappingForUrlPatterns(null, false, "/protected/*");
		}else{
			servletContext.setInitParameter("keystorePath", "/resources/keystore/keystore.jks");
			servletContext.setInitParameter("keystoreAlias", "signer");
			servletContext.setInitParameter("keystorePassword", "password");
			servletContext.setInitParameter("truststorePath", "/resources/keystore/truststore.jks");
			servletContext.setInitParameter("truststorePassword", "password");
			servletContext.setInitParameter("trustCheckEnabled", "false");
			servletContext.setInitParameter("configurationFile", p.getProperty("federa.mypivot.configurationFile"));
			servletContext.setInitParameter("showDetailInErrorPage", "true");
			servletContext.setInitParameter("metadataConnectionTimeout", "60000");
			servletContext.setInitParameter("metadataFile", "/resources/metadata/metadata.xml");
			servletContext.setInitParameter("metadataFileEncoding", "utf-8");
			servletContext.setInitParameter("entityURLPrefix", p.getProperty("federa.mypivot.entityURLPrefix"));
			servletContext.setInitParameter("icar.inf3.error.returnURL", p.getProperty("federa.mypivot.entityURLPrefix") + "/mypivot");
			servletContext.setInitParameter("language", "it");
			servletContext.setInitParameter("enforceAssertionConditions", "false");
			servletContext.setInitParameter("authorityRegistryMetadataProviderURL", p.getProperty("federa.ar.entityURLPrefix") + "/ar/metadata");
			servletContext.setInitParameter("authorityRegistrySubjectNameQualifier", p.getProperty("federa.ar.entityURLPrefix") + "/ar");
			servletContext.setInitParameter("identifyingAttribute", "CodiceFiscale");
			
			servletRegistration = servletContext.addServlet("AssertionConsumerService",
					it.cefriel.icar.inf3.web.servlet.AssertionConsumerService.class);
			servletRegistration.setInitParameter("authnFailedPage", "/resources/authnFailed.jsp");
			servletRegistration.addMapping("/AssertionConsumerService");
			
			servletRegistration = servletContext.addServlet("MetadataPublisherServlet", it.cefriel.icar.inf3.web.servlet.MetadataPublisherServlet.class);
			servletRegistration.addMapping("/MetadataPublisherServlet", "/metadata");
			
			/*
			 * AuthenticationFilter
			 */
			filterRegistration = servletContext.addFilter("INF-3 Authentication Filter",
					it.cefriel.icar.inf3.web.filters.AuthenticationFilter.class);
			filterRegistration.setInitParameter("forwardBinding", "HTTP-Redirect");
			filterRegistration.setInitParameter("returnBinding", "HTTP-POST");
			filterRegistration.setInitParameter("localProxyMetadataProviderURL", p.getProperty("federa.gw.entityURLPrefix") + "/gw/metadata");
			filterRegistration.setInitParameter("postAuthnRequestPage", "/resources/PostAuthnRequest.jsp");
			filterRegistration.setInitParameter("proxyCount", "2");
			filterRegistration.setInitParameter("nameIDFormat", "unspecified");
			filterRegistration.addMappingForUrlPatterns(null, false, "/protected/*");
			
			/*
			 * FederaAccountFilter
			 */
			filterRegistration = servletContext.addFilter("FederaAccountFilter", org.springframework.web.filter.DelegatingFilterProxy.class);
			filterRegistration.setInitParameter("targetBeanName", "federaAccountFilter");
			filterRegistration.setInitParameter("targetFilterLifecycle", "true");
			filterRegistration.setInitParameter("loginUrl", "/protected/login");
			filterRegistration.setInitParameter("loginLandingPage", "/index.jsp");
			filterRegistration.setInitParameter("logoutUrl", "/protected/logout");
			filterRegistration.setInitParameter("logoutLandingPage", "/index.jsp");
			filterRegistration.setInitParameter("unauthorizedUserLandingPage", "/403.jsp");
			filterRegistration.addMappingForUrlPatterns(null, false, "/protected/*");	
		}
		
		
		
		/*
		 * AccountFilter
		 */
		filterRegistration = servletContext.addFilter("AccountFilter", org.springframework.web.filter.DelegatingFilterProxy.class);
		filterRegistration.setInitParameter("targetBeanName", "accountFilter");
		filterRegistration.setInitParameter("targetFilterLifecycle", "true");
		filterRegistration.addMappingForUrlPatterns(null, false, "/*");
		
		/*
		 * WS Servlet
		 */
		servletRegistration = servletContext
				.addServlet(
						"cxf", org.apache.cxf.transport.servlet.CXFServlet.class);
		servletRegistration
				.addMapping("/cxf", "/services/*");


		servletContext.addListener(RequestContextListener.class);

		super.onStartup(servletContext);

	}

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { ApplicationContextConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return null;
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	@Override
	protected Filter[] getServletFilters() {

		HiddenHttpMethodFilter hiddenHttpMethodFilter = new HiddenHttpMethodFilter();

		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);

		return new Filter[] { hiddenHttpMethodFilter, characterEncodingFilter };
	}

}