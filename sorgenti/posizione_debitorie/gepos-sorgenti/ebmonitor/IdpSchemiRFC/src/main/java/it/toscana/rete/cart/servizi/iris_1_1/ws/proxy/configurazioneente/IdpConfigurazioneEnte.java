
package it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.configurazioneente;

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
 *         &lt;element ref="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpConfigurazione}IdpConfigurazioneEnte"/>
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
    "idpConfigurazioneEnte"
})
@XmlRootElement(name = "IdpConfigurazioneEnte")
public class IdpConfigurazioneEnte {

    @XmlElement(name = "IdpConfigurazioneEnte", namespace = "http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpConfigurazione", required = true)
    protected it.toscana.rete.cart.servizi.iris_1_1.idpconfigurazione.IdpConfigurazioneEnte idpConfigurazioneEnte;

    /**
     * Gets the value of the idpConfigurazioneEnte property.
     * 
     * @return
     *     possible object is
     *     {@link it.toscana.rete.cart.servizi.iris_1_1.idpconfigurazione.IdpConfigurazioneEnte }
     *     
     */
    public it.toscana.rete.cart.servizi.iris_1_1.idpconfigurazione.IdpConfigurazioneEnte getIdpConfigurazioneEnte() {
        return idpConfigurazioneEnte;
    }

    /**
     * Sets the value of the idpConfigurazioneEnte property.
     * 
     * @param value
     *     allowed object is
     *     {@link it.toscana.rete.cart.servizi.iris_1_1.idpconfigurazione.IdpConfigurazioneEnte }
     *     
     */
    public void setIdpConfigurazioneEnte(it.toscana.rete.cart.servizi.iris_1_1.idpconfigurazione.IdpConfigurazioneEnte value) {
        this.idpConfigurazioneEnte = value;
    }

}
