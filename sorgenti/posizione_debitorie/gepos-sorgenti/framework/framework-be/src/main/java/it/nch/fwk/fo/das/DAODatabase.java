/*
 * Creato il 25-nov-2005
 *
 * TODO Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
package it.nch.fwk.fo.das;

import it.nch.fwk.fo.core.QueryParams;
import it.nch.fwk.fo.core.StatelessSessionManager;
import it.nch.fwk.fo.cross.AbstractDAO;
import it.nch.fwk.fo.das.exception.DasUncheckedException;
import it.nch.fwk.fo.dto.DTO;
import it.nch.fwk.fo.dto.DTOCollection;
import it.nch.fwk.fo.dto.DTOPo;
import it.nch.fwk.fo.pager.CachedQueryListPager;
import it.nch.fwk.fo.pager.PagingCriteria;
import it.nch.fwk.fo.pager.SimpleQueryListPager;
import it.nch.fwk.fo.util.Tracer;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.type.Type;

/**
 * @author sberisso
 * @version 0.1 
 *
 * TODO Per modificare il modello associato al commento di questo tipo generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
public abstract class DAODatabase extends AbstractDAO implements DataProvider {
	
	SimpleQueryListPager simplePager = null;
	CachedQueryListPager cachedPager = null;
	public DAODatabase() {
	}
	
	
	// interfaccia DataProvider	
	public int init() {	
		return 0;
	}		
	
	public DTOPo getSqlPagedQuery(StatelessSessionManager sm, PagingCriteria pagingCriteria, Query  hqlQuery, Query  hqlCount){
    	Tracer.debug(getClass().getName(),"getPagedQuery", "PAGINAZIONE SEMPLICE");
    	simplePager = new SimpleQueryListPager();   
    	return  simplePager.getSqlPagedQuery(pagingCriteria, sm, null,null,hqlQuery, hqlCount);
    }
	
	public DTOCollection getPagedQuery(StatelessSessionManager sm, PagingCriteria pagingCriteria, Query  hqlQuery, Query  hqlCount){
    	Tracer.debug(getClass().getName(),"getPagedQuery", "PAGINAZIONE SEMPLICE");
    	simplePager = new SimpleQueryListPager();    
    	return  simplePager.getPagedQuery(pagingCriteria, sm, hqlQuery, hqlCount);
    }

	public DTOCollection getPagedQuery(StatelessSessionManager sm, PagingCriteria pagingCriteria, String hqlStringQuery, QueryParams queryParams){
    	Tracer.debug(getClass().getName(),"getPagedQuery", "PAGINAZIONE SEMPLICE");
    	simplePager = new SimpleQueryListPager();    
    	Query hqlQuery = sm.retrieveQuery(hqlStringQuery, queryParams);
   		return simplePager.getPagedQuery(pagingCriteria, sm, hqlQuery);
    }

	public DTOPo getDTOPoPagedQuery(StatelessSessionManager sm,PagingCriteria pagingCriteria , String hqlStringQuery, QueryParams queryParams){
    	Tracer.debug(getClass().getName(),"getDTOPoPagedQuery", "PAGINAZIONE SEMPLICE");
    	simplePager = new SimpleQueryListPager();    
    	Query hqlQuery = sm.retrieveQuery(hqlStringQuery, queryParams);
    	return  simplePager.getDTOPoPagedQuery(pagingCriteria, sm, hqlQuery);
    }

	public DTOPo getDTOPoPagedQueryByCriteria(StatelessSessionManager sm,PagingCriteria pagingCriteria , Criteria criteria){
    	Tracer.debug(getClass().getName(),"getDTOPoPagedQueryByCriteria", "PAGINAZIONE SEMPLICE");
    	simplePager = new SimpleQueryListPager();    
    	return  simplePager.getDTOPoPagedQueryByCriteria(pagingCriteria, sm,criteria);
    }
	
	public DTOCollection getPagedQueryByCriteria(StatelessSessionManager sm, PagingCriteria pagingCriteria, Criteria criteria){
    	Tracer.debug(getClass().getName(),"getPagedQueryByCriteria", "PAGINAZIONE SEMPLICE");
    	simplePager = new SimpleQueryListPager();  
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
    public DTOPo getDTOPoPagedQuery(StatelessSessionManager sm,PagingCriteria pagingCriteria , Query  hqlQuery, Query  hqlCount){
    	Tracer.debug(getClass().getName(),"getDTOPoPagedQuery", "PAGINAZIONE SEMPLICE");
    	simplePager = new SimpleQueryListPager();    
    	return  simplePager.getDTOPoPagedQuery(pagingCriteria, sm, hqlQuery, hqlCount);
    }

	
	public DTOCollection getPagedQuery(StatelessSessionManager sm, DTO dto, String hqlQuery, String hqlCount)
	{
    
		PagingCriteria pagingCriteria = dto.getPagingCriteria();
		DTOCollection DTOColl = null;
    	
		boolean enableCachedPaging = false;
		try 
		{    
	    	if (enableCachedPaging)
	        {
	            Tracer.debug(getClass().getName(),"getPagedQuery", "PAGINAZIONE CACHED");
	    		cachedPager = new CachedQueryListPager();    //TODO inject with SPRING
	            DTOColl = cachedPager.getPagedQuery(pagingCriteria, sm, hqlQuery, hqlCount); //, String hqlQuery, String hqlCount);   
	        }
	        else
	        { 
	        	Tracer.debug(getClass().getName(),"getPagedQuery", "PAGINAZIONE SEMPLICE");
	        	simplePager = new SimpleQueryListPager();    //TODO inject with SPRING
	            DTOColl = simplePager.getPagedQuery(pagingCriteria, sm, hqlQuery, hqlCount);
	        }
	    	
		} catch (HibernateException e) {
			throw new DasUncheckedException(null, e);
		}
    	return DTOColl;
    }
	
	  public void buildQuery(QueryParams queryParams, StringBuffer query, String condition, String key, Object value, Type hibernateType)
	   {
		   if (value instanceof java.lang.String)
		   {
			   if(value != null && ! ((String)value).trim().equals(""))
			   {
				   query.append(condition);
				   queryParams.put(key,value, hibernateType);
			   }
		   }
		   else {
			   if(value != null)
			   {
				   query.append(condition);
				   queryParams.put(key,value, hibernateType);
			   }
		   }
	   } 
	 protected boolean isEmpty(Object o) {
		 boolean res = false;
		 if (o == null) res = true;
		 if (o instanceof Collection) res = ((Collection)o).size() == 0;
		 if (o instanceof String) res = ((String)o).toString().length() == 0;
		 return res;
	 }
	public int destroy() {
		throw new UnsupportedOperationException();
	}
	
	// metodi generali
	
	public void saveObject() {}
	public void loadObject() {}
	
	
}
