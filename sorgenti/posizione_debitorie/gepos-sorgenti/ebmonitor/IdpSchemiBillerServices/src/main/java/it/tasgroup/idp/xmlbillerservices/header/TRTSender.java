
package it.tasgroup.idp.xmlbillerservices.header;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TRTSender complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TRTSender">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SenderId" type="{http://idp.tasgroup.it/xmlbillerservices/Common}Max35Text"/>
 *         &lt;element name="SenderSys" type="{http://idp.tasgroup.it/xmlbillerservices/Common}Max35Text"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TRTSender", propOrder = {
    "senderId",
    "senderSys"
})
public class TRTSender {

    @XmlElement(name = "SenderId", required = true)
    protected String senderId;
    @XmlElement(name = "SenderSys", required = true)
    protected String senderSys;

    /**
     * Gets the value of the senderId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSenderId() {
        return senderId;
    }

    /**
     * Sets the value of the senderId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSenderId(String value) {
        this.senderId = value;
    }

    /**
     * Gets the value of the senderSys property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSenderSys() {
        return senderSys;
    }

    /**
     * Sets the value of the senderSys property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSenderSys(String value) {
        this.senderSys = value;
    }

}
