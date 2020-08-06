
package it.tasgroup.idp.xmlbillerservices.ws.notificapagamento;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import it.tasgroup.idp.xmlbillerservices.informativapagamento.IdpEsitoNotifica;


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
 *         &lt;element name="Esito" type="{http://idp.tasgroup.it/xmlbillerservices/InformativaPagamento}IdpEsitoNotifica"/>
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
    "esito"
})
@XmlRootElement(name = "NotificaPagamentoResponse")
public class NotificaPagamentoResponse {

    @XmlElement(name = "Esito", namespace = "", required = true)
    protected IdpEsitoNotifica esito;

    /**
     * Gets the value of the esito property.
     * 
     * @return
     *     possible object is
     *     {@link IdpEsitoNotifica }
     *     
     */
    public IdpEsitoNotifica getEsito() {
        return esito;
    }

    /**
     * Sets the value of the esito property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdpEsitoNotifica }
     *     
     */
    public void setEsito(IdpEsitoNotifica value) {
        this.esito = value;
    }

}
