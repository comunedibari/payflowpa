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

import it.regioneveneto.mygov.payment.mypivot.dao.TesoreriaFunctionDao;
import it.regioneveneto.mygov.payment.mypivot.domain.po.TesoreriaNoMatchSubset;
import it.regioneveneto.mygov.payment.mypivot.domain.po.TesoreriaSubset;
import it.regioneveneto.mygov.payment.mypivot.utils.Constants;

@Repository
public class TesoreriaFunctionDaoImpl implements TesoreriaFunctionDao {

	private static final Logger LOG = Logger.getLogger(TesoreriaFunctionDaoImpl.class);

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Integer getCountTesoreriaPageListFunction(final String cod_ipa_ente, final Date dt_data_contabile_da, final Date dt_data_contabile_a, final Date data_valuta_da, final Date data_valuta_a,
			final Date dt_data_ultimo_aggiornamento_da, final Date dt_data_ultimo_aggiornamento_a, final String deCausaleT,
			final String importo, final String conto, final String codOr1, final Boolean visualizzaNascosti, final String errorCode, final String codIuv, final String codIuf) {


		LOG.info("getCountTesoreriaPageListFunction");
		
		StringBuffer sqlQuery = new StringBuffer("SELECT * FROM get_count_tesoreria_subset_function(");
		if (StringUtils.isNotBlank(cod_ipa_ente))	sqlQuery.append("'" + cod_ipa_ente + "',"); else sqlQuery.append("null,");
		if (dt_data_contabile_da!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(dt_data_contabile_da) + "',"); else sqlQuery.append("null,");
		if (dt_data_contabile_a!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(dt_data_contabile_a) + "',"); else sqlQuery.append("null,");
		if (data_valuta_da!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(data_valuta_da) + "',"); else sqlQuery.append("null,");
		if (data_valuta_a!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(data_valuta_a) + "',"); else sqlQuery.append("null,");		
		if (dt_data_ultimo_aggiornamento_da!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(dt_data_ultimo_aggiornamento_da) + "',"); else sqlQuery.append("null,");
		if (dt_data_ultimo_aggiornamento_a!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(dt_data_ultimo_aggiornamento_a) + "',"); else sqlQuery.append("null,");	
		if (StringUtils.isNotBlank(deCausaleT)) sqlQuery.append("'" + deCausaleT + "',"); else sqlQuery.append("null,");
     	if (StringUtils.isNotBlank(importo))	sqlQuery.append("'" + importo + "',"); else sqlQuery.append("null,");		
		if (StringUtils.isNotBlank(conto))	sqlQuery.append("'" + conto + "',"); else sqlQuery.append("null,");
		if (StringUtils.isNotBlank(codOr1))	sqlQuery.append("'" + codOr1 + "',"); else sqlQuery.append("null,");
		if (visualizzaNascosti != null)	sqlQuery.append(visualizzaNascosti + ","); else sqlQuery.append("null,");
		if (StringUtils.isNotBlank(errorCode))	sqlQuery.append("'" + errorCode + "',"); else sqlQuery.append("null,");
		if (StringUtils.isNotBlank(codIuv))	sqlQuery.append("'" + codIuv + "',"); else sqlQuery.append("null,");
		if (StringUtils.isNotBlank(codIuf))	sqlQuery.append("'" + codIuf + "'"); else sqlQuery.append("null");
		sqlQuery.append(")");

		Query query = entityManager.createNativeQuery(sqlQuery.toString());
		BigInteger numberResultList = (BigInteger)query.getSingleResult();
		return numberResultList.intValue();
	}
	
	@Override
	public List<TesoreriaSubset> getTesoreriaPageListFunction(final String cod_ipa_ente, final Date dt_data_contabile_da, final Date dt_data_contabile_a, final Date data_valuta_da, final Date data_valuta_a,
			final Date dt_data_ultimo_aggiornamento_da, final Date dt_data_ultimo_aggiornamento_a, final String deCausaleT,
			final String importo, final String conto, final String codOr1, final Boolean visualizzaNascosti, final String errorCode, final String codIuv, final String codIuf, final int page,
			final int size) {

		LOG.info("getTesoreriaPageListFunction");
		
		StringBuffer sqlQuery = new StringBuffer("SELECT * FROM get_tesoreria_subset_function(");
			if (StringUtils.isNotBlank(cod_ipa_ente))	sqlQuery.append("'" + cod_ipa_ente + "',"); else sqlQuery.append("null,");
			if (dt_data_contabile_da!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(dt_data_contabile_da) + "',"); else sqlQuery.append("null,");
			if (dt_data_contabile_a!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(dt_data_contabile_a) + "',"); else sqlQuery.append("null,");
			if (data_valuta_da!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(data_valuta_da) + "',"); else sqlQuery.append("null,");
			if (data_valuta_a!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(data_valuta_a) + "',"); else sqlQuery.append("null,");		
			if (dt_data_ultimo_aggiornamento_da!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(dt_data_ultimo_aggiornamento_da) + "',"); else sqlQuery.append("null,");
			if (dt_data_ultimo_aggiornamento_a!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(dt_data_ultimo_aggiornamento_a) + "',"); else sqlQuery.append("null,");	
			if (StringUtils.isNotBlank(deCausaleT)) sqlQuery.append("'" + deCausaleT + "',"); else sqlQuery.append("null,");
			if (StringUtils.isNotBlank(importo))	sqlQuery.append("'" + importo + "',"); else sqlQuery.append("null,");		
			if (StringUtils.isNotBlank(conto))	sqlQuery.append("'" + conto + "',"); else sqlQuery.append("null,");
			if (StringUtils.isNotBlank(codOr1))	sqlQuery.append("'" + codOr1 + "',"); else sqlQuery.append("null,");
			if (visualizzaNascosti != null)	sqlQuery.append(visualizzaNascosti + ","); else sqlQuery.append("null,");
			if (StringUtils.isNotBlank(errorCode))	sqlQuery.append("'" + errorCode + "',"); else sqlQuery.append("null,");
			if (StringUtils.isNotBlank(codIuv))	sqlQuery.append("'" + codIuv + "',"); else sqlQuery.append("null,");
			if (StringUtils.isNotBlank(codIuf))	sqlQuery.append("'" + codIuf + "',"); else sqlQuery.append("null,");
			sqlQuery.append(page + ",");
			sqlQuery.append(size);
			sqlQuery.append(")");

		Query query = entityManager.createNativeQuery(sqlQuery.toString(), TesoreriaSubset.class);
		List<TesoreriaSubset> resultList = query.getResultList();
		return resultList;
		
		
	}
	
	@Override
	public Integer getCountTesoreriaNoMatchPageListFunction(final String cod_ipa_ente, final Date dt_data_contabile_da, final Date dt_data_contabile_a, final Date data_valuta_da, final Date data_valuta_a,
			final Date dt_data_ultimo_aggiornamento_da, final Date dt_data_ultimo_aggiornamento_a, final String deCausaleT,
			final String importo, final String conto, final String codOr1, final Boolean visualizzaNascosti, final String errorCode) {


		LOG.info("getCountTesoreriaNoMatchPageListFunction");
		
		StringBuffer sqlQuery = new StringBuffer("SELECT * FROM get_count_tesoreria_no_match_subset_function(");
		if (StringUtils.isNotBlank(cod_ipa_ente))	sqlQuery.append("'" + cod_ipa_ente + "',"); else sqlQuery.append("null,");
		if (dt_data_contabile_da!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(dt_data_contabile_da) + "',"); else sqlQuery.append("null,");
		if (dt_data_contabile_a!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(dt_data_contabile_a) + "',"); else sqlQuery.append("null,");
		if (data_valuta_da!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(data_valuta_da) + "',"); else sqlQuery.append("null,");
		if (data_valuta_a!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(data_valuta_a) + "',"); else sqlQuery.append("null,");		
		if (dt_data_ultimo_aggiornamento_da!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(dt_data_ultimo_aggiornamento_da) + "',"); else sqlQuery.append("null,");
		if (dt_data_ultimo_aggiornamento_a!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(dt_data_ultimo_aggiornamento_a) + "',"); else sqlQuery.append("null,");
		if (StringUtils.isNotBlank(deCausaleT)) sqlQuery.append("'" + deCausaleT + "',"); else sqlQuery.append("null,");	
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
	public List<TesoreriaNoMatchSubset> getTesoreriaNoMatchPageListFunction(final String cod_ipa_ente, final Date dt_data_contabile_da, final Date dt_data_contabile_a, final Date data_valuta_da, final Date data_valuta_a,
			final Date dt_data_ultimo_aggiornamento_da, final Date dt_data_ultimo_aggiornamento_a, final String deCausaleT,
			final String importo, final String conto, final String codOr1, final Boolean visualizzaNascosti, final String errorCode, final String codIuv, final String codIuf, final int page,
			final int size) {

		LOG.info("getTesoreriaNoMatchPageListFunction");
		
		StringBuffer sqlQuery = new StringBuffer("SELECT * FROM get_tesoreria_subset_function(");
			if (StringUtils.isNotBlank(cod_ipa_ente))	sqlQuery.append("'" + cod_ipa_ente + "',"); else sqlQuery.append("null,");
			if (dt_data_contabile_da!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(dt_data_contabile_da) + "',"); else sqlQuery.append("null,");
			if (dt_data_contabile_a!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(dt_data_contabile_a) + "',"); else sqlQuery.append("null,");
			if (data_valuta_da!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(data_valuta_da) + "',"); else sqlQuery.append("null,");
			if (data_valuta_a!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(data_valuta_a) + "',"); else sqlQuery.append("null,");		
			if (dt_data_ultimo_aggiornamento_da!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(dt_data_ultimo_aggiornamento_da) + "',"); else sqlQuery.append("null,");
			if (dt_data_ultimo_aggiornamento_a!=null)	sqlQuery.append("'" + Constants.YYYYMMDD.format(dt_data_ultimo_aggiornamento_a) + "',"); else sqlQuery.append("null,");
			if (StringUtils.isNotBlank(deCausaleT)) sqlQuery.append("'" + deCausaleT + "',"); else sqlQuery.append("null,");
			if (StringUtils.isNotBlank(importo))	sqlQuery.append("'" + importo + "',"); else sqlQuery.append("null,");		
			if (StringUtils.isNotBlank(conto))	sqlQuery.append("'" + conto + "',"); else sqlQuery.append("null,");
			if (StringUtils.isNotBlank(codOr1))	sqlQuery.append("'" + codOr1 + "',"); else sqlQuery.append("null,");
			if (visualizzaNascosti != null)	sqlQuery.append(visualizzaNascosti + ","); else sqlQuery.append("null,");
			if (StringUtils.isNotBlank(errorCode))	sqlQuery.append("'" + errorCode + "',"); else sqlQuery.append("null,");
			if (StringUtils.isNotBlank(codIuv))	sqlQuery.append("'" + codIuv + "',"); else sqlQuery.append("null,");
			if (StringUtils.isNotBlank(codIuf))	sqlQuery.append("'" + codIuf + "',"); else sqlQuery.append("null,");
			sqlQuery.append(page + ",");
			sqlQuery.append(size);
			sqlQuery.append(")");
			
			

		Query query = entityManager.createNativeQuery(sqlQuery.toString(), TesoreriaNoMatchSubset.class);
		List<TesoreriaNoMatchSubset> resultList = query.getResultList();
		return resultList;
	}
	
}
