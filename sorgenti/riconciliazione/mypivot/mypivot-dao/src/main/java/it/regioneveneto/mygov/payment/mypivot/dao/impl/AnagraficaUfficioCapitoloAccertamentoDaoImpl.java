package it.regioneveneto.mygov.payment.mypivot.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import it.regioneveneto.mygov.payment.mypivot.dao.AnagraficaUfficioCapitoloAccertamentoDao;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.AnagraficaUfficioCapitoloAccertamentoDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.PageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.po.AnagraficaUfficioCapitoloAccertamento;
import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO;

/**
 *
 * @author Alessandro Paolillo
 * @author Stefano De Feo
 *
 */
@Repository
public class AnagraficaUfficioCapitoloAccertamentoDaoImpl implements AnagraficaUfficioCapitoloAccertamentoDao {

	private static final Logger logger = Logger.getLogger(AnagraficaUfficioCapitoloAccertamentoDaoImpl.class);
	
	@PersistenceContext
	private EntityManager entityManager;
	
	/**
	 * Restituisce l'elenco (in distinct) degli uffici dati l'identificativo ente e il codice tipo dovuto. 
	 * 
	 * @param {@link Long}   	   enteId, 		  Identificativo ente
	 * @param {@link List<String>} codTipiDovuto, Elenco codici tipi dovuto
	 * @param {@link Boolean} 	   flgAttivo, 	  Stato attivazione ente
	 * 
	 * @return {@link List<AnagraficaUfficioCapitoloAccertamento>}
	 * @throws Exception 
	 * @author Marianna Memoli
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AnagraficaUfficioCapitoloAccertamento> findDistinctUfficiByFilter(Long enteId, List<String> codTipiDovuto, Boolean flgAttivo) throws Exception {
		try {
			logger.debug("DAO :: RICERCA :: UFFICI :: PARAMETRI DI RICERCA [enteId:" + enteId + ", codTipiDovuto:" + codTipiDovuto + ", flgAttivo:" + flgAttivo + "] :: START");
			
			/**
			 * Controllo se i filtri obbligatori sono valorizzati, se non lo sono lancio un eccezione per sospendere l'esecuzione della query.
			 */
			if (codTipiDovuto == null || codTipiDovuto.isEmpty() || enteId == null) {
				/*
				 * Interrompo l'esecuzione 
				 */
				String errMsg = "Impossibile avviare la ricerca degli uffici. Parametri obbligatori assenti o non corretti: " + 
								"enteId[" + enteId + "], codTipiDovuto[" + codTipiDovuto + "]";
				/* */
				logger.error(errMsg);
				/* */
				throw new Exception(errMsg);
			}
			/*
			 */
			String sql = "SELECT * FROM " +
						   "(" +
							 "SELECT " +
								"DISTINCT ON (anag.cod_ufficio) cod_ufficio, anag.* " +
							 "FROM " +
							 	"mygov_anagrafica_uff_cap_acc anag " +
							 "WHERE " +
							    "anag.mygov_ente_id = :enteId AND anag.cod_tipo_dovuto IN (:codTipiDovuto) " + 
							    (flgAttivo != null ? "AND anag.flg_attivo = :flgAttivo " : "") + 
						   ") as subq " +
						 "ORDER BY " +
						  	"de_ufficio ASC";
			
			/* Query di selezione */
			Query query = entityManager.createNativeQuery(sql, AnagraficaUfficioCapitoloAccertamento.class);
			
			/*
			 * Associare i valori della mappa per ciascun parametro denominato nella query
			 */
			query.setParameter("enteId", enteId);
			query.setParameter("codTipiDovuto", codTipiDovuto);
			
			if(flgAttivo != null) query.setParameter("flgAttivo", flgAttivo);
			
			List<AnagraficaUfficioCapitoloAccertamento> ret = query.getResultList();
				
			logger.debug("DAO :: RICERCA :: UFFICI :: END");
			
			return ret;
		}catch(Exception e) {
			logger.error("DAO :: RICERCA :: UFFICI :: END", e);
			throw new Exception(e);
		}
	}

	/**
	 * Restituisce l'elenco (in distinct) dei capitoli dati l'identificativo ente, l'anno di esercizio, il codice ufficio e il codice tipo dovuto. 
	 * 
	 * @param {@link Long}    enteId, 		 Identificativo ente
	 * @param {@link String}  codTipoDovuto, Codice tipo dovuto
	 * @param {@link String}  codUfficio, 	 Codice ufficio
	 * @param {@link String}  annoEsercizio, Anno di esercizio (Es: yyyy)
	 * @param {@link Boolean} flgAttivo,     Stato attivazione ente
	 * 
	 * @return {@link List<AnagraficaUfficioCapitoloAccertamento>}
	 * @author Marianna Memoli
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AnagraficaUfficioCapitoloAccertamento> findDistinctCapitoliByFilter(
			Long enteId, String codTipoDovuto, String codUfficio, String annoEsercizio, Boolean flgAttivo) throws Exception {
		try {
			logger.debug("DAO :: RICERCA :: CAPITOLI :: PARAMETRI DI RICERCA [enteId:" + enteId + ", codTipoDovuto:" + codTipoDovuto + ", " + 
						 "codUfficio: " + codUfficio + ", annoEsercizio:" + annoEsercizio + ", flgAttivo:" + flgAttivo + "] :: START");
			
			/**
			 * Controllo se i filtri obbligatori sono valorizzati, se non lo sono lancio un eccezione per sospendere l'esecuzione della query.
			 */
			if (!StringUtils.hasText(codTipoDovuto) || !StringUtils.hasText(codUfficio) || !StringUtils.hasText(annoEsercizio) || enteId == null || flgAttivo == null) {
				/*
				 * Interrompo l'esecuzione 
				 */
				String errMsg = "Impossibile avviare la ricerca degli uffici. Parametri obbligatori assenti o non corretti: " + 
								"enteId[" + enteId + "], codTipoDovuto[" + codTipoDovuto + "], annoEsercizio[" + annoEsercizio + "], " + 
								"codUfficio[" + codUfficio + "], flgAttivo[" +  flgAttivo + "]";
				/* */
				logger.error(errMsg);
				/* */
				throw new Exception(errMsg);
			}
			
			/*
			 */
			String sql = "SELECT * FROM " +
					   	    "(" + 
					   	      "SELECT " +
					   	       	 "DISTINCT ON (anag.cod_capitolo) cod_capitolo, anag.* " +
							  "FROM " +
							 	 "mygov_anagrafica_uff_cap_acc anag " +
							  "WHERE " +
							     "anag.mygov_ente_id = :enteId AND anag.cod_tipo_dovuto = :codTipoDovuto AND anag.cod_ufficio = :codUfficio AND " +
							     "anag.de_anno_esercizio = :annoEsercizio AND anag.flg_attivo = :flgAttivo " +
						    ") as subq " +
						 "ORDER BY " +
						  	"de_capitolo ASC";
			
			/* Query di selezione */
			Query query = entityManager.createNativeQuery(sql, AnagraficaUfficioCapitoloAccertamento.class);
			
			/*
			 * Associare i valori della mappa per ciascun parametro denominato nella query
			 */
			query.setParameter("enteId", enteId);
			query.setParameter("codTipoDovuto", codTipoDovuto);
			query.setParameter("codUfficio", codUfficio);
			query.setParameter("annoEsercizio", annoEsercizio);
			query.setParameter("flgAttivo", flgAttivo);
				
			List<AnagraficaUfficioCapitoloAccertamento> ret = query.getResultList();
			
			logger.debug("DAO :: RICERCA :: CAPITOLI :: END");
			
			return ret;
		}catch(Exception e) {
			logger.error("DAO :: RICERCA :: CAPITOLI :: END", e);
			throw new Exception(e);
		}
	}

	/**
	 * Restituisce l'elenco (in distinct) degli accertamenti dati l'identificativo ente, l'anno di esercizio, il codice ufficio e 
	 * il codice tipo dovuto. 
	 * 
	 * @param {@link Long}    enteId, 		 Identificativo ente
	 * @param {@link String}  codTipoDovuto, Codice tipo dovuto
	 * @param {@link String}  codUfficio, 	 Codice ufficio
	 * @param {@link String}  annoEsercizio, Anno di esercizio (Es: yyyy)
	 * @param {@link String}  codCapitolo, 	 Codice capitolo
	 * @param {@link Boolean} flgAttivo,     Stato attivazione ente
	 * 
	 * @return {@link List<AnagraficaUfficioCapitoloAccertamento>}
	 * @throws Exception 
	 * @author Marianna Memoli
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<AnagraficaUfficioCapitoloAccertamento> findDistinctAccertamentiByFilter(
			Long enteId, String codTipoDovuto, String codUfficio, String annoEsercizio, String codCapitolo, Boolean flgAttivo) throws Exception {
		try {
			logger.debug("DAO :: RICERCA :: ACCERTAMENTI :: PARAMETRI DI RICERCA [enteId:" + enteId + ", codTipoDovuto:" + codTipoDovuto + ", " + 
						 "codUfficio: " + codUfficio + ", annoEsercizio:" + annoEsercizio + ", codCapitolo:" + codCapitolo + ", " +
						 "flgAttivo:" + flgAttivo + "] :: START");
			
			/**
			 * Controllo se i filtri obbligatori sono valorizzati, se non lo sono lancio un eccezione per sospendere l'esecuzione della query.
			 */
			if (!StringUtils.hasText(codTipoDovuto) || !StringUtils.hasText(codUfficio) || !StringUtils.hasText(annoEsercizio) || !StringUtils.hasText(codCapitolo) || enteId == null || flgAttivo == null) {
				/*
				 * Interrompo l'esecuzione 
				 */
				String errMsg = "Impossibile avviare la ricerca degli uffici. Parametri obbligatori assenti o non corretti: " + 
								"enteId[" + enteId + "], codTipoDovuto[" + codTipoDovuto + "], codUfficio[" + codUfficio + "], " +
								"annoEsercizio[" + annoEsercizio + "], codCapitolo[" + codCapitolo + "], flgAttivo[" +  flgAttivo + "]";
				/* */
				logger.error(errMsg);
				/* */
				throw new Exception(errMsg);
			}
			
			String sql = "SELECT * FROM " +
					         "(" +
					           "SELECT " +
					           	  "DISTINCT ON (anag.cod_accertamento) cod_accertamento, anag.* " +
					           "FROM " +
					           	  "mygov_anagrafica_uff_cap_acc anag " +
					           "WHERE " +
								  "anag.mygov_ente_id = :enteId AND anag.cod_tipo_dovuto = :codTipoDovuto AND " +
								  "anag.cod_ufficio = :codUfficio AND anag.cod_capitolo = :codCapitolo AND anag.flg_attivo = :flgAttivo AND " +
								  "anag.de_anno_esercizio = :annoEsercizio AND anag.cod_accertamento IS NOT NULL AND anag.cod_accertamento <> 'n/a' " +
							 ") as subq " +
						 "ORDER BY " +
						  	"de_accertamento ASC";
			
			/* Query di selezione */
			Query query = entityManager.createNativeQuery(sql, AnagraficaUfficioCapitoloAccertamento.class);
			
			/*
			 * Associare i valori della mappa per ciascun parametro denominato nella query
			 */
			query.setParameter("enteId", enteId);
			query.setParameter("codTipoDovuto", codTipoDovuto);
			query.setParameter("codUfficio", codUfficio);
			query.setParameter("annoEsercizio", annoEsercizio);
			query.setParameter("codCapitolo", codCapitolo);
			query.setParameter("flgAttivo", flgAttivo);
				
			List<AnagraficaUfficioCapitoloAccertamento> ret = query.getResultList();
			
			logger.debug("DAO :: RICERCA :: ACCERTAMENTI :: END");
			
			return ret;
		}catch(Exception e) {
			logger.error("DAO :: RICERCA :: ACCERTAMENTI :: END", e);
			throw new Exception(e);
		}
	}
	
	/**
	 * Restituisce l'elenco (in distinct) dei capitoli dati l'identificativo ente, il codice ufficio e il codice tipo dovuto. 
	 * Il servizio restituisce un'elenco di capitoli dove il bean descrittivo dell'anagrafica è in versione ridotta.
     * In versione ridotta perchè, di tutte informazioni che descrivono l'anagrafica vengono considerate solo quelle
     * tali per cui un capitolo sia correttamente descritto ad un'utente che ne prende visione.
     * 
	 * @param {@link Long}    enteId, 		 Identificativo ente
	 * @param {@link String}  codTipoDovuto, Codice tipo dovuto
	 * @param {@link String}  codUfficio, 	 Codice ufficio
	 * 
	 * @return {@link List<AnagraficaUfficioCapitoloAccertamento>}
	 * @author Marianna Memoli
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AnagraficaUfficioCapitoloAccertamento> findDistinctCapitoliByEnteDovutoUfficio(Long enteId, String codTipoDovuto, String codUfficio) throws Exception {
		try {
			logger.debug("DAO :: RICERCA :: CAPITOLI :: PARAMETRI DI RICERCA [enteId:" + enteId + ", codTipoDovuto:" + codTipoDovuto + ", " + 
						 "codUfficio: " + codUfficio + "] :: START");
			
			/**
			 * Controllo se i filtri obbligatori sono valorizzati, se non lo sono lancio un eccezione per sospendere l'esecuzione della query.
			 */
			if (!StringUtils.hasText(codTipoDovuto) || !StringUtils.hasText(codUfficio) || enteId == null) {
				/*
				 * Interrompo l'esecuzione 
				 */
				String errMsg = "Impossibile avviare la ricerca degli uffici. Parametri obbligatori assenti o non corretti: " + 
								"enteId[" + enteId + "], codTipoDovuto[" + codTipoDovuto + "], codUfficio[" + codUfficio + "]";
				/* */
				logger.error(errMsg);
				/* */
				throw new Exception(errMsg);
			}
			
			/*
			 */
			String sql = "SELECT * FROM " +
					   	    "(" + 
					   	      "SELECT " +
					   	       	 "DISTINCT ON (anag.cod_capitolo) cod_capitolo, anag.* " +
							  "FROM " +
							 	 "mygov_anagrafica_uff_cap_acc anag " +
							  "WHERE " +
							     "anag.mygov_ente_id = :enteId AND anag.cod_tipo_dovuto = :codTipoDovuto AND anag.cod_ufficio = :codUfficio " +
						    ") as subq " +
						 "ORDER BY " +
						  	"de_capitolo ASC";
			
			/* Query di selezione */
			Query query = entityManager.createNativeQuery(sql, AnagraficaUfficioCapitoloAccertamento.class);
			
			/*
			 * Associare i valori della mappa per ciascun parametro denominato nella query
			 */
			query.setParameter("enteId", enteId);
			query.setParameter("codTipoDovuto", codTipoDovuto);
			query.setParameter("codUfficio", codUfficio);
				
			List<AnagraficaUfficioCapitoloAccertamento> ret = query.getResultList();
			
			logger.debug("DAO :: RICERCA :: CAPITOLI :: END");
			
			return ret;
		}catch(Exception e) {
			logger.error("DAO :: RICERCA :: CAPITOLI :: END", e);
			throw new Exception(e);
		}
	}
	
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageDto<AnagraficaUfficioCapitoloAccertamentoDto> findByFilter(Long idEnte,
			String codiceUfficio, String denominazioneUfficio, String codiceCapitolo, String denominazioneCapitolo, String deAnnoEsercizio, String codiceAccertamento, 
			String denominazioneAccertamento, String codTipoDovuto, Boolean flagAttivo, Boolean hasPagination, int page, int pageSize) throws Exception {
		
		try {
			logger.debug("DAO :: RICERCA :: ANAGRAFICA UFFICIO CAPITOLO ACCERTAMENTO   :: START");
		/** ====================================================================================================== **/
		/* =										BUILD PARAM DELLA QUERY										  = */
		/** ====================================================================================================== **/	

			/** 
			 * Costruisce la stringa sql dei parametri da passare alla function 
			 */
			String sqlPage = buildSqlPage(hasPagination, page, pageSize);

			String sqlParam = buildSqlParam(idEnte, codiceUfficio, denominazioneUfficio,  codiceCapitolo,  denominazioneCapitolo, deAnnoEsercizio, codiceAccertamento, 
					denominazioneAccertamento,  codTipoDovuto,  flagAttivo);
			
		/** ====================================================================================================== **/
		/* =									BUILD SQL SELECT / COUNT										  = */
		/** ====================================================================================================== **/
			String sql = "";
			String sqlCount = "";


			if (codTipoDovuto.equals("n/a")){

				sql = "SELECT uff.mygov_ente_id, uff.cod_ufficio, uff.de_ufficio, uff.cod_capitolo, uff.de_capitolo,"
						+ " uff.cod_accertamento, uff.de_accertamento, uff.flg_attivo, "
						+ "to_date(to_char(uff.dt_ultima_modifica,'DD-MM-YYYY'), 'DD-MM-YYYY') as dt_ultima_modifica, "
						+ "uff.cod_tipo_dovuto, cast (null as text) as de_tipo, uff.mygov_anagrafica_uff_cap_acc_id, uff.de_anno_esercizio "
						+ "FROM mygov_anagrafica_uff_cap_acc uff "
						+ "WHERE cod_tipo_dovuto is null AND uff.mygov_ente_id = " + idEnte + sqlParam;
			}

			else if (codTipoDovuto.equals("")){
				sql = "SELECT " +
						"uff.mygov_ente_id, " +
						"uff.cod_ufficio, " +
						"uff.de_ufficio, " +
						"uff.cod_capitolo, " +
						"uff.de_capitolo, " +
						"uff.cod_accertamento, " +
						"uff.de_accertamento, " +
						"uff.flg_attivo, " +
						"to_date(to_char(uff.dt_ultima_modifica,'DD-MM-YYYY'), 'DD-MM-YYYY') as dt_ultima_modifica, " +
						"uff.cod_tipo_dovuto, " +
						"tipoDovuto.de_tipo, " +
						"uff.mygov_anagrafica_uff_cap_acc_id, " +
						"uff.de_anno_esercizio "+
						"FROM mygov_anagrafica_uff_cap_acc uff, mygov_ente_tipo_dovuto tipoDovuto " +
						"WHERE uff.mygov_ente_id = tipoDovuto.mygov_ente_id AND uff.cod_tipo_dovuto = tipoDovuto.cod_tipo AND uff.mygov_ente_id = " + idEnte + sqlParam + " " +
						"UNION SELECT uff.mygov_ente_id, uff.cod_ufficio, uff.de_ufficio, uff.cod_capitolo, uff.de_capitolo, uff.cod_accertamento, "
						+ "uff.de_accertamento, uff.flg_attivo, to_date(to_char(uff.dt_ultima_modifica,'DD-MM-YYYY'), 'DD-MM-YYYY') as dt_ultima_modifica, "
						+ "uff.cod_tipo_dovuto, cast (null as text) as de_tipo, uff.mygov_anagrafica_uff_cap_acc_id, uff.de_anno_esercizio "
						+ "FROM mygov_anagrafica_uff_cap_acc uff "
						+ "WHERE cod_tipo_dovuto is null AND uff.mygov_ente_id = " + idEnte + sqlParam;
			}
			else {

				/** 
				 * Costruisce la stringa sql dei parametri da passare alla function 
				 */
				sql = "SELECT " +
						"uff.mygov_ente_id, " +
						"uff.cod_ufficio, " +
						"uff.de_ufficio, " +
						"uff.cod_capitolo, " +
						"uff.de_capitolo, " +
						"uff.cod_accertamento, " +
						"uff.de_accertamento, " +
						"uff.flg_attivo, " +
						"to_date(to_char(uff.dt_ultima_modifica,'DD-MM-YYYY'), 'DD-MM-YYYY') as dt_ultima_modifica, " +
						"uff.cod_tipo_dovuto, " +
						"tipoDovuto.de_tipo, " +
						"uff.mygov_anagrafica_uff_cap_acc_id, " +
						"uff.de_anno_esercizio "+
						"FROM mygov_anagrafica_uff_cap_acc uff, mygov_ente_tipo_dovuto tipoDovuto " +
						"WHERE uff.mygov_ente_id = tipoDovuto.mygov_ente_id AND uff.cod_tipo_dovuto = tipoDovuto.cod_tipo AND uff.mygov_ente_id = " + idEnte + sqlParam;
			}

			sqlCount = "SELECT COUNT(*) FROM ( "+sql+" ) count";
			
		/** ====================================================================================================== **/
		/* =									BUILD SQL E PARAM DELLA QUERY									  = */
		/** ====================================================================================================== **/
			/** Query di selezione */
			Query queryCount = entityManager.createNativeQuery(sqlCount); 
			
			BigInteger appCount = (BigInteger) queryCount.getSingleResult();
			Long countTotalRecords = appCount.longValue();
			
			if(hasPagination)
				sql = sql+sqlPage;
			
			/** Query di selezione */
			Query query = entityManager.createNativeQuery(sql); 
			
		/** ====================================================================================================== **/
		/* =											EXECUTE QUERY											  = */
		/** ====================================================================================================== **/
			
			/* Instance return object */
			PageDto<AnagraficaUfficioCapitoloAccertamentoDto> ret = new PageDto<AnagraficaUfficioCapitoloAccertamentoDto>();
		
				/* execute */
				ret.setList(resultTransformer(query.getResultList()));
				
				//count Entities
				ret.setTotalRecords(countTotalRecords);
			
			Double totalPages = new Double(pageSize == 0 ? 1L : Math.ceil(ret.getTotalRecords() / (double) pageSize));
			boolean hasNextPage = page < totalPages;
			boolean hasPreviousPage = page > 1;
			
			ret.setNextPage(hasNextPage);
			
			if (page == 0 && pageSize == 0){
				ret.setPage(1);
				ret.setPageSize(5);
			}else{
				ret.setPage(page);
				ret.setPageSize(pageSize);
			}
			ret.setPreviousPage(hasPreviousPage);
			ret.setTotalPages(totalPages.intValue());
		
			return ret;
		}catch(Exception e) {
			throw new Exception(e);
		}
	}
	
	/**
	 * 
	 * @param idEnte
	 * @param codiceUfficio
	 * @param denominazioneUfficio
	 * @param codiceCapitolo
	 * @param denominazioneCapitolo
	 * @param codiceAccertamento
	 * @param denominazioneAccertamento
	 * @param codTipoDovuto
	 * @param flagAttivo
	 * @return
	 * @throws Exception
	 * @author Alessandro Paolillo
	 * @author Stefano De Feo
	 */
	private String buildSqlParam(
			Long idEnte,
			String codiceUfficio, String denominazioneUfficio, String codiceCapitolo, String denominazioneCapitolo, String deAnnoEsercizio, String codiceAccertamento, 
			String denominazioneAccertamento, String codTipoDovuto, Boolean flagAttivo) throws Exception {
		try{
			String sql = " ";
			
			/* Codice Ufficio */
			if(StringUtils.hasText(codiceUfficio)) 
				sql += " AND cod_ufficio = '" + codiceUfficio.trim() + "'";
			
			/* Denominazione Ufficio */
			if(StringUtils.hasText(denominazioneUfficio))
				sql += " AND de_ufficio ilike '%" + denominazioneUfficio.trim() + "%'";
		
			/* Codice Capitolo */
			if(StringUtils.hasText(codiceCapitolo)) 
				sql += " AND cod_capitolo = '" + codiceCapitolo.trim() + "'";
			
			/* Denominazione Capitolo */
			if(StringUtils.hasText(denominazioneCapitolo))
				sql += " AND de_capitolo ilike '%" + denominazioneCapitolo.trim() + "%'";
			
			/* Anno Esercizio */
			if(StringUtils.hasText(deAnnoEsercizio))
				sql += " AND de_anno_esercizio = '" + deAnnoEsercizio.trim() + "'";
			
			/* Codice Capitolo */
			if(StringUtils.hasText(codiceAccertamento)) 
				sql += " AND cod_accertamento = '" + codiceAccertamento.trim() + "'";
			
			/* Denominazione Capitolo */
			if(StringUtils.hasText(denominazioneAccertamento))
				sql += " AND de_accertamento ilike '%" + denominazioneAccertamento.trim() + "%'";
			
			/* Codice Tipo Dovuto */
			if(StringUtils.hasText(codTipoDovuto) && codTipoDovuto.compareTo("n/a")!=0  )
				sql += " AND cod_tipo_dovuto = '" + codTipoDovuto.trim() + "'";
			
			/* Flag Ufficio Attivo */
			if(flagAttivo!=null)
				sql += " AND flg_attivo = " + flagAttivo;
			
			return sql;
		} catch (Exception e) {
			logger.error("buildSqlParam >>> ", e);
			throw (e);
		}
	}
	
	/**
	 * Costruisce la stringa sql dei parametri da passare alla function per la paginazione
	 * 
	 * Paginazione:
	 * -----------------------------------------------------------------------------------------------------------------------------------------
	 * @param {@link Boolean}    hasPagination,    Determina se la query deve essere paginata, qualora la fosse diventa obbligatorio la
	 * 											   valorizzazione dei successivi due parametri.
	 * @param {@link Integer} 	 page, 			   Visualizza il punto di partenza nel set di dati corrente.
	 * @param {@link Integer} 	 pageSize, 		   Numero di record che la query deve selezione nella pagina corrente
	 * 
	 * @return {@link String}
	 * @throws Exception
	 */
	private String buildSqlPage(Boolean hasPagination, Integer page, Integer pageSize) throws Exception {
		try{
			
			String sql = "";
			if ( hasPagination ) {
				if (page == 0 && pageSize == 0){
					sql += " OFFSET 0";
					sql += " LIMIT 5";
				}
				else{
					sql += " OFFSET "+((page-1)*pageSize);
					sql += " LIMIT "+pageSize;
				}
			}
			return sql;
		} catch (Exception e) {
			logger.error("buildSqlPage >>> ", e);
			throw (e);
		}
	}
	
	/**
	 * Setto nel dto i valori ritornati dalla query.
	 * 
	 * @param rows
	 * @return
	 */
	private List<AnagraficaUfficioCapitoloAccertamentoDto> resultTransformer(List<Object[]> rows) {
		List<AnagraficaUfficioCapitoloAccertamentoDto> result = new ArrayList<AnagraficaUfficioCapitoloAccertamentoDto>();

		for (Object[] row : rows) {
			/* */
			AnagraficaUfficioCapitoloAccertamentoDto item = new AnagraficaUfficioCapitoloAccertamentoDto();
			
			EnteTO enteTO = new EnteTO();
			enteTO.setId(Long.parseLong(String.valueOf(row[0])));
			item.setEnte(enteTO);
			
			item.setCodUfficio(String.valueOf(row[1]));
			item.setDeUfficio(String.valueOf(row[2]));
			item.setCodCapitolo(String.valueOf(row[3]));
			item.setDeCapitolo(String.valueOf(row[4]));
			item.setCodAccertamento(String.valueOf(row[5]));
			item.setDeAccertamento(String.valueOf(row[6]));
			item.setFlgAttivo(Boolean.valueOf(String.valueOf(row[7])));
			item.setDtUltimaModifica(row[8] != null ? (Date) row[8] : null);
			item.setCodTipoDovuto(String.valueOf(row[9]));
			item.setDeTipoDovuto(String.valueOf(row[10]));
			item.setId(Long.parseLong(String.valueOf(row[11])));
			item.setDeAnnoEsercizio(String.valueOf(row[12]));
			
			result.add(item);
		}
		
		return result;
	}

	/**
	 * Modifica dell'anagrafica
	 */
	@Override
	public int aggiornaAnagrafica(AnagraficaUfficioCapitoloAccertamentoDto anagraficaUfficioCapitoloAccertamentoDto) throws Exception{
		try{
			logger.debug("INIZIO MODIFICA ANAGRAFICA");
			
			String sql = "UPDATE mygov_anagrafica_uff_cap_acc SET ";
			
			String updateParam = setUpdateParam(anagraficaUfficioCapitoloAccertamentoDto);
			
			sql = sql + updateParam + " where mygov_anagrafica_uff_cap_acc_id = "+ anagraficaUfficioCapitoloAccertamentoDto.getId();
			
			/** Query di aggiornamento */
			Query query = entityManager.createNativeQuery(sql);
			
			logger.debug("FINE MODIFICA ANAGRAFICA");
			
			return query.executeUpdate();
			
		}catch (Exception e) {
			logger.error("FINE MODIFICA ANAGRAFICA", e);
		}
		return 0;
	}

	/**
	 * 
	 * @param anagraficaDto
	 * @return
	 * @author Alessandro Paolillo
	 * @author Stefano De Feo
	 */
	private String setUpdateParam(AnagraficaUfficioCapitoloAccertamentoDto anagraficaDto) {
		
		String updateParam=" de_ufficio = '"+anagraficaDto.getDeUfficio()+"' "+
				" ,cod_ufficio = '"+anagraficaDto.getCodUfficio()+"' "+
				" ,de_capitolo = '"+anagraficaDto.getDeCapitolo()+"' "+
				" ,cod_capitolo = '"+anagraficaDto.getCodCapitolo()+"' "+
				" ,de_anno_esercizio ='"+anagraficaDto.getDeAnnoEsercizio()+"' "+
				" ,de_accertamento ='"+anagraficaDto.getDeAccertamento()+"' "+
				" ,cod_accertamento ='"+anagraficaDto.getCodAccertamento()+"' "+
				" ,cod_tipo_dovuto ='"+anagraficaDto.getCodTipoDovuto()+"' "+
				" ,dt_ultima_modifica = current_timestamp ";
		
		return updateParam;
		
	}


	/**
	 * Recupera anagrafica tramite id
	 */
	@SuppressWarnings("unchecked")
	@Override
	public AnagraficaUfficioCapitoloAccertamentoDto findById(Long rowId) throws Exception{
		
		AnagraficaUfficioCapitoloAccertamentoDto res = new AnagraficaUfficioCapitoloAccertamentoDto();
		
		try{
			logger.debug("INIZIO FIND BY ID");
			
			String sql ="SELECT " +
					"uff.mygov_ente_id, " +
					"uff.cod_ufficio, " +
					"uff.de_ufficio, " +
					"uff.cod_capitolo, " +
					"uff.de_capitolo, " +
					"uff.cod_accertamento, " +
					"uff.de_accertamento, " +
					"uff.flg_attivo, " +
					"uff.dt_ultima_modifica, " +
					"uff.cod_tipo_dovuto, " +
					"tipoDovuto.de_tipo, " +
					"uff.mygov_anagrafica_uff_cap_acc_id, " +
					"uff.de_anno_esercizio "+
					"FROM mygov_anagrafica_uff_cap_acc uff, mygov_ente_tipo_dovuto tipoDovuto " +
					"WHERE " + 
						"mygov_anagrafica_uff_cap_acc_id =" +rowId+
						" AND uff.mygov_ente_id = tipoDovuto.mygov_ente_id" +
						" AND uff.cod_tipo_dovuto = tipoDovuto.cod_tipo";
			
			/** Query di select */
			Query query = entityManager.createNativeQuery(sql); 
			
			
			
			PageDto<AnagraficaUfficioCapitoloAccertamentoDto> ret = new PageDto<AnagraficaUfficioCapitoloAccertamentoDto>();
			
			/* execute */
			ret.setList(resultTransformer(query.getResultList()));
			
			res = ret.getList().get(0);
			
			logger.debug("FINE FIND BY ID");
			
			return res;
			
		}catch (Exception e) {
			logger.error("ERRORE IN FIND BY ID", e);
		}
		
		return null;
	}

	/**
	 * Recupero tutti gli uffici appartenenti all'ente selezionato
	 * 
	 * @param enteId
	 * @return
	 * @throws Exception
	 * @author Alessandro Paolillo
	 * @author Stefano De Feo
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AnagraficaUfficioCapitoloAccertamento> findDistinctUfficiByEnteId(Long enteId) throws Exception {
		try {
			logger.debug("DAO :: RICERCA :: UFFICI :: PARAMETRI DI RICERCA [enteId:" + enteId + "] :: START");
			
			/**
			 * Controllo se i filtri obbligatori sono valorizzati, se non lo sono lancio un eccezione per sospendere l'esecuzione della query.
			 */
			if (enteId == null) {
				/*
				 * Interrompo l'esecuzione 
				 */
				String errMsg = "Impossibile avviare la ricerca degli uffici. Parametri obbligatori assenti o non corretti: " + 
								"enteId[" + enteId + "]";
				/* */
				logger.error(errMsg);
				/* */
				throw new Exception(errMsg);
			}
			/*
			 */
			String sql = "SELECT * FROM " +
						   "(" +
							 "SELECT " +
								"DISTINCT ON (anag.cod_ufficio) cod_ufficio, anag.* " +
							 "FROM " +
							 	"mygov_anagrafica_uff_cap_acc anag " +
							 "WHERE " +
							    "anag.mygov_ente_id = :enteId " +
						   ") as subq " +
						 "ORDER BY " +
						  	"de_ufficio ASC";
			
			/* Query di selezione */
			Query query = entityManager.createNativeQuery(sql, AnagraficaUfficioCapitoloAccertamento.class);
			
			/*
			 * Associare i valori della mappa per ciascun parametro denominato nella query
			 */
			query.setParameter("enteId", enteId);
			
			List<AnagraficaUfficioCapitoloAccertamento> ret = query.getResultList();
				
			logger.debug("DAO :: RICERCA :: UFFICI :: END");
			
			return ret;
		}catch(Exception e) {
			logger.error("DAO :: RICERCA :: UFFICI :: END", e);
			throw new Exception(e);
		}
	}

	/**
	 * Recupero tutti i capitoli dato l'id ente
	 * 
	 * @param idEnte
	 * @return
	 * @throws Exception
	 * @author Alessandro Paolillo
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AnagraficaUfficioCapitoloAccertamento> findDistinctCapitoliByEnteId(Long idEnte) throws Exception {
		try {
			logger.debug("DAO :: RICERCA :: CAPITOLI :: PARAMETRI DI RICERCA [enteId:" + idEnte + " :: START");
			
			/**
			 * Controllo se i filtri obbligatori sono valorizzati, se non lo sono lancio un eccezione per sospendere l'esecuzione della query.
			 */
			if (idEnte == null ) {
				/*
				 * Interrompo l'esecuzione 
				 */
				String errMsg = "Impossibile avviare la ricerca degli uffici. Parametri obbligatori assenti o non corretti: " +
								"enteId[" + idEnte + "]";
				/* */
				logger.error(errMsg);
				/* */
				throw new Exception(errMsg);
			}
			/*
			 */
			String sql = "SELECT * FROM " +
			   	    "(" + 
			   	      "SELECT " +
			   	       	 "DISTINCT ON (anag.cod_capitolo) cod_capitolo, anag.* " +
					  "FROM " +
					 	 "mygov_anagrafica_uff_cap_acc anag " +
					  "WHERE " +
					     "anag.mygov_ente_id = :enteId " +
				    ") as subq " +
				 "ORDER BY " +
				  	"de_capitolo ASC";
			
			/* Query di selezione */
			Query query = entityManager.createNativeQuery(sql, AnagraficaUfficioCapitoloAccertamento.class);
			
			/*
			 * Associare i valori della mappa per ciascun parametro denominato nella query
			 */
			query.setParameter("enteId", idEnte);
			
			List<AnagraficaUfficioCapitoloAccertamento> ret = query.getResultList();
				
			logger.debug("DAO :: RICERCA :: CAPITOLI :: END");
			
			return ret;
		}catch(Exception e) {
			logger.error("DAO :: RICERCA :: CAPITOLI :: END", e);
			throw new Exception(e);
		}
	}

	/**
	 * Recupero tutti gli accertamenti dato id ente, codice ufficio e codice capitolo
	 * 
	 * @param idEnte
	 * @param codUff
	 * @param codCap
	 * @return
	 * @throws Exception
	 * @author Alessandro Paolillo
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AnagraficaUfficioCapitoloAccertamento> findDistinctAccertamentiByCodUffcodCap(Long idEnte,
			String codUff, String codCap) throws Exception {
		try {
			logger.debug("DAO :: RICERCA :: ACCERTAMENTI :: PARAMETRI DI RICERCA [enteId:" + idEnte + " codiceUfficio:" + codUff + " codiceCapitolo:" + codCap + "] :: START");
			
			/**
			 * Controllo se i filtri obbligatori sono valorizzati, se non lo sono lancio un eccezione per sospendere l'esecuzione della query.
			 */
			if (idEnte == null || !StringUtils.hasText(codUff) || !StringUtils.hasText(codCap)) {
				/*
				 * Interrompo l'esecuzione 
				 */
				String errMsg = "Impossibile avviare la ricerca degli uffici. Parametri obbligatori assenti o non corretti: " + 
								"enteId[" + idEnte + "] codiceUfficio[" +codUff+ "] codiceCapitolo [" +codCap+ "]";
				/* */
				logger.error(errMsg);
				/* */
				throw new Exception(errMsg);
			}
			/*
			 */
			String sql = "SELECT * FROM " +
			   	    "(" + 
			   	      "SELECT " +
			   	       	 "DISTINCT ON (anag.cod_accertamento) cod_accertamento, anag.* " +
					  "FROM " +
					 	 "mygov_anagrafica_uff_cap_acc anag " +
					  "WHERE " +
					     "anag.mygov_ente_id = :enteId AND anag.cod_ufficio = :codUfficio AND anag.cod_capitolo = :codCapitolo" +
				    ") as subq " +
				 "ORDER BY " +
				  	"de_capitolo ASC";
			
			/* Query di selezione */
			Query query = entityManager.createNativeQuery(sql, AnagraficaUfficioCapitoloAccertamento.class);
			
			/*
			 * Associare i valori della mappa per ciascun parametro denominato nella query
			 */
			query.setParameter("enteId", idEnte);
			query.setParameter("codUfficio", codUff);
			query.setParameter("codCapitolo", codCap);
			
			List<AnagraficaUfficioCapitoloAccertamento> ret = query.getResultList();
				
			logger.debug("DAO :: RICERCA :: ACCERTAMENTI :: END");
			
			return ret;
		}catch(Exception e) {
			logger.error("DAO :: RICERCA :: ACCERTAMENTI :: END", e);
			throw new Exception(e);
		}
	}

	
	/**
	 * 
	 * @param idEnte
	 * @param codTipoDovuto
	 * @return
	 * @throws Exception
	 * @author Alessandro Paolillo
	 */
	@Override
	public String getDeTipoDovutoByIdEnteAndCodTipoDovuto(Long idEnte, String codTipoDovuto) throws Exception {
		String deTipoDovuto = "";
		try{
			
			/**
			 * Controllo se i filtri obbligatori sono valorizzati, se non lo sono lancio un eccezione per sospendere l'esecuzione della query.
			 */
			if (idEnte == null || !StringUtils.hasText(codTipoDovuto)) {
				/*
				 * Interrompo l'esecuzione 
				 */
				String errMsg = "Impossibile avviare la ricerca della denominazione tipo dovuto. Parametri obbligatori assenti o non corretti: " + 
								"enteId[" + idEnte + "] codTipoDovuto[" +codTipoDovuto+"] ";
				/* */
				logger.error(errMsg);
				/* */
				throw new Exception(errMsg);
			}
			
			String sql = "SELECT de_tipo FROM mygov_ente_tipo_dovuto WHERE mygov_ente_id = :enteId AND cod_tipo = :codTipoDovuto " ;
			
			Query query = entityManager.createNativeQuery(sql);
			
			/*
			 * Associare i valori della mappa per ciascun parametro denominato nella query
			 */
			query.setParameter("enteId", idEnte);
			query.setParameter("codTipoDovuto", codTipoDovuto);
			
			deTipoDovuto = (String) query.getSingleResult();
			
			return deTipoDovuto;
			
		}catch (Exception e) {
			throw new Exception(e);
		}
	}
}