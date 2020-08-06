package it.regioneveneto.mygov.payment.mypivot.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import it.regioneveneto.mygov.payment.mypivot.dao.DettaglioCruscottoIncassiDao;

/**
 * 
 * @author Alessandro Paolillo
 * 
 */
@Repository
public class DettaglioCruscottoIncassiDaoImpl implements DettaglioCruscottoIncassiDao {

	private static final Logger logger = Logger.getLogger(DettaglioCruscottoIncassiDaoImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Recupera l'elenco di IUD relative le RT che hanno prodotto il dato statistico.
	 * 
	 * @param {@link Long}    enteId,    	   Identificativo dell'ente
	 * @param {@link Integer} anno,       	   L'anno per cui ritornare il dato statistico
	 * @param {@link Integer} mese, 	 	   Il mese per cui ritornare il dato statistico
	 * @param {@link Integer} giorno,      	   Il giorno per cui ritornare il dato statistico
	 * @param {@link String}  codUfficio, 	   Codice dell'ufficio
	 * @param {@link String}  codTipoDovuto,   Codice del tipo dovuto
	 * @param {@link String}  codCapitolo, 	   Codice del capitolo
	 * @param {@link String}  codAccertamento, Codice dell'accertamento
	 * 
	 * @return {@link List<String}
	 * @throws Exception
	 * @author Alessandro Paolillo
	 */
	@SuppressWarnings("unchecked")
	public List<String> findListaPagamentiByFilter(Long enteId, Integer anno, Integer mese, Integer giorno, String codUfficio, String codTipoDovuto,
			String codCapitolo, String codAccertamento) throws Exception {
		try {
			logger.debug("DAO :: RICERCA :: LISTA RT CRUSCOTTO :: PARAMETRI DI RICERCA[enteId:" + enteId + ", anno:" + anno + ", mese:" + mese + ", " + 
						"giorno:" + giorno + ", codUfficio:" + codUfficio + ", codTipoDovuto:" + codTipoDovuto + ", codCapitolo:" + codCapitolo + ", " +
						"codAccertamento:" + codAccertamento + "] :: START");
			
			/*
			 * Controllo se i filtri obbligatori sono valorizzati, se non lo sono lancio un eccezione per sospendere l'esecuzione della query.
			 */
			if (!StringUtils.hasText(codTipoDovuto) || enteId == null) {
				/*
				 * Interrompo l'esecuzione 
				 */
				String errMsg = "Impossibile avviare la ricerca delle RT per il dettaglio CRUSCOTTO. Parametri obbligatori assenti o non corretti: " + 
								"enteId[" + enteId + "], codTipoDovuto[" + codTipoDovuto + "]";
				/* */
				logger.error(errMsg);
				/* */
				throw new Exception(errMsg);
			}
			
			/* 
			 * Costruisce la stringa sql dei parametri da passare alla function 
			 */
			String sqlParam = buildSqlParam(enteId, anno, mese, giorno, codUfficio, codTipoDovuto, codCapitolo, codAccertamento);
			
			/* 
			 * Sql 
			 */
			String sql= "SELECT * FROM get_dettaglio_pagamenti_cruscotto(" + sqlParam + ") ";
					
			/* 
			 * Query di selezione
			 */
			Query query = entityManager.createNativeQuery(sql);
			
			/*
			 * Execute 
			 */
			List<String> ret = query.getResultList();
			
			logger.debug("DAO :: RICERCA :: LISTA RT CRUSCOTTO :: END");
			
			return ret;
		}catch(Exception e) {
			logger.error("DAO :: RICERCA :: LISTA RT CRUSCOTTO :: END", e);
			throw new Exception(e);
		}
	}
	
	/**
	 * Costruisce la stringa sql dei parametri da passare alla function
	 * 
	 * @param {@link Long}    enteId,    	   Identificativo dell'ente
	 * @param {@link Integer} anno,       	   L'anno per cui ritornare il dato statistico
	 * @param {@link Integer} mese, 	 	   Il mese per cui ritornare il dato statistico
	 * @param {@link Integer} giorno,      	   Il giorno per cui ritornare il dato statistico
	 * @param {@link String}  codUfficio, 	   Codice dell'ufficio
	 * @param {@link String}  codTipoDovuto,   Codice del tipo dovuto
	 * @param {@link String}  codCapitolo, 	   Codice del capitolo
	 * @param {@link String}  codAccertamento, Codice dell'accertamento
	 * 
	 * @return {@link String}
	 * @throws Exception
	 * @author Alessandro Paolillo
	 */
	private String buildSqlParam(Long enteId, Integer anno, Integer mese, Integer giorno, String codUfficio, String codTipoDovuto, String codCapitolo, String codAccertamento) throws Exception {
		try{
			String sql = "";      

			/* ANNO */
			if(anno != null) 
				sql += "'" + anno + "', ";
			else
				sql += "null, ";

			/* MESE */
			if(mese != null) 
				sql += "'" + mese + "', ";
			else
				sql += "null, ";	
			
			/* GIORNO */
			if(giorno != null) 
				sql += "'" + giorno + "', ";
			else
				sql += "null, ";
			
			/* CODICE UFFICIO */
			if(StringUtils.hasText(codUfficio))
				sql += "'" + codUfficio + "', ";
			else
				sql += "null, ";
		
			/* CODICE DOVUTO */
			sql += "'" + codTipoDovuto + "', ";
			
			/* CODICE CAPITOLO */
			if(StringUtils.hasText(codCapitolo)) 
				sql += "'" + codCapitolo + "', ";		
			else
				sql += "null, ";
			
			sql += "'" + enteId + "', ";
			
			/* CODICE ACCERTAMENTO */
			if(StringUtils.hasText(codAccertamento))
				sql += "'" + codAccertamento + "' ";	
			else
				sql += "null ";
			
			return sql;
		} catch (Exception e) {
			logger.error("buildSqlParam >>> ", e);
			throw (e);
		}
	}
}