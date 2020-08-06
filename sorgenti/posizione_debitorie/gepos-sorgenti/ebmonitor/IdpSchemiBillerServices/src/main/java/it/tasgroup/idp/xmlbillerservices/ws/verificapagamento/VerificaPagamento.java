
package it.tasgroup.idp.xmlbillerservices.ws.verificapagamento;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import it.tasgroup.idp.xmlbillerservices.informativapagamento.IdpVerificaStatoPagamento;


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
 *         &lt;element ref="{http://idp.tasgroup.it/xmlbillerservices/InformativaPagamento}IdpVerificaStatoPagamento"/>
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
    "idpVerificaStatoPagamento"
})
@XmlRootElement(name = "VerificaPagamento")
public class VerificaPagamento {

    @XmlElement(name = "IdpVerificaStatoPagamento", namespace = "http://idp.tasgroup.it/xmlbillerservices/InformativaPagamento", required = true)
    protected IdpVerificaStatoPagamento idpVerificaStatoPagamento;

    /**
     * Gets the value of the idpVerificaStatoPagamento property.
     * 
     * @return
     *     possible object is
     *     {@link IdpVerificaStatoPagamento }
     *     
     */
    public IdpVerificaStatoPagamento getIdpVerificaStatoPagamento() {
        return idpVerificaStatoPagamento;
    }

    /**
     * Sets the value of the idpVerificaStatoPagamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdpVerificaStatoPagamento }
     *     
     */
    public void setIdpVerificaStatoPagamento(IdpVerificaStatoPagamento value) {
        this.idpVerificaStatoPagamento = value;
    }

}
