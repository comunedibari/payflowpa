
package it.tasgroup.idp.xmlbillerservices.ws.notificapagamento;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import it.tasgroup.idp.xmlbillerservices.informativapagamento.IdpInformativaPagamento;


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
 *         &lt;element ref="{http://idp.tasgroup.it/xmlbillerservices/InformativaPagamento}IdpInformativaPagamento"/>
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
    "idpInformativaPagamento"
})
@XmlRootElement(name = "NotificaPagamento")
public class NotificaPagamento {

    @XmlElement(name = "IdpInformativaPagamento", namespace = "http://idp.tasgroup.it/xmlbillerservices/InformativaPagamento", required = true)
    protected IdpInformativaPagamento idpInformativaPagamento;

    /**
     * Gets the value of the idpInformativaPagamento property.
     * 
     * @return
     *     possible object is
     *     {@link IdpInformativaPagamento }
     *     
     */
    public IdpInformativaPagamento getIdpInformativaPagamento() {
        return idpInformativaPagamento;
    }

    /**
     * Sets the value of the idpInformativaPagamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdpInformativaPagamento }
     *     
     */
    public void setIdpInformativaPagamento(IdpInformativaPagamento value) {
        this.idpInformativaPagamento = value;
    }

}
