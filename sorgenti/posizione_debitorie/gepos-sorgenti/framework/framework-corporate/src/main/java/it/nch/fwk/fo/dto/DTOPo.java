package it.nch.fwk.fo.dto;

import it.nch.fwk.fo.pager.PagingData;

public interface DTOPo extends FrameworkDTOInterface {

	void setPagingData(PagingData pagingData);
	
	PagingData getPagingData();
	
	void setPersistenceObject(Object po);
	
	Object getPersistenceObject();

}
