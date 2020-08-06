
package it.toscana.rete.cart.servizi.iris_1_1.idpheader;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for HeaderE2E complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HeaderE2E">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="E2ESrvcNm" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max60Text"/>
 *         &lt;element name="E2EMsgId" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpHeader}MsgId"/>
 *         &lt;element name="XMLCrtDt" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}IdPDateTime"/>
 *         &lt;element name="Sender" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpHeader}E2ESender"/>
 *         &lt;element name="Receiver" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpHeader}E2EReceiver"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HeaderE2E", propOrder = {
    "e2ESrvcNm",
    "e2EMsgId",
    "xmlCrtDt",
    "sender",
    "receiver"
})
public class HeaderE2E {

    @XmlElement(name = "E2ESrvcNm", required = true)
    protected String e2ESrvcNm;
    @XmlElement(name = "E2EMsgId", required = true)
    protected String e2EMsgId;
    @XmlElement(name = "XMLCrtDt", required = true)
    protected XMLGregorianCalendar xmlCrtDt;
    @XmlElement(name = "Sender", required = true)
    protected E2ESender sender;
    @XmlElement(name = "Receiver", required = true)
    protected E2EReceiver receiver;

    /**
     * Gets the value of the e2ESrvcNm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getE2ESrvcNm() {
        return e2ESrvcNm;
    }

    /**
     * Sets the value of the e2ESrvcNm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setE2ESrvcNm(String value) {
        this.e2ESrvcNm = value;
    }

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
     * Gets the value of the sender property.
     * 
     * @return
     *     possible object is
     *     {@link E2ESender }
     *     
     */
    public E2ESender getSender() {
        return sender;
    }

    /**
     * Sets the value of the sender property.
     * 
     * @param value
     *     allowed object is
     *     {@link E2ESender }
     *     
     */
    public void setSender(E2ESender value) {
        this.sender = value;
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
