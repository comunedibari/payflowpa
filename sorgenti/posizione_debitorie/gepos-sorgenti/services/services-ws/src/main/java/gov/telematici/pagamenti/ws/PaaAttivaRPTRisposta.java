
package gov.telematici.pagamenti.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for paaAttivaRPTRisposta complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="paaAttivaRPTRisposta">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="paaAttivaRPTRisposta" type="{http://ws.pagamenti.telematici.gov/}esitoAttivaRPT"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "paaAttivaRPTRisposta", propOrder = {
    "paaAttivaRPTRisposta"
})
public class PaaAttivaRPTRisposta {

    @XmlElement(required = true)
    protected EsitoAttivaRPT paaAttivaRPTRisposta;

    /**
     * Gets the value of the paaAttivaRPTRisposta property.
     * 
     * @return
     *     possible object is
     *     {@link EsitoAttivaRPT }
     *     
     */
    public EsitoAttivaRPT getPaaAttivaRPTRisposta() {
        return paaAttivaRPTRisposta;
    }

    /**
     * Sets the value of the paaAttivaRPTRisposta property.
     * 
     * @param value
     *     allowed object is
     *     {@link EsitoAttivaRPT }
     *     
     */
    public void setPaaAttivaRPTRisposta(EsitoAttivaRPT value) {
        this.paaAttivaRPTRisposta = value;
    }

}
