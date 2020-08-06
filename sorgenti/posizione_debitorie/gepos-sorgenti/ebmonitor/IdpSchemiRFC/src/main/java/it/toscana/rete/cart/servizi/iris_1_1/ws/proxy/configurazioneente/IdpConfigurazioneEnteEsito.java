
package it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.configurazioneente;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import it.toscana.rete.cart.servizi.iris_1_1.idpesito.IdpEsito;


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
 *         &lt;element ref="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpEsito}IdpEsito"/>
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
    "idpEsito"
})
@XmlRootElement(name = "IdpConfigurazioneEnte.Esito")
public class IdpConfigurazioneEnteEsito {

    @XmlElement(name = "IdpEsito", namespace = "http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpEsito", required = true)
    protected IdpEsito idpEsito;

    /**
     * Gets the value of the idpEsito property.
     * 
     * @return
     *     possible object is
     *     {@link IdpEsito }
     *     
     */
    public IdpEsito getIdpEsito() {
        return idpEsito;
    }

    /**
     * Sets the value of the idpEsito property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdpEsito }
     *     
     */
    public void setIdpEsito(IdpEsito value) {
        this.idpEsito = value;
    }

}
