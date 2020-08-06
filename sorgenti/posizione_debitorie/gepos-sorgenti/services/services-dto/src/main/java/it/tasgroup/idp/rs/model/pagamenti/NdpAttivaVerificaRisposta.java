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
 * NdpAttivaVerificaRisposta
 */
@XmlRootElement
public class NdpAttivaVerificaRisposta implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	
    public NdpAttivaVerificaRisposta() {
    }

    private String     esito;
    private BigDecimal importoSingoloVersamento;
    private String     ibanAccredito; 
    private String     tipoIdentificativoUnivocoBeneficiario;
    private String     codiceIdentificativoUnivocoBeneficiario;
    private String     denominazioneBeneficiario;
    private String     causaleVersamento;
    
    
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
	public BigDecimal getImportoSingoloVersamento() {
		return importoSingoloVersamento;
	}

	public void setImportoSingoloVersamento(BigDecimal importoSingoloVersamento) {
		this.importoSingoloVersamento = importoSingoloVersamento;
	}
	@XmlElement
	public String getIbanAccredito() {
		return ibanAccredito;
	}

	public void setIbanAccredito(String ibanAccredito) {
		this.ibanAccredito = ibanAccredito;
	}
	@XmlElement
	public String getTipoIdentificativoUnivocoBeneficiario() {
		return tipoIdentificativoUnivocoBeneficiario;
	}

	public void setTipoIdentificativoUnivocoBeneficiario(String tipoIdentificativoUnivocoBeneficiario) {
		this.tipoIdentificativoUnivocoBeneficiario = tipoIdentificativoUnivocoBeneficiario;
	}
	@XmlElement
	public String getDenominazioneBeneficiario() {
		return denominazioneBeneficiario;
	}

	public void setDenominazioneBeneficiario(String denominazioneBeneficiario) {
		this.denominazioneBeneficiario = denominazioneBeneficiario;
	}
	@XmlElement
	public String getCausaleVersamento() {
		return causaleVersamento;
	}

	public void setCausaleVersamento(String causaleVersamento) {
		this.causaleVersamento = causaleVersamento;
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
	
	@XmlElement
	public String getCodiceIdentificativoUnivocoBeneficiario() {
		return codiceIdentificativoUnivocoBeneficiario;
	}

	public void setCodiceIdentificativoUnivocoBeneficiario(String codiceIdentificativoUnivocoBeneficiario) {
		this.codiceIdentificativoUnivocoBeneficiario = codiceIdentificativoUnivocoBeneficiario;
	}

   
}
