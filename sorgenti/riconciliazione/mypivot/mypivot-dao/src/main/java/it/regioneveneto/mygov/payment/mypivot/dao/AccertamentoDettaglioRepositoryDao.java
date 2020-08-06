package it.regioneveneto.mygov.payment.mypivot.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import it.regioneveneto.mygov.payment.mypivot.domain.po.AccertamentoDettaglio;

/**
 * 
 * @author Marianna Memoli
 *
 */
public interface AccertamentoDettaglioRepositoryDao extends JpaRepository<AccertamentoDettaglio, Long>, JpaSpecificationExecutor<AccertamentoDettaglio> {

	/**
	 * Controlla se in tabella esiste già una relazione per una RT non in stato "ANNULLATO" con questi stessi dati.
	 * 
	 * @param {@link String} codIud
	 * @param {@link String} codIuv
	 * @param {@link String} codIpaEnte
	 * @param {@link String} codStato
	 * @param {@link String} deTipoStato
	 * 
	 * @return {@link AccertamentoDettaglio}
	 */
	@Query("SELECT ad " +
		   "FROM AccertamentoDettaglio ad " + 
		   "WHERE " +
		   	    "ad.codIud = ?1 AND ad.codIuv = ?2 AND ad.codIpaEnte = ?3 AND " + 
				"ad.accertamento.anagraficaStato.deTipoStato = ?4 AND ad.accertamento.anagraficaStato.codStato <> ?5 "
		  )
	public AccertamentoDettaglio exist(String codIud, String codIuv, String codIpaEnte, String deTipoStato, String codStato);
	
	/** 
	 * Restituisce il numero di pagamenti associati all'accertamento dato l'identificativo.
	 * 
	 * @param {@link Long} accertamentoId
	 * 
	 * @return {@link Long}
	 */
	@Query("SELECT count(*) FROM AccertamentoDettaglio ad WHERE ad.accertamento.id = ?1 ")
	public Long countRowByAccertamentoId(Long accertamentoId);

	/**
	 * Cancella l'associazione tra i pagamenti e l'accertamento dato i dettagli della relazione.
	 * 
	 * @param {@link Long}   accertamentoId, Identificativo dell'accertamento
	 * @param {@link String} codIpaEnte, 	 Codice ipa dell'ente
	 * @param {@link String} codTipoDovuto,  Codice tipo dovuto
	 * @param {@link String} codIud, 	 	 Identificativo univoco del dovuto
	 * @param {@link String} codIuv, 	 	 Identificativo univoco versamento
	 *    
	 * @throws Exception
	 * @author Marianna Memoli
	 */
	@Modifying(clearAutomatically = true)
	@Query("DELETE FROM AccertamentoDettaglio a WHERE a.accertamento.id = ?1 AND a.codIpaEnte = ?2 AND a.codTipoDovuto = ?3 AND a.codIud = ?4 AND a.codIuv = ?5")
	public void deleteBy(Long accertamentoId, String codIpaEnte, String codTipoDovuto, String codIud, String codIuv);
	
	@Query("select ad.codIud, ad.codCapitolo, ad.codUfficio, ad.codAccertamento, sum(ad.numImporto) "
			+ "from AccertamentoDettaglio ad "
			+ "where ad.accertamento.id = ?1 "
			+ "group by ad.codIud, ad.codCapitolo, ad.codUfficio, ad.codAccertamento "
			+ "order by ad.codIud, ad.codCapitolo, ad.codUfficio, ad.codAccertamento asc")
	public List<Object[]> getListaDettagliRaggruppataByAccertamentoId(final Long accertamentoId);
}
