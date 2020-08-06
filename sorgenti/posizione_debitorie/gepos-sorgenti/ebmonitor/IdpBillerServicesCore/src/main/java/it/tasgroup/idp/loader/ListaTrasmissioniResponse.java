
package it.tasgroup.idp.loader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element name="trasmissioni" type="{http://idp.tasgroup.it/Loader/}trasmissioneType" maxOccurs="unbounded" minOccurs="0"/>
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
    "trasmissioni"
})
@XmlRootElement(name = "listaTrasmissioniResponse")
public class ListaTrasmissioniResponse
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected StatoEsitoType result;
    protected FaultType fault;
    protected List<TrasmissioneType> trasmissioni;

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
     * Gets the value of the trasmissioni property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the trasmissioni property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTrasmissioni().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TrasmissioneType }
     * 
     * 
     */
    public List<TrasmissioneType> getTrasmissioni() {
        if (trasmissioni == null) {
            trasmissioni = new ArrayList<TrasmissioneType>();
        }
        return this.trasmissioni;
    }

}
