
package it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpinformativapagamentoente;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInformativaPagamento}IdpInformativaPagamento"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "idpInformativaPagamento"
})
@XmlRootElement(name = "IdpInformativaPagamento")
public class IdpInformativaPagamento {

    @XmlElement(name = "IdpInformativaPagamento", namespace = "http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInformativaPagamento", required = true)
    protected it.toscana.rete.cart.servizi.iris_1_1.idpinformativapagamento.IdpInformativaPagamento idpInformativaPagamento;

    /**
     * Gets the value of the idpInformativaPagamento property.
     * 
     * @return
     *     possible object is
     *     {@link it.toscana.rete.cart.servizi.iris_1_1.idpinformativapagamento.IdpInformativaPagamento }
     *     
     */
    public it.toscana.rete.cart.servizi.iris_1_1.idpinformativapagamento.IdpInformativaPagamento getIdpInformativaPagamento() {
        return idpInformativaPagamento;
    }

    /**
     * Sets the value of the idpInformativaPagamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link it.toscana.rete.cart.servizi.iris_1_1.idpinformativapagamento.IdpInformativaPagamento }
     *     
     */
    public void setIdpInformativaPagamento(it.toscana.rete.cart.servizi.iris_1_1.idpinformativapagamento.IdpInformativaPagamento value) {
        this.idpInformativaPagamento = value;
    }

}
