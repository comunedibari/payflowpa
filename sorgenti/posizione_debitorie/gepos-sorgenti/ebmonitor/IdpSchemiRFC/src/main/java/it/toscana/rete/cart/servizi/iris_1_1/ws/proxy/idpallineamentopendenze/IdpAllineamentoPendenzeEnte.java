
package it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpallineamentopendenze;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import it.toscana.rete.cart.servizi.iris_1_1.idpallineamentopendenze.IdpAllineamentoPendenze;


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
 *         &lt;element ref="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpAllineamentoPendenze}IdpAllineamentoPendenze"/>
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
    "idpAllineamentoPendenze"
})
@XmlRootElement(name = "IdpAllineamentoPendenzeEnte")
public class IdpAllineamentoPendenzeEnte {

    @XmlElement(name = "IdpAllineamentoPendenze", namespace = "http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpAllineamentoPendenze", required = true)
    protected IdpAllineamentoPendenze idpAllineamentoPendenze;

    /**
     * Gets the value of the idpAllineamentoPendenze property.
     * 
     * @return
     *     possible object is
     *     {@link IdpAllineamentoPendenze }
     *     
     */
    public IdpAllineamentoPendenze getIdpAllineamentoPendenze() {
        return idpAllineamentoPendenze;
    }

    /**
     * Sets the value of the idpAllineamentoPendenze property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdpAllineamentoPendenze }
     *     
     */
    public void setIdpAllineamentoPendenze(IdpAllineamentoPendenze value) {
        this.idpAllineamentoPendenze = value;
    }

}
