
package it.toscana.rete.cart.servizi.iris_1_1.idpconfigurazione;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IdpBodyEnte complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IdpBodyEnte">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ConfigurazioneEnte" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpConfigurazione}ConfigurazioneEnte"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IdpBodyEnte", propOrder = {
    "configurazioneEnte"
})
public class IdpBodyEnte {

    @XmlElement(name = "ConfigurazioneEnte", required = true)
    protected ConfigurazioneEnte configurazioneEnte;

    /**
     * Gets the value of the configurazioneEnte property.
     * 
     * @return
     *     possible object is
     *     {@link ConfigurazioneEnte }
     *     
     */
    public ConfigurazioneEnte getConfigurazioneEnte() {
        return configurazioneEnte;
    }

    /**
     * Sets the value of the configurazioneEnte property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConfigurazioneEnte }
     *     
     */
    public void setConfigurazioneEnte(ConfigurazioneEnte value) {
        this.configurazioneEnte = value;
    }

}
