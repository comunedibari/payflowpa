package it.regioneveneto.mygov.payment.mypivot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import it.regioneveneto.mygov.payment.mypivot.controller.command.AccertamentoVisualizzaPagamentiCommand;
import it.regioneveneto.mygov.payment.mypivot.controller.command.AccertamentoVisualizzaPagamentiCommand.OPERATION;
import it.regioneveneto.mygov.payment.mypivot.controller.utils.AccertamentoUtils;
import it.regioneveneto.mygov.payment.mypivot.controller.utils.UtilitiesCtrl;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.AccertamentoDettaglioDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.AccertamentoDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.AccertamentoFlussoExportDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.AccertamentoUfficioCapitoloDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.DynamicMessageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.MessagesDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.PageDto;
import it.regioneveneto.mygov.payment.mypivot.service.AccertamentoDettaglioService;
import it.regioneveneto.mygov.payment.mypivot.service.AccertamentoService;
import it.regioneveneto.mygov.payment.mypivot.service.OperatoreEnteTipoDovutoService;
import it.regioneveneto.mygov.payment.mypivot.utils.Constants;
import it.regioneveneto.mygov.payment.mypivot.utils.SecurityContext;
import it.regioneveneto.mygov.payment.mypivot.utils.SessionVariables;

/**
 * Gestione della view del dettaglio Accertamento, consultazione in sola lettura.
 * 
 * @author Marianna Memoli
 */
@Controller
@RequestMapping("/protected/accertamenti")
public class DettaglioAccertamentoController {

	private static Log logger = LogFactory.getLog(DettaglioAccertamentoController.class);

	@Autowired
	private OperatoreEnteTipoDovutoService operatoreEnteTipoDovutoService;
	
	@Autowired
	private AccertamentoService accertamentoService;
	
	@Autowired
	private AccertamentoDettaglioService accertamentoDettaglioService;
	
	public DettaglioAccertamentoController() {
		super();
	}
	 
	/**
	 * Prepara e restituisce la view che permette di visualizzare il dettaglio completo dell'accertamento (GET - IN SOLA LETTURA). 
	 * -----------------------------------------------------------------------------------------------------------------------------------
	 * Prima di erogare la pagina effettuo dei controlli per determinare se sussistono le condizioni tali perchè l'utente possa accedere
	 * alla funzionalità.
	 * 
	 * I controlli eseguiti sono i seguenti:
	 *   1. L'utente deve aver selezionato l'ente beneficiario, se mancante verrà rediretto alla pagina di scelta dell'ente.
	 *   2. L'utente deve avere ruoli attivi, se mancante verrà rediretto alla pagina di scelta dell'ente.
	 *   3. L'utente deve avere uno dei ruoli utili ad accedere alla funzionalità di "Accertamento" (ROLE_ADMIN e/o ROLE_ACC).
	 *   4. L'utente deve essere operatore (attivo) del tipo dovuto(..dell'ente) per cui è stato creato l'accertamento, se non lo è mostro un 
	 *   	messaggio di errore (Es: Utente non autorizzato)
	 * 
	 * A seguito dei controlli, recupero le informazioni necessarie alla view che sono: 
	 * 
	 *   "accertamentoDto": 			 Oggetto che presenta le informazioni caratterizzanti l'accertamento
	 *   "isReadonly": 					 Valorizzato a true, fa in modo di configurare la view di modo che sia IN SOLA LETTURA.
	 *   "pagamentiAccertamentoCommand": Model attribute che mappa i campi della form
	 *   
	 * @param {@link HttpServletRequest} request
	 * @param {@link Long} 			 	 accertamentoID,  Identificativo dell'accertamento
	 * @param {@link String} 			 page, 			  Pagina da visualizzare
	 * @param {@link String} 			 pageSize,  	  Numero di elementi per pagina
	 * 
	 * @return {@link ModelAndView}
	 * 
	 * @author Marianna Memoli
	 */
	@RequestMapping(value = { "/dettaglio.html" }, method = RequestMethod.GET)
	public ModelAndView dettaglio_Get(HttpServletRequest request, Model model,
			@RequestParam(value = "accertamentoID") String accertamentoID, @RequestParam(required = false) String pg, @RequestParam(required = false) String pgSize) {
		
		logger.debug("DETTAGLIO :: ACCERTAMENTO :: GET :: Fields[accertamentoID: " + accertamentoID + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: START");
		
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
			AccertamentoVisualizzaPagamentiCommand pagamentiAccertamentoCommand = new AccertamentoVisualizzaPagamentiCommand();
			/**
			 * Indentificativo accertamento
			 */
			pagamentiAccertamentoCommand.setAccertamentoId(accertamentoID);
			/**
			 * Prevalorizzo il bean di ricerca della form con gli ultimi valori dei filtri sottomessi e salvati in sessione.
			 */
			AccertamentoUtils.setSessionFilterPagamentiToBean(request, SecurityContext.getEnte().getCodIpaEnte(), null, null, null, null, null, null, null, null, null, null, pg, pgSize, SessionVariables.ACTION_ACCERTAMENTO_RICERCA_PAGAMENTI, pagamentiAccertamentoCommand);
		
			/**
			 * Avvio la ricerca
			 */
			mav = dettaglio(request, pagamentiAccertamentoCommand);
			
			/** REDIRECT INFO: ================================================================================================================== */
			/*
			 * L'istruzione "model.containsAttribute('notAuthorizedClosedEmptyAcc')" mi serve per verificare se è stato passato il parametro 
			 * "notAuthorizedClosedEmptyAcc" da una redirect view cosi da mostrare il messaggio di errore appropriato nella VIEW che sarà caricata.
			 * 
			 * In particolare il parametro "notAuthorizedClosedEmptyAcc" è gestito per quando l'utente ha tentato di chiudere un'accertamento 
			 * per il quale non sono stati aggiunti pagamenti.
			 *    
			 * Il valore atteso del parametro è un boolean.
			 */    
			/** ======================================================================================================================== */
			
			/*
			 * Leggo il parametro dagli attributi.
			 */
			Boolean notAuthorizedClosedEmptyAcc = (model != null && model.containsAttribute("notAuthorizedClosedEmptyAcc")) ? (Boolean) model.asMap().get("notAuthorizedClosedEmptyAcc") : false;
				
			/* Valorizzando l'attributo, lato view so di dover mostrare dei messaggi in popup o meno */
			mav.addObject("notAuthorizedClosedEmptyAcc", notAuthorizedClosedEmptyAcc);
			
		}catch(Exception e){
			logger.error("DETTAGLIO :: ACCERTAMENTO :: GET :: Fields[accertamentoID: " + accertamentoID + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: ERRORE ", e);
			/* 
			 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
			 */
			return UtilitiesCtrl.getViewErrorMessage("mypivot.accertamenti.errore.internal");
		}
		
		logger.debug("DETTAGLIO :: ACCERTAMENTO :: GET :: Fields[accertamentoID: " + accertamentoID + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: END");

		return mav;
	}
	
	/**
	 * Restituisce la view di dettaglio degli accertamenti in POST, quando l'utente da view ha avviato la ricerca pagamenti (RT). 
	 * -----------------------------------------------------------------------------------------------------------------------------------
	 * Prima di erogare la pagina effettuo dei controlli per determinare se sussistono le condizioni tali perchè l'utente possa accedere
	 * alla funzionalità.
	 * 
	 * I controlli eseguiti sono i seguenti:
	 *   1. L'utente deve aver selezionato l'ente beneficiario, se mancante verrà rediretto alla pagina di scelta dell'ente.
	 *   2. L'utente deve avere ruoli attivi, se mancante verrà rediretto alla pagina di scelta dell'ente.
	 *   3. L'utente deve avere uno dei ruoli utili ad accedere alla funzionalità di "Accertamento" (ROLE_ADMIN e/o ROLE_ACC).
	 *   4. L'utente deve essere operatore (attivo) del tipo dovuto(..dell'ente) per cui è stato creato l'accertamento, se non lo è mostro un 
	 *   	messaggio di errore (Es: Utente non autorizzato)
	 * 
	 * @param {@link HttpServletRequest} request
	 * @param {@link AccertamentoVisualizzaPagamentiCommand} pagamentiAccertamentoCommand, Bean della form di ricerca
	 * 
	 * @return {@link ModelAndView}
	 */
	@RequestMapping(value = { "/dettaglio.html" }, method = RequestMethod.POST)
	public ModelAndView dettaglio_Post(HttpServletRequest request, @ModelAttribute("pagamentiAccertamentoCommand") AccertamentoVisualizzaPagamentiCommand pagamentiAccertamentoCommand) {
		
		logger.debug("DETTAGLIO :: ACCERTAMENTO :: POST :: Fields[accertamentoID: " + pagamentiAccertamentoCommand.getAccertamentoId() + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: START");
		
		/* */
		ModelAndView mav = new ModelAndView();
		try{
			/**
			 * La funzione verifica che l'utente autenticato abbia selezionato l'ente.
			 */
			if(!UtilitiesCtrl.hasSecurityEnte()) return new ModelAndView(new RedirectView(request.getContextPath() + Constants.PATH_PAGE_SCELTA_ENTE));
		
			/*
			 * Init variabili da sottomettere alla view se richiesto il dettaglio capitoli.
			 */
			Boolean showCapitoli = false;
			Map<String, Object> detail_RT = new HashMap<String, Object>();
			
			/**
			 * Prima di avviare la ricerca, controllo se è stato richiesto il dettaglio dei capitoli associati alla RT.
			 * Se si, costruisco il pacchetto dati da inviare alla view.
			 */
			if(pagamentiAccertamentoCommand.getOperation() != null && pagamentiAccertamentoCommand.getOperation().equals(OPERATION.DETAIL)){
				
				AccertamentoFlussoExportDto selected = null;
				
				/** Ciclo la lista risottomessa dal submit per individuare la RT selezionata */
				for(AccertamentoDettaglioDto item : pagamentiAccertamentoCommand.getResultList()) {
					/* */
					if(item.getSelected() != null && item.getSelected()) {
						/* */
						selected = item.getFlussoExportDTO();
						/* */ 
						logger.info("Dettaglio Capitoli associati alla RT :: Fields[operation: " + OPERATION.DETAIL.name() + ", RT:" + selected.toString() + "]");
						break;
					}
				}
				
				/** Se ho individuato la RT selezionata */
				if(selected != null ){
					try{
						/*
						 * Recupera l'elenco dei capitoli associati alla RT.
						 */
						List<AccertamentoUfficioCapitoloDto> capitoli = accertamentoDettaglioService.findListCapitoliByRT(
																				Long.parseLong(pagamentiAccertamentoCommand.getAccertamentoId()), 
																				SecurityContext.getEnte().getCodIpaEnte(), 
																				SecurityContext.getEnte().getId(), 
																				selected.getCodTipoDovuto(), 
																				selected.getCodiceIud(), 
																				selected.getCodiceIuv(),
																				null);
						/*
						 * Controllo di aver recuperato la lista dei capitoli associati, altrimenti rimando alla pagina di errore.
						 */
						if(capitoli != null && !capitoli.isEmpty()){
							/* */
							showCapitoli = true;
							/**
							 * Build object return view 
							 */
							detail_RT.put("flussoRT", selected); 	 	/* Anagrafica RT */
							detail_RT.put("capitoli", capitoli);		/* Lista Capitoli */
							/*
							 * Determina la modalità in cui sarà mostrato il dettaglio capitoli.
							 * Se l'intero importo della RT è attribuito ad un singolo ufficio/capitolo/accertamento lo mostriamo inline.
							 * Altrimenti, tramite un pulsante di dettaglio apriamo un popup che riporta la lista delle tuple in una tabellina.
							 */
							detail_RT.put("openAs", capitoli.size() > 1 ? "POPUP" : "INLINE"); 
						}else{
							logger.error("Dettaglio Capitoli associati alla RT :: Fields[operation: " + OPERATION.DETAIL.name() + ", RT:" + selected.toString() + ", accertamentoID: " + pagamentiAccertamentoCommand.getAccertamentoId() + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: ERRORE :: La lista capitoli associata alla RT e' vuota.");
							/* 
							 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
							 */
							return UtilitiesCtrl.getViewErrorMessage("mypivot.accertamenti.errore.capitoliAssociati");
						}
					}catch(Exception e){
						logger.error("Dettaglio Capitoli associati alla RT :: ACCERTAMENTO :: POST :: Fields[operation: " + OPERATION.DETAIL.name() + ", RT:" + selected.toString() + ", accertamentoID: " + pagamentiAccertamentoCommand.getAccertamentoId() + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: ERRORE ", e);
						/* 
						 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
						 */
						return UtilitiesCtrl.getViewErrorMessage("mypivot.accertamenti.errore.capitoliAssociati");
					}
				}// close if 
			}// close if operation detail
			else
				/**
				 * E' stata avviata una nuova ricerca, con nuovi filtri, reimposto la pagina di partenza alla prima.
				 */
				pagamentiAccertamentoCommand.setPage(1);
			
			/*
			 */
			mav = dettaglio(request, pagamentiAccertamentoCommand);
			
			/**
			 * Add parameter to return view
			 */
			mav.addObject("showCapitoli", showCapitoli);
			mav.addObject("dtt_Capitoli", detail_RT);
			
			
		   // Salvo i nuovi filtri di ricerca impostati in sessione.
			if(SecurityContext.getEnte() != null){
				
				Map<String, Object> filtersMap = new HashMap<String, Object>();
				/* */
				filtersMap.put(SessionVariables.ACCERTAMENTO_PAGE_SIZE, 		pagamentiAccertamentoCommand.getPageSize());
				filtersMap.put(SessionVariables.ACCERTAMENTO_PAGE, 				pagamentiAccertamentoCommand.getPage());
				/* */
				filtersMap.put(SessionVariables.ACCERTAMENTO_CODICE_TIPO_DOVUTO, pagamentiAccertamentoCommand.getCod_tipo_dovuto());
				/* */
				filtersMap.put(SessionVariables.ACCERTAMENTO_COD_IDENT_UNIVOCO_PAGATORE, pagamentiAccertamentoCommand.getCodice_identificativo_univoco_pagatore());
				/* */
				filtersMap.put(SessionVariables.ACCERTAMENTO_CODICE_IUD, 	pagamentiAccertamentoCommand.getCodice_iud());
				filtersMap.put(SessionVariables.ACCERTAMENTO_CODICE_IUV,	pagamentiAccertamentoCommand.getCodice_iuv());
				/* */
				filtersMap.put(SessionVariables.ACCERTAMENTO_CHECK_DATA_AGG, pagamentiAccertamentoCommand.getData_ultimo_aggiornamento_check());
				filtersMap.put(SessionVariables.ACCERTAMENTO_DATA_AGG_DAL,    pagamentiAccertamentoCommand.getData_ultimo_aggiornamento_da());
				filtersMap.put(SessionVariables.ACCERTAMENTO_DATA_AGG_AL,    pagamentiAccertamentoCommand.getData_ultimo_aggiornamento_a());
				/* */
				filtersMap.put(SessionVariables.ACCERTAMENTO_CHECK_DATA_ESITO_SINGOLO_PAG, pagamentiAccertamentoCommand.getData_esito_singolo_pagamento_check());
				filtersMap.put(SessionVariables.ACCERTAMENTO_DATA_ESITO_SINGOLO_PAG_DAL,    pagamentiAccertamentoCommand.getData_esito_singolo_pagamento_da());
				filtersMap.put(SessionVariables.ACCERTAMENTO_DATA_ESITO_SINGOLO_PAG_AL,    pagamentiAccertamentoCommand.getData_esito_singolo_pagamento_a());
					 
				SecurityContext.setEnteViewFilters(SecurityContext.getEnte().getCodIpaEnte(), SessionVariables.ACTION_ACCERTAMENTO_RICERCA_PAGAMENTI, filtersMap);
			}
		   // =======================================================
					
			logger.debug("DETTAGLIO :: ACCERTAMENTO :: POST :: Fields[accertamentoID: " + pagamentiAccertamentoCommand.getAccertamentoId() + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: END");

			/**
			 */
			return mav;
			
		}catch(Exception e){
			logger.error("DETTAGLIO :: ACCERTAMENTO :: POST :: Fields[accertamentoID: " + pagamentiAccertamentoCommand.getAccertamentoId() + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: ERRORE ", e);
			/* 
			 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
			 */
			return UtilitiesCtrl.getViewErrorMessage("mypivot.accertamenti.errore.internal");
		}
	}
	
	/**
	 * Funzione che gestisce le fasi comuni della view che permette di visualizzare il dettaglio dell'accertamento 
	 * in GET e POST.
	 * 
	 * @param {@link HttpServletRequest} 					 request
	 * @param {@link AccertamentoVisualizzaPagamentiCommand} pagamentiAccertamentoCommand
	 * 
	 * @return {@link ModelAndView}
	 */
	public ModelAndView dettaglio(HttpServletRequest request, AccertamentoVisualizzaPagamentiCommand pagamentiAccertamentoCommand) {
		
		logger.debug("DETTAGLIO :: ACCERTAMENTO :: Fields[accertamentoID: " + pagamentiAccertamentoCommand.getAccertamentoId() + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: START");
		
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
				logger.warn("DETTAGLIO :: ACCERTAMENTO :: Fields[accertamentoID: " + pagamentiAccertamentoCommand.getAccertamentoId() + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: NON risulta OPERATORE per nessun tipo dovuto.");
				/* 
				 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
				 */
				return UtilitiesCtrl.getViewErrorMessage("mypivot.messages.error.nessunTipoDovutoAssegnato");
			}
			
			/**
			 * Get anagrafica accertamento 
			 */
			AccertamentoDto accertamentoDto = accertamentoService.findById(Long.parseLong(pagamentiAccertamentoCommand.getAccertamentoId()));
			
			/**
			 * Controllo che l'utente sia operatore per il tipo dovuto dell'accertamento.
			 */
			if (!activeOperatoreEnteTd.contains(accertamentoDto.getEnteTipoDovuto().getCodTipo())) {
				logger.warn("DETTAGLIO :: ACCERTAMENTO :: Fields[accertamentoID: " + pagamentiAccertamentoCommand.getAccertamentoId() + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: NON risulta OPERATORE per il tipo dovuto dell'accertamento.");
				/* 
				 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
				 */
				return UtilitiesCtrl.getViewErrorMessage("mypivot.messages.error.notAuthorized");
			}
			
			/**
			 * Inizializzo il bean per la ricerca
			 */
			pagamentiAccertamentoCommand = AccertamentoUtils.initializeFilterPagamenti(pagamentiAccertamentoCommand, accertamentoDto.getEnteTipoDovuto().getCodTipo(), accertamentoDto.getId());
			
			/**
			 * Instance return bean 
			 */
			PageDto<AccertamentoDettaglioDto> result = new PageDto<AccertamentoDettaglioDto>();
			
			try{
				Boolean checkEsito = pagamentiAccertamentoCommand.getData_esito_singolo_pagamento_check() != null ? pagamentiAccertamentoCommand.getData_esito_singolo_pagamento_check() : false;
				Boolean checkAgg = pagamentiAccertamentoCommand.getData_ultimo_aggiornamento_check() != null ? pagamentiAccertamentoCommand.getData_ultimo_aggiornamento_check() : false;
				
				/**
				 * Eseguo la query di ricerca
				 */
				result = accertamentoDettaglioService.findPagamentiInAccertamentoByFilter(
								 Long.parseLong(accertamentoDto.getId()), SecurityContext.getEnte().getId(), 
								 pagamentiAccertamentoCommand.getCod_tipo_dovuto(), 
								 pagamentiAccertamentoCommand.getCodice_iud(), pagamentiAccertamentoCommand.getCodice_iuv(), 
								 pagamentiAccertamentoCommand.getCodice_identificativo_univoco_pagatore(), 
								 checkEsito ? pagamentiAccertamentoCommand.getData_esito_singolo_pagamento_da() : null, 
								 checkEsito ? pagamentiAccertamentoCommand.getData_esito_singolo_pagamento_a() : null,
								 checkAgg ? pagamentiAccertamentoCommand.getData_ultimo_aggiornamento_da() : null, 
								 checkAgg ? pagamentiAccertamentoCommand.getData_ultimo_aggiornamento_a() : null, 
								 true, pagamentiAccertamentoCommand.getPage(), pagamentiAccertamentoCommand.getPageSize());
				
			}catch(Exception e){
				logger.error("DETTAGLIO :: ACCERTAMENTO :: Fields[accertamentoID: " + pagamentiAccertamentoCommand.getAccertamentoId() + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "]", e);
				/* */
				messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.accertamenti.errore.pagamenti.ricerca"));
				/* */
				mav.addObject("messagesDto", messagesDto);
			}
			
			// prima di ritornare la view reset value operation
			pagamentiAccertamentoCommand.setOperation(OPERATION.RC);
						
			/**
			 * Set value paginazione
			 */
			pagamentiAccertamentoCommand.setResultList(result.getList()); 			/* Risultati ricerca */
			pagamentiAccertamentoCommand.setPreviousPage(result.isPreviousPage());	/* C'è una pagina precedente */
			pagamentiAccertamentoCommand.setNextPage(result.isNextPage()); 			/* C'è una pagina successiva */
			pagamentiAccertamentoCommand.setTotalPages(result.getTotalPages()); 	/* Numero di pagini in totale */
			pagamentiAccertamentoCommand.setTotalRecords(result.getTotalRecords()); /* Numero elemnti trovati */
			
			/**
			 * Set param da ritornare alla view
			 */
			mav.addObject("accertamentoDto", accertamentoDto);
			/* Model attribute */
			mav.addObject("pagamentiAccertamentoCommand", pagamentiAccertamentoCommand);
			/* Fa in modo di configurare la view, di modo che sia in sola lettura */
			mav.addObject("isReadonly", true);
			
			/*
			 */
			mav.setViewName("dettaglioAccertamento"); /* Nome della view */
			
		}catch(Exception e){
			logger.error("DETTAGLIO :: ACCERTAMENTO :: Fields[accertamentoID: " + pagamentiAccertamentoCommand.getAccertamentoId() + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: ERRORE ", e);
			/* 
			 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
			 */
			return UtilitiesCtrl.getViewErrorMessage("mypivot.accertamenti.errore.internal");
		}
		
		logger.debug("DETTAGLIO :: ACCERTAMENTO :: Fields[accertamentoID: " + pagamentiAccertamentoCommand.getAccertamentoId() + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: END");

		return mav;
	}
}