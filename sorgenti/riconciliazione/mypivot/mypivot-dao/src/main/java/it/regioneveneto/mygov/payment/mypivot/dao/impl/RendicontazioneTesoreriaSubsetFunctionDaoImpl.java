package it.regioneveneto.mygov.payment.mypivot.dao.impl;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import it.regioneveneto.mygov.payment.mypivot.dao.RendicontazioneTesoreriaSubsetFunctionDao;
import it.regioneveneto.mygov.payment.mypivot.domain.po.RendicontazioneTesoreriaSubset;
import it.regioneveneto.mygov.payment.mypivot.utils.Constants;

@Repository
public class RendicontazioneTesoreriaSubsetFunctionDaoImpl implements RendicontazioneTesoreriaSubsetFunctionDao {
	
	private static final Logger LOG = Logger.getLogger(RendicontazioneTesoreriaSubsetFunctionDaoImpl.class);
	
	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<RendicontazioneTesoreriaSubset> getRendicontazioneTesoreriaPageListFunction(String codice_ipa_ente,
			String identificativo_flusso_rendicontazione, String identificativo_univoco_regolamento,
			Date dt_data_regolamento_da, Date dt_data_regolamento_a, Date dt_data_contabile_da,
			Date dt_data_contabile_a, Date dt_data_valuta_da, Date dt_data_valuta_a,
			Date dt_data_ultimo_aggiornamento_da, Date dt_data_ultimo_aggiornamento_a, String de_causale_t,
			String importo, String conto, String codor1, String cod_tipo_dovuto, Boolean is_cod_tipo_dovuto_present,
			String cod_fed_user_id, Boolean flagnascosto, String classificazione_completezza, int page, int size) {
		
		LOG.info("getRendicontazioneTesoreriaPageListFunction");
		
		StringBuffer sqlQuery = new StringBuffer("SELECT * FROM get_rendicontazione_tesoreria_subset_function(");
		if (StringUtils.isNotBlank(codice_ipa_ente)) sqlQuery.append("'" + codice_ipa_ente + "',"); else sqlQuery.append("null,");
		if (StringUtils.isNotBlank(identificativo_flusso_rendicontazione)) sqlQuery.append("'" + identificativo_flusso_rendicontazione + "',"); else sqlQuery.append("null,");
		if (StringUtils.isNotBlank(identificativo_univoco_regolamento)) sqlQuery.append("'" + identificativo_univoco_regolamento + "',"); else sqlQuery.append("null,");
		if (dt_data_regolamento_da!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(dt_data_regolamento_da) + "',"); else sqlQuery.append("null,");
		if (dt_data_regolamento_a!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(dt_data_regolamento_a) + "',"); else sqlQuery.append("null,");
		if (dt_data_contabile_da!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(dt_data_contabile_da) + "',"); else sqlQuery.append("null,");
		if (dt_data_contabile_a!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(dt_data_contabile_a) + "',"); else sqlQuery.append("null,");
		if (dt_data_valuta_da!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(dt_data_valuta_da) + "',"); else sqlQuery.append("null,");
		if (dt_data_valuta_a!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(dt_data_valuta_a) + "',"); else sqlQuery.append("null,");
		if (dt_data_ultimo_aggiornamento_da!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(dt_data_ultimo_aggiornamento_da) + "',"); else sqlQuery.append("null,");
		if (dt_data_ultimo_aggiornamento_a!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(dt_data_ultimo_aggiornamento_a) + "',"); else sqlQuery.append("null,");
		if (StringUtils.isNotBlank(de_causale_t)) sqlQuery.append("'" + de_causale_t + "',"); else sqlQuery.append("null,");
		if (StringUtils.isNotBlank(importo)) sqlQuery.append("'" + importo + "',"); else sqlQuery.append("null,");
		if (StringUtils.isNotBlank(conto)) sqlQuery.append("'" + conto + "',"); else sqlQuery.append("null,");
		if (StringUtils.isNotBlank(codor1)) sqlQuery.append("'" + codor1 + "',"); else sqlQuery.append("null,");
		if (StringUtils.isNotBlank(cod_tipo_dovuto)) sqlQuery.append("'" + cod_tipo_dovuto + "',"); else sqlQuery.append("null,");
		if (is_cod_tipo_dovuto_present != null)	sqlQuery.append(is_cod_tipo_dovuto_present + ","); else sqlQuery.append("null,");
		if (StringUtils.isNotBlank(cod_fed_user_id)) sqlQuery.append("'" + cod_fed_user_id + "',"); else sqlQuery.append("null,");
		if (flagnascosto != null)	sqlQuery.append(flagnascosto + ","); else sqlQuery.append("null,");
		if (StringUtils.isNotBlank(classificazione_completezza)) sqlQuery.append("'" + classificazione_completezza + "',"); else sqlQuery.append("null,");
		sqlQuery.append(page + ",");
		sqlQuery.append(size);
		sqlQuery.append(")");
		
		Query query = entityManager.createNativeQuery(sqlQuery.toString(), RendicontazioneTesoreriaSubset.class);
		List<RendicontazioneTesoreriaSubset> resultList = query.getResultList();
		return resultList;
	}

	@Override
	public int getCountRendicontazioneTesoreriaPageListFunction(String codice_ipa_ente,
			String identificativo_flusso_rendicontazione, String identificativo_univoco_regolamento,
			Date dt_data_regolamento_da, Date dt_data_regolamento_a, Date dt_data_contabile_da,
			Date dt_data_contabile_a, Date dt_data_valuta_da, Date dt_data_valuta_a,
			Date dt_data_ultimo_aggiornamento_da, Date dt_data_ultimo_aggiornamento_a, String de_causale_t,
			String importo, String conto, String codor1, String cod_tipo_dovuto, Boolean is_cod_tipo_dovuto_present,
			String cod_fed_user_id, Boolean flagnascosto, String classificazione_completezza) {
		
		LOG.info("getCountRendicontazioneTesoreriaPageListFunction");
		
		StringBuffer sqlQuery = new StringBuffer("SELECT * FROM get_count_rendicontazione_tesoreria_subset_function(");
		if (StringUtils.isNotBlank(codice_ipa_ente)) sqlQuery.append("'" + codice_ipa_ente + "',"); else sqlQuery.append("null,");
		if (StringUtils.isNotBlank(identificativo_flusso_rendicontazione)) sqlQuery.append("'" + identificativo_flusso_rendicontazione + "',"); else sqlQuery.append("null,");
		if (StringUtils.isNotBlank(identificativo_univoco_regolamento)) sqlQuery.append("'" + identificativo_univoco_regolamento + "',"); else sqlQuery.append("null,");
		if (dt_data_regolamento_da!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(dt_data_regolamento_da) + "',"); else sqlQuery.append("null,");
		if (dt_data_regolamento_a!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(dt_data_regolamento_a) + "',"); else sqlQuery.append("null,");
		if (dt_data_contabile_da!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(dt_data_contabile_da) + "',"); else sqlQuery.append("null,");
		if (dt_data_contabile_a!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(dt_data_contabile_a) + "',"); else sqlQuery.append("null,");
		if (dt_data_valuta_da!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(dt_data_valuta_da) + "',"); else sqlQuery.append("null,");
		if (dt_data_valuta_a!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(dt_data_valuta_a) + "',"); else sqlQuery.append("null,");
		if (dt_data_ultimo_aggiornamento_da!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(dt_data_ultimo_aggiornamento_da) + "',"); else sqlQuery.append("null,");
		if (dt_data_ultimo_aggiornamento_a!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(dt_data_ultimo_aggiornamento_a) + "',"); else sqlQuery.append("null,");
		if (StringUtils.isNotBlank(de_causale_t)) sqlQuery.append("'" + de_causale_t + "',"); else sqlQuery.append("null,");
		if (StringUtils.isNotBlank(importo)) sqlQuery.append("'" + importo + "',"); else sqlQuery.append("null,");
		if (StringUtils.isNotBlank(conto)) sqlQuery.append("'" + conto + "',"); else sqlQuery.append("null,");
		if (StringUtils.isNotBlank(codor1)) sqlQuery.append("'" + codor1 + "',"); else sqlQuery.append("null,");
		if (StringUtils.isNotBlank(cod_tipo_dovuto)) sqlQuery.append("'" + cod_tipo_dovuto + "',"); else sqlQuery.append("null,");
		if (is_cod_tipo_dovuto_present != null)	sqlQuery.append(is_cod_tipo_dovuto_present + ","); else sqlQuery.append("null,");
		if (StringUtils.isNotBlank(cod_fed_user_id)) sqlQuery.append("'" + cod_fed_user_id + "',"); else sqlQuery.append("null,");
		if (flagnascosto != null)	sqlQuery.append(flagnascosto + ","); else sqlQuery.append("null,");
		if (StringUtils.isNotBlank(classificazione_completezza)) sqlQuery.append("'" + classificazione_completezza + "'"); else sqlQuery.append("null");
		sqlQuery.append(")");
		
		Query query = entityManager.createNativeQuery(sqlQuery.toString());
		BigInteger numberResult = (BigInteger)query.getSingleResult();
		int finalResult = numberResult.intValue();
		return finalResult;
	}

}
