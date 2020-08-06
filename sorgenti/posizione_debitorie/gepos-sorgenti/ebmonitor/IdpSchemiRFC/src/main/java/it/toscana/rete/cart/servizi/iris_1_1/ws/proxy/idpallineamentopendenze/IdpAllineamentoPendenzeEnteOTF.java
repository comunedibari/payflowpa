
package it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpallineamentopendenze;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import it.toscana.rete.cart.servizi.iris_1_1.idpallineamentopendenze.IdpAllineamentoPendenzeOTF;


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
 *         &lt;element ref="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpAllineamentoPendenze}IdpAllineamentoPendenzeOTF"/>
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
    "idpAllineamentoPendenzeOTF"
})
@XmlRootElement(name = "IdpAllineamentoPendenzeEnteOTF")
public class IdpAllineamentoPendenzeEnteOTF {

    @XmlElement(name = "IdpAllineamentoPendenzeOTF", namespace = "http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpAllineamentoPendenze", required = true)
    protected IdpAllineamentoPendenzeOTF idpAllineamentoPendenzeOTF;

    /**
     * Gets the value of the idpAllineamentoPendenzeOTF property.
     * 
     * @return
     *     possible object is
     *     {@link IdpAllineamentoPendenzeOTF }
     *     
     */
    public IdpAllineamentoPendenzeOTF getIdpAllineamentoPendenzeOTF() {
        return idpAllineamentoPendenzeOTF;
    }

    /**
     * Sets the value of the idpAllineamentoPendenzeOTF property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdpAllineamentoPendenzeOTF }
     *     
     */
    public void setIdpAllineamentoPendenzeOTF(IdpAllineamentoPendenzeOTF value) {
        this.idpAllineamentoPendenzeOTF = value;
    }

}
