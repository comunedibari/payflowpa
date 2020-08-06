/**
 * 
 */
package it.regioneveneto.mygov.payment.mypivot.service;

import it.regioneveneto.mygov.payment.mypivot.domain.dto.EnteDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.PageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.po.Ente;
import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Giorgio Vallini
 *
 */
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public interface EnteService {

	Boolean verificaPassword(final String codIpaEnte, final String password);
	
	PageDto<EnteTO> getEntePage(final String fullTextSearch, final int page,
			final int size, final Direction direction, final String properties);

	List<EnteTO> getAllEnti();

	EnteTO getByCodIpaEnte(String codIpaEnte);
	
	PageDto<EnteTO> getEnteListPage(Pageable pageable);

	String getLogoEnteByCodIpaEnte(String codIpaEnte);
	
	Ente createEnte(EnteDto enteDto);
	
	Ente updateEnte(EnteDto enteDto);
	
	void deleteEnte(String codIpa);
}
