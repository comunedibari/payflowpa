package it.regioneveneto.mygov.payment.mypivotsb.controller;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.apache.el.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.DynamicMessageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.MessagesDto;
import it.regioneveneto.mygov.payment.mypivot.domain.po.EnteTipoDovuto;
import it.regioneveneto.mygov.payment.mypivot.domain.to.AnagraficaUfficioCapitoloAccertamentoTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO;
import it.regioneveneto.mygov.payment.mypivot.service.AnagraficaUfficioCapitoloAccertamentoService;
import it.regioneveneto.mygov.payment.mypivot.service.EnteService;
import it.regioneveneto.mygov.payment.mypivot.service.EnteTipoDovutoService;
import it.regioneveneto.mygov.payment.mypivot.service.UtilityService;
import it.regioneveneto.mygov.payment.mypivotsb.constant.ApiDescription;
import it.regioneveneto.mygov.payment.mypivotsb.constant.Response;
import it.regioneveneto.mygov.payment.mypivotsb.response.MultipleDataResponse;
import it.regioneveneto.mygov.payment.mypivotsb.response.ResponseIF;
import it.regioneveneto.mygov.payment.mypivotsb.response.SingleDataResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/api/utilities", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api(tags = { "Utility" })
public class UtilityController extends BaseController {
	
	@Autowired
	private EnteService enteService;
	
	@Autowired
	private EnteTipoDovutoService enteTipoDovutoService;
	
	@Autowired
	private UtilityService utilityService; 

	@Autowired
	private AnagraficaUfficioCapitoloAccertamentoService anagraficaUfficioCapitoloAccertamentoService;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation(value = "get all classificazioni", notes = ApiDescription.DESCR_GET_ALL_CLASSIFICAZIONI, response = MultipleDataResponse.class)
	@GetMapping(value = "/classificazioni")
	public ResponseIF elencoClassificazioni(
			@RequestParam(required = true) String codIpa,
			@RequestParam(required = true) String tipoVisualizzazione
			
			) throws ParseException {		
		try {
			log.info("get lista Classificazioni");
			EnteTO enteTO = enteService.getByCodIpaEnte(codIpa);
			
			if (enteTO == null) {
				SingleDataResponse response = new SingleDataResponse();
				MessagesDto messagesDto = new MessagesDto();
				messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.codIpaEnteNonValido"));
				response.setMessages(messagesDto);
				return response;
			}
			
			return new MultipleDataResponse(utilityService.getAllClassificazioni(enteTO.getFlgTesoreria(),enteTO.getFlgPagati(),tipoVisualizzazione));
			
		} catch (EntityNotFoundException entityNotFoundException) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, entityNotFoundException.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, String.format(Response.RESPONSE_KO, e.getMessage()));
		}
	}

	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation(value = "get all stati accertamento", notes = ApiDescription.DESCR_GET_ALL_STATO_ACCERTAMENTO, response = MultipleDataResponse.class)
	@GetMapping(value = "/statiAccertamento")
	public ResponseIF getStatiAccertamento(
			) throws ParseException {		
		try {
			log.info("get all stati accertamento");
		
			return new MultipleDataResponse(utilityService.getStatiAccertamento());
			
		} catch (EntityNotFoundException entityNotFoundException) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, entityNotFoundException.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, String.format(Response.RESPONSE_KO, e.getMessage()));
		}
	}

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation(value = "get all uffici", notes = ApiDescription.DESCR_GET_ALL_UFFICIO, response = MultipleDataResponse.class)
	@GetMapping(value = "/sceltaUfficioCapitolo")
	public ResponseIF sceltaUfficioCapitolo(
			@RequestParam(required = true) String codIpa,
			
			@RequestParam(required = false) String codTipoDovuto,
			@RequestParam(required = false) Boolean flgAttivo

			
			) throws ParseException {		
		try {
			log.info("get lista uffici");
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
			
			List<String> codTipiDovuto = new ArrayList<String>();
			List<AnagraficaUfficioCapitoloAccertamentoTO> activeUfficioEnteTdAsObj = new ArrayList<AnagraficaUfficioCapitoloAccertamentoTO>();
			
			if (codTipoDovuto != null) {			
				codTipiDovuto.add(codTipoDovuto);
				activeUfficioEnteTdAsObj = anagraficaUfficioCapitoloAccertamentoService.findDistinctUfficiByFilter(enteTO.getId(), codTipiDovuto, flgAttivo);				
			}
			else
				activeUfficioEnteTdAsObj = anagraficaUfficioCapitoloAccertamentoService.findDistinctUfficiByEnteId(enteTO.getId());
			
			return new MultipleDataResponse(activeUfficioEnteTdAsObj);
			
		} catch (EntityNotFoundException entityNotFoundException) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, entityNotFoundException.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, String.format(Response.RESPONSE_KO, e.getMessage()));
		}
	}

	
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation(value = "get all capitoli", notes = ApiDescription.DESCR_GET_ALL_CAPITOLO, response = MultipleDataResponse.class)
	@GetMapping(value = "/sceltaCapitolo")
	public ResponseIF sceltaCapitolo(
			@RequestParam(required = true) String codIpa,
			
			@RequestParam(required = false) String codTipoDovuto,
			@RequestParam(required = false) String codUfficio,
			@RequestParam(required = false) Boolean flgAttivo,
			@RequestParam(required = false) String annoEsercizio

			
			) throws ParseException {		
		try {
			log.info("get lista capitoli");
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
			
			List<AnagraficaUfficioCapitoloAccertamentoTO> lista = new ArrayList<AnagraficaUfficioCapitoloAccertamentoTO>();
			MessagesDto messagesDto = new MessagesDto();
			MultipleDataResponse response = new MultipleDataResponse();
			
			if ( flgAttivo != null || annoEsercizio != null) {
				if (!StringUtils.hasText(codTipoDovuto) || !StringUtils.hasText(codUfficio) || !StringUtils.hasText(annoEsercizio) || flgAttivo == null) {
					messagesDto.getInformationMessages().add(new DynamicMessageDto("mypivot.sceltaCapitolo.errore.errorParameters"));
					response.setMessages(messagesDto);
					return response;
				}
				else
					lista =  anagraficaUfficioCapitoloAccertamentoService.findDistinctCapitoliByFilter(enteTO.getId(), codTipoDovuto,
							codUfficio, annoEsercizio, flgAttivo);
			}
			else if ( codTipoDovuto != null || codUfficio != null) {
				if(codTipoDovuto == null || codUfficio == null) {
					messagesDto.getInformationMessages().add(new DynamicMessageDto("mypivot.sceltaCapitolo.errore.errorParameters"));
					response.setMessages(messagesDto);
					return response;
				}
				else
					lista = anagraficaUfficioCapitoloAccertamentoService.findDistinctCapitoliByEnteDovutoUfficio(enteTO.getId(), codTipoDovuto, codUfficio);
			}
			else
				lista = anagraficaUfficioCapitoloAccertamentoService.findDistinctCapitoliByEnteId(enteTO.getId());
			
			
			return new MultipleDataResponse(lista);
			
		} catch (EntityNotFoundException entityNotFoundException) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, entityNotFoundException.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, String.format(Response.RESPONSE_KO, e.getMessage()));
		}
	}
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation(value = "get all accertamenti capitoli", notes = ApiDescription.DESCR_GET_ALL_ACCERTAMENTO_CAPITOLO, response = MultipleDataResponse.class)
	@GetMapping(value = "/sceltaAccertamentoCapitolo")
	public ResponseIF sceltaAccertamentiCapitolo(
			@RequestParam(required = true) String codIpa,
			@RequestParam(value = "codiceUfficio", required = true) String codUff, 
			@RequestParam(value = "codiceCapitolo", required = true) String codCap,
	
			@RequestParam(required = false) String codTipoDovuto,
			@RequestParam(required = false) Boolean flgAttivo,
			@RequestParam(required = false) String annoEsercizio

			
			
			) throws ParseException {		
		
		List<AnagraficaUfficioCapitoloAccertamentoTO> activeCapitoliEnteTdAsObj = null;
		log.debug("INIZIO RECUPERO ACCERTAMENTI DATO ENTE ID, CODICE UFFICIO E CODICE CAPITOLO, codiceUfficio: " + codUff + " codiceCapitolo: " + codCap);
		MessagesDto messagesDto = new MessagesDto();
		MultipleDataResponse response = new MultipleDataResponse();
		try{
			EnteTO enteTO = enteService.getByCodIpaEnte(codIpa);
			if (enteTO == null) {
				messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.codIpaEnteNonValido"));
				response.setMessages(messagesDto);
				return response;
			}
			
			if (StringUtils.hasText(codTipoDovuto) || StringUtils.hasText(annoEsercizio) || flgAttivo != null) {
				
				if (!StringUtils.hasText(codTipoDovuto) || !StringUtils.hasText(codUff) || !StringUtils.hasText(annoEsercizio) || !StringUtils.hasText(codCap) || flgAttivo == null) {
					messagesDto.getInformationMessages().add(new DynamicMessageDto("mypivot.sceltaAccertamentoCapitolo.errore.errorParameters"));
					response.setMessages(messagesDto);
					return response;
				}
				else
					activeCapitoliEnteTdAsObj = anagraficaUfficioCapitoloAccertamentoService.findDistinctAccertamentiByFilter(
							enteTO.getId(), codTipoDovuto, codUff, annoEsercizio, codCap, flgAttivo);
			}
			else
				activeCapitoliEnteTdAsObj = anagraficaUfficioCapitoloAccertamentoService.findDistinctAccertamentiByCodUffcodCap(enteTO.getId(), codUff, codCap);
			
		} catch (EntityNotFoundException entityNotFoundException) {
			log.error("ERRORE RECUPERO ACCERTAMENTI DATO ENTE ID, CODICE UFFICIO E CODICE CAPITOLO, codiceUfficio: " + codUff + " codiceCapitolo: " + codCap + "EXCEPTION:" + entityNotFoundException);		
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, entityNotFoundException.getMessage());
		} catch (Exception e) {
			log.error("ERRORE RECUPERO ACCERTAMENTI DATO ENTE ID, CODICE UFFICIO E CODICE CAPITOLO, codiceUfficio: " + codUff + " codiceCapitolo: " + codCap + "EXCEPTION:" + e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, String.format(Response.RESPONSE_KO, e.getMessage()));
		}

		log.debug("FINE RECUPERO ACCERTAMENTI DATO ENTE ID, CODICE UFFICIO E CODICE CAPITOLO, codiceUfficio: " + codUff + " codiceCapitolo: " + codCap);
		return new MultipleDataResponse(activeCapitoliEnteTdAsObj);
	}
	
}