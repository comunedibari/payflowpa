package it.regioneveneto.mygov.payment.mypivot.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.regioneveneto.mygov.payment.mypivot.dao.DettaglioCruscottoIncassiDao;
import it.regioneveneto.mygov.payment.mypivot.dao.VmStatisticaAccertamentiDao;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.cruscottoIncassi.CruscottoIncassiDto;
import it.regioneveneto.mygov.payment.mypivot.service.VmStatisticaAccertamentiService;

/**
 * 
 * @author Marianna Memoli
 *
 */
@Service
public class VmStatisticaAccertamentiServiceImpl implements VmStatisticaAccertamentiService {
	
	private static final Logger logger = Logger.getLogger(VmStatisticaAccertamentiServiceImpl.class);
	
	@Autowired
	private VmStatisticaAccertamentiDao vmStatisticaAccDao;
	
	@Autowired
	private DettaglioCruscottoIncassiDao dettaglioDao;
	
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
	@Override
	public CruscottoIncassiDto getTotaliRipartitiPerAccertamentiByAnno(Long enteId, Integer year, String codTipoDovuto, String codUfficio, String codCapitolo) throws Exception {
		try{
			logger.debug("SERVICE :: STATISTICA :: TOTALI per ACCERTAMENTI :: PARAMETRI DI RICERCA[enteId:" + enteId + ", year:" + year + ", " +
						 "codTipoDovuto:" + codTipoDovuto + ", codUfficio:" + codUfficio + ", codCapitolo:" + codCapitolo + "]");

			return vmStatisticaAccDao.getTotaliRipartitiPerAccertamentiByAnno(enteId, year, codTipoDovuto, codUfficio, codCapitolo);
			 
		}catch(Exception e){
			logger.error("SERVICE :: STATISTICA :: TOTALI per ACCERTAMENTI :: ERROR ", e);
			throw(e);
		}
	}
	
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
	@Override
	public CruscottoIncassiDto getTotaliRipartitiPerAccertamentiByAnnoMese(Long enteId, Integer year, Integer month, String codTipoDovuto, String codUfficio, String codCapitolo) throws Exception {
		try{
			logger.debug("SERVICE :: STATISTICA :: TOTALI per ACCERTAMENTI :: PARAMETRI DI RICERCA[enteId:" + enteId + ", year:" + year + ", " +
						 "month:" + month + ", codTipoDovuto:" + codTipoDovuto + ", codUfficio:" + codUfficio + ", codCapitolo:" + codCapitolo + "]");

			return vmStatisticaAccDao.getTotaliRipartitiPerAccertamentiByAnnoMese(enteId, year, month, codTipoDovuto, codUfficio, codCapitolo);
			 
		}catch(Exception e){
			logger.error("SERVICE :: STATISTICA :: TOTALI per ACCERTAMENTI :: ERROR ", e);
			throw(e);
		}
	}
	
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
	@Override
	public CruscottoIncassiDto getTotaliRipartitiPerAccertamentiByAnnoMeseGiorno(Long enteId, Integer year, Integer month, Integer day, String codTipoDovuto, String codUfficio, String codCapitolo) throws Exception {
		try{
			logger.debug("SERVICE :: STATISTICA :: TOTALI per ACCERTAMENTI :: PARAMETRI DI RICERCA[enteId:" + enteId + ", year:" + year + ", " +
						 "month:" + month + ", day:" + day + ", codTipoDovuto:" + codTipoDovuto + ", codUfficio:" + codUfficio + ", " + 
						 "codCapitolo:" + codCapitolo + "]");

			return vmStatisticaAccDao.getTotaliRipartitiPerAccertamentiByAnnoMeseGiorno(enteId, year, month, day, codTipoDovuto, codUfficio, codCapitolo);
			 
		}catch(Exception e){
			logger.error("SERVICE :: STATISTICA :: TOTALI per ACCERTAMENTI :: ERROR ", e);
			throw(e);
		}
	}
	
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
	@Override
	public List<String> findListaPagamentiByFilter(Long enteId, Integer anno, Integer mese, Integer giorno, String codUfficio, String codTipoDovuto,
			String codCapitolo, String codAccertamento) throws Exception {
		try{
			logger.debug("SERVICE :: STATISTICA :: LISTA RT CRUSCOTTO :: PARAMETRI DI RICERCA[" + 
					 "enteId:" + enteId + ", anno:" + anno + ", mese:" + mese  + ", giorno:" + giorno + ", codUfficio:" + codUfficio + ", " +
					 "codTipoDovuto:" + codTipoDovuto + "codCapitolo:" + codCapitolo + "codAccertamento:" + codAccertamento + "] :: START");

			return dettaglioDao.findListaPagamentiByFilter(enteId, anno, mese, giorno, codUfficio, codTipoDovuto, codCapitolo, codAccertamento);
			 
		}catch(Exception e){
			logger.error("SERVICE :: STATISTICA :: LISTA RT CRUSCOTTO :: ERROR ", e);
			throw(e);
		}
	}
}