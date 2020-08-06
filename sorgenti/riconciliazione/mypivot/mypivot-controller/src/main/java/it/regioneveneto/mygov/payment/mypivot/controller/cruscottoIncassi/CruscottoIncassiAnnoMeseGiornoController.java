package it.regioneveneto.mygov.payment.mypivot.controller.cruscottoIncassi;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import it.regioneveneto.mygov.payment.mypivot.controller.command.CruscottoIncassiAnnoMeseGiornoCommand;
import it.regioneveneto.mygov.payment.mypivot.controller.command.CruscottoIncassiAnnoMeseGiornoCommand.TIPO_STATISTICHE;
import it.regioneveneto.mygov.payment.mypivot.controller.utils.UtilitiesCtrl;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.cruscottoIncassi.CruscottoIncassiAnnoMeseGiornoDto;
import it.regioneveneto.mygov.payment.mypivot.service.VmStatisticaAnnoMeseGiornoService;
import it.regioneveneto.mygov.payment.mypivot.utils.Constants;
import it.regioneveneto.mygov.payment.mypivot.utils.SecurityContext;
import it.regioneveneto.mygov.payment.mypivot.utils.SessionVariables;
import it.regioneveneto.mygov.payment.mypivot.utils.Utilities;

/**
 * Controller di gestione della sezione delle Statistiche:
 * 
 * 		- TOTALI per ANNO		- TOTALI per MESE		- TOTALI per GIORNO
 * 
 * @author Marianna Memoli
 */
@Controller
@RequestMapping("/protected/cruscottoIncassi")
public class CruscottoIncassiAnnoMeseGiornoController {

	private static Log logger = LogFactory.getLog(CruscottoIncassiAnnoMeseGiornoController.class);

	@Autowired
	private VmStatisticaAnnoMeseGiornoService vmStatisticheService;
	
	public CruscottoIncassiAnnoMeseGiornoController() {
		super();
	}
	
	/**
	 * Restituisce la view di ricerca dei dati statistici (GET) per le statistiche. 
	 * -----------------------------------------------------------------------------------------------------------------------------------
	 * Alla view sono ritornati i seguenti oggetti:
	 * 
	 *   "responseResult":     Oggetto contenente i dati correlati alla statistica
	 *   "statisticheCommand": Model attribute
	 * 	 "flgTesoreria": 	   Se l'ente non ha abilitato la tesoreria, lato view non sarà possibile visualizzare gli importi incassati
	 * 
	 * @param {@link HttpServletRequest} request
	 * @param {@link TIPO_STATISTICHE} 	 tipo
	 * 
	 * @return {@link ModelAndView}
	 * @author Marianna Memoli
	 */
	@RequestMapping(value = { "/totaliPerAnnoMeseGiorno.html" }, method = RequestMethod.GET)
	public ModelAndView get(HttpServletRequest request, Model model, @RequestParam(required = false) TIPO_STATISTICHE tipo) {
		
		logger.debug("RICERCA :: STATISTICHE :: ANNO/MESE/GIORNO :: GET :: Utente[tipo: " + tipo + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: START");
	
		/* */
		ModelAndView mav = new ModelAndView();
		
		try{
			/**
			 * La funzione verifica che l'utente autenticato abbia selezionato l'ente.
			 */
			if(!UtilitiesCtrl.hasSecurityEnte()) return new ModelAndView(new RedirectView(request.getContextPath() + Constants.PATH_PAGE_SCELTA_ENTE));
		
			/**
			 * Se in ingresso la tipologia di statistica non è valorizzata, continuo caricando la statistica di default.
			 */
			if(tipo == null) tipo = TIPO_STATISTICHE.ANNO;
			
			/**
			 * Instance Bean form di ricerca
			 */
			CruscottoIncassiAnnoMeseGiornoCommand statisticheCommand = new CruscottoIncassiAnnoMeseGiornoCommand();
			
			/**
			 * Resetto i filtri di ricerca salvati in sessione delle view relative le altre tipologie di statistica.
			 */
			SecurityContext.setEnteViewFilters(SecurityContext.getEnte().getCodIpaEnte(), SessionVariables.ACTION_STATISTICA_UFFICI, null);
			SecurityContext.setEnteViewFilters(SecurityContext.getEnte().getCodIpaEnte(), SessionVariables.ACTION_STATISTICA_TIPI_DOVUTO, null);
			SecurityContext.setEnteViewFilters(SecurityContext.getEnte().getCodIpaEnte(), SessionVariables.ACTION_STATISTICA_CAPITOLI, null);
			SecurityContext.setEnteViewFilters(SecurityContext.getEnte().getCodIpaEnte(), SessionVariables.ACTION_STATISTICA_ACCERTAMENTI, null);
			SecurityContext.setEnteViewFilters(SecurityContext.getEnte().getCodIpaEnte(), SessionVariables.ACTION_STATISTICA_DETTAGLIO_CRUSCOTTO, null);
			
			/**
			 * Prevalorizzo il bean di ricerca della form con gli ultimi valori dei filtri sottomessi e salvati in sessione.
			 */
			setSessionFilterToBean(
					request, SecurityContext.getEnte().getCodIpaEnte(), statisticheCommand, tipo, null, null, null, null, null, null, null, null);
						
			/**
			 * Avvio la ricerca
			 */
			mav = statisticaTotaliPerAnnoMeseGiorno(statisticheCommand, request);
			
		}catch(Exception e){
			logger.error("RICERCA :: STATISTICHE :: ANNO/MESE/GIORNO :: GET :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: ERRORE ", e);
			/* 
			 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
			 */
			return UtilitiesCtrl.getViewErrorMessage("mypivot.statistiche.errore.ricerca");
		}
		
		logger.debug("RICERCA :: STATISTICHE :: ANNO/MESE/GIORNO :: GET :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: END");

		return mav;
	}
	
	/**
	 * Restituisce la view di ricerca dei dati statistici (POST). 
	 * -----------------------------------------------------------------------------------------------------------------------------------
	 * Alla view sono ritornati i seguenti oggetti:
	 * 
	 *   "responseResult":     Oggetto contenente i dati correlati alla statistica
	 *   "statisticheCommand": Model attribute
	 * 	 "flgTesoreria": 	   Se l'ente non ha abilitato la tesoreria, lato view non sarà possibile visualizzare gli importi incassati
	 * 
	 * @param {@link HttpServletRequest} request
	 * @param {@link CruscottoIncassiAnnoMeseGiornoCommand} statisticheCommand
	 * 
	 * @return {@link ModelAndView}
	 * @author Marianna Memoli
	 */
	@RequestMapping(value = { "/totaliPerAnnoMeseGiorno.html" }, method = RequestMethod.POST)
	public ModelAndView post(@ModelAttribute("statisticheCommand") CruscottoIncassiAnnoMeseGiornoCommand statisticheCommand, HttpServletRequest request) {

		logger.debug("RICERCA :: STATISTICHE :: ANNO/MESE/GIORNO :: POST :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + ", statisticheCommand: " + statisticheCommand.toString() + "] :: START");

		ModelAndView mav = new ModelAndView();

		try{
			/**
			 * Avvio la ricerca
			 */
			mav = statisticaTotaliPerAnnoMeseGiorno(statisticheCommand, request);
			
		    /**
		     * Salvo i nuovi filtri di ricerca impostati in sessione.
		     */
			if(SecurityContext.getEnte() != null && StringUtils.hasText(SessionVariables.ACTION_STATISTICA_AMG)){
				Map<String, Object> filtersMap = new HashMap<String, Object>();
					
					filtersMap.put(SessionVariables.STATISTICHE_TIPOLOGIA, 		 	    statisticheCommand.getTipologia());
					filtersMap.put(SessionVariables.STATISTICHE_CHECK_IMPORTI_PAGATI,   statisticheCommand.getPagatiCheck());
					filtersMap.put(SessionVariables.STATISTICHE_CHECK_IMPORTI_REND,	    statisticheCommand.getRendicontatiCheck());
					filtersMap.put(SessionVariables.STATISTICHE_CHECK_IMPORTI_INCA,     statisticheCommand.getIncassatiCheck());
					filtersMap.put(SessionVariables.STATISTICHE_CHECK_ANNO,   	  		statisticheCommand.getAnnoCheck());
					filtersMap.put(SessionVariables.STATISTICHE_CHECK_ANNO_MESE,   	   	statisticheCommand.getAnnoMeseCheck());
					filtersMap.put(SessionVariables.STATISTICHE_CHECK_ANNO_MESE_GIORNO, statisticheCommand.getAnnoMeseGiornoCheck());
					filtersMap.put(SessionVariables.STATISTICHE_LISTA_ANNI, 	  		statisticheCommand.getListaAnni());
					filtersMap.put(SessionVariables.STATISTICHE_ANNO, 		  			statisticheCommand.getAnno());
					filtersMap.put(SessionVariables.STATISTICHE_LISTA_MESI, 	  		statisticheCommand.getListaMesi());
					filtersMap.put(SessionVariables.STATISTICHE_GIORNO_DAL, 	  		statisticheCommand.getGiornoDal());
					filtersMap.put(SessionVariables.STATISTICHE_GIORNO_AL, 	  			statisticheCommand.getGiornoAl());
					
				SecurityContext.setEnteViewFilters(SecurityContext.getEnte().getCodIpaEnte(), SessionVariables.ACTION_STATISTICA_AMG, filtersMap);
			}
			
		}catch(Exception e){
			logger.debug("RICERCA :: STATISTICHE :: ANNO/MESE/GIORNO :: POST :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + ", statisticheCommand: " + statisticheCommand.toString() + "] :: ERROR", e);
			/* 
			 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
			 */
			return UtilitiesCtrl.getViewErrorMessage("mypivot.statistiche.errore.ricerca");
		}
		
		logger.debug("RICERCA :: STATISTICHE :: ANNO/MESE/GIORNO :: POST :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + ", statisticheCommand: " + statisticheCommand.toString() + "] :: END");

		return mav;
	}
	
	/**
	 * Funzione che gestisce le fasi comuni della ricerca dei dati statistici avviata sia in modalità GET che POST per le statistiche:
	 * -----------------------------------------------------------------------------------------------------------------------------------
	 * Prima di erogare la pagina effettuo dei controlli per determinare se sussistono le condizioni tali perchè l'utente possa accedere
	 * alla funzionalità.
	 * 
	 * I controlli eseguiti sono i seguenti:
	 *   1. L'utente deve aver selezionato l'ente beneficiario, se mancante verrà rediretto alla pagina di scelta dell'ente.
	 *   2. L'utente deve avere ruoli attivi, se mancante verrà rediretto alla pagina di scelta dell'ente.
	 *   3. L'utente deve avere uno dei ruoli utili ad accedere alla funzionalità delle "Statistiche" (ROLE_ADMIN e/o ROLE_STATS).
	 * 
	 * A seguito dei controlli, istanzio il bean che mappa i filtri di ricerca ed eventulamente lo prevalorizzo con i filtri dell'ultima ricerca
	 * avviata che ho salvato in sessione. Avvio la ricerca.
	 * 
	 * Alla view sono ritornati i seguenti oggetti:
	 * 
	 *   "responseResult":     Oggetto contenente i dati correlati alla statistica
	 *   "statisticheCommand": Model attribute
	 * 	 "flgTesoreria": 	   Se l'ente non ha abilitato la tesoreria, lato view non sarà possibile visualizzare gli importi incassati
	 * 
	 * @param {@link HttpServletRequest} request
	 * @param {@link CruscottoIncassiAnnoMeseGiornoCommand} statisticheCommand
	 * 
	 * @return {@link ModelAndView}
	 * @author Marianna Memoli
	 */
	private ModelAndView statisticaTotaliPerAnnoMeseGiorno(CruscottoIncassiAnnoMeseGiornoCommand statisticheCommand, HttpServletRequest request) {

		logger.debug("RICERCA :: STATISTICA :: ANNO/MESE/GIORNO :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: IN CORSO ");

		/* */
		ModelAndView mav = new ModelAndView("totaliPerAnnoMeseGiorno");
		
		try{
			/**
			 * La funzione verifica che l'utente autenticato abbia selezionato l'ente.
			 */
			if(!UtilitiesCtrl.hasSecurityEnte()) return new ModelAndView(new RedirectView(request.getContextPath() + Constants.PATH_PAGE_SCELTA_ENTE));
		
			/**
			 * La funzione verifica che l'utente autenticato abbia dei ruoli attivi.
			 */
			if(!UtilitiesCtrl.hasSecurityRole()) return new ModelAndView(new RedirectView(request.getContextPath() + Constants.PATH_PAGE_SCELTA_ENTE));
		
			/**
			 * La funzione verifica che l'utente possa accedere alla sezione delle Statistiche
			 */
			if(!UtilitiesCtrl.hasSecurityAccessFunctionalityCruscottoIncassi()) return UtilitiesCtrl.getViewErrorMessage("mypivot.messages.error.notAuthorized");
			
			/**
			 * Inizializzo il bean per la ricerca
			 */
			initializeFilter(statisticheCommand);
			
			/* */
			CruscottoIncassiAnnoMeseGiornoDto result = null;
			
			/**
			 * Eseguo la query di ricerca
			 */
			switch (statisticheCommand.getTipologia()) {
				case ANNO: {
					/* */
					result = vmStatisticheService.getTotaliPerAnno(SecurityContext.getEnte().getId(), statisticheCommand.getListaAnni());
					/* */
					break;
				}
				case MESE: {
					/* */
					result = vmStatisticheService.getTotaliPerAnnoMese(SecurityContext.getEnte().getId(), statisticheCommand.getAnno(), statisticheCommand.getListaMesi());
					/* */
					break;
				}	
				case GIORNO: {
					/* */
					result = vmStatisticheService.getTotaliPerAnnoMeseGiorno(SecurityContext.getEnte().getId(), statisticheCommand.getGiornoDal(), statisticheCommand.getGiornoAl());
					/* */
					break;
				}
				default: break;
			}
			
			/**
			 * Set param da ritornare alla view
			 */
			mav.addObject("responseResult", result);
			/* */
			mav.addObject("flgTesoreria", SecurityContext.getEnte().getFlgTesoreria()); // abilita o meno la visualizzazione degli importi incassati
			/* */
			mav.addObject("statisticheCommand", statisticheCommand);

		}catch(Exception e){
			logger.error("RICERCA :: STATISTICA :: ANNO/MESE/GIORNO :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: ERRORE ", e);
			/* 
			 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
			 */
			return UtilitiesCtrl.getViewErrorMessage("mypivot.statistiche.errore.ricerca");
		}
		
		logger.debug("RICERCA :: STATISTICA :: ANNO/MESE/GIORNO :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: TERMINATA ");
		
		return mav;
	}
	
	/**
	 * La funzione valorizza il bean di ricerca della sezione delle "Statistiche" con gli ultimi valori dei filtri sottomessi e salvati.
	 * 
	 * @param {@link HttpServletRequest} request
	 * @param {@link String}  			 codIpaEnte,  	  	Codice Ipa dell'ente selezionato
	 * @param {@link TIPO_STATISTICHE}   tipologia, 		Tipo di Statistica selezionata
	 * @param {@link Boolean}   		 pagatiCheck, 		Visualizzazione dell'Importo Pagato
	 * @param {@link Boolean}   		 rendicontatiCheck, Visualizzazione dell'Importo Rendicontato
	 * @param {@link Boolean}   		 incassatiCheck, 	Visualizzazione dell'Importo Incassato
	 * @param {@link List<Integer>}		 listaAnni, 	 	Elenco anni per cui filtrare l'estrazione del dato statistico
	 * @param {@link Integer}			 anno, 		 		L'anno per cui filtrare l'estrazione del dato statistico
	 * @param {@link List<Integer>}		 listaMesi, 		Elenco mesi dell'anno per cui filtrare l'estrazione del dato statistico
	 * @param {@link String} 			 giornoDal,    		Giorno nel mese dell'anno da cui iniziare l'estrazione del dato statistico
	 * @param {@link String} 			 giornoAl,     		Giorno nel mese dell'anno in cui terminare l'estrazione del dato statistico
	 * @param {@link CruscottoIncassiAnnoMeseGiornoCommand} statisticheCommand, Model attribute
	 */
	@SuppressWarnings("unchecked")
	private void setSessionFilterToBean(
			HttpServletRequest request, String codIpaEnte, CruscottoIncassiAnnoMeseGiornoCommand statisticheCommand, TIPO_STATISTICHE tipologia, 
			Boolean pagatiCheck, Boolean rendicontatiCheck, Boolean incassatiCheck, List<Integer> listaAnni, Integer anno, List<Integer> listaMesi, 
			String giornoDal, String giornoAl) {
		try{
			/** 
			 * Recupero dalla sessione l'oggetto in cui ho salvato i filtri della ricerca.
			 */
			Map<String, Object> filtersMap = SecurityContext.getEnteViewFilters(codIpaEnte, SessionVariables.ACTION_STATISTICA_AMG);
			
			if (filtersMap == null) filtersMap = new HashMap<String, Object>();
			
		 /* ============================================== TIPOLOGIA STATISTICA ========================================== */
			try {
				Object session_pr = filtersMap == null ? null : filtersMap.get(SessionVariables.STATISTICHE_TIPOLOGIA);
				if (tipologia == null)
				   statisticheCommand.setTipologia((TIPO_STATISTICHE) session_pr);
				else
					statisticheCommand.setTipologia(tipologia);
			} catch (Exception e) {
				statisticheCommand.setTipologia(TIPO_STATISTICHE.ANNO);
			}
			filtersMap.put(SessionVariables.STATISTICHE_TIPOLOGIA, statisticheCommand.getTipologia());
	
		 /* ================================== CHECKBOX ABILITA/DISABILITA IMPORTO PAGATO ================================ */
			try {
				Object session_pr = filtersMap == null ? null : filtersMap.get(SessionVariables.STATISTICHE_CHECK_IMPORTI_PAGATI);
				if (pagatiCheck == null)
					statisticheCommand.setPagatiCheck((Boolean) session_pr);
				else
					statisticheCommand.setPagatiCheck(pagatiCheck);
			} catch (Exception e) {
				statisticheCommand.setPagatiCheck(Boolean.TRUE);
			}
			filtersMap.put(SessionVariables.STATISTICHE_CHECK_IMPORTI_PAGATI, statisticheCommand.getPagatiCheck());
			
		/* =============================== CHECKBOX ABILITA/DISABILITA IMPORTO RENDICONTATO ============================== */
			try {
				Object session_pr = filtersMap == null ? null : filtersMap.get(SessionVariables.STATISTICHE_CHECK_IMPORTI_REND);
				if (rendicontatiCheck == null)
					statisticheCommand.setRendicontatiCheck((Boolean) session_pr);
				else
					statisticheCommand.setRendicontatiCheck(rendicontatiCheck);
			} catch (Exception e) {
				statisticheCommand.setRendicontatiCheck(Boolean.TRUE);
			}
			filtersMap.put(SessionVariables.STATISTICHE_CHECK_IMPORTI_REND, statisticheCommand.getRendicontatiCheck());
			
		/* =============================== CHECKBOX ABILITA/DISABILITA IMPORTO INCASSATO ================================ */
			try {
				Object session_pr = filtersMap == null ? null : filtersMap.get(SessionVariables.STATISTICHE_CHECK_IMPORTI_INCA);
				if (incassatiCheck == null)
					statisticheCommand.setIncassatiCheck((Boolean) session_pr);
				else
					statisticheCommand.setIncassatiCheck(incassatiCheck);
			} catch (Exception e) {
				statisticheCommand.setIncassatiCheck(Boolean.TRUE);
			}
			filtersMap.put(SessionVariables.STATISTICHE_CHECK_IMPORTI_INCA, statisticheCommand.getIncassatiCheck());
			
		/* ================================================= LISTA ANNI ============================================== */
			try {
				Object session_pr = filtersMap == null ? null : filtersMap.get(SessionVariables.STATISTICHE_LISTA_ANNI);
				if (listaAnni == null)
					statisticheCommand.setListaAnni((List<Integer>) session_pr);
				else
					statisticheCommand.setListaAnni(listaAnni);
			} catch (Exception e) {
				statisticheCommand.setListaAnni(null);
			}
			filtersMap.put(SessionVariables.STATISTICHE_LISTA_ANNI, statisticheCommand.getListaAnni());
			
		/* ===================================================== ANNO =============================================== */
			try {
				Object session_pr = filtersMap == null ? null : filtersMap.get(SessionVariables.STATISTICHE_ANNO);
				if (anno == null)
					statisticheCommand.setAnno((Integer) session_pr);
				else
					statisticheCommand.setAnno(anno);
			} catch (Exception e) {
				statisticheCommand.setAnno(null);
			}
			filtersMap.put(SessionVariables.STATISTICHE_ANNO, statisticheCommand.getAnno());
			
		/* ================================================= LISTA MESI ============================================== */
			try {
				Object session_pr = filtersMap == null ? null : filtersMap.get(SessionVariables.STATISTICHE_LISTA_MESI);
				if (listaMesi == null)
					statisticheCommand.setListaMesi((List<Integer>) session_pr);
				else
					statisticheCommand.setListaMesi(listaMesi);
			} catch (Exception e) {
				statisticheCommand.setListaMesi(null);
			}
			filtersMap.put(SessionVariables.STATISTICHE_LISTA_MESI, statisticheCommand.getListaMesi());
		
		/* ================================================= GIORNO DAL ============================================== */
			try {
				Object session_pr = filtersMap == null ? null : filtersMap.get(SessionVariables.STATISTICHE_GIORNO_DAL);
				if (giornoDal == null)
				   statisticheCommand.setGiornoDal((String) session_pr);
				else
					statisticheCommand.setGiornoDal(giornoDal);
			} catch (Exception e) {
				statisticheCommand.setGiornoDal("");
			}
			filtersMap.put(SessionVariables.STATISTICHE_GIORNO_DAL, statisticheCommand.getGiornoDal());
			
		/* ================================================= GIORNO AL =============================================== */
			try {
				Object session_pr = filtersMap == null ? null : filtersMap.get(SessionVariables.STATISTICHE_GIORNO_AL);
				if (giornoAl == null)
					statisticheCommand.setGiornoAl((String) session_pr);
				else
					statisticheCommand.setGiornoAl(giornoAl);
			} catch (Exception e) {
				statisticheCommand.setGiornoAl("");
			}
			filtersMap.put(SessionVariables.STATISTICHE_GIORNO_AL, statisticheCommand.getGiornoAl());
			
			
			/** 
			 * Rimetto in sessione l'oggetto in cui ho salvato i filtri della ricerca accertamento.
			 */
			SecurityContext.setEnteViewFilters(codIpaEnte, SessionVariables.ACTION_STATISTICA_AMG, filtersMap);
			
		}catch(Exception e){
			logger.warn("SET SESSION FILTER :: STATISTICHE :: ANNO/MESE/GIORNO :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: ERRORE ", e);
		}
	}
	
	/**
	 * Inizializza il bean di ricerca che mappa la form.
	 * 
	 * @param {@link CruscottoIncassiAnnoMeseGiornoCommand} statisticheCommand
	 */
	public static void initializeFilter(CruscottoIncassiAnnoMeseGiornoCommand statisticheCommand) {
		/**
		 * Se la proprietà è nulla, la inizializzo selezionando la tipologia statistica di default "Totali per Anno"
		 */
		if(statisticheCommand.getTipologia() == null) statisticheCommand.setTipologia(TIPO_STATISTICHE.ANNO);
		
		/**
		 * Se nessuna tipologia di filtro è attiva, di default abilito quella per anno.
		 */
		if(statisticheCommand.getAnnoCheck() == null && statisticheCommand.getAnnoMeseCheck() == null && statisticheCommand.getAnnoMeseGiornoCheck() == null){
			/* */
			statisticheCommand.setAnnoCheck(Boolean.FALSE);
			statisticheCommand.setAnnoMeseCheck(Boolean.FALSE);
			statisticheCommand.setAnnoMeseGiornoCheck(Boolean.FALSE);
			/* */
			if(statisticheCommand.getTipologia().equals(TIPO_STATISTICHE.ANNO))   statisticheCommand.setAnnoCheck(Boolean.TRUE);
			if(statisticheCommand.getTipologia().equals(TIPO_STATISTICHE.MESE))   statisticheCommand.setAnnoMeseCheck(Boolean.TRUE);
			if(statisticheCommand.getTipologia().equals(TIPO_STATISTICHE.GIORNO)) statisticheCommand.setAnnoMeseGiornoCheck(Boolean.TRUE);
		}
		
		/**
		 * Se l'ente ha abilitata la tesoreria e nessuna tipologia di importo è abilitata, di default le abilito tutte.
		 */
		if(SecurityContext.getEnte().getFlgTesoreria() && statisticheCommand.getPagatiCheck() == null && statisticheCommand.getRendicontatiCheck() == null && statisticheCommand.getIncassatiCheck() == null){
			/* */
			statisticheCommand.setPagatiCheck(Boolean.TRUE);
			/* */
			statisticheCommand.setRendicontatiCheck(Boolean.TRUE);
			/* */
			statisticheCommand.setIncassatiCheck(Boolean.TRUE);
		}else
			/**
			 * Se l'ente ha disabilitata la tesoreria e nessuna tipologia di importo è abilitata, di default abilito "Rendicontati" e "Incassati".
			 */
			if(!SecurityContext.getEnte().getFlgTesoreria() && statisticheCommand.getPagatiCheck() == null && statisticheCommand.getRendicontatiCheck() == null) {
				/* */
				statisticheCommand.setPagatiCheck(Boolean.TRUE);
				/* */
				statisticheCommand.setRendicontatiCheck(Boolean.TRUE);
			}
		
		/**
		 * Se la proprietà è nulla, la inizializzo selezionando di default gli ultimi 4 anni.
		 */
		if (statisticheCommand.getListaAnni() == null || statisticheCommand.getListaAnni().isEmpty()) {
			try {
				List<Integer> years = new ArrayList<Integer>();		Date data = new Date();
				
				years.add(Integer.parseInt(Constants.YYYY.format(data)));
				years.add(Integer.parseInt(Constants.YYYY.format(Utilities.addOrSubtractTime(data, Calendar.YEAR, -1))));
				years.add(Integer.parseInt(Constants.YYYY.format(Utilities.addOrSubtractTime(data, Calendar.YEAR, -2))));
				years.add(Integer.parseInt(Constants.YYYY.format(Utilities.addOrSubtractTime(data, Calendar.YEAR, -3))));
				
				statisticheCommand.setListaAnni(years);
			} catch (Exception e) {
				// Nothing to do
			}
		}else{
			/**
			 * Se, invece, è valorizzata la ordino in modo descrescente. 
			 */
			Collections.sort(statisticheCommand.getListaAnni(), Collections.reverseOrder());
		}
		
		/**
		 * Se la proprietà è nulla, la inizializzo selezionando l'anno corrente.
		 */
		if (statisticheCommand.getAnno() == null) {
			try {
				statisticheCommand.setAnno(Integer.parseInt(Constants.YYYY.format(new Date())));
			} catch (Exception e) {
				// Nothing to do
			}
		}
		
		/**
		 * Se la proprietà è nulla, la inizializzo selezionando tutti mesi dell'anno(individuato sopra) che intercorrono dal mese corrente 
		 * al mese di Gennaio.
		 */
		if (statisticheCommand.getListaMesi() == null || statisticheCommand.getListaMesi().isEmpty()) {
			try {
				List<Integer> months = new ArrayList<Integer>();
				
				/**
				 * Get anno corrente
				 */
				Calendar calNow = Calendar.getInstance();
				/**
				 * Get anno selezionato
				 */
				Calendar cal = Calendar.getInstance(); cal.set(Calendar.YEAR, statisticheCommand.getAnno());
				
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
				statisticheCommand.setListaMesi(months);
			} catch (Exception e) {
				// Nothing to do
			}
		}else{
			/**
			 * Se, invece, è valorizzata la ordino in modo descrescente. 
			 */
			Collections.sort(statisticheCommand.getListaMesi(), Collections.reverseOrder());
		}
		
		Date giornoDalFilter = null;

		if (StringUtils.hasText(statisticheCommand.getGiornoDal())) {
			try {
				giornoDalFilter = Constants.DDMMYYYY.parse(statisticheCommand.getGiornoDal());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date giornoAlFilter = null;

		if (StringUtils.hasText(statisticheCommand.getGiornoAl())) {
			try {
				giornoAlFilter = Constants.DDMMYYYY.parse(statisticheCommand.getGiornoAl());
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

		statisticheCommand.setGiornoDal(Constants.DDMMYYYY.format(giornoDalFilter));
		statisticheCommand.setGiornoAl(Constants.DDMMYYYY.format(giornoAlFilter));
	}
}