package it.regioneveneto.mygov.payment.mypivot.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.regioneveneto.mygov.payment.mypivot.domain.to.AnagraficaStatoTO;

/**
 * @author Marianna Memoli
 */
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public interface AnagraficaStatoService {

	/**
	 * Restituisce l'elenco degli stati del tipo passato in ingresso alla funzione.
	 * 
	 * @param {@link String} deTipoStato, codice tipo stato
	 * 
	 * @return {@link List<AnagraficaStatoTO>}
	 * @author Marianna Memoli
	 */
	public List<AnagraficaStatoTO> getAllByTipo(final String deTipoStato);
}
