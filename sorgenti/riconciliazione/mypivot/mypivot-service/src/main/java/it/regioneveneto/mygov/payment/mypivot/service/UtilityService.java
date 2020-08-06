/**
 * 
 */
package it.regioneveneto.mygov.payment.mypivot.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.regioneveneto.mygov.payment.mypivot.domain.dto.ErrorTypeDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.StatoAccertamentoDto;


@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public interface UtilityService {

	Boolean verificaClassificazione(String codClass, boolean flgPagati, boolean flgTesoreria, String tipoVisualizzazione);
	
	List<ErrorTypeDto> getAllClassificazioni(boolean flgPagati, boolean flgTesoreria, String tipoVisualizzazione);

	List<StatoAccertamentoDto> getStatiAccertamento();

}
