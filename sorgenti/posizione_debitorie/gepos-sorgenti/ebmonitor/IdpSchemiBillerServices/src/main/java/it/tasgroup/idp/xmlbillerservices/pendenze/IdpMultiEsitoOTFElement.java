
package it.tasgroup.idp.xmlbillerservices.pendenze;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IdpMultiEsitoOTFElement complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IdpMultiEsitoOTFElement">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MsgId" type="{http://idp.tasgroup.it/xmlbillerservices/Header}MsgId"/>
 *         &lt;element name="InfoMessaggio" type="{http://idp.tasgroup.it/xmlbillerservices/Pendenze}InfoMessaggio"/>
 *         &lt;element name="InfoDettaglio" type="{http://idp.tasgroup.it/xmlbillerservices/Pendenze}InfoDettaglio" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IdpMultiEsitoOTFElement", propOrder = {
    "msgId",
    "infoMessaggio",
    "infoDettaglio"
})
public class IdpMultiEsitoOTFElement {

    @XmlElement(name = "MsgId", required = true)
    protected String msgId;
    @XmlElement(name = "InfoMessaggio", required = true)
    protected InfoMessaggio infoMessaggio;
    @XmlElement(name = "InfoDettaglio")
    protected InfoDettaglio infoDettaglio;

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
     * Gets the value of the infoMessaggio property.
     * 
     * @return
     *     possible object is
     *     {@link InfoMessaggio }
     *     
     */
    public InfoMessaggio getInfoMessaggio() {
        return infoMessaggio;
    }

    /**
     * Sets the value of the infoMessaggio property.
     * 
     * @param value
     *     allowed object is
     *     {@link InfoMessaggio }
     *     
     */
    public void setInfoMessaggio(InfoMessaggio value) {
        this.infoMessaggio = value;
    }

    /**
     * Gets the value of the infoDettaglio property.
     * 
     * @return
     *     possible object is
     *     {@link InfoDettaglio }
     *     
     */
    public InfoDettaglio getInfoDettaglio() {
        return infoDettaglio;
    }

    /**
     * Sets the value of the infoDettaglio property.
     * 
     * @param value
     *     allowed object is
     *     {@link InfoDettaglio }
     *     
     */
    public void setInfoDettaglio(InfoDettaglio value) {
        this.infoDettaglio = value;
    }

}
