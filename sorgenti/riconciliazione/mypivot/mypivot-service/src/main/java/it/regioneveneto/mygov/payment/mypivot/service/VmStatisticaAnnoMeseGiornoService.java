package it.regioneveneto.mygov.payment.mypivot.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.regioneveneto.mygov.payment.mypivot.domain.dto.cruscottoIncassi.CruscottoIncassiAnnoMeseGiornoDto;

/**
 * 
 * @author Marianna Memoli
 *
 */
public interface VmStatisticaAnnoMeseGiornoService {

	/**
	 * Restituisce l'elenco degli Importi Pagati, Rendicontati e Incassati relativi l'ente per gli anni indicati.
	 * 
	 * @param {@link Long}     	    enteId, Identificativo dell'ente
	 * @param {@link List<Integer>} years,  Elenco anni per cui ritornare il dato statistico
	 * 
	 * @return {@link CruscottoIncassiAnnoMeseGiornoDto}
	 * 
	 * @throws Exception
	 * @author Marianna Memoli
 	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CruscottoIncassiAnnoMeseGiornoDto getTotaliPerAnno(Long enteId, List<Integer> years) throws Exception;
	
	/** 
	 * Restituisce l'elenco degli Importi Pagati, Rendicontati e Incassati relativi l'ente nei mese dell'anno indicati.
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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CruscottoIncassiAnnoMeseGiornoDto getTotaliPerAnnoMese(Long enteId, Integer year, List<Integer> months) throws Exception;
	
	/**
	 * Restituisce l'elenco degli Importi Pagati, Rendicontati e Incassati relativi i giorni che vanno dalla data d'inizio e fine estrazione.
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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CruscottoIncassiAnnoMeseGiornoDto getTotaliPerAnnoMeseGiorno(Long enteId, String fromDay, String toDay) throws Exception;
}