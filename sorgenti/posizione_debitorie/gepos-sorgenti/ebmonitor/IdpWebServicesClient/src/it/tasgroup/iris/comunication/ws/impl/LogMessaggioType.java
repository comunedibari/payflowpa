
package it.tasgroup.iris.comunication.ws.impl;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for LogMessaggioType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LogMessaggioType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="data" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="stato" type="{http://impl.ws.comunication.iris.tasgroup.it/}StatoMessaggioType" minOccurs="0"/>
 *         &lt;element name="Canale" type="{http://impl.ws.comunication.iris.tasgroup.it/}CanaleType" minOccurs="0"/>
 *         &lt;element name="Utente" type="{http://impl.ws.comunication.iris.tasgroup.it/}UtenteType" minOccurs="0"/>
 *         &lt;element name="MessaggioLogico" type="{http://impl.ws.comunication.iris.tasgroup.it/}MessaggioLogicoType" minOccurs="0"/>
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
@XmlType(name = "LogMessaggioType", propOrder = {
    "id",
    "data",
    "stato",
    "canale",
    "utente",
    "messaggioLogico",
    "sortingField",
    "sortingDir"
})
public class LogMessaggioType {

    @XmlElement(required = true)
    protected BigInteger id;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar data;
    protected StatoMessaggioType stato;
    @XmlElement(name = "Canale")
    protected CanaleType canale;
    @XmlElement(name = "Utente")
    protected UtenteType utente;
    @XmlElement(name = "MessaggioLogico")
    protected MessaggioLogicoType messaggioLogico;
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
     * Gets the value of the data property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getData() {
        return data;
    }

    /**
     * Sets the value of the data property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setData(XMLGregorianCalendar value) {
        this.data = value;
    }

    /**
     * Gets the value of the stato property.
     * 
     * @return
     *     possible object is
     *     {@link StatoMessaggioType }
     *     
     */
    public StatoMessaggioType getStato() {
        return stato;
    }

    /**
     * Sets the value of the stato property.
     * 
     * @param value
     *     allowed object is
     *     {@link StatoMessaggioType }
     *     
     */
    public void setStato(StatoMessaggioType value) {
        this.stato = value;
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
     * Gets the value of the utente property.
     * 
     * @return
     *     possible object is
     *     {@link UtenteType }
     *     
     */
    public UtenteType getUtente() {
        return utente;
    }

    /**
     * Sets the value of the utente property.
     * 
     * @param value
     *     allowed object is
     *     {@link UtenteType }
     *     
     */
    public void setUtente(UtenteType value) {
        this.utente = value;
    }

    /**
     * Gets the value of the messaggioLogico property.
     * 
     * @return
     *     possible object is
     *     {@link MessaggioLogicoType }
     *     
     */
    public MessaggioLogicoType getMessaggioLogico() {
        return messaggioLogico;
    }

    /**
     * Sets the value of the messaggioLogico property.
     * 
     * @param value
     *     allowed object is
     *     {@link MessaggioLogicoType }
     *     
     */
    public void setMessaggioLogico(MessaggioLogicoType value) {
        this.messaggioLogico = value;
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
