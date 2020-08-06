
package it.tasgroup.idp.xmlbillerservices.header;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InboundIdpHeader complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InboundIdpHeader">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TRT" type="{http://idp.tasgroup.it/xmlbillerservices/Header}InboundHeaderTRT"/>
 *         &lt;element name="E2E" type="{http://idp.tasgroup.it/xmlbillerservices/Header}InboundHeaderE2E"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InboundIdpHeader", propOrder = {
    "trt",
    "e2E"
})
public class InboundIdpHeader {

    @XmlElement(name = "TRT", required = true)
    protected InboundHeaderTRT trt;
    @XmlElement(name = "E2E", required = true)
    protected InboundHeaderE2E e2E;

    /**
     * Gets the value of the trt property.
     * 
     * @return
     *     possible object is
     *     {@link InboundHeaderTRT }
     *     
     */
    public InboundHeaderTRT getTRT() {
        return trt;
    }

    /**
     * Sets the value of the trt property.
     * 
     * @param value
     *     allowed object is
     *     {@link InboundHeaderTRT }
     *     
     */
    public void setTRT(InboundHeaderTRT value) {
        this.trt = value;
    }

    /**
     * Gets the value of the e2E property.
     * 
     * @return
     *     possible object is
     *     {@link InboundHeaderE2E }
     *     
     */
    public InboundHeaderE2E getE2E() {
        return e2E;
    }

    /**
     * Sets the value of the e2E property.
     * 
     * @param value
     *     allowed object is
     *     {@link InboundHeaderE2E }
     *     
     */
    public void setE2E(InboundHeaderE2E value) {
        this.e2E = value;
    }

}
