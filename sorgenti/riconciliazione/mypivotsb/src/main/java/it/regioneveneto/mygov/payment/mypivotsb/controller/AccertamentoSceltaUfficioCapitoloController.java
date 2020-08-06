package it.regioneveneto.mygov.payment.mypivotsb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.AccertamentoDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.AccertamentoSceltaUfficioCapitoloDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.AccertamentoUfficioCapitoloDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.DynamicMessageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.MessagesDto;
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
public class AccertamentoSceltaUfficioCapitoloController extends BaseController {



	@Autowired
	private AccertamentoService accertamentoService;

	@Autowired
	private UtenteService utenteService;

	@Autowired
	private EnteService enteService;
	
	@Autowired
	private AccertamentoDettaglioService accertamentoDttService;

	@Value("${myPivot.enableGlobalProfile}")
	private Boolean enableGlobalProfile;
	
	public AccertamentoSceltaUfficioCapitoloController() {
		super();
	}



	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation(value = "add Pagamenti", notes = ApiDescription.DESCR_GET_ALL_RT_TO_ADD_ACCERTAMENTO, response = PageDtoResponse.class)
	@PostMapping(value = "/addPagamenti")
	public ResponseIF addPagamenti(

			@RequestBody AccertamentoSceltaUfficioCapitoloDto accertamentoSceltaUfficioCapitoloDto

			) {

		UtenteTO utenteTO = null;
		
		EnteTO ente = enteService.getByCodIpaEnte(accertamentoSceltaUfficioCapitoloDto.getCodIpa());
		if (ente == null) {
			SingleDataResponse<?> response = new SingleDataResponse();
			MessagesDto messagesDto = new MessagesDto();
			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.codIpaEnteNonValido"));
			response.setMessages(messagesDto);
			return response;
		}
		
		if(enableGlobalProfile) 
			utenteTO = utenteService.getUtenteByGlobalDefault();	
		
		log.debug("SCELTA UFFICIO-CAPITOLO :: ACCERTAMENTO :: Fields[accertamentoID: " + accertamentoSceltaUfficioCapitoloDto.getAccertamentoId() + ", codFedUserId: " + utenteTO.getCodFedUserId() + "] :: START");

		/* */
		PageDtoResponse<?> response = new PageDtoResponse();
		MessagesDto messagesDto = new MessagesDto();

		try{

			AccertamentoDto accertamentoDto = accertamentoService.findById(Long.parseLong(accertamentoSceltaUfficioCapitoloDto.getAccertamentoId()));

			//			/**
			//			 * Controllo che l'utente sia operatore per il tipo dovuto che si sta integrando con i pagamenti.
			//			 */
			//			if (!activeOperatoreEnteTd.contains(accertamentoDto.getEnteTipoDovuto().getCodTipo())) {
			//				logger.warn("SCELTA UFFICIO-CAPITOLO :: ACCERTAMENTO :: Fields[accertamentoID: " + sceltaUfficioCapitoloCommand.getAccertamentoId() + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: NON risulta OPERATORE per il tipo dovuto dell'accertamento.");
			//				/* 
			//				 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
			//				 */
			//				return UtilitiesCtrl.getViewErrorMessage("mypivot.messages.error.notAuthorized");
			//			}

			/**
			 * Controllo che l'accertamento sia in stato "INSERITO" per poter essere modificato.
			 */
			if (!accertamentoDto.getStato().getCodStato().equals(Constants.COD_TIPO_STATO_ACCERTAMENTO_INSERITO)) {
				log.warn("SCELTA UFFICIO-CAPITOLO :: ACCERTAMENTO :: Fields[accertamentoID: " + accertamentoSceltaUfficioCapitoloDto.getAccertamentoId() + ", codFedUserId: " +utenteTO.getCodFedUserId() + "] :: L'accertamento è in stato[" + accertamentoDto.getStato().getCodStato() + "] perciò non può essere modificato.");
				messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.accertamenti.errore.notAuthorizedUpdate"));
				response.setMessages(messagesDto);
				return response;
			}

			if(accertamentoSceltaUfficioCapitoloDto.getUfficiTOList() == null || accertamentoSceltaUfficioCapitoloDto.getUfficiTOList().isEmpty()) {
				messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.accertamenti.errore.internal"));
				response.setMessages(messagesDto);
				return response;
			}

			try {
				if(accertamentoSceltaUfficioCapitoloDto.getFlussiExportDTOList().size() > 1){

					/**
					 * Controllo che la lista uffici contenga un solo elemento.
					 */
					if (accertamentoSceltaUfficioCapitoloDto.getUfficiTOList().size() > 1) {
						log.warn("SCELTA UFFICIO-CAPITOLO :: ACCERTAMENTO :: Fields[accertamentoID: " + accertamentoSceltaUfficioCapitoloDto.getAccertamentoId() + ", codFedUserId: " +utenteTO.getCodFedUserId() + "] :: Per l'inserimento multiplo di Pagamenti non è consentita la ripartizione in più capitoli.");
						messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.accertamenti.errore.notAuthorizedUpdate"));
						response.setMessages(messagesDto);
						return response;
					}


					/* */
					String codAccertamento = StringUtils.hasText(accertamentoSceltaUfficioCapitoloDto.getCodAccertamento()) ? 
							accertamentoSceltaUfficioCapitoloDto.getCodAccertamento() : 
								Constants.CODICE_NOT_AVAILABLE;
							/*
							 */
							AccertamentoUfficioCapitoloDto ufficiTO = new AccertamentoUfficioCapitoloDto();
							ufficiTO.setCodUfficio(accertamentoSceltaUfficioCapitoloDto.getUfficiTOList().get(0).getCodUfficio());
							ufficiTO.setCodCapitolo(accertamentoSceltaUfficioCapitoloDto.getUfficiTOList().get(0).getCodCapitolo());
							ufficiTO.setCodAccertamento(codAccertamento);

							/**
							 * Questo scenario prevede un unico capitolo/accertamento.
							 * Sono inserite in "mygov_accertamento dettaglio" tante righe quanti sono i pagamenti selezionati e gli importi 
							 * riportati in tabella corrispondono agli importi dei singoli pagamenti.
							 */
							accertamentoDttService.addPagamenti( 
									Long.parseLong(accertamentoDto.getId()),  						/* Identificativo dell'accertamento */
									accertamentoDto.getEnteTipoDovuto().getEnte().getCodIpaEnte(), 	/* Codice ipa dell'ente */
									accertamentoSceltaUfficioCapitoloDto.getFlussiExportDTOList(), 	/* Lista RT da aggiungere all'accertamento */
									ufficiTO, 											  			/* Dati del capitolo/accertamento */
									utenteTO.getId());												/* Identificativo dell'utente in modifica */
				}else{

					/**
					 * Questo scenario prevede l'acquisizione di un singolo pagamento permettendo quindi lo spacchettamento su più 
					 * capitoli/accertamenti.
					 * Sono inserite in "mygov_accertamento dettaglio" tante righe quanti sono gli importi inseriti dall'utente.
					 */
					accertamentoDttService.addPagamenti( 
							Long.parseLong(accertamentoDto.getId()), 								/* Identificativo dell'accertamento */
							accertamentoDto.getEnteTipoDovuto().getEnte().getCodIpaEnte(),  		/* Codice ipa dell'ente */
							accertamentoSceltaUfficioCapitoloDto.getFlussiExportDTOList().get(0),  	/* Lista RT da aggiungere all'accertamento */
							accertamentoSceltaUfficioCapitoloDto.getUfficiTOList(), 				/* Lista capitoli/accertamenti */
							utenteTO.getId()); 									/* Identificativo dell'utente in modifica */
				}


			}catch(Exception e){
				log.error("SCELTA UFFICIO-CAPITOLO :: ACCERTAMENTO :: Fields[accertamentoID: " + accertamentoSceltaUfficioCapitoloDto.getAccertamentoId() + ", codFedUserId: " + utenteTO.getCodFedUserId() + "] :: ERRORE ", e);
				messagesDto.getInformationMessages().add(new DynamicMessageDto("mypivot.accertamenti.errore.internal"));
				response.setMessages(messagesDto);
				return response;
			}
		}catch(Exception e){
			log.error("SCELTA UFFICIO-CAPITOLO :: ACCERTAMENTO :: Fields[accertamentoID: " + accertamentoSceltaUfficioCapitoloDto.getAccertamentoId() + ", codFedUserId: " + utenteTO.getCodFedUserId() + "] :: ERRORE ", e);
			messagesDto.getInformationMessages().add(new DynamicMessageDto("mypivot.accertamenti.errore.internal"));
			response.setMessages(messagesDto);
			return response;
		}

		//		logger.debug("SCELTA UFFICIO-CAPITOLO :: ACCERTAMENTO :: Fields[accertamentoID: " + sceltaUfficioCapitoloCommand.getAccertamentoId() + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: ENTE");
		//
		messagesDto.getSuccessMessages().add(new DynamicMessageDto("mypivot.accertamenti.addPagamenti.OK"));
		response.setMessages(messagesDto);
		return response;

	}



}