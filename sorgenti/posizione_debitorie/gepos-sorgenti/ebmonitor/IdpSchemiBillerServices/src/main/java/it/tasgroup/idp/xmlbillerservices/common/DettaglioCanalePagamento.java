
package it.tasgroup.idp.xmlbillerservices.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DettaglioCanalePagamento complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DettaglioCanalePagamento">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Filiale" type="{http://idp.tasgroup.it/xmlbillerservices/Common}Max35Text" minOccurs="0"/>
 *         &lt;element name="Sportello" type="{http://idp.tasgroup.it/xmlbillerservices/Common}Max35Text" minOccurs="0"/>
 *         &lt;element name="IdTerminale" type="{http://idp.tasgroup.it/xmlbillerservices/Common}Max35Text" minOccurs="0"/>
 *         &lt;element name="IdOperazione" type="{http://idp.tasgroup.it/xmlbillerservices/Common}Max35Text" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DettaglioCanalePagamento", propOrder = {
    "filiale",
    "sportello",
    "idTerminale",
    "idOperazione"
})
public class DettaglioCanalePagamento {

    @XmlElement(name = "Filiale")
    protected String filiale;
    @XmlElement(name = "Sportello")
    protected String sportello;
    @XmlElement(name = "IdTerminale")
    protected String idTerminale;
    @XmlElement(name = "IdOperazione")
    protected String idOperazione;

    /**
     * Gets the value of the filiale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFiliale() {
        return filiale;
    }

    /**
     * Sets the value of the filiale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFiliale(String value) {
        this.filiale = value;
    }

    /**
     * Gets the value of the sportello property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSportello() {
        return sportello;
    }

    /**
     * Sets the value of the sportello property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSportello(String value) {
        this.sportello = value;
    }

    /**
     * Gets the value of the idTerminale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdTerminale() {
        return idTerminale;
    }

    /**
     * Sets the value of the idTerminale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdTerminale(String value) {
        this.idTerminale = value;
    }

    /**
     * Gets the value of the idOperazione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdOperazione() {
        return idOperazione;
    }

    /**
     * Sets the value of the idOperazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdOperazione(String value) {
        this.idOperazione = value;
    }

}
