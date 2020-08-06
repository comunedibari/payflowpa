package it.regioneveneto.mygov.payment.mypivot.dao;

import java.util.Date;

import it.regioneveneto.mygov.payment.mypivot.domain.dto.FlussoRendicontazioneDistinctDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.PageDto;

public interface FlussoRendicontazioneFunctionDao {

	PageDto<FlussoRendicontazioneDistinctDto> callFlussoRendicontazioneFunction(final Long mygovEnteId,
			final Date dt_data_regolamento_da, final Date dt_data_regolamento_a, final String iuf,
			final String identificativoUnivocoRegolamento, final int page, final int pageSize);

}