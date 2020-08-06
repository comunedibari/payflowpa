package it.regioneveneto.mygov.payment.mypivotsb.controller;


import javax.persistence.EntityNotFoundException;

import org.apache.el.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
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
import it.regioneveneto.mygov.payment.mypivot.domain.dto.EnteDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.MessagesDto;
import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO;
import it.regioneveneto.mygov.payment.mypivot.service.EnteService;
import it.regioneveneto.mygov.payment.mypivotsb.constant.ApiDescription;
import it.regioneveneto.mygov.payment.mypivotsb.constant.Response;
import it.regioneveneto.mygov.payment.mypivotsb.mapper.EnteMapper;
import it.regioneveneto.mygov.payment.mypivotsb.response.IoResponse;
import it.regioneveneto.mygov.payment.mypivotsb.response.PageDtoResponse;
import it.regioneveneto.mygov.payment.mypivotsb.response.ResponseIF;
import it.regioneveneto.mygov.payment.mypivotsb.response.SingleDataResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api(tags = { "Enti" })
public class EnteController extends BaseController {
	
	@Autowired
	private EnteService enteService;
	
	@Autowired
	private EnteMapper enteMapper; 
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation(value = "get all Enti", notes = ApiDescription.DESCR_GET_ALL_ENTE, response = PageDtoResponse.class)
	@GetMapping(value = "/ente")
	public ResponseIF elencoEnti(Pageable pageable) throws ParseException {		
		try {
			log.info("get lista Enti");
			
			Integer page = pageable.getPageNumber();
			Integer pgSize = pageable.getPageSize();
			
			int pageToGet = 0;
			if (page > 0) {
				pageToGet = page - 1;
			}
			
			Pageable pageable_new = PageRequest.of(pageToGet, pgSize);
			
			return new PageDtoResponse(enteService.getEnteListPage(pageable_new));
		} catch (EntityNotFoundException entityNotFoundException) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, entityNotFoundException.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, String.format(Response.RESPONSE_KO, e.getMessage()));
		}
	}
	
	
	@ApiOperation(value = "get single Ente", notes = ApiDescription.DESCR_GET_SINGLE_ENTE, response = SingleDataResponse.class)
	@GetMapping(value = "/ente/{codIpa}")
	public ResponseIF getEnte(@PathVariable("codIpa") String codIpa) throws ParseException {		
		try {
			log.info("codIpa = "+ codIpa);
			//return new SingleDataResponse<>(enteService.getByCodIpaEnte(codIpa));
			EnteTO ente = enteService.getByCodIpaEnte(codIpa);
			if (ente == null) {
				SingleDataResponse<?> response = new SingleDataResponse();
				MessagesDto messagesDto = new MessagesDto();
				messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.codIpaEnteNonValido"));
				response.setMessages(messagesDto);
				return response;
			}
			
			return new SingleDataResponse<>(enteMapper.convertToDTO(enteService.getByCodIpaEnte(codIpa)));			
		} catch (EntityNotFoundException entityNotFoundException) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, entityNotFoundException.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, String.format(Response.RESPONSE_KO, e.getMessage()));
		}				
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation(value = "create Ente", notes = ApiDescription.DESCR_CREATE_ENTE, response = SingleDataResponse.class)
	@PostMapping(value = "/ente")
	public ResponseIF createEnte(
			@RequestBody EnteDto enteDto
			) throws ParseException {		
		try {
			log.info("create Ente");
					
			return new SingleDataResponse(enteMapper.convertToDTO(this.enteService.createEnte(enteDto)));
			
		} catch (EntityNotFoundException entityNotFoundException) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, entityNotFoundException.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, String.format(Response.RESPONSE_KO, e.getMessage()));
		}
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation(value = "update Ente", notes = ApiDescription.DESCR_UPDATE_ENTE, response = SingleDataResponse.class)
	@PatchMapping(value = "/ente")
	public ResponseIF updateEnte(
			@RequestBody EnteDto enteDto
			) throws ParseException {		
		try {
			log.info("update Ente");
			
			EnteTO ente = enteService.getByCodIpaEnte(enteDto.getCodIpa());
			if (ente == null) {
				SingleDataResponse<?> response = new SingleDataResponse();
				MessagesDto messagesDto = new MessagesDto();
				messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.codIpaEnteNonValido"));
				response.setMessages(messagesDto);
				return response;
			}
			return new SingleDataResponse(enteMapper.convertToDTO(this.enteService.updateEnte(enteDto)));
			
		} catch (EntityNotFoundException entityNotFoundException) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, entityNotFoundException.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, String.format(Response.RESPONSE_KO, e.getMessage()));
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation(value = "delete Ente", notes = ApiDescription.DESCR_DELETE_ENTE, response = IoResponse.class)
	@DeleteMapping(value = "/ente")
	public ResponseIF deleteEnte(
			@RequestParam(required = true) String codIpa
			) throws ParseException {		
		try {
			log.info("delete Ente");
			
			EnteTO ente = enteService.getByCodIpaEnte(codIpa);
			if (ente == null) {
				SingleDataResponse<?> response = new SingleDataResponse();
				MessagesDto messagesDto = new MessagesDto();
				messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.codIpaEnteNonValido"));
				response.setMessages(messagesDto);
				return response;
			}
			this.enteService.deleteEnte(codIpa);
			return new IoResponse("OK", "Ente deleted");
			
		} catch (EntityNotFoundException entityNotFoundException) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, entityNotFoundException.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, String.format(Response.RESPONSE_KO, e.getMessage()));
		}
	}

}