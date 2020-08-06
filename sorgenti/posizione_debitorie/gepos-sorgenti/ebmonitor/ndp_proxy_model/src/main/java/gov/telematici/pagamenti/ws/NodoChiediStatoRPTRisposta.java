
package gov.telematici.pagamenti.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for nodoChiediStatoRPTRisposta complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="nodoChiediStatoRPTRisposta">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.pagamenti.telematici.gov/}risposta">
 *       &lt;sequence>
 *         &lt;element name="esito" type="{http://ws.pagamenti.telematici.gov/}esitoChiediStatoRPT" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "nodoChiediStatoRPTRisposta", propOrder = {
    "esito"
})
public class NodoChiediStatoRPTRisposta
    extends Risposta
{

    protected EsitoChiediStatoRPT esito;

    /**
     * Gets the value of the esito property.
     * 
     * @return
     *     possible object is
     *     {@link EsitoChiediStatoRPT }
     *     
     */
    public EsitoChiediStatoRPT getEsito() {
        return esito;
    }

    /**
     * Sets the value of the esito property.
     * 
     * @param value
     *     allowed object is
     *     {@link EsitoChiediStatoRPT }
     *     
     */
    public void setEsito(EsitoChiediStatoRPT value) {
        this.esito = value;
    }

}
