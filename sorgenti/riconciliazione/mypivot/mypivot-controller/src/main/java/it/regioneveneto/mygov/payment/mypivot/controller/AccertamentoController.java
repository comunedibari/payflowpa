package it.regioneveneto.mygov.payment.mypivot.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import it.regioneveneto.mygov.payment.mypivot.controller.command.AccertamentoNuovoCommand;
import it.regioneveneto.mygov.payment.mypivot.controller.utils.AccertamentoUtils;
import it.regioneveneto.mygov.payment.mypivot.controller.utils.UtilitiesCtrl;
import it.regioneveneto.mygov.payment.mypivot.controller.validator.AccertamentoValidator;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.AccertamentoDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.DynamicMessageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.MessagesDto;
import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTipoDovutoTO;
import it.regioneveneto.mygov.payment.mypivot.service.AccertamentoDettaglioService;
import it.regioneveneto.mygov.payment.mypivot.service.AccertamentoService;
import it.regioneveneto.mygov.payment.mypivot.service.OperatoreEnteTipoDovutoService;
import it.regioneveneto.mygov.payment.mypivot.utils.Constants;
import it.regioneveneto.mygov.payment.mypivot.utils.SecurityContext;
import it.regioneveneto.mygov.payment.mypivot.utils.Utilities;

/**
 * Controller per la gestione del processo di :
 * 	1. Creazione di una nuova anagrafica accertamento
 *  2. Aggiornamento stato accertamento
 *  3. Download del PDF
 *  
 * @author Marianna Memoli
 */
@Controller  
@RequestMapping("/protected/accertamenti")
public class AccertamentoController {

	private static Log logger = LogFactory.getLog(AccertamentoController.class);

	@Autowired
	private OperatoreEnteTipoDovutoService operatoreEnteTipoDovutoService;
	
	@Autowired
	private AccertamentoService accertamentoService;
	
	@Autowired
	private AccertamentoDettaglioService accertamentoDettaglioService;
	
	@Autowired
	private AccertamentoValidator validator;
	
	@Autowired
	private ServletContext servletContext;
	
	@Resource
	private Environment env;
	
	public AccertamentoController() {
		super();
	}
	 
	/**
	 * Restituisce la view con la form per la definizione di un nuovo accertamento (GET). 
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
	 * A seguito dei controlli, è ritornata la view con i seguenti oggetti:
	 * 
	 *   "listaTipiDovuti": Elenco dei tipi dovuti per cui è possibile definire l'accertamento
	 * 
	 * @param {@link HttpServletRequest} request
	 * 
	 * @return {@link ModelAndView}
	 * @author Marianna Memoli
	 */
	@RequestMapping(value = { "/nuovo.html" }, method = RequestMethod.GET)
	public ModelAndView setupForm(HttpServletRequest request) {
		
		logger.debug("NUOVO :: ACCERTAMENTO :: GET :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: START");

		/* */
		ModelAndView mav = new ModelAndView("nuovoAccertamento", "accertamentoCommand", new AccertamentoNuovoCommand());
		
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
			List<EnteTipoDovutoTO> activeOperatoreEnteTd = operatoreEnteTipoDovutoService.getListaEnteTipoDovutoForOperatoreAndCodIpaEnte(SecurityContext.getUtente().getCodFedUserId(), SecurityContext.getEnte().getCodIpaEnte());
			
			if (activeOperatoreEnteTd == null || activeOperatoreEnteTd.isEmpty()) {
				logger.warn("NUOVO :: ACCERTAMENTO :: GET :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: NON risulta OPERATORE per nessun tipo dovuto.");
				/* 
				 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
				 */
				return UtilitiesCtrl.getViewErrorMessage("mypivot.messages.error.nessunTipoDovutoAssegnato");
			}
			
			/**
			 * Set param da ritornare alla view
			 */
			mav.addObject("listaTipiDovuti", activeOperatoreEnteTd);
			
		}catch(Exception e){
			logger.error("NUOVO :: ACCERTAMENTO :: GET :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: ERRORE ", e);
			/* 
			 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
			 */
			return UtilitiesCtrl.getViewErrorMessage("mypivot.accertamenti.errore.nuovo");
		}
		
		logger.debug("NUOVO :: ACCERTAMENTO :: GET :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: END");

		return mav;
	}
	
	/**
	 * Funzione richiamata al submit del form di creazione accertamento.
	 * -----------------------------------------------------------------------------------------------------------------------------------
	 * Prima di salvare l'accertamento effettuo dei controlli per determinare se sussistono le condizioni tali perchè l'utente abbia accesso
	 * alla funzionalità.
	 * 
	 * I controlli eseguiti sono i seguenti:
	 *   1. L'utente deve aver selezionato l'ente beneficiario, se mancante verrà rediretto alla pagina di scelta dell'ente.
	 *   2. L'utente deve avere ruoli attivi, se mancante verrà rediretto alla pagina di scelta dell'ente.
	 *   3. L'utente deve avere uno dei ruoli utili ad accedere alla funzionalità di "Accertamento" (ROLE_ADMIN e/o ROLE_ACC).
	 *   4. L'utente deve essere operatore (attivo) per almeno un tipo dovuto dell'ente, se mancante mostro un messaggio di errore (Es: Utente non autorizzato)
	 * 
     * A seguito dei controlli, procedo alla validazione dei dati compilati nel form inviato.
	 * Se il form è valido salvo l'accertamento e reindirizzo l'utente alla pagina in cui può definire i pagamenti in accertamento.
	 * Se il form non è valido, reindirizzo l'utente di nuovo alla pagina della form di creazione presentando gli errori di validazione.
	 * 
	 * @param {@link HttpServletRequest}   request
	 * @param {@link AccertamentoNuovoCommand}  accertamentoCommand, Bean che mappa la form di inserimento 
	 * @param {@link BindingResult}        result
	 * 
	 * @return {@link ModelAndView}
	 * @author Marianna Memoli
	 */
	@RequestMapping(value = { "/nuovo.html" }, method = RequestMethod.POST)
	public ModelAndView submitForm(@ModelAttribute("accertamentoCommand") AccertamentoNuovoCommand accertamentoCommand, HttpServletRequest request, BindingResult result) {

		logger.debug("NUOVO :: ACCERTAMENTO :: POST :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: START");
		
		/* */
		ModelAndView mav = new ModelAndView("nuovoAccertamento");
		
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
			List<EnteTipoDovutoTO> activeOperatoreEnteTd = operatoreEnteTipoDovutoService.getListaEnteTipoDovutoForOperatoreAndCodIpaEnte(SecurityContext.getUtente().getCodFedUserId(), SecurityContext.getEnte().getCodIpaEnte());
			
			if (activeOperatoreEnteTd == null || activeOperatoreEnteTd.isEmpty()) {
				logger.warn("NUOVO :: ACCERTAMENTO :: GET :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: NON risulta OPERATORE per nessun tipo dovuto.");
				/* 
				 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
				 */
				return UtilitiesCtrl.getViewErrorMessage("mypivot.messages.error.nessunTipoDovutoAssegnato");
			}
			
			/**
			 * Set param da ritornare alla view, in caso di errore o form non valido.
			 */
			mav.addObject("listaTipiDovuti", activeOperatoreEnteTd);
			mav.addObject(accertamentoCommand);
			
			/**
			 * Validazione form
			 */
			validator.validate(accertamentoCommand, result);

			/**
			 * Check validation errors
			 */
			if (result.hasErrors()) return mav;
			
			/**
			 * Salvataggio accertamento
			 */
			Long id = accertamentoService.saveAccertamento(
												accertamentoCommand.getNomeAccertamento(),  	/* Descrizione accertamento */
												accertamentoCommand.getCodTipoDovuto(), 		/* Codice tipo dovuto */
												SecurityContext.getEnte().getCodIpaEnte(), 		/* Codice ipa ente */
												SecurityContext.getUtente().getId());			/* Identificativo utente */
					
			/**
			 * Savataggio effettuato : Set view redirect alla pagina in cui l'utente può definire i pagamenti da associare all'accertamento 
			 * 						   appena creato. 
			 */
			mav.setView(new RedirectView(request.getContextPath() + Constants.PATH_PAGE_DETTAGLIO_ACCERTAMENTO + "?accertamentoID=" + id));
		
			logger.debug("NUOVO :: ACCERTAMENTO :: POST :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: END");
			
			return mav;
		}catch(Exception e){
			logger.error("NUOVO :: ACCERTAMENTO :: POST :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: ERRORE ", e);
			/* 
			 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
			 */
			return UtilitiesCtrl.getViewErrorMessage("mypivot.accertamenti.errore.nuovo");
		}
	}
	
	/**
	 * Aggiorna lo stato dell'anagrafica accertamento dati lo stato e il suo identificativo. 
	 * Controller richiamato dalla pagina di ricerca.
	 * -----------------------------------------------------------------------------------------------------------------------------------
	 * Prima di aggiornare l'anagrafica effettuo dei controlli per determinare se sussistono le condizioni tali perchè l'utente possa accedere
	 * alla funzionalità.
	 * 
	 * I controlli eseguiti sono i seguenti:
	 *   1. L'utente deve aver selezionato l'ente beneficiario, se mancante verrà rediretto alla pagina di scelta dell'ente.
	 *   2. L'utente deve avere ruoli attivi, se mancante verrà rediretto alla pagina di scelta dell'ente.
	 *   3. L'utente deve avere uno dei ruoli utili ad accedere alla funzionalità di "Accertamento" (ROLE_ADMIN e/o ROLE_ACC).
	 *   4. L'utente deve essere operatore (attivo) del tipo dovuto(..dell'ente) per cui è stato creato l'accertamento, se non lo è mostro un 
	 *   	messaggio di errore (Es: Utente non autorizzato)
	 *   5. Le transazioni dell'accertamento sono:
	 *   		1. CHIUSO    --> stato finale, per poterlo chiudere l'accertamento deve essere in stato "INSERITO" e con pagamenti
	 *   		2. ANNULLATO --> stato finale, per poterlo annullare l'accertamento deve essere in stato "INSERITO"
	 * 
	 * A seguito dei controlli, aggiorno l'anagrafica e in caso di successo rimando l'utente di nuovo alla pagina di ricerca.
	 * 
	 * @param {@link HttpServletRequest} request
	 * @param {@link Long} 			 	 accertamentoID, Identificativo dell'accertamento da aggiornare
	 * @param {@link String} 			 codStato, 		 Codice stato
	 * @param {@link RedirectAttributes} redirectAttributes, A RedirectAttributes model is empty when the method is called and is never
	 * 														 used unless the method returns a redirect view name or a RedirectView
	 * 
	 * @return {@link ModelAndView}
	 * @author Marianna Memoli
	 */
	@RequestMapping(value = { "/ricerca/setStato.html" }, method = RequestMethod.GET)
	public ModelAndView setStatus_Search(HttpServletRequest request, RedirectAttributes redirectAttributes, @RequestParam(value = "accertamentoID", required = true) Long accertamentoID, @RequestParam(value = "codStato", required = true) String codStato) {
		
		logger.debug("SET " + codStato + " :: ACCERTAMENTO :: GET :: Fields[accertamentoID: " + accertamentoID + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: START");
		
		/* */
		ModelAndView mav = new ModelAndView();
		
		try{
			/**
			 * Update stato
			 */
			mav = setStatus(request, redirectAttributes, accertamentoID, codStato, request.getContextPath() + Constants.PATH_PAGE_RICERCA_ACCERTAMENTI + "?page=1");
			
		}catch(Exception e){
			logger.error("SET " + codStato + " :: ACCERTAMENTO :: GET :: Fields[accertamentoID: " + accertamentoID + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: ERRORE ", e);
			/* 
			 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
			 */
			return UtilitiesCtrl.getViewErrorMessage("mypivot.accertamenti.errore.internal");
		}
		
		logger.debug("SET " + codStato + " :: ACCERTAMENTO :: GET :: Fields[accertamentoID: " + accertamentoID + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: END");

		return mav;
	}
	
	/**
	 * Aggiorna lo stato dell'anagrafica accertamento dati lo stato e il suo identificativo. 
	 * Controller richiamato dalla pagina di dettaglio.
	 * -----------------------------------------------------------------------------------------------------------------------------------
	 * Prima di aggiornare l'anagrafica effettuo dei controlli per determinare se sussistono le condizioni tali perchè l'utente possa accedere
	 * alla funzionalità.
	 * 
	 * I controlli eseguiti sono i seguenti:
	 *   1. L'utente deve aver selezionato l'ente beneficiario, se mancante verrà rediretto alla pagina di scelta dell'ente.
	 *   2. L'utente deve avere ruoli attivi, se mancante verrà rediretto alla pagina di scelta dell'ente.
	 *   3. L'utente deve avere uno dei ruoli utili ad accedere alla funzionalità di "Accertamento" (ROLE_ADMIN e/o ROLE_ACC).
	 *   4. L'utente deve essere operatore (attivo) del tipo dovuto(..dell'ente) per cui è stato creato l'accertamento, se non lo è mostro un 
	 *   	messaggio di errore (Es: Utente non autorizzato)
	 *   5. Le transazioni dell'accertamento sono:
	 *   		1. CHIUSO    --> stato finale, per poterlo chiudere l'accertamento deve essere in stato "INSERITO" e con pagamenti
	 *   		2. ANNULLATO --> stato finale, per poterlo annullare l'accertamento deve essere in stato "INSERITO"
	 *   
	 * A seguito dei controlli, aggiorno l'anagrafica e in caso di successo rimando l'utente di nuovo alla pagina di dettaglio.
	 * 
	 * @param {@link HttpServletRequest} request
	 * @param {@link Long} 			 	 accertamentoID,     Identificativo dell'accertamento da aggiornare
	 * @param {@link String} 			 codStato, 		     Codice stato
	 * @param {@link RedirectAttributes} redirectAttributes, A RedirectAttributes model is empty when the method is called and is never
	 * 														 used unless the method returns a redirect view name or a RedirectView
	 * @return {@link ModelAndView}
	 * @author Marianna Memoli
	 */
	@RequestMapping(value = { "/dettaglio/setStato.html" }, method = RequestMethod.GET)
	public ModelAndView setStatus_Detail(HttpServletRequest request, RedirectAttributes redirectAttributes, @RequestParam(value = "accertamentoID", required = true) Long accertamentoID, @RequestParam(value = "codStato", required = true) String codStato) {
		
		logger.debug("SET " + codStato + " :: ACCERTAMENTO :: GET :: Fields[accertamentoID: " + accertamentoID + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: START");
		
		/* */
		ModelAndView mav = new ModelAndView();
		
		try{
			/**
			 * Update stato
			 */
			mav = setStatus(request, redirectAttributes, accertamentoID, codStato, request.getContextPath() + Constants.PATH_PAGE_DETTAGLIO_ACCERTAMENTO + "?accertamentoID=" + accertamentoID);
			
		}catch(Exception e){
			logger.error("SET " + codStato + " :: ACCERTAMENTO :: GET :: Fields[accertamentoID: " + accertamentoID + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: ERRORE ", e);
			/* 
			 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
			 */
			return UtilitiesCtrl.getViewErrorMessage("mypivot.accertamenti.errore.internal");
		}
		
		logger.debug("SET " + codStato + " :: ACCERTAMENTO :: GET :: Fields[accertamentoID: " + accertamentoID + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: END");

		return mav;
	}
	
	/**
	 * Funzioni comuni alla procedura di aggiornamento dello stato di un'accertamento.
	 * 
	 * @param {@link HttpServletRequest} request
	 * @param {@link Long} 			 	 accertamentoID,     Identificativo dell'accertamento da aggiornare
	 * @param {@link String} 			 codStato, 		     Codice stato
	 * @param {@link String} 			 redirectUrl, 	     Pagina alla quale reindirizzare l'utente al termine
	 * @param {@link RedirectAttributes} redirectAttributes, A RedirectAttributes model is empty when the method is called and is never
	 * 														 used unless the method returns a redirect view name or a RedirectView
	 * 
	 * @return {@link ModelAndView}
	 * @author Marianna Memoli
	 */
	public ModelAndView setStatus(HttpServletRequest request, RedirectAttributes redirectAttributes, Long accertamentoID, String codStato, String redirectUrl) {
		
		logger.debug("SET " + codStato + " :: ACCERTAMENTO :: GET :: Fields[accertamentoID: " + accertamentoID + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: START");
		
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
			List<String> activeOperatoreEnteTd = operatoreEnteTipoDovutoService.getListaCodTipoDovutoForOperatoreAndCodIpaEnte(SecurityContext.getUtente().getCodFedUserId(), SecurityContext.getEnte().getCodIpaEnte());
			
			if (activeOperatoreEnteTd == null || activeOperatoreEnteTd.isEmpty()) {
				logger.warn("SET " + codStato + " :: ACCERTAMENTO :: GET :: Fields[accertamentoID: " + accertamentoID + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: NON risulta OPERATORE per nessun tipo dovuto.");
				/* 
				 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
				 */
				return UtilitiesCtrl.getViewErrorMessage("mypivot.messages.error.nessunTipoDovutoAssegnato");
			}
			
			/**
			 * Get anagrafica accertamento 
			 */
			AccertamentoDto accertamentoDto = accertamentoService.findById(accertamentoID);
			
			/**
			 * Controllo che l'utente sia operatore per il tipo dovuto dell'accertamento a cui si vuole cambiare stato.
			 */
			if (!activeOperatoreEnteTd.contains(accertamentoDto.getEnteTipoDovuto().getCodTipo())) {
				logger.warn("SET " + codStato + " :: ACCERTAMENTO :: GET :: Fields[accertamentoID: " + accertamentoID + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: NON risulta OPERATORE per il tipo dovuto dell'accertamento.");
				/* 
				 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
				 */
				return UtilitiesCtrl.getViewErrorMessage("mypivot.messages.error.notAuthorized");
			}
			
			/**
			 * Controllo che l'accertamento sia ancora in stato: INSERITO e con PAGAMENTI per poterlo chiudere, se non lo è 
			 * mostro un messaggio di errore
			 */
			if (codStato.equals(Constants.COD_TIPO_STATO_ACCERTAMENTO_CHIUSO)) {
				
				if(!accertamentoDto.getStato().getCodStato().equals(Constants.COD_TIPO_STATO_ACCERTAMENTO_INSERITO)) {
					logger.warn("SET " + codStato + " :: ACCERTAMENTO :: GET :: Fields[accertamentoID: " + accertamentoID + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: L'accertamento è in stato[" + accertamentoDto.getStato().getCodStato() + "] perciò non può essere chiuso.");
					/* 
					 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
					 */
					return UtilitiesCtrl.getViewErrorMessage("mypivot.accertamenti.errore.notAuthorizedClosed", new Object[]{accertamentoDto.getDeNomeAccertamento(), accertamentoDto.getStato().getCodStato()});
				}
				
				/*
				 * Controllo che l'accertamento abbia dei pagamenti(RT) in accertamento per poterlo chiudere
				 */
				if(accertamentoDettaglioService.countRowByAccertamentoId(accertamentoID) == 0){
					/* */
					logger.warn("SET " + codStato + " :: ACCERTAMENTO :: GET :: Fields[accertamentoID: " + accertamentoID + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: L'accertamento non presenta pagamenti associati perciò non può essere chiuso.");
					/* */
					redirectAttributes.getFlashAttributes().clear();
					/* Come attributo di ritorno della redirect aggiungo un parametro di modo che poi possa mostrare il messagio di errore */
					redirectAttributes.addFlashAttribute("notAuthorizedClosedEmptyAcc", true);
					/* Set redirect to view */
					mav.setView(new RedirectView(redirectUrl));
					/* */
					return mav;
				}
			}else
				/**
				 * Controllo che l'accertamento deve essere ancora in stato: INSERITO per poterlo annullare, se non lo è mostro un messaggio di 
				 * errore
				 */
				if (codStato.equals(Constants.COD_TIPO_STATO_ACCERTAMENTO_ANNULLATO)) {
					
					if(!accertamentoDto.getStato().getCodStato().equals(Constants.COD_TIPO_STATO_ACCERTAMENTO_INSERITO)) {
						logger.warn("SET " + codStato + " :: ACCERTAMENTO :: GET :: Fields[accertamentoID: " + accertamentoID + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: L'accertamento è in stato[" + accertamentoDto.getStato().getCodStato() + "] perciò non può essere annullato.");
						/* 
						 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
						 */
						return UtilitiesCtrl.getViewErrorMessage("mypivot.accertamenti.errore.notAuthorizedCancel", new Object[]{accertamentoDto.getDeNomeAccertamento(), accertamentoDto.getStato().getCodStato()});
					}
				}
			
			try{
				/**
				 * Update stato
				 */
				accertamentoService.setStatoAndSave(accertamentoID, codStato, SecurityContext.getUtente().getCodFedUserId());
				
			}catch(Exception e){
				logger.error("SET " + codStato + " :: ACCERTAMENTO :: Fields[accertamentoID: " + accertamentoID + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: ERRORE ", e);
				/* */
				messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.accertamenti.errore.setStatus"));
				/* */
				mav.addObject("messagesDto", messagesDto);
			}
			
			/**
			 * Set redirect to view ricerca 
			 */
			mav.setView(new RedirectView(redirectUrl));
			
		}catch(Exception e){
			logger.error("SET " + codStato + " :: ACCERTAMENTO :: GET :: Fields[accertamentoID: " + accertamentoID + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: ERRORR ", e);
			/* 
			 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
			 */
			return UtilitiesCtrl.getViewErrorMessage("mypivot.accertamenti.errore.internal");
		}
		
		logger.debug("SET " + codStato + " :: ACCERTAMENTO :: GET :: Fields[accertamentoID: " + accertamentoID + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: END");

		return mav;
	}
	
	/**
	 * Download del documento relativo l'accertamento. Controller richiamato dalla pagina di ricerca.
	 * -----------------------------------------------------------------------------------------------------------------------------------
	 * Prima fornire il doc effettuo dei controlli per determinare se sussistono le condizioni tali perchè l'utente possa accedere
	 * alla funzionalità.
	 * 
	 * I controlli eseguiti sono i seguenti:
	 *   1. L'utente deve aver selezionato l'ente beneficiario, se mancante verrà rediretto alla pagina di scelta dell'ente.
	 *   2. L'utente deve avere ruoli attivi, se mancante verrà rediretto alla pagina di scelta dell'ente.
	 *   3. L'utente deve avere uno dei ruoli utili ad accedere alla funzionalità di "Accertamento" (ROLE_ADMIN e/o ROLE_ACC).
	 *   4. L'utente deve essere operatore (attivo) del tipo dovuto(..dell'ente) per cui è stato creato l'accertamento, se non lo è mostro un 
	 *   	messaggio di errore (Es: Utente non autorizzato)
	 *   5. L'accertamento deve essere in stato "CHIUSO per poter scaricare il report, se non lo è mostro un messaggio di errore
	 * 
	 * A seguito dei controlli, avvia il download.
	 * 
	 * @param {@link HttpServletRequest} request
	 * @param {@link Long} 			 	 accertamentoID, Identificativo dell'accertamento da aggiornare
	 * @param {@link Integer} 			 page, Pagine della ricerca in visualizzazione
	 * 
	 * @return {@link ModelAndView}
	 * @author Marianna Memoli
	 */
	@RequestMapping(value = { "/ricerca/download.html" }, method = RequestMethod.GET)
	public ModelAndView download_Search(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "accertamentoID", required = true) Long accertamentoID, @RequestParam(value = "page", required = false) Integer page) {
		
		logger.debug("DOWNLOAD :: ACCERTAMENTO :: GET :: Fields[accertamentoID: " + accertamentoID + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + ", page:" + page + "] :: START");
		
		/* */
		ModelAndView mav = new ModelAndView();
		
		try{
			/**
			 * Update stato
			 */
			mav = download(request, response, accertamentoID, request.getContextPath() + Constants.PATH_PAGE_RICERCA_ACCERTAMENTI + "?page=" + (page != null ? page : "1"));
			
		}catch(Exception e){
			logger.error("DOWNLOAD :: ACCERTAMENTO :: GET :: Fields[accertamentoID: " + accertamentoID + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + ", page:" + page + "] :: ERRORE ", e);
			/* 
			 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
			 */
			return UtilitiesCtrl.getViewErrorMessage("mypivot.accertamenti.errore.internal");
		}
		
		logger.debug("DOWNLOAD :: ACCERTAMENTO :: GET :: Fields[accertamentoID: " + accertamentoID + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + ", page:" + page + "] :: END");

		return mav;
	}
	
	/**
	 * Download del documento relativo l'accertamento. Controller richiamato dalla pagina di dettaglio accertamento.
	 * -----------------------------------------------------------------------------------------------------------------------------------
	 * Prima fornire il doc effettuo dei controlli per determinare se sussistono le condizioni tali perchè l'utente possa accedere
	 * alla funzionalità.
	 * 
	 * I controlli eseguiti sono i seguenti:
	 *   1. L'utente deve aver selezionato l'ente beneficiario, se mancante verrà rediretto alla pagina di scelta dell'ente.
	 *   2. L'utente deve avere ruoli attivi, se mancante verrà rediretto alla pagina di scelta dell'ente.
	 *   3. L'utente deve avere uno dei ruoli utili ad accedere alla funzionalità di "Accertamento" (ROLE_ADMIN e/o ROLE_ACC).
	 *   4. L'utente deve essere operatore (attivo) del tipo dovuto(..dell'ente) per cui è stato creato l'accertamento, se non lo è mostro un 
	 *   	messaggio di errore (Es: Utente non autorizzato)
	 *   5. L'accertamento deve essere in stato "CHIUSO" per poter scaricare il report, se non lo è mostro un messaggio di errore
	 * 
	 * A seguito dei controlli, avvia il download.
	 * 
	 * @param {@link HttpServletRequest} request
	 * @param {@link Long} 			 	 accertamentoID, Identificativo dell'accertamento da aggiornare
	 * 
	 * @return {@link ModelAndView}
	 * @author Marianna Memoli
	 */
	@RequestMapping(value = { "/dettaglio/download.html" }, method = RequestMethod.GET)
	public ModelAndView download_Detail(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "accertamentoID", required = true) Long accertamentoID ) {
		
		logger.debug("DOWNLOAD :: ACCERTAMENTO :: GET :: Fields[accertamentoID: " + accertamentoID + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: START");
		
		/* */
		ModelAndView mav = new ModelAndView();
		
		try{
			/**
			 * Update stato
			 */
			mav = download(request, response, accertamentoID, request.getContextPath() + Constants.PATH_PAGE_DETTAGLIO_ACCERTAMENTO + "?accertamentoID=" + accertamentoID);
			
		}catch(Exception e){
			logger.error("DOWNLOAD :: ACCERTAMENTO :: GET :: Fields[accertamentoID: " + accertamentoID + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: ERRORE ", e);
			/* 
			 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
			 */
			return UtilitiesCtrl.getViewErrorMessage("mypivot.accertamenti.errore.internal");
		}
		
		logger.debug("DOWNLOAD :: ACCERTAMENTO :: GET :: Fields[accertamentoID: " + accertamentoID + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: END");

		return mav;
	}
	
	/**
	 * Download del documento relativo l'accertamento
	 *  
	 * @param {@link HttpServletRequest}  request
	 * @param {@link HttpServletResponse} response
	 * @param {@link Long} 				  accertamentoID, Identificativo accertamento
	 * @param {@link String} 			  redirectUrl, 	  Pagina alla quale reindirizzare l'utente al termine
	 * 
	 * @return {@ModelAndView}
	 * @author Marianna Memoli
	 */
	public ModelAndView download(HttpServletRequest request, HttpServletResponse response, Long accertamentoID, String redirectUrl) {
		
		logger.debug("DOWNLOAD :: ACCERTAMENTO :: GET :: Fields[accertamentoID: " + accertamentoID + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: START");
		
		/* */
		ModelAndView mav = new ModelAndView();
		
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
			List<String> activeOperatoreEnteTd = operatoreEnteTipoDovutoService.getListaCodTipoDovutoForOperatoreAndCodIpaEnte(SecurityContext.getUtente().getCodFedUserId(), SecurityContext.getEnte().getCodIpaEnte());
			
			if (activeOperatoreEnteTd == null || activeOperatoreEnteTd.isEmpty()) {
				logger.warn("DOWNLOAD :: ACCERTAMENTO :: GET :: Fields[accertamentoID: " + accertamentoID + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: NON risulta OPERATORE per nessun tipo dovuto.");
				/* 
				 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
				 */
				return UtilitiesCtrl.getViewErrorMessage("mypivot.messages.error.nessunTipoDovutoAssegnato");
			}
			
			/**
			 * Get anagrafica accertamento 
			 */
			AccertamentoDto accertamentoDto = accertamentoService.findById(accertamentoID);
			
			/**
			 * Controllo che l'utente sia operatore per il tipo dovuto dell'accertamento di cui si vuole fare il download del report.
			 */
			if (!activeOperatoreEnteTd.contains(accertamentoDto.getEnteTipoDovuto().getCodTipo())) {
				logger.warn("DOWNLOAD :: ACCERTAMENTO :: GET :: Fields[accertamentoID: " + accertamentoID + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: NON risulta OPERATORE per il tipo dovuto dell'accertamento.");
				/* 
				 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
				 */
				return UtilitiesCtrl.getViewErrorMessage("mypivot.messages.error.notAuthorized");
			}
			
			/**
			 * Controllo che l'accertamento sia in stato "CHIUSO" per poterlo scaricare.
			 */
			if(!accertamentoDto.getStato().getCodStato().equals(Constants.COD_TIPO_STATO_ACCERTAMENTO_CHIUSO)) {
				logger.warn("DOWNLOAD :: ACCERTAMENTO :: GET :: Fields[accertamentoID: " + accertamentoID + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: L'accertamento è in stato[" + accertamentoDto.getStato().getCodStato() + "] perciò non può essere fatto il download del report.");
				/* 
				 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
				 */
				return UtilitiesCtrl.getViewErrorMessage("mypivot.accertamenti.errore.notAuthorizedDownload", new Object[]{accertamentoDto.getDeNomeAccertamento()});
			}
			
			/**
			 * Logo ente  
			 */
			String logoEntePath = env.getProperty("mypivot.resourcesRootDir") + "/loghiEnti/" + SecurityContext.getEnte().getCodIpaEnte() + ".png";
			
			/*
			 * Parametri d'input al report
			 */
			Map<String, Object> reportParameters = new HashMap<String, Object>();
			// Info ente
			reportParameters.put("cod_ipa_ente", 								SecurityContext.getEnte().getCodIpaEnte());
			reportParameters.put("codice_fiscale_ente", 						SecurityContext.getEnte().getCodiceFiscaleEnte());
			reportParameters.put("de_rp_ente_benef_denominazione_beneficiario", SecurityContext.getEnte().getDeNomeEnte());
			reportParameters.put("de_rp_ente_benef_email_beneficiario", 		SecurityContext.getEnte().getEmailAmministratore());
			// Info accertamento
			reportParameters.put("id_accertamento", 				accertamentoDto.getId());
			reportParameters.put("de_tipo_dovuto_accertamento", 	accertamentoDto.getEnteTipoDovuto().getDeTipo());
			reportParameters.put("cod_tipo_dovuto_accertamento", 	accertamentoDto.getEnteTipoDovuto().getCodTipo());
			reportParameters.put("de_nome_accertamento", 			accertamentoDto.getDeNomeAccertamento());
			reportParameters.put("dt_ultima_modifica_accertamento", accertamentoDto.getDtUltimaModifica());
			// Info utente
			reportParameters.put("codice_fiscale_utente", SecurityContext.getUtente().getCodCodiceFiscaleUtente());
			reportParameters.put("email_address_utente",  SecurityContext.getUtente().getDeEmailAddress());
			reportParameters.put("anagrafica_utente", 	  SecurityContext.getUtente().getDeFirstname() + " " + SecurityContext.getUtente().getDeLastname());
			// Loghi
			reportParameters.put("pagolapa_img", servletContext.getRealPath("/images/pagolapa.png"));
			reportParameters.put("ente_img", logoEntePath);
			
			/**
			 * Report file jrxml 
			 */
			String reportFilePathSpecificoPerEnte = env.getProperty("mypivot.reportsTemplateRootDir") + SecurityContext.getEnte().getCodIpaEnte() + "/accertamento.jrxml";
			File report = new File(reportFilePathSpecificoPerEnte);

			String reportFilePath = null;
			if (report.exists())
				reportFilePath = reportFilePathSpecificoPerEnte;
			else
				reportFilePath = env.getProperty("mypivot.reportsTemplateRootDir") + "accertamento.jrxml";
			
			/**
			 * Connesione al database
			 */
			final JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
	        dsLookup.setResourceRef(true);
	        DataSource dataSource = dsLookup.getDataSource(env.getProperty("dataSource.jndiName"));
	         
	        ByteArrayOutputStream reportStream = new ByteArrayOutputStream(4096);
	        
	        /**
	    	 * Esporta l'oggetto report generato in formato PDF e scrive i risultati sul flusso di output.
	    	 */
			Utilities.exportToPdf(reportFilePath, reportParameters, dataSource.getConnection(), reportStream);

			/**
			 * Aggiorna l'anagrafica dell'accertamento impostando a true la varibile che individua che il report dell'accertamento
			 * è stato stampato almento una volta.
			 */
			if(!accertamentoDto.getPrinted()) accertamentoService.setPrintedAndSave(accertamentoID,  SecurityContext.getUtente().getCodFedUserId());
			
			/**
			 * Build nome file, formato: accertamento-<cod tipo dovuto>-yyyyMMddHHmmss.pdf
			 */
			String filename = "accertamento-" + accertamentoDto.getEnteTipoDovuto().getCodTipo() + "-" + Utilities.formatDate(new Date(), Constants.yyyyMMddHHmmss_PATTERN) + ".pdf";
			
			reportStream.flush();
			reportStream.close();

			response.setContentType("application/pdf");
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Content-disposition", "attachment; filename=\"" + filename + "\"");

			response.getOutputStream().write(reportStream.toByteArray());
			response.getOutputStream().flush();
			response.getOutputStream().close();
			
			/**
			 * Set redirect to view ricerca 
			 */
			mav = null;
			
		}catch(Exception e){
			logger.error("DOWNLOAD :: ACCERTAMENTO :: GET :: Fields[accertamentoID: " + accertamentoID + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: ERRORE ", e);
			/* 
			 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
			 */
			return UtilitiesCtrl.getViewErrorMessage("mypivot.accertamenti.errore.download");
		}
		
		logger.debug("DOWNLOAD :: ACCERTAMENTO :: GET :: Fields[accertamentoID: " + accertamentoID + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: END");

		return mav;
	}
}