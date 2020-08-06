package it.regioneveneto.mygov.payment.mypivot.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import it.regioneveneto.mygov.payment.mypivot.controller.command.AnagraficaUfficioCapitoloAccertamentoCommand;
import it.regioneveneto.mygov.payment.mypivot.controller.utils.AccertamentoUtils;
import it.regioneveneto.mygov.payment.mypivot.controller.utils.UtilitiesCtrl;
import it.regioneveneto.mygov.payment.mypivot.controller.validator.AnagraficaUfficioCapitoloAccertamentoValidator;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.AggiornaAnagraficaUfficioCapitoloAccertamentoResultDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.AggiornaAnagraficaUfficioCapitoloAccertamentoResultDto.ESITO;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.AnagraficaUfficioCapitoloAccertamentoDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.DynamicMessageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.MessagesDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.PageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.ResponseDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.ResponseUploadAnagUffCapAccDto;
import it.regioneveneto.mygov.payment.mypivot.domain.po.AnagraficaUfficioCapitoloAccertamento;
import it.regioneveneto.mygov.payment.mypivot.domain.po.Ente;
import it.regioneveneto.mygov.payment.mypivot.domain.to.AnagraficaUfficioCapitoloAccertamentoTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTipoDovutoTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.UtenteTO;
import it.regioneveneto.mygov.payment.mypivot.service.AnagraficaUfficioCapitoloAccertamentoService;
import it.regioneveneto.mygov.payment.mypivot.service.OperatoreEnteTipoDovutoService;
import it.regioneveneto.mygov.payment.mypivot.utils.Constants;
import it.regioneveneto.mygov.payment.mypivot.utils.SecurityContext;
import it.regioneveneto.mygov.payment.mypivot.utils.SessionVariables;



/**
 * Controller della form dela pagina di Ricerca accertamenti.
 * 
 * @author Alessandro Paolillo
 */
@Controller
@RequestMapping("/protected/accertamentiAnagrafiche")
public class VisualizzaAnagraficaUfficioCapitoloAccertamentoController {

	private static Log logger = LogFactory.getLog(VisualizzaAnagraficaUfficioCapitoloAccertamentoController.class);

	@Autowired
	private OperatoreEnteTipoDovutoService operatoreEnteTipoDovutoService;
	
	@Autowired
	private AnagraficaUfficioCapitoloAccertamentoService anagraficaUfficioCapitoloAccertamentoService;
	
	@Autowired
	private AnagraficaUfficioCapitoloAccertamentoValidator validator;
	
	
	public VisualizzaAnagraficaUfficioCapitoloAccertamentoController() {
		super();
	}
	
	
	/**
	 * Restituisce la view con la form per la gestione anagrafica (Ufficio - Accertamento - Capitolo) (GET)
	 * --------------------------------------------------------------------------------------------------------------------------------- 
	 * 
	 *  I controlli eseguiti sono i seguenti:
	 *   1. L'utente deve aver selezionato l'ente beneficiario, se mancante verrà rediretto alla pagina di scelta dell'ente.
	 *   2. L'utente deve avere ruoli attivi, se mancante verrà rediretto alla pagina di scelta dell'ente.
	 *   3. L'utente deve avere uno dei ruoli utili ad accedere alla funzionalità di "Accertamento" (ROLE_ADMIN, ROLE_ACC).
	 * 
	 * 
	 * @param request
	 * @param response
	 * @param tab (U = Uffici, I = Import massivo)
	 * @return
	 * @author Alessandro Paolillo
	 */
	@RequestMapping(value = { "/ricerca.html" }, method = RequestMethod.GET)
	public ModelAndView setupFormGestAna(HttpServletRequest request, @RequestParam(required = true) String tab, Model model, 
			@RequestParam(required = false) String page, @RequestParam(required = false) String pageSize) {
		
		logger.debug("RICERCA :: GESTIONE ANAGRAFICA UFFICIO CAPITOLO ACCERTAMENTO :: GET :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: START");
		
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
			if (!AccertamentoUtils.hasSecurityAccessFunctionality()) return UtilitiesCtrl.getViewErrorMessage("mypivot.messages.error.notAuthorized");
			
			
			AnagraficaUfficioCapitoloAccertamentoCommand anagraficaUfficioCapitoloAccertamentoCommand = new AnagraficaUfficioCapitoloAccertamentoCommand();
			
			/**
			 * Prevalorizzo il bean di ricerca della form con gli ultimi valori dei filtri sottomessi e salvati in sessione.
			 * request, codIpaEnte, codTipoDovuto, codiceUfficio, denominazioneUfficio, codiceCapitolo, denominazioneCapitolo, 
			 * codiceAccertamento, denominazioneAccertamento, page, pageSize, anagraficaUfficioCapitoloAccertamentoCommand)
			 */
			setSessionFilterToBean(request, SecurityContext.getEnte().getCodIpaEnte(), null, null, null, null, null, null, null, null, page, pageSize, null, anagraficaUfficioCapitoloAccertamentoCommand);
			
			
			/**
			 * Avvio la ricerca
			 */
			mav = ricerca(anagraficaUfficioCapitoloAccertamentoCommand, request, (model != null && model.containsAttribute("missingPagNoConfirm")) ? true : false);
			 
			
		}catch (Exception e) {
			logger.error("RICERCA :: GESTIONE ANAGRAFICA UFFICIO CAPITOLO ACCERTAMENTO :: GET :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: ERRORE ", e);
			/* 
			 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
			 */
			return UtilitiesCtrl.getViewErrorMessage("mypivot.anagrafica.errore.setStatus");
		}
		
		mav.addObject("tab", tab);
		
		logger.debug("RICERCA :: GESTIONE ANAGRAFICA UFFICIO CAPITOLO ACCERTAMENTO :: GET :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: END");
		
		return mav;
	}
	
	
	/**
	 * Restituisce la view con la form per la gestione anagrafica (Ufficio - Accertamento - Capitolo) (POST)
	 * L'utente ha avviato una ricerca
	 * --------------------------------------------------------------------------------------------------------------------------------- 
	 * 
	 * @param anagraficaUfficioCapitoloAccertamentoCommand
	 * @param request
	 * @param tab
	 * @return
	 * @author Alessandro Paolillo
	 */
	@RequestMapping(value = { "/ricerca.html" }, method = RequestMethod.POST)
	public ModelAndView post(@ModelAttribute("anagraficaUfficioCapitoloAccertamentoCommand") AnagraficaUfficioCapitoloAccertamentoCommand anagraficaUfficioCapitoloAccertamentoCommand, 
			HttpServletRequest request, @RequestParam(required = true) String tab) {
		
		logger.debug("RICERCA :: GESTIONE ANAGRAFICA UFFICIO CAPITOLO ACCERTAMENTO :: POST :: Fields[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + ", anagraficaUfficioCapitoloAccertamentoCommand" + anagraficaUfficioCapitoloAccertamentoCommand.toString() + "] :: START");
		
		ModelAndView mav = new ModelAndView();
		
		try{
			
			/*
			 * E' stata avviata una nuova ricerca, con nuovi filtri, reimposto la pagina di partenza alla prima.
			 */
			anagraficaUfficioCapitoloAccertamentoCommand.setPage(1);
			/*
			 * Avvio la ricerca
			 */
			mav = ricerca(anagraficaUfficioCapitoloAccertamentoCommand, request, false);
			
			// Salvo i nuovi filtri di ricerca impostati in sessione.
			if(SecurityContext.getEnte() != null){
							
				Map<String, Object> filtersMap = new HashMap<String, Object>();
				
					filtersMap.put(SessionVariables.ANAGRAFICA_UCA_PAGE_SIZE, 					anagraficaUfficioCapitoloAccertamentoCommand.getPageSize());
					filtersMap.put(SessionVariables.ANAGRAFICA_UCA_PAGE,						anagraficaUfficioCapitoloAccertamentoCommand.getPage());
					filtersMap.put(SessionVariables.ANAGRAFICA_UCA_CODICE_TIPO_DOVUTO,			anagraficaUfficioCapitoloAccertamentoCommand.getCodTipoDovuto());
					filtersMap.put(SessionVariables.ANAGRAFICA_UCA_CODICE_UFFICIO,				anagraficaUfficioCapitoloAccertamentoCommand.getCodiceUfficio());
					filtersMap.put(SessionVariables.ANAGRAFICA_UCA_DENOMINAZIONE_UFFICIO,		anagraficaUfficioCapitoloAccertamentoCommand.getDenominazioneUfficio());
					filtersMap.put(SessionVariables.ANAGRAFICA_UCA_CODICE_CAPITOLO,				anagraficaUfficioCapitoloAccertamentoCommand.getCodiceCapitolo());
					filtersMap.put(SessionVariables.ANAGRAFICA_UCA_DENOMINAZIONE_CAPITOLO,		anagraficaUfficioCapitoloAccertamentoCommand.getDenominazioneCapitolo());
					filtersMap.put(SessionVariables.ANAGRAFICA_UCA_CODICE_ACCERTAMENTO,			anagraficaUfficioCapitoloAccertamentoCommand.getCodiceAccertamento());
					filtersMap.put(SessionVariables.ANAGRAFICA_UCA_DENOMINAZIONE_ACCERTAMENTO,	anagraficaUfficioCapitoloAccertamentoCommand.getDenominazioneAccertamento());
					filtersMap.put(SessionVariables.ANAGRAFICA_UCA_FLG_ATTIVO,					anagraficaUfficioCapitoloAccertamentoCommand.getFlgAttivo());
					filtersMap.put(SessionVariables.ANAGRAFICA_UCA_ANNO_ESERCIZIO, 				anagraficaUfficioCapitoloAccertamentoCommand.getAnnoEsercizio());
				
				SecurityContext.setEnteViewFilters(SecurityContext.getEnte().getCodIpaEnte(), SessionVariables.ACTION_ANAGRAFICA_UCA_RICERCA, filtersMap);
				
			}
			
		}catch (Exception e) {
			logger.error("RICERCA :: GESTIONE ANAGRAFICA UFFICIO CAPITOLO ACCERTAMENTO :: POST :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + ", anagraficaUfficioCapitoloAccertamentoCommand" + anagraficaUfficioCapitoloAccertamentoCommand.toString() + "] :: ERRORE ", e);
			/* 
			 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
			 */
			return UtilitiesCtrl.getViewErrorMessage("mypivot.anagrafica.errore.ricerca");
		}
		
		mav.addObject("tab", "U");
		
		logger.debug("RICERCA :: GESTIONE ANAGRAFICA UFFICIO CAPITOLO ACCERTAMENTO :: POST :: Fields[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + ", anagraficaUfficioCapitoloAccertamentoCommand" + anagraficaUfficioCapitoloAccertamentoCommand.toString() + "] :: FINE");
		
		return mav;
	}
	
	
	
	/**
	 * 
	 * @param anagraficaUfficioCapitoloAccertamentoCommand
	 * @param request
	 * @param missingPagNoConfirm
	 * @return
	 * @author Alessandro Paolillo
	 */
	private ModelAndView ricerca(AnagraficaUfficioCapitoloAccertamentoCommand anagraficaUfficioCapitoloAccertamentoCommand, HttpServletRequest request, boolean missingPagNoConfirm) {
		
		/* */
		ModelAndView mav = new ModelAndView();
		
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
			 * La funzione verifica che l'utente possa per accedere alla funzionalità di "Accertamento".
			 */
			if(!AccertamentoUtils.hasSecurityAccessFunctionality()) return UtilitiesCtrl.getViewErrorMessage("mypivot.messages.error.notAuthorized");

			////////////RECUPERO LISTA DEI TIPIDOVUTO PER ENTE SELEZIONATO/////////////////		
			/**
			 * Recupero la lista dei tipi dovuto dell'ente per cui l'utente è un operatore attivo. 
			 * Se la lista è vuota, mostro un messaggio di errore perchè vuol dire che l'utente non può ne consultare ne creare accertamenti.
			 */
			List<EnteTipoDovutoTO> activeOperatoreEnteTdAsObj = operatoreEnteTipoDovutoService.getListaEnteTipoDovutoForOperatoreAndCodIpaEnte(SecurityContext.getUtente().getCodFedUserId(), SecurityContext.getEnte().getCodIpaEnte());
			
			if (activeOperatoreEnteTdAsObj == null || activeOperatoreEnteTdAsObj.isEmpty()) {
				logger.warn("RICERCA :: GESTIONE ANAGRAFICA UFFICIO CAPITOLO ACCERTAMENTO :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: NON risulta OPERATORE per nessun tipo dovuto.");
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
			////////////////FINE RECUPERO LISTA DEI TIPIDOVUTO/////////////////////////////
			
			/**
			 * Inizializzo il bean per la ricerca
			 */
			initialize(anagraficaUfficioCapitoloAccertamentoCommand);
			
			/**
			 * Eseguo la query di ricerca
			 */
			PageDto<AnagraficaUfficioCapitoloAccertamentoDto> result = anagraficaUfficioCapitoloAccertamentoService.findByFilter(
					SecurityContext.getEnte().getId(), anagraficaUfficioCapitoloAccertamentoCommand.getCodTipoDovuto(), anagraficaUfficioCapitoloAccertamentoCommand.getCodiceUfficio(), 
					anagraficaUfficioCapitoloAccertamentoCommand.getDenominazioneUfficio(), anagraficaUfficioCapitoloAccertamentoCommand.getCodiceCapitolo(), 
					anagraficaUfficioCapitoloAccertamentoCommand.getDenominazioneCapitolo(), anagraficaUfficioCapitoloAccertamentoCommand.getAnnoEsercizio(), anagraficaUfficioCapitoloAccertamentoCommand.getCodiceAccertamento(), anagraficaUfficioCapitoloAccertamentoCommand.getDenominazioneAccertamento(), 
					null, null, anagraficaUfficioCapitoloAccertamentoCommand.getFlgAttivo(), true, anagraficaUfficioCapitoloAccertamentoCommand.getPage(), anagraficaUfficioCapitoloAccertamentoCommand.getPageSize(), null);
			
			// if(missingPagNoConfirm) messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.accertamenti.errore.notAuthorizedConfirmNoPag"));
			
			/*
			 * Set param da ritornare alla view
			 */
			mav.addObject("responseDtoPage", result);
			mav.addObject("listaTipiDovuti", activeOperatoreEnteTdAsObj);
			mav.addObject(anagraficaUfficioCapitoloAccertamentoCommand);
			mav.setViewName("visualizzaAnagraficaUfficioCapitoloAccertamento");
			
			
		}catch (Exception e) {
			logger.error("RICERCA :: GESTIONE ANAGRAFICA UFFICIO CAPITOLO ACCERTAMENTO :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: ERRORE", e);
			/* 
			 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
			 */
			return UtilitiesCtrl.getViewErrorMessage("mypivot.anagrafica.errore.ricerca");
		}
		
		/* */
		mav.addObject("messagesDto", messagesDto);
		
		return mav;
		
	}
	
	/**
	 * Inizializzo il bean che mappa la form di ricerca
	 * 
	 * @param anagraficaUfficioCapitoloAccertamentoCommand
	 * @author Alessandro Paolillo
	 */
	private void initialize(AnagraficaUfficioCapitoloAccertamentoCommand anagraficaUfficioCapitoloAccertamentoCommand) {
		
		/*
		 * Se la proprietà è nulla, la inizializzo a stringa vuota. La stringa vuota individua l'opzione di filtro ALL(tutti i tipi dovuto) 
		 */
		if( anagraficaUfficioCapitoloAccertamentoCommand.getCodTipoDovuto() == null) anagraficaUfficioCapitoloAccertamentoCommand.setCodTipoDovuto(""); 
		
		if(StringUtils.isBlank(anagraficaUfficioCapitoloAccertamentoCommand.getAnnoEsercizio())) {
			Date now = new Date();
			Calendar cal = Calendar.getInstance();
		    cal.setTime(now);
		    int year = cal.get(Calendar.YEAR);
		    anagraficaUfficioCapitoloAccertamentoCommand.setAnnoEsercizio(String.valueOf(year));
		}
		
	}
	
	
	/**
	 * Valorizza il bean di ricerca della form con gli ultimi valori dei filtri sottomessi e salvati.
	 * 
	 * @param request
	 * @param codIpaEnte
	 * @param codTipoDovuto
	 * @param codiceUfficio
	 * @param denominazioneUfficio
	 * @param codiceCapitolo
	 * @param denominazioneCapitolo
	 * @param codiceAccertamento
	 * @param denominazioneAccertamento
	 * @param page
	 * @param pageSize
	 * @param flgAttivo
	 * @param anagraficaUfficioCapitoloAccertamentoCommand
	 * @author Alessandro Paolillo
	 */
	private void setSessionFilterToBean(
			HttpServletRequest request, String codIpaEnte, String codTipoDovuto, String codiceUfficio, String denominazioneUfficio,
			String codiceCapitolo, String denominazioneCapitolo, String deAnnoEsercizio, String codiceAccertamento, String denominazioneAccertamento,
			String page, String pageSize, Boolean flgAttivo, AnagraficaUfficioCapitoloAccertamentoCommand anagraficaUfficioCapitoloAccertamentoCommand) {
		
		try{
			/** 
			 * Recupero dalla sessione l'oggetto in cui ho salvato i filtri della ricerca accertamento.
			 */
			Map<String, Object> filtersMap = SecurityContext.getEnteViewFilters(codIpaEnte, SessionVariables.ACTION_ANAGRAFICA_UCA_RICERCA);
			
			if (filtersMap == null) filtersMap = new HashMap<String, Object>();
			
			/** ============================================== CODICE TIPO DOVUTO ========================================== */
			try {
				Object session_pr = filtersMap == null ? null : filtersMap.get(SessionVariables.ANAGRAFICA_UCA_CODICE_TIPO_DOVUTO);
				if (codTipoDovuto == null)
					anagraficaUfficioCapitoloAccertamentoCommand.setCodTipoDovuto((String) session_pr);
				else
					anagraficaUfficioCapitoloAccertamentoCommand.setCodTipoDovuto(codTipoDovuto);
			} catch (Exception e) {
				anagraficaUfficioCapitoloAccertamentoCommand.setCodTipoDovuto("");
			}
			filtersMap.put(SessionVariables.ANAGRAFICA_UCA_CODICE_TIPO_DOVUTO, anagraficaUfficioCapitoloAccertamentoCommand.getCodTipoDovuto());
			
			/** ============================================== CODICE UFFICIO ========================================== */
			try {
				Object session_pr = filtersMap == null ? null : filtersMap.get(SessionVariables.ANAGRAFICA_UCA_CODICE_UFFICIO);
				if (codiceUfficio == null)
					anagraficaUfficioCapitoloAccertamentoCommand.setCodiceUfficio((String) session_pr);
				else
					anagraficaUfficioCapitoloAccertamentoCommand.setCodiceUfficio(codiceUfficio);
			} catch (Exception e) {
				anagraficaUfficioCapitoloAccertamentoCommand.setCodiceUfficio("");
			}
			filtersMap.put(SessionVariables.ANAGRAFICA_UCA_CODICE_UFFICIO, anagraficaUfficioCapitoloAccertamentoCommand.getCodiceUfficio());
			
			/** ============================================== DENOMINAZIONE UFFICIO ========================================== */
			try {
				Object session_pr = filtersMap == null ? null : filtersMap.get(SessionVariables.ANAGRAFICA_UCA_DENOMINAZIONE_UFFICIO);
				if (denominazioneUfficio == null)
					anagraficaUfficioCapitoloAccertamentoCommand.setDenominazioneUfficio((String) session_pr);
				else
					anagraficaUfficioCapitoloAccertamentoCommand.setDenominazioneUfficio(denominazioneUfficio);
			} catch (Exception e) {
				anagraficaUfficioCapitoloAccertamentoCommand.setDenominazioneUfficio("");
			}
			filtersMap.put(SessionVariables.ANAGRAFICA_UCA_DENOMINAZIONE_UFFICIO, anagraficaUfficioCapitoloAccertamentoCommand.getDenominazioneUfficio());
			
			/** ============================================== CODICE CAPITOLO ========================================== */
			try {
				Object session_pr = filtersMap == null ? null : filtersMap.get(SessionVariables.ANAGRAFICA_UCA_CODICE_CAPITOLO);
				if (codiceCapitolo == null)
					anagraficaUfficioCapitoloAccertamentoCommand.setCodiceCapitolo((String) session_pr);
				else
					anagraficaUfficioCapitoloAccertamentoCommand.setCodiceCapitolo(codiceCapitolo);
			} catch (Exception e) {
				anagraficaUfficioCapitoloAccertamentoCommand.setCodiceCapitolo("");
			}
			filtersMap.put(SessionVariables.ANAGRAFICA_UCA_CODICE_CAPITOLO, anagraficaUfficioCapitoloAccertamentoCommand.getCodiceCapitolo());
			
			/** ============================================== DENOMINAZIONE CAPITOLO ========================================== */
			try {
				Object session_pr = filtersMap == null ? null : filtersMap.get(SessionVariables.ANAGRAFICA_UCA_DENOMINAZIONE_CAPITOLO);
				if (denominazioneCapitolo == null)
					anagraficaUfficioCapitoloAccertamentoCommand.setDenominazioneCapitolo((String) session_pr);
				else
					anagraficaUfficioCapitoloAccertamentoCommand.setDenominazioneCapitolo(denominazioneCapitolo);
			} catch (Exception e) {
				anagraficaUfficioCapitoloAccertamentoCommand.setDenominazioneCapitolo("");
			}
			filtersMap.put(SessionVariables.ANAGRAFICA_UCA_DENOMINAZIONE_CAPITOLO, anagraficaUfficioCapitoloAccertamentoCommand.getDenominazioneCapitolo());
			
			/** ============================================== CODICE ACCERTAMENTO ========================================== */
			try {
				Object session_pr = filtersMap == null ? null : filtersMap.get(SessionVariables.ANAGRAFICA_UCA_CODICE_ACCERTAMENTO);
				if (codiceAccertamento == null)
					anagraficaUfficioCapitoloAccertamentoCommand.setCodiceAccertamento((String) session_pr);
				else
					anagraficaUfficioCapitoloAccertamentoCommand.setCodiceAccertamento(codiceAccertamento);
			} catch (Exception e) {
				anagraficaUfficioCapitoloAccertamentoCommand.setCodiceAccertamento("");
			}
			filtersMap.put(SessionVariables.ANAGRAFICA_UCA_CODICE_ACCERTAMENTO, anagraficaUfficioCapitoloAccertamentoCommand.getCodiceAccertamento());
			
			/** ============================================== DENOMINAZIONE ACCERTAMENTO ========================================== */
			try {
				Object session_pr = filtersMap == null ? null : filtersMap.get(SessionVariables.ANAGRAFICA_UCA_DENOMINAZIONE_ACCERTAMENTO);
				if (denominazioneAccertamento == null)
					anagraficaUfficioCapitoloAccertamentoCommand.setDenominazioneAccertamento((String) session_pr);
				else
					anagraficaUfficioCapitoloAccertamentoCommand.setDenominazioneAccertamento(denominazioneAccertamento);
			} catch (Exception e) {
				anagraficaUfficioCapitoloAccertamentoCommand.setDenominazioneAccertamento("");
			}
			filtersMap.put(SessionVariables.ANAGRAFICA_UCA_DENOMINAZIONE_ACCERTAMENTO, anagraficaUfficioCapitoloAccertamentoCommand.getDenominazioneAccertamento());
			
			/** ===================================== NUMERO PAGINA ================================= */
			try {
				Object session_pr = filtersMap == null ? null : filtersMap.get(SessionVariables.ANAGRAFICA_UCA_PAGE);
				if (page == null)
					anagraficaUfficioCapitoloAccertamentoCommand.setPage(Integer.parseInt(session_pr.toString()));
				else
					anagraficaUfficioCapitoloAccertamentoCommand.setPage(Integer.parseInt(page));
			} catch (Exception e) {
				anagraficaUfficioCapitoloAccertamentoCommand.setPage(1);
			}
			filtersMap.put(SessionVariables.ANAGRAFICA_UCA_PAGE, anagraficaUfficioCapitoloAccertamentoCommand.getPage());
			
		 /** ===================================== ELEMENTI PER PAGINA ================================= */
			try {
				Object session_pr = filtersMap == null ? null : filtersMap.get(SessionVariables.ANAGRAFICA_UCA_PAGE_SIZE);
				if (pageSize == null)
					anagraficaUfficioCapitoloAccertamentoCommand.setPageSize(Integer.parseInt(session_pr.toString()));
				else
					anagraficaUfficioCapitoloAccertamentoCommand.setPageSize(Integer.parseInt(pageSize));
			} catch (Exception e) {
				anagraficaUfficioCapitoloAccertamentoCommand.setPageSize(5);
			}
			filtersMap.put(SessionVariables.ANAGRAFICA_UCA_PAGE_SIZE, anagraficaUfficioCapitoloAccertamentoCommand.getPageSize());
			
			 /** ===================================== FLAG ATTIVO ================================= */
			try {
				Object session_pr = (filtersMap == null || filtersMap.get(SessionVariables.ANAGRAFICA_UCA_FLG_ATTIVO) == null) ? true : filtersMap.get(SessionVariables.ANAGRAFICA_UCA_FLG_ATTIVO);
				if (flgAttivo == null)
					anagraficaUfficioCapitoloAccertamentoCommand.setFlgAttivo((Boolean) session_pr);
				else
					anagraficaUfficioCapitoloAccertamentoCommand.setFlgAttivo(flgAttivo);
			} catch (Exception e) {
				anagraficaUfficioCapitoloAccertamentoCommand.setFlgAttivo(true);
			}
			filtersMap.put(SessionVariables.ANAGRAFICA_UCA_FLG_ATTIVO, anagraficaUfficioCapitoloAccertamentoCommand.getFlgAttivo());
			
			/** ============================================== ANNO ESERCIZIO ========================================== */
			try {
				Object session_pr = filtersMap == null ? null : filtersMap.get(SessionVariables.ANAGRAFICA_UCA_ANNO_ESERCIZIO);
				if (deAnnoEsercizio == null)
					anagraficaUfficioCapitoloAccertamentoCommand.setAnnoEsercizio((String) session_pr);
				else
					anagraficaUfficioCapitoloAccertamentoCommand.setAnnoEsercizio(deAnnoEsercizio);
			} catch (Exception e) {
			    anagraficaUfficioCapitoloAccertamentoCommand.setAnnoEsercizio("");
			}
			filtersMap.put(SessionVariables.ANAGRAFICA_UCA_ANNO_ESERCIZIO, anagraficaUfficioCapitoloAccertamentoCommand.getAnnoEsercizio());

			/** 
			 * Rimetto in sessione l'oggetto in cui ho salvato i filtri della ricerca.
			 */
			SecurityContext.setEnteViewFilters(codIpaEnte, SessionVariables.ACTION_ANAGRAFICA_UCA_RICERCA, filtersMap);
			
		}catch (Exception e) {
			logger.warn("RICERCA :: GESTIONE ANAGRAFICA UFFICIO CAPITOLO ACCERTAMENTO :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: ERRORE ", e);
		}
		
	}
	
	/**
	 * 
	 * @param request
	 * @param id
	 * @return
	 * @author Alessandro Paolillo
	 */
	@RequestMapping(value = { "/cancellaAnagrafica.html" }, method = RequestMethod.GET)
	public ModelAndView cancellaAnagrafica(HttpServletRequest request,
			@RequestParam(required = true) String id) throws Exception{
		
		ModelAndView mav = new ModelAndView();
		EnteTO enteTO = SecurityContext.getEnte();
		if (enteTO == null) {
			mav = new ModelAndView(new RedirectView(request.getContextPath() + "/protected/sceltaEnte.html"));
			return mav;
		} else {
			
			if (SecurityContext.getRoles() == null) {
				return new ModelAndView(new RedirectView(request.getContextPath() + "/protected/sceltaEnte.html"));
			}

			if (!SecurityContext.getRoles().contains(Constants.RUOLO_AMMINISTRATORE)) {
				mav.setViewName("message");

				MessagesDto messagesDto = new MessagesDto();
				messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.notAuthorized"));
				mav.addObject("messagesDto", messagesDto);

				return mav;
			}
			
			try{
				
				anagraficaUfficioCapitoloAccertamentoService.cancellaAnagrafica(Long.parseLong(id));
				
				mav = setupFormGestAna(request, "U", null, null, null);
				
				mav.addObject("esitoMsg", "CANCELLATO");
				
			}catch (Exception e) {
				logger.error("Errore nella cancellazione dell'anagrafica", e);
				mav = new ModelAndView();
				mav.setViewName("message");
				MessagesDto errMsgDto = new MessagesDto();
				errMsgDto.getErrorMessages().add(new DynamicMessageDto("mypivot.anagrafica.cancella.info.error"));
				mav.addObject("messagesDto", errMsgDto);
			}

		}
		return mav;
	}
	
	
	
	/**
	 * Ritorna la view per l'inserimento di una nuova angrafica
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 * @author Alessandro Paolillo
	 */
	@RequestMapping(value = { "/nuovaAnagrafica.html" }, method = RequestMethod.GET)
	public ModelAndView nuovaAnagrafica(HttpServletRequest request) throws Exception{
		
		ModelAndView mav = new ModelAndView();
		EnteTO enteTO = SecurityContext.getEnte();
		if (enteTO == null) {
			mav = new ModelAndView(new RedirectView(request.getContextPath() + "/protected/sceltaEnte.html"));
			return mav;
		}else{
			if (SecurityContext.getRoles() == null) {
				return new ModelAndView(new RedirectView(request.getContextPath() + "/protected/sceltaEnte.html"));
			}

			if (!SecurityContext.getRoles().contains(Constants.RUOLO_AMMINISTRATORE)) {
				mav.setViewName("message");

				MessagesDto messagesDto = new MessagesDto();
				messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.notAuthorized"));
				mav.addObject("messagesDto", messagesDto);

				return mav;
			}
			
			AnagraficaUfficioCapitoloAccertamentoCommand anagraficaCommand = new AnagraficaUfficioCapitoloAccertamentoCommand();
			
			mav = commonNuovaAnagrafica(request, anagraficaCommand);
		}
		return mav;
	}
	

	/**
	 * 
	 * @param request
	 * @param anagraficaCommand
	 * @return
	 * @author Alessandro Paolillo
	 */
	private ModelAndView commonNuovaAnagrafica(HttpServletRequest request, AnagraficaUfficioCapitoloAccertamentoCommand anagraficaCommand){
		
		ModelAndView mav = new ModelAndView();
		
		try{
			
			////////////RECUPERO LISTA DEI TIPIDOVUTO PER ENTE SELEZIONATO/////////////////		
			/**
			 * Recupero la lista dei tipi dovuto dell'ente per cui l'utente è un operatore attivo. 
			 * Se la lista è vuota, mostro un messaggio di errore perchè vuol dire che l'utente non può ne consultare ne creare accertamenti.
			 */
			List<EnteTipoDovutoTO> activeOperatoreEnteTdAsObj = operatoreEnteTipoDovutoService.getListaEnteTipoDovutoForOperatoreAndCodIpaEnte(SecurityContext.getUtente().getCodFedUserId(), SecurityContext.getEnte().getCodIpaEnte());
			
			if (activeOperatoreEnteTdAsObj == null || activeOperatoreEnteTdAsObj.isEmpty()) {
				logger.warn("RICERCA :: GESTIONE ANAGRAFICA UFFICIO CAPITOLO ACCERTAMENTO :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: NON risulta OPERATORE per nessun tipo dovuto.");
				/* 
				 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
				 */
				return UtilitiesCtrl.getViewErrorMessage("mypivot.messages.error.nessunTipoDovutoAssegnato");
			}
			////////////////FINE RECUPERO LISTA DEI TIPIDOVUTO/////////////////////////////
			
			////////////RECUPERO LISTA DEGLI UFFICI PER ENTE SELEZIONATO/////////////////	
			List<AnagraficaUfficioCapitoloAccertamentoTO> activeUfficioEnteTdAsObj = anagraficaUfficioCapitoloAccertamentoService.findDistinctUfficiByEnteId(SecurityContext.getEnte().getId());
			////////////////FINE RECUPERO LISTA UFFICI/////////////////////////////
			
			////////////RECUPERO LISTA CAPITOLI PER ENTE SELEZIONATO/////////////////
			List<AnagraficaUfficioCapitoloAccertamentoTO> activeCapitoloEnteTdAsObj = anagraficaUfficioCapitoloAccertamentoService.findDistinctCapitoliByEnteId(SecurityContext.getEnte().getId());
			////////////////FINE RECUPERO LISTA CAPITOLI/////////////////////////////
			
			mav.addObject("listaTipiDovuti", activeOperatoreEnteTdAsObj);
			mav.addObject("listaUffici", activeUfficioEnteTdAsObj);
			mav.addObject("listaCapitoli", activeCapitoloEnteTdAsObj);
			mav.addObject(anagraficaCommand);
			mav.setViewName("nuovaAnagrafica");
			
			
		}catch (Exception e) {
			logger.error("Errore nell'inserimento di una nuova anagrafica", e);
			mav = new ModelAndView();
			mav.setViewName("message");
			MessagesDto errMsgDto = new MessagesDto();
			errMsgDto.getErrorMessages().add(new DynamicMessageDto("mypivot.anagrafica.inserisci.info.error"));
			mav.addObject("messagesDto", errMsgDto);
		}
	
	return mav;
		
	}
	
	
	/**
	 * 
	 * @param anagraficaUfficioCapitoloAccertamentoCommand
	 * @param request
	 * @return
	 * @throws Exception
	 * @author Alessandro Paolillo
	 */
	@RequestMapping(value = { "/inserisciAnagrafica.html" }, method = RequestMethod.POST)
	public ModelAndView inserisciAnagrafica(
			@ModelAttribute("anagraficaUfficioCapitoloAccertamentoCommand") AnagraficaUfficioCapitoloAccertamentoCommand anagraficaUfficioCapitoloAccertamentoCommand,
			HttpServletRequest request, BindingResult result) throws Exception {
		
		ModelAndView mav = new ModelAndView();

		EnteTO enteTO = SecurityContext.getEnte();
		UtenteTO utenteTO = SecurityContext.getUtente();
		if (enteTO == null) {
			mav = new ModelAndView(new RedirectView(request.getContextPath() + "/protected/sceltaEnte.html"));
		} else if (utenteTO == null || !SecurityContext.getRoles().contains(Constants.RUOLO_AMMINISTRATORE)) {
			mav = new ModelAndView();
			// ERRORE, tentativo di accedere ad ente non autorizzato
			logger.error("Tentativo di modifica anagrafica non autorizzata");
			mav.setViewName("message");
			MessagesDto messagesDto = new MessagesDto();
			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.notAuthorized"));
			mav.addObject("messagesDto", messagesDto);
			} else {
				
				/**
				 * Validazione form
				 */
				validator.validate(anagraficaUfficioCapitoloAccertamentoCommand, result);
				
				MessagesDto messagesDto = new MessagesDto();
				if (anagraficaUfficioCapitoloAccertamentoCommand == null 
						|| anagraficaUfficioCapitoloAccertamentoCommand.getFlgAttivo() == null
						|| StringUtils.isBlank(anagraficaUfficioCapitoloAccertamentoCommand.getCodTipoDovuto())
						|| StringUtils.isBlank(anagraficaUfficioCapitoloAccertamentoCommand.getCodiceCapitolo())
						|| StringUtils.isBlank(anagraficaUfficioCapitoloAccertamentoCommand.getDenominazioneCapitolo())
						|| StringUtils.isBlank(anagraficaUfficioCapitoloAccertamentoCommand.getAnnoEsercizio())) {
					
					String errMsg = "Mancano dati obbligatori per l'inserimento di una nuova anagrafica "+ anagraficaUfficioCapitoloAccertamentoCommand.stampaObbligatori();
					logger.error(errMsg);
					messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.anagrafica.dettaglioAnagrafica.modAnagrafica.nocommand"));
					
					if (result.hasErrors()){
						List<EnteTipoDovutoTO> activeOperatoreEnteTd = operatoreEnteTipoDovutoService.getListaEnteTipoDovutoForOperatoreAndCodIpaEnte(SecurityContext.getUtente().getCodFedUserId(), SecurityContext.getEnte().getCodIpaEnte());
						List<AnagraficaUfficioCapitoloAccertamentoTO> activeUfficioEnteTd = anagraficaUfficioCapitoloAccertamentoService.findDistinctUfficiByEnteId(SecurityContext.getEnte().getId());
						List<AnagraficaUfficioCapitoloAccertamentoTO> activeCapitoloEnteTd = anagraficaUfficioCapitoloAccertamentoService.findDistinctCapitoliByEnteId(SecurityContext.getEnte().getId());
						
						mav.addObject("listaTipiDovuti", activeOperatoreEnteTd);
						mav.addObject("listaUffici", activeUfficioEnteTd);
						mav.addObject("listaCapitoli", activeCapitoloEnteTd);
						mav.addObject(anagraficaUfficioCapitoloAccertamentoCommand);
						mav.setViewName("nuovaAnagrafica");
						return mav;
					}
					
					}else{
						
						if (result.hasErrors()){
							List<EnteTipoDovutoTO> activeOperatoreEnteTd = operatoreEnteTipoDovutoService.getListaEnteTipoDovutoForOperatoreAndCodIpaEnte(SecurityContext.getUtente().getCodFedUserId(), SecurityContext.getEnte().getCodIpaEnte());
							List<AnagraficaUfficioCapitoloAccertamentoTO> activeUfficioEnteTd = anagraficaUfficioCapitoloAccertamentoService.findDistinctUfficiByEnteId(SecurityContext.getEnte().getId());
							List<AnagraficaUfficioCapitoloAccertamentoTO> activeCapitoloEnteTd = anagraficaUfficioCapitoloAccertamentoService.findDistinctCapitoliByEnteId(SecurityContext.getEnte().getId());
							
							mav.addObject("listaTipiDovuti", activeOperatoreEnteTd);
							mav.addObject("listaUffici", activeUfficioEnteTd);
							mav.addObject("listaCapitoli", activeCapitoloEnteTd);
							mav.addObject(anagraficaUfficioCapitoloAccertamentoCommand);
							mav.setViewName("nuovaAnagrafica");
							return mav;
						}
						
						AnagraficaUfficioCapitoloAccertamento nuovaAnagrafica = new AnagraficaUfficioCapitoloAccertamento();
						
						nuovaAnagrafica.setCodTipoDovuto(anagraficaUfficioCapitoloAccertamentoCommand.getCodTipoDovuto());
						if (StringUtils.isBlank(anagraficaUfficioCapitoloAccertamentoCommand.getCodiceUfficio())
								|| anagraficaUfficioCapitoloAccertamentoCommand.getCodiceUfficio().equals("n/a")){
							nuovaAnagrafica.setCodUfficio("n/a");
							nuovaAnagrafica.setDeUfficio("n/a");
							nuovaAnagrafica.setFlgAttivo(true);
						}
						else {
							nuovaAnagrafica.setCodUfficio(anagraficaUfficioCapitoloAccertamentoCommand.getCodiceUfficio());
							nuovaAnagrafica.setDeUfficio(anagraficaUfficioCapitoloAccertamentoCommand.getDenominazioneUfficio());						
							nuovaAnagrafica.setFlgAttivo(anagraficaUfficioCapitoloAccertamentoCommand.getFlgAttivo());
						}
						nuovaAnagrafica.setCodCapitolo(anagraficaUfficioCapitoloAccertamentoCommand.getCodiceCapitolo());
						nuovaAnagrafica.setDeCapitolo(anagraficaUfficioCapitoloAccertamentoCommand.getDenominazioneCapitolo());
						nuovaAnagrafica.setDeAnnoEsercizio(anagraficaUfficioCapitoloAccertamentoCommand.getAnnoEsercizio());
						nuovaAnagrafica.setCodAccertamento(StringUtils.isBlank(anagraficaUfficioCapitoloAccertamentoCommand.getCodiceAccertamento()) ? "n/a" : anagraficaUfficioCapitoloAccertamentoCommand.getCodiceAccertamento());
						nuovaAnagrafica.setDeAccertamento(StringUtils.isBlank(anagraficaUfficioCapitoloAccertamentoCommand.getDenominazioneAccertamento()) ? "n/a" : anagraficaUfficioCapitoloAccertamentoCommand.getDenominazioneAccertamento());
						nuovaAnagrafica.setDtCreazione(new Date());
						nuovaAnagrafica.setDtUltimaModifica(new Date());
						Ente ente = new Ente();
						ente.setId(enteTO.getId());
						nuovaAnagrafica.setEnte(ente);
						
						AggiornaAnagraficaUfficioCapitoloAccertamentoResultDto esitoInserimento = anagraficaUfficioCapitoloAccertamentoService.salvaAnagrafica(nuovaAnagrafica);
						
						AnagraficaUfficioCapitoloAccertamentoDto nuovoDto = esitoInserimento.getAnagraficaAggiornata();
						ESITO esito = esitoInserimento.getEsito();
						String esitoMsg="";
						switch (esito) {
							case INSERITA:
								mav = commonVisualizzaDettaglio(request, enteTO, String.valueOf(nuovoDto.getId()), nuovoDto.getCodAccertamento(), nuovoDto.getCodCapitolo(), nuovoDto.getCodTipoDovuto(), nuovoDto.getDeTipoDovuto(), 
										nuovoDto.getCodUfficio(), nuovoDto.getDeAccertamento(), nuovoDto.getDeAnnoEsercizio(), nuovoDto.getDeCapitolo(), nuovoDto.getDeUfficio(), nuovoDto.getFlgAttivo());
								logger.debug("Anagrafica inserita correttamente [" + esito + "]");
								messagesDto.getInformationMessages().add(new DynamicMessageDto("mypivot.anagrafica.info.inserimentoAnagrafica.ok"));
								esitoMsg="INSERITA";
								break;
							case EXIST:
								mav = commonNuovaAnagrafica(request, anagraficaUfficioCapitoloAccertamentoCommand);
								logger.debug("Anagrafica già esistente [" + esito + "]");
								messagesDto.getInformationMessages().add(new DynamicMessageDto("mypivot.anagrafica.info.inserimentoAnagrafica.exist"));
								esitoMsg="EXIST";
								break;
							default:
								mav = commonNuovaAnagrafica(request, anagraficaUfficioCapitoloAccertamentoCommand);
								logger.debug("Errore nell'inserimento dell'anagrafica [" + esito + "]");
								messagesDto.getInformationMessages().add(new DynamicMessageDto("mypivot.anagrafica.info.inserimentoAnagrafica.ko"));
								esitoMsg="ERROR";
								break;
						}
						
						mav.addObject("esitoMsg",esitoMsg);
						mav.addObject("messagesDto", messagesDto);
						
					}
			}
		
		return mav;
		
	}
	
	
	/**
	 * Ritorna la view per la visualizzazione del dettaglio dell'anagrafica
	 * 
	 * @param request
	 * @param enteTO
	 * @param id
	 * @param codAccertamento
	 * @param codCapitolo
	 * @param codTipoDovuto
	 * @param deTipoDovuto
	 * @param codUfficio
	 * @param deAccertamento
	 * @param deAnnoEsercizio
	 * @param deCapitolo
	 * @param deUfficio
	 * @param flgAttivo
	 * @return
	 * @author Alessandro Paolillo
	 */
	private ModelAndView commonVisualizzaDettaglio(HttpServletRequest request, EnteTO enteTO, String id,
			String codAccertamento, String codCapitolo, String codTipoDovuto, String deTipoDovuto, String codUfficio,
			String deAccertamento, String deAnnoEsercizio, String deCapitolo, String deUfficio, Boolean flgAttivo) {
		
		ModelAndView mav = new ModelAndView();
		
		if (SecurityContext.getRoles() == null) {
			return new ModelAndView(new RedirectView(request.getContextPath() + "/protected/sceltaEnte.html"));
		}

		if (!SecurityContext.getRoles().contains(Constants.RUOLO_AMMINISTRATORE)) {
			mav.setViewName("message");

			MessagesDto messagesDto = new MessagesDto();
			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.notAuthorized"));
			mav.addObject("messagesDto", messagesDto);

			return mav;
		}
		
		try{
			AnagraficaUfficioCapitoloAccertamentoDto anagraficaUfficioCapitoloAccertamentoDto = new AnagraficaUfficioCapitoloAccertamentoDto();
			anagraficaUfficioCapitoloAccertamentoDto.setId(Long.parseLong(id));
			anagraficaUfficioCapitoloAccertamentoDto.setCodAccertamento(codAccertamento);
			anagraficaUfficioCapitoloAccertamentoDto.setCodCapitolo(codCapitolo);
			anagraficaUfficioCapitoloAccertamentoDto.setCodTipoDovuto(codTipoDovuto);
			anagraficaUfficioCapitoloAccertamentoDto.setDeTipoDovuto(deTipoDovuto);
			anagraficaUfficioCapitoloAccertamentoDto.setCodUfficio(codUfficio);
			anagraficaUfficioCapitoloAccertamentoDto.setDeAccertamento(deAccertamento);
			anagraficaUfficioCapitoloAccertamentoDto.setDeAnnoEsercizio(deAnnoEsercizio);
			anagraficaUfficioCapitoloAccertamentoDto.setDeCapitolo(deCapitolo);
			anagraficaUfficioCapitoloAccertamentoDto.setDeUfficio(deUfficio);
			anagraficaUfficioCapitoloAccertamentoDto.setFlgAttivo(flgAttivo);
	
			mav.addObject("isAmministratore",Boolean.valueOf((SecurityContext.getRoles().contains(Constants.RUOLO_AMMINISTRATORE))));
			mav.addObject("anagUffCapAccDto", anagraficaUfficioCapitoloAccertamentoDto);
			mav.setViewName("visualizzaDettaglioAnagrafica");
		
		}catch (Exception e) {
			logger.error("Impossibile visualizzare il dettaglio dell'anagrafica Ufficio Capitolo Accertamento", e);
			mav = new ModelAndView();
			mav.setViewName("message");
			MessagesDto errMsgDto = new MessagesDto();
			errMsgDto.getErrorMessages().add(new DynamicMessageDto("mypivot.anagrafica.dettaglio.info.error"));
			mav.addObject("messagesDto", errMsgDto);
		}
		return mav;
	}
	
	private File convert(MultipartFile file)
	{    
		File convFile = new File(file.getOriginalFilename());
		try {
			convFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(convFile); 
			fos.write(file.getBytes());
			fos.close(); 
		} catch (IOException e) {
			e.printStackTrace();
		}
		return convFile;
	}
	
	/**
	 * 
	 * Gestione dei un file csv per l'inserimento massivo di anagrafiche uff cap acc
	 * 
	 * @param file
	 * @return
	 * @author Alessandro Paolillo
	 */
	@RequestMapping(value = {"/uploadCSV.json"}, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseUploadAnagUffCapAccDto> uploadCSV(@RequestParam("file") MultipartFile file) {

		logger.debug("INSERIMENTO MASSIVO :: ANAGRAFICA UFF CAP ACC :: POST :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: START");
		ResponseUploadAnagUffCapAccDto response = new ResponseUploadAnagUffCapAccDto();
		EnteTO ente = SecurityContext.getEnte();

		if (ente.getCodIpaEnte().equals("R_VENETO")){
			response.setMessage("Non è consentito l'inserimento massivo per la regione Veneto.");
			response.setCode("400");
			logger.debug("INSERIMENTO MASSIVO :: ANAGRAFICA UFF CAP ACC :: POST :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: END");
			return new ResponseEntity<ResponseUploadAnagUffCapAccDto>(response, HttpStatus.OK);
		}

		String split[] = {};
		String filename = file.getOriginalFilename();
		InputStream inputStream = null;
		FileInputStream fileInputStream = null;
		ZipFile zipFile = null;
		ZipInputStream zipInputStream = null;
		String endName="1_0.zip";
		try {
			fileInputStream = new FileInputStream(convert(file));
			zipInputStream = new ZipInputStream(fileInputStream);
			ZipEntry zipEntry = zipInputStream.getNextEntry();

			if (zipInputStream.getNextEntry() != null){
				zipInputStream.close();
				response.setMessage("Non è consentito un file .zip con più di un tracciato csv.");
				response.setCode("400");
				logger.debug("INSERIMENTO MASSIVO :: ANAGRAFICA UFF CAP ACC :: POST :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: END");
				return new ResponseEntity<ResponseUploadAnagUffCapAccDto>(response, HttpStatus.OK);
			}

			zipFile = new ZipFile(filename);
			inputStream = zipFile.getInputStream(zipEntry);
			//controllo che i nomi del file zip e del csv all'interno siano uguali tranne le estensioni
			if (!zipEntry.getName().substring(0, zipEntry.getName().length()-4).equals(filename.substring(0, filename.length()-4))){
				zipFile.close();
				zipInputStream.close();
				response.setMessage("Il nome del file non è corretto.");
				response.setCode("400");
				logger.debug("INSERIMENTO MASSIVO :: ANAGRAFICA UFF CAP ACC :: POST :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: END");
				return new ResponseEntity<ResponseUploadAnagUffCapAccDto>(response, HttpStatus.OK);
			}
			if (!zipEntry.getName().endsWith(".csv")){
				zipFile.close();
				zipInputStream.close();
				response.setMessage("Il file .zip deve contenere un tracciato csv.");
				response.setCode("400");
				logger.debug("INSERIMENTO MASSIVO :: ANAGRAFICA UFF CAP ACC :: POST :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: END");
				return new ResponseEntity<ResponseUploadAnagUffCapAccDto>(response, HttpStatus.OK);
			}	

			//il nome del file deve essere scritto con il formato C_IPA-NOME FLUSSO-VERSIONE. es: R_VENETO-FLUSSO_IMPORT_MASSIVO-1_0
			if (!filename.contains("-")){
				zipFile.close();
				zipInputStream.close();
				response.setMessage("Il nome del file non è corretto");
				response.setCode("400");
				logger.debug("INSERIMENTO MASSIVO :: ANAGRAFICA UFF CAP ACC :: POST :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: END");
				return new ResponseEntity<ResponseUploadAnagUffCapAccDto>(response, HttpStatus.OK);
			}
			split = filename.split("-");
			if (split.length != 3 || !split[0].equals(ente.getCodIpaEnte()) || !split[2].equals(endName)){
				zipFile.close();
				zipInputStream.close();
				response.setMessage("Il nome del file non è corretto.");
				response.setCode("400");
				logger.debug("INSERIMENTO MASSIVO :: ANAGRAFICA UFF CAP ACC :: POST :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: END");
				return new ResponseEntity<ResponseUploadAnagUffCapAccDto>(response, HttpStatus.OK);
			}	
		} catch (IOException e1) {
			response.setMessage("Errore durante la lettura del file .zip");
			response.setCode("400");
			logger.debug("INSERIMENTO MASSIVO :: ANAGRAFICA UFF CAP ACC :: POST :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: END");
			return new ResponseEntity<ResponseUploadAnagUffCapAccDto>(response, HttpStatus.OK);
		}
		try {
			anagraficaUfficioCapitoloAccertamentoService.manageFileCSV(inputStream, ente.getCodIpaEnte());
		} catch (Exception e) {
			try {
				zipFile.close();
				zipInputStream.close();
			} catch (IOException e1) {}
			response.setMessage(e.getMessage());
			response.setCode("400");
			logger.debug("INSERIMENTO MASSIVO :: ANAGRAFICA UFF CAP ACC :: POST :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: END");
			return new ResponseEntity<ResponseUploadAnagUffCapAccDto>(response, HttpStatus.OK);
		}
		response.setMessage("Upload eseguito correttamente");
		response.setCode("200");
		try {
			zipFile.close();
			zipInputStream.close();
		} catch (IOException e1) {}
		logger.debug("INSERIMENTO MASSIVO :: ANAGRAFICA UFF CAP ACC :: POST :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: END");
		return new ResponseEntity<ResponseUploadAnagUffCapAccDto>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = {"/keepAlive.json" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDto> keepAlive(@RequestParam(required = true) Long _) {
		ResponseDto responseDto = new ResponseDto();
		responseDto.setStatus("OK");
		return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param request
	 * @param codUff
	 * @return
	 * @throws Exception
	 * @author Alessandro Paolillo
	 */
	@RequestMapping(value = { "/getAccertamenti.json" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity< List<AnagraficaUfficioCapitoloAccertamentoTO>> getAccertamenti(@RequestParam("codiceUfficio") String codUff, @RequestParam("codiceCapitolo") String codCap) throws Exception{
		
		List<AnagraficaUfficioCapitoloAccertamentoTO> activeCapitoliEnteTdAsObj = null;
		logger.debug("INIZIO RECUPERO ACCERTAMENTI DATO ENTE ID, CODICE UFFICIO E CODICE CAPITOLO, codiceUfficio: " + codUff + " codiceCapitolo: " + codCap);
		try{
				
			activeCapitoliEnteTdAsObj = anagraficaUfficioCapitoloAccertamentoService.findDistinctAccertamentiByCodUffcodCap(SecurityContext.getEnte().getId(), codUff, codCap);
			
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("ERRORE RECUPERO ACCERTAMENTI DATO ENTE ID, CODICE UFFICIO E CODICE CAPITOLO, codiceUfficio: " + codUff + " codiceCapitolo: " + codCap + "EXCEPTION:" + e);
			return new ResponseEntity< List<AnagraficaUfficioCapitoloAccertamentoTO>> ( activeCapitoliEnteTdAsObj, HttpStatus.BAD_REQUEST);
		}
		logger.debug("FINE RECUPERO ACCERTAMENTI DATO ENTE ID, CODICE UFFICIO E CODICE CAPITOLO, codiceUfficio: " + codUff + " codiceCapitolo: " + codCap);
		return new ResponseEntity< List<AnagraficaUfficioCapitoloAccertamentoTO>> ( activeCapitoliEnteTdAsObj, HttpStatus.OK);
	}

	
}
