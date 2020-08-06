
package it.tasgroup.idp.xmlbillerservices.header;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for E2EReceiver complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="E2EReceiver">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="E2ERcvrId" type="{http://idp.tasgroup.it/xmlbillerservices/Common}Max35Text"/>
 *         &lt;element name="E2ERcvrSys" type="{http://idp.tasgroup.it/xmlbillerservices/Common}Max35Text"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "E2EReceiver", propOrder = {
    "e2ERcvrId",
    "e2ERcvrSys"
})
public class E2EReceiver {

    @XmlElement(name = "E2ERcvrId", required = true)
    protected String e2ERcvrId;
    @XmlElement(name = "E2ERcvrSys", required = true)
    protected String e2ERcvrSys;

    /**
     * Gets the value of the e2ERcvrId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getE2ERcvrId() {
        return e2ERcvrId;
    }

    /**
     * Sets the value of the e2ERcvrId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setE2ERcvrId(String value) {
        this.e2ERcvrId = value;
    }

    /**
     * Gets the value of the e2ERcvrSys property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getE2ERcvrSys() {
        return e2ERcvrSys;
    }

    /**
     * Sets the value of the e2ERcvrSys property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setE2ERcvrSys(String value) {
        this.e2ERcvrSys = value;
    }

}
