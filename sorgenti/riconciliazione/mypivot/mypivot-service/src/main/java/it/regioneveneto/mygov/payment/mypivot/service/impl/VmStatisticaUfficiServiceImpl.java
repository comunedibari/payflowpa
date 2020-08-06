package it.regioneveneto.mygov.payment.mypivot.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.regioneveneto.mygov.payment.mypivot.dao.VmStatisticaUfficiDao;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.cruscottoIncassi.CruscottoIncassiDto;
import it.regioneveneto.mygov.payment.mypivot.service.VmStatisticaUfficiService;

/**
 * 
 * @author Marianna Memoli
 *
 */
@Service
public class VmStatisticaUfficiServiceImpl implements VmStatisticaUfficiService {
	
	private static final Logger logger = Logger.getLogger(VmStatisticaUfficiServiceImpl.class);
	
	@Autowired
	private VmStatisticaUfficiDao vmStatisticaUfficiDao;
	

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
	@Override
	public CruscottoIncassiDto getTotaliRipartitiPerUfficiByAnno(Long enteId, Integer year, List<String> codTipiDovuto) throws Exception {
		try{
			logger.debug("SERVICE :: STATISTICA :: TOTALI per UFFICI :: PARAMETRI DI RICERCA[enteId:" + enteId + ", year:" + year + ", codTipiDovuto:" + codTipiDovuto + "]");

			return vmStatisticaUfficiDao.getTotaliRipartitiPerUfficiByAnno(enteId, year, codTipiDovuto);
			 
		}catch(Exception e){
			logger.error("SERVICE :: STATISTICA :: TOTALI per UFFICI :: ERROR ", e);
			throw(e);
		}
	}
	
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
	@Override
	public CruscottoIncassiDto getTotaliRipartitiPerUfficiByAnnoMese(Long enteId, Integer year, Integer month, List<String> codTipiDovuto) throws Exception {
		try{
			logger.debug("SERVICE :: STATISTICA :: TOTALI per UFFICI :: PARAMETRI DI RICERCA[enteId:" + enteId + ", year:" + year + ", " +
						 "month:" + month + ", codTipiDovuto:" + codTipiDovuto + "]");

			return vmStatisticaUfficiDao.getTotaliRipartitiPerUfficiByAnnoMese(enteId, year, month, codTipiDovuto);
			 
		}catch(Exception e){
			logger.error("SERVICE :: STATISTICA :: TOTALI per UFFICI :: ERROR ", e);
			throw(e);
		}
	}
	
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
	@Override
	public CruscottoIncassiDto getTotaliRipartitiPerUfficiByAnnoMeseGiorno(Long enteId, Integer year, Integer month, Integer day, List<String> codTipiDovuto) throws Exception {
		try{
			logger.debug("SERVICE :: STATISTICA :: TOTALI per UFFICI :: PARAMETRI DI RICERCA[enteId:" + enteId + ", year:" + year + ", " +
						 "month:" + month + ", day:" + day + ", codTipiDovuto:" + codTipiDovuto + "]");

			return vmStatisticaUfficiDao.getTotaliRipartitiPerUfficiByAnnoMeseGiorno(enteId, year, month, day, codTipiDovuto);
			 
		}catch(Exception e){
			logger.error("SERVICE :: STATISTICA :: TOTALI per UFFICI :: ERROR ", e);
			throw(e);
		}
	}
}