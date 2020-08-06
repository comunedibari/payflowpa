
package gov.telematici.pagamenti.ws;

import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for nodoChiediFlussoRendicontazioneRisposta complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="nodoChiediFlussoRendicontazioneRisposta">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.pagamenti.telematici.gov/}risposta">
 *       &lt;sequence>
 *         &lt;element name="xmlRendicontazione" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "nodoChiediFlussoRendicontazioneRisposta", propOrder = {
    "xmlRendicontazione"
})
public class NodoChiediFlussoRendicontazioneRisposta
    extends Risposta
{

    @XmlMimeType("application/octet-stream")
    protected DataHandler xmlRendicontazione;

    /**
     * Gets the value of the xmlRendicontazione property.
     * 
     * @return
     *     possible object is
     *     {@link DataHandler }
     *     
     */
    public DataHandler getXmlRendicontazione() {
        return xmlRendicontazione;
    }

    /**
     * Sets the value of the xmlRendicontazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataHandler }
     *     
     */
    public void setXmlRendicontazione(DataHandler value) {
        this.xmlRendicontazione = value;
    }

}
