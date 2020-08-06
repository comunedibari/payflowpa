package it.regioneveneto.mygov.payment.mypivotsb.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import it.regioneveneto.mygov.payment.mypivot.controller.command.VisualizzaFlussoRendicontazioneCommand;
import it.regioneveneto.mygov.payment.mypivot.controller.command.VisualizzaFlussoRicevutaCommand;
import it.regioneveneto.mygov.payment.mypivot.controller.command.VisualizzaFlussoTesoreriaCommand;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.DynamicMessageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.FlussoRendicontazioneDistinctDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.FlussoRicevutaDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.FlussoTesoreriaDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.FlussoTesoreriaEspansoDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.MessagesDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.PageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.bilancio.BilancioContainerDto;
import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoRendicontazioneTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoTesoreriaTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.UtenteTO;
import it.regioneveneto.mygov.payment.mypivot.service.EnteService;
import it.regioneveneto.mygov.payment.mypivot.service.FlussoExportService;
import it.regioneveneto.mygov.payment.mypivot.service.FlussoRendicontazioneService;
import it.regioneveneto.mygov.payment.mypivot.service.FlussoTesoreriaService;
import it.regioneveneto.mygov.payment.mypivot.service.UtenteService;
import it.regioneveneto.mygov.payment.mypivot.service.exception.RtNonInRendicontazioneException;
import it.regioneveneto.mygov.payment.mypivot.utils.Constants;
import it.regioneveneto.mygov.payment.mypivot.utils.Utilities;
import it.regioneveneto.mygov.payment.mypivotsb.constant.ApiDescription;
import it.regioneveneto.mygov.payment.mypivotsb.controller.visualizzazioneFlussi.filter.FlussiRendicontazioneViewFilter;
import it.regioneveneto.mygov.payment.mypivotsb.controller.visualizzazioneFlussi.filter.FlussiRicevutaViewFilter;
import it.regioneveneto.mygov.payment.mypivotsb.controller.visualizzazioneFlussi.filter.FlussiTesoreriaViewFilter;
import it.regioneveneto.mygov.payment.mypivotsb.response.PageDtoResponse;
import it.regioneveneto.mygov.payment.mypivotsb.response.ResponseIF;
import it.regioneveneto.mygov.payment.mypivotsb.response.SingleDataResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/api/flussi", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api(tags = { "Flussi" })
public class VisualizzaFlussiController extends BaseController {
	

	@Autowired
	private FlussoTesoreriaService flussoTesoreriaService;

	@Autowired
	private FlussoRendicontazioneService flussoRendicontazioneService;

	@Autowired
	private FlussoExportService flussoExportService;
	
	@Autowired
	private EnteService enteService;
	
	@Value("${myPivot.enableGlobalProfile}")
	private Boolean enableGlobalProfile;
	
	@Autowired
	private UtenteService utenteService;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation(value = "get Ricevuta Telematiche", notes = ApiDescription.DESCR_GET_ALL_RT, response = PageDtoResponse.class)
	@GetMapping(value = "/visualizzaRicevute")
	public ResponseIF visualizzaRicevute(		
		Pageable pageable,
		@RequestParam(required = true) String codIpa,
		
		//@RequestParam(required = false) String pg,
		//@RequestParam(required = false) String pgSize,
		
		Boolean dataEsitoSingoloPagamentoCheck,
		@RequestParam(required = false) String dataEsitoSingoloPagamentoDa,
		@RequestParam(required = false) String dataEsitoSingoloPagamentoA,
		@RequestParam(required = false) String iud, 
		@RequestParam(required = false) String iuv,
		@RequestParam(required = false) String denominazioneAttestante,
		@RequestParam(required = false) String identificativoUnivocoRiscossione,
		@RequestParam(required = false) String codiceIdentificativoUnivocoPagatore,
		@RequestParam(required = false) String anagraficaPagatore,
		@RequestParam(required = false) String codiceIdentificativoUnivocoVersante,
		@RequestParam(required = false) String anagraficaVersante,
		@RequestParam(required = false) String codTipoDovuto, 
		@RequestParam(required = false) String iuf
		) {
		
	log.info("Chiamata al controller GET getVisualizzaRicevuta");

	PageDtoResponse<?> response = new PageDtoResponse();

	EnteTO enteTO = enteService.getByCodIpaEnte(codIpa);			
	if (enteTO == null) {
		MessagesDto messagesDto = new MessagesDto();
		messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.codIpaEnteNonValido"));
		response.setMessages(messagesDto);
		return response;
	}
	
	FlussiRicevutaViewFilter flussiRicevutaViewFilter = new FlussiRicevutaViewFilter();

	VisualizzaFlussoRicevutaCommand visualizzaFlussoRicevutaCommand = new VisualizzaFlussoRicevutaCommand();
	MessagesDto messagesDto = new MessagesDto();
	
	UtenteTO utenteTO = null;
	
	if(enableGlobalProfile) 
		utenteTO = utenteService.getUtenteByGlobalDefault();	
	
	flussiRicevutaViewFilter.setFilters(Integer.toString(pageable.getPageNumber()), Integer.toString(pageable.getPageSize()), dataEsitoSingoloPagamentoCheck,
			dataEsitoSingoloPagamentoDa, dataEsitoSingoloPagamentoA, iud, iuv, denominazioneAttestante,
			identificativoUnivocoRiscossione, codiceIdentificativoUnivocoPagatore, anagraficaPagatore,
			codiceIdentificativoUnivocoVersante, anagraficaVersante, codTipoDovuto, iuf,
			visualizzaFlussoRicevutaCommand);

	flussiRicevutaViewFilter.initialize(visualizzaFlussoRicevutaCommand);

	Date dt_data_esito_singolo_pagamento_da = null;

	if (visualizzaFlussoRicevutaCommand.getDataEsitoSingoloPagamentoCheck() != null
			&& visualizzaFlussoRicevutaCommand.getDataEsitoSingoloPagamentoCheck()
			&& StringUtils.isNotBlank(visualizzaFlussoRicevutaCommand.getDataEsitoSingoloPagamentoDa())) {
		try {
			dt_data_esito_singolo_pagamento_da = Constants.DDMMYYYY
					.parse(visualizzaFlussoRicevutaCommand.getDataEsitoSingoloPagamentoDa());
		} catch (ParseException e) {
			// Nothing to do
		}
	}
	

	Date dt_data_esito_singolo_pagamento_a = null;

	if (visualizzaFlussoRicevutaCommand.getDataEsitoSingoloPagamentoCheck() != null
			&& visualizzaFlussoRicevutaCommand.getDataEsitoSingoloPagamentoCheck()
			&& StringUtils.isNotBlank(visualizzaFlussoRicevutaCommand.getDataEsitoSingoloPagamentoA())) {
		try {
			dt_data_esito_singolo_pagamento_a = Constants.DDMMYYYY
					.parse(visualizzaFlussoRicevutaCommand.getDataEsitoSingoloPagamentoA());
		} catch (ParseException e) {
			// Nothing to do
		}
	}

	PageDto<FlussoRicevutaDto> flussoRicevutaDtoPage = null;

	if (StringUtils.isNotBlank(iuf)) {
		try {
			
			flussoRicevutaDtoPage = flussoRendicontazioneService.getFlussoRendicontazionePageByIUF(codIpa,
					utenteTO.getCodFedUserId(),
					dt_data_esito_singolo_pagamento_da,
					dt_data_esito_singolo_pagamento_a, visualizzaFlussoRicevutaCommand.getIud(),
					visualizzaFlussoRicevutaCommand.getIuv(),
					visualizzaFlussoRicevutaCommand.getDenominazioneAttestante(),
					visualizzaFlussoRicevutaCommand.getIdentificativoUnivocoRiscossione(),
					visualizzaFlussoRicevutaCommand.getCodiceIdentificativoUnivocoPagatore(),
					visualizzaFlussoRicevutaCommand.getAnagraficaPagatore(),
					visualizzaFlussoRicevutaCommand.getCodiceIdentificativoUnivocoVersante(),
					visualizzaFlussoRicevutaCommand.getAnagraficaVersante(),
					visualizzaFlussoRicevutaCommand.getCodTipoDovuto(), visualizzaFlussoRicevutaCommand.getIuf(),
					PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), getRicevutaSort()));

			
//			FlussoRendicontazioneDto flussoRendicontazioneDto = flussoRendicontazioneService
//					.getFlussoRendicontazioneDto(codIpa, visualizzaFlussoRicevutaCommand.getIuf());
//			
//			return new SingleDataResponse<>(flussoRendicontazioneDto);

		} catch (RtNonInRendicontazioneException e) {
			//log.error(e);
			messagesDto = new MessagesDto();
			messagesDto.getErrorMessages()
					.add(new DynamicMessageDto("mypivot.messages.error.iudNonInRendicontazione"));
			response.setMessages(messagesDto);
		}
	} else {
		
		Integer page = pageable.getPageNumber();
		Integer pgSize = pageable.getPageSize();
		
		int pageToGet = 0;
		if (page > 0) {
			pageToGet = page - 1;
		}
		flussoRicevutaDtoPage = flussoExportService.getFlussoExportPage(PageRequest.of(pageToGet, pgSize,
				getRicevuteSort()),
				codIpa,
				utenteTO.getCodFedUserId(),
				dt_data_esito_singolo_pagamento_da, dt_data_esito_singolo_pagamento_a,
				visualizzaFlussoRicevutaCommand.getIud(), visualizzaFlussoRicevutaCommand.getIuv(),
				visualizzaFlussoRicevutaCommand.getDenominazioneAttestante(),
				visualizzaFlussoRicevutaCommand.getIdentificativoUnivocoRiscossione(),
				visualizzaFlussoRicevutaCommand.getCodiceIdentificativoUnivocoPagatore(),
				visualizzaFlussoRicevutaCommand.getAnagraficaPagatore(),
				visualizzaFlussoRicevutaCommand.getCodiceIdentificativoUnivocoVersante(),
				visualizzaFlussoRicevutaCommand.getAnagraficaVersante(),
				visualizzaFlussoRicevutaCommand.getCodTipoDovuto());
	}

	response.setPage(flussoRicevutaDtoPage);
	response.setMessages(messagesDto);
	return response;	
	}

	
	private Sort getRicevuteSort() {
		List<Order> orders = new ArrayList<>();
		orders.add(new Order(Direction.DESC, "dtEDatiPagDatiSingPagDataEsitoSingoloPagamento"));
		//orders.add(new Order(Direction.ASC, "codRpSilinviarpIdUnivocoVersamento"));
		orders.add(new Order(Direction.ASC, "codIud"));
		return Sort.by(orders);
	}
	
	
	private Sort getRicevutaSort() {
		List<Order> orders = new ArrayList<>();
		orders.add(new Order(Direction.DESC, "id.codDatiSingPagamIdentificativoUnivocoVersamento"));
		orders.add(new Order(Direction.ASC, "id.indiceDatiSingoloPagamento"));
		return Sort.by(orders);
	}

	
	private Sort getTesoreriaSort() {
		List<Order> orders = new ArrayList<>();
		orders.add(new Order(Direction.DESC, "id.deAnnoBolletta"));
		orders.add(new Order(Direction.DESC, "id.codBolletta"));
		return Sort.by(orders);
	}



	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation(value = "get Rendicontazione Pago PA", notes = ApiDescription.DESCR_GET_ALL_RENDICONTAZIONE, response = PageDtoResponse.class)
	@GetMapping(value = "/visualizzaRendicontazione")
	public ResponseIF getVisualizzaRendicontazione(		
		Pageable pageable,
		
		@RequestParam(required = true) String codIpa,
//		@RequestParam(required = false) String pg,
//		@RequestParam(required = false) String pgSize, 
		@RequestParam(required = false) Boolean dataRegolamentoCheck,
		@RequestParam(required = false) String dataRegolamentoDa,
		@RequestParam(required = false) String dataRegolamentoA, 
		@RequestParam(required = false) String iuf,
		@RequestParam(required = false) String identificativoUnivocoRegolamento
		
			) {

		log.info("Chiamata al controller GET getVisualizzaRendicontazione");

		EnteTO enteTO = enteService.getByCodIpaEnte(codIpa);			
		if (enteTO == null) {
			SingleDataResponse<?> response = new SingleDataResponse();
			MessagesDto messagesDto = new MessagesDto();
			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.codIpaEnteNonValido"));
			response.setMessages(messagesDto);
			return response;
		}

		FlussiRendicontazioneViewFilter flussiRendicontazioneViewFilter = new FlussiRendicontazioneViewFilter();

		Integer page = pageable.getPageNumber();
		int pageToGet = 1;
		if (page > 0) {
			pageToGet = page;
		}
		
		VisualizzaFlussoRendicontazioneCommand visualizzaFlussoRendicontazioneCommand = new VisualizzaFlussoRendicontazioneCommand();
		MessagesDto messagesDto = new MessagesDto();
		PageDtoResponse<?> response = new PageDtoResponse();
		
		flussiRendicontazioneViewFilter.setFilters(
				Integer.toString(pageToGet), Integer.toString(pageable.getPageSize()) ,
//				Integer.toString(pageable.getPageNumber()),	Integer.toString(pageable.getPageSize()), 
				dataRegolamentoCheck, dataRegolamentoDa,
				dataRegolamentoA, iuf, identificativoUnivocoRegolamento, visualizzaFlussoRendicontazioneCommand);

		flussiRendicontazioneViewFilter.initialize(visualizzaFlussoRendicontazioneCommand);

		Date dt_data_regolamento_da = null;

		if (visualizzaFlussoRendicontazioneCommand.getDataRegolamentoCheck() != null
				&& visualizzaFlussoRendicontazioneCommand.getDataRegolamentoCheck()
				&& StringUtils.isNotBlank(visualizzaFlussoRendicontazioneCommand.getDataRegolamentoDa())) {
			try {
				dt_data_regolamento_da = Constants.DDMMYYYY
						.parse(visualizzaFlussoRendicontazioneCommand.getDataRegolamentoDa());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date dt_data_regolamento_a = null;

		if (visualizzaFlussoRendicontazioneCommand.getDataRegolamentoCheck() != null
				&& visualizzaFlussoRendicontazioneCommand.getDataRegolamentoCheck()
				&& StringUtils.isNotBlank(visualizzaFlussoRendicontazioneCommand.getDataRegolamentoA())) {
			try {
				dt_data_regolamento_a = Constants.DDMMYYYY
						.parse(visualizzaFlussoRendicontazioneCommand.getDataRegolamentoA());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		PageDto<FlussoRendicontazioneDistinctDto> flussoRendicontazioneDtoPage = flussoRendicontazioneService
				.getFlussoRendicontazionePage(enteTO.getId(), dt_data_regolamento_da, dt_data_regolamento_a,
						visualizzaFlussoRendicontazioneCommand.getIuf(),
						visualizzaFlussoRendicontazioneCommand.getIdentificativoUnivocoRegolamento(),
						visualizzaFlussoRendicontazioneCommand.getPage(),
						visualizzaFlussoRendicontazioneCommand.getPageSize());

		response.setPage(flussoRendicontazioneDtoPage);
		response.setMessages(messagesDto);
		return response;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation(value = "get Giornali di Cassa", notes = ApiDescription.DESCR_GET_ALL_GIORNALE_DI_CASSA, response = PageDtoResponse.class)
	@GetMapping(value = "/visualizzaTesoreria")
	public ResponseIF getVisualizzaTesoreria(		
		Pageable pageable,
		
		@RequestParam(required = true) String codIpa,
	
//		@RequestParam(required = false) String pg,
//		@RequestParam(required = false) String pgSize, 
		
		@RequestParam(required = false) Boolean dataContabileCheck,
		@RequestParam(required = false) String dataContabileDa,
		@RequestParam(required = false) String dataContabileA,
		@RequestParam(required = false) Boolean dataValutaCheck,
		@RequestParam(required = false) String dataValutaDa, @RequestParam(required = false) String dataValutaA,
		@RequestParam(required = false) String deAnnoBolletta, @RequestParam(required = false) String codBolletta,
		@RequestParam(required = false) String importo, @RequestParam(required = false) String conto,
		@RequestParam(required = false) String codOr1, @RequestParam(required = false) String iuv,
		@RequestParam(required = false) String iuf) {
		
		log.info("Chiamata al controller GET getVisualizzaTesoreria");

		EnteTO enteTO = enteService.getByCodIpaEnte(codIpa);			
		if (enteTO == null) {
			SingleDataResponse<?> response = new SingleDataResponse();
			MessagesDto messagesDto = new MessagesDto();
			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.codIpaEnteNonValido"));
			response.setMessages(messagesDto);
			return response;
		}
		
		MessagesDto messagesDto = new MessagesDto();
		PageDtoResponse<?> response = new PageDtoResponse();
		
		if (!enteTO.getFlgTesoreria()) {

			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.tesoreriaNonAbilitata"));
			response.setMessages(messagesDto);
			return response;
		}

		FlussiTesoreriaViewFilter flussiTesoreriaViewFilter = new FlussiTesoreriaViewFilter();

		VisualizzaFlussoTesoreriaCommand visualizzaFlussoTesoreriaCommand = new VisualizzaFlussoTesoreriaCommand();

		flussiTesoreriaViewFilter.setFilters(Integer.toString(pageable.getPageNumber()), Integer.toString(pageable.getPageSize()), dataContabileCheck, dataContabileDa, dataContabileA,
				dataValutaCheck, dataValutaDa, dataValutaA, deAnnoBolletta, codBolletta, importo, conto, codOr1, iuv,
				iuf, visualizzaFlussoTesoreriaCommand);


		flussiTesoreriaViewFilter.initialize(visualizzaFlussoTesoreriaCommand);

		Date dt_data_contabile_da = null;

		if (visualizzaFlussoTesoreriaCommand.getDataContabileCheck() != null
				&& visualizzaFlussoTesoreriaCommand.getDataContabileCheck()
				&& StringUtils.isNotBlank(visualizzaFlussoTesoreriaCommand.getDataContabileDa())) {
			try {
				dt_data_contabile_da = Constants.DDMMYYYY.parse(visualizzaFlussoTesoreriaCommand.getDataContabileDa());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date dt_data_contabile_a = null;

		if (visualizzaFlussoTesoreriaCommand.getDataContabileCheck() != null
				&& visualizzaFlussoTesoreriaCommand.getDataContabileCheck()
				&& StringUtils.isNotBlank(visualizzaFlussoTesoreriaCommand.getDataContabileA())) {
			try {
				dt_data_contabile_a = Constants.DDMMYYYY.parse(visualizzaFlussoTesoreriaCommand.getDataContabileA());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date dt_data_valuta_da = null;

		if (visualizzaFlussoTesoreriaCommand.getDataValutaCheck() != null
				&& visualizzaFlussoTesoreriaCommand.getDataValutaCheck()
				&& StringUtils.isNotBlank(visualizzaFlussoTesoreriaCommand.getDataValutaDa())) {
			try {
				dt_data_valuta_da = Constants.DDMMYYYY.parse(visualizzaFlussoTesoreriaCommand.getDataValutaDa());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date dt_data_valuta_a = null;

		if (visualizzaFlussoTesoreriaCommand.getDataValutaCheck() != null
				&& visualizzaFlussoTesoreriaCommand.getDataValutaCheck()
				&& StringUtils.isNotBlank(visualizzaFlussoTesoreriaCommand.getDataValutaA())) {
			try {
				dt_data_valuta_a = Constants.DDMMYYYY.parse(visualizzaFlussoTesoreriaCommand.getDataValutaA());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Sort tesoreriaSort = getTesoreriaSort();

		Integer page = pageable.getPageNumber();
		Integer pgSize = pageable.getPageSize();
		
		int pageToGet = 0;
		if (page > 0) {
			pageToGet = page - 1;
		}
				
		PageDto<FlussoTesoreriaDto> flussoTesoreriaDtoPage = flussoTesoreriaService.getFlussoTesoreriaPage(
				enteTO.getCodIpaEnte(), dt_data_valuta_da, dt_data_valuta_a, dt_data_contabile_da, dt_data_contabile_a,
				visualizzaFlussoTesoreriaCommand.getDeAnnoBolletta(), visualizzaFlussoTesoreriaCommand.getCodBolletta(),
				visualizzaFlussoTesoreriaCommand.getImporto(), visualizzaFlussoTesoreriaCommand.getConto(),
				visualizzaFlussoTesoreriaCommand.getCodOr1(), visualizzaFlussoTesoreriaCommand.getIuv(),
				visualizzaFlussoTesoreriaCommand.getIuf(), 
//				visualizzaFlussoTesoreriaCommand.getPage(), visualizzaFlussoTesoreriaCommand.getPageSize(), tesoreriaSort
				PageRequest.of(pageToGet, pgSize)
				);

		response.setPage(flussoTesoreriaDtoPage);
		response.setMessages(messagesDto);
		return response;
	}

	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation(value = "espandi Giornale di Cassa", notes = ApiDescription.DESCR_GET_SINGLE_GIORNALE_DI_CASSA, response = SingleDataResponse.class)
	@GetMapping(value = "/espandiTesoreria")
	public ResponseIF getEspandiTesoreria(		
		
		@RequestParam(required = true) String codIpa,
			
		@RequestParam(required = true) String deAnnoBolletta, 
		@RequestParam(required = true) String codBolletta) {
		
		log.info("Chiamata al controller GET getEspandiTesoreria");

		EnteTO enteTO = enteService.getByCodIpaEnte(codIpa);			
		if (enteTO == null) {
			SingleDataResponse<?> response = new SingleDataResponse();
			MessagesDto messagesDto = new MessagesDto();
			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.codIpaEnteNonValido"));
			response.setMessages(messagesDto);
			return response;
		}
		
		MessagesDto messagesDto = new MessagesDto();
		
		if (!enteTO.getFlgTesoreria()) {

			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.tesoreriaNonAbilitata"));
			SingleDataResponse<?> response = new SingleDataResponse();
			response.setMessages(messagesDto);
			return response;
		}
		
		FlussoTesoreriaTO flussoTesoreriaTO = flussoTesoreriaService
				.findByCodIpaEnteDeAnnoBollettaAndCodBolletta(enteTO.getCodIpaEnte(), deAnnoBolletta, codBolletta);
		
		if(flussoTesoreriaTO == null) {

			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.bollettaNonTrovata"));
			SingleDataResponse<?> response = new SingleDataResponse();
			response.setMessages(messagesDto);
			return response;
		}

		
		/* Estrai bilanci */

		boolean hasIUF = Utilities.hasFlussoTesoreriaIUF(flussoTesoreriaTO);
		boolean hasIUV = Utilities.hasFlussoTesoreriaIUV(flussoTesoreriaTO);
		
		BilancioContainerDto container = null;
	
		if (hasIUF) {
			String iuf = flussoTesoreriaTO.getCodIdUnivocoFlusso();
			List<FlussoExportTO> listaFlussiExportTO = getListaFlussiExportTOByCodIpaEnteAndIUF(codIpa, iuf);
			if (listaFlussiExportTO != null && !CollectionUtils.isEmpty(listaFlussiExportTO)) {
			container = flussoTesoreriaService.estraiListaAccertamenti(codIpa,
					listaFlussiExportTO);
			}
		}
		if (hasIUV) {
			String iuv = flussoTesoreriaTO.getCodIdUnivocoVersamento();
			List<FlussoExportTO> listaFlussiExportTO = flussoExportService.findByCodIpaEnteIUV(codIpa, iuv);
			if (listaFlussiExportTO != null && !CollectionUtils.isEmpty(listaFlussiExportTO)) {
			container = flussoTesoreriaService.estraiListaAccertamenti(codIpa,
					listaFlussiExportTO);
			}
		}

		
		
		/* Fine estrazione bilanci */
		
//		FlussoTesoreriaDto flussoTesoreriaDto = new FlussoTesoreriaDto();
//		flussoTesoreriaDto.setCodiceIpaEnte(enteTO.getCodIpaEnte());
//		flussoTesoreriaDto.setDeAnnoBolletta(flussoTesoreriaTO.getDeAnnoBolletta());
//		flussoTesoreriaDto.setCodBolletta(flussoTesoreriaTO.getCodBolletta());
//		flussoTesoreriaDto.setDeImporto(Utilities.parseImportoString(flussoTesoreriaTO.getNumIpBolletta()));
//		flussoTesoreriaDto.setFlussoTesoreriaTO(flussoTesoreriaTO);
//
//		SingleDataResponse<FlussoTesoreriaDto> response = new SingleDataResponse<>(flussoTesoreriaDto);

		FlussoTesoreriaEspansoDto flussoTesoreriaDto = new FlussoTesoreriaEspansoDto();
		flussoTesoreriaDto.setCodiceIpaEnte(enteTO.getCodIpaEnte());
		flussoTesoreriaDto.setDeAnnoBolletta(flussoTesoreriaTO.getDeAnnoBolletta());
		flussoTesoreriaDto.setCodBolletta(flussoTesoreriaTO.getCodBolletta());
		flussoTesoreriaDto.setDeImporto(Utilities.parseImportoString(flussoTesoreriaTO.getNumIpBolletta()));
		flussoTesoreriaDto.setFlussoTesoreriaTO(flussoTesoreriaTO);
		
		flussoTesoreriaDto.setBilancioContainerDto(container);
		
		SingleDataResponse<FlussoTesoreriaEspansoDto> response = new SingleDataResponse<>(flussoTesoreriaDto);	
		
		response.setMessages(messagesDto);
		return response;

	}



	private List<FlussoExportTO> getListaFlussiExportTOByCodIpaEnteAndIUF(String codIpaEnte, String iuf) {
		Assert.hasText(codIpaEnte, "Parametro [ codIpaEnte ] vuoto");
		Assert.hasText(iuf, "Parametro [ iuf ] vuoto");

		List<FlussoRendicontazioneTO> listaFlussiRendicontazioneTO = flussoRendicontazioneService
				.findByCodIpaEnteAndIUF(codIpaEnte, iuf);
		if (listaFlussiRendicontazioneTO != null && !listaFlussiRendicontazioneTO.isEmpty()) {
			List<FlussoExportTO> listaFlussiExportTO = new ArrayList<FlussoExportTO>();
			for (FlussoRendicontazioneTO rend : listaFlussiRendicontazioneTO) {
				String iuv = rend.getId().getCodDatiSingPagamIdentificativoUnivocoVersamento();
				int indiceDatiSingoloPagamento = rend.getId().getIndiceDatiSingoloPagamento();
				FlussoExportTO flussoExportTO = flussoExportService
						.findByCodIpaEnteIUVAndIndiceDatiSingoloPagamento(codIpaEnte, iuv, indiceDatiSingoloPagamento);
				if (flussoExportTO != null) {
					listaFlussiExportTO.add(flussoExportTO);
				}
			}
			return listaFlussiExportTO;
		}
		return null;
	}

		
}