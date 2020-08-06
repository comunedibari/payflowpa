
package gov.telematici.pagamenti.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for paaVerificaRPTRisposta complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="paaVerificaRPTRisposta">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="paaVerificaRPTRisposta" type="{http://ws.pagamenti.telematici.gov/}esitoVerificaRPT"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "paaVerificaRPTRisposta", propOrder = {
    "paaVerificaRPTRisposta"
})
public class PaaVerificaRPTRisposta {

    @XmlElement(required = true)
    protected EsitoVerificaRPT paaVerificaRPTRisposta;

    /**
     * Gets the value of the paaVerificaRPTRisposta property.
     * 
     * @return
     *     possible object is
     *     {@link EsitoVerificaRPT }
     *     
     */
    public EsitoVerificaRPT getPaaVerificaRPTRisposta() {
        return paaVerificaRPTRisposta;
    }

    /**
     * Sets the value of the paaVerificaRPTRisposta property.
     * 
     * @param value
     *     allowed object is
     *     {@link EsitoVerificaRPT }
     *     
     */
    public void setPaaVerificaRPTRisposta(EsitoVerificaRPT value) {
        this.paaVerificaRPTRisposta = value;
    }

}
