package it.nch.is.fo.common;

import it.nch.fwk.fo.dto.business.Pojo;

public class ComboPropertyVo implements Pojo {
	
	private String description;
	private String value;	
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Long getId() {
		return null;
	}

	public void setId(Long arg0) {
	}
	
}