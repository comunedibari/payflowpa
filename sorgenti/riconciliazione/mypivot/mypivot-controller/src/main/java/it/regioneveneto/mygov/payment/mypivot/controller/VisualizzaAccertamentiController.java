package it.regioneveneto.mygov.payment.mypivot.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import it.regioneveneto.mygov.payment.mypivot.controller.command.AccertamentiVisualizzaCommand;
import it.regioneveneto.mygov.payment.mypivot.controller.utils.AccertamentoUtils;
import it.regioneveneto.mygov.payment.mypivot.controller.utils.UtilitiesCtrl;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.AccertamentoDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.MessagesDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.PageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.to.AnagraficaStatoTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTipoDovutoTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.UtenteTO;
import it.regioneveneto.mygov.payment.mypivot.service.AccertamentoService;
import it.regioneveneto.mygov.payment.mypivot.service.AnagraficaStatoService;
import it.regioneveneto.mygov.payment.mypivot.service.OperatoreEnteTipoDovutoService;
import it.regioneveneto.mygov.payment.mypivot.service.UtenteService;
import it.regioneveneto.mygov.payment.mypivot.utils.Constants;
import it.regioneveneto.mygov.payment.mypivot.utils.SecurityContext;
import it.regioneveneto.mygov.payment.mypivot.utils.SessionVariables;

/**
 * Controller della form presente nella pagina di Ricerca degli Accertamenti.
 * 
 * @author Marianna Memoli
 */
@Controller
@RequestMapping("/protected/accertamenti")
public class VisualizzaAccertamentiController {

	private static Log logger = LogFactory.getLog(VisualizzaAccertamentiController.class);

	@Autowired
	private OperatoreEnteTipoDovutoService operatoreEnteTipoDovutoService;
	
	@Autowired
	private AnagraficaStatoService anagraficaStatoService;

	@Autowired
	private UtenteService utenteService;
	
	@Autowired
	private AccertamentoService accertamentoService;
	
	public VisualizzaAccertamentiController() {
		super();
	}
	
	/**
	 * Ritorna alla View l'elenco delle tipologie di stato che possono essere assegnate all'accertamento.
	 * Usato per popolare le combo.
	 * 
	 * @return {@link List<AnagraficaStatoTO>}
	 * @author Marianna Memoli
	 */
	@ModelAttribute("statiList")
    public List<AnagraficaStatoTO> populateStatiAccertamento() {
        return anagraficaStatoService.getAllByTipo(Constants.DE_TIPO_STATO_ACCERTAMENTO);
    }

	/**
	 * Restituisce la view di ricerca degli accertamenti (GET). 
	 * -----------------------------------------------------------------------------------------------------------------------------------
	 * Prima di erogare la pagina effettuo dei controlli per determinare se sussistono le condizioni tali perchè l'utente possa accedere
	 * alla funzionalità.
	 * 
	 * I controlli eseguiti sono i seguenti:
	 *   1. L'utente deve aver selezionato l'ente beneficiario, se mancante verrà rediretto alla pagina di scelta dell'ente.
	 *   2. L'utente deve avere ruoli attivi, se mancante verrà rediretto alla pagina di scelta dell'ente.
	 *   3. L'utente deve avere uno dei ruoli utili ad accedere alla funzionalità di "Accertamento" (ROLE_ADMIN e/o ROLE_ACC).
	 *   4. L'utente deve essere operatore (attivo) per almeno un tipo dovuto dell'ente, se mancante mostro un messaggio di errore (Es: Utente non autorizzato)
	 * 
	 * A seguito dei controlli, istanzio il bean che mappa i filtri di ricerca ed eventulamente lo prevalorizzo con i filtri dell'ultima ricerca avviata
	 * che ho salvato in sessione. Avvio la ricerca.
	 * 
	 * Alla view sono ritornati i seguenti oggetti:
	 * 
	 *   "responseDtoPage": Oggetto con la lista pagina degli accertameti trovati
	 *   "listaTipiDovuti": Elenco dei tipi dovuti per cui è possibile filtrare la ricerca 
	 * 
	 * @param {@link HttpServletRequest} request
	 * @param {@link String} 			 page, 		Pagina da visualizzare
	 * @param {@link String} 			 pageSize,  Numero di elementi per pagina
	 * 
	 * @return {@link ModelAndView}
	 * 
	 * @author Marianna Memoli
	 */
	@RequestMapping(value = { "/ricerca.html" }, method = RequestMethod.GET)
	public ModelAndView get(HttpServletRequest request, Model model, @RequestParam(required = false) String pg, @RequestParam(required = false) String pgSize) {
		
		logger.debug("RICERCA :: ACCERTAMENTI :: GET :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: START");
		/* */
		ModelAndView mav = new ModelAndView();
		
		try{
			/**
			 * La funzione verifica che l'utente autenticato abbia selezionato l'ente.
			 */
			if(!UtilitiesCtrl.hasSecurityEnte()) return new ModelAndView(new RedirectView(request.getContextPath() + Constants.PATH_PAGE_SCELTA_ENTE));
		
			/*
			 * Resetto i filtri di ricerca delle view di:
			 * 
			 *  - Dettaglio Accertamento; 		- Aggiungi Pagamenti;		- Rimuovi Pagamenti;
			 */
			SecurityContext.setEnteViewFilters(SecurityContext.getEnte().getCodIpaEnte(), SessionVariables.ACTION_ACCERTAMENTO_RICERCA_PAGAMENTI, null);
			SecurityContext.setEnteViewFilters(SecurityContext.getEnte().getCodIpaEnte(), SessionVariables.ACTION_ACCERTAMENTO_RICERCA_PAGAMENTI_ADD, null);
			SecurityContext.setEnteViewFilters(SecurityContext.getEnte().getCodIpaEnte(), SessionVariables.ACTION_ACCERTAMENTO_RICERCA_PAGAMENTI_DELETE, null);
			
			/**
			 * Instance Bean form di ricerca
			 */
			AccertamentiVisualizzaCommand visualizzaAccertamentiCommand = new AccertamentiVisualizzaCommand();
			
			/**
			 * Prevalorizzo il bean di ricerca della form con gli ultimi valori dei filtri sottomessi e salvati in sessione.
			 */
			AccertamentoUtils.setSessionFilterAccertamentoToBean(request, SecurityContext.getEnte().getCodIpaEnte(), null, null, null, null, null, null, null, pg, pgSize, visualizzaAccertamentiCommand);
			
			/**
			 * Avvio la ricerca
			 */
			mav = ricerca(visualizzaAccertamentiCommand, request);
			 
		   /** REDIRECT INFO: ================================================================================================================== */
			/**
			 * L'istruzione "model.containsAttribute('notAuthorizedClosedEmptyAcc')" mi serve per verificare se è stato passato il parametro 
			 * "notAuthorizedClosedEmptyAcc" da una redirect view cosi da mostrare il messaggio di errore appropriato nella VIEW che sarà caricata.
			 * 
			 * In particolare il parametro "notAuthorizedClosedEmptyAcc" è gestito per quando l'utente ha tentato di chiudere un'accertamento 
			 * per il quale non sono stati aggiunti pagamenti.
			 *    
			 * Il valore atteso del parametro è un boolean.
			 */    
			
			/* Leggo il parametro dagli attributi */
			Boolean notAuthorizedClosedEmptyAcc = (model != null && model.containsAttribute("notAuthorizedClosedEmptyAcc")) ? (Boolean) model.asMap().get("notAuthorizedClosedEmptyAcc") : false;
				
			/* Valorizzando l'attributo, lato view so di dover mostrare dei messaggi in popup o meno */
			mav.addObject("notAuthorizedClosedEmptyAcc", notAuthorizedClosedEmptyAcc);
		   /** ======================================================================================================================== */
			
		}catch(Exception e){
			logger.error("RICERCA :: ACCERTAMENTI :: GET :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: ERRORE ", e);
			/* 
			 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
			 */
			return UtilitiesCtrl.getViewErrorMessage("mypivot.accertamenti.errore.ricerca");
		}
		
		logger.debug("RICERCA :: ACCERTAMENTI :: GET :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: END");

		return mav;
	}

	/**
	 * Restituisce alla view di ricerca degli accertamenti (POST), l'elenco di accertamenti che rispettano i criteri di ricerca impostati
	 * da interfaccia. 
	 * -----------------------------------------------------------------------------------------------------------------------------------
	 * Prima di erogare la pagina effettuo dei controlli per determinare se sussistono le condizioni tali perchè l'utente possa accedere
	 * alla funzionalità.
	 * 
	 * I controlli eseguiti sono i seguenti:
	 *   1. L'utente deve aver selezionato l'ente beneficiario, se mancante verrà rediretto alla pagina di scelta dell'ente.
	 *   2. L'utente deve avere ruoli attivi, se mancante verrà rediretto alla pagina di scelta dell'ente.
	 *   3. L'utente deve avere uno dei ruoli utili ad accedere alla funzionalità di "Accertamento" (ROLE_ADMIN e/o ROLE_ACC).
	 *   4. L'utente deve essere operatore (attivo) per almeno un tipo dovuto dell'ente, se mancante mostro un messaggio di errore (Es: Utente non autorizzato)
	 * 
	 * Avvio la ricerca, filtrando secondo i filtri sottomessi nella form di ricerca.
	 * 
	 * Alla view sono ritornati i seguenti oggetti:
	 * 
	 *   "responseDtoPage": Oggetto con la lista paginata degli accertameti trovati
	 *   "listaTipiDovuti": Elenco dei tipi dovuti per cui è possibile filtrare la ricerca 
	 * 
	 * @param {@link HttpServletRequest} 			request
	 * @param {@link AccertamentiVisualizzaCommand} visualizzaAccertamentiCommand, Bean della form di ricerca
	 * 
	 * @return {@link ModelAndView}
	 * @author Marianna Memoli
	 */
	@RequestMapping(value = { "/ricerca.html" }, method = RequestMethod.POST)
	public ModelAndView post(@ModelAttribute("visualizzaAccertamentiCommand") AccertamentiVisualizzaCommand visualizzaAccertamentiCommand, HttpServletRequest request) {

		logger.debug("RICERCA :: ACCERTAMENTI :: POST :: Fields[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + ", visualizzaAccertamentiCommand" + visualizzaAccertamentiCommand.toString() + "] :: START");
	
		ModelAndView mav = new ModelAndView();
		
		try{
			/*
			 * E' stata avviata una nuova ricerca, con nuovi filtri, reimposto la pagina di partenza alla prima.
			 */
			visualizzaAccertamentiCommand.setPage(1);
			/*
			 * Avvio la ricerca
			 */
			mav = ricerca(visualizzaAccertamentiCommand, request);
			
		   // Salvo i nuovi filtri di ricerca impostati in sessione.
			if(SecurityContext.getEnte() != null){
				Map<String, Object> filtersMap = new HashMap<String, Object>();
					
					filtersMap.put(SessionVariables.ACCERTAMENTO_PAGE_SIZE, 		 visualizzaAccertamentiCommand.getPageSize());
					filtersMap.put(SessionVariables.ACCERTAMENTO_PAGE, 				 visualizzaAccertamentiCommand.getPage());
					filtersMap.put(SessionVariables.ACCERTAMENTO_CODICE_IUV,		 visualizzaAccertamentiCommand.getCodiceIuv());
					filtersMap.put(SessionVariables.ACCERTAMENTO_DATA_AGG_DAL, 		 visualizzaAccertamentiCommand.getDataUltimoAggDa());
					filtersMap.put(SessionVariables.ACCERTAMENTO_DATA_AGG_AL, 		 visualizzaAccertamentiCommand.getDataUltimoAggA());
					filtersMap.put(SessionVariables.ACCERTAMENTO_CHECK_DATA_AGG, 	 visualizzaAccertamentiCommand.getDataUltimoAggCheck());
					filtersMap.put(SessionVariables.ACCERTAMENTO_NOME_ACCERTAMENTO,  visualizzaAccertamentiCommand.getNomeAccertamento());
					filtersMap.put(SessionVariables.ACCERTAMENTO_CODICE_STATO, 		 visualizzaAccertamentiCommand.getCodStato());
					filtersMap.put(SessionVariables.ACCERTAMENTO_CODICE_TIPO_DOVUTO, visualizzaAccertamentiCommand.getCodTipoDovuto());
					
				SecurityContext.setEnteViewFilters(SecurityContext.getEnte().getCodIpaEnte(), SessionVariables.ACTION_ACCERTAMENTO_RICERCA, filtersMap);
			}
		   // =======================================================
			
		}catch(Exception e){
			logger.error("RICERCA :: ACCERTAMENTI :: POST :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + ", visualizzaAccertamentiCommand" + visualizzaAccertamentiCommand.toString() + "] :: ERRORE ", e);
			/* 
			 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
			 */
			return UtilitiesCtrl.getViewErrorMessage("mypivot.accertamenti.errore.ricerca");
		}
		
		logger.debug("RICERCA :: ACCERTAMENTI :: POST :: Fields[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + ", visualizzaAccertamentiCommand" + visualizzaAccertamentiCommand.toString() + "] :: END");

		return mav;
	}
	
	/**
	 * Funzione che gestisce le fasi comuni della ricerca accertamenti avviata sia in modalità GET che POST. 
	 * -----------------------------------------------------------------------------------------------------------------------------------
	 * Prima di erogare la pagina effettuo dei controlli per determinare se sussistono le condizioni tali perchè l'utente possa accedere
	 * alla funzionalità.
	 * 
	 * I controlli eseguiti sono i seguenti:
	 *   1. L'utente deve aver selezionato l'ente beneficiario, se mancante verrà rediretto alla pagina di scelta dell'ente.
	 *   2. L'utente deve avere ruoli attivi, se mancante verrà rediretto alla pagina di scelta dell'ente.
	 *   3. L'utente deve avere uno dei ruoli utili ad accedere alla funzionalità di "Accertamento" (ROLE_ADMIN e/o ROLE_ACC).
	 *   4. L'utente deve essere operatore (attivo) per almeno un tipo dovuto dell'ente, se mancante mostro un messaggio di errore (Es: Utente non autorizzato)
	 * 
	 * A seguito dei controlli, instanzio il bean che mappa i filtri di ricerca ed eventulamente lo prevalorizzo con i filtri che ho salvato in sessione 
	 * dall'ultima ricerca avviata.
	 * 
	 * Avvio la ricerca.
	 * 
	 * Alla view sono ritornati i seguenti oggetti:
	 * 
	 *   "responseDtoPage": Oggetto con la lista paginata degli accertamenti trovati
	 *   "listaTipiDovuti": Elenco dei tipi dovuti per cui è possibile filtrare la ricerca 
	 * 
	 * @param {@link HttpServletRequest}            request
	 * @param {@link visualizzaAccertamentiCommand} visualizzaAccertamentiCommand, Filtri di ricerca
	 * 
	 * @return {@link ModelAndView}
	 */
	private ModelAndView ricerca(AccertamentiVisualizzaCommand visualizzaAccertamentiCommand, HttpServletRequest request) {

		/* */
		ModelAndView mav = new ModelAndView();
		/* */
		MessagesDto messagesDto = new MessagesDto();
		
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
			 * La funzione verifica che l'utente possa accedere alla funzionalità di "Accertamento".
			 */
			if(!AccertamentoUtils.hasSecurityAccessFunctionality()) return UtilitiesCtrl.getViewErrorMessage("mypivot.messages.error.notAuthorized");

			/**
			 * Recupero la lista dei tipi dovuto dell'ente per cui l'utente è un operatore attivo. 
			 * Se la lista è vuota, mostro un messaggio di errore perchè vuol dire che l'utente non può ne consultare ne creare accertamenti.
			 */
			List<EnteTipoDovutoTO> activeOperatoreEnteTdAsObj = operatoreEnteTipoDovutoService.getListaEnteTipoDovutoForOperatoreAndCodIpaEnte(SecurityContext.getUtente().getCodFedUserId(), SecurityContext.getEnte().getCodIpaEnte());
			
			if (activeOperatoreEnteTdAsObj == null || activeOperatoreEnteTdAsObj.isEmpty()) {
				logger.warn("RICERCA :: ACCERTAMENTI :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: NON risulta OPERATORE per nessun tipo dovuto.");
				/* 
				 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
				 */
				return UtilitiesCtrl.getViewErrorMessage("mypivot.messages.error.nessunTipoDovutoAssegnato");
			}
		
			/**
			 * Ciclo la lista appena recuperata dei tipi dovuto e aggiungo ad una lista temporanea solamente il codice, mi servirà
			 * come filtro della query. 
			 */
			List<String> activeOperatoreEnteTdAsString = new ArrayList<String>();
			/* */
			for (EnteTipoDovutoTO item : activeOperatoreEnteTdAsObj) activeOperatoreEnteTdAsString.add(item.getCodTipo());
			
			/**
			 * Determino in base ai ruoli dell'utente loggato quali accertamenti può visualizzare.
			 * Se l'utente autenticato ha ruolo:
			 *   - ROLE_ACC: visualizza solamente i propri accertamenti
			 *   - ROLE_ADMIN: visualizza accertamenti effettuati dagli utenti operatori dell'ente e quelli caricati
			 *   				da BatchLoadFlussiExport tramite l' utente WS dell' ente (C_IPA-WS_USER)
			 */
			List<Long> utenteIDs = new ArrayList<Long>();
			
			if(SecurityContext.getRoles().contains(Constants.RUOLO_AMMINISTRATORE)){
				/*
				 * Caso in cui l'utente visualizza i propri accertamenti e quelli degli altri utenti operatori dell'ente, 
				 */
				List<UtenteTO> entities = utenteService.findByCodIpaEnte(SecurityContext.getEnte().getCodIpaEnte());
				
				/*
				 * e dell' utente WS.
				 */
				UtenteTO WsUser = utenteService.getUtenteWSByCodIpaEnte(SecurityContext.getEnte().getCodIpaEnte());
				if (!entities.contains(WsUser) && WsUser != null) entities.add(WsUser);
				
				/*
				 * Ciclo gli utenti e aggiungo alla lista solamente l'identificativo. 
				 */
				for (UtenteTO item : entities) utenteIDs.add(item.getId());
				
				logger.debug("RICERCA :: ACCERTAMENTI :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: L'utente autenticato è un ADMIN, visualizza gli accertamenti degli utenti con ID[" + StringUtils.collectionToCommaDelimitedString(utenteIDs) + "]");

			}else{
				logger.debug("RICERCA :: ACCERTAMENTI :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: L'utente autenticato visualizza solo i propri accertamenti");
				/*
				 * Caso in cui l'utente visualizza solamente i propri accertamenti, alla lista aggiungo solo il suo identificativo utente. 
				 */
				utenteIDs.add(SecurityContext.getUtente().getId());
			}
			
			/**
			 * Inizializzo il bean per la ricerca
			 */
			AccertamentoUtils.initializeFilterAccertamento(visualizzaAccertamentiCommand);
			
			/* Determino se è attiva la ricerca per data modifica */
			Boolean check = visualizzaAccertamentiCommand.getDataUltimoAggCheck() != null ? visualizzaAccertamentiCommand.getDataUltimoAggCheck() : false;
			
			/**
			 * Eseguo la query di ricerca
			 */
			PageDto<AccertamentoDto> result = accertamentoService.findByFilter(
					SecurityContext.getEnte().getCodIpaEnte(), utenteIDs, activeOperatoreEnteTdAsString, 
					visualizzaAccertamentiCommand.getCodTipoDovuto(), 
					visualizzaAccertamentiCommand.getCodStato(), visualizzaAccertamentiCommand.getNomeAccertamento(), 
					(check ? visualizzaAccertamentiCommand.getDataUltimoAggDa() : null), 
					(check ? visualizzaAccertamentiCommand.getDataUltimoAggA() : null), 
					visualizzaAccertamentiCommand.getCodiceIuv(), 
					true, visualizzaAccertamentiCommand.getPage(), visualizzaAccertamentiCommand.getPageSize());
			
			/**
			 * Set param da ritornare alla view
			 */
			mav.addObject("responseDtoPage", result);
			/* */
			mav.addObject("listaTipiDovuti", activeOperatoreEnteTdAsObj);
			/* */
			mav.addObject("visualizzaAccertamentiCommand", visualizzaAccertamentiCommand);

			/* */
			mav.setViewName("visualizzaAccertamenti");
			
		}catch(Exception e){
			logger.error("RICERCA :: ACCERTAMENTI :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: ERRORE", e);
			/* 
			 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
			 */
			return UtilitiesCtrl.getViewErrorMessage("mypivot.accertamenti.errore.ricerca");
		}
		
		/* */
		mav.addObject("messagesDto", messagesDto);
		
		return mav;
	}
}