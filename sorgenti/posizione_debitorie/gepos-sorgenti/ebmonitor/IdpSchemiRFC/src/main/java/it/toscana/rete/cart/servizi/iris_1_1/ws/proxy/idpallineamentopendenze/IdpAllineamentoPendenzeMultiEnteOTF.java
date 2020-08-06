
package it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpallineamentopendenze;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import it.toscana.rete.cart.servizi.iris_1_1.idpallineamentopendenze.IdpAllineamentoPendenzeMultiOTF;


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
 *         &lt;element ref="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpAllineamentoPendenze}IdpAllineamentoPendenzeMultiOTF"/>
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
    "idpAllineamentoPendenzeMultiOTF"
})
@XmlRootElement(name = "IdpAllineamentoPendenzeMultiEnteOTF")
public class IdpAllineamentoPendenzeMultiEnteOTF {

    @XmlElement(name = "IdpAllineamentoPendenzeMultiOTF", namespace = "http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpAllineamentoPendenze", required = true)
    protected IdpAllineamentoPendenzeMultiOTF idpAllineamentoPendenzeMultiOTF;

    /**
     * Gets the value of the idpAllineamentoPendenzeMultiOTF property.
     * 
     * @return
     *     possible object is
     *     {@link IdpAllineamentoPendenzeMultiOTF }
     *     
     */
    public IdpAllineamentoPendenzeMultiOTF getIdpAllineamentoPendenzeMultiOTF() {
        return idpAllineamentoPendenzeMultiOTF;
    }

    /**
     * Sets the value of the idpAllineamentoPendenzeMultiOTF property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdpAllineamentoPendenzeMultiOTF }
     *     
     */
    public void setIdpAllineamentoPendenzeMultiOTF(IdpAllineamentoPendenzeMultiOTF value) {
        this.idpAllineamentoPendenzeMultiOTF = value;
    }

}
