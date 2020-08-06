package it.nch.fwk.fo.pager;

import java.util.ArrayList;
import java.util.Collection;

public abstract class ListPager{
    
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
	
	public PagingData calculatePagingData(PagingData pagingData){
        //TOTAL NUMBER OF PAGES
        if ((pagingData.getNumTotalRecords() % pagingData.getResultsPerPage()) == 0)
            pagingData.setNumTotalPages(pagingData.getNumTotalRecords() /
            		pagingData.getResultsPerPage());
        else
            pagingData.setNumTotalPages((pagingData.getNumTotalRecords() /
            		pagingData.getResultsPerPage()) + 1);
        //if changed the results per page there will be an extra page
        if (pagingData.getRecordPosition() % pagingData.getResultsPerPage() != 0)
            pagingData.setNumTotalPages(pagingData.getNumTotalPages() + 1);

        //NUMBER OF RECORDS REMAINING
        if (pagingData.getRecordPosition() < pagingData.getNumTotalRecords())
            pagingData.setNumRecordsNext(pagingData.getNumTotalRecords() -
                                         pagingData.getRecordPosition());
        else
            pagingData.setNumRecordsNext(0);

        //NUMBER OF PREVIOUS RECORDS, CURRENT PAGE NUMBER
        if (pagingData.getRecordPosition() > pagingData.getResultsPerPage()) {
            pagingData.setNumRecordsPrev(pagingData.getRecordPosition() -
            		pagingData.getResultsPerPage());
            if (pagingData.getRecordPosition() % pagingData.getResultsPerPage() == 0)
                pagingData.setPageNumber(pagingData.getRecordPosition() /
                		pagingData.getResultsPerPage());
            else
                pagingData.setPageNumber((pagingData.getRecordPosition() /
                		pagingData.getResultsPerPage()) + 1);
        } else if (pagingData.getRecordPosition() < pagingData.getResultsPerPage()) {
            pagingData.setNumRecordsPrev(pagingData.getRecordPosition());
            pagingData.setPageNumber(1);
        } else if (pagingData.getRecordPosition() == pagingData.getResultsPerPage()) {
            pagingData.setNumRecordsPrev(0);
            pagingData.setPageNumber(1);
        }
        //NUMBER OF PAGES REMAINING
        if (pagingData.getPageNumber() < pagingData.getNumTotalPages())
            pagingData.setNumPagesNext(pagingData.getNumTotalPages() -
                                       pagingData.getPageNumber());
        else
            pagingData.setNumPagesNext(0);
        //NUMBER OF PREVIOUS PAGES
        if (pagingData.getPageNumber() > 1)
            pagingData.setNumPagesPrev(pagingData.getPageNumber() - 1);
        else
            pagingData.setNumPagesPrev(0);
        
        return pagingData;
    }
    

    public PagingData createPageNumbersList(PagingData pagingData) {
    	Collection pages = new ArrayList();
        int begin = -1;
        int end = -1;
        
        int currentPage = pagingData.getPageNumber();
        int totalPages  = pagingData.getNumTotalPages();
        int rimanentiSx = NUM_PAGES_FOR_SEGMENT;
        int aggiuntiSx = 0;
        int rimanentiDx = NUM_PAGES_FOR_SEGMENT;
        int aggiuntiDx = 0;
        if (totalPages > 0){
	        for (int i=1;i<=NUM_PAGES_FOR_SEGMENT;i++){
	        	if (currentPage - aggiuntiSx - 1 > 0){
	        		aggiuntiSx++;
	        		rimanentiSx--;
	        	}else
	        		break;
	        }
	        for (int i=1;i<=NUM_PAGES_FOR_SEGMENT;i++){
	        	if (currentPage + aggiuntiDx + 1 <= totalPages){
	        		aggiuntiDx++;
	        		rimanentiDx--;
	        	}else
	        		break;
	        }
	        if (!(rimanentiDx == 0  && rimanentiSx == 0)){
	        	if (!(rimanentiDx > 0  && rimanentiSx > 0)){
	                for (int i=1;i<=rimanentiSx;i++){
	                	if (currentPage + aggiuntiDx + 1 <= totalPages){
	                		aggiuntiDx++;
	                	}else
	                		break;
	                }
	        		for (int i=1;i<=rimanentiDx;i++){
	            	if (currentPage - aggiuntiSx - 1 > 0){
	            		aggiuntiSx++;
	            	}else
	            		break;
	        		}
	        	}	
	        }
	        begin = currentPage - 	aggiuntiSx;
	        end =  currentPage + aggiuntiDx;
	        Page page = new Page();
	        for (int i = begin; i <= end; i++) {
	            page = new Page();
	            page.setNumPage(i);
	            page.setLabel(i + "");
	            if (pagingData.getPageNumber() == i)
	                page.setIsSelected(true);
	
	            //this works!! (but no offsets calculated)
	            int pos = (i * pagingData.getResultsPerPage()) - pagingData.getResultsPerPage();
	
	            page.setPosition(pos);
	            if (pos < pagingData.getNumTotalRecords())
	                pages.add(page);
	        }
        }
        pagingData.setPageNumbers(pages);
        return pagingData; 
    }

    public PagingData calculatePagingDataLight(PagingData pagingData){
        //if changed the results per page there will be an extra page
        //NUMBER OF RECORDS REMAINING
        if (pagingData.getRecordPosition() < pagingData.getNumTotalRecords())
            pagingData.setNumRecordsNext(pagingData.getNumTotalRecords() -
                                         pagingData.getRecordPosition());
        else
            pagingData.setNumRecordsNext(0);

        //NUMBER OF PREVIOUS RECORDS, CURRENT PAGE NUMBER
        if (pagingData.getRecordPosition() > pagingData.getResultsPerPage()) {
            pagingData.setNumRecordsPrev(pagingData.getRecordPosition() -
            		pagingData.getResultsPerPage());
        } else if (pagingData.getRecordPosition() < pagingData.getResultsPerPage()) {
            pagingData.setNumRecordsPrev(pagingData.getRecordPosition());
        } else if (pagingData.getRecordPosition() == pagingData.getResultsPerPage()) {
            pagingData.setNumRecordsPrev(0);
        }
        //NUMBER OF PAGES REMAINING
        if (pagingData.getPageNumber() < pagingData.getNumTotalPages())
            pagingData.setNumPagesNext(pagingData.getNumTotalPages() -
                                       pagingData.getPageNumber());
        else
            pagingData.setNumPagesNext(0);
        //NUMBER OF PREVIOUS PAGES
        if (pagingData.getPageNumber() > 1)
            pagingData.setNumPagesPrev(pagingData.getPageNumber() - 1);
        else
            pagingData.setNumPagesPrev(0);
        
        return pagingData;
    }

}