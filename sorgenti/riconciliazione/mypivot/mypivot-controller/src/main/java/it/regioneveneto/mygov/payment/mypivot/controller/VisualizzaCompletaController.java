package it.regioneveneto.mygov.payment.mypivot.controller;

import java.beans.PropertyDescriptor;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import it.regioneveneto.mygov.payment.mypivot.controller.command.VisualizzaCompletaCommand;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.DynamicMessageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.ErrorTypeDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.ExportCSVDtoIFace;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.ExportCSVIudRtIufTesDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.ExportCSVRtIufDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.ExportCSVRtIufTesDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.MessagesDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.PageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.RendicontazioneSubsetDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.RendicontazioneTesoreriaSubsetDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.TesoreriaNoMatchSubsetDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.TesoreriaSubsetDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.VisualizzaCompletaDto;
import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTipoDovutoTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.PrenotazioneFlussoRiconciliazioneTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.UtenteTO;
import it.regioneveneto.mygov.payment.mypivot.service.ImportExportRendicontazioneTesoreriaService;
import it.regioneveneto.mygov.payment.mypivot.service.OperatoreEnteTipoDovutoService;
import it.regioneveneto.mygov.payment.mypivot.service.PrenotazioneFlussoRiconciliazioneService;
import it.regioneveneto.mygov.payment.mypivot.service.RendicontazioneSubsetService;
import it.regioneveneto.mygov.payment.mypivot.service.RendicontazioneTesoreriaSubsetService;
import it.regioneveneto.mygov.payment.mypivot.service.TesoreriaSubsetService;
import it.regioneveneto.mygov.payment.mypivot.service.exception.NessunTipoDovutoAttivoException;
import it.regioneveneto.mygov.payment.mypivot.service.exception.TipoDovutoNonValidoPerUtenteException;
import it.regioneveneto.mygov.payment.mypivot.utils.Constants;
import it.regioneveneto.mygov.payment.mypivot.utils.Constants.VERSIONE_TRACCIATO_EXPORT;
import it.regioneveneto.mygov.payment.mypivot.utils.Constants.VISUALIZZA_NASCOSTI;
import it.regioneveneto.mygov.payment.mypivot.utils.SecurityContext;
import it.regioneveneto.mygov.payment.mypivot.utils.SessionVariables;
import it.regioneveneto.mygov.payment.mypivot.utils.Utilities;

@Controller
@RequestMapping("/protected")
public class VisualizzaCompletaController {

	@Autowired
	private ImportExportRendicontazioneTesoreriaService importExportRendicontazioneTesoreriaService;

	@Autowired
	private RendicontazioneSubsetService rendicontazioneSubsetService;

	@Autowired
	private TesoreriaSubsetService tesoreriaSubsetService;

	@Autowired
	private PrenotazioneFlussoRiconciliazioneService prenotazioneFlussoRiconciliazioneService;

	@Autowired
	private OperatoreEnteTipoDovutoService operatoreEnteTipoDovutoService;

	@Autowired
	private RendicontazioneTesoreriaSubsetService rendicontazioneTesoreriaSubsetService;

	@Resource
	private Environment env;

	public VisualizzaCompletaController() {
		super();
	}

	@RequestMapping(value = { "/visualizzaCompleta.html" }, method = RequestMethod.GET)
	public ModelAndView get(HttpServletRequest request, @RequestParam(required = false) Boolean forceClear,
			@RequestParam(required = false) String pg, @RequestParam(required = false) String pgSize,
			@RequestParam(required = false) String codice_iud, @RequestParam(required = false) String codice_iuv,
			@RequestParam(required = false) String denominazione_attestante,
			@RequestParam(required = false) String identificativo_univoco_riscossione,
			@RequestParam(required = false) String codice_identificativo_univoco_versante,
			@RequestParam(required = false) String anagrafica_versante,
			@RequestParam(required = false) String codice_identificativo_univoco_pagatore,
			@RequestParam(required = false) String anagrafica_pagatore,
			@RequestParam(required = false) String causale_versamento,
			@RequestParam(required = false) String data_esito_singolo_pagamento_da,
			@RequestParam(required = false) String data_esito_singolo_pagamento_a,
			@RequestParam(required = false) String identificativo_flusso_rendicontazione,
			@RequestParam(required = false) String identificativo_univoco_regolamento,
			@RequestParam(required = false) String data_regolamento_da,
			@RequestParam(required = false) String data_regolamento_a,
			@RequestParam(required = false) String data_contabile_da,
			@RequestParam(required = false) String data_contabile_a,
			@RequestParam(required = false) String data_valuta_da, @RequestParam(required = false) String data_valuta_a,
			@RequestParam(required = false) String cod_tipo_dovuto, @RequestParam(required = false) String conto,
			@RequestParam(required = false) String importo, @RequestParam(required = false) String codOr1,
			@RequestParam(required = false) String data_valuta_check,
			@RequestParam(required = false) String data_esito_check,
			@RequestParam(required = false) String data_regolamento_check,
			@RequestParam(required = false) String data_contabile_check,
			@RequestParam(required = false) String data_esecuzione_check,
			@RequestParam(required = false) String data_esecuzione_singolo_pagamento_da,
			@RequestParam(required = false) String data_esecuzione_singolo_pagamento_a,
			@RequestParam(required = false) String data_ultimo_aggiornamento_da,
			@RequestParam(required = false) String data_ultimo_aggiornamento_a,
			@RequestParam(required = false) String data_ultimo_aggiornamento_check,
			@RequestParam(required = false) String errorCode, @RequestParam(required = false) String visualizzaNascosti,
			@RequestParam(required = false) Boolean exportSuccess, @RequestParam(required = false) Boolean exportFailed,
			@RequestParam(required = false) String tipoVisualizzazione,
			@RequestParam(required = false) Boolean classificazioneNonValida) {

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

		try {
			if (StringUtils.isNotBlank(cod_tipo_dovuto)
					&& !operatoreEnteTipoDovutoService.isCodTipoDovutoValidoPerOperatoreAndCodIpaEnte(
							utenteTO.getCodFedUserId(), enteTO.getCodIpaEnte(), cod_tipo_dovuto)) {
				mav.setViewName("message");

				MessagesDto messagesDto = new MessagesDto();
				messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.tipoDovutoNonValido"));
				mav.addObject("messagesDto", messagesDto);

				return mav;
			}
		} catch (Exception e) {
			mav.setViewName("message");

			MessagesDto messagesDto = new MessagesDto();
			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.tipoDovutoNonValido"));
			mav.addObject("messagesDto", messagesDto);

			return mav;
		}

		VisualizzaCompletaCommand visualizzaCompletaCommand = new VisualizzaCompletaCommand();
		MessagesDto messagesDto = new MessagesDto();

		if (forceClear != null && forceClear) {
			String codIpa = SecurityContext.getEnte().getCodIpaEnte();
			SecurityContext.setEnteViewFilters(codIpa, SessionVariables.ACTION_VISUALIZZA_TESORERIA, null);
		}
		// if
		// (SecurityContext.getEnteViewFilters(SecurityContext.getEnte().getCodIpaEnte(),
		// SessionVariables.ACTION_VISUALIZZA_TESORERIA) == null) {
		// data_regolamento_check = "on";
		// Calendar cal = Calendar.getInstance();
		// cal.add(Calendar.DAY_OF_MONTH, 5);
		// data_regolamento_a = Constants.DDMMYYYY.format(cal.getTime());
		// cal.add(Calendar.MONTH, -1);
		// data_regolamento_da = Constants.DDMMYYYY.format(cal.getTime());
		//
		// }

		setFilters(request, pg, pgSize, codice_iud, codice_iuv, denominazione_attestante,
				identificativo_univoco_riscossione, codice_identificativo_univoco_versante, anagrafica_versante,
				codice_identificativo_univoco_pagatore, anagrafica_pagatore, causale_versamento,
				data_esito_singolo_pagamento_da, data_esito_singolo_pagamento_a, identificativo_flusso_rendicontazione,
				identificativo_univoco_regolamento, data_regolamento_da, data_regolamento_a, data_contabile_da,
				data_contabile_a, data_valuta_da, data_valuta_a, cod_tipo_dovuto, conto, importo, codOr1,
				data_valuta_check, data_esito_check, data_regolamento_check, data_contabile_check,
				data_esecuzione_singolo_pagamento_da, data_esecuzione_singolo_pagamento_a, data_esecuzione_check,
				data_ultimo_aggiornamento_da, data_ultimo_aggiornamento_a, data_ultimo_aggiornamento_check, errorCode,
				visualizzaNascosti, tipoVisualizzazione, visualizzaCompletaCommand);

		initialize(visualizzaCompletaCommand);

		Date dt_data_esecuzione_singolo_pagamento_da = null;

		if (visualizzaCompletaCommand.getData_esecuzione_check() != null
				&& visualizzaCompletaCommand.getData_esecuzione_check().equals("on")
				&& StringUtils.isNotBlank(visualizzaCompletaCommand.getData_esecuzione_singolo_pagamento_da())) {
			try {
				dt_data_esecuzione_singolo_pagamento_da = Constants.DDMMYYYY
						.parse(visualizzaCompletaCommand.getData_esecuzione_singolo_pagamento_da());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date dt_data_esecuzione_singolo_pagamento_a = null;

		if (visualizzaCompletaCommand.getData_esecuzione_check() != null
				&& visualizzaCompletaCommand.getData_esecuzione_check().equals("on")
				&& StringUtils.isNotBlank(visualizzaCompletaCommand.getData_esecuzione_singolo_pagamento_a())) {
			try {
				dt_data_esecuzione_singolo_pagamento_a = Constants.DDMMYYYY
						.parse(visualizzaCompletaCommand.getData_esecuzione_singolo_pagamento_a());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date dt_data_esito_singolo_pagamento_da = null;

		if (visualizzaCompletaCommand.getData_esito_check() != null
				&& visualizzaCompletaCommand.getData_esito_check().equals("on")
				&& StringUtils.isNotBlank(visualizzaCompletaCommand.getData_esito_singolo_pagamento_da())) {
			try {
				dt_data_esito_singolo_pagamento_da = Constants.DDMMYYYY
						.parse(visualizzaCompletaCommand.getData_esito_singolo_pagamento_da());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date dt_data_esito_singolo_pagamento_a = null;

		if (visualizzaCompletaCommand.getData_esito_check() != null
				&& visualizzaCompletaCommand.getData_esito_check().equals("on")
				&& StringUtils.isNotBlank(visualizzaCompletaCommand.getData_esito_singolo_pagamento_a())) {
			try {
				dt_data_esito_singolo_pagamento_a = Constants.DDMMYYYY
						.parse(visualizzaCompletaCommand.getData_esito_singolo_pagamento_a());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date dt_data_regolamento_da = null;

		if (visualizzaCompletaCommand.getData_regolamento_check() != null
				&& visualizzaCompletaCommand.getData_regolamento_check().equals("on")
				&& StringUtils.isNotBlank(visualizzaCompletaCommand.getData_regolamento_da())) {
			try {
				dt_data_regolamento_da = Constants.DDMMYYYY.parse(visualizzaCompletaCommand.getData_regolamento_da());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date dt_data_regolamento_a = null;

		if (visualizzaCompletaCommand.getData_regolamento_check() != null
				&& visualizzaCompletaCommand.getData_regolamento_check().equals("on")
				&& StringUtils.isNotBlank(visualizzaCompletaCommand.getData_regolamento_a())) {
			try {
				dt_data_regolamento_a = Constants.DDMMYYYY.parse(visualizzaCompletaCommand.getData_regolamento_a());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date dt_data_contabile_da = null;

		if (visualizzaCompletaCommand.getData_contabile_check() != null
				&& visualizzaCompletaCommand.getData_contabile_check().equals("on")
				&& StringUtils.isNotBlank(visualizzaCompletaCommand.getData_contabile_da())) {
			try {
				dt_data_contabile_da = Constants.DDMMYYYY.parse(visualizzaCompletaCommand.getData_contabile_da());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date dt_data_contabile_a = null;

		if (visualizzaCompletaCommand.getData_contabile_check() != null
				&& visualizzaCompletaCommand.getData_contabile_check().equals("on")
				&& StringUtils.isNotBlank(visualizzaCompletaCommand.getData_contabile_a())) {
			try {
				dt_data_contabile_a = Constants.DDMMYYYY.parse(visualizzaCompletaCommand.getData_contabile_a());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date dt_data_valuta_da = null;

		if (visualizzaCompletaCommand.getData_valuta_check() != null
				&& visualizzaCompletaCommand.getData_valuta_check().equals("on")
				&& StringUtils.isNotBlank(visualizzaCompletaCommand.getData_valuta_da())) {
			try {
				dt_data_valuta_da = Constants.DDMMYYYY.parse(visualizzaCompletaCommand.getData_valuta_da());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date dt_data_valuta_a = null;

		if (visualizzaCompletaCommand.getData_valuta_check() != null
				&& visualizzaCompletaCommand.getData_valuta_check().equals("on")
				&& StringUtils.isNotBlank(visualizzaCompletaCommand.getData_valuta_a())) {
			try {
				dt_data_valuta_a = Constants.DDMMYYYY.parse(visualizzaCompletaCommand.getData_valuta_a());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date dt_data_ultimo_aggiornamento_da = null;

		if (visualizzaCompletaCommand.getData_ultimo_aggiornamento_check() != null
				&& visualizzaCompletaCommand.getData_ultimo_aggiornamento_check().equals("on")
				&& StringUtils.isNotBlank(visualizzaCompletaCommand.getData_ultimo_aggiornamento_da())) {
			try {
				dt_data_ultimo_aggiornamento_da = Constants.DDMMYYYY
						.parse(visualizzaCompletaCommand.getData_ultimo_aggiornamento_da());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date dt_data_ultimo_aggiornamento_a = null;

		if (visualizzaCompletaCommand.getData_ultimo_aggiornamento_check() != null
				&& visualizzaCompletaCommand.getData_ultimo_aggiornamento_check().equals("on")
				&& StringUtils.isNotBlank(visualizzaCompletaCommand.getData_ultimo_aggiornamento_a())) {
			try {
				dt_data_ultimo_aggiornamento_a = Constants.DDMMYYYY
						.parse(visualizzaCompletaCommand.getData_ultimo_aggiornamento_a());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Boolean visualizzaNascostiBool = null;
		if (VISUALIZZA_NASCOSTI.TRUE.getValue().equalsIgnoreCase(visualizzaNascosti)) {
			visualizzaNascostiBool = true;
		} else if (VISUALIZZA_NASCOSTI.FALSE.getValue().equalsIgnoreCase(visualizzaNascosti)) {
			visualizzaNascostiBool = false;
		}

		String classificazione = visualizzaCompletaCommand.getTipoErrore();
		if (classificazione.equals(Constants.COD_ERRORE_IUD_RT_IUF_TES)
				|| classificazione.equals(Constants.COD_ERRORE_RT_IUF_TES)
				|| classificazione.equals(Constants.COD_ERRORE_RT_IUF)
				|| classificazione.equals(Constants.COD_ERRORE_RT_NO_IUF)
				|| classificazione.equals(Constants.COD_ERRORE_RT_NO_IUD)
				|| classificazione.equals(Constants.COD_ERRORE_IUD_NO_RT)
				|| classificazione.equals(Constants.COD_ERRORE_IUD_RT_IUF)
				|| classificazione.equals(Constants.COD_ERRORE_RT_TES)) {

			PageDto<VisualizzaCompletaDto> visualizzaCompletaDtoPage = importExportRendicontazioneTesoreriaService
					.getImportExportRendicontazioneTesoreriaPageFunction(utenteTO.getCodFedUserId(),
							enteTO.getCodIpaEnte(), visualizzaCompletaCommand.getCodice_iud(),
							visualizzaCompletaCommand.getCodice_iuv(),
							visualizzaCompletaCommand.getDenominazione_attestante(),
							visualizzaCompletaCommand.getIdentificativo_univoco_riscossione(),
							visualizzaCompletaCommand.getCodice_identificativo_univoco_versante(),
							visualizzaCompletaCommand.getAnagrafica_versante(),
							visualizzaCompletaCommand.getCodice_identificativo_univoco_pagatore(),
							visualizzaCompletaCommand.getAnagrafica_pagatore(),
							visualizzaCompletaCommand.getCausale_versamento(), dt_data_esecuzione_singolo_pagamento_da,
							dt_data_esecuzione_singolo_pagamento_a, dt_data_esito_singolo_pagamento_da,
							dt_data_esito_singolo_pagamento_a,
							visualizzaCompletaCommand.getIdentificativo_flusso_rendicontazione(),
							visualizzaCompletaCommand.getIdentificativo_univoco_regolamento(), dt_data_regolamento_da,
							dt_data_regolamento_a, dt_data_contabile_da, dt_data_contabile_a, dt_data_valuta_da,
							dt_data_valuta_a, dt_data_ultimo_aggiornamento_da, dt_data_ultimo_aggiornamento_a,
							visualizzaCompletaCommand.getCod_tipo_dovuto(), visualizzaCompletaCommand.getImporto(),
							visualizzaCompletaCommand.getConto(), visualizzaCompletaCommand.getCodOr1(),
							visualizzaNascostiBool, visualizzaCompletaCommand.getTipoErrore(),
							visualizzaCompletaCommand.getPage(), visualizzaCompletaCommand.getPageSize());

			mav.addObject("visualizzaCompletaDtoPage", visualizzaCompletaDtoPage);

		} else if (classificazione.equals(Constants.COD_ERRORE_IUF_NO_TES)
				|| classificazione.equals(Constants.COD_ERRORE_IUV_NO_RT)) {

			Sort sort = getRendicontazioneSubsetSort(classificazione);

			/**
			 * Modifica: e' stato sviluppato un nuovo metodo per restituire in
			 * pagina le rendicontazioni
			 **/
			// PageDto<RendicontazioneSubsetDto> rendicontazioneSubsetDtoPage =
			// rendicontazioneSubsetService
			// .getRendicontazioneSubsetPage(enteTO.getCodIpaEnte(),
			// visualizzaCompletaCommand.getIdentificativo_flusso_rendicontazione(),
			// visualizzaCompletaCommand.getIdentificativo_univoco_regolamento(),
			// dt_data_regolamento_da,
			// dt_data_regolamento_a, dt_data_ultimo_aggiornamento_da,
			// dt_data_ultimo_aggiornamento_a,
			// classificazione,
			// visualizzaCompletaCommand.getCod_tipo_dovuto(),utenteTO.getCodFedUserId(),
			// visualizzaNascostiBool,
			// visualizzaCompletaCommand.getPage(),
			// visualizzaCompletaCommand.getPageSize(), sort);

			PageDto<RendicontazioneSubsetDto> rendicontazioneSubsetDtoPage = rendicontazioneSubsetService
					.getRendicontazioneSubsetPageFunction(enteTO.getCodIpaEnte(),
							visualizzaCompletaCommand.getIdentificativo_flusso_rendicontazione(),
							visualizzaCompletaCommand.getIdentificativo_univoco_regolamento(), dt_data_regolamento_da,
							dt_data_regolamento_a, dt_data_ultimo_aggiornamento_da, dt_data_ultimo_aggiornamento_a,
							classificazione, visualizzaCompletaCommand.getCod_tipo_dovuto(), utenteTO.getCodFedUserId(),
							visualizzaNascostiBool, visualizzaCompletaCommand.getPage(),
							visualizzaCompletaCommand.getPageSize(), sort);

			mav.addObject("rendicontazioneSubsetDtoPage", rendicontazioneSubsetDtoPage);
		} else if (classificazione.equals(Constants.COD_ERRORE_TES_NO_IUF_OR_IUV)) {

			PageDto<TesoreriaSubsetDto> tesoreriaSubsetDtoPage = tesoreriaSubsetService.getTesoreriaSubsetPageFunction(
					enteTO.getCodIpaEnte(), dt_data_contabile_da, dt_data_contabile_a, dt_data_valuta_da,
					dt_data_valuta_a, dt_data_ultimo_aggiornamento_da, dt_data_ultimo_aggiornamento_a,
					visualizzaCompletaCommand.getImporto(), visualizzaCompletaCommand.getConto(),
					visualizzaCompletaCommand.getCodOr1(), classificazione, visualizzaNascostiBool,
					visualizzaCompletaCommand.getCodice_iuv(),
					visualizzaCompletaCommand.getIdentificativo_flusso_rendicontazione(),
					visualizzaCompletaCommand.getPage(), visualizzaCompletaCommand.getPageSize());

			mav.addObject("tesoreriaSubsetDtoPage", tesoreriaSubsetDtoPage);
		} else if (classificazione.equals(Constants.COD_ERRORE_TES_NO_MATCH)) {
			PageDto<TesoreriaNoMatchSubsetDto> tesoreriaNoMatchSubsetDtoPage = tesoreriaSubsetService
					.getTesoreriaNoMatchSubsetPageFunction(enteTO.getCodIpaEnte(), dt_data_contabile_da,
							dt_data_contabile_a, dt_data_valuta_da, dt_data_valuta_a, dt_data_ultimo_aggiornamento_da,
							dt_data_ultimo_aggiornamento_a, visualizzaCompletaCommand.getCausale_versamento(),
							visualizzaCompletaCommand.getImporto(), visualizzaCompletaCommand.getConto(),
							visualizzaCompletaCommand.getCodOr1(), visualizzaCompletaCommand.getTipoErrore(),
							visualizzaNascostiBool, visualizzaCompletaCommand.getPage(),
							visualizzaCompletaCommand.getPageSize());
			mav.addObject("tesoreriaSubsetDtoPage", tesoreriaNoMatchSubsetDtoPage);
		} else if (classificazione.equals(Constants.COD_ERRORE_IUF_TES_DIV_IMP)) {
			PageDto<RendicontazioneTesoreriaSubsetDto> rendicontazioneTesoreriaSubsetDtoPage = rendicontazioneTesoreriaSubsetService
					.getRendicontazioneTesoreriaSubsetPageFunction(enteTO.getCodIpaEnte(),
							visualizzaCompletaCommand.getIdentificativo_flusso_rendicontazione(),
							visualizzaCompletaCommand.getIdentificativo_univoco_regolamento(), dt_data_regolamento_da,
							dt_data_regolamento_a, dt_data_contabile_da, dt_data_contabile_a, dt_data_valuta_da,
							dt_data_valuta_a, dt_data_ultimo_aggiornamento_da, dt_data_ultimo_aggiornamento_a,
							visualizzaCompletaCommand.getCausale_versamento(), visualizzaCompletaCommand.getImporto(),
							visualizzaCompletaCommand.getConto(), visualizzaCompletaCommand.getCodOr1(),
							null, utenteTO.getCodFedUserId(),
							visualizzaNascostiBool, visualizzaCompletaCommand.getTipoErrore(),
							visualizzaCompletaCommand.getPage(), visualizzaCompletaCommand.getPageSize());
			mav.addObject("rendicontazioneTesoreriaSubsetDtoPage", rendicontazioneTesoreriaSubsetDtoPage);
		}

		if (!visualizzaCompletaCommand.getTipoErrore().isEmpty()) {
			messagesDto.getInformationMessages().add(new DynamicMessageDto(
					"mypivot.classificazioni." + visualizzaCompletaCommand.getTipoErrore() + ".info"));
		}

		if (exportSuccess != null && exportSuccess) {
			messagesDto.getSuccessMessages().add(new DynamicMessageDto("mypivot.export.success"));
		} else if (exportFailed != null && exportFailed) {
			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.export.errore"));
		} else if (classificazioneNonValida != null && classificazioneNonValida) {
			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.export.errore.classificazioneNonValida"));
		}

		List<EnteTipoDovutoTO> enteTipoDovutos = operatoreEnteTipoDovutoService
				.getListaEnteTipoDovutoForOperatoreAndCodIpaEnte(utenteTO.getCodFedUserId(), enteTO.getCodIpaEnte());

		mav.addObject("listaTipiDovutoPerEnte", enteTipoDovutos);
		List<ErrorTypeDto> listaClassificazioni = getClassificazioniList(enteTO.getFlgPagati(),
				enteTO.getFlgTesoreria(), visualizzaCompletaCommand.getTipoVisualizzazione());
		mav.addObject("listaClassificazioni", listaClassificazioni);

		if (visualizzaCompletaCommand.getTipoVisualizzazione()
				.equals(Constants.TIPO_VISUALIZZAZIONE.RICONCILIAZIONE.getValue())) {
			mav.addObject("isRiconciliazione", true);
			mav.addObject("isAnomalie", false);
			mav.addObject("tipoVisualizzazione", Constants.TIPO_VISUALIZZAZIONE.RICONCILIAZIONE.getValue());
		}
		if (visualizzaCompletaCommand.getTipoVisualizzazione()
				.equals(Constants.TIPO_VISUALIZZAZIONE.ANOMALIE.getValue())) {
			mav.addObject("isRiconciliazione", false);
			mav.addObject("isAnomalie", true);
			mav.addObject("tipoVisualizzazione", Constants.TIPO_VISUALIZZAZIONE.ANOMALIE.getValue());
		}

		List<String> listaVersioniTracciato = Utilities.versioneTracciatoToList();
		mav.addObject("versioniTracciato", listaVersioniTracciato);

		mav.addObject("flgPagati", enteTO.getFlgPagati());
		mav.addObject("flgTesoreria", enteTO.getFlgTesoreria());

		mav.addObject("numCols", calcolaSpan(enteTO.getFlgPagati(), enteTO.getFlgTesoreria()));

		mav.addObject("messagesDto", messagesDto);
		mav.addObject(visualizzaCompletaCommand);

		mav.addObject("exportRecordThreshold", Long.valueOf(env.getProperty("mypivot.export.record.threshold")));

		mav.setViewName("visualizzaCompleta");

		return mav;
	}

	private int calcolaSpan(boolean flgPagati, boolean flgTesoreria) {
		if (flgPagati && flgTesoreria) {
			return 3;
		} else if (!flgPagati && !flgTesoreria) {
			return 6;
		} else {
			return 4;
		}
	}

	private Sort getVisualizzaCompletaSort(String errorCode) {
		Sort sort = null;
		if (errorCode.equals(Constants.COD_ERRORE_IUD_RT_IUF_TES) || errorCode.equals(Constants.COD_ERRORE_RT_IUF_TES)
				|| errorCode.equals(Constants.COD_ERRORE_RT_IUF) || errorCode.equals(Constants.COD_ERRORE_IUD_RT_IUF)) {
			sort = new Sort(new Order(Direction.ASC, "dtDataEsitoSingoloPagamento"),
					new Order(Direction.ASC, "id.codiceIuv"), new Order(Direction.ASC, "codiceIud"));
		} else if (errorCode.equals(Constants.COD_ERRORE_RT_NO_IUF)) {
			sort = new Sort(new Order(Direction.ASC, "dtDataEsitoSingoloPagamento"),
					new Order(Direction.ASC, "id.codiceIuv"), new Order(Direction.ASC, "codiceIud"));
		} else if (errorCode.equals(Constants.COD_ERRORE_RT_NO_IUD)) {
			sort = new Sort(new Order(Direction.ASC, "dtDataEsitoSingoloPagamento"),
					new Order(Direction.ASC, "id.codiceIuv"), new Order(Direction.ASC, "codiceIud"));
		} else if (errorCode.equals(Constants.COD_ERRORE_IUD_NO_RT)) {
			sort = new Sort(new Order(Direction.ASC, "dtDataEsecuzionePagamento"),
					new Order(Direction.ASC, "codiceIud"));
		}

		return sort;
	}

	private Sort getRendicontazioneSubsetSort(String errorCode) {
		return new Sort(new Order(Direction.ASC, "dtDataRegolamento"),
				new Order(Direction.ASC, "id.identificativoFlussoRendicontazione"));
	}

	private Sort getTesoreriaSubsetSort(String classificazione) {
		return new Sort(new Order(Direction.ASC, "dtDataValuta"),
				new Order(Direction.ASC, "id.identificativoFlussoRendicontazione"),
				new Order(Direction.ASC, "id.codiceIuv"));
	}

	@RequestMapping(value = { "/visualizzaCompleta.html" }, method = RequestMethod.POST)
	public ModelAndView post(
			@ModelAttribute("visualizzaCompletaCommand") VisualizzaCompletaCommand visualizzaCompletaCommand,
			HttpServletRequest request) {

		ModelAndView mav = new ModelAndView();
		MessagesDto messagesDto = new MessagesDto();

		if (SecurityContext.getRoles() == null) {
			return new ModelAndView(new RedirectView(request.getContextPath() + "/protected/sceltaEnte.html"));
		}

		if (!SecurityContext.getRoles().contains(Constants.RUOLO_VISUALIZZATORE)) {
			mav.setViewName("message");

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

			messagesDto = new MessagesDto();
			messagesDto.getErrorMessages()
					.add(new DynamicMessageDto("mypivot.messages.error.nessunTipoDovutoAssegnato"));
			mav.addObject("messagesDto", messagesDto);

			return mav;
		}

		try {
			if (visualizzaCompletaCommand.getImporto() != null && !visualizzaCompletaCommand.getImporto().isEmpty()) {

				String importoDigitato = visualizzaCompletaCommand.getImporto();
				importoDigitato = importoDigitato.replace(",", ".");
				visualizzaCompletaCommand.setImporto(importoDigitato);

				Double.parseDouble(visualizzaCompletaCommand.getImporto());
			}
		} catch (Exception ex) {
			mav.setViewName("message");

			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.invalidImporto"));
			mav.addObject("messagesDto", messagesDto);

			return mav;
		}

		try {
			if (StringUtils.isNotBlank(visualizzaCompletaCommand.getCod_tipo_dovuto())
					&& !operatoreEnteTipoDovutoService.isCodTipoDovutoValidoPerOperatoreAndCodIpaEnte(
							utenteTO.getCodFedUserId(), enteTO.getCodIpaEnte(),
							visualizzaCompletaCommand.getCod_tipo_dovuto())) {
				mav.setViewName("message");

				messagesDto = new MessagesDto();
				messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.tipoDovutoNonValido"));
				mav.addObject("messagesDto", messagesDto);

				return mav;
			}
		} catch (Exception e) {
			mav.setViewName("message");

			messagesDto = new MessagesDto();
			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.tipoDovutoNonValido"));
			mav.addObject("messagesDto", messagesDto);

			return mav;
		}

		initialize(visualizzaCompletaCommand);

		Date dt_data_esecuzione_singolo_pagamento_da = null;

		if ((visualizzaCompletaCommand.getData_esecuzione_check() != null
				&& visualizzaCompletaCommand.getData_esecuzione_check().equals("on"))
				&& StringUtils.isNotBlank(visualizzaCompletaCommand.getData_esecuzione_singolo_pagamento_da())) {
			try {
				dt_data_esecuzione_singolo_pagamento_da = Constants.DDMMYYYY
						.parse(visualizzaCompletaCommand.getData_esecuzione_singolo_pagamento_da());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date dt_data_esecuzione_singolo_pagamento_a = null;

		if ((visualizzaCompletaCommand.getData_esecuzione_check() != null
				&& visualizzaCompletaCommand.getData_esecuzione_check().equals("on"))
				&& StringUtils.isNotBlank(visualizzaCompletaCommand.getData_esecuzione_singolo_pagamento_a())) {
			try {
				dt_data_esecuzione_singolo_pagamento_a = Constants.DDMMYYYY
						.parse(visualizzaCompletaCommand.getData_esecuzione_singolo_pagamento_a());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date dt_data_esito_singolo_pagamento_da = null;

		if ((visualizzaCompletaCommand.getData_esito_check() != null
				&& visualizzaCompletaCommand.getData_esito_check().equals("on"))
				&& StringUtils.isNotBlank(visualizzaCompletaCommand.getData_esito_singolo_pagamento_da())) {
			try {
				dt_data_esito_singolo_pagamento_da = Constants.DDMMYYYY
						.parse(visualizzaCompletaCommand.getData_esito_singolo_pagamento_da());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date dt_data_esito_singolo_pagamento_a = null;

		if ((visualizzaCompletaCommand.getData_esito_check() != null
				&& visualizzaCompletaCommand.getData_esito_check().equals("on"))
				&& StringUtils.isNotBlank(visualizzaCompletaCommand.getData_esito_singolo_pagamento_a())) {
			try {
				dt_data_esito_singolo_pagamento_a = Constants.DDMMYYYY
						.parse(visualizzaCompletaCommand.getData_esito_singolo_pagamento_a());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date dt_data_regolamento_da = null;

		if ((visualizzaCompletaCommand.getData_regolamento_check() != null
				&& visualizzaCompletaCommand.getData_regolamento_check().equals("on"))
				&& StringUtils.isNotBlank(visualizzaCompletaCommand.getData_regolamento_da())) {
			try {
				dt_data_regolamento_da = Constants.DDMMYYYY.parse(visualizzaCompletaCommand.getData_regolamento_da());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date dt_data_regolamento_a = null;

		if ((visualizzaCompletaCommand.getData_regolamento_check() != null
				&& visualizzaCompletaCommand.getData_regolamento_check().equals("on"))
				&& StringUtils.isNotBlank(visualizzaCompletaCommand.getData_regolamento_a())) {
			try {
				dt_data_regolamento_a = Constants.DDMMYYYY.parse(visualizzaCompletaCommand.getData_regolamento_a());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date dt_data_contabile_da = null;

		if ((visualizzaCompletaCommand.getData_contabile_check() != null
				&& visualizzaCompletaCommand.getData_contabile_check().equals("on"))
				&& StringUtils.isNotBlank(visualizzaCompletaCommand.getData_contabile_da())) {
			try {
				dt_data_contabile_da = Constants.DDMMYYYY.parse(visualizzaCompletaCommand.getData_contabile_da());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date dt_data_contabile_a = null;

		if ((visualizzaCompletaCommand.getData_contabile_check() != null
				&& visualizzaCompletaCommand.getData_contabile_check().equals("on"))
				&& StringUtils.isNotBlank(visualizzaCompletaCommand.getData_contabile_a())) {
			try {
				dt_data_contabile_a = Constants.DDMMYYYY.parse(visualizzaCompletaCommand.getData_contabile_a());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date dt_data_valuta_da = null;

		if ((visualizzaCompletaCommand.getData_valuta_check() != null
				&& visualizzaCompletaCommand.getData_valuta_check().equals("on"))
				&& StringUtils.isNotBlank(visualizzaCompletaCommand.getData_valuta_da())) {
			try {
				dt_data_valuta_da = Constants.DDMMYYYY.parse(visualizzaCompletaCommand.getData_valuta_da());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date dt_data_valuta_a = null;

		if ((visualizzaCompletaCommand.getData_valuta_check() != null
				&& visualizzaCompletaCommand.getData_valuta_check().equals("on"))
				&& StringUtils.isNotBlank(visualizzaCompletaCommand.getData_valuta_a())) {
			try {
				dt_data_valuta_a = Constants.DDMMYYYY.parse(visualizzaCompletaCommand.getData_valuta_a());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date dt_data_ultimo_aggiornamento_da = null;

		if (visualizzaCompletaCommand.getData_ultimo_aggiornamento_check() != null
				&& visualizzaCompletaCommand.getData_ultimo_aggiornamento_check().equals("on")
				&& StringUtils.isNotBlank(visualizzaCompletaCommand.getData_ultimo_aggiornamento_da())) {
			try {
				dt_data_ultimo_aggiornamento_da = Constants.DDMMYYYY
						.parse(visualizzaCompletaCommand.getData_ultimo_aggiornamento_da());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date dt_data_ultimo_aggiornamento_a = null;

		if (visualizzaCompletaCommand.getData_ultimo_aggiornamento_check() != null
				&& visualizzaCompletaCommand.getData_ultimo_aggiornamento_check().equals("on")
				&& StringUtils.isNotBlank(visualizzaCompletaCommand.getData_ultimo_aggiornamento_a())) {
			try {
				dt_data_ultimo_aggiornamento_a = Constants.DDMMYYYY
						.parse(visualizzaCompletaCommand.getData_ultimo_aggiornamento_a());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		visualizzaCompletaCommand.setPage(1);

		Map<String, Object> filtersMap = new HashMap<String, Object>();

		Boolean visualizzaNascostiBool = null;
		if (VISUALIZZA_NASCOSTI.TRUE.getValue().equalsIgnoreCase(visualizzaCompletaCommand.getVisualizzaNascosti())) {
			visualizzaNascostiBool = true;
		} else if (VISUALIZZA_NASCOSTI.FALSE.getValue()
				.equalsIgnoreCase(visualizzaCompletaCommand.getVisualizzaNascosti())) {
			visualizzaNascostiBool = false;
		}

		if (visualizzaCompletaCommand.getTipoErrore().equals(Constants.COD_ERRORE_IUD_RT_IUF_TES)
				|| visualizzaCompletaCommand.getTipoErrore().equals(Constants.COD_ERRORE_RT_IUF_TES)
				|| visualizzaCompletaCommand.getTipoErrore().equals(Constants.COD_ERRORE_RT_IUF)
				|| visualizzaCompletaCommand.getTipoErrore().equals(Constants.COD_ERRORE_RT_NO_IUF)
				|| visualizzaCompletaCommand.getTipoErrore().equals(Constants.COD_ERRORE_RT_NO_IUD)
				|| visualizzaCompletaCommand.getTipoErrore().equals(Constants.COD_ERRORE_IUD_NO_RT)
				|| visualizzaCompletaCommand.getTipoErrore().equals(Constants.COD_ERRORE_IUD_RT_IUF)
				|| visualizzaCompletaCommand.getTipoErrore().equals(Constants.COD_ERRORE_RT_TES)) {

			PageDto<VisualizzaCompletaDto> visualizzaCompletaDtoPage = importExportRendicontazioneTesoreriaService
					.getImportExportRendicontazioneTesoreriaPageFunction(utenteTO.getCodFedUserId(),
							enteTO.getCodIpaEnte(), visualizzaCompletaCommand.getCodice_iud(),
							visualizzaCompletaCommand.getCodice_iuv(),
							visualizzaCompletaCommand.getDenominazione_attestante(),
							visualizzaCompletaCommand.getIdentificativo_univoco_riscossione(),
							visualizzaCompletaCommand.getCodice_identificativo_univoco_versante(),
							visualizzaCompletaCommand.getAnagrafica_versante(),
							visualizzaCompletaCommand.getCodice_identificativo_univoco_pagatore(),
							visualizzaCompletaCommand.getAnagrafica_pagatore(),
							visualizzaCompletaCommand.getCausale_versamento(), dt_data_esecuzione_singolo_pagamento_da,
							dt_data_esecuzione_singolo_pagamento_a, dt_data_esito_singolo_pagamento_da,
							dt_data_esito_singolo_pagamento_a,
							visualizzaCompletaCommand.getIdentificativo_flusso_rendicontazione(),
							visualizzaCompletaCommand.getIdentificativo_univoco_regolamento(), dt_data_regolamento_da,
							dt_data_regolamento_a, dt_data_contabile_da, dt_data_contabile_a, dt_data_valuta_da,
							dt_data_valuta_a, dt_data_ultimo_aggiornamento_da, dt_data_ultimo_aggiornamento_a,
							visualizzaCompletaCommand.getCod_tipo_dovuto(), visualizzaCompletaCommand.getImporto(),
							visualizzaCompletaCommand.getConto(), visualizzaCompletaCommand.getCodOr1(),
							visualizzaNascostiBool, visualizzaCompletaCommand.getTipoErrore(),
							visualizzaCompletaCommand.getPage(), visualizzaCompletaCommand.getPageSize());

			mav.addObject("visualizzaCompletaDtoPage", visualizzaCompletaDtoPage);
			filtersMap.put(SessionVariables.EXPORT_TOTALE_RECORD, visualizzaCompletaDtoPage.getTotalRecords());
		} else if (visualizzaCompletaCommand.getTipoErrore().equals(Constants.COD_ERRORE_IUF_NO_TES)
				|| visualizzaCompletaCommand.getTipoErrore().equals(Constants.COD_ERRORE_IUV_NO_RT)) {

			Sort sort = getRendicontazioneSubsetSort(visualizzaCompletaCommand.getTipoErrore());

			/**
			 * Modifica: e' stato sviluppato un nuovo metodo per restituire in
			 * pagina le rendicontazioni
			 **/
			// PageDto<RendicontazioneSubsetDto> rendicontazioneSubsetDtoPage =
			// rendicontazioneSubsetService
			// .getRendicontazioneSubsetPage(enteTO.getCodIpaEnte(),
			// visualizzaCompletaCommand.getIdentificativo_flusso_rendicontazione(),
			// visualizzaCompletaCommand.getIdentificativo_univoco_regolamento(),
			// dt_data_regolamento_da,
			// dt_data_regolamento_a, dt_data_ultimo_aggiornamento_da,
			// dt_data_ultimo_aggiornamento_a,
			// visualizzaCompletaCommand.getTipoErrore(),
			// visualizzaCompletaCommand.getCod_tipo_dovuto(),utenteTO.getCodFedUserId(),
			// visualizzaNascostiBool,
			// visualizzaCompletaCommand.getPage(),
			// visualizzaCompletaCommand.getPageSize(),
			// sort);

			PageDto<RendicontazioneSubsetDto> rendicontazioneSubsetDtoPage = rendicontazioneSubsetService
					.getRendicontazioneSubsetPageFunction(enteTO.getCodIpaEnte(),
							visualizzaCompletaCommand.getIdentificativo_flusso_rendicontazione(),
							visualizzaCompletaCommand.getIdentificativo_univoco_regolamento(), dt_data_regolamento_da,
							dt_data_regolamento_a, dt_data_ultimo_aggiornamento_da, dt_data_ultimo_aggiornamento_a,
							visualizzaCompletaCommand.getTipoErrore(), visualizzaCompletaCommand.getCod_tipo_dovuto(),
							utenteTO.getCodFedUserId(), visualizzaNascostiBool, visualizzaCompletaCommand.getPage(),
							visualizzaCompletaCommand.getPageSize(), sort);

			mav.addObject("rendicontazioneSubsetDtoPage", rendicontazioneSubsetDtoPage);
		} else if (visualizzaCompletaCommand.getTipoErrore().equals(Constants.COD_ERRORE_TES_NO_IUF_OR_IUV)) {

			PageDto<TesoreriaSubsetDto> tesoreriaSubsetDtoPage = tesoreriaSubsetService.getTesoreriaSubsetPageFunction(
					enteTO.getCodIpaEnte(), dt_data_contabile_da, dt_data_contabile_a, dt_data_valuta_da,
					dt_data_valuta_a, dt_data_ultimo_aggiornamento_da, dt_data_ultimo_aggiornamento_a,
					visualizzaCompletaCommand.getImporto(), visualizzaCompletaCommand.getConto(),
					visualizzaCompletaCommand.getCodOr1(), visualizzaCompletaCommand.getTipoErrore(),
					visualizzaNascostiBool, visualizzaCompletaCommand.getCodice_iuv(),
					visualizzaCompletaCommand.getIdentificativo_flusso_rendicontazione(),
					visualizzaCompletaCommand.getPage(), visualizzaCompletaCommand.getPageSize());

			mav.addObject("tesoreriaSubsetDtoPage", tesoreriaSubsetDtoPage);
		} else if (visualizzaCompletaCommand.getTipoErrore().equals(Constants.COD_ERRORE_TES_NO_MATCH)) {
			PageDto<TesoreriaNoMatchSubsetDto> tesoreriaNoMatchSubsetDtoPage = tesoreriaSubsetService
					.getTesoreriaNoMatchSubsetPageFunction(enteTO.getCodIpaEnte(), dt_data_contabile_da,
							dt_data_contabile_a, dt_data_valuta_da, dt_data_valuta_a, dt_data_ultimo_aggiornamento_da,
							dt_data_ultimo_aggiornamento_a, visualizzaCompletaCommand.getCausale_versamento(),
							visualizzaCompletaCommand.getImporto(), visualizzaCompletaCommand.getConto(),
							visualizzaCompletaCommand.getCodOr1(), visualizzaCompletaCommand.getTipoErrore(),
							visualizzaNascostiBool, visualizzaCompletaCommand.getPage(),
							visualizzaCompletaCommand.getPageSize());
			mav.addObject("tesoreriaSubsetDtoPage", tesoreriaNoMatchSubsetDtoPage);
		} else if (visualizzaCompletaCommand.getTipoErrore().equals(Constants.COD_ERRORE_IUF_TES_DIV_IMP)) {
			PageDto<RendicontazioneTesoreriaSubsetDto> rendicontazioneTesoreriaSubsetDtoPage = rendicontazioneTesoreriaSubsetService
					.getRendicontazioneTesoreriaSubsetPageFunction(enteTO.getCodIpaEnte(),
							visualizzaCompletaCommand.getIdentificativo_flusso_rendicontazione(),
							visualizzaCompletaCommand.getIdentificativo_univoco_regolamento(), dt_data_regolamento_da,
							dt_data_regolamento_a, dt_data_contabile_da, dt_data_contabile_a, dt_data_valuta_da,
							dt_data_valuta_a, dt_data_ultimo_aggiornamento_da, dt_data_ultimo_aggiornamento_a,
							visualizzaCompletaCommand.getCausale_versamento(), visualizzaCompletaCommand.getImporto(),
							visualizzaCompletaCommand.getConto(), visualizzaCompletaCommand.getCodOr1(),
							null, utenteTO.getCodFedUserId(),
							visualizzaNascostiBool, visualizzaCompletaCommand.getTipoErrore(),
							visualizzaCompletaCommand.getPage(), visualizzaCompletaCommand.getPageSize());
			mav.addObject("rendicontazioneTesoreriaSubsetDtoPage", rendicontazioneTesoreriaSubsetDtoPage);
		}

		if (!visualizzaCompletaCommand.getTipoErrore().isEmpty()) {
			messagesDto.getInformationMessages().add(new DynamicMessageDto(
					"mypivot.classificazioni." + visualizzaCompletaCommand.getTipoErrore() + ".info"));
		}
		List<EnteTipoDovutoTO> enteTipoDovutos = operatoreEnteTipoDovutoService
				.getListaEnteTipoDovutoForOperatoreAndCodIpaEnte(utenteTO.getCodFedUserId(), enteTO.getCodIpaEnte());
		mav.addObject("listaTipiDovutoPerEnte", enteTipoDovutos);
		List<ErrorTypeDto> listaClassificazioni = getClassificazioniList(enteTO.getFlgPagati(),
				enteTO.getFlgTesoreria(), visualizzaCompletaCommand.getTipoVisualizzazione());
		mav.addObject("listaClassificazioni", listaClassificazioni);

		if (visualizzaCompletaCommand.getTipoVisualizzazione()
				.equals(Constants.TIPO_VISUALIZZAZIONE.RICONCILIAZIONE.getValue())) {
			mav.addObject("isRiconciliazione", true);
			mav.addObject("isAnomalie", false);
			mav.addObject("tipoVisualizzazione", Constants.TIPO_VISUALIZZAZIONE.RICONCILIAZIONE.getValue());
		}
		if (visualizzaCompletaCommand.getTipoVisualizzazione()
				.equals(Constants.TIPO_VISUALIZZAZIONE.ANOMALIE.getValue())) {
			mav.addObject("isRiconciliazione", false);
			mav.addObject("isAnomalie", true);
			mav.addObject("tipoVisualizzazione", Constants.TIPO_VISUALIZZAZIONE.ANOMALIE.getValue());
		}

		List<String> listaVersioniTracciato = Utilities.versioneTracciatoToList();
		mav.addObject("versioniTracciato", listaVersioniTracciato);

		mav.addObject("flgPagati", enteTO.getFlgPagati());
		mav.addObject("flgTesoreria", enteTO.getFlgTesoreria());
		mav.addObject("numCols", calcolaSpan(enteTO.getFlgPagati(), enteTO.getFlgTesoreria()));
		mav.addObject("messagesDto", messagesDto);
		mav.addObject(visualizzaCompletaCommand);

		mav.setViewName("visualizzaCompleta");

		// save session filter data
		String codIpa = SecurityContext.getEnte().getCodIpaEnte();
		filtersMap.put(SessionVariables.EXPORT_TESORERIA_PG, visualizzaCompletaCommand.getPage());
		filtersMap.put(SessionVariables.EXPORT_TESORERIA_PG_SIZE, visualizzaCompletaCommand.getPageSize());
		filtersMap.put(SessionVariables.EXPORT_TESORERIA_CODICE_IUD, visualizzaCompletaCommand.getCodice_iud());
		filtersMap.put(SessionVariables.EXPORT_TESORERIA_CODICE_IUV, visualizzaCompletaCommand.getCodice_iuv());
		filtersMap.put(SessionVariables.EXPORT_TESORERIA_DENOMINAZIONE_ATTESTANTE,
				visualizzaCompletaCommand.getDenominazione_attestante());
		filtersMap.put(SessionVariables.EXPORT_TESORERIA_IDENTIFICATIVO_UNIVOCO_RISCOSSIONE,
				visualizzaCompletaCommand.getIdentificativo_univoco_riscossione());

		filtersMap.put(SessionVariables.EXPORT_TESORERIA_CODICE_IDENTIFICATIVO_UNIVOCO_VERSANTE,
				visualizzaCompletaCommand.getCodice_identificativo_univoco_versante());
		filtersMap.put(SessionVariables.EXPORT_TESORERIA_ANAGRAFICA_VERSANTE,
				visualizzaCompletaCommand.getAnagrafica_versante());
		filtersMap.put(SessionVariables.EXPORT_TESORERIA_CODICE_IDENTIFICATIVO_UNIVOCO_PAGATORE,
				visualizzaCompletaCommand.getCodice_identificativo_univoco_pagatore());
		filtersMap.put(SessionVariables.EXPORT_TESORERIA_ANAGRAFICA_PAGATORE,
				visualizzaCompletaCommand.getAnagrafica_pagatore());
		filtersMap.put(SessionVariables.EXPORT_TESORERIA_CAUSALE_VERSAMENTO,
				visualizzaCompletaCommand.getCausale_versamento());
		filtersMap.put(SessionVariables.EXPORT_TESORERIA_DATA_ESECUZIONE_SINGOLO_PAGAMENTO_DA,
				visualizzaCompletaCommand.getData_esecuzione_singolo_pagamento_da());
		filtersMap.put(SessionVariables.EXPORT_TESORERIA_DATA_ESECUZIONE_SINGOLO_PAGAMENTO_A,
				visualizzaCompletaCommand.getData_esecuzione_singolo_pagamento_a());
		filtersMap.put(SessionVariables.EXPORT_TESORERIA_DATA_ESITO_SINGOLO_PAGAMENTO_DA,
				visualizzaCompletaCommand.getData_esito_singolo_pagamento_da());
		filtersMap.put(SessionVariables.EXPORT_TESORERIA_DATA_ESITO_SINGOLO_PAGAMENTO_A,
				visualizzaCompletaCommand.getData_esito_singolo_pagamento_a());
		filtersMap.put(SessionVariables.EXPORT_TESORERIA_IDENTIFICATIVO_FLUSSO_TESORERIA,
				visualizzaCompletaCommand.getIdentificativo_flusso_rendicontazione());
		filtersMap.put(SessionVariables.EXPORT_TESORERIA_IDENTIFICATIVO_UNIVOCO_REGOLAMENTO,
				visualizzaCompletaCommand.getIdentificativo_univoco_regolamento());
		filtersMap.put(SessionVariables.EXPORT_TESORERIA_DATA_REGOLAMENTO_DA,
				visualizzaCompletaCommand.getData_regolamento_da());
		filtersMap.put(SessionVariables.EXPORT_TESORERIA_DATA_REGOLAMENTO_A,
				visualizzaCompletaCommand.getData_regolamento_a());

		filtersMap.put(SessionVariables.EXPORT_TESORERIA_DATA_CONTABILE_DA,
				visualizzaCompletaCommand.getData_contabile_da());
		filtersMap.put(SessionVariables.EXPORT_TESORERIA_DATA_CONTABILE_A,
				visualizzaCompletaCommand.getData_contabile_a());
		filtersMap.put(SessionVariables.EXPORT_TESORERIA_DATA_VALUTA_DA, visualizzaCompletaCommand.getData_valuta_da());
		filtersMap.put(SessionVariables.EXPORT_TESORERIA_DATA_VALUTA_A, visualizzaCompletaCommand.getData_valuta_a());
		filtersMap.put(SessionVariables.EXPORT_TESORERIA_COD_TIPO_DOVUTO,
				visualizzaCompletaCommand.getCod_tipo_dovuto());
		filtersMap.put(SessionVariables.EXPORT_TESORERIA_CONTO, visualizzaCompletaCommand.getConto());
		filtersMap.put(SessionVariables.EXPORT_TESORERIA_COD_OR1, visualizzaCompletaCommand.getCodOr1());
		filtersMap.put(SessionVariables.EXPORT_TESORERIA_IMPORTO, visualizzaCompletaCommand.getImporto());
		filtersMap.put(SessionVariables.EXPORT_TESORERIA_DATA_ESECUZIONE_CHECK,
				visualizzaCompletaCommand.getData_esecuzione_check());
		filtersMap.put(SessionVariables.EXPORT_TESORERIA_DATA_ESITO_CHECK,
				visualizzaCompletaCommand.getData_esito_check());
		filtersMap.put(SessionVariables.EXPORT_TESORERIA_DATA_REGOLAMENTO_CHECK,
				visualizzaCompletaCommand.getData_regolamento_check());
		filtersMap.put(SessionVariables.EXPORT_TESORERIA_DATA_VALUTA_CHECK,
				visualizzaCompletaCommand.getData_valuta_check());
		filtersMap.put(SessionVariables.EXPORT_TESORERIA_DATA_CONTABILE_CHECK,
				visualizzaCompletaCommand.getData_contabile_check());
		filtersMap.put(SessionVariables.EXPORT_TESORERIA_ERROR_CODE, visualizzaCompletaCommand.getTipoErrore());
		filtersMap.put(SessionVariables.EXPORT_RICONCILIAZIONE_DATA_ULTIMO_AGGIORNAMENTO_CHECK,
				visualizzaCompletaCommand.getData_ultimo_aggiornamento_check());
		filtersMap.put(SessionVariables.EXPORT_RICONCILIAZIONE_DATA_ULTIMO_AGGIORNAMENTO_DA,
				visualizzaCompletaCommand.getData_ultimo_aggiornamento_da());
		filtersMap.put(SessionVariables.EXPORT_RICONCILIAZIONE_DATA_ULTIMO_AGGIORNAMENTO_A,
				visualizzaCompletaCommand.getData_ultimo_aggiornamento_a());

		filtersMap.put(SessionVariables.EXPORT_SEGNALAZIONI_VISUALIZZA_NASCOSTI,
				visualizzaCompletaCommand.getVisualizzaNascosti());

		filtersMap.put(SessionVariables.EXPORT_TESORERIA_TIPO_VISUALIZZAZIONE,
				visualizzaCompletaCommand.getTipoVisualizzazione());

		SecurityContext.setEnteViewFilters(codIpa, SessionVariables.ACTION_VISUALIZZA_TESORERIA, filtersMap);

		mav.addObject("exportRecordThreshold", Long.valueOf(env.getProperty("mypivot.export.record.threshold")));

		return mav;
	}

	@Deprecated
	@RequestMapping(value = { "/esportaCSVCompleta.html" }, method = RequestMethod.GET)
	public void exportCSVCompleta(HttpServletRequest request, HttpServletResponse response) throws Exception {

		EnteTO enteTO = SecurityContext.getEnte();
		UtenteTO utenteTO = SecurityContext.getUtente();
		Map<String, Object> filtersMap = SecurityContext.getEnteViewFilters(enteTO.getCodIpaEnte(),
				SessionVariables.ACTION_VISUALIZZA_TESORERIA);
		// GET session filter data
		// request.getSession().setAttribute(SessionVariables.EXPORT_TESORERIA_PG,
		// visualizzaTesoreriaCommand.getPage());
		// get.setAttribute(SessionVariables.EXPORT_TESORERIA_PG_SIZE,
		// visualizzaTesoreriaCommand.getPageSize());
		String codIUD = (String) filtersMap.get(SessionVariables.EXPORT_TESORERIA_CODICE_IUD);
		String codIUV = (String) filtersMap.get(SessionVariables.EXPORT_TESORERIA_CODICE_IUV);
		String denominazioneAttestante = (String) filtersMap
				.get(SessionVariables.EXPORT_TESORERIA_DENOMINAZIONE_ATTESTANTE);
		String identificativoUnivocoRiscossione = (String) filtersMap
				.get(SessionVariables.EXPORT_TESORERIA_IDENTIFICATIVO_UNIVOCO_RISCOSSIONE);
		String idVersante = (String) filtersMap
				.get(SessionVariables.EXPORT_TESORERIA_CODICE_IDENTIFICATIVO_UNIVOCO_VERSANTE);
		String anagraficaVersante = (String) filtersMap.get(SessionVariables.EXPORT_TESORERIA_ANAGRAFICA_VERSANTE);
		String idPagatore = (String) filtersMap
				.get(SessionVariables.EXPORT_TESORERIA_CODICE_IDENTIFICATIVO_UNIVOCO_PAGATORE);
		String anagraficaPagatore = (String) filtersMap.get(SessionVariables.EXPORT_TESORERIA_ANAGRAFICA_PAGATORE);
		String causaleVersamento = (String) filtersMap.get(SessionVariables.EXPORT_TESORERIA_CAUSALE_VERSAMENTO);
		String idFlussoRend = (String) filtersMap
				.get(SessionVariables.EXPORT_TESORERIA_IDENTIFICATIVO_FLUSSO_TESORERIA);
		String identificativoUnivocoRegolamento = (String) filtersMap
				.get(SessionVariables.EXPORT_TESORERIA_IDENTIFICATIVO_UNIVOCO_REGOLAMENTO);
		String codTipoDovuto = (String) filtersMap.get(SessionVariables.EXPORT_TESORERIA_COD_TIPO_DOVUTO);
		String importo = (String) filtersMap.get(SessionVariables.EXPORT_TESORERIA_IMPORTO);
		String codOR1 = (String) filtersMap.get(SessionVariables.EXPORT_TESORERIA_COD_OR1);
		String conto = (String) filtersMap.get(SessionVariables.EXPORT_TESORERIA_CONTO);
		String esecuzioneCheck = (String) filtersMap.get(SessionVariables.EXPORT_TESORERIA_DATA_ESECUZIONE_CHECK);
		String esitoCheck = (String) filtersMap.get(SessionVariables.EXPORT_TESORERIA_DATA_ESITO_CHECK);
		String regolamentoCheck = (String) filtersMap.get(SessionVariables.EXPORT_TESORERIA_DATA_REGOLAMENTO_CHECK);
		String valutaCheck = (String) filtersMap.get(SessionVariables.EXPORT_TESORERIA_DATA_VALUTA_CHECK);
		String contabileCheck = (String) filtersMap.get(SessionVariables.EXPORT_TESORERIA_DATA_CONTABILE_CHECK);
		String visualizzaNascosti = (String) filtersMap.get(SessionVariables.EXPORT_SEGNALAZIONI_VISUALIZZA_NASCOSTI);

		String errorCode = (String) filtersMap.get(SessionVariables.EXPORT_TESORERIA_ERROR_CODE);

		Long recordTotali = null;

		if (!(errorCode.equals(Constants.COD_ERRORE_IUD_RT_IUF) || errorCode.equals(Constants.COD_ERRORE_RT_IUF_TES)
				|| errorCode.equals(Constants.COD_ERRORE_IUD_RT_IUF_TES))) {
			response.sendRedirect(request.getContextPath() + "/protected/visualizzaCompleta.html");
			return;
		}

		if (errorCode.equals(Constants.COD_ERRORE_IUD_RT_IUF_TES) || errorCode.equals(Constants.COD_ERRORE_RT_IUF_TES)
				|| errorCode.equals(Constants.COD_ERRORE_RT_IUF) || errorCode.equals(Constants.COD_ERRORE_RT_NO_IUF)
				|| errorCode.equals(Constants.COD_ERRORE_RT_NO_IUD) || errorCode.equals(Constants.COD_ERRORE_IUD_NO_RT)
				|| errorCode.equals(Constants.COD_ERRORE_IUD_RT_IUF)) {
			recordTotali = (Long) filtersMap.get(SessionVariables.EXPORT_TOTALE_RECORD);
			if (recordTotali != null
					&& recordTotali.longValue() > Long.parseLong(env.getProperty("mypivot.export.record.threshold"))) {
				response.sendRedirect(request.getContextPath() + "/protected/visualizzaCompleta.html");
				return;
			}
		}

		String dtEsecuzioneDaString = (String) filtersMap
				.get(SessionVariables.EXPORT_TESORERIA_DATA_ESECUZIONE_SINGOLO_PAGAMENTO_DA);
		Date dtEsecuzioneDa = null;

		if (esecuzioneCheck != null && esecuzioneCheck.equals("on") && StringUtils.isNotBlank(dtEsecuzioneDaString)) {
			try {
				dtEsecuzioneDa = Constants.DDMMYYYY.parse(dtEsecuzioneDaString);
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		String dtEsecuzioneAString = (String) filtersMap
				.get(SessionVariables.EXPORT_TESORERIA_DATA_ESECUZIONE_SINGOLO_PAGAMENTO_A);
		Date dtEsecuzioneA = null;

		if (esecuzioneCheck != null && esecuzioneCheck.equals("on") && StringUtils.isNotBlank(dtEsecuzioneAString)) {
			try {
				dtEsecuzioneA = Constants.DDMMYYYY.parse(dtEsecuzioneAString);
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		String dtEsitoDaString = (String) filtersMap
				.get(SessionVariables.EXPORT_TESORERIA_DATA_ESITO_SINGOLO_PAGAMENTO_DA);
		Date dtEsitoDa = null;

		if (esitoCheck != null && esitoCheck.equals("on") && StringUtils.isNotBlank(dtEsitoDaString)) {
			try {
				dtEsitoDa = Constants.DDMMYYYY.parse(dtEsitoDaString);
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		String dtEsitoAString = (String) filtersMap
				.get(SessionVariables.EXPORT_TESORERIA_DATA_ESITO_SINGOLO_PAGAMENTO_A);
		Date dtEsitoA = null;

		if (esitoCheck != null && esitoCheck.equals("on") && StringUtils.isNotBlank(dtEsitoAString)) {
			try {
				dtEsitoA = Constants.DDMMYYYY.parse(dtEsitoAString);
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		String dtRegolamentoDaString = (String) filtersMap.get(SessionVariables.EXPORT_TESORERIA_DATA_REGOLAMENTO_DA);
		Date dtRegolamentoDa = null;

		if (regolamentoCheck != null && regolamentoCheck.equals("on")
				&& StringUtils.isNotBlank(dtRegolamentoDaString)) {
			try {
				dtRegolamentoDa = Constants.DDMMYYYY.parse(dtRegolamentoDaString);
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		String dtRegolamentoAString = (String) filtersMap.get(SessionVariables.EXPORT_TESORERIA_DATA_REGOLAMENTO_A);
		Date dtRegolamentoA = null;

		if (regolamentoCheck != null && regolamentoCheck.equals("on") && StringUtils.isNotBlank(dtRegolamentoAString)) {
			try {
				dtRegolamentoA = Constants.DDMMYYYY.parse(dtRegolamentoAString);
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		String dtValutaDaString = (String) filtersMap.get(SessionVariables.EXPORT_TESORERIA_DATA_VALUTA_DA);
		Date dtValutaDa = null;

		if (valutaCheck != null && valutaCheck.equals("on") && StringUtils.isNotBlank(dtValutaDaString)) {
			try {
				dtValutaDa = Constants.DDMMYYYY.parse(dtValutaDaString);
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		String dtValutaAString = (String) filtersMap.get(SessionVariables.EXPORT_TESORERIA_DATA_VALUTA_A);
		Date dtValutaA = null;

		if (valutaCheck != null && valutaCheck.equals("on") && StringUtils.isNotBlank(dtValutaAString)) {
			try {
				dtValutaA = Constants.DDMMYYYY.parse(dtValutaAString);
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		String dtContabileDaString = (String) filtersMap.get(SessionVariables.EXPORT_TESORERIA_DATA_CONTABILE_DA);
		Date dtContabileDa = null;

		if (contabileCheck != null && contabileCheck.equals("on") && StringUtils.isNotBlank(dtValutaDaString)) {
			try {
				dtContabileDa = Constants.DDMMYYYY.parse(dtContabileDaString);
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		String dtContabileAString = (String) filtersMap.get(SessionVariables.EXPORT_TESORERIA_DATA_CONTABILE_A);
		Date dtContabileA = null;

		if (contabileCheck != null && contabileCheck.equals("on") && StringUtils.isNotBlank(dtContabileAString)) {
			try {
				dtValutaA = Constants.DDMMYYYY.parse(dtContabileAString);
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		List<ExportCSVDtoIFace> rows = null;
		ICsvBeanWriter beanWriter = null;
		String fileName = UUID.randomUUID().toString();
		PropertyDescriptor[] descriptors = null;

		// if (StringUtils.isBlank(errorCode)) {

		Sort sort = getVisualizzaCompletaSort(errorCode);

		rows = importExportRendicontazioneTesoreriaService.getImportExportRendicontazioneTesoreria(
				utenteTO.getCodFedUserId(), enteTO.getCodIpaEnte(), codIUD, codIUV, denominazioneAttestante,
				identificativoUnivocoRiscossione, idVersante, anagraficaVersante, idPagatore, anagraficaPagatore,
				causaleVersamento, dtEsecuzioneDa, dtEsecuzioneA, dtEsitoDa, dtEsitoA, idFlussoRend,
				identificativoUnivocoRegolamento, dtRegolamentoDa, dtRegolamentoA, dtContabileDa, dtContabileA,
				dtValutaDa, dtValutaA, codTipoDovuto, importo, conto, codOR1, visualizzaNascosti, errorCode, sort);

		switch (errorCode) {
		case Constants.COD_ERRORE_IUD_RT_IUF_TES:
			descriptors = BeanUtils.getPropertyDescriptors(ExportCSVIudRtIufTesDto.class);
			break;
		case Constants.COD_ERRORE_RT_IUF_TES:
			descriptors = BeanUtils.getPropertyDescriptors(ExportCSVRtIufTesDto.class);
			break;
		case Constants.COD_ERRORE_RT_IUF:
			descriptors = BeanUtils.getPropertyDescriptors(ExportCSVRtIufDto.class);
			break;
		}

		// } else {
		//
		// }

		try {
			beanWriter = new CsvBeanWriter(new FileWriter(fileName + ".csv"), CsvPreference.EXCEL_PREFERENCE);

			final String[] header = new String[descriptors.length];
			for (int i = 0; i < descriptors.length; i++) {
				PropertyDescriptor propertyDescriptor = descriptors[i];
				String propertyName = propertyDescriptor.getName();

				if (propertyName.equals("class") || propertyName.equals("id"))
					continue;

				header[i] = propertyName;
			}

			// write the header
			beanWriter.writeHeader(header);

			for (Object exportCSVDto : rows) {
				beanWriter.write(exportCSVDto, header);
			}

		} finally {
			if (beanWriter != null) {
				beanWriter.close();
			}
		}

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		File file = new File(fileName + ".csv");
		FileInputStream fileInputStream = new FileInputStream(file);
		Utilities.copyFile(fileInputStream, baos, 4096);

		fileInputStream.close();

		response.setContentType("application/xls");
		response.setHeader("Cache-Control", "private");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + ".csv\"");

		response.getOutputStream().write(baos.toByteArray());
		response.getOutputStream().flush();
		response.getOutputStream().close();

		baos.close();
		file.delete();
	}

	private void setFilters(HttpServletRequest request, String pg, String pgSize, String codice_iud, String codice_iuv,
			String denominazione_attestante, String identificativo_univoco_riscossione,
			String codice_identificativo_univoco_versante, String anagrafica_versante,
			String codice_identificativo_univoco_pagatore, String anagrafica_pagatore, String causale_versamento,
			String data_esito_singolo_pagamento_da, String data_esito_singolo_pagamento_a,
			String identificativo_flusso_rendicontazione, String identificativo_univoco_regolamento,
			String data_regolamento_da, String data_regolamento_a, String data_contabile_da, String data_contabile_a,
			String data_valuta_da, String data_valuta_a, String cod_tipo_dovuto, String conto, String importo,
			String codOr1, String data_valuta_check, String data_esito_check, String data_risultato_check,
			String data_contabile_check, String data_esecuzione_singolo_pagamento_da,
			String data_esecuzione_singolo_pagamento_a, String data_esecuzione_check,
			String data_ultimo_aggiornamento_da, String data_ultimo_aggiornamento_a,
			String data_ultimo_aggiornamento_check, String errorCode, String visualizzaNascosti,
			String tipoVisualizzazione, VisualizzaCompletaCommand visualizzaTesoreriaCommand) {

		String codIpa = SecurityContext.getEnte().getCodIpaEnte();
		Map<String, Object> filtersMap = SecurityContext.getEnteViewFilters(codIpa,
				SessionVariables.ACTION_VISUALIZZA_TESORERIA);
		Object session_pg = filtersMap == null ? null : filtersMap.get(SessionVariables.EXPORT_TESORERIA_PG);
		setFilter_pg(request, pg, session_pg, visualizzaTesoreriaCommand);

		Object session_pgSize = filtersMap == null ? null : filtersMap.get(SessionVariables.EXPORT_TESORERIA_PG_SIZE);
		setFilter_pgSize(request, pgSize, session_pgSize, visualizzaTesoreriaCommand);

		Object session_codice_iud = filtersMap == null ? null
				: filtersMap.get(SessionVariables.EXPORT_TESORERIA_CODICE_IUD);
		setFilter_codice_iud(request, codice_iud, session_codice_iud, visualizzaTesoreriaCommand);

		Object session_codice_iuv = filtersMap == null ? null
				: filtersMap.get(SessionVariables.EXPORT_TESORERIA_CODICE_IUV);
		setFilter_codice_iuv(request, codice_iuv, session_codice_iuv, visualizzaTesoreriaCommand);

		Object session_denominazione_attestante = filtersMap == null ? null
				: filtersMap.get(SessionVariables.EXPORT_TESORERIA_DENOMINAZIONE_ATTESTANTE);
		setFilter_denominazione_attestante(request, denominazione_attestante, session_denominazione_attestante,
				visualizzaTesoreriaCommand);

		Object session_identificativo_univoco_riscossione = filtersMap == null ? null
				: filtersMap.get(SessionVariables.EXPORT_TESORERIA_IDENTIFICATIVO_UNIVOCO_RISCOSSIONE);
		setFilter_identificativo_univoco_riscossione(request, identificativo_univoco_riscossione,
				session_identificativo_univoco_riscossione, visualizzaTesoreriaCommand);

		Object session_codice_identificativo_univoco_versante = filtersMap == null ? null
				: filtersMap.get(SessionVariables.EXPORT_TESORERIA_CODICE_IDENTIFICATIVO_UNIVOCO_VERSANTE);
		setFilter_codice_identificativo_univoco_versante(request, codice_identificativo_univoco_versante,
				session_codice_identificativo_univoco_versante, visualizzaTesoreriaCommand);

		Object session_anagrafica_versante = filtersMap == null ? null
				: filtersMap.get(SessionVariables.EXPORT_TESORERIA_ANAGRAFICA_VERSANTE);
		setFilter_anagrafica_versante(request, anagrafica_versante, session_anagrafica_versante,
				visualizzaTesoreriaCommand);

		Object session_codice_identificativo_univoco_pagatore = filtersMap == null ? null
				: filtersMap.get(SessionVariables.EXPORT_TESORERIA_CODICE_IDENTIFICATIVO_UNIVOCO_PAGATORE);
		setFilter_codice_identificativo_univoco_pagatore(request, codice_identificativo_univoco_pagatore,
				session_codice_identificativo_univoco_pagatore, visualizzaTesoreriaCommand);

		Object session_anagrafica_pagatore = filtersMap == null ? null
				: filtersMap.get(SessionVariables.EXPORT_TESORERIA_ANAGRAFICA_PAGATORE);
		setFilter_anagrafica_pagatore(request, anagrafica_pagatore, session_anagrafica_pagatore,
				visualizzaTesoreriaCommand);

		Object session_causale_versamento = filtersMap == null ? null
				: filtersMap.get(SessionVariables.EXPORT_TESORERIA_CAUSALE_VERSAMENTO);
		setFilter_causale_versamento(request, causale_versamento, session_causale_versamento,
				visualizzaTesoreriaCommand);

		Object session_data_esecuzione_singolo_pagamento_da = filtersMap == null ? null
				: filtersMap.get(SessionVariables.EXPORT_TESORERIA_DATA_ESECUZIONE_SINGOLO_PAGAMENTO_DA);
		setFilter_data_esecuzione_singolo_pagamento_da(request, data_esecuzione_singolo_pagamento_da,
				session_data_esecuzione_singolo_pagamento_da, visualizzaTesoreriaCommand);

		Object session_data_esecuzione_singolo_pagamento_a = filtersMap == null ? null
				: filtersMap.get(SessionVariables.EXPORT_TESORERIA_DATA_ESECUZIONE_SINGOLO_PAGAMENTO_A);
		setFilter_data_esecuzione_singolo_pagamento_a(request, data_esecuzione_singolo_pagamento_a,
				session_data_esecuzione_singolo_pagamento_a, visualizzaTesoreriaCommand);

		Object session_data_esito_singolo_pagamento_da = filtersMap == null ? null
				: filtersMap.get(SessionVariables.EXPORT_TESORERIA_DATA_ESITO_SINGOLO_PAGAMENTO_DA);
		setFilter_data_esito_singolo_pagamento_da(request, data_esito_singolo_pagamento_da,
				session_data_esito_singolo_pagamento_da, visualizzaTesoreriaCommand);

		Object session_data_esito_singolo_pagamento_a = filtersMap == null ? null
				: filtersMap.get(SessionVariables.EXPORT_TESORERIA_DATA_ESITO_SINGOLO_PAGAMENTO_A);
		setFilter_data_esito_singolo_pagamento_a(request, data_esito_singolo_pagamento_a,
				session_data_esito_singolo_pagamento_a, visualizzaTesoreriaCommand);

		Object session_data_contabile_da = filtersMap == null ? null
				: filtersMap.get(SessionVariables.EXPORT_TESORERIA_DATA_CONTABILE_DA);
		setFilter_data_contabile_da(request, data_contabile_da, session_data_contabile_da, visualizzaTesoreriaCommand);

		Object session_data_contabile_a = filtersMap == null ? null
				: filtersMap.get(SessionVariables.EXPORT_TESORERIA_DATA_CONTABILE_A);
		setFilter_data_contabile_a(request, data_contabile_a, session_data_contabile_a, visualizzaTesoreriaCommand);

		Object session_data_valuta_da = filtersMap == null ? null
				: filtersMap.get(SessionVariables.EXPORT_TESORERIA_DATA_VALUTA_DA);
		setFilter_data_valuta_da(request, data_valuta_da, session_data_valuta_da, visualizzaTesoreriaCommand);

		Object session_data_valuta_a = filtersMap == null ? null
				: filtersMap.get(SessionVariables.EXPORT_TESORERIA_DATA_VALUTA_A);
		setFilter_data_valuta_a(request, data_valuta_a, session_data_valuta_a, visualizzaTesoreriaCommand);

		Object session_identificativo_flusso_rendicontazione = filtersMap == null ? null
				: filtersMap.get(SessionVariables.EXPORT_TESORERIA_IDENTIFICATIVO_FLUSSO_TESORERIA);
		setFilter_identificativo_flusso_rendicontazione(request, identificativo_flusso_rendicontazione,
				session_identificativo_flusso_rendicontazione, visualizzaTesoreriaCommand);

		Object session_identificativo_univoco_regolamento = filtersMap == null ? null
				: filtersMap.get(SessionVariables.EXPORT_TESORERIA_IDENTIFICATIVO_UNIVOCO_REGOLAMENTO);
		setFilter_identificativo_univoco_regolamento(request, identificativo_univoco_regolamento,
				session_identificativo_univoco_regolamento, visualizzaTesoreriaCommand);

		Object session_data_regolamento_da = filtersMap == null ? null
				: filtersMap.get(SessionVariables.EXPORT_TESORERIA_DATA_REGOLAMENTO_DA);
		setFilter_data_regolamento_da(request, data_regolamento_da, session_data_regolamento_da,
				visualizzaTesoreriaCommand);

		Object session_data_regolamento_a = filtersMap == null ? null
				: filtersMap.get(SessionVariables.EXPORT_TESORERIA_DATA_REGOLAMENTO_A);
		setFilter_data_regolamento_a(request, data_regolamento_a, session_data_regolamento_a,
				visualizzaTesoreriaCommand);

		Object session_cod_tipo_dovuto = filtersMap == null ? null
				: filtersMap.get(SessionVariables.EXPORT_TESORERIA_COD_TIPO_DOVUTO);
		setFilter_cod_tipo_dovuto(request, cod_tipo_dovuto, session_cod_tipo_dovuto, visualizzaTesoreriaCommand);

		Object session_importo = filtersMap == null ? null : filtersMap.get(SessionVariables.EXPORT_TESORERIA_IMPORTO);
		setFilter_importo(request, importo, session_importo, visualizzaTesoreriaCommand);

		Object session_conto = filtersMap == null ? null : filtersMap.get(SessionVariables.EXPORT_TESORERIA_CONTO);
		setFilter_conto(request, conto, session_conto, visualizzaTesoreriaCommand);

		Object session_codOr1 = filtersMap == null ? null : filtersMap.get(SessionVariables.EXPORT_TESORERIA_COD_OR1);
		setFilter_codOr1(request, codOr1, session_codOr1, visualizzaTesoreriaCommand);

		Object session_dt_valuta_check = filtersMap == null ? null
				: filtersMap.get(SessionVariables.EXPORT_TESORERIA_DATA_VALUTA_CHECK);
		setFilter_dataValutaCheck(request, data_valuta_check, session_dt_valuta_check, visualizzaTesoreriaCommand);

		Object session_dt_risultato_check = filtersMap == null ? null
				: filtersMap.get(SessionVariables.EXPORT_TESORERIA_DATA_REGOLAMENTO_CHECK);
		setFilter_dataRisultatoCheck(request, data_risultato_check, session_dt_risultato_check,
				visualizzaTesoreriaCommand);

		Object session_dt_esecuzione_check = filtersMap == null ? null
				: filtersMap.get(SessionVariables.EXPORT_TESORERIA_DATA_ESECUZIONE_CHECK);
		setFilter_dataEsecuzioneCheck(request, data_esecuzione_check, session_dt_esecuzione_check,
				visualizzaTesoreriaCommand);

		Object session_dt_esito_check = filtersMap == null ? null
				: filtersMap.get(SessionVariables.EXPORT_TESORERIA_DATA_ESITO_CHECK);
		setFilter_dataEsitoCheck(request, data_esito_check, session_dt_esito_check, visualizzaTesoreriaCommand);

		Object session_dt_contabile_check = filtersMap == null ? null
				: filtersMap.get(SessionVariables.EXPORT_TESORERIA_DATA_CONTABILE_CHECK);
		setFilter_datacontabileCheck(request, data_contabile_check, session_dt_contabile_check,
				visualizzaTesoreriaCommand);

		Object session_errorCode = filtersMap == null ? null
				: filtersMap.get(SessionVariables.EXPORT_TESORERIA_ERROR_CODE);
		setFilter_errorCode(request, errorCode, session_errorCode, visualizzaTesoreriaCommand);

		Object session_data_ultimo_aggiornamento_da = filtersMap == null ? null
				: filtersMap.get(SessionVariables.EXPORT_RICONCILIAZIONE_DATA_ULTIMO_AGGIORNAMENTO_DA);
		setFilter_data_ultimo_aggiornamento_da(request, data_ultimo_aggiornamento_da,
				session_data_ultimo_aggiornamento_da, visualizzaTesoreriaCommand);

		Object session_data_ultimo_aggiornamento_a = filtersMap == null ? null
				: filtersMap.get(SessionVariables.EXPORT_RICONCILIAZIONE_DATA_ULTIMO_AGGIORNAMENTO_A);
		setFilter_data_ultimo_aggiornamento_a(request, data_ultimo_aggiornamento_a, session_data_ultimo_aggiornamento_a,
				visualizzaTesoreriaCommand);

		Object session_dt_ultimo_aggiornamento_check = filtersMap == null ? null
				: filtersMap.get(SessionVariables.EXPORT_RICONCILIAZIONE_DATA_ULTIMO_AGGIORNAMENTO_CHECK);
		setFilter_dataUltimoAggiornamentoCheck(request, data_ultimo_aggiornamento_check,
				session_dt_ultimo_aggiornamento_check, visualizzaTesoreriaCommand);

		Object session_segnalazioni_nascosti = filtersMap == null ? null
				: filtersMap.get(SessionVariables.EXPORT_SEGNALAZIONI_VISUALIZZA_NASCOSTI);
		setFilter_segnalazioneNascosti(request, visualizzaNascosti, session_segnalazioni_nascosti,
				visualizzaTesoreriaCommand);

		Object session_tipo_visualizzazione = filtersMap == null ? null
				: filtersMap.get(SessionVariables.EXPORT_TESORERIA_TIPO_VISUALIZZAZIONE);
		setFilter_tipoVisualizzazione(request, tipoVisualizzazione, session_tipo_visualizzazione,
				visualizzaTesoreriaCommand);
	}

	private void setFilter_data_regolamento_a(HttpServletRequest request, String request_data_regolamento_a,
			Object session_data_regolamento_a, VisualizzaCompletaCommand visualizzaTesoreriaCommand) {
		try {
			if (request_data_regolamento_a == null)
				visualizzaTesoreriaCommand.setData_regolamento_a(session_data_regolamento_a.toString());
			else
				visualizzaTesoreriaCommand.setData_regolamento_a(request_data_regolamento_a);
		} catch (NullPointerException e) {
			visualizzaTesoreriaCommand.setData_regolamento_a("");
		}

		setSingleFilter(request, SessionVariables.EXPORT_TESORERIA_DATA_REGOLAMENTO_A,
				visualizzaTesoreriaCommand.getData_regolamento_a());

	}

	private void setFilter_data_regolamento_da(HttpServletRequest request, String request_data_regolamento_da,
			Object session_data_regolamento_da, VisualizzaCompletaCommand visualizzaTesoreriaCommand) {
		try {
			if (request_data_regolamento_da == null)
				visualizzaTesoreriaCommand.setData_regolamento_da(session_data_regolamento_da.toString());
			else
				visualizzaTesoreriaCommand.setData_regolamento_da(request_data_regolamento_da);
		} catch (NullPointerException e) {
			visualizzaTesoreriaCommand.setData_regolamento_da("");
		}
		setSingleFilter(request, SessionVariables.EXPORT_TESORERIA_DATA_REGOLAMENTO_DA,
				visualizzaTesoreriaCommand.getData_regolamento_da());

	}

	private void setFilter_identificativo_univoco_regolamento(HttpServletRequest request,
			String request_identificativo_univoco_regolamento, Object session_identificativo_univoco_regolamento,
			VisualizzaCompletaCommand visualizzaTesoreriaCommand) {
		try {
			if (request_identificativo_univoco_regolamento == null)
				visualizzaTesoreriaCommand
						.setIdentificativo_univoco_regolamento(session_identificativo_univoco_regolamento.toString());
			else
				visualizzaTesoreriaCommand
						.setIdentificativo_univoco_regolamento(request_identificativo_univoco_regolamento);
		} catch (NullPointerException e) {
			visualizzaTesoreriaCommand.setIdentificativo_univoco_regolamento("");
		}
		setSingleFilter(request, SessionVariables.EXPORT_TESORERIA_IDENTIFICATIVO_UNIVOCO_REGOLAMENTO,
				visualizzaTesoreriaCommand.getIdentificativo_univoco_regolamento());

	}

	private void setFilter_identificativo_flusso_rendicontazione(HttpServletRequest request,
			String request_identificativo_flusso_rendicontazione, Object session_identificativo_flusso_rendicontazione,
			VisualizzaCompletaCommand visualizzaTesoreriaCommand) {
		try {
			if (request_identificativo_flusso_rendicontazione == null)
				visualizzaTesoreriaCommand.setIdentificativo_flusso_rendicontazione(
						session_identificativo_flusso_rendicontazione.toString());
			else
				visualizzaTesoreriaCommand
						.setIdentificativo_flusso_rendicontazione(request_identificativo_flusso_rendicontazione);
		} catch (NullPointerException e) {
			visualizzaTesoreriaCommand.setIdentificativo_flusso_rendicontazione("");
		}
		setSingleFilter(request, SessionVariables.EXPORT_TESORERIA_IDENTIFICATIVO_FLUSSO_TESORERIA,
				visualizzaTesoreriaCommand.getIdentificativo_flusso_rendicontazione());

	}

	private void setFilter_data_esecuzione_singolo_pagamento_da(HttpServletRequest request,
			String request_data_esecuzione_singolo_pagamento_da, Object session_data_esecuzione_singolo_pagamento_da,
			VisualizzaCompletaCommand visualizzaTesoreriaCommand) {
		try {
			if (request_data_esecuzione_singolo_pagamento_da == null)
				visualizzaTesoreriaCommand.setData_esecuzione_singolo_pagamento_da(
						session_data_esecuzione_singolo_pagamento_da.toString());
			else
				visualizzaTesoreriaCommand
						.setData_esecuzione_singolo_pagamento_da(request_data_esecuzione_singolo_pagamento_da);
		} catch (NullPointerException e) {
			visualizzaTesoreriaCommand.setData_esecuzione_singolo_pagamento_da("");
		}
		setSingleFilter(request, SessionVariables.EXPORT_TESORERIA_DATA_ESECUZIONE_SINGOLO_PAGAMENTO_DA,
				visualizzaTesoreriaCommand.getData_esecuzione_singolo_pagamento_da());

	}

	private void setFilter_data_esecuzione_singolo_pagamento_a(HttpServletRequest request,
			String request_data_esecuzione_singolo_pagamento_a, Object session_data_esecuzione_singolo_pagamento_a,
			VisualizzaCompletaCommand visualizzaTesoreriaCommand) {
		try {
			if (request_data_esecuzione_singolo_pagamento_a == null)
				visualizzaTesoreriaCommand
						.setData_esecuzione_singolo_pagamento_a(session_data_esecuzione_singolo_pagamento_a.toString());
			else
				visualizzaTesoreriaCommand
						.setData_esecuzione_singolo_pagamento_a(request_data_esecuzione_singolo_pagamento_a);
		} catch (NullPointerException e) {
			visualizzaTesoreriaCommand.setData_esecuzione_singolo_pagamento_a("");
		}
		setSingleFilter(request, SessionVariables.EXPORT_TESORERIA_DATA_ESECUZIONE_SINGOLO_PAGAMENTO_A,
				visualizzaTesoreriaCommand.getData_esecuzione_singolo_pagamento_a());

	}

	private void setFilter_data_esito_singolo_pagamento_da(HttpServletRequest request,
			String request_data_esito_singolo_pagamento_da, Object session_data_esito_singolo_pagamento_da,
			VisualizzaCompletaCommand visualizzaTesoreriaCommand) {
		try {
			if (request_data_esito_singolo_pagamento_da == null)
				visualizzaTesoreriaCommand
						.setData_esito_singolo_pagamento_da(session_data_esito_singolo_pagamento_da.toString());
			else
				visualizzaTesoreriaCommand.setData_esito_singolo_pagamento_da(request_data_esito_singolo_pagamento_da);
		} catch (NullPointerException e) {
			visualizzaTesoreriaCommand.setData_esito_singolo_pagamento_da("");
		}
		setSingleFilter(request, SessionVariables.EXPORT_TESORERIA_DATA_ESITO_SINGOLO_PAGAMENTO_DA,
				visualizzaTesoreriaCommand.getData_esito_singolo_pagamento_da());

	}

	private void setFilter_data_esito_singolo_pagamento_a(HttpServletRequest request,
			String request_data_esito_singolo_pagamento_a, Object session_data_esito_singolo_pagamento_a,
			VisualizzaCompletaCommand visualizzaTesoreriaCommand) {
		try {
			if (request_data_esito_singolo_pagamento_a == null)
				visualizzaTesoreriaCommand
						.setData_esito_singolo_pagamento_a(session_data_esito_singolo_pagamento_a.toString());
			else
				visualizzaTesoreriaCommand.setData_esito_singolo_pagamento_a(request_data_esito_singolo_pagamento_a);
		} catch (NullPointerException e) {
			visualizzaTesoreriaCommand.setData_esito_singolo_pagamento_a("");
		}
		setSingleFilter(request, SessionVariables.EXPORT_TESORERIA_DATA_ESITO_SINGOLO_PAGAMENTO_A,
				visualizzaTesoreriaCommand.getData_esito_singolo_pagamento_a());

	}

	private void setFilter_data_contabile_da(HttpServletRequest request, String request_data_contabile_da,
			Object session_data_contabile_da, VisualizzaCompletaCommand visualizzaTesoreriaCommand) {
		try {
			if (request_data_contabile_da == null)
				visualizzaTesoreriaCommand.setData_contabile_da(session_data_contabile_da.toString());
			else
				visualizzaTesoreriaCommand.setData_contabile_da(request_data_contabile_da);
		} catch (NullPointerException e) {
			visualizzaTesoreriaCommand.setData_contabile_da("");
		}
		setSingleFilter(request, SessionVariables.EXPORT_TESORERIA_DATA_CONTABILE_DA,
				visualizzaTesoreriaCommand.getData_contabile_da());

	}

	private void setFilter_data_contabile_a(HttpServletRequest request, String request_data_contabile_a,
			Object session_data_contabile_a, VisualizzaCompletaCommand visualizzaTesoreriaCommand) {
		try {
			if (request_data_contabile_a == null)
				visualizzaTesoreriaCommand.setData_contabile_a(session_data_contabile_a.toString());
			else
				visualizzaTesoreriaCommand.setData_contabile_a(request_data_contabile_a);
		} catch (NullPointerException e) {
			visualizzaTesoreriaCommand.setData_contabile_a("");
		}
		setSingleFilter(request, SessionVariables.EXPORT_TESORERIA_DATA_CONTABILE_A,
				visualizzaTesoreriaCommand.getData_contabile_da());

	}

	private void setFilter_data_valuta_da(HttpServletRequest request, String request_data_valuta_da,
			Object session_data_valuta_da, VisualizzaCompletaCommand visualizzaTesoreriaCommand) {
		try {
			if (request_data_valuta_da == null)
				visualizzaTesoreriaCommand.setData_valuta_da(session_data_valuta_da.toString());
			else
				visualizzaTesoreriaCommand.setData_valuta_da(request_data_valuta_da);
		} catch (NullPointerException e) {
			visualizzaTesoreriaCommand.setData_valuta_da("");
		}
		setSingleFilter(request, SessionVariables.EXPORT_TESORERIA_DATA_VALUTA_DA,
				visualizzaTesoreriaCommand.getData_valuta_da());

	}

	private void setFilter_data_valuta_a(HttpServletRequest request, String request_data_valuta_a,
			Object session_data_valuta_a, VisualizzaCompletaCommand visualizzaTesoreriaCommand) {
		try {
			if (request_data_valuta_a == null)
				visualizzaTesoreriaCommand.setData_valuta_a(session_data_valuta_a.toString());
			else
				visualizzaTesoreriaCommand.setData_valuta_a(request_data_valuta_a);
		} catch (NullPointerException e) {
			visualizzaTesoreriaCommand.setData_valuta_a("");
		}
		setSingleFilter(request, SessionVariables.EXPORT_TESORERIA_DATA_VALUTA_A,
				visualizzaTesoreriaCommand.getData_valuta_a());

	}

	private void setFilter_causale_versamento(HttpServletRequest request, String request_causale_versamento,
			Object session_causale_versamento, VisualizzaCompletaCommand visualizzaTesoreriaCommand) {
		try {
			if (request_causale_versamento == null)
				visualizzaTesoreriaCommand.setCausale_versamento(session_causale_versamento.toString());
			else
				visualizzaTesoreriaCommand.setCausale_versamento(request_causale_versamento);
		} catch (NullPointerException e) {
			visualizzaTesoreriaCommand.setCausale_versamento("");
		}
		setSingleFilter(request, SessionVariables.EXPORT_TESORERIA_CAUSALE_VERSAMENTO,
				visualizzaTesoreriaCommand.getCausale_versamento());

	}

	private void setFilter_anagrafica_pagatore(HttpServletRequest request, String request_anagrafica_pagatore,
			Object session_anagrafica_pagatore, VisualizzaCompletaCommand visualizzaTesoreriaCommand) {
		try {
			if (request_anagrafica_pagatore == null)
				visualizzaTesoreriaCommand.setAnagrafica_pagatore(session_anagrafica_pagatore.toString());
			else
				visualizzaTesoreriaCommand.setAnagrafica_pagatore(request_anagrafica_pagatore);
		} catch (NullPointerException e) {
			visualizzaTesoreriaCommand.setAnagrafica_pagatore("");
		}
		setSingleFilter(request, SessionVariables.EXPORT_TESORERIA_ANAGRAFICA_PAGATORE,
				visualizzaTesoreriaCommand.getAnagrafica_pagatore());

	}

	private void setFilter_codice_identificativo_univoco_pagatore(HttpServletRequest request,
			String request_codice_identificativo_univoco_pagatore,
			Object session_codice_identificativo_univoco_pagatore,
			VisualizzaCompletaCommand visualizzaTesoreriaCommand) {
		try {
			if (request_codice_identificativo_univoco_pagatore == null)
				visualizzaTesoreriaCommand.setCodice_identificativo_univoco_pagatore(
						session_codice_identificativo_univoco_pagatore.toString());
			else
				visualizzaTesoreriaCommand
						.setCodice_identificativo_univoco_pagatore(request_codice_identificativo_univoco_pagatore);
		} catch (NullPointerException e) {
			visualizzaTesoreriaCommand.setCodice_identificativo_univoco_pagatore("");
		}
		setSingleFilter(request, SessionVariables.EXPORT_TESORERIA_CODICE_IDENTIFICATIVO_UNIVOCO_PAGATORE,
				visualizzaTesoreriaCommand.getCodice_identificativo_univoco_pagatore());

	}

	private void setFilter_anagrafica_versante(HttpServletRequest request, String request_anagrafica_versante,
			Object session_anagrafica_versante, VisualizzaCompletaCommand visualizzaTesoreriaCommand) {
		try {
			if (request_anagrafica_versante == null)
				visualizzaTesoreriaCommand.setAnagrafica_versante(session_anagrafica_versante.toString());
			else
				visualizzaTesoreriaCommand.setAnagrafica_versante(request_anagrafica_versante);
		} catch (NullPointerException e) {
			visualizzaTesoreriaCommand.setAnagrafica_versante("");
		}
		setSingleFilter(request, SessionVariables.EXPORT_TESORERIA_ANAGRAFICA_VERSANTE,
				visualizzaTesoreriaCommand.getAnagrafica_versante());

	}

	private void setFilter_codice_identificativo_univoco_versante(HttpServletRequest request,
			String request_codice_identificativo_univoco_versante,
			Object session_codice_identificativo_univoco_versante,
			VisualizzaCompletaCommand visualizzaTesoreriaCommand) {
		try {
			if (request_codice_identificativo_univoco_versante == null)
				visualizzaTesoreriaCommand.setCodice_identificativo_univoco_versante(
						session_codice_identificativo_univoco_versante.toString());
			else
				visualizzaTesoreriaCommand
						.setCodice_identificativo_univoco_versante(request_codice_identificativo_univoco_versante);
		} catch (NullPointerException e) {
			visualizzaTesoreriaCommand.setCodice_identificativo_univoco_versante("");
		}
		setSingleFilter(request, SessionVariables.EXPORT_TESORERIA_CODICE_IDENTIFICATIVO_UNIVOCO_VERSANTE,
				visualizzaTesoreriaCommand.getCodice_identificativo_univoco_versante());

	}

	private void setFilter_identificativo_univoco_riscossione(HttpServletRequest request,
			String request_identificativo_univoco_riscossione, Object session_identificativo_univoco_riscossione,
			VisualizzaCompletaCommand visualizzaTesoreriaCommand) {
		try {
			if (request_identificativo_univoco_riscossione == null)
				visualizzaTesoreriaCommand
						.setIdentificativo_univoco_riscossione(session_identificativo_univoco_riscossione.toString());
			else
				visualizzaTesoreriaCommand
						.setIdentificativo_univoco_riscossione(request_identificativo_univoco_riscossione);
		} catch (NullPointerException e) {
			visualizzaTesoreriaCommand.setIdentificativo_univoco_riscossione("");
		}
		setSingleFilter(request, SessionVariables.EXPORT_TESORERIA_IDENTIFICATIVO_UNIVOCO_RISCOSSIONE,
				visualizzaTesoreriaCommand.getIdentificativo_univoco_riscossione());

	}

	private void setFilter_denominazione_attestante(HttpServletRequest request, String request_denominazione_attestante,
			Object session_denominazione_attestante, VisualizzaCompletaCommand visualizzaTesoreriaCommand) {
		try {
			if (request_denominazione_attestante == null)
				visualizzaTesoreriaCommand.setDenominazione_attestante(session_denominazione_attestante.toString());
			else
				visualizzaTesoreriaCommand.setDenominazione_attestante(request_denominazione_attestante);
		} catch (NullPointerException e) {
			visualizzaTesoreriaCommand.setDenominazione_attestante("");
		}
		setSingleFilter(request, SessionVariables.EXPORT_TESORERIA_DENOMINAZIONE_ATTESTANTE,
				visualizzaTesoreriaCommand.getDenominazione_attestante());

	}

	private void setFilter_codice_iuv(HttpServletRequest request, String request_codice_iuv, Object session_codice_iuv,
			VisualizzaCompletaCommand visualizzaTesoreriaCommand) {
		try {
			if (request_codice_iuv == null)
				visualizzaTesoreriaCommand.setCodice_iuv(session_codice_iuv.toString());
			else
				visualizzaTesoreriaCommand.setCodice_iuv(request_codice_iuv);
		} catch (NullPointerException e) {
			visualizzaTesoreriaCommand.setCodice_iuv("");
		}
		setSingleFilter(request, SessionVariables.EXPORT_TESORERIA_CODICE_IUV,
				visualizzaTesoreriaCommand.getCodice_iuv());

	}

	private void setFilter_codice_iud(HttpServletRequest request, String request_codice_iud, Object session_codice_iud,
			VisualizzaCompletaCommand visualizzaTesoreriaCommand) {
		try {
			if (request_codice_iud == null)
				visualizzaTesoreriaCommand.setCodice_iud(session_codice_iud.toString());
			else
				visualizzaTesoreriaCommand.setCodice_iud(request_codice_iud);
		} catch (NullPointerException e) {
			visualizzaTesoreriaCommand.setCodice_iud("");
		}
		setSingleFilter(request, SessionVariables.EXPORT_TESORERIA_CODICE_IUD,
				visualizzaTesoreriaCommand.getCodice_iud());

	}

	private void setFilter_pgSize(HttpServletRequest request, String request_pgSize, Object session_pgSize,
			VisualizzaCompletaCommand visualizzaTesoreriaCommand) {
		try {
			if (request_pgSize == null)
				visualizzaTesoreriaCommand.setPageSize(Integer.parseInt(session_pgSize.toString()));
			else
				visualizzaTesoreriaCommand.setPageSize(Integer.parseInt(request_pgSize));
		} catch (NullPointerException e) {
			visualizzaTesoreriaCommand.setPageSize(5);
		} catch (NumberFormatException e) {
			visualizzaTesoreriaCommand.setPageSize(5);
		}
		setSingleFilter(request, SessionVariables.EXPORT_TESORERIA_PG_SIZE, visualizzaTesoreriaCommand.getPageSize());

	}

	private void setFilter_pg(HttpServletRequest request, String request_pg, Object session_pg,
			VisualizzaCompletaCommand visualizzaTesoreriaCommand) {
		try {
			if (request_pg == null)
				visualizzaTesoreriaCommand.setPage(Integer.parseInt(session_pg.toString()));
			else {
				visualizzaTesoreriaCommand.setPage(Integer.parseInt(request_pg));
			}
		} catch (NullPointerException e) {
			visualizzaTesoreriaCommand.setPage(1);
		} catch (NumberFormatException e) {
			visualizzaTesoreriaCommand.setPage(1);
		}

		setSingleFilter(request, SessionVariables.EXPORT_TESORERIA_PG, visualizzaTesoreriaCommand.getPage());

	}

	private void setFilter_cod_tipo_dovuto(HttpServletRequest request, String request_cod_tipo_dovuto,
			Object session_cod_tipo_dovuto, VisualizzaCompletaCommand visualizzaTesoreriaCommand) {
		try {
			if (request_cod_tipo_dovuto == null)
				visualizzaTesoreriaCommand.setCod_tipo_dovuto(session_cod_tipo_dovuto.toString());
			else {
				if (isCodTipoDovutoValidoPerOperatoreEdEnte(request_cod_tipo_dovuto))
					visualizzaTesoreriaCommand.setCod_tipo_dovuto(request_cod_tipo_dovuto);
				else
					visualizzaTesoreriaCommand.setCod_tipo_dovuto("");
			}
		} catch (NullPointerException e) {
			visualizzaTesoreriaCommand.setCod_tipo_dovuto("");
		}
		setSingleFilter(request, SessionVariables.EXPORT_TESORERIA_COD_TIPO_DOVUTO,
				visualizzaTesoreriaCommand.getCod_tipo_dovuto());

	}

	private void setFilter_importo(HttpServletRequest request, String request_importo, Object session_importo,
			VisualizzaCompletaCommand visualizzaTesoreriaCommand) {
		try {
			if (request_importo == null)
				visualizzaTesoreriaCommand.setImporto(session_importo.toString());
			else
				visualizzaTesoreriaCommand.setImporto(request_importo);
		} catch (NullPointerException e) {
			visualizzaTesoreriaCommand.setImporto("");
		}
		setSingleFilter(request, SessionVariables.EXPORT_TESORERIA_IMPORTO, visualizzaTesoreriaCommand.getImporto());

	}

	private void setFilter_conto(HttpServletRequest request, String request_conto, Object session_conto,
			VisualizzaCompletaCommand visualizzaTesoreriaCommand) {
		try {
			if (request_conto == null)
				visualizzaTesoreriaCommand.setConto(session_conto.toString());
			else
				visualizzaTesoreriaCommand.setImporto(request_conto);
		} catch (NullPointerException e) {
			visualizzaTesoreriaCommand.setConto("");
		}
		setSingleFilter(request, SessionVariables.EXPORT_TESORERIA_CONTO, visualizzaTesoreriaCommand.getConto());

	}

	private void setFilter_codOr1(HttpServletRequest request, String request_codOr1, Object session_codOr1,
			VisualizzaCompletaCommand visualizzaTesoreriaCommand) {
		try {
			if (request_codOr1 == null)
				visualizzaTesoreriaCommand.setCodOr1(session_codOr1.toString());
			else
				visualizzaTesoreriaCommand.setCodOr1(request_codOr1);
		} catch (NullPointerException e) {
			visualizzaTesoreriaCommand.setCodOr1("");
		}
		setSingleFilter(request, SessionVariables.EXPORT_TESORERIA_COD_OR1, visualizzaTesoreriaCommand.getCodOr1());
	}

	private void setFilter_datacontabileCheck(HttpServletRequest request, String data_contabile_check,
			Object session_data_contabile_check, VisualizzaCompletaCommand visualizzaTesoreriaCommand) {
		try {
			if (data_contabile_check == null)
				visualizzaTesoreriaCommand.setData_contabile_check(session_data_contabile_check.toString());
			else
				visualizzaTesoreriaCommand.setData_contabile_check(data_contabile_check);
		} catch (NullPointerException e) {
			visualizzaTesoreriaCommand.setData_contabile_check("");
		}
		setSingleFilter(request, SessionVariables.EXPORT_TESORERIA_DATA_CONTABILE_CHECK,
				visualizzaTesoreriaCommand.getData_contabile_check());
	}

	private void setFilter_dataRisultatoCheck(HttpServletRequest request, String data_regolamento_check,
			Object session_data_regolamento_check, VisualizzaCompletaCommand visualizzaTesoreriaCommand) {
		try {
			if (data_regolamento_check == null)
				visualizzaTesoreriaCommand.setData_regolamento_check(session_data_regolamento_check.toString());
			else
				visualizzaTesoreriaCommand.setData_regolamento_check(data_regolamento_check);
		} catch (NullPointerException e) {
			visualizzaTesoreriaCommand.setData_regolamento_check("");
		}
		setSingleFilter(request, SessionVariables.EXPORT_TESORERIA_DATA_REGOLAMENTO_CHECK,
				visualizzaTesoreriaCommand.getData_regolamento_check());

	}

	private void setFilter_dataEsecuzioneCheck(HttpServletRequest request, String data_esecuzione_check,
			Object session_dt_esecuzione_check, VisualizzaCompletaCommand visualizzaTesoreriaCommand) {
		try {
			if (data_esecuzione_check == null)
				visualizzaTesoreriaCommand.setData_esecuzione_check(session_dt_esecuzione_check.toString());
			else
				visualizzaTesoreriaCommand.setData_esecuzione_check(data_esecuzione_check);
		} catch (NullPointerException e) {
			visualizzaTesoreriaCommand.setData_esecuzione_check("");
		}
		setSingleFilter(request, SessionVariables.EXPORT_TESORERIA_DATA_ESECUZIONE_CHECK,
				visualizzaTesoreriaCommand.getData_esecuzione_check());
	}

	private void setFilter_dataEsitoCheck(HttpServletRequest request, String data_esito_check,
			Object session_dt_esito_check, VisualizzaCompletaCommand visualizzaTesoreriaCommand) {
		try {
			if (data_esito_check == null)
				visualizzaTesoreriaCommand.setData_esito_check(session_dt_esito_check.toString());
			else
				visualizzaTesoreriaCommand.setData_esito_check(data_esito_check);
		} catch (NullPointerException e) {
			visualizzaTesoreriaCommand.setData_esito_check("");
		}
		setSingleFilter(request, SessionVariables.EXPORT_TESORERIA_DATA_ESITO_CHECK,
				visualizzaTesoreriaCommand.getData_esito_check());
	}

	private void setFilter_dataValutaCheck(HttpServletRequest request, String data_valuta_check,
			Object session_data_valuta_check, VisualizzaCompletaCommand visualizzaTesoreriaCommand) {
		try {
			if (data_valuta_check == null)
				visualizzaTesoreriaCommand.setData_valuta_check(session_data_valuta_check.toString());
			else
				visualizzaTesoreriaCommand.setData_valuta_check(data_valuta_check);
		} catch (NullPointerException e) {
			visualizzaTesoreriaCommand.setData_valuta_check("");
		}
		setSingleFilter(request, SessionVariables.EXPORT_TESORERIA_DATA_VALUTA_CHECK,
				visualizzaTesoreriaCommand.getData_valuta_check());
	}

	private void setFilter_errorCode(HttpServletRequest request, String errorCode, Object session_errorCode,
			VisualizzaCompletaCommand visualizzaTesoreriaCommand) {
		try {
			if (errorCode == null)
				visualizzaTesoreriaCommand.setTipoErrore(session_errorCode.toString());
			else
				visualizzaTesoreriaCommand.setTipoErrore(errorCode);
		} catch (NullPointerException e) {
			visualizzaTesoreriaCommand.setTipoErrore("");
		}
		setSingleFilter(request, SessionVariables.EXPORT_TESORERIA_ERROR_CODE,
				visualizzaTesoreriaCommand.getTipoErrore());
	}

	private void setSingleFilter(HttpServletRequest request, String key, Object value) {
		String codIpa = SecurityContext.getEnte().getCodIpaEnte();
		Map<String, Object> filtersMap = SecurityContext.getEnteViewFilters(codIpa,
				SessionVariables.ACTION_VISUALIZZA_TESORERIA);
		if (filtersMap == null) {
			filtersMap = new HashMap<String, Object>();
		}
		filtersMap.put(key, value);
		SecurityContext.setEnteViewFilters(codIpa, SessionVariables.ACTION_VISUALIZZA_TESORERIA, filtersMap);
	}

	private void setFilter_data_ultimo_aggiornamento_da(HttpServletRequest request,
			String request_data_ultimo_aggiornamento_da, Object session_data_ultimo_aggiornamento_da,
			VisualizzaCompletaCommand visualizzaTesoreriaCommand) {
		try {
			if (request_data_ultimo_aggiornamento_da == null)
				visualizzaTesoreriaCommand
						.setData_ultimo_aggiornamento_da(session_data_ultimo_aggiornamento_da.toString());
			else
				visualizzaTesoreriaCommand.setData_ultimo_aggiornamento_da(request_data_ultimo_aggiornamento_da);
		} catch (NullPointerException e) {
			visualizzaTesoreriaCommand.setData_ultimo_aggiornamento_da("");
		}
		setSingleFilter(request, SessionVariables.EXPORT_RICONCILIAZIONE_DATA_ULTIMO_AGGIORNAMENTO_DA,
				visualizzaTesoreriaCommand.getData_ultimo_aggiornamento_da());

	}

	private void setFilter_data_ultimo_aggiornamento_a(HttpServletRequest request,
			String request_data_ultimo_aggiornamento_a, Object session_data_ultimo_aggiornamento_a,
			VisualizzaCompletaCommand visualizzaTesoreriaCommand) {
		try {
			if (request_data_ultimo_aggiornamento_a == null)
				visualizzaTesoreriaCommand
						.setData_ultimo_aggiornamento_a(session_data_ultimo_aggiornamento_a.toString());
			else
				visualizzaTesoreriaCommand.setData_ultimo_aggiornamento_a(request_data_ultimo_aggiornamento_a);
		} catch (NullPointerException e) {
			visualizzaTesoreriaCommand.setData_ultimo_aggiornamento_a("");
		}
		setSingleFilter(request, SessionVariables.EXPORT_RICONCILIAZIONE_DATA_ULTIMO_AGGIORNAMENTO_A,
				visualizzaTesoreriaCommand.getData_ultimo_aggiornamento_a());

	}

	private void setFilter_segnalazioneNascosti(HttpServletRequest request, String request_visualizza_nascosti,
			Object session_segnalazioni_nascosti, VisualizzaCompletaCommand visualizzaTesoreriaCommand) {
		try {
			if (request_visualizza_nascosti == null)
				visualizzaTesoreriaCommand.setVisualizzaNascosti(session_segnalazioni_nascosti.toString());
			else
				visualizzaTesoreriaCommand.setVisualizzaNascosti(request_visualizza_nascosti.toString());
		} catch (NullPointerException e) {
			visualizzaTesoreriaCommand.setVisualizzaNascosti(VISUALIZZA_NASCOSTI.ALL.getValue());
		}
		setSingleFilter(request, SessionVariables.EXPORT_SEGNALAZIONI_VISUALIZZA_NASCOSTI,
				visualizzaTesoreriaCommand.getVisualizzaNascosti());
	}

	private void setFilter_dataUltimoAggiornamentoCheck(HttpServletRequest request,
			String data_ultimo_aggiornamento_check, Object session_dt_ultimo_aggiornamento_check,
			VisualizzaCompletaCommand visualizzaTesoreriaCommand) {
		try {
			if (data_ultimo_aggiornamento_check == null)
				visualizzaTesoreriaCommand
						.setData_ultimo_aggiornamento_check(session_dt_ultimo_aggiornamento_check.toString());
			else
				visualizzaTesoreriaCommand.setData_ultimo_aggiornamento_check(data_ultimo_aggiornamento_check);
		} catch (NullPointerException e) {
			visualizzaTesoreriaCommand.setData_ultimo_aggiornamento_check("");
		}
		setSingleFilter(request, SessionVariables.EXPORT_RICONCILIAZIONE_DATA_ULTIMO_AGGIORNAMENTO_CHECK,
				visualizzaTesoreriaCommand.getData_ultimo_aggiornamento_check());
	}

	private void setFilter_tipoVisualizzazione(HttpServletRequest request, String request_tipo_visualizzazione,
			Object session_tipo_visualizzazione, VisualizzaCompletaCommand visualizzaTesoreriaCommand) {
		try {
			if (request_tipo_visualizzazione == null)
				visualizzaTesoreriaCommand.setTipoVisualizzazione(session_tipo_visualizzazione.toString());
			else
				visualizzaTesoreriaCommand.setTipoVisualizzazione(request_tipo_visualizzazione.toString());
		} catch (NullPointerException e) {
			visualizzaTesoreriaCommand
					.setTipoVisualizzazione(Constants.TIPO_VISUALIZZAZIONE.RICONCILIAZIONE.getValue());
		}
		setSingleFilter(request, SessionVariables.EXPORT_TESORERIA_TIPO_VISUALIZZAZIONE,
				visualizzaTesoreriaCommand.getTipoVisualizzazione());
	}

	/**
	 * @param target
	 */
	private void initialize(VisualizzaCompletaCommand visualizzaTesoreriaCommand) {

		Date data_esecuzione_singolo_pagamento_da = null;

		if (StringUtils.isNotBlank(visualizzaTesoreriaCommand.getData_esecuzione_singolo_pagamento_da())) {
			try {
				data_esecuzione_singolo_pagamento_da = Constants.DDMMYYYY
						.parse(visualizzaTesoreriaCommand.getData_esecuzione_singolo_pagamento_da());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date data_esecuzione_singolo_pagamento_a = null;

		if (StringUtils.isNotBlank(visualizzaTesoreriaCommand.getData_esecuzione_singolo_pagamento_a())) {
			try {
				data_esecuzione_singolo_pagamento_a = Constants.DDMMYYYY
						.parse(visualizzaTesoreriaCommand.getData_esecuzione_singolo_pagamento_a());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		if ((data_esecuzione_singolo_pagamento_da == null) && (data_esecuzione_singolo_pagamento_a == null)) {
			data_esecuzione_singolo_pagamento_a = new Date();
			data_esecuzione_singolo_pagamento_a = DateUtils.setHours(data_esecuzione_singolo_pagamento_a, 0);
			data_esecuzione_singolo_pagamento_a = DateUtils.setMinutes(data_esecuzione_singolo_pagamento_a, 0);
			data_esecuzione_singolo_pagamento_a = DateUtils.setSeconds(data_esecuzione_singolo_pagamento_a, 0);
			data_esecuzione_singolo_pagamento_a = DateUtils.setMilliseconds(data_esecuzione_singolo_pagamento_a, 0);

			data_esecuzione_singolo_pagamento_da = DateUtils.addMonths(data_esecuzione_singolo_pagamento_a, -1);
		} else if ((data_esecuzione_singolo_pagamento_da != null) && (data_esecuzione_singolo_pagamento_a == null)) {
			data_esecuzione_singolo_pagamento_da = DateUtils.setHours(data_esecuzione_singolo_pagamento_da, 0);
			data_esecuzione_singolo_pagamento_da = DateUtils.setMinutes(data_esecuzione_singolo_pagamento_da, 0);
			data_esecuzione_singolo_pagamento_da = DateUtils.setSeconds(data_esecuzione_singolo_pagamento_da, 0);
			data_esecuzione_singolo_pagamento_da = DateUtils.setMilliseconds(data_esecuzione_singolo_pagamento_da, 0);

			data_esecuzione_singolo_pagamento_a = DateUtils.addMonths(data_esecuzione_singolo_pagamento_da, -1);
		} else if ((data_esecuzione_singolo_pagamento_da == null) && (data_esecuzione_singolo_pagamento_a != null)) {
			data_esecuzione_singolo_pagamento_a = DateUtils.setHours(data_esecuzione_singolo_pagamento_a, 0);
			data_esecuzione_singolo_pagamento_a = DateUtils.setMinutes(data_esecuzione_singolo_pagamento_a, 0);
			data_esecuzione_singolo_pagamento_a = DateUtils.setSeconds(data_esecuzione_singolo_pagamento_a, 0);
			data_esecuzione_singolo_pagamento_a = DateUtils.setMilliseconds(data_esecuzione_singolo_pagamento_a, 0);

			data_esecuzione_singolo_pagamento_da = DateUtils.addMonths(data_esecuzione_singolo_pagamento_a, -1);
		}

		visualizzaTesoreriaCommand.setData_esecuzione_singolo_pagamento_da(
				Constants.DDMMYYYY.format(data_esecuzione_singolo_pagamento_da));
		visualizzaTesoreriaCommand
				.setData_esecuzione_singolo_pagamento_a(Constants.DDMMYYYY.format(data_esecuzione_singolo_pagamento_a));

		Date data_esito_singolo_pagamento_da = null;

		if (StringUtils.isNotBlank(visualizzaTesoreriaCommand.getData_esito_singolo_pagamento_da())) {
			try {
				data_esito_singolo_pagamento_da = Constants.DDMMYYYY
						.parse(visualizzaTesoreriaCommand.getData_esito_singolo_pagamento_da());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date data_esito_singolo_pagamento_a = null;

		if (StringUtils.isNotBlank(visualizzaTesoreriaCommand.getData_esito_singolo_pagamento_a())) {
			try {
				data_esito_singolo_pagamento_a = Constants.DDMMYYYY
						.parse(visualizzaTesoreriaCommand.getData_esito_singolo_pagamento_a());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		if ((data_esito_singolo_pagamento_da == null) && (data_esito_singolo_pagamento_a == null)) {
			data_esito_singolo_pagamento_a = new Date();
			data_esito_singolo_pagamento_a = DateUtils.setHours(data_esito_singolo_pagamento_a, 0);
			data_esito_singolo_pagamento_a = DateUtils.setMinutes(data_esito_singolo_pagamento_a, 0);
			data_esito_singolo_pagamento_a = DateUtils.setSeconds(data_esito_singolo_pagamento_a, 0);
			data_esito_singolo_pagamento_a = DateUtils.setMilliseconds(data_esito_singolo_pagamento_a, 0);

			data_esito_singolo_pagamento_da = DateUtils.addMonths(data_esito_singolo_pagamento_a, -1);
		} else if ((data_esito_singolo_pagamento_da != null) && (data_esito_singolo_pagamento_a == null)) {
			data_esito_singolo_pagamento_da = DateUtils.setHours(data_esito_singolo_pagamento_da, 0);
			data_esito_singolo_pagamento_da = DateUtils.setMinutes(data_esito_singolo_pagamento_da, 0);
			data_esito_singolo_pagamento_da = DateUtils.setSeconds(data_esito_singolo_pagamento_da, 0);
			data_esito_singolo_pagamento_da = DateUtils.setMilliseconds(data_esito_singolo_pagamento_da, 0);

			data_esito_singolo_pagamento_a = DateUtils.addMonths(data_esito_singolo_pagamento_da, -1);
		} else if ((data_esito_singolo_pagamento_da == null) && (data_esito_singolo_pagamento_a != null)) {
			data_esito_singolo_pagamento_a = DateUtils.setHours(data_esito_singolo_pagamento_a, 0);
			data_esito_singolo_pagamento_a = DateUtils.setMinutes(data_esito_singolo_pagamento_a, 0);
			data_esito_singolo_pagamento_a = DateUtils.setSeconds(data_esito_singolo_pagamento_a, 0);
			data_esito_singolo_pagamento_a = DateUtils.setMilliseconds(data_esito_singolo_pagamento_a, 0);

			data_esito_singolo_pagamento_da = DateUtils.addMonths(data_esito_singolo_pagamento_a, -1);
		}

		visualizzaTesoreriaCommand
				.setData_esito_singolo_pagamento_da(Constants.DDMMYYYY.format(data_esito_singolo_pagamento_da));
		visualizzaTesoreriaCommand
				.setData_esito_singolo_pagamento_a(Constants.DDMMYYYY.format(data_esito_singolo_pagamento_a));

		Date data_regolamento_da = null;

		if (StringUtils.isNotBlank(visualizzaTesoreriaCommand.getData_regolamento_da())) {
			try {
				data_regolamento_da = Constants.DDMMYYYY.parse(visualizzaTesoreriaCommand.getData_regolamento_da());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date data_regolamento_a = null;

		if (StringUtils.isNotBlank(visualizzaTesoreriaCommand.getData_regolamento_a())) {
			try {
				data_regolamento_a = Constants.DDMMYYYY.parse(visualizzaTesoreriaCommand.getData_regolamento_a());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		if ((data_regolamento_da == null) && (data_regolamento_a == null)) {
			data_regolamento_a = new Date();
			data_regolamento_a = DateUtils.setHours(data_regolamento_a, 0);
			data_regolamento_a = DateUtils.setMinutes(data_regolamento_a, 0);
			data_regolamento_a = DateUtils.setSeconds(data_regolamento_a, 0);
			data_regolamento_a = DateUtils.setMilliseconds(data_regolamento_a, 0);

			data_regolamento_da = DateUtils.addMonths(data_regolamento_a, -1);
		} else if ((data_regolamento_da != null) && (data_regolamento_a == null)) {
			data_regolamento_da = DateUtils.setHours(data_regolamento_da, 0);
			data_regolamento_da = DateUtils.setMinutes(data_regolamento_da, 0);
			data_regolamento_da = DateUtils.setSeconds(data_regolamento_da, 0);
			data_regolamento_da = DateUtils.setMilliseconds(data_regolamento_da, 0);

			data_regolamento_a = DateUtils.addMonths(data_regolamento_da, -1);
		} else if ((data_regolamento_da == null) && (data_regolamento_a != null)) {
			data_regolamento_a = DateUtils.setHours(data_regolamento_a, 0);
			data_regolamento_a = DateUtils.setMinutes(data_regolamento_a, 0);
			data_regolamento_a = DateUtils.setSeconds(data_regolamento_a, 0);
			data_regolamento_a = DateUtils.setMilliseconds(data_regolamento_a, 0);

			data_regolamento_da = DateUtils.addMonths(data_regolamento_a, -1);
		}

		visualizzaTesoreriaCommand.setData_regolamento_da(Constants.DDMMYYYY.format(data_regolamento_da));
		visualizzaTesoreriaCommand.setData_regolamento_a(Constants.DDMMYYYY.format(data_regolamento_a));

		Date data_contabile_da = null;

		if (StringUtils.isNotBlank(visualizzaTesoreriaCommand.getData_contabile_da())) {
			try {
				data_contabile_da = Constants.DDMMYYYY.parse(visualizzaTesoreriaCommand.getData_contabile_da());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date data_contabile_a = null;

		if (StringUtils.isNotBlank(visualizzaTesoreriaCommand.getData_contabile_a())) {
			try {
				data_contabile_a = Constants.DDMMYYYY.parse(visualizzaTesoreriaCommand.getData_contabile_a());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		if ((data_contabile_da == null) && (data_contabile_a == null)) {
			data_contabile_a = new Date();
			data_contabile_a = DateUtils.setHours(data_contabile_a, 0);
			data_contabile_a = DateUtils.setMinutes(data_contabile_a, 0);
			data_contabile_a = DateUtils.setSeconds(data_contabile_a, 0);
			data_contabile_a = DateUtils.setMilliseconds(data_contabile_a, 0);
			data_contabile_da = DateUtils.addMonths(data_contabile_a, -1);
		} else if ((data_contabile_da != null) && (data_contabile_a == null)) {
			data_contabile_da = DateUtils.setHours(data_contabile_da, 0);
			data_contabile_da = DateUtils.setMinutes(data_contabile_da, 0);
			data_contabile_da = DateUtils.setSeconds(data_contabile_da, 0);
			data_contabile_da = DateUtils.setMilliseconds(data_contabile_da, 0);
			data_contabile_a = DateUtils.addMonths(data_contabile_da, -1);
		} else if ((data_contabile_da == null) && (data_contabile_a != null)) {
			data_contabile_a = DateUtils.setHours(data_contabile_a, 0);
			data_contabile_a = DateUtils.setMinutes(data_contabile_a, 0);
			data_contabile_a = DateUtils.setSeconds(data_contabile_a, 0);
			data_contabile_a = DateUtils.setMilliseconds(data_contabile_a, 0);
			data_contabile_da = DateUtils.addMonths(data_contabile_a, -1);
		}

		visualizzaTesoreriaCommand.setData_contabile_a(Constants.DDMMYYYY.format(data_contabile_a));
		visualizzaTesoreriaCommand.setData_contabile_da(Constants.DDMMYYYY.format(data_contabile_da));

		Date data_valuta_da = null;

		if (StringUtils.isNotBlank(visualizzaTesoreriaCommand.getData_valuta_da())) {
			try {
				data_valuta_da = Constants.DDMMYYYY.parse(visualizzaTesoreriaCommand.getData_valuta_da());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date data_valuta_a = null;

		if (StringUtils.isNotBlank(visualizzaTesoreriaCommand.getData_valuta_a())) {
			try {
				data_valuta_a = Constants.DDMMYYYY.parse(visualizzaTesoreriaCommand.getData_valuta_a());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		if ((data_valuta_da == null) && (data_valuta_a == null)) {
			data_valuta_a = new Date();
			data_valuta_a = DateUtils.setHours(data_valuta_a, 0);
			data_valuta_a = DateUtils.setMinutes(data_valuta_a, 0);
			data_valuta_a = DateUtils.setSeconds(data_valuta_a, 0);
			data_valuta_a = DateUtils.setMilliseconds(data_valuta_a, 0);
			data_valuta_da = DateUtils.addMonths(data_valuta_a, -1);
		} else if ((data_valuta_da != null) && (data_valuta_a == null)) {
			data_valuta_da = DateUtils.setHours(data_valuta_da, 0);
			data_valuta_da = DateUtils.setMinutes(data_valuta_da, 0);
			data_valuta_da = DateUtils.setSeconds(data_valuta_da, 0);
			data_valuta_da = DateUtils.setMilliseconds(data_valuta_da, 0);
			data_valuta_a = DateUtils.addMonths(data_valuta_da, -1);
		} else if ((data_valuta_da == null) && (data_valuta_a != null)) {
			data_valuta_a = DateUtils.setHours(data_valuta_a, 0);
			data_valuta_a = DateUtils.setMinutes(data_valuta_a, 0);
			data_valuta_a = DateUtils.setSeconds(data_valuta_a, 0);
			data_valuta_a = DateUtils.setMilliseconds(data_valuta_a, 0);
			data_valuta_da = DateUtils.addMonths(data_valuta_a, -1);
		}

		visualizzaTesoreriaCommand.setData_valuta_a(Constants.DDMMYYYY.format(data_valuta_a));
		visualizzaTesoreriaCommand.setData_valuta_da(Constants.DDMMYYYY.format(data_valuta_da));

		Date data_ultimo_aggiornamento_da = null;

		if (StringUtils.isNotBlank(visualizzaTesoreriaCommand.getData_ultimo_aggiornamento_da())) {
			try {
				data_ultimo_aggiornamento_da = Constants.DDMMYYYY
						.parse(visualizzaTesoreriaCommand.getData_ultimo_aggiornamento_da());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date data_ultimo_aggiornamento_a = null;

		if (StringUtils.isNotBlank(visualizzaTesoreriaCommand.getData_ultimo_aggiornamento_a())) {
			try {
				data_ultimo_aggiornamento_a = Constants.DDMMYYYY
						.parse(visualizzaTesoreriaCommand.getData_ultimo_aggiornamento_a());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		if ((data_ultimo_aggiornamento_da == null) && (data_ultimo_aggiornamento_a == null)) {
			data_ultimo_aggiornamento_a = new Date();
			data_ultimo_aggiornamento_a = DateUtils.setHours(data_ultimo_aggiornamento_a, 0);
			data_ultimo_aggiornamento_a = DateUtils.setMinutes(data_ultimo_aggiornamento_a, 0);
			data_ultimo_aggiornamento_a = DateUtils.setSeconds(data_ultimo_aggiornamento_a, 0);
			data_ultimo_aggiornamento_a = DateUtils.setMilliseconds(data_ultimo_aggiornamento_a, 0);
			data_ultimo_aggiornamento_da = DateUtils.addMonths(data_ultimo_aggiornamento_a, -1);
		} else if ((data_ultimo_aggiornamento_da != null) && (data_ultimo_aggiornamento_a == null)) {
			data_ultimo_aggiornamento_da = DateUtils.setHours(data_ultimo_aggiornamento_da, 0);
			data_ultimo_aggiornamento_da = DateUtils.setMinutes(data_ultimo_aggiornamento_da, 0);
			data_ultimo_aggiornamento_da = DateUtils.setSeconds(data_ultimo_aggiornamento_da, 0);
			data_ultimo_aggiornamento_da = DateUtils.setMilliseconds(data_ultimo_aggiornamento_da, 0);
			data_ultimo_aggiornamento_a = DateUtils.addMonths(data_ultimo_aggiornamento_da, -1);
		} else if ((data_ultimo_aggiornamento_da == null) && (data_ultimo_aggiornamento_a != null)) {
			data_ultimo_aggiornamento_a = DateUtils.setHours(data_ultimo_aggiornamento_a, 0);
			data_ultimo_aggiornamento_a = DateUtils.setMinutes(data_ultimo_aggiornamento_a, 0);
			data_ultimo_aggiornamento_a = DateUtils.setSeconds(data_ultimo_aggiornamento_a, 0);
			data_ultimo_aggiornamento_a = DateUtils.setMilliseconds(data_ultimo_aggiornamento_a, 0);
			data_ultimo_aggiornamento_da = DateUtils.addMonths(data_ultimo_aggiornamento_a, -1);
		}

		visualizzaTesoreriaCommand
				.setData_ultimo_aggiornamento_a(Constants.DDMMYYYY.format(data_ultimo_aggiornamento_a));
		visualizzaTesoreriaCommand
				.setData_ultimo_aggiornamento_da(Constants.DDMMYYYY.format(data_ultimo_aggiornamento_da));

		if (StringUtils.isBlank(visualizzaTesoreriaCommand.getVisualizzaNascosti())) {
			visualizzaTesoreriaCommand.setVisualizzaNascosti(VISUALIZZA_NASCOSTI.ALL.getValue());
		}
	}

	private List<ErrorTypeDto> getClassificazioniList(boolean flgPagati, boolean flgTesoreria,
			String tipoVisualizzazione) {
		List<ErrorTypeDto> errorTypes = new ArrayList<ErrorTypeDto>();

		if (tipoVisualizzazione.equals(Constants.TIPO_VISUALIZZAZIONE.RICONCILIAZIONE.getValue())) {
			if (flgPagati && flgTesoreria) {
				errorTypes.add(new ErrorTypeDto(Constants.COD_ERRORE_IUD_RT_IUF_TES,
						"mypivot.classificazioni." + Constants.COD_ERRORE_IUD_RT_IUF_TES));
			}

			if (flgTesoreria) {
				errorTypes.add(new ErrorTypeDto(Constants.COD_ERRORE_RT_IUF_TES,
						"mypivot.classificazioni." + Constants.COD_ERRORE_RT_IUF_TES));
				errorTypes.add(new ErrorTypeDto(Constants.COD_ERRORE_RT_TES,
						"mypivot.classificazioni." + Constants.COD_ERRORE_RT_TES));
			}

			errorTypes.add(new ErrorTypeDto(Constants.COD_ERRORE_RT_IUF,
					"mypivot.classificazioni." + Constants.COD_ERRORE_RT_IUF));

			if (flgPagati) {
				errorTypes.add(new ErrorTypeDto(Constants.COD_ERRORE_IUD_RT_IUF,
						"mypivot.classificazioni." + Constants.COD_ERRORE_IUD_RT_IUF));
			}
		} else if (tipoVisualizzazione.equals(Constants.TIPO_VISUALIZZAZIONE.ANOMALIE.getValue())) {
			if (flgPagati) {
				errorTypes.add(new ErrorTypeDto(Constants.COD_ERRORE_IUD_NO_RT,
						"mypivot.classificazioni." + Constants.COD_ERRORE_IUD_NO_RT));
				errorTypes.add(new ErrorTypeDto(Constants.COD_ERRORE_RT_NO_IUD,
						"mypivot.classificazioni." + Constants.COD_ERRORE_RT_NO_IUD));
			}

			errorTypes.add(new ErrorTypeDto(Constants.COD_ERRORE_RT_NO_IUF,
					"mypivot.classificazioni." + Constants.COD_ERRORE_RT_NO_IUF));
			errorTypes.add(new ErrorTypeDto(Constants.COD_ERRORE_IUV_NO_RT,
					"mypivot.classificazioni." + Constants.COD_ERRORE_IUV_NO_RT));

			if (flgTesoreria) {
				errorTypes.add(new ErrorTypeDto(Constants.COD_ERRORE_IUF_NO_TES,
						"mypivot.classificazioni." + Constants.COD_ERRORE_IUF_NO_TES));
				errorTypes.add(new ErrorTypeDto(Constants.COD_ERRORE_IUF_TES_DIV_IMP,
						"mypivot.classificazioni." + Constants.COD_ERRORE_IUF_TES_DIV_IMP));
				errorTypes.add(new ErrorTypeDto(Constants.COD_ERRORE_TES_NO_IUF_OR_IUV,
						"mypivot.classificazioni." + Constants.COD_ERRORE_TES_NO_IUF_OR_IUV));
				errorTypes.add(new ErrorTypeDto(Constants.COD_ERRORE_TES_NO_MATCH,
						"mypivot.classificazioni." + Constants.COD_ERRORE_TES_NO_MATCH));
			}
		}

		return errorTypes;
	}

	@RequestMapping(value = { "/prenotaExport.html" }, method = RequestMethod.GET)
	public ModelAndView prenotaExport(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String versioneTracciato) throws Exception {

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

		if (StringUtils.isBlank(versioneTracciato)) {
			mav.setViewName("message");

			MessagesDto messagesDto = new MessagesDto();
			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.versioneTracciatoErrata"));
			mav.addObject("messagesDto", messagesDto);

			return mav;
		}

		Map<String, Object> filtersMap = SecurityContext.getEnteViewFilters(enteTO.getCodIpaEnte(),
				SessionVariables.ACTION_VISUALIZZA_TESORERIA);
		// GET session filter data
		String codIUD = (String) filtersMap.get(SessionVariables.EXPORT_TESORERIA_CODICE_IUD);
		String codIUV = (String) filtersMap.get(SessionVariables.EXPORT_TESORERIA_CODICE_IUV);
		String denominazioneAttestante = (String) filtersMap
				.get(SessionVariables.EXPORT_TESORERIA_DENOMINAZIONE_ATTESTANTE);
		String identificativoUnivocoRiscossione = (String) filtersMap
				.get(SessionVariables.EXPORT_TESORERIA_IDENTIFICATIVO_UNIVOCO_RISCOSSIONE);
		String idVersante = (String) filtersMap
				.get(SessionVariables.EXPORT_TESORERIA_CODICE_IDENTIFICATIVO_UNIVOCO_VERSANTE);
		String anagraficaVersante = (String) filtersMap.get(SessionVariables.EXPORT_TESORERIA_ANAGRAFICA_VERSANTE);
		String idPagatore = (String) filtersMap
				.get(SessionVariables.EXPORT_TESORERIA_CODICE_IDENTIFICATIVO_UNIVOCO_PAGATORE);
		String anagraficaPagatore = (String) filtersMap.get(SessionVariables.EXPORT_TESORERIA_ANAGRAFICA_PAGATORE);
		String causaleVersamento = (String) filtersMap.get(SessionVariables.EXPORT_TESORERIA_CAUSALE_VERSAMENTO);
		String idFlussoRend = (String) filtersMap
				.get(SessionVariables.EXPORT_TESORERIA_IDENTIFICATIVO_FLUSSO_TESORERIA);
		String identificativoUnivocoRegolamento = (String) filtersMap
				.get(SessionVariables.EXPORT_TESORERIA_IDENTIFICATIVO_UNIVOCO_REGOLAMENTO);
		String codTipoDovuto = (String) filtersMap.get(SessionVariables.EXPORT_TESORERIA_COD_TIPO_DOVUTO);
		String importo = (String) filtersMap.get(SessionVariables.EXPORT_TESORERIA_IMPORTO);
		String codOR1 = (String) filtersMap.get(SessionVariables.EXPORT_TESORERIA_COD_OR1);
		String conto = (String) filtersMap.get(SessionVariables.EXPORT_TESORERIA_CONTO);
		String errorCode = (String) filtersMap.get(SessionVariables.EXPORT_TESORERIA_ERROR_CODE);

		if (StringUtils.isNotBlank(causaleVersamento)
				&& causaleVersamento.length() > Constants.CAUSALE_MAX_LENGTH_EXPORT_RICONCILIAZIONE_VERSIONE) {
			causaleVersamento = causaleVersamento.substring(0,
					Constants.CAUSALE_MAX_LENGTH_EXPORT_RICONCILIAZIONE_VERSIONE);
		}

		String dtUltimoAggCheckString = (String) filtersMap
				.get(SessionVariables.EXPORT_RICONCILIAZIONE_DATA_ULTIMO_AGGIORNAMENTO_CHECK);
		String dtUltimoAggDaString = (String) filtersMap
				.get(SessionVariables.EXPORT_RICONCILIAZIONE_DATA_ULTIMO_AGGIORNAMENTO_DA);
		Date dtUltimoAggDa = null;

		if (StringUtils.isNotBlank(dtUltimoAggCheckString) && dtUltimoAggCheckString.equals("on")
				&& StringUtils.isNotBlank(dtUltimoAggDaString)) {
			try {
				dtUltimoAggDa = Constants.DDMMYYYY.parse(dtUltimoAggDaString);
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		String dtUltimoAggAString = (String) filtersMap
				.get(SessionVariables.EXPORT_RICONCILIAZIONE_DATA_ULTIMO_AGGIORNAMENTO_A);
		Date dtUltimoAggA = null;

		if (StringUtils.isNotBlank(dtUltimoAggCheckString) && dtUltimoAggCheckString.equals("on")
				&& StringUtils.isNotBlank(dtUltimoAggAString)) {
			try {
				dtUltimoAggA = Constants.DDMMYYYY.parse(dtUltimoAggAString);
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		String dtEsecuzioneCheckString = (String) filtersMap
				.get(SessionVariables.EXPORT_TESORERIA_DATA_ESECUZIONE_CHECK);
		String dtEsecuzioneDaString = (String) filtersMap
				.get(SessionVariables.EXPORT_TESORERIA_DATA_ESECUZIONE_SINGOLO_PAGAMENTO_DA);
		Date dtEsecuzioneDa = null;

		if (StringUtils.isNotBlank(dtEsecuzioneCheckString) && dtEsecuzioneCheckString.equals("on")
				&& StringUtils.isNotBlank(dtEsecuzioneDaString)) {
			try {
				dtEsecuzioneDa = Constants.DDMMYYYY.parse(dtEsecuzioneDaString);
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		String dtEsecuzioneAString = (String) filtersMap
				.get(SessionVariables.EXPORT_TESORERIA_DATA_ESECUZIONE_SINGOLO_PAGAMENTO_A);
		Date dtEsecuzioneA = null;

		if (StringUtils.isNotBlank(dtEsecuzioneCheckString) && dtEsecuzioneCheckString.equals("on")
				&& StringUtils.isNotBlank(dtEsecuzioneAString)) {
			try {
				dtEsecuzioneA = Constants.DDMMYYYY.parse(dtEsecuzioneAString);
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		String dtEsitoCheckString = (String) filtersMap.get(SessionVariables.EXPORT_TESORERIA_DATA_ESITO_CHECK);
		String dtEsitoDaString = (String) filtersMap
				.get(SessionVariables.EXPORT_TESORERIA_DATA_ESITO_SINGOLO_PAGAMENTO_DA);
		Date dtEsitoDa = null;

		if (StringUtils.isNotBlank(dtEsitoCheckString) && dtEsitoCheckString.equals("on")
				&& StringUtils.isNotBlank(dtEsitoDaString)) {
			try {
				dtEsitoDa = Constants.DDMMYYYY.parse(dtEsitoDaString);
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		String dtEsitoAString = (String) filtersMap
				.get(SessionVariables.EXPORT_TESORERIA_DATA_ESITO_SINGOLO_PAGAMENTO_A);
		Date dtEsitoA = null;

		if (StringUtils.isNotBlank(dtEsitoCheckString) && dtEsitoCheckString.equals("on")
				&& StringUtils.isNotBlank(dtEsitoAString)) {
			try {
				dtEsitoA = Constants.DDMMYYYY.parse(dtEsitoAString);
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		String dtRegolamentoCheckString = (String) filtersMap
				.get(SessionVariables.EXPORT_TESORERIA_DATA_REGOLAMENTO_CHECK);
		String dtRegolamentoDaString = (String) filtersMap.get(SessionVariables.EXPORT_TESORERIA_DATA_REGOLAMENTO_DA);
		Date dtRegolamentoDa = null;

		if (StringUtils.isNotBlank(dtRegolamentoCheckString) && dtRegolamentoCheckString.equals("on")
				&& StringUtils.isNotBlank(dtRegolamentoDaString)) {
			try {
				dtRegolamentoDa = Constants.DDMMYYYY.parse(dtRegolamentoDaString);
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		String dtRegolamentoAString = (String) filtersMap.get(SessionVariables.EXPORT_TESORERIA_DATA_REGOLAMENTO_A);
		Date dtRegolamentoA = null;

		if (StringUtils.isNotBlank(dtRegolamentoCheckString) && dtRegolamentoCheckString.equals("on")
				&& StringUtils.isNotBlank(dtRegolamentoAString)) {
			try {
				dtRegolamentoA = Constants.DDMMYYYY.parse(dtRegolamentoAString);
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		String dtContabileCheckString = (String) filtersMap.get(SessionVariables.EXPORT_TESORERIA_DATA_CONTABILE_CHECK);
		String dtContabileDaString = (String) filtersMap.get(SessionVariables.EXPORT_TESORERIA_DATA_CONTABILE_DA);
		Date dtContabileDa = null;

		if (StringUtils.isNotBlank(dtContabileCheckString) && dtContabileCheckString.equals("on")
				&& StringUtils.isNotBlank(dtContabileDaString)) {
			try {
				dtContabileDa = Constants.DDMMYYYY.parse(dtContabileDaString);
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		String dtContabileAString = (String) filtersMap.get(SessionVariables.EXPORT_TESORERIA_DATA_CONTABILE_A);
		Date dtContabileA = null;

		if (StringUtils.isNotBlank(dtContabileCheckString) && dtContabileCheckString.equals("on")
				&& StringUtils.isNotBlank(dtContabileAString)) {
			try {
				dtContabileA = Constants.DDMMYYYY.parse(dtContabileAString);
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		String dtValutaCheckString = (String) filtersMap.get(SessionVariables.EXPORT_TESORERIA_DATA_VALUTA_CHECK);
		String dtValutaDaString = (String) filtersMap.get(SessionVariables.EXPORT_TESORERIA_DATA_VALUTA_DA);
		Date dtValutaDa = null;

		if (StringUtils.isNotBlank(dtValutaCheckString) && dtValutaCheckString.equals("on")
				&& StringUtils.isNotBlank(dtValutaDaString)) {
			try {
				dtValutaDa = Constants.DDMMYYYY.parse(dtValutaDaString);
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		String dtValutaAString = (String) filtersMap.get(SessionVariables.EXPORT_TESORERIA_DATA_VALUTA_A);
		Date dtValutaA = null;

		if (StringUtils.isNotBlank(dtValutaCheckString) && dtValutaCheckString.equals("on")
				&& StringUtils.isNotBlank(dtValutaAString)) {
			try {
				dtValutaA = Constants.DDMMYYYY.parse(dtValutaAString);
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		try {
			PrenotazioneFlussoRiconciliazioneTO prenotazione = prenotazioneFlussoRiconciliazioneService
					.insertPrenotazioneFlussoRiconciliazione(enteTO.getCodIpaEnte(), utenteTO.getCodFedUserId(),
							Constants.COD_TIPO_STATO_EXPORT_FLUSSO_RICONCILIAZIONE_PRENOTATO,
							Constants.DE_TIPO_STATO_PRENOTA_FLUSSO_RICONCILIAZIONE, errorCode, codTipoDovuto, codIUV,
							idFlussoRend, dtUltimoAggDa, dtUltimoAggA, dtEsecuzioneDa, dtEsecuzioneA, dtEsitoDa,
							dtEsitoA, dtRegolamentoDa, dtRegolamentoA, dtContabileDa, dtContabileA, dtValutaDa,
							dtValutaA, codIUD, identificativoUnivocoRiscossione, idPagatore, anagraficaPagatore,
							idVersante, anagraficaVersante, denominazioneAttestante, codOR1,
							identificativoUnivocoRegolamento, conto, importo, causaleVersamento, versioneTracciato);
			if (prenotazione != null) {
				return new ModelAndView(new RedirectView(
						request.getContextPath() + "/protected/visualizzaCompleta.html?exportSuccess=true"));
			} else {
				return new ModelAndView(new RedirectView(
						request.getContextPath() + "/protected/visualizzaCompleta.html?exportFailed=true"));
			}
		} catch (NessunTipoDovutoAttivoException e) {
			mav.setViewName("message");

			MessagesDto messagesDto = new MessagesDto();
			messagesDto.getErrorMessages()
					.add(new DynamicMessageDto("mypivot.messages.error.nessunTipoDovutoAssegnato"));
			mav.addObject("messagesDto", messagesDto);

			return mav;
		} catch (TipoDovutoNonValidoPerUtenteException e) {
			mav.setViewName("message");

			MessagesDto messagesDto = new MessagesDto();
			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.tipoDovutoNonValido"));
			mav.addObject("messagesDto", messagesDto);

			return mav;
		} catch (Exception e) {
			return new ModelAndView(new RedirectView(
					request.getContextPath() + "/protected/visualizzaCompleta.html?exportSuccess=true"));
		}

	}

	private boolean isCodTipoDovutoValidoPerOperatoreEdEnte(String codTipoDovuto) {
		UtenteTO utenteTO = SecurityContext.getUtente();
		EnteTO enteTO = SecurityContext.getEnte();
		return operatoreEnteTipoDovutoService.isCodTipoDovutoValidoPerOperatoreAndCodIpaEnte(utenteTO.getCodFedUserId(),
				enteTO.getCodIpaEnte(), codTipoDovuto);
	}

}
