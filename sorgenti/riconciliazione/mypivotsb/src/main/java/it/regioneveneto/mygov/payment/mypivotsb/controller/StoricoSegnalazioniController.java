package it.regioneveneto.mygov.payment.mypivotsb.controller;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import it.regioneveneto.mygov.payment.mypivot.controller.exception.MyPivotControllerException;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.DynamicMessageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.MessagesDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.PageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.storicosegnalazioni.SegnalazioneDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.storicosegnalazioni.SegnalazioniFilterDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.storicosegnalazioni.StoricoSegnalazioniDto;
import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.SegnalazioneTO;
import it.regioneveneto.mygov.payment.mypivot.service.EnteService;
import it.regioneveneto.mygov.payment.mypivot.service.SegnalazioneService;
import it.regioneveneto.mygov.payment.mypivot.service.exception.MyPivotServiceException;
import it.regioneveneto.mygov.payment.mypivot.utils.Constants;
import it.regioneveneto.mygov.payment.mypivotsb.constant.ApiDescription;
import it.regioneveneto.mygov.payment.mypivotsb.response.ResponseIF;
import it.regioneveneto.mygov.payment.mypivotsb.response.SingleDataResponse;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping(value = "/api/segnalazioni", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api(tags = { "Storico Segnalazioni" })
public class StoricoSegnalazioniController extends BaseController {
	
	public static final String CALL_FROM_VISUALIZZA = "visualizza";
	public static final String CALL_FROM_DETTAGLIO = "dettaglio";


	@Autowired
	private EnteService enteService;
	

	@Autowired
	private SegnalazioneService segnalazioneService;


	public StoricoSegnalazioniController() {
		super();
	}
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation(value = "get all Segnalazioni", notes = ApiDescription.DESCR_GET_ALL_SEGNALAZIONI, response = SingleDataResponse.class)
	@GetMapping(value = "/visualizzaStoricoSegnalazioni")
	public ResponseIF visualizzaStoricoSegnalazioni(
			Pageable pageable,
			@RequestParam(required = true) String codIpa,
			
			@RequestParam(required = false) String classificazioneCompletezza, 
			@RequestParam(required = false) String codiceIuv, 
			@RequestParam(required = false) Boolean codiceIuvEnabled, 
			@RequestParam(required = false) String codiceIuf, 
			@RequestParam(required = false) Boolean codiceIufEnabled,
			@RequestParam(required = false) String codiceIud,
			@RequestParam(required = false) Boolean codiceIudEnabled,
			@RequestParam(required = false) String codFedUserId,
			@RequestParam(required = false) String dataDa,
			@RequestParam(required = false) String dataA,
			@RequestParam(required = false) String attivi,
			@RequestParam(required = false) String nascosti
			
	) {

		SingleDataResponse response = new SingleDataResponse();
		MessagesDto messagesDto = new MessagesDto();
		
		try {

				EnteTO enteTO = enteService.getByCodIpaEnte(codIpa);					
				if (enteTO == null) {
					messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.codIpaEnteNonValido"));
					response.setMessages(messagesDto);
					return response;
				}
				
				SegnalazioniFilterDto filter = initFilter(classificazioneCompletezza, codiceIuv, codiceIuvEnabled,
						codiceIuf, codiceIufEnabled, codiceIud, codiceIudEnabled, codFedUserId, dataDa, dataA, attivi,
						nascosti, 
						pageable.getPageNumber(), pageable.getPageSize(), pageable.getPageSize());
						//pageNum, prevPageSize, pageSize);
				
				
				Sort segnalazioneSort = getSegnalazioneSort();

				Integer page = pageable.getPageNumber();
				Integer pgSize = pageable.getPageSize();
				
				int pageToGet = 0;
				if (page > 0) {
					pageToGet = page - 1;
				}
				
				PageDto<SegnalazioneTO> segnalazioniPaginate = segnalazioneService
						.findPageByFilter(enteTO.getCodIpaEnte(), filter, 
								PageRequest.of(pageToGet, pgSize, segnalazioneSort));

				StoricoSegnalazioniDto storicoSegnalazioniDto = createStoricoSegnalazioniDto(segnalazioniPaginate);

				response.setItems(storicoSegnalazioniDto);
				
				

		} catch (MyPivotServiceException e) {
			messagesDto.getErrorMessages()
			.add(new DynamicMessageDto("Errore di servizio nel recupero dello storico segnalazioni"));
			response.setMessages(messagesDto);
		} catch (MyPivotControllerException e) {
			messagesDto.getErrorMessages()
			.add(new DynamicMessageDto("Errore di controllo nel recupero dello storico segnalazioni"));
			response.setMessages(messagesDto);			
		} catch (RuntimeException e) {
			messagesDto.getErrorMessages()
			.add(new DynamicMessageDto("Errore"));
			response.setMessages(messagesDto);			
		}
		return response;
	}

	private Sort getSegnalazioneSort() {
		List<Order> orders = new ArrayList<>();
		orders.add(new Order(Direction.DESC, "dtCreazione"));
		orders.add(new Order(Direction.DESC, "id"));
		return Sort.by(orders);
	}
	
	private SegnalazioniFilterDto initFilter(String classificazioneCompletezza, String codiceIuv, Boolean codiceIuvEnabled, String codiceIuf, Boolean codiceIufEnabled,
			String codiceIud, Boolean codiceIudEnabled, String codFedUserId, String dataDa, String dataA, String attivi, String nascosti,
			Integer pageNum, Integer prevPageSize, Integer pageSize) throws MyPivotControllerException {

		log.debug("inizializzando il filtro per il recupero dello storico segnalazioni classificazioneCompletezza["
				+ classificazioneCompletezza + "], codiceIuv[" + codiceIuv + "], codiceIuf[" + codiceIuf
				+ "], codiceIud[" + codiceIud + "], codFedUserId[" + codFedUserId + "], dataDa[" + dataDa + "], dataA["
				+ dataA + "], attivi[" + attivi + "], nascosti[" + nascosti + "], pageNum[" + pageNum + "], pageSize["
				+ pageSize + "]");
		validaParametri(classificazioneCompletezza, codiceIuv, codiceIuf, codiceIud, codFedUserId, dataDa, dataA,
				attivi, nascosti, pageNum, prevPageSize, pageSize);

		int pageNumInt = (pageNum != null && (pageSize == null || pageSize.equals(prevPageSize))) ? pageNum : 1;
		int pageSizeInt = (pageSize != null) ? pageSize : 5;

		Boolean nascostiBool = (StringUtils.isNotBlank(nascosti)) ? Boolean.valueOf(nascosti) : null;
		Boolean attiviBool = (StringUtils.isNotBlank(attivi)) ? Boolean.valueOf(attivi) : null;

		SegnalazioniFilterDto filter = new SegnalazioniFilterDto(pageNumInt, pageSizeInt);
		filter.setClassificazioneCompletezza(classificazioneCompletezza);
		filter.setCodiceIuv(codiceIuv);
		filter.setCodiceIuf(codiceIuf);
		filter.setCodiceIud(codiceIud);
		filter.setCodFedUserId(codFedUserId);
		filter.setDataDa(dataDa);
		filter.setDataA(dataA);
		filter.setAttivi(attiviBool);
		filter.setNascosti(nascostiBool);
		filter.setCodiceIuvEnabled(Boolean.TRUE.equals(codiceIuvEnabled));
		filter.setCodiceIufEnabled(Boolean.TRUE.equals(codiceIufEnabled));
		filter.setCodiceIudEnabled(Boolean.TRUE.equals(codiceIudEnabled));
		return filter;
	}

	private static StoricoSegnalazioniDto createStoricoSegnalazioniDto(
			final PageDto<SegnalazioneTO> segnalazioniPaginate) {
		List<SegnalazioneTO> listaSegnalazioni = segnalazioniPaginate.getList();

		List<SegnalazioneDto> storicoSegnalazioni = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(listaSegnalazioni)) {
			for (SegnalazioneTO segnalazioneTO : listaSegnalazioni) {
				SegnalazioneDto segnalazione = new SegnalazioneDto();
//				if (segnalazioneTO.getUtente() != null) {
//					segnalazione.setDeFirstname(segnalazioneTO.getUtente().getDeFirstname());
//					segnalazione.setDeLastname(segnalazioneTO.getUtente().getDeLastname());
//				} else {
//					log.warn("la segnalazione id[" + segnalazioneTO.getId() + "] non ha l'utente associato");
//				}
				segnalazione.setId(segnalazioneTO.getId());
				segnalazione.setCodIuv(segnalazioneTO.getCodIuv());
				segnalazione.setDtUltimaModifica(segnalazioneTO.getDtUltimaModifica());
				segnalazione.setFlgAttivo(segnalazioneTO.getFlgAttivo());
				segnalazione.setClassificazioneCompletezza(segnalazioneTO.getClassificazioneCompletezza());
				segnalazione.setFlgNascosto(segnalazioneTO.getFlgNascosto());
				segnalazione.setCodIuf(segnalazioneTO.getCodIuf());
				segnalazione.setCodIud(segnalazioneTO.getCodIud());
				segnalazione.setDtCreazione(segnalazioneTO.getDtCreazione());
				segnalazione.setDeNota(segnalazioneTO.getDeNota());
				storicoSegnalazioni.add(segnalazione);
			}
		}

		int currPage = segnalazioniPaginate.getPage();
		int totPage = segnalazioniPaginate.getTotalPages();
		int maxElmPerPagina = segnalazioniPaginate.getPageSize();
		StoricoSegnalazioniDto storicoSegnalazioniDto = StoricoSegnalazioniDto
				.getInstanceByCurrPageAndTotPageAndsegnalazioni(currPage, totPage, maxElmPerPagina,
						storicoSegnalazioni);
		return storicoSegnalazioniDto;
	}


	private void validaParametri(String classificazioneCompletezza, String codiceIuv, String codiceIuf,
			String codiceIud, String codFedUserId, String dataDa, String dataA, String attivi, String nascosti,
			Integer pageNum, Integer prevPageSize, Integer pageSize) throws MyPivotControllerException {
		String errMsg = null;
		if (StringUtils.isNotBlank(dataA)) {
			try {
				Constants.DDMMYY.parse(dataA);
			} catch (ParseException e) {
				errMsg = "La Data A deve essere una data nel formato DD/MM/YY";
			}
		} else if (StringUtils.isNotBlank(dataDa)) {
			try {
				Constants.DDMMYY.parse(dataDa);
			} catch (ParseException e) {
				errMsg = "La Data Da deve essere una data nel formato DD/MM/YY";
			}
		} else if (StringUtils.isNotBlank(nascosti) && !(nascosti.equalsIgnoreCase(Boolean.TRUE.toString())
				|| nascosti.equalsIgnoreCase(Boolean.FALSE.toString()))) {
			errMsg = "Il flag nascosti deve essere un valore booleano";
		} else if (StringUtils.isNotBlank(attivi) && !(attivi.equalsIgnoreCase(Boolean.TRUE.toString())
				|| attivi.equalsIgnoreCase(Boolean.FALSE.toString()))) {
			errMsg = "Il flag attivi deve essere un valore booleano";
		}

		if (errMsg != null) {
			throw new MyPivotControllerException(errMsg);
		}
	}
	
}