
package it.toscana.rete.cart.servizi.iris_1_1.idpinformativapagamento;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Java class for idPagamento complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="idPagamento">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude>IdentificativoIdp">
 *       &lt;attribute name="TipoPendenza" use="required" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInformativaPagamento}TipoDebito" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "idPagamento", propOrder = {
    "value"
})
public class IdPagamento {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "TipoPendenza", required = true)
    protected String tipoPendenza;

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
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

}
