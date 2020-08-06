package it.tasgroup.iris.dto;

import it.nch.fwk.fo.pager.PagingCriteria;
import it.nch.fwk.fo.pager.PagingData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO usato per contenere le liste da visualizzare in home page cittadino
 *  
 * @author fabrizie
 *
 */
public class PagamentiInScadenzaDTO implements Serializable {
	
	private PagingCriteria pagingCriteria = new PagingCriteria();
	
	private PagingData pagingData = new PagingData();
	
	private List pagamentiInScadenza = new ArrayList();
	
	private List pagamentiInDelega = new ArrayList();
	
	
	public PagamentiInScadenzaDTO() {}
	

	public PagingCriteria getPagingCriteria()
	{
		return pagingCriteria;
	}

	public void setPagingCriteria(PagingCriteria pagingCriteria)
	{
		this.pagingCriteria = pagingCriteria;
	}

	public PagingData getPagingData()
	{
		return pagingData;
	}

	public void setPagingData(PagingData pagingData)
	{
		this.pagingData = pagingData;
	}


    public List getPagamentiInScadenza() {
        return pagamentiInScadenza;
    }


    public void setPagamentiInScadenza(List pagamentiInScadenza) {
        this.pagamentiInScadenza = pagamentiInScadenza;
    }


    public List getPagamentiInDelega() {
        return pagamentiInDelega;
    }


    public void setPagamentiInDelega(List pagamentiInDelega) {
        this.pagamentiInDelega = pagamentiInDelega;
    }
	
}


