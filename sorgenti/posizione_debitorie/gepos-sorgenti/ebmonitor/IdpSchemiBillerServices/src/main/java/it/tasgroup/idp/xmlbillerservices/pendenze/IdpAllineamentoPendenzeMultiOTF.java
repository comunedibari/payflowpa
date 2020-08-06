
package it.tasgroup.idp.xmlbillerservices.pendenze;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import it.tasgroup.idp.xmlbillerservices.header.IdpOTF;
import it.tasgroup.idp.xmlbillerservices.header.InboundHeaderTRT;


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
 *         &lt;element name="HeaderTRT" type="{http://idp.tasgroup.it/xmlbillerservices/Header}InboundHeaderTRT"/>
 *         &lt;element ref="{http://idp.tasgroup.it/xmlbillerservices/Header}IdpOTF"/>
 *         &lt;element name="Parametri" type="{http://idp.tasgroup.it/xmlbillerservices/Pendenze}IdpAllineamentoPendenzeMultiOTFParametriType" minOccurs="0"/>
 *         &lt;element name="IdpAllineamentoPendenzeOTF" type="{http://idp.tasgroup.it/xmlbillerservices/Pendenze}IdpAllineamentoPendenzeMultiOTFElementType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="Versione" use="required" type="{http://idp.tasgroup.it/xmlbillerservices/Pendenze}Versione" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "headerTRT",
    "idpOTF",
    "parametri",
    "idpAllineamentoPendenzeOTF"
})
@XmlRootElement(name = "IdpAllineamentoPendenzeMultiOTF")
public class IdpAllineamentoPendenzeMultiOTF {

    @XmlElement(name = "HeaderTRT", required = true)
    protected InboundHeaderTRT headerTRT;
    @XmlElement(name = "IdpOTF", namespace = "http://idp.tasgroup.it/xmlbillerservices/Header", required = true)
    protected IdpOTF idpOTF;
    @XmlElement(name = "Parametri")
    protected IdpAllineamentoPendenzeMultiOTFParametriType parametri;
    @XmlElement(name = "IdpAllineamentoPendenzeOTF", required = true)
    protected List<IdpAllineamentoPendenzeMultiOTFElementType> idpAllineamentoPendenzeOTF;
    @XmlAttribute(name = "Versione", required = true)
    protected String versione;

    /**
     * Gets the value of the headerTRT property.
     * 
     * @return
     *     possible object is
     *     {@link InboundHeaderTRT }
     *     
     */
    public InboundHeaderTRT getHeaderTRT() {
        return headerTRT;
    }

    /**
     * Sets the value of the headerTRT property.
     * 
     * @param value
     *     allowed object is
     *     {@link InboundHeaderTRT }
     *     
     */
    public void setHeaderTRT(InboundHeaderTRT value) {
        this.headerTRT = value;
    }

    /**
     * Gets the value of the idpOTF property.
     * 
     * @return
     *     possible object is
     *     {@link IdpOTF }
     *     
     */
    public IdpOTF getIdpOTF() {
        return idpOTF;
    }

    /**
     * Sets the value of the idpOTF property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdpOTF }
     *     
     */
    public void setIdpOTF(IdpOTF value) {
        this.idpOTF = value;
    }

    /**
     * Gets the value of the parametri property.
     * 
     * @return
     *     possible object is
     *     {@link IdpAllineamentoPendenzeMultiOTFParametriType }
     *     
     */
    public IdpAllineamentoPendenzeMultiOTFParametriType getParametri() {
        return parametri;
    }

    /**
     * Sets the value of the parametri property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdpAllineamentoPendenzeMultiOTFParametriType }
     *     
     */
    public void setParametri(IdpAllineamentoPendenzeMultiOTFParametriType value) {
        this.parametri = value;
    }

    /**
     * Gets the value of the idpAllineamentoPendenzeOTF property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the idpAllineamentoPendenzeOTF property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIdpAllineamentoPendenzeOTF().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IdpAllineamentoPendenzeMultiOTFElementType }
     * 
     * 
     */
    public List<IdpAllineamentoPendenzeMultiOTFElementType> getIdpAllineamentoPendenzeOTF() {
        if (idpAllineamentoPendenzeOTF == null) {
            idpAllineamentoPendenzeOTF = new ArrayList<IdpAllineamentoPendenzeMultiOTFElementType>();
        }
        return this.idpAllineamentoPendenzeOTF;
    }

    /**
     * Gets the value of the versione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersione() {
        return versione;
    }

    /**
     * Sets the value of the versione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersione(String value) {
        this.versione = value;
    }

}
