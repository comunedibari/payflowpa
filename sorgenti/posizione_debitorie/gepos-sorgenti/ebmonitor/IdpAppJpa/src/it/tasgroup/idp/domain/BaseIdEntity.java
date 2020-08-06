package it.tasgroup.idp.domain;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

@MappedSuperclass
public class BaseIdEntity extends BaseEntity implements Serializable {

	/*** Auto Generated Identity Property ***/
	public Long id;
	/*** Check Property Optimistic Locking***/
	private Long version;

	private static final long serialVersionUID = 1L;

//	Soluzione con AUTO		
//	@Id
//	@GeneratedValue(strategy=GenerationType.AUTO)
	
//	Soluzione con MultipleHiLo	
//	@GenericGenerator(	name="table-hilo-generator", 
//						strategy="org.hibernate.id.MultipleHiLoPerTableGenerator")
//	@GeneratedValue(generator="table-hilo-generator")
	
// 	soluzione con TABLE GENERATOR PERSONALIZZATO
//	@Id	
//	@GeneratedValue(strategy=GenerationType.TABLE, generator="pk_gen")		
//	@TableGenerator (name = "pk_gen",
//	schema="QLT_PAYTAS_OWNER",
//	table = "TB_GENERATOR",
//	pkColumnName = "gen_name", 
//	valueColumnName = "gen_value",
//	pkColumnValue = "TEST_PK",
//	initialValue = 1,	
//	allocationSize = 1   
//	)	
	
//	Soluzione con TABLE	
//	@Id
//	@GeneratedValue(strategy=GenerationType.TABLE)
	
//	Soluzione con AUTO		
//	@Id
//	@GeneratedValue(strategy=GenerationType.AUTO)	
//	public Long getId() {
//		return this.id;
//	}
	public void setId(Long id) {
		this.id = id;
	}

	@Version
	public Long getVersion() {
		return this.version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}

}
