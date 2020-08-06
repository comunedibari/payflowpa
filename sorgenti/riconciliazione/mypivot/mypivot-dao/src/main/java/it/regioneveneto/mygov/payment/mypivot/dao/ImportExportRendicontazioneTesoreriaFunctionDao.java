package it.regioneveneto.mygov.payment.mypivot.dao;

import java.util.Date;
import java.util.List;

import it.regioneveneto.mygov.payment.mypivot.domain.po.ImportExportRendicontazioneTesoreria;

public interface ImportExportRendicontazioneTesoreriaFunctionDao {
	
	
	List<ImportExportRendicontazioneTesoreria> getImportExportRendicontazioneTesoreriaPageListFunction(final String cod_fed_user_id,
			final String cod_ipa_ente, final String codice_iud, final String codice_iuv, final String denominazione_attestante,
			final String identificativo_univoco_riscossione, final String codice_identificativo_univoco_versante,
			final String anagrafica_versante, final String codice_identificativo_univoco_pagatore, final String anagrafica_pagatore,
			final String causale_versamento, final Date dt_data_esecuzione_singolo_pagamento_da,
			final Date dt_data_esecuzione_singolo_pagamento_a, final Date data_esito_singolo_pagamento_da,
			final Date data_esito_singolo_pagamento_a, final String identificativo_flusso_rendicontazione,
			final String identificativo_univoco_regolamento, final Date data_regolamento_da, final Date data_regolamento_a,
			final Date dt_data_contabile_da, final Date dt_data_contabile_a, final Date data_valuta_da, final Date data_valuta_a,
			final Date dt_data_ultimo_aggiornamento_da, final Date dt_data_ultimo_aggiornamento_a, final String cod_tipo_dovuto, final boolean is_cod_tipo_dovuto_present,
			final String importo, final String conto, final String codOr1, final Boolean visualizzaNascosti, final String errorCode, final int page,
			final int size);
	
	Integer getCountImportExportRendicontazioneTesoreriaPageListFunction(final String cod_fed_user_id,
			final String cod_ipa_ente, final String codice_iud, final String codice_iuv, final String denominazione_attestante,
			final String identificativo_univoco_riscossione, final String codice_identificativo_univoco_versante,
			final String anagrafica_versante, final String codice_identificativo_univoco_pagatore, final String anagrafica_pagatore,
			final String causale_versamento, final Date dt_data_esecuzione_singolo_pagamento_da,
			final Date dt_data_esecuzione_singolo_pagamento_a, final Date data_esito_singolo_pagamento_da,
			final Date data_esito_singolo_pagamento_a, final String identificativo_flusso_rendicontazione,
			final String identificativo_univoco_regolamento, final Date data_regolamento_da, final Date data_regolamento_a,
			final Date dt_data_contabile_da, final Date dt_data_contabile_a, final Date data_valuta_da, final Date data_valuta_a,
			final Date dt_data_ultimo_aggiornamento_da, final Date dt_data_ultimo_aggiornamento_a, final String cod_tipo_dovuto, final boolean is_cod_tipo_dovuto_present,
			final String importo, final String conto, final String codOr1, final Boolean visualizzaNascosti, final String errorCode);

	List<ImportExportRendicontazioneTesoreria> get_import_export_rend_tes_to_send_gepos_function();
}
