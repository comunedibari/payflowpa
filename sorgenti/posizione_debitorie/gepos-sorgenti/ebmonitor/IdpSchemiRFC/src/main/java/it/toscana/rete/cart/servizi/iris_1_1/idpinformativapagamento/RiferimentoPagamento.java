
package it.toscana.rete.cart.servizi.iris_1_1.idpinformativapagamento;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import it.toscana.rete.cart.servizi.iris_1_1.idpinclude.TipoPagamento;


/**
 * <p>Java class for RiferimentoPagamento complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RiferimentoPagamento">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IdPagamento" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max35Text"/>
 *       &lt;/sequence>
 *       &lt;attribute name="TipoPagamento" use="required" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}TipoPagamento" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RiferimentoPagamento", propOrder = {
    "idPagamento"
})
public class RiferimentoPagamento {

    @XmlElement(name = "IdPagamento", required = true)
    protected String idPagamento;
    @XmlAttribute(name = "TipoPagamento", required = true)
    protected TipoPagamento tipoPagamento;

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
     * Gets the value of the tipoPagamento property.
     * 
     * @return
     *     possible object is
     *     {@link TipoPagamento }
     *     
     */
    public TipoPagamento getTipoPagamento() {
        return tipoPagamento;
    }

    /**
     * Sets the value of the tipoPagamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoPagamento }
     *     
     */
    public void setTipoPagamento(TipoPagamento value) {
        this.tipoPagamento = value;
    }

}
