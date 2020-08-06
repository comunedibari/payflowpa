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
public class EsitoNdpOk implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    public EsitoNdpOk() {
    	esito="OK";
    }
    
    private String esito;    
    
    @XmlElement(required = true)
	public String getEsito() {
		return esito;
	}

	public void setEsito(String esito) {
		this.esito = esito;
	}
   
}
