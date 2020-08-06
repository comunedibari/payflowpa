package it.regioneveneto.mygov.payment.mypivot.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Sort;

import it.regioneveneto.mygov.payment.mypivot.domain.po.RendicontazioneSubset;


public interface RendicontazioneSubsetFunctionDao {

	public List<RendicontazioneSubset> getRendicontazioneSubsetFunction(String codice_ipa_ente, String identificativo_flusso_rendicontazione,
			String identificativo_univoco_regolamento, Date data_regolamento_da, Date data_regolamento_a, Date data_ultimo_aggiornamento_da,
			Date data_ultimo_aggiornamento_a, String errorCode, String cod_tipo_dovuto, String cod_fed_user_id, Boolean visualizzaNascostiBool, 
			Integer page, Integer size, Sort sort);
	
	public Integer getCountRendicontazioneSubsetFunction(String codice_ipa_ente, String identificativo_flusso_rendicontazione,
			String identificativo_univoco_regolamento, Date data_regolamento_da, Date data_regolamento_a, Date data_ultimo_aggiornamento_da,
			Date data_ultimo_aggiornamento_a, String errorCode, String cod_tipo_dovuto, String cod_fed_user_id,
			Boolean visualizzaNascostiBool);
}