package it.nch.erbweb.business.dao.util;


import it.nch.fwk.fo.core.HibernateQueryLogger;
import it.nch.fwk.fo.dto.DTOCollection;
import it.nch.fwk.fo.dto.DTOCollectionImpl;
import it.nch.fwk.fo.dto.DTOPo;
import it.nch.fwk.fo.dto.DTOPoImpl;
import it.nch.fwk.fo.pager.ListPager;
import it.nch.fwk.fo.pager.PagingCriteria;
import it.nch.fwk.fo.pager.PagingData;

import java.util.Collection;
import java.util.HashSet;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;


public class SimpleQueryListPager extends ListPager
{ 

	static final String FIRST_COMMAND                                  = "first";
	static final String LAST_COMMAND                                   = "last";
	static final String PREV_COMMAND                                   = "prev";
	static final String NEXT_COMMAND                                   = "next";
	static final String PAGE_COMMAND                                   = "page";
	static final String CURRENT_COMMAND                                = "current";
	static final String PAGE_FIRST                             = "1";
	static final int    UNKNOWN_LAST_PAGE_NUMBER               = -2;
	static final int    FIRST_PAGE_NUMBER                      = 1;
	static final int    NUM_PAGES_FOR_SEGMENT = 2;
	
    public SimpleQueryListPager()
    {

    }

    public DTOCollection getPagedQuery(PagingCriteria pagingCriteria, Session sm, String queryString, String countQueryString){
    	return this.getPagedQuery(pagingCriteria, sm,queryString, countQueryString,null,null);    	
    }

    public DTOCollection getPagedQuery(PagingCriteria pagingCriteria, Session sm, Query  query,Query  countQuery){
    	return this.getPagedQuery(pagingCriteria, sm,null,null, query, countQuery);
    }
    
    public DTOCollection getPagedQuery(PagingCriteria pagingCriteria, Session sm, 
    															 String queryString, String countQueryString, Query  query,Query  countQuery)
    {
    	DTOCollection dtoCollection = null;
    	if (pagingCriteria!=null && pagingCriteria.getPaginationSmart() != null && pagingCriteria.getPaginationSmart().trim().equals("true")){
	    	DTOPo dtopo =  executePagedQueryLight(pagingCriteria, sm, queryString,  countQueryString,  query,countQuery);
		    dtoCollection = new DTOCollectionImpl((Collection)dtopo.getPersistenceObject(), true);
		    dtoCollection.setPagingData(dtopo.getPagingData());
		}else{
	    	DTOPo dtopo =  executePagedQuery(pagingCriteria, sm, queryString,  countQueryString,  query,countQuery);
		    dtoCollection = new DTOCollectionImpl((Collection)dtopo.getPersistenceObject(), true);
		    dtoCollection.setPagingData(dtopo.getPagingData());
		}
		return dtoCollection;
   }

    public DTOCollection getPagedQuery(PagingCriteria pagingCriteria, Session sm, Query  query)
	{
    	DTOCollection dtoCollection = null;
    	if (pagingCriteria.getPaginationSmart() != null && pagingCriteria.getPaginationSmart().trim().equals("true")){
			DTOPo dtopo = executePagedQueryLight(pagingCriteria, sm, null, null, query, null);
			dtoCollection = new DTOCollectionImpl((Collection)dtopo.getPersistenceObject(), true);
			dtoCollection.setPagingData(dtopo.getPagingData());
		}else{
	    	DTOPo dtopo = executePagedQuery(pagingCriteria, sm, null, null, query, null);
			dtoCollection = new DTOCollectionImpl((Collection)dtopo.getPersistenceObject(), true);
			dtoCollection.setPagingData(dtopo.getPagingData());
		}
		return dtoCollection;
    }

    public DTOCollection getPagedQueryByCriteria(PagingCriteria pagingCriteria, Session sm, Criteria  criteria)
	{
    	DTOCollection dtoCollection = null;
    	if (pagingCriteria.getPaginationSmart() != null && pagingCriteria.getPaginationSmart().trim().equals("true")){
			DTOPo dtopo = executePagedQueryLightByCriteria(pagingCriteria, sm, null, null, criteria, null);
			dtoCollection = new DTOCollectionImpl((Collection)dtopo.getPersistenceObject(), true);
			dtoCollection.setPagingData(dtopo.getPagingData());
		}else{
			throw new IllegalStateException("Illegal Paging type");
		}
		return dtoCollection;
    }

    public DTOPo getDTOPoPagedQueryByCriteria(PagingCriteria pagingCriteria, Session sm,  Criteria criteria)
	{
    	DTOPo dtopo = null;
    	if (pagingCriteria.getPaginationSmart() != null && pagingCriteria.getPaginationSmart().trim().equals("true")){
    		dtopo = executePagedQueryLightByCriteria(pagingCriteria, sm, null, null, criteria, null);
    	}else{
    		throw new IllegalStateException("Illegal Paging type");
    	}
        return dtopo;
	}
    
    public DTOPo getDTOPoPagedQuery(PagingCriteria pagingCriteria, Session sm,  Query  query,Query  countQuery)
	{
    	DTOPo dtopo = null;
    	if (pagingCriteria.getPaginationSmart() != null && pagingCriteria.getPaginationSmart().trim().equals("true")){
    		dtopo =  executePagedQueryLight(pagingCriteria, sm, null,null,  query,countQuery);
    	}else{
    		dtopo =  executePagedQuery(pagingCriteria, sm, null,null,  query,countQuery);
    	}
        return dtopo;
	}

    public DTOPo getDTOPoPagedQuery(PagingCriteria pagingCriteria, Session sm,  Query  query)
	{
    	DTOPo dtopo = null;
    	if (pagingCriteria.getPaginationSmart() != null && pagingCriteria.getPaginationSmart().trim().equals("true")){
    		dtopo =  executePagedQueryLight(pagingCriteria, sm, null,null,  query, null);
    	}else{
        	dtopo =  executePagedQuery(pagingCriteria, sm, null,null,  query, null);
    	}
        return dtopo;
	}

    private DTOPo  executePagedQuery(PagingCriteria pagingCriteria, Session sm, 
			 	String queryString, String countQueryString, Query  query,Query  countQuery)
	{
		PagingData pagingData = new PagingData();
		if (pagingCriteria== null)
		{
		pagingCriteria = new PagingCriteria(); 
		pagingCriteria.setEnablePaging(false);
		}	
		
		//the initial values
		int firstResult = -1;
		int maxResults = -1;
		int newRecordPosition = 0;
		
		Collection result = null;
		pagingData.setResultsPerPage(pagingCriteria.getResultsPerPage());
		pagingData.setRecordPosition(pagingCriteria.getRecordPosition());
		pagingData.setNumTotalRecords(pagingCriteria.getNumTotalRecords());
		
		if(pagingCriteria.getEnablePaging())
		{
		maxResults = pagingData.getResultsPerPage();
		firstResult = pagingData.getRecordPosition();
		}
		//count the total records only if it is the first time (still has initial value of -1)
		//TODO is it correct to count once, or should count every time, to avoid problems when records 
		//are inserted or deleted in the meantime?
		if(pagingData.getNumTotalRecords() == -1)
		{
		int count = -1;
		if (countQuery  != null) {
			Integer countResult = (Integer) countQuery.uniqueResult();
			new HibernateQueryLogger().debugSQL(sm.getSessionFactory(), countQuery);		
			count = (countResult == null) ? 0 : countResult.intValue();
		}
		else if (countQueryString != null) {		
			Collection c = sm.createQuery(countQueryString).list();
			Integer integer = (Integer)(c.iterator().next());
			count = integer.intValue();
		}
		else {
			//Attenzione: se il db non permette i cursori scrollabili il driver JDBC 
			ScrollableResults scrollableResultSet = query.scroll(); //query.scroll(ScrollMode.FORWARD_ONLY);
			scrollableResultSet.last();
			count = scrollableResultSet.getRowNumber();
            count++;
			scrollableResultSet.close();
		}
		
		pagingData.setNumTotalRecords(count);
		
		
		}
		
		//first time set the default values 0-10
		if(pagingData.getRecordPosition() == -1)
		{
		//firstTimeInitialize
		pagingData.setRecordPosition(0);
		firstResult = pagingData.getRecordPosition();
		newRecordPosition = pagingData.getRecordPosition() + pagingData.getResultsPerPage();
		pagingData.setPageNumber(1); //default is the first page               
		}
		//going to the next page, or directionless requests behave the same way
		else if(pagingCriteria.getDirection() == null ||
		pagingCriteria.getDirection().equals("next") ||
		pagingCriteria.getDirection().equals("none") ||
		pagingCriteria.getDirection().equals(""))
		{
//        else if(pagingCriteria.getDirection() != null && pagingCriteria.getDirection().equals("next")) {
		//goNext
		newRecordPosition = pagingData.getRecordPosition() + pagingData.getResultsPerPage();
		}
		else if (pagingCriteria.getDirection() != null && pagingCriteria.getDirection().equals("prev"))
		{
		//goPrevious
		firstResult = pagingData.getRecordPosition() - (pagingData.getResultsPerPage() * 2);
		newRecordPosition = (pagingData.getRecordPosition() - pagingData.getResultsPerPage());
		if (newRecordPosition <= 0)
			newRecordPosition = pagingData.getResultsPerPage();
		}
		
		if (query  != null ){
			
//TODO check this; allowed load of all entries 

		// Add	
			if(pagingCriteria.getEnablePaging()){
				query.setFirstResult(firstResult);
				query.setMaxResults(maxResults);
			}
					
	   // End Add
					

//		Preavious code
					
//			query.setFirstResult(firstResult);
//			query.setMaxResults(maxResults);
					
			result =query.list();
		}else{
						
//TODO check this 

		// Add	
			if(pagingCriteria.getEnablePaging()){
				Query q = sm.createQuery(queryString);
				if (firstResult > -1)
					q.setFirstResult(firstResult);
				if (maxResults > -1)
					q.setMaxResults(maxResults);
				result = q.list();
			}else{
				result = sm.createQuery(queryString).list();
			}
		// End Add
							

//		Preavious code
					
//			result = sm.createQuery(queryString, firstResult, maxResults);
		}	
		
		
		//set pagingData values now to be returned to front end
		
		pagingData.setRecordPosition(newRecordPosition);
		
		//only if pagingEnabled populate pagingInfo with parameters
		if(pagingCriteria.getEnablePaging()){
			pagingData = calculatePagingData(pagingData);
		}
	
//TODO check this; imho if a null pagingCriteria is passed the pagingData returnd should be has
//				   1 as number of page and total records number as page size
		
//	Add	
		else{
			pagingData.setResultsPerPage(pagingData.getNumTotalRecords());
		}
// End Add		

		//CREATE PAGENUMBERS LIST
		pagingData = createPageNumbersList(pagingData);
		
		DTOPo dtopo = new DTOPoImpl();
		dtopo.setPersistenceObject(result);
		dtopo.setPagingData(pagingData);
		
		return dtopo;

	}

    private DTOPo  executePagedQueryLight(PagingCriteria pagingCriteria, Session sm, 
		 	String queryString, String countQueryString, Query  query,Query  countQuery){
		
    	PagingData pagingData = new PagingData();	
    	Collection result = this.executePaginationQuery(pagingCriteria,sm,queryString,countQueryString,query,countQuery,pagingData);
    	while ( (result == null || result.size() == 0) && (pagingData.getPageNumber() > FIRST_PAGE_NUMBER) ){
    		{
    			pagingCriteria.setNumNextPage(pagingData.getPageNumber() - 1);
    			pagingCriteria.setDirection(PAGE_COMMAND);
    			result = this.executePaginationQuery(pagingCriteria,sm,queryString,countQueryString,query,countQuery,pagingData);
    		}
    	}	
    	DTOPo dtopo = new DTOPoImpl();
		dtopo.setPersistenceObject(result);
		dtopo.setPagingData(pagingData);
		return dtopo;
    	
	}
   	
    private DTOPo  executePagedQueryLightByCriteria(PagingCriteria pagingCriteria, Session sm, 
		 	String queryString, String countQueryString, Criteria  criteria,Query  countQuery){
		
    	PagingData pagingData = new PagingData();	
    	Collection result = this.executePaginationQueryByCriteria(pagingCriteria,sm,queryString,countQueryString,criteria,countQuery,pagingData);
    	while ( (result == null || result.size() == 0) && (pagingData.getPageNumber() > FIRST_PAGE_NUMBER) ){
    		{
    			pagingCriteria.setNumNextPage(pagingData.getPageNumber() - 1);
    			pagingCriteria.setDirection(PAGE_COMMAND);
    			result = this.executePaginationQueryByCriteria(pagingCriteria,sm,queryString,countQueryString,criteria,countQuery,pagingData);
    		}
    	}	
    	DTOPo dtopo = new DTOPoImpl();
		dtopo.setPersistenceObject(result);
		dtopo.setPagingData(pagingData);
		return dtopo;
    	
	}

    private Collection  executePaginationQueryByCriteria(PagingCriteria pagingCriteria, Session sm, 
		 	String queryString, String countQueryString, Criteria  criteria,Query  countQuery,PagingData pagingData){
    	return this.executePagination(pagingCriteria, sm, null, null,null,null, pagingData,criteria);
    }
    private Collection  executePaginationQuery(PagingCriteria pagingCriteria, Session sm, 
    		 	String queryString, String countQueryString, Query  query,Query  countQuery,PagingData pagingData){
    	return this.executePagination(pagingCriteria, sm, queryString, countQueryString, query, countQuery, pagingData, null);
    }
    private Collection  executePagination(PagingCriteria pagingCriteria, Session sm, 
		 	String queryString, String countQueryString, Query  query,Query  countQuery,PagingData pagingData,Criteria criteria){

		Collection result = new HashSet(); 
		if (pagingCriteria == null) {
    		//ManageFrameworkException.processBusinessException("FW_001");
    	}	
		int numPage = -1;
		int elementsCount = -1;
		int firstResult = 0;
		int maxResults = pagingCriteria.getResultsPerPage();
		int lastPage = -1; 
		int count = -1;
		if (countQuery  != null) {
			Integer countResult = (Integer) countQuery.uniqueResult();
			new HibernateQueryLogger().debugSQL(sm.getSessionFactory(), countQuery);		
			count = (countResult == null) ? 0 : countResult.intValue();
		}
		else if (countQueryString != null) {		
			Collection c = sm.createQuery(countQueryString).list();
			Integer integer = (Integer)(c.iterator().next());
			count = integer.intValue();
		}
		else {
			//Attenzione: se il db non permette i cursori scrollabili il driver JDBC
			ScrollableResults scrollableResultSet = null;
			if (query != null)
				scrollableResultSet = query.scroll(); //query.scroll(ScrollMode.FORWARD_ONLY);
			else
				scrollableResultSet = criteria.scroll();
			
			scrollableResultSet.last();
			elementsCount = scrollableResultSet.getRowNumber();
			elementsCount++;
			scrollableResultSet.close();
		}
		lastPage = computePageNumber(elementsCount,maxResults);
	
		if (pagingCriteria.getNumCurrentPage() > 0)
			numPage = pagingCriteria.getNumCurrentPage();
		else
			numPage = FIRST_PAGE_NUMBER;
		String paginationCommand = pagingCriteria.getDirection(); 
		if (paginationCommand == null)
			paginationCommand = new String("");
	    if (paginationCommand.equalsIgnoreCase(CURRENT_COMMAND)) {
	    }
	    else if (paginationCommand.equalsIgnoreCase(NEXT_COMMAND)) {
	        numPage++;
	    }
	    else if (paginationCommand.equalsIgnoreCase(PREV_COMMAND)) {
	        numPage--;
	    }
	    else if (paginationCommand.equalsIgnoreCase(FIRST_COMMAND)  || 
	    		 paginationCommand.equalsIgnoreCase("")) {
	        numPage = FIRST_PAGE_NUMBER;
	    }
	    else if (paginationCommand.equalsIgnoreCase(PAGE_COMMAND)) {
	    	if (pagingCriteria.getNumNextPage() > 0)
	    		numPage = pagingCriteria.getNumNextPage();
	    	//else
	    		//ManageFrameworkException.processBusinessException("FW_001");
	    }
	    else if (paginationCommand.equalsIgnoreCase(LAST_COMMAND)) {
	        numPage = lastPage;
	    }
	    if (numPage > lastPage ) 
	    	numPage = lastPage;
	    
	    if (numPage > 0) {
	    	if (pagingCriteria.getRecordPosition() >0)
	    		firstResult=pagingCriteria.getRecordPosition();
	    	else
	    		firstResult = ((numPage - 1) * maxResults);
	    	if (criteria == null){
		    	if (queryString  != null  && queryString.trim().length() > 0){
					Query q = sm.createQuery(queryString);					
					if (firstResult > -1)
						q.setFirstResult(firstResult);
					if (maxResults > -1)
						q.setMaxResults(maxResults);					
					result = q.list();
				}else{
					query.setFirstResult(firstResult);
					query.setMaxResults(maxResults);
					result = query.list();
				}	
	    	}else{
				criteria.setFirstResult(firstResult);
				criteria.setMaxResults(maxResults);
				result =criteria.list();
	    	}
	    }	
		pagingData.setNumTotalPages(lastPage);
		pagingData.setNumTotalRecords(elementsCount);
		pagingData.setNumPagesPerPage(lastPage);
		pagingData.setRecordPosition(firstResult + maxResults);
        pagingData.setPageNumber(numPage);
        pagingData.setResultsPerPage(maxResults);
        pagingData = calculatePagingDataLight(pagingData);
       	pagingData = createPageNumbersList(pagingData);
		return result;
    }	

    
    
    private int computePageNumber(int elementsCount, int pageSize) {
	    int numPage = -1;
	    numPage = (int) (Math.floor((double) elementsCount / (double) pageSize));
	    if ((elementsCount % pageSize) != 0)
	        numPage++;
	    return numPage;
	}

}
