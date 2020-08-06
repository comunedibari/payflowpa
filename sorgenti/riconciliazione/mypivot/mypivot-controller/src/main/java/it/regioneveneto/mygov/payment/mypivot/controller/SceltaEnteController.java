/**
 * 
 */
package it.regioneveneto.mygov.payment.mypivot.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import it.regioneveneto.mygov.payment.mypivot.controller.command.SceltaEnteCommand;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.DynamicMessageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.MessagesDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.PageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO;
import it.regioneveneto.mygov.payment.mypivot.service.AccertamentoService;
import it.regioneveneto.mygov.payment.mypivot.service.EnteService;
import it.regioneveneto.mygov.payment.mypivot.utils.SecurityContext;

/**
 * @author Giorgio Vallini
 */
@Controller
@RequestMapping("/protected")
public class SceltaEnteController {

	private static Log log = LogFactory.getLog(SceltaEnteController.class);

	@Resource
	private Environment env;
	
	@Autowired
	private EnteService enteService;
	
	public SceltaEnteController() {
		super();
	}

	/*
	@RequestMapping(value = { "/sceltaEnte.html" }, method = RequestMethod.POST)
	public ModelAndView showEnti(
			@ModelAttribute("sceltaEnteCommand") SceltaEnteCommand sceltaEnteCommand,
			BindingResult result, SessionStatus status,
			HttpServletRequest request) {

		ModelAndView mav = new ModelAndView();

		PageDto<EnteTO> entiDtoPage = enteService.getEntePage(
				sceltaEnteCommand.getFullTextSearch(),
				sceltaEnteCommand.getPage(), sceltaEnteCommand.getPageSize(),
				Direction.DESC, "deNomeEnte");

		mav.setViewName("sceltaEnte");
		mav.addObject("entiDtoPage", entiDtoPage);

		// save session filter data
		request.getSession().setAttribute(SessionVariables.SCELTA_ENTE_PG,
				sceltaEnteCommand.getPage());
		request.getSession().setAttribute(SessionVariables.SCELTA_ENTE_PG_SIZE,
				sceltaEnteCommand.getPageSize());
		request.getSession().setAttribute(
				SessionVariables.SCELTA_ENTE_F_SEARCH,
				sceltaEnteCommand.getFullTextSearch());

		return mav;
	}
	*/

	@RequestMapping(value = { "/sceltaEnte.html" }, method = RequestMethod.GET)
	public ModelAndView showDebiti(HttpServletRequest request,
			@RequestParam(required = false) Boolean forceClear,
			@RequestParam(required = false) String pg,
			@RequestParam(required = false) String pgSize,
			@RequestParam(required = false) String fSearch,
			@RequestParam(required = false) String redirectUrl) {

		ModelAndView mav = new ModelAndView();

		SceltaEnteCommand sceltaEnteCommand = new SceltaEnteCommand();
		MessagesDto messagesDto = new MessagesDto();

		/*
		if (forceClear != null && forceClear) {
			request.getSession().removeAttribute(
					SessionVariables.SCELTA_ENTE_PG);
			request.getSession().removeAttribute(
					SessionVariables.SCELTA_ENTE_PG_SIZE);
			request.getSession().removeAttribute(
					SessionVariables.SCELTA_ENTE_F_SEARCH);
		}
		setFilters(request, pg, pgSize, fSearch, sceltaEnteCommand);
		PageDto<EnteTO> entiDtoPage = enteService.getEntePage(
				sceltaEnteCommand.getFullTextSearch(),
				sceltaEnteCommand.getPage(), sceltaEnteCommand.getPageSize(),
				Direction.DESC, "deNomeEnte");
		*/
		
		List<EnteTO> enteTOs = SecurityContext.getAllEnti();
		
		if (enteTOs!=null){
			PageDto<EnteTO> entiDtoPage = new PageDto<EnteTO>();
			for (EnteTO ente : enteTOs) {
				String logoEnte = enteService.getLogoEnteByCodIpaEnte(ente.getCodIpaEnte());
				ente.setDeLogoEnte(logoEnte);
			}
			entiDtoPage.setList(enteTOs);
			entiDtoPage.setTotalRecords(enteTOs.size());
			mav.addObject("entiDtoPage", entiDtoPage);
		}
		else{
			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.myProfile"));
		}


		mav.setViewName("sceltaEnte");

		mav.addObject("messagesDto", messagesDto);
		mav.addObject(sceltaEnteCommand);

		if (StringUtils.isBlank(redirectUrl)) {
			mav.addObject("redirectUrl", "home.html");
		} else {
			mav.addObject("redirectUrl", redirectUrl);
		}

		return mav;
	}

	/*
	private void setFilters(HttpServletRequest request, String pg,
			String pgSize, String fSearch, SceltaEnteCommand sceltaEnteCommand) {

		Object session_pg = request.getSession().getAttribute(
				SessionVariables.SCELTA_ENTE_PG);
		Object session_pg_size = request.getSession().getAttribute(
				SessionVariables.SCELTA_ENTE_PG_SIZE);
		Object session_f_search = request.getSession().getAttribute(
				SessionVariables.SCELTA_ENTE_F_SEARCH);

		// PAGE
		setFilter_page(request, pg, session_pg, sceltaEnteCommand);

		// PAGE SIZE
		setFilter_pageSize(request, pgSize, session_pg_size, sceltaEnteCommand);

		// FULL TEXT SEARCH
		setFilter_fSearch(request, fSearch, session_f_search, sceltaEnteCommand);

	}
	
	
	private void setFilter_page(HttpServletRequest request, String request_pg,
			Object session_pg, SceltaEnteCommand sceltaEnteCommand) {
		try {
			if (request_pg == null)
				sceltaEnteCommand.setPage(Integer.parseInt(session_pg
						.toString()));
			else {
				sceltaEnteCommand.setPage(Integer.parseInt(request_pg));
			}
		} catch (NullPointerException e) {
			sceltaEnteCommand.setPage(0);
		} catch (NumberFormatException e) {
			sceltaEnteCommand.setPage(0);
		}
		request.getSession().setAttribute(SessionVariables.SCELTA_ENTE_PG,
				sceltaEnteCommand.getPage());
	}

	private void setFilter_pageSize(HttpServletRequest request,
			String request_pgSize, Object session_pg_size,
			SceltaEnteCommand sceltaEnteCommand) {
		try {
			if (request_pgSize == null)
				sceltaEnteCommand.setPageSize(Integer.parseInt(session_pg_size
						.toString()));
			else
				sceltaEnteCommand.setPageSize(Integer.parseInt(request_pgSize));
		} catch (NullPointerException e) {
			sceltaEnteCommand.setPageSize(5);
		} catch (NumberFormatException e) {
			sceltaEnteCommand.setPageSize(5);
		}
		request.getSession().setAttribute(SessionVariables.SCELTA_ENTE_PG_SIZE,
				sceltaEnteCommand.getPageSize());
	}

	private void setFilter_fSearch(HttpServletRequest request,
			String request_fSearch, Object session_f_search,
			SceltaEnteCommand sceltaEnteCommand) {
		try {
			if (request_fSearch == null)
				sceltaEnteCommand
						.setFullTextSearch(session_f_search.toString());
			else
				sceltaEnteCommand.setFullTextSearch(request_fSearch);
		} catch (NullPointerException e) {
			sceltaEnteCommand.setFullTextSearch("");
		}
		request.getSession().setAttribute(
				SessionVariables.SCELTA_ENTE_F_SEARCH,
				sceltaEnteCommand.getFullTextSearch());
	}
	*/

}
