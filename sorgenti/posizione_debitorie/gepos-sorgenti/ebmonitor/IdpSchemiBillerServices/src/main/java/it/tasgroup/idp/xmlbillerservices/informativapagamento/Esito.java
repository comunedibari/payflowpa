
package it.tasgroup.idp.xmlbillerservices.informativapagamento;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Esito complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Esito">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Stato" type="{http://idp.tasgroup.it/xmlbillerservices/InformativaPagamento}StatoMessaggio"/>
 *         &lt;element name="Codice" type="{http://idp.tasgroup.it/xmlbillerservices/Common}Max35Text"/>
 *         &lt;element name="Descrizione" type="{http://idp.tasgroup.it/xmlbillerservices/Common}Max256Text"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Esito", propOrder = {
    "stato",
    "codice",
    "descrizione"
})
public class Esito {

    @XmlElement(name = "Stato", required = true)
    protected StatoMessaggio stato;
    @XmlElement(name = "Codice", required = true)
    protected String codice;
    @XmlElement(name = "Descrizione", required = true)
    protected String descrizione;

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

}
