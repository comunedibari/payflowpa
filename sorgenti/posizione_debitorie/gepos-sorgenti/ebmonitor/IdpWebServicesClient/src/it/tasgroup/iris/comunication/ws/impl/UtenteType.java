
package it.tasgroup.iris.comunication.ws.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UtenteType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UtenteType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id_utente" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="is_anonymous" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="scopo_messaggio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UtenteType", propOrder = {
    "idUtente",
    "isAnonymous",
    "scopoMessaggio"
})
public class UtenteType {

    @XmlElement(name = "id_utente", required = true)
    protected String idUtente;
    @XmlElement(name = "is_anonymous")
    protected boolean isAnonymous;
    @XmlElement(name = "scopo_messaggio")
    protected String scopoMessaggio;

    /**
     * Gets the value of the idUtente property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdUtente() {
        return idUtente;
    }

    /**
     * Sets the value of the idUtente property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdUtente(String value) {
        this.idUtente = value;
    }

    /**
     * Gets the value of the isAnonymous property.
     * 
     */
    public boolean isIsAnonymous() {
        return isAnonymous;
    }

    /**
     * Sets the value of the isAnonymous property.
     * 
     */
    public void setIsAnonymous(boolean value) {
        this.isAnonymous = value;
    }

    /**
     * Gets the value of the scopoMessaggio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScopoMessaggio() {
        return scopoMessaggio;
    }

    /**
     * Sets the value of the scopoMessaggio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScopoMessaggio(String value) {
        this.scopoMessaggio = value;
    }

}
