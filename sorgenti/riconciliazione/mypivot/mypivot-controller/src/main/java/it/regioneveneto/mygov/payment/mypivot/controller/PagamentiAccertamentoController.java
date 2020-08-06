package it.regioneveneto.mygov.payment.mypivot.controller;

import java.math.BigDecimal;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import it.regioneveneto.mygov.payment.mypivot.controller.command.AccertamentoVisualizzaPagamentiCommand;
import it.regioneveneto.mygov.payment.mypivot.controller.command.AccertamentoVisualizzaPagamentiCommand.OPERATION;
import it.regioneveneto.mygov.payment.mypivot.controller.utils.AccertamentoUtils;
import it.regioneveneto.mygov.payment.mypivot.controller.utils.UtilitiesCtrl;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.AccertamentoDettaglioDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.AccertamentoDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.AccertamentoFlussoExportDto;
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
 * 
 * @author Marianna Memoli
 *
 */
@Controller
@RequestMapping("/protected/accertamenti")
public class PagamentiAccertamentoController {

	private static Log logger = LogFactory.getLog(PagamentiAccertamentoController.class);

	@Autowired
	private OperatoreEnteTipoDovutoService operatoreEnteTipoDovutoService;
	
	@Autowired
	private AccertamentoService accertamentoService;
	
	@Autowired
	private AccertamentoDettaglioService accertamentoDettaglioService;
	
	public PagamentiAccertamentoController() {
		super();
	}
	 
	/**
	 * Ritorna alla View il path relativo della pagina per aggiungere pagamenti in accertamento.
	 * 
	 * @return {@link String}
	 * @author Marianna Memoli
	 */
	@ModelAttribute("R_PATH_PAGE_ADD")
    public String getUrlPageAddPagamenti() {
		 return Constants.PATH_PAGE_ADD_PAG_ACCERTAMENTO;
    }
	
	/**
	 * Ritorna alla View il path relativo della pagina per rimuovere pagamenti dall'accertamento.
	 * 
	 * @return {@link String}
	 * @author Marianna Memoli
	 */
	@ModelAttribute("R_PATH_PAGE_DEL")
    public String getUrlPageDelPagamenti() {
		 return Constants.PATH_PAGE_DEL_PAG_ACCERTAMENTO;
    }
	
	/**
	 * Prepara e restituisce la view che permette di selezionare i pagamenti da aggiungere all'accertamento in GET. 
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
	 *   5. L'accertamento deve essere ancora in stato "INSERITO" per potervi aggiungere pagamenti, se non lo è mostro un messaggio di errore
	 * 
	 * A seguito dei controlli, recupero le informazioni necessarie alla view che sono: 
	 * 
	 *   "accertamentoDto": Oggetto che presenta le informazioni caratterizzanti l'accertamento(IN VIEW SONO PRESENTATE IN SOLA LETTURA)
	 *   "isReadonly": 		Valorizzato a false, fa in modo di configurare la view di modo che sia editabile per la modifca dei pagamenti.
	 *   "isAddAction": 	Valorizzato a true, fa in modo di configurare la view di modo che sia possibile aggiungere pagamenti.
	 *   "pagamentiAccertamentoCommand": Model attribute che mappa i campi della form
	 *   
	 * @param {@link HttpServletRequest} request
	 * @param {@link Long} 			 	 accertamentoID,  Identificativo dell'accertamento a cui associare i pagamenti
	 * @param {@link String} 			 page, 			  Pagina da visualizzare
	 * @param {@link String} 			 pageSize,  	  Numero di elementi per pagina
	 * 
	 * @return {@link ModelAndView}
	 * 
	 * @author Marianna Memoli
	 */
	@RequestMapping(value = { "/addPagamenti.html" }, method = RequestMethod.GET)
	public ModelAndView addPagamenti_Get( HttpServletRequest request, Model model, 
			@RequestParam(value = "accertamentoID") String accertamentoID, @RequestParam(required = false) String pg, @RequestParam(required = false) String pgSize) {
		
		logger.debug("ADD PAGAMENTI :: ACCERTAMENTO :: GET :: Fields[accertamentoID: " + accertamentoID + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: START");
		
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
			AccertamentoUtils.setSessionFilterPagamentiToBean(request, SecurityContext.getEnte().getCodIpaEnte(), null, null, null, null, null, null, null, null, null, null, pg, pgSize, SessionVariables.ACTION_ACCERTAMENTO_RICERCA_PAGAMENTI_ADD, pagamentiAccertamentoCommand);
			
			/**
			 * Avvio la ricerca
			 */
			mav = addPagamenti(request, pagamentiAccertamentoCommand);
			
			/** NOTE: ================================================================================================================== */
			/* 
			 * L'istruzione "model.containsAttribute('successRtAdd')" mi serve per verificare se è stato passato il parametro 
			 * "successRtAdd" da una redirect view cosi da mostrare il messaggio di errore appropriato nella VIEW che sarà caricata.
			 * 
			 * In particolare il parametro "successRtAdd" è gestito per quando l'utente ha definito delle RT da aggiungere all'accertamento
			 * e l'operazione è andata a buon fine.
			 *    
			 * Il valore atteso del parametro è un boolean.
			 */    
			/** ======================================================================================================================== */
			
			/**
			 * Leggo il parametro(vedi Note) dagli attributi.
			 */
			Boolean successRtAdd = (model != null && model.containsAttribute("successRtAdd")) ? (Boolean) model.asMap().get("successRtAdd") : false;
				
			/* Valorizzando l'attributo, lato view so di dover mostrare dei messaggi in popup o meno */
			mav.addObject("successRtAdd", successRtAdd);
			
		}catch(Exception e){
			logger.error("ADD PAGAMENTI :: ACCERTAMENTO :: GET :: Fields[accertamentoID: " + accertamentoID + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: ERRORE ", e);
			/* 
			 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
			 */
			return UtilitiesCtrl.getViewErrorMessage("mypivot.accertamenti.errore.internal");
		}
		
		logger.debug("ADD PAGAMENTI :: ACCERTAMENTO :: GET :: Fields[accertamentoID: " + accertamentoID + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: END");

		return mav;
	}
	
	/**
	 * Il controller è richiamato al submit della form per aggiungere i pagamenti in accertamento (POST). 
	 * La funzione verifica se ci sono pagamenti selezionati da salvare, se si costruisce il pacchetto dati da inviare alla pagina
	 * per lo spacchettamento in capitoli e accertamenti dei pagamenti. 
	 * Dopodicchè, leggo i filtri di ricerca sottomessi e li sovrascrivo a quelli presenti in sessione.
	 * Avvio di nuovo la ricerca, reindirizzando l'utente alla stessa pagina ma in method GET.
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
	 *   5. L'accertamento deve essere ancora in stato "INSERITO" per potervi aggiungere pagamenti, se non lo è mostro un messaggio di errore
	 * 
	 * @param {@link HttpServletRequest} request
	 * @param {@link AccertamentoVisualizzaPagamentiCommand} pagamentiAccertamentoCommand, Bean della form di ricerca
	 * 
	 * @return {@link ModelAndView}
	 * @author Marianna Memoli
	 */
	@RequestMapping(value = { "/addPagamenti.html" }, method = RequestMethod.POST)
	public ModelAndView addPagamenti_Post(HttpServletRequest request, RedirectAttributes redirectAttributes, @ModelAttribute("pagamentiAccertamentoCommand") AccertamentoVisualizzaPagamentiCommand pagamentiAccertamentoCommand) {
		
		logger.debug("ADD PAGAMENTI :: ACCERTAMENTO :: POST :: Fields[accertamentoID: " + pagamentiAccertamentoCommand.getAccertamentoId() + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: START");
		
		try{
			/**
			 * Prima di avviare la ricerca, controllo se ho da salvare l'aggiunta di nuovi pagamenti in accertamento.
			 * Se si, costruisco il pacchetto dati da inviare alla view che permette lo spacchettamento in capitoli e 
			 * accertamenti della\e RT.
			 */
			if(pagamentiAccertamentoCommand.getOperation() != null && pagamentiAccertamentoCommand.getOperation().equals(OPERATION.SAVE)){
				/* */
				List<AccertamentoFlussoExportDto> selected = new ArrayList<AccertamentoFlussoExportDto>();
				/* */
				BigDecimal totaleImportoDovuti = new BigDecimal(0);
				
				/** Ciclo la lista risottomessa dal submit e ne considero solo quelli selezionati */
				for(AccertamentoDettaglioDto item : pagamentiAccertamentoCommand.getResultList()) {
					/* */
					if(item.getSelected() != null && item.getSelected()) {
						/* */
						selected.add(item.getFlussoExportDTO());
						/* */
						if(item.getFlussoExportDTO().getSingoloImportoPagato() != null) 
							/** Se l'importo è valorizzato, lo aggiungo al totale */
							totaleImportoDovuti = totaleImportoDovuti.add(item.getFlussoExportDTO().getSingoloImportoPagato());
					}
				}
				
				/** Se ci sono elementi selezionati */
				if(!selected.isEmpty()){
					try{
						/* Aggiungo gli attributi da passare alla view */
						redirectAttributes.getFlashAttributes().clear();
						redirectAttributes.addFlashAttribute("pagamentiList",  selected);
						redirectAttributes.addFlashAttribute("totaleImportoDovuti", totaleImportoDovuti);
						
						/**
						 * Instance view redirect 
						 */
						ModelAndView mav = new ModelAndView(new RedirectView(request.getContextPath() + "/protected/accertamenti/sceltaUfficioCapitolo.html?accertamentoID=" + pagamentiAccertamentoCommand.getAccertamentoId()));
						
						return mav;
					}catch(Exception e){
						logger.error("ADD PAGAMENTI :: ACCERTAMENTO :: POST :: Fields[accertamentoID: " + pagamentiAccertamentoCommand.getAccertamentoId() + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: ERRORE ", e);
						/* 
						 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
						 */
						return UtilitiesCtrl.getViewErrorMessage("mypivot.accertamenti.errore.addPagamenti.nuovo");
					}
				}
			}// close if save == true
			
			/**
			 * E' stata avviata una nuova ricerca, con nuovi filtri, reimposto la pagina di partenza alla prima.
			 */
			pagamentiAccertamentoCommand.setPage(1);
			
			// Salvo i nuovi filtri di ricerca impostati in sessione.
			if(SecurityContext.getEnte() != null){
				Map<String, Object> filtersMap = new HashMap<String, Object>();
				/* */
				filtersMap.put(SessionVariables.ACCERTAMENTO_PAGE_SIZE, 		pagamentiAccertamentoCommand.getPageSize());
				filtersMap.put(SessionVariables.ACCERTAMENTO_PAGE, 				pagamentiAccertamentoCommand.getPage());
				/* */
				filtersMap.put(SessionVariables.ACCERTAMENTO_CODICE_TIPO_DOVUTO,		 pagamentiAccertamentoCommand.getCod_tipo_dovuto());
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
				filtersMap.put(SessionVariables.ACCERTAMENTO_DATA_ESITO_SINGOLO_PAG_AL, 	 pagamentiAccertamentoCommand.getData_esito_singolo_pagamento_a());
					 
				SecurityContext.setEnteViewFilters(SecurityContext.getEnte().getCodIpaEnte(), SessionVariables.ACTION_ACCERTAMENTO_RICERCA_PAGAMENTI_ADD, filtersMap);
			}
		   // =======================================================
					
			logger.debug("ADD PAGAMENTI :: ACCERTAMENTO :: POST :: Fields[accertamentoID: " + pagamentiAccertamentoCommand.getAccertamentoId() + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: END");

			/**
			 * Reindirizzo l'utente alla stessa pagina ma in method POST, questo perchè capitava che quando si fa il refresh della pagina con F5
			 * risottometteva informazioni non più valide.
			 */
			String urlParam = "?accertamentoID=" + pagamentiAccertamentoCommand.getAccertamentoId() + 
							  "&pg=" + pagamentiAccertamentoCommand.getPage() + "&pgSize=" + pagamentiAccertamentoCommand.getPageSize();
			
			return new ModelAndView(new RedirectView(request.getContextPath() + Constants.PATH_PAGE_ADD_PAG_ACCERTAMENTO + urlParam)); 
			
		}catch(Exception e){
			logger.error("ADD PAGAMENTI :: ACCERTAMENTO :: POST :: Fields[accertamentoID: " + pagamentiAccertamentoCommand.getAccertamentoId() + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: ERRORE ", e);
			/* 
			 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
			 */
			return UtilitiesCtrl.getViewErrorMessage("mypivot.accertamenti.errore.internal");
		}
	}
	
	/**
	 * Funzione che gestisce le fasi comuni della view che permette di selezionare i pagamenti da aggiungere all'accertamento 
	 * in GET e POST.
	 * 
	 * @param {@link HttpServletRequest} request
	 * @param {@link AccertamentoVisualizzaPagamentiCommand} pagamentiAccertamentoCommand
	 * 
	 * @return {@link ModelAndView}
	 */
	public ModelAndView addPagamenti(HttpServletRequest request, AccertamentoVisualizzaPagamentiCommand pagamentiAccertamentoCommand) {
		
		logger.debug("ADD PAGAMENTI :: ACCERTAMENTO :: Fields[accertamentoID: " + pagamentiAccertamentoCommand.getAccertamentoId() + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: START");
		
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
			 * Se la lista è vuota, mostro un messaggio di errore perchè vuol dire che l'utente non può ne consultare ne modificare accertamenti.
			 */
			List<String> activeOperatoreEnteTd = operatoreEnteTipoDovutoService.getListaCodTipoDovutoForOperatoreAndCodIpaEnte(SecurityContext.getUtente().getCodFedUserId(), SecurityContext.getEnte().getCodIpaEnte());
			
			if (activeOperatoreEnteTd == null || activeOperatoreEnteTd.isEmpty()) {
				logger.warn("ADD PAGAMENTI :: ACCERTAMENTO :: Fields[accertamentoID: " + pagamentiAccertamentoCommand.getAccertamentoId() + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: NON risulta OPERATORE per nessun tipo dovuto.");
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
			 * Controllo che l'utente sia operatore per il tipo dovuto che si sta integrando con i pagamenti.
			 */
			if (!activeOperatoreEnteTd.contains(accertamentoDto.getEnteTipoDovuto().getCodTipo())) {
				logger.warn("ADD PAGAMENTI :: ACCERTAMENTO :: Fields[accertamentoID: " + pagamentiAccertamentoCommand.getAccertamentoId() + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: NON risulta OPERATORE per il tipo dovuto dell'accertamento.");
				/* 
				 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
				 */
				return UtilitiesCtrl.getViewErrorMessage("mypivot.messages.error.notAuthorized");
			}
			
			/**
			 * Controllo che l'accertamento sia in stato "INSERITO" per poter essere modificato.
			 */
			if (!accertamentoDto.getStato().getCodStato().equals(Constants.COD_TIPO_STATO_ACCERTAMENTO_INSERITO)) {
				logger.warn("ADD PAGAMENTI :: ACCERTAMENTO :: Fields[accertamentoID: " + pagamentiAccertamentoCommand.getAccertamentoId() + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: L'accertamento è in stato[" + accertamentoDto.getStato().getCodStato() + "] perciò non può essere modificato.");
				/* 
				 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
				 */
				return UtilitiesCtrl.getViewErrorMessage("mypivot.accertamenti.errore.notAuthorizedUpdate", new Object[]{accertamentoDto.getDeNomeAccertamento(), accertamentoDto.getStato().getCodStato()});
			}
			
			/**
			 * Inizializzo il bean per la ricerca
			 */
			pagamentiAccertamentoCommand = AccertamentoUtils.initializeFilterPagamenti(pagamentiAccertamentoCommand, accertamentoDto.getEnteTipoDovuto().getCodTipo(), accertamentoDto.getId());
			
			/**
			 * Instance return bean 
			 */
			PageDto<AccertamentoDettaglioDto> result =  new PageDto<AccertamentoDettaglioDto>();
			
			try{
				Boolean checkEsito = pagamentiAccertamentoCommand.getData_esito_singolo_pagamento_check() != null ? pagamentiAccertamentoCommand.getData_esito_singolo_pagamento_check() : false;
				Boolean checkAgg = pagamentiAccertamentoCommand.getData_ultimo_aggiornamento_check() != null ? pagamentiAccertamentoCommand.getData_ultimo_aggiornamento_check() : false;
				
				/**
				 * Eseguo la query di ricerca
				 */
				result = accertamentoDettaglioService.findPagamentiAccertabiliByFilter(
							 SecurityContext.getEnte().getId(), pagamentiAccertamentoCommand.getCod_tipo_dovuto(), 
							 pagamentiAccertamentoCommand.getCodice_iud(), 
							 pagamentiAccertamentoCommand.getCodice_iuv(), 
							 pagamentiAccertamentoCommand.getCodice_identificativo_univoco_pagatore(), 
							 checkEsito ? pagamentiAccertamentoCommand.getData_esito_singolo_pagamento_da() : null, 
							 checkEsito ? pagamentiAccertamentoCommand.getData_esito_singolo_pagamento_a() : null,
							 checkAgg ? pagamentiAccertamentoCommand.getData_ultimo_aggiornamento_da() : null, 
							 checkAgg ? pagamentiAccertamentoCommand.getData_ultimo_aggiornamento_a() : null, 
							 true, pagamentiAccertamentoCommand.getPage(), pagamentiAccertamentoCommand.getPageSize());
				
			}catch(Exception e){
				logger.error("ADD PAGAMENTI :: ACCERTAMENTO :: Fields[accertamentoID: " + pagamentiAccertamentoCommand.getAccertamentoId() + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: ERRORE ", e);
				/* */
				messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.accertamenti.errore.addPagamenti.ricerca"));
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
			mav.addObject("isReadonly", false); /* Fa in modo di configurare la view, di modo che sia editabile */
			mav.addObject("isAddAction", true); /* Fa in modo di configurare la view  di modo che sia possibile aggiungere pagamenti. */
			mav.addObject("pagamentiAccertamentoCommand", pagamentiAccertamentoCommand); /* Model attribute */
			
			mav.setViewName("editPagamentiAccertamento"); /* Nome della view */
			
		}catch(Exception e){
			logger.error("ADD PAGAMENTI :: ACCERTAMENTO :: Fields[accertamentoID: " + pagamentiAccertamentoCommand.getAccertamentoId() + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: ERRORE ", e);
			/* 
			 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
			 */
			return UtilitiesCtrl.getViewErrorMessage("mypivot.accertamenti.errore.internal");
		}
		
		logger.debug("ADD PAGAMENTI :: ACCERTAMENTO :: Fields[accertamentoID: " + pagamentiAccertamentoCommand.getAccertamentoId() + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: ENTE");

		return mav;
	}
	 
	/**
	 * Prepara e restituisce la view che permette di selezionare i pagamenti da rimuovere dall'accertamento in GET. 
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
	 *   5. L'accertamento deve essere ancora in stato "INSERITO" per potervi rimuovere pagamenti, se non lo è mostro un messaggio di errore
	 * 
	 * A seguito dei controlli, recupero le informazioni necessarie alla view che sono: 
	 * 
	 *   "accertamentoDto": Oggetto che presenta le informazioni caratterizzanti l'accertamento(IN VIEW SONO PRESENTATE IN SOLA LETTURA)
	 *   "isReadonly": Valorizzato a false, fa in modo di configurare la view di modo che sia editabile per la modifca dei pagamenti.
	 *   "isAddAction": Valorizzato a false, fa in modo di configurare la view di modo che sia possibile rimuovere pagamenti.
	 *   "pagamentiAccertamentoCommand": Model attribute che mappa i campi della form
	 *   
	 * @param {@link HttpServletRequest} request
	 * @param {@link Long} 			 	 accertamentoID,  Identificativo dell'accertamento da cui disassociare i pagamenti
	 * @param {@link String} 			 page, 			  Pagina da visualizzare
	 * @param {@link String} 			 pageSize,  	  Numero di elementi per pagina
	 * 
	 * @return {@link ModelAndView}
	 * 
	 * @author Marianna Memoli
	 */
	@RequestMapping(value = { "/deletePagamenti.html" }, method = RequestMethod.GET)
	public ModelAndView deletePagamenti_Get(HttpServletRequest request, Model model, 
			@RequestParam(value = "accertamentoID") String accertamentoID, @RequestParam(required = false) String pg, @RequestParam(required = false) String pgSize) {
		
		logger.debug("DELETE PAGAMENTI :: ACCERTAMENTO :: GET :: Fields[accertamentoID: " + accertamentoID + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: START");
		
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
			AccertamentoUtils.setSessionFilterPagamentiToBean(request, SecurityContext.getEnte().getCodIpaEnte(), null, null, null, null, null, null, null, null, null, null, pg, pgSize, SessionVariables.ACTION_ACCERTAMENTO_RICERCA_PAGAMENTI_DELETE, pagamentiAccertamentoCommand);
			/**
			 * Avvio la ricerca
			 */
			mav = deletePagamenti(request, pagamentiAccertamentoCommand);
			
			/** NOTE: ================================================================================================================== */
			/* 
			 * L'istruzione "model.containsAttribute('successRtDel')" mi serve per verificare se è stato passato il parametro 
			 * "successRtDel" da una redirect view cosi da mostrare il messaggio di errore appropriato nella VIEW che sarà caricata.
			 * 
			 * In particolare il parametro "successRtDel" è gestito per quando l'utente ha definito delle RT da rimuovere dall'accertamento
			 * e l'operazione è andata a buon fine.
			 *    
			 * Il valore atteso del parametro è un boolean.
			 */    
			/** ======================================================================================================================== */
			
			/**
			 * Leggo il parametro(vedi Note) dagli attributi.
			 */
			Boolean successRtDel = (model != null && model.containsAttribute("successRtDel")) ? (Boolean) model.asMap().get("successRtDel") : false;
				
			/* Valorizzando l'attributo, lato view so di dover mostrare dei messaggi in popup o meno */
			mav.addObject("successRtDel", successRtDel);
			
		}catch(Exception e){
			logger.error("DELETE PAGAMENTI :: ACCERTAMENTO :: GET :: Fields[accertamentoID: " + accertamentoID + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: ERRORE ", e);
			/* 
			 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
			 */
			return UtilitiesCtrl.getViewErrorMessage("mypivot.accertamenti.errore.internal");
		}
		
		logger.debug("DELETE PAGAMENTI :: ACCERTAMENTO :: GET :: Fields[accertamentoID: " + accertamentoID + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: END");

		return mav;
	}
	
	/**
	 * Il controller è richiamato al submit della form per rimuovere i pagamenti dall'accertamento (POST). 
	 * La funzione verifica se ci sono pagamenti selezionati da rimuovere, se si esegue le cancellazioni.
	 * Dopodicchè, leggo i filtri di ricerca sottomessi e li sovrascrivo a quelli presenti in sessione.
	 * Avvio di nuovo la ricerca, reindirizzando l'utente alla stessa pagina ma in method GET.
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
	 *   5. L'accertamento deve essere ancora in stato "INSERITO" per potervi rimuovere pagamenti, se non lo è mostro un messaggio di errore
	 * 
	 * @param {@link HttpServletRequest} request
	 * @param {@link AccertamentoVisualizzaPagamentiCommand} pagamentiAccertamentoCommand, Bean della form di ricerca
	 * 
	 * @return {@link ModelAndView}
	 * 
	 * @author Marianna Memoli
	 */
	@RequestMapping(value = { "/deletePagamenti.html" }, method = RequestMethod.POST)
	public ModelAndView deletePagamenti_Post(HttpServletRequest request, RedirectAttributes redirectAttributes,
			@ModelAttribute("pagamentiAccertamentoCommand") AccertamentoVisualizzaPagamentiCommand pagamentiAccertamentoCommand) {
		
		logger.debug("DELETE PAGAMENTI :: ACCERTAMENTO :: POST :: Fields[accertamentoID: " + pagamentiAccertamentoCommand.getAccertamentoId() + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: START");
		
		try{
			/**
			 * Prima di avviare la ricerca, controllo se ho da salvare la rimozione di pagamenti in accertamento 
			 */
			if(pagamentiAccertamentoCommand.getOperation() != null && pagamentiAccertamentoCommand.getOperation().equals(OPERATION.SAVE)){
				
				List<AccertamentoFlussoExportDto> selected = new ArrayList<AccertamentoFlussoExportDto>();
				
				/**
				 * Ciclo la lista risottomessa dal submit e considero solo quelli selezionati
				 */
				for(AccertamentoDettaglioDto item : pagamentiAccertamentoCommand.getResultList()) {
					if(item.getSelected() != null && item.getSelected()) selected.add(item.getFlussoExportDTO());
				}
				
				if(!selected.isEmpty()){
					try{
						/**
						 * Cancello l'associazione tra i pagamenti e l'accertamento dato i dettagli della relazione.
						 */
						accertamentoDettaglioService.deletePagamenti(
									Long.parseLong(pagamentiAccertamentoCommand.getAccertamentoId()),
									SecurityContext.getUtente().getId(), 
									SecurityContext.getEnte().getCodIpaEnte(), selected);
						
						/**
						 * Aggiungo come attributo da passare alla view il parametro che mostra in popup il messaggio
						 * di operazione eseguita con successo.
						 */
						redirectAttributes.getFlashAttributes().clear();
						redirectAttributes.addFlashAttribute("successRtDel", Boolean.TRUE);
						
					}catch(Exception e){
						logger.error("DELETE PAGAMENTI :: ACCERTAMENTO :: POST :: Fields[accertamentoID: " + pagamentiAccertamentoCommand.getAccertamentoId() + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: ERRORE ", e);
						/* 
						 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
						 */
						return UtilitiesCtrl.getViewErrorMessage("mypivot.accertamenti.errore.addPagamenti.nuovo");
					}
				}
			}// close if insert
			
			/**
			 * E' stata avviata una nuova ricerca, con nuovi filtri, reimposto la pagina di partenza alla prima.
			 */
			pagamentiAccertamentoCommand.setPage(1);
			
			// Salvo i nuovi filtri di ricerca impostati in sessione.
			if(SecurityContext.getEnte() != null){
				Map<String, Object> filtersMap = new HashMap<String, Object>();
				/* */
				filtersMap.put(SessionVariables.ACCERTAMENTO_PAGE_SIZE, 		pagamentiAccertamentoCommand.getPageSize());
				filtersMap.put(SessionVariables.ACCERTAMENTO_PAGE, 				pagamentiAccertamentoCommand.getPage());
				/* */
				filtersMap.put(SessionVariables.ACCERTAMENTO_CODICE_TIPO_DOVUTO,		 pagamentiAccertamentoCommand.getCod_tipo_dovuto());
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
				filtersMap.put(SessionVariables.ACCERTAMENTO_DATA_ESITO_SINGOLO_PAG_AL, 	 pagamentiAccertamentoCommand.getData_esito_singolo_pagamento_a());
					 
				SecurityContext.setEnteViewFilters(SecurityContext.getEnte().getCodIpaEnte(), SessionVariables.ACTION_ACCERTAMENTO_RICERCA_PAGAMENTI_DELETE, filtersMap);
			}
		   // =======================================================
			
			logger.debug("DELETE PAGAMENTI :: ACCERTAMENTO :: POST :: Fields[accertamentoID: " + pagamentiAccertamentoCommand.getAccertamentoId() + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: END");

			/**
			 * Reindirizzo l'utente alla stessa pagina ma in method POST, questo perchè capitava che quando si fa il refresh della pagina con F5
			 * risottometteva informazioni non più valide.
			 */
			String urlParam = "?accertamentoID=" + pagamentiAccertamentoCommand.getAccertamentoId() + 
							  "&pg=" + pagamentiAccertamentoCommand.getPage() + "&pgSize=" + pagamentiAccertamentoCommand.getPageSize();
			
			return new ModelAndView(new RedirectView(request.getContextPath() + Constants.PATH_PAGE_DEL_PAG_ACCERTAMENTO + urlParam));
			
		}catch(Exception e){
			logger.error("DELETE PAGAMENTI :: ACCERTAMENTO :: POST :: Fields[accertamentoID: " + pagamentiAccertamentoCommand.getAccertamentoId() + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: ERRORE ", e);
			/* 
			 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
			 */
			return UtilitiesCtrl.getViewErrorMessage("mypivot.accertamenti.errore.internal");
		}
	}
	
	/**
	 * Funzione che gestisce le fasi comuni della view che permette di selezionare i pagamenti "RT_IUF_TES" da rimuovere dall'accertamento 
	 * in GET e POST.
	 * 
	 * @param {@link HttpServletRequest} request
	 * @param {@link AccertamentoVisualizzaPagamentiCommand} pagamentiAccertamentoCommand
	 * 
	 * @return {@link ModelAndView}
	 */
	public ModelAndView deletePagamenti(HttpServletRequest request, AccertamentoVisualizzaPagamentiCommand pagamentiAccertamentoCommand) {
		
		logger.debug("DELETE PAGAMENTI :: ACCERTAMENTO :: Fields[accertamentoID: " + pagamentiAccertamentoCommand.getAccertamentoId() + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: START");
		
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
			 * Se la lista è vuota, mostro un messaggio di errore perchè vuol dire che l'utente non può ne consultare ne modificare accertamenti.
			 */
			List<String> activeOperatoreEnteTd = operatoreEnteTipoDovutoService.getListaCodTipoDovutoForOperatoreAndCodIpaEnte(SecurityContext.getUtente().getCodFedUserId(), SecurityContext.getEnte().getCodIpaEnte());
			
			if (activeOperatoreEnteTd == null || activeOperatoreEnteTd.isEmpty()) {
				logger.warn("ADD PAGAMENTI :: ACCERTAMENTO :: Fields[accertamentoID: " + pagamentiAccertamentoCommand.getAccertamentoId() + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: NON risulta OPERATORE per nessun tipo dovuto.");
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
			 * Controllo che l'utente sia operatore per il tipo dovuto che si sta integrando con i pagamenti.
			 */
			if (!activeOperatoreEnteTd.contains(accertamentoDto.getEnteTipoDovuto().getCodTipo())) {
				logger.warn("DELETE PAGAMENTI :: ACCERTAMENTO :: Fields[accertamentoID: " + pagamentiAccertamentoCommand.getAccertamentoId() + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: NON risulta OPERATORE per il tipo dovuto dell'accertamento.");
				/* 
				 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
				 */
				return UtilitiesCtrl.getViewErrorMessage("mypivot.messages.error.notAuthorized");
			}
			
			/**
			 * Controllo che l'accertamento sia in stato "INSERITO" per poter essere modificato.
			 */
			if (!accertamentoDto.getStato().getCodStato().equals(Constants.COD_TIPO_STATO_ACCERTAMENTO_INSERITO)) {
				logger.warn("DELETE PAGAMENTI :: ACCERTAMENTO :: Fields[accertamentoID: " + pagamentiAccertamentoCommand.getAccertamentoId() + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: L'accertamento è in stato[" + accertamentoDto.getStato().getCodStato() + "] perciò non può essere modificato.");
				/* 
				 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
				 */
				return UtilitiesCtrl.getViewErrorMessage("mypivot.accertamenti.errore.notAuthorizedUpdate", new Object[]{accertamentoDto.getDeNomeAccertamento(), accertamentoDto.getStato().getCodStato()});
			}
			
			/**
			 * Inizializzo il bean per la ricerca
			 */
			pagamentiAccertamentoCommand = AccertamentoUtils.initializeFilterPagamenti(pagamentiAccertamentoCommand, accertamentoDto.getEnteTipoDovuto().getCodTipo(), accertamentoDto.getId());
			
			/**
			 * Instance return bean 
			 */
			PageDto<AccertamentoDettaglioDto> result =  new PageDto<AccertamentoDettaglioDto>();
			
			try{
				Boolean checkEsito = pagamentiAccertamentoCommand.getData_esito_singolo_pagamento_check() != null ? pagamentiAccertamentoCommand.getData_esito_singolo_pagamento_check() : false;
				Boolean checkAgg = pagamentiAccertamentoCommand.getData_ultimo_aggiornamento_check() != null ? pagamentiAccertamentoCommand.getData_ultimo_aggiornamento_check() : false;
				
				/**
				 * Eseguo la query di ricerca
				 */
				result = accertamentoDettaglioService.findPagamentiInAccertamentoByFilter(
							 Long.parseLong(accertamentoDto.getId()), SecurityContext.getEnte().getId(), 
							 pagamentiAccertamentoCommand.getCod_tipo_dovuto(), 
							 pagamentiAccertamentoCommand.getCodice_iud(), 
							 pagamentiAccertamentoCommand.getCodice_iuv(), 
							 pagamentiAccertamentoCommand.getCodice_identificativo_univoco_pagatore(), 
							 checkEsito ? pagamentiAccertamentoCommand.getData_esito_singolo_pagamento_da() : null, 
							 checkEsito ? pagamentiAccertamentoCommand.getData_esito_singolo_pagamento_a() : null,
							 checkAgg ? pagamentiAccertamentoCommand.getData_ultimo_aggiornamento_da() : null, 
							 checkAgg ? pagamentiAccertamentoCommand.getData_ultimo_aggiornamento_a() : null, 
							 true, pagamentiAccertamentoCommand.getPage(), pagamentiAccertamentoCommand.getPageSize());
				
			}catch(Exception e){
				logger.error("DELETE PAGAMENTI :: ACCERTAMENTO :: Fields[accertamentoID: " + pagamentiAccertamentoCommand.getAccertamentoId() + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: ERRORE ", e);
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
			mav.addObject("isReadonly", false); 	/* Fa in modo di configurare la view, di modo che sia editabile */
			mav.addObject("isAddAction", false); 	/* Fa in modo di configurare la view  di modo che sia possibile rimuovere pagamenti. */
			mav.addObject("pagamentiAccertamentoCommand", pagamentiAccertamentoCommand); 	/* Model attribute */
			
			mav.setViewName("editPagamentiAccertamento"); /* Nome della view */
			
		}catch(Exception e){
			logger.error("DELETE PAGAMENTI :: ACCERTAMENTO :: Fields[accertamentoID: " + pagamentiAccertamentoCommand.getAccertamentoId() + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: ERRORE ", e);
			/* 
			 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
			 */
			return UtilitiesCtrl.getViewErrorMessage("mypivot.accertamenti.errore.internal");
		}
		
		logger.debug("DELETE PAGAMENTI :: ACCERTAMENTO :: Fields[accertamentoID: " + pagamentiAccertamentoCommand.getAccertamentoId() + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: END ");

		return mav;
	}
}