package it.regioneveneto.mygov.payment.mypivot.dao.impl;

import static org.springframework.data.jpa.repository.query.QueryUtils.toOrders;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Repository;

import it.regioneveneto.mygov.payment.mypivot.dao.ImportExportRendicontazioneTesoreriaCustomDao;
import it.regioneveneto.mygov.payment.mypivot.domain.po.ImportExportRendicontazioneTesoreria;

/**
 * dao per customizzare il comportamento standard 
 *
 */
@Deprecated
@Repository
public class ImportExportRendicontazioneTesoreriaDaoImpl implements ImportExportRendicontazioneTesoreriaCustomDao {
	private static final Logger LOG = Logger.getLogger(ImportExportRendicontazioneTesoreriaDaoImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Deprecated
	@Override
	public List<ImportExportRendicontazioneTesoreria> provaFinderNative() {
		//		LOG.info("finding nemo");
		//		StringBuffer sqlQuery = new StringBuffer()//
		//				.append("select ")//
		//				.append("	i ")//
		//				.append("from ")//
		//				.append("	mygov_import_export_rendicontazione_tesoreria i ")//
		//				.append("where ")//
		//				.append("	i.codiceIud = '008150904111858214EX917DS0016080000' ")//
		//				;
		//		
		//		Query query = entityManager.createNativeQuery(sqlQuery.toString(), ImportExportRendicontazioneTesoreria.class);
		//		List<ImportExportRendicontazioneTesoreria> resultList = query.getResultList();
		//		LOG.info("finding nemo [" + resultList.size() + "]");
		//		return resultList;
		return null;
	}

	
}