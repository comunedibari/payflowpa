package it.regioneveneto.mygov.payment.mypivot.dao;

import java.util.Date;
import java.util.List;

import it.regioneveneto.mygov.payment.mypivot.domain.po.RendicontazioneTesoreriaSubset;

public interface RendicontazioneTesoreriaSubsetFunctionDao {

	List<RendicontazioneTesoreriaSubset> getRendicontazioneTesoreriaPageListFunction(final String codice_ipa_ente,
			final String identificativo_flusso_rendicontazione, final String identificativo_univoco_regolamento,
			final Date dt_data_regolamento_da, final Date dt_data_regolamento_a, final Date dt_data_contabile_da,
			final Date dt_data_contabile_a, final Date dt_data_valuta_da, final Date dt_data_valuta_a,
			final Date dt_data_ultimo_aggiornamento_da, final Date dt_data_ultimo_aggiornamento_a,
			final String de_causale_t, final String importo, final String conto, final String codor1,
			final String cod_tipo_dovuto, final Boolean is_cod_tipo_dovuto_present, final String cod_fed_user_id,
			final Boolean flagnascosto, final String classificazione_completezza, final int page, final int size);

	int getCountRendicontazioneTesoreriaPageListFunction(final String codice_ipa_ente,
			final String identificativo_flusso_rendicontazione, final String identificativo_univoco_regolamento,
			final Date dt_data_regolamento_da, final Date dt_data_regolamento_a, final Date dt_data_contabile_da,
			final Date dt_data_contabile_a, final Date dt_data_valuta_da, final Date dt_data_valuta_a,
			final Date dt_data_ultimo_aggiornamento_da, final Date dt_data_ultimo_aggiornamento_a,
			final String de_causale_t, final String importo, final String conto, final String codor1,
			final String cod_tipo_dovuto, final Boolean is_cod_tipo_dovuto_present, final String cod_fed_user_id,
			final Boolean flagnascosto, final String classificazione_completezza);
}
