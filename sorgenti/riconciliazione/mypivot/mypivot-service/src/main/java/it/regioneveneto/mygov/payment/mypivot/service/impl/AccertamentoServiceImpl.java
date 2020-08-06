package it.regioneveneto.mygov.payment.mypivot.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.persistence.RollbackException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import it.regioneveneto.mygov.payment.mypivot.dao.AccertamentoDao;
import it.regioneveneto.mygov.payment.mypivot.dao.AccertamentoDettaglioRepositoryDao;
import it.regioneveneto.mygov.payment.mypivot.dao.AccertamentoRepositoryDao;
import it.regioneveneto.mygov.payment.mypivot.dao.AnagraficaStatoDao;
import it.regioneveneto.mygov.payment.mypivot.dao.EnteTipoDovutoDao;
import it.regioneveneto.mygov.payment.mypivot.dao.FlussoExportDao;
import it.regioneveneto.mygov.payment.mypivot.dao.UtenteDao;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.AccertamentoDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.PageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.bilancio.BilancioFlussoExportDto;
import it.regioneveneto.mygov.payment.mypivot.domain.po.Accertamento;
import it.regioneveneto.mygov.payment.mypivot.domain.po.AnagraficaStato;
import it.regioneveneto.mygov.payment.mypivot.domain.po.EnteTipoDovuto;
import it.regioneveneto.mygov.payment.mypivot.domain.po.FlussoExport;
import it.regioneveneto.mygov.payment.mypivot.domain.po.Utente;
import it.regioneveneto.mygov.payment.mypivot.domain.to.AnagraficaStatoTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTipoDovutoTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.UtenteTO;
import it.regioneveneto.mygov.payment.mypivot.service.AccertamentoService;
import it.regioneveneto.mygov.payment.mypivot.service.utils.ModelMapperUtil;
import it.regioneveneto.mygov.payment.mypivot.utils.Constants;
import it.regioneveneto.mygov.payment.mypivot.utils.SecurityContext;
import it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.BilancioDocument;
import it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtAccertamento;
import it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtBilancio;
import it.veneto.regione.schemas.x2012.pagamenti.ente.bilancio.CtCapitolo;

/**
 * 
 * @author Marianna Memoli
 *
 */
@Service
public class AccertamentoServiceImpl implements AccertamentoService {

	private static final Logger logger = Logger.getLogger(AccertamentoServiceImpl.class);

	@Autowired
	private ModelMapperUtil modelMapperUtil;

	@Autowired
	private AccertamentoDao accertamentoDao;

	@Autowired
	private AccertamentoRepositoryDao repositoryDao;

	@Autowired
	private UtenteDao utenteDao;

	@Autowired
	private AnagraficaStatoDao anagraficaStatoDao;

	@Autowired
	private EnteTipoDovutoDao enteTipoDovutoDao;

	@Autowired
	private AccertamentoDettaglioRepositoryDao repositoryDettaglioDao;

	@Autowired
	private FlussoExportDao flussoExportDao;

	/**
	 * Recupera l'elenco di accertamenti, eventualmente filtrati. Il servizio
	 * restituisce un'elenco filtrato dove il bean descrittivo dell'accertamento
	 * è in versione ridotta. In versione ridotta perchè, di tutte informazioni
	 * che descrivono l'accertamento vengono considerate quelle principali
	 * cossicchè l'accertamento sia correttamente descritto ad un'utente che ne
	 * prende visione.
	 * 
	 * Filtri di ricerca OBBLIGATORI:
	 * ------------------------------------------------------------------------------------------------------------------------------------------
	 * 
	 * @param {@link
	 * 			String} codIpaEnte, Ente selezionato come beaneficiario
	 * @param {@link
	 * 			List<Long>} utenteIDs, Accertamenti creati dagli utenti
	 *            presenti nella lista. Se l'utente autenticato ha ruolo: -
	 *            ROLE_ACC: visualizza solamente i propri accertamenti -
	 *            ROLE_ADMIN: visualizza accertamenti effetuati da utenti
	 *            operatori dell'ente.
	 * @param {@link
	 * 			List<String>} tipiDovutoCODs, Elenco dei tipi dovuto sui quali
	 *            l'utente autenticato ha i permessi di operatore
	 * 
	 *            Filtri di ricerca:
	 *            ------------------------------------------------------------------------------------------------------------------------------------------
	 * @param {@link
	 * 			String} codTipoDovuto, Codice tipo dovuto
	 * @param {@link
	 * 			String} codStato, Codice dello stato
	 * @param {@link
	 * 			String} nomeAccertamento, Testo digitato contenuto nella
	 *            descrizione dell'accertamento
	 * @param {@link
	 * 			String} dataUltimoAggDal, Data ultimo aggiornamento dal
	 *            (formato dd/MM/yyyy)
	 * @param {@link
	 * 			String} dataUltimoAggAl, Data ultimo aggiornamento al (formato
	 *            dd/MM/yyyy)
	 * @param {@link
	 * 			String} codiceIuv, Identificativo univoco versamento
	 * 
	 *            Paginazione:
	 *            -----------------------------------------------------------------------------------------------------------------------------------------
	 * @param {@link
	 * 			Boolean} hasPagination, Determina se la query deve essere
	 *            paginata, qualora la fosse diventa obbligatorio la
	 *            valorizzazione dei successivi due parametri.
	 * @param {@link
	 * 			Integer} page, Visualizza il punto di partenza nel set di dati
	 *            corrente.
	 * @param {@link
	 * 			Integer} pageSize, Numero di record che la query deve
	 *            selezione nella pagina corrente
	 * 
	 * @return {@link PageDto<AccertamentoDto>}
	 * @throws Exception
	 * 
	 * @author Marianna Memoli
	 */
	@Override
	public PageDto<AccertamentoDto> findByFilter(String codIpaEnte, List<Long> utenteIDs, List<String> tipiDovutoCODs,
			String codTipoDovuto, String codStato, String nomeAccertamento, String dataUltimoAggDal,
			String dataUltimoAggAl, String codiceIuv, Boolean hasPagination, int page, int pageSize) throws Exception {
		try {
			logger.debug("SERVICE :: RICERCA :: ACCERTAMENTI :: PARAMETRI DI RICERCA[" + "codIpaEnte:" + codIpaEnte
					+ ", utenteIDs:" + utenteIDs + ", tipiDovutoCODs:" + tipiDovutoCODs + ", " + "codTipoDovuto:"
					+ codTipoDovuto + ", codStato:" + codStato + ", nomeAccertamento:" + nomeAccertamento + ", "
					+ "dataUltimoAggDal:" + dataUltimoAggDal + ", dataUltimoAggAl:" + dataUltimoAggAl + ", codiceIuv:"
					+ codiceIuv + ", " + "hasPagination:" + hasPagination + ", page:" + page + ", pageSize:" + pageSize
					+ "] :: START");

			/* Eseguo query */
			PageDto<Accertamento> entities = accertamentoDao.findByFilter(codIpaEnte, utenteIDs, tipiDovutoCODs,
					codTipoDovuto, codStato, nomeAccertamento, dataUltimoAggDal, dataUltimoAggAl, codiceIuv,
					hasPagination, page, pageSize);

			/* Instance return DTO */
			PageDto<AccertamentoDto> beans = new PageDto<AccertamentoDto>(entities.getPage(), entities.getPageSize(),
					entities.isPreviousPage(), entities.isNextPage(), new ArrayList<AccertamentoDto>(),
					entities.getTotalPages(), entities.getTotalRecords());

			/* Converto l'entity bean in DTO */
			for (Accertamento src : entities.getList()) {
				/* */
				beans.getList().add(convertDbToPage(src));
			}

			logger.debug("SERVICE :: RICERCA :: ACCERTAMENTI :: END");

			return beans;
		} catch (Exception e) {
			logger.error("SERVICE :: RICERCA :: ACCERTAMENTI :: ERROR ", e);
			throw (e);
		}
	}

	/**
	 * Inserisce in banca dati l'anagrafica di un nuovo accertamento.
	 *
	 * @param {@link
	 * 			String} nomeAccertamento, Nome/descrizione dell'accertamento
	 * @param {@link
	 * 			String} codTipoDovuto, Codice del tipo di dovuto
	 * @param {@link
	 * 			String} codIpaEnte, Codice Ipa dell'ente
	 * @param {@link
	 * 			Long} utenteId, Identificativo dell'utente che ha creato
	 *            l'accertamento
	 * 
	 * @return {@link Long} - Identificativo dell'accertamento creato
	 * @throws Exception
	 * @author Marianna Memoli
	 */
	@Override
	public Long saveAccertamento(String nomeAccertamento, String codTipoDovuto, String codIpaEnte, Long utenteId)
			throws Exception {
		try {
			logger.debug("INSERT :: Accertamento :: Fields[nomeAccertamento:" + nomeAccertamento + ", codTipoDovuto:"
					+ codTipoDovuto + ", " + "codIpaEnte:" + codIpaEnte + "] :: START");

			/**
			 * Get references Entity
			 */
			EnteTipoDovuto enteTipoDovuto = enteTipoDovutoDao.findByCodIpaEnteAndCodTipo(codIpaEnte, codTipoDovuto);
			Utente utente = utenteDao.findById(utenteId); // findOne(utenteId);
			AnagraficaStato stato = anagraficaStatoDao.findByCodStatoAndDeTipoStato(
					Constants.COD_TIPO_STATO_ACCERTAMENTO_INSERITO, Constants.DE_TIPO_STATO_ACCERTAMENTO);

			/**
			 * Instance Entity, set value
			 */
			Accertamento entity = new Accertamento();
			entity.setEnteTipoDovuto(enteTipoDovuto); /* Set Tipo del dovuto */
			entity.setUtente(
					utente); /* Set utente che sta creando l'accertamento */
			entity.setAnagraficaStato(
					stato); /*
							 * Set Stato dell'accertamento in creazione è sempre
							 * "INSERITO"
							 */
			entity.setDeNomeAccertamento(
					nomeAccertamento); /* Set nome/descrizione accertamento */
			entity.setDtCreazione(new Date()); /* Set data creazione */
			entity.setDtUltimaModifica(
					new Date()); /* Set data ultima modifica */

			/**
			 * Save entity
			 */
			Accertamento saved = repositoryDao.save(entity);

			logger.debug("INSERT :: Accertamento :: Id anagrafica inserita: " + saved.getId());

			logger.debug("INSERT :: Accertamento :: Fields[nomeAccertamento:" + nomeAccertamento + ", codTipoDovuto:"
					+ codTipoDovuto + ", " + "codIpaEnte:" + codIpaEnte + "] :: END");

			return saved.getId();
		} catch (Exception e) {
			/* */
			logger.debug("INSERT :: Accertamento :: Fields[nomeAccertamento:" + nomeAccertamento + ", codTipoDovuto:"
					+ codTipoDovuto + ", " + "codIpaEnte:" + codIpaEnte + "] :: ERROR", e);
			/* */
			throw new RollbackException(e);
		}
	}

	/**
	 * Recupera l'anagrafica dell'accertamento dato l'identificativo.
	 * 
	 * @param {@link
	 * 			Long} accertamentoID, Identificativo dell'anagrafica di cui
	 *            recuperare il dettaglio
	 * 
	 * @return {@link AccertamentoDto} - oggetto contenente il dettaglio
	 * 
	 * @throws Exception
	 * @author Marianna Memoli
	 */
	@Override
	public AccertamentoDto findById(Long accertamentoId) throws Exception {
		try {
			logger.debug("FIND :: Accertamento :: Fields[accertamentoID:" + accertamentoId + "] :: START ");

			/*
			 * Get entity
			 */
//			Accertamento entity = repositoryDao.findOne(accertamentoId);
			Accertamento entity = repositoryDao.findById(accertamentoId);

			/* Convert to bean */
			AccertamentoDto bean = convertDbToPage(entity);

			logger.debug("FIND :: Accertamento :: Fields[accertamentoID:" + accertamentoId + "] :: END ");

			return bean;
		} catch (Exception e) {
			/* */
			logger.debug("FIND :: Accertamento :: Fields[accertamentoID:" + accertamentoId + "] :: ERROR ", e);
			/* */
			throw (e);
		}
	}

	/**
	 * Aggiorna l'anagrafica dell'accertamento reimpostando l'anagrafica stato
	 * con quella fornita in ingresso.
	 * 
	 * @param {@link
	 * 			Long} accertamentoId, Identificativo dell'anagrafica da
	 *            aggiornare
	 * @param {@link
	 * 			String} codStato, Codice dello stato
	 * @param {@link
	 * 			String} codFedUserId, Codice utente
	 * 
	 * @throws Exception
	 * 
	 * @author Marianna Memoli
	 */
	@Override
	public void setStatoAndSave(Long accertamentoId, String codStato, String codFedUserId) throws Exception {
		try {
			logger.debug("UPDATE STATO :: Accertamento :: Fields[accertamentoID:" + accertamentoId + ", codStato:"
					+ codStato + ", codFedUserId: " + codFedUserId + "] :: START ");

			/**
			 * Get anagrafica stato
			 */
			AnagraficaStato stato = anagraficaStatoDao.findByCodStatoAndDeTipoStato(codStato,
					Constants.DE_TIPO_STATO_ACCERTAMENTO);
			/**
			 * Get utente che sta aggiornando l'accertamento
			 */
			Utente utente = utenteDao.findByCodFedUserIdIgnoreCase(codFedUserId);

			/**
			 * Get accertamento da ggiornare
			 */
//			Accertamento entity = repositoryDao.findOne(accertamentoId);
			Accertamento entity = repositoryDao.findById(accertamentoId);
			/*
			 * set utente
			 */
			entity.setUtente(utente);
			/*
			 * set anagrafica stato
			 */
			entity.setAnagraficaStato(stato);
			/*
			 * set data modifica
			 */
			entity.setDtUltimaModifica(new Date());

			/** Update */
			repositoryDao.saveAndFlush(entity);

			logger.debug("UPDATE STATO :: Accertamento :: Fields[accertamentoID:" + accertamentoId + ", codStato:"
					+ codStato + ", codFedUserId: " + codFedUserId + "] :: END ");

//			if (codStato.equals(Constants.COD_TIPO_STATO_ACCERTAMENTO_CHIUSO)) {
//				boolean flussoAggiornato = aggiornaBilancioFlussoExport(accertamentoId);
//
//				logger.debug("Flusso Export aggiornato [ " + flussoAggiornato + " ]");
//			}
		} catch (Exception e) {
			/* */
			logger.debug("UPDATE STATO :: Accertamento :: Fields[accertamentoID:" + accertamentoId + ", codStato:"
					+ codStato + ", codFedUserId: " + codFedUserId + "] :: ERROR ", e);
			/* */
			throw new RollbackException(e);
		}
	}

	/**
	 * Riversa le proprietà dell'entity nel bean DTO.
	 * 
	 * @param {@link
	 * 			Accertamento} src, entity bean
	 * 
	 * @return {@link AccertamentoDto}
	 * @author Marianna Memoli
	 */
	private AccertamentoDto convertDbToPage(Accertamento src) {
		/* Build DTO */
		AccertamentoDto bean = new AccertamentoDto();

		/* */
		bean.setId(String.valueOf(src.getId()));
		/* */
		bean.setDeNomeAccertamento(src.getDeNomeAccertamento());
		/* */
		bean.setDtCreazione(Constants.DDMMYYYY_HHMM.format(src.getDtCreazione()));
		/* */
		bean.setDtUltimaModifica(Constants.DDMMYYYY_HHMM.format(src.getDtUltimaModifica()));
		/* */
		bean.setStato(modelMapperUtil.map(src.getAnagraficaStato(), AnagraficaStatoTO.class));
		/* */
		bean.setEnteTipoDovuto(modelMapperUtil.map(src.getEnteTipoDovuto(), EnteTipoDovutoTO.class));
		/* */
		bean.setUtente(modelMapperUtil.map(src.getUtente(), UtenteTO.class));
		/* */
		bean.setPrinted(src.isPrinted());

		return bean;
	}

	/**
	 * Aggiorna l'anagrafica dell'accertamento impostando a true la varibile che
	 * individua che il report dell'accertamento è stato stampato almento una
	 * volta.
	 * 
	 * @param {@link
	 * 			Long} accertamentoId, Identificativo dell'anagrafica da
	 *            aggiornare
	 * @param {@link
	 * 			String} codFedUserId, Codice utente
	 * 
	 * @throws Exception
	 * 
	 * @author Marianna Memoli
	 */
	@Override
	public void setPrintedAndSave(Long accertamentoId, String codFedUserId) throws Exception {
		try {
			logger.debug("UPDATE FLAG PRINT :: Accertamento :: Fields[accertamentoId:" + accertamentoId
					+ ", codFedUserId: " + codFedUserId + "] :: START ");

			/**
			 * Get utente che sta aggiornando l'accertamento
			 */
			Utente utente = utenteDao.findByCodFedUserIdIgnoreCase(codFedUserId);

			/**
			 * Get accertamento da aggiornare
			 */
			Accertamento entity = repositoryDao.findOne(accertamentoId);
			/*
			 * set utente
			 */
			entity.setUtente(utente);
			/*
			 * set stampato
			 */
			entity.setPrinted(true);
			/*
			 * set data modifica
			 */
			entity.setDtUltimaModifica(new Date());

			/** Update */
			repositoryDao.saveAndFlush(entity);

			logger.debug("UPDATE FLAG PRINT :: Accertamento :: Fields[accertamentoId:" + accertamentoId
					+ ", codFedUserId: " + codFedUserId + "] :: END ");

		} catch (Exception e) {
			/* */
			logger.debug("UPDATE FLAG PRINT :: Accertamento :: Fields[accertamentoId:" + accertamentoId
					+ ", codFedUserId: " + codFedUserId + "] :: ERROR ", e);
			/* */
			throw new RollbackException(e);
		}
	}

	private boolean aggiornaBilancioFlussoExport(final Long accertamentoId) {
		Assert.notNull(accertamentoId);
		logger.debug(
				"Aggiornamento struttura bilancio nella tabella flusso export per i dettagli dell'accertamento con ID [ "
						+ accertamentoId + " ]");
		EnteTO enteTO = SecurityContext.getEnte();
		Accertamento accertamento = repositoryDao.findOne(accertamentoId);
		AnagraficaStato anagraficaStatoAccertamento = accertamento.getAnagraficaStato();
		if (anagraficaStatoAccertamento.getDeTipoStato().equals(Constants.DE_TIPO_STATO_ACCERTAMENTO)
				&& anagraficaStatoAccertamento.getCodStato().equals(Constants.COD_TIPO_STATO_ACCERTAMENTO_CHIUSO)) {
			List<Object[]> listaBilancioExportObject = repositoryDettaglioDao
					.getListaDettagliRaggruppataByAccertamentoId(accertamentoId);

			if (listaBilancioExportObject == null || CollectionUtils.isEmpty(listaBilancioExportObject)) {
				throw new RuntimeException(
						"Nessun dettaglio trovato per l'accertamento con ID [ " + accertamentoId + " ]");
			}
			List<BilancioFlussoExportDto> listaBilancioExport = new ArrayList<BilancioFlussoExportDto>();
			for (Object[] obj : listaBilancioExportObject) {
				BilancioFlussoExportDto dto = new BilancioFlussoExportDto();
				dto.setCodIud(String.valueOf(obj[0]));
				dto.setCodCapitolo(String.valueOf(obj[1]));
				dto.setCodUfficio(String.valueOf(obj[2]));
				dto.setCodAccertamento(String.valueOf(obj[3]));
				dto.setNumImporto((BigDecimal) obj[4]);
				listaBilancioExport.add(dto);
			}
			Map<String, BilancioDocument> mappaBilancio = new HashMap<String, BilancioDocument>();
			for (BilancioFlussoExportDto dto : listaBilancioExport) {
				String key = dto.getCodIud();
				if (!mappaBilancio.containsKey(key)) {
					mappaBilancio.put(key, BilancioDocument.Factory.newInstance());
				}

				BilancioDocument document = mappaBilancio.get(key);
				CtBilancio bilancio = null;
				if (document.getBilancio() == null) {
					bilancio = document.addNewBilancio();
				} else {
					bilancio = document.getBilancio();
				}
				boolean capitoloFound = false;
				for (CtCapitolo ctCapitolo : bilancio.getCapitoloArray()) {
					if (ctCapitolo.getCodCapitolo().equals(dto.getCodCapitolo())
							&& ctCapitolo.getCodUfficio().equals(dto.getCodUfficio())) {
						boolean accertamentoFound = false;
						for (CtAccertamento ctAccertamento : ctCapitolo.getAccertamentoArray()) {
							if (ctAccertamento.getCodAccertamento().equals(dto.getCodAccertamento())) {
								BigDecimal importo = ctAccertamento.getImporto();
								importo = importo.add(dto.getNumImporto());
								ctAccertamento.setImporto(importo);
								accertamentoFound = true;
							}
						}
						if (!accertamentoFound) {
							CtAccertamento ctAccertamento = ctCapitolo.addNewAccertamento();
							ctAccertamento.setCodAccertamento(dto.getCodAccertamento());
							ctAccertamento.setImporto(dto.getNumImporto());
						}
						capitoloFound = true;
					}
				}
				if (!capitoloFound) {
					CtCapitolo ctCapitolo = bilancio.addNewCapitolo();
					ctCapitolo.setCodCapitolo(dto.getCodCapitolo());
					ctCapitolo.setCodUfficio(dto.getCodUfficio());

					// Set dell'accertamento
					CtAccertamento ctAccertamento = ctCapitolo.addNewAccertamento();
					ctAccertamento.setCodAccertamento(dto.getCodAccertamento());
					ctAccertamento.setImporto(dto.getNumImporto());
				}
				mappaBilancio.put(key, document);
			}

			for (Map.Entry<String, BilancioDocument> entry : mappaBilancio.entrySet()) {
				String key = entry.getKey();
				BilancioDocument document = entry.getValue();

				XmlOptions xmlOptions = new XmlOptions();
				Map<String, String> namespaceMap = new HashMap<String, String>();
				xmlOptions.setUseDefaultNamespace();
				namespaceMap.put("", "http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/bilancio/");
				xmlOptions.setSaveImplicitNamespaces(namespaceMap);
				xmlOptions.setSavePrettyPrint();
				xmlOptions.setSaveNamespacesFirst();
				String bilancio = document.xmlText(xmlOptions);
				StringBuffer sb = new StringBuffer();
				Scanner scanner = new Scanner(bilancio);
				while (scanner.hasNextLine()) {
					String line = scanner.nextLine();
					sb.append(line.trim());
				}
				scanner.close();
				bilancio = sb.toString();
				logger.debug("Bilancio: " + bilancio);

				FlussoExport flussoExport = flussoExportDao.findByCodIpaEnteIUD(enteTO.getCodIpaEnte(), key);
				flussoExport.setBilancio(bilancio);
				flussoExportDao.saveAndFlush(flussoExport);
			}
			return true;
		}
		throw new RuntimeException("Accertamento non in stato CHIUSO");
	}
}