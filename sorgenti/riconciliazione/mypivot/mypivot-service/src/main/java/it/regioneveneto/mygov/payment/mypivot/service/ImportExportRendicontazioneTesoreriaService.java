package it.regioneveneto.mygov.payment.mypivot.service;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.regioneveneto.mygov.payment.mypivot.domain.dto.ExportCSVDtoIFace;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.PageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.VisualizzaCompletaDto;
import it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO;
import it.regioneveneto.mygov.payment.mypivot.service.exception.MyPivotServiceException;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public interface ImportExportRendicontazioneTesoreriaService {

	PageDto<VisualizzaCompletaDto> getImportExportRendicontazioneTesoreriaPage(String cod_fed_user_id,
			String cod_ipa_ente, String codice_iud, String codice_iuv, String denominazione_attestante,
			String identificativo_univoco_riscossione, String codice_identificativo_univoco_versante,
			String anagrafica_versante, String codice_identificativo_univoco_pagatore, String anagrafica_pagatore,
			String causale_versamento, Date dt_data_esecuzione_singolo_pagamento_da,
			Date dt_data_esecuzione_singolo_pagamento_a, Date data_esito_singolo_pagamento_da,
			Date data_esito_singolo_pagamento_a, String identificativo_flusso_rendicontazione,
			String identificativo_univoco_regolamento, Date data_regolamento_da, Date data_regolamento_a,
			Date dt_data_contabile_da, Date dt_data_contabile_a, Date data_valuta_da, Date data_valuta_a,
			Date dt_data_ultimo_aggiornamento_da, Date dt_data_ultimo_aggiornamento_a, String cod_tipo_dovuto,
			String importo, String conto, String codOr1, String visualizzaNascosti, String errorCode, int page,
			int size, Sort sort);

	PageDto<VisualizzaCompletaDto> getImportExportRendicontazioneTesoreriaPageFunction(String cod_fed_user_id,
			String cod_ipa_ente, String codice_iud, String codice_iuv, String denominazione_attestante,
			String identificativo_univoco_riscossione, String codice_identificativo_univoco_versante,
			String anagrafica_versante, String codice_identificativo_univoco_pagatore, String anagrafica_pagatore,
			String causale_versamento, Date dt_data_esecuzione_singolo_pagamento_da,
			Date dt_data_esecuzione_singolo_pagamento_a, Date data_esito_singolo_pagamento_da,
			Date data_esito_singolo_pagamento_a, String identificativo_flusso_rendicontazione,
			String identificativo_univoco_regolamento, Date data_regolamento_da, Date data_regolamento_a,
			Date dt_data_contabile_da, Date dt_data_contabile_a, Date data_valuta_da, Date data_valuta_a,
			Date dt_data_ultimo_aggiornamento_da, Date dt_data_ultimo_aggiornamento_a, String cod_tipo_dovuto,
			String importo, String conto, String codOr1, Boolean visualizzaNascosti, String errorCode, int page,
			int size);

	List<ExportCSVDtoIFace> getImportExportRendicontazioneTesoreria(String cod_fed_user_id, String cod_ipa_ente,
			String codice_iud, String codice_iuv, String denominazione_attestante,
			String identificativo_univoco_riscossione, String codice_identificativo_univoco_versante,
			String anagrafica_versante, String codice_identificativo_univoco_pagatore, String anagrafica_pagatore,
			String causale_versamento, Date dt_data_esecuzione_singolo_pagamento_da,
			Date dt_data_esecuzione_singolo_pagamento_a, Date data_esito_singolo_pagamento_da,
			Date data_esito_singolo_pagamento_a, String identificativo_flusso_rendicontazione,
			String identificativo_univoco_regolamento, Date data_regolamento_da, Date data_regolamento_a,
			Date dt_data_contabile_da, Date dt_data_contabile_a, Date data_valuta_da, Date data_valuta_a,
			String cod_tipo_dovuto, String importo, String conto, String codOr1, String visualizzaNascosti,
			String errorCode, Sort sort) throws IllegalAccessException, InvocationTargetException;

	ImportExportRendicontazioneTesoreriaTO getByEnteAndClassificazioneCompletezzaAndCodIuvAndCodIufAndCodIud(
			String codiceIpaEnte, String classificazioneCompletezza, String codIuv, String codIuf, String codIud)
			throws MyPivotServiceException;

	PageDto<ImportExportRendicontazioneTesoreriaTO> findByEnteAndClassificazioneCompletezzaAndCodIufAndPagination(
			String codiceIpaEnte, String classificazioneCompletezza, String codIuf, int page, int pageSize)
			throws MyPivotServiceException;

	ImportExportRendicontazioneTesoreriaTO getByEnteAndClassificazioneCompletezzaAndCodIuf(String codiceIpaEnte,
			String classificazioneCompletezza, String codIuf) throws MyPivotServiceException;
}
