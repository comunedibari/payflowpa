
package it.tasgroup.idp.xmlbillerservices.header;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="URL_BACK">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyURI">
 *               &lt;maxLength value="512"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="URL_CANCEL">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyURI">
 *               &lt;maxLength value="512"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="OFFLINE_PAYMENT_METHODS" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="ID_PSP" type="{http://idp.tasgroup.it/xmlbillerservices/Common}Max100Text" minOccurs="0"/>
 *         &lt;element name="DATI_VERSANTE" type="{http://idp.tasgroup.it/xmlbillerservices/Header}DATI_VERSANTE_Type" minOccurs="0"/>
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
    "urlback",
    "urlcancel",
    "offlinepaymentmethods",
    "idpsp",
    "dativersante"
})
@XmlRootElement(name = "IdpOTF")
public class IdpOTF {

    @XmlElement(name = "URL_BACK", required = true)
    protected String urlback;
    @XmlElement(name = "URL_CANCEL", required = true)
    protected String urlcancel;
    @XmlElement(name = "OFFLINE_PAYMENT_METHODS")
    protected Boolean offlinepaymentmethods;
    @XmlElement(name = "ID_PSP")
    protected String idpsp;
    @XmlElement(name = "DATI_VERSANTE")
    protected DATIVERSANTEType dativersante;

    /**
     * Gets the value of the urlback property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getURLBACK() {
        return urlback;
    }

    /**
     * Sets the value of the urlback property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setURLBACK(String value) {
        this.urlback = value;
    }

    /**
     * Gets the value of the urlcancel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getURLCANCEL() {
        return urlcancel;
    }

    /**
     * Sets the value of the urlcancel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setURLCANCEL(String value) {
        this.urlcancel = value;
    }

    /**
     * Gets the value of the offlinepaymentmethods property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isOFFLINEPAYMENTMETHODS() {
        return offlinepaymentmethods;
    }

    /**
     * Sets the value of the offlinepaymentmethods property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setOFFLINEPAYMENTMETHODS(Boolean value) {
        this.offlinepaymentmethods = value;
    }

    /**
     * Gets the value of the idpsp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDPSP() {
        return idpsp;
    }

    /**
     * Sets the value of the idpsp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDPSP(String value) {
        this.idpsp = value;
    }

    /**
     * Gets the value of the dativersante property.
     * 
     * @return
     *     possible object is
     *     {@link DATIVERSANTEType }
     *     
     */
    public DATIVERSANTEType getDATIVERSANTE() {
        return dativersante;
    }

    /**
     * Sets the value of the dativersante property.
     * 
     * @param value
     *     allowed object is
     *     {@link DATIVERSANTEType }
     *     
     */
    public void setDATIVERSANTE(DATIVERSANTEType value) {
        this.dativersante = value;
    }

}
