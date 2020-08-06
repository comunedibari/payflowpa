
package it.tasgroup.idp.notification;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for notificaType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="notificaType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="senderId" type="{http://idp.tasgroup.it/Notification/}stText35"/>
 *         &lt;element name="senderSys" type="{http://idp.tasgroup.it/Notification/}stText35"/>
 *         &lt;element name="timestampCreazione" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="msgId" type="{http://idp.tasgroup.it/Notification/}stText35" minOccurs="0"/>
 *         &lt;element name="tipoNotifica" type="{http://idp.tasgroup.it/Notification/}tipoNotificaType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "notificaType", propOrder = {
    "senderId",
    "senderSys",
    "timestampCreazione",
    "msgId",
    "tipoNotifica"
})
public class NotificaType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected String senderId;
    @XmlElement(required = true)
    protected String senderSys;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar timestampCreazione;
    protected String msgId;
    @XmlElement(required = true)
    protected TipoNotificaType tipoNotifica;

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

    /**
     * Gets the value of the timestampCreazione property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTimestampCreazione() {
        return timestampCreazione;
    }

    /**
     * Sets the value of the timestampCreazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTimestampCreazione(XMLGregorianCalendar value) {
        this.timestampCreazione = value;
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
     * Gets the value of the tipoNotifica property.
     * 
     * @return
     *     possible object is
     *     {@link TipoNotificaType }
     *     
     */
    public TipoNotificaType getTipoNotifica() {
        return tipoNotifica;
    }

    /**
     * Sets the value of the tipoNotifica property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoNotificaType }
     *     
     */
    public void setTipoNotifica(TipoNotificaType value) {
        this.tipoNotifica = value;
    }

}
