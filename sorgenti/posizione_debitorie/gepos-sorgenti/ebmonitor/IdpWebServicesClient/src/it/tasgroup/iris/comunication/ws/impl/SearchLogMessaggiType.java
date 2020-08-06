
package it.tasgroup.iris.comunication.ws.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for SearchLogMessaggiType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SearchLogMessaggiType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="canale" type="{http://impl.ws.comunication.iris.tasgroup.it/}CanaleType" minOccurs="0"/>
 *         &lt;element name="stato" type="{http://impl.ws.comunication.iris.tasgroup.it/}StatoMessaggioType" minOccurs="0"/>
 *         &lt;element name="dataDa" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="dataA" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="utente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipoMessaggio" type="{http://impl.ws.comunication.iris.tasgroup.it/}TipoMessaggioType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SearchLogMessaggiType", propOrder = {
    "canale",
    "stato",
    "dataDa",
    "dataA",
    "utente",
    "tipoMessaggio"
})
public class SearchLogMessaggiType {

    protected CanaleType canale;
    protected StatoMessaggioType stato;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataDa;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataA;
    protected String utente;
    protected TipoMessaggioType tipoMessaggio;

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
     * Gets the value of the dataDa property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataDa() {
        return dataDa;
    }

    /**
     * Sets the value of the dataDa property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataDa(XMLGregorianCalendar value) {
        this.dataDa = value;
    }

    /**
     * Gets the value of the dataA property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataA() {
        return dataA;
    }

    /**
     * Sets the value of the dataA property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataA(XMLGregorianCalendar value) {
        this.dataA = value;
    }

    /**
     * Gets the value of the utente property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUtente() {
        return utente;
    }

    /**
     * Sets the value of the utente property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUtente(String value) {
        this.utente = value;
    }

    /**
     * Gets the value of the tipoMessaggio property.
     * 
     * @return
     *     possible object is
     *     {@link TipoMessaggioType }
     *     
     */
    public TipoMessaggioType getTipoMessaggio() {
        return tipoMessaggio;
    }

    /**
     * Sets the value of the tipoMessaggio property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoMessaggioType }
     *     
     */
    public void setTipoMessaggio(TipoMessaggioType value) {
        this.tipoMessaggio = value;
    }

}
