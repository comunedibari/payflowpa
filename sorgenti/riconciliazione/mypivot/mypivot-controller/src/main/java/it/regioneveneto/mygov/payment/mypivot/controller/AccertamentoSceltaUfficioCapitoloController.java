package it.regioneveneto.mygov.payment.mypivot.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import it.regioneveneto.mygov.payment.mypivot.controller.command.AccertamentoSceltaUfficioCapitoloCommand;
import it.regioneveneto.mygov.payment.mypivot.controller.command.AccertamentoSceltaUfficioCapitoloCommand.OPERATION;
import it.regioneveneto.mygov.payment.mypivot.controller.utils.AccertamentoUtils;
import it.regioneveneto.mygov.payment.mypivot.controller.utils.UtilitiesCtrl;
import it.regioneveneto.mygov.payment.mypivot.controller.validator.AccertamentoSceltaUfficioCapitoloValidator;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.AccertamentoDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.AccertamentoFlussoExportDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.AccertamentoUfficioCapitoloDto;
import it.regioneveneto.mygov.payment.mypivot.domain.to.AnagraficaUfficioCapitoloAccertamentoTO;
import it.regioneveneto.mygov.payment.mypivot.service.AccertamentoDettaglioService;
import it.regioneveneto.mygov.payment.mypivot.service.AccertamentoService;
import it.regioneveneto.mygov.payment.mypivot.service.AnagraficaUfficioCapitoloAccertamentoService;
import it.regioneveneto.mygov.payment.mypivot.service.OperatoreEnteTipoDovutoService;
import it.regioneveneto.mygov.payment.mypivot.utils.Constants;
import it.regioneveneto.mygov.payment.mypivot.utils.SecurityContext;

/**
 * Controller per gestire la spacchettamento in capitoli e accertamenti delle RT aggiunte all'accertemento.
 * 
 * Gli scenari d'uso sono due :
 *  1) selezione di un singolo pagamento:
 * 										Questo scenario che prevede l'acquisizione di più pagamenti non permette lo spacchettamento su più 
 * 										capitoli/accertamenti per questi è attribuibile quindi un unico capitolo/accertamento.
 * 										Sono inserite in "mygov_accertamento dettaglio" tante righe quanti sono i pagamenti selezionati e gli importi
 * 										riportati in tabella corrispondono agli importi dei singoli pagamenti.
 * 
 *  2) selezione di molteplici pagamenti: 
 *  									 Questo scenario che prevede l'acquisizione di un singolo pagamento permettendo quindi lo spacchettamento su 
 *  									 più capitoli/accertamenti. Sono inserite in "mygov_accertamento dettaglio" tante righe quanti sono gli 
 *  									 importi inseriti dall'utente.
 *  
 * @author Marianna Memoli
 */
@Controller
@RequestMapping("/protected/accertamenti")
public class AccertamentoSceltaUfficioCapitoloController {

	private static Log logger = LogFactory.getLog(AccertamentoSceltaUfficioCapitoloController.class);

	@Autowired
	private OperatoreEnteTipoDovutoService operatoreEnteTipoDovutoService;
	
	@Autowired
	private AccertamentoService accertamentoService;
	
	@Autowired
	private AnagraficaUfficioCapitoloAccertamentoService ufficiService;
	
	@Autowired
	private AccertamentoSceltaUfficioCapitoloValidator validator;

	@Autowired
	private AccertamentoDettaglioService accertamentoDttService;
	
	
	public AccertamentoSceltaUfficioCapitoloController() {
		super();
	}
	
	/**
	 * Range di anni -10 +10 rispetto l'anno corrente.
	 * 
	 * @return {@link List<String>}
	 * @author Marianna Memoli
	 */
	@ModelAttribute("yearsList")
    public List<String> populateStatiAccertamento() {
		List<String> years = new ArrayList<String>();
		
		/* Get Anno corrente */
		Integer year = Integer.parseInt(Constants.YYYY.format(new Date()));
		
		/* Get range -10 */
		for (int i = 0; i < 10; i++) years.add(((year - 10) + i) + "");
		
		/* Get range +10 */
		//for (int i = 0; i < 10; i++) years.add((year + i) + "");
		years.add(year + "");
		
        return years;
    }
	
	/**
	 * Restituisce la view con la form per la definizione dei capitoli e accertamenti (GET). 
	 * -----------------------------------------------------------------------------------------------------------------------------------
	 * Prima di erogare la pagina effettuo dei controlli per determinare se sussistono le condizioni tali perchè l'utente possa accedere
	 * alla funzionalità.
	 * 
	 * I controlli eseguiti sono i seguenti:
	 *   1. Il parametro di request "accertamentoID" è valorizzato, se mancante verrà rediretto alla pagina di ricerca degli accertamenti.
	 *   2. L'attributo della redirect "pagamentiList" è valorizzato, se mancante verrà rediretto alla pagina di edit dell'accertamento.
	 *   3. L'attributo della redirect "totaleImportoDovuti" è valorizzato, se mancante verrà rediretto alla pagina di edit dell'accertamento.
	 * 
	 * A seguito dei controlli, è ritornata la view con i seguenti oggetti:
	 * 
	 *   "sceltaCommand": model attribute che mappa la form.
	 * 	 "doSubmit": 	  true o false a seconda se al caricamento della view devo forzare il submit. 
	 * 	 "emptyUffici":   true o false a seconda se l'elenco uffici è vuoto. 
	 * 
	 * @param {@link HttpServletRequest} request
	 * 
	 * @return {@link ModelAndView}
	 * @author Marianna Memoli
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/sceltaUfficioCapitolo.html", method = {RequestMethod.GET})
	public ModelAndView sceltaUfficioCapitolo_Get(HttpServletRequest request, Model model, @RequestParam(value = "accertamentoID") String accertamentoID){
		try{
			logger.debug("SCELTA UFFICIO-CAPITOLO :: ACCERTAMENTO :: GET :: Fields[accertamentoID: " + accertamentoID + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: START");

			/**
			 * La funzione verifica che l'utente autenticato abbia selezionato l'ente.
			 */
			if(!UtilitiesCtrl.hasSecurityEnte()) return new ModelAndView(new RedirectView(request.getContextPath() + Constants.PATH_PAGE_SCELTA_ENTE));
		
			/**
			 * La funzione verifica che il parametro di request dell'id accertamento sia valorizzato.
			 */
			if(!StringUtils.hasText(accertamentoID)){
				/* */
				logger.error("SCELTA UFFICIO-CAPITOLO :: ACCERTAMENTO :: Fields[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: Impossibile caricare la view il parametro di request 'accertamentoID' è nullo. L'utente è rediretto alla pagina di ricerca Accertamenti.");
				/* */
				return new ModelAndView(new RedirectView(request.getContextPath() + Constants.PATH_PAGE_RICERCA_ACCERTAMENTI));
			}
			
			/**
			 * Verifico se è stato passato come attributo della redirect i parametri "pagamentiList" e "totaleImportoDovuti".
			 * 
			 * Se il parametro è assente, rimando l'utente alla pagina per aggiungere i pagamenti in accertamento.
			 */    
			if(model == null || !model.containsAttribute("pagamentiList") || !model.containsAttribute("totaleImportoDovuti")){
				/* */
				logger.error("SCELTA UFFICIO-CAPITOLO :: ACCERTAMENTO :: Fields[accertamentoID: " + accertamentoID + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: Impossibile caricare la view l'attributo del model 'pagamentiList' e/o 'totaleImportoDovuti' sono mancanti. L'utente è rediretto alla pagina per aggiungere i pagamenti in accertamento.");
				/* */
				return new ModelAndView(new RedirectView(request.getContextPath() + Constants.PATH_PAGE_ADD_PAG_ACCERTAMENTO + "?accertamentoID=" + accertamentoID));
			}
			
			/*
			 * GET RT
			 */
			List<AccertamentoFlussoExportDto> pagamentiList = (List<AccertamentoFlussoExportDto>) model.asMap().get("pagamentiList");
			
			/**
			 * Se la lista di RT è vuota, rimando l'utente alla pagina per aggiungere i pagamenti in accertamento.
			 */ 
			if(pagamentiList.isEmpty()){
				/* */
				logger.error("SCELTA UFFICIO-CAPITOLO :: ACCERTAMENTO :: Fields[accertamentoID: " + accertamentoID + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: Impossibile caricare la view l'attributo del model 'pagamentiList' è nullo. L'utente è rediretto alla pagina per aggiungere i pagamenti in accertamento.");
				/* */
				return new ModelAndView(new RedirectView(request.getContextPath() + Constants.PATH_PAGE_ADD_PAG_ACCERTAMENTO + "?accertamentoID=" + accertamentoID));
			}
			
			/*
			 * GET importo totale dei dovuti nell'accertamento.
			 */
			BigDecimal totImportoDovuti = (BigDecimal) model.asMap().get("totaleImportoDovuti");
			
			String formatTotImportoDovuti = "€ " + Constants.NUMBER_FORMAT_US.format(totImportoDovuti);
			
			/**
			 * Instance model attribute 
			 */
			AccertamentoSceltaUfficioCapitoloCommand sceltaUfficioCapitoloCommand = new AccertamentoSceltaUfficioCapitoloCommand();
			sceltaUfficioCapitoloCommand.setFormatTotImportoDovuti(formatTotImportoDovuti);						/* Importo totale dei dovuti formattato come "€ <importo>" */
			sceltaUfficioCapitoloCommand.setTotImportoDovuti(totImportoDovuti);									/* Importo totale dei dovuti */
			sceltaUfficioCapitoloCommand.setFlussiExportDTO(pagamentiList);										/* Lista RT */
			sceltaUfficioCapitoloCommand.setAccertamentoId(accertamentoID);										/* Identificativo accertamento */
			sceltaUfficioCapitoloCommand.setMultiple(pagamentiList.size() == 1 ? Boolean.FALSE : Boolean.TRUE); /* Gestisce il caso di selezione singola o multipla delle RT */
			sceltaUfficioCapitoloCommand.setOperation(OPERATION.NO_OP);											/* No operation */
			sceltaUfficioCapitoloCommand.setCodUfficio("");
			sceltaUfficioCapitoloCommand.setCodCapitolo("");
			sceltaUfficioCapitoloCommand.setCodAccertamento("");
			/*
			 * Init value in caso di scenario multiple = false
			 */
			sceltaUfficioCapitoloCommand.setImporto(totImportoDovuti.toPlainString()); 	/* Importo parziale del capitolo */
			sceltaUfficioCapitoloCommand.setFormatTotImportoCapitoli("€ 0.0");
			sceltaUfficioCapitoloCommand.setTotImportoCapitoli(new BigDecimal(0));      /* Totale importo assegnato ai capitoli */
			sceltaUfficioCapitoloCommand.setFormatTotImportoMancante(formatTotImportoDovuti);
			sceltaUfficioCapitoloCommand.setEnableFormAdd(true); 
			
			/**
			 * Set param da ritornare alla view
			 */
			ModelAndView mav = new ModelAndView("sceltaUfficioCapitoloAccertamento");
			mav.addObject("sceltaCommand", sceltaUfficioCapitoloCommand); /* Model attribute */
			mav.addObject("doSubmit", Boolean.TRUE);					  /* Force submit form */
			mav.addObject("emptyUffici", Boolean.FALSE);
			
			return mav;
		}catch(Exception e){
			logger.error("SCELTA UFFICIO-CAPITOLO :: ACCERTAMENTO :: GET :: Fields[accertamentoID: " + accertamentoID + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: ERRORE ", e);
			/* 
			 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
			 */
			return UtilitiesCtrl.getViewErrorMessage("mypivot.accertamenti.errore.internal");
		}
	}
	
	/**
	 * Restituisce la view con la form per la definizione dei capitoli e accertamenti (POST). 
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
	 *   "sceltaCommand": model attribute che mappa la form.
	 * 	 "doSubmit": true o false a seconda se al caricamento della view devo forzare il submit. 
	 * 
	 * @param {@link HttpServletRequest} request
	 * 
	 * @return {@link ModelAndView}
	 * @author Marianna Memoli
	 */
	@RequestMapping(value = "/sceltaUfficioCapitolo.html", method= {RequestMethod.POST})
	public ModelAndView sceltaUfficioCapitolo_Post(HttpServletRequest request, 
			@ModelAttribute("sceltaCommand") AccertamentoSceltaUfficioCapitoloCommand sceltaUfficioCapitoloCommand, BindingResult result, RedirectAttributes redirectAttributes) {
		
		logger.debug("SCELTA UFFICIO-CAPITOLO :: ACCERTAMENTO :: Fields[accertamentoID: " + sceltaUfficioCapitoloCommand.getAccertamentoId() + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: START");
		
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
				logger.warn("SCELTA UFFICIO-CAPITOLO :: ACCERTAMENTO :: Fields[accertamentoID: " + sceltaUfficioCapitoloCommand.getAccertamentoId() + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: NON risulta OPERATORE per nessun tipo dovuto.");
				/* 
				 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
				 */
				return UtilitiesCtrl.getViewErrorMessage("mypivot.messages.error.nessunTipoDovutoAssegnato");
			}
			
			/**
			 * Get anagrafica accertamento 
			 */
			AccertamentoDto accertamentoDto = accertamentoService.findById(Long.parseLong(sceltaUfficioCapitoloCommand.getAccertamentoId()));
			
			/**
			 * Controllo che l'utente sia operatore per il tipo dovuto che si sta integrando con i pagamenti.
			 */
			if (!activeOperatoreEnteTd.contains(accertamentoDto.getEnteTipoDovuto().getCodTipo())) {
				logger.warn("SCELTA UFFICIO-CAPITOLO :: ACCERTAMENTO :: Fields[accertamentoID: " + sceltaUfficioCapitoloCommand.getAccertamentoId() + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: NON risulta OPERATORE per il tipo dovuto dell'accertamento.");
				/* 
				 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
				 */
				return UtilitiesCtrl.getViewErrorMessage("mypivot.messages.error.notAuthorized");
			}
			
			/**
			 * Controllo che l'accertamento sia in stato "INSERITO" per poter essere modificato.
			 */
			if (!accertamentoDto.getStato().getCodStato().equals(Constants.COD_TIPO_STATO_ACCERTAMENTO_INSERITO)) {
				logger.warn("SCELTA UFFICIO-CAPITOLO :: ACCERTAMENTO :: Fields[accertamentoID: " + sceltaUfficioCapitoloCommand.getAccertamentoId() + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: L'accertamento è in stato[" + accertamentoDto.getStato().getCodStato() + "] perciò non può essere modificato.");
				/* 
				 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
				 */
				return UtilitiesCtrl.getViewErrorMessage("mypivot.accertamenti.errore.notAuthorizedUpdate", new Object[]{accertamentoDto.getDeNomeAccertamento(), accertamentoDto.getStato().getCodStato()});
			}
			
			/*
			 * Di default valorizzo a false gli attibuti che individuano le liste come vuote.
			 */
			boolean emptyUffici = Boolean.FALSE, emptyCapitoli = Boolean.FALSE, emptyAccertamenti = Boolean.FALSE;
			
			try{
				/** 
				 * Valori possibili: 
				 *  1. "CH_SEL_UFFICIO" :  L'ufficio selezionato è cambiato. Seleziona l'anno di esercizio di default e Applica la ricerca capitoli.
				 *  2. "CH_SEL_ANNO" : 	   L'anno di esercizio selezionato è cambiato. Applica la ricerca capitoli.
				 *  3. "CH_SEL_CAPITOLO" : Il capitolo selezionato è cambiato. Applica la ricerca accertamenti.
				 *  4. "ADD_ITEM" : 	   Aggiunge in lista la riga dell'ufficio, capitolo ed accertamento contabile selezionati
				 *  5. "DEL_ITEM" : 	   Elimina dalla lista la riga dell'ufficio, capitolo ed accertamento contabile selezionati
				 *  6. "SAVE" : 		   Salvataggio della form.
				 *  7. "NO_OP" :		   Nessuna operazione.
				 */
				if(sceltaUfficioCapitoloCommand.getOperation().equals(OPERATION.SAVE)){
					try{
						/**
						 */
						if(sceltaUfficioCapitoloCommand.isMultiple()){
							/* */
							String codAccertamento = StringUtils.hasText(sceltaUfficioCapitoloCommand.getCodAccertamento()) ? 
																		 sceltaUfficioCapitoloCommand.getCodAccertamento() : 
																		 Constants.CODICE_NOT_AVAILABLE;
							/*
							 */
							AccertamentoUfficioCapitoloDto ufficiTO = new AccertamentoUfficioCapitoloDto();
							ufficiTO.setCodUfficio(sceltaUfficioCapitoloCommand.getCodUfficio());
							ufficiTO.setCodCapitolo(sceltaUfficioCapitoloCommand.getCodCapitolo());
							ufficiTO.setCodAccertamento(codAccertamento);
							
							/**
							 * Questo scenario prevede un unico capitolo/accertamento.
							 * Sono inserite in "mygov_accertamento dettaglio" tante righe quanti sono i pagamenti selezionati e gli importi 
							 * riportati in tabella corrispondono agli importi dei singoli pagamenti.
							 */
							accertamentoDttService.addPagamenti( 
										Long.parseLong(accertamentoDto.getId()),  						/* Identificativo dell'accertamento */
										accertamentoDto.getEnteTipoDovuto().getEnte().getCodIpaEnte(), 	/* Codice ipa dell'ente */
										sceltaUfficioCapitoloCommand.getFlussiExportDTO(), 				/* Lista RT da aggiungere all'accertamento */
										ufficiTO, 											  			/* Dati del capitolo/accertamento */
										SecurityContext.getUtente().getId());							/* Identificativo dell'utente in modifica */
						}else{
							if(sceltaUfficioCapitoloCommand.getUfficiTO() == null || sceltaUfficioCapitoloCommand.getUfficiTO().isEmpty())
								/* 
								 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
								 */
								return UtilitiesCtrl.getViewErrorMessage("mypivot.accertamenti.errore.internal");
							
							/**
							 * Questo scenario prevede l'acquisizione di un singolo pagamento permettendo quindi lo spacchettamento su più 
							 * capitoli/accertamenti.
							 * Sono inserite in "mygov_accertamento dettaglio" tante righe quanti sono gli importi inseriti dall'utente.
							 */
							accertamentoDttService.addPagamenti( 
										Long.parseLong(accertamentoDto.getId()), 						/* Identificativo dell'accertamento */
										accertamentoDto.getEnteTipoDovuto().getEnte().getCodIpaEnte(),  /* Codice ipa dell'ente */
										sceltaUfficioCapitoloCommand.getFlussiExportDTO().get(0),  		/* Lista RT da aggiungere all'accertamento */
										sceltaUfficioCapitoloCommand.getUfficiTO(), 					/* Lista capitoli/accertamenti */
										SecurityContext.getUtente().getId()); 							/* Identificativo dell'utente in modifica */
						}
						
						/*
						 * Aggiungo come attributo da passare alla view, il parametro per aprire la popup di operazione avvenuta con successo.
						 */
						redirectAttributes.getFlashAttributes().clear();
						redirectAttributes.addFlashAttribute("successRtAdd", Boolean.TRUE);
						
						/**
						 * Reindirizzo l'utente alla pagina di edit dei pagamenti
						 */
						return new ModelAndView(new RedirectView(request.getContextPath() + Constants.PATH_PAGE_ADD_PAG_ACCERTAMENTO + "?accertamentoID=" + sceltaUfficioCapitoloCommand.getAccertamentoId()));
						
					}catch(Exception e){
						logger.error("ADD PAGAMENTI :: ACCERTAMENTO :: POST :: Fields[accertamentoID: " + sceltaUfficioCapitoloCommand.getAccertamentoId() + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: ERRORE ", e);
						/* 
						 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
						 */
						return UtilitiesCtrl.getViewErrorMessage("mypivot.accertamenti.errore.addPagamenti.nuovo");
					}
				}else{
					List<String> codTipiDovuto = new ArrayList<String>();	codTipiDovuto.add(accertamentoDto.getEnteTipoDovuto().getCodTipo());
 					/**
					 * Recupero l'elenco (in distinct) degli uffici attivi dati per l'ente e il codice tipo dovuto dell'accertamento. 
					 */
					List<AnagraficaUfficioCapitoloAccertamentoTO> ufficiList = ufficiService.findDistinctUfficiByFilter(accertamentoDto.getEnteTipoDovuto().getEnte().getId(), codTipiDovuto, Boolean.TRUE);
					/* Init null */
					List<AnagraficaUfficioCapitoloAccertamentoTO> capitoliList = null;
					List<AnagraficaUfficioCapitoloAccertamentoTO> accertamentiList = null;
					
					/**
					 * Controllo che l'elenco uffici non sia vuoto, perchè in tal caso posso interrompere la procedura
					 * considerato che l'ufficio è un parametro obbligatorio.
					 */
					if(!ufficiList.isEmpty()){
						/**
						 * Se il codice ufficio e l'anno di esercizio sono valorizzati recupero l'elenco (in distinct) dei capitoli. 
						 */
						if(StringUtils.hasText(sceltaUfficioCapitoloCommand.getCodUfficio()) &&  StringUtils.hasText(sceltaUfficioCapitoloCommand.getAnnoEsercizio()))
							/*
							 */
							capitoliList = ufficiService.findDistinctCapitoliByFilter(
																					accertamentoDto.getEnteTipoDovuto().getEnte().getId(), 
																					accertamentoDto.getEnteTipoDovuto().getCodTipo(), 
																					sceltaUfficioCapitoloCommand.getCodUfficio(), 
																					sceltaUfficioCapitoloCommand.getAnnoEsercizio(), 
																					Boolean.TRUE);
						/**
						 * Se il codice ufficio, l'anno di esercizio e il codice capitolo sono valorizzati recupero l'elenco (in distinct) degli
						 * accertamenti. 
						 */
						if(StringUtils.hasText(sceltaUfficioCapitoloCommand.getCodUfficio()) && StringUtils.hasText(sceltaUfficioCapitoloCommand.getAnnoEsercizio()) && StringUtils.hasText(sceltaUfficioCapitoloCommand.getCodCapitolo()))
							/*
							 */
							accertamentiList = ufficiService.findDistinctAccertamentiByFilter(
																						accertamentoDto.getEnteTipoDovuto().getEnte().getId(), 
																						accertamentoDto.getEnteTipoDovuto().getCodTipo(), 
																						sceltaUfficioCapitoloCommand.getCodUfficio(), 
																						sceltaUfficioCapitoloCommand.getAnnoEsercizio(), 
																						sceltaUfficioCapitoloCommand.getCodCapitolo(), 
																						Boolean.TRUE);
						/**
						 * Check Operation
						 */
						switch (sceltaUfficioCapitoloCommand.getOperation()) {
							case CH_SEL_UFFICIO: {
								/* Svuoto la selezione al codice capitolo */
								sceltaUfficioCapitoloCommand.setCodCapitolo("");
								/* Svuoto la selezione al codice accertamento */
								sceltaUfficioCapitoloCommand.setCodAccertamento("");
								
								if(StringUtils.hasText(sceltaUfficioCapitoloCommand.getCodUfficio())){
									/* Di default seleziono come anno di esercizio il corrente */
									sceltaUfficioCapitoloCommand.setAnnoEsercizio(Constants.YYYY.format(new Date()));
									
									/*
									 */
									capitoliList = ufficiService.findDistinctCapitoliByFilter(
																							accertamentoDto.getEnteTipoDovuto().getEnte().getId(), 
																							accertamentoDto.getEnteTipoDovuto().getCodTipo(), 
																							sceltaUfficioCapitoloCommand.getCodUfficio(), 
																							sceltaUfficioCapitoloCommand.getAnnoEsercizio(), 
																							Boolean.TRUE);
									
									/* L'elenco capitoli ritornato è vuto. */
									if(capitoliList == null || capitoliList.isEmpty()) emptyCapitoli = Boolean.TRUE;	
								}else
									/* Svuoto la selezione anche dalla select dell'anno di esercizio */
									sceltaUfficioCapitoloCommand.setAnnoEsercizio("");
								
								break;
							}
							case CH_SEL_ANNO: {
								/* Svuoto la selezione al codice accertamento */
								sceltaUfficioCapitoloCommand.setCodCapitolo("");
								/* Svuoto la selezione al codice accertamento */
								sceltaUfficioCapitoloCommand.setCodAccertamento("");
								
								if(StringUtils.hasText(sceltaUfficioCapitoloCommand.getAnnoEsercizio())){
									/* L'elenco capitoli ritornato è vuto. */
									if(capitoliList == null || capitoliList.isEmpty()) emptyCapitoli = Boolean.TRUE;
								}
								
								break;
							}
							case CH_SEL_CAPITOLO: {
								/* Svuoto la selezione al codice accertamento */
								sceltaUfficioCapitoloCommand.setCodAccertamento("");
								
								if(StringUtils.hasText(sceltaUfficioCapitoloCommand.getCodCapitolo())){
									/* L'elenco capitoli ritornato è vuto. */
									if(capitoliList == null || capitoliList.isEmpty()) emptyCapitoli = Boolean.TRUE;
									/* L'elenco accertamenti ritornato è vuto. */
									if(accertamentiList == null || accertamentiList.isEmpty()) emptyAccertamenti = Boolean.TRUE;	
								}
								
								break;
							}
							case ADD_ITEM: {
								
								/**
								 * Validazione form
								 */
								validator.validate(sceltaUfficioCapitoloCommand, result);

								/**
								 * Check validation errors
								 */
								if (!result.hasErrors()) {
									
									/* */
									Long enteId = accertamentoDto.getEnteTipoDovuto().getEnte().getId();
									/* */
									String codUfficio = sceltaUfficioCapitoloCommand.getCodUfficio();
									/* */
									String deUfficio = ufficiService.getDeUfficio(enteId, codUfficio);
									/* */
									String codCapitolo = sceltaUfficioCapitoloCommand.getCodCapitolo();
									/* */
									String deCapitolo = ufficiService.getDeCapitolo(enteId, codUfficio, codCapitolo);
									/* */
									String annoEsercizio = sceltaUfficioCapitoloCommand.getAnnoEsercizio();
									/* */
									String codAccertamento = StringUtils.hasText(sceltaUfficioCapitoloCommand.getCodAccertamento()) ? 
																				 sceltaUfficioCapitoloCommand.getCodAccertamento() : 
																				 Constants.CODICE_NOT_AVAILABLE;
									/* */
									String deAccertamento = StringUtils.hasText(sceltaUfficioCapitoloCommand.getCodAccertamento()) ? 
																				ufficiService.getDeAccertamento(enteId, codUfficio, codCapitolo, sceltaUfficioCapitoloCommand.getCodAccertamento()) :
																				Constants.CODICE_NOT_AVAILABLE;
									/* Escape double quotes */											
									deAccertamento = deAccertamento.replaceAll("\"","&#34;");
																				
									/* */
									String numImporto = sceltaUfficioCapitoloCommand.getImporto();
									
									/**
									 */
									AccertamentoUfficioCapitoloDto dto = new AccertamentoUfficioCapitoloDto();
									dto.setCodUfficio(codUfficio); 			 /* Codice ufficio */
								  	dto.setDeUfficio(deUfficio);	 		 /* Descrizione testuale dell'ufficio */
									dto.setCodCapitolo(codCapitolo);		 /* Codice del capitolo */
								    dto.setDeCapitolo(deCapitolo);			 /* Descrizione testuale del capitolo */
								    dto.setDeAnnoEsercizio(annoEsercizio);	 /* Anno esercizio, attributo riferito al capitolo */
									dto.setCodAccertamento(codAccertamento); /* Codice accertamento */
									dto.setDeAccertamento(deAccertamento);	 /* Descrizione testuale dell'accertamento */
									dto.setNumImporto(numImporto); 			 /* Quota parte dell'importo associata al capitolo ed accertamento contabile selezionati */

									/**
									 * Add to list
									 */
									sceltaUfficioCapitoloCommand.getUfficiTO().add(dto);
									/**
									 * Sommo al totale importo capitoli, l'importo del capitolo appena creato
									 */
									sceltaUfficioCapitoloCommand.setTotImportoCapitoli(
															sceltaUfficioCapitoloCommand.getTotImportoCapitoli().add(new BigDecimal(numImporto)));
									/**
									 * Fomat as string dell'importo totale a capitoli
									 */
									sceltaUfficioCapitoloCommand.setFormatTotImportoCapitoli(
													"€ " + Constants.NUMBER_FORMAT_US.format(sceltaUfficioCapitoloCommand.getTotImportoCapitoli()));
									
									/**
									 * Reset dei filtri di selezione
									 */
									sceltaUfficioCapitoloCommand.setCodUfficio("");
									sceltaUfficioCapitoloCommand.setCodCapitolo("");
									sceltaUfficioCapitoloCommand.setCodAccertamento("");
									sceltaUfficioCapitoloCommand.setAnnoEsercizio("");
									
									/**
									 * Calcolo l'importo che rimane da assegnare rispetto all'importo totale dei dovuti
									 */
									BigDecimal diff = sceltaUfficioCapitoloCommand.getTotImportoDovuti()
																				.subtract(sceltaUfficioCapitoloCommand.getTotImportoCapitoli());
									
									/**
									 * Uso l'importo appena calcolato per inizializzare:
									 *  - l'input text dell'importo per la prossima aggiunta capitolo
									 *  - la label che riassume l'importo che rimane da assegnare
									 */
									sceltaUfficioCapitoloCommand.setImporto(diff.toPlainString());
									sceltaUfficioCapitoloCommand.setFormatTotImportoMancante("€ " + Constants.NUMBER_FORMAT_US.format(diff));
									
								}// close if no errors
								
								/* Verifico di nuovo se L'elenco capitoli ritornato è vuto. */
								if(StringUtils.hasText(sceltaUfficioCapitoloCommand.getCodUfficio()) && (capitoliList == null || capitoliList.isEmpty())) emptyCapitoli = Boolean.TRUE;
							
								/*  Verifico di nuovo se L'elenco accertamenti ritornato è vuto. */
								if(StringUtils.hasText(sceltaUfficioCapitoloCommand.getCodCapitolo()) && (accertamentiList == null || accertamentiList.isEmpty())) emptyAccertamenti = Boolean.TRUE;	
								
								break;
							}
							case DEL_ITEM: {
								/**
								 * Controllo che l'index dell'item da riuovere sia non nullo e un elemento effettivo dell'array  
								 */
								if(StringUtils.hasText(sceltaUfficioCapitoloCommand.getRmIndex())
										&& Integer.parseInt(sceltaUfficioCapitoloCommand.getRmIndex()) < sceltaUfficioCapitoloCommand.getUfficiTO().size()){
								
									/** 
									 * Get index to remove
									 */
									int index = Integer.parseInt(sceltaUfficioCapitoloCommand.getRmIndex());
								
									/**
									 * Sottraggo al totale importo capitoli, l'importo del capitolo da rimuovere
									 */
									sceltaUfficioCapitoloCommand.setTotImportoCapitoli(sceltaUfficioCapitoloCommand.getTotImportoCapitoli()
													.subtract(new BigDecimal(sceltaUfficioCapitoloCommand.getUfficiTO().get(index).getNumImporto())));
									/**
									 * Fomat as string dell'importo totale a capitoli
									 */
									sceltaUfficioCapitoloCommand.setFormatTotImportoCapitoli(
													"€ " + Constants.NUMBER_FORMAT_US.format(sceltaUfficioCapitoloCommand.getTotImportoCapitoli()));
									
									/**
									 * Rimuovo dalla lista il capitolo all'indice passato
									 */
									sceltaUfficioCapitoloCommand.getUfficiTO().remove(index);
									
									/**
									 * Calcolo l'importo che rimane da assegnare rispetto all'importo totale dei dovuti
									 */
									BigDecimal diff = sceltaUfficioCapitoloCommand.getTotImportoDovuti()
																				.subtract(sceltaUfficioCapitoloCommand.getTotImportoCapitoli());
									
									/**
									 * Uso l'importo appena calcolato per inizializzare:
									 *  - l'input text dell'importo per la prossima aggiunta capitolo
									 *  - la label che riassume l'importo che rimane da assegnare
									 */
									sceltaUfficioCapitoloCommand.setImporto(diff.toPlainString());
									sceltaUfficioCapitoloCommand.setFormatTotImportoMancante("€ " + Constants.NUMBER_FORMAT_US.format(diff));
									
									/*  Verifico di nuovo se L'elenco capitoli ritornato è vuto. */
									if(StringUtils.hasText(sceltaUfficioCapitoloCommand.getCodUfficio()) && (capitoliList == null || capitoliList.isEmpty())) emptyCapitoli = Boolean.TRUE;
									
									/* L'elenco accertamenti ritornato è vuto. */
									if(StringUtils.hasText(sceltaUfficioCapitoloCommand.getCodCapitolo()) && (accertamentiList == null || accertamentiList.isEmpty())) emptyAccertamenti = Boolean.TRUE;	
									
								}// close if
								
								break;
							}
							case NO_OP: break; 	default : break;
						}// close switch
					}else{
						/**
						 * L'elenco uffici ritornato è vuoto. 
						 */
						emptyUffici = Boolean.TRUE;
					}
					
					// prima di ritornare la view reset value operation
					sceltaUfficioCapitoloCommand.setOperation(OPERATION.NO_OP);
					
					/**
					 * Set param da ritornare alla view
					 */
					mav.addObject("doSubmit", Boolean.FALSE);
					
					mav.addObject("emptyUffici", emptyUffici);
					mav.addObject("emptyCapitoli", emptyCapitoli);	
					mav.addObject("emptyAccertamenti", emptyAccertamenti);	
					
					mav.addObject("ufficiList", ufficiList);
					mav.addObject("capitoliList", capitoliList);
					mav.addObject("accertamentiList", accertamentiList);
					
					mav.addObject("accertamentoDto", accertamentoDto);
					mav.addObject("sceltaCommand", sceltaUfficioCapitoloCommand); /* Model attribute */
					
					mav.setViewName("sceltaUfficioCapitoloAccertamento"); /* Nome della view */
				}// close else not save operation
				
				/**
				 */
				if(sceltaUfficioCapitoloCommand.isMultiple()){
					/**
					 * Per lo scenario molteplici pagamenti, il pulsante per il salvataggio delle modifiche è abilitato solo se i campi obbligatori
					 * del form sono valorizzati.
					 */
					if(StringUtils.hasText(sceltaUfficioCapitoloCommand.getCodUfficio()) && 
																		StringUtils.hasText(sceltaUfficioCapitoloCommand.getAnnoEsercizio()) && 
																		StringUtils.hasText(sceltaUfficioCapitoloCommand.getCodCapitolo()))
						sceltaUfficioCapitoloCommand.setEnableConfirmButton(Boolean.TRUE);
					else
						sceltaUfficioCapitoloCommand.setEnableConfirmButton(Boolean.FALSE);
				}else
					/**
					 * Per lo scenario singolo agamento, il pulsante per il salvataggio delle modifiche è abilitato solo quando l'importo totale dei 
					 * dovuti e l'importo totale a capitoli coincide.
					 */
					if(sceltaUfficioCapitoloCommand.getTotImportoDovuti().compareTo(sceltaUfficioCapitoloCommand.getTotImportoCapitoli()) == 0){
						sceltaUfficioCapitoloCommand.setEnableConfirmButton(Boolean.TRUE);
						sceltaUfficioCapitoloCommand.setEnableFormAdd(Boolean.FALSE);
					}else{
						sceltaUfficioCapitoloCommand.setEnableConfirmButton(Boolean.FALSE);
						sceltaUfficioCapitoloCommand.setEnableFormAdd(Boolean.TRUE);
					}
			}catch(Exception e){
				logger.error("SCELTA UFFICIO-CAPITOLO :: ACCERTAMENTO :: Fields[accertamentoID: " + sceltaUfficioCapitoloCommand.getAccertamentoId() + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: ERRORE ", e);
				/* 
				 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
				 */
				return UtilitiesCtrl.getViewErrorMessage("mypivot.accertamenti.errore.internal", null);
			}
		}catch(Exception e){
			logger.error("SCELTA UFFICIO-CAPITOLO :: ACCERTAMENTO :: Fields[accertamentoID: " + sceltaUfficioCapitoloCommand.getAccertamentoId() + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: ERRORE ", e);
			/* 
			 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
			 */
			return UtilitiesCtrl.getViewErrorMessage("mypivot.accertamenti.errore.internal");
		}
		
		logger.debug("SCELTA UFFICIO-CAPITOLO :: ACCERTAMENTO :: Fields[accertamentoID: " + sceltaUfficioCapitoloCommand.getAccertamentoId() + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: ENTE");

		return mav;
	}
}