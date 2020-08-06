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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import it.regioneveneto.mygov.payment.mypivot.controller.command.CruscottoIncassiCapitoliCommand;
import it.regioneveneto.mygov.payment.mypivot.controller.utils.UtilitiesCtrl;
import it.regioneveneto.mygov.payment.mypivot.controller.validator.CruscottoIncassiValidator;
import it.regioneveneto.mygov.payment.mypivot.domain.to.AnagraficaUfficioCapitoloAccertamentoTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTipoDovutoTO;
import it.regioneveneto.mygov.payment.mypivot.service.AnagraficaUfficioCapitoloAccertamentoService;
import it.regioneveneto.mygov.payment.mypivot.service.OperatoreEnteTipoDovutoService;
import it.regioneveneto.mygov.payment.mypivot.service.VmStatisticaCapitoliService;
import it.regioneveneto.mygov.payment.mypivot.utils.Constants;
import it.regioneveneto.mygov.payment.mypivot.utils.SecurityContext;
import it.regioneveneto.mygov.payment.mypivot.utils.SessionVariables;

/**
 * Controller di gestione della statistica: TOTALI per CAPITOLI
 * 
 * @author Marianna Memoli
 */
@Controller
@RequestMapping("/protected/cruscottoIncassi")
public class CruscottoIncassiCapitoliController {

	private static Log logger = LogFactory.getLog(CruscottoIncassiCapitoliController.class);

	@Autowired
	private VmStatisticaCapitoliService vmStatisticheService;
	
	@Autowired
	private OperatoreEnteTipoDovutoService operatoreEnteTipoDovutoService;
	
	@Autowired
	private AnagraficaUfficioCapitoloAccertamentoService ufficiService;
	
	@Autowired
	private CruscottoIncassiValidator validator;
	
	public CruscottoIncassiCapitoliController() {
		super();
	}
	
	/**
	 * Restituisce la view di ricerca dei dati statistici (GET) per le statistiche. 
	 * -----------------------------------------------------------------------------------------------------------------------------------
	 * Alla view sono ritornati i seguenti oggetti:
	 * 
	 *   "responseResult":     Oggetto contenente i dati correlati alla statistica.
	 *   "statisticheCommand": Model attribute.
	 * 	 "listaTipiDovuti":    Elenco dei tipi dovuti per cui è possibile filtrare la ricerca. 
	 * 	 "listaUffici" :	   Elenco degli uffici per cui è possibile filtrare la ricerca. 
	 * 	 "showTableResult":    True o false a seconda se devo mostrare o meno la tabella. 
	 * 	 "flgTesoreria": 	   Se l'ente non ha abilitato la tesoreria, lato view non sarà possibile visualizzare gli importi incassati
	 * 
	 * @param {@link HttpServletRequest} request
	 * 
	 * @return {@link ModelAndView}
	 * @author Marianna Memoli
	 */
	@RequestMapping(value = { "/totaliRipartitiPerCapitoli.html" }, method = RequestMethod.GET)
	public ModelAndView get(HttpServletRequest request, Model model) {
		
		logger.debug("RICERCA :: STATISTICHE :: RIPARTITI per CAPITOLI :: GET :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: START");
	
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
			CruscottoIncassiCapitoliCommand command = new CruscottoIncassiCapitoliCommand();
			
			/**
			 * Resetto i filtri di ricerca salvati in sessione delle view relative le altre tipologie di statistica.
			 */
			SecurityContext.setEnteViewFilters(SecurityContext.getEnte().getCodIpaEnte(), SessionVariables.ACTION_STATISTICA_AMG, null);
			SecurityContext.setEnteViewFilters(SecurityContext.getEnte().getCodIpaEnte(), SessionVariables.ACTION_STATISTICA_UFFICI, null);
			SecurityContext.setEnteViewFilters(SecurityContext.getEnte().getCodIpaEnte(), SessionVariables.ACTION_STATISTICA_TIPI_DOVUTO, null);
			SecurityContext.setEnteViewFilters(SecurityContext.getEnte().getCodIpaEnte(), SessionVariables.ACTION_STATISTICA_ACCERTAMENTI, null);
			SecurityContext.setEnteViewFilters(SecurityContext.getEnte().getCodIpaEnte(), SessionVariables.ACTION_STATISTICA_DETTAGLIO_CRUSCOTTO, null);
			
			/**
			 * Prevalorizzo il bean di ricerca della form con gli ultimi valori dei filtri sottomessi e salvati in sessione.
			 */
			setSessionFilterToBean(request, SecurityContext.getEnte().getCodIpaEnte(), command, null, null, null, null, null, null, null, null);
						
			/**
			 * Avvio la ricerca
			 */
			mav = statisticaTotaliRipartitiPerCapitoli(command, request, null, Boolean.FALSE);
			
		}catch(Exception e){
			logger.error("RICERCA :: STATISTICHE :: RIPARTITI per CAPITOLI :: GET :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: ERRORE ", e);
			/* 
			 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
			 */
			return UtilitiesCtrl.getViewErrorMessage("mypivot.statistiche.errore.ricerca");
		}
		
		logger.debug("RICERCA :: STATISTICHE :: RIPARTITI per CAPITOLI :: GET :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: END");

		return mav;
	}
	
	/**
	 * Restituisce la view di ricerca dei dati statistici (POST). 
	 * -----------------------------------------------------------------------------------------------------------------------------------
	 * Alla view sono ritornati i seguenti oggetti:
	 * 
	 *   "responseResult":     Oggetto contenente i dati correlati alla statistica.
	 *   "statisticheCommand": Model attribute.
	 * 	 "listaTipiDovuti":    Elenco dei tipi dovuti per cui è possibile filtrare la ricerca. 
	 * 	 "listaUffici" :	   Elenco degli uffici per cui è possibile filtrare la ricerca. 
	 *   "showTableResult":    True o false a seconda se devo mostrare o meno la tabella. 
	 * 	 "flgTesoreria": 	   Se l'ente non ha abilitato la tesoreria, lato view non sarà possibile visualizzare gli importi incassati
	 * 
	 * @param {@link HttpServletRequest} request
	 * @param {@link CruscottoIncassiCapitoliCommand} command
	 * 
	 * @return {@link ModelAndView}
	 * @author Marianna Memoli
	 */
	@RequestMapping(value = { "/totaliRipartitiPerCapitoli.html" }, method = RequestMethod.POST)
	public ModelAndView post(@ModelAttribute("statisticheCommand") CruscottoIncassiCapitoliCommand command, HttpServletRequest request, BindingResult bindingResult) {

		logger.debug("RICERCA :: STATISTICHE :: RIPARTITI per CAPITOLI :: POST :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + ", command: " + command.toString() + "] :: START");

		ModelAndView mav = new ModelAndView();

		try{
			/**
			 * Avvio la ricerca
			 */
			mav = statisticaTotaliRipartitiPerCapitoli(command, request, bindingResult, Boolean.TRUE);
			
		    /**
		     * Salvo i nuovi filtri di ricerca impostati in sessione.
		     */
			if(SecurityContext.getEnte() != null && StringUtils.hasText(SessionVariables.ACTION_STATISTICA_CAPITOLI)){
				Map<String, Object> filtersMap = new HashMap<String, Object>();
					
					filtersMap.put(SessionVariables.STATISTICHE_CHECK_IMPORTI_PAGATI, 	command.getPagatiCheck());
					filtersMap.put(SessionVariables.STATISTICHE_CHECK_IMPORTI_REND,	  	command.getRendicontatiCheck());
					filtersMap.put(SessionVariables.STATISTICHE_CHECK_IMPORTI_INCA,  	command.getIncassatiCheck());
					filtersMap.put(SessionVariables.STATISTICHE_CHECK_ANNO,   	  		command.getAnnoCheck());
					filtersMap.put(SessionVariables.STATISTICHE_CHECK_ANNO_MESE,   	   	command.getAnnoMeseCheck());
					filtersMap.put(SessionVariables.STATISTICHE_CHECK_ANNO_MESE_GIORNO,	command.getAnnoMeseGiornoCheck());
					filtersMap.put(SessionVariables.STATISTICHE_ANNO, 		 	   		command.getAnno());
					filtersMap.put(SessionVariables.STATISTICHE_MESE, 		 	        command.getMese());
					filtersMap.put(SessionVariables.STATISTICHE_GIORNO, 		 	    command.getGiorno());
					filtersMap.put(SessionVariables.STATISTICHE_CODICE_UFFICIO, 	    command.getCodiceUfficio());
					filtersMap.put(SessionVariables.STATISTICHE_CODICE_TIPO_DOVUTO, 	command.getCodiceTipoDovuto());
					
				SecurityContext.setEnteViewFilters(SecurityContext.getEnte().getCodIpaEnte(), SessionVariables.ACTION_STATISTICA_CAPITOLI, filtersMap);
			}
			
		}catch(Exception e){
			logger.debug("RICERCA :: STATISTICHE :: RIPARTITI per ACCERTAMENTI :: POST :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + ", command: " + command.toString() + "] :: ERROR", e);
			/* 
			 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
			 */
			return UtilitiesCtrl.getViewErrorMessage("mypivot.statistiche.errore.ricerca");
		}
		
		logger.debug("RICERCA :: STATISTICHE :: RIPARTITI per ACCERTAMENTI :: POST :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + ", command: " + command.toString() + "] :: END");

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
	 *   "responseResult":     Oggetto contenente i dati correlati alla statistica.
	 *   "statisticheCommand": Model attribute.
	 * 	 "listaTipiDovuti":    Elenco dei tipi dovuti per cui è possibile filtrare la ricerca. 
	 * 	 "listaUffici" :	   Elenco degli uffici per cui è possibile filtrare la ricerca. 
	 *   "showTableResult":    True o false a seconda se devo mostrare o meno la tabella. 
	 * 	 "flgTesoreria": 	   Se l'ente non ha abilitato la tesoreria, lato view non sarà possibile visualizzare gli importi incassati
	 * 
	 * @param {@link HttpServletRequest} 			  request
	 * @param {@link CruscottoIncassiCapitoliCommand} command
	 * @param {@link Boolean} 						  doSubmit,  True o false a seconda se sto caricando la view o ne è stato fatto il submit.				
	 * 
	 * @return {@link ModelAndView}
	 * @author Marianna Memoli
	 */
	private ModelAndView statisticaTotaliRipartitiPerCapitoli(CruscottoIncassiCapitoliCommand command, HttpServletRequest request, BindingResult bindingResult, Boolean doSubmit) {

		logger.debug("RICERCA :: STATISTICA :: RIPARTITI per CAPITOLI :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: IN CORSO ");

		/* */
		ModelAndView mav = new ModelAndView("totaliRipartitiPerCapitoli");
		
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
			 * Restituisce l'elenco (in distinct) degli uffici dati l'identificativo ente e i tipi dovuto . 
			 */
			List<AnagraficaUfficioCapitoloAccertamentoTO> ufficiList = ufficiService.findDistinctUfficiByFilter(SecurityContext.getEnte().getId(), activeOperatoreEnteTdAsString, null);
			
			/**
			 * Inizializzo il bean per la ricerca
			 */
			initializeFilter(command);
			
			/**
			 * Per default valorizzo a false la varibile che mi indica se mostrare o meno la tabella di ricerca.
			 */
			Boolean showTableResult = Boolean.FALSE;
			
			/**
			 * Se "doSubmit" è false vuol dire che sto caricando la view per la prima volta, quindi non devo avviare la ricerca.
			 */
			if(doSubmit){
				
				/**
				 * Validazione form
				 */
				validator.validate(command, bindingResult);
				
				/**
				 * Check validation errors
				 */
				if (!bindingResult.hasErrors()) {
					/*
					 *  Il form è stato inviato senza errori, posso mostrare i risultati della ricerca
					 */
					showTableResult = Boolean.TRUE;	 
					/*
					 *  Init response to null
					 */
					Object responseResult = null; 	 
					
					/**
					 * In base ai filtri valorizzati individuo il service da chiamare.
					 */
					if(StringUtils.hasText(command.getCodiceTipoDovuto()) && StringUtils.hasText(command.getCodiceUfficio())){
						/**
						 * Filtro per Anno
						 */
						if(command.getAnnoCheck() != null && command.getAnnoCheck()){
							/* */
							responseResult = vmStatisticheService.getTotaliRipartitiPerCapitoliByAnnoUfficioDovuto(SecurityContext.getEnte().getId(), Integer.parseInt(command.getAnno()), command.getCodiceUfficio(), command.getCodiceTipoDovuto());
						}else
							/**
							 * Filtro per Anno e Mese
							 */
							if(command.getAnnoMeseCheck() != null && command.getAnnoMeseCheck()){
								/* */
								Date mese = Constants.MMYYYY.parse(command.getMese());
								/* */
								Calendar cal = Calendar.getInstance(); 		cal.setTime(mese);
								/* */
								responseResult = vmStatisticheService.getTotaliRipartitiPerCapitoliByAnnoMeseUfficioDovuto(SecurityContext.getEnte().getId(), cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, command.getCodiceUfficio(), command.getCodiceTipoDovuto());
							}
						else
							/**
							 * Filtro per Anno, Mese e Giorno 
							 */
							if(command.getAnnoMeseGiornoCheck() != null && command.getAnnoMeseGiornoCheck()){
								/* */
								Date giorno = Constants.DDMMYYYY.parse(command.getGiorno());
								/* */
								Calendar cal = Calendar.getInstance(); 		cal.setTime(giorno);
								/* */
								responseResult = vmStatisticheService.getTotaliRipartitiPerCapitoliByAnnoMeseGiornoUfficioDovuto(SecurityContext.getEnte().getId(), cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DAY_OF_MONTH), command.getCodiceUfficio(), command.getCodiceTipoDovuto());
							}
					}// close if dovuto e ufficio
					else
						if(StringUtils.hasText(command.getCodiceTipoDovuto())){
							/**
							 * Filtro per Anno
							 */
							if(command.getAnnoCheck() != null && command.getAnnoCheck()){
								/* */
								responseResult = vmStatisticheService.getTotaliRipartitiPerCapitoliByAnnoDovuto(SecurityContext.getEnte().getId(), Integer.parseInt(command.getAnno()), command.getCodiceTipoDovuto());
							}else
								/**
								 * Filtro per Anno e Mese
								 */
								if(command.getAnnoMeseCheck() != null && command.getAnnoMeseCheck()){
									/* */
									Date mese = Constants.MMYYYY.parse(command.getMese());
									/* */
									Calendar cal = Calendar.getInstance(); 		cal.setTime(mese);
									/* */
									responseResult = vmStatisticheService.getTotaliRipartitiPerCapitoliByAnnoMeseDovuto(SecurityContext.getEnte().getId(), cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, command.getCodiceTipoDovuto());
								}
							else
								/**
								 * Filtro per Anno, Mese e Giorno 
								 */
								if(command.getAnnoMeseGiornoCheck() != null && command.getAnnoMeseGiornoCheck()){
									/* */
									Date giorno = Constants.DDMMYYYY.parse(command.getGiorno());
									/* */
									Calendar cal = Calendar.getInstance(); 		cal.setTime(giorno);
									/* */
									responseResult = vmStatisticheService.getTotaliRipartitiPerCapitoliByAnnoMeseGiornoDovuto(SecurityContext.getEnte().getId(), cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DAY_OF_MONTH), command.getCodiceTipoDovuto());
								}
						}// close if dovuto
					else
						if(StringUtils.hasText(command.getCodiceUfficio())){
							/**
							 * Filtro per Anno
							 */
							if(command.getAnnoCheck() != null && command.getAnnoCheck()){
								/* */
								responseResult = vmStatisticheService.getTotaliRipartitiPerCapitoliByAnnoUfficio(SecurityContext.getEnte().getId(), Integer.parseInt(command.getAnno()), command.getCodiceUfficio());
							}else
								/**
								 * Filtro per Anno e Mese
								 */
								if(command.getAnnoMeseCheck() != null && command.getAnnoMeseCheck()){
									/* */
									Date mese = Constants.MMYYYY.parse(command.getMese());
									/* */
									Calendar cal = Calendar.getInstance(); 		cal.setTime(mese);
									/* */
									responseResult = vmStatisticheService.getTotaliRipartitiPerCapitoliByAnnoMeseUfficio(SecurityContext.getEnte().getId(), cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, command.getCodiceUfficio());
								}
							else
								/**
								 * Filtro per Anno, Mese e Giorno 
								 */
								if(command.getAnnoMeseGiornoCheck() != null && command.getAnnoMeseGiornoCheck()){
									/* */
									Date giorno = Constants.DDMMYYYY.parse(command.getGiorno());
									/* */
									Calendar cal = Calendar.getInstance(); 		cal.setTime(giorno);
									/* */
									responseResult = vmStatisticheService.getTotaliRipartitiPerCapitoliByAnnoMeseGiornoUfficio(SecurityContext.getEnte().getId(), cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DAY_OF_MONTH), command.getCodiceUfficio());
								}
						}// close if ufficio
					/**
					 * Add result to response view
					 */
					mav.addObject("responseResult", responseResult);
				}// close if validator
			}// close if doSubmit
			
			/**
			 * Set param da ritornare alla view
			 */
			mav.addObject("listaUffici", ufficiList);
			/* */
			mav.addObject("listaTipiDovuti", activeOperatoreEnteTdAsObj);
			/* */
			mav.addObject("flgTesoreria", SecurityContext.getEnte().getFlgTesoreria());
			/* */
			mav.addObject("showTableResult", showTableResult);
			/* */
			mav.addObject("statisticheCommand", command);

		}catch(Exception e){
			logger.error("RICERCA :: STATISTICA :: RIPARTITI per CAPITOLI :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: ERRORE ", e);
			/* 
			 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
			 */
			return UtilitiesCtrl.getViewErrorMessage("mypivot.statistiche.errore.ricerca");
		}
		
		logger.debug("RICERCA :: STATISTICA :: RIPARTITI per CAPITOLI :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: TERMINATA ");
		
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
	 * @param {@link String}			 codiceTipoDovuto, 	 Codice del tipo dovuto
	 * @param {@link String}			 codiceUfficio, 	 Codice ufficio
	 * @param {@link CruscottoIncassiCapitoliCommand} command, Model attribute
	 */
	private void setSessionFilterToBean(
			HttpServletRequest request, String codIpaEnte, CruscottoIncassiCapitoliCommand command, 
			Boolean pagatiCheck, Boolean rendicontatiCheck, Boolean incassatiCheck, String anno, String mese, String giorno,
			String codTipoDovuto, String codUfficio) {
		try{
			/** 
			 * Recupero dalla sessione l'oggetto in cui ho salvato i filtri della ricerca.
			 */
			Map<String, Object> filtersMap = SecurityContext.getEnteViewFilters(codIpaEnte, SessionVariables.ACTION_STATISTICA_CAPITOLI);
			
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
			
		/* ================================================= MESE ============================================== */
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
		
		/* ================================================= GIORNO ============================================== */
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
			
		/* ========================================== CODICE TIPO DOVUTO ======================================== */
			try {
				Object session_pr = filtersMap == null ? null : filtersMap.get(SessionVariables.STATISTICHE_CODICE_TIPO_DOVUTO);
				if (codTipoDovuto == null)
					command.setCodiceTipoDovuto((String) session_pr);
				else
					command.setCodiceTipoDovuto(codTipoDovuto);
			} catch (Exception e) {
				command.setCodiceTipoDovuto("");
			}
			filtersMap.put(SessionVariables.STATISTICHE_CODICE_TIPO_DOVUTO, command.getCodiceTipoDovuto());
			
		/* ============================================= CODICE UFFICIO ======================================== */
			try {
				Object session_pr = filtersMap == null ? null : filtersMap.get(SessionVariables.STATISTICHE_CODICE_UFFICIO);
				if (codUfficio == null)
					command.setCodiceUfficio((String) session_pr);
				else
					command.setCodiceUfficio(codTipoDovuto);
			} catch (Exception e) {
				command.setCodiceUfficio("");
			}
			filtersMap.put(SessionVariables.STATISTICHE_CODICE_UFFICIO, command.getCodiceUfficio());
			
			/** 
			 * Rimetto in sessione l'oggetto in cui ho salvato i filtri della ricerca accertamento.
			 */
			SecurityContext.setEnteViewFilters(codIpaEnte, SessionVariables.ACTION_STATISTICA_CAPITOLI, filtersMap);
			
		}catch(Exception e){
			logger.warn("SET SESSION FILTER :: STATISTICHE :: RIPARTITI per CAPITOLI :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: ERRORE ", e);
		}
	}
	
	/**
	 * Inizializza il bean di ricerca.
	 * 
	 * @param {@link CruscottoIncassiCapitoliCommand} command
	 */
	public static void initializeFilter(CruscottoIncassiCapitoliCommand command) {
		
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