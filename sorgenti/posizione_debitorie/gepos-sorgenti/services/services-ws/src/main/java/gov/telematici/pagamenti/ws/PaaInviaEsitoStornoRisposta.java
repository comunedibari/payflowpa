
package gov.telematici.pagamenti.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for paaInviaEsitoStornoRisposta complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="paaInviaEsitoStornoRisposta">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="paaInviaEsitoStornoRisposta" type="{http://ws.pagamenti.telematici.gov/}tipoInviaEsitoStornoRisposta"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "paaInviaEsitoStornoRisposta", propOrder = {
    "paaInviaEsitoStornoRisposta"
})
public class PaaInviaEsitoStornoRisposta {

    @XmlElement(required = true)
    protected TipoInviaEsitoStornoRisposta paaInviaEsitoStornoRisposta;

    /**
     * Gets the value of the paaInviaEsitoStornoRisposta property.
     * 
     * @return
     *     possible object is
     *     {@link TipoInviaEsitoStornoRisposta }
     *     
     */
    public TipoInviaEsitoStornoRisposta getPaaInviaEsitoStornoRisposta() {
        return paaInviaEsitoStornoRisposta;
    }

    /**
     * Sets the value of the paaInviaEsitoStornoRisposta property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoInviaEsitoStornoRisposta }
     *     
     */
    public void setPaaInviaEsitoStornoRisposta(TipoInviaEsitoStornoRisposta value) {
        this.paaInviaEsitoStornoRisposta = value;
    }

}
