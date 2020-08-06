package it.nch.fwk.fo.pager;

import java.util.List;

//import org.hibernate.*;

public class QueryList  
{

    private int absolutePosition; //the index of the firstResult
    private String userId;
    private int resultsPerPage;
    private int subsetPosition;
    private int subsetSize;
    private int maxRecords;

    private List result = null;
    //private Query query = null;
    private List[] subsets = null;
    private int numSubsets;
    private String queryString;


    public QueryList()
    {

    }

    public int getAbsolutePosition() {

        return absolutePosition;
    }

    public String getUserId() {
        return userId;
    }

    public int getResultsPerPage() {

        return resultsPerPage;
    }

    public int getSubsetPosition() {

        return subsetPosition;
    }

    public int getSubsetSize() {
        return subsetSize;
    }

    /*public Query getQuery() {
        return query;
    }*/

    public List getResult() {
        return result;
    }

    public List[] getSubsets() {
        return subsets;
    }

    public int getMaxRecords() {
        return maxRecords;
    }

    public int getNumSubsets() {
        return numSubsets;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setAbsolutePosition(int absolutePosition) {

        this.absolutePosition = absolutePosition;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setResultsPerPage(int resultsPerPage) {

        this.resultsPerPage = resultsPerPage;
    }

    public void setSubsetPosition(int subsetPosition) {

        this.subsetPosition = subsetPosition;
    }

    public void setSubsetSize(int subsetSize) {
        this.subsetSize = subsetSize;
    }

   /* public void setQuery(Query query) {
        this.query = query;
    }*/

    public void setResult(List result) {
        this.result = result;
    }

    public void setSubsets(List[] subsets) {
        this.subsets = subsets;
    }

    public void setMaxRecords(int maxRecords) {
        this.maxRecords = maxRecords;
    }

    public void setNumSubsets(int numSubsets) {
        this.numSubsets = numSubsets;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

}
