/**
 * 
 */
package it.regioneveneto.mygov.payment.mypivot.service;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.regioneveneto.mygov.payment.mypivot.domain.dto.EnteTipoDovutoDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.PageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.po.EnteTipoDovuto;
import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTipoDovutoTO;

/**
 * @author giorgiovallini
 *
 */
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public interface EnteTipoDovutoService {

	List<EnteTipoDovuto> getByCodIpaEnte(final String codIpaEnte);

	List<EnteTipoDovutoTO> getByCodIpaEnteAndCodTipoDovutoList(final String codIpaEnte, final Collection<String> listaCodiciTipoDovuto);

	EnteTipoDovuto getByCodIpaEnteAndCodTipoDovuto(final String codIpaEnte, final String codiceTipoDovuto);
	
	public EnteTipoDovutoTO getById(long id);
	
	public EnteTipoDovuto createEnteTipoDovuto(EnteTipoDovutoDto enteTipoDovutoDto, Boolean flagGlobal);
	
	public EnteTipoDovuto updateEnteTipoDovuto(EnteTipoDovutoDto enteTipoDovutoDto);
	
	public PageDto<EnteTipoDovutoTO> getByCodIpaEntePage(Pageable pageable, String codIpaEnte);
	
	public void deleteEnteTipoDovuto(long idEnteTipoDovuto);
}
