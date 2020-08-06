package it.tasgroup.iris.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@SuppressWarnings("serial")
@MappedSuperclass
public class BaseEntity implements Serializable {

	private Long version;
	
	@Version
	@Column(name="VERSION")
	public Long getVersion() {
		
		return version;
		
	}
	
	public void setVersion(Long version) {
		
		this.version = version;
		
	}
	
}
