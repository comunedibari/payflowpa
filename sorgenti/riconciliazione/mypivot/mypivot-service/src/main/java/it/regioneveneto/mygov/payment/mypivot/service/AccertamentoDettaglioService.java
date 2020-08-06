package it.regioneveneto.mygov.payment.mypivot.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.regioneveneto.mygov.payment.mypivot.domain.dto.AccertamentoDettaglioDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.AccertamentoFlussoExportDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.AccertamentoUfficioCapitoloDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.PageDto;

/**
 * 
 * @author Marianna Memoli
 * @author Cristiano Perin
 */
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public interface AccertamentoDettaglioService {

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PageDto<AccertamentoDettaglioDto> findPagamentiAccertabiliByFilter(
			 Long enteId, String codTipoDovuto, String codiceIud, String codiceIuv, String codiceIdentificativoUnivocoPagatore, 
			 String dataEsitoSingoloPagamentoDal, String dataEsitoSingoloPagamentoAl, String dataUltimoAggiornamentoDal, 
			 String dataUltimoAggiornamentoAl, Boolean hasPagination, int page, int pageSize) throws Exception;
	
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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PageDto<AccertamentoDettaglioDto> findPagamentiInAccertamentoByFilter(
			 Long accertamentoId, Long enteId, String codTipoDovuto, String codiceIud, String codiceIuv, 
			 String codiceIdentificativoUnivocoPagatore, String dataEsitoSingoloPagamentoDal, String dataEsitoSingoloPagamentoAl,  
			 String dataUltimoAggiornamentoDal, String dataUltimoAggiornamentoAl, Boolean hasPagination, int page, int pageSize) throws Exception;
	
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
	 * @param {@link AccertamentoUfficioCapitoloDto}    anag, 			Dati del capitolo/accertamento
	 * 
	 * @throws Exception
	 * @author Marianna Memoli
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addPagamenti(Long accertamentoId, String codIpaEnte, List<AccertamentoFlussoExportDto> pagList, AccertamentoUfficioCapitoloDto anag, Long utenteId) throws Exception;
	
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
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addPagamenti(Long accertamentoId, String codIpaEnte, AccertamentoFlussoExportDto rt, List<AccertamentoUfficioCapitoloDto> uffici, Long utenteId) throws Exception;
	
	/**
	 * Cancella l'associazione tra i pagamenti e l'accertamento dati i dettagli della relazione.
	 * 
	 * @param {@link Long} 		 accertamentoId, 			   Identificativo dell'accertamento
	 * @param {@link Long} 	 	 utenteId, 		 			   Identificativo dell'utente che ha eliminato le RT
	 * @param {@link String} 	 codIpaEnte, 	 			   Codice ipa dell'ente
	 * @param {@link List<AccertamentoFlussoExportDto>} items, RT da rimuovere dall'accertamento
	 *    
	 * @throws Exception
	 * @author Marianna Memoli
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void deletePagamenti(Long accertamentoId, Long utenteId, String codIpaEnte, List<AccertamentoFlussoExportDto> items) throws Exception;
	
	/**
	 * Restituisce il numero di pagamenti associati all'accertamento dato l'identificativo.
	 * 
	 * @param {@link Long} accertamentoId
	 * 
	 * @return {@link Long}
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Long countRowByAccertamentoId(Long accertamentoId) throws Exception;
	
	List<String> getDistinctCodUfficioAccertamentiChiusiByCodIpaEnteAndListaIUD(final String codIpaEnte,
			final List<String> listaIud);

	List<String> getDistinctCodTipoDovutoAccertamentiChiusiByCodIpaEnteListaIUDAndCodUfficio(final String codIpaEnte,
			final List<String> listaIud, final String codUfficio);

	List<String> getDistinctCodCapitoloAccertamentiChiusiByCodIpaEnteListaIUDCodUfficioAndCodTipoDovuto(
			final String codIpaEnte, final List<String> listaIud, final String codUfficio, final String codTipoDovuto);

	List<String> getDistinctCodAccertamentoAccertamentiChiusiByCodIpaEnteListaIUDCodUfficioCodTipoDovutoAndCodCapitolo(
			final String codIpaEnte, final List<String> listaIud, final String codUfficio, final String codTipoDovuto,
			final String codCapitolo);

	BigDecimal getSumImportoByAccertamento(final String codIpaEnte, final List<String> listaIud,
			final String codUfficio, final String codTipoDovuto, final String codCapitolo,
			final String codAccertamento);
	
	/**
	 * Recupera l'elenco dei capitoli associati alla RT.
	 * 
	 * @param {@link Long}    accertamentoId, Identificativo accertamento
	 * @param {@link Long}    enteId,    	  Identificativo dell'ente
	 * @param {@link String}  codiceIpaEnte,  Codice ipa dell'ente
	 * @param {@link String}  codTipoDovuto,  Codice del tipo dovuto
	 * @param {@link String}  codiceIud, 	  Identificativo univoco del dovuto
	 * @param {@link String}  codiceIuv, 	  Identificativo univoco versamento
	 * @param {@link Boolean} flgAttivo, 	  Stato attivazione ufficio ente	(Facoltativo)
	 * 
	 * @return {@link List<AccertamentoUfficioCapitoloDto>}
	 * @throws Exception
	 * @author Marianna Memoli
 	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AccertamentoUfficioCapitoloDto> findListCapitoliByRT(
				Long accertamentoId, String codiceIpaEnte, Long enteId, String codTipoDovuto, String codiceIud, String codiceIuv, Boolean flgAttivo) throws Exception;
}
