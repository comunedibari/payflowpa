
package it.toscana.rete.cart.servizi.iris_1_1.idpheader;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for E2ESender complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="E2ESender">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="E2ESndrId" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max35Text"/>
 *         &lt;element name="E2ESndrSys" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max35Text"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "E2ESender", propOrder = {
    "e2ESndrId",
    "e2ESndrSys"
})
public class E2ESender {

    @XmlElement(name = "E2ESndrId", required = true)
    protected String e2ESndrId;
    @XmlElement(name = "E2ESndrSys", required = true)
    protected String e2ESndrSys;

    /**
     * Gets the value of the e2ESndrId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getE2ESndrId() {
        return e2ESndrId;
    }

    /**
     * Sets the value of the e2ESndrId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setE2ESndrId(String value) {
        this.e2ESndrId = value;
    }

    /**
     * Gets the value of the e2ESndrSys property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getE2ESndrSys() {
        return e2ESndrSys;
    }

    /**
     * Sets the value of the e2ESndrSys property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setE2ESndrSys(String value) {
        this.e2ESndrSys = value;
    }

}
