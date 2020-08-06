package it.regioneveneto.mygov.payment.mypivot.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.regioneveneto.mygov.payment.mypivot.domain.dto.FlussoRicevutaDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.PageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.po.FlussoExportId;
import it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO;
import it.regioneveneto.mygov.payment.mypivot.service.exception.RtNonInRendicontazioneException;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public interface FlussoExportService {
	List<FlussoExportTO> findAllByIdSet(Iterable<FlussoExportId> flussoExportIds);

	FlussoExportTO findByCodIpaEnteIUVAndIndiceDatiSingoloPagamento(final String codIpaEnte, final String iuv,
			final int indiceDatiSingoloPagamento);

	List<FlussoExportTO> findByCodIpaEnteIUV(final String codIpaEnte, final String iuv);

	PageDto<FlussoRicevutaDto> getFlussoExportPage(String codIpaEnte, String codFedUserId,
			Date dt_data_esito_singolo_pagamento_da, Date dt_data_esito_singolo_pagamento_a, String iud, String iuv,
			String denominazioneAttestante, String identificativoUnivocoRiscossione,
			String codiceIdentificativoUnivocoPagatore, String anagraficaPagatore,
			String codiceIdentificativoUnivocoVersante, String anagraficaVersante, String codTipoDovuto, int page,
			int pageSize, Sort sort);

	PageDto<FlussoRicevutaDto> getFlussoExportPageByIUF(String codIpaEnte, String codFedUserId,
			Date dt_data_esito_singolo_pagamento_da, Date dt_data_esito_singolo_pagamento_a, String iud, String iuv,
			String denominazioneAttestante, String identificativoUnivocoRiscossione,
			String codiceIdentificativoUnivocoPagatore, String anagraficaPagatore,
			String codiceIdentificativoUnivocoVersante, String anagraficaVersante, String codTipoDovuto, String iuf,
			int page, int pageSize, Sort sort) throws RtNonInRendicontazioneException;

	PageDto<FlussoRicevutaDto> getFlussoExportPage(String codIpaEnte, String codFedUserId,
			Date dt_data_esito_singolo_pagamento_da, Date dt_data_esito_singolo_pagamento_a, List<String> iud,
			String iuv, String denominazioneAttestante, String identificativoUnivocoRiscossione,
			String codiceIdentificativoUnivocoPagatore, String anagraficaPagatore,
			String codiceIdentificativoUnivocoVersante, String anagraficaVersante, String codTipoDovuto, int page,
			int pageSize, Sort sort);

	PageDto<FlussoRicevutaDto> getFlussoExportPage(Pageable pageable, String codIpaEnte, String codFedUserId,
			Date dt_data_esito_singolo_pagamento_da, Date dt_data_esito_singolo_pagamento_a, String iud, String iuv,
			String denominazioneAttestante, String identificativoUnivocoRiscossione,
			String codiceIdentificativoUnivocoPagatore, String anagraficaPagatore,
			String codiceIdentificativoUnivocoVersante, String anagraficaVersante, String codTipoDovuto);
	
	PageDto<FlussoRicevutaDto> getFlussoExportPage(Pageable pageable, String codIpaEnte, String codFedUserId,
			Date dt_data_esito_singolo_pagamento_da, Date dt_data_esito_singolo_pagamento_a, List<String> iud, String iuv,
			String denominazioneAttestante, String identificativoUnivocoRiscossione,
			String codiceIdentificativoUnivocoPagatore, String anagraficaPagatore,
			String codiceIdentificativoUnivocoVersante, String anagraficaVersante, String codTipoDovuto);
	
}
