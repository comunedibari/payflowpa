
package it.tasgroup.idp.xmlbillerservices.header;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for OutboundHeaderE2E complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OutboundHeaderE2E">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="E2EMsgId" type="{http://idp.tasgroup.it/xmlbillerservices/Header}MsgId"/>
 *         &lt;element name="XMLCrtDt" type="{http://idp.tasgroup.it/xmlbillerservices/Common}IdPDateTime"/>
 *         &lt;element name="Receiver" type="{http://idp.tasgroup.it/xmlbillerservices/Header}E2EReceiver"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OutboundHeaderE2E", propOrder = {
    "e2EMsgId",
    "xmlCrtDt",
    "receiver"
})
public class OutboundHeaderE2E {

    @XmlElement(name = "E2EMsgId", required = true)
    protected String e2EMsgId;
    @XmlElement(name = "XMLCrtDt", required = true)
    protected XMLGregorianCalendar xmlCrtDt;
    @XmlElement(name = "Receiver", required = true)
    protected E2EReceiver receiver;

    /**
     * Gets the value of the e2EMsgId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getE2EMsgId() {
        return e2EMsgId;
    }

    /**
     * Sets the value of the e2EMsgId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setE2EMsgId(String value) {
        this.e2EMsgId = value;
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
     *     {@link E2EReceiver }
     *     
     */
    public E2EReceiver getReceiver() {
        return receiver;
    }

    /**
     * Sets the value of the receiver property.
     * 
     * @param value
     *     allowed object is
     *     {@link E2EReceiver }
     *     
     */
    public void setReceiver(E2EReceiver value) {
        this.receiver = value;
    }

}
