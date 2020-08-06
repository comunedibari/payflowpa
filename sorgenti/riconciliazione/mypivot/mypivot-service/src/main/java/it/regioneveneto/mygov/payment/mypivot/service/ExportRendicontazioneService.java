/**
 * 
 */
package it.regioneveneto.mygov.payment.mypivot.service;

import it.regioneveneto.mygov.payment.mypivot.domain.dto.ExportCSVDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.PageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.VisualizzaDto;

import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Giorgio Vallini
 *
 */
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public interface ExportRendicontazioneService {

	PageDto<VisualizzaDto> getExportRendicontazionePage(String cod_ipa_ente, String codice_iud, String codice_iuv, String denominazione_attestante,
			String identificativo_univoco_riscossione, String codice_identificativo_univoco_versante, String anagrafica_versante,
			String codice_identificativo_univoco_pagatore, String anagrafica_pagatore, String causale_versamento, Date data_esito_singolo_pagamento_da,
			Date data_esito_singolo_pagamento_a, String identificativo_flusso_rendicontazione, String identificativo_univoco_regolamento,
			Date data_regolamento_da, Date data_regolamento_a, String cod_tipo_dovuto, int page, int size, Sort sort);

	List<ExportCSVDto> getExportRendicontazione(String cod_ipa_ente, String codice_iud, String codice_iuv, String denominazione_attestante,
			String identificativo_univoco_riscossione, String codice_identificativo_univoco_versante, String anagrafica_versante,
			String codice_identificativo_univoco_pagatore, String anagrafica_pagatore, String causale_versamento, Date data_esito_singolo_pagamento_da,
			Date data_esito_singolo_pagamento_a, String identificativo_flusso_rendicontazione, String identificativo_univoco_regolamento,
			Date data_regolamento_da, Date data_regolamento_a, String cod_tipo_dovuto, Sort sort);

}
