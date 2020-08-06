
package it.tasgroup.iris.comunication.ws.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UtenteCanaleType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UtenteCanaleType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id_utente" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="canale" type="{http://impl.ws.comunication.iris.tasgroup.it/}CanaleType"/>
 *         &lt;element name="is_anonymous" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="stato" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="configurazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UtenteCanaleType", propOrder = {
    "idUtente",
    "canale",
    "isAnonymous",
    "stato",
    "configurazione"
})
public class UtenteCanaleType {

    @XmlElement(name = "id_utente", required = true)
    protected String idUtente;
    @XmlElement(required = true)
    protected CanaleType canale;
    @XmlElement(name = "is_anonymous")
    protected boolean isAnonymous;
    protected String stato;
    protected String configurazione;

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
     * Gets the value of the canale property.
     * 
     * @return
     *     possible object is
     *     {@link CanaleType }
     *     
     */
    public CanaleType getCanale() {
        return canale;
    }

    /**
     * Sets the value of the canale property.
     * 
     * @param value
     *     allowed object is
     *     {@link CanaleType }
     *     
     */
    public void setCanale(CanaleType value) {
        this.canale = value;
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
     * Gets the value of the stato property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStato() {
        return stato;
    }

    /**
     * Sets the value of the stato property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStato(String value) {
        this.stato = value;
    }

    /**
     * Gets the value of the configurazione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConfigurazione() {
        return configurazione;
    }

    /**
     * Sets the value of the configurazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConfigurazione(String value) {
        this.configurazione = value;
    }

}
