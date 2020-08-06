package it.nch.fwk.fo.dto;

import it.nch.fwk.fo.pager.PagingData;

public class DTOPoImpl extends FrameworkDTO  implements DTOPo {

	private PagingData pagingData = null;
	private Object persistenceObject = null;
	
	public DTOPoImpl() {
		super();
	}

	public DTOPoImpl(Object po) {
		super();
		this.persistenceObject =po;
	}

	
	public void setPagingData(PagingData pagingData) {
		this.pagingData = pagingData;
	}

	public PagingData getPagingData() {
		return this.pagingData;
	}

	public void setPersistenceObject(Object po) {
		this.persistenceObject = po;
	}

	public Object getPersistenceObject() {
		return this.persistenceObject;
	}

}
