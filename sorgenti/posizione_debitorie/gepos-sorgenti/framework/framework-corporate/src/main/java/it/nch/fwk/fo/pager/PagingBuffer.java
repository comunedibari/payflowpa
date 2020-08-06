package it.nch.fwk.fo.pager;

import java.util.HashMap;


public class PagingBuffer
{

    private static PagingBuffer instance = null;

    private static HashMap queryLists = new HashMap();

    protected PagingBuffer()
    {
        //exists only to defeat instantiation
    }

    public static PagingBuffer getInstance()
    {
        if(instance == null){
            instance = new PagingBuffer();
        }
        return instance;
    }

    public QueryList getQueryList(String userid)
    {
        return (QueryList)queryLists.get(userid);
    }

    public void addQueryList(String userid, QueryList queryList)
    {
        queryLists.put(userid, queryList);
    }

    public void removeQueryList(String userid)
    {
        queryLists.remove(userid);
    }

}
