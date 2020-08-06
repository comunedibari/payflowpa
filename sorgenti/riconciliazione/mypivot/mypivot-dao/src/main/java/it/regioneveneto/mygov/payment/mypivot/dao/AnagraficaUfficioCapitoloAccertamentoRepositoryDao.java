package it.regioneveneto.mygov.payment.mypivot.dao;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import it.regioneveneto.mygov.payment.mypivot.domain.po.AnagraficaUfficioCapitoloAccertamento;
import it.regioneveneto.mygov.payment.mypivot.domain.po.Ente;

/**
 *
 * @author Alessandro Paolillo
 *
 */
public interface AnagraficaUfficioCapitoloAccertamentoRepositoryDao
		extends JpaRepository<AnagraficaUfficioCapitoloAccertamento, Long>,
		JpaSpecificationExecutor<AnagraficaUfficioCapitoloAccertamento> {

	/**
	 * Controlla se in tabella esiste già un record con lo stesso id ente, codice
	 * ufficio, codice capitolo, codice tipo dovuto, codice accertamento e anno
	 * esercizio
	 * 
	 * @param {@link
	 * 			String} cod_ufficio
	 * @param {@link
	 * 			String} cod_capitolo
	 * @param {@link
	 * 			String} cod_tipo_dovuto
	 * @param {@link
	 * 			String} cod_accertamento
	 * @param {@link
	 * 			Long} id ente
	 * @param {@link
	 * 			String} de_anno_esercizio
	 * 
	 * @return {@link AnagraficaUfficioCapitoloAccertamento}
	 * @author Alessandro Paolillo
	 */
	@Query("SELECT auca " + "FROM AnagraficaUfficioCapitoloAccertamento auca " + "WHERE "
			+ "auca.codUfficio = ?1 AND auca.codCapitolo = ?2 AND auca.codTipoDovuto = ?3 AND auca.codAccertamento = ?4 AND auca.ente.id = ?5 AND auca.deAnnoEsercizio = ?6")
	public AnagraficaUfficioCapitoloAccertamento exist(String codUfficio, String codCapitolo, String codTipoDovuto,
			String codAccertamento, Long idEnte, String deAnnoEsercizio);

	/**
	 * Restituisce il campo de_ufficio, passando l'id ente e il cod_ufficio
	 * 
	 * @param {@link
	 * 			Long} id ente
	 * @param {@link
	 * 			String} cod_ufficio
	 * 
	 * @return {@link String}
	 * @author Alessandro Paolillo
	 */
	@Query("SELECT DISTINCT auca.deUfficio " + "FROM AnagraficaUfficioCapitoloAccertamento auca " + "WHERE "
			+ "auca.ente.id = ?1 AND auca.codUfficio = ?2")
	public String getDeUfficioByIdEnteAndCodUfficio(Long idEnte, String codUfficio);

	/**
	 * 
	 * Restituisce il campo de_capitolo, passando l'id ente, il cod_ufficio e il
	 * cod_capitolo
	 * 
	 * @param {@link
	 * 			Long} id ente
	 * @param {@link
	 * 			String} cod_ufficio
	 * @param {@link
	 * 			String} cod_capitolo
	 * 
	 * @return {@link String}
	 * @author Alessandro Paolillo
	 */
	@Query("SELECT DISTINCT auca.deCapitolo " + "FROM AnagraficaUfficioCapitoloAccertamento auca " + "WHERE "
			+ "auca.ente.id = ?1 AND auca.codUfficio = ?2 AND auca.codCapitolo = ?3")
	public String getDeCapitoloByIdEnteAndCodUfficioAndCodCapitolo(Long idEnte, String codUfficio, String codCapitolo);

	/**
	 * 
	 * Restituisce il campo de_accertamento, passando l'id ente, il cod_ufficio, il
	 * cod_capitolo e il cod_accertamento
	 * 
	 * @param {@link
	 * 			Long} id ente
	 * @param {@link
	 * 			String} cod_ufficio
	 * @param {@link
	 * 			String} cod_capitolo
	 * @param {@link
	 * 			String} cod_accertamento
	 * 
	 * @return {@link String}
	 * @author Alessandro Paolillo
	 */
	@Query("SELECT DISTINCT auca.deAccertamento " + "FROM AnagraficaUfficioCapitoloAccertamento auca " + "WHERE "
			+ "auca.ente.id = ?1 AND auca.codUfficio = ?2 AND auca.codCapitolo = ?3 AND auca.codAccertamento = ?4")
	public String getDeAccertamentoByIdEnteAndCodUfficioAndCodCapitoloAndCodAccertamento(Long idEnte, String codUfficio,
			String codCapitolo, String codAccertamento);

	/**
	 * 
	 * @param codUfficio
	 * @param flgAttivo
	 * @return
	 * @author Alessandro Paolillo
	 */
	@Modifying
	@Query("update AnagraficaUfficioCapitoloAccertamento "
			+ "SET flg_attivo = ?2, dt_ultima_modifica = current_timestamp "
			+ "WHERE cod_ufficio = ?1 AND mygov_ente_id = ?3")
	public int updateFlgAttivo(String codUfficio, boolean flgAttivo, Long idEnte);

	/**
	 * 
	 * @param codCapitolo
	 * @param deAnnoEsercizio
	 * @return
	 * @author Alessandro Paolillo
	 */
	@Modifying
	@Query("update AnagraficaUfficioCapitoloAccertamento "
			+ "SET de_anno_esercizio = ?2, dt_ultima_modifica = current_timestamp "
			+ "WHERE cod_capitolo = ?1 AND mygov_ente_id = ?3")
	public int updateAnno(String codCapitolo, String deAnnoEsercizio, Long idEnte);

	/**
	 * 
	 * @param codCapitolo
	 * @param deCapitolo
	 * @param id
	 * @return
	 * @author Alessandro Paolillo
	 */
	@Modifying
	@Query("update AnagraficaUfficioCapitoloAccertamento "
			+ "SET de_capitolo = ?2, dt_ultima_modifica = current_timestamp "
			+ "WHERE cod_capitolo = ?1 AND mygov_ente_id = ?3")
	public int updateDeCapitolo(String codCapitolo, String deCapitolo, Long id);

	/**
	 * 
	 * @param codUfficio
	 * @param deUfficio
	 * @param id
	 * @return
	 * @author Alessandro Paolillo
	 */
	@Modifying
	@Query("update AnagraficaUfficioCapitoloAccertamento "
			+ "SET de_ufficio = ?2, dt_ultima_modifica = current_timestamp "
			+ "WHERE cod_ufficio = ?1 AND mygov_ente_id = ?3")
	public int updateDeUfficio(String codUfficio, String deUfficio, Long id);

	/**
	 * 
	 * @param codAccertamento
	 * @param deAccertamento
	 * @param id
	 * @return
	 * @author Alessandro Paolillo
	 */
	@Modifying
	@Query("update AnagraficaUfficioCapitoloAccertamento "
			+ "SET de_accertamento = ?2, dt_ultima_modifica = current_timestamp "
			+ "WHERE cod_accertamento = ?1 AND mygov_ente_id = ?3")
	public int updateDeAccertamento(String codAccertamento, String deAccertamento, Long id);

	@Modifying
	@Query("update AnagraficaUfficioCapitoloAccertamento "
			+ "SET flg_attivo = ?2, dt_ultima_modifica = current_timestamp " + "WHERE cod_ufficio = ?1")
	public int updateFlgAttivo(String codUfficio, boolean flgAttivo);
	// /**
	// * Controlla se in tabella esiste già un record con lo stesso codice ufficio,
	// codice capitolo, codice tipo dovuto e codice accertamento.
	// *
	// * @param {@link String} cod_ufficio
	// * @param {@link String} cod_capitolo
	// * @param {@link String} cod_tipo_dovuto
	// * @param {@link String} cod_accertamento
	// * @param {@link Long} id ente
	// *
	// * @return {@link AnagraficaUfficioCapitoloAccertamento}
	// */
	// @Modifying
	// @Query("DELETE from AnagraficaUfficioCapitoloAccertamento auca" +
	// "WHERE "+
	// "auca.codUfficio = ?1 AND auca.codCapitolo = ?2 AND auca.codTipoDovuto = ?3
	// AND auca.codAccertamento = ?4 AND auca.ente.id = ?5")
	// public AnagraficaUfficioCapitoloAccertamento delete(String codUfficio, String
	// codCapitolo, String codTipoDovuto, String codAccertamento, Long idEnte);
	//
	// /**
	// * Controlla se in tabella esiste già un record con lo stesso codice ufficio,
	// codice capitolo, codice tipo dovuto e codice accertamento.
	// *
	// * @param {@link String} cod_ufficio
	// * @param {@link String} cod_capitolo
	// * @param {@link String} cod_tipo_dovuto
	// * @param {@link Long} id ente
	// *
	// * @return {@link AnagraficaUfficioCapitoloAccertamento}
	// */
	// @Modifying
	// @Query("DELETE from AnagraficaUfficioCapitoloAccertamento auca" +
	// "WHERE "+
	// "auca.codUfficio = ?1 AND auca.codCapitolo = ?2 AND auca.codTipoDovuto = ?3
	// AND auca.ente.id = ?4")
	// public AnagraficaUfficioCapitoloAccertamento delete(String codUfficio, String
	// codCapitolo, String codTipoDovuto, Long idEnte);

	@Modifying
	@Query("UPDATE AnagraficaUfficioCapitoloAccertamento a SET a.codUfficio = ?2, a.deUfficio = ?3, a.codCapitolo = ?4, a.deCapitolo = ?5, a.deAnnoEsercizio = ?6, a.codAccertamento = ?7, a.deAccertamento = ?8, a.codTipoDovuto = ?9, a.flgAttivo = ?10, a.dtUltimaModifica = ?11 WHERE a.id = ?1")
	public int aggiornaAnagrafica(final Long id, final String codUfficio, final String deUfficio,
			final String codCapitolo, final String deCapitolo, final String deAnnoEsercizio,
			final String codAccertamento, final String deAccertamento, final String codTipoDovuto,
			final boolean flgAttivo, final Date dataUltimaModifica);
	
	@Modifying
	@Query("DELETE AnagraficaUfficioCapitoloAccertamento a WHERE a.id = ?1")
	public int deleteAnagrafica(final Long id);

	
	public AnagraficaUfficioCapitoloAccertamento findById(Long id);
}