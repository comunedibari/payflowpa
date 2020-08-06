
package it.tasgroup.idp.xmlbillerservices.informativapagamento;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IdpEsitoNotifica complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IdpEsitoNotifica">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="E2EMsgId" type="{http://idp.tasgroup.it/xmlbillerservices/Header}MsgId"/>
 *         &lt;element name="Stato" type="{http://idp.tasgroup.it/xmlbillerservices/InformativaPagamento}StatoMessaggio"/>
 *         &lt;element name="Codice" type="{http://idp.tasgroup.it/xmlbillerservices/Common}Max35Text" minOccurs="0"/>
 *         &lt;element name="Descrizione" type="{http://idp.tasgroup.it/xmlbillerservices/Common}Max256Text" minOccurs="0"/>
 *         &lt;element name="Note" type="{http://idp.tasgroup.it/xmlbillerservices/Common}Max2000Text" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IdpEsitoNotifica", propOrder = {
    "e2EMsgId",
    "stato",
    "codice",
    "descrizione",
    "note"
})
public class IdpEsitoNotifica {

    @XmlElement(name = "E2EMsgId", required = true)
    protected String e2EMsgId;
    @XmlElement(name = "Stato", required = true)
    protected StatoMessaggio stato;
    @XmlElement(name = "Codice")
    protected String codice;
    @XmlElement(name = "Descrizione")
    protected String descrizione;
    @XmlElement(name = "Note")
    protected String note;

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
     * Gets the value of the stato property.
     * 
     * @return
     *     possible object is
     *     {@link StatoMessaggio }
     *     
     */
    public StatoMessaggio getStato() {
        return stato;
    }

    /**
     * Sets the value of the stato property.
     * 
     * @param value
     *     allowed object is
     *     {@link StatoMessaggio }
     *     
     */
    public void setStato(StatoMessaggio value) {
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

}
