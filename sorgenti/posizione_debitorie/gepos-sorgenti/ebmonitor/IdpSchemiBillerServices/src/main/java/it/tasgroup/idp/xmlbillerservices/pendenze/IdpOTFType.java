
package it.tasgroup.idp.xmlbillerservices.pendenze;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IdpOTFType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IdpOTFType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IdSessioneGW" type="{http://idp.tasgroup.it/xmlbillerservices/Common}Max35Text"/>
 *         &lt;element name="UrlGW" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IdpOTFType", propOrder = {
    "idSessioneGW",
    "urlGW"
})
public class IdpOTFType {

    @XmlElement(name = "IdSessioneGW", required = true)
    protected String idSessioneGW;
    @XmlElement(name = "UrlGW", required = true)
    @XmlSchemaType(name = "anyURI")
    protected String urlGW;

    /**
     * Gets the value of the idSessioneGW property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdSessioneGW() {
        return idSessioneGW;
    }

    /**
     * Sets the value of the idSessioneGW property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdSessioneGW(String value) {
        this.idSessioneGW = value;
    }

    /**
     * Gets the value of the urlGW property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrlGW() {
        return urlGW;
    }

    /**
     * Sets the value of the urlGW property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrlGW(String value) {
        this.urlGW = value;
    }

}
