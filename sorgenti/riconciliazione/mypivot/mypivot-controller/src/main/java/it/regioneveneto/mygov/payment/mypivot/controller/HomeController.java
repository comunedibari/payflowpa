package it.regioneveneto.mygov.payment.mypivot.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.regioneveneto.mygov.payment.mypivot.domain.dto.DynamicMessageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.MessagesDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.myprofile.RoleDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.myprofile.RolesDto;
import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.OperatoreEnteTipoDovutoTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.UtenteTO;
import it.regioneveneto.mygov.payment.mypivot.service.OperatoreEnteTipoDovutoService;
import it.regioneveneto.mygov.payment.mypivot.utils.Constants;
import it.regioneveneto.mygov.payment.mypivot.utils.SecurityContext;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	@Resource
	private Environment env;
	
	@Autowired
	private OperatoreEnteTipoDovutoService operatoreEnteTipoDovutoService;

	@RequestMapping(method = RequestMethod.GET, value = "/home.html", produces = MediaType.TEXT_HTML_VALUE)
	public ModelAndView home(HttpServletRequest request, HttpServletResponse response) throws Exception {

		ModelAndView mav = new ModelAndView();
		
//		LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
//        localeResolver.setLocale(request, response, Locale.ITALIAN);

		mav.setViewName("home");
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/protected/changeEnte.html", produces = MediaType.TEXT_HTML_VALUE)
	public ModelAndView changeEnte(HttpServletRequest request, HttpServletResponse response, @RequestParam(required = true) String enteToChange,
			@RequestParam(required = true) String redirectUrl) throws Exception {

		ModelAndView mav = new ModelAndView();

		//CONTROLLO SE UTENTE E' ABILITATO PER ENTE RICHIESTP
		List<EnteTO> enteTOs = SecurityContext.getAllEnti();
		boolean found = false;
		for (EnteTO enteTO : enteTOs) {
			if (enteTO.getCodIpaEnte().equals(enteToChange)) {
				found = true;
			}
		}

		if (!found) {
			//ERRORE, tentativo di accedere ad ente non autorizzato
			mav.setViewName("message");

			MessagesDto messagesDto = new MessagesDto();
			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.notAuthorized"));
			mav.addObject("messagesDto", messagesDto);
			return mav;
		}

		SecurityContext.setEnte(enteToChange);
		UtenteTO utenteTO = SecurityContext.getUtente();
		if (utenteTO == null) {
			//ERRORE, tentativo di accedere ad ente non autorizzato
			mav.setViewName("message");

			MessagesDto messagesDto = new MessagesDto();
			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.notAuthorized"));
			mav.addObject("messagesDto", messagesDto);
			return mav;
		}

		//RUOLI UTENTE PER QUESTO ENTE
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
		
		RolesDto rolesDto = restTemplate.getForObject(env.getProperty("myprofile.contextURL") + ":" + env.getProperty("myprofile.portEndpointURL")
				+ "/myProfileServer/rest/roles/" + utenteTO.getCodFedUserId() + "/" + enteToChange + "/" + env.getProperty("myprofile.mypivot.application.code") + ".json", RolesDto.class);
		if (rolesDto.getMessage().equals("OK")) {
			
			//redirectUrl = "protected/visualizza.html";
			
			List<String> roles = new ArrayList<String>();
			for (RoleDto roleDto : rolesDto.getResultRoles()) {
				roles.add(roleDto.getRoleName());
			}
			SecurityContext.setRoles(roles);
			
			List<OperatoreEnteTipoDovutoTO> listaOetdTO = operatoreEnteTipoDovutoService.findActiveByCodFedUserIdAndCodIpaEnte(utenteTO.getCodFedUserId(), enteToChange);
			SecurityContext.setAllOperatoreEnteTipoDovuto(listaOetdTO);
			
			if (roles!=null && roles.size()!=0){
//				if (!roles.contains(Constants.RUOLO_VISUALIZZATORE) && roles.contains(Constants.RUOLO_AMMINISTRATORE)) {
//					mav = new ModelAndView(new RedirectView(request.getContextPath() + "/protected/carica/flussiUpload.html?tipoFlusso=E"));
//					return mav;
//				}
//				if (roles.contains(Constants.RUOLO_VISUALIZZATORE) && !roles.contains(Constants.RUOLO_AMMINISTRATORE)) {
//					mav = new ModelAndView(new RedirectView(request.getContextPath() + "/protected/visualizza.html"));
//					return mav;
//				}
				if (roles.contains(Constants.RUOLO_VISUALIZZATORE)) {
					mav = new ModelAndView(new RedirectView(request.getContextPath() + "/protected/visualizzaCompleta.html"));
					return mav;
				}
			}
			else{
				//ERRORE, tentativo di accedere ad ente non autorizzato
				mav.setViewName("message");

				MessagesDto messagesDto = new MessagesDto();
				messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error"));
				mav.addObject("messagesDto", messagesDto);
				return mav;				
			}
		} else {
			//ERRORE, tentativo di accedere ad ente non autorizzato
			mav.setViewName("message");

			MessagesDto messagesDto = new MessagesDto();
			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error"));
			mav.addObject("messagesDto", messagesDto);
			return mav;
		}
		// comportamento di default nel caso l'utente abbia ruoli ma non RUOLO_VISUALIZZATORE 
		if (StringUtils.isBlank(redirectUrl)) {
			mav.setViewName("home");
		} else {
			mav = new ModelAndView(new RedirectView(request.getContextPath() + "/" + redirectUrl));
		}

		return mav;
	}
	
	@RequestMapping("/privacy.html")
	public ModelAndView privacy(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mav = new ModelAndView();

		mav.setViewName("privacy");

		return mav;
	}

	@RequestMapping("/cookies.html")
	public ModelAndView cookies(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mav = new ModelAndView();

		mav.setViewName("cookies");

		return mav;
	}
}
