
package it.tasgroup.iris.comunication.ws.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for subscribeCanali complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="subscribeCanali">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Subscriber" type="{http://impl.ws.comunication.iris.tasgroup.it/}UtenteType"/>
 *         &lt;element name="TipoCanaleResponse" type="{http://impl.ws.comunication.iris.tasgroup.it/}TipoCanaleType"/>
 *         &lt;element name="ParametroCanale" type="{http://impl.ws.comunication.iris.tasgroup.it/}ParametroCanaleType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "subscribeCanali", propOrder = {
    "subscriber",
    "tipoCanaleResponse",
    "parametroCanale"
})
public class SubscribeCanali {

    @XmlElement(name = "Subscriber", required = true)
    protected UtenteType subscriber;
    @XmlElement(name = "TipoCanaleResponse", required = true)
    protected TipoCanaleType tipoCanaleResponse;
    @XmlElement(name = "ParametroCanale", required = true)
    protected ParametroCanaleType parametroCanale;

    /**
     * Gets the value of the subscriber property.
     * 
     * @return
     *     possible object is
     *     {@link UtenteType }
     *     
     */
    public UtenteType getSubscriber() {
        return subscriber;
    }

    /**
     * Sets the value of the subscriber property.
     * 
     * @param value
     *     allowed object is
     *     {@link UtenteType }
     *     
     */
    public void setSubscriber(UtenteType value) {
        this.subscriber = value;
    }

    /**
     * Gets the value of the tipoCanaleResponse property.
     * 
     * @return
     *     possible object is
     *     {@link TipoCanaleType }
     *     
     */
    public TipoCanaleType getTipoCanaleResponse() {
        return tipoCanaleResponse;
    }

    /**
     * Sets the value of the tipoCanaleResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoCanaleType }
     *     
     */
    public void setTipoCanaleResponse(TipoCanaleType value) {
        this.tipoCanaleResponse = value;
    }

    /**
     * Gets the value of the parametroCanale property.
     * 
     * @return
     *     possible object is
     *     {@link ParametroCanaleType }
     *     
     */
    public ParametroCanaleType getParametroCanale() {
        return parametroCanale;
    }

    /**
     * Sets the value of the parametroCanale property.
     * 
     * @param value
     *     allowed object is
     *     {@link ParametroCanaleType }
     *     
     */
    public void setParametroCanale(ParametroCanaleType value) {
        this.parametroCanale = value;
    }

}
