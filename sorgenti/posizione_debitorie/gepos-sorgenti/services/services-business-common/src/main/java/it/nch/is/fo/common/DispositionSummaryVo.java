package it.nch.is.fo.common;

import it.nch.fwk.fo.dto.business.Pojo;

public class DispositionSummaryVo implements Pojo {
	public int dispositionCount;
	public Double totalAmount;

	public Long getId() {
		return new Long(0L);
	}
	public void setId(Long arg0) {
		
	}
	
	public String toString() {
		return
			"\r\ndispositionCount ... " + dispositionCount +
			"\r\ntotalAmount .... " + totalAmount;
	}
}
