package it.tasgroup.iris.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class CfgUtenteModalitaPagamentoId implements java.io.Serializable {

	private String applicationId;
	private String systemId;
	private String codiceFiscale;

	public CfgUtenteModalitaPagamentoId() {
	}

	

	public CfgUtenteModalitaPagamentoId(String applicationId, String systemId,
			String codiceFiscale) {
		super();
		this.applicationId = applicationId;
		this.systemId = systemId;
		this.codiceFiscale = codiceFiscale;
	}



	@Column(name="SYSTEM_ID")
	public String getSystemId() {
		return this.systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
    @Column(name="APPLICATION_ID")
	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	@Column(name="CODICEFISCALE")
	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	
}
