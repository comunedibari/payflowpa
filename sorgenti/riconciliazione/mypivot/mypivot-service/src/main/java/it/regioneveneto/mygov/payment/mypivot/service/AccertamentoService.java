package it.regioneveneto.mygov.payment.mypivot.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.regioneveneto.mygov.payment.mypivot.domain.dto.AccertamentoDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.PageDto;

/**
 * 
 * @author Marianna Memoli
 *
 */
public interface AccertamentoService {

	/**
	 * Recupera l'elenco di accertamenti, eventualmente filtrati.
	 * Il servizio restituisce un'elenco filtrato dove il bean descrittivo dell'accertamento è in versione ridotta.
     * In versione ridotta perchè, di tutte informazioni che descrivono l'accertamento vengono considerate quelle principali cossicchè
     * l'accertamento sia correttamente descritto ad un'utente che ne prende visione.
     * 
	 * Filtri di ricerca OBBLIGATORI:
	 * ------------------------------------------------------------------------------------------------------------------------------------------
	 * @param {@link String} 	   codIpaEnte,     Ente selezionato come beaneficiario
	 * @param {@link List<Long>}   utenteIDs,      Accertamenti creati dagli utenti presenti nella lista. Se l'utente autenticato ha ruolo:
	 * 												- ROLE_ACC:   visualizza solamente i propri accertamenti
	 * 												- ROLE_ADMIN: visualizza accertamenti effetuati da utenti operatori dell'ente.
	 * @param {@link List<String>} tipiDovutoCODs, Elenco dei tipi dovuto sui quali l'utente autenticato ha i permessi di operatore
	 * 
	 * Filtri di ricerca:
	 * ------------------------------------------------------------------------------------------------------------------------------------------
	 * @param {@link String} 	 codTipoDovuto,    Codice tipo dovuto
	 * @param {@link String} 	 codStato,         Codice dello stato
	 * @param {@link String} 	 nomeAccertamento, Testo digitato contenuto nella descrizione dell'accertamento
	 * @param {@link String} 	 dataUltimoAggDal, Data ultimo aggiornamento dal  (formato dd/MM/yyyy)
	 * @param {@link String} 	 dataUltimoAggAl,  Data ultimo aggiornamento al  (formato dd/MM/yyyy)
	 * @param {@link String}     codiceIuv, 	   Identificativo univoco versamento
	 * 
	 * Paginazione:
	 * -----------------------------------------------------------------------------------------------------------------------------------------
	 * @param {@link Boolean}    hasPagination,    Determina se la query deve essere paginata, qualora la fosse diventa obbligatorio la
	 * 											   valorizzazione dei successivi due parametri.
	 * @param {@link Integer} 	 page, 			   Visualizza il punto di partenza nel set di dati corrente.
	 * @param {@link Integer} 	 pageSize, 		   Numero di record che la query deve selezione nella pagina corrente
	 * 
	 * @return {@link PageDto<AccertamentoDto>}
	 * 
	 * @throws Exception
	 * @author Marianna Memoli
 	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PageDto<AccertamentoDto> findByFilter(
			String codIpaEnte, List<Long> utenteIDs, List<String> tipiDovutoCODs, String codTipoDovuto, String codStato, String nomeAccertamento, 
			String dataUltimoAggDal, String dataUltimoAggAl, String codiceIuv, Boolean hasPagination, int page, int pageSize) throws Exception;

	/**
	 * Inserisce in banca dati l'anagrafica di un nuovo accertamento.
	 *
	 * @param {@link String} 	nomeAccertamento, Nome/descrizione dell'accertamento
	 * @param {@link String} 	codTipoDovuto, 	  Codice del tipo di dovuto
	 * @param {@link String} 	codIpaEnte, 	  Codice Ipa dell'ente
	 * @param {@link Long} 		utenteId, 		  Identificativo dell'utente che ha creato l'accertamento
	 * 
	 * @return {@link Long} - Identificativo dell'accertamento creato
	 * @throws Exception
	 * @author Marianna Memoli
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Long saveAccertamento(String nomeAccertamento, String codTipoDovuto, String codIpaEnte, Long utenteId) throws Exception;
	
	/**
	 * Recupera l'anagrafica dell'accertamento dato l'identificativo.
	 * 
	 * @param {@link Long} accertamentoId, Identificativo dell'anagrafica di cui recuperare il dettaglio
	 * 
	 * @return {@link AccertamentoDto} - oggetto contenente il dettaglio
	 * 
	 * @throws Exception
	 * @author Marianna Memoli
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AccertamentoDto findById(Long accertamentoId) throws Exception;
	
	/**
	 * Aggiorna l'anagrafica dell'accertamento reimpostando l'anagrafica stato con quella fornita in ingresso.
	 * 
	 * @param {@link Long} 	 accertamentoId,  Identificativo dell'anagrafica da aggiornare
	 * @param {@link String} codStato,		  Codice dello stato
	 * @param {@link String} codFedUserId,	  Codice utente
	 * 
	 * @throws Exception
	 * @author Marianna Memoli
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void setStatoAndSave(Long accertamentoId, String codStato, String codFedUserId) throws Exception;
	
	/**
	 * Aggiorna l'anagrafica dell'accertamento impostanto a true la varibile che individua che il report dell'accertamento
	 * è stato stampato almento una volta.
	 * 
	 * @param {@link Long} 	 accertamentoId,  Identificativo dell'anagrafica da aggiornare
	 * @param {@link String} codFedUserId,	  Codice utente
	 * 
	 * @throws Exception
	 * @author Marianna Memoli
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void setPrintedAndSave(Long accertamentoId, String codFedUserId) throws Exception;
}