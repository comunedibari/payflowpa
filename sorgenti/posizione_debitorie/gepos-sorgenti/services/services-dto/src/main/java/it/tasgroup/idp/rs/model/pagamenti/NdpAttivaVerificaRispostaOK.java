package it.tasgroup.idp.rs.model.pagamenti;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * NdpAttivaVerificaRisposta
 */
@XmlRootElement
public class NdpAttivaVerificaRispostaOK implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	
    public NdpAttivaVerificaRispostaOK() {
    }

    public NdpAttivaVerificaRispostaOK(NdpAttivaVerificaRisposta resp) {
    	
    	this.esito  = "OK";    	
    	this.importoSingoloVersamento = resp.getImportoSingoloVersamento();
    	this.ibanAccredito            = resp.getIbanAccredito(); 
    	this.tipoIdentificativoUnivocoBeneficiario = resp.getTipoIdentificativoUnivocoBeneficiario();
    	this.codiceIdentificativoUnivocoBeneficiario = resp.getCodiceIdentificativoUnivocoBeneficiario();
    	this.denominazioneBeneficiario = resp.getDenominazioneBeneficiario();
    	this.causaleVersamento = resp.getCausaleVersamento();
    }
    private String     esito;
    private BigDecimal importoSingoloVersamento;
    private String     ibanAccredito; 
    private String     tipoIdentificativoUnivocoBeneficiario;
    private String     codiceIdentificativoUnivocoBeneficiario;
    private String     denominazioneBeneficiario;
    private String     causaleVersamento;
    
    
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
	public String getCodiceIdentificativoUnivocoBeneficiario() {
		return codiceIdentificativoUnivocoBeneficiario;
	}

	public void setCodiceIdentificativoUnivocoBeneficiario(String codiceIdentificativoUnivocoBeneficiario) {
		this.codiceIdentificativoUnivocoBeneficiario = codiceIdentificativoUnivocoBeneficiario;
	}

   
}
