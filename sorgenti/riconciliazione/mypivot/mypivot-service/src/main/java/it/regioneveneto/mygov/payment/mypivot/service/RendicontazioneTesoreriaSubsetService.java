package it.regioneveneto.mygov.payment.mypivot.service;

import java.util.Date;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.regioneveneto.mygov.payment.mypivot.domain.dto.PageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.RendicontazioneTesoreriaSubsetDto;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public interface RendicontazioneTesoreriaSubsetService {

	PageDto<RendicontazioneTesoreriaSubsetDto> getRendicontazioneTesoreriaSubsetPageFunction(String codIpaEnte,
			String identificativoUnivocoFlusso, String identificativoUnivocoRegolamento, Date dt_data_regolamento_da,
			Date dt_data_regolamento_a, Date dt_data_contabile_da, Date dt_data_contabile_a, Date dt_data_valuta_da,
			Date dt_data_valuta_a, Date dt_data_ultimo_aggiornamento_da, Date dt_data_ultimo_aggiornamento_a,
			String causaleTesoreria, String importo, String conto, String codOr1, String codTipoDovuto,
			String codFedUserId, Boolean visualizzaNascosti, String classificazioneCompletezza, int page, int pageSize);

}
