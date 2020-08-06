package it.regioneveneto.mygov.payment.mypivot.dao;


import java.util.List;

import it.regioneveneto.mygov.payment.mypivot.domain.dto.cruscottoIncassi.CruscottoIncassiDto;

/**
 * Query per l'interrogazione delle viste materializzate relative i dati statistici degli Importi Pagati, Rendicontati e Incassati
 * ripartiti per Uffici e filtrati a scelta tra:
 *   - un anno
 *   - un anno ed un mese
 *   - un anno, un mese ed un giorno
 * 
 * @author Marianna Memoli
 */
public interface VmStatisticaUfficiDao {
	
	/** 
	 * Restituisce l'elenco delle ripartizioni per Uffici degli Importi Pagati, Rendicontati e Incassati relativi l'ente e l'anno indicati.
	 * 
	 * @param {@link Long}         enteId, 		  Identificativo dell'ente
	 * @param {@link Integer} 	   year,  		  L'anno per cui ritornare il dato statistico
	 * @param {@link List<String>} codTipiDovuto, Elenco dei tipi dovuto sui quali l'utente autenticato ha i permessi di operatore
	 * 
	 * @return {@link CruscottoIncassiDto}
	 * 
	 * @throws Exception
	 * @author Marianna Memoli
 	 */
	public CruscottoIncassiDto getTotaliRipartitiPerUfficiByAnno(Long enteId, Integer year, List<String> codTipiDovuto) throws Exception;
	
	/** 
	 * Restituisce l'elenco delle ripartizioni per Uffici degli Importi Pagati, Rendicontati e Incassati relativi l'ente e 
	 * il mese dell'anno indicati.
	 * 
	 * @param {@link Long}    	   enteId, 		  Identificativo dell'ente
	 * @param {@link Integer} 	   year,   		  L'anno per cui ritornare il dato statistico
	 * @param {@link Integer} 	   month,  	   	  Il mese per cui ritornare il dato statistico
	 * @param {@link List<String>} codTipiDovuto, Elenco dei tipi dovuto sui quali l'utente autenticato ha i permessi di operatore
	 * 
	 * @return {@link CruscottoIncassiDto}
	 * 
	 * @throws Exception
	 * @author Marianna Memoli
 	 */
	public CruscottoIncassiDto getTotaliRipartitiPerUfficiByAnnoMese(Long enteId, Integer year, Integer month, List<String> codTipiDovuto) throws Exception;
	
	/**
	 * Restituisce l'elenco delle ripartizioni per Uffici degli Importi Pagati, Rendicontati e Incassati relativi l'ente e il giorno nel mese
	 * dell'anno indicati.
	 *  
	 * @param {@link Long}    	   enteId, 		  Identificativo dell'ente
	 * @param {@link Integer} 	   year, 		  L'anno per cui ritornare il dato statistico
	 * @param {@link Integer} 	   month, 		  Il mese per cui ritornare il dato statistico
	 * @param {@link Integer} 	   day, 		  Il giorno per cui ritornare il dato statistico
	 * @param {@link List<String>} codTipiDovuto, Elenco dei tipi dovuto sui quali l'utente autenticato ha i permessi di operatore
	 * 
	 * @return {@link CruscottoIncassiDto}
	 * 
	 * @throws Exception
	 * @author Marianna Memoli
 	 */
	public CruscottoIncassiDto getTotaliRipartitiPerUfficiByAnnoMeseGiorno(Long enteId, Integer year, Integer month, Integer day, List<String> codTipiDovuto) throws Exception;
}