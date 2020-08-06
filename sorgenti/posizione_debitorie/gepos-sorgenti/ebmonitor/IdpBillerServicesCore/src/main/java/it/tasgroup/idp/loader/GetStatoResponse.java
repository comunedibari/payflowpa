
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
 *         &lt;element name="statoEsito" type="{http://idp.tasgroup.it/Loader/}statoEsitoType"/>
 *         &lt;element name="numPagamenti" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="numPagamentiElaborati" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="numPagamentiOk" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "statoEsito",
    "numPagamenti",
    "numPagamentiElaborati",
    "numPagamentiOk"
})
@XmlRootElement(name = "getStatoResponse")
public class GetStatoResponse
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected StatoEsitoType statoEsito;
    protected int numPagamenti;
    protected int numPagamentiElaborati;
    protected int numPagamentiOk;

    /**
     * Gets the value of the statoEsito property.
     * 
     * @return
     *     possible object is
     *     {@link StatoEsitoType }
     *     
     */
    public StatoEsitoType getStatoEsito() {
        return statoEsito;
    }

    /**
     * Sets the value of the statoEsito property.
     * 
     * @param value
     *     allowed object is
     *     {@link StatoEsitoType }
     *     
     */
    public void setStatoEsito(StatoEsitoType value) {
        this.statoEsito = value;
    }

    /**
     * Gets the value of the numPagamenti property.
     * 
     */
    public int getNumPagamenti() {
        return numPagamenti;
    }

    /**
     * Sets the value of the numPagamenti property.
     * 
     */
    public void setNumPagamenti(int value) {
        this.numPagamenti = value;
    }

    /**
     * Gets the value of the numPagamentiElaborati property.
     * 
     */
    public int getNumPagamentiElaborati() {
        return numPagamentiElaborati;
    }

    /**
     * Sets the value of the numPagamentiElaborati property.
     * 
     */
    public void setNumPagamentiElaborati(int value) {
        this.numPagamentiElaborati = value;
    }

    /**
     * Gets the value of the numPagamentiOk property.
     * 
     */
    public int getNumPagamentiOk() {
        return numPagamentiOk;
    }

    /**
     * Sets the value of the numPagamentiOk property.
     * 
     */
    public void setNumPagamentiOk(int value) {
        this.numPagamentiOk = value;
    }

}
