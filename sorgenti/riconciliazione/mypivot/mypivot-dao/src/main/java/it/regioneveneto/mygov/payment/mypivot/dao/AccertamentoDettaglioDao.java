package it.regioneveneto.mygov.payment.mypivot.dao;


import java.math.BigDecimal;
import java.util.List;

import it.regioneveneto.mygov.payment.mypivot.domain.dto.AccertamentoDettaglioDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.AccertamentoUfficioCapitoloDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.PageDto;

/**
 * 
 * @author Marianna Memoli
 *
 */
public interface AccertamentoDettaglioDao {

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
	 * @param {@link String} 	 page, 			   Visualizza il punto di partenza nel set di dati corrente.
	 * @param {@link String} 	 pageSize, 		   Numero di record che la query deve selezione nella pagina corrente
	 * 
	 * @return {@link PageDto<AccertamentoDettaglioDto>}
	 * @throws Exception
	 * @author Marianna Memoli
 	 */
	public PageDto<AccertamentoDettaglioDto> findPagamentiInAccertamentoByFilter(
			 Long accertamentoId, Long enteId, String codTipoDovuto, String codiceIud, String codiceIuv, 
			 String codiceIdentificativoUnivocoPagatore, String dataEsitoSingoloPagamentoDal, String dataEsitoSingoloPagamentoAl,  
			 String dataUltimoAggiornamentoDal, String dataUltimoAggiornamentoAl, Boolean hasPagination, int page, int pageSize) throws Exception;
	
	/**
	 * Recupera l'elenco dei capitoli associati alla RT.
	 * 
	 * @param {@link Long}    accertamentoId, Identificativo accertamento
	 * @param {@link Long}    enteId,    	  Identificativo dell'ente
	 * @param {@link String}  codiceIpaEnte,  Codice ipa dell'ente
	 * @param {@link String}  codTipoDovuto,  Codice del tipo dovuto
	 * @param {@link String}  codiceIud, 	  Identificativo univoco del dovuto
	 * @param {@link String}  codiceIuv, 	  Identificativo univoco versamento
	 * @param {@link Boolean} flgAttivo, 	  Stato attivazione ufficio ente	  (Facoltativo)
	 * 
	 * @return {@link List<AccertamentoUfficioCapitoloDto>}
	 * @throws Exception
	 * @author Marianna Memoli
 	 */
	public List<AccertamentoUfficioCapitoloDto> findListCapitoliByRT(
				Long accertamentoId, String codiceIpaEnte, Long enteId, String codTipoDovuto, String codiceIud, String codiceIuv, Boolean flgAttivo) throws Exception;
	
	List<String> findDistinctCodUfficioByCodIpaEnteListaIUDAndStato(String codIpaEnte, List<String> listaIud,
			String codStato, String deTipoStato);
	
	List<String> findDistinctCodTipoDovutoByCodIpaEnteListaIUDCodUfficioAndStato(String codIpaEnte,
			List<String> listaIud, String codUfficio, String codStato, String deTipoStato);
	
	List<String> findDistinctCodCapitoloByCodIpaEnteListaIUDCodUfficioCodTipoDovutoAndStato(String codIpaEnte,
			List<String> listaIud, String codUfficio, String codTipoDovuto, String codStato, String deTipoStato);
	
	List<String> findDistinctCodAccertamentoByCodIpaEnteListaIUDCodUfficioCodTipoDovutoCodCapitoloAndStato(
			String codIpaEnte, List<String> listaIud, String codUfficio, String codTipoDovuto, String codCapitolo,
			String codStato, String deTipoStato);
	
	BigDecimal getSumImportoByAccertamentoAndStato(String codIpaEnte, List<String> listaIud, String codUfficio,
			String codTipoDovuto, String codCapitolo, String codAccertamento, String codStato, String deTipoStato);
}