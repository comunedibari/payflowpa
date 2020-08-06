package it.regioneveneto.mygov.payment.mypivot.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.regioneveneto.mygov.payment.mypivot.domain.dto.AggiornaSegnalazioneDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.AggiornaSegnalazioneResultDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.PageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.SegnalazioneKeyDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.storicosegnalazioni.SegnalazioniFilterDto;
import it.regioneveneto.mygov.payment.mypivot.domain.to.SegnalazioneTO;
import it.regioneveneto.mygov.payment.mypivot.service.exception.MyPivotServiceException;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public interface SegnalazioneService {

	PageDto<SegnalazioneTO> findPageByFilter(String codIpaEnte, SegnalazioniFilterDto filter);
	
	PageDto<SegnalazioneTO> findPageByFilter(String codIpaEnte, SegnalazioniFilterDto filter, Pageable pageable);

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	AggiornaSegnalazioneResultDto aggiornaSegnalazione(AggiornaSegnalazioneDto segnalazione) throws MyPivotServiceException;

	SegnalazioneTO findAttiveByKey(final SegnalazioneKeyDto segnalazioneKey) throws MyPivotServiceException;

	Map<SegnalazioneKeyDto, SegnalazioneTO> findAttiveByKeys(final List<SegnalazioneKeyDto> segnalazioneKey)
			throws MyPivotServiceException;

	List<SegnalazioneTO> findByKey(final SegnalazioneKeyDto segnalazioneKey) throws MyPivotServiceException;
}
