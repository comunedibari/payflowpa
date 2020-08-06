
package it.tasgroup.iris.comunication.ws.impl;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CanaleType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CanaleType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="stato" type="{http://impl.ws.comunication.iris.tasgroup.it/}StatoCanaleType" minOccurs="0"/>
 *         &lt;element name="denominazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="num_tentativi" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="tempo_retry" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="configurazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sortingField" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sortingDir" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CanaleType", propOrder = {
    "id",
    "stato",
    "denominazione",
    "numTentativi",
    "tempoRetry",
    "configurazione",
    "sortingField",
    "sortingDir"
})
public class CanaleType {

    @XmlElement(required = true)
    protected BigInteger id;
    protected StatoCanaleType stato;
    protected String denominazione;
    @XmlElement(name = "num_tentativi")
    protected BigInteger numTentativi;
    @XmlElement(name = "tempo_retry")
    protected BigInteger tempoRetry;
    protected String configurazione;
    protected String sortingField;
    protected String sortingDir;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setId(BigInteger value) {
        this.id = value;
    }

    /**
     * Gets the value of the stato property.
     * 
     * @return
     *     possible object is
     *     {@link StatoCanaleType }
     *     
     */
    public StatoCanaleType getStato() {
        return stato;
    }

    /**
     * Sets the value of the stato property.
     * 
     * @param value
     *     allowed object is
     *     {@link StatoCanaleType }
     *     
     */
    public void setStato(StatoCanaleType value) {
        this.stato = value;
    }

    /**
     * Gets the value of the denominazione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDenominazione() {
        return denominazione;
    }

    /**
     * Sets the value of the denominazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDenominazione(String value) {
        this.denominazione = value;
    }

    /**
     * Gets the value of the numTentativi property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNumTentativi() {
        return numTentativi;
    }

    /**
     * Sets the value of the numTentativi property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNumTentativi(BigInteger value) {
        this.numTentativi = value;
    }

    /**
     * Gets the value of the tempoRetry property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTempoRetry() {
        return tempoRetry;
    }

    /**
     * Sets the value of the tempoRetry property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTempoRetry(BigInteger value) {
        this.tempoRetry = value;
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

    /**
     * Gets the value of the sortingField property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSortingField() {
        return sortingField;
    }

    /**
     * Sets the value of the sortingField property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSortingField(String value) {
        this.sortingField = value;
    }

    /**
     * Gets the value of the sortingDir property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSortingDir() {
        return sortingDir;
    }

    /**
     * Sets the value of the sortingDir property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSortingDir(String value) {
        this.sortingDir = value;
    }

}
