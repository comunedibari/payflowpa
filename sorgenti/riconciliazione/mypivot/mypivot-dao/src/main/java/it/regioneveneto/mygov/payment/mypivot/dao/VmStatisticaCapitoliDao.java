package it.regioneveneto.mygov.payment.mypivot.dao;


import it.regioneveneto.mygov.payment.mypivot.domain.dto.cruscottoIncassi.CruscottoIncassiDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.cruscottoIncassi.CruscottoIncassiMultiRowCol;

/**
 * Query per l'interrogazione delle viste materializzate relative i dati statistici degli Importi Pagati, Rendicontati e Incassati
 * ripartiti per Capitoli e filtrati per un ufficio e/o un tipo dovuto e a scelta tra:
 *   - un anno
 *   - un anno ed un mese
 *   - un anno, un mese ed un giorno
 * 
 * @author Marianna Memoli
 */
public interface VmStatisticaCapitoliDao {
	
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
	public CruscottoIncassiDto getTotaliRipartitiPerCapitoliByAnnoUfficioDovuto(Long enteId, Integer year, String codUfficio, String codTipoDovuto) throws Exception;
	
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
	public CruscottoIncassiDto getTotaliRipartitiPerCapitoliByAnnoMeseUfficioDovuto(Long enteId, Integer year, Integer month, String codUfficio, String codTipoDovuto) throws Exception;
	
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
	public CruscottoIncassiDto getTotaliRipartitiPerCapitoliByAnnoMeseGiornoUfficioDovuto(
								Long enteId, Integer year, Integer month, Integer day, String codUfficio, String codTipoDovuto) throws Exception;

/* SERVICE Totali Ripartiti Per Capitoli By Ufficio =========================================================================================== */
	
	/** 
	 * Restituisce l'elenco delle ripartizioni per Capitoli degli Importi Pagati, Rendicontati e Incassati relativi l'ente e l'anno indicati.
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
	public CruscottoIncassiMultiRowCol getTotaliRipartitiPerCapitoliByAnnoUfficio(Long enteId, Integer year, String codUfficio) throws Exception;
	
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
	public CruscottoIncassiMultiRowCol getTotaliRipartitiPerCapitoliByAnnoMeseUfficio(Long enteId, Integer year, Integer month, String codUfficio) throws Exception;
	
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
	public CruscottoIncassiMultiRowCol getTotaliRipartitiPerCapitoliByAnnoMeseGiornoUfficio(
									Long enteId, Integer year, Integer month, Integer day, String codUfficio) throws Exception;

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
	public CruscottoIncassiMultiRowCol getTotaliRipartitiPerCapitoliByAnnoDovuto(Long enteId, Integer year, String codTipoDovuto) throws Exception;
	
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
	public CruscottoIncassiMultiRowCol getTotaliRipartitiPerCapitoliByAnnoMeseDovuto(Long enteId, Integer year, Integer month, String codTipoDovuto) throws Exception;
	
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
	public CruscottoIncassiMultiRowCol getTotaliRipartitiPerCapitoliByAnnoMeseGiornoDovuto(
										Long enteId, Integer year, Integer month, Integer day, String codTipoDovuto) throws Exception;
}