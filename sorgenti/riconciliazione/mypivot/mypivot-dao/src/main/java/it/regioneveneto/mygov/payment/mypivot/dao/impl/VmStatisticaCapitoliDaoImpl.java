package it.regioneveneto.mygov.payment.mypivot.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
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

import it.regioneveneto.mygov.payment.mypivot.dao.VmStatisticaCapitoliDao;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.cruscottoIncassi.CruscottoIncassiDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.cruscottoIncassi.CruscottoIncassiImportiDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.cruscottoIncassi.CruscottoIncassiMultiRowCol;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.cruscottoIncassi.VmStatisticaDto;
import it.regioneveneto.mygov.payment.mypivot.utils.Constants;

/**
 * Query per l'interrogazione delle viste materializzate relative i dati statistici degli Importi Pagati, Rendicontati e Incassati
 * ripartiti per Capitoli e filtrati per un ufficio e/o un tipo dovuto e a scelta tra:
 *   - un anno
 *   - un anno ed un mese
 *   - un anno, un mese ed un giorno
 * 
 * @author Marianna Memoli
 */
@Repository
public class VmStatisticaCapitoliDaoImpl implements VmStatisticaCapitoliDao {

	private static final Logger logger = Logger.getLogger(VmStatisticaCapitoliDaoImpl.class);

	@PersistenceContext
	private EntityManager entityManager;
	
/* SERVICE Totali Ripartiti Per Capitoli By Ufficio e Dovuto ================================================================================== */
	
	/** 
	 * Restituisce l'elenco delle ripartizioni per Capitoli degli Importi Pagati, Rendicontati e Incassati relativi l'ente e l'anno indicati.
	 * Si richiede di selezionare, un ufficio e un tipo dovuto.
	 * 
	 * @param {@link Long}    enteId, 	  	 Identificativo dell'ente
	 * @param {@link Integer} year,       	 L'anno per cui ritornare il dato statistico
	 * @param {@link String}  codUfficio,    Codice ufficio
	 * @param {@link String}  codTipoDovuto, Codice del tipo dovuto
	 * 
	 * @return {@link CruscottoIncassiDtO}
	 * 
	 * @throws Exception
	 * @author Marianna Memoli
 	 */
	@SuppressWarnings("unchecked")
	public CruscottoIncassiDto getTotaliRipartitiPerCapitoliByAnnoUfficioDovuto(Long enteId, Integer year, String codUfficio, String codTipoDovuto) throws Exception{
		try {
			logger.debug("DAO :: STATISTICA :: TOTALI RIPARTITI per CAPITOLI :: PARAMETRI DI RICERCA[" + 
						 "enteId:" + enteId + ", year:" + year + ", codUfficio:" + codUfficio + ", codTipoDovuto:" + codTipoDovuto + "] :: START");
			
			/*
			 * Controllo se i filtri obbligatori sono valorizzati, se non lo sono lancio un eccezione per sospendere l'esecuzione della query.
			 */
			if (enteId == null || year == null || !StringUtils.hasText(codUfficio) || !StringUtils.hasText(codTipoDovuto)) {
				/*
				 * Interrompo l'esecuzione 
				 */
				String errMsg = "Impossibile avviare la ricerca statistica 'TOTALI RIPARTITI per CAPITOLI'. Parametri obbligatori assenti o non corretti: " + 
								"enteId[" + enteId + "], year[" + year + "], codUfficio[" + codUfficio + "], codTipoDovuto[" + codTipoDovuto + "]";
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
								"cod_uff, " + 
								"de_uff, " + 
								"cod_td, " +
								"de_td, " + 
								"cod_cap, " + 
								"de_cap, " + 
								"SUM(imp_pag)  AS imp_pag, " + 
								"SUM(imp_rend) AS imp_rend, " + 
								"SUM(imp_inc)  AS imp_inc " +
						 "FROM " + 
								"vm_statistica_ente_anno_mese_uff_td_cap " +
						 "WHERE " +
						 		"mygov_ente_id = :enteId AND anno = :year AND cod_uff = :codUfficio AND cod_td = :codTipoDovuto " + 
						 "GROUP BY " + 
						 		"mygov_ente_id, anno, cod_uff, de_uff, cod_td, de_td, cod_cap, de_cap " + 
						 "ORDER BY " +
						 		"de_cap ";
			
			/** 
			 * Query di selezione
			 */
			Query query = entityManager.createNativeQuery(sql); 
			
			/**
			 * Set param filter
			 */
			query.setParameter("enteId", enteId);
			query.setParameter("year", year);
			query.setParameter("codUfficio", codUfficio);
			query.setParameter("codTipoDovuto", codTipoDovuto);
			
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
			
			/**
			 * Descrizione dell'ufficio e del tipo dovuto selezionati come filtro per la ricerca statistica.
			 */
			String _deUfficioFiltered = null, _deTipoDovutoFiltered = null;
			
			/*
			 */
			for (Object[] row : rows) {
				/**
				 * Get codici e descrizioni
				 */
				String _codUfficio = (String) row[2];  	  String _codTipoDovuto = (String) row[4];		String _codCapitolo = (String) row[6]; 	 	
				String _deUfficio = (String) row[3];	  String _deTipoDovuto = (String) row[5];	    String _deCapitolo = (String) row[7];
				/**
				 * Get Importi
				 */
				BigDecimal importoPagato = (BigDecimal) row[8];			// Importo Pagato
				BigDecimal importoRendicontato = (BigDecimal) row[9];	// Importo Rendicontato
				BigDecimal importoIncassato = (BigDecimal) row[10];		// Importo Incassato
				/**
				 * Get if detail RT is enable. Per accedere al dettaglio L'importo deve essere <= 5000 e diverso da ZERO.
				 * 
				 * Compare To ->> Returns: -1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.
				 */
				boolean enableDttImpPagato = importoPagato.compareTo(new BigDecimal(5000)) > 0     || importoPagato.compareTo(BigDecimal.ZERO) == 0 ? Boolean.FALSE : Boolean.TRUE;
				boolean enableDttImpRend = importoRendicontato.compareTo(new BigDecimal(5000)) > 0 || importoRendicontato.compareTo(BigDecimal.ZERO) == 0 ? Boolean.FALSE : Boolean.TRUE;
				boolean enableDttImpInc = importoIncassato.compareTo(new BigDecimal(5000)) > 0     || importoIncassato.compareTo(BigDecimal.ZERO) == 0 ? Boolean.FALSE : Boolean.TRUE;
				
				/**
				 * Build DTO importi
				 */ 
				CruscottoIncassiImportiDto importiDTO = new CruscottoIncassiImportiDto();
				importiDTO.setImportoPagato("€ " + Constants.NUMBER_FORMAT_IT.format(importoPagato)); 
				importiDTO.setImportoRendicontato("€ " + Constants.NUMBER_FORMAT_IT.format(importoRendicontato)); 
				importiDTO.setImportoIncassato("€ " + Constants.NUMBER_FORMAT_IT.format(importoIncassato)); 
				//
				importiDTO.setEnableDttImportoPagato(enableDttImpPagato);
				importiDTO.setEnableDttImportoRend(enableDttImpRend);
				importiDTO.setEnableDttImportoInc(enableDttImpInc);
				
				/** 
				 */
				VmStatisticaDto item = new VmStatisticaDto();
				// Se la descrizione è nulla, visualizzo il codice
				item.setDesc(StringUtils.hasText(_deCapitolo)? _deCapitolo : _codCapitolo);
				//
				item.setCodice(_codCapitolo);
				//
				item.setImportiDTO(importiDTO);
				
				/* */
				result.add(item);
				
				/** 
				 * Manteno la descrizione dell'ufficio e del tipo dovuto selezionati
				 */
				_deUfficioFiltered = StringUtils.hasText(_deUfficio) ? _deUfficio : _codUfficio;
				_deTipoDovutoFiltered = StringUtils.hasText(_deTipoDovuto) ? _deTipoDovuto : _codTipoDovuto;
				
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
			dto.setDeUfficioFiltered(_deUfficioFiltered);
			dto.setDeTipoDovutoFiltered(_deTipoDovutoFiltered);
			
			logger.debug("DAO :: STATISTICA :: TOTALI RIPARTITI per CAPITOLI :: END");
			
			return dto;
		}catch(Exception e) {
			logger.error("DAO :: STATISTICA :: TOTALI RIPARTITI per CAPITOLI :: ERROR ", e);
			throw new Exception(e);
		}
	}
	
	/** 
	 * Restituisce l'elenco delle ripartizioni per Capitoli degli Importi Pagati, Rendicontati e Incassati relativi l'ente e il mese dell'anno indicati.
	 * Si richiede di selezionare, un ufficio e un tipo dovuto.
	 * 
	 * @param {@link Long}    enteId, 	  	 Identificativo dell'ente
	 * @param {@link Integer} year,       	 L'anno per cui ritornare il dato statistico
	 * @param {@link Integer} month, 	 	 Il mese per cui ritornare il dato statistico
	 * @param {@link String}  codUfficio,    Codice ufficio
	 * @param {@link String}  codTipoDovuto, Codice del tipo dovuto
	 * 
	 * @return {@link CruscottoIncassiDto}
	 * 
	 * @throws Exception
	 * @author Marianna Memoli
 	 */
	@SuppressWarnings("unchecked")
	public CruscottoIncassiDto getTotaliRipartitiPerCapitoliByAnnoMeseUfficioDovuto(Long enteId, Integer year, Integer month, String codUfficio, String codTipoDovuto) throws Exception{
		try {
			logger.debug("DAO :: STATISTICA :: TOTALI RIPARTITI per CAPITOLI :: PARAMETRI DI RICERCA[" + 
					 	 "enteId:" + enteId + ", year:" + year + ", month:" + month + ", codUfficio:" + codUfficio + ", codTipoDovuto: " + codTipoDovuto + 
					 	 "] :: START");
		
			/*
			 * Controllo se i filtri obbligatori sono valorizzati, se non lo sono lancio un eccezione per sospendere l'esecuzione della query.
			 */
			if (enteId == null || year == null || month == null || !StringUtils.hasText(codUfficio) || !StringUtils.hasText(codTipoDovuto)) {
				/*
				 * Interrompo l'esecuzione 
				 */
				String errMsg = "Impossibile avviare la ricerca statistica 'TOTALI RIPARTITI per CAPITOLI'. Parametri obbligatori assenti o non corretti: " + 
								"enteId[" + enteId + "], year[" + year + "], month[" + month + "], codUfficio[" + codUfficio + "], codTipoDovuto[" + codTipoDovuto + "]";
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
								"cod_uff, " + 
								"de_uff, " + 
								"cod_td, " +
								"de_td, " + 
								"cod_cap, " + 
								"de_cap, " + 
								"SUM(imp_pag)  AS imp_pag, " + 
								"SUM(imp_rend) AS imp_rend, " + 
								"SUM(imp_inc)  AS imp_inc " +
						 "FROM " + 
								"vm_statistica_ente_anno_mese_uff_td_cap " +
						 "WHERE " +
						 		"mygov_ente_id = :enteId AND anno = :year AND mese = :month AND cod_uff = :codUfficio AND cod_td = :codTipoDovuto " + 
						 "GROUP BY " + 
						 		"mygov_ente_id, anno, mese, cod_uff, de_uff, cod_td, de_td, cod_cap, de_cap " + 
						 "ORDER BY " +
						 		"de_cap ";
			
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
			query.setParameter("codTipoDovuto", codTipoDovuto);
			query.setParameter("codUfficio", codUfficio);
			
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
			 * Descrizione dell'ufficio e del tipo dovuto selezionati come filtro per la ricerca statistica.
			 */
			String _deUfficioFiltered = null, _deTipoDovutoFiltered = null;
			
			/**
			 * Setto nel DTO i valori ritornati dalla query.
			 */
			List<VmStatisticaDto> result = new ArrayList<VmStatisticaDto>();
					
			/*
			 */
			for (Object[] row : rows) {
				/**
				 * Get codici e descrizioni
				 */
				String _codUfficio = (String) row[3];  	  String _codTipoDovuto = (String) row[5];		String _codCapitolo = (String) row[7]; 	 	
				String _deUfficio = (String) row[4];	  String _deTipoDovuto = (String) row[6];	    String _deCapitolo = (String) row[8];
				/**
				 * Get Importi
				 */
				BigDecimal importoPagato = (BigDecimal) row[9];				// Importo pagato
				BigDecimal importoRendicontato = (BigDecimal) row[10];		// Importo rendicontato
				BigDecimal importoIncassato = (BigDecimal) row[11];			// Importo incassato
				/**
				 * Get if detail RT is enable. Per accedere al dettaglio L'importo deve essere <= 5000 e diverso da ZERO.
				 * 
				 * Compare To ->> Returns: -1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.
				 */
				boolean enableDttImpPagato = importoPagato.compareTo(new BigDecimal(5000)) > 0     || importoPagato.compareTo(BigDecimal.ZERO) == 0 ? Boolean.FALSE : Boolean.TRUE;
				boolean enableDttImpRend = importoRendicontato.compareTo(new BigDecimal(5000)) > 0 || importoRendicontato.compareTo(BigDecimal.ZERO) == 0 ? Boolean.FALSE : Boolean.TRUE;
				boolean enableDttImpInc = importoIncassato.compareTo(new BigDecimal(5000)) > 0     || importoIncassato.compareTo(BigDecimal.ZERO) == 0 ? Boolean.FALSE : Boolean.TRUE;
				
				/**
				 * Build DTO importi
				 */ 
				CruscottoIncassiImportiDto importiDTO = new CruscottoIncassiImportiDto();
				importiDTO.setImportoPagato("€ " + Constants.NUMBER_FORMAT_IT.format(importoPagato)); 
				importiDTO.setImportoRendicontato("€ " + Constants.NUMBER_FORMAT_IT.format(importoRendicontato)); 
				importiDTO.setImportoIncassato("€ " + Constants.NUMBER_FORMAT_IT.format(importoIncassato)); 
				//
				importiDTO.setEnableDttImportoPagato(enableDttImpPagato);
				importiDTO.setEnableDttImportoRend(enableDttImpRend);
				importiDTO.setEnableDttImportoInc(enableDttImpInc);
				
				/** 
				 */
				VmStatisticaDto item = new VmStatisticaDto();
				// Se la descrizione è nulla, visualizzo il codice
				item.setDesc(StringUtils.hasText(_deCapitolo) ? _deCapitolo : _codCapitolo);
				//
				item.setCodice(_codCapitolo);
				//
				item.setImportiDTO(importiDTO);
				
				/* */
				result.add(item);
				
				/** 
				 * Mantengo la descrizione dell'ufficio e del tipo dovuto selezionati
				 */
				_deUfficioFiltered = StringUtils.hasText(_deUfficio) ? _deUfficio : _codUfficio;
				_deTipoDovutoFiltered = StringUtils.hasText(_deTipoDovuto) ? _deTipoDovuto : _codTipoDovuto;
				
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
			dto.setDeUfficioFiltered(_deUfficioFiltered);
			dto.setDeTipoDovutoFiltered(_deTipoDovutoFiltered);
			
			logger.debug("DAO :: STATISTICA :: TOTALI RIPARTITI per CAPITOLI :: END");
			
			return dto;
		}catch(Exception e) {
			logger.error("DAO :: STATISTICA :: TOTALI RIPARTITI per CAPITOLI :: ERROR ", e);
			throw new Exception(e);
		}
	}
	
	/**
	 * Restituisce l'elenco delle ripartizioni per capitoli degli Importi Pagati, Rendicontati e Incassati relativi l'ente e il giorno nel mese 
	 * dell'anno indicati.
	 * Si richiede di selezionare, un ufficio e un tipo dovuto.
	 * 
	 * @param {@link Long}    enteId,     	 Identificativo dell'ente
	 * @param {@link Integer} year,       	 L'anno per cui ritornare il dato statistico
	 * @param {@link Integer} month, 	 	 Il mese per cui ritornare il dato statistico
	 * @param {@link Integer} day,      	 Il giorno per cui ritornare il dato statistico
	 * @param {@link String}  codUfficio,    Codice ufficio
	 * @param {@link String}  codTipoDovuto, Codice del tipo dovuto
	 * 
	 * @return {@link CruscottoIncassiDto}
	 * 
	 * @throws Exception
	 * @author Marianna Memoli
 	 */
	@SuppressWarnings("unchecked")
	public CruscottoIncassiDto getTotaliRipartitiPerCapitoliByAnnoMeseGiornoUfficioDovuto(Long enteId, Integer year, Integer month, Integer day, String codUfficio, String codTipoDovuto) throws Exception {
		try {
			logger.debug("DAO :: STATISTICA :: TOTALI RIPARTITI per CAPITOLI :: PARAMETRI DI RICERCA[" + 
						 "enteId:" + enteId + ", year:" + year + ", month:" + month  + ", day:" + day + ", codUfficio:" + codUfficio + ", " +
						 "codTipoDovuto:" + codTipoDovuto + "] :: START");
			
			/*
			 * Controllo se i filtri obbligatori sono valorizzati, se non lo sono lancio un eccezione per sospendere l'esecuzione della query.
			 */
			if (enteId == null || year == null || month == null || day == null || !StringUtils.hasText(codUfficio) || !StringUtils.hasText(codTipoDovuto)) {
				/*
				 * Interrompo l'esecuzione 
				 */
				String errMsg = "Impossibile avviare la ricerca statistica 'TOTALI RIPARTITI per CAPITOLI'. Parametri obbligatori assenti o non corretti: " + 
								"enteId[" + enteId + "], year[" + year + "], month[" + month + "], day[" + day + "], codUfficio[" + codUfficio + "], " + 
								"codTipoDovuto[" + codTipoDovuto + "]";
				/* *
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
								"cod_uff, " + 
								"de_uff, " + 
								"cod_td, " +
								"de_td, " + 
								"cod_cap, " + 
								"de_cap, " + 
								"SUM(imp_pag)  AS imp_pag, " + 
								"SUM(imp_rend) AS imp_rend, " + 
								"SUM(imp_inc)  AS imp_inc " +
						 "FROM " + 
								"vm_statistica_ente_anno_mese_giorno_uff_td_cap " +
						 "WHERE " + 
								"mygov_ente_id = :enteId AND anno = :year AND mese = :month AND giorno = :day AND " +
								"cod_uff = :codUfficio AND cod_td = :codTipoDovuto " +
						 "GROUP BY " + 
						 		"mygov_ente_id, anno, mese, giorno, cod_uff, de_uff, cod_td, de_td, cod_cap, de_cap " + 
						 "ORDER BY " +
								"de_cap";
			
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
			query.setParameter("codUfficio", codUfficio);
			query.setParameter("codTipoDovuto", codTipoDovuto);
			
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
			
			/**
			 * Descrizione dell'ufficio e del tipo dovuto selezionati come filtro per la ricerca statistica.
			 */
			String _deUfficioFiltered = null, _deTipoDovutoFiltered = null;
			
			/*
			 */
			for (Object[] row : rows) {
				/**
				 * Get codici e descrizioni
				 */
				String _codUfficio = (String) row[3];  	  String _codTipoDovuto = (String) row[5];		String _codCapitolo = (String) row[7]; 	 	
				String _deUfficio = (String) row[4];	  String _deTipoDovuto = (String) row[6];	    String _deCapitolo = (String) row[8];
				/**
				 * Get Importi
				 */
				BigDecimal importoPagato = (BigDecimal) row[9];				// Importo pagato
				BigDecimal importoRendicontato = (BigDecimal) row[10];		// Importo rendicontato
				BigDecimal importoIncassato = (BigDecimal) row[11];			// Importo incassato
				/**
				 * Get if detail RT is enable. Per accedere al dettaglio L'importo deve essere <= 5000 e diverso da ZERO.
				 * 
				 * Compare To ->> Returns: -1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.
				 */
				boolean enableDttImpPagato = importoPagato.compareTo(new BigDecimal(5000)) > 0     || importoPagato.compareTo(BigDecimal.ZERO) == 0 ? Boolean.FALSE : Boolean.TRUE;
				boolean enableDttImpRend = importoRendicontato.compareTo(new BigDecimal(5000)) > 0 || importoRendicontato.compareTo(BigDecimal.ZERO) == 0 ? Boolean.FALSE : Boolean.TRUE;
				boolean enableDttImpInc = importoIncassato.compareTo(new BigDecimal(5000)) > 0     || importoIncassato.compareTo(BigDecimal.ZERO) == 0 ? Boolean.FALSE : Boolean.TRUE;
				
				/**
				 * Build DTO importi
				 */ 
				CruscottoIncassiImportiDto importiDTO = new CruscottoIncassiImportiDto();
				importiDTO.setImportoPagato("€ " + Constants.NUMBER_FORMAT_IT.format(importoPagato)); 
				importiDTO.setImportoRendicontato("€ " + Constants.NUMBER_FORMAT_IT.format(importoRendicontato)); 
				importiDTO.setImportoIncassato("€ " + Constants.NUMBER_FORMAT_IT.format(importoIncassato)); 
				//
				importiDTO.setEnableDttImportoPagato(enableDttImpPagato);
				importiDTO.setEnableDttImportoRend(enableDttImpRend);
				importiDTO.setEnableDttImportoInc(enableDttImpInc);
				
				/** 
				 */
				VmStatisticaDto item = new VmStatisticaDto();
				//
				item.setDesc(StringUtils.hasText(_deCapitolo) ? _deCapitolo : _codCapitolo);
				//
				item.setCodice(_codCapitolo);
				//
				item.setImportiDTO(importiDTO);
			 
				/* */
				result.add(item);
				
				/** 
				 * Mantengo la descrizione dell'ufficio e del tipo dovuto selezionati.
				 */
				_deUfficioFiltered = StringUtils.hasText(_deUfficio) ? _deUfficio : _codUfficio;
				_deTipoDovutoFiltered = StringUtils.hasText(_deTipoDovuto) ? _deTipoDovuto : _codTipoDovuto;
				
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
			dto.setDeUfficioFiltered(_deUfficioFiltered);
			dto.setDeTipoDovutoFiltered(_deTipoDovutoFiltered);
			
			logger.debug("DAO :: STATISTICA :: TOTALI RIPARTITI per CAPITOLI :: END");
			
			return dto;
		}catch(Exception e) {
			logger.error("DAO :: STATISTICA :: TOTALI RIPARTITI per CAPITOLI :: ERROR ", e);
			throw new Exception(e);
		}
	}
	
/* SERVICE Totali Ripartiti Per Capitoli By Ufficio =========================================================================================== */
	
	/** 
	 * Restituisce l'elenco delle ripartizioni per capitoli degli Importi Pagati, Rendicontati e Incassati relativi l'ente e l'anno indicati.
	 * Si richiede di selezionare un ufficio.
	 * 
	 * @param {@link Long}    enteId, 	  Identificativo dell'ente
	 * @param {@link Integer} year,       L'anno per cui ritornare il dato statistico
	 * @param {@link String}  codUfficio, Codice ufficio
	 * 
	 * @return {@link CruscottoIncassiMultiRowCol}
	 * 
	 * @throws Exception
	 * @author Marianna Memoli
 	 */
	@SuppressWarnings("unchecked")
	public CruscottoIncassiMultiRowCol getTotaliRipartitiPerCapitoliByAnnoUfficio(Long enteId, Integer year, String codUfficio) throws Exception{
		try {
			logger.debug("DAO :: STATISTICA :: TOTALI RIPARTITI per CAPITOLI :: PARAMETRI DI RICERCA[" + 
						 "enteId:" + enteId + ", year:" + year + ", codUfficio:" + codUfficio + "] :: START");
			
			/*
			 * Controllo se i filtri obbligatori sono valorizzati, se non lo sono lancio un eccezione per sospendere l'esecuzione della query.
			 */
			if (enteId == null || year == null || !StringUtils.hasText(codUfficio)) {
				/*
				 * Interrompo l'esecuzione 
				 */
				String errMsg = "Impossibile avviare la ricerca statistica 'TOTALI RIPARTITI per CAPITOLI'. Parametri obbligatori assenti o non corretti: " + 
								"enteId[" + enteId + "], year[" + year + "], codUfficio[" + codUfficio + "]";
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
								"cod_uff, " +
								"de_uff, " +
								"cod_td, " + 
								"de_td, " +
								"cod_cap, " + 
								"de_cap, " +
								"SUM(imp_pag)  AS imp_pag, " + 
								"SUM(imp_rend) AS imp_rend, " + 
								"SUM(imp_inc)  AS imp_inc " +
						 "FROM " + 
								"vm_statistica_ente_anno_mese_uff_td_cap " +
						 "WHERE " +
						 		"mygov_ente_id = :enteId AND anno = :year AND cod_uff = :codUfficio " + 
						 "GROUP BY " + 
						 		"mygov_ente_id, anno, cod_uff, de_uff, cod_td, de_td, cod_cap, de_cap " + 
						 "ORDER BY " +
						 		"cod_td ";
			
			/** 
			 * Query di selezione
			 */
			Query query = entityManager.createNativeQuery(sql); 
			
			/**
			 * Set Param filter
			 */
			query.setParameter("enteId", enteId);
			query.setParameter("year", year);
			query.setParameter("codUfficio", codUfficio);
			
			/* execute */
			List<Object[]> resultSet = query.getResultList();
			
			
			if(resultSet != null && !resultSet.isEmpty()){
				/**
				 * Instance
				 */
				String _deUfficioFiltered = null;
				Map<String, String> _headerColumns = new HashMap<String, String>(), _headerRows = new HashMap<String, String>();
				Map<String, CruscottoIncassiImportiDto> _rowcolCell = new HashMap<String, CruscottoIncassiImportiDto>();
				Map<String, Map<String, BigDecimal>> _totColumns = new HashMap<String, Map<String, BigDecimal>>(), _totRows = new HashMap<String, Map<String, BigDecimal>>();
				
				/*
				 */
				for (Object[] row : resultSet) {
					/**
					 * Get importi per il dovuto/capitolo
					 */
					BigDecimal importoPagato = (BigDecimal) row[8];			// importo pagato
					BigDecimal importoRendicontato = (BigDecimal) row[9];	// importo rendicontato
					BigDecimal importoIncassato = (BigDecimal) row[10];		// importo incassato
					
					/**
					 * Get if detail RT is enable. Per accedere al dettaglio L'importo deve essere <= 5000 e diverso da ZERO.
					 * 
					 * Compare To ->> Returns: -1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.
					 */
					boolean enableDttImpPagato = importoPagato.compareTo(new BigDecimal(5000)) > 0     || importoPagato.compareTo(BigDecimal.ZERO) == 0 ? Boolean.FALSE : Boolean.TRUE;
					boolean enableDttImpRend = importoRendicontato.compareTo(new BigDecimal(5000)) > 0 || importoRendicontato.compareTo(BigDecimal.ZERO) == 0 ? Boolean.FALSE : Boolean.TRUE;
					boolean enableDttImpInc = importoIncassato.compareTo(new BigDecimal(5000)) > 0     || importoIncassato.compareTo(BigDecimal.ZERO) == 0 ? Boolean.FALSE : Boolean.TRUE;
					
					/**
					 * Get codici e descrizioni
					 */
					String _codUfficio = (String) row[2]; 	String _codTipoDovuto = (String) row[4];    String _codCapitolo = (String) row[6];
					
					String _deUfficio = (String) row[3];    String _deTipoDovuto = (String) row[5];		String _deCapitolo = (String) row[7];
					
					/**
					 * Get descrizione Ufficio usato come filtro di ricerca
					 */
					_deUfficioFiltered = StringUtils.hasText(_deUfficio) ? _deUfficio : _codUfficio;
					
					/**
					 * Build DTO importi per il dovuto/capitolo
					 */
					CruscottoIncassiImportiDto importiRowCol = new CruscottoIncassiImportiDto();
					importiRowCol.setImportoPagato("€ " + Constants.NUMBER_FORMAT_IT.format(importoPagato)); 
					importiRowCol.setImportoRendicontato("€ " + Constants.NUMBER_FORMAT_IT.format(importoRendicontato)); 
					importiRowCol.setImportoIncassato("€ " + Constants.NUMBER_FORMAT_IT.format(importoIncassato)); 
					//
					importiRowCol.setEnableDttImportoPagato(enableDttImpPagato);
					importiRowCol.setEnableDttImportoRend(enableDttImpRend);
					importiRowCol.setEnableDttImportoInc(enableDttImpInc);
					
					/**
					 * Aggiungo la nuova associazioni dovuto/capitolo con relativi importi (Formattato come "€ <importo>").
					 */
					_rowcolCell.put(_codTipoDovuto + "_" + _codCapitolo, importiRowCol);
	
					/**
					 * Se non è stato già inserito, aggiungo alla mappa delle intestazione delle colonne il capitolo
					 */
					if(!_headerColumns.containsKey(_codCapitolo)) _headerColumns.put(_codCapitolo, StringUtils.hasText(_deCapitolo) ? _deCapitolo : _codCapitolo);
					/**
					 * Se non è stato già inserito, aggiungo alla mappa delle intestazione delle righe il dovuto
					 */
					if(!_headerRows.containsKey(_codTipoDovuto)) _headerRows.put(_codTipoDovuto, _deTipoDovuto);
					
					/**
					 * Controllo se nella mappa con il totale delle somme degli importi Pagati, Rendicontati e Incassati per colonna
					 * è stato già inserito il capitolo.
					 * Se no, creo il bean DTO degli importi e aggiungo il tutto alla mappa.
					 * 
					 * Se si, recupero dalla mappa il capitolo e il bean degli importi. A questi importi sommo quelli derivati 
					 * dall'iterazione corrente.
					 */
					if(!_totColumns.containsKey(_codCapitolo)){
						/*
						 * Build DTO
						 */
						Map<String, BigDecimal> importo = new HashMap<String, BigDecimal>();
						importo.put("importoPagato", importoPagato);
						importo.put("importoRendicontato", importoRendicontato);
						importo.put("importoIncassato", importoIncassato);
						/*
						 * Add to map
						 */
						_totColumns.put(_codCapitolo, importo);
					}else{
						/*
						 * Get DTO
						 */
						Map<String, BigDecimal> importo = _totColumns.get(_codCapitolo);
						/*
						 * Sommo gli importi  
						 */
						importo.put("importoPagato", importo.get("importoPagato").add(importoPagato));
						importo.put("importoRendicontato", importo.get("importoRendicontato").add(importoRendicontato));
						importo.put("importoIncassato", importo.get("importoIncassato").add(importoIncassato));
						/*
						 * Add to map
						 */
						_totColumns.put(_codCapitolo, importo);
					}
					
					/**
					 * Controllo se nella mappa con il totale delle somme degli importi Pagati, Rendicontati e Incassati per riga
					 * è stato già inserito il dovuto.
					 * Se no, creo il bean DTO degli importi e aggiungo il tutto alla mappa.
					 * 
					 * Se si, recupero dalla mappa il dovuto e il bean degli importi. A questi importi sommo quelli derivati 
					 * dall'iterazione corrente.
					 */
					if(!_totRows.containsKey(_codTipoDovuto)){
						/*
						 * Build DTO
						 */
						Map<String, BigDecimal> importo = new HashMap<String, BigDecimal>();
						importo.put("importoPagato", importoPagato);
						importo.put("importoRendicontato", importoRendicontato);
						importo.put("importoIncassato", importoIncassato);
						/*
						 * Add to map
						 */
						_totRows.put(_codTipoDovuto, importo);
					}else{
						/*
						 * Get DTO
						 */
						Map<String, BigDecimal> importo = _totRows.get(_codTipoDovuto);
						/*
						 * Sommo gli importi  
						 */
						importo.put("importoPagato", importo.get("importoPagato").add(importoPagato));
						importo.put("importoRendicontato", importo.get("importoRendicontato").add(importoRendicontato));
						importo.put("importoIncassato", importo.get("importoIncassato").add(importoIncassato));
						/*
						 * Add to map
						 */
						_totRows.put(_codTipoDovuto, importo);
					}
				} // close for
				
				/**
				 * Init variabili in cui salvo il totale delle somme degli importi della colonna dei totali per riga.
				 */
				BigDecimal _footerImportoPagato = BigDecimal.ZERO, _footerImportoRend = BigDecimal.ZERO, _footerImportoInc = BigDecimal.ZERO;		
				
				/**
				 * Riciclo le mappe dei totali per colonna e per riga per formattarli come "€ <importo>".
				 */
				Map<String, CruscottoIncassiImportiDto> totColumns = new HashMap<String, CruscottoIncassiImportiDto>();
				Map<String, CruscottoIncassiImportiDto> totRows = new HashMap<String,CruscottoIncassiImportiDto>();
				
				/**
				 * COLONNE
				 */
				for(Entry<String, Map<String, BigDecimal>> item : _totColumns.entrySet()) {
					Map<String, BigDecimal> map = item.getValue();
					/*
					 * Build DTO
					 */
					CruscottoIncassiImportiDto dto = new CruscottoIncassiImportiDto();
					dto.setImportoPagato("€ " + Constants.NUMBER_FORMAT_IT.format(map.get("importoPagato"))); 
					dto.setImportoRendicontato("€ " + Constants.NUMBER_FORMAT_IT.format(map.get("importoRendicontato"))); 
					dto.setImportoIncassato("€ " + Constants.NUMBER_FORMAT_IT.format(map.get("importoIncassato"))); 
					/*
					 * Add to map
					 */
					totColumns.put(item.getKey(), dto);
				}
				/**
				 * RIGHE
				 */
				for (Entry<String, Map<String, BigDecimal>> item : _totRows.entrySet()) {
					Map<String, BigDecimal> map = item.getValue();
					/*
					 * Build DTO
					 */
					CruscottoIncassiImportiDto dto = new CruscottoIncassiImportiDto();
					dto.setImportoPagato("€ " + Constants.NUMBER_FORMAT_IT.format(map.get("importoPagato"))); 
					dto.setImportoRendicontato("€ " + Constants.NUMBER_FORMAT_IT.format(map.get("importoRendicontato"))); 
					dto.setImportoIncassato("€ " + Constants.NUMBER_FORMAT_IT.format(map.get("importoIncassato"))); 
					/*
					 * Add to map
					 */
					totRows.put(item.getKey(), dto);
					/*
					 * Sommo importi 
					 */
					_footerImportoPagato = _footerImportoPagato.add(map.get("importoPagato"));
					_footerImportoRend =   _footerImportoRend.add(map.get("importoRendicontato"));
					_footerImportoInc =    _footerImportoInc.add(map.get("importoIncassato"));
				}
				
				/**
				 * Set importi totali delle somme degli importi totali per riga.
				 */
				CruscottoIncassiImportiDto footerTotColumns = new CruscottoIncassiImportiDto();
				footerTotColumns.setImportoPagato("€ " + Constants.NUMBER_FORMAT_IT.format(_footerImportoPagato));
				footerTotColumns.setImportoRendicontato("€ " + Constants.NUMBER_FORMAT_IT.format(_footerImportoRend));
				footerTotColumns.setImportoIncassato("€ " + Constants.NUMBER_FORMAT_IT.format(_footerImportoInc));
				
				/**
				 * Return Cruscotto Incassi DTO
				 */
				CruscottoIncassiMultiRowCol dto = new CruscottoIncassiMultiRowCol();
				dto.setHeaderRows(_headerRows);
				dto.setHeaderColumns(_headerColumns);
				dto.setRowcolCell(_rowcolCell);
				dto.setTotRows(totRows);
				dto.setTotColumns(totColumns);
				dto.setDescFiltered(_deUfficioFiltered);
				dto.setFooterTotColumns(footerTotColumns);
			
				logger.debug("DAO :: STATISTICA :: TOTALI RIPARTITI per CAPITOLI :: END");
				
				return dto;
			}else{
				logger.debug("DAO :: STATISTICA :: TOTALI RIPARTITI per CAPITOLI :: END");
				
				return null;
			}
		}catch(Exception e) {
			logger.error("DAO :: STATISTICA :: TOTALI RIPARTITI per CAPITOLI :: ERROR ", e);
			throw new Exception(e);
		}
	}
	
	/** 
	 * Restituisce l'elenco delle ripartizioni per Capitoli degli Importi Pagati, Rendicontati e Incassati relativi l'ente e il mese 
	 * dell'anno indicati. Si richiede di selezionare un ufficio.
	 * 
	 * @param {@link Long}    enteId, 	  Identificativo dell'ente
	 * @param {@link Integer} year,       L'anno per cui ritornare il dato statistico
	 * @param {@link Integer} month, 	  Il mese per cui ritornare il dato statistico
	 * @param {@link String}  codUfficio, Codice ufficio
	 * 
	 * @return {@link CruscottoIncassiMultiRowCol}
	 * 
	 * @throws Exception
	 * @author Marianna Memoli
 	 */
	@SuppressWarnings("unchecked")
	public CruscottoIncassiMultiRowCol getTotaliRipartitiPerCapitoliByAnnoMeseUfficio(Long enteId, Integer year, Integer month, String codUfficio) throws Exception{
		try {
			logger.debug("DAO :: STATISTICA :: TOTALI RIPARTITI per CAPITOLI :: PARAMETRI DI RICERCA[" + 
					 	 "enteId:" + enteId + ", year:" + year + ", month:" + month + ", codUfficio:" + codUfficio + "] :: START");
		
			/*
			 * Controllo se i filtri obbligatori sono valorizzati, se non lo sono lancio un eccezione per sospendere l'esecuzione della query.
			 */
			if (enteId == null || year == null || month == null || !StringUtils.hasText(codUfficio)) {
				/*
				 * Interrompo l'esecuzione 
				 */
				String errMsg = "Impossibile avviare la ricerca statistica 'TOTALI RIPARTITI per CAPITOLI'. Parametri obbligatori assenti o non corretti: " + 
								"enteId[" + enteId + "], year[" + year + "], month[" + month + "], codUfficio[" + codUfficio + "]";
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
								"anno, 	   " +
								"mese, 	   " +
								"cod_uff,  " +
								"de_uff,   " +
								"cod_td,   " + 
								"de_td,    " +
								"cod_cap,  " + 
								"de_cap,   " +
								"SUM(imp_pag)  AS imp_pag, " + 
								"SUM(imp_rend) AS imp_rend, " + 
								"SUM(imp_inc)  AS imp_inc " +
						 "FROM " + 
								"vm_statistica_ente_anno_mese_uff_td_cap " +
						 "WHERE " + 
								"mygov_ente_id = :enteId AND anno = :year AND mese = :month AND cod_uff = :codUfficio " + 
						 "GROUP BY " + 
						 		"mygov_ente_id, anno, mese, cod_uff, de_uff, cod_td, de_td, cod_cap, de_cap " +
						 "ORDER BY " +
							 	"cod_td ";
			
			/** 
			 * Query di selezione
			 */
			Query query = entityManager.createNativeQuery(sql); 
			
			/*
			 * set param filter
			 */
			query.setParameter("enteId", enteId);
			query.setParameter("year", year);
			query.setParameter("month", month);
			query.setParameter("codUfficio", codUfficio);
			
			/* execute */
			List<Object[]> resultSet = query.getResultList();
			

			if(resultSet != null && !resultSet.isEmpty()){
				/**
				 * Instance
				 */
				String _deUfficioFiltered = null;
				Map<String, String> _headerColumns = new HashMap<String, String>(), _headerRows = new HashMap<String, String>();
				Map<String, CruscottoIncassiImportiDto> _rowcolCell = new HashMap<String, CruscottoIncassiImportiDto>();
				Map<String, Map<String, BigDecimal>> _totColumns = new HashMap<String, Map<String, BigDecimal>>(), _totRows = new HashMap<String, Map<String, BigDecimal>>();
				
				/*
				 */
				for (Object[] row : resultSet) {
					/**
					 * Get importi per il dovuto/capitolo
					 */
					BigDecimal importoPagato = (BigDecimal) row[9];			// importo pagato
					BigDecimal importoRendicontato = (BigDecimal) row[10];	// importo rendicontato
					BigDecimal importoIncassato = (BigDecimal) row[11];		// importo incassato
					
					/**
					 * Get if detail RT is enable. Per accedere al dettaglio L'importo deve essere <= 5000 e diverso da ZERO.
					 * 
					 * Compare To ->> Returns: -1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.
					 */
					boolean enableDttImpPagato = importoPagato.compareTo(new BigDecimal(5000)) > 0     || importoPagato.compareTo(BigDecimal.ZERO) == 0 ? Boolean.FALSE : Boolean.TRUE;
					boolean enableDttImpRend = importoRendicontato.compareTo(new BigDecimal(5000)) > 0 || importoRendicontato.compareTo(BigDecimal.ZERO) == 0 ? Boolean.FALSE : Boolean.TRUE;
					boolean enableDttImpInc = importoIncassato.compareTo(new BigDecimal(5000)) > 0     || importoIncassato.compareTo(BigDecimal.ZERO) == 0 ? Boolean.FALSE : Boolean.TRUE;
					
					/**
					 * Get codici e descrizioni
					 */
					String _codUfficio = (String) row[3]; 	String _codTipoDovuto = (String) row[5];    String _codCapitolo = (String) row[7];
					
					String _deUfficio = (String) row[4];    String _deTipoDovuto = (String) row[6];		String _deCapitolo = (String) row[8];
					
					/**
					 * Get descrizione Ufficio usato come filtro di ricerca
					 */
					_deUfficioFiltered = StringUtils.hasText(_deUfficio)  ? _deUfficio : _codUfficio;
					
					/**
					 * Build DTO importi per il dovuto/capitolo
					 */
					CruscottoIncassiImportiDto importiRowCol = new CruscottoIncassiImportiDto();
					importiRowCol.setImportoPagato("€ " + Constants.NUMBER_FORMAT_IT.format(importoPagato)); 
					importiRowCol.setImportoRendicontato("€ " + Constants.NUMBER_FORMAT_IT.format(importoRendicontato)); 
					importiRowCol.setImportoIncassato("€ " + Constants.NUMBER_FORMAT_IT.format(importoIncassato)); 
					//
					importiRowCol.setEnableDttImportoPagato(enableDttImpPagato);
					importiRowCol.setEnableDttImportoRend(enableDttImpRend);
					importiRowCol.setEnableDttImportoInc(enableDttImpInc);
					
					/**
					 * Aggiungo la nuova associazioni dovuto/capitolo con relativi importi (Formattato come "€ <importo>").
					 */
					_rowcolCell.put(_codTipoDovuto + "_" + _codCapitolo, importiRowCol);
	
					/**
					 * Se non è stato già inserito, aggiungo alla mappa delle intestazione delle colonne il capitolo
					 */
					if(!_headerColumns.containsKey(_codCapitolo)) _headerColumns.put(_codCapitolo, StringUtils.hasText(_deCapitolo) ? _deCapitolo : _codCapitolo);
					/**
					 * Se non è stato già inserito, aggiungo alla mappa delle intestazione delle righe il dovuto
					 */
					if(!_headerRows.containsKey(_codTipoDovuto)) _headerRows.put(_codTipoDovuto, _deTipoDovuto);
					
					/**
					 * Controllo se nella mappa con il totale delle somme degli importi Pagati, Rendicontati e Incassati per colonna
					 * è stato già inserito il capitolo.
					 * Se no, creo il bean DTO degli importi e aggiungo il tutto alla mappa.
					 * 
					 * Se si, recupero dalla mappa il capitolo e il bean degli importi. A questi importi sommo quelli derivati 
					 * dall'iterazione corrente.
					 */
					if(!_totColumns.containsKey(_codCapitolo)){
						/*
						 * Build DTO
						 */
						Map<String, BigDecimal> importo = new HashMap<String, BigDecimal>();
						importo.put("importoPagato", importoPagato);
						importo.put("importoRendicontato", importoRendicontato);
						importo.put("importoIncassato", importoIncassato);
						/*
						 * Add to map
						 */
						_totColumns.put(_codCapitolo, importo);
					}else{
						/*
						 * Get DTO
						 */
						Map<String, BigDecimal> importo = _totColumns.get(_codCapitolo);
						/*
						 * Sommo gli importi  
						 */
						importo.put("importoPagato", importo.get("importoPagato").add(importoPagato));
						importo.put("importoRendicontato", importo.get("importoRendicontato").add(importoRendicontato));
						importo.put("importoIncassato", importo.get("importoIncassato").add(importoIncassato));
						/*
						 * Add to map
						 */
						_totColumns.put(_codCapitolo, importo);
					}
					
					/**
					 * Controllo se nella mappa con il totale delle somme degli importi Pagati, Rendicontati e Incassati per riga
					 * è stato già inserito il dovuto.
					 * Se no, creo il bean DTO degli importi e aggiungo il tutto alla mappa.
					 * 
					 * Se si, recupero dalla mappa il dovuto e il bean degli importi. A questi importi sommo quelli derivati 
					 * dall'iterazione corrente.
					 */
					if(!_totRows.containsKey(_codTipoDovuto)){
						/*
						 * Build DTO
						 */
						Map<String, BigDecimal> importo = new HashMap<String, BigDecimal>();
						importo.put("importoPagato", importoPagato);
						importo.put("importoRendicontato", importoRendicontato);
						importo.put("importoIncassato", importoIncassato);
						/*
						 * Add to map
						 */
						_totRows.put(_codTipoDovuto, importo);
					}else{
						/*
						 * Get DTO
						 */
						Map<String, BigDecimal> importo = _totRows.get(_codTipoDovuto);
						/*
						 * Sommo gli importi  
						 */
						importo.put("importoPagato", importo.get("importoPagato").add(importoPagato));
						importo.put("importoRendicontato", importo.get("importoRendicontato").add(importoRendicontato));
						importo.put("importoIncassato", importo.get("importoIncassato").add(importoIncassato));
						/*
						 * Add to map
						 */
						_totRows.put(_codTipoDovuto, importo);
					}
				} // close for
			
				/**
				 * Init variabili in cui salvo il totale delle somme degli importi della colonna dei totali per riga.
				 */
				BigDecimal _footerImportoPagato = BigDecimal.ZERO, _footerImportoRend = BigDecimal.ZERO, _footerImportoInc = BigDecimal.ZERO;		
				
				/**
				 * Riciclo le mappe dei totali per colonna e per riga per formattarli come "€ <importo>".
				 */
				Map<String, CruscottoIncassiImportiDto> totColumns = new HashMap<String, CruscottoIncassiImportiDto>();
				Map<String, CruscottoIncassiImportiDto> totRows = new HashMap<String,CruscottoIncassiImportiDto>();
				
				/**
				 * COLONNE
				 */
				for(Entry<String, Map<String, BigDecimal>> item : _totColumns.entrySet()) {
					Map<String, BigDecimal> map = item.getValue();
					/*
					 * Build DTO
					 */
					CruscottoIncassiImportiDto dto = new CruscottoIncassiImportiDto();
					dto.setImportoPagato("€ " + Constants.NUMBER_FORMAT_IT.format(map.get("importoPagato"))); 
					dto.setImportoRendicontato("€ " + Constants.NUMBER_FORMAT_IT.format(map.get("importoRendicontato"))); 
					dto.setImportoIncassato("€ " + Constants.NUMBER_FORMAT_IT.format(map.get("importoIncassato"))); 
					/*
					 * Add to map
					 */
					totColumns.put(item.getKey(), dto);
				}
				/**
				 * RIGHE
				 */
				for (Entry<String, Map<String, BigDecimal>> item : _totRows.entrySet()) {
					Map<String, BigDecimal> map = item.getValue();
					/*
					 * Build DTO
					 */
					CruscottoIncassiImportiDto dto = new CruscottoIncassiImportiDto();
					dto.setImportoPagato("€ " + Constants.NUMBER_FORMAT_IT.format(map.get("importoPagato"))); 
					dto.setImportoRendicontato("€ " + Constants.NUMBER_FORMAT_IT.format(map.get("importoRendicontato"))); 
					dto.setImportoIncassato("€ " + Constants.NUMBER_FORMAT_IT.format(map.get("importoIncassato"))); 
					/*
					 * Add to map
					 */
					totRows.put(item.getKey(), dto);
					/*
					 * Sommo importi 
					 */
					_footerImportoPagato = _footerImportoPagato.add(map.get("importoPagato"));
					_footerImportoRend =   _footerImportoRend.add(map.get("importoRendicontato"));
					_footerImportoInc =    _footerImportoInc.add(map.get("importoIncassato"));
				}
				
				/**
				 * Set importi totali delle somme degli importi totali per riga.
				 */
				CruscottoIncassiImportiDto footerTotColumns = new CruscottoIncassiImportiDto();
				footerTotColumns.setImportoPagato("€ " + Constants.NUMBER_FORMAT_IT.format(_footerImportoPagato));
				footerTotColumns.setImportoRendicontato("€ " + Constants.NUMBER_FORMAT_IT.format(_footerImportoRend));
				footerTotColumns.setImportoIncassato("€ " + Constants.NUMBER_FORMAT_IT.format(_footerImportoInc));
				
				/**
				 * Return Cruscotto Incassi DTO
				 */
				CruscottoIncassiMultiRowCol dto = new CruscottoIncassiMultiRowCol();
				dto.setHeaderRows(_headerRows);
				dto.setHeaderColumns(_headerColumns);
				dto.setRowcolCell(_rowcolCell);
				dto.setTotRows(totRows);
				dto.setTotColumns(totColumns);
				dto.setDescFiltered(_deUfficioFiltered);
				dto.setFooterTotColumns(footerTotColumns);
				
				logger.debug("DAO :: STATISTICA :: TOTALI RIPARTITI per CAPITOLI :: END");
				
				return dto;
			}else{
				logger.debug("DAO :: STATISTICA :: TOTALI RIPARTITI per CAPITOLI :: END");
				
				return null;
			}
		}catch(Exception e) {
			logger.error("DAO :: STATISTICA :: TOTALI RIPARTITI per CAPITOLI :: ERROR ", e);
			throw new Exception(e);
		}
	}
	
	/**
	 * Restituisce l'elenco delle ripartizioni per Capitoli degli Importi Pagati, Rendicontati e Incassati relativi l'ente e il giorno 
	 * nel mese dell'anno indicati.
	 * 
	 * @param {@link Long}    enteId,     Identificativo dell'ente
	 * @param {@link Integer} year,       L'anno per cui ritornare il dato statistico
	 * @param {@link Integer} month, 	  Il mese per cui ritornare il dato statistico
	 * @param {@link Integer} day,        Il giorno per cui ritornare il dato statistico
	 * @param {@link String}  codUfficio, Codice ufficio
	 * 
	 * @return {@link CruscottoIncassiMultiRowCol}
	 * 
	 * @throws Exception
	 * @author Marianna Memoli
 	 */
	@SuppressWarnings("unchecked")
	public CruscottoIncassiMultiRowCol getTotaliRipartitiPerCapitoliByAnnoMeseGiornoUfficio(Long enteId, Integer year, Integer month, Integer day, String codUfficio) throws Exception {
		try {
			logger.debug("DAO :: STATISTICA :: TOTALI RIPARTITI per CAPITOLI :: PARAMETRI DI RICERCA[" + 
						 "enteId:" + enteId + ", year:" + year + ", month:" + month  + ", day:" + day + ", codUfficio:" + codUfficio + "] :: START");
			
			/*
			 * Controllo se i filtri obbligatori sono valorizzati, se non lo sono lancio un eccezione per sospendere l'esecuzione della query.
			 */
			if (enteId == null || year == null || month == null || day == null || !StringUtils.hasText(codUfficio)) {
				/*
				 * Interrompo l'esecuzione 
				 */
				String errMsg = "Impossibile avviare la ricerca statistica 'TOTALI RIPARTITI per CAPITOLI'. Parametri obbligatori assenti o non corretti: " + 
								"enteId[" + enteId + "], year[" + year + "], month[" + month + "], day[" + day + "], codUfficio[" + codUfficio + "]";
				/* *
				logger.error(errMsg);
				/* */
				throw new Exception(errMsg);
			}
			
			/*
			 * Costruisco la stringa sql
			 */
			String sql = "SELECT " + 
								"mygov_ente_id, " +
								"anno, 		" +
								"mese, 		" +
								"giorno, 	" +
								"cod_uff, 	" +
								"de_uff, 	" +
								"cod_td, 	" + 
								"de_td, 	" +
								"cod_cap, 	" + 
								"de_cap, 	" +
								"SUM(imp_pag)  AS imp_pag, " + 
								"SUM(imp_rend) AS imp_rend, " + 
								"SUM(imp_inc)  AS imp_inc " +
						 "FROM " + 
								"vm_statistica_ente_anno_mese_giorno_uff_td_cap " +
						 "WHERE " + 
								"mygov_ente_id = :enteId AND anno = :year AND mese = :month AND giorno = :day AND cod_uff = :codUfficio " +
						 "GROUP BY " + 
						 		"mygov_ente_id, anno, mese, giorno, cod_uff, de_uff, cod_td, de_td, cod_cap, de_cap " + 
						 "ORDER BY " +
								"cod_td";
			
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
			query.setParameter("codUfficio", codUfficio);
			
			/* execute */
			List<Object[]> resultSet = query.getResultList();


			if(resultSet != null && !resultSet.isEmpty()){
				/**
				 * Instance
				 */
				String _deUfficioFiltered = null;
				Map<String, String> _headerColumns = new HashMap<String, String>(), _headerRows = new HashMap<String, String>();
				Map<String, CruscottoIncassiImportiDto> _rowcolCell = new HashMap<String, CruscottoIncassiImportiDto>();
				Map<String, Map<String, BigDecimal>> _totColumns = new HashMap<String, Map<String, BigDecimal>>(), _totRows = new HashMap<String, Map<String, BigDecimal>>();
				
				/*
				 */
				for (Object[] row : resultSet) {
					/**
					 * Get importi per il dovuto/capitolo
					 */
					BigDecimal importoPagato = (BigDecimal) row[10];			// importo pagato
					BigDecimal importoRendicontato = (BigDecimal) row[11];		// importo rendicontato
					BigDecimal importoIncassato = (BigDecimal) row[12];			// importo incassato
					
					/**
					 * Get if detail RT is enable. Per accedere al dettaglio L'importo deve essere <= 5000 e diverso da ZERO.
					 * 
					 * Compare To ->> Returns: -1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.
					 */
					boolean enableDttImpPagato = importoPagato.compareTo(new BigDecimal(5000)) > 0     || importoPagato.compareTo(BigDecimal.ZERO) == 0 ? Boolean.FALSE : Boolean.TRUE;
					boolean enableDttImpRend = importoRendicontato.compareTo(new BigDecimal(5000)) > 0 || importoRendicontato.compareTo(BigDecimal.ZERO) == 0 ? Boolean.FALSE : Boolean.TRUE;
					boolean enableDttImpInc = importoIncassato.compareTo(new BigDecimal(5000)) > 0     || importoIncassato.compareTo(BigDecimal.ZERO) == 0 ? Boolean.FALSE : Boolean.TRUE;
					
					/**
					 * Get codici e descrizioni
					 */
					String _codUfficio = (String) row[4]; 	String _codTipoDovuto = (String) row[6];    String _codCapitolo = (String) row[8];
					
					String _deUfficio = (String) row[5];    String _deTipoDovuto = (String) row[7];		String _deCapitolo = (String) row[9];
	
					/**
					 * Get descrizione Uffico usato come filtro di ricerca
					 */
					_deUfficioFiltered = StringUtils.hasText(_deUfficio)  ? _deUfficio : _codUfficio;
					
					/**
					 * Build DTO importi per il dovuto/capitolo
					 */
					CruscottoIncassiImportiDto importiRowCol = new CruscottoIncassiImportiDto();
					importiRowCol.setImportoPagato("€ " + Constants.NUMBER_FORMAT_IT.format(importoPagato)); 
					importiRowCol.setImportoRendicontato("€ " + Constants.NUMBER_FORMAT_IT.format(importoRendicontato)); 
					importiRowCol.setImportoIncassato("€ " + Constants.NUMBER_FORMAT_IT.format(importoIncassato)); 
					//
					importiRowCol.setEnableDttImportoPagato(enableDttImpPagato);
					importiRowCol.setEnableDttImportoRend(enableDttImpRend);
					importiRowCol.setEnableDttImportoInc(enableDttImpInc);
					
					/**
					 * Aggiungo la nuova associazioni dovuto/capitolo con relativi importi (Formattato come "€ <importo>").
					 */
					_rowcolCell.put(_codTipoDovuto + "_" + _codCapitolo, importiRowCol);
	
					/**
					 * Se non è stato già inserito, aggiungo alla mappa delle intestazione delle colonne il capitolo
					 */
					if(!_headerColumns.containsKey(_codCapitolo)) _headerColumns.put(_codCapitolo, StringUtils.hasText(_deCapitolo) ? _deCapitolo : _codCapitolo);
					/**
					 * Se non è stato già inserito, aggiungo alla mappa delle intestazione delle righe il dovuto
					 */
					if(!_headerRows.containsKey(_codTipoDovuto)) _headerRows.put(_codTipoDovuto, _deTipoDovuto);
					
					/**
					 * Controllo se nella mappa con il totale delle somme degli importi Pagati, Rendicontati e Incassati per colonna
					 * è stato già inserito il capitolo.
					 * Se no, creo il bean DTO degli importi e aggiungo il tutto alla mappa.
					 * 
					 * Se si, recupero dalla mappa il capitolo e il bean degli importi. A questi importi sommo quelli derivati 
					 * dall'iterazione corrente.
					 */
					if(!_totColumns.containsKey(_codCapitolo)){
						/*
						 * Build DTO
						 */
						Map<String, BigDecimal> importo = new HashMap<String, BigDecimal>();
						importo.put("importoPagato", importoPagato);
						importo.put("importoRendicontato", importoRendicontato);
						importo.put("importoIncassato", importoIncassato);
						/*
						 * Add to map
						 */
						_totColumns.put(_codCapitolo, importo);
					}else{
						/*
						 * Get DTO
						 */
						Map<String, BigDecimal> importo = _totColumns.get(_codCapitolo);
						/*
						 * Sommo gli importi  
						 */
						importo.put("importoPagato", importo.get("importoPagato").add(importoPagato));
						importo.put("importoRendicontato", importo.get("importoRendicontato").add(importoRendicontato));
						importo.put("importoIncassato", importo.get("importoIncassato").add(importoIncassato));
						/*
						 * Add to map
						 */
						_totColumns.put(_codCapitolo, importo);
					}
					
					/**
					 * Controllo se nella mappa con il totale delle somme degli importi Pagati, Rendicontati e Incassati per riga
					 * è stato già inserito il dovuto.
					 * Se no, creo il bean DTO degli importi e aggiungo il tutto alla mappa.
					 * 
					 * Se si, recupero dalla mappa il dovuto e il bean degli importi. A questi importi sommo quelli derivati 
					 * dall'iterazione corrente.
					 */
					if(!_totRows.containsKey(_codTipoDovuto)){
						/*
						 * Build DTO
						 */
						Map<String, BigDecimal> importo = new HashMap<String, BigDecimal>();
						importo.put("importoPagato", importoPagato);
						importo.put("importoRendicontato", importoRendicontato);
						importo.put("importoIncassato", importoIncassato);
						/*
						 * Add to map
						 */
						_totRows.put(_codTipoDovuto, importo);
					}else{
						/*
						 * Get DTO
						 */
						Map<String, BigDecimal> importo = _totRows.get(_codTipoDovuto);
						/*
						 * Sommo gli importi  
						 */
						importo.put("importoPagato", importo.get("importoPagato").add(importoPagato));
						importo.put("importoRendicontato", importo.get("importoRendicontato").add(importoRendicontato));
						importo.put("importoIncassato", importo.get("importoIncassato").add(importoIncassato));
						/*
						 * Add to map
						 */
						_totRows.put(_codTipoDovuto, importo);
					}
				} // close for
			
				/**
				 * Init variabili in cui salvo il totale delle somme degli importi della colonna dei totali per riga.
				 */
				BigDecimal _footerImportoPagato = BigDecimal.ZERO, _footerImportoRend = BigDecimal.ZERO, _footerImportoInc = BigDecimal.ZERO;		
				
				/**
				 * Riciclo le mappe dei totali per colonna e per riga per formattarli come "€ <importo>".
				 */
				Map<String, CruscottoIncassiImportiDto> totColumns = new HashMap<String, CruscottoIncassiImportiDto>();
				Map<String, CruscottoIncassiImportiDto> totRows = new HashMap<String,CruscottoIncassiImportiDto>();
				
				/**
				 * COLONNE
				 */
				for(Entry<String, Map<String, BigDecimal>> item : _totColumns.entrySet()) {
					Map<String, BigDecimal> map = item.getValue();
					/*
					 * Build DTO
					 */
					CruscottoIncassiImportiDto dto = new CruscottoIncassiImportiDto();
					dto.setImportoPagato("€ " + Constants.NUMBER_FORMAT_IT.format(map.get("importoPagato"))); 
					dto.setImportoRendicontato("€ " + Constants.NUMBER_FORMAT_IT.format(map.get("importoRendicontato"))); 
					dto.setImportoIncassato("€ " + Constants.NUMBER_FORMAT_IT.format(map.get("importoIncassato"))); 
					/*
					 * Add to map
					 */
					totColumns.put(item.getKey(), dto);
				}
				/**
				 * RIGHE
				 */
				for (Entry<String, Map<String, BigDecimal>> item : _totRows.entrySet()) {
					Map<String, BigDecimal> map = item.getValue();
					/*
					 * Build DTO
					 */
					CruscottoIncassiImportiDto dto = new CruscottoIncassiImportiDto();
					dto.setImportoPagato("€ " + Constants.NUMBER_FORMAT_IT.format(map.get("importoPagato"))); 
					dto.setImportoRendicontato("€ " + Constants.NUMBER_FORMAT_IT.format(map.get("importoRendicontato"))); 
					dto.setImportoIncassato("€ " + Constants.NUMBER_FORMAT_IT.format(map.get("importoIncassato"))); 
					/*
					 * Add to map
					 */
					totRows.put(item.getKey(), dto);
					
					/*
					 * Sommo importi 
					 */
					_footerImportoPagato = _footerImportoPagato.add(map.get("importoPagato"));
					_footerImportoRend =   _footerImportoRend.add(map.get("importoRendicontato"));
					_footerImportoInc =    _footerImportoInc.add(map.get("importoIncassato"));
				}
				
				/**
				 * Set importi totali delle somme degli importi totali per riga.
				 */
				CruscottoIncassiImportiDto footerTotColumns = new CruscottoIncassiImportiDto();
				footerTotColumns.setImportoPagato("€ " + Constants.NUMBER_FORMAT_IT.format(_footerImportoPagato));
				footerTotColumns.setImportoRendicontato("€ " + Constants.NUMBER_FORMAT_IT.format(_footerImportoRend));
				footerTotColumns.setImportoIncassato("€ " + Constants.NUMBER_FORMAT_IT.format(_footerImportoInc));
				
				/**
				 * Return Cruscotto Incassi DTO
				 */
				CruscottoIncassiMultiRowCol dto = new CruscottoIncassiMultiRowCol();
				dto.setHeaderRows(_headerRows);
				dto.setHeaderColumns(_headerColumns);
				dto.setRowcolCell(_rowcolCell);
				dto.setTotRows(totRows);
				dto.setTotColumns(totColumns);
				dto.setDescFiltered(_deUfficioFiltered);
				dto.setFooterTotColumns(footerTotColumns);
			
				logger.debug("DAO :: STATISTICA :: TOTALI RIPARTITI per CAPITOLI :: END");
				
				return dto;
			}else{
				logger.debug("DAO :: STATISTICA :: TOTALI RIPARTITI per CAPITOLI :: END");
				
				return null;
			}
		}catch(Exception e) {
			logger.error("DAO :: STATISTICA :: TOTALI RIPARTITI per CAPITOLI :: ERROR ", e);
			throw new Exception(e);
		}
	}

/* SERVICE Totali Ripartiti Per Capitoli By Dovuto ============================================================================================= */
	
	/** 
	 * Restituisce l'elenco delle ripartizioni per capitoli degli Importi Pagati, Rendicontati e Incassati relativi l'ente e l'anno indicati.
	 * Si richiede di selezionare un tipo dovuto.
	 * 
	 * @param {@link Long}    enteId, 	  	 Identificativo dell'ente
	 * @param {@link Integer} year,       	 L'anno per cui ritornare il dato statistico
	 * @param {@link String}  codTipoDovuto, Codice del tipo dovuto
	 * 
	 * @return {@link CruscottoIncassiMultiRowCol}
	 * 
	 * @throws Exception
	 * @author Marianna Memoli
 	 */
	@SuppressWarnings("unchecked")
	public CruscottoIncassiMultiRowCol getTotaliRipartitiPerCapitoliByAnnoDovuto(Long enteId, Integer year, String codTipoDovuto) throws Exception{
		try {
			logger.debug("DAO :: STATISTICA :: TOTALI RIPARTITI per CAPITOLI :: PARAMETRI DI RICERCA[" + 
						 "enteId:" + enteId + ", year:" + year + ", codTipoDovuto:" + codTipoDovuto + "] :: START");
			
			/*
			 * Controllo se i filtri obbligatori sono valorizzati, se non lo sono lancio un eccezione per sospendere l'esecuzione della query.
			 */
			if (enteId == null || year == null || !StringUtils.hasText(codTipoDovuto)) {
				/*
				 * Interrompo l'esecuzione 
				 */
				String errMsg = "Impossibile avviare la ricerca statistica 'TOTALI RIPARTITI per CAPITOLI'. Parametri obbligatori assenti o non corretti: " + 
								"enteId[" + enteId + "], year[" + year + "], codTipoDovuto[" + codTipoDovuto + "]";
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
								"cod_uff, " +
								"de_uff, " +
								"cod_td, " + 
								"de_td, " +
								"cod_cap, " + 
								"de_cap, " +
								"SUM(imp_pag)  AS imp_pag, " + 
								"SUM(imp_rend) AS imp_rend, " + 
								"SUM(imp_inc)  AS imp_inc " +
						 "FROM " + 
								"vm_statistica_ente_anno_mese_uff_td_cap " +
						 "WHERE " +
						 		"mygov_ente_id = :enteId AND anno = :year AND cod_td = :codTipoDovuto " + 
						 "GROUP BY " + 
						 		"mygov_ente_id, anno, cod_uff, de_uff, cod_td, de_td, cod_cap, de_cap " + 
						 "ORDER BY " +
						 		"cod_uff ";
			
			/** 
			 * Query di selezione
			 */
			Query query = entityManager.createNativeQuery(sql); 
			
			/**
			 * Set param filter
			 */
			query.setParameter("enteId", enteId);
			query.setParameter("year", year);
			query.setParameter("codTipoDovuto", codTipoDovuto);
			
			/* execute */
			List<Object[]> resultSet = query.getResultList();
			

			if(resultSet != null && !resultSet.isEmpty()){
				/**
				 * Instance
				 */
				String _deTipoDovutoFiltered = null;
				Map<String, String> _headerColumns = new HashMap<String, String>(), _headerRows = new HashMap<String, String>();
				Map<String, CruscottoIncassiImportiDto> _rowcolCell = new HashMap<String, CruscottoIncassiImportiDto>();
				Map<String, Map<String, BigDecimal>> _totColumns = new HashMap<String, Map<String, BigDecimal>>(), _totRows = new HashMap<String, Map<String, BigDecimal>>();
				
				/*
				 */
				for (Object[] row : resultSet) {
					/**
					 * Get importi per il ufficio/capitolo
					 */
					BigDecimal importoPagato = (BigDecimal) row[8];			// importo pagato
					BigDecimal importoRendicontato = (BigDecimal) row[9];	// importo rendicontato
					BigDecimal importoIncassato = (BigDecimal) row[10];		// importo incassato
					
					/**
					 * Get if detail RT is enable. Per accedere al dettaglio L'importo deve essere <= 5000 e diverso da ZERO.
					 * 
					 * Compare To ->> Returns: -1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.
					 */
					boolean enableDttImpPagato = importoPagato.compareTo(new BigDecimal(5000)) > 0     || importoPagato.compareTo(BigDecimal.ZERO) == 0 ? Boolean.FALSE : Boolean.TRUE;
					boolean enableDttImpRend = importoRendicontato.compareTo(new BigDecimal(5000)) > 0 || importoRendicontato.compareTo(BigDecimal.ZERO) == 0 ? Boolean.FALSE : Boolean.TRUE;
					boolean enableDttImpInc = importoIncassato.compareTo(new BigDecimal(5000)) > 0     || importoIncassato.compareTo(BigDecimal.ZERO) == 0 ? Boolean.FALSE : Boolean.TRUE;
					
					/**
					 * Get codici e descrizioni
					 */
					String _codUfficio = (String) row[2]; 	String _codTipoDovuto = (String) row[4];    String _codCapitolo = (String) row[6];
					
					String _deUfficio = (String) row[3];    String _deTipoDovuto = (String) row[5];		String _deCapitolo = (String) row[7];
					
					/**
					 * Get descrizione dovuto usato come filtro di ricerca
					 */
					_deTipoDovutoFiltered = StringUtils.hasText(_deTipoDovuto) ? _deTipoDovuto : _codTipoDovuto;
					
					/**
					 * Build DTO importi per il ufficio/capitolo
					 */
					CruscottoIncassiImportiDto importiRowCol = new CruscottoIncassiImportiDto();
					importiRowCol.setImportoPagato("€ " + Constants.NUMBER_FORMAT_IT.format(importoPagato)); 
					importiRowCol.setImportoRendicontato("€ " + Constants.NUMBER_FORMAT_IT.format(importoRendicontato)); 
					importiRowCol.setImportoIncassato("€ " + Constants.NUMBER_FORMAT_IT.format(importoIncassato)); 
					//
					importiRowCol.setEnableDttImportoPagato(enableDttImpPagato);
					importiRowCol.setEnableDttImportoRend(enableDttImpRend);
					importiRowCol.setEnableDttImportoInc(enableDttImpInc);
					
					/**
					 * Aggiungo la nuova associazioni ufficio/capitolo con relativi importi (Formattato come "€ <importo>").
					 */
					_rowcolCell.put(_codUfficio + "_" + _codCapitolo, importiRowCol);
					
					/**
					 * Se non è stato già inserito, aggiungo alla mappa delle intestazione delle colonne il capitolo
					 */
					if(!_headerColumns.containsKey(_codCapitolo)) _headerColumns.put(_codCapitolo, StringUtils.hasText(_deCapitolo) ? _deCapitolo : _codCapitolo);
					/**
					 * Se non è stato già inserito, aggiungo alla mappa delle intestazione delle righe l'ufficio
					 */
					if(!_headerRows.containsKey(_codUfficio)) _headerRows.put(_codUfficio, StringUtils.hasText(_deUfficio) ? _deUfficio : _codUfficio);
					
					/**
					 * Controllo se nella mappa con il totale delle somme degli importi Pagati, Rendicontati e Incassati per colonna
					 * è stato già inserito il capitolo.
					 * Se no, creo il bean DTO degli importi e aggiungo il tutto alla mappa.
					 * 
					 * Se si, recupero dalla mappa il capitolo e il bean degli importi. A questi importi sommo quelli derivati 
					 * dall'iterazione corrente.
					 */
					if(!_totColumns.containsKey(_codCapitolo)){
						/*
						 * Build DTO
						 */
						Map<String, BigDecimal> importo = new HashMap<String, BigDecimal>();
						importo.put("importoPagato", importoPagato);
						importo.put("importoRendicontato", importoRendicontato);
						importo.put("importoIncassato", importoIncassato);
						/*
						 * Add to map
						 */
						_totColumns.put(_codCapitolo, importo);
					}else{
						/*
						 * Get DTO
						 */
						Map<String, BigDecimal> importo = _totColumns.get(_codCapitolo);
						/*
						 * Sommo gli importi  
						 */
						importo.put("importoPagato", importo.get("importoPagato").add(importoPagato));
						importo.put("importoRendicontato", importo.get("importoRendicontato").add(importoRendicontato));
						importo.put("importoIncassato", importo.get("importoIncassato").add(importoIncassato));
						/*
						 * Add to map
						 */
						_totColumns.put(_codCapitolo, importo);
					}
					
					/**
					 * Controllo se nella mappa con il totale delle somme degli importi Pagati, Rendicontati e Incassati per riga
					 * è stato già inserito l'ufficio.
					 * Se no, creo il bean DTO degli importi e aggiungo il tutto alla mappa.
					 * 
					 * Se si, recupero dalla mappa l'ufficio e il bean degli importi. A questi importi sommo quelli derivati 
					 * dall'iterazione corrente.
					 */
					if(!_totRows.containsKey(_codUfficio)){
						/*
						 * Build DTO
						 */
						Map<String, BigDecimal> importo = new HashMap<String, BigDecimal>();
						importo.put("importoPagato", importoPagato);
						importo.put("importoRendicontato", importoRendicontato);
						importo.put("importoIncassato", importoIncassato);
						/*
						 * Add to map
						 */
						_totRows.put(_codUfficio, importo);
					}else{
						/*
						 * Get DTO
						 */
						Map<String, BigDecimal> importo = _totRows.get(_codUfficio);
						/*
						 * Sommo gli importi  
						 */
						importo.put("importoPagato", importo.get("importoPagato").add(importoPagato));
						importo.put("importoRendicontato", importo.get("importoRendicontato").add(importoRendicontato));
						importo.put("importoIncassato", importo.get("importoIncassato").add(importoIncassato));
						/*
						 * Add to map
						 */
						_totRows.put(_codUfficio, importo);
					}
				} // close for
			
				/**
				 * Init variabili in cui salvo il totale delle somme degli importi della colonna dei totali per riga.
				 */
				BigDecimal _footerImportoPagato = BigDecimal.ZERO, _footerImportoRend = BigDecimal.ZERO, _footerImportoInc = BigDecimal.ZERO;		
				
				/**
				 * Riciclo le mappe dei totali per colonna e per riga per formattarli come "€ <importo>".
				 */
				Map<String, CruscottoIncassiImportiDto> totColumns = new HashMap<String, CruscottoIncassiImportiDto>();
				Map<String, CruscottoIncassiImportiDto> totRows = new HashMap<String,CruscottoIncassiImportiDto>();
				
				/**
				 * COLONNE
				 */
				for(Entry<String, Map<String, BigDecimal>> item : _totColumns.entrySet()) {
					Map<String, BigDecimal> map = item.getValue();
					/*
					 * Build DTO
					 */
					CruscottoIncassiImportiDto dto = new CruscottoIncassiImportiDto();
					dto.setImportoPagato("€ " + Constants.NUMBER_FORMAT_IT.format(map.get("importoPagato"))); 
					dto.setImportoRendicontato("€ " + Constants.NUMBER_FORMAT_IT.format(map.get("importoRendicontato"))); 
					dto.setImportoIncassato("€ " + Constants.NUMBER_FORMAT_IT.format(map.get("importoIncassato"))); 
					/*
					 * Add to map
					 */
					totColumns.put(item.getKey(), dto);
				}
				/**
				 * RIGHE
				 */
				for (Entry<String, Map<String, BigDecimal>> item : _totRows.entrySet()) {
					Map<String, BigDecimal> map = item.getValue();
					/*
					 * Build DTO
					 */
					CruscottoIncassiImportiDto dto = new CruscottoIncassiImportiDto();
					dto.setImportoPagato("€ " + Constants.NUMBER_FORMAT_IT.format(map.get("importoPagato"))); 
					dto.setImportoRendicontato("€ " + Constants.NUMBER_FORMAT_IT.format(map.get("importoRendicontato"))); 
					dto.setImportoIncassato("€ " + Constants.NUMBER_FORMAT_IT.format(map.get("importoIncassato"))); 
					/*
					 * Add to map
					 */
					totRows.put(item.getKey(), dto);
					/*
					 * Sommo importi 
					 */
					_footerImportoPagato = _footerImportoPagato.add(map.get("importoPagato"));
					_footerImportoRend =   _footerImportoRend.add(map.get("importoRendicontato"));
					_footerImportoInc =    _footerImportoInc.add(map.get("importoIncassato"));
				}
				
				/**
				 * Set importi totali delle somme degli importi totali per riga.
				 */
				CruscottoIncassiImportiDto footerTotColumns = new CruscottoIncassiImportiDto();
				footerTotColumns.setImportoPagato("€ " + Constants.NUMBER_FORMAT_IT.format(_footerImportoPagato));
				footerTotColumns.setImportoRendicontato("€ " + Constants.NUMBER_FORMAT_IT.format(_footerImportoRend));
				footerTotColumns.setImportoIncassato("€ " + Constants.NUMBER_FORMAT_IT.format(_footerImportoInc));
				
				/**
				 * Return Cruscotto Incassi DTO
				 */
				CruscottoIncassiMultiRowCol dto = new CruscottoIncassiMultiRowCol();
				dto.setHeaderRows(_headerRows);
				dto.setHeaderColumns(_headerColumns);
				dto.setRowcolCell(_rowcolCell);
				dto.setTotRows(totRows);
				dto.setTotColumns(totColumns);
				dto.setDescFiltered(_deTipoDovutoFiltered);
				dto.setFooterTotColumns(footerTotColumns);
				
				logger.debug("DAO :: STATISTICA :: TOTALI RIPARTITI per CAPITOLI :: END");
				
				return dto;
			}else{
				logger.debug("DAO :: STATISTICA :: TOTALI RIPARTITI per CAPITOLI :: END");
				
				return null;
			}
		}catch(Exception e) {
			logger.error("DAO :: STATISTICA :: TOTALI RIPARTITI per CAPITOLI :: ERROR ", e);
			throw new Exception(e);
		}
	}
	
	/** 
	 * Restituisce l'elenco delle ripartizioni per Capitoli degli importi totali, pagati e incassati relativi l'ente e il mese dell'anno indicati.
	 * Si richiede di selezionare un tipo dovuto.
	 * 
	 * @param {@link Long}    enteId, 	  	 Identificativo dell'ente
	 * @param {@link Integer} year,       	 L'anno per cui ritornare il dato statistico
	 * @param {@link Integer} month, 	 	 Il mese per cui ritornare il dato statistico
	 * @param {@link String}  codTipoDovuto, Codice del tipo dovuto
	 * 
	 * @return {@link CruscottoIncassiMultiRowCol}
	 * 
	 * @throws Exception
	 * @author Marianna Memoli
 	 */
	@SuppressWarnings("unchecked")
	public CruscottoIncassiMultiRowCol getTotaliRipartitiPerCapitoliByAnnoMeseDovuto(Long enteId, Integer year, Integer month, String codTipoDovuto) throws Exception{
		try {
			logger.debug("DAO :: STATISTICA :: TOTALI RIPARTITI per CAPITOLI :: PARAMETRI DI RICERCA[" + 
					 	 "enteId:" + enteId + ", year:" + year + ", month:" + month + ", codTipoDovuto:" + codTipoDovuto + "] :: START");
		
			/*
			 * Controllo se i filtri obbligatori sono valorizzati, se non lo sono lancio un eccezione per sospendere l'esecuzione della query.
			 */
			if (enteId == null || year == null || month == null || !StringUtils.hasText(codTipoDovuto)) {
				/*
				 * Interrompo l'esecuzione 
				 */
				String errMsg = "Impossibile avviare la ricerca statistica 'TOTALI RIPARTITI per CAPITOLI'. Parametri obbligatori assenti o non corretti: " + 
								"enteId[" + enteId + "], year[" + year + "], month[" + month + "], codTipoDovuto[" + codTipoDovuto + "]";
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
								"anno,		" +
								"mese, 		" +
								"cod_uff, 	" +
								"de_uff, 	" +
								"cod_td, 	" + 
								"de_td, 	" +
								"cod_cap, 	" + 
								"de_cap, 	" +
								"SUM(imp_pag)  AS imp_pag, " + 
								"SUM(imp_rend) AS imp_rend, " + 
								"SUM(imp_inc)  AS imp_inc " +
						 "FROM " + 
								"vm_statistica_ente_anno_mese_uff_td_cap " +
						 "WHERE " + 
								"mygov_ente_id = :enteId AND anno = :year AND mese = :month AND cod_td = :codTipoDovuto " + 
						 "GROUP BY " + 
							 	"mygov_ente_id, anno, mese, cod_uff, de_uff, cod_td, de_td, cod_cap, de_cap " + 
						 "ORDER BY " +
							 	"cod_uff ";
			
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
			query.setParameter("codTipoDovuto", codTipoDovuto);
			
			/* execute */
			List<Object[]> resultSet = query.getResultList();
			

			if(resultSet != null && !resultSet.isEmpty()){
				/**
				 * Instance
				 */
				String _deTipoDovutoFiltered = null;
				Map<String, String> _headerColumns = new HashMap<String, String>(), _headerRows = new HashMap<String, String>();
				Map<String, CruscottoIncassiImportiDto> _rowcolCell = new HashMap<String, CruscottoIncassiImportiDto>();
				Map<String, Map<String, BigDecimal>> _totColumns = new HashMap<String, Map<String, BigDecimal>>(), _totRows = new HashMap<String, Map<String, BigDecimal>>();
				
				/*
				 */
				for (Object[] row : resultSet) {
					/**
					 * Get importi per il ufficio/capitolo
					 */
					BigDecimal importoPagato = (BigDecimal) row[9];			// importo pagato
					BigDecimal importoRendicontato = (BigDecimal) row[10];	// importo rendicontato
					BigDecimal importoIncassato = (BigDecimal) row[11];		// importo incassato
					
					/**
					 * Get if detail RT is enable. Per accedere al dettaglio L'importo deve essere <= 5000 e diverso da ZERO.
					 * 
					 * Compare To ->> Returns: -1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.
					 */
					boolean enableDttImpPagato = importoPagato.compareTo(new BigDecimal(5000)) > 0     || importoPagato.compareTo(BigDecimal.ZERO) == 0 ? Boolean.FALSE : Boolean.TRUE;
					boolean enableDttImpRend = importoRendicontato.compareTo(new BigDecimal(5000)) > 0 || importoRendicontato.compareTo(BigDecimal.ZERO) == 0 ? Boolean.FALSE : Boolean.TRUE;
					boolean enableDttImpInc = importoIncassato.compareTo(new BigDecimal(5000)) > 0     || importoIncassato.compareTo(BigDecimal.ZERO) == 0 ? Boolean.FALSE : Boolean.TRUE;
					
					/**
					 * Get codici e descrizioni
					 */
					String _codUfficio = (String) row[3]; 	String _codTipoDovuto = (String) row[5];    String _codCapitolo = (String) row[7];
					
					String _deUfficio = (String) row[4];    String _deTipoDovuto = (String) row[6];		String _deCapitolo = (String) row[8];
					
					/**
					 * Get descrizione dovuto usato come filtro di ricerca
					 */
					_deTipoDovutoFiltered = StringUtils.hasText(_deTipoDovuto) ? _deTipoDovuto : _codTipoDovuto;
					
					/**
					 * Build DTO importi per il ufficio/capitolo
					 */
					CruscottoIncassiImportiDto importiRowCol = new CruscottoIncassiImportiDto();
					importiRowCol.setImportoPagato("€ " + Constants.NUMBER_FORMAT_IT.format(importoPagato)); 
					importiRowCol.setImportoRendicontato("€ " + Constants.NUMBER_FORMAT_IT.format(importoRendicontato)); 
					importiRowCol.setImportoIncassato("€ " + Constants.NUMBER_FORMAT_IT.format(importoIncassato)); 
					//
					importiRowCol.setEnableDttImportoPagato(enableDttImpPagato);
					importiRowCol.setEnableDttImportoRend(enableDttImpRend);
					importiRowCol.setEnableDttImportoInc(enableDttImpInc);
					
					/**
					 * Aggiungo la nuova associazioni ufficio/capitolo con relativi importi (Formattato come "€ <importo>").
					 */
					_rowcolCell.put(_codUfficio + "_" + _codCapitolo, importiRowCol);
					
					/**
					 * Se non è stato già inserito, aggiungo alla mappa delle intestazione delle colonne il capitolo
					 */
					if(!_headerColumns.containsKey(_codCapitolo)) _headerColumns.put(_codCapitolo, StringUtils.hasText(_deCapitolo) ? _deCapitolo : _codCapitolo);
					/**
					 * Se non è stato già inserito, aggiungo alla mappa delle intestazione delle righe l'ufficio
					 */
					if(!_headerRows.containsKey(_codUfficio)) _headerRows.put(_codUfficio, StringUtils.hasText(_deUfficio) ? _deUfficio : _codUfficio);
					
					/**
					 * Controllo se nella mappa con il totale delle somme degli importi Pagati, Rendicontati e Incassati per colonna
					 * è stato già inserito il capitolo.
					 * Se no, creo il bean DTO degli importi e aggiungo il tutto alla mappa.
					 * 
					 * Se si, recupero dalla mappa il capitolo e il bean degli importi. A questi importi sommo quelli derivati 
					 * dall'iterazione corrente.
					 */
					if(!_totColumns.containsKey(_codCapitolo)){
						/*
						 * Build DTO
						 */
						Map<String, BigDecimal> importo = new HashMap<String, BigDecimal>();
						importo.put("importoPagato", importoPagato);
						importo.put("importoRendicontato", importoRendicontato);
						importo.put("importoIncassato", importoIncassato);
						/*
						 * Add to map
						 */
						_totColumns.put(_codCapitolo, importo);
					}else{
						/*
						 * Get DTO
						 */
						Map<String, BigDecimal> importo = _totColumns.get(_codCapitolo);
						/*
						 * Sommo gli importi  
						 */
						importo.put("importoPagato", importo.get("importoPagato").add(importoPagato));
						importo.put("importoRendicontato", importo.get("importoRendicontato").add(importoRendicontato));
						importo.put("importoIncassato", importo.get("importoIncassato").add(importoIncassato));
						/*
						 * Add to map
						 */
						_totColumns.put(_codCapitolo, importo);
					}
					
					/**
					 * Controllo se nella mappa con il totale delle somme degli importi Pagati, Rendicontati e Incassati per riga
					 * è stato già inserito l'ufficio.
					 * Se no, creo il bean DTO degli importi e aggiungo il tutto alla mappa.
					 * 
					 * Se si, recupero dalla mappa l'ufficio e il bean degli importi. A questi importi sommo quelli derivati 
					 * dall'iterazione corrente.
					 */
					if(!_totRows.containsKey(_codUfficio)){
						/*
						 * Build DTO
						 */
						Map<String, BigDecimal> importo = new HashMap<String, BigDecimal>();
						importo.put("importoPagato", importoPagato);
						importo.put("importoRendicontato", importoRendicontato);
						importo.put("importoIncassato", importoIncassato);
						/*
						 * Add to map
						 */
						_totRows.put(_codUfficio, importo);
					}else{
						/*
						 * Get DTO
						 */
						Map<String, BigDecimal> importo = _totRows.get(_codUfficio);
						/*
						 * Sommo gli importi  
						 */
						importo.put("importoPagato", importo.get("importoPagato").add(importoPagato));
						importo.put("importoRendicontato", importo.get("importoRendicontato").add(importoRendicontato));
						importo.put("importoIncassato", importo.get("importoIncassato").add(importoIncassato));
						/*
						 * Add to map
						 */
						_totRows.put(_codUfficio, importo);
					}
				}// close for
			
				/**
				 * Init variabili in cui salvo il totale delle somme degli importi della colonna dei totali per riga.
				 */
				BigDecimal _footerImportoPagato = BigDecimal.ZERO, _footerImportoRend = BigDecimal.ZERO, _footerImportoInc = BigDecimal.ZERO;		
				
				/**
				 * Riciclo le mappe dei totali per colonna e per riga per formattarli come "€ <importo>".
				 */
				Map<String, CruscottoIncassiImportiDto> totColumns = new HashMap<String, CruscottoIncassiImportiDto>();
				Map<String, CruscottoIncassiImportiDto> totRows = new HashMap<String,CruscottoIncassiImportiDto>();
				
				/**
				 * COLONNE
				 */
				for(Entry<String, Map<String, BigDecimal>> item : _totColumns.entrySet()) {
					Map<String, BigDecimal> map = item.getValue();
					/*
					 * Build DTO
					 */
					CruscottoIncassiImportiDto dto = new CruscottoIncassiImportiDto();
					dto.setImportoPagato("€ " + Constants.NUMBER_FORMAT_IT.format(map.get("importoPagato"))); 
					dto.setImportoRendicontato("€ " + Constants.NUMBER_FORMAT_IT.format(map.get("importoRendicontato"))); 
					dto.setImportoIncassato("€ " + Constants.NUMBER_FORMAT_IT.format(map.get("importoIncassato"))); 
					/*
					 * Add to map
					 */
					totColumns.put(item.getKey(), dto);
				}
				/**
				 * RIGHE
				 */
				for (Entry<String, Map<String, BigDecimal>> item : _totRows.entrySet()) {
					Map<String, BigDecimal> map = item.getValue();
					/*
					 * Build DTO
					 */
					CruscottoIncassiImportiDto dto = new CruscottoIncassiImportiDto();
					dto.setImportoPagato("€ " + Constants.NUMBER_FORMAT_IT.format(map.get("importoPagato"))); 
					dto.setImportoRendicontato("€ " + Constants.NUMBER_FORMAT_IT.format(map.get("importoRendicontato"))); 
					dto.setImportoIncassato("€ " + Constants.NUMBER_FORMAT_IT.format(map.get("importoIncassato"))); 
					/*
					 * Add to map
					 */
					totRows.put(item.getKey(), dto);
					/*
					 * Sommo importi 
					 */
					_footerImportoPagato = _footerImportoPagato.add(map.get("importoPagato"));
					_footerImportoRend =   _footerImportoRend.add(map.get("importoRendicontato"));
					_footerImportoInc =    _footerImportoInc.add(map.get("importoIncassato"));
				}
				
				/**
				 * Set importi totali delle somme degli importi totali per riga.
				 */
				CruscottoIncassiImportiDto footerTotColumns = new CruscottoIncassiImportiDto();
				footerTotColumns.setImportoPagato("€ " + Constants.NUMBER_FORMAT_IT.format(_footerImportoPagato));
				footerTotColumns.setImportoRendicontato("€ " + Constants.NUMBER_FORMAT_IT.format(_footerImportoRend));
				footerTotColumns.setImportoIncassato("€ " + Constants.NUMBER_FORMAT_IT.format(_footerImportoInc));
				
				/**
				 * Return Cruscotto Incassi DTO
				 */
				CruscottoIncassiMultiRowCol dto = new CruscottoIncassiMultiRowCol();
				dto.setHeaderRows(_headerRows);
				dto.setHeaderColumns(_headerColumns);
				dto.setRowcolCell(_rowcolCell);
				dto.setTotRows(totRows);
				dto.setTotColumns(totColumns);
				dto.setDescFiltered(_deTipoDovutoFiltered);
				dto.setFooterTotColumns(footerTotColumns);
				
				logger.debug("DAO :: STATISTICA :: TOTALI RIPARTITI per CAPITOLI :: END");
				
				return dto;
			}else{
				logger.debug("DAO :: STATISTICA :: TOTALI RIPARTITI per CAPITOLI :: END");
				
				return null;
			}
		}catch(Exception e) {
			logger.error("DAO :: STATISTICA :: TOTALI RIPARTITI per CAPITOLI :: ERROR ", e);
			throw new Exception(e);
		}
	}

	/**
	 * Restituisce l'elenco delle ripartizioni per capitoli degli Importi Pagati, Rendicontati e Incassati relativi l'ente e il giorno nel mese
	 * dell'anno indicato.
	 * Si richiede di selezionare un tipo dovuto.
	 * 
	 * @param {@link Long}    enteId,     	 Identificativo dell'ente
	 * @param {@link Integer} year,       	 L'anno per cui ritornare il dato statistico
	 * @param {@link Integer} month, 	  	 Il mese per cui ritornare il dato statistico
	 * @param {@link Integer} day,      	 Il giorno per cui ritornare il dato statistico
	 * @param {@link String}  codTipoDovuto, Codice del tipo dovuto
	 * 
	 * @return {@link CruscottoIncassiMultiRowCol}
	 * 
	 * @throws Exception
	 * @author Marianna Memoli
 	 */
	@SuppressWarnings("unchecked")
	public CruscottoIncassiMultiRowCol getTotaliRipartitiPerCapitoliByAnnoMeseGiornoDovuto(Long enteId, Integer year, Integer month, Integer day, String codTipoDovuto) throws Exception {
		try {
			logger.debug("DAO :: STATISTICA :: TOTALI RIPARTITI per CAPITOLI :: PARAMETRI DI RICERCA[" + 
						 "enteId:" + enteId + ", year:" + year + ", month:" + month  + ", day:" + day + ", codTipoDovuto:" + codTipoDovuto + "] :: START");
			
			/*
			 * Controllo se i filtri obbligatori sono valorizzati, se non lo sono lancio un eccezione per sospendere l'esecuzione della query.
			 */
			if (enteId == null || year == null || month == null || day == null || !StringUtils.hasText(codTipoDovuto)) {
				/*
				 * Interrompo l'esecuzione 
				 */
				String errMsg = "Impossibile avviare la ricerca statistica 'TOTALI RIPARTITI per CAPITOLI'. Parametri obbligatori assenti o non corretti: " + 
								"enteId[" + enteId + "], year[" + year + "], month[" + month + "], day[" + day + "], codTipoDovuto[" + codTipoDovuto + "]";
				/* *
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
								"cod_uff, " +
								"de_uff, " +
								"cod_td, " + 
								"de_td, " +
								"cod_cap, " + 
								"de_cap, " +
								"SUM(imp_pag)  AS imp_pag, " + 
								"SUM(imp_rend) AS imp_rend, " + 
								"SUM(imp_inc)  AS imp_inc " +
						 "FROM " + 
								"vm_statistica_ente_anno_mese_giorno_uff_td_cap " +
						 "WHERE " + 
								"mygov_ente_id = :enteId AND anno = :year AND mese = :month AND giorno = :day AND cod_td = :codTipoDovuto " +
						 "GROUP BY " + 
						 		"mygov_ente_id, anno, mese, giorno, cod_uff, de_uff, cod_td, de_td, cod_cap, de_cap " + 
						 "ORDER BY " +
								"cod_uff";
			
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
			query.setParameter("codTipoDovuto", codTipoDovuto);
			
			/* execute */
			List<Object[]> resultSet = query.getResultList();
			

			if(resultSet != null && !resultSet.isEmpty()){
				/**
				 * Instance
				 */
				String _deTipoDovutoFiltered = null;
				Map<String, String> _headerColumns = new HashMap<String, String>(), _headerRows = new HashMap<String, String>();
				Map<String, CruscottoIncassiImportiDto> _rowcolCell = new HashMap<String, CruscottoIncassiImportiDto>();
				Map<String, Map<String, BigDecimal>> _totColumns = new HashMap<String, Map<String, BigDecimal>>(), _totRows = new HashMap<String, Map<String, BigDecimal>>();
				
				/*
				 */
				for (Object[] row : resultSet) {
					/**
					 * Get importi per il ufficio/capitolo
					 */
					BigDecimal importoPagato = (BigDecimal) row[10];			// importo pagato
					BigDecimal importoRendicontato = (BigDecimal) row[11];		// importo rendicontato
					BigDecimal importoIncassato = (BigDecimal) row[12];			// importo incassato
					
					/**
					 * Get if detail RT is enable. Per accedere al dettaglio L'importo deve essere <= 5000 e diverso da ZERO.
					 * 
					 * Compare To ->> Returns: -1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.
					 */
					boolean enableDttImpPagato = importoPagato.compareTo(new BigDecimal(5000)) > 0     || importoPagato.compareTo(BigDecimal.ZERO) == 0 ? Boolean.FALSE : Boolean.TRUE;
					boolean enableDttImpRend = importoRendicontato.compareTo(new BigDecimal(5000)) > 0 || importoRendicontato.compareTo(BigDecimal.ZERO) == 0 ? Boolean.FALSE : Boolean.TRUE;
					boolean enableDttImpInc = importoIncassato.compareTo(new BigDecimal(5000)) > 0     || importoIncassato.compareTo(BigDecimal.ZERO) == 0 ? Boolean.FALSE : Boolean.TRUE;
					
					/**
					 * Get codici e descrizioni
					 */
					String _codUfficio = (String) row[4]; 	String _codTipoDovuto = (String) row[6];    String _codCapitolo = (String) row[8];
					
					String _deUfficio = (String) row[5];    String _deTipoDovuto = (String) row[7];		String _deCapitolo = (String) row[9];
					
					/**
					 * Get descrizione dovuto usato come filtro di ricerca
					 */
					_deTipoDovutoFiltered = StringUtils.hasText(_deTipoDovuto) ? _deTipoDovuto : _codTipoDovuto;
					
					/**
					 * Build DTO importi per l' ufficio/capitolo
					 */
					CruscottoIncassiImportiDto importiRowCol = new CruscottoIncassiImportiDto();
					importiRowCol.setImportoPagato("€ " + Constants.NUMBER_FORMAT_IT.format(importoPagato)); 
					importiRowCol.setImportoRendicontato("€ " + Constants.NUMBER_FORMAT_IT.format(importoRendicontato)); 
					importiRowCol.setImportoIncassato("€ " + Constants.NUMBER_FORMAT_IT.format(importoIncassato)); 
					//
					importiRowCol.setEnableDttImportoPagato(enableDttImpPagato);
					importiRowCol.setEnableDttImportoRend(enableDttImpRend);
					importiRowCol.setEnableDttImportoInc(enableDttImpInc);
					
					/**
					 * Aggiungo la nuova associazioni ufficio/capitoli con relativi importi (Formattato come "€ <importo>").
					 */
					_rowcolCell.put(_codUfficio + "_" + _codCapitolo, importiRowCol);
					
					/**
					 * Se non è stato già inserito, aggiungo alla mappa delle intestazione delle colonne il capitolo
					 */
					if(!_headerColumns.containsKey(_codCapitolo)) _headerColumns.put(_codCapitolo, StringUtils.hasText(_deCapitolo) ? _deCapitolo : _codCapitolo);
					/**
					 * Se non è stato già inserito, aggiungo alla mappa delle intestazione delle righe l'ufficio
					 */
					if(!_headerRows.containsKey(_codUfficio)) _headerRows.put(_codUfficio, StringUtils.hasText(_deUfficio) ? _deUfficio : _codUfficio);
					
					/**
					 * Controllo se nella mappa con il totale delle somme degli importi Pagati, Rendicontati e Incassati per colonna
					 * è stato già inserito il capitolo.
					 * Se no, creo il bean DTO degli importi e aggiungo il tutto alla mappa.
					 * 
					 * Se si, recupero dalla mappa il capitolo e il bean degli importi. A questi importi sommo quelli derivati 
					 * dall'iterazione corrente.
					 */
					if(!_totColumns.containsKey(_codCapitolo)){
						/*
						 * Build DTO
						 */
						Map<String, BigDecimal> importo = new HashMap<String, BigDecimal>();
						importo.put("importoPagato", importoPagato);
						importo.put("importoRendicontato", importoRendicontato);
						importo.put("importoIncassato", importoIncassato);
						/*
						 * Add to map
						 */
						_totColumns.put(_codCapitolo, importo);
					}else{
						/*
						 * Get DTO
						 */
						Map<String, BigDecimal> importo = _totColumns.get(_codCapitolo);
						/*
						 * Sommo gli importi  
						 */
						importo.put("importoPagato", importo.get("importoPagato").add(importoPagato));
						importo.put("importoRendicontato", importo.get("importoRendicontato").add(importoRendicontato));
						importo.put("importoIncassato", importo.get("importoIncassato").add(importoIncassato));
						/*
						 * Add to map
						 */
						_totColumns.put(_codCapitolo, importo);
					}
					
					/**
					 * Controllo se nella mappa con il totale delle somme degli importi Pagati, Rendicontati e Incassati per riga
					 * è stato già inserito l'ufficio.
					 * Se no, creo il bean DTO degli importi e aggiungo il tutto alla mappa.
					 * 
					 * Se si, recupero dalla mappa l'ufficio e il bean degli importi. A questi importi sommo quelli derivati 
					 * dall'iterazione corrente.
					 */
					if(!_totRows.containsKey(_codUfficio)){
						/*
						 * Build DTO
						 */
						Map<String, BigDecimal> importo = new HashMap<String, BigDecimal>();
						importo.put("importoPagato", importoPagato);
						importo.put("importoRendicontato", importoRendicontato);
						importo.put("importoIncassato", importoIncassato);
						/*
						 * Add to map
						 */
						_totRows.put(_codUfficio, importo);
					}else{
						/*
						 * Get DTO
						 */
						Map<String, BigDecimal> importo = _totRows.get(_codUfficio);
						/*
						 * Sommo gli importi  
						 */
						importo.put("importoPagato", importo.get("importoPagato").add(importoPagato));
						importo.put("importoRendicontato", importo.get("importoRendicontato").add(importoRendicontato));
						importo.put("importoIncassato", importo.get("importoIncassato").add(importoIncassato));
						/*
						 * Add to map
						 */
						_totRows.put(_codUfficio, importo);
					}
				} // close for
			
				/**
				 * Init variabili in cui salvo il totale delle somme degli importi della colonna dei totali per riga.
				 */
				BigDecimal _footerImportoPagato = BigDecimal.ZERO, _footerImportoRend = BigDecimal.ZERO, _footerImportoInc = BigDecimal.ZERO;		
				
				/**
				 * Riciclo le mappe dei totali per colonna e per riga per formattarli come "€ <importo>".
				 */
				Map<String, CruscottoIncassiImportiDto> totColumns = new HashMap<String, CruscottoIncassiImportiDto>();
				Map<String, CruscottoIncassiImportiDto> totRows = new HashMap<String,CruscottoIncassiImportiDto>();
				
				/**
				 * COLONNE
				 */
				for(Entry<String, Map<String, BigDecimal>> item : _totColumns.entrySet()) {
					Map<String, BigDecimal> map = item.getValue();
					/*
					 * Build DTO
					 */
					CruscottoIncassiImportiDto dto = new CruscottoIncassiImportiDto();
					dto.setImportoPagato("€ " + Constants.NUMBER_FORMAT_IT.format(map.get("importoPagato"))); 
					dto.setImportoRendicontato("€ " + Constants.NUMBER_FORMAT_IT.format(map.get("importoRendicontato"))); 
					dto.setImportoIncassato("€ " + Constants.NUMBER_FORMAT_IT.format(map.get("importoIncassato"))); 
					/*
					 * Add to map
					 */
					totColumns.put(item.getKey(), dto);
				}
				/**
				 * RIGHE
				 */
				for (Entry<String, Map<String, BigDecimal>> item : _totRows.entrySet()) {
					Map<String, BigDecimal> map = item.getValue();
					/*
					 * Build DTO
					 */
					CruscottoIncassiImportiDto dto = new CruscottoIncassiImportiDto();
					dto.setImportoPagato("€ " + Constants.NUMBER_FORMAT_IT.format(map.get("importoPagato"))); 
					dto.setImportoRendicontato("€ " + Constants.NUMBER_FORMAT_IT.format(map.get("importoRendicontato"))); 
					dto.setImportoIncassato("€ " + Constants.NUMBER_FORMAT_IT.format(map.get("importoIncassato"))); 
					/*
					 * Add to map
					 */
					totRows.put(item.getKey(), dto);
					/*
					 * Sommo importi 
					 */
					_footerImportoPagato = _footerImportoPagato.add(map.get("importoPagato"));
					_footerImportoRend =   _footerImportoRend.add(map.get("importoRendicontato"));
					_footerImportoInc =    _footerImportoInc.add(map.get("importoIncassato"));
				}
				
				/**
				 * Set importi totali delle somme degli importi totali per riga.
				 */
				CruscottoIncassiImportiDto footerTotColumns = new CruscottoIncassiImportiDto();
				footerTotColumns.setImportoPagato("€ " + Constants.NUMBER_FORMAT_IT.format(_footerImportoPagato));
				footerTotColumns.setImportoRendicontato("€ " + Constants.NUMBER_FORMAT_IT.format(_footerImportoRend));
				footerTotColumns.setImportoIncassato("€ " + Constants.NUMBER_FORMAT_IT.format(_footerImportoInc));
				
				/**
				 * Return Cruscotto Incassi DTO
				 */
				CruscottoIncassiMultiRowCol dto = new CruscottoIncassiMultiRowCol();
				dto.setHeaderRows(_headerRows);
				dto.setHeaderColumns(_headerColumns);
				dto.setRowcolCell(_rowcolCell);
				dto.setTotRows(totRows);
				dto.setTotColumns(totColumns);
				dto.setDescFiltered(_deTipoDovutoFiltered);
				dto.setFooterTotColumns(footerTotColumns);
				
				logger.debug("DAO :: STATISTICA :: TOTALI RIPARTITI per CAPITOLI :: END");
				
				return dto;
			}else{
				logger.debug("DAO :: STATISTICA :: TOTALI RIPARTITI per CAPITOLI :: END");
				
				return null;
			}
		}catch(Exception e) {
			logger.error("DAO :: STATISTICA :: TOTALI RIPARTITI per CAPITOLI :: ERROR ", e);
			throw new Exception(e);
		}
	}
}