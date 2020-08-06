
package it.tasgroup.idp.xmlbillerservices.pendenze;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Dettaglio complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Dettaglio">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="E2EMsgId" type="{http://idp.tasgroup.it/xmlbillerservices/Common}Max35Text"/>
 *         &lt;element name="IdDebito" type="{http://idp.tasgroup.it/xmlbillerservices/Common}Max35Text" minOccurs="0"/>
 *         &lt;element name="IdPagamento" type="{http://idp.tasgroup.it/xmlbillerservices/Common}Max35Text" minOccurs="0"/>
 *         &lt;element name="Stato" type="{http://idp.tasgroup.it/xmlbillerservices/Pendenze}StatoDettaglio"/>
 *         &lt;element name="Codice" type="{http://idp.tasgroup.it/xmlbillerservices/Common}Max35Text" minOccurs="0"/>
 *         &lt;element name="Descrizione" type="{http://idp.tasgroup.it/xmlbillerservices/Common}Max256Text" minOccurs="0"/>
 *         &lt;element name="Note" type="{http://idp.tasgroup.it/xmlbillerservices/Common}Max2000Text" minOccurs="0"/>
 *         &lt;element name="IdentificativoUnivocoVersamento" type="{http://idp.tasgroup.it/xmlbillerservices/Common}Max35Text" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Dettaglio", propOrder = {
    "e2EMsgId",
    "idDebito",
    "idPagamento",
    "stato",
    "codice",
    "descrizione",
    "note",
    "identificativoUnivocoVersamento"
})
public class Dettaglio {

    @XmlElement(name = "E2EMsgId", required = true)
    protected String e2EMsgId;
    @XmlElement(name = "IdDebito")
    protected String idDebito;
    @XmlElement(name = "IdPagamento")
    protected String idPagamento;
    @XmlElement(name = "Stato", required = true)
    protected StatoDettaglio stato;
    @XmlElement(name = "Codice")
    protected String codice;
    @XmlElement(name = "Descrizione")
    protected String descrizione;
    @XmlElement(name = "Note")
    protected String note;
    @XmlElement(name = "IdentificativoUnivocoVersamento")
    protected String identificativoUnivocoVersamento;

    /**
     * Gets the value of the e2EMsgId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getE2EMsgId() {
        return e2EMsgId;
    }

    /**
     * Sets the value of the e2EMsgId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setE2EMsgId(String value) {
        this.e2EMsgId = value;
    }

    /**
     * Gets the value of the idDebito property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdDebito() {
        return idDebito;
    }

    /**
     * Sets the value of the idDebito property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdDebito(String value) {
        this.idDebito = value;
    }

    /**
     * Gets the value of the idPagamento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdPagamento() {
        return idPagamento;
    }

    /**
     * Sets the value of the idPagamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdPagamento(String value) {
        this.idPagamento = value;
    }

    /**
     * Gets the value of the stato property.
     * 
     * @return
     *     possible object is
     *     {@link StatoDettaglio }
     *     
     */
    public StatoDettaglio getStato() {
        return stato;
    }

    /**
     * Sets the value of the stato property.
     * 
     * @param value
     *     allowed object is
     *     {@link StatoDettaglio }
     *     
     */
    public void setStato(StatoDettaglio value) {
        this.stato = value;
    }

    /**
     * Gets the value of the codice property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodice() {
        return codice;
    }

    /**
     * Sets the value of the codice property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodice(String value) {
        this.codice = value;
    }

    /**
     * Gets the value of the descrizione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Sets the value of the descrizione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizione(String value) {
        this.descrizione = value;
    }

    /**
     * Gets the value of the note property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNote() {
        return note;
    }

    /**
     * Sets the value of the note property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNote(String value) {
        this.note = value;
    }

    /**
     * Gets the value of the identificativoUnivocoVersamento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativoUnivocoVersamento() {
        return identificativoUnivocoVersamento;
    }

    /**
     * Sets the value of the identificativoUnivocoVersamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativoUnivocoVersamento(String value) {
        this.identificativoUnivocoVersamento = value;
    }

}
