package it.regioneveneto.mygov.payment.mypivotsb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import it.regioneveneto.mygov.payment.mypivot.controller.command.AccertamentoVisualizzaPagamentiCommand;
import it.regioneveneto.mygov.payment.mypivot.controller.utils.AccertamentoUtils;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.AccertamentoDeletePagamentiDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.AccertamentoDettaglioDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.AccertamentoDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.AccertamentoFlussoExportDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.DynamicMessageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.MessagesDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.PageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.UtenteTO;
import it.regioneveneto.mygov.payment.mypivot.service.AccertamentoDettaglioService;
import it.regioneveneto.mygov.payment.mypivot.service.AccertamentoService;
import it.regioneveneto.mygov.payment.mypivot.service.EnteService;
import it.regioneveneto.mygov.payment.mypivot.service.UtenteService;
import it.regioneveneto.mygov.payment.mypivot.utils.Constants;
import it.regioneveneto.mygov.payment.mypivotsb.constant.ApiDescription;
import it.regioneveneto.mygov.payment.mypivotsb.response.PageDtoResponse;
import it.regioneveneto.mygov.payment.mypivotsb.response.ResponseIF;
import it.regioneveneto.mygov.payment.mypivotsb.response.SingleDataResponse;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping(value = "/api/accertamenti", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api(tags = { "Accertamenti" })
public class PagamentiAccertamentoController extends BaseController {


	@Autowired
	private AccertamentoService accertamentoService;

	@Autowired
	private AccertamentoDettaglioService accertamentoDettaglioService;

	@Autowired
	private UtenteService utenteService;

	@Autowired
	private EnteService enteService;

	@Value("${myPivot.enableGlobalProfile}")
	private Boolean enableGlobalProfile;

	
	public PagamentiAccertamentoController() {
		super();
	}



	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation(value = "get all Pagamenti to add to accertamento", notes = ApiDescription.DESCR_GET_ALL_RT_TO_ADD_ACCERTAMENTO, response = PageDtoResponse.class)
	@GetMapping(value = "/addPagamentiGet")	
	public ResponseIF addPagamentiGet(
			@RequestParam(value = "accertamentoID") String accertamentoID, 
			@RequestParam(value = "codIpa") String codIpa, 
			Pageable pageable,			
			@RequestParam(required = false) String codTipoDovuto,
			@RequestParam(required = false) String codiceIdentificativoUnivocoPagatore,
			@RequestParam(required = false) String codiceIud,
			@RequestParam(required = false) String codiceIuv,
			@RequestParam(required = false) Boolean dataUltimoAggiornamentoCheck,
			@RequestParam(required = false) String dataUltimoAggiornamentoDal,
			@RequestParam(required = false) String dataUltimoAggiornamentoAl,
			@RequestParam(required = false) Boolean dataEsitoSingoloPagamentoCheck,
			@RequestParam(required = false) String dataEsitoSingoloPagamentoDal,
			@RequestParam(required = false) String dataEsitoSingoloPagamentoAl


			) {

		EnteTO ente = enteService.getByCodIpaEnte(codIpa);
		if (ente == null) {
			SingleDataResponse<?> response = new SingleDataResponse();
			MessagesDto messagesDto = new MessagesDto();
			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.codIpaEnteNonValido"));
			response.setMessages(messagesDto);
			return response;
		}
		
		UtenteTO utenteTO = null;
		
		if(enableGlobalProfile) 
			utenteTO = utenteService.getUtenteByGlobalDefault();	
		
		log.debug("ADD PAGAMENTI :: ACCERTAMENTO :: GET :: Fields[accertamentoID: " + accertamentoID + ", codFedUserId: " + utenteTO.getCodFedUserId() + "] :: START");

		/* */
		PageDtoResponse<?> response = new PageDtoResponse();
		MessagesDto messagesDto = new MessagesDto();

		try{

			/**
			 * Instance Bean form di ricerca
			 */
			AccertamentoVisualizzaPagamentiCommand pagamentiAccertamentoCommand = new AccertamentoVisualizzaPagamentiCommand();

			/**
			 * Indentificativo accertamento
			 */
			pagamentiAccertamentoCommand.setAccertamentoId(accertamentoID);

			/**
			 * Prevalorizzo il bean di ricerca
			 */

			Integer page = pageable.getPageNumber();
			int pageToGet = 1;
			if (page > 0) {
				pageToGet = page;
			}
			
			String pg = Integer.toString(pageToGet);
			String pgSize = Integer.toString(pageable.getPageSize());

			AccertamentoUtils.setSessionFilterPagamentiToBean(utenteTO.getCodFedUserId(), codIpa, codTipoDovuto, codiceIdentificativoUnivocoPagatore, codiceIud, codiceIuv, 
					dataUltimoAggiornamentoCheck, dataUltimoAggiornamentoDal, dataUltimoAggiornamentoAl, dataEsitoSingoloPagamentoCheck, 
					dataEsitoSingoloPagamentoDal, dataEsitoSingoloPagamentoAl, pg, pgSize, pagamentiAccertamentoCommand);


			/**
			 * Avvio la ricerca
			 */
			//			PageDto<AccertamentoDettaglioDto> result = addPagamenti(pagamentiAccertamentoCommand);


			/**
			 * Get anagrafica accertamento 
			 */
			AccertamentoDto accertamentoDto = accertamentoService.findById(Long.parseLong(pagamentiAccertamentoCommand.getAccertamentoId()));

			/**
			 * Controllo che l'accertamento sia in stato "INSERITO" per poter essere modificato.
			 */
			if (!accertamentoDto.getStato().getCodStato().equals(Constants.COD_TIPO_STATO_ACCERTAMENTO_INSERITO)) {
				log.warn("ADD PAGAMENTI :: ACCERTAMENTO :: Fields[accertamentoID: " + pagamentiAccertamentoCommand.getAccertamentoId() + ", codFedUserId: " + utenteTO.getCodFedUserId() + "] :: L'accertamento è in stato[" + accertamentoDto.getStato().getCodStato() + "] perciò non può essere modificato.");
				/* 
				 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
				 */
				//				return UtilitiesCtrl.getViewErrorMessage("mypivot.accertamenti.errore.notAuthorizedUpdate", new Object[]{accertamentoDto.getDeNomeAccertamento(), accertamentoDto.getStato().getCodStato()});
				messagesDto.getInformationMessages().add(new DynamicMessageDto("mypivot.accertamenti.errore.notAuthorizedUpdate"));
				response.setMessages(messagesDto);
				return response;
			}

			/**
			 * Inizializzo il bean per la ricerca
			 */
			pagamentiAccertamentoCommand = AccertamentoUtils.initializeFilterPagamenti(pagamentiAccertamentoCommand, accertamentoDto.getEnteTipoDovuto().getCodTipo(), accertamentoDto.getId());

			/**
			 * Instance return bean 
			 */
			PageDto<AccertamentoDettaglioDto> result =  new PageDto<AccertamentoDettaglioDto>();

			try{
				Boolean checkEsito = pagamentiAccertamentoCommand.getData_esito_singolo_pagamento_check() != null ? pagamentiAccertamentoCommand.getData_esito_singolo_pagamento_check() : false;
				Boolean checkAgg = pagamentiAccertamentoCommand.getData_ultimo_aggiornamento_check() != null ? pagamentiAccertamentoCommand.getData_ultimo_aggiornamento_check() : false;

				/**
				 * Eseguo la query di ricerca
				 */
				result = accertamentoDettaglioService.findPagamentiAccertabiliByFilter(
						ente.getId(), pagamentiAccertamentoCommand.getCod_tipo_dovuto(), 
						pagamentiAccertamentoCommand.getCodice_iud(), 
						pagamentiAccertamentoCommand.getCodice_iuv(), 
						pagamentiAccertamentoCommand.getCodice_identificativo_univoco_pagatore(), 
						checkEsito ? pagamentiAccertamentoCommand.getData_esito_singolo_pagamento_da() : null, 
								checkEsito ? pagamentiAccertamentoCommand.getData_esito_singolo_pagamento_a() : null,
										checkAgg ? pagamentiAccertamentoCommand.getData_ultimo_aggiornamento_da() : null, 
												checkAgg ? pagamentiAccertamentoCommand.getData_ultimo_aggiornamento_a() : null, 
														true, pagamentiAccertamentoCommand.getPage(), pagamentiAccertamentoCommand.getPageSize());

			}catch(Exception e){
				log.error("ADD PAGAMENTI :: ACCERTAMENTO :: Fields[accertamentoID: " + pagamentiAccertamentoCommand.getAccertamentoId() + ", codFedUserId: " + utenteTO.getCodFedUserId() + "] :: ERRORE ", e);
				/* */
				messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.accertamenti.errore.addPagamenti.ricerca"));
				/* */
				response.setPage(result);
				return response;
			}


			log.debug("ADD PAGAMENTI :: ACCERTAMENTO :: GET :: Fields[accertamentoID: " + accertamentoID + ", codFedUserId: " + utenteTO.getCodFedUserId() + "] :: END");
			response.setPage(result);
			return response;


		}catch(Exception e){
			log.error("ADD PAGAMENTI :: ACCERTAMENTO :: GET :: Fields[accertamentoID: " + accertamentoID + ", codFedUserId: " + utenteTO.getCodFedUserId() + "] :: ERRORE ", e);
			messagesDto.getInformationMessages().add(new DynamicMessageDto("mypivot.accertamenti.errore.internal"));
			response.setMessages(messagesDto);
			return response;
		}

	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation(value = "get all Pagamenti that can be removed from accertamento", notes = ApiDescription.DESCR_GET_ALL_RT_TO_DEL_ACCERTAMENTO, response = PageDtoResponse.class)
	@GetMapping(value = "/deletePagamentiGet")	
	public ResponseIF deletePagamentiGet(
			@RequestParam(value = "accertamentoID") String accertamentoID, 
			@RequestParam(value = "codIpa") String codIpa, 
			Pageable pageable,			
			@RequestParam(required = false) String codTipoDovuto,
			@RequestParam(required = false) String codiceIdentificativoUnivocoPagatore,
			@RequestParam(required = false) String codiceIud,
			@RequestParam(required = false) String codiceIuv,
			@RequestParam(required = false) Boolean dataUltimoAggiornamentoCheck,
			@RequestParam(required = false) String dataUltimoAggiornamentoDal,
			@RequestParam(required = false) String dataUltimoAggiornamentoAl,
			@RequestParam(required = false) Boolean dataEsitoSingoloPagamentoCheck,
			@RequestParam(required = false) String dataEsitoSingoloPagamentoDal,
			@RequestParam(required = false) String dataEsitoSingoloPagamentoAl

			) {

		EnteTO ente = enteService.getByCodIpaEnte(codIpa);
		if (ente == null) {
			SingleDataResponse<?> response = new SingleDataResponse();
			MessagesDto messagesDto = new MessagesDto();
			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.codIpaEnteNonValido"));
			response.setMessages(messagesDto);
			return response;
		}
		
		UtenteTO utenteTO = null;
		
		if(enableGlobalProfile) 
			utenteTO = utenteService.getUtenteByGlobalDefault();	
		
		log.debug("DEL PAGAMENTI :: ACCERTAMENTO :: GET :: Fields[accertamentoID: " + accertamentoID + ", codFedUserId: " + utenteTO.getCodFedUserId() + "] :: START");

		/* */
		PageDtoResponse<?> response = new PageDtoResponse();
		MessagesDto messagesDto = new MessagesDto();

		try{

			/**
			 * Instance Bean form di ricerca
			 */
			AccertamentoVisualizzaPagamentiCommand pagamentiAccertamentoCommand = new AccertamentoVisualizzaPagamentiCommand();

			/**
			 * Indentificativo accertamento
			 */
			pagamentiAccertamentoCommand.setAccertamentoId(accertamentoID);

			/**
			 * Prevalorizzo il bean di ricerca
			 */

			Integer page = pageable.getPageNumber();
			int pageToGet = 1;
			if (page > 0) {
				pageToGet = page;
			}
			
			String pg = Integer.toString(pageToGet);
			String pgSize = Integer.toString(pageable.getPageSize());

			AccertamentoUtils.setSessionFilterPagamentiToBean(utenteTO.getCodFedUserId(), codIpa, codTipoDovuto, codiceIdentificativoUnivocoPagatore, codiceIud, codiceIuv, 
					dataUltimoAggiornamentoCheck, dataUltimoAggiornamentoDal, dataUltimoAggiornamentoAl, dataEsitoSingoloPagamentoCheck, 
					dataEsitoSingoloPagamentoDal, dataEsitoSingoloPagamentoAl, pg, pgSize, pagamentiAccertamentoCommand);


			/**
			 * Get anagrafica accertamento 
			 */
			AccertamentoDto accertamentoDto = accertamentoService.findById(Long.parseLong(pagamentiAccertamentoCommand.getAccertamentoId()));

			/**
			 * Controllo che l'accertamento sia in stato "INSERITO" per poter essere modificato.
			 */
			if (!accertamentoDto.getStato().getCodStato().equals(Constants.COD_TIPO_STATO_ACCERTAMENTO_INSERITO)) {
				log.warn("DEL PAGAMENTI :: ACCERTAMENTO :: Fields[accertamentoID: " + pagamentiAccertamentoCommand.getAccertamentoId() + ", codFedUserId: " + utenteTO.getCodFedUserId() + "] :: L'accertamento è in stato[" + accertamentoDto.getStato().getCodStato() + "] perciò non può essere modificato.");
				messagesDto.getInformationMessages().add(new DynamicMessageDto("mypivot.accertamenti.errore.notAuthorizedUpdate"));
				response.setMessages(messagesDto);
				return response;
			}

			/**
			 * Inizializzo il bean per la ricerca
			 */
			pagamentiAccertamentoCommand = AccertamentoUtils.initializeFilterPagamenti(pagamentiAccertamentoCommand, accertamentoDto.getEnteTipoDovuto().getCodTipo(), accertamentoDto.getId());


			/**
			 * Instance return bean 
			 */
			PageDto<AccertamentoDettaglioDto> result =  new PageDto<AccertamentoDettaglioDto>();

			try{
				Boolean checkEsito = pagamentiAccertamentoCommand.getData_esito_singolo_pagamento_check() != null ? pagamentiAccertamentoCommand.getData_esito_singolo_pagamento_check() : false;
				Boolean checkAgg = pagamentiAccertamentoCommand.getData_ultimo_aggiornamento_check() != null ? pagamentiAccertamentoCommand.getData_ultimo_aggiornamento_check() : false;

				/**
				 * Eseguo la query di ricerca
				 */
				result = accertamentoDettaglioService.findPagamentiInAccertamentoByFilter(
						Long.parseLong(accertamentoDto.getId()), ente.getId(), 
						pagamentiAccertamentoCommand.getCod_tipo_dovuto(), 
						pagamentiAccertamentoCommand.getCodice_iud(), 
						pagamentiAccertamentoCommand.getCodice_iuv(), 
						pagamentiAccertamentoCommand.getCodice_identificativo_univoco_pagatore(), 
						checkEsito ? pagamentiAccertamentoCommand.getData_esito_singolo_pagamento_da() : null, 
								checkEsito ? pagamentiAccertamentoCommand.getData_esito_singolo_pagamento_a() : null,
										checkAgg ? pagamentiAccertamentoCommand.getData_ultimo_aggiornamento_da() : null, 
												checkAgg ? pagamentiAccertamentoCommand.getData_ultimo_aggiornamento_a() : null, 
														true, pagamentiAccertamentoCommand.getPage(), pagamentiAccertamentoCommand.getPageSize());


			}catch(Exception e){
				log.error("DEL PAGAMENTI :: ACCERTAMENTO :: Fields[accertamentoID: " + pagamentiAccertamentoCommand.getAccertamentoId() + ", codFedUserId: " + utenteTO.getCodFedUserId() + "] :: ERRORE ", e);
				/* */
				messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.accertamenti.errore.addPagamenti.ricerca"));
				/* */
				response.setPage(result);
				return response;
			}


			log.debug("DEL PAGAMENTI :: ACCERTAMENTO :: GET :: Fields[accertamentoID: " + accertamentoID + ", codFedUserId: " + utenteTO.getCodFedUserId() + "] :: END");
			response.setPage(result);
			return response;


		}catch(Exception e){
			log.error("DEL PAGAMENTI :: ACCERTAMENTO :: GET :: Fields[accertamentoID: " + accertamentoID + ", codFedUserId: " + utenteTO.getCodFedUserId() + "] :: ERRORE ", e);
			messagesDto.getInformationMessages().add(new DynamicMessageDto("mypivot.accertamenti.errore.internal"));
			response.setMessages(messagesDto);
			return response;
		}

	}


	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation(value = "remove Pagamenti from accertamento", notes = ApiDescription.DESCR_DEL_RT_FROM_ACCERTAMENTO, response = PageDtoResponse.class)
	@PostMapping(value = "/deletePagamenti")	
	public ResponseIF deletePagamenti(
			
			@RequestBody AccertamentoDeletePagamentiDto accertamentoDeletePagamentiDto

			) {

		UtenteTO utenteTO = null;
		
		EnteTO ente = enteService.getByCodIpaEnte(accertamentoDeletePagamentiDto.getCodIpa());
		if (ente == null) {
			SingleDataResponse<?> response = new SingleDataResponse();
			MessagesDto messagesDto = new MessagesDto();
			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.codIpaEnteNonValido"));
			response.setMessages(messagesDto);
			return response;
		}
		
		if(enableGlobalProfile) 
			utenteTO = utenteService.getUtenteByGlobalDefault();	
		
		log.debug("DEL PAGAMENTI :: ACCERTAMENTO :: GET :: Fields[accertamentoID: " + accertamentoDeletePagamentiDto.getAccertamentoId() + ", codFedUserId: " + utenteTO.getCodFedUserId() + "] :: START");

		/* */
		PageDtoResponse<?> response = new PageDtoResponse();
		MessagesDto messagesDto = new MessagesDto();
		
		List<AccertamentoFlussoExportDto> selected = accertamentoDeletePagamentiDto.getFlussiExportDTOList();
		
		try{

			if(!selected.isEmpty()){
				try{
					/**
					 * Cancello l'associazione tra i pagamenti e l'accertamento dato i dettagli della relazione.
					 */
					accertamentoDettaglioService.deletePagamenti(
								Long.parseLong(accertamentoDeletePagamentiDto.getAccertamentoId()),
								utenteTO.getId(), 
								accertamentoDeletePagamentiDto.getCodIpa(), selected);
					
					
				}catch(Exception e){
					log.error("DELETE PAGAMENTI :: ACCERTAMENTO :: POST :: Fields[accertamentoID: " + accertamentoDeletePagamentiDto.getAccertamentoId() + ", codFedUserId: " + utenteTO.getCodFedUserId() + "] :: ERRORE ", e);
					messagesDto.getInformationMessages().add(new DynamicMessageDto("mypivot.accertamenti.errore.delPagamenti"));
					response.setMessages(messagesDto);
					return response;
				}
			}


			log.debug("DEL PAGAMENTI :: ACCERTAMENTO :: GET :: Fields[accertamentoID: " + accertamentoDeletePagamentiDto.getAccertamentoId() + ", codFedUserId: " + utenteTO.getCodFedUserId() + "] :: END");
			messagesDto.getSuccessMessages().add(new DynamicMessageDto("mypivot.accertamenti.delPagamenti.OK"));
			response.setMessages(messagesDto);
			return response;



		}catch(Exception e){
			log.error("DEL PAGAMENTI :: ACCERTAMENTO :: GET :: Fields[accertamentoID: " + accertamentoDeletePagamentiDto.getAccertamentoId() + ", codFedUserId: " + utenteTO.getCodFedUserId() + "] :: ERRORE ", e);
			messagesDto.getInformationMessages().add(new DynamicMessageDto("mypivot.accertamenti.errore.internal"));
			response.setMessages(messagesDto);
			return response;
		}

	}

}