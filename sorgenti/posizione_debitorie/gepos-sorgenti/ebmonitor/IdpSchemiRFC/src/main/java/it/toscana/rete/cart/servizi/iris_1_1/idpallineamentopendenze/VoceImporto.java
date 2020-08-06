
package it.toscana.rete.cart.servizi.iris_1_1.idpallineamentopendenze;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VoceImporto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VoceImporto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Codice" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max35Text"/>
 *         &lt;element name="Descrizione" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max256Text"/>
 *         &lt;element name="Importo" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Importo"/>
 *         &lt;element name="CapitoloBilancio" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max35Text" minOccurs="0"/>
 *         &lt;element name="Accertamento" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max35Text" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="Tipo" use="required" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max35Text" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VoceImporto", propOrder = {
    "codice",
    "descrizione",
    "importo",
    "capitoloBilancio",
    "accertamento"
})
public class VoceImporto {

    @XmlElement(name = "Codice", required = true)
    protected String codice;
    @XmlElement(name = "Descrizione", required = true)
    protected String descrizione;
    @XmlElement(name = "Importo", required = true)
    protected BigDecimal importo;
    @XmlElement(name = "CapitoloBilancio")
    protected String capitoloBilancio;
    @XmlElement(name = "Accertamento")
    protected String accertamento;
    @XmlAttribute(name = "Tipo", required = true)
    protected String tipo;

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
     * Gets the value of the importo property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImporto() {
        return importo;
    }

    /**
     * Sets the value of the importo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImporto(BigDecimal value) {
        this.importo = value;
    }

    /**
     * Gets the value of the capitoloBilancio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCapitoloBilancio() {
        return capitoloBilancio;
    }

    /**
     * Sets the value of the capitoloBilancio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCapitoloBilancio(String value) {
        this.capitoloBilancio = value;
    }

    /**
     * Gets the value of the accertamento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccertamento() {
        return accertamento;
    }

    /**
     * Sets the value of the accertamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccertamento(String value) {
        this.accertamento = value;
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
        return tipo;
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
