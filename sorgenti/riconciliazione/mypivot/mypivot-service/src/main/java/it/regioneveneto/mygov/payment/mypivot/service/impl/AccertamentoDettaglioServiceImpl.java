package it.regioneveneto.mygov.payment.mypivot.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.RollbackException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import it.regioneveneto.mygov.payment.mypivot.dao.AccertamentoDettaglioDao;
import it.regioneveneto.mygov.payment.mypivot.dao.AccertamentoDettaglioRepositoryDao;
import it.regioneveneto.mygov.payment.mypivot.dao.AccertamentoRepositoryDao;
import it.regioneveneto.mygov.payment.mypivot.dao.UtenteDao;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.AccertamentoDettaglioDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.AccertamentoFlussoExportDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.AccertamentoUfficioCapitoloDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.PageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.po.Accertamento;
import it.regioneveneto.mygov.payment.mypivot.domain.po.AccertamentoDettaglio;
import it.regioneveneto.mygov.payment.mypivot.domain.po.Utente;
import it.regioneveneto.mygov.payment.mypivot.service.AccertamentoDettaglioService;
import it.regioneveneto.mygov.payment.mypivot.utils.Constants;

/**
 * 
 * @author Marianna Memoli
 *
 */
@Service
public class AccertamentoDettaglioServiceImpl implements AccertamentoDettaglioService {

	private static final Logger logger = Logger.getLogger(AccertamentoDettaglioServiceImpl.class);

	@Autowired
	private AccertamentoDettaglioDao accertamentoDettaglioDao;

	@Autowired
	private AccertamentoRepositoryDao accertamentoRepositoryDao;

	@Autowired
	private UtenteDao utenteDao;

	@Autowired
	private AccertamentoDettaglioRepositoryDao acccertamentoDttRepositoryDao;

	/**
	 * Recupera l'elenco dei pagamenti inseribili in accertamento, eventualmente filtrati.
	 * 
	 * Filtri di ricerca OBBLIGATORI:
	 * ------------------------------------------------------------------------------------------------------------------------------------------
	 * @param {@link Long}     enteId,    		Identificativo dell'ente
	 * @param {@link String}   codTipoDovuto, 	Codice del tipo dovuto
	 * 
	 * Filtri di ricerca OPZIONALI:
	 * ------------------------------------------------------------------------------------------------------------------------------------------
	 * @param {@link String}   codiceIud, 	   						Identificativo univoco dovuto
	 * @param {@link String}   codiceIuv, 	  						Identificativo univoco versamento
	 * @param {@link String}   codiceIdentificativoUnivocoPagatore, CF/PIVA Pagatore
	 * 
	 * @param {@link String}   dataEsitoSingoloPagamentoDal,  		Data esito pagamento dal  (formato dd/MM/yyyy)
	 * @param {@link String}   dataEsitoSingoloPagamentoAl,   		Data esito pagamento al   (formato dd/MM/yyyy)
	 * 
	 * @param {@link String}   dataUltimoAggiornamentoDal, 			Data ultimo aggiornamento dal  (formato dd/MM/yyyy)
	 * @param {@link String}   dataUltimoAggiornamentoAl,   		Data ultimo aggiornamento al   (formato dd/MM/yyyy)
	 * 
	 * Paginazione:
	 * -----------------------------------------------------------------------------------------------------------------------------------------
	 * @param {@link Boolean}    hasPagination,    Determina se la query deve essere paginata, qualora la fosse diventa obbligatorio la
	 * 											   valorizzazione dei successivi due parametri.
	 * @param {@link Integer} 	 page, 			   Visualizza il punto di partenza nel set di dati corrente.
	 * @param {@link Integer} 	 pageSize, 		   Numero di record che la query deve selezione nella pagina corrente
	 * 
	 * @return {@link PageDto<AccertamentoDettaglioDto>}
	 * @throws Exception
	 * @author Marianna Memoli
 	 */
	@Override
	public PageDto<AccertamentoDettaglioDto> findPagamentiAccertabiliByFilter(
			 Long enteId, String codTipoDovuto, String codiceIud, String codiceIuv, String codiceIdentificativoUnivocoPagatore, 
			 String dataEsitoSingoloPagamentoDal, String dataEsitoSingoloPagamentoAl, String dataUltimoAggiornamentoDal, 
			 String dataUltimoAggiornamentoAl, Boolean hasPagination, int page, int pageSize) throws Exception {
		try{
			logger.debug("SERVICE :: RICERCA :: PAGAMENTI INSERIBILI IN ACCERTAMENTO :: PARAMETRI DI RICERCA[" + 
							"enteId:" + enteId + ", codTipoDovuto:" + codTipoDovuto + ", " + 
							"codiceIud:" + codiceIud + ", codiceIuv:" + codiceIuv + ", " +
							"codiceIdentificativoUnivocoPagatore:" + codiceIdentificativoUnivocoPagatore + ", " + 
							"dataEsitoSingoloPagamentoDal:" + dataEsitoSingoloPagamentoDal + ", " +
							"dataEsitoSingoloPagamentoAl:" + dataEsitoSingoloPagamentoAl + ", " +
							"dataUltimoAggiornamentoDal:" + dataUltimoAggiornamentoDal + ", " + 
							"dataUltimoAggiornamentoAl:" + dataUltimoAggiornamentoAl + ", " + 
							"hasPagination:" + hasPagination + ", page:" + page + ", pageSize:" + pageSize + 
					"] :: START");
			
			/** 
			 * Eseguo query 
			 */
			PageDto<AccertamentoDettaglioDto> beans = accertamentoDettaglioDao.findPagamentiAccertabiliByFilter(
												enteId, codTipoDovuto, codiceIud, codiceIuv, codiceIdentificativoUnivocoPagatore, 
												dataEsitoSingoloPagamentoDal, dataEsitoSingoloPagamentoAl, dataUltimoAggiornamentoDal,
												dataUltimoAggiornamentoAl, hasPagination, page, pageSize);
			
			logger.debug("SERVICE :: RICERCA :: PAGAMENTI INSERIBILI IN ACCERTAMENTO :: END");
			
			return beans;
		}catch(Exception e){
			logger.error("SERVICE :: RICERCA :: PAGAMENTI INSERIBILI IN ACCERTAMENTO :: END", e);
			throw(e);
		}
	}
	
	/**
	 * Recupera l'elenco dei pagamenti inseriti in accertamento dato l'identificativo dello stesso, eventualmente filtrati.
	 * 
	 * Filtri di ricerca OBBLIGATORI:
	 * ------------------------------------------------------------------------------------------------------------------------------------------
	 * @param {@link Long}     accertamentoID,  Identificativo accertamento
	 * @param {@link Long}     enteId,    		Identificativo dell'ente
	 * @param {@link String}   codTipoDovuto, 	Codice del tipo dovuto
	 * 
	 * Filtri di ricerca OPZIONALI:
	 * ------------------------------------------------------------------------------------------------------------------------------------------
	 * @param {@link String}   codiceIud, 	   						Identificativo univoco dovuto
	 * @param {@link String}   codiceIuv, 	  						Identificativo univoco versamento
	 * @param {@link String}   codiceIdentificativoUnivocoPagatore, CF/PIVA Pagatore
	 * 
	 * @param {@link String}   dataEsitoSingoloPagamentoDal,  		Data esito pagamento dal  (formato dd/MM/yyyy)
	 * @param {@link String}   dataEsitoSingoloPagamentoAl,   		Data esito pagamento al   (formato dd/MM/yyyy)
	 * 
	 * @param {@link String}   dataUltimoAggiornamentoDal, 			Data ultimo aggiornamento dal  (formato dd/MM/yyyy)
	 * @param {@link String}   dataUltimoAggiornamentoAl,   		Data ultimo aggiornamento al   (formato dd/MM/yyyy)
	 * 
	 * Paginazione:
	 * -----------------------------------------------------------------------------------------------------------------------------------------
	 * @param {@link Boolean}    hasPagination,    Determina se la query deve essere paginata, qualora la fosse diventa obbligatorio la
	 * 											   valorizzazione dei successivi due parametri.
	 * @param {@link Integer} 	 page, 			   Visualizza il punto di partenza nel set di dati corrente.
	 * @param {@link Integer} 	 pageSize, 		   Numero di record che la query deve selezione nella pagina corrente
	 * 
	 * @return {@link PageDto<AccertamentoDettaglioDto>}
	 * @throws Exception
	 * @author Marianna Memoli
 	 */
	@Override
	public PageDto<AccertamentoDettaglioDto> findPagamentiInAccertamentoByFilter(
			 Long accertamentoId, Long enteId, String codTipoDovuto, String codiceIud, String codiceIuv, 
			 String codiceIdentificativoUnivocoPagatore, String dataEsitoSingoloPagamentoDal, String dataEsitoSingoloPagamentoAl,  
			 String dataUltimoAggiornamentoDal, String dataUltimoAggiornamentoAl, Boolean hasPagination, int page, int pageSize) throws Exception {
		try{
			logger.debug("SERVICE :: RICERCA :: PAGAMENTI INSERITI IN ACCERTAMENTO :: PARAMETRI DI RICERCA[" + 
							 	"accertamentoId:" + accertamentoId + ", enteId:" + enteId + ", codTipoDovuto:" + codTipoDovuto + ", " + 
								"codiceIud:" + codiceIud + ", codiceIuv:" + codiceIuv + ", " +
								"codiceIdentificativoUnivocoPagatore:" + codiceIdentificativoUnivocoPagatore + ", " + 
								"dataEsitoSingoloPagamentoDal:" + dataEsitoSingoloPagamentoDal + ", " +
								"dataEsitoSingoloPagamentoAl:" + dataEsitoSingoloPagamentoAl + ", " +
								"dataUltimoAggiornamentoDal:" + dataUltimoAggiornamentoDal + ", " + 
								"dataUltimoAggiornamentoAl:" + dataUltimoAggiornamentoAl + ", " + 
								"hasPagination:" + hasPagination + ", page:" + page + ", pageSize:" + pageSize + 
						"] :: START");

			/** 
			 * Eseguo query 
			 */
			PageDto<AccertamentoDettaglioDto> beans = accertamentoDettaglioDao.findPagamentiInAccertamentoByFilter(
													accertamentoId, enteId, codTipoDovuto, codiceIud, codiceIuv, 
													codiceIdentificativoUnivocoPagatore, dataEsitoSingoloPagamentoDal, dataEsitoSingoloPagamentoAl, 
													dataUltimoAggiornamentoDal, dataUltimoAggiornamentoAl, hasPagination, page, pageSize);
			
			logger.debug("SERVICE :: RICERCA :: PAGAMENTI INSERITI IN ACCERTAMENTO :: END");
			
			return beans;
		}catch(Exception e){
			logger.error("SERVICE :: RICERCA :: PAGAMENTI INSERITI IN ACCERTAMENTO :: END", e);
			throw(e);
		}
	}
	
	/**
	 * Censisce in banca dati i pagamenti in accertamento.
	 * Questo scenario che prevede l'acquisizione di più pagamenti non permette lo spacchettamento su più capitoli/accertamenti
	 * per questi è attribuibile quindi un unico capitolo/accertamento.
	 * Sono inserite in "mygov_accertamento dettaglio" tante righe quanti sono i pagamenti selezionati e gli importi riportati in 
	 * tabella corrispondono agli importi dei singoli pagamenti.
	 * 
	 * @param {@link Long} 						        accertamentoId, Identificativo dell'accertamento
	 * @param {@link String} 						    codIpaEnte, 	Codice ipa del'ente
	 * @param {@link Long} 							    utenteId, 		Identificativo dell'utente in modifica
	 * @param {@link List<AccertamentoFlussoExportDto>} pagList, 		Lista RT da aggiungere all'accertamento
	 * @param {@link AccertamentoUfficioCapitoloDto}    anag, 		    Dati del capitolo/accertamento
	 * 
	 * @throws Exception
	 * @author Marianna Memoli
	 */
	@Override
	public void addPagamenti(Long accertamentoId, String codIpaEnte, List<AccertamentoFlussoExportDto> list, AccertamentoUfficioCapitoloDto anag, Long utenteId) throws Exception{
		try{
			logger.debug("INSERT RT :: ACCERTAMENTO :: Fields[accertamentoId:" + accertamentoId + ", codIpaEnte:" + codIpaEnte + ", " +
						 "N. Pagamenti:" + list.size() + ", ufficio:" + anag.toString() + ", utenteId:" + utenteId + "] :: START");	

			/**
			 * Get Accertamento
			 */
//			Accertamento accertamento = accertamentoRepositoryDao.findOne(accertamentoId);
			Accertamento accertamento = accertamentoRepositoryDao.findById(accertamentoId);
			/**
			 * Get Anagrafica utente che sta aggiornando l'accertamento
			 */
//			Utente utente = utenteDao.findOne(utenteId);
			Utente utente = utenteDao.findById(utenteId);

			
			/**
			 * Ciclo i pagamenti, se non esiste gia in tabella scrivo sul db
			 */
			for (AccertamentoFlussoExportDto item : list) {
				
				logger.debug("Info RT :: " + item.toString());
				
				/*
				 * Controllo che in tabella questa RT non sia gia presente
				 */
				if(acccertamentoDttRepositoryDao.exist(item.getCodiceIud(), item.getCodiceIuv(), codIpaEnte, Constants.DE_TIPO_STATO_ACCERTAMENTO, Constants.COD_TIPO_STATO_ACCERTAMENTO_ANNULLATO) != null) 
					throw new RollbackException("Relation Fields[codIud:" + item.getCodiceIud() + ", codIuv:" + item.getCodiceIuv() + ", codIpaEnte:" + codIpaEnte + "] Esiste già.");
				
				/**
				 * Instance Entity, set value
				 */
				AccertamentoDettaglio entity = new AccertamentoDettaglio();
				/*
				 */
				entity.setAccertamento(accertamento);			  /* Set Riferimento all'accertamento */
				entity.setUtente(utente);						  /* Set utente che sta creando l'accertamento */
				entity.setCodIud(item.getCodiceIud());			  /* Idendificativo univoco dovuto */
				entity.setCodIuv(item.getCodiceIuv());			  /* Idendificativo univoco versamento */
				entity.setCodIpaEnte(codIpaEnte);				  /* Codice Ipa ente */
				entity.setCodTipoDovuto(item.getCodTipoDovuto()); /* Codice della tipologia di dovuto */
				/*
				 */
				entity.setCodUfficio(anag.getCodUfficio());			  /* Codice dell'ufficio */
				entity.setCodCapitolo(anag.getCodCapitolo());		  /* Codice del capitolo */
				entity.setCodAccertamento(anag.getCodAccertamento()); /* Codice dell'accertamento */
				entity.setNumImporto(item.getSingoloImportoPagato()); /* Importo RT */
				entity.setFlgImportoInserito(Boolean.TRUE);
				/*
				 */
				entity.setDtDataInserimento(new Date()); 	/* Set data inserimento */
				entity.setDtUltimaModifica(new Date());		/* Set data aggiornamento */

				/**
				 *  Save entity 
				 */
				acccertamentoDttRepositoryDao.saveAndFlush(entity);
			}// close for
			
			/** 
			 * Update accertamento
			 */
			accertamento.setUtente(utente);					/* Set utente  */
			accertamento.setDtUltimaModifica(new Date());	/* Set data modifica */
			
			/**
			 * Save 
			 */
			accertamentoRepositoryDao.saveAndFlush(accertamento);
				
			logger.debug("INSERT RT :: ACCERTAMENTO :: Fields[accertamentoId:" + accertamentoId + ", codIpaEnte:" + codIpaEnte + ", " +
					     "N. Pagamenti:" + list.size() + ", ufficio:" + anag.toString() + ", utenteId:" + utenteId + "] :: END");	
		}catch(Exception e){
			/* */
			logger.debug("INSERT RT :: ACCERTAMENTO :: Fields[accertamentoId:" + accertamentoId + ", codIpaEnte:" + codIpaEnte + ", " +
					 	 "N. Pagamenti:" + list.size() + ", ufficio:" + anag.toString() + ", utenteId:" + utenteId + "] :: ERROR", e);	
			/* */
			throw new RollbackException(e);
		}
	}
	
	/**
	 * Censisce in banca dati i pagamenti in accertamento.
	 * Questo scenario che prevede l'acquisizione di un singolo pagamento permettendo quindi lo spacchettamento su più capitoli/accertamenti.
	 * Sono inserite in "mygov_accertamento dettaglio" tante righe quanti sono gli importi inseriti dall'utente.
	 * 
	 * @param {@link Long} 						     	   accertamentoId, Identificativo dell'accertamento
	 * @param {@link String} 						 	   codIpaEnte, 	   Codice ipa dell'ente
	 * @param {@link Long} 							 	   utenteId, 	   Identificativo dell'utente in modifica
	 * @param {@link AccertamentoFlussoExportDto} 	       pagamento, 	   Lista RT da aggiungere all'accertamento
	 * @param {@link List<AccertamentoUfficioCapitoloDto>} anagList, 	   Lista capitoli/accertamenti
	 * 
	 * @throws Exception
	 * @author Marianna Memoli
	 */
	@Override
	public void addPagamenti(Long accertamentoId, String codIpaEnte, AccertamentoFlussoExportDto pagamento, List<AccertamentoUfficioCapitoloDto> uffici, Long utenteId) throws Exception{
		try{
			logger.debug("INSERT RT :: ACCERTAMENTO :: Fields[accertamentoId:" + accertamentoId + ", codIpaEnte:" + codIpaEnte + ", " +
						 "N. Capitoli:" + uffici.size() + ", pagamento:" + pagamento.toString() + ", utenteId:" + utenteId + "] :: START");	
			
			/**
			 * Get Accertamento
			 */
//			Accertamento accertamento = accertamentoRepositoryDao.findOne(accertamentoId);
			Accertamento accertamento = accertamentoRepositoryDao.findById(accertamentoId);
			
			/**
			 * Get Anagrafica utente che sta aggiornando l'accertamento
			 */
//			Utente utente = utenteDao.findOne(utenteId);
			Utente utente = utenteDao.findById(utenteId);
			
			/*
			 * Controllo che in tabella questa RT non sia gia presente
			 */
			if(acccertamentoDttRepositoryDao.exist(pagamento.getCodiceIud(), pagamento.getCodiceIuv(), codIpaEnte, Constants.DE_TIPO_STATO_ACCERTAMENTO, Constants.COD_TIPO_STATO_ACCERTAMENTO_ANNULLATO) != null) 
				throw new RollbackException("Relation Fields[codIud:" + pagamento.getCodiceIud() + ", codIuv:" + pagamento.getCodiceIuv() + ", codIpaEnte:" + codIpaEnte + "] Esiste già.");
			
			/**
			 * Ciclo i pagamenti, se non esiste gia in tabella scrivo sul db
			 */
			for (AccertamentoUfficioCapitoloDto item : uffici) { 
				logger.debug("Info capitolo :: " + item.toString());
				
				/**
				 * Instance Entity, set value
				 */
				AccertamentoDettaglio entity = new AccertamentoDettaglio();
				/*
				 */
				entity.setAccertamento(accertamento);			 	   /* Set Riferimento all'accertamento */
				entity.setUtente(utente);						 	   /* Set utente che sta creando l'accertamento */
				entity.setCodIud(pagamento.getCodiceIud());			   /* Idendificativo univoco dovuto */
				entity.setCodIuv(pagamento.getCodiceIuv());			   /* Idendificativo univoco versamento */
				entity.setCodIpaEnte(codIpaEnte);				       /* Codice Ipa ente */
				entity.setCodTipoDovuto(pagamento.getCodTipoDovuto()); /* Codice della tipologia di dovuto */
				/*
				 */
				entity.setCodUfficio(item.getCodUfficio());				    /* Codice dell'ufficio */
				entity.setCodCapitolo(item.getCodCapitolo());		  		/* Codice del capitolo */
				entity.setCodAccertamento(item.getCodAccertamento()); 		/* Codice dell'accertamento */
				entity.setNumImporto(new BigDecimal(item.getNumImporto())); /* Importo RT */
				entity.setFlgImportoInserito(Boolean.TRUE);
				/*
				 */
				entity.setDtDataInserimento(new Date()); 	/* Set data inserimento */
				entity.setDtUltimaModifica(new Date());		/* Set data aggiornamento */

				/**
				 *  Save entity 
				 */
				acccertamentoDttRepositoryDao.saveAndFlush(entity);
			}// close for
			
			/** 
			 * Update accertamento
			 */
			accertamento.setUtente(utente);					/* Set utente  */
			accertamento.setDtUltimaModifica(new Date());	/* Set data modifica */
			
			/**
			 * Save 
			 */
			accertamentoRepositoryDao.saveAndFlush(accertamento);
			
			logger.debug("INSERT RT :: ACCERTAMENTO :: Fields[accertamentoId:" + accertamentoId + ", codIpaEnte:" + codIpaEnte + ", " +
					 	 "N. Capitoli:" + uffici.size() + ", pagamento:" + pagamento.toString() + ", utenteId:" + utenteId + "] :: END");	
		
		}catch(Exception e){
			/* */
			logger.debug("INSERT RT :: ACCERTAMENTO :: Fields[accertamentoId:" + accertamentoId + ", codIpaEnte:" + codIpaEnte + ", " +
					 	 "N. Capitoli:" + uffici.size() + ", pagamento:" + pagamento.toString() + ", utenteId:" + utenteId + "] :: ERROR", e);	
			/* */
			throw new RollbackException(e);
		}
	}
	
	/**
	 * Cancella l'associazione tra i pagamenti e l'accertamento dato i dettagli della relazione.
	 * 
	 * @param {@link Long} 		 accertamentoId, 			   Identificativo dell'accertamento
	 * @param {@link Long} 	 	 utenteId, 		 			   Identificativo dell'utente che ha eliminato le RT
	 * @param {@link String} 	 codIpaEnte, 	 			   Codice ipa dell'ente
	 * @param {@link List<AccertamentoFlussoExportDto>} items, RT da rimuovere dall'accertamento
	 *    
	 * @throws Exception
	 * @author Marianna Memoli
	 */
	@Override
	public void deletePagamenti(Long accertamentoId, Long utenteId, String codIpaEnte, List<AccertamentoFlussoExportDto> items) throws Exception{
		try{
			logger.debug("DELETE RT :: ACCERTAMENTO :: Fields[accertamentoId: " + accertamentoId + ", utenteId: " + utenteId + ", codIpaEnte: " + codIpaEnte + ", N. pagamenti: " + items.size() + "] :: START");	

			for (AccertamentoFlussoExportDto dto : items) {
				/**
				 * Delete entity (Possono essere 1 o N a seconda se il pagamento, quando è stato aggiunto, è stato spacchettato
				 * per uno o più capitoli. 
				 */
				acccertamentoDttRepositoryDao.deleteBy(accertamentoId, codIpaEnte, dto.getCodTipoDovuto(), dto.getCodiceIud(), dto.getCodiceIuv());
			}
			
			/**
			 * Get Anagrafica utente che sta aggiornando l'accertamento
			 */
//			Utente utente = utenteDao.findOne(utenteId);
			Utente utente = utenteDao.findById(utenteId);
			
			/**
			 * Update Accertamento utente e data ultimo aggiornamento
			 */
//			Accertamento accertamento = accertamentoRepositoryDao.findOne(accertamentoId);
			Accertamento accertamento = accertamentoRepositoryDao.findById(accertamentoId);
			
			/**
			 * Set utente 
			 */
			accertamento.setUtente(utente);
			/**
			 * Set data modifica 
			 */
			accertamento.setDtUltimaModifica(new Date());
			/**
			 * Save 
			 */
			accertamentoRepositoryDao.saveAndFlush(accertamento);
			
			logger.debug("DELETE RT :: ACCERTAMENTO :: Fields[accertamentoId: " + accertamentoId + ", utenteId: " + utenteId + ", codIpaEnte: " + codIpaEnte + ", N. pagamenti: " + items.size() + "] :: END");	

		}catch(Exception e){
			/* */
			logger.debug("DELETE RT :: ACCERTAMENTO :: Fields[accertamentoId: " + accertamentoId + ", utenteId: " + utenteId + ", codIpaEnte: " + codIpaEnte + ", N. pagamenti: " + items.size() + "] :: ERROR", e);	
			/* */
			throw new RollbackException(e);
		}
	}
	
	/**
	 * Restituisce il numero di pagamenti associati all'accertamento dato l'identificativo.
	 * 
	 * @param {@link Long} accertamentoId
	 * 
	 * @return {@link Long}
	 */
	@Override
	public Long countRowByAccertamentoId(Long accertamentoId) throws Exception{
		try{
			logger.debug("COUNT :: Pagamenti In Accertamento :: Fields[accertamentoId:" + accertamentoId + "] :: START");	

			/**
			 */
			Long count = acccertamentoDttRepositoryDao.countRowByAccertamentoId(accertamentoId);
			
			logger.debug("COUNT :: Pagamenti In Accertamento :: Fields[accertamentoId:" + accertamentoId + "] :: Trovati n " + count + ":: END ");	
			
			return count;
			
		}catch(Exception e){
			/* */
			logger.error("COUNT :: Pagamenti In Accertamento :: Fields[accertamentoId:" + accertamentoId + "] :: ERROR ", e);	
			/* */
			throw(e);
		}
	}

	@Override
	public List<String> getDistinctCodUfficioAccertamentiChiusiByCodIpaEnteAndListaIUD(final String codIpaEnte,
			final List<String> listaIud) {
		Assert.hasText(codIpaEnte, "Parametro [ codIpaEnte ] vuoto");
		Assert.notEmpty(listaIud, "Paramentro [ listaIud ] vuoto");

		logger.debug(
				"Chiamata al Service getDistinctCodUfficioAccertamentiChiusiByCodIpaEnteAndListaIUD con i parameri codIpaEnte [ "
						+ codIpaEnte + " ]");

		List<String> listaCodUfficio = accertamentoDettaglioDao.findDistinctCodUfficioByCodIpaEnteListaIUDAndStato(
				codIpaEnte, listaIud, Constants.COD_TIPO_STATO_ACCERTAMENTO_CHIUSO,
				Constants.DE_TIPO_STATO_ACCERTAMENTO);

		return listaCodUfficio;
	}

	@Override
	public List<String> getDistinctCodTipoDovutoAccertamentiChiusiByCodIpaEnteListaIUDAndCodUfficio(
			final String codIpaEnte, final List<String> listaIud, final String codUfficio) {
		Assert.hasText(codIpaEnte, "Parametro [ codIpaEnte ] vuoto");
		Assert.notEmpty(listaIud, "Paramentro [ listaIud ] vuoto");
		Assert.hasText(codUfficio, "Parametro [ codUfficio ] vuoto");

		logger.debug(
				"Chiamata al Service getDistinctCodTipoDovutoAccertamentiChiusiByCodIpaEnteListaIUDAndCodUfficio con i parameri codIpaEnte [ "
						+ codIpaEnte + " ] e codUfficio [ " + codUfficio + " ]");

		List<String> listaCodTipoDovuto = accertamentoDettaglioDao
				.findDistinctCodTipoDovutoByCodIpaEnteListaIUDCodUfficioAndStato(codIpaEnte, listaIud, codUfficio,
						Constants.COD_TIPO_STATO_ACCERTAMENTO_CHIUSO, Constants.DE_TIPO_STATO_ACCERTAMENTO);

		return listaCodTipoDovuto;
	}

	@Override
	public List<String> getDistinctCodCapitoloAccertamentiChiusiByCodIpaEnteListaIUDCodUfficioAndCodTipoDovuto(
			final String codIpaEnte, final List<String> listaIud, final String codUfficio, final String codTipoDovuto) {
		Assert.hasText(codIpaEnte, "Parametro [ codIpaEnte ] vuoto");
		Assert.notEmpty(listaIud, "Paramentro [ listaIud ] vuoto");
		Assert.hasText(codUfficio, "Parametro [ codUfficio ] vuoto");
		Assert.hasText(codTipoDovuto, "Parametro [ codTipoDovuto ] vuoto");

		logger.debug(
				"Chiamata al Service getDistinctCodCapitoloAccertamentiChiusiByCodIpaEnteListaIUDCodUfficioAndCodTipoDovuto con i parameri codIpaEnte [ "
						+ codIpaEnte + " ], codUfficio [ " + codUfficio + " ] e codTipoDovuto [ " + codTipoDovuto
						+ " ]");

		List<String> listaCodCapitolo = accertamentoDettaglioDao
				.findDistinctCodCapitoloByCodIpaEnteListaIUDCodUfficioCodTipoDovutoAndStato(codIpaEnte, listaIud,
						codUfficio, codTipoDovuto, Constants.COD_TIPO_STATO_ACCERTAMENTO_CHIUSO,
						Constants.DE_TIPO_STATO_ACCERTAMENTO);

		return listaCodCapitolo;
	}

	@Override
	public List<String> getDistinctCodAccertamentoAccertamentiChiusiByCodIpaEnteListaIUDCodUfficioCodTipoDovutoAndCodCapitolo(
			final String codIpaEnte, final List<String> listaIud, final String codUfficio, final String codTipoDovuto,
			final String codCapitolo) {
		Assert.hasText(codIpaEnte, "Parametro [ codIpaEnte ] vuoto");
		Assert.notEmpty(listaIud, "Paramentro [ listaIud ] vuoto");
		Assert.hasText(codUfficio, "Parametro [ codUfficio ] vuoto");
		Assert.hasText(codTipoDovuto, "Parametro [ codTipoDovuto ] vuoto");
		Assert.hasText(codCapitolo, "Parametro [ codCapitolo ] vuoto");

		logger.debug(
				"Chiamata al Service getDistinctCodAccertamentoAccertamentiChiusiByCodIpaEnteListaIUDCodUfficioCodTipoDovutoAndCodCapitolo con i parameri codIpaEnte [ "
						+ codIpaEnte + " ], codUfficio [ " + codUfficio + " ], codTipoDovuto [ " + codTipoDovuto
						+ " ] e codCapitolo [ " + codCapitolo + " ]");

		List<String> listaCodAccertamento = accertamentoDettaglioDao
				.findDistinctCodAccertamentoByCodIpaEnteListaIUDCodUfficioCodTipoDovutoCodCapitoloAndStato(codIpaEnte,
						listaIud, codUfficio, codTipoDovuto, codCapitolo, Constants.COD_TIPO_STATO_ACCERTAMENTO_CHIUSO,
						Constants.DE_TIPO_STATO_ACCERTAMENTO);

		return listaCodAccertamento;
	}

	@Override
	public BigDecimal getSumImportoByAccertamento(final String codIpaEnte, final List<String> listaIud,
			final String codUfficio, final String codTipoDovuto, final String codCapitolo,
			final String codAccertamento) {
		Assert.hasText(codIpaEnte, "Parametro [ codIpaEnte ] vuoto");
		Assert.notEmpty(listaIud, "Paramentro [ listaIud ] vuoto");
		Assert.hasText(codUfficio, "Parametro [ codUfficio ] vuoto");
		Assert.hasText(codTipoDovuto, "Parametro [ codTipoDovuto ] vuoto");
		Assert.hasText(codCapitolo, "Parametro [ codCapitolo ] vuoto");
		Assert.hasText(codAccertamento, "Parametro [ codAccertamento ] vuoto");

		logger.debug("Chiamata al Service getSumImportoByAccertamento con i parameri codIpaEnte [ " + codIpaEnte
				+ " ], codUfficio [ " + codUfficio + " ], codTipoDovuto [ " + codTipoDovuto + " ], codCapitolo [ "
				+ codCapitolo + " ] e codAccertamento [ " + codAccertamento + " ]");

		BigDecimal sum = accertamentoDettaglioDao.getSumImportoByAccertamentoAndStato(codIpaEnte, listaIud, codUfficio,
				codTipoDovuto, codCapitolo, codAccertamento, Constants.COD_TIPO_STATO_ACCERTAMENTO_CHIUSO,
				Constants.DE_TIPO_STATO_ACCERTAMENTO);

		return sum;
	}
	
	/**
	 * Recupera l'elenco dei capitoli associati alla RT.
	 * 
	 * @param {@link Long}   accertamentoId, Identificativo accertamento
	 * @param {@link Long}   enteId,    	 Identificativo dell'ente
	 * @param {@link String} codiceIpaEnte,  Codice ipa dell'ente
	 * @param {@link String} codTipoDovuto,  Codice del tipo dovuto
	 * @param {@link String} codiceIud, 	 Identificativo univoco del dovuto
	 * @param {@link String} codiceIuv, 	 Identificativo univoco versamento
	 * @param {@link Boolean} flgAttivo, 	 Stato attivazione ufficio ente	  (Facoltativo)
	 * 
	 * @return {@link List<AccertamentoUfficioCapitoloDto>}
	 * @throws Exception
	 * @author Marianna Memoli
 	 */
	@Override
	public List<AccertamentoUfficioCapitoloDto> findListCapitoliByRT(
				Long accertamentoId, String codiceIpaEnte, Long enteId, String codTipoDovuto, String codiceIud, String codiceIuv, Boolean flgAttivo) throws Exception{
		try{
			logger.debug("SERVICE :: RICERCA :: CAPITOLI ASSOCIATI ALLA RT :: PARAMETRI DI RICERCA[" + 
						 "accertamentoId:" + accertamentoId + ", enteId:" + enteId + ", codiceIpaEnte:" + codiceIpaEnte + ", " +
						 "codTipoDovuto:" + codTipoDovuto + ", codiceIud:" + codiceIud + ", codiceIuv:" + codiceIuv + ", " +
						 "flgAttivo:" + flgAttivo + "] :: START");

			/** 
			 * Eseguo query 
			 */
			List<AccertamentoUfficioCapitoloDto> beans = accertamentoDettaglioDao.findListCapitoliByRT(accertamentoId, codiceIpaEnte, enteId, codTipoDovuto, codiceIud, codiceIuv, flgAttivo);
			
			logger.debug("SERVICE :: RICERCA :: CAPITOLI ASSOCIATI ALLA RT :: END");
			
			return beans;
		}catch(Exception e){
			logger.error("SERVICE :: RICERCA :: CAPITOLI ASSOCIATI ALLA RT :: ERROR ", e);
			throw(e);
		}
	}
}
