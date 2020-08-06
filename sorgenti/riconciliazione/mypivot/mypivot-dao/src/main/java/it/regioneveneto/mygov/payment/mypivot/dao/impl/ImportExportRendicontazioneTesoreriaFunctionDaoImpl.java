package it.regioneveneto.mygov.payment.mypivot.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import it.regioneveneto.mygov.payment.mypivot.dao.ImportExportRendicontazioneTesoreriaFunctionDao;
import it.regioneveneto.mygov.payment.mypivot.domain.po.FlussoExport;
import it.regioneveneto.mygov.payment.mypivot.domain.po.ImportExportRendicontazioneTesoreria;
import it.regioneveneto.mygov.payment.mypivot.utils.Constants;

@Repository
public class ImportExportRendicontazioneTesoreriaFunctionDaoImpl implements ImportExportRendicontazioneTesoreriaFunctionDao {

	private static final Logger LOG = Logger.getLogger(ImportExportRendicontazioneTesoreriaFunctionDaoImpl.class);

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Integer getCountImportExportRendicontazioneTesoreriaPageListFunction(final String cod_fed_user_id,
			final String cod_ipa_ente, final String codice_iud, final String codice_iuv, final String denominazione_attestante,
			final String identificativo_univoco_riscossione, final String codice_identificativo_univoco_versante,
			final String anagrafica_versante, final String codice_identificativo_univoco_pagatore, final String anagrafica_pagatore,
			final String causale_versamento, final Date dt_data_esecuzione_singolo_pagamento_da,
			final Date dt_data_esecuzione_singolo_pagamento_a, final Date data_esito_singolo_pagamento_da,
			final Date data_esito_singolo_pagamento_a, final String identificativo_flusso_rendicontazione,
			final String identificativo_univoco_regolamento, final Date data_regolamento_da, final Date data_regolamento_a,
			final Date dt_data_contabile_da, final Date dt_data_contabile_a, final Date data_valuta_da, final Date data_valuta_a,
			final Date dt_data_ultimo_aggiornamento_da, final Date dt_data_ultimo_aggiornamento_a, final String cod_tipo_dovuto, final boolean is_cod_tipo_dovuto_present,
			final String importo, final String conto, final String codOr1, final Boolean visualizzaNascosti, final String errorCode) {


		LOG.info("getCountImportExportRendicontazioneTesoreriaPageListFunction");
		
		StringBuffer sqlQuery = new StringBuffer("SELECT * FROM get_count_import_export_rend_tes_function(");
	    if (StringUtils.isNotBlank(cod_fed_user_id))	sqlQuery.append("'" + cod_fed_user_id + "',"); else sqlQuery.append("null,");
		if (StringUtils.isNotBlank(cod_ipa_ente))	sqlQuery.append("'" + cod_ipa_ente + "',"); else sqlQuery.append("null,");
		if (StringUtils.isNotBlank(codice_iud))	sqlQuery.append("'" + codice_iud + "',"); else sqlQuery.append("null,");
		if (StringUtils.isNotBlank(codice_iuv))	sqlQuery.append("'" + codice_iuv + "',"); else sqlQuery.append("null,");
		if (StringUtils.isNotBlank(denominazione_attestante))	sqlQuery.append("'" + denominazione_attestante + "',"); else sqlQuery.append("null,");
		if (StringUtils.isNotBlank(identificativo_univoco_riscossione))	sqlQuery.append("'" + identificativo_univoco_riscossione + "',"); else sqlQuery.append("null,");
		if (StringUtils.isNotBlank(codice_identificativo_univoco_versante))	sqlQuery.append("'" + codice_identificativo_univoco_versante + "',"); else sqlQuery.append("null,");
		if (StringUtils.isNotBlank(anagrafica_versante))	sqlQuery.append("'" + anagrafica_versante + "',"); else sqlQuery.append("null,");
		if (StringUtils.isNotBlank(codice_identificativo_univoco_pagatore))	sqlQuery.append("'" + codice_identificativo_univoco_pagatore + "',"); else sqlQuery.append("null,");			
		if (StringUtils.isNotBlank(anagrafica_pagatore))	sqlQuery.append("'" + anagrafica_pagatore + "',"); else sqlQuery.append("null,");
		if (StringUtils.isNotBlank(causale_versamento))	sqlQuery.append("'" + causale_versamento + "',"); else sqlQuery.append("null,");		
		if (dt_data_esecuzione_singolo_pagamento_da!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(dt_data_esecuzione_singolo_pagamento_da) + "',"); else sqlQuery.append("null,");
		if (dt_data_esecuzione_singolo_pagamento_a!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(dt_data_esecuzione_singolo_pagamento_a) + "',"); else sqlQuery.append("null,");
		if (data_esito_singolo_pagamento_da!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(data_esito_singolo_pagamento_da) + "',"); else sqlQuery.append("null,");
		if (data_esito_singolo_pagamento_a!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(data_esito_singolo_pagamento_a) + "',"); else sqlQuery.append("null,");	
		if (StringUtils.isNotBlank(identificativo_flusso_rendicontazione))	sqlQuery.append("'" + identificativo_flusso_rendicontazione + "',"); else sqlQuery.append("null,");
		if (StringUtils.isNotBlank(identificativo_univoco_regolamento))	sqlQuery.append("'" + identificativo_univoco_regolamento + "',"); else sqlQuery.append("null,");
		if (data_regolamento_da!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(data_regolamento_da) + "',"); else sqlQuery.append("null,");
		if (data_regolamento_a!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(data_regolamento_a) + "',"); else sqlQuery.append("null,");
		if (dt_data_contabile_da!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(dt_data_contabile_da) + "',"); else sqlQuery.append("null,");
		if (dt_data_contabile_a!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(dt_data_contabile_a) + "',"); else sqlQuery.append("null,");
		if (data_valuta_da!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(data_valuta_da) + "',"); else sqlQuery.append("null,");
		if (data_valuta_a!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(data_valuta_a) + "',"); else sqlQuery.append("null,");		
		if (dt_data_ultimo_aggiornamento_da!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(dt_data_ultimo_aggiornamento_da) + "',"); else sqlQuery.append("null,");
		if (dt_data_ultimo_aggiornamento_a!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(dt_data_ultimo_aggiornamento_a) + "',"); else sqlQuery.append("null,");		
		if (StringUtils.isNotBlank(cod_tipo_dovuto))	sqlQuery.append("'" + cod_tipo_dovuto + "',"); else sqlQuery.append("null,");
		sqlQuery.append(is_cod_tipo_dovuto_present + ",");
		if (StringUtils.isNotBlank(importo))	sqlQuery.append("'" + importo + "',"); else sqlQuery.append("null,");		
		if (StringUtils.isNotBlank(conto))	sqlQuery.append("'" + conto + "',"); else sqlQuery.append("null,");
		if (StringUtils.isNotBlank(codOr1))	sqlQuery.append("'" + codOr1 + "',"); else sqlQuery.append("null,");
		if (visualizzaNascosti != null)	sqlQuery.append(visualizzaNascosti + ","); else sqlQuery.append("null,");
		if (StringUtils.isNotBlank(errorCode))	sqlQuery.append("'" + errorCode + "'"); else sqlQuery.append("null");
		sqlQuery.append(")");

		Query query = entityManager.createNativeQuery(sqlQuery.toString());
		BigInteger numberResultList = (BigInteger)query.getSingleResult();
		return numberResultList.intValue();
	}
	
	@Override
	public List<ImportExportRendicontazioneTesoreria> getImportExportRendicontazioneTesoreriaPageListFunction(final String cod_fed_user_id,
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
			final int size) {

		LOG.info("getImportExportRendicontazioneTesoreriaPageListFunction");
		
		StringBuffer sqlQuery = new StringBuffer("SELECT * FROM get_import_export_rend_tes_function(");
		    if (StringUtils.isNotBlank(cod_fed_user_id))	sqlQuery.append("'" + cod_fed_user_id + "',"); else sqlQuery.append("null,");
			if (StringUtils.isNotBlank(cod_ipa_ente))	sqlQuery.append("'" + cod_ipa_ente + "',"); else sqlQuery.append("null,");
			if (StringUtils.isNotBlank(codice_iud))	sqlQuery.append("'" + codice_iud + "',"); else sqlQuery.append("null,");
			if (StringUtils.isNotBlank(codice_iuv))	sqlQuery.append("'" + codice_iuv + "',"); else sqlQuery.append("null,");
			if (StringUtils.isNotBlank(denominazione_attestante))	sqlQuery.append("'" + denominazione_attestante + "',"); else sqlQuery.append("null,");
			if (StringUtils.isNotBlank(identificativo_univoco_riscossione))	sqlQuery.append("'" + identificativo_univoco_riscossione + "',"); else sqlQuery.append("null,");
			if (StringUtils.isNotBlank(codice_identificativo_univoco_versante))	sqlQuery.append("'" + codice_identificativo_univoco_versante + "',"); else sqlQuery.append("null,");
			if (StringUtils.isNotBlank(anagrafica_versante))	sqlQuery.append("'" + anagrafica_versante + "',"); else sqlQuery.append("null,");
			if (StringUtils.isNotBlank(codice_identificativo_univoco_pagatore))	sqlQuery.append("'" + codice_identificativo_univoco_pagatore + "',"); else sqlQuery.append("null,");			
			if (StringUtils.isNotBlank(anagrafica_pagatore))	sqlQuery.append("'" + anagrafica_pagatore + "',"); else sqlQuery.append("null,");
			if (StringUtils.isNotBlank(causale_versamento))	sqlQuery.append("'" + causale_versamento + "',"); else sqlQuery.append("null,");		
			if (dt_data_esecuzione_singolo_pagamento_da!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(dt_data_esecuzione_singolo_pagamento_da) + "',"); else sqlQuery.append("null,");
			if (dt_data_esecuzione_singolo_pagamento_a!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(dt_data_esecuzione_singolo_pagamento_a) + "',"); else sqlQuery.append("null,");
			if (data_esito_singolo_pagamento_da!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(data_esito_singolo_pagamento_da) + "',"); else sqlQuery.append("null,");
			if (data_esito_singolo_pagamento_a!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(data_esito_singolo_pagamento_a) + "',"); else sqlQuery.append("null,");	
			if (StringUtils.isNotBlank(identificativo_flusso_rendicontazione))	sqlQuery.append("'" + identificativo_flusso_rendicontazione + "',"); else sqlQuery.append("null,");
			if (StringUtils.isNotBlank(identificativo_univoco_regolamento))	sqlQuery.append("'" + identificativo_univoco_regolamento + "',"); else sqlQuery.append("null,");
			if (data_regolamento_da!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(data_regolamento_da) + "',"); else sqlQuery.append("null,");
			if (data_regolamento_a!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(data_regolamento_a) + "',"); else sqlQuery.append("null,");
			if (dt_data_contabile_da!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(dt_data_contabile_da) + "',"); else sqlQuery.append("null,");
			if (dt_data_contabile_a!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(dt_data_contabile_a) + "',"); else sqlQuery.append("null,");
			if (data_valuta_da!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(data_valuta_da) + "',"); else sqlQuery.append("null,");
			if (data_valuta_a!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(data_valuta_a) + "',"); else sqlQuery.append("null,");		
			if (dt_data_ultimo_aggiornamento_da!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(dt_data_ultimo_aggiornamento_da) + "',"); else sqlQuery.append("null,");
			if (dt_data_ultimo_aggiornamento_a!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(dt_data_ultimo_aggiornamento_a) + "',"); else sqlQuery.append("null,");		
			if (StringUtils.isNotBlank(cod_tipo_dovuto))	sqlQuery.append("'" + cod_tipo_dovuto + "',"); else sqlQuery.append("null,");
			sqlQuery.append(is_cod_tipo_dovuto_present + ",");
			if (StringUtils.isNotBlank(importo))	sqlQuery.append("'" + importo + "',"); else sqlQuery.append("null,");		
			if (StringUtils.isNotBlank(conto))	sqlQuery.append("'" + conto + "',"); else sqlQuery.append("null,");
			if (StringUtils.isNotBlank(codOr1))	sqlQuery.append("'" + codOr1 + "',"); else sqlQuery.append("null,");
			if (visualizzaNascosti != null)	sqlQuery.append(visualizzaNascosti + ","); else sqlQuery.append("null,");
			if (StringUtils.isNotBlank(errorCode))	sqlQuery.append("'" + errorCode + "',"); else sqlQuery.append("null,");
			sqlQuery.append(page + ",");
			sqlQuery.append(size);
			sqlQuery.append(")");
			
			

		Query query = entityManager.createNativeQuery(sqlQuery.toString(), ImportExportRendicontazioneTesoreria.class);
		List<ImportExportRendicontazioneTesoreria> resultList = query.getResultList();
		return resultList;
		
		
	}
	
	public List<ImportExportRendicontazioneTesoreria> get_import_export_rend_tes_to_send_gepos_function(){
	
		List<ImportExportRendicontazioneTesoreria> result = new ArrayList<ImportExportRendicontazioneTesoreria>();

		StringBuffer sqlQuery = new StringBuffer("SELECT * FROM get_import_export_rend_tes_to_send_gepos_function()");

		Query query = entityManager.createNativeQuery(sqlQuery.toString(), ImportExportRendicontazioneTesoreria.class);
		result = query.getResultList();

		return result;

	}
	
}
