
package it.tasgroup.idp.xmlbillerservices.ws.richiestapagamento;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import it.tasgroup.idp.xmlbillerservices.pendenze.IdpMultiEsitoOTF;


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
 *         &lt;element ref="{http://idp.tasgroup.it/xmlbillerservices/Pendenze}IdpMultiEsitoOTF"/>
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
    "idpMultiEsitoOTF"
})
@XmlRootElement(name = "RichiestaPagamentoResponse")
public class RichiestaPagamentoResponse {

    @XmlElement(name = "IdpMultiEsitoOTF", namespace = "http://idp.tasgroup.it/xmlbillerservices/Pendenze", required = true)
    protected IdpMultiEsitoOTF idpMultiEsitoOTF;

    /**
     * Gets the value of the idpMultiEsitoOTF property.
     * 
     * @return
     *     possible object is
     *     {@link IdpMultiEsitoOTF }
     *     
     */
    public IdpMultiEsitoOTF getIdpMultiEsitoOTF() {
        return idpMultiEsitoOTF;
    }

    /**
     * Sets the value of the idpMultiEsitoOTF property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdpMultiEsitoOTF }
     *     
     */
    public void setIdpMultiEsitoOTF(IdpMultiEsitoOTF value) {
        this.idpMultiEsitoOTF = value;
    }

}
