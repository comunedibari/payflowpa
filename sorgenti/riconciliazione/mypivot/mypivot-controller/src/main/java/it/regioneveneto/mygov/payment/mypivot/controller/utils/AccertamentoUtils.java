package it.regioneveneto.mygov.payment.mypivot.controller.utils;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import it.regioneveneto.mygov.payment.mypivot.controller.command.AccertamentiVisualizzaCommand;
import it.regioneveneto.mygov.payment.mypivot.controller.command.AccertamentoVisualizzaPagamentiCommand;
import it.regioneveneto.mygov.payment.mypivot.controller.command.AccertamentoVisualizzaPagamentiCommand.OPERATION;
import it.regioneveneto.mygov.payment.mypivot.utils.Constants;
import it.regioneveneto.mygov.payment.mypivot.utils.SecurityContext;
import it.regioneveneto.mygov.payment.mypivot.utils.SessionVariables;

/**
 * 
 * @author Marianna Memoli
 *
 */
public class AccertamentoUtils {

	private static Log logger = LogFactory.getLog(AccertamentoUtils.class);

	/**
	 * La funzione verifica che l'utente possa accedere alla funzionalità di "Accertamento".
	 * La funzione di accertamento è accessibile:
	 *  - solo per gli utenti che hanno ruolo ROLE_ADMIN e/o ROLE_ACC.
	 *  
	 * @return {@link Boolean}
	 * 
	 * @author Marianna Memoli
	 */
	public static boolean hasSecurityAccessFunctionality() throws Exception {
		
		logger.debug("VERIFICA AUTORIZZAZIONE FUNC ACCERTAMENTO :: ContextFields[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: START");

		/**
		 * Dal context leggo i dati dei ruoli
		 */
		List<String> roles = SecurityContext.getRoles();	/* Get ruoli dell'utente autenticato per ente selezionato come beneficiario */
		
		/**
		 */
		if (!(roles.contains(Constants.RUOLO_AMMINISTRATORE) || roles.contains(Constants.RUOLO_ACCERTAMENTO))) {
			logger.warn("VERIFICA AUTORIZZAZIONE FUNC ACCERTAMENTO :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + ", Ruoli: " + StringUtils.collectionToCommaDelimitedString(roles) + "] :: L'utente NON È AUTORIZZATO ad accedere alla funzionalità.");
			/**
			 */
			return false;
		}
		
		logger.debug("VERIFICA AUTORIZZAZIONE FUNC ACCERTAMENTO :: ContextFields[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: END");

		return true;
	}
	
	/**
	 * La funzione valorizza il bean di ricerca della form "Visualizza Accertamenti" con gli ultimi valori dei filtri sottomessi e salvati.
	 * 
	 * @param {@link HttpServletRequest} 			request
	 * @param {@link String}  						codIpaEnte,  	  	Codice Ipa dell'ente selezionato
	 * @param {@link String}  						codTipoDovuto,  	Codice tipo dovuto
	 * @param {@link String}  						codStato, 		  	Codice dello stato 
	 * @param {@link String}  						nomeAccertamento,   Testo digitato contenuto nella descrizione dell'accertamento
	 * @param {@link String}  						dataUltimoAggCheck, Individua se è attivo o meno il filtro sulla data ultimo aggiornamento
	 * @param {@link String}  						dataUltimoAggDa, 	Data ultimo aggiornamento dal (formato dd/MM/yyyy)
	 * @param {@link String}  						dataUltimoAggA, 	Data ultimo aggiornamento al  (formato dd/MM/yyyy)
	 * @param {@link String} 						codiceIuv, 		  	Identificativo univoco versamento
	 * @param {@link String}  						page, 			  	Numero pagina
	 * @param {@link String}  						pageSize, 	      	Elementi per pagina
	 * @param {@link AccertamentiVisualizzaCommand} visualizzaAccertamentiCommand
	 */
	public static void setSessionFilterAccertamentoToBean(
			HttpServletRequest request, String codIpaEnte, String codTipoDovuto, String codStato, String nomeAccertamento, Boolean dataUltimoAggCheck, 
			String dataUltimoAggDa, String dataUltimoAggA, String codiceIuv, String page, String pageSize, AccertamentiVisualizzaCommand visualizzaAccertamentiCommand) {
		try{
			/** 
			 * Recupero dalla sessione l'oggetto in cui ho salvato i filtri della ricerca accertamento.
			 */
			Map<String, Object> filtersMap = SecurityContext.getEnteViewFilters(codIpaEnte, SessionVariables.ACTION_ACCERTAMENTO_RICERCA);
			
			if (filtersMap == null) filtersMap = new HashMap<String, Object>();
			
		 /** ============================================== CODICE TIPO DOVUTO ========================================== */
			try {
				Object session_pr = filtersMap == null ? null : filtersMap.get(SessionVariables.ACCERTAMENTO_CODICE_TIPO_DOVUTO);
				if (codTipoDovuto == null)
				   visualizzaAccertamentiCommand.setCodTipoDovuto((String) session_pr);
				else
				   visualizzaAccertamentiCommand.setCodTipoDovuto(codTipoDovuto);
			} catch (Exception e) {
				visualizzaAccertamentiCommand.setCodTipoDovuto("");
			}
			filtersMap.put(SessionVariables.ACCERTAMENTO_CODICE_TIPO_DOVUTO, visualizzaAccertamentiCommand.getCodTipoDovuto());
		
		 /** ============================================== CODICE STATO ========================================== */
			try {
				Object session_pr = filtersMap == null ? null : filtersMap.get(SessionVariables.ACCERTAMENTO_CODICE_STATO);
				if (codStato == null)
				   visualizzaAccertamentiCommand.setCodStato((String) session_pr);
				else
				   visualizzaAccertamentiCommand.setCodStato(codStato);
			} catch (Exception e) {
				visualizzaAccertamentiCommand.setCodStato("");
			}
			filtersMap.put(SessionVariables.ACCERTAMENTO_CODICE_STATO, visualizzaAccertamentiCommand.getCodStato());
		
		 /** ============================================== NOME ACCERTAMENTO ========================================== */
			try {
				Object session_pr = filtersMap == null ? null : filtersMap.get(SessionVariables.ACCERTAMENTO_NOME_ACCERTAMENTO);
				if (nomeAccertamento == null)
				   visualizzaAccertamentiCommand.setNomeAccertamento((String) session_pr);
				else
				   visualizzaAccertamentiCommand.setNomeAccertamento(nomeAccertamento);
			} catch (Exception e) {
				visualizzaAccertamentiCommand.setNomeAccertamento("");
			}
			filtersMap.put(SessionVariables.ACCERTAMENTO_NOME_ACCERTAMENTO, visualizzaAccertamentiCommand.getNomeAccertamento());
			
	 	 /** ===================================== CHECKBOX DATA ULTIMO AGGIORNAMENTO ==================================== */
			try {
				Object session_pr = filtersMap == null ? null : filtersMap.get(SessionVariables.ACCERTAMENTO_CHECK_DATA_AGG);
				if (dataUltimoAggCheck == null)
				   visualizzaAccertamentiCommand.setDataUltimoAggCheck((Boolean) session_pr);
				else
				   visualizzaAccertamentiCommand.setDataUltimoAggCheck(dataUltimoAggCheck);
			} catch (Exception e) {
				visualizzaAccertamentiCommand.setDataUltimoAggCheck(false);
			}
			filtersMap.put(SessionVariables.ACCERTAMENTO_CHECK_DATA_AGG, visualizzaAccertamentiCommand.getDataUltimoAggCheck());
		 
		 /** ===================================== DATA ULTIMO AGGIORNAMENTO DA ================================= */
			try {
				Object session_pr = filtersMap == null ? null : filtersMap.get(SessionVariables.ACCERTAMENTO_DATA_AGG_DAL);
				if (dataUltimoAggDa == null)
				   visualizzaAccertamentiCommand.setDataUltimoAggDa((String) session_pr);
				else
				   visualizzaAccertamentiCommand.setDataUltimoAggDa(dataUltimoAggDa);
			} catch (Exception e) {
				visualizzaAccertamentiCommand.setDataUltimoAggDa("");
			}
			filtersMap.put(SessionVariables.ACCERTAMENTO_DATA_AGG_DAL, visualizzaAccertamentiCommand.getDataUltimoAggDa());
			
		 /** ===================================== DATA ULTIMO AGGIORNAMENTO AL ================================= */
			try {
				Object session_pr = filtersMap == null ? null : filtersMap.get(SessionVariables.ACCERTAMENTO_DATA_AGG_AL);
				if (dataUltimoAggA == null)
				   visualizzaAccertamentiCommand.setDataUltimoAggA((String) session_pr);
				else
				   visualizzaAccertamentiCommand.setDataUltimoAggA(dataUltimoAggA);
			} catch (Exception e) {
				visualizzaAccertamentiCommand.setDataUltimoAggA("");
			}
			filtersMap.put(SessionVariables.ACCERTAMENTO_DATA_AGG_AL, visualizzaAccertamentiCommand.getDataUltimoAggA());
			
		 /** ===================================== CODICE IUV ================================= */
			try {
				Object session_pr = filtersMap == null ? null : filtersMap.get(SessionVariables.ACCERTAMENTO_CODICE_IUV);
				if (codiceIuv == null)
				   visualizzaAccertamentiCommand.setCodiceIuv((String) session_pr);
				else
				   visualizzaAccertamentiCommand.setCodiceIuv(codiceIuv);
			} catch (Exception e) {
				visualizzaAccertamentiCommand.setCodiceIuv("");
			}
			filtersMap.put(SessionVariables.ACCERTAMENTO_CODICE_IUV, visualizzaAccertamentiCommand.getCodiceIuv());
			
		/** ===================================== NUMERO PAGINA ================================= */
			try {
				Object session_pr = filtersMap == null ? null : filtersMap.get(SessionVariables.ACCERTAMENTO_PAGE);
				if (page == null)
				   visualizzaAccertamentiCommand.setPage(Integer.parseInt(session_pr.toString()));
				else
				   visualizzaAccertamentiCommand.setPage(Integer.parseInt(page));
			} catch (Exception e) {
				visualizzaAccertamentiCommand.setPage(1);
			}
			filtersMap.put(SessionVariables.ACCERTAMENTO_PAGE, visualizzaAccertamentiCommand.getPage());
			
		 /** ===================================== ELEMENTI PER PAGINA ================================= */
			try {
				Object session_pr = filtersMap == null ? null : filtersMap.get(SessionVariables.ACCERTAMENTO_PAGE_SIZE);
				if (pageSize == null)
				   visualizzaAccertamentiCommand.setPageSize(Integer.parseInt(session_pr.toString()));
				else
				   visualizzaAccertamentiCommand.setPageSize(Integer.parseInt(pageSize));
			} catch (Exception e) {
				visualizzaAccertamentiCommand.setPageSize(5);
			}
			filtersMap.put(SessionVariables.ACCERTAMENTO_PAGE_SIZE, visualizzaAccertamentiCommand.getPageSize());
		
			/** 
			 * Rimetto in sessione l'oggetto in cui ho salvato i filtri della ricerca accertamento.
			 */
			SecurityContext.setEnteViewFilters(codIpaEnte, SessionVariables.ACTION_ACCERTAMENTO_RICERCA, filtersMap);
			
		}catch(Exception e){
			logger.warn("SET SESSION FILTER :: ACCERTAMENTI :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: ERRORE ", e);
		}
	}
	
	/**
	 * Inizializza il bean di ricerca che mappa la form "Visualizza Accertamenti".
	 * 
	 * @param {@link AccertamentiVisualizzaCommand} visualizzaAccertamentiCommand
	 */
	public static void initializeFilterAccertamento(AccertamentiVisualizzaCommand visualizzaAccertamentiCommand) {
		/*
		 * Se la proprietà è nulla, la inizializzo a stringa vuota. La stringa vuota individua l'opzione di filtro ALL(tutti i tipi dovuto) 
		 */
		if(visualizzaAccertamentiCommand.getCodTipoDovuto() == null) visualizzaAccertamentiCommand.setCodTipoDovuto(""); 
		
		/*
		 * Se la proprietà è nulla, la inizializzo a stringa vuota. La stringa vuota individua l'opzione di filtro ALL(tutte le tipologie di stato) 
		 */
		if(visualizzaAccertamentiCommand.getCodStato() == null) visualizzaAccertamentiCommand.setCodStato("");

		Date data_ultimo_aggiornamento_da = null;

		if (StringUtils.hasText(visualizzaAccertamentiCommand.getDataUltimoAggDa())) {
			try {
				data_ultimo_aggiornamento_da = Constants.DDMMYYYY.parse(visualizzaAccertamentiCommand.getDataUltimoAggDa());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date data_ultimo_aggiornamento_a = null;

		if (StringUtils.hasText(visualizzaAccertamentiCommand.getDataUltimoAggA())) {
			try {
				data_ultimo_aggiornamento_a = Constants.DDMMYYYY.parse(visualizzaAccertamentiCommand.getDataUltimoAggA());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		if ((data_ultimo_aggiornamento_da == null) && (data_ultimo_aggiornamento_a == null)) {
			data_ultimo_aggiornamento_a  = new Date();
			data_ultimo_aggiornamento_a  = DateUtils.setHours(data_ultimo_aggiornamento_a, 0);
			data_ultimo_aggiornamento_a  = DateUtils.setMinutes(data_ultimo_aggiornamento_a, 0);
			data_ultimo_aggiornamento_a  = DateUtils.setSeconds(data_ultimo_aggiornamento_a, 0);
			data_ultimo_aggiornamento_a  = DateUtils.setMilliseconds(data_ultimo_aggiornamento_a, 0);
			data_ultimo_aggiornamento_da = DateUtils.addDays(data_ultimo_aggiornamento_a, -10);
		}else 
			if ((data_ultimo_aggiornamento_da != null) && (data_ultimo_aggiornamento_a == null)) {
				data_ultimo_aggiornamento_da = DateUtils.setHours(data_ultimo_aggiornamento_da, 0);
				data_ultimo_aggiornamento_da = DateUtils.setMinutes(data_ultimo_aggiornamento_da, 0);
				data_ultimo_aggiornamento_da = DateUtils.setSeconds(data_ultimo_aggiornamento_da, 0);
				data_ultimo_aggiornamento_da = DateUtils.setMilliseconds(data_ultimo_aggiornamento_da, 0);
				data_ultimo_aggiornamento_a  = DateUtils.addDays(data_ultimo_aggiornamento_da, 10);
			} 
		else 
			if ((data_ultimo_aggiornamento_da == null) && (data_ultimo_aggiornamento_a != null)) {
				data_ultimo_aggiornamento_a  = DateUtils.setHours(data_ultimo_aggiornamento_a, 0);
				data_ultimo_aggiornamento_a  = DateUtils.setMinutes(data_ultimo_aggiornamento_a, 0);
				data_ultimo_aggiornamento_a  = DateUtils.setSeconds(data_ultimo_aggiornamento_a, 0);
				data_ultimo_aggiornamento_a  = DateUtils.setMilliseconds(data_ultimo_aggiornamento_a, 0);
				data_ultimo_aggiornamento_da = DateUtils.addDays(data_ultimo_aggiornamento_a, -10);
			}

		visualizzaAccertamentiCommand.setDataUltimoAggDa(Constants.DDMMYYYY.format(data_ultimo_aggiornamento_da));
		visualizzaAccertamentiCommand.setDataUltimoAggA(Constants.DDMMYYYY.format(data_ultimo_aggiornamento_a));
	}
	
	/**
	 * La funzione valorizza il bean di ricerca della form delle RT con gli ultimi valori dei filtri sottomessi e salvati.
	 * 
	 * @param {@link String}  codIpaEnte, 						   Codice ipa dell'ente
	 * @param {@link String}  codTipoDovuto, 					   Codice del tipo dovuto
	 * @param {@link String}  codiceIdentificativoUnivocoPagatore, CF/PIVA Pagatore
	 * @param {@link String}  codiceIud, 						   Identificativo univoco del dovuto
	 * @param {@link String}  codiceIuv, 						   Identificativo univoco versamento
	 * @param {@link Boolean} dataUltimoAggiornamentoCheck, 	   Individua se è attivo o meno il filtro sulla data ultimo aggiornamento.
	 * @param {@link String}  dataUltimoAggiornamentoDal, 		   Data ultimo aggiornamento dal  (formato DD/MM/YYYY)
	 * @param {@link String}  dataUltimoAggiornamentoAl,		   Data ultimo aggiornamento al  (formato DD/MM/YYYY)	
	 * @param {@link Boolean} dataEsitoSingoloPagamentoCheck, 	   Individua se è attivo o meno il filtro sulla data esito pagamento
	 * @param {@link String}  dataEsitoSingoloPagamentoDal, 	   Data esito pagamento dal  (formato DD/MM/YYYY)
	 * @param {@link String}  dataEsitoSingoloPagamentoAl, 		   Data esito pagamento al  (formato DD/MM/YYYY)
	 * @param {@link String}  page, 			 				   Numero pagina
	 * @param {@link String}  pageSize, 	     				   Elementi per pagina
	 * @param {@link String}  keySession, 	     			  	   Nome della chiave usata per salvare i filtri di ricerca in sessione
	 * @param {@link HttpServletRequest} request
	 * @param {@link AccertamentoVisualizzaPagamentiCommand} pagamentiAccertamentoCommand
	 * 
	 * @author Marianna Memoli
	 */
	public static void setSessionFilterPagamentiToBean(HttpServletRequest request, 
			String codIpaEnte, String codTipoDovuto, String codiceIdentificativoUnivocoPagatore, String codiceIud, String codiceIuv, 
			Boolean dataUltimoAggiornamentoCheck, String dataUltimoAggiornamentoDal, String dataUltimoAggiornamentoAl, 
			Boolean dataEsitoSingoloPagamentoCheck, String dataEsitoSingoloPagamentoDal, String dataEsitoSingoloPagamentoAl, 
			String page, String pageSize, String keySession, AccertamentoVisualizzaPagamentiCommand pagamentiAccertamentoCommand) {
		try{
			/** 
			 * Recupero dalla sessione l'oggetto in cui ho salvato i filtri della ricerca accertamento.
			 */
			Map<String, Object> filtersMap = SecurityContext.getEnteViewFilters(codIpaEnte, keySession);
			
			if (filtersMap == null) filtersMap = new HashMap<String, Object>();
			
		 /** ================================================= CODICE TIPO DOVUTO ============================================= */
			try {
				Object session_pr = filtersMap == null ? null : filtersMap.get(SessionVariables.ACCERTAMENTO_CODICE_TIPO_DOVUTO);
				if (codTipoDovuto == null)
					pagamentiAccertamentoCommand.setCod_tipo_dovuto((String) session_pr);
				else
					pagamentiAccertamentoCommand.setCod_tipo_dovuto(codTipoDovuto);
			} catch (Exception e) {
				pagamentiAccertamentoCommand.setCod_tipo_dovuto("");
			}
			filtersMap.put(SessionVariables.ACCERTAMENTO_CODICE_TIPO_DOVUTO, pagamentiAccertamentoCommand.getCod_tipo_dovuto());
		
		 /** ===================================== CODICE IDENTIFICATIVO UNIVOCO PAGATORE =================================== */
			try {
				Object session_pr = filtersMap == null ? null : filtersMap.get(SessionVariables.ACCERTAMENTO_COD_IDENT_UNIVOCO_PAGATORE);
				if (codiceIdentificativoUnivocoPagatore == null)
					pagamentiAccertamentoCommand.setCodice_identificativo_univoco_pagatore((String) session_pr);
				else
					pagamentiAccertamentoCommand.setCodice_identificativo_univoco_pagatore(codiceIdentificativoUnivocoPagatore);
			} catch (Exception e) {
				pagamentiAccertamentoCommand.setCodice_identificativo_univoco_pagatore("");
			}
			filtersMap.put(SessionVariables.ACCERTAMENTO_COD_IDENT_UNIVOCO_PAGATORE, pagamentiAccertamentoCommand.getCodice_identificativo_univoco_pagatore());
			
		/** ================================================= CODICE IUD ============================================= */
			try {
				Object session_pr = filtersMap == null ? null : filtersMap.get(SessionVariables.ACCERTAMENTO_CODICE_IUD);
				if (codiceIud == null)
					pagamentiAccertamentoCommand.setCodice_iud((String) session_pr);
				else
					pagamentiAccertamentoCommand.setCodice_iud(codiceIud);
			} catch (Exception e) {
				pagamentiAccertamentoCommand.setCodice_iud("");
			}
			filtersMap.put(SessionVariables.ACCERTAMENTO_CODICE_IUD, pagamentiAccertamentoCommand.getCodice_iud());
			
		/** ================================================= CODICE IUV ============================================= */
			try {
				Object session_pr = filtersMap == null ? null : filtersMap.get(SessionVariables.ACCERTAMENTO_CODICE_IUV);
				if (codiceIuv == null)
					pagamentiAccertamentoCommand.setCodice_iuv((String) session_pr);
				else
					pagamentiAccertamentoCommand.setCodice_iuv(codiceIuv);
			} catch (Exception e) {
				pagamentiAccertamentoCommand.setCodice_iuv("");
			}
			filtersMap.put(SessionVariables.ACCERTAMENTO_CODICE_IUV, pagamentiAccertamentoCommand.getCodice_iuv());
			
	 	/** ======================================= CHECKBOX DATA ULTIMO AGGIORNAMENTO =================================== */
			try {
				Object session_pr = filtersMap == null ? null : filtersMap.get(SessionVariables.ACCERTAMENTO_CHECK_DATA_AGG);
				if (dataUltimoAggiornamentoCheck == null)
					pagamentiAccertamentoCommand.setData_ultimo_aggiornamento_check((Boolean) session_pr);
				else
					pagamentiAccertamentoCommand.setData_ultimo_aggiornamento_check(dataUltimoAggiornamentoCheck);
			} catch (Exception e) {
				pagamentiAccertamentoCommand.setData_ultimo_aggiornamento_check(false);
			}
			filtersMap.put(SessionVariables.ACCERTAMENTO_CHECK_DATA_AGG, pagamentiAccertamentoCommand.getData_ultimo_aggiornamento_check());
		 
		/** ======================================== DATA ULTIMO AGGIORNAMENTO DA ==================================== */
			try {
				Object session_pr = filtersMap == null ? null : filtersMap.get(SessionVariables.ACCERTAMENTO_DATA_AGG_DAL);
				if (dataUltimoAggiornamentoDal == null)
					pagamentiAccertamentoCommand.setData_ultimo_aggiornamento_da((String) session_pr);
				else
					pagamentiAccertamentoCommand.setData_ultimo_aggiornamento_da(dataUltimoAggiornamentoDal);
			} catch (Exception e) {
				pagamentiAccertamentoCommand.setData_ultimo_aggiornamento_da("");
			}
			filtersMap.put(SessionVariables.ACCERTAMENTO_DATA_AGG_DAL, pagamentiAccertamentoCommand.getData_ultimo_aggiornamento_da());
			
		/** ======================================== DATA ULTIMO AGGIORNAMENTO AL ==================================== */
			try {
				Object session_pr = filtersMap == null ? null : filtersMap.get(SessionVariables.ACCERTAMENTO_DATA_AGG_AL);
				if (dataUltimoAggiornamentoAl == null)
					pagamentiAccertamentoCommand.setData_ultimo_aggiornamento_a((String) session_pr);
				else
					pagamentiAccertamentoCommand.setData_ultimo_aggiornamento_a(dataUltimoAggiornamentoAl);
			} catch (Exception e) {
				pagamentiAccertamentoCommand.setData_ultimo_aggiornamento_a("");
			}
			filtersMap.put(SessionVariables.ACCERTAMENTO_DATA_AGG_AL, pagamentiAccertamentoCommand.getData_ultimo_aggiornamento_a());
			
		/** ======================================== CHECKBOX DATA ESITO SINGOLO PAGAMENTO ==================================== */
			try {
				Object session_pr = filtersMap == null ? null : filtersMap.get(SessionVariables.ACCERTAMENTO_CHECK_DATA_ESITO_SINGOLO_PAG);
				if (dataEsitoSingoloPagamentoCheck == null)
					pagamentiAccertamentoCommand.setData_esito_singolo_pagamento_check((Boolean) session_pr);
				else
					pagamentiAccertamentoCommand.setData_esito_singolo_pagamento_check(dataEsitoSingoloPagamentoCheck);
			} catch (Exception e) {
				pagamentiAccertamentoCommand.setData_esito_singolo_pagamento_check(false);
			}
			filtersMap.put(SessionVariables.ACCERTAMENTO_CHECK_DATA_ESITO_SINGOLO_PAG, pagamentiAccertamentoCommand.getData_esito_singolo_pagamento_check());
			
		/** ======================================== DATA ULTIMO ESITO SINGOLO PAGAMENTO DA ==================================== */
			try {
				Object session_pr = filtersMap == null ? null : filtersMap.get(SessionVariables.ACCERTAMENTO_DATA_ESITO_SINGOLO_PAG_DAL);
				if (dataEsitoSingoloPagamentoDal == null)
					pagamentiAccertamentoCommand.setData_esito_singolo_pagamento_da((String) session_pr);
				else
					pagamentiAccertamentoCommand.setData_esito_singolo_pagamento_da(dataEsitoSingoloPagamentoDal);
			} catch (Exception e) {
				pagamentiAccertamentoCommand.setData_esito_singolo_pagamento_da("");
			}
			filtersMap.put(SessionVariables.ACCERTAMENTO_DATA_ESITO_SINGOLO_PAG_DAL, pagamentiAccertamentoCommand.getData_esito_singolo_pagamento_da());
			
		/** ======================================== DATA ULTIMO ESITO SINGOLO PAGAMENTO AL ==================================== */
			try {
				Object session_pr = filtersMap == null ? null : filtersMap.get(SessionVariables.ACCERTAMENTO_DATA_ESITO_SINGOLO_PAG_AL);
				if (dataEsitoSingoloPagamentoAl == null)
					pagamentiAccertamentoCommand.setData_esito_singolo_pagamento_a((String) session_pr);
				else
					pagamentiAccertamentoCommand.setData_esito_singolo_pagamento_a(dataEsitoSingoloPagamentoAl);
			} catch (Exception e) {
				pagamentiAccertamentoCommand.setData_esito_singolo_pagamento_a("");
			}
			filtersMap.put(SessionVariables.ACCERTAMENTO_DATA_ESITO_SINGOLO_PAG_AL, pagamentiAccertamentoCommand.getData_esito_singolo_pagamento_a());
			
		/** ======================================== NUMERO PAGINA ==================================== */
			try {
				Object session_pr = filtersMap == null ? null : filtersMap.get(SessionVariables.ACCERTAMENTO_PAGE);
				if (page == null)
					pagamentiAccertamentoCommand.setPage(Integer.parseInt(session_pr.toString()));
				else
					pagamentiAccertamentoCommand.setPage(Integer.parseInt(page));
			} catch (Exception e) {
				pagamentiAccertamentoCommand.setPage(1);
			}
			filtersMap.put(SessionVariables.ACCERTAMENTO_PAGE, pagamentiAccertamentoCommand.getPage());
			
		/** ======================================== ELEMENTI PER PAGINA ==================================== */
			try {
				Object session_pr = filtersMap == null ? null : filtersMap.get(SessionVariables.ACCERTAMENTO_PAGE_SIZE);
				if (pageSize == null)
					pagamentiAccertamentoCommand.setPageSize(Integer.parseInt(session_pr.toString()));
				else
					pagamentiAccertamentoCommand.setPageSize(Integer.parseInt(pageSize));
			} catch (Exception e) {
				pagamentiAccertamentoCommand.setPageSize(5);
			}
			filtersMap.put(SessionVariables.ACCERTAMENTO_PAGE_SIZE, pagamentiAccertamentoCommand.getPageSize());
		
			/** 
			 * Rimetto in sessione l'oggetto in cui ho salvato i filtri della ricerca accertamento.
			 */
			SecurityContext.setEnteViewFilters(codIpaEnte, keySession, filtersMap);
			
		}catch(Exception e){
			logger.warn("setSessionFilterToBean :: ACCERTAMENTO :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: ERROR ", e);
		}
	}
	
	
	
	public static void setSessionFilterPagamentiToBean( String codFedUserId,
			String codIpaEnte, String codTipoDovuto, String codiceIdentificativoUnivocoPagatore, String codiceIud, String codiceIuv, 
			Boolean dataUltimoAggiornamentoCheck, String dataUltimoAggiornamentoDal, String dataUltimoAggiornamentoAl, 
			Boolean dataEsitoSingoloPagamentoCheck, String dataEsitoSingoloPagamentoDal, String dataEsitoSingoloPagamentoAl, 
			String page, String pageSize, AccertamentoVisualizzaPagamentiCommand pagamentiAccertamentoCommand) {
		try{

			
		 /** ================================================= CODICE TIPO DOVUTO ============================================= */

				if (codTipoDovuto == null)
					pagamentiAccertamentoCommand.setCod_tipo_dovuto("");
				else
					pagamentiAccertamentoCommand.setCod_tipo_dovuto(codTipoDovuto);
		
		 /** ===================================== CODICE IDENTIFICATIVO UNIVOCO PAGATORE =================================== */

				if (codiceIdentificativoUnivocoPagatore == null)
					pagamentiAccertamentoCommand.setCodice_identificativo_univoco_pagatore("");
				else
					pagamentiAccertamentoCommand.setCodice_identificativo_univoco_pagatore(codiceIdentificativoUnivocoPagatore);
			
			
		/** ================================================= CODICE IUD ============================================= */

				if (codiceIud == null)
					pagamentiAccertamentoCommand.setCodice_iud("");
				else
					pagamentiAccertamentoCommand.setCodice_iud(codiceIud);
				

			
		/** ================================================= CODICE IUV ============================================= */

				if (codiceIuv == null)
					pagamentiAccertamentoCommand.setCodice_iuv("");
				else
					pagamentiAccertamentoCommand.setCodice_iuv(codiceIuv);
				

	 	/** ======================================= CHECKBOX DATA ULTIMO AGGIORNAMENTO =================================== */


				if (dataUltimoAggiornamentoCheck == null)
					pagamentiAccertamentoCommand.setData_ultimo_aggiornamento_check(false);
				else
					pagamentiAccertamentoCommand.setData_ultimo_aggiornamento_check(dataUltimoAggiornamentoCheck);

		 
		/** ======================================== DATA ULTIMO AGGIORNAMENTO DA ==================================== */

				if (dataUltimoAggiornamentoDal == null)
					pagamentiAccertamentoCommand.setData_ultimo_aggiornamento_da("");
				else
					pagamentiAccertamentoCommand.setData_ultimo_aggiornamento_da(dataUltimoAggiornamentoDal);
				
			
		/** ======================================== DATA ULTIMO AGGIORNAMENTO AL ==================================== */

				if (dataUltimoAggiornamentoAl == null)
					pagamentiAccertamentoCommand.setData_ultimo_aggiornamento_a("");
				else
					pagamentiAccertamentoCommand.setData_ultimo_aggiornamento_a(dataUltimoAggiornamentoAl);
				
			
		/** ======================================== CHECKBOX DATA ESITO SINGOLO PAGAMENTO ==================================== */

				if (dataEsitoSingoloPagamentoCheck == null)
					pagamentiAccertamentoCommand.setData_esito_singolo_pagamento_check(false);
				else
					pagamentiAccertamentoCommand.setData_esito_singolo_pagamento_check(dataEsitoSingoloPagamentoCheck);
				

		/** ======================================== DATA ULTIMO ESITO SINGOLO PAGAMENTO DA ==================================== */


				if (dataEsitoSingoloPagamentoDal == null)
					pagamentiAccertamentoCommand.setData_esito_singolo_pagamento_da("");
				else
					pagamentiAccertamentoCommand.setData_esito_singolo_pagamento_da(dataEsitoSingoloPagamentoDal);


		/** ======================================== DATA ULTIMO ESITO SINGOLO PAGAMENTO AL ==================================== */


				if (dataEsitoSingoloPagamentoAl == null)
					pagamentiAccertamentoCommand.setData_esito_singolo_pagamento_a("");
				else
					pagamentiAccertamentoCommand.setData_esito_singolo_pagamento_a(dataEsitoSingoloPagamentoAl);


		/** ======================================== NUMERO PAGINA ==================================== */


				if (page == null)
					pagamentiAccertamentoCommand.setPage(1);
				else
					pagamentiAccertamentoCommand.setPage(Integer.parseInt(page));

			
		/** ======================================== ELEMENTI PER PAGINA ==================================== */


				if (pageSize == null)
					pagamentiAccertamentoCommand.setPageSize(5);
				else
					pagamentiAccertamentoCommand.setPageSize(Integer.parseInt(pageSize));

			
		}catch(Exception e){
			logger.warn("setSessionFilterToBean :: ACCERTAMENTO :: Utente[codFedUserId: " + codFedUserId + "] :: ERROR ", e);
		}
	}

	
	/**
	 * Inizializzo il bean che mappa la form di ricerca dei pagamenti
	 * 
	 * @param {@link AccertamentoVisualizzaPagamentiCommand} pagamentiAccertamentoCommand, 	Bean ricerca form
	 * @param {@link String} 						         accertamentoId, 				Identificativo dell'accertamento
	 * @param {@link String} 								 codTipoDovuto, 				Codice del tipo dovuto
	 * 
	 * @author Marianna Memoli
	 */
	public static AccertamentoVisualizzaPagamentiCommand initializeFilterPagamenti(AccertamentoVisualizzaPagamentiCommand pagamentiAccertamentoCommand, String codTipoDovuto, String accertamentoId) {
		
		/**
		 * Indentificativo accertamento
		 */
		pagamentiAccertamentoCommand.setAccertamentoId(accertamentoId);
		/**
		 * Codice tipo dovuto  		
		 */
		pagamentiAccertamentoCommand.setCod_tipo_dovuto(codTipoDovuto); 
		/**
		 * Svuoto la lista di ricerca
		 */
		pagamentiAccertamentoCommand.setResultList(null);
		/**
		 * Reset value operation, avvia la ricerca.
		 */
		pagamentiAccertamentoCommand.setOperation(OPERATION.RC);
		
	/** ========================================================================================================================== **/
		
		Date data_ultimo_aggiornamento_da = null;

		if (StringUtils.hasText(pagamentiAccertamentoCommand.getData_ultimo_aggiornamento_da())) {
			try {
				data_ultimo_aggiornamento_da = Constants.DDMMYYYY.parse(pagamentiAccertamentoCommand.getData_ultimo_aggiornamento_da());
			} catch (ParseException e) {
				// Nothing to do
			}
		}
 
		Date data_ultimo_aggiornamento_a = null;

		if (StringUtils.hasText(pagamentiAccertamentoCommand.getData_ultimo_aggiornamento_a())) {
			try {
				data_ultimo_aggiornamento_a = Constants.DDMMYYYY.parse(pagamentiAccertamentoCommand.getData_ultimo_aggiornamento_a());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		if ((data_ultimo_aggiornamento_da == null) && (data_ultimo_aggiornamento_a == null)) {
			data_ultimo_aggiornamento_a = new Date();
			data_ultimo_aggiornamento_a = DateUtils.setHours(data_ultimo_aggiornamento_a, 0);
			data_ultimo_aggiornamento_a = DateUtils.setMinutes(data_ultimo_aggiornamento_a, 0);
			data_ultimo_aggiornamento_a = DateUtils.setSeconds(data_ultimo_aggiornamento_a, 0);
			data_ultimo_aggiornamento_a = DateUtils.setMilliseconds(data_ultimo_aggiornamento_a, 0);
			data_ultimo_aggiornamento_da = DateUtils.addDays(data_ultimo_aggiornamento_a, -10);
		} else 
			if ((data_ultimo_aggiornamento_da != null) && (data_ultimo_aggiornamento_a == null)) {
				data_ultimo_aggiornamento_da = DateUtils.setHours(data_ultimo_aggiornamento_da, 0);
				data_ultimo_aggiornamento_da = DateUtils.setMinutes(data_ultimo_aggiornamento_da, 0);
				data_ultimo_aggiornamento_da = DateUtils.setSeconds(data_ultimo_aggiornamento_da, 0);
				data_ultimo_aggiornamento_da = DateUtils.setMilliseconds(data_ultimo_aggiornamento_da, 0);
				data_ultimo_aggiornamento_a = DateUtils.addDays(data_ultimo_aggiornamento_da, 10);
			} 
		else 
			if ((data_ultimo_aggiornamento_da == null) && (data_ultimo_aggiornamento_a != null)) {
				data_ultimo_aggiornamento_a = DateUtils.setHours(data_ultimo_aggiornamento_a, 0);
				data_ultimo_aggiornamento_a = DateUtils.setMinutes(data_ultimo_aggiornamento_a, 0);
				data_ultimo_aggiornamento_a = DateUtils.setSeconds(data_ultimo_aggiornamento_a, 0);
				data_ultimo_aggiornamento_a = DateUtils.setMilliseconds(data_ultimo_aggiornamento_a, 0);
				data_ultimo_aggiornamento_da = DateUtils.addDays(data_ultimo_aggiornamento_a, -10);
			}

		pagamentiAccertamentoCommand.setData_ultimo_aggiornamento_da(Constants.DDMMYYYY.format(data_ultimo_aggiornamento_da));
		pagamentiAccertamentoCommand.setData_ultimo_aggiornamento_a(Constants.DDMMYYYY.format(data_ultimo_aggiornamento_a));
		
	/** ========================================================================================================================== **/
		
		Date data_esito_singolo_pagamento_da = null;

		if (StringUtils.hasText(pagamentiAccertamentoCommand.getData_esito_singolo_pagamento_da())) {
			try {
				data_esito_singolo_pagamento_da = Constants.DDMMYYYY.parse(pagamentiAccertamentoCommand.getData_esito_singolo_pagamento_da());
			} catch (ParseException e) {
				// Nothing to do
			}
		}
		
		Date data_esito_singolo_pagamento_a = null;

		if (StringUtils.hasText(pagamentiAccertamentoCommand.getData_esito_singolo_pagamento_a())) {
			try {
				data_esito_singolo_pagamento_a = Constants.DDMMYYYY.parse(pagamentiAccertamentoCommand.getData_esito_singolo_pagamento_a());
			} catch (ParseException e) {
				// Nothing to do
			}
		}
		if ((data_esito_singolo_pagamento_da == null) && (data_esito_singolo_pagamento_a == null)) {
			data_esito_singolo_pagamento_a = new Date();
			data_esito_singolo_pagamento_a = DateUtils.setHours(data_esito_singolo_pagamento_a, 0);
			data_esito_singolo_pagamento_a = DateUtils.setMinutes(data_esito_singolo_pagamento_a, 0);
			data_esito_singolo_pagamento_a = DateUtils.setSeconds(data_esito_singolo_pagamento_a, 0);
			data_esito_singolo_pagamento_a = DateUtils.setMilliseconds(data_esito_singolo_pagamento_a, 0);
			data_esito_singolo_pagamento_da = DateUtils.addDays(data_esito_singolo_pagamento_a, -10);
		} else 
			if ((data_esito_singolo_pagamento_da != null) && (data_esito_singolo_pagamento_a == null)) {
				data_esito_singolo_pagamento_da = DateUtils.setHours(data_esito_singolo_pagamento_da, 0);
				data_esito_singolo_pagamento_da = DateUtils.setMinutes(data_esito_singolo_pagamento_da, 0);
				data_esito_singolo_pagamento_da = DateUtils.setSeconds(data_esito_singolo_pagamento_da, 0);
				data_esito_singolo_pagamento_da = DateUtils.setMilliseconds(data_esito_singolo_pagamento_da, 0);
				data_esito_singolo_pagamento_a = DateUtils.addDays(data_esito_singolo_pagamento_da, 10);
			} 
		else 
			if ((data_esito_singolo_pagamento_da == null) && (data_esito_singolo_pagamento_a != null)) {
				data_esito_singolo_pagamento_a = DateUtils.setHours(data_esito_singolo_pagamento_a, 0);
				data_esito_singolo_pagamento_a = DateUtils.setMinutes(data_esito_singolo_pagamento_a, 0);
				data_esito_singolo_pagamento_a = DateUtils.setSeconds(data_esito_singolo_pagamento_a, 0);
				data_esito_singolo_pagamento_a = DateUtils.setMilliseconds(data_esito_singolo_pagamento_a, 0);
				data_esito_singolo_pagamento_da = DateUtils.addDays(data_esito_singolo_pagamento_a, -10);
			}

		pagamentiAccertamentoCommand.setData_esito_singolo_pagamento_da(Constants.DDMMYYYY.format(data_esito_singolo_pagamento_da));
		pagamentiAccertamentoCommand.setData_esito_singolo_pagamento_a(Constants.DDMMYYYY.format(data_esito_singolo_pagamento_a));
		
	/** ========================================================================================================================== **/
		
		return pagamentiAccertamentoCommand;
	}
}