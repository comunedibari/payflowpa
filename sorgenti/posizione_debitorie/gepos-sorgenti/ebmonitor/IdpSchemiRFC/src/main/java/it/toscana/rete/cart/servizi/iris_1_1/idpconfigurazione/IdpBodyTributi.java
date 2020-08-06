
package it.toscana.rete.cart.servizi.iris_1_1.idpconfigurazione;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IdpBodyTributi complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IdpBodyTributi">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ConfigurazioneTributi" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpConfigurazione}ConfigurazioneTributi"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IdpBodyTributi", propOrder = {
    "configurazioneTributi"
})
public class IdpBodyTributi {

    @XmlElement(name = "ConfigurazioneTributi", required = true)
    protected ConfigurazioneTributi configurazioneTributi;

    /**
     * Gets the value of the configurazioneTributi property.
     * 
     * @return
     *     possible object is
     *     {@link ConfigurazioneTributi }
     *     
     */
    public ConfigurazioneTributi getConfigurazioneTributi() {
        return configurazioneTributi;
    }

    /**
     * Sets the value of the configurazioneTributi property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConfigurazioneTributi }
     *     
     */
    public void setConfigurazioneTributi(ConfigurazioneTributi value) {
        this.configurazioneTributi = value;
    }

}
