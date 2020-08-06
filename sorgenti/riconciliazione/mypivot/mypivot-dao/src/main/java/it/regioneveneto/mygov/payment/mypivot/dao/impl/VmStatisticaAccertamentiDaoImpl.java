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

import it.regioneveneto.mygov.payment.mypivot.dao.VmStatisticaAccertamentiDao;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.cruscottoIncassi.CruscottoIncassiDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.cruscottoIncassi.CruscottoIncassiImportiDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.cruscottoIncassi.VmStatisticaDto;
import it.regioneveneto.mygov.payment.mypivot.utils.Constants;

/**
 * Query per l'interrogazione delle viste materializzate relative i dati statistici degli Importi Pagati, Rendicontati e Incassati
 * ripartiti per accertamenti e filtrati per un ufficio, un tipo dovuto e un capitolo e a scelta tra:
 *   - un anno
 *   - un anno ed un mese
 *   - un anno, un mese ed un giorno
 * 
 * @author Marianna Memoli
 */
@Repository
public class VmStatisticaAccertamentiDaoImpl implements VmStatisticaAccertamentiDao {

	private static final Logger logger = Logger.getLogger(VmStatisticaAccertamentiDaoImpl.class);

	@PersistenceContext
	private EntityManager entityManager;
	
	/** 
	 * Restituisce l'elenco delle ripartizioni per accertamenti degli Importi Pagati, Rendicontati e Incassati relativi l'ente e l'anno indicati.
	 * Si richiede di selezionare un ufficio, un tipo dovuto e un capitolo.
	 * 
	 * @param {@link Long}    enteId, 	  	 Identificativo dell'ente
	 * @param {@link Integer} year,       	 L'anno per cui ritornare il dato statistico
	 * @param {@link String}  codTipoDovuto, Codice del tipo dovuto
	 * @param {@link String}  codUfficio,    Codice ufficio
	 * @param {@link String}  codCapitolo,   Codice del capitolo
	 *
	 * @return {@link CruscottoIncassiDtO}
	 * 
	 * @throws Exception
	 * @author Marianna Memoli
 	 */
	@SuppressWarnings("unchecked")
	public CruscottoIncassiDto getTotaliRipartitiPerAccertamentiByAnno(Long enteId, Integer year, String codTipoDovuto, String codUfficio, String codCapitolo) throws Exception{
		try {
			logger.debug("DAO :: STATISTICA :: TOTALI RIPARTITI per ACCERTAMENTI :: PARAMETRI DI RICERCA[" + 
						 "enteId:" + enteId + ", year:" + year + ", codUfficio:" + codUfficio + ", codTipoDovuto:" + codTipoDovuto + 
						 "codCapitolo:" + codCapitolo + "] :: START");
			
			/*
			 * Controllo se i filtri obbligatori sono valorizzati, se non lo sono lancio un eccezione per sospendere l'esecuzione della query.
			 */
			if (enteId == null || year == null || !StringUtils.hasText(codUfficio) || !StringUtils.hasText(codTipoDovuto) || !StringUtils.hasText(codCapitolo)) {
				/*
				 * Interrompo l'esecuzione 
				 */
				String errMsg = "Impossibile avviare la ricerca statistica 'TOTALI RIPARTITI per ACCERTAMENTI'. Parametri obbligatori assenti o non corretti: " + 
								"enteId[" + enteId + "], year[" + year + "], codUfficio[" + codUfficio + "], codTipoDovuto[" + codTipoDovuto + "]" +
								"codCapitolo[" + codCapitolo + "]";
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
								"anno, 		" +
								"cod_uff, 	" + 
								"de_uff, 	" + 
								"cod_td, 	" +
								"de_td, 	" + 
								"cod_cap, 	" + 
								"de_cap, 	" + 
								"cod_acc, 	" + 
								"de_acc, 	" +
								"SUM(imp_pag)  AS imp_pag, " + 
								"SUM(imp_rend) AS imp_rend, " + 
								"SUM(imp_inc)  AS imp_inc " +
						 "FROM " + 
								"vm_statistica_ente_anno_mese_uff_td_cap_acc " +
						 "WHERE " +
						 		"mygov_ente_id = :enteId AND anno = :year AND " + 
						 		"cod_td = :codTipoDovuto AND cod_uff = :codUfficio AND cod_cap = :codCapitolo " + 
						 "GROUP BY " + 
						 		"mygov_ente_id, anno, cod_uff, de_uff, cod_td, de_td, cod_cap, de_cap, cod_acc, de_acc " + 
						 "ORDER BY " +
						 		"de_acc ";
			
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
			query.setParameter("codCapitolo", codCapitolo);
			
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
			 * Descrizione dell'ufficio, del tipo dovuto e del capitolo selezionati come filtro per la ricerca statistica.
			 */
			String _deUfficioFiltered = null, _deTipoDovutoFiltered = null, _deCapitoloFiltered = null;
			
			/*
			 */
			for (Object[] row : rows) {
				/**
				 * Get codici e descrizioni
				 */
				String _codUfficio = (String) row[2];  	  String _codTipoDovuto = (String) row[4];		String _codCapitolo = (String) row[6]; 	 	
				String _deUfficio = (String) row[3];	  String _deTipoDovuto = (String) row[5];	    String _deCapitolo = (String) row[7];
				String _codAccertamento = (String) row[8];		String _deAccertamento = (String) row[9];
				/**
				 * Get Importi
				 */
				BigDecimal importoPagato = (BigDecimal) row[10];			// Importo Pagato
				BigDecimal importoRendicontato = (BigDecimal) row[11];		// Importo Rendicontato
				BigDecimal importoIncassato = (BigDecimal) row[12];			// Importo Incassato
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
				item.setDesc(StringUtils.hasText(_deAccertamento) ? _deAccertamento.replaceAll("\"","&#34;") : _codAccertamento);
				//
				item.setCodice(_codAccertamento);
				//
				item.setImportiDTO(importiDTO);
				
				/* */
				result.add(item);
				
				/** 
				 * Mantengo la descrizione dell'ufficio, del tipo dovuto e del capitolo selezionati
				 */
				_deUfficioFiltered = StringUtils.hasText(_deUfficio) ? _deUfficio : _codUfficio;
				_deTipoDovutoFiltered = StringUtils.hasText(_deTipoDovuto) ? _deTipoDovuto : _codTipoDovuto;
				_deCapitoloFiltered = StringUtils.hasText(_deCapitolo) ? _deCapitolo : _codCapitolo;
				
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
			dto.setDeCapitoloFiltered(_deCapitoloFiltered);
			
			logger.debug("DAO :: STATISTICA :: TOTALI RIPARTITI per ACCERTAMENTI :: END");
			
			return dto;
		}catch(Exception e) {
			logger.error("DAO :: STATISTICA :: TOTALI RIPARTITI per ACCERTAMENTI :: ERROR ", e);
			throw new Exception(e);
		}
	}
	
	/** 
	 * Restituisce l'elenco delle ripartizioni per accertamenti degli Importi Pagati, Rendicontati e Incassati relativi l'ente e il mese 
	 * dell'anno indicati.
	 * Si richiede di selezionare un ufficio, un tipo dovuto e un capitolo.
	 * 
	 * @param {@link Long}    enteId, 	  	 Identificativo dell'ente
	 * @param {@link Integer} year,       	 L'anno per cui ritornare il dato statistico
	 * @param {@link Integer} month, 	 	 Il mese per cui ritornare il dato statistico
	 * @param {@link String}  codTipoDovuto, Codice del tipo dovuto
	 * @param {@link String}  codUfficio,    Codice ufficio
	 * @param {@link String}  codCapitolo,   Codice del capitolo
	 * 
	 * @return {@link CruscottoIncassiDto}
	 * 
	 * @throws Exception
	 * @author Marianna Memoli
 	 */
	@SuppressWarnings("unchecked")
	public CruscottoIncassiDto getTotaliRipartitiPerAccertamentiByAnnoMese(Long enteId, Integer year, Integer month, String codTipoDovuto, String codUfficio, String codCapitolo) throws Exception{
		try {
			logger.debug("DAO :: STATISTICA :: TOTALI RIPARTITI per ACCERTAMENTI :: PARAMETRI DI RICERCA[" + 
					 	 "enteId:" + enteId + ", year:" + year + ", month:" + month + ", codUfficio:" + codUfficio + ", " + 
					 	 "codTipoDovuto: " + codTipoDovuto + "codCapitolo:" + codCapitolo + "codCapitolo:" + codCapitolo + "] :: START");
		
			/*
			 * Controllo se i filtri obbligatori sono valorizzati, se non lo sono lancio un eccezione per sospendere l'esecuzione della query.
			 */
			if (enteId == null || year == null || month == null || !StringUtils.hasText(codUfficio) || !StringUtils.hasText(codTipoDovuto) || !StringUtils.hasText(codCapitolo)) {
				/*
				 * Interrompo l'esecuzione 
				 */
				String errMsg = "Impossibile avviare la ricerca statistica 'TOTALI RIPARTITI per ACCERTAMENTI'. Parametri obbligatori assenti o non corretti: " + 
								"enteId[" + enteId + "], year[" + year + "], month[" + month + "], codUfficio[" + codUfficio + "], " + 
								"codTipoDovuto[" + codTipoDovuto + "codCapitolo:" + codCapitolo + "]";
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
								"anno, 		" +
								"mese, 		" +
								"cod_uff, 	" + 
								"de_uff, 	" + 
								"cod_td, 	" +
								"de_td, 	" + 
								"cod_cap, 	" + 
								"de_cap, 	" + 
								"cod_acc, 	" + 
								"de_acc, 	" +
								"SUM(imp_pag)  AS imp_pag, " + 
								"SUM(imp_rend) AS imp_rend, " + 
								"SUM(imp_inc)  AS imp_inc " +
						 "FROM " + 
								"vm_statistica_ente_anno_mese_uff_td_cap_acc " +
						 "WHERE " +
						 		"mygov_ente_id = :enteId AND anno = :year AND mese = :month AND " + 
						 		"cod_uff = :codUfficio AND cod_td = :codTipoDovuto AND cod_cap = :codCapitolo " + 
						 "GROUP BY " + 
					 			"mygov_ente_id, anno, mese, cod_uff, de_uff, cod_td, de_td, cod_cap, de_cap, cod_acc, de_acc " + 
						 "ORDER BY " +
						 		"de_acc ";
			
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
			query.setParameter("codCapitolo", codCapitolo);
			
			/* execute */
			List<Object[]> rows = query.getResultList();
			
			/**
			 * Init:
			 *   - Totale degli Importi pagati (RT)
			 *   - Totale degli Importi rendicontati
			 *   - Totale degli Importi incassati
			 */
			BigDecimal totImportoPagato = BigDecimal.ZERO, totImportoRendicontato = BigDecimal.ZERO, totImportoIncassato = BigDecimal.ZERO;
			
			/**
			 * Descrizione dell'ufficio, del tipo dovuto e del capitolo selezionati come filtro per la ricerca statistica.
			 */
			String _deUfficioFiltered = null, _deTipoDovutoFiltered = null, _deCapitoloFiltered = null;
			
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
				String _codAccertamento = (String) row[9];		String _deAccertamento = (String) row[10];
				/**
				 * Get Importi
				 */
				BigDecimal importoPagato = (BigDecimal) row[11];			// Importo pagato
				BigDecimal importoRendicontato = (BigDecimal) row[12];		// Importo rendicontato
				BigDecimal importoIncassato = (BigDecimal) row[13];			// Importo incassato
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
				item.setDesc(StringUtils.hasText(_deAccertamento)  ? _deAccertamento.replaceAll("\"","&#34;") : _codAccertamento);
				//
				item.setCodice(_codAccertamento);
				//
				item.setImportiDTO(importiDTO);
				
				/* */
				result.add(item);
				
				/** 
				 * Mantengo la descrizione dell'ufficio, del tipo dovuto e del capitolo selezionati
				 */
				_deUfficioFiltered = StringUtils.hasText(_deUfficio) ? _deUfficio : _codUfficio;
				_deTipoDovutoFiltered = StringUtils.hasText(_deTipoDovuto) ? _deTipoDovuto : _codTipoDovuto;
				_deCapitoloFiltered = StringUtils.hasText(_deCapitolo) ? _deCapitolo : _codCapitolo;
				
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
			dto.setDeCapitoloFiltered(_deCapitoloFiltered);
			
			logger.debug("DAO :: STATISTICA :: TOTALI RIPARTITI per ACCERTAMENTI :: END");
			
			return dto;
		}catch(Exception e) {
			logger.error("DAO :: STATISTICA :: TOTALI RIPARTITI per ACCERTAMENTI :: ERROR ", e);
			throw new Exception(e);
		}
	}
	
	/**
	 * Restituisce l'elenco delle ripartizioni per accertamenti degli Importi Pagati, Rendicontati e Incassati relativi l'ente e il giorno nel mese 
	 * dell'anno indicati.
	 * Si richiede di selezionare un ufficio, un tipo dovuto e un capitolo.
	 * 
	 * @param {@link Long}    enteId,     	 Identificativo dell'ente
	 * @param {@link Integer} year,       	 L'anno per cui ritornare il dato statistico
	 * @param {@link Integer} month, 	 	 Il mese per cui ritornare il dato statistico
	 * @param {@link Integer} day,      	 Il giorno per cui ritornare il dato statistico
	 * @param {@link String}  codTipoDovuto, Codice del tipo dovuto
	 * @param {@link String}  codUfficio,    Codice ufficio
	 * @param {@link String}  codCapitolo,   Codice del capitolo
	 * 
	 * @return {@link CruscottoIncassiDto}
	 * 
	 * @throws Exception
	 * @author Marianna Memoli
 	 */
	@SuppressWarnings("unchecked")
	public CruscottoIncassiDto getTotaliRipartitiPerAccertamentiByAnnoMeseGiorno(Long enteId, Integer year, Integer month, Integer day, String codTipoDovuto, String codUfficio, String codCapitolo) throws Exception {
		try {
			logger.debug("DAO :: STATISTICA :: TOTALI RIPARTITI per ACCERTAMENTI :: PARAMETRI DI RICERCA[" + 
						 "enteId:" + enteId + ", year:" + year + ", month:" + month  + ", day:" + day + ", codUfficio:" + codUfficio + ", " +
						 "codTipoDovuto:" + codTipoDovuto + "codCapitolo:" + codCapitolo + "] :: START");
			
			/*
			 * Controllo se i filtri obbligatori sono valorizzati, se non lo sono lancio un eccezione per sospendere l'esecuzione della query.
			 */
			if (enteId == null || year == null || month == null || day == null || !StringUtils.hasText(codUfficio) || !StringUtils.hasText(codTipoDovuto) || !StringUtils.hasText(codCapitolo)) {
				/*
				 * Interrompo l'esecuzione 
				 */
				String errMsg = "Impossibile avviare la ricerca statistica 'TOTALI RIPARTITI per ACCERTAMENTI'. Parametri obbligatori assenti o non corretti: " + 
								"enteId[" + enteId + "], year[" + year + "], month[" + month + "], day[" + day + "], codUfficio[" + codUfficio + "], " + 
								"codTipoDovuto[" + codTipoDovuto + "], codCapitolo[" + codCapitolo + "]";
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
								"cod_acc, 	" + 
								"de_acc, 	" +
								"SUM(imp_pag)  AS imp_pag, " + 
								"SUM(imp_rend) AS imp_rend, " + 
								"SUM(imp_inc)  AS imp_inc " +
						 "FROM " + 
								"vm_statistica_ente_anno_mese_giorno_uff_td_cap_acc " +
						 "WHERE " + 
								"mygov_ente_id = :enteId AND anno = :year AND mese = :month AND giorno = :day AND " +
								"cod_uff = :codUfficio AND cod_td = :codTipoDovuto AND cod_cap = :codCapitolo " +
						 "GROUP BY " + 
						 		"mygov_ente_id, anno, mese, giorno, cod_uff, de_uff, cod_td, de_td, cod_cap, de_cap, cod_acc, de_acc " + 
						 "ORDER BY " +
								"de_acc";
			
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
			query.setParameter("day", day);
			query.setParameter("codUfficio", codUfficio);
			query.setParameter("codTipoDovuto", codTipoDovuto);
			query.setParameter("codCapitolo", codCapitolo);
			
			/* execute */
			List<Object[]> rows = query.getResultList();
			
			/**
			 * Setto nel dto i valori ritornati dalla query.
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
			String _deUfficioFiltered = null, _deTipoDovutoFiltered = null, _deCapitoloFiltered = null;
			
			/*
			 */
			for (Object[] row : rows) {
				/**
				 * Get codici e descrizioni
				 */
				String _codUfficio = (String) row[4];  	  String _codTipoDovuto = (String) row[6];		String _codCapitolo = (String) row[8]; 	 	
				String _deUfficio = (String) row[5];	  String _deTipoDovuto = (String) row[7];	    String _deCapitolo = (String) row[9];
				String _codAccertamento = (String) row[10];		String _deAccertamento = (String) row[11];
				/**
				 * Get Importi
				 */
				BigDecimal importoPagato = (BigDecimal) row[12];			// Importo pagato
				BigDecimal importoRendicontato = (BigDecimal) row[13];		// Importo rendicontato
				BigDecimal importoIncassato = (BigDecimal) row[14];			// Importo incassato
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
				item.setDesc(StringUtils.hasText(_deAccertamento) ? _deAccertamento.replaceAll("\"","&#34;") : _codAccertamento);
				//
				item.setCodice(_codAccertamento);
				//
				item.setImportiDTO(importiDTO);
			 
				/* */
				result.add(item);
				
				/** 
				 * Mantengo la descrizione dell'ufficio, del tipo dovuto e del capitolo selezionati.
				 */
				_deUfficioFiltered = StringUtils.hasText(_deUfficio) ? _deUfficio : _codUfficio;
				_deTipoDovutoFiltered = StringUtils.hasText(_deTipoDovuto) ? _deTipoDovuto : _codTipoDovuto;
				_deCapitoloFiltered = StringUtils.hasText(_deCapitolo) ? _deCapitolo : _codCapitolo;
				
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
			dto.setDeCapitoloFiltered(_deCapitoloFiltered);
			
			logger.debug("DAO :: STATISTICA :: TOTALI RIPARTITI per ACCERTAMENTI :: END");
			
			return dto;
		}catch(Exception e) {
			logger.error("DAO :: STATISTICA :: TOTALI RIPARTITI per ACCERTAMENTI :: ERROR ", e);
			throw new Exception(e);
		}
	}
}