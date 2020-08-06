package it.regioneveneto.mygov.payment.mypivotsb.controller;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.AggiornaSegnalazioneDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.AggiornaSegnalazioneResultDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.AggiornaSegnalazioneResultDto.ESITO;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.DynamicMessageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.MessagesDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.PageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.SegnalazioneDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.dettaglio.DettaglioDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.flussorendicontazione.DettaglioFlussoRendicontazioneDto;
import it.regioneveneto.mygov.payment.mypivot.domain.po.EnteTipoDovuto;
import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTipoDovutoTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.UtenteTO;
import it.regioneveneto.mygov.payment.mypivot.service.EnteService;
import it.regioneveneto.mygov.payment.mypivot.service.EnteTipoDovutoService;
import it.regioneveneto.mygov.payment.mypivot.service.FlussoRendicontazioneService;
import it.regioneveneto.mygov.payment.mypivot.service.ImportExportRendicontazioneTesoreriaService;
import it.regioneveneto.mygov.payment.mypivot.service.OperatoreEnteTipoDovutoService;
import it.regioneveneto.mygov.payment.mypivot.service.SegnalazioneService;
import it.regioneveneto.mygov.payment.mypivot.service.UtenteService;
import it.regioneveneto.mygov.payment.mypivot.service.UtilityService;
import it.regioneveneto.mygov.payment.mypivot.service.exception.MyPivotServiceException;
import it.regioneveneto.mygov.payment.mypivot.service.utils.ModelMapperUtil;
import it.regioneveneto.mygov.payment.mypivot.utils.DettaglioUtils;
import it.regioneveneto.mygov.payment.mypivotsb.constant.ApiDescription;
import it.regioneveneto.mygov.payment.mypivotsb.constant.Response;
import it.regioneveneto.mygov.payment.mypivotsb.response.ResponseIF;
import it.regioneveneto.mygov.payment.mypivotsb.response.SingleDataResponse;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api(tags = { "Dettagli" })
public class DettaglioController extends BaseController {

	@Autowired
	private EnteService enteService;

	@Resource
	private Environment env;

	@Autowired
	private UtenteService utenteService;

	@Autowired
	private ImportExportRendicontazioneTesoreriaService importExportRendicontazioneTesoreriaService;

	@Autowired
	private SegnalazioneService segnalazioneService;

	@Autowired
	private FlussoRendicontazioneService flussoRendicontazioneService;

	@Autowired
	private OperatoreEnteTipoDovutoService operatoreEnteTipoDovutoService;

	@Autowired
	private EnteTipoDovutoService enteTipoDovutoService;

	@Autowired
	private ModelMapperUtil modelMapperUtil;

	@Value("${myPivot.enableGlobalProfile}")
	private Boolean enableGlobalProfile;

	@Autowired
	private UtilityService utilityService;
	
	public DettaglioController() {
		super();
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation(value = "create Segnalazione", notes = ApiDescription.DESCR_CREATE_SEGNALAZIONE, response = SingleDataResponse.class)
	@PostMapping(value = "/segnalazione")
	public ResponseIF aggiungiSegnalazione(
			//			@RequestParam(required = true) String codIpa,
			@RequestBody SegnalazioneDto segnalazioneDto
			) throws ParseException {		
		try {
			log.info("create Segnalazione");

			UtenteTO utenteTO = null;

			if(enableGlobalProfile) 
				utenteTO = utenteService.getUtenteByGlobalDefault();	

			EnteTO enteTO = enteService.getByCodIpaEnte(segnalazioneDto.getCodIpa());
			MessagesDto messagesDto = new MessagesDto();
			SingleDataResponse response = new SingleDataResponse();

			if (segnalazioneDto == null || StringUtils.isBlank(segnalazioneDto.getClassificazioneCompletezza())//
					|| StringUtils.isBlank(segnalazioneDto.getDeNota())//
					// && segnalazioneCommand.getFlgNascosto() != null//
					) {
				String errMsg = "Mancano dati obbligatori per aggiungere la segnalazione classificazioneCompletezza["
						+ segnalazioneDto.getClassificazioneCompletezza() + "], nota["
						+ segnalazioneDto.getDeNota() + "], nascosto[" + segnalazioneDto.getFlgNascosto() + "]";
				log.error(errMsg);
				messagesDto.getErrorMessages()
				.add(new DynamicMessageDto("mypivot.dettaglio.info.addsegnalazione.nocommand"));
			} else {
				String errorCode = segnalazioneDto.getClassificazioneCompletezza();
				if (!utilityService.verificaClassificazione(errorCode, enteTO.getFlgTesoreria(), enteTO.getFlgTesoreria(), "R") &&
						!utilityService.verificaClassificazione(errorCode, enteTO.getFlgTesoreria(), enteTO.getFlgTesoreria(), "A")) {
//					MessagesDto messagesDto = new MessagesDto();
					messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.classificazioneCompletezzaNonValida"));
					response.setMessages(messagesDto);
					return response;			
				}
				
				AggiornaSegnalazioneDto segnalazione = new AggiornaSegnalazioneDto();
				if (segnalazioneDto.getIdSegnalazione() != null) {
					log.debug("Aggionramento sengalazione id[" + segnalazioneDto.getIdSegnalazione() + "]");
					segnalazione.setIdSegnalazione(segnalazioneDto.getIdSegnalazione());
				} else {
					log.debug("Creazione di una nuova segnalazione");
					segnalazione.setEnte(enteTO);
					segnalazione.setClassificazioneCompletezza(segnalazioneDto.getClassificazioneCompletezza());
					String codIud = StringUtils.isNotBlank(segnalazioneDto.getCodIud())
							? segnalazioneDto.getCodIud() : null;
							String codIuv = StringUtils.isNotBlank(segnalazioneDto.getCodIuv())
									? segnalazioneDto.getCodIuv() : null;
									String codIuf = StringUtils.isNotBlank(segnalazioneDto.getCodIuf())
											? segnalazioneDto.getCodIuf() : null;

											segnalazione.setCodIud(codIud);
											segnalazione.setCodIuv(codIuv);
											segnalazione.setCodIuf(codIuf);
				}

				
				segnalazione.setDeNota(segnalazioneDto.getDeNota());
				segnalazione.setFlgNascosto(Boolean.TRUE.equals(segnalazioneDto.getFlgNascosto()));

				segnalazione.setUtente(utenteTO);														
				
				AggiornaSegnalazioneResultDto esitoAggiornamento = segnalazioneService
						.aggiornaSegnalazione(segnalazione);
				ESITO esito = esitoAggiornamento.getEsito();
				switch (esito) {
				case INSERITA:
				case AGGIORNATA:
					log.debug("Segnalazione aggiornata correttamente [" + esito + "]. [" + esitoAggiornamento.getMsg()
					+ "]");
					messagesDto.getInformationMessages()
					.add(new DynamicMessageDto("mypivot.dettaglio.info.addsegnalazione.ok"));
					break;
				case NO_NEED_TO_UPDATE:
					log.debug("Segnalazione gia aggiornata. [" + esitoAggiornamento.getMsg() + "]");
					messagesDto.getInformationMessages()
					.add(new DynamicMessageDto("mypivot.dettaglio.info.addsegnalazione.noneedtoupdate"));
					break;
				case ERROR:
					log.error("Errore nell'aggiornamento della segnalazione. [" + esitoAggiornamento.getMsg() + "]");

					messagesDto.getErrorMessages().add(new DynamicMessageDto(
							"mypivot.dettaglio.info.addsegnalazione.ko", esitoAggiornamento.getMsg()));
					break;
				default:
					log.error(
							"Esito non previsto [" + esito + "], all'utente viene comunque segnalato messaggio di ok");
					messagesDto.getInformationMessages()
					.add(new DynamicMessageDto("mypivot.dettaglio.info.addsegnalazione.ok"));
					break;
				}
				
//				segnalazione.setUtente(null);
//				response.setItems(segnalazione);
			}

			response.setMessages(messagesDto);
			return response;

		} catch (EntityNotFoundException entityNotFoundException) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, entityNotFoundException.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, String.format(Response.RESPONSE_KO, e.getMessage()));
		}

	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation(value = "update Segnalazione", notes = ApiDescription.DESCR_UPDATE_SEGNALAZIONE, response = SingleDataResponse.class)
	@PatchMapping(value = "/segnalazione")
	public ResponseIF modificaSegnalazione(

			@RequestBody SegnalazioneDto segnalazioneDto
			
			) throws ParseException {		
		try {
			log.info("update Segnalazione");

			UtenteTO utenteTO = null;
			
			if(enableGlobalProfile) 
				utenteTO = utenteService.getUtenteByGlobalDefault();	
			
			EnteTO enteTO = enteService.getByCodIpaEnte(segnalazioneDto.getCodIpa());
			MessagesDto messagesDto = new MessagesDto();
			SingleDataResponse response = new SingleDataResponse();

			if (segnalazioneDto == null || StringUtils.isBlank(segnalazioneDto.getClassificazioneCompletezza())//
					|| StringUtils.isBlank(segnalazioneDto.getDeNota())//
					// && segnalazioneCommand.getFlgNascosto() != null//
					) {
				String errMsg = "Mancano dati obbligatori per aggiungere la segnalazione classificazioneCompletezza["
						+ segnalazioneDto.getClassificazioneCompletezza() + "], nota["
						+ segnalazioneDto.getDeNota() + "], nascosto[" + segnalazioneDto.getFlgNascosto() + "]";
				log.error(errMsg);
				messagesDto.getErrorMessages()
				.add(new DynamicMessageDto("mypivot.dettaglio.info.addsegnalazione.nocommand"));
			} else {
				String errorCode = segnalazioneDto.getClassificazioneCompletezza();
				if (!utilityService.verificaClassificazione(errorCode, enteTO.getFlgTesoreria(), enteTO.getFlgTesoreria(), "R") &&
						!utilityService.verificaClassificazione(errorCode, enteTO.getFlgTesoreria(), enteTO.getFlgTesoreria(), "A")) {
//					MessagesDto messagesDto = new MessagesDto();
					messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.classificazioneCompletezzaNonValida"));
					response.setMessages(messagesDto);
					return response;			
				}
				
				AggiornaSegnalazioneDto segnalazione = new AggiornaSegnalazioneDto();
				if (segnalazioneDto.getIdSegnalazione() != null) {
					log.debug("Aggionramento sengalazione id[" + segnalazioneDto.getIdSegnalazione() + "]");
					segnalazione.setIdSegnalazione(segnalazioneDto.getIdSegnalazione());
				} else {
					log.debug("Creazione di una nuova segnalazione");
					segnalazione.setEnte(enteTO);
					segnalazione.setClassificazioneCompletezza(segnalazioneDto.getClassificazioneCompletezza());
					String codIud = StringUtils.isNotBlank(segnalazioneDto.getCodIud())
							? segnalazioneDto.getCodIud() : null;
							String codIuv = StringUtils.isNotBlank(segnalazioneDto.getCodIuv())
									? segnalazioneDto.getCodIuv() : null;
									String codIuf = StringUtils.isNotBlank(segnalazioneDto.getCodIuf())
											? segnalazioneDto.getCodIuf() : null;

											segnalazione.setCodIud(codIud);
											segnalazione.setCodIuv(codIuv);
											segnalazione.setCodIuf(codIuf);
				}

				segnalazione.setUtente(utenteTO);
				segnalazione.setDeNota(segnalazioneDto.getDeNota());
				segnalazione.setFlgNascosto(Boolean.TRUE.equals(segnalazioneDto.getFlgNascosto()));
				

				AggiornaSegnalazioneResultDto esitoAggiornamento = segnalazioneService
						.aggiornaSegnalazione(segnalazione);
				ESITO esito = esitoAggiornamento.getEsito();
				switch (esito) {
				case INSERITA:
				case AGGIORNATA:
					log.debug("Segnalazione aggiornata correttamente [" + esito + "]. [" + esitoAggiornamento.getMsg()
					+ "]");
					messagesDto.getInformationMessages()
					.add(new DynamicMessageDto("mypivot.dettaglio.info.addsegnalazione.ok"));
					break;
				case NO_NEED_TO_UPDATE:
					log.debug("Segnalazione gia aggiornata. [" + esitoAggiornamento.getMsg() + "]");
					messagesDto.getInformationMessages()
					.add(new DynamicMessageDto("mypivot.dettaglio.info.addsegnalazione.noneedtoupdate"));
					break;
				case ERROR:
					log.error("Errore nell'aggiornamento della segnalazione. [" + esitoAggiornamento.getMsg() + "]");

					messagesDto.getErrorMessages().add(new DynamicMessageDto(
							"mypivot.dettaglio.info.addsegnalazione.ko", esitoAggiornamento.getMsg()));
					break;
				default:
					log.error(
							"Esito non previsto [" + esito + "], all'utente viene comunque segnalato messaggio di ok");
					messagesDto.getInformationMessages()
					.add(new DynamicMessageDto("mypivot.dettaglio.info.addsegnalazione.ok"));
					break;
				}
				
//				segnalazione.setUtente(null);
//				response.setItems(segnalazione);
			}

			response.setMessages(messagesDto);
			return response;

		} catch (EntityNotFoundException entityNotFoundException) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, entityNotFoundException.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, String.format(Response.RESPONSE_KO, e.getMessage()));
		}

	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation(value = "get single Rendicontazione", notes = ApiDescription.DESCR_GET_SINGLE_RENDICONTAZIONE, response = SingleDataResponse.class)
	@GetMapping(value = "/riconciliazioni/visualizzaDettaglioRendicontazione")
	public ResponseIF visualizzaDettaglioRendicontazione(
			Pageable pageable,
			@RequestParam(required = true) String codIpa,
			@RequestParam(required = false) String classificazioneCompletezza,
			@RequestParam(required = false) String codiceIuf 

			) throws ParseException {		

		log.info("get single Rendicontazione");		
		SingleDataResponse response = new SingleDataResponse();
		
		EnteTO enteTO = enteService.getByCodIpaEnte(codIpa);			
		if (enteTO == null) {
			MessagesDto messagesDto = new MessagesDto();
			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.codIpaEnteNonValido"));
			response.setMessages(messagesDto);
			return response;
		}

		if (!utilityService.verificaClassificazione(classificazioneCompletezza, enteTO.getFlgTesoreria(), enteTO.getFlgTesoreria(), "R") &&
				!utilityService.verificaClassificazione(classificazioneCompletezza, enteTO.getFlgTesoreria(), enteTO.getFlgTesoreria(), "A")) {
			MessagesDto messagesDto = new MessagesDto();
			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.classificazioneCompletezzaNonValida"));
			response.setMessages(messagesDto);
			return response;			
		}

		UtenteTO utenteTO = null;
		
		if(enableGlobalProfile) 
			utenteTO = utenteService.getUtenteByGlobalDefault();	

		try {
			//			Integer page = pageable.getPageNumber();
			//			Integer pageSize = pageable.getPageSize();

			Collection<String> codTipoDovutoValidi = new HashSet<>();
			// TODO DA RECUPERARE DA PARAMETRO, SE "TUTTI" ALLORA RECUPERARE LA
			// LISTA
			List<EnteTipoDovutoTO> enteTipoDovutoTOList = operatoreEnteTipoDovutoService
					.getListaEnteTipoDovutoForOperatoreAndCodIpaEnteNoSession(utenteTO.getCodFedUserId(),
							enteTO.getCodIpaEnte());
			if (CollectionUtils.isNotEmpty(enteTipoDovutoTOList)) {
				for (EnteTipoDovutoTO enteTipoDovutoTO : enteTipoDovutoTOList) {
					codTipoDovutoValidi.add(enteTipoDovutoTO.getCodTipo());
				}
			}

			ImportExportRendicontazioneTesoreriaTO recordVistaTesoreria = importExportRendicontazioneTesoreriaService
					.getByEnteAndClassificazioneCompletezzaAndCodIuf(enteTO.getCodIpaEnte(), classificazioneCompletezza,
							codiceIuf);

			Sort sort = getFlussoRendicontazioneSort();

			PageDto<DettaglioFlussoRendicontazioneDto> dettaglioFlussoRendicontazionePaginato = flussoRendicontazioneService
					.findDettagliFlussoRendicontazione(enteTO.getCodIpaEnte(), codiceIuf, codTipoDovutoValidi, 
							pageable, sort);

			DettaglioDto dettaglioDto = DettaglioUtils.createDettaglioDtoRendicontazione(classificazioneCompletezza,
					codiceIuf, dettaglioFlussoRendicontazionePaginato, enteTO, recordVistaTesoreria);

			response.setItems(dettaglioDto);

		} catch (MyPivotServiceException e) {
			log.error("Impossibile visualizzare il dettaglio", e);
			MessagesDto errMsgDto = new MessagesDto();
			errMsgDto.getErrorMessages().add(new DynamicMessageDto("mypivot.dettaglio.info.error"));
			response.setMessages(errMsgDto);
		}

		return response;
	}

	private Sort getFlussoRendicontazioneSort() {
		List<Order> orders = new ArrayList<>();
		orders.add(new Order(Direction.DESC, "id.codDatiSingPagamIdentificativoUnivocoVersamento"));
		orders.add(new Order(Direction.ASC, "id.indiceDatiSingoloPagamento"));
		return Sort.by(orders);
	}




	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation(value = "get single Rendicontazione", notes = ApiDescription.DESCR_GET_SINGLE_RENDICONTAZIONE, response = SingleDataResponse.class)
	@GetMapping(value = "/riconciliazioni/visualizzaDettaglio")
	public ResponseIF visualizzaDettaglio(

			@RequestParam(required = true) String codIpa,						
			@RequestParam(required = false) String classificazioneCompletezza,
			@RequestParam(required = false) String codiceIuv,
			@RequestParam(required = false) String identificativoFlussoRendicontazione,
			@RequestParam(required = false) String codiceIud

			) throws ParseException {		

		log.info("get single Rendicontazione");		

		EnteTO enteTO = enteService.getByCodIpaEnte(codIpa);
		SingleDataResponse response = new SingleDataResponse();
	
		if (enteTO == null) {
			MessagesDto messagesDto = new MessagesDto();
			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.codIpaEnteNonValido"));
			response.setMessages(messagesDto);
			return response;
		}
		
		if (!utilityService.verificaClassificazione(classificazioneCompletezza, enteTO.getFlgTesoreria(), enteTO.getFlgTesoreria(), "R") &&
				!utilityService.verificaClassificazione(classificazioneCompletezza, enteTO.getFlgTesoreria(), enteTO.getFlgTesoreria(), "A")) {
			MessagesDto messagesDto = new MessagesDto();
			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.classificazioneCompletezzaNonValida"));
			response.setMessages(messagesDto);
			return response;			
		}
		
		try {

			ImportExportRendicontazioneTesoreriaTO recordVista = importExportRendicontazioneTesoreriaService
					.getByEnteAndClassificazioneCompletezzaAndCodIuvAndCodIufAndCodIud(enteTO.getCodIpaEnte(),
							classificazioneCompletezza, codiceIuv, identificativoFlussoRendicontazione, codiceIud);

			List<EnteTipoDovuto> listaEnteTipoDovuto = enteTipoDovutoService.getByCodIpaEnte(enteTO.getCodIpaEnte());
			List<EnteTipoDovutoTO> listaEnteTipoDovutoTO = mapListaEnteTipoDovutoInListaEnteTipoDovutoTO(
					listaEnteTipoDovuto);

			DettaglioDto dettaglioDto = DettaglioUtils.createDettaglioDtoFromImportExportRendicontazioneTesoreriaTO(
					recordVista, enteTO, listaEnteTipoDovutoTO);

			response.setItems(dettaglioDto);
			if(dettaglioDto == null) {
				MessagesDto infoMsgDto = new MessagesDto();
				infoMsgDto.getInformationMessages().add(new DynamicMessageDto("mypivot.dettaglio.info.NoDataFound"));
				response.setMessages(infoMsgDto);
			}
			
		} catch (MyPivotServiceException e) {
			log.error("Impossibile visualizzare il dettaglio", e);
			MessagesDto errMsgDto = new MessagesDto();
			errMsgDto.getErrorMessages().add(new DynamicMessageDto("mypivot.dettaglio.info.error"));
			response.setMessages(errMsgDto);
		}

		return response;

	}


	@SuppressWarnings("deprecation")
	private List<EnteTipoDovutoTO> mapListaEnteTipoDovutoInListaEnteTipoDovutoTO(
			List<EnteTipoDovuto> listaEnteTipoDovuto) {
		Assert.notEmpty(listaEnteTipoDovuto);
		List<EnteTipoDovutoTO> listaEnteTipoDovutoTO = new ArrayList<EnteTipoDovutoTO>();
		for (EnteTipoDovuto etd : listaEnteTipoDovuto) {
			EnteTipoDovutoTO etdTO = modelMapperUtil.map(etd, EnteTipoDovutoTO.class);
			listaEnteTipoDovutoTO.add(etdTO);
		}
		return listaEnteTipoDovutoTO;
	}
}