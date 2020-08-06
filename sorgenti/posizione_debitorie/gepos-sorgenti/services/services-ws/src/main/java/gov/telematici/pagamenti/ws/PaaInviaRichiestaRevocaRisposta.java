
package gov.telematici.pagamenti.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for paaInviaRichiestaRevocaRisposta complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="paaInviaRichiestaRevocaRisposta">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="paaInviaRichiestaRevocaRisposta" type="{http://ws.pagamenti.telematici.gov/}tipoInviaRichiestaRevocaRisposta"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "paaInviaRichiestaRevocaRisposta", propOrder = {
    "paaInviaRichiestaRevocaRisposta"
})
public class PaaInviaRichiestaRevocaRisposta {

    @XmlElement(required = true)
    protected TipoInviaRichiestaRevocaRisposta paaInviaRichiestaRevocaRisposta;

    /**
     * Gets the value of the paaInviaRichiestaRevocaRisposta property.
     * 
     * @return
     *     possible object is
     *     {@link TipoInviaRichiestaRevocaRisposta }
     *     
     */
    public TipoInviaRichiestaRevocaRisposta getPaaInviaRichiestaRevocaRisposta() {
        return paaInviaRichiestaRevocaRisposta;
    }

    /**
     * Sets the value of the paaInviaRichiestaRevocaRisposta property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoInviaRichiestaRevocaRisposta }
     *     
     */
    public void setPaaInviaRichiestaRevocaRisposta(TipoInviaRichiestaRevocaRisposta value) {
        this.paaInviaRichiestaRevocaRisposta = value;
    }

}
