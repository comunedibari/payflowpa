
package it.toscana.rete.cart.servizi.iris_1_1.idpallineamentopendenze;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import it.toscana.rete.cart.servizi.iris_1_1.idpinclude.TipoOperazione;


/**
 * <p>Java class for HeadPendenza complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HeadPendenza">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IdPendenza" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}IdentificativoIdp"/>
 *         &lt;element name="Mittente" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpAllineamentoPendenze}Mittente"/>
 *         &lt;element name="Destinatari" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpAllineamentoPendenze}Destinatari"/>
 *         &lt;element name="CartellaDiPagamento" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="Note" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max2000Text" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="TipoPendenza" use="required" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max35Text" />
 *       &lt;attribute name="TipoOperazione" use="required" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}TipoOperazione" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HeadPendenza", propOrder = {
    "idPendenza",
    "mittente",
    "destinatari",
    "cartellaDiPagamento",
    "note"
})
@XmlSeeAlso({
    Pendenza.class
})
public class HeadPendenza {

    @XmlElement(name = "IdPendenza", required = true)
    protected String idPendenza;
    @XmlElement(name = "Mittente", required = true)
    protected Mittente mittente;
    @XmlElement(name = "Destinatari", required = true)
    protected Destinatari destinatari;
    @XmlElement(name = "CartellaDiPagamento")
    protected Boolean cartellaDiPagamento;
    @XmlElement(name = "Note")
    protected String note;
    @XmlAttribute(name = "TipoPendenza", required = true)
    protected String tipoPendenza;
    @XmlAttribute(name = "TipoOperazione", required = true)
    protected TipoOperazione tipoOperazione;

    /**
     * Gets the value of the idPendenza property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdPendenza() {
        return idPendenza;
    }

    /**
     * Sets the value of the idPendenza property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdPendenza(String value) {
        this.idPendenza = value;
    }

    /**
     * Gets the value of the mittente property.
     * 
     * @return
     *     possible object is
     *     {@link Mittente }
     *     
     */
    public Mittente getMittente() {
        return mittente;
    }

    /**
     * Sets the value of the mittente property.
     * 
     * @param value
     *     allowed object is
     *     {@link Mittente }
     *     
     */
    public void setMittente(Mittente value) {
        this.mittente = value;
    }

    /**
     * Gets the value of the destinatari property.
     * 
     * @return
     *     possible object is
     *     {@link Destinatari }
     *     
     */
    public Destinatari getDestinatari() {
        return destinatari;
    }

    /**
     * Sets the value of the destinatari property.
     * 
     * @param value
     *     allowed object is
     *     {@link Destinatari }
     *     
     */
    public void setDestinatari(Destinatari value) {
        this.destinatari = value;
    }

    /**
     * Gets the value of the cartellaDiPagamento property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCartellaDiPagamento() {
        return cartellaDiPagamento;
    }

    /**
     * Sets the value of the cartellaDiPagamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCartellaDiPagamento(Boolean value) {
        this.cartellaDiPagamento = value;
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
     * Gets the value of the tipoPendenza property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoPendenza() {
        return tipoPendenza;
    }

    /**
     * Sets the value of the tipoPendenza property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoPendenza(String value) {
        this.tipoPendenza = value;
    }

    /**
     * Gets the value of the tipoOperazione property.
     * 
     * @return
     *     possible object is
     *     {@link TipoOperazione }
     *     
     */
    public TipoOperazione getTipoOperazione() {
        return tipoOperazione;
    }

    /**
     * Sets the value of the tipoOperazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoOperazione }
     *     
     */
    public void setTipoOperazione(TipoOperazione value) {
        this.tipoOperazione = value;
    }

}
