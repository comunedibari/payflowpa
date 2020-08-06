
package it.tasgroup.iris.comunication.ws.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for validaConfigurazioneCanaleResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="validaConfigurazioneCanaleResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ValidoResponse" type="{http://impl.ws.comunication.iris.tasgroup.it/}ValidoType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "validaConfigurazioneCanaleResponse", propOrder = {
    "validoResponse"
})
public class ValidaConfigurazioneCanaleResponse {

    @XmlElement(name = "ValidoResponse", required = true)
    protected ValidoType validoResponse;

    /**
     * Gets the value of the validoResponse property.
     * 
     * @return
     *     possible object is
     *     {@link ValidoType }
     *     
     */
    public ValidoType getValidoResponse() {
        return validoResponse;
    }

    /**
     * Sets the value of the validoResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link ValidoType }
     *     
     */
    public void setValidoResponse(ValidoType value) {
        this.validoResponse = value;
    }

}
