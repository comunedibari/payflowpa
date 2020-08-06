package it.regioneveneto.mygov.payment.mypivotsb.controller;

import java.text.ParseException;

import javax.persistence.EntityNotFoundException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.AggiornaAnagraficaUfficioCapitoloAccertamentoResultDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.AggiornaAnagraficaUfficioCapitoloAccertamentoResultDto.ESITO;
//import it.regioneveneto.mygov.payment.mypivot.controller.validator.AnagraficaUfficioCapitoloAccertamentoValidator;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.AnagraficaUfficioCapitoloAccertamentoDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.DynamicMessageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.MessagesDto;
import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.UtenteTO;
import it.regioneveneto.mygov.payment.mypivot.service.AnagraficaUfficioCapitoloAccertamentoService;
import it.regioneveneto.mygov.payment.mypivot.service.EnteService;
import it.regioneveneto.mygov.payment.mypivot.service.UtenteService;
import it.regioneveneto.mygov.payment.mypivotsb.constant.ApiDescription;
import it.regioneveneto.mygov.payment.mypivotsb.constant.Response;
import it.regioneveneto.mygov.payment.mypivotsb.controller.validator.AnagraficaUfficioCapitoloAccertamentoValidator;
import it.regioneveneto.mygov.payment.mypivotsb.response.ResponseIF;
import it.regioneveneto.mygov.payment.mypivotsb.response.SingleDataResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/api/accertamentiAnagrafiche", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api(tags = { "Flussi" })
public class DettaglioAnagraficaUfficioCapitoloAccertamentoController extends BaseController {


	@Autowired
	private EnteService enteService;

	@Autowired
	private AnagraficaUfficioCapitoloAccertamentoService anagraficaService;

	@Autowired
	private AnagraficaUfficioCapitoloAccertamentoValidator validator;

	@Autowired
	private UtenteService utenteService;

	@Value("${myPivot.enableGlobalProfile}")
	private Boolean enableGlobalProfile;

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation(value = "get single Anagrafica", notes = ApiDescription.DESCR_GET_SINGLE_ANAGRAFICA, response = SingleDataResponse.class)
	@GetMapping(value = "/dettaglioAnagraficaCapitolo")
	public ResponseIF visualizzaDettaglio(

			@RequestParam(required = true) String codIpa,
			@RequestParam(required = false) String id

			) throws ParseException {		
		try {
			log.info("id Anagrafica = "+ id);
			EnteTO enteTO = enteService.getByCodIpaEnte(codIpa);
		
			if (enteTO == null) {
				SingleDataResponse<?> response = new SingleDataResponse();
				MessagesDto messagesDto = new MessagesDto();
				messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.codIpaEnteNonValido"));
				response.setMessages(messagesDto);
				return response;
			}
			AnagraficaUfficioCapitoloAccertamentoDto anagraficaUfficioCapitoloAccertamentoDto = anagraficaService.getAnagraficaById(Long.parseLong(id),enteTO.getId());

			return new SingleDataResponse<>(anagraficaUfficioCapitoloAccertamentoDto);						

		} catch (EntityNotFoundException entityNotFoundException) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, entityNotFoundException.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, String.format(Response.RESPONSE_KO, e.getMessage()));
		}					
	}



	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation(value = "update Anagrafica", notes = ApiDescription.DESCR_UPDATE_ANAGRAFICA, response = SingleDataResponse.class)
	@PatchMapping(value = "/modificaAnagraficaCapitolo")
	public ResponseIF modificaAnagrafica(

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
		
		String codIpa = enteTO.getCodIpaEnte();
		UtenteTO utenteTO = null;
		
		if(enableGlobalProfile) 
			utenteTO = utenteService.getUtenteByGlobalDefault();	
		
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

			String esitoMsg="";

			if (StringUtils.isBlank(anagraficaUfficioCapitoloAccertamentoDto.getCodUfficio())
					|| anagraficaUfficioCapitoloAccertamentoDto.getCodUfficio().equals("n/a")){
				anagraficaUfficioCapitoloAccertamentoDto.setFlgAttivo(true);
				anagraficaUfficioCapitoloAccertamentoDto.setCodUfficio("n/a");
				anagraficaUfficioCapitoloAccertamentoDto.setDeUfficio("n/a");
			} else {
				anagraficaUfficioCapitoloAccertamentoDto.setCodUfficio(anagraficaUfficioCapitoloAccertamentoDto.getCodUfficio());
				if (StringUtils.isBlank(anagraficaUfficioCapitoloAccertamentoDto.getDeUfficio()))
					anagraficaUfficioCapitoloAccertamentoDto.setDeUfficio("n/a");
				else
					anagraficaUfficioCapitoloAccertamentoDto.setDeUfficio(anagraficaUfficioCapitoloAccertamentoDto.getDeUfficio());
			}

			anagraficaUfficioCapitoloAccertamentoDto.setEnte(enteTO);

			if(StringUtils.isBlank(anagraficaUfficioCapitoloAccertamentoDto.getDeAccertamento()))
				anagraficaUfficioCapitoloAccertamentoDto.setDeAccertamento("n/a");
			else
				anagraficaUfficioCapitoloAccertamentoDto.setDeAccertamento(anagraficaUfficioCapitoloAccertamentoDto.getDeAccertamento());

			if(StringUtils.isBlank(anagraficaUfficioCapitoloAccertamentoDto.getCodAccertamento()))
				anagraficaUfficioCapitoloAccertamentoDto.setCodAccertamento("n/a");
			else
				anagraficaUfficioCapitoloAccertamentoDto.setCodAccertamento(anagraficaUfficioCapitoloAccertamentoDto.getCodAccertamento());

			String codTipoDovuto = anagraficaUfficioCapitoloAccertamentoDto.getCodTipoDovuto();


			AggiornaAnagraficaUfficioCapitoloAccertamentoResultDto esitoAggiornamento = null;
			//se non contiene , vuol dire che ho selezionato solo un valore, procedo all'aggiornamento
			if (!codTipoDovuto.contains(",")){
				anagraficaUfficioCapitoloAccertamentoDto.setCodTipoDovuto(anagraficaUfficioCapitoloAccertamentoDto.getCodTipoDovuto());
				try {
					esitoAggiornamento = anagraficaService.modificaAnagrafica(anagraficaUfficioCapitoloAccertamentoDto,codIpa);
				} catch (EntityNotFoundException entityNotFoundException) {
					throw new ResponseStatusException(HttpStatus.NOT_FOUND, entityNotFoundException.getMessage());
				} catch (Exception e) {
					log.error(e.getMessage(), e);
					throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, String.format(Response.RESPONSE_KO, e.getMessage()));
				}
			}
			//altrimenti devo effettuare un update e gli inserimenti per gli altri codici tipo dovuto
			else{
				String listCodTipoDovuto[] = codTipoDovuto.split(",");


				try {

					//il primo sarà sempre una modifica
					anagraficaUfficioCapitoloAccertamentoDto.setCodTipoDovuto(listCodTipoDovuto[0]);
					esitoAggiornamento = anagraficaService.aggiornaAnagrafica(anagraficaUfficioCapitoloAccertamentoDto,codIpa);
					ESITO esito = esitoAggiornamento.getEsito();
					if (esito.toString() == "AGGIORNATA"){
						anagraficaService.salvaAnagrafiche(anagraficaUfficioCapitoloAccertamentoDto, listCodTipoDovuto, false);
					}

				} catch (Exception e){
					log.error("Errore nell'aggiornamento dell'anagrafica");
					messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.anagrafica.info.updateAnagrafica.ko"));
					esitoMsg="ERROR";
				}
			}




			AnagraficaUfficioCapitoloAccertamentoDto aggDto = esitoAggiornamento.getAnagraficaAggiornata();

			ESITO esito = esitoAggiornamento.getEsito();

			switch (esito) {
			case AGGIORNATA:
				messagesDto.getInformationMessages().add(new DynamicMessageDto("mypivot.anagrafica.info.updateAnagrafica.ok"));
				esitoMsg="AGGIORNATA";
				response.setItems(aggDto);
				break;
			case EXIST:
				messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.anagrafica.info.updateAnagrafica.ko.exist"));
				esitoMsg="EXIST";
				break;
			case ERROR:
				messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.anagrafica.info.updateAnagrafica.ko"));
				esitoMsg="ERROR";
				break;
			default:
				messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.anagrafica.info.updateAnagrafica.ko"));
				break;
			}

		}

		response.setMessages(messagesDto);
		return response;

	}

}