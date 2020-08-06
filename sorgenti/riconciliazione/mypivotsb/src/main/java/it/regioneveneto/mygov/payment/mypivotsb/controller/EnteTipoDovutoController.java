package it.regioneveneto.mygov.payment.mypivotsb.controller;


import javax.persistence.EntityNotFoundException;

import org.apache.el.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.DynamicMessageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.EnteTipoDovutoDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.MessagesDto;
import it.regioneveneto.mygov.payment.mypivot.domain.po.EnteTipoDovuto;
import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO;
import it.regioneveneto.mygov.payment.mypivot.service.EnteService;
import it.regioneveneto.mygov.payment.mypivot.service.EnteTipoDovutoService;
import it.regioneveneto.mygov.payment.mypivotsb.constant.ApiDescription;
import it.regioneveneto.mygov.payment.mypivotsb.constant.Response;
import it.regioneveneto.mygov.payment.mypivotsb.mapper.EnteTipoDovutoMapper;
import it.regioneveneto.mygov.payment.mypivotsb.response.IoResponse;
import it.regioneveneto.mygov.payment.mypivotsb.response.PageDtoResponse;
import it.regioneveneto.mygov.payment.mypivotsb.response.ResponseIF;
import it.regioneveneto.mygov.payment.mypivotsb.response.SingleDataResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api(tags = { "Tipi Dovuto" })
public class EnteTipoDovutoController extends BaseController {
	
	@Autowired
	private EnteTipoDovutoService enteTipoDovutoService;
	
	@Autowired
	private EnteService enteService;
	
	@Autowired
	private EnteTipoDovutoMapper enteTipoDovutoMapper ;

	@Value("${myPivot.enableGlobalProfile}")
	private Boolean enableGlobalProfile;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation(value = "get all Tipi Dovuto", notes = ApiDescription.DESCR_GET_ALL_ENTE_TIPO_DOVUTO, response = PageDtoResponse.class)
	@GetMapping(value = "/enteTipoDovuto")
	public ResponseIF elencoEnteTipiDovuto(
			Pageable pageable,
			@RequestParam(required = true) String codIpa
			) throws ParseException {		
		try {
			log.info("get lista Tipi Dovuto");
			
			EnteTO enteTO = enteService.getByCodIpaEnte(codIpa);			
			if (enteTO == null) {
				SingleDataResponse<?> response = new SingleDataResponse();
				MessagesDto messagesDto = new MessagesDto();
				messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.codIpaEnteNonValido"));
				response.setMessages(messagesDto);
				return response;
			}
			
			Integer page = pageable.getPageNumber();
			Integer pgSize = pageable.getPageSize();
			
			int pageToGet = 0;
			if (page > 0) {
				pageToGet = page - 1;
			}
			
			Pageable pageable_new = PageRequest.of(pageToGet, pgSize);
			
			return new PageDtoResponse(enteTipoDovutoService.getByCodIpaEntePage(pageable_new,codIpa));
		} catch (EntityNotFoundException entityNotFoundException) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, entityNotFoundException.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, String.format(Response.RESPONSE_KO, e.getMessage()));
		}
	}

	
	@ApiOperation(value = "get single Tipo Dovuto", notes = ApiDescription.DESCR_GET_SINGLE_ENTE_TIPO_DOVUTO, response = SingleDataResponse.class)
	@GetMapping(value = "/enteTipoDovuto/{idEnteTipoDovuto}")
	public ResponseIF getEnteTipoDovuto(@PathVariable("idEnteTipoDovuto") long idEnteTipoDovuto) throws ParseException {		
		try {
			log.info("idEnteTipoDovuto = "+ idEnteTipoDovuto);
			return new SingleDataResponse<>(enteTipoDovutoService.getById(idEnteTipoDovuto));
		} catch (EntityNotFoundException entityNotFoundException) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, entityNotFoundException.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, String.format(Response.RESPONSE_KO, e.getMessage()));
		}				
	}

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation(value = "create Tipo dovuto", notes = ApiDescription.DESCR_CREATE_ENTE_TIPO_DOVUTO, response = SingleDataResponse.class)
	@PostMapping(value = "/enteTipoDovuto")
	public ResponseIF createEnteTipoDovuto(
			@RequestBody EnteTipoDovutoDto enteTipoDovutoDto
			) throws ParseException {		
		try {
			log.info("create Tipo Dovuto");
			
			EnteTO enteTO = enteService.getByCodIpaEnte(enteTipoDovutoDto.getEnte().getCodIpaEnte());			
			if (enteTO == null) {
				SingleDataResponse<?> response = new SingleDataResponse();
				MessagesDto messagesDto = new MessagesDto();
				messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.codIpaEnteNonValido"));
				response.setMessages(messagesDto);
				return response;
			}
				
			return new SingleDataResponse(enteTipoDovutoMapper.convertToDTO(this.enteTipoDovutoService.createEnteTipoDovuto(enteTipoDovutoDto,enableGlobalProfile)));
			
		} catch (EntityNotFoundException entityNotFoundException) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, entityNotFoundException.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, String.format(Response.RESPONSE_KO, e.getMessage()));
		}
	}
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation(value = "update Tipo dovuto", notes = ApiDescription.DESCR_UPDATE_ENTE_TIPO_DOVUTO, response = SingleDataResponse.class)
	@PatchMapping(value = "/enteTipoDovuto")
	public ResponseIF updateEnteTipoDovuto(
			@RequestBody EnteTipoDovutoDto enteTipoDovutoDto
			) throws ParseException {		
		try {
			log.info("update Tipo Dovuto");
			
			EnteTO enteTO = enteService.getByCodIpaEnte(enteTipoDovutoDto.getEnte().getCodIpaEnte());			
			if (enteTO == null) {
				SingleDataResponse<?> response = new SingleDataResponse();
				MessagesDto messagesDto = new MessagesDto();
				messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.codIpaEnteNonValido"));
				response.setMessages(messagesDto);
				return response;
			}
			
			EnteTipoDovuto item = enteTipoDovutoService.getByCodIpaEnteAndCodTipoDovuto(enteTO.getCodIpaEnte(), enteTipoDovutoDto.getCodTipo());
			if (item == null) {			
				SingleDataResponse<?> response = new SingleDataResponse();
				MessagesDto messagesDto = new MessagesDto();
				messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.codTipoDovutoNonValido"));
				response.setMessages(messagesDto);
				return response;
			}
			
			return new SingleDataResponse(enteTipoDovutoMapper.convertToDTO(this.enteTipoDovutoService.updateEnteTipoDovuto(enteTipoDovutoDto)));
			
		} catch (EntityNotFoundException entityNotFoundException) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, entityNotFoundException.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, String.format(Response.RESPONSE_KO, e.getMessage()));
		}
	}

	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation(value = "delete Tipo Dovuto", notes = ApiDescription.DESCR_DELETE_ENTE_TIPO_DOVUTO, response = IoResponse.class)
	@DeleteMapping(value = "/enteTipoDovuto")
	public ResponseIF deleteEnteTipoDovuto(
			@RequestParam(required = true) long id
			) throws ParseException {		
		try {
			log.info("delete Tipo Dovuto");
			
			this.enteTipoDovutoService.deleteEnteTipoDovuto(id);
			return new IoResponse("OK", "TipoDovuto deleted");
			
		} catch (EntityNotFoundException entityNotFoundException) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, entityNotFoundException.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, String.format(Response.RESPONSE_KO, e.getMessage()));
		}
	}
	
}