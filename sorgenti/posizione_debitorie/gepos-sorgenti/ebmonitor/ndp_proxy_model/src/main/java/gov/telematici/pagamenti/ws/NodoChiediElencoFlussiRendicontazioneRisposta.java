
package gov.telematici.pagamenti.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for nodoChiediElencoFlussiRendicontazioneRisposta complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="nodoChiediElencoFlussiRendicontazioneRisposta">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.pagamenti.telematici.gov/}risposta">
 *       &lt;sequence>
 *         &lt;element name="elencoFlussiRendicontazione" type="{http://ws.pagamenti.telematici.gov/}tipoElencoFlussiRendicontazione" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "nodoChiediElencoFlussiRendicontazioneRisposta", propOrder = {
    "elencoFlussiRendicontazione"
})
public class NodoChiediElencoFlussiRendicontazioneRisposta
    extends Risposta
{

    protected TipoElencoFlussiRendicontazione elencoFlussiRendicontazione;

    /**
     * Gets the value of the elencoFlussiRendicontazione property.
     * 
     * @return
     *     possible object is
     *     {@link TipoElencoFlussiRendicontazione }
     *     
     */
    public TipoElencoFlussiRendicontazione getElencoFlussiRendicontazione() {
        return elencoFlussiRendicontazione;
    }

    /**
     * Sets the value of the elencoFlussiRendicontazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoElencoFlussiRendicontazione }
     *     
     */
    public void setElencoFlussiRendicontazione(TipoElencoFlussiRendicontazione value) {
        this.elencoFlussiRendicontazione = value;
    }

}
