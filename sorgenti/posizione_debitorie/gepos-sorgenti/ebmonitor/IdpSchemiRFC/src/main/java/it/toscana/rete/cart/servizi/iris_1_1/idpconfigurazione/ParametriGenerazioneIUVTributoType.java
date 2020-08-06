
package it.toscana.rete.cart.servizi.iris_1_1.idpconfigurazione;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ParametriGenerazioneIUVTributoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ParametriGenerazioneIUVTributoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PrefissoGenerazioneIUV" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max10Text"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ParametriGenerazioneIUVTributoType", propOrder = {
    "prefissoGenerazioneIUV"
})
public class ParametriGenerazioneIUVTributoType {

    @XmlElement(name = "PrefissoGenerazioneIUV", required = true)
    protected String prefissoGenerazioneIUV;

    /**
     * Gets the value of the prefissoGenerazioneIUV property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrefissoGenerazioneIUV() {
        return prefissoGenerazioneIUV;
    }

    /**
     * Sets the value of the prefissoGenerazioneIUV property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrefissoGenerazioneIUV(String value) {
        this.prefissoGenerazioneIUV = value;
    }

}
