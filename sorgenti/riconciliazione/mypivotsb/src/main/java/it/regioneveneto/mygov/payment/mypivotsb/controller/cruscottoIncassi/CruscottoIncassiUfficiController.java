package it.regioneveneto.mygov.payment.mypivotsb.controller.cruscottoIncassi;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import it.regioneveneto.mygov.payment.mypivot.domain.dto.MessagesDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.cruscottoIncassi.CruscottoIncassiDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.cruscottoIncassi.CruscottoIncassiUfficiInputDto;
import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTipoDovutoTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.UtenteTO;
import it.regioneveneto.mygov.payment.mypivot.service.EnteService;
import it.regioneveneto.mygov.payment.mypivot.service.OperatoreEnteTipoDovutoService;
import it.regioneveneto.mygov.payment.mypivot.service.UtenteService;
import it.regioneveneto.mygov.payment.mypivot.service.VmStatisticaUfficiService;
import it.regioneveneto.mygov.payment.mypivot.utils.Constants;
import it.regioneveneto.mygov.payment.mypivotsb.constant.ApiDescription;
import it.regioneveneto.mygov.payment.mypivotsb.controller.BaseController;
import it.regioneveneto.mygov.payment.mypivotsb.response.ResponseIF;
import it.regioneveneto.mygov.payment.mypivotsb.response.SingleDataResponse;
import lombok.extern.slf4j.Slf4j;



@Slf4j
@RestController
@RequestMapping(value = "/api/cruscottoIncassi", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api(tags = { "CruscottoIncassi" })
public class CruscottoIncassiUfficiController extends BaseController {

	@Autowired
	private EnteService enteService;

	@Autowired
	private UtenteService utenteService;

	@Autowired
	private VmStatisticaUfficiService vmStatisticheService;
	
	@Autowired
	private OperatoreEnteTipoDovutoService operatoreEnteTipoDovutoService;
	
	@Value("${myPivot.enableGlobalProfile}")
	private Boolean enableGlobalProfile;

	public CruscottoIncassiUfficiController() {
		super();
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation(value = "get statistiche pagamenti aggregati per uffici", notes = ApiDescription.DESCR_GET_STATISTICHE_PER_UFFICI, response = SingleDataResponse.class)
	@GetMapping(value = "/totaliRipartitiPerUffici")	
	public ResponseIF totaliRipartitiPerUffici(

			@RequestBody CruscottoIncassiUfficiInputDto cruscottoIncassiInput

			) {

		UtenteTO utenteTO = null;
		
		if(enableGlobalProfile) 
			utenteTO = utenteService.getUtenteByGlobalDefault();	
		
		EnteTO ente = enteService.getByCodIpaEnte(cruscottoIncassiInput.getCodIpa() );
		if (ente == null) {
			SingleDataResponse<?> response = new SingleDataResponse();
			MessagesDto messagesDto = new MessagesDto();
			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.codIpaEnteNonValido"));
			response.setMessages(messagesDto);
			return response;
		}
		
		MessagesDto messagesDto = new MessagesDto();
		SingleDataResponse<?> response = new SingleDataResponse();

		log.debug("RICERCA :: STATISTICHE :: RIPARTITI per UFFICI :: GET :: Utente[codFedUserId: " + utenteTO.getCodFedUserId() + ", cruscottoIncassiInput: " + cruscottoIncassiInput.toString() + "] :: START");

		CruscottoIncassiDto result = null;
			
		try{
			

			/**
			 * Recupero la lista dei tipi dovuto dell'ente per cui l'utente è un operatore attivo. 
			 * Se la lista è vuota, mostro un messaggio di errore perchè vuol dire che l'utente non può consultare i dati statistici.
			 */
			List<EnteTipoDovutoTO> activeOperatoreEnteTdAsObj = operatoreEnteTipoDovutoService.getListaEnteTipoDovutoForOperatoreAndCodIpaEnte(utenteTO.getCodFedUserId(), ente.getCodIpaEnte());
			
			if (activeOperatoreEnteTdAsObj == null || activeOperatoreEnteTdAsObj.isEmpty()) {
				log.warn("RICERCA :: STATISTICA :: RIPARTITI per UFFICI :: Utente[codFedUserId: " + utenteTO.getCodFedUserId() + "] :: NON risulta OPERATORE per nessun tipo dovuto.");
				messagesDto.getInformationMessages().add(new DynamicMessageDto("mypivot.messages.errore.nessunTipoDovutoAssegnato"));
				response.setMessages(messagesDto);
				return response;
			}
		
			/**
			 * Ciclo la lista appena recuperata dei tipi dovuto e aggiungo ad una lista temporanea solamente il codice, mi servirà come filtro della query. 
			 */
			List<String> activeOperatoreEnteTdAsString = new ArrayList<String>();
			/* */
			for (EnteTipoDovutoTO item : activeOperatoreEnteTdAsObj) activeOperatoreEnteTdAsString.add(item.getCodTipo());
			
			/**
			 * Inizializzo il bean per la ricerca
			 */
			initializeFilter(cruscottoIncassiInput, ente.getFlgTesoreria());
				
			
			/**
			 * Eseguo la query di ricerca
			 */
			 if(cruscottoIncassiInput.getAnnoCheck() != null && cruscottoIncassiInput.getAnnoCheck())
				/**
				 * Filtro per Anno
				 */
				result = vmStatisticheService.getTotaliRipartitiPerUfficiByAnno(ente.getId(), Integer.parseInt(cruscottoIncassiInput.getAnno()), activeOperatoreEnteTdAsString);
			 else
				 if(cruscottoIncassiInput.getAnnoMeseCheck() != null && cruscottoIncassiInput.getAnnoMeseCheck()){
					/**
					 * Filtro per Anno e Mese
					 */
					Date mese = Constants.MMYYYY.parse(cruscottoIncassiInput.getMese());
					/* */
					Calendar cal = Calendar.getInstance(); 		cal.setTime(mese);
					/* */
					result = vmStatisticheService.getTotaliRipartitiPerUfficiByAnnoMese(ente.getId(), cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, activeOperatoreEnteTdAsString);
				 }
			 else
				 if(cruscottoIncassiInput.getAnnoMeseGiornoCheck()  != null && cruscottoIncassiInput.getAnnoMeseGiornoCheck()){
					/**
					 * Filtro per Anno, Mese e Giorno 
					 */
					Date giorno = Constants.DDMMYYYY.parse(cruscottoIncassiInput.getGiorno());
					/* */
					Calendar cal = Calendar.getInstance(); 		cal.setTime(giorno);
					/* */
					result = vmStatisticheService.getTotaliRipartitiPerUfficiByAnnoMeseGiorno(ente.getId(), cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DAY_OF_MONTH), activeOperatoreEnteTdAsString);
				 }
			
			
		}catch(Exception e){
			log.debug("RICERCA :: STATISTICHE :: RIPARTITI per UFFICI :: GET :: Utente[codFedUserId: " + utenteTO.codFedUserId + ", command: " + cruscottoIncassiInput.toString() + "] :: ERROR", e);
			messagesDto.getInformationMessages().add(new DynamicMessageDto("mypivot.statistiche.errore.ricerca"));
			response.setMessages(messagesDto);
			return response;
		}

		log.debug("RICERCA :: STATISTICHE :: RIPARTITI per UFFICI :: GET :: Utente[codFedUserId: " + utenteTO.codFedUserId + ", command: " + cruscottoIncassiInput.toString() + "] :: END");
		return new SingleDataResponse<CruscottoIncassiDto>(result);

	}
	



	
	/**
	 * Inizializza il bean di ricerca.
	 * 
	 * @param {@link CruscottoIncassiUfficiInputDto} command
	 */
	public static void initializeFilter(CruscottoIncassiUfficiInputDto cruscottoIncassiInput, Boolean flgTesoreria) {
		/**
		 * Se nessuna tipologia di filtro è attiva, di default abilito quella per anno.
		 */
		if(cruscottoIncassiInput.getAnnoCheck() == null && cruscottoIncassiInput.getAnnoMeseCheck() == null && cruscottoIncassiInput.getAnnoMeseGiornoCheck() == null){
			/* */
			cruscottoIncassiInput.setAnnoCheck(Boolean.TRUE);
			/* */
			cruscottoIncassiInput.setAnnoMeseCheck(Boolean.FALSE);
			/* */
			cruscottoIncassiInput.setAnnoMeseGiornoCheck(Boolean.FALSE);
		}
		
		/**
		 * Se l'ente ha abilitata la tesoreria e nessuna tipologia di importo è abilitata, di default le abilito tutte.
		 */
		if(flgTesoreria && cruscottoIncassiInput.getPagatiCheck() == null && cruscottoIncassiInput.getRendicontatiCheck() == null && cruscottoIncassiInput.getIncassatiCheck() == null){
			/* */
			cruscottoIncassiInput.setPagatiCheck(Boolean.TRUE);
			/* */
			cruscottoIncassiInput.setRendicontatiCheck(Boolean.TRUE);
			/* */
			cruscottoIncassiInput.setIncassatiCheck(Boolean.TRUE);
		}else
			/**
			 * Se l'ente ha disabilitata la tesoreria e nessuna tipologia di importo è abilitata, di default abilito "Rendicontati" e "Incassati".
			 */
			if(!flgTesoreria && cruscottoIncassiInput.getPagatiCheck() == null && cruscottoIncassiInput.getRendicontatiCheck() == null) {
				/* */
				cruscottoIncassiInput.setPagatiCheck(Boolean.TRUE);
				/* */
				cruscottoIncassiInput.setRendicontatiCheck(Boolean.TRUE);
			}
		
		/**
		 * Se la proprietà è nulla, la inizializzo selezionando l'anno corrente.
		 */
		if (!StringUtils.hasText(cruscottoIncassiInput.getAnno())) {
			try {
				cruscottoIncassiInput.setAnno(Constants.YYYY.format(new Date()));
			} catch (Exception e) {
				// Nothing to do
			}
		}
		
		/**
		 * Se la proprietà è nulla, la inizializzo selezionando il mese corrente.
		 */
		if (!StringUtils.hasText(cruscottoIncassiInput.getMese())) {
			try {
				cruscottoIncassiInput.setMese(Constants.MMYYYY.format(new Date()));
			} catch (Exception e) {
				// Nothing to do
			}
		}
		
		/**
		 * Se la proprietà è nulla, la inizializzo selezionando il giorno corrente.
		 */
		if (!StringUtils.hasText(cruscottoIncassiInput.getGiorno())) {
			try {
				cruscottoIncassiInput.setGiorno(Constants.DDMMYYYY.format(new Date()));
			} catch (Exception e) {
				// Nothing to do
			}
		}
	}
	
}