
package it.toscana.rete.cart.servizi.iris_1_1.idpinformativapagamento;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Pagante complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Pagante">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IdPagante" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max35Text" minOccurs="0"/>
 *         &lt;element name="Descrizione" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max70Text" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="Tipo" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max35Text" default="CodiceFiscale" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Pagante", propOrder = {
    "idPagante",
    "descrizione"
})
public class Pagante {

    @XmlElement(name = "IdPagante")
    protected String idPagante;
    @XmlElement(name = "Descrizione")
    protected String descrizione;
    @XmlAttribute(name = "Tipo")
    protected String tipo;

    /**
     * Gets the value of the idPagante property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdPagante() {
        return idPagante;
    }

    /**
     * Sets the value of the idPagante property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdPagante(String value) {
        this.idPagante = value;
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
     * Gets the value of the tipo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipo() {
        if (tipo == null) {
            return "CodiceFiscale";
        } else {
            return tipo;
        }
    }

    /**
     * Sets the value of the tipo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipo(String value) {
        this.tipo = value;
    }

}
