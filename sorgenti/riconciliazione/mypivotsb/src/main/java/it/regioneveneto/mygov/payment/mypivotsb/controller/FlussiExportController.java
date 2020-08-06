/**
 * 
 */
package it.regioneveneto.mygov.payment.mypivotsb.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import it.regioneveneto.mybox.client.MyBoxClient;
import it.regioneveneto.mybox.domain.MyBoxAuthorize;
import it.regioneveneto.mybox.domain.MyBoxAuthorizeRisposta;
import it.regioneveneto.mygov.payment.mypivot.controller.command.FlussiExportCommand;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.DynamicMessageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.FlussoExportDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.MessagesDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.PageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.ResponseUploadFlussoDto;
import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.PrenotazioneFlussoRiconciliazioneTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.UtenteTO;
import it.regioneveneto.mygov.payment.mypivot.service.EnteService;
import it.regioneveneto.mygov.payment.mypivot.service.PrenotazioneFlussoRiconciliazioneService;
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
@RequestMapping(value = "/api/export", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api(tags = { "Carica" })
public class FlussiExportController extends BaseController {

	@Autowired
	private EnteService enteService;

	@Autowired
	private UtenteService utenteService;

	@Autowired
	private MyBoxClient myBoxClient;

	//	@Autowired
	//	private OperatoreEnteTipoDovutoService operatoreEnteTipoDovutoService;

	@Autowired
	private PrenotazioneFlussoRiconciliazioneService prenotazioneFlussoRiconciliazioneService;

	@Autowired
	private Environment environment;

	@Value("${myPivot.enableGlobalProfile}")
	private Boolean enableGlobalProfile;

	public FlussiExportController() {
		super();
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation(value = "get all flussi Export", notes = ApiDescription.DESCR_GET_ALL_EXPORT_FLUSSO, response = PageDtoResponse.class)
	@GetMapping(value = "/searchFlussiExport")		
	public ResponseIF searchFlussiExport(

			Pageable pageable,
			@RequestParam(required = true) String codIpa,

			//			@RequestParam(required = false) String pg, @RequestParam(required = false) String pgSize,
			
			@RequestParam(required = false) String codRequestToken,
			
			@RequestParam(required = false) String dtFrom, 
			@RequestParam(required = false) String dtTo,			
			@RequestParam(required = false) String fSearch

			) {

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

		FlussiExportCommand flussiExportCommand = new FlussiExportCommand();


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


		setFilters(pageable.getPageNumber(), pageable.getPageSize(), dtFrom, dtTo, fSearch, flussiExportCommand);

		initialize(flussiExportCommand);

		String dataFromStr = flussiExportCommand.getDateFrom();
		String dataToStr = flussiExportCommand.getDateTo();
		Date dateFrom = null;
		Date dateTo = null;

		if (StringUtils.isNotBlank(codRequestToken)) {
			
			PageDto<FlussoExportDto> flussiDtoPage =prenotazioneFlussoRiconciliazioneService.getPrenotazioneByCodRequestToken(codRequestToken);

			response.setPage(flussiDtoPage);
			response.setMessages(messagesDto);
			return response;

		}
		else {
			// DATE FROM
			if (StringUtils.isNotBlank(dataFromStr)) {
				try {
					dateFrom = Constants.DDMMYYYY.parse(dataFromStr);
				} catch (ParseException e) {
					log.error("Errore nel parsing della data inizio nella ricerca degli export. DateFrom = [" + dataFromStr
							+ "]", e);
					dateFrom = Constants.MINIMUM_DATE;
				}
			}
	
			// DATE TO
			if (StringUtils.isNotBlank(dataToStr)) {
				try {
					dateTo = Constants.DDMMYYYY.parse(dataToStr);
				} catch (ParseException e) {
					log.error("Errore nel parsing della data fine nella ricerca degli export. DateTo = [" + dataToStr + "]",
							e);
					dateTo = Constants.MAXIMUM_DATE;
				}
			}
	
			List<Order> orders = new ArrayList<>();
			orders.add(new Order(Direction.DESC, "dtCreazione"));
	
			int page = pageable.getPageNumber();
			int pageToGet = 0;
			if (page > 0) {
				pageToGet = page - 1;
			}
			
			PageDto<FlussoExportDto> flussiDtoPage = prenotazioneFlussoRiconciliazioneService.getFlussoPage(
					utenteTO.getCodFedUserId(), ente.getCodIpaEnte(), flussiExportCommand.getFullTextSearch(), dateFrom,
					dateTo, PageRequest.of(pageToGet, pageable.getPageSize(), Sort.by(orders)));
	
			response.setPage(flussiDtoPage);
			response.setMessages(messagesDto);
			return response;
		}

	}


	// FILTRI TABBELLA FLUSSI
	private void setFilters(Integer pg, Integer pgSize, String dtFrom, String dtTo,
			String fSearch, FlussiExportCommand flussiExportCommand) {

		// PAGE
		flussiExportCommand.setPage(pg);
		// PAGE SIZE
		flussiExportCommand.setPageSize(pgSize);

		// DATE FROM
		flussiExportCommand.setDateFrom(dtFrom);

		// DATE TO
		flussiExportCommand.setDateTo(dtTo);

		// FULL TEXT SEARCH
		if (fSearch == null)
			flussiExportCommand.setFullTextSearch("");
		else
			flussiExportCommand.setFullTextSearch(fSearch);

	}


	private void initialize(FlussiExportCommand flussiExportCommand) {

		Date data_da = null;

		if (StringUtils.isNotBlank(flussiExportCommand.getDateFrom())) {
			try {
				data_da = Constants.DDMMYYYY.parse(flussiExportCommand.getDateFrom());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date data_a = null;

		if (StringUtils.isNotBlank(flussiExportCommand.getDateTo())) {
			try {
				data_a = Constants.DDMMYYYY.parse(flussiExportCommand.getDateTo());
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

		flussiExportCommand.setDateFrom(Constants.DDMMYYYY.format(data_da));
		flussiExportCommand.setDateTo(Constants.DDMMYYYY.format(data_a));

	}

	private MyBoxAuthorizeRisposta requestAuthToken(EnteTO ente) {
		MyBoxAuthorize myboxAuthorize = new MyBoxAuthorize();
		myboxAuthorize.setClientKey(ente.getMyboxClientKey());
		myboxAuthorize.setClientSecret(ente.getMyboxClientSecret());
		MyBoxAuthorizeRisposta myboxAuthorizeRisposta = myBoxClient.myBoxAuthorize(myboxAuthorize);
		return myboxAuthorizeRisposta;
	}



	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation(value = "download File", notes = ApiDescription.DESCR_DOWNLOAD_FILE, response = PageDtoResponse.class)
	@GetMapping(value = "/downloadFile", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)		
	//	public ResponseEntity<ByteArrayResource>  downoadFile(
	public	ResponseEntity<byte[]> downloadFile(

			@RequestParam(required = true) String codIpa,
			@RequestParam(required = true) Long idFlusso

			) {

		ResponseUploadFlussoDto response = new ResponseUploadFlussoDto();
		
		EnteTO ente = enteService.getByCodIpaEnte(codIpa);
		if (ente == null) {
			response.setMessage("mypivot.messages.error.codIpaEnteNonValido");
			response.setCode("404");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, response.getMessage());	
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


		// get authorization token from myBox required for upload
		MyBoxAuthorizeRisposta myBoxResponse;

		try {

			myBoxResponse = requestAuthToken(ente);

		} catch (Exception e) {
			log.error("Errore di comunicazione con myBox o inserimento in importDovuti: " + e.getMessage());
			response.setMessage("Errore di comunicazione con myBox o inserimento in importDovuti: " + e.getMessage());
			response.setCode("404");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, response.getMessage());
			//			return new ResponseEntity<ResponseUploadFlussoDto>(response, HttpStatus.NOT_FOUND);			

		}

		// Download del file da myBox
		try {

			PrenotazioneFlussoRiconciliazioneTO prenotazioneFlussoRiconciliazioneTO = 
					prenotazioneFlussoRiconciliazioneService.getByCodIdFlusso(idFlusso);

			String authorizationToken = myBoxResponse.getTokenRisposta();

			String downloadFilesUrl= environment.getProperty("myBox.contextURL")+"/rest/download.html?"
					+ "authorizationToken="+authorizationToken+
					"&fileName="+prenotazioneFlussoRiconciliazioneTO.getDeNomeFileGenerato();

			RestTemplateBuilder  restTemplate = new RestTemplateBuilder ();
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));
			HttpEntity<String> entity = new HttpEntity<>(headers);
			ResponseEntity<byte[]> response_serv = restTemplate.build()
					.exchange(downloadFilesUrl, HttpMethod.GET, entity, byte[].class);

			String file_path = prenotazioneFlussoRiconciliazioneTO.getDeNomeFileGenerato();
			String file_name = file_path.substring(file_path.lastIndexOf("/")+1);

			//		          byte[] data = response_serv.getBody();
			//		          ByteArrayResource resource = new ByteArrayResource(data);		          		   		        		  
			//		          return ResponseEntity.ok()
			//		                  // Content-Disposition
			//		                  .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + "R_CAMPAN-18_ESTRAZIONE_RICONCILIAZIONE_20200122122052-1_1.zip")
			//		                  // Content-Type
			//		                  //.contentType(MediaType.APPLICATION_OCTET_STREAM_VALUE)
			//		                  // Content-Lengh
			//		                  .contentLength(data.length) //
			//		                  .body(resource);

			byte[] bytes = response_serv.getBody();
			MultiValueMap<String, String> headers_ret = new LinkedMultiValueMap<>();
			headers_ret.add("Content-Type", "application/octet-stream");
			headers_ret.add("Content-Disposition", "attachment; filename="+file_name);
			return new ResponseEntity<>(bytes, headers_ret, HttpStatus.OK);

		} catch (Exception e) {
			log.error("Errore di comunicazione con myBox o inserimento in importDovuti: " + e.getMessage());
			response.setMessage("Errore di comunicazione con myBox o inserimento in importDovuti: " + e.getMessage());
			response.setCode("404");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, response.getMessage());			
		}

	}

}
