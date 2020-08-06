package it.regioneveneto.mygov.payment.mypivot.dao;

import it.regioneveneto.mygov.payment.mypivot.domain.dto.cruscottoIncassi.CruscottoIncassiDto;

/**
 * Query per l'interrogazione delle viste materializzate relative i dati statistici degli Importi Pagati, Rendicontati e Incassati
 * ripartiti per accertamenti e filtrati per un ufficio, un tipo dovuto e un capitolo e a scelta tra:
 *   - un anno
 *   - un anno ed un mese
 *   - un anno, un mese ed un giorno
 * 
 * @author Marianna Memoli
 */
public interface VmStatisticaAccertamentiDao {
	
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
	public CruscottoIncassiDto getTotaliRipartitiPerAccertamentiByAnno(
								Long enteId, Integer year, String codTipoDovuto, String codUfficio, String codCapitolo) throws Exception;
	
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
	public CruscottoIncassiDto getTotaliRipartitiPerAccertamentiByAnnoMese(
						Long enteId, Integer year, Integer month, String codTipoDovuto, String codUfficio, String codCapitolo) throws Exception;
	
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
	public CruscottoIncassiDto getTotaliRipartitiPerAccertamentiByAnnoMeseGiorno(
						Long enteId, Integer year, Integer month, Integer day, String codTipoDovuto, String codUfficio, String codCapitolo) throws Exception;

}