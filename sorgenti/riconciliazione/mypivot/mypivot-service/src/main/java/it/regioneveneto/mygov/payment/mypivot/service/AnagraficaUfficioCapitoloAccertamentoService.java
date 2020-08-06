package it.regioneveneto.mygov.payment.mypivot.service;

import java.io.InputStream;
import java.util.List;

import org.springframework.data.domain.Sort.Order;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import it.regioneveneto.mygov.payment.mypivot.domain.dto.AggiornaAnagraficaUfficioCapitoloAccertamentoResultDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.AnagraficaUfficioCapitoloAccertamentoDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.PageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.po.AnagraficaUfficioCapitoloAccertamento;
import it.regioneveneto.mygov.payment.mypivot.domain.to.AnagraficaUfficioCapitoloAccertamentoTO;

/**
 *
 * @author Alessandro Paolillo
 *
 */
public interface AnagraficaUfficioCapitoloAccertamentoService {

	/**S
	 * Recupera l'elenco di anagrafiche "uff_cap_acc", eventualmente filtrati.
	 * 
	 * Filtri di ricerca OBBLIGATORI:
	 * ------------------------------------------------------------------------------------------------------------------------------------------
	 * @param {@link Long} 	   enteId,     	   Identificativo dell'ente selezionato come beaneficiario
	 * 
	 * Filtri di ricerca OPZIONALI:
	 * ------------------------------------------------------------------------------------------------------------------------------------------
	 * @param {@link String} 	 codTipoDovuto,   	   Codice tipo dovuto
	 * @param {@link String} 	 codUfficio,           Codice dell'ufficio
	 * @param {@link String} 	 deUfficio, 		   Testo digitato contenuto nella descrizione dell'ufficio
	 * @param {@link String} 	 codCapitolo, 		   Codice capitolo
	 * @param {@link String} 	 deCapitolo, 		   Testo digitato contenuto nella descrizione del capitolo
	 * @param {@link String} 	 deAnnoEsercizio, 	   Anno di esercizio
	 * @param {@link String} 	 codAccertamento,      Codice accertamento
	 * @param {@link String} 	 deAccertamento, 	   Testo digitato contenuto nella descrizione dell'accertamento
	 * @param {@link String} 	 dtUltimaModificaFrom, Data ultima modifica dal  (formato dd/MM/yyyy)
	 * @param {@link String} 	 dtUltimaModificaTo,   Data ultima modifica al   (formato dd/MM/yyyy)
	 * @param {@link String}     flgAttivo, 	   	   
	 * 
	 * Paginazione:
	 * -----------------------------------------------------------------------------------------------------------------------------------------
	 * @param {@link Boolean}    hasPagination,  Determina se la query deve essere paginata, qualora la fosse diventa obbligatorio la
	 * 											 valorizzazione dei successivi due parametri.
	 * @param {@link String} 	 page, 			 Visualizza il punto di partenza nel set di dati corrente.
	 * @param {@link String} 	 pageSize, 		 Numero di record che la query deve selezione nella pagina corrente
	 * 
	 * @param {@link Order} 	 order, 		 Colonna da usare per l'ordinamento della query, se assente di default l'ordinamento è per 
	 * 											 data ultima modifica (DESC)
	 * 
	 * @return {@link PageDto<AnagraficaUfficioCapitoloAccertamentoTO>}
	 * @throws Exception
 	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PageDto<AnagraficaUfficioCapitoloAccertamentoDto> findByFilter(Long idEnte, String codTipoDovuto, String codUfficio, 
			String deUfficio, String codCapitolo, String deCapitolo, String deAnnoEsercizio, String codAccertamento, String deAccertamento,
			String dtUltimaModificaFrom, String dtUltimaModificaTo, Boolean flgAttivo, Boolean hasPagination, int page, int pageSize, Order order) throws Exception;

	/**
	 * Restituisce l'elenco (in distinct) degli uffici dati l'identificativo ente e il codice tipo dovuto. 
	 * Il servizio restituisce un'elenco di uffici dove il bean descrittivo dell'anagrafica è in versione ridotta.
     * In versione ridotta perchè, di tutte informazioni che descrivono l'anagrafica vengono considerate solo quelle
     * tali per cui un'ufficio sia correttamente descritto ad un'utente che ne prende visione.
     * 
	 * @param {@link Long}   	   enteId, 		  Identificativo ente
	 * @param {@link List<String>} codTipiDovuto, Elenco codici tipi dovuto
	 * @param {@link Boolean} 	   flgAttivo, 	  Stato attivazione ente
	 * 
	 * @return {@link List<AnagraficaUfficioCapitoloAccertamentoTO>}
	 * @throws Exception 
	 * @author Marianna Memoli
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AnagraficaUfficioCapitoloAccertamentoTO> findDistinctUfficiByFilter(Long enteId, List<String> codTipiDovuto, Boolean flgAttivo) throws Exception;

	/**
	 * Restituisce l'elenco (in distinct) dei capitoli dati l'identificativo ente, l'anno di esercizio, il codice ufficio e il codice tipo dovuto. 
	 * Il servizio restituisce un'elenco di capitoli dove il bean descrittivo dell'anagrafica è in versione ridotta.
     * In versione ridotta perchè, di tutte informazioni che descrivono l'anagrafica vengono considerate solo quelle
     * tali per cui un capitolo sia correttamente descritto ad un'utente che ne prende visione.
     * 
	 * @param {@link Long}    enteId, 		 Identificativo ente
	 * @param {@link String}  codTipoDovuto, Codice tipo dovuto
	 * @param {@link String}  codUfficio, 	 Codice ufficio
	 * @param {@link String}  annoEsercizio, Anno di esercizio (Es: yyyy)
	 * @param {@link Boolean} flgAttivo,     Stato attivazione ente
	 * 
	 * @return {@link List<AnagraficaUfficioCapitoloAccertamentoTO>}
	 * @author Marianna Memoli
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AnagraficaUfficioCapitoloAccertamentoTO> findDistinctCapitoliByFilter(
								Long enteId, String codTipoDovuto, String codUfficio, String annoEsercizio, Boolean flgAttivo) throws Exception;

	/**
	 * Restituisce l'elenco (in distinct) dei capitoli dati l'identificativo ente, il codice ufficio e il codice tipo dovuto. 
	 * Il servizio restituisce un'elenco di capitoli dove il bean descrittivo dell'anagrafica è in versione ridotta.
     * In versione ridotta perchè, di tutte informazioni che descrivono l'anagrafica vengono considerate solo quelle
     * tali per cui un capitolo sia correttamente descritto ad un'utente che ne prende visione.
     * 
	 * @param {@link Long}    enteId, 		 Identificativo ente
	 * @param {@link String}  codTipoDovuto, Codice tipo dovuto
	 * @param {@link String}  codUfficio, 	 Codice ufficio
	 * 
	 * @return {@link List<AnagraficaUfficioCapitoloAccertamentoTO>}
	 * @author Marianna Memoli
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AnagraficaUfficioCapitoloAccertamentoTO> findDistinctCapitoliByEnteDovutoUfficio(Long enteId, String codTipoDovuto, String codUfficio) throws Exception;
	
	/**
	 * Restituisce l'elenco (in distinct) degli accertamenti dati l'identificativo ente, l'anno di esercizio, il codice ufficio e 
	 * il codice tipo dovuto. 
	 * Il servizio restituisce un'elenco di accertamenti dove il bean descrittivo dell'anagrafica è in versione ridotta.
     * In versione ridotta perchè, di tutte informazioni che descrivono l'anagrafica vengono considerate solo quelle
     * tali per cui un'accertamento sia correttamente descritto ad un'utente che ne prende visione.
     * 
	 * @param {@link Long}    enteId, 		 Identificativo ente
	 * @param {@link String}  codTipoDovuto, Codice tipo dovuto
	 * @param {@link String}  codUfficio, 	 Codice ufficio
	 * @param {@link String}  annoEsercizio, Anno di esercizio (Es: yyyy)
	 * @param {@link String}  codCapitolo, 	 Codice capitolo
	 * @param {@link Boolean} flgAttivo,     Stato attivazione ente
	 * 
	 * @return {@link List<AnagraficaUfficioCapitoloAccertamentoTO>}
	 * @throws Exception 
	 * @author Marianna Memoli
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AnagraficaUfficioCapitoloAccertamentoTO> findDistinctAccertamentiByFilter(
				Long enteId, String codTipoDovuto, String codUfficio, String annoEsercizio, String codCapitolo, Boolean flgAttivo) throws Exception;

	/**
	 * Restituisce la descrizione dell'ufficio.
	 * 
	 * @param {@link Long}   enteId, 	 Identificativo ente
	 * @param {@link String} codUfficio, Codice dell'ufficio
	 * 
	 * @return {@link String}
	 * @throws Exception 
	 * @author Marianna Memoli
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public String getDeUfficio(Long enteId, String codUfficio) throws Exception;

	/**
	 * Restituisce la descrizione del capitolo
	 * 
	 * @param {@link Long}   enteId, 	  Identificativo ente
	 * @param {@link String} codUfficio,  Codice dell'ufficio
	 * @param {@link String} codCapitolo, Codice del capitolo
	 * 
	 * @return {@link String}
	 * @throws Exception
	 * @author Marianna Memoli
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public String getDeCapitolo(Long enteId, String codUfficio, String codCapitolo) throws Exception;

	/**
	 * Restituisce dell'accertamento
	 * 
	 * @param {@link Long}   enteId, 	  	  Identificativo ente
	 * @param {@link String} codUfficio,  	  Codice dell'ufficio
	 * @param {@link String} codCapitolo, 	  Codice del capitolo
	 * @param {@link String} codAccertamento, Codice dell'accertamento
	 * 
	 * @return {@link String}
	 * @throws Exception
	 * @author Marianna Memoli
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public String getDeAccertamento(Long enteId, String codUfficio, String codCapitolo, String codAccertamento) throws Exception;

	/**
	 * Esegue la modifica del record e eventualmente dello stato dell'ufficio
	 * 
	 * @param anagraficaUfficioCapitoloAccertamentoDto
	 * @return
	 * @author Alessandro Paolillo
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public AggiornaAnagraficaUfficioCapitoloAccertamentoResultDto aggiornaAnagrafica(AnagraficaUfficioCapitoloAccertamentoDto anagraficaUfficioCapitoloAccertamentoDto) throws Exception;

	/**
	 * 
	 * @param id
	 * @author Alessandro Paolillo
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = false)
	public void cancellaAnagrafica(Long id);
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = false)
	public Boolean deleteAnagrafica(Long id, Long idEnte);

	
	/**
	 * Legge un file CSV ed effettua operazioni CRUD di anagrafiche "uff_cap_acc"
	 * 
	 * @param {@link MultipartFile} fileCSV
	 * 	 * 
	 * @throws Exception
	 *
	 * @author Alessandro Paolillo
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void manageFileCSV(InputStream inputstreamFileCSV, String codIpaEnte) throws Exception;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void manageFileCSV(InputStream inputstreamFileCSV, String codIpaEnte, String codFedUserId) throws Exception;

	/**
	 * 
	 * @param nuovaAnagrafica
	 * @return
	 * @author Alessandro Paolillo
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public AggiornaAnagraficaUfficioCapitoloAccertamentoResultDto salvaAnagrafica(
			AnagraficaUfficioCapitoloAccertamento nuovaAnagrafica) throws Exception;

	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public AggiornaAnagraficaUfficioCapitoloAccertamentoResultDto salvaAnagrafica(
			AnagraficaUfficioCapitoloAccertamento nuovaAnagrafica, String codIpaEnte) throws Exception;

	/**
	 * 
	 * @param listNuoveAnagrafiche
	 * @return
	 * @author Alessandro Paolillo
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void salvaAnagrafiche(AnagraficaUfficioCapitoloAccertamentoDto nuovaAnagrafica, String[] listCodTipoDovuto, boolean flag) throws Exception;
	
	/**
	 * Recupero tutti gli uffici appartenenti all'ente selezionato
	 * 
	 * @param enteId
	 * @return
	 * @throws Exception
	 * @author Alessandro Paolillo
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AnagraficaUfficioCapitoloAccertamentoTO> findDistinctUfficiByEnteId(Long enteId) throws Exception;

	/**
	 * Recupero tutti i capitoli dato l'id ente
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 * @author Alessandro Paolillo
	 */
	public List<AnagraficaUfficioCapitoloAccertamentoTO> findDistinctCapitoliByEnteId(Long idEnte) throws Exception;

	/**
	 * Recupero tutti gli accertamenti dato id ente, codice ufficio e codice capitolo
	 * 
	 * @param id
	 * @param codUff
	 * @param codCap
	 * @return
	 * @author Alessandro Paolillo
	 */
	public List<AnagraficaUfficioCapitoloAccertamentoTO> findDistinctAccertamentiByCodUffcodCap(Long idEnte, String codUff,
			String codCap) throws Exception;

	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 * @author Alessandro Paolillo
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AnagraficaUfficioCapitoloAccertamentoDto getAnagraficaById(Long id) throws Exception;
	
	/**
	 * 
	 * @param cod
	 * @return
	 * @throws Exception
	 * @author Alessandro Paolillo
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public String getDeTipoDovutoByCod(String cod) throws Exception;
	
	public AnagraficaUfficioCapitoloAccertamentoDto getAnagraficaById(Long id, Long idEnte) throws Exception;
	public String getDeTipoDovutoByCod(String cod,Long idEnte) throws Exception;

	public AggiornaAnagraficaUfficioCapitoloAccertamentoResultDto aggiornaAnagrafica(AnagraficaUfficioCapitoloAccertamentoDto anagraficaUfficioCapitoloAccertamentoDto, String codIpaEnte) throws Exception;
	public AggiornaAnagraficaUfficioCapitoloAccertamentoResultDto modificaAnagrafica(AnagraficaUfficioCapitoloAccertamentoDto anagraficaUfficioCapitoloAccertamentoDto, String codIpaEnte) throws Exception;
}