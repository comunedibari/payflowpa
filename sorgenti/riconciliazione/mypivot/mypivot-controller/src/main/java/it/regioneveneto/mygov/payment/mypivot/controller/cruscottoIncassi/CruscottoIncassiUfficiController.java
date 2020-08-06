package it.regioneveneto.mygov.payment.mypivot.controller.cruscottoIncassi;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import it.regioneveneto.mygov.payment.mypivot.controller.command.CruscottoIncassiUfficiCommand;
import it.regioneveneto.mygov.payment.mypivot.controller.utils.UtilitiesCtrl;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.cruscottoIncassi.CruscottoIncassiDto;
import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTipoDovutoTO;
import it.regioneveneto.mygov.payment.mypivot.service.OperatoreEnteTipoDovutoService;
import it.regioneveneto.mygov.payment.mypivot.service.VmStatisticaUfficiService;
import it.regioneveneto.mygov.payment.mypivot.utils.Constants;
import it.regioneveneto.mygov.payment.mypivot.utils.SecurityContext;
import it.regioneveneto.mygov.payment.mypivot.utils.SessionVariables;

/**
 * Controller di gestione della statistica: TOTALI per UFFICI
 * 
 * @author Marianna Memoli
 */
@Controller
@RequestMapping("/protected/cruscottoIncassi")
public class CruscottoIncassiUfficiController {

	private static Log logger = LogFactory.getLog(CruscottoIncassiUfficiController.class);

	@Autowired
	private VmStatisticaUfficiService vmStatisticheService;
	
	@Autowired
	private OperatoreEnteTipoDovutoService operatoreEnteTipoDovutoService;
	
	public CruscottoIncassiUfficiController() {
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
	 * 
	 * @return {@link ModelAndView}
	 * @author Marianna Memoli
	 */
	@RequestMapping(value = { "/totaliRipartitiPerUffici.html" }, method = RequestMethod.GET)
	public ModelAndView get(HttpServletRequest request, Model model) {
		
		logger.debug("RICERCA :: STATISTICHE :: RIPARTITI per UFFICI :: GET :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: START");
	
		/* */
		ModelAndView mav = new ModelAndView();
		
		try{
			/**
			 * La funzione verifica che l'utente autenticato abbia selezionato l'ente.
			 */
			if(!UtilitiesCtrl.hasSecurityEnte()) return new ModelAndView(new RedirectView(request.getContextPath() + Constants.PATH_PAGE_SCELTA_ENTE));
		
			/**
			 * Instance Bean form di ricerca
			 */
			CruscottoIncassiUfficiCommand command = new CruscottoIncassiUfficiCommand();
			
			/**
			 * Resetto i filtri di ricerca salvati in sessione delle view relative le altre tipologie di statistica.
			 */
			SecurityContext.setEnteViewFilters(SecurityContext.getEnte().getCodIpaEnte(), SessionVariables.ACTION_STATISTICA_AMG, null);
			SecurityContext.setEnteViewFilters(SecurityContext.getEnte().getCodIpaEnte(), SessionVariables.ACTION_STATISTICA_TIPI_DOVUTO, null);
			SecurityContext.setEnteViewFilters(SecurityContext.getEnte().getCodIpaEnte(), SessionVariables.ACTION_STATISTICA_CAPITOLI, null);
			SecurityContext.setEnteViewFilters(SecurityContext.getEnte().getCodIpaEnte(), SessionVariables.ACTION_STATISTICA_ACCERTAMENTI, null);
			SecurityContext.setEnteViewFilters(SecurityContext.getEnte().getCodIpaEnte(), SessionVariables.ACTION_STATISTICA_DETTAGLIO_CRUSCOTTO, null);
					
			/**
			 * Prevalorizzo il bean di ricerca della form con gli ultimi valori dei filtri sottomessi e salvati in sessione.
			 */
			setSessionFilterToBean(request, SecurityContext.getEnte().getCodIpaEnte(), command, null, null, null, null, null, null);
						
			/**
			 * Avvio la ricerca
			 */
			mav = statisticaTotaliRipartitiPerUffici(command, request);
			
		}catch(Exception e){
			logger.error("RICERCA :: STATISTICHE :: RIPARTITI per UFFICI :: GET :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: ERRORE ", e);
			/* 
			 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
			 */
			return UtilitiesCtrl.getViewErrorMessage("mypivot.statistiche.errore.ricerca");
		}
		
		logger.debug("RICERCA :: STATISTICHE :: RIPARTITI per UFFICI :: GET :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: END");

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
	 * @param {@link CruscottoIncassiUfficiCommand} command
	 * 
	 * @return {@link ModelAndView}
	 * @author Marianna Memoli
	 */
	@RequestMapping(value = { "/totaliRipartitiPerUffici.html" }, method = RequestMethod.POST)
	public ModelAndView post(@ModelAttribute("statisticheCommand") CruscottoIncassiUfficiCommand command, HttpServletRequest request) {

		logger.debug("RICERCA :: STATISTICHE :: RIPARTITI per UFFICI :: POST :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + ", command: " + command.toString() + "] :: START");

		ModelAndView mav = new ModelAndView();

		try{
			/**
			 * Avvio la ricerca
			 */
			mav = statisticaTotaliRipartitiPerUffici(command, request);
			
		    /**
		     * Salvo i nuovi filtri di ricerca impostati in sessione.
		     */
			if(SecurityContext.getEnte() != null && StringUtils.hasText(SessionVariables.ACTION_STATISTICA_UFFICI)){
				Map<String, Object> filtersMap = new HashMap<String, Object>();
					
					filtersMap.put(SessionVariables.STATISTICHE_CHECK_IMPORTI_PAGATI, 	command.getPagatiCheck());
					filtersMap.put(SessionVariables.STATISTICHE_CHECK_IMPORTI_REND,	  	command.getRendicontatiCheck());
					filtersMap.put(SessionVariables.STATISTICHE_CHECK_IMPORTI_INCA,   	command.getIncassatiCheck());
					filtersMap.put(SessionVariables.STATISTICHE_CHECK_ANNO,   	  		command.getAnnoCheck());
					filtersMap.put(SessionVariables.STATISTICHE_CHECK_ANNO_MESE,   	   	command.getAnnoMeseCheck());
					filtersMap.put(SessionVariables.STATISTICHE_CHECK_ANNO_MESE_GIORNO, command.getAnnoMeseGiornoCheck());
					filtersMap.put(SessionVariables.STATISTICHE_ANNO, 		  			command.getAnno());
					filtersMap.put(SessionVariables.STATISTICHE_MESE, 		  			command.getMese());
					filtersMap.put(SessionVariables.STATISTICHE_GIORNO, 		  		command.getGiorno());
					
				SecurityContext.setEnteViewFilters(SecurityContext.getEnte().getCodIpaEnte(), SessionVariables.ACTION_STATISTICA_UFFICI, filtersMap);
			}
			
		}catch(Exception e){
			logger.debug("RICERCA :: STATISTICHE :: RIPARTITI per UFFICI :: POST :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + ", command: " + command.toString() + "] :: ERROR", e);
			/* 
			 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
			 */
			return UtilitiesCtrl.getViewErrorMessage("mypivot.statistiche.errore.ricerca");
		}
		
		logger.debug("RICERCA :: STATISTICHE :: RIPARTITI per UFFICI :: POST :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + ", command: " + command.toString() + "] :: END");

		return mav;
	}
	
	/**
	 * Funzione che gestisce le fasi comuni della ricerca dei dati statistici avviata sia in modalità GET che POST.
	 * -----------------------------------------------------------------------------------------------------------------------------------
	 * Prima di erogare la pagina effettuo dei controlli per determinare se sussistono le condizioni tali perchè l'utente possa accedere
	 * alla funzionalità.
	 * 
	 * I controlli eseguiti sono i seguenti:
	 *   1. L'utente deve aver selezionato l'ente beneficiario, se mancante verrà rediretto alla pagina di scelta dell'ente.
	 *   2. L'utente deve avere ruoli attivi, se mancante verrà rediretto alla pagina di scelta dell'ente.
	 *   3. L'utente deve avere uno dei ruoli utili ad accedere alla funzionalità delle "Statistiche" (ROLE_ADMIN e/o ROLE_STATS).
	 *   4. L'utente deve essere operatore (attivo) per almeno un tipo dovuto dell'ente, se mancante mostro un messaggio di errore (Es: Utente non autorizzato)
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
	 * @param {@link CruscottoIncassiUfficiCommand} command
	 * 
	 * @return {@link ModelAndView}
	 * @author Marianna Memoli
	 */
	private ModelAndView statisticaTotaliRipartitiPerUffici(CruscottoIncassiUfficiCommand command, HttpServletRequest request) {

		logger.debug("RICERCA :: STATISTICA :: RIPARTITI per UFFICI :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: IN CORSO ");

		/* */
		ModelAndView mav = new ModelAndView("totaliRipartitiPerUffici");
		
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
			 * Recupero la lista dei tipi dovuto dell'ente per cui l'utente è un operatore attivo. 
			 * Se la lista è vuota, mostro un messaggio di errore perchè vuol dire che l'utente non può consultare i dati statistici.
			 */
			List<EnteTipoDovutoTO> activeOperatoreEnteTdAsObj = operatoreEnteTipoDovutoService.getListaEnteTipoDovutoForOperatoreAndCodIpaEnte(SecurityContext.getUtente().getCodFedUserId(), SecurityContext.getEnte().getCodIpaEnte());
			
			if (activeOperatoreEnteTdAsObj == null || activeOperatoreEnteTdAsObj.isEmpty()) {
				logger.warn("RICERCA :: STATISTICA :: RIPARTITI per UFFICI :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: NON risulta OPERATORE per nessun tipo dovuto.");
				/* 
				 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
				 */
				return UtilitiesCtrl.getViewErrorMessage("mypivot.messages.error.nessunTipoDovutoAssegnato");
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
			initializeFilter(command);
			
			/* */
			CruscottoIncassiDto result = null;
			
			/**
			 * Eseguo la query di ricerca
			 */
			 if(command.getAnnoCheck() != null && command.getAnnoCheck())
				/**
				 * Filtro per Anno
				 */
				result = vmStatisticheService.getTotaliRipartitiPerUfficiByAnno(SecurityContext.getEnte().getId(), Integer.parseInt(command.getAnno()), activeOperatoreEnteTdAsString);
			 else
				 if(command.getAnnoMeseCheck() != null && command.getAnnoMeseCheck()){
					/**
					 * Filtro per Anno e Mese
					 */
					Date mese = Constants.MMYYYY.parse(command.getMese());
					/* */
					Calendar cal = Calendar.getInstance(); 		cal.setTime(mese);
					/* */
					result = vmStatisticheService.getTotaliRipartitiPerUfficiByAnnoMese(SecurityContext.getEnte().getId(), cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, activeOperatoreEnteTdAsString);
				 }
			 else
				 if(command.getAnnoMeseGiornoCheck()  != null && command.getAnnoMeseGiornoCheck()){
					/**
					 * Filtro per Anno, Mese e Giorno 
					 */
					Date giorno = Constants.DDMMYYYY.parse(command.getGiorno());
					/* */
					Calendar cal = Calendar.getInstance(); 		cal.setTime(giorno);
					/* */
					result = vmStatisticheService.getTotaliRipartitiPerUfficiByAnnoMeseGiorno(SecurityContext.getEnte().getId(), cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DAY_OF_MONTH), activeOperatoreEnteTdAsString);
				 }
			 
			/**
			 * Set param da ritornare alla view
			 */
			mav.addObject("responseResult", result);
			/* */
			mav.addObject("flgTesoreria", SecurityContext.getEnte().getFlgTesoreria());
			/* */
			mav.addObject("statisticheCommand", command);

		}catch(Exception e){
			logger.error("RICERCA :: STATISTICA :: RIPARTITI per UFFICI :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: ERRORE ", e);
			/* 
			 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
			 */
			return UtilitiesCtrl.getViewErrorMessage("mypivot.statistiche.errore.ricerca");
		}
		
		logger.debug("RICERCA :: STATISTICA :: RIPARTITI per UFFICI :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: TERMINATA ");
		
		return mav;
	}
	
	/**
	 * La funzione valorizza il bean di ricerca con gli ultimi valori dei filtri sottomessi e salvati.
	 * 
	 * @param {@link HttpServletRequest} request
	 * @param {@link String}  			 codIpaEnte,  	  	 Codice Ipa dell'ente selezionato
	 * @param {@link Boolean}   		 pagatiCheck, 		 Visualizzazione dell'Importo Pagato
	 * @param {@link Boolean}   		 rendicontatiCheck,  Visualizzazione dell'Importo Rendicontato
	 * @param {@link Boolean}   		 incassatiCheck, 	 Visualizzazione dell'Importo Incassato
	 * @param {@link String}			 anno, 		 		 L'anno per cui filtrare l'estrazione del dato statistico
	 * @param {@link String}			 mese, 		 		 Il mese per cui filtrare l'estrazione del dato statistico
	 * @param {@link String}			 giorno, 		 	 Il giorno per cui filtrare l'estrazione del dato statistico
	 * @param {@link CruscottoIncassiUfficiCommand} command, Model attribute
	 */
	private void setSessionFilterToBean(
			HttpServletRequest request, String codIpaEnte, CruscottoIncassiUfficiCommand command, 
			Boolean pagatiCheck, Boolean rendicontatiCheck, Boolean incassatiCheck, String anno, String mese, String giorno) {
		try{
			/** 
			 * Recupero dalla sessione l'oggetto in cui ho salvato i filtri della ricerca.
			 */
			Map<String, Object> filtersMap = SecurityContext.getEnteViewFilters(codIpaEnte, SessionVariables.ACTION_STATISTICA_UFFICI);
			
			if (filtersMap == null) filtersMap = new HashMap<String, Object>();
			
		 /* ================================== CHECKBOX ABILITA/DISABILITA IMPORTO PAGATO ================================ */
			try {
				Object session_pr = filtersMap == null ? null : filtersMap.get(SessionVariables.STATISTICHE_CHECK_IMPORTI_PAGATI);
				if (pagatiCheck == null)
					command.setPagatiCheck((Boolean) session_pr);
				else
					command.setPagatiCheck(pagatiCheck);
			} catch (Exception e) {
				command.setPagatiCheck(Boolean.TRUE);
			}
			filtersMap.put(SessionVariables.STATISTICHE_CHECK_IMPORTI_PAGATI, command.getPagatiCheck());
			
		/* =============================== CHECKBOX ABILITA/DISABILITA IMPORTO RENDICONTATO ============================== */
			try {
				Object session_pr = filtersMap == null ? null : filtersMap.get(SessionVariables.STATISTICHE_CHECK_IMPORTI_REND);
				if (rendicontatiCheck == null)
					command.setRendicontatiCheck((Boolean) session_pr);
				else
					command.setRendicontatiCheck(rendicontatiCheck);
			} catch (Exception e) {
				command.setRendicontatiCheck(Boolean.TRUE);
			}
			filtersMap.put(SessionVariables.STATISTICHE_CHECK_IMPORTI_REND, command.getRendicontatiCheck());
			
		/* =============================== CHECKBOX ABILITA/DISABILITA IMPORTO INCASSATO ================================ */
			try {
				Object session_pr = filtersMap == null ? null : filtersMap.get(SessionVariables.STATISTICHE_CHECK_IMPORTI_INCA);
				if (incassatiCheck == null)
					command.setIncassatiCheck((Boolean) session_pr);
				else
					command.setIncassatiCheck(incassatiCheck);
			} catch (Exception e) {
				command.setIncassatiCheck(Boolean.TRUE);
			}
			filtersMap.put(SessionVariables.STATISTICHE_CHECK_IMPORTI_INCA, command.getIncassatiCheck());
			
		/* ===================================================== ANNO =============================================== */
			try {
				Object session_pr = filtersMap == null ? null : filtersMap.get(SessionVariables.STATISTICHE_ANNO);
				if (anno == null)
					command.setAnno((String) session_pr);
				else
					command.setAnno(anno);
			} catch (Exception e) {
				command.setAnno("");
			}
			filtersMap.put(SessionVariables.STATISTICHE_ANNO, command.getAnno());
			
		/* =================================================== MESE ================================================ */
			try {
				Object session_pr = filtersMap == null ? null : filtersMap.get(SessionVariables.STATISTICHE_MESE);
				if (mese == null)
					command.setMese((String) session_pr);
				else
					command.setMese(mese);
			} catch (Exception e) {
				command.setMese("");
			}
			filtersMap.put(SessionVariables.STATISTICHE_MESE, command.getMese());
		
		/* ================================================= GIORNO ================================================ */
			try {
				Object session_pr = filtersMap == null ? null : filtersMap.get(SessionVariables.STATISTICHE_GIORNO);
				if (giorno == null)
				    command.setGiorno((String) session_pr);
				else
					command.setGiorno(giorno);
			} catch (Exception e) {
				command.setGiorno("");
			}
			filtersMap.put(SessionVariables.STATISTICHE_GIORNO, command.getGiorno());
			
			
			/** 
			 * Rimetto in sessione l'oggetto in cui ho salvato i filtri della ricerca accertamento.
			 */
			SecurityContext.setEnteViewFilters(codIpaEnte, SessionVariables.ACTION_STATISTICA_UFFICI, filtersMap);
			
		}catch(Exception e){
			logger.warn("SET SESSION FILTER :: STATISTICHE :: RIPARTITI per UFFICI :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: ERRORE ", e);
		}
	}
	
	/**
	 * Inizializza il bean di ricerca.
	 * 
	 * @param {@link CruscottoIncassiUfficiCommand} command
	 */
	public static void initializeFilter(CruscottoIncassiUfficiCommand command) {
		/**
		 * Se nessuna tipologia di filtro è attiva, di default abilito quella per anno.
		 */
		if(command.getAnnoCheck() == null && command.getAnnoMeseCheck() == null && command.getAnnoMeseGiornoCheck() == null){
			/* */
			command.setAnnoCheck(Boolean.TRUE);
			/* */
			command.setAnnoMeseCheck(Boolean.FALSE);
			/* */
			command.setAnnoMeseGiornoCheck(Boolean.FALSE);
		}
		
		/**
		 * Se l'ente ha abilitata la tesoreria e nessuna tipologia di importo è abilitata, di default le abilito tutte.
		 */
		if(SecurityContext.getEnte().getFlgTesoreria() && command.getPagatiCheck() == null && command.getRendicontatiCheck() == null && command.getIncassatiCheck() == null){
			/* */
			command.setPagatiCheck(Boolean.TRUE);
			/* */
			command.setRendicontatiCheck(Boolean.TRUE);
			/* */
			command.setIncassatiCheck(Boolean.TRUE);
		}else
			/**
			 * Se l'ente ha disabilitata la tesoreria e nessuna tipologia di importo è abilitata, di default abilito "Rendicontati" e "Incassati".
			 */
			if(!SecurityContext.getEnte().getFlgTesoreria() && command.getPagatiCheck() == null && command.getRendicontatiCheck() == null) {
				/* */
				command.setPagatiCheck(Boolean.TRUE);
				/* */
				command.setRendicontatiCheck(Boolean.TRUE);
			}
		
		/**
		 * Se la proprietà è nulla, la inizializzo selezionando l'anno corrente.
		 */
		if (!StringUtils.hasText(command.getAnno())) {
			try {
				command.setAnno(Constants.YYYY.format(new Date()));
			} catch (Exception e) {
				// Nothing to do
			}
		}
		
		/**
		 * Se la proprietà è nulla, la inizializzo selezionando il mese corrente.
		 */
		if (!StringUtils.hasText(command.getMese())) {
			try {
				command.setMese(Constants.MMYYYY.format(new Date()));
			} catch (Exception e) {
				// Nothing to do
			}
		}
		
		/**
		 * Se la proprietà è nulla, la inizializzo selezionando il giorno corrente.
		 */
		if (!StringUtils.hasText(command.getGiorno())) {
			try {
				command.setGiorno(Constants.DDMMYYYY.format(new Date()));
			} catch (Exception e) {
				// Nothing to do
			}
		}
	}
}