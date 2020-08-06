package it.regioneveneto.mygov.payment.mypivotsb.controller;

import java.text.ParseException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import it.regioneveneto.mygov.payment.mypivot.controller.command.AccertamentoVisualizzaPagamentiCommand;
import it.regioneveneto.mygov.payment.mypivot.controller.command.AccertamentoVisualizzaPagamentiCommand.OPERATION;
import it.regioneveneto.mygov.payment.mypivot.controller.utils.AccertamentoUtils;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.AccertamentoDettaglioDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.AccertamentoDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.AccertamentoUfficioCapitoloDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.AccertamentoVisualizzaCapitoliPagamentiDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.AccertamentoVisualizzaPagamentiDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.DynamicMessageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.MessagesDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.PageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.UtenteTO;
import it.regioneveneto.mygov.payment.mypivot.service.AccertamentoDettaglioService;
import it.regioneveneto.mygov.payment.mypivot.service.AccertamentoService;
import it.regioneveneto.mygov.payment.mypivot.service.EnteService;
import it.regioneveneto.mygov.payment.mypivot.service.UtenteService;
import it.regioneveneto.mygov.payment.mypivotsb.constant.ApiDescription;
import it.regioneveneto.mygov.payment.mypivotsb.response.ResponseIF;
import it.regioneveneto.mygov.payment.mypivotsb.response.SingleDataResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller per la gestione del processo di :
 * 	1. Creazione di una nuova anagrafica accertamento
 *  2. Aggiornamento stato accertamento
 */

@Slf4j
@RestController
@RequestMapping(value = "/api/accertamenti", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api(tags = { "Accertamenti" })
public class DettaglioAccertamentoController  extends BaseController {

	//	@Autowired
	//	private OperatoreEnteTipoDovutoService operatoreEnteTipoDovutoService;

	//	@Autowired
	//	private EnteTipoDovutoService enteTipoDovutoService;
	
	@Autowired
	private EnteService enteService;
	
	@Autowired
	private AccertamentoService accertamentoService;

	@Autowired
	private AccertamentoDettaglioService accertamentoDettaglioService;

//	@Autowired
//	private OperatoreEnteTipoDovutoService operatoreEnteTipoDovutoService;

	@Autowired
	private UtenteService utenteService;

	@Value("${myPivot.enableGlobalProfile}")
	private Boolean enableGlobalProfile;

	@Resource
	private Environment env;

	public DettaglioAccertamentoController() {
		super();
	}



	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation(value = "get single Accertamento", notes = ApiDescription.DESCR_GET_SINGLE_ACCERTAMENTO, response = SingleDataResponse.class)
	@GetMapping(value = "/dettaglioAccertamento")
	public ResponseIF dettaglioAccertamento(

			@RequestParam(required = true) String codIpa,
			@RequestParam(value = "accertamentoID", required = true) Long accertamentoID,

			@RequestParam(required = false) String codiceIdentificativoUnivocoPagatore, 
			@RequestParam(required = false) String codiceIud, String codiceIuv, 
			@RequestParam(required = false) Boolean dataUltimoAggiornamentoCheck, 
			@RequestParam(required = false) String dataUltimoAggiornamentoDal, 
			@RequestParam(required = false) String dataUltimoAggiornamentoAl, 
			@RequestParam(required = false) Boolean dataEsitoSingoloPagamentoCheck, 
			@RequestParam(required = false) String dataEsitoSingoloPagamentoDal, 
			@RequestParam(required = false) String dataEsitoSingoloPagamentoAl,
			
			Pageable pageable
			
			) throws ParseException {

		UtenteTO utenteTO = null;
				
		EnteTO enteTO = enteService.getByCodIpaEnte(codIpa);			
		if (enteTO == null) {
			SingleDataResponse<?> response = new SingleDataResponse();
			MessagesDto messagesDto = new MessagesDto();
			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.codIpaEnteNonValido"));
			response.setMessages(messagesDto);
			return response;
		}
		
		if(enableGlobalProfile) 
			utenteTO = utenteService.getUtenteByGlobalDefault();	

		SingleDataResponse  response = new SingleDataResponse();
		MessagesDto messagesDto = new MessagesDto();

		log.debug("DETTAGLIO :: ACCERTAMENTO :: GET :: Fields[accertamentoID: " + accertamentoID + ", codFedUserId: " + utenteTO.getCodFedUserId() + "] :: START");



		try{

			/**
			 * Instance Bean form di ricerca
			 */
			AccertamentoVisualizzaPagamentiCommand pagamentiAccertamentoCommand = new AccertamentoVisualizzaPagamentiCommand();
			/**
			 * Indentificativo accertamento
			 */
			pagamentiAccertamentoCommand.setAccertamentoId(Long.toString(accertamentoID));
			/**
			 * Prevalorizzo il bean di ricerca della form con gli ultimi valori dei filtri sottomessi e salvati in sessione.
			 */
			
			
			String pg = Integer.toString(pageable.getPageNumber());
			String pgSize = Integer.toString(pageable.getPageSize());

			AccertamentoUtils.setSessionFilterPagamentiToBean(utenteTO.getCodFedUserId(), codIpa, null,	
					codiceIdentificativoUnivocoPagatore, codiceIud, codiceIuv, dataUltimoAggiornamentoCheck, dataUltimoAggiornamentoDal, dataUltimoAggiornamentoAl, dataEsitoSingoloPagamentoCheck, dataEsitoSingoloPagamentoDal, dataEsitoSingoloPagamentoAl,
					pg, pgSize, pagamentiAccertamentoCommand);

			/**
			 * Avvio la ricerca
			 */
			return dettaglio(pagamentiAccertamentoCommand, utenteTO, codIpa);

//			log.debug("DETTAGLIO :: ACCERTAMENTO :: GET :: Fields[accertamentoID: " + accertamentoID + ", codFedUserId: " + utenteTO.getCodFedUserId() + "] :: END");


		}catch(Exception e){
			log.error("DETTAGLIO :: ACCERTAMENTO :: GET :: Fields[accertamentoID: " + accertamentoID + ", codFedUserId: " + utenteTO.getCodFedUserId() + "] :: ERRORE ", e);
			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.accertamenti.errore.internal"));				
			response.setMessages(messagesDto);
			return response;
		}


	}

	
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseIF dettaglio(AccertamentoVisualizzaPagamentiCommand pagamentiAccertamentoCommand, UtenteTO utenteTO, String codIpa) {
		
		log.debug("DETTAGLIO :: ACCERTAMENTO :: Fields[accertamentoID: " + pagamentiAccertamentoCommand.getAccertamentoId() + ", codFedUserId: " + utenteTO.getCodFedUserId() + "] :: START");
		SingleDataResponse  response = new SingleDataResponse();
		MessagesDto messagesDto = new MessagesDto();
		AccertamentoVisualizzaPagamentiDto accertamentoVisualizzaPagamentiDto = new AccertamentoVisualizzaPagamentiDto();
		AccertamentoDto accertamentoDto;
		
		try{

//			/**
//			 * Recupero la lista dei tipi dovuto dell'ente per cui l'utente è un operatore attivo. 
//			 * Se la lista è vuota, mostro un messaggio di errore perchè vuol dire che l'utente non può ne consultare ne creare accertamenti.
//			 */
//			List<String> activeOperatoreEnteTd = operatoreEnteTipoDovutoService.getListaCodTipoDovutoForOperatoreAndCodIpaEnte(SecurityContext.getUtente().getCodFedUserId(), SecurityContext.getEnte().getCodIpaEnte());
//			
//			if (activeOperatoreEnteTd == null || activeOperatoreEnteTd.isEmpty()) {
//				logger.warn("DETTAGLIO :: ACCERTAMENTO :: Fields[accertamentoID: " + pagamentiAccertamentoCommand.getAccertamentoId() + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: NON risulta OPERATORE per nessun tipo dovuto.");
//				/* 
//				 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
//				 */
//				return UtilitiesCtrl.getViewErrorMessage("mypivot.messages.error.nessunTipoDovutoAssegnato");
//			}
			
			/**
			 * Get anagrafica accertamento 
			 */
			accertamentoDto = accertamentoService.findById(Long.parseLong(pagamentiAccertamentoCommand.getAccertamentoId()));
			
			
			/**
			 * Inizializzo il bean per la ricerca
			 */
			pagamentiAccertamentoCommand = AccertamentoUtils.initializeFilterPagamenti(pagamentiAccertamentoCommand, accertamentoDto.getEnteTipoDovuto().getCodTipo(), accertamentoDto.getId());
			
			/**
			 * Instance return bean 
			 */
			PageDto<AccertamentoDettaglioDto> result = new PageDto<AccertamentoDettaglioDto>();
			
			try{
				Boolean checkEsito = pagamentiAccertamentoCommand.getData_esito_singolo_pagamento_check() != null ? pagamentiAccertamentoCommand.getData_esito_singolo_pagamento_check() : false;
				Boolean checkAgg = pagamentiAccertamentoCommand.getData_ultimo_aggiornamento_check() != null ? pagamentiAccertamentoCommand.getData_ultimo_aggiornamento_check() : false;
				EnteTO enteTO = enteService.getByCodIpaEnte(codIpa);
				/**
				 * Eseguo la query di ricerca
				 */
				result = accertamentoDettaglioService.findPagamentiInAccertamentoByFilter(
								 Long.parseLong(accertamentoDto.getId()), enteTO.getId(), 
								 pagamentiAccertamentoCommand.getCod_tipo_dovuto(), 
								 pagamentiAccertamentoCommand.getCodice_iud(), pagamentiAccertamentoCommand.getCodice_iuv(), 
								 pagamentiAccertamentoCommand.getCodice_identificativo_univoco_pagatore(), 
								 checkEsito ? pagamentiAccertamentoCommand.getData_esito_singolo_pagamento_da() : null, 
								 checkEsito ? pagamentiAccertamentoCommand.getData_esito_singolo_pagamento_a() : null,
								 checkAgg ? pagamentiAccertamentoCommand.getData_ultimo_aggiornamento_da() : null, 
								 checkAgg ? pagamentiAccertamentoCommand.getData_ultimo_aggiornamento_a() : null, 
								 true, pagamentiAccertamentoCommand.getPage(), pagamentiAccertamentoCommand.getPageSize());
				
			}catch(Exception e){
				log.error("DETTAGLIO :: ACCERTAMENTO :: Fields[accertamentoID: " + pagamentiAccertamentoCommand.getAccertamentoId() + ", codFedUserId: " + utenteTO.getCodFedUserId() + "]", e);
				/* */
				messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.accertamenti.errore.pagamenti.ricerca"));
				/* */					
				response.setMessages(messagesDto);
				return response;
			}
			
			if(enableGlobalProfile)
				accertamentoDto.setUtente(null);
			
			accertamentoVisualizzaPagamentiDto.setAccertamentoDto(accertamentoDto);
			accertamentoVisualizzaPagamentiDto.setResultList(result);	


		}catch(Exception e){
			log.error("DETTAGLIO :: ACCERTAMENTO :: Fields[accertamentoID: " + pagamentiAccertamentoCommand.getAccertamentoId() + ", codFedUserId: " + utenteTO.getCodFedUserId() + "] :: ERRORE ", e);
			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.accertamenti.errore.internal"));
			response.setMessages(messagesDto);
			return response;
		}
		
		log.debug("DETTAGLIO :: ACCERTAMENTO :: Fields[accertamentoID: " + pagamentiAccertamentoCommand.getAccertamentoId() + ", codFedUserId: " + utenteTO.getCodFedUserId() + "] :: END");
		return new SingleDataResponse(accertamentoVisualizzaPagamentiDto);
	}
	

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation(value = "get dettaglio Capiotli RT Accertamento", notes = ApiDescription.DESCR_GET_CAPITOLI_RT_ACCERTAMENTO, response = SingleDataResponse.class)
	@GetMapping(value = "/capitoliRTAccertamento")
	public ResponseIF capitoliRTAccertamento(

			@RequestParam(required = true) String codIpa,
			@RequestParam(value = "accertamentoID", required = true) Long accertamentoID,
			@RequestParam(value = "iuv", required = true) String iuv,
			@RequestParam(required = true) String codTipoDovuto
			
			) throws ParseException {

		UtenteTO utenteTO = null;
		
		if(enableGlobalProfile) 
			utenteTO = utenteService.getUtenteByGlobalDefault();	

		SingleDataResponse  response = new SingleDataResponse();
		MessagesDto messagesDto = new MessagesDto();
		EnteTO enteTO = enteService.getByCodIpaEnte(codIpa);
			
		if (enteTO == null) {
			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.codIpaEnteNonValido"));
			response.setMessages(messagesDto);
			return response;
		}
		
		log.debug("DETTAGLIO :: ACCERTAMENTO :: GET :: Fields[accertamentoID: " + accertamentoID + ", codFedUserId: " + utenteTO.getCodFedUserId() + "] :: START");


				try{
					// cerco il versamento mediante codice iuv e prendo il primo elemento della lista
					
					AccertamentoDettaglioDto selected = accertamentoDettaglioService.
							findPagamentiInAccertamentoByFilter(accertamentoID, enteTO.getId(), codTipoDovuto, null, iuv, null, null, null, null, null, false, 0, 1).getList().get(0);
					
					/*
					 * Recupera l'elenco dei capitoli associati alla RT.
					 */
					AccertamentoVisualizzaCapitoliPagamentiDto accertamentoVisualizzaCapitoliPagamentiDto = 
							new AccertamentoVisualizzaCapitoliPagamentiDto();
					
					List<AccertamentoUfficioCapitoloDto> capitoli = accertamentoDettaglioService.findListCapitoliByRT(
																			accertamentoID, 
																			codIpa, 
																			enteTO.getId(), 
																			codTipoDovuto,																			
																			selected.getFlussoExportDTO().getCodiceIud(), 
																			iuv,
																			null);
					/*
					 * Controllo di aver recuperato la lista dei capitoli associati, altrimenti rimando alla pagina di errore.
					 */
					if(capitoli != null && !capitoli.isEmpty()){


						accertamentoVisualizzaCapitoliPagamentiDto.setResultList(capitoli);
						accertamentoVisualizzaCapitoliPagamentiDto.setAccertamentoDettaglioDto(selected);
						response.setItems(accertamentoVisualizzaCapitoliPagamentiDto);
						return response;
						
					}else{
						log.error("Dettaglio Capitoli associati alla RT :: Fields[operation: " + OPERATION.DETAIL.name() + ", RT:" + iuv + ", accertamentoID: " + accertamentoID + ", codFedUserId: " + utenteTO.getCodFedUserId() + "] :: ERRORE :: La lista capitoli associata alla RT e' vuota.");
						messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.accertamenti.errore.capitoliAssociati"));				
						response.setMessages(messagesDto);
						return response;
					}
				}catch(Exception e){
					log.error("Dettaglio Capitoli associati alla RT :: ACCERTAMENTO :: POST :: Fields[operation: " + OPERATION.DETAIL.name() + ", RT:" + iuv + ", accertamentoID: " + accertamentoID + ", codFedUserId: " + utenteTO.getCodFedUserId() + "] :: ERRORE ", e);
					messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.accertamenti.errore.capitoliAssociati"));				
					response.setMessages(messagesDto);
					return response;
				}
	}


	
}