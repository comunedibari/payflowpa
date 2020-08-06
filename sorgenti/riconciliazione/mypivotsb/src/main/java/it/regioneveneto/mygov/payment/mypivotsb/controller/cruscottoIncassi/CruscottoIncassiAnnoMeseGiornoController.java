package it.regioneveneto.mygov.payment.mypivotsb.controller.cruscottoIncassi;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
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
import it.regioneveneto.mygov.payment.mypivot.domain.dto.cruscottoIncassi.CruscottoIncassiAnnoMeseGiornoDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.cruscottoIncassi.CruscottoIncassiAnnoMeseGiornoInputDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.cruscottoIncassi.CruscottoIncassiAnnoMeseGiornoInputDto.TIPO_STATISTICHE;
import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.UtenteTO;
import it.regioneveneto.mygov.payment.mypivot.service.EnteService;
import it.regioneveneto.mygov.payment.mypivot.service.UtenteService;
import it.regioneveneto.mygov.payment.mypivot.service.VmStatisticaAnnoMeseGiornoService;
import it.regioneveneto.mygov.payment.mypivot.utils.Constants;
import it.regioneveneto.mygov.payment.mypivot.utils.Utilities;
import it.regioneveneto.mygov.payment.mypivotsb.constant.ApiDescription;
import it.regioneveneto.mygov.payment.mypivotsb.controller.BaseController;
import it.regioneveneto.mygov.payment.mypivotsb.response.ResponseIF;
import it.regioneveneto.mygov.payment.mypivotsb.response.SingleDataResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller di gestione della sezione delle Statistiche:
 * 
 * 		- TOTALI per ANNO		- TOTALI per MESE		- TOTALI per GIORNO
 * 
 */

@Slf4j
@RestController
@RequestMapping(value = "/api/cruscottoIncassi", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api(tags = { "CruscottoIncassi" })
public class CruscottoIncassiAnnoMeseGiornoController extends BaseController {


	@Autowired
	private EnteService enteService;

	@Autowired
	private UtenteService utenteService;

	@Autowired
	private VmStatisticaAnnoMeseGiornoService vmStatisticheService;

	@Value("${myPivot.enableGlobalProfile}")
	private Boolean enableGlobalProfile;
	
	public CruscottoIncassiAnnoMeseGiornoController() {
		super();
	}



	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation(value = "get statistiche pagamenti aggregati per Anno/Mese/Girono", notes = ApiDescription.DESCR_GET_STATISTICHE_PER_ANNOMESEGIORNO, response = SingleDataResponse.class)
	@GetMapping(value = "/totaliPerAnnoMeseGiorno")	
	public ResponseIF totaliPerAnnoMeseGiorno(

			@RequestBody CruscottoIncassiAnnoMeseGiornoInputDto cruscottoIncassiInput

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

		log.debug("RICERCA :: STATISTICHE :: ANNO/MESE/GIORNO :: GET :: Utente[codFedUserId: " + utenteTO.getCodFedUserId() + ", cruscottoIncassiInput: " + cruscottoIncassiInput.toString() + "] :: START");

		CruscottoIncassiAnnoMeseGiornoDto result = new CruscottoIncassiAnnoMeseGiornoDto();

		try{

			/**
			 * Inizializzo il bean per la ricerca
			 */
			initializeFilter(cruscottoIncassiInput, ente.getFlgTesoreria());

			/* */


			/**
			 * Eseguo la query di ricerca
			 */
			switch (cruscottoIncassiInput.getTipologia()) {
			case ANNO: {
				/* */
				result = vmStatisticheService.getTotaliPerAnno(ente.getId(), cruscottoIncassiInput.getListaAnni());
				/* */
				break;
			}
			case MESE: {
				/* */
				result = vmStatisticheService.getTotaliPerAnnoMese(ente.getId(), cruscottoIncassiInput.getAnno(), cruscottoIncassiInput.getListaMesi());
				/* */
				break;
			}	
			case GIORNO: {
				/* */
				result = vmStatisticheService.getTotaliPerAnnoMeseGiorno(ente.getId(), cruscottoIncassiInput.getGiornoDal(), cruscottoIncassiInput.getGiornoAl());
				/* */
				break;
			}
			default: break;
			}


		}catch(Exception e){
			log.error("RICERCA :: STATISTICA :: ANNO/MESE/GIORNO :: Utente[codFedUserId: " + utenteTO.codFedUserId + "] :: ERRORE ", e);
			messagesDto.getInformationMessages().add(new DynamicMessageDto("mypivot.statistiche.errore.ricerca"));
			response.setMessages(messagesDto);
			return response;
		}

		log.debug("RICERCA :: STATISTICHE :: ANNO/MESE/GIORNO :: GET :: Utente[codFedUserId: " + utenteTO.getCodFedUserId() + ", cruscottoIncassiInput: " + cruscottoIncassiInput.toString() + "] :: END");
		return new SingleDataResponse<CruscottoIncassiAnnoMeseGiornoDto>(result);

	}




	public static void initializeFilter(CruscottoIncassiAnnoMeseGiornoInputDto statisticheFilter, Boolean flgTesoreria) {
		/**
		 * Se la proprietà è nulla, la inizializzo selezionando la tipologia statistica di default "Totali per Anno"
		 */
		if(statisticheFilter.getTipologia() == null) statisticheFilter.setTipologia(TIPO_STATISTICHE.ANNO);

		/**
		 * Se nessuna tipologia di filtro è attiva, di default abilito quella per anno.
		 */
		if(statisticheFilter.getAnnoCheck() == null && statisticheFilter.getAnnoMeseCheck() == null && statisticheFilter.getAnnoMeseGiornoCheck() == null){
			/* */
			statisticheFilter.setAnnoCheck(Boolean.FALSE);
			statisticheFilter.setAnnoMeseCheck(Boolean.FALSE);
			statisticheFilter.setAnnoMeseGiornoCheck(Boolean.FALSE);
			/* */
			if(statisticheFilter.getTipologia().equals(TIPO_STATISTICHE.ANNO))   statisticheFilter.setAnnoCheck(Boolean.TRUE);
			if(statisticheFilter.getTipologia().equals(TIPO_STATISTICHE.MESE))   statisticheFilter.setAnnoMeseCheck(Boolean.TRUE);
			if(statisticheFilter.getTipologia().equals(TIPO_STATISTICHE.GIORNO)) statisticheFilter.setAnnoMeseGiornoCheck(Boolean.TRUE);
		}

		/**
		 * Se l'ente ha abilitata la tesoreria e nessuna tipologia di importo è abilitata, di default le abilito tutte.
		 */
		if(flgTesoreria && statisticheFilter.getPagatiCheck() == null && statisticheFilter.getRendicontatiCheck() == null && statisticheFilter.getIncassatiCheck() == null){
			/* */
			statisticheFilter.setPagatiCheck(Boolean.TRUE);
			/* */
			statisticheFilter.setRendicontatiCheck(Boolean.TRUE);
			/* */
			statisticheFilter.setIncassatiCheck(Boolean.TRUE);
		}else
			/**
			 * Se l'ente ha disabilitata la tesoreria e nessuna tipologia di importo è abilitata, di default abilito "Rendicontati" e "Incassati".
			 */
			if(!flgTesoreria && statisticheFilter.getPagatiCheck() == null && statisticheFilter.getRendicontatiCheck() == null) {
				/* */
				statisticheFilter.setPagatiCheck(Boolean.TRUE);
				/* */
				statisticheFilter.setRendicontatiCheck(Boolean.TRUE);
			}

		/**
		 * Se la proprietà è nulla, la inizializzo selezionando di default gli ultimi 4 anni.
		 */
		if (statisticheFilter.getListaAnni() == null || statisticheFilter.getListaAnni().isEmpty()) {
			try {
				List<Integer> years = new ArrayList<Integer>();		Date data = new Date();

				years.add(Integer.parseInt(Constants.YYYY.format(data)));
				years.add(Integer.parseInt(Constants.YYYY.format(Utilities.addOrSubtractTime(data, Calendar.YEAR, -1))));
				years.add(Integer.parseInt(Constants.YYYY.format(Utilities.addOrSubtractTime(data, Calendar.YEAR, -2))));
				years.add(Integer.parseInt(Constants.YYYY.format(Utilities.addOrSubtractTime(data, Calendar.YEAR, -3))));

				statisticheFilter.setListaAnni(years);
			} catch (Exception e) {
				// Nothing to do
			}
		}else{
			/**
			 * Se, invece, è valorizzata la ordino in modo descrescente. 
			 */
			Collections.sort(statisticheFilter.getListaAnni(), Collections.reverseOrder());
		}

		/**
		 * Se la proprietà è nulla, la inizializzo selezionando l'anno corrente.
		 */
		if (statisticheFilter.getAnno() == null) {
			try {
				statisticheFilter.setAnno(Integer.parseInt(Constants.YYYY.format(new Date())));
			} catch (Exception e) {
				// Nothing to do
			}
		}

		/**
		 * Se la proprietà è nulla, la inizializzo selezionando tutti mesi dell'anno(individuato sopra) che intercorrono dal mese corrente 
		 * al mese di Gennaio.
		 */
		if (statisticheFilter.getListaMesi() == null || statisticheFilter.getListaMesi().isEmpty()) {
			try {
				List<Integer> months = new ArrayList<Integer>();

				/**
				 * Get anno corrente
				 */
				Calendar calNow = Calendar.getInstance();
				/**
				 * Get anno selezionato
				 */
				Calendar cal = Calendar.getInstance(); cal.set(Calendar.YEAR, statisticheFilter.getAnno());

				/**
				 * Se gli anni coincidono, il mese da cui inizare la selezione è il corrente, altrimenti il mese d'inizio è Dicembre
				 */
				if(DateUtils.truncatedEquals(cal, calNow, Calendar.YEAR))
					/* */
					months.add(cal.get(Calendar.MONTH) + 1);
				else
					/* */
					months.add(12);

				// set mesi per aggiungerli come filtro di default
				for (int i = months.get(0)-1; i > 0; i--) months.add(i);

				/* */
				statisticheFilter.setListaMesi(months);
			} catch (Exception e) {
				// Nothing to do
			}
		}else{
			/**
			 * Se, invece, è valorizzata la ordino in modo descrescente. 
			 */
			Collections.sort(statisticheFilter.getListaMesi(), Collections.reverseOrder());
		}

		Date giornoDalFilter = null;

		if (StringUtils.hasText(statisticheFilter.getGiornoDal())) {
			try {
				giornoDalFilter = Constants.DDMMYYYY.parse(statisticheFilter.getGiornoDal());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date giornoAlFilter = null;

		if (StringUtils.hasText(statisticheFilter.getGiornoAl())) {
			try {
				giornoAlFilter = Constants.DDMMYYYY.parse(statisticheFilter.getGiornoAl());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		/**
		 * Se le proprietà sono nulle, le inizializzo selezionando come data di fine estrazione il giorno corrente e come data d'inizio
		 * estrazione il giorno 1.
		 */
		if ((giornoDalFilter == null) && (giornoAlFilter == null)) {
			/* */
			giornoAlFilter = new Date();
			/* */
			giornoDalFilter = DateUtils.setDays(giornoAlFilter, 1);
		}else 
			/**
			 * Se le proprietà di fine estrazione è nulla, la inizializzo selezionando l'ultimo giorno del mese a cui si riferisce 'giornoDalFilter'.
			 */
			if ((giornoDalFilter != null) && (giornoAlFilter == null)) {
				/* */
				Calendar cal = Calendar.getInstance(); 		cal.setTime(giornoDalFilter);
				/* get last day of month */
				int day = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
				/* */
				giornoAlFilter = DateUtils.setDays(giornoDalFilter, day);
			} 
			else 
				/**
				 * Se le proprietà d'inizio etrazione è nulla, la inizializzo selezionando il primo giorno del mese a cui si riferisce 'giornoAlFilter'.
				 */
				if ((giornoDalFilter == null) && (giornoAlFilter != null)) {
					/* */
					giornoDalFilter = DateUtils.setDays(giornoAlFilter, 1);
				}

		statisticheFilter.setGiornoDal(Constants.DDMMYYYY.format(giornoDalFilter));
		statisticheFilter.setGiornoAl(Constants.DDMMYYYY.format(giornoAlFilter));
	}
}