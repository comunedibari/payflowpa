package it.regioneveneto.mygov.payment.mypivot.dao;


import java.util.List;

import it.regioneveneto.mygov.payment.mypivot.domain.dto.cruscottoIncassi.CruscottoIncassiAnnoMeseGiornoDto;

/**
 * Query per l'interrogazione delle viste materializzate relative le seguenti tipologie di statistiche:
 * 
 *    - TOTALI per ANNO
 *    - TOTALI per MESE
 *    - TOTALI per GIORNO
 * 
 * @author Marianna Memoli
 */
public interface VmStatisticaAnnoMeseGiornoDao {

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
	public CruscottoIncassiAnnoMeseGiornoDto getTotaliPerAnno(Long enteId, List<Integer> years) throws Exception;
	
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
	public CruscottoIncassiAnnoMeseGiornoDto getTotaliPerAnnoMese(Long enteId, Integer year, List<Integer> months) throws Exception;
	
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
	public CruscottoIncassiAnnoMeseGiornoDto getTotaliPerAnnoMeseGiorno(Long enteId, String fromDay, String toDay) throws Exception;
}