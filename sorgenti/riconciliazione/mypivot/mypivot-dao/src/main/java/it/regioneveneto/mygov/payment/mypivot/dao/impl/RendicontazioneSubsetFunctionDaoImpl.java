package it.regioneveneto.mygov.payment.mypivot.dao.impl;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import it.regioneveneto.mygov.payment.mypivot.dao.RendicontazioneSubsetFunctionDao;
import it.regioneveneto.mygov.payment.mypivot.domain.po.RendicontazioneSubset;
import it.regioneveneto.mygov.payment.mypivot.utils.Constants;

@Repository
public class RendicontazioneSubsetFunctionDaoImpl implements RendicontazioneSubsetFunctionDao {

	private static final Logger LOG = Logger.getLogger(RendicontazioneSubsetFunctionDaoImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * @author Alessandro Paolillo
	 * Il metodo richiama la function per il recupero delle rendicontazioni subset.
	 */
	@Override
	public List<RendicontazioneSubset> getRendicontazioneSubsetFunction(String codice_ipa_ente, String identificativo_flusso_rendicontazione,
			String identificativo_univoco_regolamento, Date data_regolamento_da, Date data_regolamento_a, Date data_ultimo_aggiornamento_da,
			Date data_ultimo_aggiornamento_a, String errorCode, String cod_tipo_dovuto, String cod_fed_user_id,
			Boolean visualizzaNascostiBool, Integer page, Integer size, Sort sort) {

		LOG.info("getRendicontazioneSubsetFunction");

		StringBuffer sqlQuery = new StringBuffer("SELECT * FROM get_rendicontazione_subset_function(");
		appendElementOrNullComma(sqlQuery, codice_ipa_ente);
		appendElementOrNullComma(sqlQuery, identificativo_flusso_rendicontazione);
		appendElementOrNullComma(sqlQuery, identificativo_univoco_regolamento);
		appendElementOrNullComma(sqlQuery, data_regolamento_da);
		appendElementOrNullComma(sqlQuery, data_regolamento_a);
		appendElementOrNullComma(sqlQuery, data_ultimo_aggiornamento_da);
		appendElementOrNullComma(sqlQuery, data_ultimo_aggiornamento_a);
		appendElementOrNullComma(sqlQuery, errorCode);
		appendElementOrNullComma(sqlQuery, cod_tipo_dovuto);
		appendElementOrNullComma(sqlQuery, cod_fed_user_id);
		appendElementOrNullComma(sqlQuery, visualizzaNascostiBool);
		sqlQuery.append((page != null ? page : "null") + ",");
		sqlQuery.append((size != null ? size : "null"));
		sqlQuery.append(")");

		Query query = entityManager.createNativeQuery(sqlQuery.toString(), RendicontazioneSubset.class);
		@SuppressWarnings("unchecked")
		List<RendicontazioneSubset> resultList = query.getResultList();
		return resultList;
	}
	
	private static final void appendElementOrNullComma(StringBuffer sqlQuery, String element) {
		sqlQuery.append(StringUtils.isEmpty(element) ? "null, " : "'" + element + "', ");
	}

	private static final void appendElementOrNull(StringBuffer sqlQuery, Boolean element) {
		sqlQuery.append(element == null ? "null " : element + " ");
	}

	private static final void appendElementOrNullComma(StringBuffer sqlQuery, Boolean element) {
		sqlQuery.append(element == null ? "null, " : element + ", ");
	}

	private static final void appendElementOrNullComma(StringBuffer sqlQuery, Date element) {
		sqlQuery.append(element == null ? "null, " : "'" + Constants.YYYYMMDD.format(element) + "' , ");
	}
	
	/**
	 * @author Alessandro Paolillo
	 * Il metodo richiama la function per il recupero del numero di rendicontazioni subset.
	 */
	@Override
	public Integer getCountRendicontazioneSubsetFunction(String codice_ipa_ente, String identificativo_flusso_rendicontazione,
			String identificativo_univoco_regolamento, Date data_regolamento_da, Date data_regolamento_a, Date data_ultimo_aggiornamento_da,
			Date data_ultimo_aggiornamento_a, String errorCode, String cod_tipo_dovuto, String cod_fed_user_id,
			Boolean visualizzaNascostiBool) {
		
		LOG.info("getCountRendicontazioneSubsetFunction");
		
		StringBuffer sqlQuery = new StringBuffer("SELECT * FROM get_count_rendicontazione_subset_function(");
		appendElementOrNullComma(sqlQuery, codice_ipa_ente);
		appendElementOrNullComma(sqlQuery, identificativo_flusso_rendicontazione);
		appendElementOrNullComma(sqlQuery, identificativo_univoco_regolamento);
		appendElementOrNullComma(sqlQuery, data_regolamento_da);
		appendElementOrNullComma(sqlQuery, data_regolamento_a);
		appendElementOrNullComma(sqlQuery, data_ultimo_aggiornamento_da);
		appendElementOrNullComma(sqlQuery, data_ultimo_aggiornamento_a);
		appendElementOrNullComma(sqlQuery, errorCode);
		appendElementOrNullComma(sqlQuery, cod_tipo_dovuto);
		appendElementOrNullComma(sqlQuery, cod_fed_user_id);
		appendElementOrNull(sqlQuery, visualizzaNascostiBool);
		sqlQuery.append(")");

		Query query = entityManager.createNativeQuery(sqlQuery.toString());
		BigInteger numberResultList = (BigInteger)query.getSingleResult();
		return numberResultList.intValue();
	}
	
}
