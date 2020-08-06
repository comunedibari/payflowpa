
package it.tasgroup.iris.comunication.ws.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for validaConfigurazioneUtenteCanale complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="validaConfigurazioneUtenteCanale">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="UtenteCanale" type="{http://impl.ws.comunication.iris.tasgroup.it/}UtenteCanaleType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "validaConfigurazioneUtenteCanale", propOrder = {
    "utenteCanale"
})
public class ValidaConfigurazioneUtenteCanale {

    @XmlElement(name = "UtenteCanale")
    protected UtenteCanaleType utenteCanale;

    /**
     * Gets the value of the utenteCanale property.
     * 
     * @return
     *     possible object is
     *     {@link UtenteCanaleType }
     *     
     */
    public UtenteCanaleType getUtenteCanale() {
        return utenteCanale;
    }

    /**
     * Sets the value of the utenteCanale property.
     * 
     * @param value
     *     allowed object is
     *     {@link UtenteCanaleType }
     *     
     */
    public void setUtenteCanale(UtenteCanaleType value) {
        this.utenteCanale = value;
    }

}
