package it.regioneveneto.mygov.payment.mypivot.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import it.regioneveneto.mygov.payment.mypivot.dao.AccertamentoDettaglioDao;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.AccertamentoDettaglioDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.AccertamentoFlussoExportDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.AccertamentoUfficioCapitoloDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.PageDto;
import it.regioneveneto.mygov.payment.mypivot.utils.Constants;
import it.regioneveneto.mygov.payment.mypivot.utils.Utilities;

/**
 * 
 * @author Marianna Memoli
 * 
 */
@Repository
public class AccertamentoDettaglioDaoImpl implements AccertamentoDettaglioDao {

	private static final Logger logger = Logger.getLogger(AccertamentoDettaglioDaoImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Recupera l'elenco dei pagamenti inseribili in accertamento, eventualmente filtrati.
	 * 
	 * Filtri di ricerca OBBLIGATORI:
	 * ------------------------------------------------------------------------------------------------------------------------------------------
	 * @param {@link Long}     enteId,    		Identificativo dell'ente
	 * @param {@link String}   codTipoDovuto, 	Codice del tipo dovuto
	 * 
	 * Filtri di ricerca OPZIONALI:
	 * ------------------------------------------------------------------------------------------------------------------------------------------
	 * @param {@link String}   codiceIud, 	   						Identificativo univoco dovuto
	 * @param {@link String}   codiceIuv, 	  						Identificativo univoco versamento
	 * @param {@link String}   codiceIdentificativoUnivocoPagatore, CF/PIVA Pagatore
	 * 
	 * @param {@link String}   dataEsitoSingoloPagamentoDal,  		Data esito pagamento dal  (formato dd/MM/yyyy)
	 * @param {@link String}   dataEsitoSingoloPagamentoAl,   		Data esito pagamento al   (formato dd/MM/yyyy)
	 * 
	 * @param {@link String}   dataUltimoAggiornamentoDal, 			Data ultimo aggiornamento dal  (formato dd/MM/yyyy)
	 * @param {@link String}   dataUltimoAggiornamentoAl,   		Data ultimo aggiornamento al   (formato dd/MM/yyyy)
	 * 
	 * Paginazione:
	 * -----------------------------------------------------------------------------------------------------------------------------------------
	 * @param {@link Boolean}    hasPagination,    Determina se la query deve essere paginata, qualora la fosse diventa obbligatorio la
	 * 											   valorizzazione dei successivi due parametri.
	 * @param {@link Integer} 	 page, 			   Visualizza il punto di partenza nel set di dati corrente.
	 * @param {@link Integer} 	 pageSize, 		   Numero di record che la query deve selezione nella pagina corrente
	 * 
	 * @return {@link PageDto<AccertamentoDettaglioDto>}
	 * @throws Exception
	 * @author Marianna Memoli
 	 */
	@SuppressWarnings("unchecked")
	public PageDto<AccertamentoDettaglioDto> findPagamentiAccertabiliByFilter(
			 Long enteId, String codTipoDovuto, String codiceIud, String codiceIuv, String codiceIdentificativoUnivocoPagatore, 
			 String dataEsitoSingoloPagamentoDal, String dataEsitoSingoloPagamentoAl, String dataUltimoAggiornamentoDal, 
			 String dataUltimoAggiornamentoAl, Boolean hasPagination, int page, int pageSize) throws Exception {
		try {
			logger.debug(
				"DAO :: RICERCA :: PAGAMENTI INSERIBILI IN ACCERTAMENTO :: PARAMETRI DI RICERCA[" + 
						"enteId:" + enteId + ", codTipoDovuto:" + codTipoDovuto + ", " + 
						"codiceIud:" + codiceIud + ", codiceIuv:" + codiceIuv + ", " +
						"codiceIdentificativoUnivocoPagatore:" + codiceIdentificativoUnivocoPagatore + ", " + 
						"dataEsitoSingoloPagamentoDal:" + dataEsitoSingoloPagamentoDal + ", " +
						"dataEsitoSingoloPagamentoAl:" + dataEsitoSingoloPagamentoAl + ", " +
						"dataUltimoAggiornamentoDal:" + dataUltimoAggiornamentoDal + ", " + 
						"dataUltimoAggiornamentoAl:" + dataUltimoAggiornamentoAl + ", " + 
						"hasPagination:" + hasPagination + ", page:" + page + ", pageSize:" + pageSize + 
				"] :: START");
			
		/** ====================================================================================================== **/
		/* =									CHECK FILTRI OBBLIGATORI										  = */
		/** ====================================================================================================== **/
			
			/*
			 * Controllo se i filtri obbligatori sono valorizzati, se non lo sono lancio un eccezione per sospendere l'esecuzione della query.
			 */
			if (!StringUtils.hasText(codTipoDovuto) || enteId == null) {
				/*
				 * Interrompo l'esecuzione 
				 */
				String errMsg = "Impossibile avviare la ricerca dei pagamenti inseribili in accertamento. Parametri obbligatori assenti o non corretti: " + 
								"enteId[" + enteId + "], codTipoDovuto[" + codTipoDovuto + "]";
				/* */
				logger.error(errMsg);
				/* */
				throw new Exception(errMsg);
			}
			
		/** ====================================================================================================== **/
		/* =										BUILD PARAM DELLA QUERY										  = */
		/** ====================================================================================================== **/	
		
			/** 
			 * Costruisce la stringa sql dei parametri da passare alla function 
			 */
			String sqlParam = buildSqlParam(enteId, codTipoDovuto, codiceIud, codiceIuv, codiceIdentificativoUnivocoPagatore, dataEsitoSingoloPagamentoDal, dataEsitoSingoloPagamentoAl, dataUltimoAggiornamentoDal, dataUltimoAggiornamentoAl);
			
			/** 
			 * Costruisce la stringa sql dei parametri da passare alla function 
			 */
			String sqlPage = buildSqlPage(hasPagination, page, pageSize);
			
			
		/** ====================================================================================================== **/
		/* =									BUILD STRING SQL SELECT / COUNT									  = */
		/** ====================================================================================================== **/
			
			/* sql count */
			String sqlCountRow = "SELECT * FROM get_count_pagamenti_inseribili_in_accertamento(" + sqlParam + ")";
				
			/* sql */
			String sql= "SELECT * FROM get_pagamenti_inseribili_in_accertamento(" + sqlParam + sqlPage + ") ";
		
		/** ====================================================================================================== **/
		/* =									BUILD QUERY SQL SELECT / COUNT									  = */
		/** ====================================================================================================== **/
					
			/** Query di selezione */
			Query query = entityManager.createNativeQuery(sql);
			
			/** Query per la count */
			Query queryCount = entityManager.createNativeQuery(sqlCountRow);
			
		/** ====================================================================================================== **/
		/* =											EXECUTE QUERY											  = */
		/** ====================================================================================================== **/
			
			/* Instance return object */
			PageDto<AccertamentoDettaglioDto> ret = new PageDto<AccertamentoDettaglioDto>();
		
			if(hasPagination){
				
				// count Entities
				BigInteger total = (BigInteger) queryCount.getSingleResult();
				
				// set count Entities
				ret.setTotalRecords(total.longValue());
				
				/* execute */
				ret.setList(resultTransformer_PagamentiAccertabili(query.getResultList()));
			}else{
				/* execute */
				ret.setList(resultTransformer_PagamentiAccertabili(query.getResultList()));
				
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
			
			logger.debug("DAO :: RICERCA :: PAGAMENTI INSERIBILI IN ACCERTAMENTO :: END");
			
			return ret;
		}catch(Exception e) {
			logger.error("DAO :: RICERCA :: PAGAMENTI INSERIBILI IN ACCERTAMENTO :: END", e);
			throw new Exception(e);
		}
	}
	
	/**
	 * Recupera l'elenco dei pagamenti inseriti in accertamento dato l'identificativo dello stesso, eventualmente filtrati.
	 * 
	 * Filtri di ricerca OBBLIGATORI:
	 * ------------------------------------------------------------------------------------------------------------------------------------------
	 * @param {@link Long}     accertamentoID,  Identificativo accertamento
	 * @param {@link Long}     enteId,    		Identificativo dell'ente
	 * @param {@link String}   codTipoDovuto, 	Codice del tipo dovuto
	 * 
	 * Filtri di ricerca OPZIONALI:
	 * ------------------------------------------------------------------------------------------------------------------------------------------
	 * @param {@link String}   codiceIud, 	   						Identificativo univoco dovuto
	 * @param {@link String}   codiceIuv, 	  						Identificativo univoco versamento
	 * @param {@link String}   codiceIdentificativoUnivocoPagatore, CF/PIVA Pagatore
	 * 
	 * @param {@link String}   dataEsitoSingoloPagamentoDal,  		Data esito pagamento dal  (formato dd/MM/yyyy)
	 * @param {@link String}   dataEsitoSingoloPagamentoAl,   		Data esito pagamento al   (formato dd/MM/yyyy)
	 * 
	 * @param {@link String}   dataUltimoAggiornamentoDal, 			Data ultimo aggiornamento dal  (formato dd/MM/yyyy)
	 * @param {@link String}   dataUltimoAggiornamentoAl,   		Data ultimo aggiornamento al   (formato dd/MM/yyyy)
	 * 
	 * Paginazione:
	 * -----------------------------------------------------------------------------------------------------------------------------------------
	 * @param {@link Boolean}    hasPagination,    Determina se la query deve essere paginata, qualora la fosse diventa obbligatorio la
	 * 											   valorizzazione dei successivi due parametri.
	 * @param {@link String} 	 page, 			   Visualizza il punto di partenza nel set di dati corrente.
	 * @param {@link String} 	 pageSize, 		   Numero di record che la query deve selezione nella pagina corrente
	 * 
	 * @return {@link PageDto<AccertamentoDettaglioDto>}
	 * @throws Exception
	 * @author Marianna Memoli
 	 */
	@SuppressWarnings("unchecked")
	public PageDto<AccertamentoDettaglioDto> findPagamentiInAccertamentoByFilter(
			 Long accertamentoId, Long enteId, String codTipoDovuto, String codiceIud, String codiceIuv, 
			 String codiceIdentificativoUnivocoPagatore, String dataEsitoSingoloPagamentoDal, String dataEsitoSingoloPagamentoAl,  
			 String dataUltimoAggiornamentoDal, String dataUltimoAggiornamentoAl, Boolean hasPagination, int page, int pageSize) throws Exception {
		try {
			logger.debug(
				"DAO :: RICERCA :: PAGAMENTI INSERITI IN ACCERTAMENTO :: PARAMETRI DI RICERCA[" + 
						 "accertamentoId:" + accertamentoId + ", enteId:" + enteId + ", codTipoDovuto:" + codTipoDovuto + ", " + 
						 "codiceIud:" + codiceIud + ", codiceIuv:" + codiceIuv + ", " +
						 "codiceIdentificativoUnivocoPagatore:" + codiceIdentificativoUnivocoPagatore + ", " + 
						 "dataEsitoSingoloPagamentoDal:" + dataEsitoSingoloPagamentoDal + ", " +
						 "dataEsitoSingoloPagamentoAl:" + dataEsitoSingoloPagamentoAl + ", " +
						 "dataUltimoAggiornamentoDal:" + dataUltimoAggiornamentoDal + ", " + 
						 "dataUltimoAggiornamentoAl:" + dataUltimoAggiornamentoAl + ", " + 
						 "hasPagination:" + hasPagination + ", page:" + page + ", pageSize:" + pageSize + 
				"] :: START");
			
		/** ====================================================================================================== **/
		/* =									CHECK FILTRI OBBLIGATORI										  = */
		/** ====================================================================================================== **/
			
			/*
			 * Controllo se i filtri obbligatori sono valorizzati, se non lo sono lancio un eccezione per sospendere l'esecuzione della query.
			 */
			if (accertamentoId == null || !StringUtils.hasText(codTipoDovuto) || enteId == null) {
				/*
				 * Interrompo l'esecuzione 
				 */
				String errMsg = "Impossibile avviare la ricerca dei pagamenti inseriti in accertamento. Parametri obbligatori assenti o non corretti: " + 
								"accertamentoId[" + accertamentoId + "], enteId[" + enteId + "], codTipoDovuto[" + codTipoDovuto + "]";
				/* */
				logger.error(errMsg);
				/* */
				throw new Exception(errMsg);
			}
			
		/** ====================================================================================================== **/
		/* =										BUILD PARAM DELLA QUERY										  = */
		/** ====================================================================================================== **/	
		
			/** 
			 * Costruisce la stringa sql dei parametri da passare alla function 
			 */
			String sqlParam = "'" + accertamentoId + "', " + buildSqlParam(enteId, codTipoDovuto, codiceIud, codiceIuv, codiceIdentificativoUnivocoPagatore, dataEsitoSingoloPagamentoDal, dataEsitoSingoloPagamentoAl, dataUltimoAggiornamentoDal, dataUltimoAggiornamentoAl);
			
			/** 
			 * Costruisce la stringa sql dei parametri da passare alla function 
			 */
			String sqlPage = buildSqlPage(hasPagination, page, pageSize);
			
		/** ====================================================================================================== **/
		/* =									BUILD STRING SQL SELECT / COUNT									  = */
		/** ====================================================================================================== **/
			
			/* sql count */
			String sqlCountRow = "SELECT * FROM get_count_pagamenti_inseriti_in_accertamento(" + sqlParam + ")";
				
			/* sql */
			String sql= "SELECT * FROM get_pagamenti_inseriti_in_accertamento(" + sqlParam + sqlPage + ") ";
		
		/** ====================================================================================================== **/
		/* =									BUILD QUERY SQL SELECT / COUNT									  = */
		/** ====================================================================================================== **/
					
			/** Query di selezione */
			Query query = entityManager.createNativeQuery(sql); 
			
			/** Query per la count */
			Query queryCount = entityManager.createNativeQuery(sqlCountRow);
			
		/** ====================================================================================================== **/
		/* =											EXECUTE QUERY											  = */
		/** ====================================================================================================== **/
			
			/* Instance return object */
			PageDto<AccertamentoDettaglioDto> ret = new PageDto<AccertamentoDettaglioDto>();
		
			if(hasPagination){
				
				// count Entities
				BigInteger total = (BigInteger) queryCount.getSingleResult();
				
				// set count Entities
				ret.setTotalRecords(total.longValue());
				
				/* execute */
				ret.setList(resultTransformer_PagamentiInAccertamento(query.getResultList()));
			}else{
				/* execute */
				ret.setList(resultTransformer_PagamentiInAccertamento(query.getResultList()));
				
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
		
			logger.debug("DAO :: RICERCA :: PAGAMENTI INSERITI IN ACCERTAMENTO :: END");
			
			return ret;
		}catch(Exception e) {
			logger.error("DAO :: RICERCA :: PAGAMENTI INSERITI IN ACCERTAMENTO :: END", e);
			throw new Exception(e);
		}
	}
	
	/**
	 * Recupera l'elenco dei capitoli associati alla RT.
	 * 
	 * @param {@link Long}    accertamentoId, Identificativo accertamento
	 * @param {@link Long}    enteId,    	  Identificativo dell'ente
	 * @param {@link String}  codiceIpaEnte,  Codice ipa dell'ente
	 * @param {@link String}  codTipoDovuto,  Codice del tipo dovuto
	 * @param {@link String}  codiceIud, 	  Identificativo univoco del dovuto
	 * @param {@link String}  codiceIuv, 	  Identificativo univoco versamento
	 * @param {@link Boolean} flgAttivo, 	  Stato attivazione ufficio ente	  (Facoltativo)
	 * 
	 * @return {@link List<AccertamentoUfficioCapitoloDto>}
	 * @throws Exception
	 * @author Marianna Memoli
 	 */
	@SuppressWarnings("unchecked")
	public List<AccertamentoUfficioCapitoloDto> findListCapitoliByRT(
			Long accertamentoId, String codiceIpaEnte, Long enteId, String codTipoDovuto, String codiceIud, String codiceIuv, Boolean flgAttivo) throws Exception {
		try {
			logger.debug("DAO :: RICERCA :: CAPITOLI ASSOCIATI ALLA RT :: PARAMETRI DI RICERCA[" + 
						 "accertamentoId:" + accertamentoId + ", enteId:" + enteId + ", codiceIpaEnte:" + codiceIpaEnte + ", " +
						 "codTipoDovuto:" + codTipoDovuto + ", codiceIud:" + codiceIud + ", codiceIuv:" + codiceIuv + ", " +
						 "flgAttivo:" + flgAttivo + "] :: START");
			
			/*
			 * Controllo se i filtri obbligatori sono valorizzati, se non lo sono lancio un eccezione per sospendere l'esecuzione della query.
			 */
			if (accertamentoId == null || enteId == null || !StringUtils.hasText(codTipoDovuto) || !StringUtils.hasText(codiceIud) || !StringUtils.hasText(codiceIuv)) {
				/*
				 * Interrompo l'esecuzione 
				 */
				String errMsg = "Impossibile avviare la ricerca dei capitoli associati alla RT in accertamento. Parametri obbligatori assenti o non corretti: " + 
								"accertamentoId[" + accertamentoId + "], enteId[" + enteId + "], codiceIpaEnte[" + codiceIpaEnte + "], " +
								"codTipoDovuto[" + codTipoDovuto + "], codiceIud[" + codiceIud + "], codiceIuv[" + codiceIuv + "]";
				/* */
				logger.error(errMsg);
				/* */
				throw new Exception(errMsg);
			}
			
			/*
			 * Costruisco la stringa sql, selezionando tutte le tuple dove il codice accertamento non è stato definito.
			 */
			String sql1 = 
				"SELECT " +
					 "DISTINCT ON (adt.cod_ufficio, adt.cod_capitolo) adt.cod_ufficio, " +
					 "uff.de_ufficio, " +
					 "adt.cod_capitolo, " +
					 "uff.de_capitolo, " +
					 "uff.de_anno_esercizio, " + 
					 "adt.cod_accertamento, " +
					 "'n/a' as de_accertamento, " +
					 "adt.num_importo " +
				"FROM " +
					 "mygov_accertamento_dettaglio AS adt 	LEFT JOIN 	  mygov_anagrafica_uff_cap_acc AS uff   ON " +
					 "adt.cod_ufficio = uff.cod_ufficio AND " +
					 "adt.cod_capitolo = uff.cod_capitolo AND " + 
					 "adt.cod_tipo_dovuto = uff.cod_tipo_dovuto AND " +
					 "uff.mygov_ente_id = :enteId " +
				"WHERE " + 
					 "adt.mygov_accertamento_id = :accertamentoId AND " +
					 "adt.cod_tipo_dovuto = :codTipoDovuto AND " +
					 "adt.cod_iud = :codiceIud AND " +
					 "adt.cod_iuv = :codiceIuv AND " +
					 "adt.cod_ipa_ente = :codIpaEnte AND " +
					 "adt.cod_accertamento = 'n/a' ";
			
			if(flgAttivo != null) sql1 += "AND uff.flg_attivo = :flgAttivo ";
			 
			/*
			 * Costruisco la stringa sql, selezionando tutte le tuple dove il codice accertamento è stato definito.
			 */
			String sql2 = 
				"SELECT " +
					 "DISTINCT ON (adt.cod_ufficio, adt.cod_capitolo, adt.cod_accertamento) adt.cod_ufficio, " +
					 "uff.de_ufficio, " +
					 "adt.cod_capitolo, " +
					 "uff.de_capitolo, " +
					 "uff.de_anno_esercizio, " + 
					 "adt.cod_accertamento, " +
					 "uff.de_accertamento, " +
					 "adt.num_importo " +
				"FROM " +
					 "mygov_accertamento_dettaglio AS adt 	LEFT JOIN 	  mygov_anagrafica_uff_cap_acc AS uff   ON " +
					 "adt.cod_ufficio = uff.cod_ufficio AND " +
					 "adt.cod_capitolo = uff.cod_capitolo AND " + 
					 "adt.cod_accertamento = uff.cod_accertamento AND " + 
					 "adt.cod_tipo_dovuto = uff.cod_tipo_dovuto AND " +
					 "uff.mygov_ente_id = :enteId " +
				"WHERE " + 
					 "adt.mygov_accertamento_id = :accertamentoId AND " +
					 "adt.cod_tipo_dovuto = :codTipoDovuto AND " +
					 "adt.cod_iud = :codiceIud AND " +
					 "adt.cod_iuv = :codiceIuv AND " +
					 "adt.cod_ipa_ente = :codIpaEnte AND " +
					 "adt.cod_accertamento <> 'n/a' ";
			
			if(flgAttivo != null) sql2 += "AND uff.flg_attivo = :flgAttivo ";
			
			/*
			 * Costruisco la stringa sql che ritorna le tuple selezionate dall'esecuzione delle query singole (sql1 e sql2)
			 */
			String sql3 = "SELECT * FROM ( " + sql1 + " UNION " + sql2 + ") as subq ORDER BY subq.de_ufficio, subq.num_importo ASC" ;
						
			/** Query di selezione */
			Query query = entityManager.createNativeQuery(sql3); 
			
			/*
			 * set param filter
			 */
			query.setParameter("accertamentoId", accertamentoId);
			query.setParameter("codTipoDovuto", codTipoDovuto);
			query.setParameter("codiceIud", codiceIud);
			query.setParameter("codiceIuv", codiceIuv);
			query.setParameter("codIpaEnte", codiceIpaEnte);
			query.setParameter("enteId", enteId);
			
			if(flgAttivo != null) query.setParameter("flgAttivo", flgAttivo);
				
			/* Instance return object */
			List<AccertamentoUfficioCapitoloDto> ret = resultTransformer_UfficioCapitolo(query.getResultList());
		
			logger.debug("DAO :: RICERCA :: CAPITOLI ASSOCIATI ALLA RT  :: END");
			
			return ret;
		}catch(Exception e) {
			logger.error("DAO :: RICERCA :: CAPITOLI ASSOCIATI ALLA RT :: ERROR ", e);
			throw new Exception(e);
		}
	}
	
	/**
	 * Costruisce la stringa sql dei parametri da passare alla function
	 * 
	 * Filtri di ricerca OBBLIGATORI:
	 * ------------------------------------------------------------------------------------------------------------------------------------------
	 * @param {@link Long}     enteId,    		Identificativo dell'ente
	 * @param {@link String}   codTipoDovuto, 	Codice del tipo dovuto
	 * 
	 * Filtri di ricerca OPZIONALI:
	 * ------------------------------------------------------------------------------------------------------------------------------------------
	 * @param {@link String}   codiceIud, 	   						Identificativo univoco dovuto
	 * @param {@link String}   codiceIuv, 	  						Identificativo univoco versamento
	 * @param {@link String}   codiceIdentificativoUnivocoPagatore, CF/PIVA Pagatore
	 * 
	 * @param {@link String}   dataEsitoSingoloPagamentoDal,  		Data esito pagamento dal  (formato dd/MM/yyyy)
	 * @param {@link String}   dataEsitoSingoloPagamentoAl,   		Data esito pagamento al   (formato dd/MM/yyyy)
	 * 
	 * @param {@link String}   dataUltimoAggiornamentoDal, 			Data ultimo aggiornamento dal  (formato dd/MM/yyyy)
	 * @param {@link String}   dataUltimoAggiornamentoAl,   		Data ultimo aggiornamento al   (formato dd/MM/yyyy)
	 * 
	 * @return {@link String}
	 * @throws Exception
	 * @author Marianna Memoli
	 */
	private String buildSqlParam(
			 Long enteId, String codTipoDovuto, String codiceIud, String codiceIuv, String codiceIdentificativoUnivocoPagatore, 
			 String dataEsitoSingoloPagamentoDal, String dataEsitoSingoloPagamentoAl, String dataUltimoAggiornamentoDal, String dataUltimoAggiornamentoAl) throws Exception {
		try{
			
			/* Set param base */
			String sql = "'" + enteId + "', '" + codTipoDovuto + "', ";													
			
			/* Codice IUD */
			if(StringUtils.hasText(codiceIud)) 
				sql += "'" + codiceIud + "', ";
			else
				sql += "null, ";	
			
			/* Codice IUV */
			if(StringUtils.hasText(codiceIuv)) 
				sql += "'" + codiceIuv + "', ";
			else
				sql += "null, ";
			
			/* CF/PIVA Pagatore */
			if(StringUtils.hasText(codiceIdentificativoUnivocoPagatore))
				sql += "'" + codiceIdentificativoUnivocoPagatore + "', ";
			else
				sql += "null, ";
		
			/* Data esito pagamento dal  (formato dd/MM/yyyy) */
			if(StringUtils.hasText(dataEsitoSingoloPagamentoDal))
				sql += "'" + Constants.DDMMYYYY.parse(dataEsitoSingoloPagamentoDal) + "', ";
			else
				sql += "null, ";
			
		
			/* Data esito pagamento al  (formato dd/MM/yyyy) */
			if(StringUtils.hasText(dataEsitoSingoloPagamentoAl)) 
				sql += "'" + Constants.DDMMYYYY.parse(dataEsitoSingoloPagamentoAl) + "', ";		
			else
				sql += "null, ";
			
			
			/* Data ultimo aggiornamento dal  (formato dd/MM/yyyy) */
			if(StringUtils.hasText(dataUltimoAggiornamentoDal)) 
				sql += "'" + Constants.DDMMYYYY.parse(dataUltimoAggiornamentoDal) + "', ";
			else
				sql += "null, ";
			
			/* Data ultimo aggiornamento al  (formato dd/MM/yyyy) */
			if(StringUtils.hasText(dataUltimoAggiornamentoAl))
				sql += "'" + Constants.DDMMYYYY.parse(dataUltimoAggiornamentoAl) + "' ";	
			else
				sql += "null ";
			
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
	 * @author Marianna Memoli
	 */
	private String buildSqlPage(Boolean hasPagination, Integer page, Integer pageSize) throws Exception {
		try{
			// adjust pagination
			int pageStart = 0; 		if (page > 0) pageStart = page - 1;
						
			/* Set param base */
			String sql = ", " + hasPagination + ", ";													
			
			/* Codice IUD */
			if(hasPagination) 
				sql += (pageStart*pageSize) + ", " + pageSize + " ";
			else
				sql += "null, null ";	
			
			return sql;
		} catch (Exception e) {
			logger.error("buildSqlPage >>> ", e);
			throw (e);
		}
	}
	
	/**
	 * Ritorno la lista di DTO del dettaglio accertamento.
	 * 
	 * @param {@link Object[]} rows
	 * @return {@link List<AccertamentoDettaglioDto>}
	 */
	private List<AccertamentoDettaglioDto> resultTransformer_PagamentiInAccertamento(List<Object[]> rows) {
		List<AccertamentoDettaglioDto> result = new ArrayList<AccertamentoDettaglioDto>();

		for (Object[] row : rows) {
			/**
			 * Info Dettaglio Accertamento
			 */
			AccertamentoDettaglioDto item = resultTransformerAccertamentoDettaglio(row);
			/**
			 * Sono possibili da rimuovere, quindi per default sono deselezionati
			 */
			item.setSelected(false);
			/**
			 * Info RT in accertamento
			 */
			item.setFlussoExportDTO(resultTransformerFlussoExport(Arrays.copyOfRange(row, 8, row.length)));
			
			/** */
			result.add(item);
		}
		
		return result;
	}
	
	/**
	 * Ritorno la lista di DTO delle RT inseribili in accertamento.
	 * 
	 * @param {@link Object[]} rows
	 * @return {@link List<AccertamentoDettaglioDto>}
	 */
	private List<AccertamentoDettaglioDto> resultTransformer_PagamentiAccertabili(List<Object[]> rows) {
		List<AccertamentoDettaglioDto> result = new ArrayList<AccertamentoDettaglioDto>();

		for (Object[] row : rows) {
			/** 
			 */
			AccertamentoFlussoExportDto flussoExportDTO = resultTransformerFlussoExport(row);
			/**
			 * Info Dettaglio Accertamento
			 */
			AccertamentoDettaglioDto item = new AccertamentoDettaglioDto();
			/**
			 * Sono possibili da inserire, quindi per default sono deselezionati
			 */
			item.setSelected(false);
			/**
			 * Info RT in accertamento
			 */
			item.setFlussoExportDTO(flussoExportDTO);
			
			/** */
			result.add(item);
		}
		
		return result;
	}
	
	/**
	 * Setto nel DTO i valori del dettaglio accertamento ritornati dalla query.
	 * 
	 * @param {@link Object[]} rows
	 * @return {@link AccertamentoDettaglioDto}
	 */
	private AccertamentoDettaglioDto resultTransformerAccertamentoDettaglio(Object[] row) {
		/**
		 */
		AccertamentoDettaglioDto item = new AccertamentoDettaglioDto();
		/** Identificativo interno */
		item.setId(String.valueOf(row[0]));	
		/** Identificativo dell'accertamento */
//		a.mygov_accertamento_id as accertamento_id_acc, (1)	
		/** Codice Ipa dell'ente */
//		a.mygov_accertamento_id as cod_ipa_ente (2)	
		/** Codice del tipo dovuto */
//		a.mygov_accertamento_id as cod_ipa_dovuto (3)	
		/** Identificativo univoco dovuto */
//		a.mygov_accertamento_id as cod_iud (4)	
		/** Identificativo univoco versamento */
//		a.mygov_accertamento_id as cod_iuv (4)
		/** Data ultima modifica */
		item.setDtUltimaModifica(Constants.DDMMYYYY_HHMM.format(row[6]));
		/** Data Inserimento */
//		a.dt_data_inserimento as dt_data_inserimento_acc, (7)	
		
		return item;
	}
	
	/**
	 * Setto nel dto i valori della RT in accertamento ritornati dalla query.
	 * 
	 * @param {@link Object[]} rows
	 * @return {@link AccertamentoFlussoExportDto}
	 */
	private AccertamentoFlussoExportDto resultTransformerFlussoExport(Object[] row) {

		/** Info RT in accertamento */
		AccertamentoFlussoExportDto to = new AccertamentoFlussoExportDto() {};
		// cod_tipo_dovuto
		to.setCodTipoDovuto(row[0] != null ? (String) row[0] : null);
		// de_tipo_dovuto
		to.setDeTipoDovuto(row[1] != null ? (String) row[1] : null);
		// cod_iud
		to.setCodiceIud(row[2] != null ? (String) row[2] : null);
		// cod_e_dati_pag_id_univoco_versamento
		to.setCodiceIuv(row[3] != null ? (String) row[3] : null);
		// cod_e_dati_pag_dati_sing_pag_id_univoco_riscoss
		to.setIdentificativoUnivocoRiscossione(row[4] != null ? (String) row[4] : null);
		// de_e_istit_att_denominazione_attestante
		to.setDenominazioneAttestante(row[5] != null ? (String) row[5] : null);
		// cod_e_istit_att_id_univ_att_codice_id_univoco
		to.setCodiceIdentificativoUnivocoAttestante(row[6] != null ? (String) row[6] : null);
		// cod_e_istit_att_id_univ_att_tipo_id_univoco
		to.setTipoIdentificativoUnivocoAttestante(row[7] != null ? ("" + row[7]) : null);
		// cod_e_sogg_vers_anagrafica_versante
		to.setAnagraficaVersante(row[8] != null ? (String) row[8] : null);
		// cod_e_sogg_vers_id_univ_vers_codice_id_univoco
		to.setCodiceIdentificativoUnivocoVersante(row[9] != null ? (String) row[9] : null);
		// cod_e_sogg_vers_id_univ_vers_tipo_id_univoco
		to.setTipoIdentificativoUnivocoVersante(row[10] != null ? ("" + row[10]) : null);
		// cod_e_sogg_pag_anagrafica_pagatore
		to.setAnagraficaPagatore(row[11] != null ? (String) row[11] : null);
		// cod_e_sogg_pag_id_univ_pag_codice_id_univoco
		to.setCodiceIdentificativoUnivocoPagatore(row[12] != null ? (String) row[12] : null);
		// cod_e_sogg_pag_id_univ_pag_tipo_id_univoco
		to.setTipoIdentificativoUnivocoPagatore(row[13] != null ? ("" + row[13]) : null);
		// dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento
		to.setDataEsitoSingoloPagamento(row[14] != null ? Constants.DDMMYYYY.format(row[14]) : null);
		// dt_ultima_modifica
		to.setDeDataUltimoAggiornamento(row[15] != null ? Constants.DDMMYYYY.format(row[15]) : Constants.CODICE_NOT_AVAILABLE);
		
		/* ============================================================================== */
			
			BigDecimal singoloImportoPagato = row[16] != null ? (BigDecimal) row[16] : null;
			
			if (singoloImportoPagato == null || singoloImportoPagato.equals(Constants.CODICE_NOT_AVAILABLE))
				to.setSingoloImportoPagato(null);
			else
				to.setSingoloImportoPagato(singoloImportoPagato);
				
			if (singoloImportoPagato == null){
				// num_e_dati_pag_dati_sing_pag_singolo_importo_pagato
				to.setFormatSingoloImportoPagato(Constants.CODICE_NOT_AVAILABLE);
			}else 
				if (!singoloImportoPagato.equals(Constants.CODICE_NOT_AVAILABLE)) {
					// num_e_dati_pag_dati_sing_pag_singolo_importo_pagato
					to.setFormatSingoloImportoPagato("€ " + Constants.NUMBER_FORMAT_US.format(singoloImportoPagato));
				}
			
			String causaleVersamento = row[17] != null ? (String) row[17] : null;
			
			// de_e_dati_pag_dati_sing_pag_causale_versamento
			to.setCausaleVersamento(Utilities.translateCodToDescNotAvaliable(causaleVersamento));
			
		/* ============================================================================== */
			
		return to;
	}
	
	/**
	 * Setto nel dto i valori dei capitoli assegnati alla RT in accertamento ritornati dalla query.
	 * 
	 * @param {@link Object[]} rows
	 * @return {@link AccertamentoUfficioCapitoloDto}
	 */
	private List<AccertamentoUfficioCapitoloDto> resultTransformer_UfficioCapitolo(List<Object[]> rows) {
		List<AccertamentoUfficioCapitoloDto> result = new ArrayList<AccertamentoUfficioCapitoloDto>();

		for (Object[] row : rows) {
			/** 
			 */
			AccertamentoUfficioCapitoloDto item = new AccertamentoUfficioCapitoloDto();

			item.setCodUfficio(String.valueOf(row[0])); 		// cod_ufficio
			item.setCodCapitolo(String.valueOf(row[2])); 		// cod_capitolo
			item.setDeAnnoEsercizio(String.valueOf(row[4])); 	// de_anno_esercizio
			item.setCodAccertamento(String.valueOf(row[5]));	// cod_accertamento
			
			item.setDeUfficio(row[1] != null ? String.valueOf(row[1]) : null); 			// de_ufficio
			item.setDeCapitolo(row[3] != null ? String.valueOf(row[3]) : null); 		// de_capitolo
			item.setDeAccertamento(row[6] != null ? String.valueOf(row[6]) : null); 	// de_accertamento
			
			// num_importo
			BigDecimal numImporto = (BigDecimal) row[7];
			
			item.setNumImporto("€ " + Constants.NUMBER_FORMAT_US.format(numImporto)); 
			
			/** */
			result.add(item);
		}
		
		return result;
	}

	@Override
	public List<String> findDistinctCodUfficioByCodIpaEnteListaIUDAndStato(final String codIpaEnte,
			final List<String> listaIud, final String codStato, final String deTipoStato) {

		logger.debug(
				"Chiamata al DAO findDistinctCodCapitoloByCodIpaEnteListaIUDCodUfficioCodTipoDovutoAndStato con i parameri codIpaEnte [ "
						+ codIpaEnte + " ]");

		StringBuilder sb = new StringBuilder("select distinct ad.codUfficio ");
		sb.append("from AccertamentoDettaglio ad ");
		sb.append("where ad.codIpaEnte = :codIpaEnte and ");
		sb.append("ad.codIud in (:listaCodIud) and ");
		sb.append("ad.accertamento.anagraficaStato.codStato = :codStato and ");
		sb.append("ad.accertamento.anagraficaStato.deTipoStato = :deTipoStato");
		String queryString = sb.toString();

		Query query = entityManager.createQuery(queryString);
		query.setParameter("codIpaEnte", codIpaEnte);
		query.setParameter("listaCodIud", listaIud);
		query.setParameter("codStato", codStato);
		query.setParameter("deTipoStato", deTipoStato);

		List<String> resultList = query.getResultList();

		if (resultList == null)
			return null;
		if (resultList.isEmpty())
			return null;

		return resultList;
	}

	@Override
	public List<String> findDistinctCodTipoDovutoByCodIpaEnteListaIUDCodUfficioAndStato(final String codIpaEnte,
			final List<String> listaIud, final String codUfficio, final String codStato, final String deTipoStato) {

		logger.debug(
				"Chiamata al DAO findDistinctCodCapitoloByCodIpaEnteListaIUDCodUfficioCodTipoDovutoAndStato con i parameri codIpaEnte [ "
						+ codIpaEnte + " ] e codUfficio [ " + codUfficio + " ]");

		StringBuilder sb = new StringBuilder("select distinct ad.codTipoDovuto ");
		sb.append("from AccertamentoDettaglio ad ");
		sb.append("where ad.codIpaEnte = :codIpaEnte and ");
		sb.append("ad.codIud in (:listaCodIud) and ");
		sb.append("ad.codUfficio = :codUfficio and ");
		sb.append("ad.accertamento.anagraficaStato.codStato = :codStato and ");
		sb.append("ad.accertamento.anagraficaStato.deTipoStato = :deTipoStato");
		String queryString = sb.toString();

		Query query = entityManager.createQuery(queryString);
		query.setParameter("codIpaEnte", codIpaEnte);
		query.setParameter("listaCodIud", listaIud);
		query.setParameter("codUfficio", codUfficio);
		query.setParameter("codStato", codStato);
		query.setParameter("deTipoStato", deTipoStato);

		List<String> resultList = query.getResultList();

		if (resultList == null)
			return null;
		if (resultList.isEmpty())
			return null;

		return resultList;
	}

	@Override
	public List<String> findDistinctCodCapitoloByCodIpaEnteListaIUDCodUfficioCodTipoDovutoAndStato(
			final String codIpaEnte, final List<String> listaIud, final String codUfficio, final String codTipoDovuto,
			final String codStato, final String deTipoStato) {

		logger.debug(
				"Chiamata al DAO findDistinctCodCapitoloByCodIpaEnteListaIUDCodUfficioCodTipoDovutoAndStato con i parameri codIpaEnte [ "
						+ codIpaEnte + " ], codUfficio [ " + codUfficio + " ] e codTipoDovuto [ " + codTipoDovuto
						+ " ]");

		StringBuilder sb = new StringBuilder("select distinct ad.codCapitolo ");
		sb.append("from AccertamentoDettaglio ad ");
		sb.append("where ad.codIpaEnte = :codIpaEnte and ");
		sb.append("ad.codIud in (:listaCodIud) and ");
		sb.append("ad.codUfficio = :codUfficio and ");
		sb.append("ad.codTipoDovuto = :codTipoDovuto and ");
		sb.append("ad.accertamento.anagraficaStato.codStato = :codStato and ");
		sb.append("ad.accertamento.anagraficaStato.deTipoStato = :deTipoStato");
		String queryString = sb.toString();

		Query query = entityManager.createQuery(queryString);
		query.setParameter("codIpaEnte", codIpaEnte);
		query.setParameter("listaCodIud", listaIud);
		query.setParameter("codUfficio", codUfficio);
		query.setParameter("codTipoDovuto", codTipoDovuto);
		query.setParameter("codStato", codStato);
		query.setParameter("deTipoStato", deTipoStato);

		List<String> resultList = query.getResultList();

		if (resultList == null)
			return null;
		if (resultList.isEmpty())
			return null;

		return resultList;
	}

	@Override
	public List<String> findDistinctCodAccertamentoByCodIpaEnteListaIUDCodUfficioCodTipoDovutoCodCapitoloAndStato(
			final String codIpaEnte, final List<String> listaIud, final String codUfficio, final String codTipoDovuto,
			final String codCapitolo, final String codStato, final String deTipoStato) {

		logger.debug(
				"Chiamata al DAO findDistinctCodAccertamentoByCodIpaEnteListaIUDCodUfficioCodTipoDovutoCodCapitoloAndStato con i parameri codIpaEnte [ "
						+ codIpaEnte + " ], codUfficio [ " + codUfficio + " ], codTipoDovuto [ " + codTipoDovuto
						+ " ] e codCapitolo [ " + codCapitolo + " ]");

		StringBuilder sb = new StringBuilder("select distinct ad.codAccertamento ");
		sb.append("from AccertamentoDettaglio ad ");
		sb.append("where ad.codIpaEnte = :codIpaEnte and ");
		sb.append("ad.codIud in (:listaCodIud) and ");
		sb.append("ad.codUfficio = :codUfficio and ");
		sb.append("ad.codTipoDovuto = :codTipoDovuto and ");
		sb.append("ad.codCapitolo = :codCapitolo and ");
		sb.append("ad.accertamento.anagraficaStato.codStato = :codStato and ");
		sb.append("ad.accertamento.anagraficaStato.deTipoStato = :deTipoStato");
		String queryString = sb.toString();

		Query query = entityManager.createQuery(queryString);
		query.setParameter("codIpaEnte", codIpaEnte);
		query.setParameter("listaCodIud", listaIud);
		query.setParameter("codUfficio", codUfficio);
		query.setParameter("codTipoDovuto", codTipoDovuto);
		query.setParameter("codCapitolo", codCapitolo);
		query.setParameter("codStato", codStato);
		query.setParameter("deTipoStato", deTipoStato);

		List<String> resultList = query.getResultList();

		if (resultList == null)
			return null;
		if (resultList.isEmpty())
			return null;

		return resultList;
	}

	@Override
	public BigDecimal getSumImportoByAccertamentoAndStato(final String codIpaEnte, final List<String> listaIud,
			final String codUfficio, final String codTipoDovuto, final String codCapitolo, final String codAccertamento,
			final String codStato, final String deTipoStato) {

		logger.debug("Chiamata al DAO getSumImportoByAccertamentoAndStato con i parameri codIpaEnte [ " + codIpaEnte
				+ " ], codUfficio [ " + codUfficio + " ], codTipoDovuto [ " + codTipoDovuto + " ], codCapitolo [ "
				+ codCapitolo + " ] e codAccertamento [ " + codAccertamento + " ]");

		StringBuilder sb = new StringBuilder("select sum(ad.numImporto) ");
		sb.append("from AccertamentoDettaglio ad ");
		sb.append("where ad.codIpaEnte = :codIpaEnte and ");
		sb.append("ad.codIud in (:listaCodIud) and ");
		sb.append("ad.codUfficio = :codUfficio and ");
		sb.append("ad.codTipoDovuto = :codTipoDovuto and ");
		sb.append("ad.codCapitolo = :codCapitolo and ");
		sb.append("ad.codAccertamento = :codAccertamento and ");
		sb.append("ad.accertamento.anagraficaStato.codStato = :codStato and ");
		sb.append("ad.accertamento.anagraficaStato.deTipoStato = :deTipoStato");
		String queryString = sb.toString();

		Query query = entityManager.createQuery(queryString);
		query.setParameter("codIpaEnte", codIpaEnte);
		query.setParameter("listaCodIud", listaIud);
		query.setParameter("codUfficio", codUfficio);
		query.setParameter("codTipoDovuto", codTipoDovuto);
		query.setParameter("codCapitolo", codCapitolo);
		query.setParameter("codAccertamento", codAccertamento);
		query.setParameter("codStato", codStato);
		query.setParameter("deTipoStato", deTipoStato);

		List<BigDecimal> resultList = query.getResultList();

		if (resultList == null)
			return null;
		if (resultList.isEmpty())
			return null;

		return resultList.get(0);
	}
}