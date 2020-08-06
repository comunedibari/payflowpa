package it.nch.fwk.fo.pager;

import java.io.Serializable;
import java.util.Collection;

//TODO make these interfaces
/**
 * Classe che raccoglie tutti i parametri necessari per la paginazione.
 * Reca i parametri risultanti dopo l'esecuzione di un avanzamento di pagina.
 *
 * @author administrator
 *
 */
public class PagingData implements Serializable
{

    //the minimum attributes to pass from front end
    private int resultsPerPage;
    private int recordPosition;
    /**
     * Numero totale di record restituito dalla query su DB.
     */
    private int numTotalRecords;

    //additional attributes received in output
    private String executionTime;
    private int pageNumber;
    private int numPagesNext;
    private int numPagesPrev;
    private int numPagesPerPage;

    private int numTotalPages;
    private int numRecordsNext;
    private int numRecordsPrev;
    private Collection pageNumbers;


    public PagingData()
    {
        this.recordPosition = -1;
        this.numTotalRecords = -1;
        this.numPagesPerPage = 10;  //TODO CONFIGURABLE PARAM
        this.recordPosition = -1;
        this.resultsPerPage = 10;  //TODO CONFIGURABLE PARAM
    }

    public void setResultsPerPage(int resultsPerPage) {
        this.resultsPerPage = resultsPerPage;
    }

    public void setExecutionTime(String executionTime) {
        this.executionTime = executionTime;
    }

    public void setRecordPosition(int recordPosition) {

        this.recordPosition = recordPosition;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setNumPagesNext(int numPagesNext) {
        this.numPagesNext = numPagesNext;
    }

    public void setNumPagesPrev(int numPagesPrev) {
        this.numPagesPrev = numPagesPrev;
    }

    public void setNumPagesPerPage(int numPagesPerPage) {
        this.numPagesPerPage = numPagesPerPage;
    }

    public void setNumTotalRecords(int numTotalRecords) {

        this.numTotalRecords = numTotalRecords;
    }

    public void setNumTotalPages(int numTotalPages) {
        this.numTotalPages = numTotalPages;
    }

    public void setNumRecordsNext(int numRecordsNext) {
        this.numRecordsNext = numRecordsNext;
    }

    public void setNumRecordsPrev(int numRecordsPrev) {
        this.numRecordsPrev = numRecordsPrev;
    }

    public void setPageNumbers(Collection pageNumbers) {
        this.pageNumbers = pageNumbers;
    }

    public int getResultsPerPage() {
        return resultsPerPage;
    }

    public String getExecutionTime() {
        return executionTime;
    }

    public int getRecordPosition() {

        return recordPosition;
    }


    public int getPageNumber() {
        return pageNumber;
    }

    public int getNumPagesNext() {
        return numPagesNext;
    }

    public int getNumPagesPrev() {
        return numPagesPrev;
    }

    public int getNumPagesPerPage() {
        return numPagesPerPage;
    }

    public int getNumTotalRecords() {

        return numTotalRecords;
    }

    public int getNumTotalPages() {
        return numTotalPages;
    }

    public int getNumRecordsNext() {
        return numRecordsNext;
    }

    public int getNumRecordsPrev() {
        return numRecordsPrev;
    }

    public Collection getPageNumbers() {
        return pageNumbers;
    }


}
