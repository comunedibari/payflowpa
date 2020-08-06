package it.regioneveneto.mygov.payment.mypivotsb.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import javax.persistence.EntityNotFoundException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import it.regioneveneto.mygov.payment.mypivot.controller.command.AnagraficaUfficioCapitoloAccertamentoCommand;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.AggiornaAnagraficaUfficioCapitoloAccertamentoResultDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.AggiornaAnagraficaUfficioCapitoloAccertamentoResultDto.ESITO;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.AnagraficaUfficioCapitoloAccertamentoDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.DynamicMessageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.MessagesDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.PageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.ResponseUploadAnagUffCapAccDto;
import it.regioneveneto.mygov.payment.mypivot.domain.po.AnagraficaUfficioCapitoloAccertamento;
import it.regioneveneto.mygov.payment.mypivot.domain.po.Ente;
import it.regioneveneto.mygov.payment.mypivot.domain.po.EnteTipoDovuto;
import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.UtenteTO;
import it.regioneveneto.mygov.payment.mypivot.service.AnagraficaUfficioCapitoloAccertamentoService;
import it.regioneveneto.mygov.payment.mypivot.service.EnteService;
import it.regioneveneto.mygov.payment.mypivot.service.EnteTipoDovutoService;
import it.regioneveneto.mygov.payment.mypivot.service.UtenteService;
import it.regioneveneto.mygov.payment.mypivotsb.constant.ApiDescription;
import it.regioneveneto.mygov.payment.mypivotsb.constant.Response;
import it.regioneveneto.mygov.payment.mypivotsb.controller.validator.AnagraficaUfficioCapitoloAccertamentoValidator;
import it.regioneveneto.mygov.payment.mypivotsb.response.IoResponse;
import it.regioneveneto.mygov.payment.mypivotsb.response.PageDtoResponse;
import it.regioneveneto.mygov.payment.mypivotsb.response.ResponseIF;
import it.regioneveneto.mygov.payment.mypivotsb.response.SingleDataResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/api/accertamentiAnagrafiche", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api(tags = { "Flussi" })
public class VisualizzaAnagraficaUfficioCapitoloAccertamentoController extends BaseController {


	@Autowired
	private EnteService enteService;

	//	@Autowired
	//	private OperatoreEnteTipoDovutoService operatoreEnteTipoDovutoService;

	@Autowired
	private AnagraficaUfficioCapitoloAccertamentoService anagraficaUfficioCapitoloAccertamentoService;

	@Autowired
	private AnagraficaUfficioCapitoloAccertamentoValidator validator;

	@Autowired
	private UtenteService utenteService;

	@Autowired
	private EnteTipoDovutoService enteTipoDovutoService;

	@Value("${myPivot.enableGlobalProfile}")
	private Boolean enableGlobalProfile;


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation(value = "create Anagrafica", notes = ApiDescription.DESCR_CREATE_ENTE_TIPO_DOVUTO, response = SingleDataResponse.class)
	@PostMapping(value = "/nuovaAnagraficaCapitolo")
	public ResponseIF nuovaAnagraficaCapitolo(

			//			@RequestParam(required = true) String codIpa,
			@RequestBody AnagraficaUfficioCapitoloAccertamentoDto anagraficaUfficioCapitoloAccertamentoDto
			) throws ParseException {

		EnteTO enteTO = null;
		try {
			enteTO = enteService.getByCodIpaEnte(anagraficaUfficioCapitoloAccertamentoDto.getEnte().getCodIpaEnte());			
			if (enteTO == null) {
				SingleDataResponse<?> response = new SingleDataResponse();
				MessagesDto messagesDto = new MessagesDto();
				messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.codIpaEnteNonValido"));
				response.setMessages(messagesDto);
				return response;
			}
		} catch (Exception e) {
			SingleDataResponse<?> response = new SingleDataResponse();
			MessagesDto messagesDto = new MessagesDto();
			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.enteObbligatorio"));
			response.setMessages(messagesDto);
			return response;			
		}
		//		enteService.getByCodIpaEnte(codIpa);
		Errors result = new BeanPropertyBindingResult(anagraficaUfficioCapitoloAccertamentoDto, "anagraficaUfficioCapitoloAccertamentoDto");

		SingleDataResponse  response = new SingleDataResponse();

		/**
		 * Validazione form
		 */
		validator.validate(anagraficaUfficioCapitoloAccertamentoDto, result);

		MessagesDto messagesDto = new MessagesDto();

		if (result.hasErrors()){					
			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.anagrafica.dettaglioAnagrafica.modAnagrafica.nocommand"));					

		}else{

			AnagraficaUfficioCapitoloAccertamento nuovaAnagrafica = new AnagraficaUfficioCapitoloAccertamento();

			nuovaAnagrafica.setCodTipoDovuto(anagraficaUfficioCapitoloAccertamentoDto.getCodTipoDovuto());
			if (StringUtils.isBlank(anagraficaUfficioCapitoloAccertamentoDto.getCodUfficio())
					|| anagraficaUfficioCapitoloAccertamentoDto.getCodUfficio().equals("n/a")){
				nuovaAnagrafica.setCodUfficio("n/a");
				nuovaAnagrafica.setDeUfficio("n/a");
				nuovaAnagrafica.setFlgAttivo(true);
			}
			else {
				nuovaAnagrafica.setCodUfficio(anagraficaUfficioCapitoloAccertamentoDto.getCodUfficio());
				nuovaAnagrafica.setDeUfficio(anagraficaUfficioCapitoloAccertamentoDto.getDeUfficio());						
				nuovaAnagrafica.setFlgAttivo(anagraficaUfficioCapitoloAccertamentoDto.getFlgAttivo());
			}
			nuovaAnagrafica.setCodCapitolo(anagraficaUfficioCapitoloAccertamentoDto.getCodCapitolo());
			nuovaAnagrafica.setDeCapitolo(anagraficaUfficioCapitoloAccertamentoDto.getDeCapitolo());
			nuovaAnagrafica.setDeAnnoEsercizio(anagraficaUfficioCapitoloAccertamentoDto.getDeAnnoEsercizio());
			nuovaAnagrafica.setCodAccertamento(StringUtils.isBlank(anagraficaUfficioCapitoloAccertamentoDto.getCodAccertamento()) ? "n/a" : anagraficaUfficioCapitoloAccertamentoDto.getCodAccertamento());
			nuovaAnagrafica.setDeAccertamento(StringUtils.isBlank(anagraficaUfficioCapitoloAccertamentoDto.getDeAccertamento()) ? "n/a" : anagraficaUfficioCapitoloAccertamentoDto.getDeAccertamento());
			nuovaAnagrafica.setDtCreazione(new Date());
			nuovaAnagrafica.setDtUltimaModifica(new Date());
			Ente ente = new Ente();
			ente.setId(enteTO.getId());
			nuovaAnagrafica.setEnte(ente);

			AggiornaAnagraficaUfficioCapitoloAccertamentoResultDto esitoInserimento;
			try {
				esitoInserimento = anagraficaUfficioCapitoloAccertamentoService.salvaAnagrafica(nuovaAnagrafica,enteTO.getCodIpaEnte());
			} catch (EntityNotFoundException entityNotFoundException) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, entityNotFoundException.getMessage());
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, String.format(Response.RESPONSE_KO, e.getMessage()));
			}

			AnagraficaUfficioCapitoloAccertamentoDto nuovoDto = esitoInserimento.getAnagraficaAggiornata();
			ESITO esito = esitoInserimento.getEsito();
			switch (esito) {
			case INSERITA:
				log.debug("Anagrafica inserita correttamente [" + esito + "]");
				messagesDto.getInformationMessages().add(new DynamicMessageDto("mypivot.anagrafica.info.inserimentoAnagrafica.ok"));
				response.setItems(nuovoDto);
				break;
			case EXIST:
				log.debug("Anagrafica già esistente [" + esito + "]");
				messagesDto.getInformationMessages().add(new DynamicMessageDto("mypivot.anagrafica.info.inserimentoAnagrafica.exist"));
				break;
			default:
				log.debug("Errore nell'inserimento dell'anagrafica [" + esito + "]");
				messagesDto.getInformationMessages().add(new DynamicMessageDto("mypivot.anagrafica.info.inserimentoAnagrafica.ko"));
				break;
			}


		}
		response.setMessages(messagesDto);
		return response;

	}



	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation(value = "delete Anagrafica", notes = ApiDescription.DESCR_DELETE_ENTE_TIPO_DOVUTO, response = IoResponse.class)
	@DeleteMapping(value = "/cancellaAnagraficaCapitolo")			
	public ResponseIF cancellaAnagrafica(
			@RequestParam(required = true) String codIpa,
			@RequestParam(required = true) String id)
					throws ParseException{

		EnteTO enteTO = enteService.getByCodIpaEnte(codIpa);			
		if (enteTO == null) {
			SingleDataResponse<?> response = new SingleDataResponse();
			MessagesDto messagesDto = new MessagesDto();
			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.codIpaEnteNonValido"));
			response.setMessages(messagesDto);
			return response;
		}
		
		log.info("delete Anagrafica");		

		try{

			if(anagraficaUfficioCapitoloAccertamentoService.deleteAnagrafica(Long.parseLong(id), enteTO.getId()))
				return new IoResponse("OK", "Anagrafica deleted");
			else
				return new IoResponse("KO", "Anagrafica not found");



		} catch (EntityNotFoundException entityNotFoundException) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, entityNotFoundException.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, String.format(Response.RESPONSE_KO, e.getMessage()));
		}
	}




	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation(value = "get all Anagrafiche Capitoli", notes = ApiDescription.DESCR_GET_ALL_ANAGRAFICA, response = PageDtoResponse.class)
	@GetMapping(value = "/ricercaCapitoli")	
	public ResponseIF ricercaCapitoli(
			Pageable pageable,
			@RequestParam(required = true) String codIpa,

			@RequestParam(required = false) String codTipoDovuto, 
			@RequestParam(required = false) String codiceUfficio, 
			@RequestParam(required = false) String denominazioneUfficio, 
			@RequestParam(required = false) String codiceCapitolo, 
			@RequestParam(required = false) String denominazioneCapitolo, 
			@RequestParam(required = false) String annoEsercizio,
			@RequestParam(required = false) Boolean flgAttivo,
			@RequestParam(required = false) String codiceAccertamento, 
			@RequestParam(required = false) String denominazioneAccertamento

			//			@RequestParam(required = true) String tab, 
			//			Model model, 

			) {

		UtenteTO utenteTO = null;

		if(enableGlobalProfile) 
			utenteTO = utenteService.getUtenteByGlobalDefault();	

		EnteTO enteTO = enteService.getByCodIpaEnte(codIpa);
		if (enteTO == null) {
			SingleDataResponse<?> response = new SingleDataResponse();
			MessagesDto messagesDto = new MessagesDto();
			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.codIpaEnteNonValido"));
			response.setMessages(messagesDto);
			return response;
		}

		EnteTipoDovuto item = enteTipoDovutoService.getByCodIpaEnteAndCodTipoDovuto(enteTO.getCodIpaEnte(), codTipoDovuto);
		if (item == null) {			
			SingleDataResponse<?> response = new SingleDataResponse();
			MessagesDto messagesDto = new MessagesDto();
			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.codTipoDovutoNonValido"));
			response.setMessages(messagesDto);
			return response;
		}

		log.debug("RICERCA :: GESTIONE ANAGRAFICA UFFICIO CAPITOLO ACCERTAMENTO :: GET :: Utente[codFedUserId: " + utenteTO.getCodFedUserId() + "] :: START");


		/* */
		PageDtoResponse<?> response = new PageDtoResponse();

		try{

			//			/**
			//			 * La funzione verifica che l'utente autenticato abbia selezionato l'ente.
			//			 */
			//			if(!UtilitiesCtrl.hasSecurityEnte()) return new ModelAndView(new RedirectView(request.getContextPath() + Constants.PATH_PAGE_SCELTA_ENTE));
			//		
			//			/**
			//			 * La funzione verifica che l'utente autenticato abbia dei ruoli attivi.
			//			 */
			//			if(!UtilitiesCtrl.hasSecurityRole()) return new ModelAndView(new RedirectView(request.getContextPath() + Constants.PATH_PAGE_SCELTA_ENTE));
			//		
			//			/**
			//			 * La funzione verifica che l'utente possa accedere alla funzionalità di "Accertamento".
			//			 */
			//			if (!AccertamentoUtils.hasSecurityAccessFunctionality()) return UtilitiesCtrl.getViewErrorMessage("mypivot.messages.error.notAuthorized");


			AnagraficaUfficioCapitoloAccertamentoCommand anagraficaUfficioCapitoloAccertamentoCommand = new AnagraficaUfficioCapitoloAccertamentoCommand();

			Integer page = pageable.getPageNumber();
			int pageToGet = 1;
			if (page > 0) {
				pageToGet = page;
			}

			String pg = Integer.toString(pageToGet);
			String pgSize = Integer.toString(pageable.getPageSize());	

			setFilterToBean(codIpa, utenteTO.getCodFedUserId(), codTipoDovuto, codiceUfficio, denominazioneUfficio,
					codiceCapitolo, denominazioneCapitolo, annoEsercizio, codiceAccertamento, denominazioneAccertamento,
					pg, pgSize, flgAttivo, anagraficaUfficioCapitoloAccertamentoCommand);


			/**
			 * Avvio la ricerca
			 */
			response.setPage(ricerca(anagraficaUfficioCapitoloAccertamentoCommand,enteTO.getId()));


		} catch (EntityNotFoundException entityNotFoundException) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, entityNotFoundException.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, String.format(Response.RESPONSE_KO, e.getMessage()));
		}


		log.debug("RICERCA :: GESTIONE ANAGRAFICA UFFICIO CAPITOLO ACCERTAMENTO :: GET :: Utente[codFedUserId: " + utenteTO.getCodFedUserId() + "] :: END");

		return response;
	}



	private PageDto<?> ricerca(AnagraficaUfficioCapitoloAccertamentoCommand anagraficaUfficioCapitoloAccertamentoCommand, Long idEnte) {

		//		MessagesDto messagesDto = new MessagesDto();

		try{

			//			/**
			//			 * La funzione verifica che l'utente autenticato abbia selezionato l'ente.
			//			 */
			//			if(!UtilitiesCtrl.hasSecurityEnte()) return new ModelAndView(new RedirectView(request.getContextPath() + Constants.PATH_PAGE_SCELTA_ENTE));
			//		
			//			/**
			//			 * La funzione verifica che l'utente autenticato abbia dei ruoli attivi.
			//			 */
			//			if(!UtilitiesCtrl.hasSecurityRole()) return new ModelAndView(new RedirectView(request.getContextPath() + Constants.PATH_PAGE_SCELTA_ENTE));
			//		
			//			/**
			//			 * La funzione verifica che l'utente possa per accedere alla funzionalità di "Accertamento".
			//			 */
			//			if(!AccertamentoUtils.hasSecurityAccessFunctionality()) return UtilitiesCtrl.getViewErrorMessage("mypivot.messages.error.notAuthorized");
			//
			//			////////////RECUPERO LISTA DEI TIPIDOVUTO PER ENTE SELEZIONATO/////////////////		
			//			/**
			//			 * Recupero la lista dei tipi dovuto dell'ente per cui l'utente è un operatore attivo. 
			//			 * Se la lista è vuota, mostro un messaggio di errore perchè vuol dire che l'utente non può ne consultare ne creare accertamenti.
			//			 */
			//			List<EnteTipoDovutoTO> activeOperatoreEnteTdAsObj = operatoreEnteTipoDovutoService.getListaEnteTipoDovutoForOperatoreAndCodIpaEnte(SecurityContext.getUtente().getCodFedUserId(), SecurityContext.getEnte().getCodIpaEnte());
			//			
			//			if (activeOperatoreEnteTdAsObj == null || activeOperatoreEnteTdAsObj.isEmpty()) {
			//				log.warn("RICERCA :: GESTIONE ANAGRAFICA UFFICIO CAPITOLO ACCERTAMENTO :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: NON risulta OPERATORE per nessun tipo dovuto.");
			//				/* 
			//				 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
			//				 */
			//				return UtilitiesCtrl.getViewErrorMessage("mypivot.messages.error.nessunTipoDovutoAssegnato");
			//			}
			//			
			//			/**
			//			 * Ciclo la lista appena recuperata dei tipi dovuto e aggiungo ad una lista temporanea solamente il codice, mi servirà
			//			 * come filtro della query. 
			//			 */
			//			List<String> activeOperatoreEnteTdAsString = new ArrayList<String>();
			//			/* */
			//			for (EnteTipoDovutoTO item : activeOperatoreEnteTdAsObj) activeOperatoreEnteTdAsString.add(item.getCodTipo());
			//			////////////////FINE RECUPERO LISTA DEI TIPIDOVUTO/////////////////////////////

			/**
			 * Inizializzo il bean per la ricerca
			 */
			initialize(anagraficaUfficioCapitoloAccertamentoCommand);

			/**
			 * Eseguo la query di ricerca
			 */
			PageDto<AnagraficaUfficioCapitoloAccertamentoDto> result = anagraficaUfficioCapitoloAccertamentoService.findByFilter(
					idEnte, anagraficaUfficioCapitoloAccertamentoCommand.getCodTipoDovuto(), anagraficaUfficioCapitoloAccertamentoCommand.getCodiceUfficio(), 
					anagraficaUfficioCapitoloAccertamentoCommand.getDenominazioneUfficio(), anagraficaUfficioCapitoloAccertamentoCommand.getCodiceCapitolo(), 
					anagraficaUfficioCapitoloAccertamentoCommand.getDenominazioneCapitolo(), anagraficaUfficioCapitoloAccertamentoCommand.getAnnoEsercizio(), anagraficaUfficioCapitoloAccertamentoCommand.getCodiceAccertamento(), anagraficaUfficioCapitoloAccertamentoCommand.getDenominazioneAccertamento(), 
					null, null, anagraficaUfficioCapitoloAccertamentoCommand.getFlgAttivo(), true, anagraficaUfficioCapitoloAccertamentoCommand.getPage(), anagraficaUfficioCapitoloAccertamentoCommand.getPageSize(), null);


			return result;

		} catch (EntityNotFoundException entityNotFoundException) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, entityNotFoundException.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, String.format(Response.RESPONSE_KO, e.getMessage()));
		}


	}

	/**
	 * Inizializzo il bean che mappa la form di ricerca
	 * 
	 * @param anagraficaUfficioCapitoloAccertamentoCommand
	 * @author Alessandro Paolillo
	 */
	private void initialize(AnagraficaUfficioCapitoloAccertamentoCommand anagraficaUfficioCapitoloAccertamentoCommand) {

		/*
		 * Se la proprietà è nulla, la inizializzo a stringa vuota. La stringa vuota individua l'opzione di filtro ALL(tutti i tipi dovuto) 
		 */
		if( anagraficaUfficioCapitoloAccertamentoCommand.getCodTipoDovuto() == null) anagraficaUfficioCapitoloAccertamentoCommand.setCodTipoDovuto(""); 

		if(StringUtils.isBlank(anagraficaUfficioCapitoloAccertamentoCommand.getAnnoEsercizio())) {
			Date now = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(now);
			int year = cal.get(Calendar.YEAR);
			anagraficaUfficioCapitoloAccertamentoCommand.setAnnoEsercizio(String.valueOf(year));
		}

	}


	private void setFilterToBean(
			String codIpaEnte, String codFedUserId, String codTipoDovuto, String codiceUfficio, String denominazioneUfficio,
			String codiceCapitolo, String denominazioneCapitolo, String deAnnoEsercizio, String codiceAccertamento, String denominazioneAccertamento,
			String page, String pageSize, Boolean flgAttivo, AnagraficaUfficioCapitoloAccertamentoCommand anagraficaUfficioCapitoloAccertamentoCommand) {

		try{

			/** ============================================== CODICE TIPO DOVUTO ========================================== */
			try {
				if (codTipoDovuto == null)
					anagraficaUfficioCapitoloAccertamentoCommand.setCodTipoDovuto("");
				else
					anagraficaUfficioCapitoloAccertamentoCommand.setCodTipoDovuto(codTipoDovuto);
			} catch (Exception e) {
				anagraficaUfficioCapitoloAccertamentoCommand.setCodTipoDovuto("");
			}


			/** ============================================== CODICE UFFICIO ========================================== */
			try {
				if (codiceUfficio == null)
					anagraficaUfficioCapitoloAccertamentoCommand.setCodiceUfficio("");
				else
					anagraficaUfficioCapitoloAccertamentoCommand.setCodiceUfficio(codiceUfficio);
			} catch (Exception e) {
				anagraficaUfficioCapitoloAccertamentoCommand.setCodiceUfficio("");
			}

			/** ============================================== DENOMINAZIONE UFFICIO ========================================== */
			try {
				if (denominazioneUfficio == null)
					anagraficaUfficioCapitoloAccertamentoCommand.setDenominazioneUfficio("");
				else
					anagraficaUfficioCapitoloAccertamentoCommand.setDenominazioneUfficio(denominazioneUfficio);
			} catch (Exception e) {
				anagraficaUfficioCapitoloAccertamentoCommand.setDenominazioneUfficio("");
			}

			/** ============================================== CODICE CAPITOLO ========================================== */
			try {
				if (codiceCapitolo == null)
					anagraficaUfficioCapitoloAccertamentoCommand.setCodiceCapitolo("");
				else
					anagraficaUfficioCapitoloAccertamentoCommand.setCodiceCapitolo(codiceCapitolo);
			} catch (Exception e) {
				anagraficaUfficioCapitoloAccertamentoCommand.setCodiceCapitolo("");
			}

			/** ============================================== DENOMINAZIONE CAPITOLO ========================================== */
			try {
				if (denominazioneCapitolo == null)
					anagraficaUfficioCapitoloAccertamentoCommand.setDenominazioneCapitolo("");
				else
					anagraficaUfficioCapitoloAccertamentoCommand.setDenominazioneCapitolo(denominazioneCapitolo);
			} catch (Exception e) {
				anagraficaUfficioCapitoloAccertamentoCommand.setDenominazioneCapitolo("");
			}

			/** ============================================== CODICE ACCERTAMENTO ========================================== */
			try {
				if (codiceAccertamento == null)
					anagraficaUfficioCapitoloAccertamentoCommand.setCodiceAccertamento("");
				else
					anagraficaUfficioCapitoloAccertamentoCommand.setCodiceAccertamento(codiceAccertamento);
			} catch (Exception e) {
				anagraficaUfficioCapitoloAccertamentoCommand.setCodiceAccertamento("");
			}

			/** ============================================== DENOMINAZIONE ACCERTAMENTO ========================================== */
			try {
				if (denominazioneAccertamento == null)
					anagraficaUfficioCapitoloAccertamentoCommand.setDenominazioneAccertamento("");
				else
					anagraficaUfficioCapitoloAccertamentoCommand.setDenominazioneAccertamento(denominazioneAccertamento);
			} catch (Exception e) {
				anagraficaUfficioCapitoloAccertamentoCommand.setDenominazioneAccertamento("");
			}

			/** ===================================== NUMERO PAGINA ================================= */
			try {
				if (page == null)
					anagraficaUfficioCapitoloAccertamentoCommand.setPage(1);
				else
					anagraficaUfficioCapitoloAccertamentoCommand.setPage(Integer.parseInt(page));
			} catch (Exception e) {
				anagraficaUfficioCapitoloAccertamentoCommand.setPage(1);
			}

			/** ===================================== ELEMENTI PER PAGINA ================================= */
			try {
				if (pageSize == null)
					anagraficaUfficioCapitoloAccertamentoCommand.setPageSize(5);
				else
					anagraficaUfficioCapitoloAccertamentoCommand.setPageSize(Integer.parseInt(pageSize));
			} catch (Exception e) {
				anagraficaUfficioCapitoloAccertamentoCommand.setPageSize(5);
			}

			/** ===================================== FLAG ATTIVO ================================= */
			try {
				if (flgAttivo == null)
					anagraficaUfficioCapitoloAccertamentoCommand.setFlgAttivo(true);
				else
					anagraficaUfficioCapitoloAccertamentoCommand.setFlgAttivo(flgAttivo);
			} catch (Exception e) {
				anagraficaUfficioCapitoloAccertamentoCommand.setFlgAttivo(true);
			}

			/** ============================================== ANNO ESERCIZIO ========================================== */
			try {
				if (deAnnoEsercizio == null)
					anagraficaUfficioCapitoloAccertamentoCommand.setAnnoEsercizio("");
				else
					anagraficaUfficioCapitoloAccertamentoCommand.setAnnoEsercizio(deAnnoEsercizio);
			} catch (Exception e) {
				anagraficaUfficioCapitoloAccertamentoCommand.setAnnoEsercizio("");
			}


		}catch (Exception e) {
			log.warn("RICERCA :: GESTIONE ANAGRAFICA UFFICIO CAPITOLO ACCERTAMENTO :: Utente[codFedUserId: " + codFedUserId + "] :: ERRORE ", e);
		}

	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation(value = "upload Anagrafiche Capitoli", notes = ApiDescription.DESCR_UPLOAD_ANAGRAFICA, response = PageDtoResponse.class)
	@PostMapping(value = "/uploadCSV")		
	public SingleDataResponse uploadCSV(
			@RequestParam(required = true) String codIpa,
			@RequestParam("file") MultipartFile file
			) {

		SingleDataResponse<?> response = new SingleDataResponse();
		MessagesDto messagesDto = new MessagesDto();

		EnteTO ente = enteService.getByCodIpaEnte(codIpa);
		if (ente == null) {
			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.codIpaEnteNonValido"));
			response.setMessages(messagesDto);
			return response;
		}
		
		UtenteTO utenteTO = null;

		if(enableGlobalProfile) 
			utenteTO = utenteService.getUtenteByGlobalDefault();	

		log.debug("INSERIMENTO MASSIVO :: ANAGRAFICA UFF CAP ACC :: POST :: Utente[codFedUserId: " + utenteTO.getCodFedUserId() + "] :: START");
		ResponseUploadAnagUffCapAccDto responseDto = new ResponseUploadAnagUffCapAccDto();			

		if (ente.getCodIpaEnte().equals("R_VENETO")){
			messagesDto.getInformationMessages().add(new DynamicMessageDto("400","Non è consentito l'inserimento massivo per la regione Veneto."));
			response.setMessages(messagesDto);
			log.debug("INSERIMENTO MASSIVO :: ANAGRAFICA UFF CAP ACC :: POST :: Utente[codFedUserId: " + utenteTO.getCodFedUserId() + "] :: END");
			//				return new ResponseEntity<ResponseUploadAnagUffCapAccDto>(response, HttpStatus.OK);
			return response;

		}

		String split[] = {};
		String filename = file.getOriginalFilename();
		InputStream inputStream = null;
		FileInputStream fileInputStream = null;
		ZipFile zipFile = null;
		ZipInputStream zipInputStream = null;
		String endName="1_0.zip";
		try {
			fileInputStream = new FileInputStream(convert(file));
			zipInputStream = new ZipInputStream(fileInputStream);
			ZipEntry zipEntry = zipInputStream.getNextEntry();

			if (zipInputStream.getNextEntry() != null){
				zipInputStream.close();
				log.debug("INSERIMENTO MASSIVO :: ANAGRAFICA UFF CAP ACC :: POST :: Utente[codFedUserId: " + utenteTO.getCodFedUserId() + "] :: END");
				messagesDto.getInformationMessages().add(new DynamicMessageDto("400","Non è consentito un file .zip con più di un tracciato csv."));
				response.setMessages(messagesDto);
				return response;
			}

			zipFile = new ZipFile(filename);
			inputStream = zipFile.getInputStream(zipEntry);
			//controllo che i nomi del file zip e del csv all'interno siano uguali tranne le estensioni
			if (!zipEntry.getName().substring(0, zipEntry.getName().length()-4).equals(filename.substring(0, filename.length()-4))){
				zipFile.close();
				zipInputStream.close();
				log.debug("INSERIMENTO MASSIVO :: ANAGRAFICA UFF CAP ACC :: POST :: Utente[codFedUserId: " + utenteTO.getCodFedUserId() + "] :: END");
				messagesDto.getInformationMessages().add(new DynamicMessageDto("400","Il nome del file non è corretto."));
				response.setMessages(messagesDto);
				return response;

			}
			if (!zipEntry.getName().endsWith(".csv")){
				zipFile.close();
				zipInputStream.close();
				log.debug("INSERIMENTO MASSIVO :: ANAGRAFICA UFF CAP ACC :: POST :: Utente[codFedUserId: " + utenteTO.getCodFedUserId() + "] :: END");
				messagesDto.getInformationMessages().add(new DynamicMessageDto("400","Il file .zip deve contenere un tracciato csv."));
				response.setMessages(messagesDto);
				return response;

			}	

			//il nome del file deve essere scritto con il formato C_IPA-NOME FLUSSO-VERSIONE. es: R_VENETO-FLUSSO_IMPORT_MASSIVO-1_0
			if (!filename.contains("-")){
				zipFile.close();
				zipInputStream.close();
				log.debug("INSERIMENTO MASSIVO :: ANAGRAFICA UFF CAP ACC :: POST :: Utente[codFedUserId: " + utenteTO.getCodFedUserId() + "] :: END");
				messagesDto.getInformationMessages().add(new DynamicMessageDto("400","Il nome del file non è corretto."));
				response.setMessages(messagesDto);
				return response;

			}
			split = filename.split("-");
			if (split.length != 3 || !split[0].equals(ente.getCodIpaEnte()) || !split[2].equals(endName)){
				zipFile.close();
				zipInputStream.close();
				log.debug("INSERIMENTO MASSIVO :: ANAGRAFICA UFF CAP ACC :: POST :: Utente[codFedUserId: " + utenteTO.getCodFedUserId() + "] :: END");
				messagesDto.getInformationMessages().add(new DynamicMessageDto("400","Il nome del file non è corretto."));
				response.setMessages(messagesDto);
				return response;

			}	
		} catch (IOException e1) {
			log.debug("INSERIMENTO MASSIVO :: ANAGRAFICA UFF CAP ACC :: POST :: Utente[codFedUserId: " + utenteTO.getCodFedUserId() + "] :: END");
			messagesDto.getInformationMessages().add(new DynamicMessageDto("400","Errore durante la lettura del file .zip"));
			response.setMessages(messagesDto);
			return response;
		}
		try {
			anagraficaUfficioCapitoloAccertamentoService.manageFileCSV(inputStream, ente.getCodIpaEnte(),utenteTO.getCodFedUserId());
		} catch (Exception e) {
			try {
				zipFile.close();
				zipInputStream.close();
			} catch (IOException e1) {}
			log.debug("INSERIMENTO MASSIVO :: ANAGRAFICA UFF CAP ACC :: POST :: Utente[codFedUserId: " + utenteTO.getCodFedUserId() + "] :: END");
			messagesDto.getInformationMessages().add(new DynamicMessageDto("400",e.getMessage()));
			response.setMessages(messagesDto);
			return response;

		}

		try {
			zipFile.close();
			zipInputStream.close();
		} catch (IOException e1) {}
		log.debug("INSERIMENTO MASSIVO :: ANAGRAFICA UFF CAP ACC :: POST :: Utente[codFedUserId: " + utenteTO.getCodFedUserId() + "] :: END");

		messagesDto.getInformationMessages().add(new DynamicMessageDto("200","Upload eseguito correttamente"));
		response.setMessages(messagesDto);
		return response;
	}


	private File convert(MultipartFile file)
	{    
		File convFile = new File(file.getOriginalFilename());
		try {
			convFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(convFile); 
			fos.write(file.getBytes());
			fos.close(); 
		} catch (IOException e) {
			e.printStackTrace();
		}
		return convFile;
	}


}