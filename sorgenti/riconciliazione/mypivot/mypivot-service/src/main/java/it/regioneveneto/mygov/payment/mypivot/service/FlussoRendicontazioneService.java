package it.regioneveneto.mygov.payment.mypivot.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.regioneveneto.mygov.payment.mypivot.domain.dto.FlussoRendicontazioneDistinctDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.FlussoRendicontazioneDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.FlussoRicevutaDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.PageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.flussorendicontazione.DettaglioFlussoRendicontazioneDto;
import it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoRendicontazioneTO;
import it.regioneveneto.mygov.payment.mypivot.service.exception.RtNonInRendicontazioneException;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public interface FlussoRendicontazioneService {

	PageDto<DettaglioFlussoRendicontazioneDto> findDettagliFlussoRendicontazione(final String codiceIpaEnte,
			final String codIuf, final Collection<String> codTipoDovutoValidi, int page, int pageSize);

	PageDto<DettaglioFlussoRendicontazioneDto> findDettagliFlussoRendicontazione(final String codiceIpaEnte,
			final String codIuf, final Collection<String> codTipoDovutoValidi, Pageable pageable, Sort sort);

	List<FlussoRendicontazioneTO> findByCodIpaEnteAndIUF(final String codIpaEnte, final String iuf);

	PageDto<FlussoRendicontazioneDistinctDto> getFlussoRendicontazionePage(Long mygovEnteId, Date dt_data_regolamento_da,
			Date dt_data_regolamento_a, String iuf, String identificativoUnivocoRegolamento, int page,
			int pageSize);

	FlussoRendicontazioneDto getFlussoRendicontazioneDto(String codIpaEnte, String iuf);
	
	PageDto<FlussoRicevutaDto> getFlussoRendicontazionePageByIUF(String codIpaEnte, String codFedUserId,
			Date dt_data_esito_singolo_pagamento_da, Date dt_data_esito_singolo_pagamento_a, String iud, String iuv,
			String denominazioneAttestante, String identificativoUnivocoRiscossione,
			String codiceIdentificativoUnivocoPagatore, String anagraficaPagatore,
			String codiceIdentificativoUnivocoVersante, String anagraficaVersante, String codTipoDovuto, String iuf,
			int page, int pageSize, Sort sort) throws RtNonInRendicontazioneException;

	PageDto<FlussoRicevutaDto> getFlussoRendicontazionePageByIUF(String codIpa, String codFedUserId,
			Date dt_data_esito_singolo_pagamento_da, Date dt_data_esito_singolo_pagamento_a, String iud, String iuv,
			String denominazioneAttestante, String identificativoUnivocoRiscossione,
			String codiceIdentificativoUnivocoPagatore, String anagraficaPagatore,
			String codiceIdentificativoUnivocoVersante, String anagraficaVersante, String codTipoDovuto, String iuf, 
			Pageable pageable);
	
}
