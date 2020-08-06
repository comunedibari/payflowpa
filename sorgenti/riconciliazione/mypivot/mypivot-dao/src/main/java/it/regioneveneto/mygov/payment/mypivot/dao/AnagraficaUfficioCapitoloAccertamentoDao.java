package it.regioneveneto.mygov.payment.mypivot.dao;

import java.util.List;

import it.regioneveneto.mygov.payment.mypivot.domain.dto.AnagraficaUfficioCapitoloAccertamentoDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.PageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.po.AnagraficaUfficioCapitoloAccertamento;

/**
 * 
 * @author Marianna Memoli
 *
 */
public interface AnagraficaUfficioCapitoloAccertamentoDao {

	/**
	 * Restituisce l'elenco (in distinct) degli uffici dati l'identificativo ente e il codice tipo dovuto. 
	 * 
	 * @param {@link Long}   	   enteId, 		  Identificativo ente
	 * @param {@link List<String>} codTipiDovuto, Elenco codici tipi dovuto
	 * @param {@link Boolean} 	   flgAttivo, 	  Stato attivazione ente
	 * 
	 * @return {@link List<AnagraficaUfficioCapitoloAccertamento>}
	 * @throws Exception 
	 * @author Marianna Memoli
	 */
	public List<AnagraficaUfficioCapitoloAccertamento> findDistinctUfficiByFilter(Long enteId, List<String> codTipiDovuto, Boolean flgAttivo) throws Exception;

	/**
	 * Restituisce l'elenco (in distinct) dei capitoli dati l'identificativo ente, l'anno di esercizio, il codice ufficio e il codice tipo dovuto. 
	 * 
	 * @param {@link Long}    enteId, 		 Identificativo ente
	 * @param {@link String}  codTipoDovuto, Codice tipo dovuto
	 * @param {@link String}  codUfficio, 	 Codice ufficio
	 * @param {@link String}  annoEsercizio, Anno di esercizio (Es: yyyy)
	 * @param {@link Boolean} flgAttivo,     Stato attivazione ente
	 * 
	 * @return {@link List<AnagraficaUfficioCapitoloAccertamento>}
	 * @author Marianna Memoli
	 */
	public List<AnagraficaUfficioCapitoloAccertamento> findDistinctCapitoliByFilter(
								Long enteId, String codTipoDovuto, String codUfficio, String annoEsercizio, Boolean flgAttivo) throws Exception;

	/**
	 * Restituisce l'elenco (in distinct) degli accertamenti dati l'identificativo ente, l'anno di esercizio, il codice ufficio e 
	 * il codice tipo dovuto. 
	 * 
	 * @param {@link Long}    enteId, 		 Identificativo ente
	 * @param {@link String}  codTipoDovuto, Codice tipo dovuto
	 * @param {@link String}  codUfficio, 	 Codice ufficio
	 * @param {@link String}  annoEsercizio, Anno di esercizio (Es: yyyy)
	 * @param {@link String}  codCapitolo, 	 Codice capitolo
	 * @param {@link Boolean} flgAttivo,     Stato attivazione ente
	 * 
	 * @return {@link List<AnagraficaUfficioCapitoloAccertamento>}
	 * @throws Exception 
	 * @author Marianna Memoli
	 */
	public List<AnagraficaUfficioCapitoloAccertamento> findDistinctAccertamentiByFilter(
			Long enteId, String codTipoDovuto, String codUfficio, String annoEsercizio, String codCapitolo, Boolean flgAttivo) throws Exception;

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
	 * @return {@link List<AnagraficaUfficioCapitoloAccertamento>}
	 * @author Marianna Memoli
	 */
	public List<AnagraficaUfficioCapitoloAccertamento> findDistinctCapitoliByEnteDovutoUfficio(Long enteId, String codTipoDovuto, String codUfficio) throws Exception;
	
	/**
	 * 
	 * @param idEnte
	 * @param codiceUfficio
	 * @param denominazioneUfficio
	 * @param codiceCapitolo
	 * @param denominazioneCapitolo
	 * @param deAnnoEsercizio
	 * @param codiceAccertamento
	 * @param denominazioneAccertamento
	 * @param codTipoDovuto
	 * @param flagAttivo
	 * @param hasPagination
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public PageDto<AnagraficaUfficioCapitoloAccertamentoDto> findByFilter(Long idEnte,
			String codiceUfficio, String denominazioneUfficio, String codiceCapitolo, String denominazioneCapitolo, String deAnnoEsercizio, String codiceAccertamento, 
			String denominazioneAccertamento, String codTipoDovuto, Boolean flagAttivo, Boolean hasPagination, int page, int pageSize) throws Exception;

	/**
	 * 
	 * @param anagraficaUfficioCapitoloAccertamentoDto
	 * @return
	 * @throws Exception
	 * @author Alessandro Paolillo
	 * @author Stefano De Feo
	 */
	public int aggiornaAnagrafica(AnagraficaUfficioCapitoloAccertamentoDto anagraficaUfficioCapitoloAccertamentoDto) throws Exception;

	/**
	 * Recupera anagrafica tramite id
	 * 
	 * @param id
	 * @return
	 * @author Alessandro Paolillo
	 * @author Stefano De Feo
	 */
	public AnagraficaUfficioCapitoloAccertamentoDto findById(Long id) throws Exception;

	/**
	 * Recupero tutti gli uffici appartenenti all'ente selezionato
	 * 
	 * @param enteId
	 * @return
	 * @throws Exception
	 * @author Alessandro Paolillo
	 * @author Stefano De Feo
	 */
	public List<AnagraficaUfficioCapitoloAccertamento> findDistinctUfficiByEnteId(Long enteId) throws Exception;

	/**
	 * Recupero tutti i capitoli dato l'id ente
	 * 
	 * @param idEnte
	 * @return
	 * @author Alessandro Paolillo
	 */
	public List<AnagraficaUfficioCapitoloAccertamento> findDistinctCapitoliByEnteId(Long idEnte) throws Exception;

	/**
	 * Recupero tutti gli accertamenti dato id ente, codice ufficio e codice capitolo
	 * 
	 * @param idEnte
	 * @param codUff
	 * @param codCap
	 * @return
	 * @author Alessandro Paolillo
	 */
	public List<AnagraficaUfficioCapitoloAccertamento> findDistinctAccertamentiByCodUffcodCap(Long idEnte,
			String codUff, String codCap) throws Exception;

	/**
	 * 
	 * @param idEnte
	 * @param codTipoDovuto
	 * @return
	 * @throws Exception
	 * @author Alessandro Paolillo
	 */
	public String getDeTipoDovutoByIdEnteAndCodTipoDovuto(Long idEnte, String codTipoDovuto) throws Exception;

}