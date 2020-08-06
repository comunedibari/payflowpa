
package it.veneto.regione.pagamenti.ente;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per paaSILPrenotaExportFlussoIncrementaleConRicevuta complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="paaSILPrenotaExportFlussoIncrementaleConRicevuta"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="password" type="{http://www.regione.veneto.it/pagamenti/ente/}stPassword" minOccurs="0"/&gt;
 *         &lt;element name="dateFrom" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="dateTo" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="identificativoTipoDovuto" type="{http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/}stText35" minOccurs="0"/&gt;
 *         &lt;element name="ricevuta" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="incrementale" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="versioneTracciato" type="{http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/}stText35" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "paaSILPrenotaExportFlussoIncrementaleConRicevuta", propOrder = {
    "password",
    "dateFrom",
    "dateTo",
    "identificativoTipoDovuto",
    "ricevuta",
    "incrementale",
    "versioneTracciato"
})
public class PaaSILPrenotaExportFlussoIncrementaleConRicevuta {

    protected String password;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateFrom;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateTo;
    protected String identificativoTipoDovuto;
    protected boolean ricevuta;
    protected boolean incrementale;
    @XmlElement(defaultValue = "1.0")
    protected String versioneTracciato;

    /**
     * Recupera il valore della proprietà password.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassword() {
        return password;
    }

    /**
     * Imposta il valore della proprietà password.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * Recupera il valore della proprietà dateFrom.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateFrom() {
        return dateFrom;
    }

    /**
     * Imposta il valore della proprietà dateFrom.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateFrom(XMLGregorianCalendar value) {
        this.dateFrom = value;
    }

    /**
     * Recupera il valore della proprietà dateTo.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateTo() {
        return dateTo;
    }

    /**
     * Imposta il valore della proprietà dateTo.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateTo(XMLGregorianCalendar value) {
        this.dateTo = value;
    }

    /**
     * Recupera il valore della proprietà identificativoTipoDovuto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativoTipoDovuto() {
        return identificativoTipoDovuto;
    }

    /**
     * Imposta il valore della proprietà identificativoTipoDovuto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativoTipoDovuto(String value) {
        this.identificativoTipoDovuto = value;
    }

    /**
     * Recupera il valore della proprietà ricevuta.
     * 
     */
    public boolean isRicevuta() {
        return ricevuta;
    }

    /**
     * Imposta il valore della proprietà ricevuta.
     * 
     */
    public void setRicevuta(boolean value) {
        this.ricevuta = value;
    }

    /**
     * Recupera il valore della proprietà incrementale.
     * 
     */
    public boolean isIncrementale() {
        return incrementale;
    }

    /**
     * Imposta il valore della proprietà incrementale.
     * 
     */
    public void setIncrementale(boolean value) {
        this.incrementale = value;
    }

    /**
     * Recupera il valore della proprietà versioneTracciato.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersioneTracciato() {
        return versioneTracciato;
    }

    /**
     * Imposta il valore della proprietà versioneTracciato.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersioneTracciato(String value) {
        this.versioneTracciato = value;
    }

}
