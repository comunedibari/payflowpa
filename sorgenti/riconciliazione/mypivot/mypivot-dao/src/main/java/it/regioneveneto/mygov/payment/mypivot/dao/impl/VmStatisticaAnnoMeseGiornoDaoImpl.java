package it.regioneveneto.mygov.payment.mypivot.dao.impl;

import java.math.BigDecimal;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import it.regioneveneto.mygov.payment.mypivot.dao.VmStatisticaAnnoMeseGiornoDao;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.cruscottoIncassi.CruscottoIncassiAnnoMeseGiornoDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.cruscottoIncassi.CruscottoIncassiImportiDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.cruscottoIncassi.VmStatisticaAnnoMeseGiornoDto;
import it.regioneveneto.mygov.payment.mypivot.utils.Constants;

/**
 * Query per l'interrogazione delle viste materializzate relative le seguenti tipologie di statistiche:
 * 
 *    - TOTALI per ANNO
 *    - TOTALI per MESE
 *    - TOTALI per GIORNO
 * 
 * @author Marianna Memoli
 */
@Repository
public class VmStatisticaAnnoMeseGiornoDaoImpl implements VmStatisticaAnnoMeseGiornoDao {

	private static final Logger logger = Logger.getLogger(VmStatisticaAnnoMeseGiornoDaoImpl.class);

	@PersistenceContext
	private EntityManager entityManager;
	 
	/**
	 * Restituisce l'elenco degli importi Pagati, Rendicontati e Incassati relativi l'ente per gli anni indicati.
	 * 
	 * @param {@link Long}     	    enteId, Identificativo dell'ente
	 * @param {@link List<Integer>} years,  Elenco anni per cui ritornare il dato statistico
	 * 
	 * @return {@link CruscottoIncassiAnnoMeseGiornoDto}
	 * 
	 * @throws Exception
	 * @author Marianna Memoli
 	 */
	@SuppressWarnings("unchecked")
	public CruscottoIncassiAnnoMeseGiornoDto getTotaliPerAnno(Long enteId, List<Integer> years) throws Exception {
		try {
			logger.debug("DAO :: STATISTICA :: TOTALI per ANNO :: PARAMETRI DI RICERCA[ enteId:" + enteId + ", " + 
						 "years:" + (years != null ? StringUtils.collectionToDelimitedString(years, "/") : null) + "] :: START");
			
			/*
			 * Controllo se i filtri obbligatori sono valorizzati, se non lo sono lancio un eccezione per sospendere l'esecuzione della query.
			 */
			if (enteId == null || years == null || years.isEmpty()) {
				/*
				 * Interrompo l'esecuzione 
				 */
				String errMsg = "Impossibile avviare la ricerca statistica 'TOTALI per ANNO'. Parametri obbligatori assenti o non corretti: " + 
								"enteId[" + enteId + "], years[" + (years != null ? "0 elementi" : null) + "]";
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
								"SUM(num_pag)  AS num_pag,  " +
								"SUM(imp_pag)  AS imp_pag,  " + 
								"SUM(imp_rend) AS imp_rend, " + 
								"SUM(imp_inc)  AS imp_inc 	" +
						 "FROM " + 
								"vm_statistica_ente_anno_mese " +
						 "WHERE " + 
								"mygov_ente_id = :enteId AND anno in (:years) " + 
						 "GROUP BY " + 
								"mygov_ente_id, anno " +
						 "ORDER BY " + 
								"anno ";
			
			/** 
			 * Query di selezione
			 */
			Query query = entityManager.createNativeQuery(sql); 
			
			/**
			 * Set param filter
			 */
			query.setParameter("enteId", enteId);
			query.setParameter("years", years);
			
			/* execute */
			List<Object[]> rows = query.getResultList();
			
			/**
			 * Setto nel DTO i valori ritornati dalla query.
			 */
			List<VmStatisticaAnnoMeseGiornoDto> result = new ArrayList<VmStatisticaAnnoMeseGiornoDto>();

			/**
			 * Init:
			 * - Totale numero pagamenti
			 * - Totele degli Importi pagati (RT)
			 * - Totale degli Importi rendicontati
			 * - Totale degli Importi incassati
			 */
			BigDecimal totNumPagamenti = new BigDecimal(0), totImportoPagato = new BigDecimal(0), totImportoRendicontato = new BigDecimal(0), totImportoIncassato = new BigDecimal(0);
			
			/*
			 */
			for (Object[] row : rows) {
				/**
				 */
				BigDecimal importoPagato = (BigDecimal) row[3]; 		// Importo pagato
				BigDecimal importoRendicontato = (BigDecimal) row[4];	// Importo rendicontato
				BigDecimal importoIncassato = (BigDecimal) row[5];		// Importo incassato

				/*
				 * Build DTO importi
				 */ 
				CruscottoIncassiImportiDto importiDTO = new CruscottoIncassiImportiDto();
				importiDTO.setImportoPagato("€ " + Constants.NUMBER_FORMAT_IT.format(importoPagato)); 
				importiDTO.setImportoRendicontato("€ " + Constants.NUMBER_FORMAT_IT.format(importoRendicontato)); 
				importiDTO.setImportoIncassato("€ " + Constants.NUMBER_FORMAT_IT.format(importoIncassato)); 
				
				/** 
				 */
				VmStatisticaAnnoMeseGiornoDto item = new VmStatisticaAnnoMeseGiornoDto();
				//
				item.setAnno(String.valueOf(row[1]));
				//
				item.setNumPagamenti(Constants.NUMBER_FORMAT_IT.format(row[2]));
				//
				item.setImportiDTO(importiDTO);
				
				/* */
				result.add(item);
				
				/**
				 * Mantengo la somma degli importi 
				 */
				totNumPagamenti = totNumPagamenti.add((BigDecimal) row[2]);
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
			CruscottoIncassiAnnoMeseGiornoDto dto = new CruscottoIncassiAnnoMeseGiornoDto();
			dto.setResult(result);
			dto.setTotNumPagamenti(Constants.NUMBER_FORMAT_IT.format(totNumPagamenti)); // Totale Numero RT
			dto.setTotImportiDTO(totImportiDTO);
			
			logger.debug("DAO :: STATISTICA :: TOTALI per ANNO :: END");
			
			return dto;
		}catch(Exception e) {
			logger.error("DAO :: STATISTICA :: TOTALI per ANNO :: ERROR ", e);
			throw new Exception(e);
		}
	}
	
	/** 
	 * Restituisce l'elenco degli importi Pagati, Rendicontati e Incassati relativi l'ente nei mese dell'anno indicati.
	 * 
	 * @param {@link Long}     	    enteId, Identificativo dell'ente
	 * @param {@link Integer} 	    year,  	L'anno d'interesse
	 * @param {@link List<Integer>} months, Elenco mesi dell'anno per cui ritornare il dato statistico
	 * 
	 * @return {@link CruscottoIncassiAnnoMeseGiornoDto}
	 * 
	 * @throws Exception
	 * @author Marianna Memoli
 	 */
	@SuppressWarnings("unchecked")
	public CruscottoIncassiAnnoMeseGiornoDto getTotaliPerAnnoMese(Long enteId, Integer year, List<Integer> months) throws Exception{
		try {
			logger.debug("DAO :: STATISTICA :: TOTALI per MESE :: PARAMETRI DI RICERCA[" + 
						 "enteId:" + enteId + ", year:" + year + ", " +
						 "months:" + (months != null ? StringUtils.collectionToDelimitedString(months, "/") : null) + "] :: START");
			
			/*
			 * Controllo se i filtri obbligatori sono valorizzati, se non lo sono lancio un eccezione per sospendere l'esecuzione della query.
			 */
			if (enteId == null || year == null) {
				/*
				 * Interrompo l'esecuzione 
				 */
				String errMsg = "Impossibile avviare la ricerca statistica 'TOTALI per MESE'. Parametri obbligatori assenti o non corretti: " + 
								"enteId[" + enteId + "], year[" + year + "]";
				/* */
				logger.error(errMsg);
				/* */
				throw new Exception(errMsg);
			}
			
			/*
			 * Costruisco la stringa sql
			 */
			String sql = "SELECT " + 
								"mygov_ente_id, anno, mese, num_pag, imp_pag, imp_rend, imp_inc " +
						 "FROM " +
								"vm_statistica_ente_anno_mese " +
						 "WHERE " +
								"mygov_ente_id = :enteId AND " + 
								"anno = :year " + 
								((months != null && !months.isEmpty()) ? "AND mese in (:months) " : "") +
						 "ORDER BY " +
								"mese ";
			
			/** 
			 * Query di selezione
			 */
			Query query = entityManager.createNativeQuery(sql); 
			
			/**
			 * Set param filter
			 */
			query.setParameter("enteId", enteId);
			query.setParameter("year", year);
			
			if(months != null && !months.isEmpty()) query.setParameter("months", months);
			
			/* execute */
			List<Object[]> rows = query.getResultList();
			
			/**
			 * Init:
			 * 	- Totale numero pagamenti
			 * 	- Totele degli Importi pagati (RT)
			 * 	- Totale degli Importi rendicontati
			 * 	- Totale degli Importi incassati
			 */
			BigDecimal totNumPagamenti = BigDecimal.ZERO, totImportoPagato = BigDecimal.ZERO, totImportoRendicontato = BigDecimal.ZERO, totImportoIncassato = BigDecimal.ZERO;
			
			/**
			 * Setto nel DTO i valori ritornati dalla query.
			 */
			List<VmStatisticaAnnoMeseGiornoDto> result = new ArrayList<VmStatisticaAnnoMeseGiornoDto>();
			
			String[] monthNames = new DateFormatSymbols(Locale.ITALIAN).getMonths();
					
			/*
			 */
			for (Object[] row : rows) {
				/** 
				 */
				BigDecimal importoPagato = (BigDecimal) row[4];			// Importo pagato
				BigDecimal importoRendicontato = (BigDecimal) row[5];	// Importo rendicontato
				BigDecimal importoIncassato = (BigDecimal) row[6];		// Importo incassato
				
				/*
				 * Build DTO importi
				 */
				CruscottoIncassiImportiDto importiDTO = new CruscottoIncassiImportiDto();
				importiDTO.setImportoPagato("€ " + Constants.NUMBER_FORMAT_IT.format(importoPagato)); 
				importiDTO.setImportoRendicontato("€ " + Constants.NUMBER_FORMAT_IT.format(importoRendicontato)); 
				importiDTO.setImportoIncassato("€ " + Constants.NUMBER_FORMAT_IT.format(importoIncassato)); 
				
				/**
				 */
				VmStatisticaAnnoMeseGiornoDto item = new VmStatisticaAnnoMeseGiornoDto();
				//
				item.setAnno(String.valueOf(row[1]));
				//
				item.setMese(String.valueOf(row[2]));
				//
				item.setNumPagamenti(Constants.NUMBER_FORMAT_IT.format(row[3]));
				//
				item.setImportiDTO(importiDTO);
				
				int month = (int) row[2];
				// 
				item.setLabelMese(monthNames[month-1]);
				
				/* */
				result.add(item);
				
				/**
				 * Mantengo la somma degli importi
				 */
				totNumPagamenti = totNumPagamenti.add((BigDecimal) row[3]);
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
			CruscottoIncassiAnnoMeseGiornoDto dto = new CruscottoIncassiAnnoMeseGiornoDto();
			dto.setResult(result);
			dto.setTotNumPagamenti(Constants.NUMBER_FORMAT_IT.format(totNumPagamenti)); 	// Totale Numero RT 
			dto.setTotImportiDTO(totImportiDTO);
			
			logger.debug("DAO :: STATISTICA :: TOTALI per MESE :: END");
			
			return dto;
		}catch(Exception e) {
			logger.error("DAO :: STATISTICA :: TOTALI per MESE :: ERROR ", e);
			throw new Exception(e);
		}
	}
	
	/**
	 * Restituisce l'elenco degli importi Pagati, Rendicontati e Incassati relativi i giorni che vanno dalla data d'inizio e fine estrazione.
	 * 
	 * @param {@link Long}   enteId,  Identificativo dell'ente
	 * @param {@link String} fromDay, Data (DD/MM/YYYY) d'inizio estrazione
	 * @param {@link String} toDay,   Data (DD/MM/YYYY) fine estrazione
	 * 
	 * @return {@link CruscottoIncassiAnnoMeseGiornoDto}
	 * 
	 * @throws Exception
	 * @author Marianna Memoli
 	 */
	@SuppressWarnings("unchecked")
	public CruscottoIncassiAnnoMeseGiornoDto getTotaliPerAnnoMeseGiorno(Long enteId, String fromDay, String toDay) throws Exception {
		try {
			logger.debug("DAO :: STATISTICA :: TOTALI per GIORNO :: PARAMETRI DI RICERCA[" + 
						 "enteId:" + enteId + ", fromDay:" + fromDay + ", toDay:" + toDay + "] :: START");
			
			/*
			 * Controllo se i filtri obbligatori sono valorizzati, se non lo sono lancio un eccezione per sospendere l'esecuzione della query.
			 */
			if (enteId == null || !StringUtils.hasText(fromDay) || !StringUtils.hasText(toDay)) {
				/*
				 * Interrompo l'esecuzione 
				 */
				String errMsg = "Impossibile avviare la ricerca statistica 'TOTALI per GIORNO'. Parametri obbligatori assenti o non corretti: " + 
								"enteId[" + enteId + "], fromDay[" + fromDay + "], toDay[" + toDay + "]";
				/* */
				logger.error(errMsg);
				/* */
				throw new Exception(errMsg);
			}
			
			/*
			 * Costruisco la stringa sql
			 */
			String sql = "SELECT " + 
								"mygov_ente_id, anno, mese, giorno, num_pag, imp_pag, imp_rend, imp_inc " +
						 "FROM " + 
								"vm_statistica_ente_anno_mese_giorno " +
						 "WHERE " + 
								"mygov_ente_id = :enteId AND " +
								"to_date(anno || '-' || mese || '-' || giorno, 'YYYY-MM-DD') BETWEEN :fromDay AND :toDay " + 
						 "ORDER BY " +
								"to_date(anno || '-' || mese || '-' || giorno, 'YYYY-MM-DD') ";
			
			/** 
			 * Query di selezione
			 */
			Query query = entityManager.createNativeQuery(sql); 
			
			/**
			 * Set param filter
			 */
			query.setParameter("enteId", enteId);
			query.setParameter("fromDay", Constants.DDMMYYYY.parse(fromDay));
			query.setParameter("toDay", Constants.DDMMYYYY.parse(toDay));
			
			/* execute */
			List<Object[]> rows = query.getResultList();
			
			/**
			 * Setto nel DTO i valori ritornati dalla query.
			 */
			List<VmStatisticaAnnoMeseGiornoDto> result = new ArrayList<VmStatisticaAnnoMeseGiornoDto>();

			/**
			 * Init:
			 * 	- Totale numero pagamenti
			 * 	- Totele degli Importi pagati (RT)
			 * 	- Totale degli Importi rendicontati
			 * 	- Totale degli Importi incassati
			 */
			BigDecimal totNumPagamenti = BigDecimal.ZERO, totImportoPagato = BigDecimal.ZERO, totImportoRendicontato = BigDecimal.ZERO, totImportoIncassato = BigDecimal.ZERO;
			
			/*
			 */
			for (Object[] row : rows) {
				/** 
				 */
				BigDecimal importoPagato = (BigDecimal) row[5];			// importo pagato
				BigDecimal importoRendicontato = (BigDecimal) row[6];	// importo rendicontato
				BigDecimal importoIncassato = (BigDecimal) row[7];		// importo incassato
				
				/*
				 * Build DTO importi
				 */ 
				CruscottoIncassiImportiDto importiDTO = new CruscottoIncassiImportiDto();
				importiDTO.setImportoPagato("€ " + Constants.NUMBER_FORMAT_IT.format(importoPagato)); 
				importiDTO.setImportoRendicontato("€ " + Constants.NUMBER_FORMAT_IT.format(importoRendicontato)); 
				importiDTO.setImportoIncassato("€ " + Constants.NUMBER_FORMAT_IT.format(importoIncassato)); 
				
				/**
				 */
				VmStatisticaAnnoMeseGiornoDto item = new VmStatisticaAnnoMeseGiornoDto();
				//
				item.setAnno(String.valueOf(row[1]));
				//
				item.setMese(String.valueOf(row[2]));
				//
				item.setGiorno(String.valueOf(row[3]));
				//
				item.setNumPagamenti(Constants.NUMBER_FORMAT_IT.format(row[4]));
				//
				item.setImportiDTO(importiDTO);
				
				/** **/
				result.add(item);
				
				/** 
				 * Mantengo la somma degli importi 
				 */
				totNumPagamenti = totNumPagamenti.add((BigDecimal) row[4]);
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
			CruscottoIncassiAnnoMeseGiornoDto dto = new CruscottoIncassiAnnoMeseGiornoDto();
			dto.setResult(result);
			dto.setTotNumPagamenti(Constants.NUMBER_FORMAT_IT.format(totNumPagamenti)); 	// Totale Numero RT
			dto.setTotImportiDTO(totImportiDTO);
			
			logger.debug("DAO :: STATISTICA :: TOTALI per GIORNO :: END");
			
			return dto;
		}catch(Exception e) {
			logger.error("DAO :: STATISTICA :: TOTALI per GIORNO :: ERROR ", e);
			throw new Exception(e);
		}
	}
}