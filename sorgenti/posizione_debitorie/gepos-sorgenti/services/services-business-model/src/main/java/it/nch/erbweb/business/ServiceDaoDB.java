/*
 * Created on Sep 4, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package it.nch.erbweb.business;

import it.nch.erbweb.business.dao.util.BeanTransformer;
import it.nch.erbweb.business.dao.util.HibernatePager;
import it.nch.erbweb.business.dao.util.QueryLocator;
import it.nch.erbweb.business.dao.util.SessionFactoryUtils;
import it.nch.erbweb.business.dao.util.SimpleQueryListPager;
import it.nch.fwk.fo.common.constants.FrameworkMessage;
import it.nch.fwk.fo.core.HibernateQueryLogger;
import it.nch.fwk.fo.core.QueryParams;
import it.nch.fwk.fo.core.StatelessSessionManager;
import it.nch.fwk.fo.core.exception.ManageFWBackEndException;
import it.nch.fwk.fo.das.DAODatabase;
import it.nch.fwk.fo.das.exception.DasUncheckedException;
import it.nch.fwk.fo.dto.DTO;
import it.nch.fwk.fo.dto.DTOCollection;
import it.nch.fwk.fo.dto.DTOPo;
import it.nch.fwk.fo.pager.CachedQueryListPager;
import it.nch.fwk.fo.pager.PagingCriteria;
import it.nch.fwk.fo.util.Tracer;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.Type;

/**
 * @author Matteo
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class ServiceDaoDB extends DAODatabase {

	private SessionFactory sessionFactory;
	private QueryLocator queryLocator;
	private StatelessSessionManager statelessSessionManager;
	
	
	/**
	 * @return
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * @param manager
	 */
	public void setSessionFactory(SessionFactory manager) {
		sessionFactory = manager;
	}

	/**
	 * 
	 * @param statelessSessionManager
	 */
	public void setStatelessSessionManager(StatelessSessionManager statelessSessionManager) {
		this.statelessSessionManager = statelessSessionManager;
	}

	/**
	 * 
	 * @return
	 */
	public StatelessSessionManager getStatelessSessionManager() {
		return statelessSessionManager;
	}


	public Session getSession() {
		
		return SessionFactoryUtils.getSession(sessionFactory);
	}
	

	public DTOCollection getPagedQuery(Session sm, PagingCriteria pagingCriteria, Query  hqlQuery, Query  hqlCount){
		Tracer.debug(getClass().getName(),"getPagedQuery", "PAGINAZIONE SEMPLICE");
		SimpleQueryListPager simplePager = new SimpleQueryListPager();    
		return  simplePager.getPagedQuery(pagingCriteria, sm, hqlQuery, hqlCount);
	}

	public DTOCollection getPagedQuery(Session sm, PagingCriteria pagingCriteria, String hqlStringQuery, QueryParams queryParams){
		Tracer.debug(getClass().getName(),"getPagedQuery", "PAGINAZIONE SEMPLICE");
		SimpleQueryListPager simplePager = new SimpleQueryListPager();    
		Query hqlQuery = retrieveQuery(sm, hqlStringQuery, queryParams);
		return simplePager.getPagedQuery(pagingCriteria, sm, hqlQuery);
	}

	public DTOPo getDTOPoPagedQuery(Session sm,PagingCriteria pagingCriteria , String hqlStringQuery, QueryParams queryParams){
		Tracer.debug(getClass().getName(),"getDTOPoPagedQuery", "PAGINAZIONE SEMPLICE");
		SimpleQueryListPager simplePager = new SimpleQueryListPager();    
		Query hqlQuery = retrieveQuery(sm, hqlStringQuery, queryParams);
		return  simplePager.getDTOPoPagedQuery(pagingCriteria, sm, hqlQuery);
	}

	public DTOPo getDTOPoPagedQueryByCriteria(Session sm,PagingCriteria pagingCriteria , Criteria criteria){
		Tracer.debug(getClass().getName(),"getDTOPoPagedQueryByCriteria", "PAGINAZIONE SEMPLICE");
		SimpleQueryListPager simplePager = new SimpleQueryListPager();    
		return  simplePager.getDTOPoPagedQueryByCriteria(pagingCriteria, sm,criteria);
	}
	
	public DTOCollection getPagedQueryByCriteria(Session sm, PagingCriteria pagingCriteria, Criteria criteria){
		Tracer.debug(getClass().getName(),"getPagedQueryByCriteria", "PAGINAZIONE SEMPLICE");
		SimpleQueryListPager simplePager = new SimpleQueryListPager();  
		return simplePager.getPagedQueryByCriteria(pagingCriteria, sm,criteria);
	}
	
	public void removeQuery(PagingCriteria pagingCriteria)
	{
		boolean cachingEnabled = false; //TODO configure this parameter
		if (cachingEnabled)
		{
			//CachedQueryListPager pager = new CachedQueryListPager(); //TODO inject with SPRING
			//pager.removeQuery(pagingCriteria);
		}
	}
	public DTOPo getDTOPoPagedQuery(Session sm,PagingCriteria pagingCriteria , Query  hqlQuery, Query  hqlCount){
		Tracer.debug(getClass().getName(),"getDTOPoPagedQuery", "PAGINAZIONE SEMPLICE");
		SimpleQueryListPager simplePager = new SimpleQueryListPager();
		return  simplePager.getDTOPoPagedQuery(pagingCriteria, sm, hqlQuery, hqlCount);
	}
	
	/**
	 * 
	 * @param sm
	 * @param pagingCriteria
	 * @param sqlQuery
	 * @param countQuery
	 * @param bt
	 * @return
	 * @throws Exception 
	 */
	public DTOPo getDTOPoPagedCountSQLQuery(StatelessSessionManager sm,PagingCriteria pagingCriteria, String  sqlQuery, String  countQuery, BeanTransformer bt) throws Exception{
		Tracer.debug(getClass().getName(),"getDTOPoPagedCountSQLQuery", "PAGINAZIONE MOLTO SEMPLICE");
		HibernatePager hibernatePager = new HibernatePager();
		return hibernatePager.getDTOPoPagedCountQuery(pagingCriteria,sm,sqlQuery,countQuery,bt);

	}

	/**
	 * 
	 * @param sm
	 * @param pagingCriteria
	 * @param sqlQuery
	 * @param countQuery
	 * @param bt
	 * @return
	 * @throws Exception 
	 */
	public DTOPo getDTOPoPagedSQLQuery(StatelessSessionManager sm,PagingCriteria pagingCriteria, String  sqlQuery, String  countQuery, BeanTransformer bt) throws Exception{
		Tracer.debug(getClass().getName(),"getDTOPoPagedSQLQuery", "PAGINAZIONE MOLTO SEMPLICE");
		HibernatePager hibernatePager = new HibernatePager();
		return hibernatePager.getDTOPoPagedQuery(pagingCriteria,sm,sqlQuery,countQuery,bt);

	}

	/**
	 * nuovo senza scroll
	 * @param sm
	 * @param pagingCriteria
	 * @param sqlQuery
	 * @param countQuery
	 * @param bt
	 * @return
	 * @throws Exception
	 */
	public DTOPo getDTOPoSQLQuery(StatelessSessionManager sm,PagingCriteria pagingCriteria, String  sqlQuery, String  countQuery, BeanTransformer bt) throws Exception{
		Tracer.debug(getClass().getName(),"getDTOPoSQLQuery", "PAGINAZIONE MOLTO SEMPLICE senza scroll");
		HibernatePager hibernatePager = new HibernatePager();
		return hibernatePager.getDTOPoQuery(pagingCriteria,sm,sqlQuery,countQuery,bt);

	}

	/**
	 * 
	 * @param pagingCriteria
	 * @param sqlQuery
	 * @param bt
	 * @return
	 */
	public DTOPo getDTOPoPagedSQLQuery(PagingCriteria pagingCriteria, Query  sqlQuery, BeanTransformer bt){
		Tracer.debug(getClass().getName(),"getDTOPoPagedSQLQuery", "PAGINAZIONE SQL CON TRASFORMAZIONE");
		if (pagingCriteria == null) 
			return getDTOPoPagedSQLQuery(sqlQuery, bt);
		HibernatePager hibernatePager = new HibernatePager();	
		return hibernatePager.scrollWithPagination(pagingCriteria, sqlQuery, bt);	
	}

	public DTOPo getDTOPoPagedSQLQuery(Query  sqlQuery, BeanTransformer bt){
		Tracer.debug(getClass().getName(),"getDTOPoPagedSQLQuery", "PAGINAZIONE SQL CON TRASFORMAZIONE");
		HibernatePager hibernatePager = new HibernatePager();	
		return hibernatePager.scrollWithPagination(new PagingCriteria(), sqlQuery, bt);	
	}

	public DTOPo getDTOPoPagedSQLQuery(Session sm,PagingCriteria pagingCriteria , Query  hqlQuery, Query  hqlCount){
		Tracer.debug(getClass().getName(),"getDTOPoPagedQuery", "PAGINAZIONE SEMPLICE");
		SimpleQueryListPager simplePager = new SimpleQueryListPager();	
		return  simplePager.getDTOPoPagedQuery(pagingCriteria, sm, hqlQuery, hqlCount);
	}

	/*
	public DTOPo getDTOPoPagedQuery(PagingCriteria pagingCriteria, Query  query, BeanTransformer bt){
		Tracer.debug(getClass().getName(),"getDTOPoPagedQuery", "PAGINAZIONE SEMPLICE");
		return HibernatePager.scrollWithPagination(pagingCriteria, query, bt);
	}
	*/

	public DTOCollection getPagedQuery(Session sm, DTO dto, String hqlQuery, String hqlCount)
	{
    
		PagingCriteria pagingCriteria = dto.getPagingCriteria();
		DTOCollection DTOColl = null;
    	
		boolean enableCachedPaging = false;
		try 
		{    
			if (enableCachedPaging)
			{
				Tracer.debug(getClass().getName(),"getPagedQuery", "PAGINAZIONE CACHED");
				CachedQueryListPager cachedPager = new CachedQueryListPager();    //TODO inject with SPRING
				//DTOColl = cachedPager.getPagedQuery(pagingCriteria, sm, hqlQuery, hqlCount); //, String hqlQuery, String hqlCount);   
			}
			else
			{ 
				Tracer.debug(getClass().getName(),"getPagedQuery", "PAGINAZIONE SEMPLICE");
				SimpleQueryListPager simplePager = new SimpleQueryListPager();    //TODO inject with SPRING
				DTOColl = simplePager.getPagedQuery(pagingCriteria, sm, hqlQuery, hqlCount);
			}
	    	
		} catch (HibernateException e) {
			throw new DasUncheckedException(null, e);
		}
		return DTOColl;
	}	
	
	public Query retrieveQuery(Session sm, String queryString, QueryParams queryParams) {
		Query query = null;
		try{
			query = sm.createQuery(queryString);
	
			for (Iterator param = queryParams.paramSet().iterator(); param.hasNext();) {
				String paramName = (String) param.next();
				Object paramValue = queryParams.getValue(paramName);
	    		
				Type paramType = queryParams.getType(paramName);
				if(paramType!=null) {
					if  (paramValue instanceof java.util.List) {
						query.setParameterList(paramName,(List) paramValue);
					} else if  (paramValue instanceof java.util.Collection) {
						query.setParameterList(paramName,(Collection) paramValue);		
					} else {					
						query.setParameter(paramName, paramValue, paramType);
					}
				} else {
					if  (paramValue instanceof java.util.List) {
						query.setParameterList(paramName,(List) paramValue);		
					} else if  (paramValue instanceof java.util.Collection) {
						query.setParameterList(paramName,(Collection) paramValue);		
					} else {					
						query.setParameter(paramName, paramValue);
					}
				}
			}
			new HibernateQueryLogger().debugSQL(sm.getSessionFactory(), query);
		} catch (HibernateException ex) {
			Tracer.error(this.getClass().getName(), "Recupero query fallita "+ queryString +"]", "", ex);
			new ManageFWBackEndException().processDAOException(ex, FrameworkMessage.DAO_0002);
		}
		return query;
	}	


	/**
	 * @return
	 */
	public QueryLocator getQueryLocator() {
		return queryLocator;
	}

	/**
	 * @param locator
	 */
	public void setQueryLocator(QueryLocator locator) {
		queryLocator = locator;
	}
	
	public Integer contaRisultati(String queryCount){
		Integer res = null;
		
		Query q = getSession().createSQLQuery(queryCount);
		res = (Integer) q.uniqueResult();
		return res;
	}

}
