package it.regioneveneto.mygov.payment.mypivot.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import it.regioneveneto.mygov.payment.mypivot.domain.po.AnagraficaStato;

/**
 * @author Marianna Memoli
 */
public interface AnagraficaStatoDao extends JpaRepository<AnagraficaStato, Long>{

	AnagraficaStato findByCodStatoAndDeTipoStato(String codStato, String deTipoStato);

	/**
	 * Restituisce l'elenco degli stati del tipo passato in ingresso alla funzione.
	 * 
	 * @param {@link String} deTipoStato, codice tipo stato
	 * 
	 * @return {@link List<AnagraficaStato>}
	 * @author Marianna Memoli
	 */
	@Query("SELECT ast FROM AnagraficaStato ast WHERE ast.deTipoStato = ?1 ORDER BY ast.deTipoStato ASC")
	public List<AnagraficaStato> getAllByTipo(final String deTipoStato);

}
