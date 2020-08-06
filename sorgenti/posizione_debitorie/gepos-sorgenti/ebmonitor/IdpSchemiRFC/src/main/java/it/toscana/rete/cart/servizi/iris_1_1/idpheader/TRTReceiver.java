
package it.toscana.rete.cart.servizi.iris_1_1.idpheader;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TRTReceiver complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TRTReceiver">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ReceiverId" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max35Text"/>
 *         &lt;element name="ReceiverSys" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max35Text"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TRTReceiver", propOrder = {
    "receiverId",
    "receiverSys"
})
public class TRTReceiver {

    @XmlElement(name = "ReceiverId", required = true)
    protected String receiverId;
    @XmlElement(name = "ReceiverSys", required = true)
    protected String receiverSys;

    /**
     * Gets the value of the receiverId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReceiverId() {
        return receiverId;
    }

    /**
     * Sets the value of the receiverId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReceiverId(String value) {
        this.receiverId = value;
    }

    /**
     * Gets the value of the receiverSys property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReceiverSys() {
        return receiverSys;
    }

    /**
     * Sets the value of the receiverSys property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReceiverSys(String value) {
        this.receiverSys = value;
    }

}
