
package it.tasgroup.idp.xmlbillerservices.header;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OutboundIdpHeader complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OutboundIdpHeader">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TRT" type="{http://idp.tasgroup.it/xmlbillerservices/Header}OutboundHeaderTRT"/>
 *         &lt;element name="E2E" type="{http://idp.tasgroup.it/xmlbillerservices/Header}OutboundHeaderE2E"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OutboundIdpHeader", propOrder = {
    "trt",
    "e2E"
})
public class OutboundIdpHeader {

    @XmlElement(name = "TRT", required = true)
    protected OutboundHeaderTRT trt;
    @XmlElement(name = "E2E", required = true)
    protected OutboundHeaderE2E e2E;

    /**
     * Gets the value of the trt property.
     * 
     * @return
     *     possible object is
     *     {@link OutboundHeaderTRT }
     *     
     */
    public OutboundHeaderTRT getTRT() {
        return trt;
    }

    /**
     * Sets the value of the trt property.
     * 
     * @param value
     *     allowed object is
     *     {@link OutboundHeaderTRT }
     *     
     */
    public void setTRT(OutboundHeaderTRT value) {
        this.trt = value;
    }

    /**
     * Gets the value of the e2E property.
     * 
     * @return
     *     possible object is
     *     {@link OutboundHeaderE2E }
     *     
     */
    public OutboundHeaderE2E getE2E() {
        return e2E;
    }

    /**
     * Sets the value of the e2E property.
     * 
     * @param value
     *     allowed object is
     *     {@link OutboundHeaderE2E }
     *     
     */
    public void setE2E(OutboundHeaderE2E value) {
        this.e2E = value;
    }

}
