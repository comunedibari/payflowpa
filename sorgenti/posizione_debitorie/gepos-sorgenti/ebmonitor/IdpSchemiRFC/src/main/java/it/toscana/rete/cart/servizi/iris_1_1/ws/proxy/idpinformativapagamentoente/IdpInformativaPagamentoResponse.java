
package it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpinformativapagamentoente;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import it.toscana.rete.cart.servizi.iris_1_1.idpesito.InfoMessaggio;


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
 *         &lt;element name="infoMessaggio" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpEsito}InfoMessaggio"/>
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
    "infoMessaggio"
})
@XmlRootElement(name = "IdpInformativaPagamentoResponse")
public class IdpInformativaPagamentoResponse {

    @XmlElement(namespace = "", required = true)
    protected InfoMessaggio infoMessaggio;

    /**
     * Gets the value of the infoMessaggio property.
     * 
     * @return
     *     possible object is
     *     {@link InfoMessaggio }
     *     
     */
    public InfoMessaggio getInfoMessaggio() {
        return infoMessaggio;
    }

    /**
     * Sets the value of the infoMessaggio property.
     * 
     * @param value
     *     allowed object is
     *     {@link InfoMessaggio }
     *     
     */
    public void setInfoMessaggio(InfoMessaggio value) {
        this.infoMessaggio = value;
    }

}
