
package it.tasgroup.idp.xmlbillerservices.ws.richiestapagamento;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import it.tasgroup.idp.xmlbillerservices.pendenze.IdpAllineamentoPendenzeMultiOTF;


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
 *         &lt;element ref="{http://idp.tasgroup.it/xmlbillerservices/Pendenze}IdpAllineamentoPendenzeMultiOTF"/>
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
    "idpAllineamentoPendenzeMultiOTF"
})
@XmlRootElement(name = "RichiestaPagamento")
public class RichiestaPagamento {

    @XmlElement(name = "IdpAllineamentoPendenzeMultiOTF", namespace = "http://idp.tasgroup.it/xmlbillerservices/Pendenze", required = true)
    protected IdpAllineamentoPendenzeMultiOTF idpAllineamentoPendenzeMultiOTF;

    /**
     * Gets the value of the idpAllineamentoPendenzeMultiOTF property.
     * 
     * @return
     *     possible object is
     *     {@link IdpAllineamentoPendenzeMultiOTF }
     *     
     */
    public IdpAllineamentoPendenzeMultiOTF getIdpAllineamentoPendenzeMultiOTF() {
        return idpAllineamentoPendenzeMultiOTF;
    }

    /**
     * Sets the value of the idpAllineamentoPendenzeMultiOTF property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdpAllineamentoPendenzeMultiOTF }
     *     
     */
    public void setIdpAllineamentoPendenzeMultiOTF(IdpAllineamentoPendenzeMultiOTF value) {
        this.idpAllineamentoPendenzeMultiOTF = value;
    }

}
