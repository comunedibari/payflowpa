
package it.toscana.rete.cart.servizi.iris_1_1.idpesito;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IdpBodyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IdpBodyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="InfoMessaggio" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpEsito}InfoMessaggio"/>
 *         &lt;element name="InfoDettaglio" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpEsito}InfoDettaglio" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IdpBodyType", propOrder = {
    "infoMessaggio",
    "infoDettaglio"
})
public class IdpBodyType {

    @XmlElement(name = "InfoMessaggio", required = true)
    protected InfoMessaggio infoMessaggio;
    @XmlElement(name = "InfoDettaglio")
    protected InfoDettaglio infoDettaglio;

    /**
     * Gets the value of the infoMessaggio property.
     * 
     * @return
     *     possible object is
     *     {@link InfoMessaggio }
     *     
     */
    public InfoMessaggio getInfoMessaggio() {
        return infoMessaggio;
    }

    /**
     * Sets the value of the infoMessaggio property.
     * 
     * @param value
     *     allowed object is
     *     {@link InfoMessaggio }
     *     
     */
    public void setInfoMessaggio(InfoMessaggio value) {
        this.infoMessaggio = value;
    }

    /**
     * Gets the value of the infoDettaglio property.
     * 
     * @return
     *     possible object is
     *     {@link InfoDettaglio }
     *     
     */
    public InfoDettaglio getInfoDettaglio() {
        return infoDettaglio;
    }

    /**
     * Sets the value of the infoDettaglio property.
     * 
     * @param value
     *     allowed object is
     *     {@link InfoDettaglio }
     *     
     */
    public void setInfoDettaglio(InfoDettaglio value) {
        this.infoDettaglio = value;
    }

}
