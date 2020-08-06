package it.regioneveneto.mygov.payment.mypivot.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.regioneveneto.mygov.payment.mypivot.domain.dto.cruscottoIncassi.CruscottoIncassiDto;

/**
 * 
 * @author Marianna Memoli
 *
 */
public interface VmStatisticaAccertamentiService {

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CruscottoIncassiDto getTotaliRipartitiPerAccertamentiByAnno(
								Long enteId, Integer year, String codTipoDovuto, String codUfficio, String codCapitolo) throws Exception;
	
	/** 
	 * Restituisce l'elenco delle ripartizioni per accertamenti degli Importi Pagati, Rendicontati e Incassati relativi l'ente e il mese 
	 * dell'anno indicati. Si richiede di selezionare un ufficio, un tipo dovuto e un capitolo.
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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CruscottoIncassiDto getTotaliRipartitiPerAccertamentiByAnnoMese(
						Long enteId, Integer year, Integer month, String codTipoDovuto, String codUfficio, String codCapitolo) throws Exception;
	
	/**
	 * Restituisce l'elenco delle ripartizioni per accertamenti degli Importi Pagati, Rendicontati e Incassati relativi l'ente e il giorno nel mese 
	 * dell'anno indicati. Si richiede di selezionare un ufficio, un tipo dovuto e un capitolo.
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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CruscottoIncassiDto getTotaliRipartitiPerAccertamentiByAnnoMeseGiorno(
						Long enteId, Integer year, Integer month, Integer day, String codTipoDovuto, String codUfficio, String codCapitolo) throws Exception;

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<String> findListaPagamentiByFilter(Long enteId, Integer anno, Integer mese, Integer giorno, String codUfficio, String codTipoDovuto,
			String codCapitolo, String codAccertamento) throws Exception;
}