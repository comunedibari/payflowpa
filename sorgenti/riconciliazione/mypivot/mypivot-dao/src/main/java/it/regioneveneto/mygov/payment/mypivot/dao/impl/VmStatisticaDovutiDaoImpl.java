package it.regioneveneto.mygov.payment.mypivot.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import it.regioneveneto.mygov.payment.mypivot.dao.VmStatisticaDovutiDao;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.cruscottoIncassi.CruscottoIncassiDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.cruscottoIncassi.CruscottoIncassiImportiDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.cruscottoIncassi.VmStatisticaDto;
import it.regioneveneto.mygov.payment.mypivot.utils.Constants;

/**
 * Query per l'interrogazione delle viste materializzate relative i dati statistici degli Importi Pagati, Rendicontati e Incassati
 * ripartiti per Tipo Dovuto e filtrati a scelta tra:
 *   - un anno
 *   - un anno ed un mese
 *   - un anno, un mese ed un giorno
 * 
 * @author Marianna Memoli
 */
@Repository
public class VmStatisticaDovutiDaoImpl implements VmStatisticaDovutiDao {

	private static final Logger logger = Logger.getLogger(VmStatisticaDovutiDaoImpl.class);

	@PersistenceContext
	private EntityManager entityManager;
	 
	/** 
	 * Restituisce l'elenco delle ripartizioni per Tipo Dovuto degli Importi Pagati, Rendicontati e Incassati relativi l'ente e l'anno indicati.
	 * 
	 * @param {@link Long}         enteId, 	      Identificativo dell'ente
	 * @param {@link Integer}      year,          L'anno per cui ritornare il dato statistico
	 * @param {@link List<String>} codTipiDovuto, Elenco dei tipi dovuto sui quali l'utente autenticato ha i permessi di operatore
	 * 
	 * @return {@link CruscottoIncassiDto}
	 * 
	 * @throws Exception
	 * @author Marianna Memoli
 	 */
	@SuppressWarnings("unchecked")
	public CruscottoIncassiDto getTotaliRipartitiPerTipiDovutoByAnno(Long enteId, Integer year, List<String> codTipiDovuto) throws Exception{
		try {
			logger.debug("DAO :: STATISTICA :: TOTALI RIPARTITI per TIPI DOVUTO :: PARAMETRI DI RICERCA[" +
						 "enteId:" + enteId + ", year:" + year + ", codTipiDovuto:" + codTipiDovuto + "] :: START");
			
			/*
			 * Controllo se i filtri obbligatori sono valorizzati, se non lo sono lancio un eccezione per sospendere l'esecuzione della query.
			 */
			if (enteId == null || year == null || codTipiDovuto == null || codTipiDovuto.isEmpty()) {
				/*
				 * Interrompo l'esecuzione 
				 */
				String errMsg = "Impossibile avviare la ricerca statistica 'TOTALI RIPARTITI per TIPI DOVUTO'. Parametri obbligatori assenti o non corretti: " + 
								"enteId[" + enteId + "], year[" + year + "], codTipiDovuto[" + codTipiDovuto + "]";
				/* */
				logger.error(errMsg);
				/* */
				throw new Exception(errMsg);
			}
			
			/*
			 * Costruisco la stringa sql
			 */
			String sql = "SELECT " +
								"mygov_ente_id, " +
								"anno, " +
								"cod_td, " + 
								"de_td, " + 
								"SUM(imp_pag)  AS imp_pag, " + 
								"SUM(imp_rend) AS imp_rend, " + 
								"SUM(imp_inc)  AS imp_inc " +
						 "FROM " + 
								"vm_statistica_ente_anno_mese_uff_td " +
						 "WHERE " +
						 		"mygov_ente_id = :enteId AND anno = :year AND cod_td in (:codTipiDovuto) " + 
						 "GROUP BY " + 
						 		"mygov_ente_id, anno, cod_td, de_td " +
						 "ORDER BY " +
						 		"de_td ";
			
			/** 
			 * Query di selezione
			 */
			Query query = entityManager.createNativeQuery(sql); 
			
			/**
			 * Set param filter
			 */
			query.setParameter("enteId", enteId);
			query.setParameter("year", year);
			query.setParameter("codTipiDovuto", codTipiDovuto);
			
			/* execute */
			List<Object[]> rows = query.getResultList();
			
			/**
			 * Setto nel DTO i valori ritornati dalla query.
			 */
			List<VmStatisticaDto> result = new ArrayList<VmStatisticaDto>();

			/**
			 * Init:
			 * 	- Totale degli Importi pagati (RT)
			 * 	- Totale degli Importi rendicontati
			 * 	- Totale degli Importi incassati
			 */
			BigDecimal totImportoPagato = BigDecimal.ZERO, totImportoRendicontato = BigDecimal.ZERO, totImportoIncassato = BigDecimal.ZERO;
			
			/*
			 */
			for (Object[] row : rows) {
				/**
				 * Get codice e descrizione
				 */
				String _codDovuto = (String) row[2]; 		String _deDovuto = (String) row[3];
				/**
				 * Get Importi
				 */
				BigDecimal importoPagato = (BigDecimal) row[4];			// Importo Pagato
				BigDecimal importoRendicontato = (BigDecimal) row[5];	// Importo Rendicontato
				BigDecimal importoIncassato = (BigDecimal) row[6];		// Importo Incassato
				/**
				 * Build DTO importi
				 */ 
				CruscottoIncassiImportiDto importiDTO = new CruscottoIncassiImportiDto();
				importiDTO.setImportoPagato("€ " + Constants.NUMBER_FORMAT_IT.format(importoPagato)); 
				importiDTO.setImportoRendicontato("€ " + Constants.NUMBER_FORMAT_IT.format(importoRendicontato)); 
				importiDTO.setImportoIncassato("€ " + Constants.NUMBER_FORMAT_IT.format(importoIncassato)); 
				
				/** 
				 */
				VmStatisticaDto item = new VmStatisticaDto();
				// Se la descrizione è nulla, visualizzo il codice
				item.setDesc(StringUtils.hasText(_deDovuto) ? _deDovuto : _codDovuto);
				//
				item.setImportiDTO(importiDTO);
				
				/* */
				result.add(item);
				
				/** 
				 * Mantengo la somma degli importi 
				 */
				totImportoPagato = totImportoPagato.add(importoPagato);
				totImportoRendicontato = totImportoRendicontato.add(importoRendicontato);
				totImportoIncassato = totImportoIncassato.add(importoIncassato);
			} 
		
			/**
			 * Set Importi Totali DTO
			 */
			CruscottoIncassiImportiDto totImportiDTO = new CruscottoIncassiImportiDto();
			totImportiDTO.setImportoPagato("€ " + Constants.NUMBER_FORMAT_IT.format(totImportoPagato));
			totImportiDTO.setImportoRendicontato("€ " + Constants.NUMBER_FORMAT_IT.format(totImportoRendicontato));
			totImportiDTO.setImportoIncassato("€ " + Constants.NUMBER_FORMAT_IT.format(totImportoIncassato));
			
			/**
			 * Return Cruscotto Incassi DTO
			 */
			CruscottoIncassiDto dto = new CruscottoIncassiDto();
			dto.setResult(result);
			dto.setTotImportiDTO(totImportiDTO);
			
			logger.debug("DAO :: STATISTICA :: TOTALI RIPARTITI per TIPI DOVUTO :: END");
			
			return dto;
		}catch(Exception e) {
			logger.error("DAO :: STATISTICA :: TOTALI RIPARTITI per TIPI DOVUTO :: ERROR ", e);
			throw new Exception(e);
		}
	}
	
	/** 
	 * Restituisce l'elenco delle ripartizioni per Tipi Dovuto degli Importi Pagati, Rendicontati e Incassati relativi l'ente nel mese 
	 * dell'anno indicati.
	 * 
	 * @param {@link Long}         enteId, 		  Identificativo dell'ente
	 * @param {@link Integer}      year,   		  L'anno per cui ritornare il dato statistico
	 * @param {@link Integer} 	   month,  		  Il mese per cui ritornare il dato statistico
	 * @param {@link List<String>} codTipiDovuto, Elenco dei tipi dovuto sui quali l'utente autenticato ha i permessi di operatore
	 * 
	 * @return {@link CruscottoIncassiDto}
	 * 
	 * @throws Exception
	 * @author Marianna Memoli
 	 */
	@SuppressWarnings("unchecked")
	public CruscottoIncassiDto getTotaliRipartitiPerTipiDovutoByAnnoMese(Long enteId, Integer year, Integer month, List<String> codTipiDovuto) throws Exception{
		try {
			logger.debug("DAO :: STATISTICA :: TOTALI RIPARTITI per TIPI DOVUTO :: PARAMETRI DI RICERCA[" + 
						 "enteId:" + enteId + ", year:" + year + ", month:" + month + ", codTipiDovuto:" + codTipiDovuto + "] :: START");
		
			/*
			 * Controllo se i filtri obbligatori sono valorizzati, se non lo sono lancio un eccezione per sospendere l'esecuzione della query.
			 */
			if (enteId == null || year == null || month == null || codTipiDovuto == null || codTipiDovuto.isEmpty()) {
				/*
				 * Interrompo l'esecuzione 
				 */
				String errMsg = "Impossibile avviare la ricerca statistica 'TOTALI RIPARTITI per TIPI DOVUTO'. Parametri obbligatori assenti o non corretti: " + 
								"enteId[" + enteId + "], year[" + year + "], month[" + month + "], codTipiDovuto[" + codTipiDovuto + "]";
				/* */
				logger.error(errMsg);
				/* */
				throw new Exception(errMsg);
			}
			
			/*
			 * Costruisco la stringa sql
			 */
			String sql = "SELECT " +
								"mygov_ente_id, " +
								"anno, " + 
								"mese, " + 
								"cod_td, " + 
								"de_td, " + 
								"SUM(imp_pag)  AS imp_pag, " + 
								"SUM(imp_rend) AS imp_rend, " + 
								"SUM(imp_inc)  AS imp_inc " +
						 "FROM " + 
								"vm_statistica_ente_anno_mese_uff_td " +
						 "WHERE " + 
								"mygov_ente_id = :enteId AND anno = :year AND mese = :month AND cod_td in (:codTipiDovuto) " + 
						 "GROUP BY " + 
						 		"mygov_ente_id, anno, mese, cod_td, de_td " +
						 "ORDER BY "+
								"de_td ";
			
			/** 
			 * Query di selezione
			 */
			Query query = entityManager.createNativeQuery(sql); 
			
			/**
			 * Set param filter
			 */
			query.setParameter("enteId", enteId);
			query.setParameter("year", year);
			query.setParameter("month", month);
			query.setParameter("codTipiDovuto", codTipiDovuto);
			
			/* execute */
			List<Object[]> rows = query.getResultList();
			
			/**
			 * Init:
			 * 	- Totale degli Importi pagati (RT)
			 * 	- Totale degli Importi rendicontati
			 * 	- Totale degli Importi incassati
			 */
			BigDecimal totImportoPagato = BigDecimal.ZERO, totImportoRendicontato = BigDecimal.ZERO, totImportoIncassato = BigDecimal.ZERO;
			
			/**
			 * Setto nel DTO i valori ritornati dalla query.
			 */
			List<VmStatisticaDto> result = new ArrayList<VmStatisticaDto>();
			
			/*
			 */
			for (Object[] row : rows) {
				/**
				 * Get codice e descrizione
				 */
				String _codDovuto = (String) row[3];			String _deDovuto = (String) row[4];
				/**
				 * Get Importi
				 */
				BigDecimal importoPagato = (BigDecimal) row[5];			// Importo Pagato
				BigDecimal importoRendicontato = (BigDecimal) row[6];	// Importo Rendicontato
				BigDecimal importoIncassato = (BigDecimal) row[7];		// Importo Incassato
				/**
				 * Build DTO importi
				 */
				CruscottoIncassiImportiDto importiDTO = new CruscottoIncassiImportiDto();
				importiDTO.setImportoPagato("€ " + Constants.NUMBER_FORMAT_IT.format(importoPagato)); 
				importiDTO.setImportoRendicontato("€ " + Constants.NUMBER_FORMAT_IT.format(importoRendicontato)); 
				importiDTO.setImportoIncassato("€ " + Constants.NUMBER_FORMAT_IT.format(importoIncassato)); 
				
				/** 
				 */
				VmStatisticaDto item = new VmStatisticaDto();
				// Se la descrizione è nulla, visualizzo il codice
				item.setDesc(StringUtils.hasText(_deDovuto) ? _deDovuto : _codDovuto);
				//
				item.setImportiDTO(importiDTO);
				
				/* */
				result.add(item);
				
				/** 
				 * Mantengo la somma degli importi 
				 */
				totImportoPagato = totImportoPagato.add(importoPagato);
				totImportoRendicontato = totImportoRendicontato.add(importoRendicontato);
				totImportoIncassato = totImportoIncassato.add(importoIncassato);
			}// close for
		
			/**
			 * Set Importi Totali DTO
			 */
			CruscottoIncassiImportiDto totImportiDTO = new CruscottoIncassiImportiDto();
			totImportiDTO.setImportoPagato("€ " + Constants.NUMBER_FORMAT_IT.format(totImportoPagato));
			totImportiDTO.setImportoRendicontato("€ " + Constants.NUMBER_FORMAT_IT.format(totImportoRendicontato));
			totImportiDTO.setImportoIncassato("€ " + Constants.NUMBER_FORMAT_IT.format(totImportoIncassato));
			
			/**
			 * Return Cruscotto Incassi DTO
			 */
			CruscottoIncassiDto dto = new CruscottoIncassiDto();
			dto.setResult(result);
			dto.setTotImportiDTO(totImportiDTO);
			
			logger.debug("DAO :: STATISTICA :: TOTALI RIPARTITI per TIPI DOVUTO :: END");
			
			return dto;
		}catch(Exception e) {
			logger.error("DAO :: STATISTICA :: TOTALI RIPARTITI per TIPI DOVUTO :: ERROR ", e);
			throw new Exception(e);
		}
	}
	
	/**
	 * Restituisce l'elenco delle ripartizioni per Tipi Dovuto degli Importi Pagati, Rendicontati e Incassati relativi l'ente e il giorno
	 * nel mese dell'anno indicati.
	 * 
	 * @param {@link Long}    	   enteId, 		  Identificativo dell'ente
	 * @param {@link Integer} 	   year,   		  L'anno per cui ritornare il dato statistico
	 * @param {@link Integer} 	   month,  		  Il mese per cui ritornare il dato statistico
	 * @param {@link Integer} 	   day,    		  Il giorno per cui ritornare il dato statistico
	 * @param {@link List<String>} codTipiDovuto, Elenco dei tipi dovuto sui quali l'utente autenticato ha i permessi di operatore
	 * 
	 * @return {@link CruscottoIncassiDto}
	 * 
	 * @throws Exception
	 * @author Marianna Memoli
 	 */
	@SuppressWarnings("unchecked")
	public CruscottoIncassiDto getTotaliRipartitiPerTipiDovutoByAnnoMeseGiorno(Long enteId,  Integer year, Integer month, Integer day, List<String> codTipiDovuto) throws Exception {
		try {
			logger.debug("DAO :: STATISTICA :: TOTALI RIPARTITI per TIPI DOVUTO :: PARAMETRI DI RICERCA[" + 
						 "enteId:" + enteId + ", year:" + year + ", month:" + month  + ", day:" + day + ", codTipiDovuto:" + codTipiDovuto + 
						 "] :: START");
			
			/*
			 * Controllo se i filtri obbligatori sono valorizzati, se non lo sono lancio un eccezione per sospendere l'esecuzione della query.
			 */
			if (enteId == null || year == null || month == null || day == null || codTipiDovuto == null || codTipiDovuto.isEmpty()) {
				/*
				 * Interrompo l'esecuzione 
				 */
				String errMsg = "Impossibile avviare la ricerca statistica 'TOTALI RIPARTITI per TIPI DOVUTO'. Parametri obbligatori assenti o non corretti: " + 
								"enteId[" + enteId + "], year[" + year + "], month[" + month + "], day[" + day + "], codTipiDovuto[" + codTipiDovuto + "]";
				/* */
				logger.error(errMsg);
				/* */
				throw new Exception(errMsg);
			}
			
			/*
			 * Costruisco la stringa sql
			 */
			String sql = "SELECT " + 
								"mygov_ente_id, " +
								"anno, " + 
								"mese, " + 
								"giorno, " +
								"cod_td, " + 
								"de_td, " + 
								"SUM(imp_pag)  AS imp_pag, " + 
								"SUM(imp_rend) AS imp_rend, " + 
								"SUM(imp_inc)  AS imp_inc " +
						 "FROM " + 
								"vm_statistica_ente_anno_mese_giorno_uff_td " +
						 "WHERE " + 
						 		"mygov_ente_id = :enteId AND anno = :year AND mese = :month AND giorno = :day AND cod_td in (:codTipiDovuto) " + 
						 "GROUP BY " + 
						 		"mygov_ente_id, anno, mese, giorno, cod_td, de_td " +
						 "ORDER BY " +
								"de_td";
			
			/** 
			 * Query di selezione
			 */
			Query query = entityManager.createNativeQuery(sql); 
			
			/**
			 * Set param filter
			 */
			query.setParameter("enteId", enteId);
			query.setParameter("year", year);
			query.setParameter("month", month);
			query.setParameter("day", day);
			query.setParameter("codTipiDovuto", codTipiDovuto);
			
			/* execute */
			List<Object[]> rows = query.getResultList();
			
			/**
			 * Setto nel DTO i valori ritornati dalla query.
			 */
			List<VmStatisticaDto> result = new ArrayList<VmStatisticaDto>();

			/**
			 * Init:
			 * 	- Totale degli Importi pagati (RT)
			 * 	- Totale degli Importi rendicontati
			 * 	- Totale degli Importi incassati
			 */
			BigDecimal totImportoPagato = BigDecimal.ZERO, totImportoRendicontato = BigDecimal.ZERO, totImportoIncassato = BigDecimal.ZERO;
			
			/*
			 */
			for (Object[] row : rows) {
				/**
				 * Get codice e descrizione
				 */
				String _codDovuto = (String) row[4];			String _deDovuto = (String) row[5];
				/**
				 * Get Importi
				 */
				BigDecimal importoPagato = (BigDecimal) row[6];			// Importo Pagato
				BigDecimal importoRendicontato = (BigDecimal) row[7];	// Importo Rendicontato
				BigDecimal importoIncassato = (BigDecimal) row[8];		// Importo Incassato
				/**
				 * Build DTO importi
				 */ 
				CruscottoIncassiImportiDto importiDTO = new CruscottoIncassiImportiDto();
				importiDTO.setImportoPagato("€ " + Constants.NUMBER_FORMAT_IT.format(importoPagato)); 
				importiDTO.setImportoRendicontato("€ " + Constants.NUMBER_FORMAT_IT.format(importoRendicontato)); 
				importiDTO.setImportoIncassato("€ " + Constants.NUMBER_FORMAT_IT.format(importoIncassato)); 
				
				/** 
				 */
				VmStatisticaDto item = new VmStatisticaDto();
				// Se la descrizione è nulla, visualizzo il codice
				item.setDesc(StringUtils.hasText(_deDovuto) ? _deDovuto : _codDovuto);
				//
				item.setImportiDTO(importiDTO);
				
				/* */
				result.add(item);
				
				/** 
				 * Mantengo la somma degli importi 
				 */
				totImportoPagato = totImportoPagato.add(importoPagato);
				totImportoRendicontato = totImportoRendicontato.add(importoRendicontato);
				totImportoIncassato = totImportoIncassato.add(importoIncassato);
			}// close for
		
			/**
			 * Set Importi Totali DTO
			 */
			CruscottoIncassiImportiDto totImportiDTO = new CruscottoIncassiImportiDto();
			totImportiDTO.setImportoPagato("€ " + Constants.NUMBER_FORMAT_IT.format(totImportoPagato));
			totImportiDTO.setImportoRendicontato("€ " + Constants.NUMBER_FORMAT_IT.format(totImportoRendicontato));
			totImportiDTO.setImportoIncassato("€ " + Constants.NUMBER_FORMAT_IT.format(totImportoIncassato));
			
			/**
			 * Set Cruscotto Incassi DTO
			 */
			CruscottoIncassiDto dto = new CruscottoIncassiDto();
			dto.setResult(result);
			dto.setTotImportiDTO(totImportiDTO);
			
			logger.debug("DAO :: STATISTICA :: TOTALI RIPARTITI per TIPI DOVUTOO :: END");
			
			return dto;
		}catch(Exception e) {
			logger.error("DAO :: STATISTICA :: TOTALI RIPARTITI per TIPI DOVUTO :: ERROR ", e);
			throw new Exception(e);
		}
	}
}