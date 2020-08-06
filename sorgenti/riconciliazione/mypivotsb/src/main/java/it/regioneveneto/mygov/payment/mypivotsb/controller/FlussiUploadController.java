/**
 * 
 */
package it.regioneveneto.mygov.payment.mypivotsb.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import it.regioneveneto.mybox.client.MyBoxClient;
import it.regioneveneto.mybox.domain.Errore;
import it.regioneveneto.mybox.domain.IntestazioneRisposta;
import it.regioneveneto.mybox.domain.MyBoxAuthorize;
import it.regioneveneto.mybox.domain.MyBoxAuthorizeRisposta;
import it.regioneveneto.mygov.payment.mypivot.controller.command.FlussiUploadCommand;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.DynamicMessageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.FlussoDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.MessagesDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.PageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.ResponseUploadFlussoDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.UploadEsitoDto;
import it.regioneveneto.mygov.payment.mypivot.domain.po.ManageFlusso;
import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.UtenteTO;
import it.regioneveneto.mygov.payment.mypivot.service.EnteService;
import it.regioneveneto.mygov.payment.mypivot.service.ManageFlussoService;
//import it.regioneveneto.mygov.payment.mypivot.service.OperatoreEnteTipoDovutoService;
import it.regioneveneto.mygov.payment.mypivot.service.UtenteService;
import it.regioneveneto.mygov.payment.mypivot.utils.Constants;
import it.regioneveneto.mygov.payment.mypivotsb.constant.ApiDescription;
import it.regioneveneto.mygov.payment.mypivotsb.response.PageDtoResponse;
import it.regioneveneto.mygov.payment.mypivotsb.response.ResponseIF;
import it.regioneveneto.mygov.payment.mypivotsb.response.SingleDataResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/api/carica", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api(tags = { "Carica" })
public class FlussiUploadController extends BaseController {

	@Autowired
	private EnteService enteService;

	@Autowired
	private UtenteService utenteService;

	@Autowired
	private MyBoxClient myBoxClient;

	@Autowired
	private ManageFlussoService manageFlussoService;

//	@Autowired
//	private OperatoreEnteTipoDovutoService operatoreEnteTipoDovutoService;

	@Autowired
	private Environment environment;

	@Value("${myPivot.enableGlobalProfile}")
	private Boolean enableGlobalProfile;
	
	public FlussiUploadController() {
		super();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation(value = "get all flussi Upload", notes = ApiDescription.DESCR_GET_ALL_UPLOAD_FLUSSO, response = PageDtoResponse.class)
	@GetMapping(value = "/searchFlussiUpload")		
	public ResponseIF getFlussiUpload(
			
			Pageable pageable,
			@RequestParam(required = true) String codIpa,
			@RequestParam(required = true) String tipoFlusso,
			
//			@RequestParam(required = false) String pg, @RequestParam(required = false) String pgSize,
			
			@RequestParam(required = false) String dtFrom, 
			@RequestParam(required = false) String dtTo,			
			@RequestParam(required = false) String fSearch
			
//			@RequestParam(required = false) String command,
//			@RequestParam(required = false) String cod, 
//			ModelAndView mav
			
			) {

		/*
		 * //CONTROLLO ROBUSTEZZA: CHECK SE L'UTENTE PUO' ESSERE OPERATORE
		 * DELL'ENTE CORRENTE if (!SecurityContext.isOperatoreOnCurrentEnte()) {
		 * mav = new ModelAndView(); MessagesDto messagesDto = new
		 * MessagesDto(); messagesDto.getErrorMessages().add(new
		 * DynamicMessageDto("pa.messages.notOperatorAccess",
		 * SecurityContext.getEnte().getDeNomeEnte().toUpperCase()));
		 * mav.addObject("messagesDto", messagesDto);
		 * mav.addObject("altMessageImg", "Accesso vietato");
		 * mav.addObject("pageTitle", "Accesso vietato");
		 * mav.addObject("imgPath", "/pa/resources/img/accessoVietato.jpg");
		 * mav.setViewName("errorComunication"); return mav; }
		 */

		PageDtoResponse<?> response = new PageDtoResponse();
		
		EnteTO ente = enteService.getByCodIpaEnte(codIpa);
		if (ente == null) {
			MessagesDto messagesDto = new MessagesDto();
			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.codIpaEnteNonValido"));
			response.setMessages(messagesDto);
			return response;
		}
		
		UtenteTO utenteTO = null;
		
		if(enableGlobalProfile) 
			utenteTO = utenteService.getUtenteByGlobalDefault();	
		
		MessagesDto messagesDto = new MessagesDto();		
		
		// CHECK TIPO FLUSSO VALIDO
		if (!tipoFlusso.equals(Constants.COD_TIPO_FLUSSO_EXPORT_PAGATI.toString())
				&& !tipoFlusso.equals(Constants.COD_TIPO_FLUSSO_RENDICONTAZIONE_STANDARD.toString())
				&& !tipoFlusso.equals(Constants.COD_TIPO_FLUSSO_RENDICONTAZIONE_POSTE.toString())
				&& !tipoFlusso.equals(Constants.COD_TIPO_FLUSSO_TESORERIA.toString())
				&& !tipoFlusso.equals(Constants.COD_TIPO_FLUSSO_GIORNALE_DI_CASSA.toString())
				&& !tipoFlusso.equals(Constants.COD_TIPO_FLUSSO_GIORNALE_DI_CASSA_OPI.toString())) {

			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.export.errore.tipoFlussoNonValido"));
			response.setMessages(messagesDto);
			return response;	
		}

		// Check se tesoreria abilitata per l'ente selezionato
		if (!ente.getFlgTesoreria() && tipoFlusso.equals(Constants.COD_TIPO_FLUSSO_GIORNALE_DI_CASSA.toString())) {
			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.export.errore.tesoreriaNonAbilitata"));
			response.setMessages(messagesDto);
			return response;	
		}


//		if (SecurityContext.getAllOperatoreEnteTipoDovuto() == null
//				|| SecurityContext.getAllOperatoreEnteTipoDovuto().isEmpty()
//				|| Utilities.isAllOperatoreEnteTipoDovutoDisabilitati(SecurityContext.getAllOperatoreEnteTipoDovuto())
//				|| Utilities.isAllOperatoreEnteTipoDovutoDisabilitati(operatoreEnteTipoDovutoService
//						.findActiveByCodFedUserIdAndCodIpaEnte(utente.getCodFedUserId(), ente.getCodIpaEnte()))) {
//			mav.setViewName("message");
//
//			messagesDto = new MessagesDto();
//			messagesDto.getErrorMessages()
//					.add(new DynamicMessageDto("mypivot.messages.error.nessunTipoDovutoAssegnato"));
//			mav.addObject("messagesDto", messagesDto);
//
//			return mav;
//		}

		FlussiUploadCommand flussiUploadCommand = new FlussiUploadCommand();


		setFilters(pageable.getPageNumber(), pageable.getPageSize(), dtFrom, dtTo, fSearch, flussiUploadCommand);

		initialize(flussiUploadCommand);

		String dataFromStr = flussiUploadCommand.getDateFrom();
		String dataToStr = flussiUploadCommand.getDateTo();
		Date dateFrom = null;
		Date dateTo = null;

		// DATE FROM
		if (StringUtils.isNotBlank(dataFromStr)) {
			try {
				dateFrom = Constants.DDMMYYYY.parse(dataFromStr);
			} catch (ParseException e) {
				log.error("Errore nel parsing della data inizio nella ricerca dei debiti. DateFrom = [" + dataFromStr
						+ "]", e);
				dateFrom = Constants.MINIMUM_DATE;
			}
		} else {
			// dateFrom = Utilities.getMinimunDate();
		}

		// DATE TO
		if (StringUtils.isNotBlank(dataToStr)) {
			try {
				dateTo = Constants.DDMMYYYY.parse(dataToStr);
			} catch (ParseException e) {
				log.error("Errore nel parsing della data fine nella ricerca dei debiti. DateTo = [" + dataToStr + "]",
						e);
				dateTo = Constants.MAXIMUM_DATE;
			}
		} else {
			// dateTo = Utilities.getMaximumDate();
		}

		// if (command != null) {
		// if (command.equals("remove") && cod != null) {
		// annullaFlusso(cod, messagesDto);
		// }
		// }

		List<Order> orders = new ArrayList<>();
		orders.add(new Order(Direction.DESC, "dtCreazione"));
	
		int pageToGet = 0;
		if (pageable.getPageNumber() > 0) {
			pageToGet = pageable.getPageNumber() - 1;
		}
		
		PageDto<FlussoDto> flussiDtoPage = manageFlussoService.getFlussoPage(tipoFlusso.charAt(0),
				utenteTO.getCodFedUserId(), ente.getCodIpaEnte(), flussiUploadCommand.getFullTextSearch(), dateFrom,
				dateTo, PageRequest.of(pageToGet, pageable.getPageSize(), Sort.by(orders)));

		response.setPage(flussiDtoPage);
		response.setMessages(messagesDto);
		return response;
		
	}


	// FILTRI TABBELLA FLUSSI
	private void setFilters(Integer pg, Integer pgSize, String dtFrom, String dtTo,
			String fSearch, FlussiUploadCommand flussiUploadCommand) {

		// PAGE
		flussiUploadCommand.setPage(pg);
		// PAGE SIZE
		flussiUploadCommand.setPageSize(pgSize);

		// DATE FROM
		flussiUploadCommand.setDateFrom(dtFrom);

		// DATE TO
		flussiUploadCommand.setDateTo(dtTo);

		// FULL TEXT SEARCH
		if (fSearch == null)
			flussiUploadCommand.setFullTextSearch("");
		else
			flussiUploadCommand.setFullTextSearch(fSearch);

	}


	private void requestAuthToken(FlussiUploadCommand flussiUploadCommand, MessagesDto messagesDto, EnteTO ente) {
		MyBoxAuthorize authorize = new MyBoxAuthorize();
		authorize.setClientKey(ente.getMyboxClientKey());
		authorize.setClientSecret(ente.getMyboxClientSecret());
		MyBoxAuthorizeRisposta risposta = myBoxClient.myBoxAuthorize(authorize);

		IntestazioneRisposta intestazioneRisposta = risposta.getIntestazioneRisposta();

		List<Errore> errori = intestazioneRisposta.getErrori();
		if (!errori.isEmpty()) {
			flussiUploadCommand.setShowUploadForm(false);
			messagesDto.getErrorMessages().add(new DynamicMessageDto("pa.messages.enteNonAutorizzatoPerUploadFlusso"));
		} else {
			String authorizationToken = risposta.getTokenRisposta();

			// UPLOAD STUFF

			flussiUploadCommand.setShowUploadForm(true);
			flussiUploadCommand.setMyBoxContextURL(environment.getProperty("myBox.contextURL"));
			flussiUploadCommand.setAuthorizationToken(authorizationToken);

		}
	}

	private void initialize(FlussiUploadCommand flussiUploadCommand) {

		Date data_da = null;

		if (StringUtils.isNotBlank(flussiUploadCommand.getDateFrom())) {
			try {
				data_da = Constants.DDMMYYYY.parse(flussiUploadCommand.getDateFrom());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date data_a = null;

		if (StringUtils.isNotBlank(flussiUploadCommand.getDateTo())) {
			try {
				data_a = Constants.DDMMYYYY.parse(flussiUploadCommand.getDateTo());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		if ((data_da == null) && (data_a == null)) {
			data_a = new Date();
			data_a = DateUtils.setHours(data_a, 0);
			data_a = DateUtils.setMinutes(data_a, 0);
			data_a = DateUtils.setSeconds(data_a, 0);
			data_a = DateUtils.setMilliseconds(data_a, 0);

			data_da = DateUtils.addMonths(data_a, -1);
		} else if ((data_da != null) && (data_a == null)) {
			data_da = DateUtils.setHours(data_da, 0);
			data_da = DateUtils.setMinutes(data_da, 0);
			data_da = DateUtils.setSeconds(data_da, 0);
			data_da = DateUtils.setMilliseconds(data_da, 0);

			data_a = DateUtils.addMonths(data_da, -1);
		} else if ((data_da == null) && (data_a != null)) {
			data_a = DateUtils.setHours(data_a, 0);
			data_a = DateUtils.setMinutes(data_a, 0);
			data_a = DateUtils.setSeconds(data_a, 0);
			data_a = DateUtils.setMilliseconds(data_a, 0);

			data_da = DateUtils.addMonths(data_a, -1);
		}

		flussiUploadCommand.setDateFrom(Constants.DDMMYYYY.format(data_da));
		flussiUploadCommand.setDateTo(Constants.DDMMYYYY.format(data_a));

	}

	private boolean validateGiornaleDiCassaCsv(UploadEsitoDto uploadEsitoDto) {
		if (uploadEsitoDto == null)
			return false;
		if (StringUtils.isBlank(uploadEsitoDto.getDe_anno_bolletta()))
			return false;
		if (StringUtils.isBlank(uploadEsitoDto.getCod_bolletta()))
			return false;
		if (StringUtils.isBlank(uploadEsitoDto.getDt_contabile()))
			return false;
		if (StringUtils.isBlank(uploadEsitoDto.getDe_denominazione()))
			return false;
		if (StringUtils.isBlank(uploadEsitoDto.getDe_causale()))
			return false;
		if (StringUtils.isBlank(uploadEsitoDto.getNum_importo()))
			return false;
		if (StringUtils.isBlank(uploadEsitoDto.getDt_valuta()))
			return false;

		if (!NumberUtils.isNumber(uploadEsitoDto.getDe_anno_bolletta()))
			return false;
		if (!NumberUtils.isNumber(uploadEsitoDto.getCod_bolletta()))
			return false;
		if (!NumberUtils.isNumber(uploadEsitoDto.getDt_contabile()))
			return false;
		if (!NumberUtils.isNumber(uploadEsitoDto.getDe_denominazione()))
			return false;
		if (!NumberUtils.isNumber(uploadEsitoDto.getDe_causale()))
			return false;
		if (!NumberUtils.isNumber(uploadEsitoDto.getNum_importo()))
			return false;
		if (!NumberUtils.isNumber(uploadEsitoDto.getDt_valuta()))
			return false;

		List<String> list = new ArrayList<String>();

		list.add(uploadEsitoDto.getDe_anno_bolletta());
		list.add(uploadEsitoDto.getCod_bolletta());
		list.add(uploadEsitoDto.getDt_contabile());
		list.add(uploadEsitoDto.getDe_denominazione());
		list.add(uploadEsitoDto.getDe_causale());
		list.add(uploadEsitoDto.getNum_importo());
		list.add(uploadEsitoDto.getDt_valuta());

		if (hasDuplicates(list))
			return false;
		return true;
	}

	private boolean hasDuplicates(List<String> list) {
		Set<String> appeared = new HashSet<String>();
		for (String item : list) {
			if (!appeared.add(item)) {
				return true;
			}
		}
		return false;
	}

	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation(value = "upload File", notes = ApiDescription.DESCR_UPLOAD_FILE, response = PageDtoResponse.class)
	@PostMapping(value = "/uploadFile")		
	public ResponseIF uploadFile(
			
//			@RequestBody UploadDto uploadDto,
			@RequestParam(required = true) String codIpa,
			@RequestParam(required = true) String tipoFlusso,

			@RequestParam("file") MultipartFile file
			
			) {
		
		SingleDataResponse<?> response = new SingleDataResponse();
		EnteTO ente = enteService.getByCodIpaEnte(codIpa);
		if (ente == null) {
			MessagesDto messagesDto = new MessagesDto();
			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.codIpaEnteNonValido"));
			response.setMessages(messagesDto);
			return response;
		}
		
		UtenteTO utenteTO = null;
		
		if(enableGlobalProfile) 
			utenteTO = utenteService.getUtenteByGlobalDefault();	
		
		MessagesDto messagesDto = new MessagesDto();
		String response_serv = "";	
		
		ResponseUploadFlussoDto responseDto = new ResponseUploadFlussoDto();
		
		// CHECK TIPO FLUSSO VALIDO
		if (!tipoFlusso.equals(Constants.COD_TIPO_FLUSSO_EXPORT_PAGATI.toString())
				&& !tipoFlusso.equals(Constants.COD_TIPO_FLUSSO_RENDICONTAZIONE_STANDARD.toString())
				&& !tipoFlusso.equals(Constants.COD_TIPO_FLUSSO_RENDICONTAZIONE_POSTE.toString())
				&& !tipoFlusso.equals(Constants.COD_TIPO_FLUSSO_TESORERIA.toString())
				&& !tipoFlusso.equals(Constants.COD_TIPO_FLUSSO_GIORNALE_DI_CASSA.toString())
				&& !tipoFlusso.equals(Constants.COD_TIPO_FLUSSO_GIORNALE_DI_CASSA_OPI.toString())) {
		
			messagesDto.getErrorMessages().add(new DynamicMessageDto("400","Non è consentito il Flusso selezionato."));
			response.setMessages(messagesDto);
			return response;

		}

		// Check se tesoreria abilitata per l'ente selezionato
		if (!ente.getFlgTesoreria() && tipoFlusso.equals(Constants.COD_TIPO_FLUSSO_GIORNALE_DI_CASSA.toString())) {			

			messagesDto.getErrorMessages().add(new DynamicMessageDto("400","Tesoreria non abilitata."));
			response.setMessages(messagesDto);
			return response;
		}


//		if (SecurityContext.getAllOperatoreEnteTipoDovuto() == null
//				|| SecurityContext.getAllOperatoreEnteTipoDovuto().isEmpty()
//				|| Utilities.isAllOperatoreEnteTipoDovutoDisabilitati(SecurityContext.getAllOperatoreEnteTipoDovuto())
//				|| Utilities.isAllOperatoreEnteTipoDovutoDisabilitati(operatoreEnteTipoDovutoService
//						.findActiveByCodFedUserIdAndCodIpaEnte(utenteTO.getCodFedUserId(), ente.getCodIpaEnte()))) {
//
//				response.setMessage("Nessun tipo di dovuto assegnato.");
//				response.setCode("400");
//				return new ResponseEntity<ResponseUploadFlussoDto>(response, HttpStatus.OK);			
//		}

		FlussiUploadCommand flussiUploadCommand = new FlussiUploadCommand();

		flussiUploadCommand.setEnte(ente.getDeNomeEnte());

		// get authorization token from myBox required for upload

		try {
			requestAuthToken(flussiUploadCommand, messagesDto, ente);

			String requestToken = UUID.randomUUID().toString();

			ManageFlusso manageFlusso = manageFlussoService.insertFlusso(tipoFlusso.charAt(0), ente.getCodIpaEnte(),
					utenteTO.getCodFedUserId(), requestToken, Constants.DE_TIPO_STATO_MANAGE,
					Constants.COD_TIPO_STATO_MANAGE_FILE_IN_DOWNLOAD);

			flussiUploadCommand.setRequestToken(requestToken);

			String pathEnvProperty = "mybox.flussi." + tipoFlusso + ".uploadPath";

			flussiUploadCommand.setUploadPath(
					environment.getProperty(pathEnvProperty) + Constants.YYYY_MM.format(manageFlusso.getDtCreazione()));
			
		} catch (Exception e) {
			log.error("Errore di comunicazione con myBox o inserimento in importDovuti: " + e.getMessage());
			
			messagesDto.getErrorMessages().add(new DynamicMessageDto("404","Errore di comunicazione con myBox o inserimento in importDovuti: " + e.getMessage()));
			response.setMessages(messagesDto);
			return response;


		}

		// Upload del file su myBox
		try {

				String uploadFilesUrl= flussiUploadCommand.getMyBoxContextURL()+"/rest/upload.html?"
						+ "authorizationToken="+flussiUploadCommand.getAuthorizationToken()+
						"&requestToken="+flussiUploadCommand.getRequestToken() +
						"&importPath="+flussiUploadCommand.getUploadPath();
				
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.MULTIPART_FORM_DATA);

				RestTemplate restTemplate = new RestTemplate();
				
				LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			    
	            map.add("files",file.getResource()); 
	            
	            HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(map, headers);
	            response_serv = restTemplate.postForObject(uploadFilesUrl, requestEntity, String.class);
				
		} catch (Exception e) {
			log.error("Errore di comunicazione con myBox o inserimento in importDovuti: " + e.getMessage());
			messagesDto.getErrorMessages().add(new DynamicMessageDto("404","Errore di comunicazione con myBox o inserimento in importDovuti: " + e.getMessage()));
			response.setMessages(messagesDto);
			return response;

		}

		try {

			JSONArray jsonArray = new JSONArray(response_serv);
			JSONObject dati = (JSONObject)jsonArray.get(0);
			
			responseDto.setNomeFile(dati.getString("fileName"));
			responseDto.setDimensioneFile(dati.getString("fileSize"));
			responseDto.setCodRequestToken(flussiUploadCommand.getRequestToken());
			responseDto.setMessage("Flie caricato correttametne");
			responseDto.setCode("200");

			
		} catch (Exception e) {
			JSONObject obj2 = new JSONObject(response_serv);
			log.debug("ERRORE INSERIMENTO MASSIVO :: Utente[codFedUserId: " + utenteTO.getCodFedUserId() + "] :: END");			
			messagesDto.getErrorMessages().add(new DynamicMessageDto(obj2.getString("codice"), obj2.getString("descrizione")));
			response.setMessages(messagesDto);
			return response;


		}

		log.debug("INSERIMENTO MASSIVO :: POST :: Utente[codFedUserId: " + utenteTO.getCodFedUserId() + "] :: END");
		return new SingleDataResponse(responseDto);
	}

	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation(value = "upload Flusso", notes = ApiDescription.DESCR_UPLOAD_FLUSSO, response = PageDtoResponse.class)
	@PostMapping(value = "/uploadFlusso")		
	public ResponseIF flussiUploadEsito(
			
			@RequestBody UploadEsitoDto uploadEsitoDto) {
		
		log.debug("ESITO UPLOAD [" + uploadEsitoDto.getCodRequestToken() + "] " + uploadEsitoDto.getEsito());

		SingleDataResponse<?> response = new SingleDataResponse();
		MessagesDto messagesDto = new MessagesDto();
		
		if (uploadEsitoDto.getEsito().equals("OK")) {
			ManageFlusso manageFlusso = manageFlussoService.cambiaStatoFlusso(uploadEsitoDto.getCodRequestToken(),
					Constants.DE_TIPO_STATO_MANAGE, Constants.COD_TIPO_STATO_MANAGE_FILE_IN_DOWNLOAD,
					Constants.COD_TIPO_STATO_MANAGE_FILE_SCARICATO);

			String pathEnvProperty = "mybox.flussi." + String.valueOf(manageFlusso.getTipoFlusso().getCodTipo())
					+ ".uploadPath";
			String uploadPath = environment.getProperty(pathEnvProperty)
					+ Constants.YYYY_MM.format(manageFlusso.getDtCreazione());

			if (manageFlusso.getTipoFlusso().getCodTipo() == Constants.COD_TIPO_FLUSSO_RENDICONTAZIONE_POSTE) {
				try {
					manageFlussoService.completaFlussoPoste(uploadEsitoDto, uploadPath);
				} catch (ParseException e) {
					manageFlussoService.cambiaStatoFlusso(uploadEsitoDto.getCodRequestToken(),
							Constants.DE_TIPO_STATO_MANAGE, Constants.COD_TIPO_STATO_MANAGE_FILE_IN_DOWNLOAD,
							Constants.COD_TIPO_STATO_MANAGE_ERROR_DOWNLOAD);
					messagesDto.getErrorMessages().add(new DynamicMessageDto("404", "KO"));
					response.setMessages(messagesDto);
					return response;
				}
			} else if (manageFlusso.getTipoFlusso().getCodTipo() == Constants.COD_TIPO_FLUSSO_GIORNALE_DI_CASSA) {
				try {
					boolean valid = validateGiornaleDiCassaCsv(uploadEsitoDto);
					if (!valid) {
						log.error(
								"I dati ricevuti non sono tutti valorizzati, non sono tutti numeri oppure sono duplicati");
						manageFlussoService.cambiaStatoFlusso(uploadEsitoDto.getCodRequestToken(),
								Constants.DE_TIPO_STATO_MANAGE, Constants.COD_TIPO_STATO_MANAGE_FILE_IN_DOWNLOAD,
								Constants.COD_TIPO_STATO_MANAGE_ERROR_DOWNLOAD);
						messagesDto.getErrorMessages().add(new DynamicMessageDto("400", "I dati ricevuti non sono tutti valorizzati, non sono tutti numeri oppure sono duplicati"));
						response.setMessages(messagesDto);
						return response;
					}
					manageFlussoService.completaFlussoGiornaleDiCassaCsv(uploadEsitoDto, uploadPath);
				} catch (Exception e) {
					manageFlussoService.cambiaStatoFlusso(uploadEsitoDto.getCodRequestToken(),
							Constants.DE_TIPO_STATO_MANAGE, Constants.COD_TIPO_STATO_MANAGE_FILE_IN_DOWNLOAD,
							Constants.COD_TIPO_STATO_MANAGE_ERROR_DOWNLOAD);
					messagesDto.getErrorMessages().add(new DynamicMessageDto("404", "KO"));
					response.setMessages(messagesDto);
					return response;
				}
			} else {
				manageFlussoService.completaFlusso(uploadEsitoDto.getCodRequestToken(), uploadPath,
						uploadEsitoDto.getNomeFile(), uploadEsitoDto.getDimensioneFile());
			}
		} else {
			manageFlussoService.cambiaStatoFlusso(uploadEsitoDto.getCodRequestToken(), Constants.DE_TIPO_STATO_MANAGE,
					Constants.COD_TIPO_STATO_MANAGE_FILE_IN_DOWNLOAD, Constants.COD_TIPO_STATO_MANAGE_ERROR_DOWNLOAD);
		}

		messagesDto.getSuccessMessages().add(new DynamicMessageDto("200", "OK"));
		response.setMessages(messagesDto);
		return response;
	}

}
