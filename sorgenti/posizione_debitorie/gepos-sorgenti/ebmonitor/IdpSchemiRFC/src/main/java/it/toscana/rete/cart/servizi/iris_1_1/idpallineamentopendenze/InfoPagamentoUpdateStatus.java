
package it.toscana.rete.cart.servizi.iris_1_1.idpallineamentopendenze;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import it.toscana.rete.cart.servizi.iris_1_1.idpinclude.TipoPagamento;


/**
 * <p>Java class for InfoPagamento.UpdateStatus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InfoPagamento.UpdateStatus">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="TipoPagamento" use="required" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}TipoPagamento" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InfoPagamento.UpdateStatus")
@XmlSeeAlso({
    it.toscana.rete.cart.servizi.iris_1_1.idpallineamentopendenze.PendenzaUpdateStatus.InfoPagamento.class
})
public class InfoPagamentoUpdateStatus {

    @XmlAttribute(name = "TipoPagamento", required = true)
    protected TipoPagamento tipoPagamento;

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
