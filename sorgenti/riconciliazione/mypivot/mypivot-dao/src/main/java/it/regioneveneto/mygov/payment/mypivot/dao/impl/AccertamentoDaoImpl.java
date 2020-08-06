package it.regioneveneto.mygov.payment.mypivot.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import it.regioneveneto.mygov.payment.mypivot.dao.AccertamentoDao;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.PageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.po.Accertamento;
import it.regioneveneto.mygov.payment.mypivot.utils.Constants;

/**
 * 
 * @author Marianna Memoli
 * 
 */
@Repository
public class AccertamentoDaoImpl implements AccertamentoDao {

	private static final Logger logger = Logger.getLogger(AccertamentoDaoImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Recupera l'elenco di accertamenti, eventualmente filtrati.
	 * 
	 * Filtri di ricerca OBBLIGATORI:
	 * ------------------------------------------------------------------------------------------------------------------------------------------
	 * @param {@link String} 	   codIpaEnte,     Ente selezionato come beaneficiario
	 * @param {@link List<Long>}   utenteIDs,      Accertamenti creati dagli utenti presenti nella lista. Se l'utente autenticato ha ruolo:
	 * 												- ROLE_ACC:   visualizza solamente i propri accertamenti
	 * 												- ROLE_ADMIN: visualizza accertamenti effetuati da utenti operatori dell'ente e che hanno i 
	 * 															  permessi di operatori per gli stessi tipi dovuti dell'utente autenticato.
	 * @param {@link List<String>} tipiDovutoCODs, Elenco dei tipi dovuto sui quali l'utente autenticato ha i permessi di operatore
	 * 
	 * 
	 * Filtri di ricerca OPZIONALI:
	 * ------------------------------------------------------------------------------------------------------------------------------------------
	 * @param {@link String} 	 codTipoDovuto,    Codice tipo dovuto
	 * @param {@link String} 	 codStato,         Codice dello stato
	 * @param {@link String} 	 nomeAccertamento, Testo digitato contenuto nella descrizione dell'accertamento
	 * @param {@link String} 	 dataUltimoAggDa,  Data ultimo aggiornamento dal  (formato dd/MM/yyyy)
	 * @param {@link String} 	 dataUltimoAggA,   Data ultimo aggiornamento al  (formato dd/MM/yyyy)
	 * @param {@link String}     codiceIuv, 	   Identificativo univoco versamento
	 * 
	 * Paginazione:
	 * -----------------------------------------------------------------------------------------------------------------------------------------
	 * @param {@link Boolean}    hasPagination,    Determina se la query deve essere paginata, qualora la fosse diventa obbligatorio la
	 * 											   valorizzazione dei successivi due parametri.
	 * @param {@link String} 	 page, 			   Visualizza il punto di partenza nel set di dati corrente.
	 * @param {@link String} 	 pageSize, 		   Numero di record che la query deve selezione nella pagina corrente
	 * 
	 * @return {@link PageDto<Accertamento>}
	 * @throws Exception
	 * @author Marianna Memoli
 	 */
	@SuppressWarnings("unchecked")
	public PageDto<Accertamento> findByFilter(
		String codIpaEnte, List<Long> utenteIDs, List<String> tipiDovutoCODs, String codTipoDovuto, String codStato, String nomeAccertamento, 
		String dataUltimoAggDal, String dataUltimoAggAl, String codiceIuv, Boolean hasPagination, int page, int pageSize) throws Exception {
		try {
			logger.debug("DAO :: RICERCA :: ACCERTAMENTI :: PARAMETRI DI RICERCA[" + 
						 "codIpaEnte:" + codIpaEnte + ", utenteIDs:" + utenteIDs + ", tipiDovutoCODs:" + tipiDovutoCODs + ", " +
						 "codTipoDovuto:" + codTipoDovuto + ", codStato:" + codStato + ", nomeAccertamento:" + nomeAccertamento + ", " +
						 "dataUltimoAggDal:" + dataUltimoAggDal + ", dataUltimoAggAl:" + dataUltimoAggAl + ", codiceIuv:" + codiceIuv + ", " +
						 "hasPagination:" + hasPagination + ", page:" + page + ", pageSize:" + pageSize + "] :: START");
			
		/** ====================================================================================================== **/
		/* =									CHECK FILTRI OBBLIGATORI										  = */
		/** ====================================================================================================== **/
			
			/*
			 * Controllo se i filtri obbligatori sono valorizzati, se non lo sono lancio un eccezione per sospendere l'esecuzione della query.
			 */
			if (!StringUtils.hasText(codIpaEnte) || utenteIDs == null || utenteIDs.isEmpty() || tipiDovutoCODs == null || tipiDovutoCODs.isEmpty()) {
				/*
				 * Interrompo l'esecuzione 
				 */
				String errMsg = "Impossibile avviare la ricerca dei record dell'accertamento. Parametri obbligatori assenti o non corretti: " + 
								"codiceIpaEnte[" + codIpaEnte + "], " + 
								"utenteIDs[" + (utenteIDs == null ? utenteIDs : StringUtils.collectionToCommaDelimitedString(utenteIDs)) + "], " + 
								"tipiDovutoCODs[" + (tipiDovutoCODs == null ? tipiDovutoCODs : StringUtils.collectionToCommaDelimitedString(tipiDovutoCODs)) + "]";
				/* */
				logger.error(errMsg);
				/* */
				throw new Exception(errMsg);
			}
			
		/** ====================================================================================================== **/
		/* =									BUILD SQL E PARAM DELLA QUERY									  = */
		/** ====================================================================================================== **/
			
			/*
			 * Costruisce l'oggetto di ricerca (sql e parametri), l'sql restituito non presenta ordinamento delle colonne ne paginazione.
			 */
			Map<String, Object> maps = buildObjectToSearch(codIpaEnte, utenteIDs, tipiDovutoCODs, codTipoDovuto, codStato, nomeAccertamento, dataUltimoAggDal, dataUltimoAggAl, codiceIuv);
		
		/** ====================================================================================================== **/
		/* =									BUILD ORDER BY													  = */
		/** ====================================================================================================== **/
					
			String sqlOrderBy = " ORDER BY v_filter.dt_ultima_modifica DESC ";
		
		/** ====================================================================================================== **/
		/* =									BUILD SQL SELECT / COUNT										  = */
		/** ====================================================================================================== **/
				
			/* sql count */
			String sqlCountRow = "SELECT count(*) as count_row FROM ( " + maps.get("sql") + " ) AS v_filter";
				
			/* sql */
			String sql= "SELECT v_filter.* FROM ( " + maps.get("sql") + " ) AS v_filter " + sqlOrderBy;
		
		/** ====================================================================================================== **/
		/* =											EXECUTE QUERY											  = */
		/** ====================================================================================================== **/
			
			/* Instance return object */
			PageDto<Accertamento> ret = new PageDto<Accertamento>();
			
			/* Query di selezione */
			Query query = entityManager.createNativeQuery(sql, Accertamento.class);
			
			/* Query per la count */
			Query queryCount = entityManager.createNativeQuery(sqlCountRow);
			
			/*
			 * Associare i valori della mappa per ciascun parametro denominato nelle query
			 */
			for (Entry<String, Object> entry : ((Map<String, Object>) maps.get("param")).entrySet()) {
				query.setParameter(entry.getKey(), entry.getValue()); /* */ queryCount.setParameter(entry.getKey(), entry.getValue());
			}
		
			if(hasPagination){
				
				// count Entities
				Number total = (Number) queryCount.getSingleResult();
				
				// set count Entities
				ret.setTotalRecords(total.longValue());
				
				// adjust pagination
				int pageStart = 0; 		if (page > 0) pageStart = page - 1;
				
				/* pagina la query */
				query.setFirstResult(pageStart * pageSize);
				query.setMaxResults(pageSize);
					
				/* execute */
				ret.setList(query.getResultList());
			}else{
				
				/* execute */
				ret.setList(query.getResultList());
				
				//count Entities
				ret.setTotalRecords(new Long(ret.getList().size()));
			}
			
			Double totalPages = new Double(pageSize == 0 ? 1L : Math.ceil(ret.getTotalRecords() / (double) pageSize));
			boolean hasNextPage = page < totalPages;
			boolean hasPreviousPage = page > 1;
			
			ret.setNextPage(hasNextPage);
			ret.setPage(page);
			ret.setPageSize(pageSize);
			ret.setPreviousPage(hasPreviousPage);
			ret.setTotalPages(totalPages.intValue());
			
			logger.debug("DAO :: RICERCA :: ACCERTAMENTI :: END");
			
			return ret;
		}catch(Exception e) {
			logger.error("DAO :: RICERCA :: ACCERTAMENTI :: END", e);
			throw new Exception(e);
		}
	}
	
	/**
	 * Costruisce l'oggetto di ricerca (sql e parametri), l'sql restituito non presenta ordinamento delle colonne ne paginazione.
	 * 
	 * Fitri di ricerca OBBLIGATORI:
	 * ------------------------------------------------------------------------------------------------------------------------------------------
	 * @param {@link String} 	 codIpaEnte,       Ente selezionato come beaneficiario
	 * @param {@link List<Long>} utenteIDs,        Accertamenti creati dagli utenti presenti nella lista. Se l'utente autenticato ha ruolo:
	 * 												- ROLE_ACC:   visualizza solamente i propri accertamenti
	 * 												- ROLE_ADMIN: visualizza accertamenti effetuati da utenti operatori dell'ente e che hanno i 
	 * 															  permessi di operatori per gli stessi tipi dovuti dell' utente autenticato.
	 * @param {@link List<String>} tipiDovutoCODs, Elenco dei tipi dovuto sui quali l'utente autenticato ha i permessi di operatore
	 * 
	 * 
	 * Filtri di ricerca OPZIONALI:
	 * ------------------------------------------------------------------------------------------------------------------------------------------
	 * @param {@link String} 	 codTipoDovuto,    Codice tipo dovuto
	 * @param {@link String} 	 codStato,         Codice dello stato
	 * @param {@link String} 	 nomeAccertamento, Testo digitato contenuto nella descrizione dell'accertamento
	 * @param {@link String} 	 dataUltimoAggDal, Data ultimo aggiornamento dal  (formato dd/MM/yyyy)
	 * @param {@link String} 	 dataUltimoAggAl,  Data ultimo aggiornamento al  (formato dd/MM/yyyy)
	 * @param {@link String}     codiceIuv, 	   Identificativo univoco versamento
	 * 
	 * @return {@link Map<String, Object>}, una mappa con:
	 * 										  key "sql" e value la stringa sql di ricerca
	 * 										  key "param" e value la mappa dei parametri da usare come filtro nella query
	 * @throws Exception
	 * @author Marianna Memoli
	 */
	private Map<String, Object> buildObjectToSearch(
			String codIpaEnte, List<Long> utenteIDs, List<String> tipiDovutoCods, String codTipoDovuto, String codStato, String nomeAccertamento, 
			String dataUltimoAggDa, String dataUltimoAggA, String codiceIuv) throws Exception {
		try {
			/* 
			 * Init variabili
			 */
			String sql, sqlSelect, sqlTable, sqlRestriction = new String();
			
			/*
			 * Mappa alla quale aggiungere i valori dei filtri di ricerca
			 */
			Map<String, Object> mapValueRestriction = new HashMap<String, Object>();
			
		/** ====================================================================================================== **/
		/* =												BUILD QUERY 										  = */
		/** ====================================================================================================== **/
			
			/*
			 * Costruisce l'sql della select per la selezione delle colonne da ritornare come risultato della query.
			 */
			sqlSelect = buildSqlSelect();
			/*
			 * Aggiunge i filtri di ricerca alla query e alla mappa i valori per cui ricercare. 
			 */
			sqlRestriction = buildSqlRestriction(codIpaEnte, utenteIDs, tipiDovutoCods, codTipoDovuto, codStato, nomeAccertamento, dataUltimoAggDa, dataUltimoAggA, codiceIuv, mapValueRestriction);
			/*
			 * Costruisce la stringa sql delle tabelle coinvolte nella ricerca degli accertamenti. 
			 */
			sqlTable = buildSqlFromTable(StringUtils.hasText(codiceIuv));
			
		/** ====================================================================================================== **/
		/* =											COSTRUISCO LA QUERY										  = */
		/** ====================================================================================================== **/
				
			/*
			 */
			sql = sqlSelect + sqlTable + sqlRestriction;
	    
		/** ====================================================================================================== **/
		/* =																									  = */
		/** ====================================================================================================== **/
			
			Map<String, Object> query = new HashMap<String, Object>();
			
			/* set parametri di ricerca */
			query.put("param", mapValueRestriction);
			query.put("sql", sql);
			
			return query;
			
		}catch(Exception e) {
			logger.error("buildObjectToSearch >>> ", e);
			throw (e);
		}
	}
	
	/**
	 * Costruisce l'sql della select per la selezione delle colonne da ritornare come risultato della query. 
	 * 
	 * @return {@link String}
	 * @throws Exception
	 * @author Marianna Memoli
	 */
	private String buildSqlSelect() throws Exception {
		try {
			String sql = "SELECT DISTINCT(accertamento.*) ";
				 	
			return sql;
		} catch (Exception e) {
			logger.error("buildSqlSelect >> ", e);
			throw(e);
		}
	}
	
	/**
	 * Costruisce la stringa sql delle tabelle coinvolte nella ricerca degli accertamenti.
	 * 
	 * @param {@link boolean} joinDettaglio, determina se mettere in INNER JOIN anche la tabella "mygov_accertamento_dettaglio"
	 * 
	 * @return {@link String}
	 * @throws Exception
	 * @author Marianna Memoli
	 */
	private String buildSqlFromTable(boolean joinDettaglio) throws Exception {
		try {
			String sql = 
				"FROM " +
					 "mygov_accertamento accertamento " +
						
				  "INNER JOIN " +
					 "mygov_ente_tipo_dovuto enteTipoDovuto ON accertamento.mygov_ente_tipo_dovuto_id = enteTipoDovuto.mygov_ente_tipo_dovuto_id " +
				 
				  "INNER JOIN " +
				 	 "mygov_anagrafica_stato anagStato ON accertamento.mygov_anagrafica_stato_id = anagStato.mygov_anagrafica_stato_id " +
				  
				  "INNER JOIN " +
					 "mygov_utente utente ON accertamento.mygov_utente_id = utente.mygov_utente_id " +
				
				 (joinDettaglio ? ("INNER JOIN mygov_accertamento_dettaglio accDettaglio ON accertamento.mygov_accertamento_id = accDettaglio.mygov_accertamento_id ") : "" ) +
				
				  "INNER JOIN " +
					 "mygov_ente ente ON enteTipoDovuto.mygov_ente_id = ente.mygov_ente_id ";

			return sql;
		} catch (Exception e) {
			logger.error("buildSqlFromTable >> ", e);
			throw(e);
		}
	}
	
	/**
	 * Aggiunge i filtri di ricerca alla query e alla mappa i valori per cui ricercare. 
	 * 
	 * @param {@link Map<String, Object> paramSql, mappa alla quale aggiungere i parametri da passare nella query
	 * 
	 * Fitri di ricerca OBBLIGATORI:
	 * ------------------------------------------------------------------------------------------------------------------------------------------
	 * @param {@link String} 	 codIpaEnte,       Ente selezionato come beaneficiario
	 * @param {@link List<Long>} utenteIDs,        Accertamenti creati dagli utenti presenti nella lista. Se l'utente autenticato ha ruolo:
	 * 												- ROLE_ACC:   visualizza solamente i propri accertamenti
	 * 												- ROLE_ADMIN: visualizza accertamenti effetuati da utenti operatori dell'ente.
	 * @param {@link List<String>} tipiDovutoCODs, Elenco dei tipi dovuto sui quali l'utente autenticato ha i permessi di operatore
	 * 
	 * 
	 * Filtri di ricerca OPZIONALI:
	 * ------------------------------------------------------------------------------------------------------------------------------------------
	 * @param {@link String} 	 codTipoDovuto,    Codice tipo dovuto
	 * @param {@link String} 	 codStato,         Codice dello stato
	 * @param {@link String} 	 nomeAccertamento, Testo digitato contenuto nella descrizione dell'accertamento
	 * @param {@link String} 	 dataUltimoAggDal, Data ultimo aggiornamento dal  (formato dd/MM/yyyy)
	 * @param {@link String} 	 dataUltimoAggAl,  Data ultimo aggiornamento al  (formato dd/MM/yyyy)
	 * @param {@link String}     codiceIuv, 	   Identificativo univoco versamento
	 * 
	 * @throws Exception
	 * @author Marianna Memoli
	 */
	private String buildSqlRestriction(
		String codIpaEnte, List<Long> utenteIDs, List<String> tipiDovutoCods, String codTipoDovuto, String codStato, String nomeAccertamento, 
		String dataUltimoAggDal, String dataUltimoAggAl, String codiceIuv, Map<String, Object> paramSql) throws Exception {
		try{
			String sql = new String();
			
			/**
			 * Aggiungo la restriction di default
			 */
			sql += "WHERE accertamento.mygov_accertamento_id IS NOT NULL ";
		
	 /* ===================================  PROPERTY ACCERTAMENTO =============================== **/
		
			/**
			 * Devono essere gestibili solo gli accertamenti creati :
			 * 
			 *  1. Per i tipi dovuto attivi per l'ente selezionato come beneficiario e per i quali l'utente autenticato ha i permessi di operatore.
			 *  
			 *  2. Creati dall'utente autenticato se ha ruolo "ROLE_ACC", anche da tutti gli altri utenti se l'utente loggato ha ruolo "ROLE_ADMIN".
			 *     Come utenti sono intesi gli operatori dell'ente.
			 */
			sql += "AND enteTipoDovuto.cod_tipo IN (:tipiDovutoCods) ";		paramSql.put("tipiDovutoCods", tipiDovutoCods); /* (Punto 1) */
			
			sql += "AND ente.cod_ipa_ente = :codIpaEnte ";					paramSql.put("codIpaEnte", codIpaEnte); 		/* (Punto 1) */
			
			sql += "AND utente.mygov_utente_id in (:utenteIDs) ";			paramSql.put("utenteIDs", utenteIDs);			/* (Punto 2) */
		
		
			/**
			 * Codice del tipo dovuto per cui è stato creato un accertamento 		(equals)
			 */
			if (StringUtils.hasText(codTipoDovuto)) {
				sql += "AND enteTipoDovuto.cod_tipo = :codTipo ";  			paramSql.put("codTipo", codTipoDovuto);
			}
		
			/**
			 * Codice dello stato in cui si trovano gli accertamenti 	(equals)
			 */
			if (StringUtils.hasText(codStato)) {
				sql += "AND anagStato.cod_stato = :codStato ";  		paramSql.put("codStato", codStato);
			}
		
			/**
			 * Il testo digitato è contenuto nel nome dell'accertamento		(like, ignore case, match anywhere) 
			 */
			if (StringUtils.hasText(nomeAccertamento)) {
	
				sql += "AND LOWER(accertamento.de_nome_accertamento) LIKE LOWER(:nomeAccertamento) ";  	
				
				paramSql.put("nomeAccertamento", "%" + nomeAccertamento + "%");
			}
		
			/**
			 * Data ultimo aggiornamento dal  (formato dd/MM/yyyy)		(>=)
			 */
			if (StringUtils.hasText(dataUltimoAggDal)) {
			
				sql += "AND accertamento.dt_ultima_modifica >= :dataUltimoAggDa ";  		
				
				paramSql.put("dataUltimoAggDa", Constants.DDMMYYYY.parse(dataUltimoAggDal));
			}
			
			/**
			 * Data ultimo aggiornamento al  (formato dd/MM/yyyy)		(<)
			 */
			if (StringUtils.hasText(dataUltimoAggAl)) {
				sql += "AND accertamento.dt_ultima_modifica < :dataUltimoAggA ";  		
				
				paramSql.put("dataUltimoAggA", Constants.DDMMYYYY.parse(dataUltimoAggAl));
			}
		
	 /* ===================================  PROPERTY ACCERTAMENTO_DETTAGLIO =============================== **/
		
			/**
			 * Codice IUV in accertamento 	(equals) 
			 */
			if (StringUtils.hasText(codiceIuv)) {
				sql += "AND accDettaglio.cod_iuv = :codiceIuv ";  	 	paramSql.put("codiceIuv", codiceIuv);
			}
			
			return sql;
		} catch (Exception e) {
			logger.error("buildSqlRestriction >>> ", e);
			throw (e);
		}
	}
}
