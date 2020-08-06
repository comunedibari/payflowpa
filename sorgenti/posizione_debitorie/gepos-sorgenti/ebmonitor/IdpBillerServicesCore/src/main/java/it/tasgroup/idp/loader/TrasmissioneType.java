
package it.tasgroup.idp.loader;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for trasmissioneType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="trasmissioneType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="senderId" type="{http://idp.tasgroup.it/Loader/}stText35"/>
 *         &lt;element name="senderSys" type="{http://idp.tasgroup.it/Loader/}stText35"/>
 *         &lt;element name="msgId" type="{http://idp.tasgroup.it/Loader/}stText35"/>
 *         &lt;element name="timestampRicezione" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="statoEsito" type="{http://idp.tasgroup.it/Loader/}statoEsitoType"/>
 *         &lt;element name="numPagamenti" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="numPagamentiElaborati" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="numPagamentiOk" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "trasmissioneType", propOrder = {
    "senderId",
    "senderSys",
    "msgId",
    "timestampRicezione",
    "statoEsito",
    "numPagamenti",
    "numPagamentiElaborati",
    "numPagamentiOk"
})
public class TrasmissioneType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected String senderId;
    @XmlElement(required = true)
    protected String senderSys;
    @XmlElement(required = true)
    protected String msgId;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar timestampRicezione;
    @XmlElement(required = true)
    protected StatoEsitoType statoEsito;
    protected int numPagamenti;
    protected int numPagamentiElaborati;
    protected int numPagamentiOk;

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
     * Gets the value of the timestampRicezione property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTimestampRicezione() {
        return timestampRicezione;
    }

    /**
     * Sets the value of the timestampRicezione property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTimestampRicezione(XMLGregorianCalendar value) {
        this.timestampRicezione = value;
    }

    /**
     * Gets the value of the statoEsito property.
     * 
     * @return
     *     possible object is
     *     {@link StatoEsitoType }
     *     
     */
    public StatoEsitoType getStatoEsito() {
        return statoEsito;
    }

    /**
     * Sets the value of the statoEsito property.
     * 
     * @param value
     *     allowed object is
     *     {@link StatoEsitoType }
     *     
     */
    public void setStatoEsito(StatoEsitoType value) {
        this.statoEsito = value;
    }

    /**
     * Gets the value of the numPagamenti property.
     * 
     */
    public int getNumPagamenti() {
        return numPagamenti;
    }

    /**
     * Sets the value of the numPagamenti property.
     * 
     */
    public void setNumPagamenti(int value) {
        this.numPagamenti = value;
    }

    /**
     * Gets the value of the numPagamentiElaborati property.
     * 
     */
    public int getNumPagamentiElaborati() {
        return numPagamentiElaborati;
    }

    /**
     * Sets the value of the numPagamentiElaborati property.
     * 
     */
    public void setNumPagamentiElaborati(int value) {
        this.numPagamentiElaborati = value;
    }

    /**
     * Gets the value of the numPagamentiOk property.
     * 
     */
    public int getNumPagamentiOk() {
        return numPagamentiOk;
    }

    /**
     * Sets the value of the numPagamentiOk property.
     * 
     */
    public void setNumPagamentiOk(int value) {
        this.numPagamentiOk = value;
    }

}
