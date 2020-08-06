package it.tasgroup.idp.rs.model.pagamenti;

import it.tasgroup.rest.utils.RESTResource;
import it.tasgroup.services.util.enumeration.EnumTipoAccredito;
import it.tasgroup.services.util.enumeration.EnumTipoAnomaliaNDP;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Movimenti di accredito
 */
@XmlRootElement
public class EsitoNDP implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public EsitoNDP(String esito, String faultCode, String faultString, String faultDescription) {
    	this.esito=esito;
    	this.faultCode=faultCode;   
    	this.faultString=faultString;    
    	this.faultDescription=faultDescription;
    }

    public EsitoNDP() {
    }

    private String esito;    
    private String faultCode;   
    private String faultString;    
    private String faultDescription;
    

    @XmlElement(required = true)
	public String getEsito() {
		return esito;
	}

	public void setEsito(String esito) {
		this.esito = esito;
	}
	@XmlElement
	public String getFaultCode() {
		return faultCode;
	}

	public void setFaultCode(String faultCode) {
		this.faultCode = faultCode;
	}
	@XmlElement
	public String getFaultString() {
		return faultString;
	}

	public void setFaultString(String faultString) {
		this.faultString = faultString;
	}
	@XmlElement
	public String getFaultDescription() {
		return faultDescription;
	}

	public void setFaultDescription(String faultDescription) {
		this.faultDescription = faultDescription;
	}

	
   
}
