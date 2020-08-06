package it.regioneveneto.mygov.payment.mypivotsb.controller;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import it.regioneveneto.mygov.payment.mypivot.controller.command.VisualizzaCompletaCommand;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.DynamicMessageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.MessagesDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.PageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.PrenotaExportFlussoRiconciliazioneRispostaDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.RendicontazioneSubsetDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.RendicontazioneTesoreriaSubsetDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.TesoreriaNoMatchSubsetDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.TesoreriaSubsetDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.VisualizzaCompletaDto;
import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.PrenotazioneFlussoRiconciliazioneTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.UtenteTO;
import it.regioneveneto.mygov.payment.mypivot.service.EnteService;
import it.regioneveneto.mygov.payment.mypivot.service.ImportExportRendicontazioneTesoreriaService;
import it.regioneveneto.mygov.payment.mypivot.service.PrenotazioneFlussoRiconciliazioneService;
import it.regioneveneto.mygov.payment.mypivot.service.RendicontazioneSubsetService;
import it.regioneveneto.mygov.payment.mypivot.service.RendicontazioneTesoreriaSubsetService;
import it.regioneveneto.mygov.payment.mypivot.service.TesoreriaSubsetService;
import it.regioneveneto.mygov.payment.mypivot.service.UtenteService;
import it.regioneveneto.mygov.payment.mypivot.service.UtilityService;
import it.regioneveneto.mygov.payment.mypivot.service.exception.NessunTipoDovutoAttivoException;
import it.regioneveneto.mygov.payment.mypivot.service.exception.TipoDovutoNonValidoPerUtenteException;
import it.regioneveneto.mygov.payment.mypivot.utils.Constants;
import it.regioneveneto.mygov.payment.mypivot.utils.Utilities;
import it.regioneveneto.mygov.payment.mypivot.utils.Constants.VISUALIZZA_NASCOSTI;
import it.regioneveneto.mygov.payment.mypivotsb.constant.ApiDescription;
import it.regioneveneto.mygov.payment.mypivotsb.constant.Response;
import it.regioneveneto.mygov.payment.mypivotsb.response.IoResponse;
import it.regioneveneto.mygov.payment.mypivotsb.response.PageDtoResponse;
import it.regioneveneto.mygov.payment.mypivotsb.response.ResponseIF;
import it.regioneveneto.mygov.payment.mypivotsb.response.SingleDataResponse;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api(tags = { "Riconciliazioni" })
public class VisualizzaCompletaController extends BaseController {
	
	@Autowired
	private ImportExportRendicontazioneTesoreriaService importExportRendicontazioneTesoreriaService;

	@Autowired
	private RendicontazioneSubsetService rendicontazioneSubsetService;

	@Autowired
	private TesoreriaSubsetService tesoreriaSubsetService;

	@Autowired
	private PrenotazioneFlussoRiconciliazioneService prenotazioneFlussoRiconciliazioneService;

	@Autowired
	private RendicontazioneTesoreriaSubsetService rendicontazioneTesoreriaSubsetService;

	@Autowired
	private EnteService enteService;
	
	@Autowired
	private UtenteService utenteService;
		
	@Resource
	private Environment env;

	@Value("${myPivot.enableGlobalProfile}")
	private Boolean enableGlobalProfile;

	@Autowired
	private UtilityService utilityService;
	
	public VisualizzaCompletaController() {
		super();
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "get all Riconcilazioni", notes = ApiDescription.DESCR_GET_ALL_RICONCILIAZIONI, response = PageDtoResponse.class)
	@GetMapping(value = "/riconciliazioni")	
	public ResponseIF getRiconciliazioni(
			
			Pageable pageable,			
			@RequestParam(required = true) String codIpa,
			
			@RequestParam(required = false) String codice_iud, 
			@RequestParam(required = false) String codice_iuv,
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
			@RequestParam(required = false) String data_valuta_da, 
			@RequestParam(required = false) String data_valuta_a,
			@RequestParam(required = false) String cod_tipo_dovuto, 
			@RequestParam(required = false) String conto,
			@RequestParam(required = false) String importo, 
			@RequestParam(required = false) String codOr1,
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
			@RequestParam(required = false) String errorCode, 
			@RequestParam(required = false) String visualizzaNascosti
//			@RequestParam(required = false) Boolean exportSuccess, 
//			@RequestParam(required = false) Boolean exportFailed,
//			@RequestParam(required = false) String tipoVisualizzazione,
//			@RequestParam(required = false) Boolean classificazioneNonValida			
			) throws ParseException {		
		try {
			PageDtoResponse<?> response = new PageDtoResponse();
						
			EnteTO enteTO = enteService.getByCodIpaEnte(codIpa);			
			if (enteTO == null) {
				MessagesDto messagesDto = new MessagesDto();
				messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.codIpaEnteNonValido"));
				response.setMessages(messagesDto);
				return response;
			}
			
			if (!utilityService.verificaClassificazione(errorCode, enteTO.getFlgTesoreria(), enteTO.getFlgTesoreria(), "R") &&
					!utilityService.verificaClassificazione(errorCode, enteTO.getFlgTesoreria(), enteTO.getFlgTesoreria(), "A")) {
				MessagesDto messagesDto = new MessagesDto();
				messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.classificazioneCompletezzaNonValida"));
				response.setMessages(messagesDto);
				return response;			
			}
			
			
			UtenteTO utenteTO = null;
			
			if(enableGlobalProfile) 
				utenteTO = utenteService.getUtenteByGlobalDefault();	

			VisualizzaCompletaCommand visualizzaCompletaCommand = new VisualizzaCompletaCommand();			
			MessagesDto messagesDto = new MessagesDto();
			
			Integer page = pageable.getPageNumber();
//			Integer pgSize = pageable.getPageSize();
			
			int pageToGet = 1;
			if (page > 0) {
				pageToGet = page;
			}
			
			setFilters(codIpa, 
//					Integer.toString(pageable.getPageNumber()), Integer.toString(pageable.getPageSize()) ,
					Integer.toString(pageToGet), Integer.toString(pageable.getPageSize()) ,
					codice_iud, codice_iuv, denominazione_attestante,
					identificativo_univoco_riscossione, codice_identificativo_univoco_versante, anagrafica_versante,
					codice_identificativo_univoco_pagatore, anagrafica_pagatore, causale_versamento,
					data_esito_singolo_pagamento_da, data_esito_singolo_pagamento_a, identificativo_flusso_rendicontazione,
					identificativo_univoco_regolamento, data_regolamento_da, data_regolamento_a, data_contabile_da,
					data_contabile_a, data_valuta_da, data_valuta_a, cod_tipo_dovuto, conto, importo, codOr1,
					data_valuta_check, data_esito_check, data_regolamento_check, data_contabile_check,
					data_esecuzione_singolo_pagamento_da, data_esecuzione_singolo_pagamento_a, data_esecuzione_check,
					data_ultimo_aggiornamento_da, data_ultimo_aggiornamento_a, data_ultimo_aggiornamento_check, errorCode,
					visualizzaNascosti, visualizzaCompletaCommand);

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

			String classificazione = visualizzaCompletaCommand.getTipoErrore()!= null ? visualizzaCompletaCommand.getTipoErrore() :"";
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

				response.setPage(visualizzaCompletaDtoPage);
	
			} else if (classificazione.equals(Constants.COD_ERRORE_IUF_NO_TES)
					|| classificazione.equals(Constants.COD_ERRORE_IUV_NO_RT)) {

				Sort sort = getRendicontazioneSubsetSort();

				PageDto<RendicontazioneSubsetDto> rendicontazioneSubsetDtoPage = rendicontazioneSubsetService
						.getRendicontazioneSubsetPageFunction(enteTO.getCodIpaEnte(),
								visualizzaCompletaCommand.getIdentificativo_flusso_rendicontazione(),
								visualizzaCompletaCommand.getIdentificativo_univoco_regolamento(), dt_data_regolamento_da,
								dt_data_regolamento_a, dt_data_ultimo_aggiornamento_da, dt_data_ultimo_aggiornamento_a,
								classificazione, visualizzaCompletaCommand.getCod_tipo_dovuto(), utenteTO.getCodFedUserId(),
								visualizzaNascostiBool, visualizzaCompletaCommand.getPage(),
								visualizzaCompletaCommand.getPageSize(), sort);

				response.setPage(rendicontazioneSubsetDtoPage);
			} else if (classificazione.equals(Constants.COD_ERRORE_TES_NO_IUF_OR_IUV)) {
				PageDto<TesoreriaSubsetDto> tesoreriaSubsetDtoPage = tesoreriaSubsetService.getTesoreriaSubsetPageFunction(
						enteTO.getCodIpaEnte(), dt_data_contabile_da, dt_data_contabile_a, dt_data_valuta_da,
						dt_data_valuta_a, dt_data_ultimo_aggiornamento_da, dt_data_ultimo_aggiornamento_a,
						visualizzaCompletaCommand.getImporto(), visualizzaCompletaCommand.getConto(),
						visualizzaCompletaCommand.getCodOr1(), classificazione, visualizzaNascostiBool,
						visualizzaCompletaCommand.getCodice_iuv(),
						visualizzaCompletaCommand.getIdentificativo_flusso_rendicontazione(),
						visualizzaCompletaCommand.getPage(), visualizzaCompletaCommand.getPageSize());
				
				response.setPage(tesoreriaSubsetDtoPage);
			} else if (classificazione.equals(Constants.COD_ERRORE_TES_NO_MATCH)) {
				PageDto<TesoreriaNoMatchSubsetDto> tesoreriaNoMatchSubsetDtoPage = tesoreriaSubsetService
						.getTesoreriaNoMatchSubsetPageFunction(enteTO.getCodIpaEnte(), dt_data_contabile_da,
								dt_data_contabile_a, dt_data_valuta_da, dt_data_valuta_a, dt_data_ultimo_aggiornamento_da,
								dt_data_ultimo_aggiornamento_a, visualizzaCompletaCommand.getCausale_versamento(),
								visualizzaCompletaCommand.getImporto(), visualizzaCompletaCommand.getConto(),
								visualizzaCompletaCommand.getCodOr1(), visualizzaCompletaCommand.getTipoErrore(),
								visualizzaNascostiBool, visualizzaCompletaCommand.getPage(),
								visualizzaCompletaCommand.getPageSize());
				response.setPage(tesoreriaNoMatchSubsetDtoPage);
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
				response.setPage(rendicontazioneTesoreriaSubsetDtoPage);
			}

			if (visualizzaCompletaCommand.getTipoErrore()!=null && !visualizzaCompletaCommand.getTipoErrore().isEmpty()) {
				messagesDto.getInformationMessages().add(new DynamicMessageDto(
						"mypivot.classificazioni." + visualizzaCompletaCommand.getTipoErrore() + ".info"));
			}

//			if (exportSuccess != null && exportSuccess) {
//				messagesDto.getSuccessMessages().add(new DynamicMessageDto("mypivot.export.success"));
//			} else if (exportFailed != null && exportFailed) {
//				messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.export.errore"));
//			} else if (classificazioneNonValida != null && classificazioneNonValida) {
//				messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.export.errore.classificazioneNonValida"));
//			}

			response.setMessages(messagesDto);

			return response;					
		
		} catch (EntityNotFoundException entityNotFoundException) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, entityNotFoundException.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, String.format(Response.RESPONSE_KO, e.getMessage()));
		}				

	}
	

	private void setFilters(String codIpa, String pg, String pgSize, String codice_iud, String codice_iuv,
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
			VisualizzaCompletaCommand visualizzaTesoreriaCommand) {

			visualizzaTesoreriaCommand.setPage(Integer.parseInt(pg));
			visualizzaTesoreriaCommand.setPageSize(Integer.parseInt(pgSize));
			visualizzaTesoreriaCommand.setCodice_iud(codice_iud);
			visualizzaTesoreriaCommand.setCodice_iuv(codice_iuv);
			visualizzaTesoreriaCommand.setDenominazione_attestante(denominazione_attestante);
			visualizzaTesoreriaCommand.setIdentificativo_univoco_riscossione(identificativo_univoco_riscossione);
			visualizzaTesoreriaCommand.setCodice_identificativo_univoco_versante(codice_identificativo_univoco_versante);
			visualizzaTesoreriaCommand.setAnagrafica_versante(anagrafica_versante);
			visualizzaTesoreriaCommand.setCodice_identificativo_univoco_pagatore(codice_identificativo_univoco_pagatore);
	
			visualizzaTesoreriaCommand.setAnagrafica_pagatore(anagrafica_pagatore);
			visualizzaTesoreriaCommand.setCausale_versamento(causale_versamento);
			visualizzaTesoreriaCommand.setData_esecuzione_singolo_pagamento_da(data_esecuzione_singolo_pagamento_da);
			visualizzaTesoreriaCommand.setData_esecuzione_singolo_pagamento_a(data_esecuzione_singolo_pagamento_a);
			visualizzaTesoreriaCommand.setData_esito_singolo_pagamento_da(data_esito_singolo_pagamento_da);
			visualizzaTesoreriaCommand.setData_esito_singolo_pagamento_a(data_esito_singolo_pagamento_a);
			visualizzaTesoreriaCommand.setData_contabile_da(data_contabile_da);
			visualizzaTesoreriaCommand.setData_contabile_a(data_contabile_a);
			visualizzaTesoreriaCommand.setData_valuta_da(data_valuta_da);
			visualizzaTesoreriaCommand.setData_valuta_a(data_valuta_a);
			visualizzaTesoreriaCommand.setIdentificativo_flusso_rendicontazione(identificativo_flusso_rendicontazione);
			visualizzaTesoreriaCommand.setIdentificativo_univoco_regolamento(identificativo_univoco_regolamento);
			visualizzaTesoreriaCommand.setData_regolamento_da(data_regolamento_da);
			visualizzaTesoreriaCommand.setData_regolamento_a(data_regolamento_a);
			visualizzaTesoreriaCommand.setCod_tipo_dovuto(cod_tipo_dovuto);
			visualizzaTesoreriaCommand.setImporto(importo);
			visualizzaTesoreriaCommand.setImporto(conto);
			visualizzaTesoreriaCommand.setCodOr1(codOr1);
			visualizzaTesoreriaCommand.setData_valuta_check(data_valuta_check);
			visualizzaTesoreriaCommand.setData_regolamento_check(data_risultato_check);
			visualizzaTesoreriaCommand.setData_esecuzione_check(data_esecuzione_check);
			visualizzaTesoreriaCommand.setData_esito_check(data_esito_check);
			visualizzaTesoreriaCommand.setData_contabile_check(data_contabile_check);
			visualizzaTesoreriaCommand.setTipoErrore(errorCode);
			visualizzaTesoreriaCommand.setData_ultimo_aggiornamento_da(data_ultimo_aggiornamento_da);
			visualizzaTesoreriaCommand.setData_ultimo_aggiornamento_a(data_ultimo_aggiornamento_a);
			visualizzaTesoreriaCommand.setData_ultimo_aggiornamento_check(data_ultimo_aggiornamento_check);
			visualizzaTesoreriaCommand.setVisualizzaNascosti(visualizzaNascosti);

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

	private Sort getRendicontazioneSubsetSort() {
		List<Order> orders = new ArrayList<>();
		orders.add(new Order(Direction.ASC, "dtDataRegolamento"));
		orders.add(new Order(Direction.ASC, "id.identificativoFlussoRendicontazione"));
		return Sort.by(orders);
	}
	


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "prenota esportazione Riconcilazioni", notes = ApiDescription.DESCR_EXPORT_RICONCILIAZIONI, response = SingleDataResponse.class)
	@GetMapping(value = "/riconciliazioni/esportaRiconciliazioni")				
	public ResponseIF esportaRiconciliazioni(
//			Pageable pageable,
			
			@RequestParam(required = true) String versioneTracciato,		
			
			@RequestParam(required = true) String codIpa,			
			@RequestParam(required = false) String codIUD, 
			@RequestParam(required = false) String codIUV,
			@RequestParam(required = false) String denominazioneAttestante,
			@RequestParam(required = false) String identificativoUnivocoRiscossione,
			@RequestParam(required = false) String idVersante,
			@RequestParam(required = false) String anagraficaVersante,
			@RequestParam(required = false) String idPagatore,
			@RequestParam(required = false) String anagraficaPagatore,
			@RequestParam(required = false) String causaleVersamento,
				
			@RequestParam(required = false) String idFlussoRend,
			@RequestParam(required = false) String identificativoUnivocoRegolamento,
			
			@RequestParam(required = false) String codTipoDovuto, 
			
			@RequestParam(required = false) String conto,
			
			@RequestParam(required = false) String importo, 
			@RequestParam(required = false) String codOR1,

			@RequestParam(required = false) String dtUltimoAggCheckString,
			@RequestParam(required = false) String dtUltimoAggAString,			
			@RequestParam(required = false) String dtUltimoAggDaString,

			@RequestParam(required = false) String dtEsecuzioneCheckString,
			@RequestParam(required = false) String dtEsecuzioneDaString,			
			@RequestParam(required = false) String dtEsecuzioneAString,
			
			@RequestParam(required = false) String dtEsitoCheckString,
			@RequestParam(required = false) String dtEsitoDaString,			
			@RequestParam(required = false) String dtEsitoAString,
			
			@RequestParam(required = false) String dtRegolamentoCheckString,
			@RequestParam(required = false) String dtRegolamentoDaString,
			@RequestParam(required = false) String dtRegolamentoAString,
			
			@RequestParam(required = false) String dtContabileCheckString,
			@RequestParam(required = false) String dtContabileDaString,			
			@RequestParam(required = false) String dtContabileAString,
			
			@RequestParam(required = false) String dtValutaCheckString,
			@RequestParam(required = false) String dtValutaDaString,
			@RequestParam(required = false) String dtValutaAString,
			
			
			@RequestParam(required = false) String errorCode, 
			@RequestParam(required = false) String visualizzaNascosti

//			@RequestParam(required = false) Boolean exportSuccess, 
//			@RequestParam(required = false) Boolean exportFailed,
//			@RequestParam(required = false) String tipoVisualizzazione,
//			@RequestParam(required = false) Boolean classificazioneNonValida			
			
			) throws Exception {

		SingleDataResponse<?> response = new SingleDataResponse();

		EnteTO enteTO = enteService.getByCodIpaEnte(codIpa);			
		if (enteTO == null) {
			MessagesDto messagesDto = new MessagesDto();
			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.codIpaEnteNonValido"));
			response.setMessages(messagesDto);
			return response;
		}
		
		if (!utilityService.verificaClassificazione(errorCode, enteTO.getFlgTesoreria(), enteTO.getFlgTesoreria(), "R") &&
				!utilityService.verificaClassificazione(errorCode, enteTO.getFlgTesoreria(), enteTO.getFlgTesoreria(), "A")) {
			MessagesDto messagesDto = new MessagesDto();
			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.classificazioneCompletezzaNonValida"));
			response.setMessages(messagesDto);
			return response;			
		}
		
		
		UtenteTO utenteTO = null;
		
		if(enableGlobalProfile) 
			utenteTO = utenteService.getUtenteByGlobalDefault();	
		
		if (StringUtils.isBlank(versioneTracciato)) {

			MessagesDto messagesDto = new MessagesDto();
			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.versioneTracciatoErrata"));
			
			response.setMessages(messagesDto);
			return response;
		}


		if (StringUtils.isNotBlank(causaleVersamento)
				&& causaleVersamento.length() > Constants.CAUSALE_MAX_LENGTH_EXPORT_RICONCILIAZIONE_VERSIONE) {
			causaleVersamento = causaleVersamento.substring(0,
					Constants.CAUSALE_MAX_LENGTH_EXPORT_RICONCILIAZIONE_VERSIONE);
		}

		Date dtUltimoAggDa = null;
		if (StringUtils.isNotBlank(dtUltimoAggCheckString) && dtUltimoAggCheckString.equals("on")
				&& StringUtils.isNotBlank(dtUltimoAggDaString)) {
			try {
				dtUltimoAggDa = Constants.DDMMYYYY.parse(dtUltimoAggDaString);
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date dtUltimoAggA = null;

		if (StringUtils.isNotBlank(dtUltimoAggCheckString) && dtUltimoAggCheckString.equals("on")
				&& StringUtils.isNotBlank(dtUltimoAggAString)) {
			try {
				dtUltimoAggA = Constants.DDMMYYYY.parse(dtUltimoAggAString);
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date dtEsecuzioneDa = null;

		if (StringUtils.isNotBlank(dtEsecuzioneCheckString) && dtEsecuzioneCheckString.equals("on")
				&& StringUtils.isNotBlank(dtEsecuzioneDaString)) {
			try {
				dtEsecuzioneDa = Constants.DDMMYYYY.parse(dtEsecuzioneDaString);
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date dtEsecuzioneA = null;

		if (StringUtils.isNotBlank(dtEsecuzioneCheckString) && dtEsecuzioneCheckString.equals("on")
				&& StringUtils.isNotBlank(dtEsecuzioneAString)) {
			try {
				dtEsecuzioneA = Constants.DDMMYYYY.parse(dtEsecuzioneAString);
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date dtEsitoDa = null;

		if (StringUtils.isNotBlank(dtEsitoCheckString) && dtEsitoCheckString.equals("on")
				&& StringUtils.isNotBlank(dtEsitoDaString)) {
			try {
				dtEsitoDa = Constants.DDMMYYYY.parse(dtEsitoDaString);
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date dtEsitoA = null;

		if (StringUtils.isNotBlank(dtEsitoCheckString) && dtEsitoCheckString.equals("on")
				&& StringUtils.isNotBlank(dtEsitoAString)) {
			try {
				dtEsitoA = Constants.DDMMYYYY.parse(dtEsitoAString);
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date dtRegolamentoDa = null;

		if (StringUtils.isNotBlank(dtRegolamentoCheckString) && dtRegolamentoCheckString.equals("on")
				&& StringUtils.isNotBlank(dtRegolamentoDaString)) {
			try {
				dtRegolamentoDa = Constants.DDMMYYYY.parse(dtRegolamentoDaString);
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date dtRegolamentoA = null;

		if (StringUtils.isNotBlank(dtRegolamentoCheckString) && dtRegolamentoCheckString.equals("on")
				&& StringUtils.isNotBlank(dtRegolamentoAString)) {
			try {
				dtRegolamentoA = Constants.DDMMYYYY.parse(dtRegolamentoAString);
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date dtContabileDa = null;

		if (StringUtils.isNotBlank(dtContabileCheckString) && dtContabileCheckString.equals("on")
				&& StringUtils.isNotBlank(dtContabileDaString)) {
			try {
				dtContabileDa = Constants.DDMMYYYY.parse(dtContabileDaString);
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date dtContabileA = null;

		if (StringUtils.isNotBlank(dtContabileCheckString) && dtContabileCheckString.equals("on")
				&& StringUtils.isNotBlank(dtContabileAString)) {
			try {
				dtContabileA = Constants.DDMMYYYY.parse(dtContabileAString);
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date dtValutaDa = null;

		if (StringUtils.isNotBlank(dtValutaCheckString) && dtValutaCheckString.equals("on")
				&& StringUtils.isNotBlank(dtValutaDaString)) {
			try {
				dtValutaDa = Constants.DDMMYYYY.parse(dtValutaDaString);
			} catch (ParseException e) {
				// Nothing to do
			}
		}

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
					.insertPrenotazioneFlussoRiconciliazione(codIpa, utenteTO.getCodFedUserId(),
							Constants.COD_TIPO_STATO_EXPORT_FLUSSO_RICONCILIAZIONE_PRENOTATO,
							Constants.DE_TIPO_STATO_PRENOTA_FLUSSO_RICONCILIAZIONE, errorCode, codTipoDovuto, codIUV,
							idFlussoRend, dtUltimoAggDa, dtUltimoAggA, dtEsecuzioneDa, dtEsecuzioneA, dtEsitoDa,
							dtEsitoA, dtRegolamentoDa, dtRegolamentoA, dtContabileDa, dtContabileA, dtValutaDa,
							dtValutaA, codIUD, identificativoUnivocoRiscossione, idPagatore, anagraficaPagatore,
							idVersante, anagraficaVersante, denominazioneAttestante, codOR1,
							identificativoUnivocoRegolamento, conto, importo, causaleVersamento, versioneTracciato);
			if (prenotazione != null) {
				PrenotaExportFlussoRiconciliazioneRispostaDto datiPrenotazione = new PrenotaExportFlussoRiconciliazioneRispostaDto();
				datiPrenotazione.setRequestToken(prenotazione.getCodRequestToken());

				if (prenotazione.getDtDataUltimoAggiornamentoDa() != null
						&& prenotazione.getDtDataUltimoAggiornamentoA() != null) {
					datiPrenotazione.setDataA(
							Utilities.toXMLGregorianCalendarWithoutTimezone(prenotazione.getDtDataUltimoAggiornamentoA()));
				}
				SingleDataResponse resp_prenotazione = new SingleDataResponse<>(datiPrenotazione);
				MessagesDto messagesDto = new MessagesDto();
				messagesDto.getSuccessMessages().add(new DynamicMessageDto("mypivot.messages.success.bookingExportSuccess"));
				resp_prenotazione.setMessages(messagesDto);
				return resp_prenotazione;
				//return new IoResponse("OK", "Booking Export Success");
			} else {
				return new IoResponse("KO", "Export failed");
			}
		} catch (NessunTipoDovutoAttivoException e) {
			MessagesDto messagesDto = new MessagesDto();
			messagesDto.getErrorMessages()
					.add(new DynamicMessageDto("mypivot.messages.error.nessunTipoDovutoAssegnato"));
			response.setMessages(messagesDto);
			return response;
		} catch (TipoDovutoNonValidoPerUtenteException e) {
			MessagesDto messagesDto = new MessagesDto();
			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.tipoDovutoNonValido"));
			response.setMessages(messagesDto);
			return response;
		} catch (Exception e) {
			return new IoResponse("KO", "Export failed");
		}

	}

	
}