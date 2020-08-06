package it.tasgroup.iris.domain;

import it.tasgroup.idp.domain.BaseIdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the CFG_IRIS_GATEWAY_CLIENT database table.
 * 
 */
@Entity
@Table(name="CFG_IRIS_GATEWAY_CLIENT")
public class CfgIrisGatewayClient extends BaseIdEntity {
	private static final long serialVersionUID = 1L;

	private String applicationId;
	private short authenticated;
	private String descrizione;
	private String systemId;
	private String urlAnnulla;
	private String urlKo;
	private String urlOk;

    public CfgIrisGatewayClient() {
    }

	/*** Id Mapping ***/
	/*** AUTO, works on DB2 with Identity ***/
	/*** AUTO + seqGenerator, works on Oracle with Sequences ***/
	@Id
	@GeneratedValue(
			strategy=GenerationType.AUTO,
			generator="rendicontazioni_pk_gen")	
	@SequenceGenerator(
	    name="rendicontazioni_pk_gen",
	    sequenceName="RENDICONTAZIONI_ID_SEQ",
	    allocationSize=1)	
	public Long getId() {
		return super.id; 
	}	

	@Column(name="APPLICATION_ID")
	public String getApplicationId() {
		return this.applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}


	public short getAuthenticated() {
		return this.authenticated;
	}

	public void setAuthenticated(short authenticated) {
		this.authenticated = authenticated;
	}


	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}






	@Column(name="SYSTEM_ID")
	public String getSystemId() {
		return this.systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}



	@Column(name="URL_ANNULLA")
	public String getUrlAnnulla() {
		return this.urlAnnulla;
	}

	public void setUrlAnnulla(String urlAnnulla) {
		this.urlAnnulla = urlAnnulla;
	}


	@Column(name="URL_KO")
	public String getUrlKo() {
		return this.urlKo;
	}

	public void setUrlKo(String urlKo) {
		this.urlKo = urlKo;
	}


	@Column(name="URL_OK")
	public String getUrlOk() {
		return this.urlOk;
	}

	public void setUrlOk(String urlOk) {
		this.urlOk = urlOk;
	}

//	@Transient
//	public boolean isAuthenticationRequired() {
//		
//		return this.authenticated > 0;
//	}

}