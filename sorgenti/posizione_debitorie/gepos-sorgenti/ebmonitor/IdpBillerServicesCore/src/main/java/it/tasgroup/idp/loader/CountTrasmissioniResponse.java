
package it.tasgroup.idp.loader;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="result" type="{http://idp.tasgroup.it/Loader/}statoEsitoType"/>
 *         &lt;element name="fault" type="{http://idp.tasgroup.it/Loader/}FaultType" minOccurs="0"/>
 *         &lt;element name="numTrasmissioni" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "result",
    "fault",
    "numTrasmissioni"
})
@XmlRootElement(name = "countTrasmissioniResponse")
public class CountTrasmissioniResponse
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected StatoEsitoType result;
    protected FaultType fault;
    protected Integer numTrasmissioni;

    /**
     * Gets the value of the result property.
     * 
     * @return
     *     possible object is
     *     {@link StatoEsitoType }
     *     
     */
    public StatoEsitoType getResult() {
        return result;
    }

    /**
     * Sets the value of the result property.
     * 
     * @param value
     *     allowed object is
     *     {@link StatoEsitoType }
     *     
     */
    public void setResult(StatoEsitoType value) {
        this.result = value;
    }

    /**
     * Gets the value of the fault property.
     * 
     * @return
     *     possible object is
     *     {@link FaultType }
     *     
     */
    public FaultType getFault() {
        return fault;
    }

    /**
     * Sets the value of the fault property.
     * 
     * @param value
     *     allowed object is
     *     {@link FaultType }
     *     
     */
    public void setFault(FaultType value) {
        this.fault = value;
    }

    /**
     * Gets the value of the numTrasmissioni property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumTrasmissioni() {
        return numTrasmissioni;
    }

    /**
     * Sets the value of the numTrasmissioni property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumTrasmissioni(Integer value) {
        this.numTrasmissioni = value;
    }

}
