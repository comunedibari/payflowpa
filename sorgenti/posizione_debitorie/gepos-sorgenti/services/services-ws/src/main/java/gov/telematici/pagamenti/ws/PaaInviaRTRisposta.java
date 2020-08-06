
package gov.telematici.pagamenti.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for paaInviaRTRisposta complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="paaInviaRTRisposta">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="paaInviaRTRisposta" type="{http://ws.pagamenti.telematici.gov/}esitoPaaInviaRT"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "paaInviaRTRisposta", propOrder = {
    "paaInviaRTRisposta"
})
public class PaaInviaRTRisposta {

    @XmlElement(required = true)
    protected EsitoPaaInviaRT paaInviaRTRisposta;

    /**
     * Gets the value of the paaInviaRTRisposta property.
     * 
     * @return
     *     possible object is
     *     {@link EsitoPaaInviaRT }
     *     
     */
    public EsitoPaaInviaRT getPaaInviaRTRisposta() {
        return paaInviaRTRisposta;
    }

    /**
     * Sets the value of the paaInviaRTRisposta property.
     * 
     * @param value
     *     allowed object is
     *     {@link EsitoPaaInviaRT }
     *     
     */
    public void setPaaInviaRTRisposta(EsitoPaaInviaRT value) {
        this.paaInviaRTRisposta = value;
    }

}
