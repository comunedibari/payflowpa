package it.regioneveneto.mygov.payment.mypivot.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.regioneveneto.mygov.payment.mypivot.dao.DettaglioCruscottoIncassiDao;
import it.regioneveneto.mygov.payment.mypivot.dao.VmStatisticaCapitoliDao;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.cruscottoIncassi.CruscottoIncassiDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.cruscottoIncassi.CruscottoIncassiMultiRowCol;
import it.regioneveneto.mygov.payment.mypivot.service.VmStatisticaCapitoliService;

/**
 * 
 * @author Marianna Memoli
 *
 */
@Service
public class VmStatisticaCapitoliServiceImpl implements VmStatisticaCapitoliService {
	
	private static final Logger logger = Logger.getLogger(VmStatisticaCapitoliServiceImpl.class);
	
	@Autowired
	private VmStatisticaCapitoliDao vmStatisticaCapitoliDao;

	@Autowired
	private DettaglioCruscottoIncassiDao dettaglioDao;
	
	
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
	@Override
	public List<String> findListaPagamentiByFilter(Long enteId, Integer anno, Integer mese, Integer giorno, String codUfficio, String codTipoDovuto, String codCapitolo) throws Exception {
		try{
			logger.debug("SERVICE :: STATISTICA :: LISTA RT CRUSCOTTO :: PARAMETRI DI RICERCA[" + 
					 "enteId:" + enteId + ", anno:" + anno + ", mese:" + mese  + ", giorno:" + giorno + ", codUfficio:" + codUfficio + ", " +
					 "codTipoDovuto:" + codTipoDovuto + "codCapitolo:" + codCapitolo + "] :: START");

			return dettaglioDao.findListaPagamentiByFilter(enteId, anno, mese, giorno, codUfficio, codTipoDovuto, codCapitolo, null);
			 
		}catch(Exception e){
			logger.error("SERVICE :: STATISTICA :: LISTA RT CRUSCOTTO :: ERROR ", e);
			throw(e);
		}
	}
	
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
	@Override
	public CruscottoIncassiDto getTotaliRipartitiPerCapitoliByAnnoUfficioDovuto(Long enteId, Integer year, String codUfficio, String codTipoDovuto) throws Exception {
		try{
			logger.debug("SERVICE :: STATISTICA :: TOTALI per CAPITOLI :: PARAMETRI DI RICERCA[enteId:" + enteId + ", year:" + year + ", " +
						 "codTipoDovuto:" + codTipoDovuto + ", codUfficio:" + codUfficio + "]");

			return vmStatisticaCapitoliDao.getTotaliRipartitiPerCapitoliByAnnoUfficioDovuto(enteId, year, codUfficio, codTipoDovuto);
			 
		}catch(Exception e){
			logger.error("SERVICE :: STATISTICA :: TOTALI per CAPITOLI :: ERROR ", e);
			throw(e);
		}
	}
	
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
	@Override
	public CruscottoIncassiDto getTotaliRipartitiPerCapitoliByAnnoMeseUfficioDovuto(Long enteId, Integer year, Integer month, String codUfficio, String codTipoDovuto) throws Exception {
		try{
			logger.debug("SERVICE :: STATISTICA :: TOTALI per CAPITOLI :: PARAMETRI DI RICERCA[enteId:" + enteId + ", year:" + year + ", " +
						 "month:" + month + ", codTipoDovuto:" + codTipoDovuto + ", codUfficio:" + codUfficio + "]");

			return vmStatisticaCapitoliDao.getTotaliRipartitiPerCapitoliByAnnoMeseUfficioDovuto(enteId, year, month, codUfficio, codTipoDovuto);
			 
		}catch(Exception e){
			logger.error("SERVICE :: STATISTICA :: TOTALI per CAPITOLI :: ERROR ", e);
			throw(e);
		}
	}
	
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
	@Override
	public CruscottoIncassiDto getTotaliRipartitiPerCapitoliByAnnoMeseGiornoUfficioDovuto(Long enteId, Integer year, Integer month, Integer day, String codUfficio, String codTipoDovuto) throws Exception {
		try{
			logger.debug("SERVICE :: STATISTICA :: TOTALI per CAPITOLI :: PARAMETRI DI RICERCA[enteId:" + enteId + ", year:" + year + ", " +
						 "month:" + month + ", day:" + day + ", codTipoDovuto:" + codTipoDovuto + ", codUfficio:" + codUfficio + "]");

			return vmStatisticaCapitoliDao.getTotaliRipartitiPerCapitoliByAnnoMeseGiornoUfficioDovuto(enteId, year, month, day, codUfficio, codTipoDovuto);
			 
		}catch(Exception e){
			logger.error("SERVICE :: STATISTICA :: TOTALI per CAPITOLI :: ERROR ", e);
			throw(e);
		}
	}
	
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
	@Override
	public CruscottoIncassiMultiRowCol getTotaliRipartitiPerCapitoliByAnnoUfficio(Long enteId, Integer year, String codUfficio) throws Exception {
		try{
			logger.debug("SERVICE :: STATISTICA :: TOTALI per CAPITOLI :: PARAMETRI DI RICERCA[enteId:" + enteId + ", year:" + year + ", codUfficio:" + codUfficio + "]");

			return vmStatisticaCapitoliDao.getTotaliRipartitiPerCapitoliByAnnoUfficio(enteId, year, codUfficio);
			 
		}catch(Exception e){
			logger.error("SERVICE :: STATISTICA :: TOTALI per CAPITOLI :: ERROR ", e);
			throw(e);
		}
	}
	
	/** 
	 * Restituisce l'elenco delle ripartizioni per capitoli degli Importi Pagati, Rendicontati e Incassati relativi l'ente e il mese 
	 * dell'anno indicati. Si richiede di selezionare un ufficio.
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
	@Override
	public CruscottoIncassiMultiRowCol getTotaliRipartitiPerCapitoliByAnnoMeseUfficio(Long enteId, Integer year, Integer month, String codUfficio) throws Exception {
		try{
			logger.debug("SERVICE :: STATISTICA :: TOTALI per CAPITOLI :: PARAMETRI DI RICERCA[enteId:" + enteId + ", year:" + year + ", " +
						 "month:" + month + ", codUfficio:" + codUfficio + "]");

			return vmStatisticaCapitoliDao.getTotaliRipartitiPerCapitoliByAnnoMeseUfficio(enteId, year, month, codUfficio); 
			 
		}catch(Exception e){
			logger.error("SERVICE :: STATISTICA :: TOTALI per CAPITOLI :: ERROR ", e);
			throw(e);
		}
	}
	
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
	@Override
	public CruscottoIncassiMultiRowCol getTotaliRipartitiPerCapitoliByAnnoMeseGiornoUfficio(Long enteId, Integer year, Integer month, Integer day, String codUfficio) throws Exception {
		try{
			logger.debug("SERVICE :: STATISTICA :: TOTALI per CAPITOLI :: PARAMETRI DI RICERCA[enteId:" + enteId + ", year:" + year + ", " +
						 "month:" + month + ", day:" + day + ", codUfficio:" + codUfficio + "]");

			return vmStatisticaCapitoliDao.getTotaliRipartitiPerCapitoliByAnnoMeseGiornoUfficio(enteId, year, month, day, codUfficio);
			 
		}catch(Exception e){
			logger.error("SERVICE :: STATISTICA :: TOTALI per CAPITOLI :: ERROR ", e);
			throw(e);
		}
	}
	
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
	@Override
	public CruscottoIncassiMultiRowCol getTotaliRipartitiPerCapitoliByAnnoDovuto(Long enteId, Integer year, String codTipoDovuto) throws Exception {
		try{
			logger.debug("SERVICE :: STATISTICA :: TOTALI per CAPITOLI :: PARAMETRI DI RICERCA[enteId:" + enteId + ", year:" + year + ", codTipoDovuto:" + codTipoDovuto + "]");

			return vmStatisticaCapitoliDao.getTotaliRipartitiPerCapitoliByAnnoDovuto(enteId, year, codTipoDovuto);
			 
		}catch(Exception e){
			logger.error("SERVICE :: STATISTICA :: TOTALI per CAPITOLI :: ERROR ", e);
			throw(e);
		}
	}
	
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
	@Override
	public CruscottoIncassiMultiRowCol getTotaliRipartitiPerCapitoliByAnnoMeseDovuto(Long enteId, Integer year, Integer month, String codTipoDovuto) throws Exception {
		try{
			logger.debug("SERVICE :: STATISTICA :: TOTALI per CAPITOLI :: PARAMETRI DI RICERCA[enteId:" + enteId + ", year:" + year + ", " +
						 "month:" + month + ", codTipoDovuto:" + codTipoDovuto + "]");

			return vmStatisticaCapitoliDao.getTotaliRipartitiPerCapitoliByAnnoMeseDovuto(enteId, year, month, codTipoDovuto);
			 
		}catch(Exception e){
			logger.error("SERVICE :: STATISTICA :: TOTALI per CAPITOLI :: ERROR ", e);
			throw(e);
		}
	}
	
	/**
	 * Restituisce l'elenco delle ripartizioni per capitoli degli Importi Pagati, Rendicontati e Incassati relativi l'ente e il giorno nel mese
	 * dell'anno indicato. Si richiede di selezionare un tipo dovuto.
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
	@Override
	public CruscottoIncassiMultiRowCol getTotaliRipartitiPerCapitoliByAnnoMeseGiornoDovuto(Long enteId, Integer year, Integer month, Integer day, String codTipoDovuto) throws Exception {
		try{
			logger.debug("SERVICE :: STATISTICA :: TOTALI per CAPITOLI :: PARAMETRI DI RICERCA[enteId:" + enteId + ", year:" + year + ", " +
						 "month:" + month + ", day:" + day + ", codTipoDovuto:" + codTipoDovuto + "]");

			return vmStatisticaCapitoliDao.getTotaliRipartitiPerCapitoliByAnnoMeseGiornoDovuto(enteId, year, month, day, codTipoDovuto);
			 
		}catch(Exception e){
			logger.error("SERVICE :: STATISTICA :: TOTALI per CAPITOLI :: ERROR ", e);
			throw(e);
		}
	}
}