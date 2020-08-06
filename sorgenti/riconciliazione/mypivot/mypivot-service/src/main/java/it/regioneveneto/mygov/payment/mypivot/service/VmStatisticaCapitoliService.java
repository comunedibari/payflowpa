package it.regioneveneto.mygov.payment.mypivot.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.regioneveneto.mygov.payment.mypivot.domain.dto.cruscottoIncassi.CruscottoIncassiDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.cruscottoIncassi.CruscottoIncassiMultiRowCol;

/**
 * 
 * @author Marianna Memoli
 *
 */
public interface VmStatisticaCapitoliService {

/* SERVICE Totali Ripartiti Per Capitoli By Ufficio e Dovuto ================================================================================== */
	
	/** 
	 * Restituisce l'elenco delle ripartizioni per capitoli degli Importi Pagati, Rendicontati e Incassati relativi l'ente e l'anno indicati.
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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CruscottoIncassiDto getTotaliRipartitiPerCapitoliByAnnoUfficioDovuto(Long enteId, Integer year, String codUfficio, String codTipoDovuto) throws Exception;
	
	/** 
	 * Restituisce l'elenco delle ripartizioni per uffici degli Importi Pagati, Rendicontati e Incassati relativi l'ente e il mese dell'anno indicati.
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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CruscottoIncassiDto getTotaliRipartitiPerCapitoliByAnnoMeseGiornoUfficioDovuto(
								Long enteId, Integer year, Integer month, Integer day, String codUfficio, String codTipoDovuto) throws Exception;

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CruscottoIncassiMultiRowCol getTotaliRipartitiPerCapitoliByAnnoUfficio(Long enteId, Integer year, String codUfficio) throws Exception;
	
	/** 
	 * Restituisce l'elenco delle ripartizioni per uffici degli Importi Pagati, Rendicontati e Incassati relativi l'ente e il mese dell'anno indicati.
	 * Si richiede di selezionare un ufficio.
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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CruscottoIncassiMultiRowCol getTotaliRipartitiPerCapitoliByAnnoMeseUfficio(Long enteId, Integer year, Integer month, String codUfficio) throws Exception;
	
	/**
	 * Restituisce l'elenco delle ripartizioni per capitoli degli Importi Pagati, Rendicontati e Incassati relativi l'ente e il giorno 
	 * nel mese dell'anno indicati.
	 * 
	 * @param {@link Long}    enteId,    Identificativo dell'ente
	 * @param {@link Integer} year,      L'anno per cui ritornare il dato statistico
	 * @param {@link Integer} month, 	 Il mese per cui ritornare il dato statistico
	 * @param {@link Integer} day,       Il giorno per cui ritornare il dato statistico
	 * @param {@link String} codUfficio, Codice ufficio
	 * 
	 * @return {@link CruscottoIncassiMultiRowCol}
	 * 
	 * @throws Exception
	 * @author Marianna Memoli
 	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CruscottoIncassiMultiRowCol getTotaliRipartitiPerCapitoliByAnnoDovuto(Long enteId, Integer year, String codTipoDovuto) throws Exception;
	
	/** 
	 * Restituisce l'elenco delle ripartizioni per capitoli degli importi totali, pagati e incassati relativi l'ente e il mese dell'anno indicati.
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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CruscottoIncassiMultiRowCol getTotaliRipartitiPerCapitoliByAnnoMeseGiornoDovuto(
										Long enteId, Integer year, Integer month, Integer day, String codTipoDovuto) throws Exception;
	
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
	 * 
	 * @return {@link List<String}
	 * @throws Exception
	 * @author Alessandro Paolillo
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<String> findListaPagamentiByFilter(Long enteId, Integer anno, Integer mese, Integer giorno, String codUfficio, String codTipoDovuto, String codCapitolo) throws Exception;
}