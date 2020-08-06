package it.tasgroup.services.dao.ejb;

import it.nch.fwk.fo.pager.PagingCriteria;
import it.nch.fwk.fo.pager.PagingData;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.persistence.dao.util.PaginationJPQLQuery;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * JPA Implementation for CMT paradigm.
 * <br />
 *
 * @author follia - Andrea Folli
 *
 * @param <T>
 */
//@Stateless
//@LocalBean
public class DaoImplJpaCmtJta<T extends Serializable> extends DaoImplJpa<T> implements Dao<T> {

	private static final Logger LOGGER = LogManager.getLogger(DaoImplJpaCmtJta.class);

	protected EntityManager em;

	@Override
	public T loadById(final Class<T> entityType, final Serializable key, final Object persistentContext, final Set<String> fetchProfiles) throws Exception {
		EntityManager em = null;
		try {
				if(LOGGER.isInfoEnabled())
					LOGGER.info("Bean to find has key: " + key);
				em = this.entityManagerBind(persistentContext);
				T entity = em.find(entityType, key);
				return entity;
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				throw e;
			}
	}

	@Override
	public T getById(final Class<T> entityType, final Serializable key, final Object persistentContext, final Set<String> fetchProfiles) throws Exception {
		EntityManager em = null;
		try {
				if(LOGGER.isInfoEnabled())
					LOGGER.info("Bean to find has key: " + key);
				em = this.entityManagerBind(persistentContext);
				T entity = em.find(entityType, key);
				return entity;
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				throw e;
			}
	}

	@Override
	public T create(T entity, final Object persistentContext) throws Exception {
		EntityManager em = null;
		try {
				if(LOGGER.isInfoEnabled())
					LOGGER.info("Dao instanceof: " + this.getClass() + ", entity to persist is: " + entity);
				em = this.entityManagerBind(persistentContext);
				em.persist(entity);
				return entity;
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				throw e;
			}
	}

	@Override
	public T update(T entity, final Object persistentContext) throws Exception {
		EntityManager em = null;
		try {
				if(LOGGER.isInfoEnabled())
					LOGGER.info("Dao instanceof: " + this.getClass() + ", entity to merge is: " + entity);
				em = this.entityManagerBind(persistentContext);
				em.merge(entity);
				return entity;
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				throw e;
			}
	}

	@Override
	public T save(T entity, final Object persistentContext) throws Exception {
		EntityManager em = null;
		try {
				if(LOGGER.isInfoEnabled())
					LOGGER.info("Dao instanceof: " + this.getClass() + ", entity to merge is: " + entity);
				em = this.entityManagerBind(persistentContext);
				em.merge(entity);
				return entity;
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				throw e;
			}
	}

	@Override
	public T delete(T entity, final Object persistentContext) throws Exception {
		EntityManager em = null;
		try {
				if(LOGGER.isInfoEnabled())
					LOGGER.info("Dao instanceof: " + this.getClass() + ", entity to delete is: " + entity);
				em = this.entityManagerBind(persistentContext);
				em.remove(em.merge(entity));
				return entity;
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				throw e;
			}
	}

	@Override
	public List<? extends Object> listByQuery(final String query, final int firstResult,
			final int maxResult, final int fetchSize, final Map<String, ? extends Object> params,
			final Object persistentContext) throws Exception {
		EntityManager em = null;
		try {
			em = this.entityManagerBind(persistentContext);
			return this.querySetup(em.createNamedQuery(query), fetchSize,
					firstResult, maxResult, params).getResultList();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw e;
		}
	}

	public List<T> paginateByQuery(final Class<T> entityType, final String queryToPaginate, PagingCriteria pagingCriteria, PagingData pagingData, final Map<String, ? extends Object> parameters) throws Exception {

		try {

			managePagingData(queryToPaginate, pagingCriteria, pagingData, parameters);

			LOGGER.debug("paginateByQuery query:"+queryToPaginate);

			Query query = em.createNativeQuery(queryToPaginate, entityType);

			return this.querySetup(query, 0, pagingCriteria.getRecordPosition(), pagingCriteria.getResultsPerPage(), parameters).getResultList();

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw e;
		}
	}

	public List paginateByJPQLQuery(PaginationJPQLQuery paginationJPQLQuery, PagingCriteria pagingCriteria, PagingData pagingData, final Map<String, ? extends Object> parameters) throws Exception {

		try {
			managePagingDataJPQL(paginationJPQLQuery.getCountQuery(), pagingCriteria, pagingData, parameters);
			LOGGER.debug("paginateByQuery query:"+ paginationJPQLQuery.getQuery());
			Query query = em.createQuery(paginationJPQLQuery.getQuery());
			return this.querySetup(query, 0, pagingCriteria.getRecordPosition(), pagingCriteria.getResultsPerPage(), parameters).getResultList();

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw e;
		}
	}
		
		protected void handleAggregateData(PaginationJPQLQuery queries, ContainerDTO containerDTO, Map<String, ?> parameters) {
				if (queries.getSumQuery() != null) {
						Query sumQuery = em.createQuery(queries.getSumQuery());
						BigDecimal sum = (BigDecimal) querySetup(sumQuery, parameters).getSingleResult();
						containerDTO.setSum(sum);
				}
				
		}
		
		
		private void managePagingDataJPQL(String queryJPQLToPaginate, PagingCriteria pagingCriteria, PagingData pagingData, Map<String, ? extends Object> parameters) {

		if (pagingData.getNumTotalRecords() < 0) {

			Query countQuery = em.createQuery(queryJPQLToPaginate);

			Number totalRowsNumber = (Number) querySetup(countQuery, parameters).getSingleResult();

			Integer totalRows = totalRowsNumber.intValue();

			pagingData.setNumTotalRecords(totalRows);
		}
	}


	public List paginateByQueryWithResultsetMapping(final String queryToPaginate, PagingCriteria pagingCriteria, PagingData pagingData, final Map<String, ? extends Object> parameters, String resultsetMapping) throws Exception {

		try {

			managePagingData(queryToPaginate, pagingCriteria, pagingData, parameters);

			Query query = em.createNativeQuery(queryToPaginate,resultsetMapping);

			return this.querySetup(query, 0, pagingCriteria.getRecordPosition(), pagingCriteria.getResultsPerPage(), parameters).getResultList();

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw e;
		}
	}


	protected void managePagingData(String queryToPaginate, PagingCriteria pagingCriteria, PagingData pagingData, Map<String, ? extends Object> parameters) {

		if (pagingData.getNumTotalRecords() < 0){

			Query countQuery = em.createNativeQuery("select count(*) from (" + queryToPaginate + ")  querydapaginare");

			Number totalRowsNumber = (Number) querySetup(countQuery, parameters).getSingleResult();

			Integer totalRows = totalRowsNumber.intValue();

			pagingData.setNumTotalRecords(totalRows);

			// le istruzioni seguenti servono solo per la paginazione della tree table
			// mentre sono ridondanti per la paginazione display:table

			pagingData.setPageNumber(pagingCriteria.getNumNextPage());

			int numMaxRowsPerPage = pagingCriteria.getResultsPerPage();

			int numTotalPages = 0;

			int numFullPages=(totalRows / numMaxRowsPerPage);

			int numNotFullPages = (totalRows % numMaxRowsPerPage == 0)? 0 : 1;

			numTotalPages = numFullPages + numNotFullPages;

			pagingData.setNumTotalPages(numTotalPages);

			List pageNumbers = new ArrayList();

			int nextPage = pagingData.getPageNumber();

			int startPage = Math.max(1,nextPage-2);

			int startPage2 = Math.min(startPage, numTotalPages-4);

			int startPage3 = Math.max(startPage2, 1);

			int endPage =  Math.min(startPage+5,numTotalPages+1);

			for (int i=startPage3; i<endPage ; i++)
				pageNumbers.add(i);

			pagingData.setPageNumbers(pageNumbers);

		}

	}

	@Override
	public Iterator<? extends Object> iterateByQuery(final String query,
			final int firstResult, final int maxResult, final int fetchSize,
			final Map<String, ? extends Object> params, final Object persistentContext)
			throws Exception {
		EntityManager em = null;
		try {
			em = this.entityManagerBind(persistentContext);
			return this.querySetup(em.createNamedQuery(query), fetchSize,
					firstResult, maxResult, params).getResultList().iterator();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public Object uniqueResultByQuery(final String query,
			final Map<String, ? extends Object> params, final Object persistentContext)
			throws Exception {
		EntityManager em = null;
		try {
			em = this.entityManagerBind(persistentContext);
			return this.querySetup(em.createNamedQuery(query), -1, -1, -1, params).getSingleResult();
		} catch (NoResultException nre) {
				LOGGER.trace(nre.getMessage(), nre);
			return null;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public EntityManager entityManagerBind(final Object persistentContext) {
		if (persistentContext != null) {
			if (persistentContext instanceof EntityManager) {
				return (EntityManager) persistentContext;
			} else {
				LOGGER.error("Persistent Context is not an instance of JPA EntityManager");
				throw new UnsupportedOperationException(
						"Persistent Context is not an instance of JPA EntityManager");
			}
		}
		return this.getEntityManager();
	}

	public List paginateByQuery(final String queryToPaginate, PagingCriteria pagingCriteria, PagingData pagingData, final Map<String, ? extends Object> parameters) throws Exception {

		try {

			managePagingData(queryToPaginate, pagingCriteria, pagingData, parameters);

			Query query = em.createNativeQuery(queryToPaginate);

			return this.querySetup(query, 0, pagingCriteria.getRecordPosition(), pagingCriteria.getResultsPerPage(), parameters).getResultList();

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw e;
		}
	}

	protected EntityManager getEntityManager() {
		return em;
	}

	protected void appendInCondition(StringBuffer query, Collection<String> conditionList, String prefix) {

		int i = 0;

		for (String condition : conditionList){

			query.append(":"+prefix+i+",");

			i++;
		}

		query.deleteCharAt(query.length() - 1).append(")");
	}

	protected void addInParameters(Map<String, Object> params, List<String> conditionList, String prefix) {

		int i = 0;

		for (String condition : conditionList) {

			params.put(prefix + i, condition);

			i++;
		}
	}

	protected void appendInCondition(StringBuffer query, Collection<String> conditionList) {

		for (String condition : conditionList)
			query.append("?,");

		query.deleteCharAt(query.length() - 1).append(")");
	}

	protected void addInParameters(Query query, List<String> conditionList, int offset) {

		int i = 1;

		for (String condition : conditionList) {

			query.setParameter(offset + i, condition);

			i++;
		}
	}

	public void deleteByKey(final Class<T> entityType, final Serializable key, final Object persistentContext) throws Exception {

		T entity = getById(entityType, key);

		delete(entity);

	}


}
