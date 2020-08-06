
package it.toscana.rete.cart.servizi.iris_1_1.idpinformativapagamento;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RiferimentoDebito complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RiferimentoDebito">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Pendenza" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}IdentificativoIdp"/>
 *       &lt;/sequence>
 *       &lt;attribute name="TipoDebito" use="required" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInformativaPagamento}TipoDebito" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RiferimentoDebito", propOrder = {
    "pendenza"
})
public class RiferimentoDebito {

    @XmlElement(name = "Pendenza", required = true)
    protected String pendenza;
    @XmlAttribute(name = "TipoDebito", required = true)
    protected String tipoDebito;

    /**
     * Gets the value of the pendenza property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPendenza() {
        return pendenza;
    }

    /**
     * Sets the value of the pendenza property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPendenza(String value) {
        this.pendenza = value;
    }

    /**
     * Gets the value of the tipoDebito property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoDebito() {
        return tipoDebito;
    }

    /**
     * Sets the value of the tipoDebito property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoDebito(String value) {
        this.tipoDebito = value;
    }

}
