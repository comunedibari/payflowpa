package it.regioneveneto.mygov.payment.mypivot.controller.cruscottoIncassi;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import it.regioneveneto.mygov.payment.mypivot.controller.command.VisualizzaFlussoRicevutaCommand;
import it.regioneveneto.mygov.payment.mypivot.controller.utils.UtilitiesCtrl;
import it.regioneveneto.mygov.payment.mypivot.controller.visualizzazioneFlussi.filter.FlussiRicevutaViewFilter;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.FlussoRicevutaDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.MessagesDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.PageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTipoDovutoTO;
import it.regioneveneto.mygov.payment.mypivot.service.FlussoExportService;
import it.regioneveneto.mygov.payment.mypivot.service.OperatoreEnteTipoDovutoService;
import it.regioneveneto.mygov.payment.mypivot.service.VmStatisticaAccertamentiService;
import it.regioneveneto.mygov.payment.mypivot.utils.Constants;
import it.regioneveneto.mygov.payment.mypivot.utils.SecurityContext;
import it.regioneveneto.mygov.payment.mypivot.utils.SessionVariables;

/**
 * 
 * @author Alessandro Paolillo
 *
 */
@Controller
@RequestMapping("/protected/cruscottoIncassi")
public class VisualizzaDettaglioCruscottoController {

	private static final Logger LOG = Logger.getLogger(VisualizzaDettaglioCruscottoController.class);

	@Autowired
	private OperatoreEnteTipoDovutoService operatoreEnteTipoDovutoService;

	@Autowired
	private FlussoExportService flussoExportService;

	@Autowired
	private VmStatisticaAccertamentiService vmStatisticheService;
	
	/**
	 * La funzione fornisce il dettaglio dei singoli pagamenti facenti parte del raggruppamento selezionato.
	 * -----------------------------------------------------------------------------------------------------------------------------------
	 * Prima di erogare la pagina effettuo dei controlli per determinare se sussistono le condizioni tali perchè l'utente possa accedere
	 * alla funzionalità.
	 * 
	 * I controlli eseguiti sono i seguenti:
	 *   1. L'utente deve aver selezionato l'ente beneficiario, se mancante verrà rediretto alla pagina di scelta dell'ente.
	 *   2. L'utente deve avere ruoli attivi, se mancante verrà rediretto alla pagina di scelta dell'ente.
	 *   3. L'utente deve avere uno dei ruoli utili ad accedere alla funzionalità delle "Statistiche" (ROLE_ADMIN e/o ROLE_STATS).
	 * 
	 * A seguito dei controlli, si recuperano la lista di IUD che hanno prodotto il dato statistico.
	 * La lista di IUD estratta viene salvata in sessione con un token.
	 * Il token viene passato in querystring al controller "visualizzaDettaglioCruscotto.html" il quale estrae il dettaglio 
	 * solamente delle RT contenute nella lista di IUD in sessione e le visualizza paginate.
	 * 
	 * @param {@link HttpServletRequest} request
	 * @param {@link String} 			 codCapitolo
	 * @param {@link String} 			 codUfficio
	 * @param {@link String} 			 codTipoDovuto
	 * 
	 * @author Alessandro Paolillo
	 */
	@RequestMapping(value = { "/capitolo/dettaglio.html" }, method = RequestMethod.GET)
	public ModelAndView getDettaglioCapitolo(HttpServletRequest request, @RequestParam(value = "capitolo", required = true) String codCapitolo, 
		@RequestParam(value = "ufficio", required = true) String codUfficio, @RequestParam(value = "dovuto", required = true) String codTipoDovuto) {
		
		LOG.debug("DETTAGLIO CRUSCOTTO :: STATISTICA CAPITOLO :: GET :: Fields[codCapitolo:" + codCapitolo + ", codUfficio:" + codUfficio + ", " +
				  "codTipoDovuto:" + codTipoDovuto + "codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: START");
		
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
			 * Recupero dalla sessione l'oggetto in cui ho salvato i filtri della ricerca.
			 */
			Map<String, Object> filtersMap = SecurityContext.getEnteViewFilters(SecurityContext.getEnte().getCodIpaEnte(), SessionVariables.ACTION_STATISTICA_CAPITOLI);
			
			/**
			 *  GET ENTE ID
			 */
			Long enteId = SecurityContext.getEnte().getId();
			
			/**
			 * Controllo che l'utente sia operatore per il tipo dovuto.
			 */
			if (!operatoreEnteTipoDovutoService.isCodTipoDovutoValidoPerOperatoreAndCodIpaEnte(SecurityContext.getUtente().getCodFedUserId(), SecurityContext.getEnte().getCodIpaEnte(), codTipoDovuto)) {
				LOG.error("DETTAGLIO CRUSCOTTO :: STATISTICA CAPITOLO :: GET :: Fields[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: NON risulta OPERATORE del tipo dovuto per cui si è filtrata la statistica.");
				/* 
				 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
				 */
				return UtilitiesCtrl.getViewErrorMessage("mypivot.messages.error.notAuthorized");
			}
			
			/**
			 *  Individuo quale filtro temporale è attivo
			 */
			Boolean annoCheck = 		  (Boolean) filtersMap.get(SessionVariables.STATISTICHE_CHECK_ANNO);
			Boolean annoMeseCheck = 	  (Boolean) filtersMap.get(SessionVariables.STATISTICHE_CHECK_ANNO_MESE);
			Boolean annoMeseGiornoCheck = (Boolean) filtersMap.get(SessionVariables.STATISTICHE_CHECK_ANNO_MESE_GIORNO);
			
			/**
			 */
			Integer anno = null, mese = null, giorno = null;
			
			if(annoCheck != null && annoCheck){
				/** 
				 *  GET ANNO
				 */
				anno = Integer.parseInt((String) filtersMap.get(SessionVariables.STATISTICHE_ANNO));
			}else
				if(annoMeseCheck != null && annoMeseCheck){
					/* */
					Date date = Constants.MMYYYY.parse((String) filtersMap.get(SessionVariables.STATISTICHE_MESE));
					/* */
					Calendar cal = Calendar.getInstance(); 		cal.setTime(date);
					/**
					 *  GET ANNO e MESE
					 */
					anno = cal.get(Calendar.YEAR); 		mese = cal.get(Calendar.MONTH)+1;
				}
			else
				if(annoMeseGiornoCheck != null && annoMeseGiornoCheck){
					/* */
					Date day = Constants.DDMMYYYY.parse((String) filtersMap.get(SessionVariables.STATISTICHE_GIORNO));
					/* */
					Calendar cal = Calendar.getInstance(); 		cal.setTime(day);
					/**
					 *  GET ANNO, MESE e GIORNO
					 */
					anno = cal.get(Calendar.YEAR); 	  mese = cal.get(Calendar.MONTH)+1;	 	giorno = cal.get(Calendar.DAY_OF_MONTH);
				}
			
			LOG.debug("Parametri usati per la ricerca degli IUD: " + 
					  "enteId[" + enteId + "], mese[" + mese + "], giorno[" + giorno + "], codUfficio[" + codUfficio + "], " + 
					  "codTipoDovuto[" + codTipoDovuto + "], codCapitolo[" + codCapitolo + "]");
			
			/**
			 * Get IUD
			 */
			List<String> iud = vmStatisticheService.findListaPagamentiByFilter(enteId, anno, mese, giorno, codUfficio, codTipoDovuto, codCapitolo, null);
			
			
			if(iud != null && !iud.isEmpty()){
				/**
				 * Generate token
				 */
				UUID token = UUID.randomUUID();
				/**
				 * Add token to session
				 */
				SecurityContext.addToSession(token.toString(), iud);
				/**
				 * Build url redirect
				 */
				String url = request.getContextPath() + "/protected/cruscottoIncassi/visualizzaDettaglioCruscotto.html?token=" + token;
				/**
				 * Instance view redirect 
				 */
				ModelAndView mav = new ModelAndView(new RedirectView(url));
				
				return mav;
			}else
				/* 
				 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
				 */
				return UtilitiesCtrl.getViewErrorMessage("mypivot.statistiche.errore.dettaglio");
			
		}catch(Exception e){
			LOG.error("DETTAGLIO CRUSCOTTO :: STATISTICA CAPITOLO :: GET :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: ERRORE ", e);
			/* 
			 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
			 */
			return UtilitiesCtrl.getViewErrorMessage("mypivot.statistiche.errore.dettaglio");
		}
	}
	
	/**
	 * La funzione fornisce il dettaglio dei singoli pagamenti facenti parte del raggruppamento selezionato.
	 * -----------------------------------------------------------------------------------------------------------------------------------
	 * Prima di erogare la pagina effettuo dei controlli per determinare se sussistono le condizioni tali perchè l'utente possa accedere
	 * alla funzionalità.
	 * 
	 * I controlli eseguiti sono i seguenti:
	 *   1. L'utente deve aver selezionato l'ente beneficiario, se mancante verrà rediretto alla pagina di scelta dell'ente.
	 *   2. L'utente deve avere ruoli attivi, se mancante verrà rediretto alla pagina di scelta dell'ente.
	 *   3. L'utente deve avere uno dei ruoli utili ad accedere alla funzionalità delle "Statistiche" (ROLE_ADMIN e/o ROLE_STATS).
	 * 
	 * A seguito dei controlli, si recuperano la lista di IUD che hanno prodotto il dato statistico.
	 * La lista di IUD estratta viene salvata in sessione con un token.
	 * Il token viene passato in querystring al controller "visualizzaDettaglioCruscotto.html" il quale estrae il dettaglio 
	 * solamente delle RT contenute nella lista di IUD in sessione e le visualizza paginate.
	 * 
	 * @param {@link HttpServletRequest} request
	 * @param {@link String} 			 codiceAccertamento, Codice dell'accertamento
	 * 
	 * @author Alessandro Paolillo
	 */
	@RequestMapping(value = { "/accertamento/dettaglio.html" }, method = RequestMethod.GET)
	public ModelAndView getDettaglioAccertamento(HttpServletRequest request, @RequestParam(value = "codice", required = true) String codAccertamento) {
		
		LOG.debug("DETTAGLIO CRUSCOTTO :: STATISTICA ACCERTAMENTO :: GET :: Fields[codAccertamento: " + codAccertamento + ", codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: START");
		
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
			 * Recupero dalla sessione l'oggetto in cui ho salvato i filtri della ricerca.
			 */
			Map<String, Object> filtersMap = SecurityContext.getEnteViewFilters(SecurityContext.getEnte().getCodIpaEnte(), SessionVariables.ACTION_STATISTICA_ACCERTAMENTI);
			
			/**
			 *  GET ENTE ID
			 */
			Long enteId = SecurityContext.getEnte().getId();
			/**
			 *  GET CODICI CAPITOLO, UFFICIO e DOVUTO
			 */
			String codUfficio =    (String) filtersMap.get(SessionVariables.STATISTICHE_CODICE_UFFICIO);
			String codTipoDovuto = (String) filtersMap.get(SessionVariables.STATISTICHE_CODICE_TIPO_DOVUTO);
			String codCapitolo =   (String) filtersMap.get(SessionVariables.STATISTICHE_CODICE_CAPITOLO);
			
			/**
			 * Controllo che l'utente sia operatore per il tipo dovuto.
			 */
			if (!operatoreEnteTipoDovutoService.isCodTipoDovutoValidoPerOperatoreAndCodIpaEnte(SecurityContext.getUtente().getCodFedUserId(), SecurityContext.getEnte().getCodIpaEnte(), codTipoDovuto)) {
				LOG.error("DETTAGLIO CRUSCOTTO :: STATISTICHE :: GET :: Fields[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: NON risulta OPERATORE del tipo dovuto per cui si è filtrata la statistica.");
				/* 
				 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
				 */
				return UtilitiesCtrl.getViewErrorMessage("mypivot.messages.error.notAuthorized");
			}
			
			/**
			 *  Individuo quale filtro temporale è attivo
			 */
			Boolean annoCheck = 		  (Boolean) filtersMap.get(SessionVariables.STATISTICHE_CHECK_ANNO);
			Boolean annoMeseCheck = 	  (Boolean) filtersMap.get(SessionVariables.STATISTICHE_CHECK_ANNO_MESE);
			Boolean annoMeseGiornoCheck = (Boolean) filtersMap.get(SessionVariables.STATISTICHE_CHECK_ANNO_MESE_GIORNO);
			
			/**
			 */
			Integer anno = null, mese = null, giorno = null;
			
			if(annoCheck != null && annoCheck){
				/** 
				 *  GET ANNO
				 */
				anno = Integer.parseInt((String) filtersMap.get(SessionVariables.STATISTICHE_ANNO));
			}else
				if(annoMeseCheck != null && annoMeseCheck){
					/* */
					Date date = Constants.MMYYYY.parse((String) filtersMap.get(SessionVariables.STATISTICHE_MESE));
					/* */
					Calendar cal = Calendar.getInstance(); 		cal.setTime(date);
					/**
					 *  GET ANNO e MESE
					 */
					anno = cal.get(Calendar.YEAR); 		mese = cal.get(Calendar.MONTH)+1;
				}
			else
				if(annoMeseGiornoCheck != null && annoMeseGiornoCheck){
					/* */
					Date day = Constants.DDMMYYYY.parse((String) filtersMap.get(SessionVariables.STATISTICHE_GIORNO));
					/* */
					Calendar cal = Calendar.getInstance(); 		cal.setTime(day);
					/**
					 *  GET ANNO, MESE e GIORNO
					 */
					anno = cal.get(Calendar.YEAR); 	  mese = cal.get(Calendar.MONTH)+1;	 	giorno = cal.get(Calendar.DAY_OF_MONTH);
				}
			
			LOG.debug("Parametri usati per la ricerca degli IUD: " + 
					  "enteId[" + enteId + "], mese[" + mese + "], giorno[" + giorno + "], codUfficio[" + codUfficio + "], " + 
					  "codTipoDovuto[" + codTipoDovuto + "], codCapitolo[" + codCapitolo + "], codAccertamento[" + codAccertamento + "]");
			
			/**
			 * Get IUD
			 */
			List<String> iud = vmStatisticheService.findListaPagamentiByFilter(enteId, anno, mese, giorno, codUfficio, codTipoDovuto, codCapitolo, codAccertamento);
			
			
			if(iud != null && !iud.isEmpty()){
				/**
				 * Generate token
				 */
				UUID token = UUID.randomUUID();
				/**
				 * Add token to session
				 */
				SecurityContext.addToSession(token.toString(), iud);
				/**
				 * Build url redirect
				 */
				String url = request.getContextPath() + "/protected/cruscottoIncassi/visualizzaDettaglioCruscotto.html?token=" + token;
				/**
				 * Instance view redirect 
				 */
				ModelAndView mav = new ModelAndView(new RedirectView(url));
				
				return mav;
			}else
				/* 
				 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
				 */
				return UtilitiesCtrl.getViewErrorMessage("mypivot.statistiche.errore.dettaglio");
			
		}catch(Exception e){
			LOG.error("DETTAGLIO CRUSCOTTO :: STATISTICA ACCERTAMENTO :: GET :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: ERRORE ", e);
			/* 
			 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
			 */
			return UtilitiesCtrl.getViewErrorMessage("mypivot.statistiche.errore.dettaglio");
		}
	}
	
	/**
	 * Restituisce la view con l'elenco delle RT  (GET). 
	 * 
	 * @author Alesssandro Paolillo
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "/visualizzaDettaglioCruscotto.html" }, method = RequestMethod.GET)
	public ModelAndView get(HttpServletRequest request, Model model, 
			@RequestParam(required = false) String token, @RequestParam(required = false) String pg, @RequestParam(required = false) String pgSize) {
		
		LOG.debug("DETTAGLIO CRUSCOTTO :: GET :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: START");
		/* */
		ModelAndView mav = new ModelAndView();
		
		try{
			/**
			 * La funzione verifica che l'utente autenticato abbia selezionato l'ente.
			 */
			if(!UtilitiesCtrl.hasSecurityEnte()) return new ModelAndView(new RedirectView(request.getContextPath() + Constants.PATH_PAGE_SCELTA_ENTE));
		
			List<String> listaIUD = null;
			
			/**
			 * recupero dalla sessione la lista di IUD
			 */
			if(StringUtils.hasText(token)){
				/* */
				listaIUD = (List<String>) SecurityContext.getFromSession(token);
			}else{
				
				Map<String, Object> filtersMap = SecurityContext.getEnteViewFilters(SecurityContext.getEnte().getCodIpaEnte(), SessionVariables.ACTION_STATISTICA_DETTAGLIO_CRUSCOTTO);
			
				if(filtersMap != null) listaIUD = (List<String>) filtersMap.get(SessionVariables.STATISTICHE_LISTA_IUD);
			}				
			
			/* 
			 * Se la lista è vuota, Redirect alla pagina di "Messaggio" per segnalare l'errore.
			 */
			if(listaIUD == null || listaIUD.isEmpty()) return UtilitiesCtrl.getViewErrorMessage("mypivot.statistiche.errore.dettaglio");
			
			
			/**
			 * Instance Bean form di ricerca
			 */
			VisualizzaFlussoRicevutaCommand flussoRicevutaCommand = new VisualizzaFlussoRicevutaCommand();
			
			/**
			 * Prevalorizzo il bean di ricerca della form con gli ultimi valori dei filtri sottomessi e salvati in sessione.
			 */
			FlussiRicevutaViewFilter flussiRicevutaViewFilter = new FlussiRicevutaViewFilter();
			flussiRicevutaViewFilter.setFilters(request, SessionVariables.ACTION_STATISTICA_DETTAGLIO_CRUSCOTTO, pg, pgSize, null, null, null, null, null, null, null, null, null, null, null, null, null, flussoRicevutaCommand);
			flussoRicevutaCommand.setListaIUD(listaIUD);
			
			Map<String, Object> filtersMap = SecurityContext.getEnteViewFilters(SecurityContext.getEnte().getCodIpaEnte(), SessionVariables.ACTION_STATISTICA_DETTAGLIO_CRUSCOTTO);
			filtersMap.put(SessionVariables.STATISTICHE_LISTA_IUD, 			flussoRicevutaCommand.getListaIUD());
			SecurityContext.setEnteViewFilters(SecurityContext.getEnte().getCodIpaEnte(), SessionVariables.ACTION_STATISTICA_DETTAGLIO_CRUSCOTTO, filtersMap);

			/**
			 * Avvio la ricerca
			 */
			mav = ricerca(flussoRicevutaCommand, request);
			
		}catch(Exception e){
			LOG.error("DETTAGLIO CRUSCOTTO :: GET :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: ERRORE ", e);
			/* 
			 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
			 */
			return UtilitiesCtrl.getViewErrorMessage("mypivot.statistiche.errore.dettaglio");
		}
		
		LOG.debug("DETTAGLIO CRUSCOTTO :: GET :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: END");

		return mav;
	}
	
	/**
	 * Restituisce la view con l'elenco delle RT  (POST). 
	 * 
	 * @author Alesssandro Paolillo
	 */
	@RequestMapping(value = { "/visualizzaDettaglioCruscotto.html" }, method = RequestMethod.POST)
	public ModelAndView post(@ModelAttribute("visualizzaFlussoRicevutaCommand") VisualizzaFlussoRicevutaCommand flussoRicevutaCommand, HttpServletRequest request) {

		LOG.debug("DETTAGLIO CRUSCOTTO :: POST :: Fields[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: START");
	
		ModelAndView mav = new ModelAndView();
		
		try{
			/**
			 * E' stata avviata una nuova ricerca, con nuovi filtri, reimposto la pagina di partenza alla prima.
			 */
			flussoRicevutaCommand.setPage(1);
			/**
			 * Avvio la ricerca
			 */
			mav = ricerca(flussoRicevutaCommand, request);
			
		   // Salvo i nuovi filtri di ricerca impostati in sessione.
			if(SecurityContext.getEnte() != null){
				Map<String, Object> filtersMap = new HashMap<String, Object>();
					
					filtersMap.put(SessionVariables.STATISTICHE_LISTA_IUD, 			flussoRicevutaCommand.getListaIUD());
					filtersMap.put(SessionVariables.FRIC_PG,	  					flussoRicevutaCommand.getPage());
					filtersMap.put(SessionVariables.FRIC_PG_SIZE,				 	flussoRicevutaCommand.getPageSize());
					filtersMap.put(SessionVariables.FRIC_DATA_ESITO_CHECK,  		flussoRicevutaCommand.getDataEsitoSingoloPagamentoCheck());
					filtersMap.put(SessionVariables.FRIC_DATA_ESITO_DA, 			flussoRicevutaCommand.getDataEsitoSingoloPagamentoDa());
					filtersMap.put(SessionVariables.FRIC_DATA_ESITO_A, 				flussoRicevutaCommand.getDataEsitoSingoloPagamentoA());
					filtersMap.put(SessionVariables.FRIC_CODICE_IUV, 				flussoRicevutaCommand.getIuv());
					filtersMap.put(SessionVariables.FRIC_CODICE_IUF, 				flussoRicevutaCommand.getIuf());
					filtersMap.put(SessionVariables.FRIC_DENOMINAZIONE_ATTESTANTE, 	flussoRicevutaCommand.getDenominazioneAttestante());
					filtersMap.put(SessionVariables.FRIC_ID_UNIVOCO_RISCOSSIONE, 	flussoRicevutaCommand.getIdentificativoUnivocoRiscossione());
					filtersMap.put(SessionVariables.FRIC_COD_ID_UNIVOCO_PAGATORE, 	flussoRicevutaCommand.getCodiceIdentificativoUnivocoPagatore());
					filtersMap.put(SessionVariables.FRIC_ANAGRAFICA_PAGATORE,     	flussoRicevutaCommand.getAnagraficaPagatore());
					filtersMap.put(SessionVariables.FRIC_COD_ID_UNIVOCO_VERSANTE, 	flussoRicevutaCommand.getCodiceIdentificativoUnivocoVersante());
					filtersMap.put(SessionVariables.FRIC_ANAGRAFICA_VERSANTE,     	flussoRicevutaCommand.getAnagraficaVersante());
					
				SecurityContext.setEnteViewFilters(SecurityContext.getEnte().getCodIpaEnte(), SessionVariables.ACTION_STATISTICA_DETTAGLIO_CRUSCOTTO, filtersMap);
			}
		   // =======================================================
			
		}catch(Exception e){
			LOG.error("DETTAGLIO CRUSCOTTO :: POST :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: ERRORE ", e);
			/* 
			 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
			 */
			return UtilitiesCtrl.getViewErrorMessage("mypivot.statistiche.errore.dettaglio");
		}
		
		LOG.debug("DETTAGLIO CRUSCOTTO :: POST :: Fields[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: END");

		return mav;
	}
	
	/**
	 * Funzione che gestisce le fasi comuni della ricerca.
	 * -----------------------------------------------------------------------------------------------------------------------------------
	 * Prima di erogare la pagina effettuo dei controlli per determinare se sussistono le condizioni tali perchè l'utente possa accedere
	 * alla funzionalità.
	 * 
	 * I controlli eseguiti sono i seguenti:
	 *   1. L'utente deve aver selezionato l'ente beneficiario, se mancante verrà rediretto alla pagina di scelta dell'ente.
	 *   2. L'utente deve avere ruoli attivi, se mancante verrà rediretto alla pagina di scelta dell'ente.
	 *   3. L'utente deve avere uno dei ruoli utili ad accedere alla funzionalità delle "Statistiche" (ROLE_VISUAL e uno tra ROLE_ADMIN e/o ROLE_STATS).
	 *   4. L'utente deve essere operatore (attivo) per almeno un tipo dovuto dell'ente, se mancante mostro un messaggio di errore (Es: Utente non autorizzato)
	 *
	 * A seguito dei controlli, istanzio il bean che mappa i filtri di ricerca ed eventulamente lo prevalorizzo con i filtri che ho salvato in sessione 
	 * dall'ultima ricerca avviata.
	 * 
	 * Avvio la ricerca.
	 * 
	 * @param {@link HttpServletRequest}            request
	 * @param {@link VisualizzaFlussoRicevutaCommand} flussoRicevutaCommand, Filtri di ricerca
	 * 
	 * @return {@link ModelAndView}
	 * @author Alessandro Paolillo
	 */
	private ModelAndView ricerca(VisualizzaFlussoRicevutaCommand flussoRicevutaCommand, HttpServletRequest request) {

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
			 * La funzione verifica che l'utente possa accedere alla funzionalità.
			 */
			if (!SecurityContext.getRoles().contains(Constants.RUOLO_VISUALIZZATORE) && !UtilitiesCtrl.hasSecurityAccessFunctionalityCruscottoIncassi()) 
				return UtilitiesCtrl.getViewErrorMessage("mypivot.messages.error.notAuthorized"); 

			/**
			 * Recupero la lista dei tipi dovuto dell'ente per cui l'utente è un operatore attivo. 
			 * Se la lista è vuota, mostro un messaggio di errore perchè vuol dire che l'utente non può ne consultare ne creare accertamenti.
			 */
			List<EnteTipoDovutoTO> activeOperatoreEnteTdAsObj = operatoreEnteTipoDovutoService.getListaEnteTipoDovutoForOperatoreAndCodIpaEnte(SecurityContext.getUtente().getCodFedUserId(), SecurityContext.getEnte().getCodIpaEnte());
			
			if (activeOperatoreEnteTdAsObj == null || activeOperatoreEnteTdAsObj.isEmpty()) {
				LOG.warn("DETTAGLIO CRUSCOTTO :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: NON risulta OPERATORE per nessun tipo dovuto.");
				/* 
				 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
				 */
				return UtilitiesCtrl.getViewErrorMessage("mypivot.messages.error.nessunTipoDovutoAssegnato");
			}
			
			 
//			List<EnteTipoDovutoTO> enteTipoDovutos = operatoreEnteTipoDovutoService
//					.getListaEnteTipoDovutoForOperatoreAndCodIpaEnte(SecurityContext.getUtente().getCodFedUserId(), SecurityContext.getEnte().getCodIpaEnte());

			
			FlussiRicevutaViewFilter flussiRicevutaViewFilter = new FlussiRicevutaViewFilter();
			/**
			 * Inizializzo il bean per la ricerca
			 */
			flussiRicevutaViewFilter.initialize(flussoRicevutaCommand);
			
			Date dt_data_esito_singolo_pagamento_da = null;

			if (flussoRicevutaCommand.getDataEsitoSingoloPagamentoCheck() != null
					&& flussoRicevutaCommand.getDataEsitoSingoloPagamentoCheck()
					&& StringUtils.hasText(flussoRicevutaCommand.getDataEsitoSingoloPagamentoDa())) {
				try {
					dt_data_esito_singolo_pagamento_da = Constants.DDMMYYYY.parse(flussoRicevutaCommand.getDataEsitoSingoloPagamentoDa());
				} catch (ParseException e) {
					// Nothing to do
				}
			}

			Date dt_data_esito_singolo_pagamento_a = null;

			if (flussoRicevutaCommand.getDataEsitoSingoloPagamentoCheck() != null
					&& flussoRicevutaCommand.getDataEsitoSingoloPagamentoCheck()
					&& StringUtils.hasText(flussoRicevutaCommand.getDataEsitoSingoloPagamentoA())) {
				try {
					dt_data_esito_singolo_pagamento_a = Constants.DDMMYYYY.parse(flussoRicevutaCommand.getDataEsitoSingoloPagamentoA());
				} catch (ParseException e) {
					// Nothing to do
				}
			}
			
			Sort ricevutaSort = getRicevutaSort();
			
			/**
			 * Eseguo la query di ricerca
			 */
			PageDto<FlussoRicevutaDto> flussoRicevutaDtoPage = flussoExportService.getFlussoExportPage(
							SecurityContext.getEnte().getCodIpaEnte(), SecurityContext.getUtente().getCodFedUserId(), 
							dt_data_esito_singolo_pagamento_da, dt_data_esito_singolo_pagamento_a,
							flussoRicevutaCommand.getListaIUD(), 
							flussoRicevutaCommand.getIuv(),
							flussoRicevutaCommand.getDenominazioneAttestante(),
							flussoRicevutaCommand.getIdentificativoUnivocoRiscossione(),
							flussoRicevutaCommand.getCodiceIdentificativoUnivocoPagatore(),
							flussoRicevutaCommand.getAnagraficaPagatore(),
							flussoRicevutaCommand.getCodiceIdentificativoUnivocoVersante(),
							flussoRicevutaCommand.getAnagraficaVersante(),
							flussoRicevutaCommand.getCodTipoDovuto(), 
							flussoRicevutaCommand.getPage(), flussoRicevutaCommand.getPageSize(), ricevutaSort);
			
			/**
			 * Set param da ritornare alla view
			 */
			mav.addObject("flussoRicevutaDtoPage", flussoRicevutaDtoPage);
//			/* */
//			mav.addObject("listaTipiDovutoPerEnte", enteTipoDovutos);
			/* */
			mav.addObject(flussoRicevutaCommand);
			/* */
			mav.addObject("forceCruscotto", true);

			/* */
			mav.setViewName("visualizzaFlussoRicevuta");
			
		}catch(Exception e){
			LOG.error("DETTAGLIO CRUSCOTTO :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: ERRORE", e);
			/* 
			 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
			 */
			return UtilitiesCtrl.getViewErrorMessage("mypivot.statistiche.errore.dettaglio");
		}
		
		/* */
		mav.addObject("messagesDto", messagesDto);
		
		return mav;
	}
	
	private Sort getRicevutaSort() {
		return new Sort(new Order(Direction.DESC, "dtEDatiPagDatiSingPagDataEsitoSingoloPagamento"),
				new Order(Direction.ASC, "id.codRpSilinviarpIdUnivocoVersamento"), new Order(Direction.ASC, "codIud"));
	}
}