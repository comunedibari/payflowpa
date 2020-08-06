package it.regioneveneto.mygov.payment.mypivotsb.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import it.regioneveneto.mygov.payment.mypivot.controller.command.AccertamentiVisualizzaCommand;
import it.regioneveneto.mygov.payment.mypivot.controller.utils.AccertamentoUtils;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.AccertamentoDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.AccertamentoNuovoDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.DynamicMessageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.MessagesDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.PageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.po.EnteTipoDovuto;
import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTipoDovutoTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.UtenteTO;
import it.regioneveneto.mygov.payment.mypivot.service.AccertamentoDettaglioService;
import it.regioneveneto.mygov.payment.mypivot.service.AccertamentoService;
import it.regioneveneto.mygov.payment.mypivot.service.EnteService;
import it.regioneveneto.mygov.payment.mypivot.service.EnteTipoDovutoService;
import it.regioneveneto.mygov.payment.mypivot.service.OperatoreEnteTipoDovutoService;
import it.regioneveneto.mygov.payment.mypivot.service.UtenteService;
import it.regioneveneto.mygov.payment.mypivot.utils.Constants;
import it.regioneveneto.mygov.payment.mypivotsb.constant.ApiDescription;
import it.regioneveneto.mygov.payment.mypivotsb.constant.Response;
import it.regioneveneto.mygov.payment.mypivotsb.controller.validator.AccertamentoValidator;
import it.regioneveneto.mygov.payment.mypivotsb.response.PageDtoResponse;
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
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api(tags = { "Accertamenti" })
public class AccertamentoController extends BaseController {

	//	@Autowired
	//	private OperatoreEnteTipoDovutoService operatoreEnteTipoDovutoService;

	//	@Autowired
	//	private EnteTipoDovutoService enteTipoDovutoService;

	@Autowired
	private AccertamentoService accertamentoService;

	@Autowired
	private AccertamentoDettaglioService accertamentoDettaglioService;

	@Autowired
	private AccertamentoValidator validator;

	//	@Autowired
	//	private EnteService enteService;

	@Autowired
	private OperatoreEnteTipoDovutoService operatoreEnteTipoDovutoService;

	@Autowired
	private EnteTipoDovutoService enteTipoDovutoService;
	
	@Autowired
	private UtenteService utenteService;

	@Autowired
	private EnteService enteService;
	
	@Resource
	private Environment env;

	@Value("${myPivot.enableGlobalProfile}")
	private Boolean enableGlobalProfile;
	
	public AccertamentoController() {
		super();
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation(value = "create Accertamento", notes = ApiDescription.DESCR_CREATE_ACCERTAMENTO, response = SingleDataResponse.class)
	@PostMapping(value = "/accertamenti")
	public ResponseIF nuovoAccertamento(

			@RequestBody AccertamentoNuovoDto accertamentoNuovoDto
			) throws ParseException {


		UtenteTO utenteTO = null;
		
		if(enableGlobalProfile) 
			utenteTO = utenteService.getUtenteByGlobalDefault();
		
		SingleDataResponse  response = new SingleDataResponse();
		
		
		EnteTO enteTO = enteService.getByCodIpaEnte(accertamentoNuovoDto.getCodIpaEnte());			
		if (enteTO == null) {			
			MessagesDto messagesDto = new MessagesDto();
			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.codIpaEnteNonValido"));
			response.setMessages(messagesDto);
			return response;
		}
		
		String c_td = accertamentoNuovoDto.getCodTipoDovuto();
		EnteTipoDovuto item = enteTipoDovutoService.getByCodIpaEnteAndCodTipoDovuto(enteTO.getCodIpaEnte(), c_td);
		if (item == null) {			
			MessagesDto messagesDto = new MessagesDto();
			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.codTipoDovutoNonValido"));
			response.setMessages(messagesDto);
			return response;
		}

		
		log.debug("NUOVO :: ACCERTAMENTO :: POST :: Utente[codFedUserId: " + utenteTO.getCodFedUserId() + "] :: START");

		try{


			//			/**
			//			 * Recupero la lista dei tipi dovuto dell'ente per cui l'utente è un operatore attivo. 
			//			 * Se la lista è vuota, mostro un messaggio di errore perchè vuol dire che l'utente non può ne consultare ne creare accertamenti.
			//			 */
			//			List<EnteTipoDovutoTO> activeOperatoreEnteTd = operatoreEnteTipoDovutoService.getListaEnteTipoDovutoForOperatoreAndCodIpaEnte(SecurityContext.getUtente().getCodFedUserId(), SecurityContext.getEnte().getCodIpaEnte());
			//			
			//			if (activeOperatoreEnteTd == null || activeOperatoreEnteTd.isEmpty()) {
			//				logger.warn("NUOVO :: ACCERTAMENTO :: GET :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: NON risulta OPERATORE per nessun tipo dovuto.");
			//				/* 
			//				 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
			//				 */
			//				return UtilitiesCtrl.getViewErrorMessage("mypivot.messages.error.nessunTipoDovutoAssegnato");
			//			}

			Errors result = new BeanPropertyBindingResult(accertamentoNuovoDto, "accertamentoNuovoDto");
			MessagesDto messagesDto = new MessagesDto();

			/**
			 * Validazione form
			 */
			validator.validate(accertamentoNuovoDto, result);

			/**
			 * Check validation errors
			 */
			if (result.hasErrors()) {
				messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.anagrafica.dettaglioAnagrafica.modAnagrafica.nocommand"));					
				response.setMessages(messagesDto);
				return response;
			}

			/**
			 * Salvataggio accertamento
			 */
			Long id = accertamentoService.saveAccertamento(
					accertamentoNuovoDto.getDeNomeAccertamento(),  	/* Descrizione accertamento */
					accertamentoNuovoDto.getCodTipoDovuto(), 		/* Codice tipo dovuto */
					accertamentoNuovoDto.getCodIpaEnte(), 		/* Codice ipa ente */
					utenteTO.getId());			/* Identificativo utente */
			AccertamentoDto accertamentoDto = accertamentoService.findById(id);
			accertamentoDto.setUtente(null);
			log.debug("NUOVO :: ACCERTAMENTO :: POST :: Utente[codFedUserId: " + utenteTO.getCodFedUserId() + "] :: END");

			return new SingleDataResponse(accertamentoDto);			

		}catch(Exception e){
			log.error("NUOVO :: ACCERTAMENTO :: POST :: Utente[codFedUserId: " +utenteTO.getCodFedUserId() + "] :: ERRORE ", e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, String.format(Response.RESPONSE_KO, e.getMessage()));
		}
	}




	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation(value = "set Stato Accertamento", notes = ApiDescription.DESCR_SET_STATUS_ACCERTAMENTO, response = SingleDataResponse.class)
	@PostMapping(value = "/accertamenti/setStato")
	public ResponseIF setStato(

			@RequestParam(value = "accertamentoID", required = true) Long accertamentoID, 
			@RequestParam(value = "codStato", required = true) String codStato

			) throws ParseException {

		UtenteTO utenteTO = null;
		
		if(enableGlobalProfile) 
			utenteTO = utenteService.getUtenteByGlobalDefault();
		
		log.debug("SET " + codStato + " :: ACCERTAMENTO :: GET :: Fields[accertamentoID: " + accertamentoID + ", codFedUserId: " + utenteTO.getCodFedUserId() + "] :: START");

		SingleDataResponse  response = new SingleDataResponse();
		MessagesDto messagesDto = new MessagesDto();

		try{

			//			/**
			//			 * Recupero la lista dei tipi dovuto dell'ente per cui l'utente è un operatore attivo. 
			//			 * Se la lista è vuota, mostro un messaggio di errore perchè vuol dire che l'utente non può ne consultare ne creare accertamenti.
			//			 */
			//			List<String> activeOperatoreEnteTd = operatoreEnteTipoDovutoService.getListaCodTipoDovutoForOperatoreAndCodIpaEnte(SecurityContext.getUtente().getCodFedUserId(), SecurityContext.getEnte().getCodIpaEnte());
			//			
			//			if (activeOperatoreEnteTd == null || activeOperatoreEnteTd.isEmpty()) {
			//				logger.warn("SET " + codStato + " :: ACCERTAMENTO :: GET :: Fields[accertamentoID: " + accertamentoID + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: NON risulta OPERATORE per nessun tipo dovuto.");
			//				/* 
			//				 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
			//				 */
			//				return UtilitiesCtrl.getViewErrorMessage("mypivot.messages.error.nessunTipoDovutoAssegnato");
			//			}

			/**
			 * Get anagrafica accertamento 
			 */
			AccertamentoDto accertamentoDto = accertamentoService.findById(accertamentoID);

			//			/**
			//			 * Controllo che l'utente sia operatore per il tipo dovuto dell'accertamento a cui si vuole cambiare stato.
			//			 */
			//			if (!activeOperatoreEnteTd.contains(accertamentoDto.getEnteTipoDovuto().getCodTipo())) {
			//				logger.warn("SET " + codStato + " :: ACCERTAMENTO :: GET :: Fields[accertamentoID: " + accertamentoID + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: NON risulta OPERATORE per il tipo dovuto dell'accertamento.");
			//				/* 
			//				 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
			//				 */
			//				return UtilitiesCtrl.getViewErrorMessage("mypivot.messages.error.notAuthorized");
			//			}

			/**
			 * Controllo che l'accertamento sia ancora in stato: INSERITO e con PAGAMENTI per poterlo chiudere, se non lo è 
			 * mostro un messaggio di errore
			 */
			if (codStato.equals(Constants.COD_TIPO_STATO_ACCERTAMENTO_CHIUSO)) {

				if(!accertamentoDto.getStato().getCodStato().equals(Constants.COD_TIPO_STATO_ACCERTAMENTO_INSERITO)) {
					log.warn("SET " + codStato + " :: ACCERTAMENTO :: GET :: Fields[accertamentoID: " + accertamentoID + ", codFedUserId: " + utenteTO.getCodFedUserId() + "] :: L'accertamento è in stato[" + accertamentoDto.getStato().getCodStato() + "] perciò non può essere chiuso.");
					messagesDto.getInformationMessages().add(new DynamicMessageDto("mypivot.accertamenti.errore.notAuthorizedClosed"));
					response.setMessages(messagesDto);
					return response;
				}

				/*
				 * Controllo che l'accertamento abbia dei pagamenti(RT) in accertamento per poterlo chiudere
				 */
				if(accertamentoDettaglioService.countRowByAccertamentoId(accertamentoID) == 0){
					/* */
					log.warn("SET " + codStato + " :: ACCERTAMENTO :: GET :: Fields[accertamentoID: " + accertamentoID + ", codFedUserId: " + utenteTO.getCodFedUserId() + "] :: L'accertamento non presenta pagamenti associati perciò non può essere chiuso.");
					messagesDto.getInformationMessages().add(new DynamicMessageDto("notAuthorizedClosedEmptyAcc"));
					response.setMessages(messagesDto);
					return response;
				}
			}else
				/**
				 * Controllo che l'accertamento deve essere ancora in stato: INSERITO per poterlo annullare, se non lo è mostro un messaggio di 
				 * errore
				 */
				if (codStato.equals(Constants.COD_TIPO_STATO_ACCERTAMENTO_ANNULLATO)) {

					if(!accertamentoDto.getStato().getCodStato().equals(Constants.COD_TIPO_STATO_ACCERTAMENTO_INSERITO)) {
						log.warn("SET " + codStato + " :: ACCERTAMENTO :: GET :: Fields[accertamentoID: " + accertamentoID + ", codFedUserId: " + utenteTO.getCodFedUserId() + "] :: L'accertamento è in stato[" + accertamentoDto.getStato().getCodStato() + "] perciò non può essere annullato.");
						messagesDto.getInformationMessages().add(new DynamicMessageDto("mypivot.accertamenti.errore.notAuthorizedCancel"));
						response.setMessages(messagesDto);
						return response;
					}
				}

			try{
				/**
				 * Update stato
				 */
				accertamentoService.setStatoAndSave(accertamentoID, codStato, utenteTO.getCodFedUserId());

			}catch(Exception e){
				log.error("SET " + codStato + " :: ACCERTAMENTO :: Fields[accertamentoID: " + accertamentoID + ", codFedUserId: " + utenteTO.getCodFedUserId() + "] :: ERRORE ", e);
				messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.accertamenti.errore.setStatus"));
				response.setMessages(messagesDto);
				return response;
			}


		}catch(Exception e){
			log.error("SET " + codStato + " :: ACCERTAMENTO :: GET :: Fields[accertamentoID: " + accertamentoID + ", codFedUserId: " + utenteTO.getCodFedUserId() + "] :: ERRORR ", e);
			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.accertamenti.errore.internal"));
			response.setMessages(messagesDto);
			return response;
		}

		log.debug("SET " + codStato + " :: ACCERTAMENTO :: GET :: Fields[accertamentoID: " + accertamentoID + ", codFedUserId: " + utenteTO.getCodFedUserId() + "] :: END");
		messagesDto.getInformationMessages().add(new DynamicMessageDto("mypivot.accertamenti.info.setStato.ok"));
		response.setMessages(messagesDto);
		return response;
	}



	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation(value = "get all Accertamenti", notes = ApiDescription.DESCR_GET_ALL_ACCERTAMENTO, response = PageDtoResponse.class)
	@GetMapping(value = "/accertamenti/ricercaAccertamenti")	
	public ResponseIF ricercaAccertamenti(
			Pageable pageable,
			@RequestParam(required = true) String codIpa,

			@RequestParam(required = false) String iuv,
			@RequestParam(required = false) String dataUltimoAggDa,
			@RequestParam(required = false) String dataUltimoAggA,
			@RequestParam(required = false) Boolean dataUltimoAggCheck,
			@RequestParam(required = false) String nomeAccertamento,
			@RequestParam(required = false) String codStato,
			@RequestParam(required = false) String codTipoDovuto

			) {

		EnteTO enteTO = enteService.getByCodIpaEnte(codIpa);			
		if (enteTO == null) {
			SingleDataResponse<?> response = new SingleDataResponse();
			MessagesDto messagesDto = new MessagesDto();
			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.codIpaEnteNonValido"));
			response.setMessages(messagesDto);
			return response;
		}
		
		UtenteTO utenteTO = null;
		
		if(enableGlobalProfile) 
			utenteTO = utenteService.getUtenteByGlobalDefault();	

		log.debug("RICERCA :: ACCERTAMENTI :: POST :: Fields[codFedUserId: " + utenteTO.getCodFedUserId() + "] :: START");

		PageDtoResponse<?> response = new PageDtoResponse();
		MessagesDto messagesDto = new MessagesDto();

		try{

			/**
			 * Recupero la lista dei tipi dovuto dell'ente per cui l'utente è un operatore attivo. 
			 * Se la lista è vuota, mostro un messaggio di errore perchè vuol dire che l'utente non può ne consultare ne creare accertamenti.
			 */
			List<EnteTipoDovutoTO> activeOperatoreEnteTdAsObj = operatoreEnteTipoDovutoService.getListaEnteTipoDovutoForOperatoreAndCodIpaEnteNoSession(utenteTO.getCodFedUserId(), codIpa);
			//
			//			if (activeOperatoreEnteTdAsObj == null || activeOperatoreEnteTdAsObj.isEmpty()) {
			//				logger.warn("RICERCA :: ACCERTAMENTI :: Utente[codFedUserId: " + utenteTO.getCodFedUserId() + "] :: NON risulta OPERATORE per nessun tipo dovuto.");
			//				/* 
			//				 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
			//				 */
			//				return UtilitiesCtrl.getViewErrorMessage("mypivot.messages.error.nessunTipoDovutoAssegnato");
			//			}

			/**
			 * Ciclo la lista appena recuperata dei tipi dovuto e aggiungo ad una lista temporanea solamente il codice, mi servirà
			 * come filtro della query. 
			 */
			List<String> activeOperatoreEnteTdAsString = new ArrayList<String>();
			/* */
			for (EnteTipoDovutoTO item : activeOperatoreEnteTdAsObj) activeOperatoreEnteTdAsString.add(item.getCodTipo());

			/**
			 * Determino in base ai ruoli dell'utente loggato quali accertamenti può visualizzare.
			 * Se l'utente autenticato ha ruolo:
			 *   - ROLE_ACC: visualizza solamente i propri accertamenti
			 *   - ROLE_ADMIN: visualizza accertamenti effettuati dagli utenti operatori dell'ente e quelli caricati
			 *   				da BatchLoadFlussiExport tramite l' utente WS dell' ente (C_IPA-WS_USER)
			 *   
			 *   I RUOLI NON VENGONO DIFFERENZIATI IN QUESTI CONTESTI
			 */
			List<Long> utenteIDs = new ArrayList<Long>();

//			if(SecurityContext.getRoles().contains(Constants.RUOLO_AMMINISTRATORE)){
				/*
				 * Caso in cui l'utente visualizza i propri accertamenti e quelli degli altri utenti operatori dell'ente, 
				 */
				List<UtenteTO> entities = utenteService.findByCodIpaEnte(codIpa);

				/*
				 * e dell' utente WS.
				 */
				UtenteTO WsUser = utenteService.getUtenteWSByCodIpaEnte(codIpa);
				if (!entities.contains(WsUser) && WsUser != null) entities.add(WsUser);

				/*
				 * Ciclo gli utenti e aggiungo alla lista solamente l'identificativo. 
				 */
				for (UtenteTO item : entities) utenteIDs.add(item.getId());

//				logger.debug("RICERCA :: ACCERTAMENTI :: Utente[codFedUserId: " + utenteTO.getCodFedUserId() + "] :: L'utente autenticato è un ADMIN, visualizza gli accertamenti degli utenti con ID[" + StringUtils.collectionToCommaDelimitedString(utenteIDs) + "]");

//			}else{
//				logger.debug("RICERCA :: ACCERTAMENTI :: Utente[codFedUserId: " + utenteTO.getCodFedUserId() + "] :: L'utente autenticato visualizza solo i propri accertamenti");
//				/*
//				 * Caso in cui l'utente visualizza solamente i propri accertamenti, alla lista aggiungo solo il suo identificativo utente. 
//				 */
//				utenteIDs.add(utenteTO.getId());
//			}

			/**
			 * Inizializzo il bean per la ricerca
			 */
				AccertamentiVisualizzaCommand visualizzaAccertamentiCommand = new AccertamentiVisualizzaCommand();
				visualizzaAccertamentiCommand.setCodiceIuv(iuv);
				visualizzaAccertamentiCommand.setCodStato(codStato);
				visualizzaAccertamentiCommand.setCodTipoDovuto(codTipoDovuto);
				visualizzaAccertamentiCommand.setDataUltimoAggA(dataUltimoAggA);
				visualizzaAccertamentiCommand.setDataUltimoAggCheck(dataUltimoAggCheck);
				visualizzaAccertamentiCommand.setDataUltimoAggDa(dataUltimoAggDa);
				visualizzaAccertamentiCommand.setNomeAccertamento(nomeAccertamento);
				visualizzaAccertamentiCommand.setPage(pageable.getPageNumber());
				visualizzaAccertamentiCommand.setPageSize(pageable.getPageSize());
				
			AccertamentoUtils.initializeFilterAccertamento(visualizzaAccertamentiCommand);

			/* Determino se è attiva la ricerca per data modifica */
			Boolean check = visualizzaAccertamentiCommand.getDataUltimoAggCheck() != null ? visualizzaAccertamentiCommand.getDataUltimoAggCheck() : false;

			/**
			 * Eseguo la query di ricerca
			 */
			PageDto<AccertamentoDto> result = accertamentoService.findByFilter(
					codIpa, utenteIDs, activeOperatoreEnteTdAsString, 
					visualizzaAccertamentiCommand.getCodTipoDovuto(), 
					visualizzaAccertamentiCommand.getCodStato(), visualizzaAccertamentiCommand.getNomeAccertamento(), 
					(check ? visualizzaAccertamentiCommand.getDataUltimoAggDa() : null), 
					(check ? visualizzaAccertamentiCommand.getDataUltimoAggA() : null), 
					visualizzaAccertamentiCommand.getCodiceIuv(), 
					true, visualizzaAccertamentiCommand.getPage(), visualizzaAccertamentiCommand.getPageSize());
			
			if(enableGlobalProfile) {
				List<AccertamentoDto> lista_elem = result.getList();
				for(AccertamentoDto item : lista_elem){
					item.setUtente(null);
				}
				result.setList(lista_elem);
			}
			
			response.setPage(result);

		}catch(Exception e){
			log.error("RICERCA :: ACCERTAMENTI :: Utente[codFedUserId: " + utenteTO.getCodFedUserId() + "] :: ERRORE", e);
			messagesDto.getInformationMessages().add(new DynamicMessageDto("mypivot.accertamenti.errore.ricerca"));
			response.setMessages(messagesDto);
			return response;
		}
		log.debug("RICERCA :: ACCERTAMENTI :: POST :: Fields[codFedUserId: " + utenteTO.getCodFedUserId() + "] :: END");
		return response;

	}

}