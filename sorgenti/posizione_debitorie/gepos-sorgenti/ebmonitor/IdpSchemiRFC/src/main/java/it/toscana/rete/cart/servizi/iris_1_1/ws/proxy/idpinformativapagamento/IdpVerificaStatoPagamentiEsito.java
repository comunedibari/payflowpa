
package it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpinformativapagamento;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import it.toscana.rete.cart.servizi.iris_1_1.idpesito.IdpEsitoVerifica;


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
 *         &lt;element ref="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpEsito}IdpEsitoVerifica"/>
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
    "idpEsitoVerifica"
})
@XmlRootElement(name = "IdpVerificaStatoPagamenti.Esito")
public class IdpVerificaStatoPagamentiEsito {

    @XmlElement(name = "IdpEsitoVerifica", namespace = "http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpEsito", required = true)
    protected IdpEsitoVerifica idpEsitoVerifica;

    /**
     * Gets the value of the idpEsitoVerifica property.
     * 
     * @return
     *     possible object is
     *     {@link IdpEsitoVerifica }
     *     
     */
    public IdpEsitoVerifica getIdpEsitoVerifica() {
        return idpEsitoVerifica;
    }

    /**
     * Sets the value of the idpEsitoVerifica property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdpEsitoVerifica }
     *     
     */
    public void setIdpEsitoVerifica(IdpEsitoVerifica value) {
        this.idpEsitoVerifica = value;
    }

}
