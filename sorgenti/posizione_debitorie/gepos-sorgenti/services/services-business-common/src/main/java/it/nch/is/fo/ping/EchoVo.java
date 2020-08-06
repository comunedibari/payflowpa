package it.nch.is.fo.ping;

import it.nch.fwk.fo.dto.business.Pojo;

public class EchoVo implements Pojo {
	public String message;
	
	public EchoVo nextSibling;
	public EchoVo prevSibling;
	
	public Long getId() {
		return new Long(0L);
	}
	public void setId(Long arg0) {
		
	}
}
