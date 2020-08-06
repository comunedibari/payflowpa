package it.regioneveneto.mygov.payment.mypivot.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.regioneveneto.mygov.payment.mypivot.dao.VmStatisticaDovutiDao;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.cruscottoIncassi.CruscottoIncassiDto;
import it.regioneveneto.mygov.payment.mypivot.service.VmStatisticaDovutiService;

/**
 * 
 * @author Marianna Memoli
 *
 */
@Service
public class VmStatisticaDovutiServiceImpl implements VmStatisticaDovutiService {
	
	private static final Logger logger = Logger.getLogger(VmStatisticaDovutiServiceImpl.class);
	
	@Autowired
	private VmStatisticaDovutiDao vmStatisticaDovutiDao;

	/** 
	 * Restituisce l'elenco delle ripartizioni per Tipo Dovuto degli Importi Pagati, Rendicontati e Incassati relativi l'ente e l'anno indicati.
	 * 
	 * @param {@link Long}   	   enteId, 	      Identificativo dell'ente
	 * @param {@link Integer} 	   year,          L'anno per cui ritornare il dato statistico
	 * @param {@link List<String>} codTipiDovuto, Elenco dei tipi dovuto sui quali l'utente autenticato ha i permessi di operatore
	 * 
	 * @return {@link CruscottoIncassiDto}
	 * 
	 * @throws Exception
	 * @author Marianna Memoli
 	 */
	@Override
	public CruscottoIncassiDto getTotaliRipartitiPerTipiDovutoByAnno(Long enteId, Integer year, List<String> codTipiDovuto) throws Exception {
		try{
			logger.debug("SERVICE :: STATISTICA :: TOTALI per TIPI DOVUTO :: PARAMETRI DI RICERCA[enteId:" + enteId + ", year:" + year + ", codTipiDovuto:" + codTipiDovuto + "]");

			return vmStatisticaDovutiDao.getTotaliRipartitiPerTipiDovutoByAnno(enteId, year, codTipiDovuto);
			 
		}catch(Exception e){
			logger.error("SERVICE :: STATISTICA :: TOTALI per TIPI DOVUTO :: ERROR ", e);
			throw(e);
		}
	}
	
	/** 
	 * Restituisce l'elenco delle ripartizioni per Tipo Dovuto degli Importi Pagati, Rendicontati e Incassati relativi l'ente e il mese dell'anno indicati.
	 * 
	 * @param {@link Long}    	   enteId, 		  Identificativo dell'ente
	 * @param {@link Integer} 	   year,  		  L'anno per cui ritornare il dato statistico
	 * @param {@link Integer} 	   month,  		  Il mese per cui ritornare il dato statistico
	 * @param {@link List<String>} codTipiDovuto, Elenco dei tipi dovuto sui quali l'utente autenticato ha i permessi di operatore
	 * 
	 * @return {@link CruscottoIncassiDto}
	 * 
	 * @throws Exception
	 * @author Marianna Memoli
 	 */
	@Override
	public CruscottoIncassiDto getTotaliRipartitiPerTipiDovutoByAnnoMese(Long enteId, Integer year, Integer month, List<String> codTipiDovuto) throws Exception {
		try{
			logger.debug("SERVICE :: STATISTICA :: TOTALI per TIPI DOVUTO :: PARAMETRI DI RICERCA[enteId:" + enteId + ", year:" + year + ", " +
						 "month:" + month + ", codTipiDovuto:" + codTipiDovuto + "]");

			return vmStatisticaDovutiDao.getTotaliRipartitiPerTipiDovutoByAnnoMese(enteId, year, month, codTipiDovuto);
			 
		}catch(Exception e){
			logger.error("SERVICE :: STATISTICA :: TOTALI per TIPI DOVUTO :: ERROR ", e);
			throw(e);
		}
	}
	
	/**
	 * Restituisce l'elenco delle ripartizioni per Tipo Dovuto degli Importi Pagati, Rendicontati e Incassati relativi l'ente e il giorno
	 * nel mese dell'anno.
	 * 
	 * @param {@link Long}    	   enteId, 		  Identificativo dell'ente
	 * @param {@link Integer} 	   year,  		  L'anno per cui ritornare il dato statistico
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
	public CruscottoIncassiDto getTotaliRipartitiPerTipiDovutoByAnnoMeseGiorno(Long enteId,  Integer year, Integer month, Integer day, List<String> codTipiDovuto) throws Exception {
		try{
			logger.debug("SERVICE :: STATISTICA :: TOTALI per TIPI DOVUTO :: PARAMETRI DI RICERCA[enteId:" + enteId + ", year:" + year + ", " + 
						 "month:" + month + ", day:" + day + ", codTipiDovuto:" + codTipiDovuto + "]");

			return vmStatisticaDovutiDao.getTotaliRipartitiPerTipiDovutoByAnnoMeseGiorno(enteId, year, month, day, codTipiDovuto);
			 
		}catch(Exception e){
			logger.error("SERVICE :: STATISTICA :: TOTALI per TIPI DOVUTO :: ERROR ", e);
			throw(e);
		}
	}
}