
package it.tasgroup.iris.comunication.ws.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getCanaliComunicazione complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getCanaliComunicazione">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Utente" type="{http://impl.ws.comunication.iris.tasgroup.it/}UtenteType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getCanaliComunicazione", propOrder = {
    "utente"
})
public class GetCanaliComunicazione {

    @XmlElement(name = "Utente")
    protected UtenteType utente;

    /**
     * Gets the value of the utente property.
     * 
     * @return
     *     possible object is
     *     {@link UtenteType }
     *     
     */
    public UtenteType getUtente() {
        return utente;
    }

    /**
     * Sets the value of the utente property.
     * 
     * @param value
     *     allowed object is
     *     {@link UtenteType }
     *     
     */
    public void setUtente(UtenteType value) {
        this.utente = value;
    }

}
