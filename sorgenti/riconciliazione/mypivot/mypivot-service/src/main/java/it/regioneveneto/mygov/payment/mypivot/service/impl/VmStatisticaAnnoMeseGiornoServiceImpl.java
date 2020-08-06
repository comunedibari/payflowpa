package it.regioneveneto.mygov.payment.mypivot.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import it.regioneveneto.mygov.payment.mypivot.dao.VmStatisticaAnnoMeseGiornoDao;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.cruscottoIncassi.CruscottoIncassiAnnoMeseGiornoDto;
import it.regioneveneto.mygov.payment.mypivot.service.VmStatisticaAnnoMeseGiornoService;

/**
 * 
 * @author Marianna Memoli
 *
 */
@Service
public class VmStatisticaAnnoMeseGiornoServiceImpl implements VmStatisticaAnnoMeseGiornoService {
	
	private static final Logger logger = Logger.getLogger(VmStatisticaAnnoMeseGiornoServiceImpl.class);
	
	@Autowired
	private VmStatisticaAnnoMeseGiornoDao vmStatisticaAMGDao;
	
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
	@Override
	public CruscottoIncassiAnnoMeseGiornoDto getTotaliPerAnno(Long enteId, List<Integer> years) throws Exception {
		try{
			logger.debug("SERVICE :: STATISTICA :: TOTALI per ANNO :: PARAMETRI DI RICERCA[" + 
						 "enteId:" + enteId + ", years:" + (years != null ? StringUtils.collectionToDelimitedString(years, "/") : null) + "]");

			return vmStatisticaAMGDao.getTotaliPerAnno(enteId, years);
			 
		}catch(Exception e){
			logger.error("SERVICE :: STATISTICA :: TOTALI per ANNO :: ERROR ", e);
			throw(e);
		}
	}
	
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
	@Override
	public CruscottoIncassiAnnoMeseGiornoDto getTotaliPerAnnoMese(Long enteId, Integer year, List<Integer> months) throws Exception {
		try{
			logger.debug("SERVICE :: STATISTICA :: TOTALI per MESE :: PARAMETRI DI RICERCA[" + 
					 	 "enteId:" + enteId + ", year:" + year + ", " +
					 	 "months:" + (months != null ? StringUtils.collectionToDelimitedString(months, "/") : null) + "]");

			return vmStatisticaAMGDao.getTotaliPerAnnoMese(enteId, year, months);
			 
		}catch(Exception e){
			logger.error("SERVICE :: STATISTICA :: TOTALI per MESE :: ERROR ", e);
			throw(e);
		}
	}
	
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
	@Override
	public CruscottoIncassiAnnoMeseGiornoDto getTotaliPerAnnoMeseGiorno(Long enteId, String fromDay, String toDay) throws Exception {
		try{
			logger.debug("SERVICE :: STATISTICA :: TOTALI per GIORNO :: PARAMETRI DI RICERCA[enteId:" + enteId + ", fromDay:" + fromDay + ", toDay:" + toDay + "]");

			return vmStatisticaAMGDao.getTotaliPerAnnoMeseGiorno(enteId, fromDay, toDay);
			 
		}catch(Exception e){
			logger.error("SERVICE :: STATISTICA :: TOTALI per GIORNO :: ERROR ", e);
			throw(e);
		}
	}
}