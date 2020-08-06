
package it.toscana.rete.cart.servizi.iris_1_1.idpesito;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InfoMessaggio complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InfoMessaggio">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Stato" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpEsito}StatoMessaggio"/>
 *         &lt;element name="Esiti" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpEsito}Esiti" minOccurs="0"/>
 *         &lt;element name="Note" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max2000Text" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InfoMessaggio", propOrder = {
    "stato",
    "esiti",
    "note"
})
public class InfoMessaggio {

    @XmlElement(name = "Stato", required = true)
    protected StatoMessaggio stato;
    @XmlElement(name = "Esiti")
    protected Esiti esiti;
    @XmlElement(name = "Note")
    protected String note;

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
     * Gets the value of the esiti property.
     * 
     * @return
     *     possible object is
     *     {@link Esiti }
     *     
     */
    public Esiti getEsiti() {
        return esiti;
    }

    /**
     * Sets the value of the esiti property.
     * 
     * @param value
     *     allowed object is
     *     {@link Esiti }
     *     
     */
    public void setEsiti(Esiti value) {
        this.esiti = value;
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
