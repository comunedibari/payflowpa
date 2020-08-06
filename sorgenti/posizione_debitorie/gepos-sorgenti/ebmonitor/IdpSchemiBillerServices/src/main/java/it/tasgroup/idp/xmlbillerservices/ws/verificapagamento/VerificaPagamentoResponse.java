
package it.tasgroup.idp.xmlbillerservices.ws.verificapagamento;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import it.tasgroup.idp.xmlbillerservices.informativapagamento.IdpEsitoVerifica;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://idp.tasgroup.it/xmlbillerservices/InformativaPagamento}IdpEsitoVerifica"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "idpEsitoVerifica"
})
@XmlRootElement(name = "VerificaPagamentoResponse")
public class VerificaPagamentoResponse {

    @XmlElement(name = "IdpEsitoVerifica", namespace = "http://idp.tasgroup.it/xmlbillerservices/InformativaPagamento", required = true)
    protected IdpEsitoVerifica idpEsitoVerifica;

    /**
     * Gets the value of the idpEsitoVerifica property.
     * 
     * @return
     *     possible object is
     *     {@link IdpEsitoVerifica }
     *     
     */
    public IdpEsitoVerifica getIdpEsitoVerifica() {
        return idpEsitoVerifica;
    }

    /**
     * Sets the value of the idpEsitoVerifica property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdpEsitoVerifica }
     *     
     */
    public void setIdpEsitoVerifica(IdpEsitoVerifica value) {
        this.idpEsitoVerifica = value;
    }

}
