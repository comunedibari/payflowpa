
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
 *         &lt;element ref="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpConfigurazione}IdpConfigurazioneTributi"/>
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
    "idpConfigurazioneTributi"
})
@XmlRootElement(name = "IdpConfigurazioneTributi")
public class IdpConfigurazioneTributi {

    @XmlElement(name = "IdpConfigurazioneTributi", namespace = "http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpConfigurazione", required = true)
    protected it.toscana.rete.cart.servizi.iris_1_1.idpconfigurazione.IdpConfigurazioneTributi idpConfigurazioneTributi;

    /**
     * Gets the value of the idpConfigurazioneTributi property.
     * 
     * @return
     *     possible object is
     *     {@link it.toscana.rete.cart.servizi.iris_1_1.idpconfigurazione.IdpConfigurazioneTributi }
     *     
     */
    public it.toscana.rete.cart.servizi.iris_1_1.idpconfigurazione.IdpConfigurazioneTributi getIdpConfigurazioneTributi() {
        return idpConfigurazioneTributi;
    }

    /**
     * Sets the value of the idpConfigurazioneTributi property.
     * 
     * @param value
     *     allowed object is
     *     {@link it.toscana.rete.cart.servizi.iris_1_1.idpconfigurazione.IdpConfigurazioneTributi }
     *     
     */
    public void setIdpConfigurazioneTributi(it.toscana.rete.cart.servizi.iris_1_1.idpconfigurazione.IdpConfigurazioneTributi value) {
        this.idpConfigurazioneTributi = value;
    }

}
