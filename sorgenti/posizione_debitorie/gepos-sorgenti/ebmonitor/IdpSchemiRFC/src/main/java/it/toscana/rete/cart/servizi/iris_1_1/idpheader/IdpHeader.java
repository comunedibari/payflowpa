
package it.toscana.rete.cart.servizi.iris_1_1.idpheader;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IdpHeader complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IdpHeader">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TRT" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpHeader}HeaderTRT"/>
 *         &lt;element name="E2E" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpHeader}HeaderE2E"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IdpHeader", propOrder = {
    "trt",
    "e2E"
})
public class IdpHeader {

    @XmlElement(name = "TRT", required = true)
    protected HeaderTRT trt;
    @XmlElement(name = "E2E", required = true)
    protected HeaderE2E e2E;

    /**
     * Gets the value of the trt property.
     * 
     * @return
     *     possible object is
     *     {@link HeaderTRT }
     *     
     */
    public HeaderTRT getTRT() {
        return trt;
    }

    /**
     * Sets the value of the trt property.
     * 
     * @param value
     *     allowed object is
     *     {@link HeaderTRT }
     *     
     */
    public void setTRT(HeaderTRT value) {
        this.trt = value;
    }

    /**
     * Gets the value of the e2E property.
     * 
     * @return
     *     possible object is
     *     {@link HeaderE2E }
     *     
     */
    public HeaderE2E getE2E() {
        return e2E;
    }

    /**
     * Sets the value of the e2E property.
     * 
     * @param value
     *     allowed object is
     *     {@link HeaderE2E }
     *     
     */
    public void setE2E(HeaderE2E value) {
        this.e2E = value;
    }

}
