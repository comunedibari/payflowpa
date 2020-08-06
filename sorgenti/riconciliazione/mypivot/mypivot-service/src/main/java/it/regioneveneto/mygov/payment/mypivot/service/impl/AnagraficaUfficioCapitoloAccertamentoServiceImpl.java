package it.regioneveneto.mygov.payment.mypivot.service.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.RollbackException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import it.regioneveneto.mygov.payment.mypivot.dao.AnagraficaUfficioCapitoloAccertamentoDao;
import it.regioneveneto.mygov.payment.mypivot.dao.AnagraficaUfficioCapitoloAccertamentoRepositoryDao;
import it.regioneveneto.mygov.payment.mypivot.dao.EnteDao;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.AggiornaAnagraficaUfficioCapitoloAccertamentoResultDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.AggiornaAnagraficaUfficioCapitoloAccertamentoResultDto.ESITO;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.AnagraficaUfficioCapitoloAccertamentoDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.PageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.po.AnagraficaUfficioCapitoloAccertamento;
import it.regioneveneto.mygov.payment.mypivot.domain.to.AnagraficaUfficioCapitoloAccertamentoTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTipoDovutoTO;
import it.regioneveneto.mygov.payment.mypivot.service.AnagraficaUfficioCapitoloAccertamentoService;
import it.regioneveneto.mygov.payment.mypivot.service.EnteService;
import it.regioneveneto.mygov.payment.mypivot.service.OperatoreEnteTipoDovutoService;
import it.regioneveneto.mygov.payment.mypivot.utils.SecurityContext;

/**
 * 
 * @author Alessandro Paolillo
 * @author Gianluca De Felice
 *
 */
@Service
public class AnagraficaUfficioCapitoloAccertamentoServiceImpl implements AnagraficaUfficioCapitoloAccertamentoService {

	private static final Logger logger = Logger.getLogger(AnagraficaUfficioCapitoloAccertamentoServiceImpl.class);

	private static Integer MAX_NUMBER_CHAR_COD = 64;
	private static Integer MAX_NUMBER_CHAR_DE = 512;

	@Autowired
	private OperatoreEnteTipoDovutoService operatoreEnteTipoDovutoService;

	@Autowired
	private AnagraficaUfficioCapitoloAccertamentoDao uffCapAccDao;

	@Autowired
	private AnagraficaUfficioCapitoloAccertamentoRepositoryDao anagraficaUfficioCapitoloAccertamentoRepositoryDao;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private EnteDao enteDao;

	@Autowired
	private EnteService enteService;

	@Autowired
	private AnagraficaUfficioCapitoloAccertamentoRepositoryDao repositoryDao;


	@Autowired
	private MessageSource messageSource;
	
	/**
	 * Recupera l'elenco di anagrafiche "uff_cap_acc", eventualmente filtrati.
	 * 
	 * Filtri di ricerca OBBLIGATORI:
	 * ------------------------------------------------------------------------------------------------------------------------------------------
	 * 
	 * @param {@link
	 * 			Long} enteId, Identificativo dell'ente selezionato come
	 *            beaneficiario
	 * 
	 *            Filtri di ricerca OPZIONALI:
	 *            ------------------------------------------------------------------------------------------------------------------------------------------
	 * @param {@link
	 * 			String} codTipoDovuto, Codice tipo dovuto
	 * @param {@link
	 * 			String} codUfficio, Codice dell'ufficio
	 * @param {@link
	 * 			String} deUfficio, Testo digitato contenuto nella descrizione
	 *            dell'ufficio
	 * @param {@link
	 * 			String} codCapitolo, Codice capitolo
	 * @param {@link
	 * 			String} deCapitolo, Testo digitato contenuto nella descrizione del
	 *            capitolo
	 * @param {@link
	 * 			String} deAnnoEsercizio, Anno di esercizio
	 * @param {@link
	 * 			String} codAccertamento, Codice accertamento
	 * @param {@link
	 * 			String} deAccertamento, Testo digitato contenuto nella descrizione
	 *            dell'accertamento
	 * @param {@link
	 * 			String} dtUltimaModificaFrom, Data ultima modifica dal (formato
	 *            dd/MM/yyyy)
	 * @param {@link
	 * 			String} dtUltimaModificaTo, Data ultima modifica al (formato
	 *            dd/MM/yyyy)
	 * @param {@link
	 * 			String} flgAttivo,
	 * 
	 *            Paginazione:
	 *            -----------------------------------------------------------------------------------------------------------------------------------------
	 * @param {@link
	 * 			Boolean} hasPagination, Determina se la query deve essere
	 *            paginata, qualora la fosse diventa obbligatorio la valorizzazione
	 *            dei successivi due parametri.
	 * @param {@link
	 * 			String} page, Visualizza il punto di partenza nel set di dati
	 *            corrente.
	 * @param {@link
	 * 			String} pageSize, Numero di record che la query deve selezione
	 *            nella pagina corrente
	 * 
	 * @param {@link
	 * 			Order} order, Colonna da usare per l'ordinamento della query, se
	 *            assente di default l'ordinamento è per data ultima modifica (DESC)
	 * 
	 * @return {@link PageDto<AnagraficaUfficioCapitoloAccertamentoTO>}
	 * @throws Exception
	 */
	@Override
	public PageDto<AnagraficaUfficioCapitoloAccertamentoDto> findByFilter(Long idEnte, String codTipoDovuto,
			String codUfficio, String deUfficio, String codCapitolo, String deCapitolo, String deAnnoEsercizio,
			String codAccertamento, String deAccertamento, String dtUltimaModificaFrom, String dtUltimaModificaTo,
			Boolean flgAttivo, Boolean hasPagination, int page, int pageSize, Order order) throws Exception {
		try {
			logger.debug("SERVICE :: RICERCA :: UFFICIO_CAPITOLO_ACCERTAMENTO :: PARAMETRI DI RICERCA[" + "enteId:"
					+ idEnte + ", codTipoDovuto:" + codTipoDovuto + ", codUfficio:" + codUfficio + ", " + "deUfficio:"
					+ deUfficio + ", codCapitolo:" + codCapitolo + ", deCapitolo:" + deCapitolo + ", "
					+ "deAnnoEsercizio:" + deAnnoEsercizio + ", codAccertamento:" + codAccertamento
					+ ", deAccertamento:" + deAccertamento + ", " + "dtUltimaModificaFrom:" + dtUltimaModificaFrom
					+ ", dtUltimaModificaTo:" + dtUltimaModificaTo + ", " + "flgAttivo:" + flgAttivo
					+ ", hasPagination:" + hasPagination + ", page:" + page + ", pageSize:" + pageSize + "] :: START");

			/**
			 * ======================================================================================================
			 **/
			/* = CHECK FILTRI OBBLIGATORI = */
			/**
			 * ======================================================================================================
			 **/

			/*
			 * Controllo se i filtri obbligatori sono valorizzati, se non lo sono lancio un
			 * eccezione per sospendere l'esecuzione della query.
			 */
			if (idEnte == null) {
				/*
				 * Interrompo l'esecuzione
				 */
				String errMsg = "Impossibile avviare la ricerca dei record anagrafica_uff_cap_acc. Parametri obbligatori assenti o non corretti: enteId["
						+ idEnte + "].";
				/* */
				logger.error(errMsg);
				/* */
				throw new Exception(errMsg);
			}

			//
			PageDto<AnagraficaUfficioCapitoloAccertamentoDto> ret = uffCapAccDao.findByFilter(idEnte, codUfficio,
					deUfficio, codCapitolo, deCapitolo, deAnnoEsercizio, codAccertamento, deAccertamento, codTipoDovuto,
					flgAttivo, hasPagination, page, pageSize);

			// set content
			ret.setList(ret.getList());

			logger.debug("SERVICE :: RICERCA :: UFFICIO_CAPITOLO_ACCERTAMENTO :: END");

			return ret;
		} catch (Exception e) {
			logger.error("SERVICE :: RICERCA :: UFFICIO_CAPITOLO_ACCERTAMENTO :: ERROR ", e);
			throw new Exception(e);
		}
	}

	/**
	 * Restituisce l'elenco (in distinct) degli uffici dati l'identificativo ente e
	 * il codice tipo dovuto. Il servizio restituisce un'elenco di uffici dove il
	 * bean descrittivo dell'anagrafica è in versione ridotta. In versione ridotta
	 * perchè, di tutte informazioni che descrivono l'anagrafica vengono considerate
	 * solo quelle tali per cui un'ufficio sia correttamente descritto ad un'utente
	 * che ne prende visione.
	 * 
	 * @param {@link
	 * 			Long} enteId, Identificativo ente
	 * @param {@link
	 * 			List<String>} codTipiDovuto, Elenco codici tipi dovuto
	 * @param {@link
	 * 			Boolean} flgAttivo, Stato attivazione ente
	 * 
	 * @return {@link List<AnagraficaUfficioCapitoloAccertamentoTO>}
	 * @throws Exception
	 * @author Marianna Memoli
	 */
	@Override
	public List<AnagraficaUfficioCapitoloAccertamentoTO> findDistinctUfficiByFilter(Long enteId,
			List<String> codTipiDovuto, Boolean flgAttivo) throws Exception {
		try {
			logger.debug("SERVICE :: RICERCA :: UFFICI :: PARAMETRI DI RICERCA[ enteId:" + enteId + ", codTipiDovuto:"
					+ codTipiDovuto + ", flgAttivo:" + flgAttivo + "] :: START");

			// execute query
			List<AnagraficaUfficioCapitoloAccertamento> entities = uffCapAccDao.findDistinctUfficiByFilter(enteId,
					codTipiDovuto, flgAttivo);
			//
			List<AnagraficaUfficioCapitoloAccertamentoTO> beans = new ArrayList<AnagraficaUfficioCapitoloAccertamentoTO>();

			// reverse entity to TO
			for (AnagraficaUfficioCapitoloAccertamento src : entities) {
				/* */
				AnagraficaUfficioCapitoloAccertamentoTO item = new AnagraficaUfficioCapitoloAccertamentoTO();
				/* */
				item.setId(src.getId());
				/* */
				item.setCodTipoDovuto(src.getCodTipoDovuto());
				/* */
				item.setCodUfficio(src.getCodUfficio());
				/* */
				item.setDeUfficio(src.getDeUfficio());
				/* */
				beans.add(item);
			}

			logger.debug("SERVICE :: RICERCA :: UFFICI :: PARAMETRI DI RICERCA[ enteId:" + enteId + ", codTipiDovuto:"
					+ codTipiDovuto + ", flgAttivo:" + flgAttivo + "] :: END");

			return beans;
		} catch (Exception e) {
			logger.error("SERVICE :: RICERCA :: UFFICI :: ERROR ", e);
			throw new Exception(e);
		}
	}

	/**
	 * Restituisce l'elenco (in distinct) dei capitoli dati l'identificativo ente,
	 * l'anno di esercizio, il codice ufficio e il codice tipo dovuto. Il servizio
	 * restituisce un'elenco di capitoli dove il bean descrittivo dell'anagrafica è
	 * in versione ridotta. In versione ridotta perchè, di tutte informazioni che
	 * descrivono l'anagrafica vengono considerate solo quelle tali per cui un
	 * capitolo sia correttamente descritto ad un'utente che ne prende visione.
	 * 
	 * @param {@link
	 * 			Long} enteId, Identificativo ente
	 * @param {@link
	 * 			String} codTipoDovuto, Codice tipo dovuto
	 * @param {@link
	 * 			String} codUfficio, Codice ufficio
	 * @param {@link
	 * 			String} annoEsercizio, Anno di esercizio (Es: yyyy)
	 * @param {@link
	 * 			Boolean} flgAttivo, Stato attivazione ente
	 * 
	 * @return {@link List<AnagraficaUfficioCapitoloAccertamentoTO>}
	 * @author Marianna Memoli
	 */
	@Override
	public List<AnagraficaUfficioCapitoloAccertamentoTO> findDistinctCapitoliByFilter(Long enteId, String codTipoDovuto,
			String codUfficio, String annoEsercizio, Boolean flgAttivo) throws Exception {
		try {
			logger.debug("SERVICE :: RICERCA :: CAPITOLI :: PARAMETRI DI RICERCA[enteId:" + enteId + ", codTipoDovuto:"
					+ codTipoDovuto + ", " + "annoEsercizio:" + annoEsercizio + ", codUfficio:" + codUfficio
					+ ", flgAttivo:" + flgAttivo + "] :: START");

			// execute query
			List<AnagraficaUfficioCapitoloAccertamento> entities = uffCapAccDao.findDistinctCapitoliByFilter(enteId,
					codTipoDovuto, codUfficio, annoEsercizio, flgAttivo);

			//
			List<AnagraficaUfficioCapitoloAccertamentoTO> beans = new ArrayList<AnagraficaUfficioCapitoloAccertamentoTO>();

			// reverse entity to TO
			for (AnagraficaUfficioCapitoloAccertamento src : entities) {
				/* */
				AnagraficaUfficioCapitoloAccertamentoTO item = new AnagraficaUfficioCapitoloAccertamentoTO();
				/* */
				item.setId(src.getId());
				/* */
				item.setCodTipoDovuto(src.getCodTipoDovuto());
				/* */
				item.setCodCapitolo(src.getCodCapitolo());
				/* */
				item.setDeCapitolo(src.getDeCapitolo());
				/* */
				beans.add(item);
			}

			logger.debug("SERVICE :: RICERCA :: CAPITOLI :: PARAMETRI DI RICERCA[enteId:" + enteId + ", codTipoDovuto:"
					+ codTipoDovuto + ", " + "annoEsercizio:" + annoEsercizio + ", codUfficio:" + codUfficio
					+ ", flgAttivo:" + flgAttivo + "] :: END");

			return beans;
		} catch (Exception e) {
			logger.error("SERVICE :: RICERCA :: CAPITOLI :: ERROR ", e);
			throw new Exception(e);
		}
	}

	/**
	 * Restituisce l'elenco (in distinct) dei capitoli dati l'identificativo ente,
	 * il codice ufficio e il codice tipo dovuto. Il servizio restituisce un'elenco
	 * di capitoli dove il bean descrittivo dell'anagrafica è in versione ridotta.
	 * In versione ridotta perchè, di tutte informazioni che descrivono l'anagrafica
	 * vengono considerate solo quelle tali per cui un capitolo sia correttamente
	 * descritto ad un'utente che ne prende visione.
	 * 
	 * @param {@link
	 * 			Long} enteId, Identificativo ente
	 * @param {@link
	 * 			String} codTipoDovuto, Codice tipo dovuto
	 * @param {@link
	 * 			String} codUfficio, Codice ufficio
	 * 
	 * @return {@link List<AnagraficaUfficioCapitoloAccertamentoTO>}
	 * @author Marianna Memoli
	 */
	public List<AnagraficaUfficioCapitoloAccertamentoTO> findDistinctCapitoliByEnteDovutoUfficio(Long enteId,
			String codTipoDovuto, String codUfficio) throws Exception {
		try {
			logger.debug("SERVICE :: RICERCA :: CAPITOLI :: PARAMETRI DI RICERCA[enteId:" + enteId + ", codTipoDovuto:"
					+ codTipoDovuto + ", " + "codUfficio:" + codUfficio + "] :: START");

			// execute query
			List<AnagraficaUfficioCapitoloAccertamento> entities = uffCapAccDao
					.findDistinctCapitoliByEnteDovutoUfficio(enteId, codTipoDovuto, codUfficio);

			//
			List<AnagraficaUfficioCapitoloAccertamentoTO> beans = new ArrayList<AnagraficaUfficioCapitoloAccertamentoTO>();

			// reverse entity to TO
			for (AnagraficaUfficioCapitoloAccertamento src : entities) {
				/* */
				AnagraficaUfficioCapitoloAccertamentoTO item = new AnagraficaUfficioCapitoloAccertamentoTO();
				/* */
				item.setId(src.getId());
				/* */
				item.setCodTipoDovuto(src.getCodTipoDovuto());
				/* */
				item.setCodCapitolo(src.getCodCapitolo());
				/* */
				item.setDeCapitolo(src.getDeCapitolo());
				/* */
				beans.add(item);
			}

			logger.debug("SERVICE :: RICERCA :: CAPITOLI :: PARAMETRI DI RICERCA[enteId:" + enteId + ", codTipoDovuto:"
					+ codTipoDovuto + ", " + "codUfficio:" + codUfficio + "] :: END");

			return beans;
		} catch (Exception e) {
			logger.error("SERVICE :: RICERCA :: CAPITOLI :: ERROR ", e);
			throw new Exception(e);
		}
	}

	/**
	 * Restituisce l'elenco (in distinct) degli accertamenti dati l'identificativo
	 * ente, l'anno di esercizio, il codice ufficio e il codice tipo dovuto. Il
	 * servizio restituisce un'elenco di accertamenti dove il bean descrittivo
	 * dell'anagrafica è in versione ridotta. In versione ridotta perchè, di tutte
	 * informazioni che descrivono l'anagrafica vengono considerate solo quelle tali
	 * per cui un'accertamento sia correttamente descritto ad un'utente che ne
	 * prende visione.
	 * 
	 * @param {@link
	 * 			Long} enteId, Identificativo ente
	 * @param {@link
	 * 			String} codTipoDovuto, Codice tipo dovuto
	 * @param {@link
	 * 			String} codUfficio, Codice ufficio
	 * @param {@link
	 * 			String} annoEsercizio, Anno di esercizio (Es: yyyy)
	 * @param {@link
	 * 			String} codCapitolo, Codice capitolo
	 * @param {@link
	 * 			Boolean} flgAttivo, Stato attivazione ente
	 * 
	 * @return {@link List<AnagraficaUfficioCapitoloAccertamentoTO>}
	 * @throws Exception
	 * @author Marianna Memoli
	 */
	@Override
	public List<AnagraficaUfficioCapitoloAccertamentoTO> findDistinctAccertamentiByFilter(Long enteId,
			String codTipoDovuto, String codUfficio, String annoEsercizio, String codCapitolo, Boolean flgAttivo)
			throws Exception {
		try {
			logger.debug("SERVICE :: RICERCA :: ACCERTAMENTI :: PARAMETRI DI RICERCA[enteId:" + enteId
					+ ", codTipoDovuto:" + codTipoDovuto + ", " + "codUfficio:" + codUfficio + ", annoEsercizio:"
					+ annoEsercizio + "codCapitolo:" + codCapitolo + ", flgAttivo:" + flgAttivo + "] :: START");

			// execute query
			List<AnagraficaUfficioCapitoloAccertamento> entities = uffCapAccDao.findDistinctAccertamentiByFilter(enteId,
					codTipoDovuto, codUfficio, annoEsercizio, codCapitolo, flgAttivo);

			//
			List<AnagraficaUfficioCapitoloAccertamentoTO> beans = new ArrayList<AnagraficaUfficioCapitoloAccertamentoTO>();

			// reverse entity to TO
			for (AnagraficaUfficioCapitoloAccertamento src : entities) {
				/* */
				AnagraficaUfficioCapitoloAccertamentoTO item = new AnagraficaUfficioCapitoloAccertamentoTO();
				/* */
				item.setId(src.getId());
				/* */
				item.setCodTipoDovuto(src.getCodTipoDovuto());
				/* */
				item.setCodAccertamento(src.getCodAccertamento());
				/* */
				item.setDeAccertamento(src.getDeAccertamento());
				/* */
				beans.add(item);
			}

			logger.debug("SERVICE :: RICERCA :: ACCERTAMENTI :: PARAMETRI DI RICERCA[enteId:" + enteId
					+ ", codTipoDovuto:" + codTipoDovuto + ", " + "codUfficio:" + codUfficio + ", annoEsercizio:"
					+ annoEsercizio + "codCapitolo:" + codCapitolo + ", flgAttivo:" + flgAttivo + "] :: END");

			return beans;
		} catch (Exception e) {
			logger.error("SERVICE :: RICERCA :: ACCERTAMENTI :: ERROR ", e);
			throw new Exception(e);
		}
	}

	/**
	 * Restituisce la descrizione dell'ufficio.
	 * 
	 * @param {@link
	 * 			Long} enteId, Identificativo ente
	 * @param {@link
	 * 			String} codUfficio, Codice dell'ufficio
	 * 
	 * @return {@link String}
	 * @throws Exception
	 * @author Marianna Memoli
	 */
	@Override
	public String getDeUfficio(Long enteId, String codUfficio) throws Exception {
		try {
			logger.debug("SERVICE :: RICERCA_DENOMINAZIONE :: UFFICI :: PARAMETRI DI RICERCA[enteId:" + enteId
					+ ", codUfficio:" + codUfficio + "] :: START");

			String desc = repositoryDao.getDeUfficioByIdEnteAndCodUfficio(enteId, codUfficio);

			logger.debug("SERVICE :: RICERCA_DENOMINAZIONE :: UFFICI :: PARAMETRI DI RICERCA[enteId:" + enteId
					+ ", codUfficio:" + codUfficio + "] :: END");

			return desc;
		} catch (Exception e) {
			logger.error("SERVICE :: RICERCA_DENOMINAZIONE :: UFFICI :: ERROR ", e);
			throw new Exception(e);
		}
	}

	/**
	 * Restituisce la descrizione del capitolo
	 * 
	 * @param {@link
	 * 			Long} enteId, Identificativo ente
	 * @param {@link
	 * 			String} codUfficio, Codice dell'ufficio
	 * @param {@link
	 * 			String} codCapitolo, Codice del capitolo
	 * 
	 * @return {@link String}
	 * @throws Exception
	 * @author Marianna Memoli
	 */
	@Override
	public String getDeCapitolo(Long enteId, String codUfficio, String codCapitolo) throws Exception {
		try {
			logger.debug("SERVICE :: RICERCA_DENOMINAZIONE :: CAPITOLI :: PARAMETRI DI RICERCA[enteId:" + enteId
					+ ", codUfficio:" + codUfficio + ", codCapitolo:" + codCapitolo + "] :: START");

			String desc = repositoryDao.getDeCapitoloByIdEnteAndCodUfficioAndCodCapitolo(enteId, codUfficio,
					codCapitolo);

			logger.debug("SERVICE :: RICERCA_DENOMINAZIONE :: CAPITOLI :: PARAMETRI DI RICERCA[enteId:" + enteId
					+ ", codUfficio:" + codUfficio + ", codCapitolo:" + codCapitolo + "] :: END");

			return desc;
		} catch (Exception e) {
			logger.error("SERVICE :: RICERCA_DENOMINAZIONE :: CAPITOLI :: ERROR ", e);
			throw new Exception(e);
		}
	}

	/**
	 * Restituisce la descrizione dell'accertamento
	 * 
	 * @param {@link
	 * 			Long} enteId, Identificativo ente
	 * @param {@link
	 * 			String} codUfficio, Codice dell'ufficio
	 * @param {@link
	 * 			String} codCapitolo, Codice del capitolo
	 * @param {@link
	 * 			String} codAccertamento, Codice dell'accertamento
	 * 
	 * @return {@link String}
	 * @throws Exception
	 * @author Marianna Memoli
	 */
	@Override
	public String getDeAccertamento(Long enteId, String codUfficio, String codCapitolo, String codAccertamento)
			throws Exception {
		try {
			logger.debug("SERVICE :: RICERCA_DENOMINAZIONE :: ACCERTAMENTI :: PARAMETRI DI RICERCA[enteId:" + enteId
					+ ", codUfficio:" + codUfficio + ", codCapitolo:" + codCapitolo + ", codAccertamento:"
					+ codAccertamento + "] :: START");

			String desc = repositoryDao.getDeAccertamentoByIdEnteAndCodUfficioAndCodCapitoloAndCodAccertamento(enteId,
					codUfficio, codCapitolo, codAccertamento);

			logger.debug("SERVICE :: RICERCA_DENOMINAZIONE :: ACCERTAMENTI :: PARAMETRI DI RICERCA[enteId:" + enteId
					+ ", codUfficio:" + codUfficio + ", codCapitolo:" + codCapitolo + ", codAccertamento:"
					+ codAccertamento + "] :: END");

			return desc;
		} catch (Exception e) {
			logger.error("SERVICE :: RICERCA_DENOMINAZIONE :: ACCERTAMENTI :: ERROR ", e);
			throw new Exception(e);
		}
	}

	/**
	 * 
	 */
	@Override
	public AggiornaAnagraficaUfficioCapitoloAccertamentoResultDto aggiornaAnagrafica(
			AnagraficaUfficioCapitoloAccertamentoDto anagraficaUfficioCapitoloAccertamentoDto) throws Exception {
		AggiornaAnagraficaUfficioCapitoloAccertamentoResultDto result = null;

		try {

			AnagraficaUfficioCapitoloAccertamentoDto dtoAggiornato = new AnagraficaUfficioCapitoloAccertamentoDto();

			if (anagraficaUfficioCapitoloAccertamentoDto.getCodAccertamento().equalsIgnoreCase("n/a")) {
				anagraficaUfficioCapitoloAccertamentoDto.setDeAccertamento("n/a");
			}
			int risUpdateAnag = 0;
			AnagraficaUfficioCapitoloAccertamento anagrafica = repositoryDao.exist(
					anagraficaUfficioCapitoloAccertamentoDto.getCodUfficio(),
					anagraficaUfficioCapitoloAccertamentoDto.getCodCapitolo(),
					anagraficaUfficioCapitoloAccertamentoDto.getCodTipoDovuto(),
					anagraficaUfficioCapitoloAccertamentoDto.getCodAccertamento(),
					anagraficaUfficioCapitoloAccertamentoDto.getEnte().getId(),
					anagraficaUfficioCapitoloAccertamentoDto.getDeAnnoEsercizio());
			if (anagrafica != null){
				risUpdateAnag = repositoryDao.aggiornaAnagrafica(anagraficaUfficioCapitoloAccertamentoDto.getId(),
					anagraficaUfficioCapitoloAccertamentoDto.getCodUfficio(),
					anagraficaUfficioCapitoloAccertamentoDto.getDeUfficio(),
					anagraficaUfficioCapitoloAccertamentoDto.getCodCapitolo(),
					anagraficaUfficioCapitoloAccertamentoDto.getDeCapitolo(),
					anagraficaUfficioCapitoloAccertamentoDto.getDeAnnoEsercizio(),
					anagraficaUfficioCapitoloAccertamentoDto.getCodAccertamento(),
					anagraficaUfficioCapitoloAccertamentoDto.getDeAccertamento(),
					anagraficaUfficioCapitoloAccertamentoDto.getCodTipoDovuto(),
					anagraficaUfficioCapitoloAccertamentoDto.getFlgAttivo(), new Date());
			} else {
				return new AggiornaAnagraficaUfficioCapitoloAccertamentoResultDto(ESITO.EXIST, anagraficaUfficioCapitoloAccertamentoDto);
			}

//			int risUpdateAnag = uffCapAccDao.aggiornaAnagrafica(anagraficaUfficioCapitoloAccertamentoDto);
			int risUpdateFlg = 0;

			if (risUpdateAnag > 0 && anagraficaUfficioCapitoloAccertamentoDto.isFlgAttivoCambiato()) {
				/**
				 * La modifica dell'anagrafica è avvenuta correttamente
				 */
				risUpdateFlg = repositoryDao.updateFlgAttivo(anagraficaUfficioCapitoloAccertamentoDto.getCodUfficio(),
						anagraficaUfficioCapitoloAccertamentoDto.getFlgAttivo(), SecurityContext.getEnte().getId());
			}

			if (!anagraficaUfficioCapitoloAccertamentoDto.isFlgAttivoCambiato()) {
				risUpdateFlg = 1;
			}

			int risUpdateDeAccertamento = 0;
			int risUpdateDeCapitolo = 0;
			int risUpdateDeUfficio = 0;
			if (anagraficaUfficioCapitoloAccertamentoDto.getEnte() != null
					&& !SecurityContext.getEnte().getCodIpaEnte().equals("R_VENETO")){
				risUpdateDeCapitolo = repositoryDao.updateDeCapitolo(
						anagraficaUfficioCapitoloAccertamentoDto.getCodCapitolo(),
						anagraficaUfficioCapitoloAccertamentoDto.getDeCapitolo(), SecurityContext.getEnte().getId());
				risUpdateDeUfficio = repositoryDao.updateDeUfficio(
						anagraficaUfficioCapitoloAccertamentoDto.getCodUfficio(),
						anagraficaUfficioCapitoloAccertamentoDto.getDeUfficio(), SecurityContext.getEnte().getId());
				if (!anagraficaUfficioCapitoloAccertamentoDto.getCodAccertamento().equalsIgnoreCase("n/a")) {
					risUpdateDeAccertamento = repositoryDao.updateDeAccertamento(
							anagraficaUfficioCapitoloAccertamentoDto.getCodAccertamento(),
							anagraficaUfficioCapitoloAccertamentoDto.getDeAccertamento(),
							SecurityContext.getEnte().getId());
				}
				if (anagraficaUfficioCapitoloAccertamentoDto.getCodAccertamento().equalsIgnoreCase("n/a")) {
					risUpdateDeAccertamento = 1;
				}
			} else {
				risUpdateDeAccertamento = 1;
				risUpdateDeCapitolo = 1;
				risUpdateDeUfficio = 1;
			}
			
			if (risUpdateAnag > 0 && risUpdateFlg > 0 && risUpdateDeCapitolo > 0
					&& risUpdateDeUfficio > 0 && risUpdateDeAccertamento > 0) {
				/**
				 * Le modifiche sono avvenute correttamente
				 */
				dtoAggiornato = uffCapAccDao.findById(anagraficaUfficioCapitoloAccertamentoDto.getId());

				result = new AggiornaAnagraficaUfficioCapitoloAccertamentoResultDto(ESITO.AGGIORNATA, dtoAggiornato);

			} else {

				String errMsg = "Non è stato possibile aggiornare l'anagrafica";
				result = new AggiornaAnagraficaUfficioCapitoloAccertamentoResultDto(ESITO.ERROR, errMsg);
			}

		} catch (Exception e) {
			throw new RollbackException(e);
		}

		return result;
	}


	@Override
	public AggiornaAnagraficaUfficioCapitoloAccertamentoResultDto aggiornaAnagrafica(
			AnagraficaUfficioCapitoloAccertamentoDto anagraficaUfficioCapitoloAccertamentoDto, String codIpaEnte) throws Exception {
		AggiornaAnagraficaUfficioCapitoloAccertamentoResultDto result = null;

		try {

			AnagraficaUfficioCapitoloAccertamentoDto dtoAggiornato = new AnagraficaUfficioCapitoloAccertamentoDto();

			if (anagraficaUfficioCapitoloAccertamentoDto.getCodAccertamento().equalsIgnoreCase("n/a")) {
				anagraficaUfficioCapitoloAccertamentoDto.setDeAccertamento("n/a");
			}
			int risUpdateAnag = 0;
			AnagraficaUfficioCapitoloAccertamento anagrafica = repositoryDao.exist(
					anagraficaUfficioCapitoloAccertamentoDto.getCodUfficio(),
					anagraficaUfficioCapitoloAccertamentoDto.getCodCapitolo(),
					anagraficaUfficioCapitoloAccertamentoDto.getCodTipoDovuto(),
					anagraficaUfficioCapitoloAccertamentoDto.getCodAccertamento(),
					anagraficaUfficioCapitoloAccertamentoDto.getEnte().getId(),
					anagraficaUfficioCapitoloAccertamentoDto.getDeAnnoEsercizio());
			if (anagrafica != null){
				risUpdateAnag = repositoryDao.aggiornaAnagrafica(anagraficaUfficioCapitoloAccertamentoDto.getId(),
					anagraficaUfficioCapitoloAccertamentoDto.getCodUfficio(),
					anagraficaUfficioCapitoloAccertamentoDto.getDeUfficio(),
					anagraficaUfficioCapitoloAccertamentoDto.getCodCapitolo(),
					anagraficaUfficioCapitoloAccertamentoDto.getDeCapitolo(),
					anagraficaUfficioCapitoloAccertamentoDto.getDeAnnoEsercizio(),
					anagraficaUfficioCapitoloAccertamentoDto.getCodAccertamento(),
					anagraficaUfficioCapitoloAccertamentoDto.getDeAccertamento(),
					anagraficaUfficioCapitoloAccertamentoDto.getCodTipoDovuto(),
					anagraficaUfficioCapitoloAccertamentoDto.getFlgAttivo(), new Date());
			} else {
				return new AggiornaAnagraficaUfficioCapitoloAccertamentoResultDto(ESITO.EXIST, anagraficaUfficioCapitoloAccertamentoDto);
			}

//			int risUpdateAnag = uffCapAccDao.aggiornaAnagrafica(anagraficaUfficioCapitoloAccertamentoDto);
			int risUpdateFlg = 0;

			if (risUpdateAnag > 0 && anagraficaUfficioCapitoloAccertamentoDto.isFlgAttivoCambiato()) {
				/**
				 * La modifica dell'anagrafica è avvenuta correttamente
				 */
				risUpdateFlg = repositoryDao.updateFlgAttivo(anagraficaUfficioCapitoloAccertamentoDto.getCodUfficio(),
						anagraficaUfficioCapitoloAccertamentoDto.getFlgAttivo(), SecurityContext.getEnte().getId());
			}

			if (!anagraficaUfficioCapitoloAccertamentoDto.isFlgAttivoCambiato()) {
				risUpdateFlg = 1;
			}
			
			EnteTO enteTO = enteService.getByCodIpaEnte(codIpaEnte);
			
			int risUpdateDeAccertamento = 0;
			int risUpdateDeCapitolo = 0;
			int risUpdateDeUfficio = 0;
			if (anagraficaUfficioCapitoloAccertamentoDto.getEnte() != null
					&& !SecurityContext.getEnte().getCodIpaEnte().equals("R_VENETO")){
				risUpdateDeCapitolo = repositoryDao.updateDeCapitolo(
						anagraficaUfficioCapitoloAccertamentoDto.getCodCapitolo(),
						anagraficaUfficioCapitoloAccertamentoDto.getDeCapitolo(), enteTO.getId());
				risUpdateDeUfficio = repositoryDao.updateDeUfficio(
						anagraficaUfficioCapitoloAccertamentoDto.getCodUfficio(),
						anagraficaUfficioCapitoloAccertamentoDto.getDeUfficio(), enteTO.getId());
				if (!anagraficaUfficioCapitoloAccertamentoDto.getCodAccertamento().equalsIgnoreCase("n/a")) {
					risUpdateDeAccertamento = repositoryDao.updateDeAccertamento(
							anagraficaUfficioCapitoloAccertamentoDto.getCodAccertamento(),
							anagraficaUfficioCapitoloAccertamentoDto.getDeAccertamento(),
							enteTO.getId());
				}
				if (anagraficaUfficioCapitoloAccertamentoDto.getCodAccertamento().equalsIgnoreCase("n/a")) {
					risUpdateDeAccertamento = 1;
				}
			} else {
				risUpdateDeAccertamento = 1;
				risUpdateDeCapitolo = 1;
				risUpdateDeUfficio = 1;
			}
			
			if (risUpdateAnag > 0 && risUpdateFlg > 0 && risUpdateDeCapitolo > 0
					&& risUpdateDeUfficio > 0 && risUpdateDeAccertamento > 0) {
				/**
				 * Le modifiche sono avvenute correttamente
				 */
				dtoAggiornato = uffCapAccDao.findById(anagraficaUfficioCapitoloAccertamentoDto.getId());

				result = new AggiornaAnagraficaUfficioCapitoloAccertamentoResultDto(ESITO.AGGIORNATA, dtoAggiornato);

			} else {

				String errMsg = "Non è stato possibile aggiornare l'anagrafica";
				result = new AggiornaAnagraficaUfficioCapitoloAccertamentoResultDto(ESITO.ERROR, errMsg);
			}

		} catch (Exception e) {
			throw new RollbackException(e);
		}

		return result;
	}

	//Metodo senza parametri recuperati dal security context
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public AggiornaAnagraficaUfficioCapitoloAccertamentoResultDto modificaAnagrafica(
			AnagraficaUfficioCapitoloAccertamentoDto anagraficaUfficioCapitoloAccertamentoDto, 
			String codIpaEnte
			
			) throws Exception {
		AggiornaAnagraficaUfficioCapitoloAccertamentoResultDto result = null;

		try {

			AnagraficaUfficioCapitoloAccertamentoDto dtoAggiornato = new AnagraficaUfficioCapitoloAccertamentoDto();

			if (anagraficaUfficioCapitoloAccertamentoDto.getCodAccertamento().equalsIgnoreCase("n/a")) {
				anagraficaUfficioCapitoloAccertamentoDto.setDeAccertamento("n/a");
			}
			int risUpdateAnag = 0;
			AnagraficaUfficioCapitoloAccertamento anagrafica = repositoryDao.exist(
					anagraficaUfficioCapitoloAccertamentoDto.getCodUfficio(),
					anagraficaUfficioCapitoloAccertamentoDto.getCodCapitolo(),
					anagraficaUfficioCapitoloAccertamentoDto.getCodTipoDovuto(),
					anagraficaUfficioCapitoloAccertamentoDto.getCodAccertamento(),
					anagraficaUfficioCapitoloAccertamentoDto.getEnte().getId(),
					anagraficaUfficioCapitoloAccertamentoDto.getDeAnnoEsercizio());
			if (anagrafica != null){
				risUpdateAnag = repositoryDao.aggiornaAnagrafica(anagraficaUfficioCapitoloAccertamentoDto.getId(),
					anagraficaUfficioCapitoloAccertamentoDto.getCodUfficio(),
					anagraficaUfficioCapitoloAccertamentoDto.getDeUfficio(),
					anagraficaUfficioCapitoloAccertamentoDto.getCodCapitolo(),
					anagraficaUfficioCapitoloAccertamentoDto.getDeCapitolo(),
					anagraficaUfficioCapitoloAccertamentoDto.getDeAnnoEsercizio(),
					anagraficaUfficioCapitoloAccertamentoDto.getCodAccertamento(),
					anagraficaUfficioCapitoloAccertamentoDto.getDeAccertamento(),
					anagraficaUfficioCapitoloAccertamentoDto.getCodTipoDovuto(),
					anagraficaUfficioCapitoloAccertamentoDto.getFlgAttivo(), new Date());
			} else {
				return new AggiornaAnagraficaUfficioCapitoloAccertamentoResultDto(ESITO.EXIST, anagraficaUfficioCapitoloAccertamentoDto);
			}

//			int risUpdateAnag = uffCapAccDao.aggiornaAnagrafica(anagraficaUfficioCapitoloAccertamentoDto);
			int risUpdateFlg = 0;

			if (risUpdateAnag > 0 && anagraficaUfficioCapitoloAccertamentoDto.isFlgAttivoCambiato()) {
				/**
				 * La modifica dell'anagrafica è avvenuta correttamente
				 */
				risUpdateFlg = repositoryDao.updateFlgAttivo(anagraficaUfficioCapitoloAccertamentoDto.getCodUfficio(),
						anagraficaUfficioCapitoloAccertamentoDto.getFlgAttivo(), SecurityContext.getEnte().getId());
			}

			if (!anagraficaUfficioCapitoloAccertamentoDto.isFlgAttivoCambiato()) {
				risUpdateFlg = 1;
			}
			
			EnteTO enteTO = enteService.getByCodIpaEnte(codIpaEnte);
			
			int risUpdateDeAccertamento = 0;
			int risUpdateDeCapitolo = 0;
			int risUpdateDeUfficio = 0;
			if (anagraficaUfficioCapitoloAccertamentoDto.getEnte() != null
//					&& !SecurityContext.getEnte().getCodIpaEnte().equals("R_VENETO")
					){
				risUpdateDeCapitolo = repositoryDao.updateDeCapitolo(
						anagraficaUfficioCapitoloAccertamentoDto.getCodCapitolo(),
						anagraficaUfficioCapitoloAccertamentoDto.getDeCapitolo(), enteTO.getId());
				risUpdateDeUfficio = repositoryDao.updateDeUfficio(
						anagraficaUfficioCapitoloAccertamentoDto.getCodUfficio(),
						anagraficaUfficioCapitoloAccertamentoDto.getDeUfficio(), enteTO.getId());
				if (!anagraficaUfficioCapitoloAccertamentoDto.getCodAccertamento().equalsIgnoreCase("n/a")) {
					risUpdateDeAccertamento = repositoryDao.updateDeAccertamento(
							anagraficaUfficioCapitoloAccertamentoDto.getCodAccertamento(),
							anagraficaUfficioCapitoloAccertamentoDto.getDeAccertamento(),
							enteTO.getId());
				}
				if (anagraficaUfficioCapitoloAccertamentoDto.getCodAccertamento().equalsIgnoreCase("n/a")) {
					risUpdateDeAccertamento = 1;
				}
			} else {
				risUpdateDeAccertamento = 1;
				risUpdateDeCapitolo = 1;
				risUpdateDeUfficio = 1;
			}
			
			if (risUpdateAnag > 0 && risUpdateFlg > 0 && risUpdateDeCapitolo > 0
					&& risUpdateDeUfficio > 0 && risUpdateDeAccertamento > 0) {
				/**
				 * Le modifiche sono avvenute correttamente
				 */
				dtoAggiornato = uffCapAccDao.findById(anagraficaUfficioCapitoloAccertamentoDto.getId());

				result = new AggiornaAnagraficaUfficioCapitoloAccertamentoResultDto(ESITO.AGGIORNATA, dtoAggiornato);

			} else {

				String errMsg = "Non è stato possibile aggiornare l'anagrafica";
				result = new AggiornaAnagraficaUfficioCapitoloAccertamentoResultDto(ESITO.ERROR, errMsg);
			}

		} catch (Exception e) {
			throw new RollbackException(e);
		}

		return result;
	}

	/**
	 * 
	 */
	@Override
	@Transactional
	public Boolean deleteAnagrafica(Long id, Long idEnte) {
		try {
			getAnagraficaById(id,idEnte);
			repositoryDao.deleteAnagrafica(id);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			return false;
		}		
	}

	@Override
	public void cancellaAnagrafica(Long id) {
		try {
			repositoryDao.delete(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	
	/**
	 * 
	 */
	@Override
	public void salvaAnagrafiche(AnagraficaUfficioCapitoloAccertamentoDto nuovaAnagrafica, String[] listCodTipoDovuto,
			boolean flag) throws Exception {
		try {
			AnagraficaUfficioCapitoloAccertamento exist = null;
			List<AnagraficaUfficioCapitoloAccertamento> listAnagUffCapAcc = new ArrayList<AnagraficaUfficioCapitoloAccertamento>();

			// inizio dal secondo valore, in modo da effettuare prima gli inserimenti e poi
			// le modifiche
			for (int i = 1; i < listCodTipoDovuto.length; i++) {
				AnagraficaUfficioCapitoloAccertamento temp = convertAnagDTOtoEntity(nuovaAnagrafica);
				temp.setCodTipoDovuto(listCodTipoDovuto[i]);
				listAnagUffCapAcc.add(temp);
			}

			for (AnagraficaUfficioCapitoloAccertamento anagUffCapAcc : listAnagUffCapAcc) {
				exist = repositoryDao.exist(anagUffCapAcc.getCodUfficio(), anagUffCapAcc.getCodCapitolo(),
						anagUffCapAcc.getCodTipoDovuto(), anagUffCapAcc.getCodAccertamento(),
						anagUffCapAcc.getEnte().getId(), anagUffCapAcc.getDeAnnoEsercizio());

				if (exist != null) {
					throw new RollbackException();
				}
				// AnagraficaUfficioCapitoloAccertamento temp = convertAnagDTOtoEntity(tempDTO);
				// entityManager.persist(anagUffCapAcc);
				repositoryDao.save(anagUffCapAcc);
				// repositoryDao.updateFlgAttivo(anagUffCapAcc.getCodUfficio(),
				// anagUffCapAcc.isFlgAttivo(), SecurityContext.getEnte().getId());
			}

		} catch (Exception e) {
			throw new RollbackException(e);
		}
	}

	private AnagraficaUfficioCapitoloAccertamento convertAnagDTOtoEntity(AnagraficaUfficioCapitoloAccertamentoDto dto) {

		AnagraficaUfficioCapitoloAccertamento anag = new AnagraficaUfficioCapitoloAccertamento();
		anag.setEnte(enteDao.findByCodIpaEnte(dto.getEnte().getCodIpaEnte()));
		anag.setCodTipoDovuto(dto.getCodTipoDovuto());
		anag.setCodUfficio(dto.getCodUfficio());
		anag.setDeUfficio(dto.getDeUfficio());
		anag.setFlgAttivo(dto.getFlgAttivo());
		anag.setCodCapitolo(dto.getCodCapitolo());
		anag.setDeCapitolo(dto.getDeCapitolo());
		anag.setDeAnnoEsercizio(dto.getDeAnnoEsercizio());
		anag.setCodAccertamento(dto.getCodAccertamento());
		anag.setDeAccertamento(dto.getDeAccertamento());
		anag.setDtCreazione(dto.getDtCreazione());
		anag.setDtUltimaModifica(dto.getDtUltimaModifica());
		return anag;
	}

	/**
	 * 
	 */
	@Override
	public AggiornaAnagraficaUfficioCapitoloAccertamentoResultDto salvaAnagrafica(
			AnagraficaUfficioCapitoloAccertamento nuovaAnagrafica) throws Exception {
		AggiornaAnagraficaUfficioCapitoloAccertamentoResultDto result = null;
		try {
			AnagraficaUfficioCapitoloAccertamento exist = null, saved = null;
			AnagraficaUfficioCapitoloAccertamentoDto dtoNuovo = new AnagraficaUfficioCapitoloAccertamentoDto();

			exist = repositoryDao.exist(nuovaAnagrafica.getCodUfficio(), nuovaAnagrafica.getCodCapitolo(),
					nuovaAnagrafica.getCodTipoDovuto(), nuovaAnagrafica.getCodAccertamento(),
					nuovaAnagrafica.getEnte().getId(), nuovaAnagrafica.getDeAnnoEsercizio());

			if (exist == null) {
				/**
				 * Save entity
				 */
				saved = repositoryDao.save(nuovaAnagrafica);
				if (saved != null && saved.getId() != null) {
					int app = repositoryDao.updateFlgAttivo(saved.getCodUfficio(), saved.isFlgAttivo(),
							SecurityContext.getEnte().getId());

					if (app > 0) {
						dtoNuovo = uffCapAccDao.findById(saved.getId());
						result = new AggiornaAnagraficaUfficioCapitoloAccertamentoResultDto(ESITO.INSERITA, dtoNuovo);
					}
				}
			} else {
				result = new AggiornaAnagraficaUfficioCapitoloAccertamentoResultDto(ESITO.EXIST);
			}

		} catch (Exception e) {
			throw new RollbackException(e);
		}
		return result;
	}

	
	@Override
	public AggiornaAnagraficaUfficioCapitoloAccertamentoResultDto salvaAnagrafica(
			AnagraficaUfficioCapitoloAccertamento nuovaAnagrafica, String codIpaEnte) throws Exception {
		AggiornaAnagraficaUfficioCapitoloAccertamentoResultDto result = null;
		try {
			AnagraficaUfficioCapitoloAccertamento exist = null, saved = null;
			AnagraficaUfficioCapitoloAccertamentoDto dtoNuovo = new AnagraficaUfficioCapitoloAccertamentoDto();

			exist = repositoryDao.exist(nuovaAnagrafica.getCodUfficio(), nuovaAnagrafica.getCodCapitolo(),
					nuovaAnagrafica.getCodTipoDovuto(), nuovaAnagrafica.getCodAccertamento(),
					nuovaAnagrafica.getEnte().getId(), nuovaAnagrafica.getDeAnnoEsercizio());

			if (exist == null) {
				/**
				 * Save entity
				 */
				saved = repositoryDao.save(nuovaAnagrafica);
				if (saved != null && saved.getId() != null) {
					EnteTO enteTO = enteService.getByCodIpaEnte(codIpaEnte);
					int app = repositoryDao.updateFlgAttivo(saved.getCodUfficio(), saved.isFlgAttivo(), enteTO.getId() );

					if (app > 0) {
						dtoNuovo = uffCapAccDao.findById(saved.getId());
						result = new AggiornaAnagraficaUfficioCapitoloAccertamentoResultDto(ESITO.INSERITA, dtoNuovo);
					}
				}
			} else {
				result = new AggiornaAnagraficaUfficioCapitoloAccertamentoResultDto(ESITO.EXIST);
			}

		} catch (Exception e) {
			throw new RollbackException(e);
		}
		return result;
	}
	
	/**
	 * Legge il file csv ed effettua le operazioni di insert e delete nella tabella
	 * AnagraficaUfficioCapitoloAccertamento
	 * 
	 * @param {@link
	 * 			MultipartFile} fileCSV, Il file csv in input
	 * @param {@link
	 * 			String} codIpaEnte Codice ipa dell'ente
	 * 
	 * @throws Exception
	 * @author Alessandro Paolillo
	 * @author Gianluca De Felice
	 */
	@Override
	public void manageFileCSV(InputStream inputstreamFileCSV, String codIpaEnte) throws Exception {

		logger.debug("MANAGE FILE CSV :: ANAGRAFICA UFF CAP ACC :: START");
		BufferedReader fileBufferedReader = new BufferedReader(new InputStreamReader(inputstreamFileCSV, "ISO-8859-1"));

		String line = "";
		String[] split = {};
		int lineNumber = 0;
		List<EnteTipoDovutoTO> activeOperatoreEnteTdAsObj = operatoreEnteTipoDovutoService
				.getListaEnteTipoDovutoForOperatoreAndCodIpaEnte(SecurityContext.getUtente().getCodFedUserId(),
						SecurityContext.getEnte().getCodIpaEnte());

		while ((line = fileBufferedReader.readLine()) != null) {
			if (lineNumber == 0) {
				lineNumber++;
				continue;
			}

			// controllo che il csv abbia come separatori i punti e virgola,
			if (!line.contains("\";\"")) {
				fileBufferedReader.close();
				logger.debug("MANAGE FILE CSV :: ANAGRAFICA UFF CAP ACC :: THROW ROLLBACK :: END");
				throw new RollbackException(
						"Errore di conversione alla linea " + lineNumber + ": il file non è formattato correttamente");
			}

			// rimuovo " ad inizio e fine riga
			line = line.substring(1, line.length() - 1);

			split = line.split("\";\"");

			// controllo che ogni riga abbia esattamente 10 valori
			if (split.length != 10) {
				fileBufferedReader.close();
				logger.debug("MANAGE FILE CSV :: ANAGRAFICA UFF CAP ACC :: THROW ROLLBACK :: END");
				throw new RollbackException(
						"Errore di conversione alla linea " + lineNumber + ": il file non è formattato correttamente");
			}

			for (String s : split) {
				String temp = new String(s.getBytes("UTF-8"), "UTF-8");
				if (!temp.equals(s)) {
					// if (!s.matches("^[a-zA-Z0-9 _]*[^èéìòàù]$")){
					fileBufferedReader.close();
					logger.debug("MANAGE FILE CSV :: ANAGRAFICA UFF CAP ACC :: THROW ROLLBACK :: END");
					throw new RollbackException("Errore di conversione alla linea " + lineNumber
							+ ": non sono permessi caratteri non UTF-8");
				}
			}

			if (StringUtils.isBlank(split[0]) && StringUtils.isBlank(split[1]) && StringUtils.isBlank(split[2])
					&& StringUtils.isBlank(split[3]) && StringUtils.isBlank(split[4]) && StringUtils.isBlank(split[5])
					&& StringUtils.isBlank(split[6]) && StringUtils.isBlank(split[7]) && StringUtils.isBlank(split[8])
					&& StringUtils.isBlank(split[9])) {
				fileBufferedReader.close();
				logger.debug("MANAGE FILE CSV :: ANAGRAFICA UFF CAP ACC :: THROW ROLLBACK :: END");
				throw new RollbackException(
						"Errore di conversione alla linea " + lineNumber + ": non sono permesse righe senza valori");
			}

			if (StringUtils.isBlank(split[0])) {
				fileBufferedReader.close();
				logger.debug("MANAGE FILE CSV :: ANAGRAFICA UFF CAP ACC :: THROW ROLLBACK :: END");
				throw new RollbackException(
						"Errore di conversione alla linea " + lineNumber + ": il campo cod_ufficio non è valorizzato");
			} else if (split[0].length() > MAX_NUMBER_CHAR_COD) {
				fileBufferedReader.close();
				logger.debug("MANAGE FILE CSV :: ANAGRAFICA UFF CAP ACC :: THROW ROLLBACK :: END");
				throw new RollbackException("Errore di conversione alla linea " + lineNumber
						+ ": il campo cod_ufficio deve avere una lunghezza massima di " + MAX_NUMBER_CHAR_COD
						+ " caratteri");
			}
			if (StringUtils.isBlank(split[1])) {
				fileBufferedReader.close();
				logger.debug("MANAGE FILE CSV :: ANAGRAFICA UFF CAP ACC :: THROW ROLLBACK :: END");
				throw new RollbackException(
						"Errore di conversione alla linea " + lineNumber + ": il campo de_ufficio non è valorizzato");
			} else if (split[1].length() > MAX_NUMBER_CHAR_DE) {
				fileBufferedReader.close();
				logger.debug("MANAGE FILE CSV :: ANAGRAFICA UFF CAP ACC :: THROW ROLLBACK :: END");
				throw new RollbackException("Errore di conversione alla linea " + lineNumber
						+ ": il campo de_ufficio deve avere una lunghezza massima di " + MAX_NUMBER_CHAR_DE
						+ " caratteri");
			}
			if (StringUtils.isBlank(split[2])) {
				fileBufferedReader.close();
				logger.debug("MANAGE FILE CSV :: ANAGRAFICA UFF CAP ACC :: THROW ROLLBACK :: END");
				throw new RollbackException(
						"Errore di conversione alla linea " + lineNumber + ": il campo flg_attivo non è valorizzato");
			} else if (!split[2].equalsIgnoreCase("true") && !split[2].equalsIgnoreCase("false")) {
				fileBufferedReader.close();
				logger.debug("MANAGE FILE CSV :: ANAGRAFICA UFF CAP ACC :: THROW ROLLBACK :: END");
				throw new RollbackException("Errore di conversione alla linea " + lineNumber
						+ ": il campo flg_attivo non è valido (true/false valori accettati)");
			}
			if (StringUtils.isBlank(split[3])) {
				fileBufferedReader.close();
				logger.debug("MANAGE FILE CSV :: ANAGRAFICA UFF CAP ACC :: THROW ROLLBACK :: END");
				throw new RollbackException(
						"Errore di conversione alla linea " + lineNumber + ": il campo cod_capitolo non è valorizzato");
			} else if (split[3].length() > MAX_NUMBER_CHAR_COD) {
				fileBufferedReader.close();
				logger.debug("MANAGE FILE CSV :: ANAGRAFICA UFF CAP ACC :: THROW ROLLBACK :: END");
				throw new RollbackException("Errore di conversione alla linea " + lineNumber
						+ ": il campo cod_capitolo deve avere una lunghezza massima di " + MAX_NUMBER_CHAR_COD
						+ " caratteri");
			}
			if (StringUtils.isBlank(split[4])) {
				fileBufferedReader.close();
				logger.debug("MANAGE FILE CSV :: ANAGRAFICA UFF CAP ACC :: THROW ROLLBACK :: END");
				throw new RollbackException(
						"Errore di conversione alla linea " + lineNumber + ": il campo de_capitolo non è valorizzato");
			} else if (split[4].length() > MAX_NUMBER_CHAR_DE) {
				fileBufferedReader.close();
				logger.debug("MANAGE FILE CSV :: ANAGRAFICA UFF CAP ACC :: THROW ROLLBACK :: END");
				throw new RollbackException("Errore di conversione alla linea " + lineNumber
						+ ": il campo de_capitolo deve avere una lunghezza massima di " + MAX_NUMBER_CHAR_DE
						+ " caratteri");
			}
			if (StringUtils.isBlank(split[5])) {
				fileBufferedReader.close();
				logger.debug("MANAGE FILE CSV :: ANAGRAFICA UFF CAP ACC :: THROW ROLLBACK :: END");
				throw new RollbackException("Errore di conversione alla linea " + lineNumber
						+ ": il campo de_anno_esercizio non è valorizzato");
			} else if (!StringUtils.isNumeric(split[5]) || split[5].length() > 4) {
				fileBufferedReader.close();
				logger.debug("MANAGE FILE CSV :: ANAGRAFICA UFF CAP ACC :: THROW ROLLBACK :: END");
				throw new RollbackException("Errore di conversione alla linea " + lineNumber
						+ ": il campo de_anno_esercizio non è un numero valido");
			}
			if (StringUtils.isBlank(split[8])) {
				fileBufferedReader.close();
				logger.debug("MANAGE FILE CSV :: ANAGRAFICA UFF CAP ACC :: THROW ROLLBACK :: END");
				throw new RollbackException("Errore di conversione alla linea " + lineNumber
						+ ": il campo cod_tipo_dovuto non è valorizzato");
			} else if (split[8].length() > 64) {
				fileBufferedReader.close();
				logger.debug("MANAGE FILE CSV :: ANAGRAFICA UFF CAP ACC :: THROW ROLLBACK :: END");
				throw new RollbackException("Errore di conversione alla linea " + lineNumber
						+ ": il campo cod_tipo_dovuto deve avere una lunghezza massima di 64 caratteri");
			}
			if (StringUtils.isBlank(split[9])) {
				fileBufferedReader.close();
				logger.debug("MANAGE FILE CSV :: ANAGRAFICA UFF CAP ACC :: THROW ROLLBACK :: END");
				throw new RollbackException(
						"Errore di conversione alla linea " + lineNumber + ": il campo azione non è valorizzato");
			}

			AnagraficaUfficioCapitoloAccertamento anag = new AnagraficaUfficioCapitoloAccertamento();
			anag.setEnte(enteDao.findByCodIpaEnte(codIpaEnte));
			anag.setCodUfficio(split[0]); // chiave di ricerca
			anag.setDeUfficio(split[1]);
			anag.setFlgAttivo(Boolean.parseBoolean(split[2]));
			anag.setCodCapitolo(split[3]); // chiave di ricerca
			anag.setDeCapitolo(split[4]);
			anag.setDeAnnoEsercizio(split[5]);

			if (StringUtils.isBlank(split[6])) {
				anag.setCodAccertamento("n/a"); // chiave di ricerca//opzionale
				anag.setDeAccertamento("n/a");
			} else if (split[6].length() > MAX_NUMBER_CHAR_COD) {
				fileBufferedReader.close();
				logger.debug("MANAGE FILE CSV :: ANAGRAFICA UFF CAP ACC :: THROW ROLLBACK :: END");
				throw new RollbackException("Errore di conversione alla linea " + lineNumber
						+ ": il campo cod_accertamento deve avere una lunghezza massima di " + MAX_NUMBER_CHAR_COD
						+ " caratteri");
			} else {
				anag.setCodAccertamento(split[6]);

				// se il codice accertamento è settato, controllo la descrizione
				if (StringUtils.isBlank(split[7]))
					anag.setDeAccertamento("n/a");
				else if (split[7].length() > MAX_NUMBER_CHAR_DE)
					anag.setDeAccertamento(split[7].substring(0, MAX_NUMBER_CHAR_DE));
				else
					anag.setDeAccertamento(split[7]);
			}

			boolean contain = false;
			if (StringUtils.isNotBlank(split[8])) {
				/**
				 * Recupero la lista dei tipi dovuto dell'ente per cui l'utente è un operatore
				 * attivo. Se la lista è vuota, mostro un messaggio di errore perchè vuol dire
				 * che l'utente non può ne consultare ne creare accertamenti.
				 */
				for (EnteTipoDovutoTO enteTipoDovutoTO : activeOperatoreEnteTdAsObj) {
					if (enteTipoDovutoTO.getCodTipo().equals(split[8]))
						contain = true;
				}
			}
			if (!contain) {
				fileBufferedReader.close();
				logger.debug("MANAGE FILE CSV :: ANAGRAFICA UFF CAP ACC :: THROW ROLLBACK :: END");
				throw new RollbackException("Errore di conversione alla linea " + lineNumber
						+ ": il campo codice_tipo_dovuto non è legato all'ente");
			}
			anag.setCodTipoDovuto(split[8]); // chiave di ricerca

			anag.setDtCreazione(new Date());
			anag.setDtUltimaModifica(new Date());

			// se il valore è I devo eseguire una insert
			if (split[9].equals("I")) {
				AnagraficaUfficioCapitoloAccertamento temp = new AnagraficaUfficioCapitoloAccertamento();

				// check per controllare che il codice accertamento sia presente
				temp = anagraficaUfficioCapitoloAccertamentoRepositoryDao.exist(anag.getCodUfficio(),
						anag.getCodCapitolo(), anag.getCodTipoDovuto(), anag.getCodAccertamento(),
						anag.getEnte().getId(), anag.getDeAnnoEsercizio());

				// se non esiste, controllo che le descrizioni dei codici siano corrette
				if (temp == null) {

					String deUfficio = anagraficaUfficioCapitoloAccertamentoRepositoryDao
							.getDeUfficioByIdEnteAndCodUfficio(anag.getEnte().getId(), anag.getCodUfficio());

					if (deUfficio != null && !deUfficio.equals(anag.getDeUfficio())) {
						fileBufferedReader.close();
						logger.debug("MANAGE FILE CSV :: ANAGRAFICA UFF CAP ACC :: THROW ROLLBACK :: END");
						throw new RollbackException("Errore di conversione alla linea " + lineNumber
								+ ":  non puoi inserire un de_ufficio diverso da un de_ufficio già esistente");
					}

					String deCapitolo = anagraficaUfficioCapitoloAccertamentoRepositoryDao
							.getDeCapitoloByIdEnteAndCodUfficioAndCodCapitolo(anag.getEnte().getId(),
									anag.getCodUfficio(), anag.getCodCapitolo());

					if (deCapitolo != null && !deCapitolo.equals(anag.getDeCapitolo())) {
						fileBufferedReader.close();
						logger.debug("MANAGE FILE CSV :: ANAGRAFICA UFF CAP ACC :: THROW ROLLBACK :: END");
						throw new RollbackException("Errore di conversione alla linea " + lineNumber
								+ ": non puoi inserire un de_capitolo diverso da un de_capitolo già esistente");
					}

					String deAccertamento = anagraficaUfficioCapitoloAccertamentoRepositoryDao
							.getDeAccertamentoByIdEnteAndCodUfficioAndCodCapitoloAndCodAccertamento(
									anag.getEnte().getId(), anag.getCodUfficio(), anag.getCodCapitolo(),
									anag.getCodAccertamento());

					if (deAccertamento != null && !deAccertamento.equals(anag.getDeAccertamento())) {
						fileBufferedReader.close();
						logger.debug("MANAGE FILE CSV :: ANAGRAFICA UFF CAP ACC :: THROW ROLLBACK :: END");
						throw new RollbackException("Errore di conversione alla linea " + lineNumber
								+ ": non puoi inserire un de_accertamento diverso da un de_accertamento già esistente");
					}

					logger.debug("MANAGE FILE CSV :: ANAGRAFICA UFF CAP ACC :: PERSIST :: ENTRY CODUFF: "
							+ anag.getCodUfficio() + ", CODCAP: " + anag.getCodCapitolo() + ", CODTIPODOV: "
							+ anag.getCodTipoDovuto() + " e CODACC: " + anag.getCodAccertamento());
					// entityManager.persist(anag);
					repositoryDao.save(anag);
				} else {
					fileBufferedReader.close();
					logger.debug("MANAGE FILE CSV :: ANAGRAFICA UFF CAP ACC :: THROW ROLLBACK :: END");
					throw new RollbackException("Errore di conversione alla linea " + lineNumber
							+ ": l'entry con codice ufficio: " + anag.getCodUfficio() + ", codice capitolo: "
							+ anag.getCodCapitolo() + ", codice tipo dovuto: " + anag.getCodTipoDovuto()
							+ ",codice accertamento: " + anag.getCodAccertamento()
							+ " e anno esercizio: " + anag.getDeAnnoEsercizio() + " è già esistente");
				}
			}

			// se il valore è D devo eseguire una delete
			else if (split[9].equals("D")) {
				AnagraficaUfficioCapitoloAccertamento temp = new AnagraficaUfficioCapitoloAccertamento();

				// check per controllare che il codice accertamento sia presente
				temp = anagraficaUfficioCapitoloAccertamentoRepositoryDao.exist(anag.getCodUfficio(),
						anag.getCodCapitolo(), anag.getCodTipoDovuto(), anag.getCodAccertamento(),
						anag.getEnte().getId(), anag.getDeAnnoEsercizio());

				// se esiste posso eseguire la delete
				if (temp != null) {
					logger.debug("MANAGE FILE CSV :: ANAGRAFICA UFF CAP ACC :: REMOVE :: ENTRY CODUFF: "
							+ anag.getCodUfficio() + ", CODCAP: " + anag.getCodCapitolo() + ", CODTIPODOV: "
							+ anag.getCodTipoDovuto() + ", CODACC: " + anag.getCodAccertamento()
							+ " e ANNO_ESERCIZIO " + anag.getDeAnnoEsercizio());
					entityManager.remove(temp);
				} else {
					fileBufferedReader.close();
					logger.debug("MANAGE FILE CSV :: ANAGRAFICA UFF CAP ACC :: THROW ROLLBACK :: END");
					throw new RollbackException(
							"Errore di conversione alla linea " + lineNumber + ": l'entry con codice ufficio: "
									+ anag.getCodUfficio() + ", codice capitolo: " + anag.getCodCapitolo()
									+ ", codice tipo dovuto: " + anag.getCodTipoDovuto()
									+ ", codice accertamento: "	+ anag.getCodAccertamento()
									+ " e anno esercizio: "	+ anag.getDeAnnoEsercizio()
									+ " non esiste e quindi non può essere eliminata");
				}
			} else {
				fileBufferedReader.close();
				logger.debug("MANAGE FILE CSV :: ANAGRAFICA UFF CAP ACC :: THROW ROLLBACK :: END");
				throw new RollbackException("Errore di conversione alla linea " + lineNumber
						+ ": il campo azione può contenere solo i caratteri I (insert) e D (delete)");
			}
			lineNumber++;
		}

		// Chiudo il file
		fileBufferedReader.close();
	}

	
	
	@Override
	public void manageFileCSV(InputStream inputstreamFileCSV, String codIpaEnte, String codFedUserId) throws Exception {

		logger.debug("MANAGE FILE CSV :: ANAGRAFICA UFF CAP ACC :: START");
		BufferedReader fileBufferedReader = new BufferedReader(new InputStreamReader(inputstreamFileCSV, "ISO-8859-1"));

		String line = "";
		String[] split = {};
		int lineNumber = 0;
		List<EnteTipoDovutoTO> activeOperatoreEnteTdAsObj = operatoreEnteTipoDovutoService
				.getListaEnteTipoDovutoForOperatoreAndCodIpaEnte(codFedUserId,
						codIpaEnte);

		while ((line = fileBufferedReader.readLine()) != null) {
			if (lineNumber == 0) {
				lineNumber++;
				continue;
			}

			// controllo che il csv abbia come separatori i punti e virgola,
			if (!line.contains("\";\"")) {
				fileBufferedReader.close();
				logger.debug("MANAGE FILE CSV :: ANAGRAFICA UFF CAP ACC :: THROW ROLLBACK :: END");
				throw new RollbackException(
						"Errore di conversione alla linea " + lineNumber + ": il file non è formattato correttamente");
			}

			// rimuovo " ad inizio e fine riga
			line = line.substring(1, line.length() - 1);

			split = line.split("\";\"");

			// controllo che ogni riga abbia esattamente 10 valori
			if (split.length != 10) {
				fileBufferedReader.close();
				logger.debug("MANAGE FILE CSV :: ANAGRAFICA UFF CAP ACC :: THROW ROLLBACK :: END");
				throw new RollbackException(
						"Errore di conversione alla linea " + lineNumber + ": il file non è formattato correttamente");
			}

			for (String s : split) {
				String temp = new String(s.getBytes("UTF-8"), "UTF-8");
				if (!temp.equals(s)) {
					// if (!s.matches("^[a-zA-Z0-9 _]*[^èéìòàù]$")){
					fileBufferedReader.close();
					logger.debug("MANAGE FILE CSV :: ANAGRAFICA UFF CAP ACC :: THROW ROLLBACK :: END");
					throw new RollbackException("Errore di conversione alla linea " + lineNumber
							+ ": non sono permessi caratteri non UTF-8");
				}
			}

			if (StringUtils.isBlank(split[0]) && StringUtils.isBlank(split[1]) && StringUtils.isBlank(split[2])
					&& StringUtils.isBlank(split[3]) && StringUtils.isBlank(split[4]) && StringUtils.isBlank(split[5])
					&& StringUtils.isBlank(split[6]) && StringUtils.isBlank(split[7]) && StringUtils.isBlank(split[8])
					&& StringUtils.isBlank(split[9])) {
				fileBufferedReader.close();
				logger.debug("MANAGE FILE CSV :: ANAGRAFICA UFF CAP ACC :: THROW ROLLBACK :: END");
				throw new RollbackException(
						"Errore di conversione alla linea " + lineNumber + ": non sono permesse righe senza valori");
			}

			if (StringUtils.isBlank(split[0])) {
				fileBufferedReader.close();
				logger.debug("MANAGE FILE CSV :: ANAGRAFICA UFF CAP ACC :: THROW ROLLBACK :: END");
				throw new RollbackException(
						"Errore di conversione alla linea " + lineNumber + ": il campo cod_ufficio non è valorizzato");
			} else if (split[0].length() > MAX_NUMBER_CHAR_COD) {
				fileBufferedReader.close();
				logger.debug("MANAGE FILE CSV :: ANAGRAFICA UFF CAP ACC :: THROW ROLLBACK :: END");
				throw new RollbackException("Errore di conversione alla linea " + lineNumber
						+ ": il campo cod_ufficio deve avere una lunghezza massima di " + MAX_NUMBER_CHAR_COD
						+ " caratteri");
			}
			if (StringUtils.isBlank(split[1])) {
				fileBufferedReader.close();
				logger.debug("MANAGE FILE CSV :: ANAGRAFICA UFF CAP ACC :: THROW ROLLBACK :: END");
				throw new RollbackException(
						"Errore di conversione alla linea " + lineNumber + ": il campo de_ufficio non è valorizzato");
			} else if (split[1].length() > MAX_NUMBER_CHAR_DE) {
				fileBufferedReader.close();
				logger.debug("MANAGE FILE CSV :: ANAGRAFICA UFF CAP ACC :: THROW ROLLBACK :: END");
				throw new RollbackException("Errore di conversione alla linea " + lineNumber
						+ ": il campo de_ufficio deve avere una lunghezza massima di " + MAX_NUMBER_CHAR_DE
						+ " caratteri");
			}
			if (StringUtils.isBlank(split[2])) {
				fileBufferedReader.close();
				logger.debug("MANAGE FILE CSV :: ANAGRAFICA UFF CAP ACC :: THROW ROLLBACK :: END");
				throw new RollbackException(
						"Errore di conversione alla linea " + lineNumber + ": il campo flg_attivo non è valorizzato");
			} else if (!split[2].equalsIgnoreCase("true") && !split[2].equalsIgnoreCase("false")) {
				fileBufferedReader.close();
				logger.debug("MANAGE FILE CSV :: ANAGRAFICA UFF CAP ACC :: THROW ROLLBACK :: END");
				throw new RollbackException("Errore di conversione alla linea " + lineNumber
						+ ": il campo flg_attivo non è valido (true/false valori accettati)");
			}
			if (StringUtils.isBlank(split[3])) {
				fileBufferedReader.close();
				logger.debug("MANAGE FILE CSV :: ANAGRAFICA UFF CAP ACC :: THROW ROLLBACK :: END");
				throw new RollbackException(
						"Errore di conversione alla linea " + lineNumber + ": il campo cod_capitolo non è valorizzato");
			} else if (split[3].length() > MAX_NUMBER_CHAR_COD) {
				fileBufferedReader.close();
				logger.debug("MANAGE FILE CSV :: ANAGRAFICA UFF CAP ACC :: THROW ROLLBACK :: END");
				throw new RollbackException("Errore di conversione alla linea " + lineNumber
						+ ": il campo cod_capitolo deve avere una lunghezza massima di " + MAX_NUMBER_CHAR_COD
						+ " caratteri");
			}
			if (StringUtils.isBlank(split[4])) {
				fileBufferedReader.close();
				logger.debug("MANAGE FILE CSV :: ANAGRAFICA UFF CAP ACC :: THROW ROLLBACK :: END");
				throw new RollbackException(
						"Errore di conversione alla linea " + lineNumber + ": il campo de_capitolo non è valorizzato");
			} else if (split[4].length() > MAX_NUMBER_CHAR_DE) {
				fileBufferedReader.close();
				logger.debug("MANAGE FILE CSV :: ANAGRAFICA UFF CAP ACC :: THROW ROLLBACK :: END");
				throw new RollbackException("Errore di conversione alla linea " + lineNumber
						+ ": il campo de_capitolo deve avere una lunghezza massima di " + MAX_NUMBER_CHAR_DE
						+ " caratteri");
			}
			if (StringUtils.isBlank(split[5])) {
				fileBufferedReader.close();
				logger.debug("MANAGE FILE CSV :: ANAGRAFICA UFF CAP ACC :: THROW ROLLBACK :: END");
				throw new RollbackException("Errore di conversione alla linea " + lineNumber
						+ ": il campo de_anno_esercizio non è valorizzato");
			} else if (!StringUtils.isNumeric(split[5]) || split[5].length() > 4) {
				fileBufferedReader.close();
				logger.debug("MANAGE FILE CSV :: ANAGRAFICA UFF CAP ACC :: THROW ROLLBACK :: END");
				throw new RollbackException("Errore di conversione alla linea " + lineNumber
						+ ": il campo de_anno_esercizio non è un numero valido");
			}
			if (StringUtils.isBlank(split[8])) {
				fileBufferedReader.close();
				logger.debug("MANAGE FILE CSV :: ANAGRAFICA UFF CAP ACC :: THROW ROLLBACK :: END");
				throw new RollbackException("Errore di conversione alla linea " + lineNumber
						+ ": il campo cod_tipo_dovuto non è valorizzato");
			} else if (split[8].length() > 64) {
				fileBufferedReader.close();
				logger.debug("MANAGE FILE CSV :: ANAGRAFICA UFF CAP ACC :: THROW ROLLBACK :: END");
				throw new RollbackException("Errore di conversione alla linea " + lineNumber
						+ ": il campo cod_tipo_dovuto deve avere una lunghezza massima di 64 caratteri");
			}
			if (StringUtils.isBlank(split[9])) {
				fileBufferedReader.close();
				logger.debug("MANAGE FILE CSV :: ANAGRAFICA UFF CAP ACC :: THROW ROLLBACK :: END");
				throw new RollbackException(
						"Errore di conversione alla linea " + lineNumber + ": il campo azione non è valorizzato");
			}

			AnagraficaUfficioCapitoloAccertamento anag = new AnagraficaUfficioCapitoloAccertamento();
			anag.setEnte(enteDao.findByCodIpaEnte(codIpaEnte));
			anag.setCodUfficio(split[0]); // chiave di ricerca
			anag.setDeUfficio(split[1]);
			anag.setFlgAttivo(Boolean.parseBoolean(split[2]));
			anag.setCodCapitolo(split[3]); // chiave di ricerca
			anag.setDeCapitolo(split[4]);
			anag.setDeAnnoEsercizio(split[5]);

			if (StringUtils.isBlank(split[6])) {
				anag.setCodAccertamento("n/a"); // chiave di ricerca//opzionale
				anag.setDeAccertamento("n/a");
			} else if (split[6].length() > MAX_NUMBER_CHAR_COD) {
				fileBufferedReader.close();
				logger.debug("MANAGE FILE CSV :: ANAGRAFICA UFF CAP ACC :: THROW ROLLBACK :: END");
				throw new RollbackException("Errore di conversione alla linea " + lineNumber
						+ ": il campo cod_accertamento deve avere una lunghezza massima di " + MAX_NUMBER_CHAR_COD
						+ " caratteri");
			} else {
				anag.setCodAccertamento(split[6]);

				// se il codice accertamento è settato, controllo la descrizione
				if (StringUtils.isBlank(split[7]))
					anag.setDeAccertamento("n/a");
				else if (split[7].length() > MAX_NUMBER_CHAR_DE)
					anag.setDeAccertamento(split[7].substring(0, MAX_NUMBER_CHAR_DE));
				else
					anag.setDeAccertamento(split[7]);
			}

			boolean contain = false;
			if (StringUtils.isNotBlank(split[8])) {
				/**
				 * Recupero la lista dei tipi dovuto dell'ente per cui l'utente è un operatore
				 * attivo. Se la lista è vuota, mostro un messaggio di errore perchè vuol dire
				 * che l'utente non può ne consultare ne creare accertamenti.
				 */
				for (EnteTipoDovutoTO enteTipoDovutoTO : activeOperatoreEnteTdAsObj) {
					if (enteTipoDovutoTO.getCodTipo().equals(split[8]))
						contain = true;
				}
			}
			if (!contain) {
				fileBufferedReader.close();
				logger.debug("MANAGE FILE CSV :: ANAGRAFICA UFF CAP ACC :: THROW ROLLBACK :: END");
				throw new RollbackException("Errore di conversione alla linea " + lineNumber
						+ ": il campo codice_tipo_dovuto non è legato all'ente");
			}
			anag.setCodTipoDovuto(split[8]); // chiave di ricerca

			anag.setDtCreazione(new Date());
			anag.setDtUltimaModifica(new Date());

			// se il valore è I devo eseguire una insert
			if (split[9].equals("I")) {
				AnagraficaUfficioCapitoloAccertamento temp = new AnagraficaUfficioCapitoloAccertamento();

				// check per controllare che il codice accertamento sia presente
				temp = anagraficaUfficioCapitoloAccertamentoRepositoryDao.exist(anag.getCodUfficio(),
						anag.getCodCapitolo(), anag.getCodTipoDovuto(), anag.getCodAccertamento(),
						anag.getEnte().getId(), anag.getDeAnnoEsercizio());

				// se non esiste, controllo che le descrizioni dei codici siano corrette
				if (temp == null) {

					String deUfficio = anagraficaUfficioCapitoloAccertamentoRepositoryDao
							.getDeUfficioByIdEnteAndCodUfficio(anag.getEnte().getId(), anag.getCodUfficio());

					if (deUfficio != null && !deUfficio.equals(anag.getDeUfficio())) {
						fileBufferedReader.close();
						logger.debug("MANAGE FILE CSV :: ANAGRAFICA UFF CAP ACC :: THROW ROLLBACK :: END");
						throw new RollbackException("Errore di conversione alla linea " + lineNumber
								+ ":  non puoi inserire un de_ufficio diverso da un de_ufficio già esistente");
					}

					String deCapitolo = anagraficaUfficioCapitoloAccertamentoRepositoryDao
							.getDeCapitoloByIdEnteAndCodUfficioAndCodCapitolo(anag.getEnte().getId(),
									anag.getCodUfficio(), anag.getCodCapitolo());

					if (deCapitolo != null && !deCapitolo.equals(anag.getDeCapitolo())) {
						fileBufferedReader.close();
						logger.debug("MANAGE FILE CSV :: ANAGRAFICA UFF CAP ACC :: THROW ROLLBACK :: END");
						throw new RollbackException("Errore di conversione alla linea " + lineNumber
								+ ": non puoi inserire un de_capitolo diverso da un de_capitolo già esistente");
					}

					String deAccertamento = anagraficaUfficioCapitoloAccertamentoRepositoryDao
							.getDeAccertamentoByIdEnteAndCodUfficioAndCodCapitoloAndCodAccertamento(
									anag.getEnte().getId(), anag.getCodUfficio(), anag.getCodCapitolo(),
									anag.getCodAccertamento());

					if (deAccertamento != null && !deAccertamento.equals(anag.getDeAccertamento())) {
						fileBufferedReader.close();
						logger.debug("MANAGE FILE CSV :: ANAGRAFICA UFF CAP ACC :: THROW ROLLBACK :: END");
						throw new RollbackException("Errore di conversione alla linea " + lineNumber
								+ ": non puoi inserire un de_accertamento diverso da un de_accertamento già esistente");
					}

					logger.debug("MANAGE FILE CSV :: ANAGRAFICA UFF CAP ACC :: PERSIST :: ENTRY CODUFF: "
							+ anag.getCodUfficio() + ", CODCAP: " + anag.getCodCapitolo() + ", CODTIPODOV: "
							+ anag.getCodTipoDovuto() + " e CODACC: " + anag.getCodAccertamento());
					// entityManager.persist(anag);
					repositoryDao.save(anag);
				} else {
					fileBufferedReader.close();
					logger.debug("MANAGE FILE CSV :: ANAGRAFICA UFF CAP ACC :: THROW ROLLBACK :: END");
					throw new RollbackException("Errore di conversione alla linea " + lineNumber
							+ ": l'entry con codice ufficio: " + anag.getCodUfficio() + ", codice capitolo: "
							+ anag.getCodCapitolo() + ", codice tipo dovuto: " + anag.getCodTipoDovuto()
							+ ",codice accertamento: " + anag.getCodAccertamento()
							+ " e anno esercizio: " + anag.getDeAnnoEsercizio() + " è già esistente");
				}
			}

			// se il valore è D devo eseguire una delete
			else if (split[9].equals("D")) {
				AnagraficaUfficioCapitoloAccertamento temp = new AnagraficaUfficioCapitoloAccertamento();

				// check per controllare che il codice accertamento sia presente
				temp = anagraficaUfficioCapitoloAccertamentoRepositoryDao.exist(anag.getCodUfficio(),
						anag.getCodCapitolo(), anag.getCodTipoDovuto(), anag.getCodAccertamento(),
						anag.getEnte().getId(), anag.getDeAnnoEsercizio());

				// se esiste posso eseguire la delete
				if (temp != null) {
					logger.debug("MANAGE FILE CSV :: ANAGRAFICA UFF CAP ACC :: REMOVE :: ENTRY CODUFF: "
							+ anag.getCodUfficio() + ", CODCAP: " + anag.getCodCapitolo() + ", CODTIPODOV: "
							+ anag.getCodTipoDovuto() + ", CODACC: " + anag.getCodAccertamento()
							+ " e ANNO_ESERCIZIO " + anag.getDeAnnoEsercizio());
					entityManager.remove(temp);
				} else {
					fileBufferedReader.close();
					logger.debug("MANAGE FILE CSV :: ANAGRAFICA UFF CAP ACC :: THROW ROLLBACK :: END");
					throw new RollbackException(
							"Errore di conversione alla linea " + lineNumber + ": l'entry con codice ufficio: "
									+ anag.getCodUfficio() + ", codice capitolo: " + anag.getCodCapitolo()
									+ ", codice tipo dovuto: " + anag.getCodTipoDovuto()
									+ ", codice accertamento: "	+ anag.getCodAccertamento()
									+ " e anno esercizio: "	+ anag.getDeAnnoEsercizio()
									+ " non esiste e quindi non può essere eliminata");
				}
			} else {
				fileBufferedReader.close();
				logger.debug("MANAGE FILE CSV :: ANAGRAFICA UFF CAP ACC :: THROW ROLLBACK :: END");
				throw new RollbackException("Errore di conversione alla linea " + lineNumber
						+ ": il campo azione può contenere solo i caratteri I (insert) e D (delete)");
			}
			lineNumber++;
		}

		// Chiudo il file
		fileBufferedReader.close();
	}
	
	/**
	 * Recupero tutti gli uffici appartenenti all'ente selezionato
	 * 
	 * @param enteId
	 * @return
	 * @throws Exception
	 * @author Alessandro Paolillo
	 */
	@Override
	public List<AnagraficaUfficioCapitoloAccertamentoTO> findDistinctUfficiByEnteId(Long enteId) throws Exception {
		try {
			logger.debug("SERVICE :: RICERCA :: UFFICI :: PARAMETRI DI RICERCA[ enteId:" + enteId + " ] :: START");

			// execute query
			List<AnagraficaUfficioCapitoloAccertamento> entities = uffCapAccDao.findDistinctUfficiByEnteId(enteId);
			//
			List<AnagraficaUfficioCapitoloAccertamentoTO> beans = new ArrayList<AnagraficaUfficioCapitoloAccertamentoTO>();

			// reverse entity to TO
			for (AnagraficaUfficioCapitoloAccertamento src : entities) {
				/* */
				AnagraficaUfficioCapitoloAccertamentoTO item = new AnagraficaUfficioCapitoloAccertamentoTO();
				/* */
				item.setId(src.getId());
				/* */
				item.setCodUfficio(src.getCodUfficio().replace('"', '\"'));
				/* */
				item.setDeUfficio(src.getDeUfficio().replace('"', '\"'));
				/* */
				item.setFlgAttivo(src.isFlgAttivo());
				/* */
				beans.add(item);
			}

			logger.debug("SERVICE :: RICERCA :: UFFICI :: END");

			return beans;
		} catch (Exception e) {
			logger.error("SERVICE :: RICERCA :: UFFICI :: ERROR ", e);
			throw new Exception(e);
		}
	}

	/**
	 * Recupero tutti i capitoli dato l'id ente
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 * @author Alessandro Paolillo
	 */
	@Override
	public List<AnagraficaUfficioCapitoloAccertamentoTO> findDistinctCapitoliByEnteId(Long idEnte) throws Exception {
		try {
			logger.debug("SERVICE :: RICERCA :: CAPITOLI :: PARAMETRI DI RICERCA[ enteId:" + idEnte + " ] :: START");

			// execute query
			List<AnagraficaUfficioCapitoloAccertamento> entities = uffCapAccDao.findDistinctCapitoliByEnteId(idEnte);
			//
			List<AnagraficaUfficioCapitoloAccertamentoTO> beans = new ArrayList<AnagraficaUfficioCapitoloAccertamentoTO>();

			// reverse entity to TO
			for (AnagraficaUfficioCapitoloAccertamento src : entities) {
				/* */
				AnagraficaUfficioCapitoloAccertamentoTO item = new AnagraficaUfficioCapitoloAccertamentoTO();
				/* */
				item.setId(src.getId());
				/* */
				item.setCodCapitolo(src.getCodCapitolo().replace('"', '\"'));
				/* */
				item.setDeCapitolo(src.getDeCapitolo().replace('"', '\"'));
				/* */
				item.setDeAnnoEsercizio(src.getDeAnnoEsercizio().replace('"', '\"'));
				/* */
				beans.add(item);
			}

			logger.debug("SERVICE :: RICERCA :: CAPITOLI :: END");

			return beans;
		} catch (Exception e) {
			logger.error("SERVICE :: RICERCA :: CAPITOLI :: ERROR ", e);
			throw new Exception(e);
		}
	}

	/**
	 * Recupero tutti gli accertamenti dato id ente, codice ufficio e codice
	 * capitolo
	 * 
	 * @param idEnte
	 * @param codUff
	 * @param codCap
	 * @return
	 * @author Alessandro Paolillo
	 */
	@Override
	public List<AnagraficaUfficioCapitoloAccertamentoTO> findDistinctAccertamentiByCodUffcodCap(Long idEnte,
			String codUff, String codCap) throws Exception {
		try {
			logger.debug("SERVICE :: RICERCA :: ACCERTAMENTI :: PARAMETRI DI RICERCA[ enteId:" + idEnte
					+ " codiceUfficio: " + codUff + " codiceCapitolo: " + codCap + "] :: START");

			// execute query
			List<AnagraficaUfficioCapitoloAccertamento> entities = uffCapAccDao
					.findDistinctAccertamentiByCodUffcodCap(idEnte, codUff, codCap);
			//
			List<AnagraficaUfficioCapitoloAccertamentoTO> beans = new ArrayList<AnagraficaUfficioCapitoloAccertamentoTO>();

			// reverse entity to TO
			for (AnagraficaUfficioCapitoloAccertamento src : entities) {
				/* */
				AnagraficaUfficioCapitoloAccertamentoTO item = new AnagraficaUfficioCapitoloAccertamentoTO();
				/* */
				item.setId(src.getId());
				/* */
				item.setCodAccertamento(src.getCodAccertamento().replace('"', '\"'));
				/* */
				item.setDeAccertamento(src.getDeAccertamento().replace('"', '\"'));
				/* */
				beans.add(item);
			}

			logger.debug("SERVICE :: RICERCA :: ACCERTAMENTI :: END");

			return beans;
		} catch (Exception e) {
			logger.error("SERVICE :: RICERCA :: ACCERTAMENTI :: ERROR ", e);
			throw new Exception(e);
		}
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 * @author Alessandro Paolillo
	 */
	@Override
	public AnagraficaUfficioCapitoloAccertamentoDto getAnagraficaById(Long id) throws Exception {

		AnagraficaUfficioCapitoloAccertamento anag = new AnagraficaUfficioCapitoloAccertamento();
		AnagraficaUfficioCapitoloAccertamentoDto anagDto = new AnagraficaUfficioCapitoloAccertamentoDto();
		try {

//			anag = repositoryDao.findOne(id);
			anag = repositoryDao.findById(id);
			
			anagDto.setId(anag.getId());
			anagDto.setCodAccertamento(anag.getCodAccertamento());
			anagDto.setCodCapitolo(anag.getCodCapitolo());
			anagDto.setCodTipoDovuto(anag.getCodTipoDovuto());

			if (anag.getCodTipoDovuto() != null && !anag.getCodTipoDovuto().isEmpty())
				anagDto.setDeTipoDovuto(getDeTipoDovutoByCod(anag.getCodTipoDovuto()));

			anagDto.setCodUfficio(anag.getCodUfficio());
			anagDto.setDeAccertamento(anag.getDeAccertamento());
			anagDto.setDeAnnoEsercizio(anag.getDeAnnoEsercizio());
			anagDto.setDeCapitolo(anag.getDeCapitolo());
			anagDto.setDeUfficio(anag.getDeUfficio());
			anagDto.setFlgAttivo(anag.isFlgAttivo());

			return anagDto;

		} catch (Exception e) {
			throw new Exception(e);
		}

	}

	@Override
	public AnagraficaUfficioCapitoloAccertamentoDto getAnagraficaById(Long id, Long idEnte) throws Exception {

		AnagraficaUfficioCapitoloAccertamento anag = new AnagraficaUfficioCapitoloAccertamento();
		AnagraficaUfficioCapitoloAccertamentoDto anagDto = new AnagraficaUfficioCapitoloAccertamentoDto();
		try {

//			anag = repositoryDao.findOne(id);
			anag = repositoryDao.findById(id);
			
			anagDto.setId(anag.getId());
			anagDto.setCodAccertamento(anag.getCodAccertamento());
			anagDto.setCodCapitolo(anag.getCodCapitolo());
			anagDto.setCodTipoDovuto(anag.getCodTipoDovuto());			
			
			anagDto.setEnte(enteService.getByCodIpaEnte(anag.getEnte().getCodIpaEnte()));
			
			if (anag.getCodTipoDovuto() != null && !anag.getCodTipoDovuto().isEmpty())
				anagDto.setDeTipoDovuto(getDeTipoDovutoByCod(anag.getCodTipoDovuto(), idEnte));

			anagDto.setCodUfficio(anag.getCodUfficio());
			anagDto.setDeAccertamento(anag.getDeAccertamento());
			anagDto.setDeAnnoEsercizio(anag.getDeAnnoEsercizio());
			anagDto.setDeCapitolo(anag.getDeCapitolo());
			anagDto.setDeUfficio(anag.getDeUfficio());
			anagDto.setFlgAttivo(anag.isFlgAttivo());

			return anagDto;

		} catch (Exception e) {
			throw new Exception(e);
		}

	}

	
	/**
	 * 
	 * @param cod
	 * @return
	 * @throws Exception
	 * @author Alessandro Paolillo
	 */
	@Override
	public String getDeTipoDovutoByCod(String cod) throws Exception {
		String deTipoDovuto = "";
		Long idEnte = SecurityContext.getEnte().getId();

		try {

			deTipoDovuto = uffCapAccDao.getDeTipoDovutoByIdEnteAndCodTipoDovuto(idEnte, cod);

			return deTipoDovuto;

		} catch (Exception e) {
			throw new Exception(e);
		}
	}


	@Override
	public String getDeTipoDovutoByCod(String cod, Long idEnte) throws Exception {
		String deTipoDovuto = "";
		try {
			deTipoDovuto = uffCapAccDao.getDeTipoDovutoByIdEnteAndCodTipoDovuto(idEnte, cod);

			return deTipoDovuto;

		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
}
