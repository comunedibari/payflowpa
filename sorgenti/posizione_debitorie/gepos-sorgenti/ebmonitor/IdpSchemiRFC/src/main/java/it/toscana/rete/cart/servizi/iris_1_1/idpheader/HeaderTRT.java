
package it.toscana.rete.cart.servizi.iris_1_1.idpheader;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for HeaderTRT complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HeaderTRT">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ServiceName" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpHeader}ServiceName"/>
 *         &lt;element name="MsgId" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpHeader}MsgId"/>
 *         &lt;element name="XMLCrtDt" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}IdPDateTime"/>
 *         &lt;element name="Sender" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpHeader}TRTSender"/>
 *         &lt;element name="Receiver" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpHeader}TRTReceiver"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HeaderTRT", propOrder = {
    "serviceName",
    "msgId",
    "xmlCrtDt",
    "sender",
    "receiver"
})
public class HeaderTRT {

    @XmlElement(name = "ServiceName", required = true)
    protected ServiceName serviceName;
    @XmlElement(name = "MsgId", required = true)
    protected String msgId;
    @XmlElement(name = "XMLCrtDt", required = true)
    protected XMLGregorianCalendar xmlCrtDt;
    @XmlElement(name = "Sender", required = true)
    protected TRTSender sender;
    @XmlElement(name = "Receiver", required = true)
    protected TRTReceiver receiver;

    /**
     * Gets the value of the serviceName property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceName }
     *     
     */
    public ServiceName getServiceName() {
        return serviceName;
    }

    /**
     * Sets the value of the serviceName property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceName }
     *     
     */
    public void setServiceName(ServiceName value) {
        this.serviceName = value;
    }

    /**
     * Gets the value of the msgId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMsgId() {
        return msgId;
    }

    /**
     * Sets the value of the msgId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMsgId(String value) {
        this.msgId = value;
    }

    /**
     * Gets the value of the xmlCrtDt property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getXMLCrtDt() {
        return xmlCrtDt;
    }

    /**
     * Sets the value of the xmlCrtDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setXMLCrtDt(XMLGregorianCalendar value) {
        this.xmlCrtDt = value;
    }

    /**
     * Gets the value of the sender property.
     * 
     * @return
     *     possible object is
     *     {@link TRTSender }
     *     
     */
    public TRTSender getSender() {
        return sender;
    }

    /**
     * Sets the value of the sender property.
     * 
     * @param value
     *     allowed object is
     *     {@link TRTSender }
     *     
     */
    public void setSender(TRTSender value) {
        this.sender = value;
    }

    /**
     * Gets the value of the receiver property.
     * 
     * @return
     *     possible object is
     *     {@link TRTReceiver }
     *     
     */
    public TRTReceiver getReceiver() {
        return receiver;
    }

    /**
     * Sets the value of the receiver property.
     * 
     * @param value
     *     allowed object is
     *     {@link TRTReceiver }
     *     
     */
    public void setReceiver(TRTReceiver value) {
        this.receiver = value;
    }

}
