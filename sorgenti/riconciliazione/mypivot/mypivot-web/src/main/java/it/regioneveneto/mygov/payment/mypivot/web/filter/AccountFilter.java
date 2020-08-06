/**
 * 
 */
package it.regioneveneto.mygov.payment.mypivot.web.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.regioneveneto.mygov.payment.mypivot.domain.dto.myprofile.TenantDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.myprofile.TenantsDto;
import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.UtenteTO;
import it.regioneveneto.mygov.payment.mypivot.service.EnteService;
import it.regioneveneto.mygov.payment.mypivot.utils.SecurityContext;

/**
 * @author Vallini Giorgio
 * 
 */
@Service("accountFilter")
public class AccountFilter implements Filter {

	/**
	 * 
	 */
	private static Log logger = LogFactory.getLog(AccountFilter.class);

	/**
	 * 
	 */
	@Autowired
	private EnteService enteService;

	@Resource
	private Environment env;

	/**
	 * 
	 */
	public AccountFilter() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		if (!(request instanceof HttpServletRequest))
			throw new ServletException("AccountFilter filter only supports HTTP request");

		UtenteTO utenteTO = SecurityContext.getUtente();
		if (utenteTO == null) {
			chain.doFilter(request, response);
			return;
		}

		if (SecurityContext.getAllEnti() == null ) {
			
			
			synchronized (SecurityContext.class) {
				try{
					//MYPROFILE GET ENTI PER UTENTE 
					RestTemplate restTemplate = new RestTemplate();
					
					List<HttpMessageConverter<?>> converters = restTemplate.getMessageConverters();
					for (Object converter : converters) {
						if (converter instanceof MappingJackson2HttpMessageConverter) {
							MappingJackson2HttpMessageConverter jconverter = (MappingJackson2HttpMessageConverter) converter;

							final ObjectMapper mapper = new ObjectMapper();
							mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
							jconverter.setObjectMapper(mapper);

						}
					}
					
					TenantsDto tenantsDto = restTemplate.getForObject(env.getProperty("myprofile.contextURL") + ":" + env.getProperty("myprofile.portEndpointURL")
							+ "/myProfileServer/rest/tenants/" + utenteTO.getCodFedUserId() + "/" + env.getProperty("myprofile.mypivot.application.code") + ".json", TenantsDto.class);
					if (tenantsDto.getMessage().equals("OK")) {
						List<EnteTO> allEnti = new ArrayList<EnteTO>();
						
						if (tenantsDto.getResultTenants() != null) {
							for (TenantDto tenanaDto : tenantsDto.getResultTenants()) {
								EnteTO enteTO = enteService.getByCodIpaEnte(tenanaDto.getTenantCode());
								if (enteTO == null)
									continue;
								allEnti.add(enteTO);
							}
						}
						
						SecurityContext.setAllEnti(allEnti);
					}else{
						logger.error("Error MyProfile getTenants ["+tenantsDto.getMessage()+"]" );
					}
					
				}
				catch (Exception e){
					logger.error("Error MyProfile getTenants ["+e.getMessage()+"]" );
				}
				
			}
			
			
		}
		chain.doFilter(request, response);
	}

	private void serializeSession() throws IOException {
		//SESSION SERIALIZATION
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = attr.getRequest();
		HttpSession session = request.getSession();

		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(buffer);

		Enumeration<String> names = session.getAttributeNames();
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();

			Object object = session.getAttribute(name);
			oos.writeObject(object);

		}

		oos.close();

		byte[] reqRawData = buffer.toByteArray();
		long reqRawDataLenght = reqRawData.length;
		logger.debug("SESSION OBJECT LENGHT : " + reqRawDataLenght + " Bytes");
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
