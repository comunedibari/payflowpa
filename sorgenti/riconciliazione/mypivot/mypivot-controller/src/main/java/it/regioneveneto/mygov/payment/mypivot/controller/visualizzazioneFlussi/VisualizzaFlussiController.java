package it.regioneveneto.mygov.payment.mypivot.controller.visualizzazioneFlussi;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import it.regioneveneto.mygov.payment.mypivot.controller.command.VisualizzaFlussoRendicontazioneCommand;
import it.regioneveneto.mygov.payment.mypivot.controller.command.VisualizzaFlussoRicevutaCommand;
import it.regioneveneto.mygov.payment.mypivot.controller.command.VisualizzaFlussoTesoreriaCommand;
import it.regioneveneto.mygov.payment.mypivot.controller.utils.JsonContainer;
import it.regioneveneto.mygov.payment.mypivot.controller.visualizzazioneFlussi.filter.FlussiRendicontazioneViewFilter;
import it.regioneveneto.mygov.payment.mypivot.controller.visualizzazioneFlussi.filter.FlussiRicevutaViewFilter;
import it.regioneveneto.mygov.payment.mypivot.controller.visualizzazioneFlussi.filter.FlussiTesoreriaViewFilter;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.DynamicMessageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.FlussoRendicontazioneDistinctDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.FlussoRendicontazioneDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.FlussoRicevutaDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.FlussoTesoreriaDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.MessagesDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.PageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.bilancio.BilancioContainerDto;
import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTipoDovutoTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoRendicontazioneTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoTesoreriaTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.UtenteTO;
import it.regioneveneto.mygov.payment.mypivot.service.FlussoExportService;
import it.regioneveneto.mygov.payment.mypivot.service.FlussoRendicontazioneService;
import it.regioneveneto.mygov.payment.mypivot.service.FlussoTesoreriaService;
import it.regioneveneto.mygov.payment.mypivot.service.OperatoreEnteTipoDovutoService;
import it.regioneveneto.mygov.payment.mypivot.service.exception.RtNonInRendicontazioneException;
import it.regioneveneto.mygov.payment.mypivot.utils.Constants;
import it.regioneveneto.mygov.payment.mypivot.utils.Constants.RESPONSE_STATUS;
import it.regioneveneto.mygov.payment.mypivot.utils.SecurityContext;
import it.regioneveneto.mygov.payment.mypivot.utils.SessionVariables;
import it.regioneveneto.mygov.payment.mypivot.utils.Utilities;

/**
 * 
 * @author Cristiano Perin
 *
 */
@Controller
@RequestMapping("/protected")
public class VisualizzaFlussiController {

	private static final Logger LOG = Logger.getLogger(VisualizzaFlussiController.class);

	@Autowired
	private OperatoreEnteTipoDovutoService operatoreEnteTipoDovutoService;

	@Autowired
	private FlussoTesoreriaService flussoTesoreriaService;

	@Autowired
	private FlussoRendicontazioneService flussoRendicontazioneService;

	@Autowired
	private FlussoExportService flussoExportService;

	@Resource
	private Environment env;

	@RequestMapping(value = { "/visualizzaTesoreria.html" }, method = RequestMethod.GET)
	public ModelAndView getVisualizzaTesoreria(HttpServletRequest request,
			@RequestParam(required = false) Boolean forceClear, @RequestParam(required = false) String pg,
			@RequestParam(required = false) String pgSize, @RequestParam(required = false) Boolean dataContabileCheck,
			@RequestParam(required = false) String dataContabileDa,
			@RequestParam(required = false) String dataContabileA,
			@RequestParam(required = false) Boolean dataValutaCheck,
			@RequestParam(required = false) String dataValutaDa, @RequestParam(required = false) String dataValutaA,
			@RequestParam(required = false) String deAnnoBolletta, @RequestParam(required = false) String codBolletta,
			@RequestParam(required = false) String importo, @RequestParam(required = false) String conto,
			@RequestParam(required = false) String codOr1, @RequestParam(required = false) String iuv,
			@RequestParam(required = false) String iuf) {
		LOG.info("Chiamata al controller GET getVisualizzaTesoreria");

		ModelAndView mav = new ModelAndView();

		if (SecurityContext.getRoles() == null) {
			return new ModelAndView(new RedirectView(request.getContextPath() + "/protected/sceltaEnte.html"));
		}

		if (!SecurityContext.getRoles().contains(Constants.RUOLO_VISUALIZZATORE)) {
			mav.setViewName("message");

			MessagesDto messagesDto = new MessagesDto();
			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.notAuthorized"));
			mav.addObject("messagesDto", messagesDto);

			return mav;
		}

		EnteTO enteTO = SecurityContext.getEnte();
		if (enteTO == null) {
			mav = new ModelAndView(new RedirectView(request.getContextPath() + "/protected/sceltaEnte.html"));
			return mav;
		}

		UtenteTO utenteTO = SecurityContext.getUtente();

		if (SecurityContext.getAllOperatoreEnteTipoDovuto() == null
				|| SecurityContext.getAllOperatoreEnteTipoDovuto().isEmpty()
				|| Utilities.isAllOperatoreEnteTipoDovutoDisabilitati(SecurityContext.getAllOperatoreEnteTipoDovuto())
				|| Utilities.isAllOperatoreEnteTipoDovutoDisabilitati(operatoreEnteTipoDovutoService
						.findActiveByCodFedUserIdAndCodIpaEnte(utenteTO.getCodFedUserId(), enteTO.getCodIpaEnte()))) {
			mav.setViewName("message");

			MessagesDto messagesDto = new MessagesDto();
			messagesDto.getErrorMessages()
					.add(new DynamicMessageDto("mypivot.messages.error.nessunTipoDovutoAssegnato"));
			mav.addObject("messagesDto", messagesDto);

			return mav;
		}

		if (!enteTO.getFlgTesoreria()) {
			mav.setViewName("message");

			MessagesDto messagesDto = new MessagesDto();
			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.tesoreriaNonAbilitata"));
			mav.addObject("messagesDto", messagesDto);

			return mav;
		}

		FlussiTesoreriaViewFilter flussiTesoreriaViewFilter = new FlussiTesoreriaViewFilter();

		if (forceClear != null && forceClear) {
			flussiTesoreriaViewFilter.forceClear(SessionVariables.ACTION_VISUALIZZA_FLUSSO_TESORERIA);
		}

		VisualizzaFlussoTesoreriaCommand visualizzaFlussoTesoreriaCommand = new VisualizzaFlussoTesoreriaCommand();
		MessagesDto messagesDto = new MessagesDto();

		flussiTesoreriaViewFilter.setFilters(request, pg, pgSize, dataContabileCheck, dataContabileDa, dataContabileA,
				dataValutaCheck, dataValutaDa, dataValutaA, deAnnoBolletta, codBolletta, importo, conto, codOr1, iuv,
				iuf, visualizzaFlussoTesoreriaCommand);

		flussiTesoreriaViewFilter.initialize(visualizzaFlussoTesoreriaCommand);

		Date dt_data_contabile_da = null;

		if (visualizzaFlussoTesoreriaCommand.getDataContabileCheck() != null
				&& visualizzaFlussoTesoreriaCommand.getDataContabileCheck()
				&& StringUtils.isNotBlank(visualizzaFlussoTesoreriaCommand.getDataContabileDa())) {
			try {
				dt_data_contabile_da = Constants.DDMMYYYY.parse(visualizzaFlussoTesoreriaCommand.getDataContabileDa());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date dt_data_contabile_a = null;

		if (visualizzaFlussoTesoreriaCommand.getDataContabileCheck() != null
				&& visualizzaFlussoTesoreriaCommand.getDataContabileCheck()
				&& StringUtils.isNotBlank(visualizzaFlussoTesoreriaCommand.getDataContabileA())) {
			try {
				dt_data_contabile_a = Constants.DDMMYYYY.parse(visualizzaFlussoTesoreriaCommand.getDataContabileA());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date dt_data_valuta_da = null;

		if (visualizzaFlussoTesoreriaCommand.getDataValutaCheck() != null
				&& visualizzaFlussoTesoreriaCommand.getDataValutaCheck()
				&& StringUtils.isNotBlank(visualizzaFlussoTesoreriaCommand.getDataValutaDa())) {
			try {
				dt_data_valuta_da = Constants.DDMMYYYY.parse(visualizzaFlussoTesoreriaCommand.getDataValutaDa());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date dt_data_valuta_a = null;

		if (visualizzaFlussoTesoreriaCommand.getDataValutaCheck() != null
				&& visualizzaFlussoTesoreriaCommand.getDataValutaCheck()
				&& StringUtils.isNotBlank(visualizzaFlussoTesoreriaCommand.getDataValutaA())) {
			try {
				dt_data_valuta_a = Constants.DDMMYYYY.parse(visualizzaFlussoTesoreriaCommand.getDataValutaA());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Sort tesoreriaSort = getTesoreriaSort();

		PageDto<FlussoTesoreriaDto> flussoTesoreriaDtoPage = flussoTesoreriaService.getFlussoTesoreriaPage(
				enteTO.getCodIpaEnte(), dt_data_valuta_da, dt_data_valuta_a, dt_data_contabile_da, dt_data_contabile_a,
				visualizzaFlussoTesoreriaCommand.getDeAnnoBolletta(), visualizzaFlussoTesoreriaCommand.getCodBolletta(),
				visualizzaFlussoTesoreriaCommand.getImporto(), visualizzaFlussoTesoreriaCommand.getConto(),
				visualizzaFlussoTesoreriaCommand.getCodOr1(), visualizzaFlussoTesoreriaCommand.getIuv(),
				visualizzaFlussoTesoreriaCommand.getIuf(), visualizzaFlussoTesoreriaCommand.getPage(),
				visualizzaFlussoTesoreriaCommand.getPageSize(), tesoreriaSort);
		mav.addObject("flussoTesoreriaDtoPage", flussoTesoreriaDtoPage);

		mav.addObject(visualizzaFlussoTesoreriaCommand);
		mav.addObject("messagesDto", messagesDto);
		mav.setViewName("visualizzaFlussoTesoreria");
		return mav;
	}

	@RequestMapping(value = { "/visualizzaTesoreria.html" }, method = RequestMethod.POST)
	public ModelAndView postVisualizzaTesoreria(HttpServletRequest request,
			@ModelAttribute("visualizzaFlussoTesoreriaCommand") VisualizzaFlussoTesoreriaCommand visualizzaFlussoTesoreriaCommand) {
		LOG.info("Chiamata al controller POST postVisualizzaTesoreria");

		ModelAndView mav = new ModelAndView();

		if (SecurityContext.getRoles() == null) {
			return new ModelAndView(new RedirectView(request.getContextPath() + "/protected/sceltaEnte.html"));
		}

		if (!SecurityContext.getRoles().contains(Constants.RUOLO_VISUALIZZATORE)) {
			mav.setViewName("message");

			MessagesDto messagesDto = new MessagesDto();
			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.notAuthorized"));
			mav.addObject("messagesDto", messagesDto);

			return mav;
		}

		EnteTO enteTO = SecurityContext.getEnte();
		if (enteTO == null) {
			mav = new ModelAndView(new RedirectView(request.getContextPath() + "/protected/sceltaEnte.html"));
			return mav;
		}

		UtenteTO utenteTO = SecurityContext.getUtente();

		if (SecurityContext.getAllOperatoreEnteTipoDovuto() == null
				|| SecurityContext.getAllOperatoreEnteTipoDovuto().isEmpty()
				|| Utilities.isAllOperatoreEnteTipoDovutoDisabilitati(SecurityContext.getAllOperatoreEnteTipoDovuto())
				|| Utilities.isAllOperatoreEnteTipoDovutoDisabilitati(operatoreEnteTipoDovutoService
						.findActiveByCodFedUserIdAndCodIpaEnte(utenteTO.getCodFedUserId(), enteTO.getCodIpaEnte()))) {
			mav.setViewName("message");

			MessagesDto messagesDto = new MessagesDto();
			messagesDto.getErrorMessages()
					.add(new DynamicMessageDto("mypivot.messages.error.nessunTipoDovutoAssegnato"));
			mav.addObject("messagesDto", messagesDto);

			return mav;
		}

		if (!enteTO.getFlgTesoreria()) {
			mav.setViewName("message");

			MessagesDto messagesDto = new MessagesDto();
			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.tesoreriaNonAbilitata"));
			mav.addObject("messagesDto", messagesDto);

			return mav;
		}

		MessagesDto messagesDto = new MessagesDto();

		FlussiTesoreriaViewFilter flussiTesoreriaViewFilter = new FlussiTesoreriaViewFilter();

		try {
			if (visualizzaFlussoTesoreriaCommand.getImporto() != null
					&& !visualizzaFlussoTesoreriaCommand.getImporto().isEmpty()) {

				String importoDigitato = visualizzaFlussoTesoreriaCommand.getImporto();
				importoDigitato = importoDigitato.replace(",", ".");
				visualizzaFlussoTesoreriaCommand.setImporto(importoDigitato);

				Double.parseDouble(visualizzaFlussoTesoreriaCommand.getImporto());
			}
		} catch (Exception ex) {
			mav.setViewName("message");

			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.invalidImporto"));
			mav.addObject("messagesDto", messagesDto);

			return mav;
		}

		flussiTesoreriaViewFilter.initialize(visualizzaFlussoTesoreriaCommand);

		Date dt_data_contabile_da = null;

		if (visualizzaFlussoTesoreriaCommand.getDataContabileCheck() != null
				&& visualizzaFlussoTesoreriaCommand.getDataContabileCheck()
				&& StringUtils.isNotBlank(visualizzaFlussoTesoreriaCommand.getDataContabileDa())) {
			try {
				dt_data_contabile_da = Constants.DDMMYYYY.parse(visualizzaFlussoTesoreriaCommand.getDataContabileDa());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date dt_data_contabile_a = null;

		if (visualizzaFlussoTesoreriaCommand.getDataContabileCheck() != null
				&& visualizzaFlussoTesoreriaCommand.getDataContabileCheck()
				&& StringUtils.isNotBlank(visualizzaFlussoTesoreriaCommand.getDataContabileA())) {
			try {
				dt_data_contabile_a = Constants.DDMMYYYY.parse(visualizzaFlussoTesoreriaCommand.getDataContabileA());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date dt_data_valuta_da = null;

		if (visualizzaFlussoTesoreriaCommand.getDataValutaCheck() != null
				&& visualizzaFlussoTesoreriaCommand.getDataValutaCheck()
				&& StringUtils.isNotBlank(visualizzaFlussoTesoreriaCommand.getDataValutaDa())) {
			try {
				dt_data_valuta_da = Constants.DDMMYYYY.parse(visualizzaFlussoTesoreriaCommand.getDataValutaDa());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date dt_data_valuta_a = null;

		if (visualizzaFlussoTesoreriaCommand.getDataValutaCheck() != null
				&& visualizzaFlussoTesoreriaCommand.getDataValutaCheck()
				&& StringUtils.isNotBlank(visualizzaFlussoTesoreriaCommand.getDataValutaA())) {
			try {
				dt_data_valuta_a = Constants.DDMMYYYY.parse(visualizzaFlussoTesoreriaCommand.getDataValutaA());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		visualizzaFlussoTesoreriaCommand.setPage(1);

		Map<String, Object> filtersMap = new HashMap<String, Object>();

		Sort tesoreriaSort = getTesoreriaSort();

		PageDto<FlussoTesoreriaDto> flussoTesoreriaDtoPage = flussoTesoreriaService.getFlussoTesoreriaPage(
				enteTO.getCodIpaEnte(), dt_data_valuta_da, dt_data_valuta_a, dt_data_contabile_da, dt_data_contabile_a,
				visualizzaFlussoTesoreriaCommand.getDeAnnoBolletta(), visualizzaFlussoTesoreriaCommand.getCodBolletta(),
				visualizzaFlussoTesoreriaCommand.getImporto(), visualizzaFlussoTesoreriaCommand.getConto(),
				visualizzaFlussoTesoreriaCommand.getCodOr1(), visualizzaFlussoTesoreriaCommand.getIuv(),
				visualizzaFlussoTesoreriaCommand.getIuf(), visualizzaFlussoTesoreriaCommand.getPage(),
				visualizzaFlussoTesoreriaCommand.getPageSize(), tesoreriaSort);
		mav.addObject("flussoTesoreriaDtoPage", flussoTesoreriaDtoPage);

		/**
		 * Aggiornamento filtri in sessione
		 */
		filtersMap.put(SessionVariables.FTES_TOTAL_RECORDS, flussoTesoreriaDtoPage.getTotalRecords());
		filtersMap.put(SessionVariables.FTES_PG, visualizzaFlussoTesoreriaCommand.getPage());
		filtersMap.put(SessionVariables.FTES_PG_SIZE, visualizzaFlussoTesoreriaCommand.getPageSize());

		filtersMap.put(SessionVariables.FTES_DATA_CONTABILE_CHECK,
				visualizzaFlussoTesoreriaCommand.getDataContabileCheck());
		filtersMap.put(SessionVariables.FTES_DATA_CONTABILE_DA, visualizzaFlussoTesoreriaCommand.getDataContabileDa());
		filtersMap.put(SessionVariables.FTES_DATA_CONTABILE_A, visualizzaFlussoTesoreriaCommand.getDataContabileA());

		filtersMap.put(SessionVariables.FTES_DATA_VALUTA_CHECK, visualizzaFlussoTesoreriaCommand.getDataValutaCheck());
		filtersMap.put(SessionVariables.FTES_DATA_VALUTA_DA, visualizzaFlussoTesoreriaCommand.getDataValutaDa());
		filtersMap.put(SessionVariables.FTES_DATA_VALUTA_A, visualizzaFlussoTesoreriaCommand.getDataValutaA());

		filtersMap.put(SessionVariables.FTES_ANNO_BOLLETTA, visualizzaFlussoTesoreriaCommand.getDeAnnoBolletta());
		filtersMap.put(SessionVariables.FTES_CODICE_BOLLETTA, visualizzaFlussoTesoreriaCommand.getCodBolletta());
		filtersMap.put(SessionVariables.FTES_IMPORTO, visualizzaFlussoTesoreriaCommand.getImporto());
		filtersMap.put(SessionVariables.FTES_CODICE_CONTO, visualizzaFlussoTesoreriaCommand.getConto());
		filtersMap.put(SessionVariables.FTES_CODICE_ORDINANTE, visualizzaFlussoTesoreriaCommand.getCodOr1());

		filtersMap.put(SessionVariables.FTES_CODICE_IUV, visualizzaFlussoTesoreriaCommand.getIuv());
		filtersMap.put(SessionVariables.FTES_CODICE_IUF, visualizzaFlussoTesoreriaCommand.getIuf());

		SecurityContext.setEnteViewFilters(enteTO.getCodIpaEnte(), SessionVariables.ACTION_VISUALIZZA_FLUSSO_TESORERIA,
				filtersMap);

		mav.addObject(visualizzaFlussoTesoreriaCommand);
		mav.addObject("messagesDto", messagesDto);
		mav.setViewName("visualizzaFlussoTesoreria");
		return mav;
	}

	private Sort getTesoreriaSort() {
		return new Sort(new Order(Direction.DESC, "deAnnoBolletta"), new Order(Direction.DESC, "codBolletta"));
	}

	private Sort getRicevutaSort() {
		return new Sort(new Order(Direction.DESC, "dtEDatiPagDatiSingPagDataEsitoSingoloPagamento"),
				new Order(Direction.ASC, "id.codRpSilinviarpIdUnivocoVersamento"), new Order(Direction.ASC, "codIud"));
	}

	@RequestMapping(value = { "/espandiTesoreria.html" }, method = RequestMethod.GET)
	public ModelAndView getEspandiTesoreria(HttpServletRequest request,
			@RequestParam(required = true) String deAnnoBolletta, @RequestParam(required = true) String codBolletta) {
		LOG.info("Chiamata al controller GET getEspandiTesoreria");

		ModelAndView mav = new ModelAndView();

		if (SecurityContext.getRoles() == null) {
			return new ModelAndView(new RedirectView(request.getContextPath() + "/protected/sceltaEnte.html"));
		}

		if (!SecurityContext.getRoles().contains(Constants.RUOLO_VISUALIZZATORE)) {
			mav.setViewName("message");

			MessagesDto messagesDto = new MessagesDto();
			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.notAuthorized"));
			mav.addObject("messagesDto", messagesDto);

			return mav;
		}

		EnteTO enteTO = SecurityContext.getEnte();
		if (enteTO == null) {
			mav = new ModelAndView(new RedirectView(request.getContextPath() + "/protected/sceltaEnte.html"));
			return mav;
		}

		UtenteTO utenteTO = SecurityContext.getUtente();

		if (SecurityContext.getAllOperatoreEnteTipoDovuto() == null
				|| SecurityContext.getAllOperatoreEnteTipoDovuto().isEmpty()
				|| Utilities.isAllOperatoreEnteTipoDovutoDisabilitati(SecurityContext.getAllOperatoreEnteTipoDovuto())
				|| Utilities.isAllOperatoreEnteTipoDovutoDisabilitati(operatoreEnteTipoDovutoService
						.findActiveByCodFedUserIdAndCodIpaEnte(utenteTO.getCodFedUserId(), enteTO.getCodIpaEnte()))) {
			mav.setViewName("message");

			MessagesDto messagesDto = new MessagesDto();
			messagesDto.getErrorMessages()
					.add(new DynamicMessageDto("mypivot.messages.error.nessunTipoDovutoAssegnato"));
			mav.addObject("messagesDto", messagesDto);

			return mav;
		}

		if (!enteTO.getFlgTesoreria()) {
			mav.setViewName("message");

			MessagesDto messagesDto = new MessagesDto();
			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.tesoreriaNonAbilitata"));
			mav.addObject("messagesDto", messagesDto);

			return mav;
		}

		FlussoTesoreriaTO flussoTesoreriaTO = flussoTesoreriaService
				.findByCodIpaEnteDeAnnoBollettaAndCodBolletta(enteTO.getCodIpaEnte(), deAnnoBolletta, codBolletta);
		
		if(flussoTesoreriaTO == null) {
			mav.setViewName("message");

			MessagesDto messagesDto = new MessagesDto();
			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.bollettaNonTrovata"));
			mav.addObject("messagesDto", messagesDto);

			return mav;
		}
		
		FlussoTesoreriaDto flussoTesoreriaDto = new FlussoTesoreriaDto();
		flussoTesoreriaDto.setCodiceIpaEnte(enteTO.getCodIpaEnte());
		flussoTesoreriaDto.setDeAnnoBolletta(flussoTesoreriaTO.getDeAnnoBolletta());
		flussoTesoreriaDto.setCodBolletta(flussoTesoreriaTO.getCodBolletta());
		flussoTesoreriaDto.setDeImporto(Utilities.parseImportoString(flussoTesoreriaTO.getNumIpBolletta()));
		flussoTesoreriaDto.setFlussoTesoreriaTO(flussoTesoreriaTO);
		mav.addObject("flussoTesoreriaDto", flussoTesoreriaDto);

		mav.addObject("deAnnoBolletta", flussoTesoreriaDto.getDeAnnoBolletta());
		mav.addObject("codBolletta", flussoTesoreriaDto.getCodBolletta());

		mav.setViewName("espandiFlussoTesoreria");

		return mav;
	}

	@RequestMapping(value = { "/espandiTesoreriaJson.html" }, method = RequestMethod.GET)
	public ResponseEntity<String> getEspandiTesoreriaJson(HttpServletRequest request,
			@RequestParam(required = true) String deAnnoBolletta, @RequestParam(required = true) String codBolletta) {

		LOG.info("Chiamata al controller GET getEspandiTesoreriaJson");

		JsonContainer jsonContainer = new JsonContainer();

		if (SecurityContext.getRoles() == null) {
			jsonContainer.setStatus(RESPONSE_STATUS.KO.getValue());
			jsonContainer.setErrorMessage("L'utente non ha ruoli associati.");
			return Utilities.createJsonResponse(jsonContainer, HttpStatus.OK);
		}

		if (!SecurityContext.getRoles().contains(Constants.RUOLO_VISUALIZZATORE)) {
			jsonContainer.setStatus(RESPONSE_STATUS.KO.getValue());
			jsonContainer.setErrorMessage("L'utente non ha il ruolo visualizzatore.");
			return Utilities.createJsonResponse(jsonContainer, HttpStatus.OK);
		}

		EnteTO enteTO = SecurityContext.getEnte();
		if (enteTO == null) {
			jsonContainer.setStatus(RESPONSE_STATUS.KO.getValue());
			jsonContainer.setErrorMessage("Nessun ente selezionato.");
			return Utilities.createJsonResponse(jsonContainer, HttpStatus.OK);
		}

		UtenteTO utenteTO = SecurityContext.getUtente();

		if (SecurityContext.getAllOperatoreEnteTipoDovuto() == null
				|| SecurityContext.getAllOperatoreEnteTipoDovuto().isEmpty()
				|| Utilities.isAllOperatoreEnteTipoDovutoDisabilitati(SecurityContext.getAllOperatoreEnteTipoDovuto())
				|| Utilities.isAllOperatoreEnteTipoDovutoDisabilitati(operatoreEnteTipoDovutoService
						.findActiveByCodFedUserIdAndCodIpaEnte(utenteTO.getCodFedUserId(), enteTO.getCodIpaEnte()))) {
			jsonContainer.setStatus(RESPONSE_STATUS.KO.getValue());
			jsonContainer.setErrorMessage("Nessun tipo dovuto associato all'utente.");
			return Utilities.createJsonResponse(jsonContainer, HttpStatus.OK);
		}

		if (!enteTO.getFlgTesoreria()) {
			jsonContainer.setStatus(RESPONSE_STATUS.KO.getValue());
			jsonContainer.setErrorMessage("Tesoreria disabilitata per l'ente selezionato.");
			return Utilities.createJsonResponse(jsonContainer, HttpStatus.OK);
		}

		String codiceIpaEnte = enteTO.getCodIpaEnte();

		FlussoTesoreriaTO flussoTesoreriaTO = flussoTesoreriaService
				.findByCodIpaEnteDeAnnoBollettaAndCodBolletta(codiceIpaEnte, deAnnoBolletta, codBolletta);
		if (flussoTesoreriaTO == null) {
			jsonContainer.setStatus(RESPONSE_STATUS.KO.getValue());
			jsonContainer
					.setErrorMessage("Nessuna bolletta trovata per i campi Anno Bolletta e Codice Bolletta indicati");
			return Utilities.createJsonResponse(jsonContainer, HttpStatus.OK);
		}
		boolean hasIUF = Utilities.hasFlussoTesoreriaIUF(flussoTesoreriaTO);
		boolean hasIUV = Utilities.hasFlussoTesoreriaIUV(flussoTesoreriaTO);

		if (hasIUF) {
			String iuf = flussoTesoreriaTO.getCodIdUnivocoFlusso();
			List<FlussoExportTO> listaFlussiExportTO = getListaFlussiExportTOByCodIpaEnteAndIUF(codiceIpaEnte, iuf);
			if (listaFlussiExportTO == null || CollectionUtils.isEmpty(listaFlussiExportTO)) {
				jsonContainer.setStatus(RESPONSE_STATUS.KO.getValue());
				jsonContainer.setInfoMessage(
						"La bolletta selezionata non contiene una ripartizione in Uffici, Tipi Dovuto, Capitoli e Accertamenti");
				return Utilities.createJsonResponse(jsonContainer, HttpStatus.OK);
			}
			BilancioContainerDto container = flussoTesoreriaService.estraiListaAccertamenti(codiceIpaEnte,
					listaFlussiExportTO);
			if (container.getListaBilancioDto() == null || CollectionUtils.isEmpty(container.getListaBilancioDto())) {
				jsonContainer.setStatus(RESPONSE_STATUS.KO.getValue());
				jsonContainer.setInfoMessage(
						"La bolletta selezionata non contiene una ripartizione in Uffici, Tipi Dovuto, Capitoli e Accertamenti");
				return Utilities.createJsonResponse(jsonContainer, HttpStatus.OK);
			}
			jsonContainer.setStatus(RESPONSE_STATUS.OK.getValue());
			jsonContainer.setBody(container.getListaBilancioDto());
			return Utilities.createJsonResponse(jsonContainer, HttpStatus.OK);
		}
		if (hasIUV) {
			String iuv = flussoTesoreriaTO.getCodIdUnivocoVersamento();
			List<FlussoExportTO> listaFlussiExportTO = flussoExportService.findByCodIpaEnteIUV(codiceIpaEnte, iuv);
			if (listaFlussiExportTO == null || CollectionUtils.isEmpty(listaFlussiExportTO)) {
				jsonContainer.setStatus(RESPONSE_STATUS.KO.getValue());
				jsonContainer.setInfoMessage(
						"La bolletta selezionata non contiene una ripartizione in Uffici, Tipi Dovuto, Capitoli e Accertamenti");
				return Utilities.createJsonResponse(jsonContainer, HttpStatus.OK);
			}
			BilancioContainerDto container = flussoTesoreriaService.estraiListaAccertamenti(codiceIpaEnte,
					listaFlussiExportTO);
			if (container.getListaBilancioDto() == null || CollectionUtils.isEmpty(container.getListaBilancioDto())) {
				jsonContainer.setStatus(RESPONSE_STATUS.KO.getValue());
				jsonContainer.setInfoMessage(
						"La bolletta selezionata non contiene una ripartizione in Uffici, Tipi Dovuto, Capitoli e Accertamenti");
				return Utilities.createJsonResponse(jsonContainer, HttpStatus.OK);
			}
			jsonContainer.setStatus(RESPONSE_STATUS.OK.getValue());
			jsonContainer.setBody(container.getListaBilancioDto());
			return Utilities.createJsonResponse(jsonContainer, HttpStatus.OK);
		}
		jsonContainer.setStatus(RESPONSE_STATUS.KO.getValue());
		jsonContainer.setInfoMessage(
				"La bolletta selezionata non contiene una ripartizione in Uffici, Tipi Dovuto, Capitoli e Accertamenti");
		return Utilities.createJsonResponse(jsonContainer, HttpStatus.OK);
	}

	private List<FlussoExportTO> getListaFlussiExportTOByCodIpaEnteAndIUF(String codIpaEnte, String iuf) {
		Assert.hasText(codIpaEnte, "Parametro [ codIpaEnte ] vuoto");
		Assert.hasText(iuf, "Parametro [ iuf ] vuoto");

		List<FlussoRendicontazioneTO> listaFlussiRendicontazioneTO = flussoRendicontazioneService
				.findByCodIpaEnteAndIUF(codIpaEnte, iuf);
		if (listaFlussiRendicontazioneTO != null && !listaFlussiRendicontazioneTO.isEmpty()) {
			List<FlussoExportTO> listaFlussiExportTO = new ArrayList<FlussoExportTO>();
			for (FlussoRendicontazioneTO rend : listaFlussiRendicontazioneTO) {
				String iuv = rend.getId().getCodDatiSingPagamIdentificativoUnivocoVersamento();
				int indiceDatiSingoloPagamento = rend.getId().getIndiceDatiSingoloPagamento();
				FlussoExportTO flussoExportTO = flussoExportService
						.findByCodIpaEnteIUVAndIndiceDatiSingoloPagamento(codIpaEnte, iuv, indiceDatiSingoloPagamento);
				if (flussoExportTO != null) {
					listaFlussiExportTO.add(flussoExportTO);
				}
			}
			return listaFlussiExportTO;
		}
		return null;
	}

	@RequestMapping(value = { "/visualizzaRendicontazione.html" }, method = RequestMethod.GET)
	public ModelAndView getVisualizzaRendicontazione(HttpServletRequest request,
			@RequestParam(required = false) Boolean forceClear, @RequestParam(required = false) String pg,
			@RequestParam(required = false) String pgSize, @RequestParam(required = false) Boolean dataRegolamentoCheck,
			@RequestParam(required = false) String dataRegolamentoDa,
			@RequestParam(required = false) String dataRegolamentoA, @RequestParam(required = false) String iuf,
			@RequestParam(required = false) String identificativoUnivocoRegolamento) {
		LOG.info("Chiamata al controller GET getVisualizzaRendicontazione");

		ModelAndView mav = new ModelAndView();

		if (SecurityContext.getRoles() == null) {
			return new ModelAndView(new RedirectView(request.getContextPath() + "/protected/sceltaEnte.html"));
		}

		if (!SecurityContext.getRoles().contains(Constants.RUOLO_VISUALIZZATORE)) {
			mav.setViewName("message");

			MessagesDto messagesDto = new MessagesDto();
			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.notAuthorized"));
			mav.addObject("messagesDto", messagesDto);

			return mav;
		}

		EnteTO enteTO = SecurityContext.getEnte();
		if (enteTO == null) {
			mav = new ModelAndView(new RedirectView(request.getContextPath() + "/protected/sceltaEnte.html"));
			return mav;
		}

		UtenteTO utenteTO = SecurityContext.getUtente();

		if (SecurityContext.getAllOperatoreEnteTipoDovuto() == null
				|| SecurityContext.getAllOperatoreEnteTipoDovuto().isEmpty()
				|| Utilities.isAllOperatoreEnteTipoDovutoDisabilitati(SecurityContext.getAllOperatoreEnteTipoDovuto())
				|| Utilities.isAllOperatoreEnteTipoDovutoDisabilitati(operatoreEnteTipoDovutoService
						.findActiveByCodFedUserIdAndCodIpaEnte(utenteTO.getCodFedUserId(), enteTO.getCodIpaEnte()))) {
			mav.setViewName("message");

			MessagesDto messagesDto = new MessagesDto();
			messagesDto.getErrorMessages()
					.add(new DynamicMessageDto("mypivot.messages.error.nessunTipoDovutoAssegnato"));
			mav.addObject("messagesDto", messagesDto);

			return mav;
		}

		FlussiRendicontazioneViewFilter flussiRendicontazioneViewFilter = new FlussiRendicontazioneViewFilter();

		if (forceClear != null && forceClear) {
			flussiRendicontazioneViewFilter.forceClear(SessionVariables.ACTION_VISUALIZZA_FLUSSO_RENDICONTAZIONE);
		}

		VisualizzaFlussoRendicontazioneCommand visualizzaFlussoRendicontazioneCommand = new VisualizzaFlussoRendicontazioneCommand();
		MessagesDto messagesDto = new MessagesDto();

		flussiRendicontazioneViewFilter.setFilters(request, pg, pgSize, dataRegolamentoCheck, dataRegolamentoDa,
				dataRegolamentoA, iuf, identificativoUnivocoRegolamento, visualizzaFlussoRendicontazioneCommand);

		flussiRendicontazioneViewFilter.initialize(visualizzaFlussoRendicontazioneCommand);

		Date dt_data_regolamento_da = null;

		if (visualizzaFlussoRendicontazioneCommand.getDataRegolamentoCheck() != null
				&& visualizzaFlussoRendicontazioneCommand.getDataRegolamentoCheck()
				&& StringUtils.isNotBlank(visualizzaFlussoRendicontazioneCommand.getDataRegolamentoDa())) {
			try {
				dt_data_regolamento_da = Constants.DDMMYYYY
						.parse(visualizzaFlussoRendicontazioneCommand.getDataRegolamentoDa());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date dt_data_regolamento_a = null;

		if (visualizzaFlussoRendicontazioneCommand.getDataRegolamentoCheck() != null
				&& visualizzaFlussoRendicontazioneCommand.getDataRegolamentoCheck()
				&& StringUtils.isNotBlank(visualizzaFlussoRendicontazioneCommand.getDataRegolamentoA())) {
			try {
				dt_data_regolamento_a = Constants.DDMMYYYY
						.parse(visualizzaFlussoRendicontazioneCommand.getDataRegolamentoA());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		PageDto<FlussoRendicontazioneDistinctDto> flussoRendicontazioneDtoPage = flussoRendicontazioneService
				.getFlussoRendicontazionePage(enteTO.getId(), dt_data_regolamento_da, dt_data_regolamento_a,
						visualizzaFlussoRendicontazioneCommand.getIuf(),
						visualizzaFlussoRendicontazioneCommand.getIdentificativoUnivocoRegolamento(),
						visualizzaFlussoRendicontazioneCommand.getPage(),
						visualizzaFlussoRendicontazioneCommand.getPageSize());

		mav.addObject("flussoRendicontazioneDtoPage", flussoRendicontazioneDtoPage);

		mav.addObject(visualizzaFlussoRendicontazioneCommand);
		mav.addObject("messagesDto", messagesDto);
		mav.setViewName("visualizzaFlussoRendicontazione");

		return mav;
	}

	@RequestMapping(value = { "/visualizzaRendicontazione.html" }, method = RequestMethod.POST)
	public ModelAndView postVisualizzaRendicontazione(HttpServletRequest request,
			@ModelAttribute("visualizzaFlussoRendicontazioneCommand") VisualizzaFlussoRendicontazioneCommand visualizzaFlussoRendicontazioneCommand) {
		LOG.info("Chiamata al controller POST postVisualizzaRendicontazione");

		ModelAndView mav = new ModelAndView();

		if (SecurityContext.getRoles() == null) {
			return new ModelAndView(new RedirectView(request.getContextPath() + "/protected/sceltaEnte.html"));
		}

		if (!SecurityContext.getRoles().contains(Constants.RUOLO_VISUALIZZATORE)) {
			mav.setViewName("message");

			MessagesDto messagesDto = new MessagesDto();
			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.notAuthorized"));
			mav.addObject("messagesDto", messagesDto);

			return mav;
		}

		EnteTO enteTO = SecurityContext.getEnte();
		if (enteTO == null) {
			mav = new ModelAndView(new RedirectView(request.getContextPath() + "/protected/sceltaEnte.html"));
			return mav;
		}

		UtenteTO utenteTO = SecurityContext.getUtente();

		if (SecurityContext.getAllOperatoreEnteTipoDovuto() == null
				|| SecurityContext.getAllOperatoreEnteTipoDovuto().isEmpty()
				|| Utilities.isAllOperatoreEnteTipoDovutoDisabilitati(SecurityContext.getAllOperatoreEnteTipoDovuto())
				|| Utilities.isAllOperatoreEnteTipoDovutoDisabilitati(operatoreEnteTipoDovutoService
						.findActiveByCodFedUserIdAndCodIpaEnte(utenteTO.getCodFedUserId(), enteTO.getCodIpaEnte()))) {
			mav.setViewName("message");

			MessagesDto messagesDto = new MessagesDto();
			messagesDto.getErrorMessages()
					.add(new DynamicMessageDto("mypivot.messages.error.nessunTipoDovutoAssegnato"));
			mav.addObject("messagesDto", messagesDto);

			return mav;
		}

		MessagesDto messagesDto = new MessagesDto();

		FlussiRendicontazioneViewFilter flussiRendicontazioneViewFilter = new FlussiRendicontazioneViewFilter();

		flussiRendicontazioneViewFilter.initialize(visualizzaFlussoRendicontazioneCommand);

		Date dt_data_regolamento_da = null;

		if (visualizzaFlussoRendicontazioneCommand.getDataRegolamentoCheck() != null
				&& visualizzaFlussoRendicontazioneCommand.getDataRegolamentoCheck()
				&& StringUtils.isNotBlank(visualizzaFlussoRendicontazioneCommand.getDataRegolamentoDa())) {
			try {
				dt_data_regolamento_da = Constants.DDMMYYYY
						.parse(visualizzaFlussoRendicontazioneCommand.getDataRegolamentoDa());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date dt_data_regolamento_a = null;

		if (visualizzaFlussoRendicontazioneCommand.getDataRegolamentoCheck() != null
				&& visualizzaFlussoRendicontazioneCommand.getDataRegolamentoCheck()
				&& StringUtils.isNotBlank(visualizzaFlussoRendicontazioneCommand.getDataRegolamentoA())) {
			try {
				dt_data_regolamento_a = Constants.DDMMYYYY
						.parse(visualizzaFlussoRendicontazioneCommand.getDataRegolamentoA());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		visualizzaFlussoRendicontazioneCommand.setPage(1);

		Map<String, Object> filtersMap = new HashMap<String, Object>();

		PageDto<FlussoRendicontazioneDistinctDto> flussoRendicontazioneDtoPage = flussoRendicontazioneService
				.getFlussoRendicontazionePage(enteTO.getId(), dt_data_regolamento_da, dt_data_regolamento_a,
						visualizzaFlussoRendicontazioneCommand.getIuf(),
						visualizzaFlussoRendicontazioneCommand.getIdentificativoUnivocoRegolamento(),
						visualizzaFlussoRendicontazioneCommand.getPage(),
						visualizzaFlussoRendicontazioneCommand.getPageSize());
		mav.addObject("flussoRendicontazioneDtoPage", flussoRendicontazioneDtoPage);

		/**
		 * Aggiornamento filtri in sessione
		 */
		filtersMap.put(SessionVariables.FREND_TOTAL_RECORDS, flussoRendicontazioneDtoPage.getTotalRecords());
		filtersMap.put(SessionVariables.FREND_PG, visualizzaFlussoRendicontazioneCommand.getPage());
		filtersMap.put(SessionVariables.FREND_PG_SIZE, visualizzaFlussoRendicontazioneCommand.getPageSize());

		filtersMap.put(SessionVariables.FREND_DATA_REGOLAMENTO_CHECK,
				visualizzaFlussoRendicontazioneCommand.getDataRegolamentoCheck());
		filtersMap.put(SessionVariables.FREND_DATA_REGOLAMENTO_DA,
				visualizzaFlussoRendicontazioneCommand.getDataRegolamentoDa());
		filtersMap.put(SessionVariables.FREND_DATA_REGOLAMENTO_A,
				visualizzaFlussoRendicontazioneCommand.getDataRegolamentoA());

		filtersMap.put(SessionVariables.FREND_CODICE_IUF, visualizzaFlussoRendicontazioneCommand.getIuf());
		filtersMap.put(SessionVariables.FREND_ID_UNIVOCO_REGOLAMENTO,
				visualizzaFlussoRendicontazioneCommand.getIdentificativoUnivocoRegolamento());

		SecurityContext.setEnteViewFilters(enteTO.getCodIpaEnte(),
				SessionVariables.ACTION_VISUALIZZA_FLUSSO_RENDICONTAZIONE, filtersMap);

		mav.addObject(visualizzaFlussoRendicontazioneCommand);
		mav.addObject("messagesDto", messagesDto);
		mav.setViewName("visualizzaFlussoRendicontazione");

		return mav;
	}

	@RequestMapping(value = { "/visualizzaRicevuta.html" }, method = RequestMethod.GET)
	public ModelAndView getVisualizzaRicevuta(HttpServletRequest request,
			@RequestParam(required = false) Boolean forceClear, @RequestParam(required = false) String pg,
			@RequestParam(required = false) String pgSize, Boolean dataEsitoSingoloPagamentoCheck,
			@RequestParam(required = false) String dataEsitoSingoloPagamentoDa,
			@RequestParam(required = false) String dataEsitoSingoloPagamentoA,
			@RequestParam(required = false) String iud, @RequestParam(required = false) String iuv,
			@RequestParam(required = false) String denominazioneAttestante,
			@RequestParam(required = false) String identificativoUnivocoRiscossione,
			@RequestParam(required = false) String codiceIdentificativoUnivocoPagatore,
			@RequestParam(required = false) String anagraficaPagatore,
			@RequestParam(required = false) String codiceIdentificativoUnivocoVersante,
			@RequestParam(required = false) String anagraficaVersante,
			@RequestParam(required = false) String codTipoDovuto, @RequestParam(required = false) String iuf) {
		LOG.info("Chiamata al controller GET getVisualizzaRicevuta");

		ModelAndView mav = new ModelAndView();

		if (SecurityContext.getRoles() == null) {
			return new ModelAndView(new RedirectView(request.getContextPath() + "/protected/sceltaEnte.html"));
		}

		if (!SecurityContext.getRoles().contains(Constants.RUOLO_VISUALIZZATORE)) {
			mav.setViewName("message");

			MessagesDto messagesDto = new MessagesDto();
			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.notAuthorized"));
			mav.addObject("messagesDto", messagesDto);

			return mav;
		}

		EnteTO enteTO = SecurityContext.getEnte();
		if (enteTO == null) {
			mav = new ModelAndView(new RedirectView(request.getContextPath() + "/protected/sceltaEnte.html"));
			return mav;
		}

		UtenteTO utenteTO = SecurityContext.getUtente();

		if (SecurityContext.getAllOperatoreEnteTipoDovuto() == null
				|| SecurityContext.getAllOperatoreEnteTipoDovuto().isEmpty()
				|| Utilities.isAllOperatoreEnteTipoDovutoDisabilitati(SecurityContext.getAllOperatoreEnteTipoDovuto())
				|| Utilities.isAllOperatoreEnteTipoDovutoDisabilitati(operatoreEnteTipoDovutoService
						.findActiveByCodFedUserIdAndCodIpaEnte(utenteTO.getCodFedUserId(), enteTO.getCodIpaEnte()))) {
			mav.setViewName("message");

			MessagesDto messagesDto = new MessagesDto();
			messagesDto.getErrorMessages()
					.add(new DynamicMessageDto("mypivot.messages.error.nessunTipoDovutoAssegnato"));
			mav.addObject("messagesDto", messagesDto);

			return mav;
		}

		FlussiRicevutaViewFilter flussiRicevutaViewFilter = new FlussiRicevutaViewFilter();

		boolean nessunParametroPassatoRicevuta = nessunParametroPassatoRicevuta(pg, pgSize, dataEsitoSingoloPagamentoDa,
				dataEsitoSingoloPagamentoA, iud, iuv, denominazioneAttestante, identificativoUnivocoRiscossione,
				codiceIdentificativoUnivocoPagatore, anagraficaPagatore, codiceIdentificativoUnivocoVersante,
				anagraficaVersante, codTipoDovuto, iuf);

		if ((forceClear != null && forceClear) || nessunParametroPassatoRicevuta) {
			flussiRicevutaViewFilter.forceClear(SessionVariables.ACTION_VISUALIZZA_FLUSSO_RICEVUTA);
		}

		VisualizzaFlussoRicevutaCommand visualizzaFlussoRicevutaCommand = new VisualizzaFlussoRicevutaCommand();
		MessagesDto messagesDto = new MessagesDto();

		flussiRicevutaViewFilter.setFilters(request, SessionVariables.ACTION_VISUALIZZA_FLUSSO_RICEVUTA, pg, pgSize, dataEsitoSingoloPagamentoCheck,
				dataEsitoSingoloPagamentoDa, dataEsitoSingoloPagamentoA, iud, iuv, denominazioneAttestante,
				identificativoUnivocoRiscossione, codiceIdentificativoUnivocoPagatore, anagraficaPagatore,
				codiceIdentificativoUnivocoVersante, anagraficaVersante, codTipoDovuto, iuf,
				visualizzaFlussoRicevutaCommand);

		flussiRicevutaViewFilter.initialize(visualizzaFlussoRicevutaCommand);

		Date dt_data_esito_singolo_pagamento_da = null;

		if (visualizzaFlussoRicevutaCommand.getDataEsitoSingoloPagamentoCheck() != null
				&& visualizzaFlussoRicevutaCommand.getDataEsitoSingoloPagamentoCheck()
				&& StringUtils.isNotBlank(visualizzaFlussoRicevutaCommand.getDataEsitoSingoloPagamentoDa())) {
			try {
				dt_data_esito_singolo_pagamento_da = Constants.DDMMYYYY
						.parse(visualizzaFlussoRicevutaCommand.getDataEsitoSingoloPagamentoDa());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date dt_data_esito_singolo_pagamento_a = null;

		if (visualizzaFlussoRicevutaCommand.getDataEsitoSingoloPagamentoCheck() != null
				&& visualizzaFlussoRicevutaCommand.getDataEsitoSingoloPagamentoCheck()
				&& StringUtils.isNotBlank(visualizzaFlussoRicevutaCommand.getDataEsitoSingoloPagamentoA())) {
			try {
				dt_data_esito_singolo_pagamento_a = Constants.DDMMYYYY
						.parse(visualizzaFlussoRicevutaCommand.getDataEsitoSingoloPagamentoA());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Sort ricevutaSort = getRicevutaSort();

		PageDto<FlussoRicevutaDto> flussoRicevutaDtoPage;

		if (StringUtils.isNotBlank(iuf)) {
			try {
				
				flussoRicevutaDtoPage = flussoRendicontazioneService.getFlussoRendicontazionePageByIUF(enteTO.getCodIpaEnte(),
						utenteTO.getCodFedUserId(), dt_data_esito_singolo_pagamento_da,
						dt_data_esito_singolo_pagamento_a, visualizzaFlussoRicevutaCommand.getIud(),
						visualizzaFlussoRicevutaCommand.getIuv(),
						visualizzaFlussoRicevutaCommand.getDenominazioneAttestante(),
						visualizzaFlussoRicevutaCommand.getIdentificativoUnivocoRiscossione(),
						visualizzaFlussoRicevutaCommand.getCodiceIdentificativoUnivocoPagatore(),
						visualizzaFlussoRicevutaCommand.getAnagraficaPagatore(),
						visualizzaFlussoRicevutaCommand.getCodiceIdentificativoUnivocoVersante(),
						visualizzaFlussoRicevutaCommand.getAnagraficaVersante(),
						visualizzaFlussoRicevutaCommand.getCodTipoDovuto(), visualizzaFlussoRicevutaCommand.getIuf(),
						visualizzaFlussoRicevutaCommand.getPage(), visualizzaFlussoRicevutaCommand.getPageSize(),
						ricevutaSort);

			/*	flussoRicevutaDtoPage = flussoExportService.getFlussoExportPageByIUF(enteTO.getCodIpaEnte(),
						utenteTO.getCodFedUserId(), dt_data_esito_singolo_pagamento_da,
						dt_data_esito_singolo_pagamento_a, visualizzaFlussoRicevutaCommand.getIud(),
						visualizzaFlussoRicevutaCommand.getIuv(),
						visualizzaFlussoRicevutaCommand.getDenominazioneAttestante(),
						visualizzaFlussoRicevutaCommand.getIdentificativoUnivocoRiscossione(),
						visualizzaFlussoRicevutaCommand.getCodiceIdentificativoUnivocoPagatore(),
						visualizzaFlussoRicevutaCommand.getAnagraficaPagatore(),
						visualizzaFlussoRicevutaCommand.getCodiceIdentificativoUnivocoVersante(),
						visualizzaFlussoRicevutaCommand.getAnagraficaVersante(),
						visualizzaFlussoRicevutaCommand.getCodTipoDovuto(), visualizzaFlussoRicevutaCommand.getIuf(),
						visualizzaFlussoRicevutaCommand.getPage(), visualizzaFlussoRicevutaCommand.getPageSize(),
						ricevutaSort);*/
				
				FlussoRendicontazioneDto flussoRendicontazioneDto = flussoRendicontazioneService
						.getFlussoRendicontazioneDto(enteTO.getCodIpaEnte(), visualizzaFlussoRicevutaCommand.getIuf());
				mav.addObject("flussoRendicontazioneDto", flussoRendicontazioneDto);
			} catch (RtNonInRendicontazioneException e) {
				LOG.error(e);
				mav.setViewName("message");

				messagesDto = new MessagesDto();
				messagesDto.getErrorMessages()
						.add(new DynamicMessageDto("mypivot.messages.error.iudNonInRendicontazione"));
				mav.addObject("messagesDto", messagesDto);
				return mav;
			}
		} else {
			flussoRicevutaDtoPage = flussoExportService.getFlussoExportPage(enteTO.getCodIpaEnte(),
					utenteTO.getCodFedUserId(), dt_data_esito_singolo_pagamento_da, dt_data_esito_singolo_pagamento_a,
					visualizzaFlussoRicevutaCommand.getIud(), visualizzaFlussoRicevutaCommand.getIuv(),
					visualizzaFlussoRicevutaCommand.getDenominazioneAttestante(),
					visualizzaFlussoRicevutaCommand.getIdentificativoUnivocoRiscossione(),
					visualizzaFlussoRicevutaCommand.getCodiceIdentificativoUnivocoPagatore(),
					visualizzaFlussoRicevutaCommand.getAnagraficaPagatore(),
					visualizzaFlussoRicevutaCommand.getCodiceIdentificativoUnivocoVersante(),
					visualizzaFlussoRicevutaCommand.getAnagraficaVersante(),
					visualizzaFlussoRicevutaCommand.getCodTipoDovuto(), visualizzaFlussoRicevutaCommand.getPage(),
					visualizzaFlussoRicevutaCommand.getPageSize(), ricevutaSort);

		}

		mav.addObject("flussoRicevutaDtoPage", flussoRicevutaDtoPage);

		List<EnteTipoDovutoTO> enteTipoDovutos = operatoreEnteTipoDovutoService
				.getListaEnteTipoDovutoForOperatoreAndCodIpaEnte(utenteTO.getCodFedUserId(), enteTO.getCodIpaEnte());

		mav.addObject("listaTipiDovutoPerEnte", enteTipoDovutos);

		mav.addObject(visualizzaFlussoRicevutaCommand);
		mav.addObject("messagesDto", messagesDto);
		mav.setViewName("visualizzaFlussoRicevuta");

		return mav;
	}

	@RequestMapping(value = { "/visualizzaRicevuta.html" }, method = RequestMethod.POST)
	public ModelAndView postVisualizzaRicevuta(HttpServletRequest request,
			@ModelAttribute("visualizzaFlussoRicevutaCommand") VisualizzaFlussoRicevutaCommand visualizzaFlussoRicevutaCommand) {
		LOG.info("Chiamata al controller POST postVisualizzaRicevuta");

		ModelAndView mav = new ModelAndView();

		if (SecurityContext.getRoles() == null) {
			return new ModelAndView(new RedirectView(request.getContextPath() + "/protected/sceltaEnte.html"));
		}

		if (!SecurityContext.getRoles().contains(Constants.RUOLO_VISUALIZZATORE)) {
			mav.setViewName("message");

			MessagesDto messagesDto = new MessagesDto();
			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.notAuthorized"));
			mav.addObject("messagesDto", messagesDto);

			return mav;
		}

		EnteTO enteTO = SecurityContext.getEnte();
		if (enteTO == null) {
			mav = new ModelAndView(new RedirectView(request.getContextPath() + "/protected/sceltaEnte.html"));
			return mav;
		}

		UtenteTO utenteTO = SecurityContext.getUtente();

		if (SecurityContext.getAllOperatoreEnteTipoDovuto() == null
				|| SecurityContext.getAllOperatoreEnteTipoDovuto().isEmpty()
				|| Utilities.isAllOperatoreEnteTipoDovutoDisabilitati(SecurityContext.getAllOperatoreEnteTipoDovuto())
				|| Utilities.isAllOperatoreEnteTipoDovutoDisabilitati(operatoreEnteTipoDovutoService
						.findActiveByCodFedUserIdAndCodIpaEnte(utenteTO.getCodFedUserId(), enteTO.getCodIpaEnte()))) {
			mav.setViewName("message");

			MessagesDto messagesDto = new MessagesDto();
			messagesDto.getErrorMessages()
					.add(new DynamicMessageDto("mypivot.messages.error.nessunTipoDovutoAssegnato"));
			mav.addObject("messagesDto", messagesDto);

			return mav;
		}

		MessagesDto messagesDto = new MessagesDto();

		FlussiRicevutaViewFilter flussiRicevutaViewFilter = new FlussiRicevutaViewFilter();

		flussiRicevutaViewFilter.initialize(visualizzaFlussoRicevutaCommand);

		Date dt_data_esito_singolo_pagamento_da = null;

		if (visualizzaFlussoRicevutaCommand.getDataEsitoSingoloPagamentoCheck() != null
				&& visualizzaFlussoRicevutaCommand.getDataEsitoSingoloPagamentoCheck()
				&& StringUtils.isNotBlank(visualizzaFlussoRicevutaCommand.getDataEsitoSingoloPagamentoDa())) {
			try {
				dt_data_esito_singolo_pagamento_da = Constants.DDMMYYYY
						.parse(visualizzaFlussoRicevutaCommand.getDataEsitoSingoloPagamentoDa());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date dt_data_esito_singolo_pagamento_a = null;

		if (visualizzaFlussoRicevutaCommand.getDataEsitoSingoloPagamentoCheck() != null
				&& visualizzaFlussoRicevutaCommand.getDataEsitoSingoloPagamentoCheck()
				&& StringUtils.isNotBlank(visualizzaFlussoRicevutaCommand.getDataEsitoSingoloPagamentoA())) {
			try {
				dt_data_esito_singolo_pagamento_a = Constants.DDMMYYYY
						.parse(visualizzaFlussoRicevutaCommand.getDataEsitoSingoloPagamentoA());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		visualizzaFlussoRicevutaCommand.setPage(1);

		Map<String, Object> filtersMap = new HashMap<String, Object>();

		Sort ricevutaSort = getRicevutaSort();

		PageDto<FlussoRicevutaDto> flussoRicevutaDtoPage;

		if (StringUtils.isNotBlank(visualizzaFlussoRicevutaCommand.getIuf())) {
			try {
				flussoRicevutaDtoPage = flussoExportService.getFlussoExportPageByIUF(enteTO.getCodIpaEnte(),
						utenteTO.getCodFedUserId(), dt_data_esito_singolo_pagamento_da,
						dt_data_esito_singolo_pagamento_a, visualizzaFlussoRicevutaCommand.getIud(),
						visualizzaFlussoRicevutaCommand.getIuv(),
						visualizzaFlussoRicevutaCommand.getDenominazioneAttestante(),
						visualizzaFlussoRicevutaCommand.getIdentificativoUnivocoRiscossione(),
						visualizzaFlussoRicevutaCommand.getCodiceIdentificativoUnivocoPagatore(),
						visualizzaFlussoRicevutaCommand.getAnagraficaPagatore(),
						visualizzaFlussoRicevutaCommand.getCodiceIdentificativoUnivocoVersante(),
						visualizzaFlussoRicevutaCommand.getAnagraficaVersante(),
						visualizzaFlussoRicevutaCommand.getCodTipoDovuto(), visualizzaFlussoRicevutaCommand.getIuf(),
						visualizzaFlussoRicevutaCommand.getPage(), visualizzaFlussoRicevutaCommand.getPageSize(),
						ricevutaSort);
				FlussoRendicontazioneDto flussoRendicontazioneDto = flussoRendicontazioneService
						.getFlussoRendicontazioneDto(enteTO.getCodIpaEnte(), visualizzaFlussoRicevutaCommand.getIuf());
				mav.addObject("flussoRendicontazioneDto", flussoRendicontazioneDto);
			} catch (RtNonInRendicontazioneException e) {
				LOG.error(e);
				mav.setViewName("message");

				messagesDto = new MessagesDto();
				messagesDto.getErrorMessages()
						.add(new DynamicMessageDto("mypivot.messages.error.iudNonInRendicontazione"));
				mav.addObject("messagesDto", messagesDto);
				return mav;
			}
		} else {
			flussoRicevutaDtoPage = flussoExportService.getFlussoExportPage(enteTO.getCodIpaEnte(),
					utenteTO.getCodFedUserId(), dt_data_esito_singolo_pagamento_da, dt_data_esito_singolo_pagamento_a,
					visualizzaFlussoRicevutaCommand.getIud(), visualizzaFlussoRicevutaCommand.getIuv(),
					visualizzaFlussoRicevutaCommand.getDenominazioneAttestante(),
					visualizzaFlussoRicevutaCommand.getIdentificativoUnivocoRiscossione(),
					visualizzaFlussoRicevutaCommand.getCodiceIdentificativoUnivocoPagatore(),
					visualizzaFlussoRicevutaCommand.getAnagraficaPagatore(),
					visualizzaFlussoRicevutaCommand.getCodiceIdentificativoUnivocoVersante(),
					visualizzaFlussoRicevutaCommand.getAnagraficaVersante(),
					visualizzaFlussoRicevutaCommand.getCodTipoDovuto(), visualizzaFlussoRicevutaCommand.getPage(),
					visualizzaFlussoRicevutaCommand.getPageSize(), ricevutaSort);

		}

		mav.addObject("flussoRicevutaDtoPage", flussoRicevutaDtoPage);

		/**
		 * Aggiornamento filtri in sessione
		 */
		filtersMap.put(SessionVariables.FRIC_TOTAL_RECORDS, flussoRicevutaDtoPage.getTotalRecords());
		filtersMap.put(SessionVariables.FRIC_PG, visualizzaFlussoRicevutaCommand.getPage());
		filtersMap.put(SessionVariables.FRIC_PG_SIZE, visualizzaFlussoRicevutaCommand.getPageSize());

		filtersMap.put(SessionVariables.FRIC_DATA_ESITO_CHECK,
				visualizzaFlussoRicevutaCommand.getDataEsitoSingoloPagamentoCheck());
		filtersMap.put(SessionVariables.FRIC_DATA_ESITO_DA,
				visualizzaFlussoRicevutaCommand.getDataEsitoSingoloPagamentoDa());
		filtersMap.put(SessionVariables.FRIC_DATA_ESITO_A,
				visualizzaFlussoRicevutaCommand.getDataEsitoSingoloPagamentoA());

		filtersMap.put(SessionVariables.FRIC_CODICE_IUD, visualizzaFlussoRicevutaCommand.getIud());
		filtersMap.put(SessionVariables.FRIC_CODICE_IUV, visualizzaFlussoRicevutaCommand.getIuv());
		filtersMap.put(SessionVariables.FRIC_CODICE_IUF, visualizzaFlussoRicevutaCommand.getIuf());
		filtersMap.put(SessionVariables.FRIC_DENOMINAZIONE_ATTESTANTE,
				visualizzaFlussoRicevutaCommand.getDenominazioneAttestante());
		filtersMap.put(SessionVariables.FRIC_ID_UNIVOCO_RISCOSSIONE,
				visualizzaFlussoRicevutaCommand.getIdentificativoUnivocoRiscossione());
		filtersMap.put(SessionVariables.FRIC_COD_ID_UNIVOCO_PAGATORE,
				visualizzaFlussoRicevutaCommand.getCodiceIdentificativoUnivocoPagatore());
		filtersMap.put(SessionVariables.FRIC_ANAGRAFICA_PAGATORE,
				visualizzaFlussoRicevutaCommand.getAnagraficaPagatore());
		filtersMap.put(SessionVariables.FRIC_COD_ID_UNIVOCO_VERSANTE,
				visualizzaFlussoRicevutaCommand.getCodiceIdentificativoUnivocoVersante());
		filtersMap.put(SessionVariables.FRIC_ANAGRAFICA_VERSANTE,
				visualizzaFlussoRicevutaCommand.getAnagraficaVersante());
		filtersMap.put(SessionVariables.FRIC_COD_TIPO_DOVUTO, visualizzaFlussoRicevutaCommand.getCodTipoDovuto());

		SecurityContext.setEnteViewFilters(enteTO.getCodIpaEnte(), SessionVariables.ACTION_VISUALIZZA_FLUSSO_RICEVUTA,
				filtersMap);

		List<EnteTipoDovutoTO> enteTipoDovutos = operatoreEnteTipoDovutoService
				.getListaEnteTipoDovutoForOperatoreAndCodIpaEnte(utenteTO.getCodFedUserId(), enteTO.getCodIpaEnte());

		mav.addObject("listaTipiDovutoPerEnte", enteTipoDovutos);

		mav.addObject(visualizzaFlussoRicevutaCommand);
		mav.addObject("messagesDto", messagesDto);
		mav.setViewName("visualizzaFlussoRicevuta");

		return mav;
	}

	private boolean nessunParametroPassatoRicevuta(String pg, String pgSize, String dataEsitoSingoloPagamentoDa,
			String dataEsitoSingoloPagamentoA, String iud, String iuv, String denominazioneAttestante,
			String identificativoUnivocoRiscossione, String codiceIdentificativoUnivocoPagatore,
			String anagraficaPagatore, String codiceIdentificativoUnivocoVersante, String anagraficaVersante,
			String codTipoDovuto, String iuf) {
		if (StringUtils.isBlank(pg) && StringUtils.isBlank(pgSize) && StringUtils.isBlank(dataEsitoSingoloPagamentoDa)
				&& StringUtils.isBlank(dataEsitoSingoloPagamentoA) && StringUtils.isBlank(iud)
				&& StringUtils.isBlank(iuv) && StringUtils.isBlank(denominazioneAttestante)
				&& StringUtils.isBlank(identificativoUnivocoRiscossione)
				&& StringUtils.isBlank(codiceIdentificativoUnivocoPagatore) && StringUtils.isBlank(anagraficaPagatore)
				&& StringUtils.isBlank(codiceIdentificativoUnivocoVersante) && StringUtils.isBlank(anagraficaVersante)
				&& StringUtils.isBlank(codTipoDovuto) && StringUtils.isBlank(iuf))
			return true;
		return false;
	}
}
