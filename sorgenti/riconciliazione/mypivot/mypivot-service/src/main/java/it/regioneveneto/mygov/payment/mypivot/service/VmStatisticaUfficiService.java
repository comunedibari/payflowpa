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
public interface VmStatisticaUfficiService {

	/** 
	 * Restituisce l'elenco delle ripartizioni per uffici degli Importi Pagati, Rendicontati e Incassati relativi l'ente e l'anno indicati.
	 * 
	 * @param {@link Long}    	   enteId, 	  	  Identificativo dell'ente
	 * @param {@link Integer} 	   year,      	  L'anno per cui ritornare il dato statistico
	 * @param {@link List<String>} codTipiDovuto, Elenco dei tipi dovuto sui quali l'utente autenticato ha i permessi di operatore
	 * 
	 * @return {@link CruscottoIncassiDto}
	 * 
	 * @throws Exception
	 * @author Marianna Memoli
 	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CruscottoIncassiDto getTotaliRipartitiPerUfficiByAnno(Long enteId, Integer year, List<String> codTipiDovuto) throws Exception;
	
	/** 
	 * Restituisce l'elenco delle ripartizioni per uffici degli Importi Pagati, Rendicontati e Incassati relativi l'ente e il mese dell'anno indicati.
	 * 
	 * @param {@link Long}    	   enteId, 		  Identificativo dell'ente
	 * @param {@link Integer} 	   year,   		  L'anno per cui ritornare il dato statistico
	 * @param {@link Integer} 	   month,  		  Il mese per cui ritornare il dato statistico
	 * @param {@link List<String>} codTipiDovuto, Elenco dei tipi dovuto sui quali l'utente autenticato ha i permessi di operatore
	 * 
	 * @return {@link CruscottoIncassiDto}
	 * 
	 * @throws Exception
	 * @author Marianna Memoli
 	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CruscottoIncassiDto getTotaliRipartitiPerUfficiByAnnoMese(Long enteId, Integer year, Integer month, List<String> codTipiDovuto) throws Exception;
	
	/**
	 * Restituisce l'elenco delle ripartizioni per uffici degli Importi Pagati, Rendicontati e Incassati relativi l'ente e il giorno del mese
	 * dell'anno indicati.
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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CruscottoIncassiDto getTotaliRipartitiPerUfficiByAnnoMeseGiorno(Long enteId, Integer year, Integer month, Integer day, List<String> codTipiDovuto) throws Exception;
}