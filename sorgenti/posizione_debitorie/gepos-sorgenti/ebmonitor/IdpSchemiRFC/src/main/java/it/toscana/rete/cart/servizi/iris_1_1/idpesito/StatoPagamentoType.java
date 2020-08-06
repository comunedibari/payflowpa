
package it.toscana.rete.cart.servizi.iris_1_1.idpesito;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import it.toscana.rete.cart.servizi.iris_1_1.idpinclude.VerificaStatoPagamento;


/**
 * <p>Java class for StatoPagamentoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StatoPagamentoType">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude>VerificaStatoPagamento">
 *       &lt;attribute name="IdPagamento" use="required" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}IdentificativoIdp" />
 *       &lt;attribute name="TipoPendenza" use="required" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max35Text" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StatoPagamentoType", propOrder = {
    "value"
})
public class StatoPagamentoType {

    @XmlValue
    protected VerificaStatoPagamento value;
    @XmlAttribute(name = "IdPagamento", required = true)
    protected String idPagamento;
    @XmlAttribute(name = "TipoPendenza", required = true)
    protected String tipoPendenza;

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link VerificaStatoPagamento }
     *     
     */
    public VerificaStatoPagamento getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link VerificaStatoPagamento }
     *     
     */
    public void setValue(VerificaStatoPagamento value) {
        this.value = value;
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
