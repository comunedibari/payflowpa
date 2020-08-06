
package it.tasgroup.idp.xmlbillerservices.header;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for OutboundHeaderTRT complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OutboundHeaderTRT">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MsgId" type="{http://idp.tasgroup.it/xmlbillerservices/Header}MsgId"/>
 *         &lt;element name="XMLCrtDt" type="{http://idp.tasgroup.it/xmlbillerservices/Common}IdPDateTime"/>
 *         &lt;element name="Receiver" type="{http://idp.tasgroup.it/xmlbillerservices/Header}TRTReceiver"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OutboundHeaderTRT", propOrder = {
    "msgId",
    "xmlCrtDt",
    "receiver"
})
public class OutboundHeaderTRT {

    @XmlElement(name = "MsgId", required = true)
    protected String msgId;
    @XmlElement(name = "XMLCrtDt", required = true)
    protected XMLGregorianCalendar xmlCrtDt;
    @XmlElement(name = "Receiver", required = true)
    protected TRTReceiver receiver;

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
