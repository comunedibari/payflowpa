
package it.tasgroup.idp.xmlbillerservices.pendenze;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IdpMultiEsitoOTF complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IdpMultiEsitoOTF">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IdpOTF" type="{http://idp.tasgroup.it/xmlbillerservices/Pendenze}IdpOTFType" minOccurs="0"/>
 *         &lt;element name="IdpEsitoOTF" type="{http://idp.tasgroup.it/xmlbillerservices/Pendenze}IdpMultiEsitoOTFElement"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IdpMultiEsitoOTF", propOrder = {
    "idpOTF",
    "idpEsitoOTF"
})
public class IdpMultiEsitoOTF {

    @XmlElement(name = "IdpOTF")
    protected IdpOTFType idpOTF;
    @XmlElement(name = "IdpEsitoOTF", required = true)
    protected IdpMultiEsitoOTFElement idpEsitoOTF;

    /**
     * Gets the value of the idpOTF property.
     * 
     * @return
     *     possible object is
     *     {@link IdpOTFType }
     *     
     */
    public IdpOTFType getIdpOTF() {
        return idpOTF;
    }

    /**
     * Sets the value of the idpOTF property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdpOTFType }
     *     
     */
    public void setIdpOTF(IdpOTFType value) {
        this.idpOTF = value;
    }

    /**
     * Gets the value of the idpEsitoOTF property.
     * 
     * @return
     *     possible object is
     *     {@link IdpMultiEsitoOTFElement }
     *     
     */
    public IdpMultiEsitoOTFElement getIdpEsitoOTF() {
        return idpEsitoOTF;
    }

    /**
     * Sets the value of the idpEsitoOTF property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdpMultiEsitoOTFElement }
     *     
     */
    public void setIdpEsitoOTF(IdpMultiEsitoOTFElement value) {
        this.idpEsitoOTF = value;
    }

}
