package it.nch.is.fo.common;

import it.nch.fwk.fo.dto.business.Pojo;

public class TaskVo implements Pojo {
	public String processType;
	public String processId;
	/**
	 * Only for test purposes!!!!!!!
	 */
	public String serverName;
	

	public Long getId() {
		return new Long(0L);
	}
	public void setId(Long arg0) {
		
	}
	
	public String toString() {
		return
			"\r\nprocessType ... " + processType +
			"\r\nserverName .... " + serverName +
			"\r\nprocessId ..... " + processId;
	}
}
