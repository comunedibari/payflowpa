package it.regioneveneto.mygov.payment.mypivotsb.controller.cruscottoIncassi;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.expression.ParseException;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.DynamicMessageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.FlussoRicevutaDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.MessagesDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.PageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.VisualizzaFlussoRicevutaDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.cruscottoIncassi.CruscottoIncassiAccertamentiDettaglioDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.cruscottoIncassi.CruscottoIncassiAccertamentiInputDto;
import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTipoDovutoTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.UtenteTO;
import it.regioneveneto.mygov.payment.mypivot.service.EnteService;
import it.regioneveneto.mygov.payment.mypivot.service.FlussoExportService;
import it.regioneveneto.mygov.payment.mypivot.service.OperatoreEnteTipoDovutoService;
import it.regioneveneto.mygov.payment.mypivot.service.UtenteService;
import it.regioneveneto.mygov.payment.mypivot.service.VmStatisticaAccertamentiService;
import it.regioneveneto.mygov.payment.mypivot.utils.Constants;
import it.regioneveneto.mygov.payment.mypivotsb.constant.ApiDescription;
import it.regioneveneto.mygov.payment.mypivotsb.controller.BaseController;
import it.regioneveneto.mygov.payment.mypivotsb.controller.visualizzazioneFlussi.filter.FlussiRicevutaViewFilter;
import it.regioneveneto.mygov.payment.mypivotsb.response.MultipleDataResponse;
import it.regioneveneto.mygov.payment.mypivotsb.response.PageDtoResponse;
import it.regioneveneto.mygov.payment.mypivotsb.response.ResponseIF;
import it.regioneveneto.mygov.payment.mypivotsb.response.SingleDataResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/api/cruscottoIncassi", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api(tags = { "CruscottoIncassi" })
public class VisualizzaDettaglioCruscottoController extends BaseController {

	private static final Logger LOG = Logger.getLogger(VisualizzaDettaglioCruscottoController.class);

	@Autowired
	private OperatoreEnteTipoDovutoService operatoreEnteTipoDovutoService;

	@Autowired
	private FlussoExportService flussoExportService;

	@Autowired
	private EnteService enteService;

	@Autowired
	private UtenteService utenteService;

	@Autowired
	private VmStatisticaAccertamentiService vmStatisticheService;

	@Value("${myPivot.enableGlobalProfile}")
	private Boolean enableGlobalProfile;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation(value = "get statistiche pagamenti aggregati per Anno/Mese/Girono", notes = ApiDescription.DESCR_GET_STATISTICHE_PER_ANNOMESEGIORNO, response = PageDtoResponse.class)
	@GetMapping(value = "/getDettaglioCapitolo")	
	public ResponseIF getDettaglioCapitolo(

			@RequestBody CruscottoIncassiAccertamentiInputDto cruscottoIncassiInput

			) {

		UtenteTO utenteTO = null;
		
		if(enableGlobalProfile) 
			utenteTO = utenteService.getUtenteByGlobalDefault();	
		
		EnteTO ente = enteService.getByCodIpaEnte(cruscottoIncassiInput.getCodIpa());
		if (ente == null) {
			SingleDataResponse<?> response = new SingleDataResponse();
			MessagesDto messagesDto = new MessagesDto();
			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.codIpaEnteNonValido"));
			response.setMessages(messagesDto);
			return response;
		}
		
		PageDtoResponse<?> response = new PageDtoResponse();
		MessagesDto messagesDto = new MessagesDto();

		log.debug("DETTAGLIO CRUSCOTTO :: STATISTICA CAPITOLO :: GET :: Fields[codCapitolo:" + cruscottoIncassiInput.getCodiceCapitolo() + 
				", codUfficio:" + cruscottoIncassiInput.getCodiceUfficio() + ", " +
				"codTipoDovuto:" + cruscottoIncassiInput.getCodiceTipoDovuto() + "codFedUserId: " + utenteTO.getCodFedUserId() + "] :: START");

		try{

			/**
			 *  GET ENTE ID
			 */
			Long enteId = ente.getId();

			/**
			 * Controllo che l'utente sia operatore per il tipo dovuto.
			 */
			if (!operatoreEnteTipoDovutoService.isCodTipoDovutoValidoPerOperatoreAndCodIpaEnte(utenteTO.getCodFedUserId(), ente.getCodIpaEnte(), cruscottoIncassiInput.getCodiceTipoDovuto())) {
				log.error("DETTAGLIO CRUSCOTTO :: STATISTICA CAPITOLO :: GET :: Fields[codFedUserId: " + utenteTO.getCodFedUserId() + "] :: NON risulta OPERATORE del tipo dovuto per cui si è filtrata la statistica.");
				messagesDto.getInformationMessages().add(new DynamicMessageDto("mypivot.messages.errore.notAuthorized"));
				response.setMessages(messagesDto);
				return response;
			}

			Boolean annoCheck = 		  cruscottoIncassiInput.getAnnoCheck();
			Boolean annoMeseCheck = 	  cruscottoIncassiInput.getAnnoMeseCheck();
			Boolean annoMeseGiornoCheck = cruscottoIncassiInput.getAnnoMeseGiornoCheck();

			String codUfficio = cruscottoIncassiInput.getCodiceUfficio();
			String codTipoDovuto =cruscottoIncassiInput.getCodiceTipoDovuto();
			String codCapitolo = cruscottoIncassiInput.getCodiceCapitolo();

			/**
			 */
			Integer anno = null, mese = null, giorno = null;

			if(annoCheck != null && annoCheck){
				/** 
				 *  GET ANNO
				 */
				anno = Integer.parseInt(cruscottoIncassiInput.getAnno());
			}else
				if(annoMeseCheck != null && annoMeseCheck){
					/* */
					Date date = Constants.MMYYYY.parse(cruscottoIncassiInput.getMese());
					/* */
					Calendar cal = Calendar.getInstance(); 		cal.setTime(date);
					/**
					 *  GET ANNO e MESE
					 */
					anno = cal.get(Calendar.YEAR); 		mese = cal.get(Calendar.MONTH)+1;
				}
				else
					if(annoMeseGiornoCheck != null && annoMeseGiornoCheck){
						/* */
						Date day = Constants.DDMMYYYY.parse(cruscottoIncassiInput.getGiorno());
						/* */
						Calendar cal = Calendar.getInstance(); 		cal.setTime(day);
						/**
						 *  GET ANNO, MESE e GIORNO
						 */
						anno = cal.get(Calendar.YEAR); 	  mese = cal.get(Calendar.MONTH)+1;	 	giorno = cal.get(Calendar.DAY_OF_MONTH);
					}

			log.debug("Parametri usati per la ricerca degli IUD: " + 
					"enteId[" + enteId + "], mese[" + mese + "], giorno[" + giorno + "], codUfficio[" + codUfficio + "], " + 
					"codTipoDovuto[" + codTipoDovuto + "], codCapitolo[" + codCapitolo + "]");

			/**
			 * Get IUD
			 */
			List<String> iud = vmStatisticheService.findListaPagamentiByFilter(enteId, anno, mese, giorno, codUfficio, codTipoDovuto, codCapitolo, null);


			if(iud != null && !iud.isEmpty()){

				return new MultipleDataResponse(iud);

			}else
			{
				messagesDto.getInformationMessages().add(new DynamicMessageDto("mypivot.statistiche.errore.dettaglio"));
				response.setMessages(messagesDto);
				return response;
			}	

		}catch(Exception e){
			log.error("DETTAGLIO CRUSCOTTO :: STATISTICA CAPITOLO :: GET :: Utente[codFedUserId: " + utenteTO.getCodFedUserId() + "] :: ERRORE ", e);
			messagesDto.getInformationMessages().add(new DynamicMessageDto("mypivot.statistiche.errore.dettaglio"));
			response.setMessages(messagesDto);
			return response;
		}
	}



	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation(value = "get statistiche pagamenti aggregati per Anno/Mese/Girono", notes = ApiDescription.DESCR_GET_STATISTICHE_PER_ANNOMESEGIORNO, response = MultipleDataResponse.class)
	@GetMapping(value = "/getDettaglioAccertamento")	
	public ResponseIF getDettaglioAccertamento(

			@RequestBody CruscottoIncassiAccertamentiDettaglioDto cruscottoIncassiInput

			) {

		UtenteTO utenteTO = null;
		
		if(enableGlobalProfile) 
			utenteTO = utenteService.getUtenteByGlobalDefault();	
		
		EnteTO ente = enteService.getByCodIpaEnte(cruscottoIncassiInput.getCodIpa());
		if (ente == null) {
			SingleDataResponse<?> response = new SingleDataResponse();
			MessagesDto messagesDto = new MessagesDto();
			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.codIpaEnteNonValido"));
			response.setMessages(messagesDto);
			return response;
		}
		
		MultipleDataResponse<?> response = new MultipleDataResponse();
		MessagesDto messagesDto = new MessagesDto();



		log.debug("DETTAGLIO CRUSCOTTO :: STATISTICA ACCERTAMENTO :: GET :: Fields[codAccertamento: " + cruscottoIncassiInput.getCodAccertamento() + ", codFedUserId: " + utenteTO.getCodFedUserId() + "] :: START");

		try{
			String codAccertamento = 	  cruscottoIncassiInput.getCodAccertamento();

			Boolean annoCheck = 		  cruscottoIncassiInput.getAnnoCheck();
			Boolean annoMeseCheck = 	  cruscottoIncassiInput.getAnnoMeseCheck();
			Boolean annoMeseGiornoCheck = cruscottoIncassiInput.getAnnoMeseGiornoCheck();

			String codUfficio = cruscottoIncassiInput.getCodiceUfficio();
			String codTipoDovuto =cruscottoIncassiInput.getCodiceTipoDovuto();
			String codCapitolo = cruscottoIncassiInput.getCodiceCapitolo();

			/**
			 *  GET ENTE ID
			 */
			Long enteId = ente.getId();


			/**
			 * Controllo che l'utente sia operatore per il tipo dovuto.
			 */
			if (!operatoreEnteTipoDovutoService.isCodTipoDovutoValidoPerOperatoreAndCodIpaEnte(utenteTO.getCodFedUserId(), ente.getCodIpaEnte(), cruscottoIncassiInput.getCodiceTipoDovuto())) {
				log.error("DETTAGLIO CRUSCOTTO :: STATISTICA CAPITOLO :: GET :: Fields[codFedUserId: " + utenteTO.getCodFedUserId() + "] :: NON risulta OPERATORE del tipo dovuto per cui si è filtrata la statistica.");
				messagesDto.getInformationMessages().add(new DynamicMessageDto("mypivot.messages.errore.notAuthorized"));
				response.setMessages(messagesDto);
				return response;
			}


			/**
			 */
			Integer anno = null, mese = null, giorno = null;

			if(annoCheck != null && annoCheck){
				/** 
				 *  GET ANNO
				 */
				anno = Integer.parseInt(cruscottoIncassiInput.getAnno());
			}else
				if(annoMeseCheck != null && annoMeseCheck){
					/* */
					Date date = Constants.MMYYYY.parse(cruscottoIncassiInput.getMese());
					/* */
					Calendar cal = Calendar.getInstance(); 		cal.setTime(date);
					/**
					 *  GET ANNO e MESE
					 */
					anno = cal.get(Calendar.YEAR); 		mese = cal.get(Calendar.MONTH)+1;
				}
				else
					if(annoMeseGiornoCheck != null && annoMeseGiornoCheck){
						/* */
						Date day = Constants.DDMMYYYY.parse(cruscottoIncassiInput.getMese());
						/* */
						Calendar cal = Calendar.getInstance(); 		cal.setTime(day);
						/**
						 *  GET ANNO, MESE e GIORNO
						 */
						anno = cal.get(Calendar.YEAR); 	  mese = cal.get(Calendar.MONTH)+1;	 	giorno = cal.get(Calendar.DAY_OF_MONTH);
					}

			LOG.debug("Parametri usati per la ricerca degli IUD: " + 
					"enteId[" + enteId + "], mese[" + mese + "], giorno[" + giorno + "], codUfficio[" + codUfficio + "], " + 
					"codTipoDovuto[" + codTipoDovuto + "], codCapitolo[" + codCapitolo + "], codAccertamento[" + codAccertamento + "]");

			/**
			 * Get IUD
			 */
			List<String> iud = vmStatisticheService.findListaPagamentiByFilter(enteId, anno, mese, giorno, codUfficio, codTipoDovuto, codCapitolo, codAccertamento);


			if(iud != null && !iud.isEmpty()){

				return new MultipleDataResponse(iud);

			}else
			{
				messagesDto.getInformationMessages().add(new DynamicMessageDto("mypivot.statistiche.errore.dettaglio"));
				response.setMessages(messagesDto);
				return response;
			}	

		}catch(Exception e){
			log.error("DETTAGLIO CRUSCOTTO :: STATISTICA ACCERTAMENTO :: GET :: Utente[codFedUserId: " + utenteTO.getCodFedUserId() + "] :: ERRORE ", e);
			messagesDto.getInformationMessages().add(new DynamicMessageDto("mypivot.statistiche.errore.dettaglio"));
			response.setMessages(messagesDto);
			return response;
		}
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation(value = "get statistiche pagamenti aggregati per Anno/Mese/Girono", notes = ApiDescription.DESCR_GET_STATISTICHE_PER_ANNOMESEGIORNO, response = PageDtoResponse.class)
	@GetMapping(value = "/visualizzaDettaglioCruscotto")	
	public ResponseIF visualizzaDettaglioCruscotto(
			
			@RequestBody VisualizzaFlussoRicevutaDto cruscottoIncassiInput
			
			) {

		UtenteTO utenteTO = null;
		
		if(enableGlobalProfile) 
			utenteTO = utenteService.getUtenteByGlobalDefault();	
		
		EnteTO ente = enteService.getByCodIpaEnte(cruscottoIncassiInput.getCodIpa());
		if (ente == null) {
			SingleDataResponse<?> response = new SingleDataResponse();
			MessagesDto messagesDto = new MessagesDto();
			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.codIpaEnteNonValido"));
			response.setMessages(messagesDto);
			return response;
		}
		
		PageDtoResponse<?> response = new PageDtoResponse();
		MessagesDto messagesDto = new MessagesDto();

		log.debug("DETTAGLIO CRUSCOTTO :: POST :: Fields[codFedUserId: " + utenteTO.getCodFedUserId() + "] :: START");

		PageDto<FlussoRicevutaDto> flussoRicevutaDtoPage = null;
		
		try{

			/**
			 * Recupero la lista dei tipi dovuto dell'ente per cui l'utente è un operatore attivo. 
			 * Se la lista è vuota, mostro un messaggio di errore perchè vuol dire che l'utente non può ne consultare ne creare accertamenti.
			 */
			List<EnteTipoDovutoTO> activeOperatoreEnteTdAsObj = operatoreEnteTipoDovutoService.getListaEnteTipoDovutoForOperatoreAndCodIpaEnte(utenteTO.getCodFedUserId(), ente.getCodIpaEnte());

			if (activeOperatoreEnteTdAsObj == null || activeOperatoreEnteTdAsObj.isEmpty()) {
				log.warn("DETTAGLIO CRUSCOTTO :: Utente[codFedUserId: " + utenteTO.getCodFedUserId() + "] :: NON risulta OPERATORE per nessun tipo dovuto.");
				messagesDto.getInformationMessages().add(new DynamicMessageDto("mypivot.messages.errore.nessunTipoDovutoAssegnato"));
				response.setMessages(messagesDto);
				return response;
			}

			//			List<EnteTipoDovutoTO> enteTipoDovutos = operatoreEnteTipoDovutoService
			//					.getListaEnteTipoDovutoForOperatoreAndCodIpaEnte(SecurityContext.getUtente().getCodFedUserId(), SecurityContext.getEnte().getCodIpaEnte());


			FlussiRicevutaViewFilter flussiRicevutaViewFilter = new FlussiRicevutaViewFilter();
			/**
			 * Inizializzo il bean per la ricerca
			 */
//			flussiRicevutaViewFilter.initialize(cruscottoIncassiInput);
			initialize(cruscottoIncassiInput);

			Date dt_data_esito_singolo_pagamento_da = null;

			if (cruscottoIncassiInput.getDataEsitoSingoloPagamentoCheck() != null
					&& cruscottoIncassiInput.getDataEsitoSingoloPagamentoCheck()
					&& StringUtils.hasText(cruscottoIncassiInput.getDataEsitoSingoloPagamentoDa())) {
				try {
					dt_data_esito_singolo_pagamento_da = Constants.DDMMYYYY.parse(cruscottoIncassiInput.getDataEsitoSingoloPagamentoDa());
				} catch (ParseException e) {
					// Nothing to do
				}
			}

			Date dt_data_esito_singolo_pagamento_a = null;

			if (cruscottoIncassiInput.getDataEsitoSingoloPagamentoCheck() != null
					&& cruscottoIncassiInput.getDataEsitoSingoloPagamentoCheck()
					&& StringUtils.hasText(cruscottoIncassiInput.getDataEsitoSingoloPagamentoA())) {
				try {
					dt_data_esito_singolo_pagamento_a = Constants.DDMMYYYY.parse(cruscottoIncassiInput.getDataEsitoSingoloPagamentoA());
				} catch (ParseException e) {
					// Nothing to do
				}
			}

			Sort ricevutaSort = getRicevutaSort();

			/**
			 * Eseguo la query di ricerca
			 */
			Integer page = cruscottoIncassiInput.getPage();
			Integer pgSize = cruscottoIncassiInput.getPageSize();
			
			int pageToGet = 0;
			if (page > 0) {
				pageToGet = page - 1;
			}
			flussoRicevutaDtoPage = flussoExportService.getFlussoExportPage(PageRequest.of(pageToGet,
					pgSize,
					ricevutaSort),
					ente.getCodIpaEnte(), utenteTO.getCodFedUserId(), 
					dt_data_esito_singolo_pagamento_da, dt_data_esito_singolo_pagamento_a,
					cruscottoIncassiInput.getListaIUD(), 
					cruscottoIncassiInput.getIuv(),
					cruscottoIncassiInput.getDenominazioneAttestante(),
					cruscottoIncassiInput.getIdentificativoUnivocoRiscossione(),
					cruscottoIncassiInput.getCodiceIdentificativoUnivocoPagatore(),
					cruscottoIncassiInput.getAnagraficaPagatore(),
					cruscottoIncassiInput.getCodiceIdentificativoUnivocoVersante(),
					cruscottoIncassiInput.getAnagraficaVersante(),
					cruscottoIncassiInput.getCodTipoDovuto());


		}catch(Exception e){
			log.info("DETTAGLIO CRUSCOTTO :: POST :: Utente[codFedUserId: " + utenteTO.getCodFedUserId() + "] :: ERRORE ", e);
			messagesDto.getInformationMessages().add(new DynamicMessageDto("mypivot.statistiche.errore.dettaglio"));
			response.setMessages(messagesDto);
			return response;
		}

		log.debug("DETTAGLIO CRUSCOTTO :: POST :: Fields[codFedUserId: " + utenteTO.getCodFedUserId() + "] :: END");
		response.setPage(flussoRicevutaDtoPage);
		return response;
	}



	private Sort getRicevutaSort() {
//		List<Order> orders = new ArrayList<>();
//		orders.add(new Order(Direction.DESC, "dtEDatiPagDatiSingPagDataEsitoSingoloPagamento"));
////		orders.add(new Order(Direction.ASC, "codRpSilinviarpIdUnivocoVersamento"));
//		orders.add(new Order(Direction.ASC, "codIud"));
//		return Sort.by(orders);
//	}
	
	List<Order> orders = new ArrayList<>();
	orders.add(new Order(Direction.DESC, "dtEDatiPagDatiSingPagDataEsitoSingoloPagamento"));
	//orders.add(new Order(Direction.ASC, "codRpSilinviarpIdUnivocoVersamento"));
	orders.add(new Order(Direction.ASC, "codIud"));
	return Sort.by(orders);
	}
	
	
	public void initialize(VisualizzaFlussoRicevutaDto visualizzaFlussoRicevutaCommand) {

		Date data_esito_singolo_pagamento_da = null;

		if (StringUtils.hasText(visualizzaFlussoRicevutaCommand.getDataEsitoSingoloPagamentoDa())) {
			try {
				data_esito_singolo_pagamento_da = Constants.DDMMYYYY
						.parse(visualizzaFlussoRicevutaCommand.getDataEsitoSingoloPagamentoDa());
			} catch (ParseException e) {
				// Nothing to do
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		Date data_esito_singolo_pagamento_a = null;

		if (StringUtils.hasText(visualizzaFlussoRicevutaCommand.getDataEsitoSingoloPagamentoA())) {
			try {
				data_esito_singolo_pagamento_a = Constants.DDMMYYYY
						.parse(visualizzaFlussoRicevutaCommand.getDataEsitoSingoloPagamentoA());
			} catch (ParseException e) {
				// Nothing to do
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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

		visualizzaFlussoRicevutaCommand
				.setDataEsitoSingoloPagamentoDa(Constants.DDMMYYYY.format(data_esito_singolo_pagamento_da));
		visualizzaFlussoRicevutaCommand
				.setDataEsitoSingoloPagamentoA(Constants.DDMMYYYY.format(data_esito_singolo_pagamento_a));
	}

}