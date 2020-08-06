package it.regioneveneto.mygov.payment.mypivot.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import it.regioneveneto.mybox.client.MyBoxClient;
import it.regioneveneto.mybox.domain.Errore;
import it.regioneveneto.mybox.domain.IntestazioneRisposta;
import it.regioneveneto.mybox.domain.MyBoxAuthorize;
import it.regioneveneto.mybox.domain.MyBoxAuthorizeRisposta;
import it.regioneveneto.mygov.payment.mypivot.controller.command.FlussiExportCommand;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.DynamicMessageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.FlussoExportDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.MessagesDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.PageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.UtenteTO;
import it.regioneveneto.mygov.payment.mypivot.service.OperatoreEnteTipoDovutoService;
import it.regioneveneto.mygov.payment.mypivot.service.PrenotazioneFlussoRiconciliazioneService;
import it.regioneveneto.mygov.payment.mypivot.utils.Constants;
import it.regioneveneto.mygov.payment.mypivot.utils.SecurityContext;
import it.regioneveneto.mygov.payment.mypivot.utils.SessionVariables;
import it.regioneveneto.mygov.payment.mypivot.utils.Utilities;

/**
 * 
 * @author Cristiano Perin
 *
 */

@Controller
@RequestMapping("/protected/flussi")
public class FlussiExportController {

	private static final Log log = LogFactory.getLog(FlussiExportController.class);

	@Autowired
	private PrenotazioneFlussoRiconciliazioneService prenotazioneFlussoRiconciliazioneService;

	@Autowired
	private OperatoreEnteTipoDovutoService operatoreEnteTipoDovutoService;

	@Autowired
	private MyBoxClient myBoxClient;

	@Autowired
	private Environment environment;

	@RequestMapping(value = { "/flussiExport.html" }, method = RequestMethod.GET)
	public ModelAndView visualizzaFlussiExport(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false) Boolean forceClear, @RequestParam(required = false) String pg,
			@RequestParam(required = false) String pgSize, @RequestParam(required = false) String dtFrom,
			@RequestParam(required = false) String dtTo, @RequestParam(required = false) String fSearch) {
		ModelAndView mav = new ModelAndView();

		EnteTO ente = SecurityContext.getEnte();
		if (ente == null) {
			mav.setView(new RedirectView(request.getContextPath()
					+ "/protected/sceltaEnte.html?redirectUrl=protected/flussi/flussiExport.html"));
			return mav;
		}

		UtenteTO utente = SecurityContext.getUtente();
		FlussiExportCommand flussiExportCommand = new FlussiExportCommand();

		MessagesDto messagesDto = new MessagesDto();

		if ((SecurityContext.getRoles() == null) || (SecurityContext.getRoles() != null
				&& !SecurityContext.getRoles().contains(Constants.RUOLO_VISUALIZZATORE))) {
			mav.setViewName("message");

			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.notAuthorized"));
			mav.addObject("messagesDto", messagesDto);

			return mav;
		}

		if (SecurityContext.getAllOperatoreEnteTipoDovuto() == null
				|| SecurityContext.getAllOperatoreEnteTipoDovuto().isEmpty()
				|| Utilities.isAllOperatoreEnteTipoDovutoDisabilitati(SecurityContext.getAllOperatoreEnteTipoDovuto())
				|| Utilities.isAllOperatoreEnteTipoDovutoDisabilitati(operatoreEnteTipoDovutoService
						.findActiveByCodFedUserIdAndCodIpaEnte(utente.getCodFedUserId(), ente.getCodIpaEnte()))) {
			mav.setViewName("message");

			messagesDto = new MessagesDto();
			messagesDto.getErrorMessages()
					.add(new DynamicMessageDto("mypivot.messages.error.nessunTipoDovutoAssegnato"));
			mav.addObject("messagesDto", messagesDto);

			return mav;
		}

		if (forceClear != null && forceClear) {
			request.getSession().removeAttribute(SessionVariables.FLUSSI_EXPORT_RICONCILIAZIONE_PG);
			request.getSession().removeAttribute(SessionVariables.FLUSSI_EXPORT_RICONCILIAZIONE_PG_SIZE);
			request.getSession().removeAttribute(SessionVariables.FLUSSI_EXPORT_RICONCILIAZIONE_DT_FROM);
			request.getSession().removeAttribute(SessionVariables.FLUSSI_EXPORT_RICONCILIAZIONE_DT_TO);
			request.getSession().removeAttribute(SessionVariables.FLUSSI_EXPORT_RICONCILIAZIONE_F_SEARCH);
		}

		setFilters(request, pg, pgSize, dtFrom, dtTo, fSearch, flussiExportCommand);

		initialize(flussiExportCommand);

		String dataFromStr = flussiExportCommand.getDateFrom();
		String dataToStr = flussiExportCommand.getDateTo();
		Date dateFrom = null;
		Date dateTo = null;

		// DATE FROM
		if (StringUtils.isNotBlank(dataFromStr)) {
			try {
				dateFrom = Constants.DDMMYYYY.parse(dataFromStr);
			} catch (ParseException e) {
				log.error("Errore nel parsing della data inizio nella ricerca degli export. DateFrom = [" + dataFromStr
						+ "]", e);
				dateFrom = Constants.MINIMUM_DATE;
			}
		}

		// DATE TO
		if (StringUtils.isNotBlank(dataToStr)) {
			try {
				dateTo = Constants.DDMMYYYY.parse(dataToStr);
			} catch (ParseException e) {
				log.error("Errore nel parsing della data fine nella ricerca degli export. DateTo = [" + dataToStr + "]",
						e);
				dateTo = Constants.MAXIMUM_DATE;
			}
		}

		PageDto<FlussoExportDto> flussiDtoPage = prenotazioneFlussoRiconciliazioneService.getFlussoPage(
				utente.getCodFedUserId(), ente.getCodIpaEnte(), flussiExportCommand.getFullTextSearch(), dateFrom,
				dateTo, flussiExportCommand.getPage(), flussiExportCommand.getPageSize(), Direction.DESC,
				"dtCreazione");

		flussiExportCommand.setEnte(SecurityContext.getEnte().getDeNomeEnte());

		try {
			MyBoxAuthorizeRisposta myBoxResponse = requestAuthToken(ente);
			IntestazioneRisposta intestazioneRisposta = myBoxResponse.getIntestazioneRisposta();
			List<Errore> errori = intestazioneRisposta.getErrori();
			if (!errori.isEmpty()) {
				throw new Exception(errori.get(0).getCodice());
			}
			flussiExportCommand.setAuthorizationToken(myBoxResponse.getTokenRisposta());
			flussiExportCommand.setShowDownloadButton(true);
		} catch (Exception e) {
			log.error("Errore di comunicazione con myBox: " + e.getMessage());
			flussiExportCommand.setAuthorizationToken("");
			flussiExportCommand.setShowDownloadButton(false);
		}

		flussiExportCommand.setMyBoxContextURL(environment.getProperty("myBox.contextURL"));

		mav.setViewName("flussiExport");
		mav.addObject("messagesDto2", messagesDto);
		mav.addObject(flussiExportCommand);
		mav.addObject("flussiExportDtoPage", flussiDtoPage);
		return mav;
	}

	@RequestMapping(value = { "/flussiExport.html" }, method = RequestMethod.POST)
	public ModelAndView postFlussiExport(@ModelAttribute("flussiExportCommand") FlussiExportCommand flussiExportCommand,
			HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mav = new ModelAndView();
		MessagesDto messagesDto = new MessagesDto();

		UtenteTO utente = SecurityContext.getUtente();

		if ((SecurityContext.getRoles() == null) || (SecurityContext.getRoles() != null
				&& !SecurityContext.getRoles().contains(Constants.RUOLO_VISUALIZZATORE))) {
			mav.setViewName("message");

			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.notAuthorized"));
			mav.addObject("messagesDto", messagesDto);

			return mav;
		}

		EnteTO ente = SecurityContext.getEnte();
		if (ente == null) {
			mav = new ModelAndView(new RedirectView(request.getContextPath() + "/protected/sceltaEnte.html"));
			return mav;
		}

		if (SecurityContext.getAllOperatoreEnteTipoDovuto() == null
				|| SecurityContext.getAllOperatoreEnteTipoDovuto().isEmpty()
				|| Utilities.isAllOperatoreEnteTipoDovutoDisabilitati(SecurityContext.getAllOperatoreEnteTipoDovuto())
				|| Utilities.isAllOperatoreEnteTipoDovutoDisabilitati(operatoreEnteTipoDovutoService
						.findActiveByCodFedUserIdAndCodIpaEnte(utente.getCodFedUserId(), ente.getCodIpaEnte()))) {
			mav.setViewName("message");

			messagesDto = new MessagesDto();
			messagesDto.getErrorMessages()
					.add(new DynamicMessageDto("mypivot.messages.error.nessunTipoDovutoAssegnato"));
			mav.addObject("messagesDto", messagesDto);

			return mav;
		}

		initialize(flussiExportCommand);

		Date dt_data_da = null;

		if (StringUtils.isNotBlank(flussiExportCommand.getDateFrom())) {
			try {
				dt_data_da = Constants.DDMMYYYY.parse(flussiExportCommand.getDateFrom());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date dt_data_a = null;

		if (StringUtils.isNotBlank(flussiExportCommand.getDateTo())) {
			try {
				dt_data_a = Constants.DDMMYYYY.parse(flussiExportCommand.getDateTo());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		flussiExportCommand.setPage(1);

		PageDto<FlussoExportDto> flussiDtoPage = prenotazioneFlussoRiconciliazioneService.getFlussoPage(
				utente.getCodFedUserId(), ente.getCodIpaEnte(), flussiExportCommand.getFullTextSearch(), dt_data_da,
				dt_data_a, flussiExportCommand.getPage(), flussiExportCommand.getPageSize(), Direction.DESC,
				"dtCreazione");

		try {
			MyBoxAuthorizeRisposta myBoxResponse = requestAuthToken(ente);
			IntestazioneRisposta intestazioneRisposta = myBoxResponse.getIntestazioneRisposta();
			List<Errore> errori = intestazioneRisposta.getErrori();
			if (!errori.isEmpty()) {
				throw new Exception(errori.get(0).getCodice());
			}
			flussiExportCommand.setAuthorizationToken(myBoxResponse.getTokenRisposta());
			flussiExportCommand.setShowDownloadButton(true);
		} catch (Exception e) {
			log.error("Errore di comunicazione con myBox: " + e.getMessage());
			flussiExportCommand.setAuthorizationToken("");
			flussiExportCommand.setShowDownloadButton(false);
		}

		flussiExportCommand.setMyBoxContextURL(environment.getProperty("myBox.contextURL"));

		mav.setViewName("flussiExport");

		mav.addObject("messagesDto2", messagesDto);
		mav.addObject(flussiExportCommand);
		mav.addObject("flussiExportDtoPage", flussiDtoPage);

		// save session filter data
		request.getSession().setAttribute(SessionVariables.FLUSSI_EXPORT_RICONCILIAZIONE_PG,
				flussiExportCommand.getPage());
		request.getSession().setAttribute(SessionVariables.FLUSSI_EXPORT_RICONCILIAZIONE_PG_SIZE,
				flussiExportCommand.getPageSize());
		request.getSession().setAttribute(SessionVariables.FLUSSI_EXPORT_RICONCILIAZIONE_DT_FROM,
				flussiExportCommand.getDateFrom());
		request.getSession().setAttribute(SessionVariables.FLUSSI_EXPORT_RICONCILIAZIONE_DT_TO,
				flussiExportCommand.getDateTo());
		request.getSession().setAttribute(SessionVariables.FLUSSI_EXPORT_RICONCILIAZIONE_F_SEARCH,
				flussiExportCommand.getFullTextSearch());

		return mav;
	}

	// FILTRI TABBELLA FLUSSI
	private void setFilters(HttpServletRequest request, String pg, String pgSize, String dtFrom, String dtTo,
			String fSearch, FlussiExportCommand flussiExportCommand) {

		Object session_pg = request.getSession().getAttribute(SessionVariables.FLUSSI_EXPORT_RICONCILIAZIONE_PG);
		Object session_pg_size = request.getSession()
				.getAttribute(SessionVariables.FLUSSI_EXPORT_RICONCILIAZIONE_PG_SIZE);
		Object session_dt_from = request.getSession()
				.getAttribute(SessionVariables.FLUSSI_EXPORT_RICONCILIAZIONE_DT_FROM);
		Object session_dt_to = request.getSession().getAttribute(SessionVariables.FLUSSI_EXPORT_RICONCILIAZIONE_DT_TO);
		Object session_f_search = request.getSession()
				.getAttribute(SessionVariables.FLUSSI_EXPORT_RICONCILIAZIONE_F_SEARCH);

		// PAGE
		setFilter_page(request, pg, session_pg, flussiExportCommand);

		// PAGE SIZE
		setFilter_pageSize(request, pgSize, session_pg_size, flussiExportCommand);

		// DATE FROM
		setFilter_dateFrom(request, dtFrom, session_dt_from, flussiExportCommand);

		// DATE TO
		setFilter_dateTo(request, dtTo, session_dt_to, flussiExportCommand);

		// FULL TEXT SEARCH
		setFilter_fSearch(request, fSearch, session_f_search, flussiExportCommand);

	}

	private void setFilter_page(HttpServletRequest request, String request_pg, Object session_pg,
			FlussiExportCommand flussiExportCommand) {
		try {
			if (request_pg == null)
				flussiExportCommand.setPage(Integer.parseInt(session_pg.toString()));
			else {
				flussiExportCommand.setPage(Integer.parseInt(request_pg));
			}
		} catch (NullPointerException e) {
			flussiExportCommand.setPage(1);
		} catch (NumberFormatException e) {
			flussiExportCommand.setPage(1);
		}
		request.getSession().setAttribute(SessionVariables.FLUSSI_EXPORT_RICONCILIAZIONE_PG,
				flussiExportCommand.getPage());
	}

	private void setFilter_pageSize(HttpServletRequest request, String request_pgSize, Object session_pg_size,
			FlussiExportCommand flussiExportCommand) {
		try {
			if (request_pgSize == null)
				flussiExportCommand.setPageSize(Integer.parseInt(session_pg_size.toString()));
			else
				flussiExportCommand.setPageSize(Integer.parseInt(request_pgSize));
		} catch (NullPointerException e) {
			flussiExportCommand.setPageSize(5);
		} catch (NumberFormatException e) {
			flussiExportCommand.setPageSize(5);
		}
		request.getSession().setAttribute(SessionVariables.FLUSSI_EXPORT_RICONCILIAZIONE_PG_SIZE,
				flussiExportCommand.getPageSize());
	}

	private void setFilter_dateFrom(HttpServletRequest request, String request_dtFrom, Object session_dt_from,
			FlussiExportCommand flussiExportCommand) {
		try {
			if (request_dtFrom == null)
				flussiExportCommand.setDateFrom(session_dt_from.toString());
			else
				flussiExportCommand.setDateFrom(request_dtFrom);
		} catch (NullPointerException e) {
			flussiExportCommand.setDateFrom("");
		}
		request.getSession().setAttribute(SessionVariables.FLUSSI_EXPORT_RICONCILIAZIONE_DT_FROM,
				flussiExportCommand.getDateFrom());
	}

	private void setFilter_dateTo(HttpServletRequest request, String request_dtTo, Object session_dt_to,
			FlussiExportCommand flussiExportCommand) {
		try {
			if (request_dtTo == null)
				flussiExportCommand.setDateTo(session_dt_to.toString());
			else
				flussiExportCommand.setDateTo(request_dtTo);
		} catch (NullPointerException e) {
			flussiExportCommand.setDateTo("");
		}
		request.getSession().setAttribute(SessionVariables.FLUSSI_EXPORT_RICONCILIAZIONE_DT_TO,
				flussiExportCommand.getDateTo());
	}

	private void setFilter_fSearch(HttpServletRequest request, String request_fSearch, Object session_f_search,
			FlussiExportCommand flussiExportCommand) {
		try {
			if (request_fSearch == null)
				flussiExportCommand.setFullTextSearch(session_f_search.toString());
			else
				flussiExportCommand.setFullTextSearch(request_fSearch);
		} catch (NullPointerException e) {
			flussiExportCommand.setFullTextSearch("");
		}
		request.getSession().setAttribute(SessionVariables.FLUSSI_EXPORT_RICONCILIAZIONE_F_SEARCH,
				flussiExportCommand.getFullTextSearch());
	}

	private void initialize(FlussiExportCommand flussiExportCommand) {

		Date data_da = null;

		if (StringUtils.isNotBlank(flussiExportCommand.getDateFrom())) {
			try {
				data_da = Constants.DDMMYYYY.parse(flussiExportCommand.getDateFrom());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date data_a = null;

		if (StringUtils.isNotBlank(flussiExportCommand.getDateTo())) {
			try {
				data_a = Constants.DDMMYYYY.parse(flussiExportCommand.getDateTo());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		if ((data_da == null) && (data_a == null)) {
			data_a = new Date();
			data_a = DateUtils.setHours(data_a, 0);
			data_a = DateUtils.setMinutes(data_a, 0);
			data_a = DateUtils.setSeconds(data_a, 0);
			data_a = DateUtils.setMilliseconds(data_a, 0);

			data_da = DateUtils.addMonths(data_a, -1);
		} else if ((data_da != null) && (data_a == null)) {
			data_da = DateUtils.setHours(data_da, 0);
			data_da = DateUtils.setMinutes(data_da, 0);
			data_da = DateUtils.setSeconds(data_da, 0);
			data_da = DateUtils.setMilliseconds(data_da, 0);

			data_a = DateUtils.addMonths(data_da, -1);
		} else if ((data_da == null) && (data_a != null)) {
			data_a = DateUtils.setHours(data_a, 0);
			data_a = DateUtils.setMinutes(data_a, 0);
			data_a = DateUtils.setSeconds(data_a, 0);
			data_a = DateUtils.setMilliseconds(data_a, 0);

			data_da = DateUtils.addMonths(data_a, -1);
		}

		flussiExportCommand.setDateFrom(Constants.DDMMYYYY.format(data_da));
		flussiExportCommand.setDateTo(Constants.DDMMYYYY.format(data_a));

	}

	private MyBoxAuthorizeRisposta requestAuthToken(EnteTO ente) {
		MyBoxAuthorize myboxAuthorize = new MyBoxAuthorize();
		myboxAuthorize.setClientKey(ente.getMyboxClientKey());
		myboxAuthorize.setClientSecret(ente.getMyboxClientSecret());
		MyBoxAuthorizeRisposta myboxAuthorizeRisposta = myBoxClient.myBoxAuthorize(myboxAuthorize);
		return myboxAuthorizeRisposta;
	}

}
