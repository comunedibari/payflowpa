
package it.tasgroup.iris.comunication.ws.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for updateTipoMessaggio complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="updateTipoMessaggio">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TipoMessaggio" type="{http://impl.ws.comunication.iris.tasgroup.it/}TipoMessaggioType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateTipoMessaggio", propOrder = {
    "tipoMessaggio"
})
public class UpdateTipoMessaggio {

    @XmlElement(name = "TipoMessaggio", required = true)
    protected TipoMessaggioType tipoMessaggio;

    /**
     * Gets the value of the tipoMessaggio property.
     * 
     * @return
     *     possible object is
     *     {@link TipoMessaggioType }
     *     
     */
    public TipoMessaggioType getTipoMessaggio() {
        return tipoMessaggio;
    }

    /**
     * Sets the value of the tipoMessaggio property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoMessaggioType }
     *     
     */
    public void setTipoMessaggio(TipoMessaggioType value) {
        this.tipoMessaggio = value;
    }

}
