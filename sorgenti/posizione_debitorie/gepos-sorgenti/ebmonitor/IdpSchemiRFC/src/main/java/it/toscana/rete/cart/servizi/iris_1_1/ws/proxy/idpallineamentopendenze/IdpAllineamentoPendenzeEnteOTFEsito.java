
package it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpallineamentopendenze;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import it.toscana.rete.cart.servizi.iris_1_1.idpesito.IdpEsitoOTF;


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
 *         &lt;element ref="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpEsito}IdpEsitoOTF"/>
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
    "idpEsitoOTF"
})
@XmlRootElement(name = "IdpAllineamentoPendenzeEnteOTF.Esito")
public class IdpAllineamentoPendenzeEnteOTFEsito {

    @XmlElement(name = "IdpEsitoOTF", namespace = "http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpEsito", required = true)
    protected IdpEsitoOTF idpEsitoOTF;

    /**
     * Gets the value of the idpEsitoOTF property.
     * 
     * @return
     *     possible object is
     *     {@link IdpEsitoOTF }
     *     
     */
    public IdpEsitoOTF getIdpEsitoOTF() {
        return idpEsitoOTF;
    }

    /**
     * Sets the value of the idpEsitoOTF property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdpEsitoOTF }
     *     
     */
    public void setIdpEsitoOTF(IdpEsitoOTF value) {
        this.idpEsitoOTF = value;
    }

}
