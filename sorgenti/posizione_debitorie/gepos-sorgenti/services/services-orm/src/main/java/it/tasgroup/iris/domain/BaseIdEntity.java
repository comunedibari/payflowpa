package it.tasgroup.iris.domain;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

@SuppressWarnings("serial")
@MappedSuperclass
public class BaseIdEntity extends BaseEntity implements Serializable {

//	private Long id;
//	
//	@Id
//	public Long getId() {
//		
//		return this.id;
//	 
//	}
//	
//	public void setId(Long id) {
//		
//		this.id = id;
//	 
//	} 

	
		
}
