package it.regioneveneto.mygov.payment.mypivot.dao;

import java.util.List;

import it.regioneveneto.mygov.payment.mypivot.domain.dto.PageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.po.Accertamento;

/**
 * 
 * @author Marianna Memoli
 *
 */
public interface AccertamentoDao {

	/**
	 * Recupera l'elenco di accertamenti, eventualmente filtrati.
	 * 
	 * Filtri di ricerca OBBLIGATORI:
	 * ------------------------------------------------------------------------------------------------------------------------------------------
	 * @param {@link String} 	   codIpaEnte,     Ente selezionato come beaneficiario
	 * @param {@link List<Long>}   utenteIDs,      Accertamenti creati dagli utenti presenti nella lista. Se l'utente autenticato ha ruolo:
	 * 												- ROLE_ACC:   visualizza solamente i propri accertamenti
	 * 												- ROLE_ADMIN: visualizza accertamenti effetuati da utenti operatori dell'ente e che hanno i 
	 * 															  permessi di operatori per gli stessi tipi dovuti dell'utente autenticato.
	 * @param {@link List<String>} tipiDovutoCODs, Elenco dei tipi dovuto sui quali l'utente autenticato ha i permessi di operatore
	 * 
	 * 
	 * Filtri di ricerca OPZIONALI:
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
	 * @param {@link String} 	 page, 			   Visualizza il punto di partenza nel set di dati corrente.
	 * @param {@link String} 	 pageSize, 		   Numero di record che la query deve selezione nella pagina corrente
	 * 
	 * @return {@link PageDto<Accertamento>}
	 * @throws Exception
	 * @author Marianna Memoli
 	 */
	public PageDto<Accertamento> findByFilter(
		String codIpaEnte, List<Long> utenteIDs, List<String> tipiDovutoCODs, String codTipoDovuto, String codStato, String nomeAccertamento, 
		String dataUltimoAggDal, String dataUltimoAggAl, String codiceIuv, Boolean hasPagination, int page, int pageSize) throws Exception;
	
}