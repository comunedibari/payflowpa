
package it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpallineamentopendenze;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import it.toscana.rete.cart.servizi.iris_1_1.idpesito.IdpMultiEsitoOTF;


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
 *         &lt;element ref="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpEsito}IdpMultiEsitoOTF"/>
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
    "idpMultiEsitoOTF"
})
@XmlRootElement(name = "IdpAllineamentoPendenzeMultiEnteOTF.Esito")
public class IdpAllineamentoPendenzeMultiEnteOTFEsito {

    @XmlElement(name = "IdpMultiEsitoOTF", namespace = "http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpEsito", required = true)
    protected IdpMultiEsitoOTF idpMultiEsitoOTF;

    /**
     * Gets the value of the idpMultiEsitoOTF property.
     * 
     * @return
     *     possible object is
     *     {@link IdpMultiEsitoOTF }
     *     
     */
    public IdpMultiEsitoOTF getIdpMultiEsitoOTF() {
        return idpMultiEsitoOTF;
    }

    /**
     * Sets the value of the idpMultiEsitoOTF property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdpMultiEsitoOTF }
     *     
     */
    public void setIdpMultiEsitoOTF(IdpMultiEsitoOTF value) {
        this.idpMultiEsitoOTF = value;
    }

}
