/**
 * 
 */
package it.regioneveneto.mygov.payment.mypivot.controller;

import java.beans.PropertyDescriptor;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

import it.regioneveneto.mygov.payment.mypivot.controller.command.VisualizzaCommand;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.DynamicMessageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.ExportCSVDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.MessagesDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.PageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.VisualizzaDto;
import it.regioneveneto.mygov.payment.mypivot.domain.po.EnteTipoDovuto;
import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO;
import it.regioneveneto.mygov.payment.mypivot.service.EnteTipoDovutoService;
import it.regioneveneto.mygov.payment.mypivot.service.ExportRendicontazioneService;
import it.regioneveneto.mygov.payment.mypivot.utils.Constants;
import it.regioneveneto.mygov.payment.mypivot.utils.SecurityContext;
import it.regioneveneto.mygov.payment.mypivot.utils.SessionVariables;
import it.regioneveneto.mygov.payment.mypivot.utils.Utilities;

/**
 * @author Giorgio Vallini
 */
@Controller
@RequestMapping("/protected")
@Deprecated
public class VisualizzaController {

	private static Log log = LogFactory.getLog(VisualizzaController.class);

	@Autowired
	private EnteTipoDovutoService enteTipoDovutoService;

	@Autowired
	private ExportRendicontazioneService exportRendicontazioneService;

	public VisualizzaController() {
		super();
	}
	


	@RequestMapping(value = { "/visualizza.html" }, method = RequestMethod.GET)
	public ModelAndView getExportRendicontazione(HttpServletRequest request, @RequestParam(required = false) Boolean forceClear,
			@RequestParam(required = false) String pg, @RequestParam(required = false) String pgSize, @RequestParam(required = false) String codice_iud,
			@RequestParam(required = false) String codice_iuv, @RequestParam(required = false) String denominazione_attestante,
			@RequestParam(required = false) String identificativo_univoco_riscossione,
			@RequestParam(required = false) String codice_identificativo_univoco_versante, @RequestParam(required = false) String anagrafica_versante,
			@RequestParam(required = false) String codice_identificativo_univoco_pagatore, @RequestParam(required = false) String anagrafica_pagatore,
			@RequestParam(required = false) String causale_versamento, @RequestParam(required = false) String data_esito_singolo_pagamento_da,
			@RequestParam(required = false) String data_esito_singolo_pagamento_a,
			@RequestParam(required = false) String identificativo_flusso_rendicontazione,
			@RequestParam(required = false) String identificativo_univoco_regolamento, @RequestParam(required = false) String data_regolamento_da,
			@RequestParam(required = false) String data_regolamento_a, @RequestParam(required = false) String cod_tipo_dovuto) {

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

		VisualizzaCommand visualizzaCommand = new VisualizzaCommand();
		MessagesDto messagesDto = new MessagesDto();

		if (forceClear != null && forceClear) {
			String codIpa = SecurityContext.getEnte().getCodIpaEnte();
			SecurityContext.setEnteViewFilters(codIpa, SessionVariables.ACTION_VISUALIZZA, null);
		}

		setFilters(request, pg, pgSize, codice_iud, codice_iuv, denominazione_attestante, identificativo_univoco_riscossione,
				codice_identificativo_univoco_versante, anagrafica_versante, codice_identificativo_univoco_pagatore, anagrafica_pagatore, causale_versamento,
				data_esito_singolo_pagamento_da, data_esito_singolo_pagamento_a, identificativo_flusso_rendicontazione, identificativo_univoco_regolamento,
				data_regolamento_da, data_regolamento_a, cod_tipo_dovuto, visualizzaCommand);

		initialize(visualizzaCommand, enteTO.getNumGiorniPagamentoPresunti());

		Date dt_data_esito_singolo_pagamento_da = null;

		if (StringUtils.isNotBlank(visualizzaCommand.getData_esito_singolo_pagamento_da())) {
			try {
				dt_data_esito_singolo_pagamento_da = Constants.DDMMYYYY.parse(visualizzaCommand.getData_esito_singolo_pagamento_da());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date dt_data_esito_singolo_pagamento_a = null;

		if (StringUtils.isNotBlank(visualizzaCommand.getData_esito_singolo_pagamento_a())) {
			try {
				dt_data_esito_singolo_pagamento_a = Constants.DDMMYYYY.parse(visualizzaCommand.getData_esito_singolo_pagamento_a());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date dt_data_regolamento_da = null;

		if (StringUtils.isNotBlank(visualizzaCommand.getData_regolamento_da())) {
			try {
				dt_data_regolamento_da = Constants.DDMMYYYY.parse(visualizzaCommand.getData_regolamento_da());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date dt_data_regolamento_a = null;

		if (StringUtils.isNotBlank(visualizzaCommand.getData_regolamento_a())) {
			try {
				dt_data_regolamento_a = Constants.DDMMYYYY.parse(visualizzaCommand.getData_regolamento_a());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Sort sort = new Sort(
			 new Order(Direction.DESC, "dtDataRegolamento"), 
			 new Order(Direction.DESC, "identificativoFlussoRendicontazione"), 
			 new Order(Direction.DESC, "identificativoUnivocoRegolamento")
			);
		
		
		PageDto<VisualizzaDto> visualizzaDtoPage = exportRendicontazioneService.getExportRendicontazionePage(enteTO.getCodIpaEnte(),
				visualizzaCommand.getCodice_iud(), visualizzaCommand.getCodice_iuv(), visualizzaCommand.getDenominazione_attestante(),
				visualizzaCommand.getIdentificativo_univoco_riscossione(), visualizzaCommand.getCodice_identificativo_univoco_versante(),
				visualizzaCommand.getAnagrafica_versante(), visualizzaCommand.getCodice_identificativo_univoco_pagatore(),
				visualizzaCommand.getAnagrafica_pagatore(), visualizzaCommand.getCausale_versamento(), dt_data_esito_singolo_pagamento_da,
				dt_data_esito_singolo_pagamento_a, visualizzaCommand.getIdentificativo_flusso_rendicontazione(),
				visualizzaCommand.getIdentificativo_univoco_regolamento(), dt_data_regolamento_da, dt_data_regolamento_a, visualizzaCommand.getCod_tipo_dovuto(),
				visualizzaCommand.getPage(), visualizzaCommand.getPageSize(), sort);

		List<EnteTipoDovuto> enteTipoDovutos = enteTipoDovutoService.getByCodIpaEnte(enteTO.getCodIpaEnte());
		mav.addObject("listaTipiDovutoPerEnte", enteTipoDovutos);
		
		mav.addObject("messagesDto", messagesDto);
		mav.addObject(visualizzaCommand);
		mav.addObject("visualizzaDtoPage", visualizzaDtoPage);

		mav.setViewName("visualizza");

		return mav;
	}

	@RequestMapping(value = { "/visualizza.html" }, method = RequestMethod.POST)
	public ModelAndView postExportRendicontazione(@ModelAttribute("visualizzaCommand") VisualizzaCommand visualizzaCommand, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView();
		MessagesDto messagesDto = new MessagesDto();

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

		initialize(visualizzaCommand, enteTO.getNumGiorniPagamentoPresunti());

		Date dt_data_esito_singolo_pagamento_da = null;

		if (StringUtils.isNotBlank(visualizzaCommand.getData_esito_singolo_pagamento_da())) {
			try {
				dt_data_esito_singolo_pagamento_da = Constants.DDMMYYYY.parse(visualizzaCommand.getData_esito_singolo_pagamento_da());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date dt_data_esito_singolo_pagamento_a = null;

		if (StringUtils.isNotBlank(visualizzaCommand.getData_esito_singolo_pagamento_a())) {
			try {
				dt_data_esito_singolo_pagamento_a = Constants.DDMMYYYY.parse(visualizzaCommand.getData_esito_singolo_pagamento_a());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date dt_data_regolamento_da = null;

		if (StringUtils.isNotBlank(visualizzaCommand.getData_regolamento_da())) {
			try {
				dt_data_regolamento_da = Constants.DDMMYYYY.parse(visualizzaCommand.getData_regolamento_da());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date dt_data_regolamento_a = null;

		if (StringUtils.isNotBlank(visualizzaCommand.getData_regolamento_a())) {
			try {
				dt_data_regolamento_a = Constants.DDMMYYYY.parse(visualizzaCommand.getData_regolamento_a());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		visualizzaCommand.setPage(1);
		Sort sort = new Sort(
				 new Order(Direction.DESC, "dtDataRegolamento"), 
				 new Order(Direction.DESC, "identificativoFlussoRendicontazione"), 
				 new Order(Direction.DESC, "identificativoUnivocoRegolamento")
				);
		PageDto<VisualizzaDto> visualizzaDtoPage = exportRendicontazioneService.getExportRendicontazionePage(enteTO.getCodIpaEnte(),
				visualizzaCommand.getCodice_iud(), visualizzaCommand.getCodice_iuv(), visualizzaCommand.getDenominazione_attestante(),
				visualizzaCommand.getIdentificativo_univoco_riscossione(), visualizzaCommand.getCodice_identificativo_univoco_versante(),
				visualizzaCommand.getAnagrafica_versante(), visualizzaCommand.getCodice_identificativo_univoco_pagatore(),
				visualizzaCommand.getAnagrafica_pagatore(), visualizzaCommand.getCausale_versamento(), dt_data_esito_singolo_pagamento_da,
				dt_data_esito_singolo_pagamento_a, visualizzaCommand.getIdentificativo_flusso_rendicontazione(),
				visualizzaCommand.getIdentificativo_univoco_regolamento(), dt_data_regolamento_da, dt_data_regolamento_a,
				visualizzaCommand.getCod_tipo_dovuto(), visualizzaCommand.getPage(), visualizzaCommand.getPageSize(), sort);

		List<EnteTipoDovuto> enteTipoDovutos = enteTipoDovutoService.getByCodIpaEnte(enteTO.getCodIpaEnte());
		mav.addObject("listaTipiDovutoPerEnte", enteTipoDovutos);

		mav.addObject("messagesDto", messagesDto);
		mav.addObject(visualizzaCommand);
		mav.addObject("visualizzaDtoPage", visualizzaDtoPage);

		mav.setViewName("visualizza");

		// save session filter data
		Map<String, Object> filtersMap = new HashMap<String, Object>();
		String codIpa = SecurityContext.getEnte().getCodIpaEnte();
		filtersMap.put(SessionVariables.EXPORT_RENDICONTAZIONE_PG, visualizzaCommand.getPage());
		filtersMap.put(SessionVariables.EXPORT_RENDICONTAZIONE_PG_SIZE, visualizzaCommand.getPageSize());
		filtersMap.put(SessionVariables.EXPORT_RENDICONTAZIONE_CODICE_IUD, visualizzaCommand.getCodice_iud());
		filtersMap.put(SessionVariables.EXPORT_RENDICONTAZIONE_CODICE_IUV, visualizzaCommand.getCodice_iuv());
		filtersMap.put(SessionVariables.EXPORT_RENDICONTAZIONE_DENOMINAZIONE_ATTESTANTE, visualizzaCommand.getDenominazione_attestante());
		filtersMap.put(SessionVariables.EXPORT_RENDICONTAZIONE_IDENTIFICATIVO_UNIVOCO_RISCOSSIONE,
				visualizzaCommand.getIdentificativo_univoco_riscossione());
		filtersMap.put(SessionVariables.EXPORT_RENDICONTAZIONE_CODICE_IDENTIFICATIVO_UNIVOCO_VERSANTE,
				visualizzaCommand.getCodice_identificativo_univoco_versante());
		filtersMap.put(SessionVariables.EXPORT_RENDICONTAZIONE_ANAGRAFICA_VERSANTE, visualizzaCommand.getAnagrafica_versante());
		filtersMap.put(SessionVariables.EXPORT_RENDICONTAZIONE_CODICE_IDENTIFICATIVO_UNIVOCO_PAGATORE,
				visualizzaCommand.getCodice_identificativo_univoco_pagatore());
		filtersMap.put(SessionVariables.EXPORT_RENDICONTAZIONE_ANAGRAFICA_PAGATORE, visualizzaCommand.getAnagrafica_pagatore());
		filtersMap.put(SessionVariables.EXPORT_RENDICONTAZIONE_CAUSALE_VERSAMENTO, visualizzaCommand.getCausale_versamento());
		filtersMap.put(SessionVariables.EXPORT_RENDICONTAZIONE_DATA_ESITO_SINGOLO_PAGAMENTO_DA,
				visualizzaCommand.getData_esito_singolo_pagamento_da());
		filtersMap.put(SessionVariables.EXPORT_RENDICONTAZIONE_DATA_ESITO_SINGOLO_PAGAMENTO_A,
				visualizzaCommand.getData_esito_singolo_pagamento_a());
		filtersMap.put(SessionVariables.EXPORT_RENDICONTAZIONE_IDENTIFICATIVO_FLUSSO_RENDICONTAZIONE,
				visualizzaCommand.getIdentificativo_flusso_rendicontazione());
		filtersMap.put(SessionVariables.EXPORT_RENDICONTAZIONE_IDENTIFICATIVO_UNIVOCO_REGOLAMENTO,
				visualizzaCommand.getIdentificativo_univoco_regolamento());
		filtersMap.put(SessionVariables.EXPORT_RENDICONTAZIONE_DATA_REGOLAMENTO_DA, visualizzaCommand.getData_regolamento_da());
		filtersMap.put(SessionVariables.EXPORT_RENDICONTAZIONE_DATA_REGOLAMENTO_A, visualizzaCommand.getData_regolamento_a());
		filtersMap.put(SessionVariables.EXPORT_RENDICONTAZIONE_COD_TIPO_DOVUTO, visualizzaCommand.getCod_tipo_dovuto());
		SecurityContext.setEnteViewFilters(codIpa, SessionVariables.ACTION_VISUALIZZA, filtersMap);
		return mav;
	}

	@RequestMapping(value = { "/esportaCSV.html" }, method = RequestMethod.GET)
	public void exportCSV(HttpServletRequest request, HttpServletResponse response) throws Exception {

		EnteTO enteTO = SecurityContext.getEnte();
		Map<String,Object> filtersMap = SecurityContext.getEnteViewFilters(enteTO.getCodIpaEnte(), SessionVariables.ACTION_VISUALIZZA);
		// GET session filter data
		//request.getSession().setAttribute(SessionVariables.EXPORT_RENDICONTAZIONE_PG, visualizzaCommand.getPage());
		//get.setAttribute(SessionVariables.EXPORT_RENDICONTAZIONE_PG_SIZE, visualizzaCommand.getPageSize());
		String codIUD = (String) filtersMap.get(SessionVariables.EXPORT_RENDICONTAZIONE_CODICE_IUD);
		String codIUV = (String) filtersMap.get(SessionVariables.EXPORT_RENDICONTAZIONE_CODICE_IUV);
		String denominazioneAttestante = (String) filtersMap.get(SessionVariables.EXPORT_RENDICONTAZIONE_DENOMINAZIONE_ATTESTANTE);
		String IUR = (String) filtersMap.get(SessionVariables.EXPORT_RENDICONTAZIONE_IDENTIFICATIVO_UNIVOCO_RISCOSSIONE);
		String idVersante = (String) filtersMap.get(SessionVariables.EXPORT_RENDICONTAZIONE_CODICE_IDENTIFICATIVO_UNIVOCO_VERSANTE);
		String anagraficaVersante = (String) filtersMap.get(SessionVariables.EXPORT_RENDICONTAZIONE_ANAGRAFICA_VERSANTE);
		String idPagatore = (String) filtersMap.get(SessionVariables.EXPORT_RENDICONTAZIONE_CODICE_IDENTIFICATIVO_UNIVOCO_PAGATORE);
		String anagraficaPagatore = (String) filtersMap.get(SessionVariables.EXPORT_RENDICONTAZIONE_ANAGRAFICA_PAGATORE);
		String causaleVersamento = (String) filtersMap.get(SessionVariables.EXPORT_RENDICONTAZIONE_CAUSALE_VERSAMENTO);
		String idFlussoRend = (String) filtersMap.get(SessionVariables.EXPORT_RENDICONTAZIONE_IDENTIFICATIVO_FLUSSO_RENDICONTAZIONE);
		String codIUR = (String) filtersMap.get(SessionVariables.EXPORT_RENDICONTAZIONE_IDENTIFICATIVO_UNIVOCO_REGOLAMENTO);
		String codTipoDovuto = (String) filtersMap.get(SessionVariables.EXPORT_RENDICONTAZIONE_COD_TIPO_DOVUTO);
		String dtEsitoDaString = (String) filtersMap.get(SessionVariables.EXPORT_RENDICONTAZIONE_DATA_ESITO_SINGOLO_PAGAMENTO_DA);
		Date dtEsitoDa = null;

		if (StringUtils.isNotBlank(dtEsitoDaString)) {
			try {
				dtEsitoDa = Constants.DDMMYYYY.parse(dtEsitoDaString);
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		String dtEsitoAString = (String) filtersMap.get(SessionVariables.EXPORT_RENDICONTAZIONE_DATA_ESITO_SINGOLO_PAGAMENTO_A);
		Date dtEsitoA = null;

		if (StringUtils.isNotBlank(dtEsitoAString)) {
			try {
				dtEsitoA = Constants.DDMMYYYY.parse(dtEsitoAString);
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		String dtRegolamentoDaString = (String) filtersMap.get(SessionVariables.EXPORT_RENDICONTAZIONE_DATA_REGOLAMENTO_DA);
		Date dtRegolamentoDa = null;

		if (StringUtils.isNotBlank(dtRegolamentoDaString)) {
			try {
				dtRegolamentoDa = Constants.DDMMYYYY.parse(dtRegolamentoDaString);
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		String dtRegolamentoAString = (String) filtersMap.get(SessionVariables.EXPORT_RENDICONTAZIONE_DATA_REGOLAMENTO_A);
		Date dtRegolamentoA = null;

		if (StringUtils.isNotBlank(dtRegolamentoAString)) {
			try {
				dtRegolamentoA = Constants.DDMMYYYY.parse(dtRegolamentoAString);
			} catch (ParseException e) {
				// Nothing to do
			}
		}
		Sort sort = new Sort(
				 new Order(Direction.DESC, "dtDataRegolamento"), 
				 new Order(Direction.DESC, "identificativoFlussoRendicontazione"), 
				 new Order(Direction.DESC, "identificativoUnivocoRegolamento")
				);
		List<ExportCSVDto> exportCSVDtos = exportRendicontazioneService.getExportRendicontazione(enteTO.getCodIpaEnte(), codIUD, codIUV,
				denominazioneAttestante, IUR, idVersante, anagraficaVersante, idPagatore, anagraficaPagatore, causaleVersamento, dtEsitoDa, dtEsitoA,
				idFlussoRend, codIUR, dtRegolamentoDa, dtRegolamentoA, codTipoDovuto, sort);

		ICsvBeanWriter beanWriter = null;
		String fileName = UUID.randomUUID().toString();
		try {
			beanWriter = new CsvBeanWriter(new FileWriter(fileName + ".csv"), CsvPreference.EXCEL_PREFERENCE);

			PropertyDescriptor[] descriptors = BeanUtils.getPropertyDescriptors(ExportCSVDto.class);
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

			for (ExportCSVDto exportCSVDto : exportCSVDtos) {
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

	private void setFilters(HttpServletRequest request, String pg, String pgSize, String codice_iud, String codice_iuv, String denominazione_attestante,
			String identificativo_univoco_riscossione, String codice_identificativo_univoco_versante, String anagrafica_versante,
			String codice_identificativo_univoco_pagatore, String anagrafica_pagatore, String causale_versamento, String data_esito_singolo_pagamento_da,
			String data_esito_singolo_pagamento_a, String identificativo_flusso_rendicontazione, String identificativo_univoco_regolamento,
			String data_regolamento_da, String data_regolamento_a, String cod_tipo_dovuto, VisualizzaCommand visualizzaCommand) {

		String codIpa = SecurityContext.getEnte().getCodIpaEnte();
		Map<String,Object> filtersMap = SecurityContext.getEnteViewFilters(codIpa, SessionVariables.ACTION_VISUALIZZA);
		Object session_pg = filtersMap == null? null : filtersMap.get(SessionVariables.EXPORT_RENDICONTAZIONE_PG);
		setFilter_pg(request, pg, session_pg, visualizzaCommand);

		Object session_pgSize = filtersMap == null? null : filtersMap.get(SessionVariables.EXPORT_RENDICONTAZIONE_PG_SIZE);
		setFilter_pgSize(request, pgSize, session_pgSize, visualizzaCommand);

		Object session_codice_iud = filtersMap == null? null : filtersMap.get(SessionVariables.EXPORT_RENDICONTAZIONE_CODICE_IUD);
		setFilter_codice_iud(request, codice_iud, session_codice_iud, visualizzaCommand);

		Object session_codice_iuv = filtersMap == null? null : filtersMap.get(SessionVariables.EXPORT_RENDICONTAZIONE_CODICE_IUV);
		setFilter_codice_iuv(request, codice_iuv, session_codice_iuv, visualizzaCommand);

		Object session_denominazione_attestante = filtersMap == null? null : filtersMap.get(SessionVariables.EXPORT_RENDICONTAZIONE_DENOMINAZIONE_ATTESTANTE);
		setFilter_denominazione_attestante(request, denominazione_attestante, session_denominazione_attestante, visualizzaCommand);

		Object session_identificativo_univoco_riscossione = filtersMap == null? null : filtersMap.get(
				SessionVariables.EXPORT_RENDICONTAZIONE_IDENTIFICATIVO_UNIVOCO_RISCOSSIONE);
		setFilter_identificativo_univoco_riscossione(request, identificativo_univoco_riscossione, session_identificativo_univoco_riscossione, visualizzaCommand);

		Object session_codice_identificativo_univoco_versante = filtersMap == null? null : filtersMap.get(
				SessionVariables.EXPORT_RENDICONTAZIONE_CODICE_IDENTIFICATIVO_UNIVOCO_VERSANTE);
		setFilter_codice_identificativo_univoco_versante(request, codice_identificativo_univoco_versante, session_codice_identificativo_univoco_versante,
				visualizzaCommand);

		Object session_anagrafica_versante = filtersMap == null? null : filtersMap.get(SessionVariables.EXPORT_RENDICONTAZIONE_ANAGRAFICA_VERSANTE);
		setFilter_anagrafica_versante(request, anagrafica_versante, session_anagrafica_versante, visualizzaCommand);

		Object session_codice_identificativo_univoco_pagatore = filtersMap == null? null :filtersMap.get(
				SessionVariables.EXPORT_RENDICONTAZIONE_CODICE_IDENTIFICATIVO_UNIVOCO_PAGATORE);
		setFilter_codice_identificativo_univoco_pagatore(request, codice_identificativo_univoco_pagatore, session_codice_identificativo_univoco_pagatore,
				visualizzaCommand);

		Object session_anagrafica_pagatore = filtersMap == null? null :filtersMap.get(SessionVariables.EXPORT_RENDICONTAZIONE_ANAGRAFICA_PAGATORE);
		setFilter_anagrafica_pagatore(request, anagrafica_pagatore, session_anagrafica_pagatore, visualizzaCommand);

		Object session_causale_versamento = filtersMap == null? null :filtersMap.get(SessionVariables.EXPORT_RENDICONTAZIONE_CAUSALE_VERSAMENTO);
		setFilter_causale_versamento(request, causale_versamento, session_causale_versamento, visualizzaCommand);

		Object session_data_esito_singolo_pagamento_da = filtersMap == null? null :filtersMap.get(
				SessionVariables.EXPORT_RENDICONTAZIONE_DATA_ESITO_SINGOLO_PAGAMENTO_DA);
		setFilter_data_esito_singolo_pagamento_da(request, data_esito_singolo_pagamento_da, session_data_esito_singolo_pagamento_da, visualizzaCommand);

		Object session_data_esito_singolo_pagamento_a = filtersMap == null? null :filtersMap.get(
				SessionVariables.EXPORT_RENDICONTAZIONE_DATA_ESITO_SINGOLO_PAGAMENTO_A);
		setFilter_data_esito_singolo_pagamento_a(request, data_esito_singolo_pagamento_a, session_data_esito_singolo_pagamento_a, visualizzaCommand);

		Object session_identificativo_flusso_rendicontazione = filtersMap == null? null :filtersMap.get(
				SessionVariables.EXPORT_RENDICONTAZIONE_IDENTIFICATIVO_FLUSSO_RENDICONTAZIONE);
		setFilter_identificativo_flusso_rendicontazione(request, identificativo_flusso_rendicontazione, session_identificativo_flusso_rendicontazione,
				visualizzaCommand);

		Object session_identificativo_univoco_regolamento = filtersMap == null? null :filtersMap.get(
				SessionVariables.EXPORT_RENDICONTAZIONE_IDENTIFICATIVO_UNIVOCO_REGOLAMENTO);
		setFilter_identificativo_univoco_regolamento(request, identificativo_univoco_regolamento, session_identificativo_univoco_regolamento, visualizzaCommand);

		Object session_data_regolamento_da = filtersMap == null? null :filtersMap.get(SessionVariables.EXPORT_RENDICONTAZIONE_DATA_REGOLAMENTO_DA);
		setFilter_data_regolamento_da(request, data_regolamento_da, session_data_regolamento_da, visualizzaCommand);

		Object session_data_regolamento_a = filtersMap == null? null :filtersMap.get(SessionVariables.EXPORT_RENDICONTAZIONE_DATA_REGOLAMENTO_A);
		setFilter_data_regolamento_a(request, data_regolamento_a, session_data_regolamento_a, visualizzaCommand);

		Object session_cod_tipo_dovuto = filtersMap == null? null :filtersMap.get(SessionVariables.EXPORT_RENDICONTAZIONE_COD_TIPO_DOVUTO);
		setFilter_cod_tipo_dovuto(request, cod_tipo_dovuto, session_cod_tipo_dovuto, visualizzaCommand);

	}

	private void setFilter_data_regolamento_a(HttpServletRequest request, String request_data_regolamento_a, Object session_data_regolamento_a,
			VisualizzaCommand visualizzaCommand) {
		try {
			if (request_data_regolamento_a == null)
				visualizzaCommand.setData_regolamento_a(session_data_regolamento_a.toString());
			else
				visualizzaCommand.setData_regolamento_a(request_data_regolamento_a);
		} catch (NullPointerException e) {
			visualizzaCommand.setData_regolamento_a("");
		}
		
		setSingleFilter(request, SessionVariables.EXPORT_RENDICONTAZIONE_DATA_REGOLAMENTO_A, visualizzaCommand.getData_regolamento_a());

	}

	private void setFilter_data_regolamento_da(HttpServletRequest request, String request_data_regolamento_da, Object session_data_regolamento_da,
			VisualizzaCommand visualizzaCommand) {
		try {
			if (request_data_regolamento_da == null)
				visualizzaCommand.setData_regolamento_da(session_data_regolamento_da.toString());
			else
				visualizzaCommand.setData_regolamento_da(request_data_regolamento_da);
		} catch (NullPointerException e) {
			visualizzaCommand.setData_regolamento_da("");
		}
		setSingleFilter(request, SessionVariables.EXPORT_RENDICONTAZIONE_DATA_REGOLAMENTO_DA, visualizzaCommand.getData_regolamento_da());

	}

	private void setFilter_identificativo_univoco_regolamento(HttpServletRequest request, String request_identificativo_univoco_regolamento,
			Object session_identificativo_univoco_regolamento, VisualizzaCommand visualizzaCommand) {
		try {
			if (request_identificativo_univoco_regolamento == null)
				visualizzaCommand.setIdentificativo_univoco_regolamento(session_identificativo_univoco_regolamento.toString());
			else
				visualizzaCommand.setIdentificativo_univoco_regolamento(request_identificativo_univoco_regolamento);
		} catch (NullPointerException e) {
			visualizzaCommand.setIdentificativo_univoco_regolamento("");
		}
		setSingleFilter(request, SessionVariables.EXPORT_RENDICONTAZIONE_IDENTIFICATIVO_UNIVOCO_REGOLAMENTO,
				visualizzaCommand.getIdentificativo_univoco_regolamento());

	}

	private void setFilter_identificativo_flusso_rendicontazione(HttpServletRequest request, String request_identificativo_flusso_rendicontazione,
			Object session_identificativo_flusso_rendicontazione, VisualizzaCommand visualizzaCommand) {
		try {
			if (request_identificativo_flusso_rendicontazione == null)
				visualizzaCommand.setIdentificativo_flusso_rendicontazione(session_identificativo_flusso_rendicontazione.toString());
			else
				visualizzaCommand.setIdentificativo_flusso_rendicontazione(request_identificativo_flusso_rendicontazione);
		} catch (NullPointerException e) {
			visualizzaCommand.setIdentificativo_flusso_rendicontazione("");
		}
		setSingleFilter(request, SessionVariables.EXPORT_RENDICONTAZIONE_IDENTIFICATIVO_FLUSSO_RENDICONTAZIONE,
				visualizzaCommand.getIdentificativo_flusso_rendicontazione());

	}

	private void setFilter_data_esito_singolo_pagamento_a(HttpServletRequest request, String request_data_esito_singolo_pagamento_a,
			Object session_data_esito_singolo_pagamento_a, VisualizzaCommand visualizzaCommand) {
		try {
			if (request_data_esito_singolo_pagamento_a == null)
				visualizzaCommand.setData_esito_singolo_pagamento_a(session_data_esito_singolo_pagamento_a.toString());
			else
				visualizzaCommand.setData_esito_singolo_pagamento_a(request_data_esito_singolo_pagamento_a);
		} catch (NullPointerException e) {
			visualizzaCommand.setData_esito_singolo_pagamento_a("");
		}
		setSingleFilter(request, SessionVariables.EXPORT_RENDICONTAZIONE_DATA_ESITO_SINGOLO_PAGAMENTO_A,
				visualizzaCommand.getData_esito_singolo_pagamento_a());

	}

	private void setFilter_data_esito_singolo_pagamento_da(HttpServletRequest request, String request_data_esito_singolo_pagamento_da,
			Object session_data_esito_singolo_pagamento_da, VisualizzaCommand visualizzaCommand) {
		try {
			if (request_data_esito_singolo_pagamento_da == null)
				visualizzaCommand.setData_esito_singolo_pagamento_da(session_data_esito_singolo_pagamento_da.toString());
			else
				visualizzaCommand.setData_esito_singolo_pagamento_da(request_data_esito_singolo_pagamento_da);
		} catch (NullPointerException e) {
			visualizzaCommand.setData_esito_singolo_pagamento_da("");
		}
		setSingleFilter(request, SessionVariables.EXPORT_RENDICONTAZIONE_DATA_ESITO_SINGOLO_PAGAMENTO_DA,
				visualizzaCommand.getData_esito_singolo_pagamento_da());

	}

	private void setFilter_causale_versamento(HttpServletRequest request, String request_causale_versamento, Object session_causale_versamento,
			VisualizzaCommand visualizzaCommand) {
		try {
			if (request_causale_versamento == null)
				visualizzaCommand.setCausale_versamento(session_causale_versamento.toString());
			else
				visualizzaCommand.setCausale_versamento(request_causale_versamento);
		} catch (NullPointerException e) {
			visualizzaCommand.setCausale_versamento("");
		}
		setSingleFilter(request, SessionVariables.EXPORT_RENDICONTAZIONE_CAUSALE_VERSAMENTO, visualizzaCommand.getCausale_versamento());

	}

	private void setFilter_anagrafica_pagatore(HttpServletRequest request, String request_anagrafica_pagatore, Object session_anagrafica_pagatore,
			VisualizzaCommand visualizzaCommand) {
		try {
			if (request_anagrafica_pagatore == null)
				visualizzaCommand.setAnagrafica_pagatore(session_anagrafica_pagatore.toString());
			else
				visualizzaCommand.setAnagrafica_pagatore(request_anagrafica_pagatore);
		} catch (NullPointerException e) {
			visualizzaCommand.setAnagrafica_pagatore("");
		}
		setSingleFilter(request,SessionVariables.EXPORT_RENDICONTAZIONE_ANAGRAFICA_PAGATORE, visualizzaCommand.getAnagrafica_pagatore());

	}

	private void setFilter_codice_identificativo_univoco_pagatore(HttpServletRequest request, String request_codice_identificativo_univoco_pagatore,
			Object session_codice_identificativo_univoco_pagatore, VisualizzaCommand visualizzaCommand) {
		try {
			if (request_codice_identificativo_univoco_pagatore == null)
				visualizzaCommand.setCodice_identificativo_univoco_pagatore(session_codice_identificativo_univoco_pagatore.toString());
			else
				visualizzaCommand.setCodice_identificativo_univoco_pagatore(request_codice_identificativo_univoco_pagatore);
		} catch (NullPointerException e) {
			visualizzaCommand.setCodice_identificativo_univoco_pagatore("");
		}
		setSingleFilter(request,SessionVariables.EXPORT_RENDICONTAZIONE_CODICE_IDENTIFICATIVO_UNIVOCO_PAGATORE,
				visualizzaCommand.getCodice_identificativo_univoco_pagatore());

	}

	private void setFilter_anagrafica_versante(HttpServletRequest request, String request_anagrafica_versante, Object session_anagrafica_versante,
			VisualizzaCommand visualizzaCommand) {
		try {
			if (request_anagrafica_versante == null)
				visualizzaCommand.setAnagrafica_versante(session_anagrafica_versante.toString());
			else
				visualizzaCommand.setAnagrafica_versante(request_anagrafica_versante);
		} catch (NullPointerException e) {
			visualizzaCommand.setAnagrafica_versante("");
		}
		setSingleFilter(request,SessionVariables.EXPORT_RENDICONTAZIONE_ANAGRAFICA_VERSANTE, visualizzaCommand.getAnagrafica_versante());

	}

	private void setFilter_codice_identificativo_univoco_versante(HttpServletRequest request, String request_codice_identificativo_univoco_versante,
			Object session_codice_identificativo_univoco_versante, VisualizzaCommand visualizzaCommand) {
		try {
			if (request_codice_identificativo_univoco_versante == null)
				visualizzaCommand.setCodice_identificativo_univoco_versante(session_codice_identificativo_univoco_versante.toString());
			else
				visualizzaCommand.setCodice_identificativo_univoco_versante(request_codice_identificativo_univoco_versante);
		} catch (NullPointerException e) {
			visualizzaCommand.setCodice_identificativo_univoco_versante("");
		}
		setSingleFilter(request,SessionVariables.EXPORT_RENDICONTAZIONE_CODICE_IDENTIFICATIVO_UNIVOCO_VERSANTE,
				visualizzaCommand.getCodice_identificativo_univoco_versante());

	}

	private void setFilter_identificativo_univoco_riscossione(HttpServletRequest request, String request_identificativo_univoco_riscossione,
			Object session_identificativo_univoco_riscossione, VisualizzaCommand visualizzaCommand) {
		try {
			if (request_identificativo_univoco_riscossione == null)
				visualizzaCommand.setIdentificativo_univoco_riscossione(session_identificativo_univoco_riscossione.toString());
			else
				visualizzaCommand.setIdentificativo_univoco_riscossione(request_identificativo_univoco_riscossione);
		} catch (NullPointerException e) {
			visualizzaCommand.setIdentificativo_univoco_riscossione("");
		}
		setSingleFilter(request,SessionVariables.EXPORT_RENDICONTAZIONE_IDENTIFICATIVO_UNIVOCO_RISCOSSIONE,
				visualizzaCommand.getIdentificativo_univoco_riscossione());

	}

	private void setFilter_denominazione_attestante(HttpServletRequest request, String request_denominazione_attestante,
			Object session_denominazione_attestante, VisualizzaCommand visualizzaCommand) {
		try {
			if (request_denominazione_attestante == null)
				visualizzaCommand.setDenominazione_attestante(session_denominazione_attestante.toString());
			else
				visualizzaCommand.setDenominazione_attestante(request_denominazione_attestante);
		} catch (NullPointerException e) {
			visualizzaCommand.setDenominazione_attestante("");
		}
		setSingleFilter(request,SessionVariables.EXPORT_RENDICONTAZIONE_DENOMINAZIONE_ATTESTANTE, visualizzaCommand.getDenominazione_attestante());

	}

	private void setFilter_codice_iuv(HttpServletRequest request, String request_codice_iuv, Object session_codice_iuv, VisualizzaCommand visualizzaCommand) {
		try {
			if (request_codice_iuv == null)
				visualizzaCommand.setCodice_iuv(session_codice_iuv.toString());
			else
				visualizzaCommand.setCodice_iuv(request_codice_iuv);
		} catch (NullPointerException e) {
			visualizzaCommand.setCodice_iuv("");
		}
		setSingleFilter(request,SessionVariables.EXPORT_RENDICONTAZIONE_CODICE_IUV, visualizzaCommand.getCodice_iuv());

	}

	private void setFilter_codice_iud(HttpServletRequest request, String request_codice_iud, Object session_codice_iud, VisualizzaCommand visualizzaCommand) {
		try {
			if (request_codice_iud == null)
				visualizzaCommand.setCodice_iud(session_codice_iud.toString());
			else
				visualizzaCommand.setCodice_iud(request_codice_iud);
		} catch (NullPointerException e) {
			visualizzaCommand.setCodice_iud("");
		}
		setSingleFilter(request,SessionVariables.EXPORT_RENDICONTAZIONE_CODICE_IUD, visualizzaCommand.getCodice_iud());

	}

	private void setFilter_pgSize(HttpServletRequest request, String request_pgSize, Object session_pgSize, VisualizzaCommand visualizzaCommand) {
		try {
			if (request_pgSize == null)
				visualizzaCommand.setPageSize(Integer.parseInt(session_pgSize.toString()));
			else
				visualizzaCommand.setPageSize(Integer.parseInt(request_pgSize));
		} catch (NullPointerException e) {
			visualizzaCommand.setPageSize(5);
		} catch (NumberFormatException e) {
			visualizzaCommand.setPageSize(5);
		}
		setSingleFilter(request,SessionVariables.EXPORT_RENDICONTAZIONE_PG_SIZE, visualizzaCommand.getPageSize());

	}

	private void setFilter_pg(HttpServletRequest request, String request_pg, Object session_pg, VisualizzaCommand visualizzaCommand) {
		try {
			if (request_pg == null)
				visualizzaCommand.setPage(Integer.parseInt(session_pg.toString()));
			else {
				visualizzaCommand.setPage(Integer.parseInt(request_pg));
			}
		} catch (NullPointerException e) {
			visualizzaCommand.setPage(1);
		} catch (NumberFormatException e) {
			visualizzaCommand.setPage(1);
		}

		setSingleFilter(request,SessionVariables.EXPORT_RENDICONTAZIONE_PG, visualizzaCommand.getPage());

	}

	
	private void setFilter_cod_tipo_dovuto(HttpServletRequest request, String request_cod_tipo_dovuto, Object session_cod_tipo_dovuto,
			VisualizzaCommand visualizzaCommand) {
		try {
			if (request_cod_tipo_dovuto == null)
				visualizzaCommand.setCod_tipo_dovuto(session_cod_tipo_dovuto.toString());
			else
				visualizzaCommand.setCod_tipo_dovuto(request_cod_tipo_dovuto);
		} catch (NullPointerException e) {
			visualizzaCommand.setCod_tipo_dovuto("");
		}
		setSingleFilter(request, SessionVariables.EXPORT_RENDICONTAZIONE_COD_TIPO_DOVUTO, visualizzaCommand.getCod_tipo_dovuto());

	}

	
	private void setSingleFilter(HttpServletRequest request, String key, Object value){
		String codIpa = SecurityContext.getEnte().getCodIpaEnte();
		Map<String,Object> filtersMap = SecurityContext.getEnteViewFilters(codIpa, SessionVariables.ACTION_VISUALIZZA);
		if(filtersMap == null){
			filtersMap = new HashMap<String,Object>();
		}
		filtersMap.put(key, value);
		SecurityContext.setEnteViewFilters(codIpa, SessionVariables.ACTION_VISUALIZZA, filtersMap);
	}
	
	/**
	 * @param target
	 */
	private void initialize(VisualizzaCommand visualizzaCommand, int numGiorniPagamentoPresunti) {

		Date data_esito_singolo_pagamento_da = null;

		if (StringUtils.isNotBlank(visualizzaCommand.getData_esito_singolo_pagamento_da())) {
			try {
				data_esito_singolo_pagamento_da = Constants.DDMMYYYY.parse(visualizzaCommand.getData_esito_singolo_pagamento_da());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date data_esito_singolo_pagamento_a = null;

		if (StringUtils.isNotBlank(visualizzaCommand.getData_esito_singolo_pagamento_a())) {
			try {
				data_esito_singolo_pagamento_a = Constants.DDMMYYYY.parse(visualizzaCommand.getData_esito_singolo_pagamento_a());
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

		visualizzaCommand.setData_esito_singolo_pagamento_da(Constants.DDMMYYYY.format(data_esito_singolo_pagamento_da));
		visualizzaCommand.setData_esito_singolo_pagamento_a(Constants.DDMMYYYY.format(data_esito_singolo_pagamento_a));

		Date data_regolamento_da = null;

		if (StringUtils.isNotBlank(visualizzaCommand.getData_regolamento_da())) {
			try {
				data_regolamento_da = Constants.DDMMYYYY.parse(visualizzaCommand.getData_regolamento_da());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date data_regolamento_a = null;

		if (StringUtils.isNotBlank(visualizzaCommand.getData_regolamento_a())) {
			try {
				data_regolamento_a = Constants.DDMMYYYY.parse(visualizzaCommand.getData_regolamento_a());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		if ((data_regolamento_da == null) && (data_regolamento_a == null)) {
			data_regolamento_a = new Date();
			data_regolamento_a = DateUtils.addDays(data_regolamento_a, numGiorniPagamentoPresunti);
			
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
		EnteTO enteTO = SecurityContext.getEnte();
		Map<String,Object> filtersMap = SecurityContext.getEnteViewFilters(enteTO.getCodIpaEnte(), SessionVariables.ACTION_VISUALIZZA);
		Object session_data_esito_singolo_pagamento_da = filtersMap.get(SessionVariables.EXPORT_RENDICONTAZIONE_DATA_ESITO_SINGOLO_PAGAMENTO_DA);
		Object session_data_esito_singolo_pagamento_a = filtersMap.get(SessionVariables.EXPORT_RENDICONTAZIONE_DATA_ESITO_SINGOLO_PAGAMENTO_A);
		Object session_data_regolamento_da = filtersMap.get(SessionVariables.EXPORT_RENDICONTAZIONE_DATA_REGOLAMENTO_DA);
		Object session_data_regolamento_a = filtersMap.get(SessionVariables.EXPORT_RENDICONTAZIONE_DATA_REGOLAMENTO_A);
		
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Calendar data = Calendar.getInstance();        
		String todayDateString = df.format(data.getTime());
		data.set(Calendar.MONTH, data.get(Calendar.MONTH)-1);
		String oneMonthAgoDateString = df.format(data.getTime());
		if(session_data_esito_singolo_pagamento_da == null || session_data_esito_singolo_pagamento_da.toString().isEmpty()){
			filtersMap.put(SessionVariables.EXPORT_RENDICONTAZIONE_DATA_ESITO_SINGOLO_PAGAMENTO_DA, oneMonthAgoDateString);
		}
		if(session_data_esito_singolo_pagamento_a == null || session_data_esito_singolo_pagamento_a.toString().isEmpty()){
			filtersMap.put(SessionVariables.EXPORT_RENDICONTAZIONE_DATA_ESITO_SINGOLO_PAGAMENTO_A, todayDateString);

		}
		if(session_data_regolamento_da == null || session_data_regolamento_da.toString().isEmpty()){
			filtersMap.put(SessionVariables.EXPORT_RENDICONTAZIONE_DATA_REGOLAMENTO_DA, oneMonthAgoDateString);

		}
		if(session_data_regolamento_a == null || session_data_regolamento_a.toString().isEmpty()){
			filtersMap.put(SessionVariables.EXPORT_RENDICONTAZIONE_DATA_REGOLAMENTO_A, todayDateString);

		}
		SecurityContext.setEnteViewFilters(enteTO.getCodIpaEnte(), SessionVariables.ACTION_VISUALIZZA, filtersMap);
		visualizzaCommand.setData_regolamento_da(Constants.DDMMYYYY.format(data_regolamento_da));
		visualizzaCommand.setData_regolamento_a(Constants.DDMMYYYY.format(data_regolamento_a));
	}
	
}
