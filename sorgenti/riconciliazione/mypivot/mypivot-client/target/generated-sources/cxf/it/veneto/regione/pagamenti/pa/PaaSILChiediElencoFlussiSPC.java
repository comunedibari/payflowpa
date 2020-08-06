
package it.veneto.regione.pagamenti.pa;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per paaSILChiediElencoFlussiSPC complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="paaSILChiediElencoFlussiSPC"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="password" type="{http://www.regione.veneto.it/pagamenti/pa/}stPassword"/&gt;
 *         &lt;element name="codIpaEnte" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="identificativoPsp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="tipoFlusso" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="dateFrom" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="dateTo" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "paaSILChiediElencoFlussiSPC", propOrder = {
    "password",
    "codIpaEnte",
    "identificativoPsp",
    "tipoFlusso",
    "dateFrom",
    "dateTo"
})
public class PaaSILChiediElencoFlussiSPC {

    @XmlElement(required = true)
    protected String password;
    @XmlElement(required = true)
    protected String codIpaEnte;
    @XmlElement(required = true)
    protected String identificativoPsp;
    @XmlElement(required = true)
    protected String tipoFlusso;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dateFrom;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dateTo;

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
     * Recupera il valore della proprietà codIpaEnte.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodIpaEnte() {
        return codIpaEnte;
    }

    /**
     * Imposta il valore della proprietà codIpaEnte.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodIpaEnte(String value) {
        this.codIpaEnte = value;
    }

    /**
     * Recupera il valore della proprietà identificativoPsp.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativoPsp() {
        return identificativoPsp;
    }

    /**
     * Imposta il valore della proprietà identificativoPsp.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativoPsp(String value) {
        this.identificativoPsp = value;
    }

    /**
     * Recupera il valore della proprietà tipoFlusso.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoFlusso() {
        return tipoFlusso;
    }

    /**
     * Imposta il valore della proprietà tipoFlusso.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoFlusso(String value) {
        this.tipoFlusso = value;
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

}
