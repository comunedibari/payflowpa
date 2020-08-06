package it.regioneveneto.mygov.payment.mypivot.service;

import it.regioneveneto.mygov.payment.mypivot.domain.dto.PageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.RendicontazioneSubsetDto;

import java.util.Date;

import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public interface RendicontazioneSubsetService {

	PageDto<RendicontazioneSubsetDto> getRendicontazioneSubsetPage(String codice_ipa_ente, String identificativo_flusso_rendicontazione,
			String identificativo_univoco_regolamento, Date data_regolamento_da, Date data_regolamento_a, Date dt_data_ultimo_aggiornamento_da,
			Date dt_data_ultimo_aggiornamento_a, String errorCode, String cod_tipo_dovuto, String cod_fed_user_id, Boolean visualizzaNascosti, int page, int size, Sort sort);

	public PageDto<RendicontazioneSubsetDto> getRendicontazioneSubsetPageFunction(String codice_ipa_ente, String identificativo_flusso_rendicontazione,
			String identificativo_univoco_regolamento, Date data_regolamento_da, Date data_regolamento_a, Date data_ultimo_aggiornamento_da,
			Date data_ultimo_aggiornamento_a, String errorCode, String cod_tipo_dovuto, String cod_fed_user_id, Boolean visualizzaNascostiBool, int page, int size, Sort sort);
}
