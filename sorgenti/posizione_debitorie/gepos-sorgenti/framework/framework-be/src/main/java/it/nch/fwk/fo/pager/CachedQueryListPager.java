package it.nch.fwk.fo.pager;


import it.nch.fwk.fo.core.StatelessSessionManager;
import it.nch.fwk.fo.dto.DTOCollection;
import it.nch.fwk.fo.dto.DTOCollectionImpl;
import it.nch.fwk.fo.util.Tracer;

import java.util.ArrayList;
//import java.util.Collection;
import java.util.List;

//NOTE:
//The "AbsolutePosition" (queryList) is the position starting at the beginning of the list for the entire scope
//The "RecordPosition" (pagingInfo) is the position starting at the end of the current list for entire scope
//The "RelativePosition" is the position inside a range of 0-subsetsize
//IMPORTANT CONSTRAINT: that the resultsPerPage is NOT greater than the subsetsize.
//this works well with size around 10-20 and subset size 500, but when results size is over 200, doesnt work well.

public class CachedQueryListPager extends ListPager
{

    private PagingBuffer buffer = null;
    private QueryList queryList = null;
    private PagingData pagingData = null;
    private PagingCriteria pagingCriteria = null;
    private StatelessSessionManager sm = null;
    private String queryString = null;
    private String countQueryString = null;

    int newRelativePosition = 0;
    int untilPosition = 10;  //TODO CONFIGURABLE PARAM
 
    public CachedQueryListPager()
    {
    	Tracer.debug(getClass().getName(), "COSTRUTTORE", "ISTANZIO NUOVO OGGETTO");
    }

    public void removeQueryList(String userid)
    {
    	buffer = PagingBuffer.getInstance();    
    	QueryList cachedQueryList = buffer.getQueryList(userid);
        if(cachedQueryList != null)
        {
             buffer.removeQueryList(userid);
        }
    } 

    //public PagedList getPagedList(PagingInfo pagingInfo, String hql, String hqlcount, Session hsession, String userid)
    public DTOCollection getPagedQuery(PagingCriteria pagingCriteria, StatelessSessionManager sm, String queryString, String countQueryString)
    {

        this.pagingCriteria = pagingCriteria;
        this.queryString = queryString;
        this.countQueryString = countQueryString;
        this.sm = sm;
        pagingData = new PagingData();
        //int firstResult = -1;
        //int maxResults = -1;
        //int newRecordPosition = 0;
        //Collection resultColl = null;
        DTOCollection dtoCollection = null;
        List result = null;
    	
        pagingData.setResultsPerPage(pagingCriteria.getResultsPerPage());
        
        int recordPosition = pagingCriteria.getRecordPosition();
        if (recordPosition < 0)
        	recordPosition = 0;
    	pagingData.setRecordPosition(recordPosition);
	    pagingData.setNumTotalRecords(pagingCriteria.getNumTotalRecords());
	    
	    buffer = PagingBuffer.getInstance();

        //create new QueryList and initialize
        queryList = buffer.getQueryList(pagingCriteria.getUserid());

        //if the user already has a queryList in the buffer for a different query, overwrite it
        
        //comment only for testing
        if((queryList == null) || (!queryList.getQueryString().equals(queryString))) // || pagingCriteria.getRecordPosition() == 0)
        {
        	Tracer.warn(getClass().getName(), "getPagedQuery", "MOSTRO PAGING DATA PRIMO ACCESSO");        	
        	//Tracer.warn(getClass().getName(), "getPagedQuery", "getNumPagesNext["+pagingData.getNumPagesNext()+"]");
        	Tracer.warn(getClass().getName(), "getPagedQuery", "getRecordPosition()["+pagingCriteria.getRecordPosition()+"]");
            Tracer.warn(getClass().getName(), "getPagedQuery", "getNumPagesPerPage["+pagingData.getNumPagesPerPage()+"]");
            Tracer.warn(getClass().getName(), "getPagedQuery", "getNumPagesPrev["+pagingData.getNumPagesPrev()+"]");
            Tracer.warn(getClass().getName(), "getPagedQuery", "getNumRecordsNext["+pagingData.getNumRecordsNext()+"]");
            Tracer.warn(getClass().getName(), "getPagedQuery", "getNumRecordsPrev["+pagingData.getNumRecordsPrev()+"]");
            Tracer.warn(getClass().getName(), "getPagedQuery", "getNumTotalPages["+pagingData.getNumTotalPages()+"]");
            Tracer.warn(getClass().getName(), "getPagedQuery", "getNumTotalRecords["+pagingData.getNumTotalRecords()+"]");
            Tracer.warn(getClass().getName(), "getPagedQuery", "getPageNumber["+pagingData.getPageNumber()+"]");
            Tracer.warn(getClass().getName(), "getPagedQuery", "getRecordPosition["+pagingData.getRecordPosition()+"]");
            Tracer.warn(getClass().getName(), "getPagedQuery", "getResultsPerPage["+pagingData.getResultsPerPage()+"]");                    
                    	
            result = createFirstQueryList();
        }
        //retrieve QueryList from Buffer
        else
        {
        	//Tracer.warn(getClass().getName(), "getPagedQuery", "queryString["+queryString+"]queryList.getQueryString()["+queryList.getQueryString()+"]");
        	if(pagingCriteria.getDirection().equals("next"))
            {
            	Tracer.info(getClass().getName(), "getPagedQuery", "DIRECTION NEXT");
                result = goNext();
            }
            else if(pagingCriteria.getDirection().equals("prev"))
            {
            	Tracer.info(getClass().getName(), "getPagedQuery", "DIRECTION PREV");
                result = goPrevious();
            }
            else if(pagingCriteria.getDirection().equals("none"))
            {
            	Tracer.info(getClass().getName(), "getPagedQuery", "DIRECTION NONE");
                result = goChangePage();
            }
            else if (pagingCriteria.getDirection().equals(""))
            {
            	Tracer.info(getClass().getName(), "getPagedQuery", "RIFACCIO LA RICERCA");
            	result = createFirstQueryList();
            }
            
            queryList.setAbsolutePosition((queryList.getSubsetSize() * queryList.getSubsetPosition()) + newRelativePosition);
            queryList.setResultsPerPage(pagingData.getResultsPerPage());
            
            //just to print for testing
            /*List[] buffersubsets = queryList.getSubsets();
            for(int i=0;i<buffersubsets.length;i++)
            {
                if (buffersubsets[i] == null)
                    Tracer.debug(getClass().getName(), "getPagedQuery", i + " " + "null");
                    
                else
                    Tracer.debug(getClass().getName(), "getPagedQuery", i + " " + "list");
            }*/
        }
        
        
        Tracer.warn(getClass().getName(), "getPagedQuery", "MOSTRO QUERY LIST");
        Tracer.warn(getClass().getName(), "getPagedQuery", "QUERY LIST getAbsolutePosition()["+queryList.getAbsolutePosition()+"]");
        Tracer.warn(getClass().getName(), "getPagedQuery", "QUERY LIST getMaxRecords()["+queryList.getMaxRecords()+"]");
        Tracer.warn(getClass().getName(), "getPagedQuery", "QUERY LIST getNumSubsets()["+queryList.getNumSubsets()+"]");
        Tracer.warn(getClass().getName(), "getPagedQuery", "QUERY LIST getQueryString()["+queryList.getQueryString()+"]");
        Tracer.warn(getClass().getName(), "getPagedQuery", "QUERY LIST getResultsPerPage()["+queryList.getResultsPerPage()+"]");
        Tracer.warn(getClass().getName(), "getPagedQuery", "QUERY LIST getSubsetPosition()["+queryList.getSubsetPosition()+"]");
        Tracer.warn(getClass().getName(), "getPagedQuery", "QUERY LIST getSubsetSize()["+queryList.getSubsetSize()+"]");
        Tracer.warn(getClass().getName(), "getPagedQuery", "QUERY LIST getUserId()["+queryList.getUserId()+"]");
        
        Tracer.info(getClass().getName(), "getPagedQuery", "result["+result+"]");

        Tracer.warn(getClass().getName(), "getPagedQuery", "MOSTRO ANCORA PAGING DATA PRIMA DI LAVORARCI");
        Tracer.warn(getClass().getName(), "getPagedQuery", "*** getNumPagesNext["+pagingData.getNumPagesNext()+"]");
        Tracer.warn(getClass().getName(), "getPagedQuery", "*** getNumPagesPerPage["+pagingData.getNumPagesPerPage()+"]");
        Tracer.warn(getClass().getName(), "getPagedQuery", "*** getNumPagesPrev["+pagingData.getNumPagesPrev()+"]");
        Tracer.warn(getClass().getName(), "getPagedQuery", "*** getNumRecordsNext["+pagingData.getNumRecordsNext()+"]");
        Tracer.warn(getClass().getName(), "getPagedQuery", "*** getNumRecordsPrev["+pagingData.getNumRecordsPrev()+"]");
        Tracer.warn(getClass().getName(), "getPagedQuery", "*** getNumTotalPages["+pagingData.getNumTotalPages()+"]");
        Tracer.warn(getClass().getName(), "getPagedQuery", "*** getNumTotalRecords["+pagingData.getNumTotalRecords()+"]");
        Tracer.warn(getClass().getName(), "getPagedQuery", "*** getPageNumber["+pagingData.getPageNumber()+"]");
        Tracer.warn(getClass().getName(), "getPagedQuery", "*** getRecordPosition["+pagingData.getRecordPosition()+"]");
        Tracer.warn(getClass().getName(), "getPagedQuery", "*** getResultsPerPage["+pagingData.getResultsPerPage()+"]");
        
         //KEY THING TO REMEMBER: record position in this class is held in the cache!!! (in queryList)
         //also held in the pagingData too.
         pagingData.setRecordPosition(queryList.getAbsolutePosition() + pagingData.getResultsPerPage());

        //only if pagingEnabled populate PagingData with parameters
        if (pagingCriteria.getEnablePaging())
            pagingData = calculatePagingData(pagingData); 

        //CREATE PAGENUMBERS LIST
        pagingData = createPageNumbersList(pagingData);
        //end paging calculations                
        
        dtoCollection = new DTOCollectionImpl(result,true);
        
        Tracer.warn(getClass().getName(), "getPagedQuery", "MOSTRO ANCORA PAGING DATA DOPO QUERY");
        Tracer.warn(getClass().getName(), "getPagedQuery", "*** getNumPagesNext["+pagingData.getNumPagesNext()+"]");
        Tracer.warn(getClass().getName(), "getPagedQuery", "*** getNumPagesPerPage["+pagingData.getNumPagesPerPage()+"]");
        Tracer.warn(getClass().getName(), "getPagedQuery", "*** getNumPagesPrev["+pagingData.getNumPagesPrev()+"]");
        Tracer.warn(getClass().getName(), "getPagedQuery", "*** getNumRecordsNext["+pagingData.getNumRecordsNext()+"]");
        Tracer.warn(getClass().getName(), "getPagedQuery", "*** getNumRecordsPrev["+pagingData.getNumRecordsPrev()+"]");
        Tracer.warn(getClass().getName(), "getPagedQuery", "*** getNumTotalPages["+pagingData.getNumTotalPages()+"]");
        Tracer.warn(getClass().getName(), "getPagedQuery", "*** getNumTotalRecords["+pagingData.getNumTotalRecords()+"]");
        Tracer.warn(getClass().getName(), "getPagedQuery", "*** getPageNumber["+pagingData.getPageNumber()+"]");
        Tracer.warn(getClass().getName(), "getPagedQuery", "*** getRecordPosition["+pagingData.getRecordPosition()+"]");
        Tracer.warn(getClass().getName(), "getPagedQuery", "*** getResultsPerPage["+pagingData.getResultsPerPage()+"]");
        
        dtoCollection.setPagingData(pagingData);
        return dtoCollection; 
   }


   private List createFirstQueryList()
   {
   	   int firstResult = -1;
       int maxResults = pagingCriteria.getNumTotalRecords(); //100; //-1;
       queryList = new QueryList();
       
       queryList.setUserId(pagingCriteria.getUserid());
       queryList.setAbsolutePosition(0);
       queryList.setResultsPerPage(pagingData.getResultsPerPage());
       queryList.setSubsetSize(500);  //(500);     //TODO CONFIGURABLE PARAM
       queryList.setQueryString(queryString);  

       //TODO count the records
       //(1)
       int queryCount = sm.count(countQueryString);
       int count = queryCount < maxResults ? queryCount : maxResults;
       pagingData.setNumTotalRecords(count); 
       
       //count = sm.count(countQueryString);               
       
       //pagingData.setNumTotalRecords(maxResults);  //(count); //NOOOOOOOO DEVONO ESSERE TANTI QUANTI DA MAX!!
       
       if(pagingData.getResultsPerPage() > pagingData.getNumTotalRecords())
       {
       		pagingData.setResultsPerPage(pagingData.getNumTotalRecords());   //???
       		queryList.setResultsPerPage(pagingData.getNumTotalRecords());
       }
       		
       int numsubsets = queryList.getMaxRecords() / queryList.getSubsetSize();
       if ((numsubsets == 0) || (queryList.getMaxRecords() % queryList.getSubsetSize() != 0))
           numsubsets++;
       queryList.setNumSubsets(numsubsets);

       firstResult = queryList.getAbsolutePosition(); 
       maxResults = pagingCriteria.getNumTotalRecords() < queryList.getSubsetSize() ? pagingCriteria.getNumTotalRecords() : queryList.getSubsetSize();

       //Tracer.info(getClass().getName(),"createFirstQueryList","firstResult["+firstResult+"]maxResults["+maxResults+"]");
       List currentSubset = (List)sm.createQuery(queryString,firstResult,maxResults);
       //Tracer.info(getClass().getName(), "createFirstQueryList", "DIMENSIONE BUFFER["+currentSubset.size()+"]");
       
       //initialize the subset structure.
       //in the beginning, only the first subset will be present in the subset list.
       queryList.setSubsetPosition(0);
       List [] subsets = new List[numsubsets];
       subsets[0] = currentSubset;
       for (int i = 1; i < numsubsets; i++) {
           subsets[i] = null;
       }

       queryList.setSubsets(subsets); 
       buffer.addQueryList(pagingCriteria.getUserid(), queryList);       
       //queryList.getResult().size();                    
       
       //now return the first sublist just for the first time
       int currentSubsetSize = currentSubset.size();
       
       //(2)
       int subListEnd = queryList.getResultsPerPage() < currentSubsetSize ? queryList.getResultsPerPage() : currentSubsetSize;       
       List result = currentSubset.subList(queryList.getAbsolutePosition(), subListEnd);
       
       return new ArrayList(result); 
   }

   private List goNext()
   {

   		  List result = null;      
   	      newRelativePosition = (queryList.getAbsolutePosition() - (queryList.getSubsetSize() * queryList.getSubsetPosition())) + queryList.getResultsPerPage();
          untilPosition = newRelativePosition + pagingData.getResultsPerPage();

          //moving on to the next subset with OVERLAP
          if ((untilPosition > queryList.getSubsetSize()) &&
              (newRelativePosition <= queryList.getSubsetSize()))
          {
        	  //Tracer.info(getClass().getName(),"goNext","VADO A createNextOverlapQueryList");
              result = createNextOverlapQueryList();
          }
          //move to new subset NO OVERLAP. rule is that both indexes have to be out of bounds of current subset
          else if ((untilPosition > queryList.getSubsetSize()) &&
                   (newRelativePosition >= queryList.getSubsetSize()))
          {
        	  //Tracer.info(getClass().getName(),"goNext","VADO A createNewSubsetQueryList");
        	  result = createNewSubsetQueryList();
          }
          //staying in the same subset
          else
          {
        	  //Tracer.info(getClass().getName(),"goNext","VADO A createNextQueryList");
              result = createNextQueryList();
          }
          return result;
   }

   private List goPrevious()
   {
   		List result = null;  
   		newRelativePosition = (queryList.getAbsolutePosition() - (queryList.getSubsetSize() * queryList.getSubsetPosition())) - pagingData.getResultsPerPage();
        untilPosition = (queryList.getAbsolutePosition() - (queryList.getSubsetSize() * queryList.getSubsetPosition()));

        if((newRelativePosition < 0) && (untilPosition > 0) &&
           (queryList.getAbsolutePosition() > queryList.getSubsetSize())) 
        {
            result = createPrevOverlapQueryList();
        }
        else if (queryList.getAbsolutePosition() % queryList.getSubsetSize() == 0)
        {
           result = createPrevNewSubsetQueryList();
        }
        else
        {
            //staying in the same subset
            result = createPrevQueryList();
        }
        return result;
   }

   private List goChangePage()
   {
       List result = null;
       List currentSubset = null;
       int firstResult = -1;
       int maxResults = -1;
       
       /*Tracer.warn(getClass().getName(), "goChangePage", "MOSTRO PAGING DATA CHE ARRIVANO");
       Tracer.warn(getClass().getName(), "goChangePage", "getNumPagesNext["+pagingData.getNumPagesNext()+"]");
       Tracer.warn(getClass().getName(), "goChangePage", "getNumPagesPerPage["+pagingData.getNumPagesPerPage()+"]");
       Tracer.warn(getClass().getName(), "goChangePage", "getNumPagesPrev["+pagingData.getNumPagesPrev()+"]");
       Tracer.warn(getClass().getName(), "goChangePage", "getNumRecordsNext["+pagingData.getNumRecordsNext()+"]");
       Tracer.warn(getClass().getName(), "goChangePage", "getNumRecordsPrev["+pagingData.getNumRecordsPrev()+"]");
       Tracer.warn(getClass().getName(), "goChangePage", "getNumTotalPages["+pagingData.getNumTotalPages()+"]");
       Tracer.warn(getClass().getName(), "goChangePage", "getNumTotalRecords["+pagingData.getNumTotalRecords()+"]");
       Tracer.warn(getClass().getName(), "goChangePage", "getPageNumber["+pagingData.getPageNumber()+"]");
       Tracer.warn(getClass().getName(), "goChangePage", "getRecordPosition["+pagingData.getRecordPosition()+"]");
       Tracer.warn(getClass().getName(), "goChangePage", "getResultsPerPage["+pagingData.getResultsPerPage()+"]");  */                  
       
       //pagingData.setNumPagesNext(3);
       // ** pagingData.setNumPagesPerPage();
       //pagingData.setNumPagesPrev(0);
       //pagingData.setNumRecordsNext(70);
       //pagingData.setNumRecordsPrev(0);
       //pagingData.setNumTotalPages(4);
       // #pagingData.setNumTotalRecords(100);
       //pagingData.setPageNumber(0);
       // #pagingData.setRecordPosition(30);
       // #pagingData.setResultsPerPage(30);
       
      /* Tracer.warn(getClass().getName(), "goChangePage", "MOSTRO PAGING DATA RISETTATI DA ME");
       Tracer.warn(getClass().getName(), "goChangePage", "getNumPagesNext["+pagingData.getNumPagesNext()+"]");
       Tracer.warn(getClass().getName(), "goChangePage", "getNumPagesPerPage["+pagingData.getNumPagesPerPage()+"]");
       Tracer.warn(getClass().getName(), "goChangePage", "getNumPagesPrev["+pagingData.getNumPagesPrev()+"]");
       Tracer.warn(getClass().getName(), "goChangePage", "getNumRecordsNext["+pagingData.getNumRecordsNext()+"]");
       Tracer.warn(getClass().getName(), "goChangePage", "getNumRecordsPrev["+pagingData.getNumRecordsPrev()+"]");
       Tracer.warn(getClass().getName(), "goChangePage", "getNumTotalPages["+pagingData.getNumTotalPages()+"]");
       Tracer.warn(getClass().getName(), "goChangePage", "getNumTotalRecords["+pagingData.getNumTotalRecords()+"]");
       Tracer.warn(getClass().getName(), "goChangePage", "getPageNumber["+pagingData.getPageNumber()+"]");
       Tracer.warn(getClass().getName(), "goChangePage", "getRecordPosition["+pagingData.getRecordPosition()+"]");
       Tracer.warn(getClass().getName(), "goChangePage", "getResultsPerPage["+pagingData.getResultsPerPage()+"]");*/
       
       
   	   queryList.setAbsolutePosition(pagingData.getRecordPosition() - pagingData.getResultsPerPage()); 

       int subsetpos = pagingData.getRecordPosition() / queryList.getSubsetSize();
       queryList.setSubsetPosition(subsetpos);

       newRelativePosition = (queryList.getAbsolutePosition() - (queryList.getSubsetSize() * queryList.getSubsetPosition())) + queryList.getResultsPerPage();
       untilPosition = newRelativePosition + pagingData.getResultsPerPage();

       List[] subsets = queryList.getSubsets();
 
       if(subsets == null || subsets[subsetpos] == null)
       {
           //load new subset
           firstResult = pagingData.getRecordPosition() - (pagingData.getRecordPosition() % queryList.getSubsetSize());
           maxResults = queryList.getSubsetSize();
           currentSubset = (List)sm.createQuery(queryString,firstResult,maxResults); 

           int numsubsets = 0;
           if(queryList.getMaxRecords() % queryList.getSubsetSize() == 0)
               numsubsets = queryList.getMaxRecords() / queryList.getSubsetSize();
           else
               numsubsets = queryList.getMaxRecords() / queryList.getSubsetSize() + 1;
           subsets = new List[numsubsets];
           for(int i=0; i<subsets.length; i++)
           {
               subsets[i] = null;
           }

           subsets[subsetpos] = currentSubset;
           queryList.setSubsets(subsets);
       }
       else
       {
           currentSubset = subsets[subsetpos];
       }

       if(untilPosition > queryList.getSubsetSize())
           result = createNextOverlapQueryList();
       else
           result = createNextQueryList();
       return result; 
   }

   private List createNextOverlapQueryList()
   {
       List[] subsets = queryList.getSubsets();
       List currentSubset = null;
       List nextSubset = null;
       List result = null;
       int firstResult = -1;
       int maxResults = -1;
       
       currentSubset = subsets[queryList.getSubsetPosition()];
       //assume the last subset is not the standard size
       result = currentSubset.subList(newRelativePosition, currentSubset.size());

       //if reached the end of the records
       if((queryList.getSubsetPosition()+1) == subsets.length)
           return result;

       //else load new subset
       firstResult = (queryList.getSubsetPosition() + 1) * queryList.getSubsetSize();
       maxResults = queryList.getSubsetSize();

       nextSubset = (List)sm.createQuery(queryString,firstResult,maxResults);
       
       subsets[queryList.getSubsetPosition() + 1] = nextSubset;
       queryList.setSubsets(subsets);

       int remainder = pagingData.getResultsPerPage() -
                       (queryList.getSubsetSize() - newRelativePosition);
       List result2 = nextSubset.subList(0, remainder);
       result.addAll(result2);
       return result;

   }

   private List createPrevNewSubsetQueryList()
   {
       //todo check if it is the first subset!!???
       int firstResult = -1;
       int maxResults = -1;
       List prevSubset = null;
       
       if(newRelativePosition < 0)
        {
            newRelativePosition = newRelativePosition + queryList.getSubsetSize();
            untilPosition = untilPosition + queryList.getSubsetSize();
        }

       List[] subsets = queryList.getSubsets();

        //load new subset
       firstResult = (queryList.getSubsetPosition() -1) * queryList.getSubsetSize();
       maxResults = queryList.getSubsetSize();
       prevSubset = (List)sm.createQuery(queryString,firstResult,maxResults);

       subsets[queryList.getSubsetPosition() - 1] = prevSubset;
       subsets[queryList.getSubsetPosition()] = null;
       queryList.setSubsets(subsets);
       queryList.setSubsetPosition(queryList.getSubsetPosition()-1);

       List resultPrev = prevSubset.subList(newRelativePosition, queryList.getSubsetSize());
       return resultPrev;

   }

   private List createPrevOverlapQueryList()
   {
       int firstResult = -1;
       int maxResults = -1;
       List prevSubset = null;

       List[] subsets = queryList.getSubsets();

       List currentSubset = subsets[queryList.getSubsetPosition()];

       //assume the first subset is the standard size
       List result = currentSubset.subList(0,untilPosition);

       //if reached the end of the records
       if(queryList.getSubsetPosition() == 0)
           return result;

       //load new subset
       firstResult = (queryList.getSubsetPosition() -1) * queryList.getSubsetSize();
       maxResults = queryList.getSubsetSize();

       prevSubset = (List)sm.createQuery(queryString,firstResult,maxResults);
       subsets[queryList.getSubsetPosition() -1] = prevSubset;
       queryList.setSubsets(subsets);
       queryList.setSubsetPosition(queryList.getSubsetPosition()-1);

       newRelativePosition = newRelativePosition + queryList.getSubsetSize();
       List resultPrev = prevSubset.subList(newRelativePosition, queryList.getSubsetSize());
       resultPrev.addAll(result);
       return resultPrev;

   }

    //here the query list has to be a contained inside the next subset.
   private List createNewSubsetQueryList()
   {
       //TODO throw an exception if the new result spans more than one subset
       List[] subsets = queryList.getSubsets();
       List currentSubset = null;
       List result = null;
       int firstResult = -1;
       int maxResults = -1;
       
       //clear prev subset
       subsets[queryList.getSubsetPosition()] = null;
       //unique case that the newRelativePosition is out of bounds
       int newSubsetPosition = queryList.getSubsetPosition()+1;

       newRelativePosition = newRelativePosition - queryList.getSubsetSize();
       untilPosition = untilPosition - queryList.getSubsetSize();

       if(subsets[newSubsetPosition] == null)
       {
       	   firstResult = (queryList.getSubsetPosition()) * queryList.getSubsetSize();
           maxResults = queryList.getSubsetSize();
       	   currentSubset = (List)sm.createQuery(queryString,firstResult,maxResults); 
           subsets[newSubsetPosition] = currentSubset;
       }
       else
       {
           currentSubset = subsets[newSubsetPosition];
       }
       queryList.setSubsetPosition(newSubsetPosition);

       result = currentSubset.subList(newRelativePosition,
                                      untilPosition);

       return new ArrayList(result);
   }

   private List createNextQueryList()
   {
       List[] subsets = queryList.getSubsets();
       List currentSubset = subsets[queryList.getSubsetPosition()];
       List result = null;
       if(untilPosition > currentSubset.size())
           untilPosition = currentSubset.size();
       result = currentSubset.subList(newRelativePosition,untilPosition);
       return result;
   }

   private List createPrevQueryList()
   {
       List[] subsets = queryList.getSubsets();
       List currentSubset = subsets[queryList.getSubsetPosition()];
       List result = null;
       //if the next subset hasn't been cancelled from the buffer then do it now
       if(((queryList.getSubsetPosition()+1) != subsets.length) &&
          (subsets[queryList.getSubsetPosition()+1] != null))
       {
           subsets[queryList.getSubsetPosition()+1] = null;
           queryList.setSubsets(subsets);
       }

       result = currentSubset.subList(newRelativePosition, untilPosition);

       return result;
   }

} 
