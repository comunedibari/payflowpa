
package it.tasgroup.iris.comunication.ws.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for updateCanaliComunicazione complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="updateCanaliComunicazione">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Canale" type="{http://impl.ws.comunication.iris.tasgroup.it/}CanaleType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateCanaliComunicazione", propOrder = {
    "canale"
})
public class UpdateCanaliComunicazione {

    @XmlElement(name = "Canale", required = true)
    protected CanaleType canale;

    /**
     * Gets the value of the canale property.
     * 
     * @return
     *     possible object is
     *     {@link CanaleType }
     *     
     */
    public CanaleType getCanale() {
        return canale;
    }

    /**
     * Sets the value of the canale property.
     * 
     * @param value
     *     allowed object is
     *     {@link CanaleType }
     *     
     */
    public void setCanale(CanaleType value) {
        this.canale = value;
    }

}
