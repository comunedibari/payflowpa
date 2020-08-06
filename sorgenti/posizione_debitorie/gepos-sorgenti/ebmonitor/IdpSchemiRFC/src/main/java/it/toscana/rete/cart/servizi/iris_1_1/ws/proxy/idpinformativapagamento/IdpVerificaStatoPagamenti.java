
package it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpinformativapagamento;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import it.toscana.rete.cart.servizi.iris_1_1.idpinformativapagamento.IdpVerificaStatoPagamento;


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
 *         &lt;element ref="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInformativaPagamento}IdpVerificaStatoPagamento"/>
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
    "idpVerificaStatoPagamento"
})
@XmlRootElement(name = "IdpVerificaStatoPagamenti")
public class IdpVerificaStatoPagamenti {

    @XmlElement(name = "IdpVerificaStatoPagamento", namespace = "http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInformativaPagamento", required = true)
    protected IdpVerificaStatoPagamento idpVerificaStatoPagamento;

    /**
     * Gets the value of the idpVerificaStatoPagamento property.
     * 
     * @return
     *     possible object is
     *     {@link IdpVerificaStatoPagamento }
     *     
     */
    public IdpVerificaStatoPagamento getIdpVerificaStatoPagamento() {
        return idpVerificaStatoPagamento;
    }

    /**
     * Sets the value of the idpVerificaStatoPagamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdpVerificaStatoPagamento }
     *     
     */
    public void setIdpVerificaStatoPagamento(IdpVerificaStatoPagamento value) {
        this.idpVerificaStatoPagamento = value;
    }

}
