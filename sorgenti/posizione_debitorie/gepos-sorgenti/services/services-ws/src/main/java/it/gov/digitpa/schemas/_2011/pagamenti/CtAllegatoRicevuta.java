
package it.gov.digitpa.schemas._2011.pagamenti;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ctAllegatoRicevuta complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ctAllegatoRicevuta">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="tipoAllegatoRicevuta" type="{http://www.digitpa.gov.it/schemas/2011/Pagamenti/}stTipoAllegatoRicevuta"/>
 *         &lt;element name="testoAllegato" type="{http://www.digitpa.gov.it/schemas/2011/Pagamenti/}stBase64"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ctAllegatoRicevuta", propOrder = {
    "tipoAllegatoRicevuta",
    "testoAllegato"
})
public class CtAllegatoRicevuta {

    @XmlElement(required = true)
    protected StTipoAllegatoRicevuta tipoAllegatoRicevuta;
    @XmlElement(required = true)
    protected byte[] testoAllegato;

    /**
     * Gets the value of the tipoAllegatoRicevuta property.
     * 
     * @return
     *     possible object is
     *     {@link StTipoAllegatoRicevuta }
     *     
     */
    public StTipoAllegatoRicevuta getTipoAllegatoRicevuta() {
        return tipoAllegatoRicevuta;
    }

    /**
     * Sets the value of the tipoAllegatoRicevuta property.
     * 
     * @param value
     *     allowed object is
     *     {@link StTipoAllegatoRicevuta }
     *     
     */
    public void setTipoAllegatoRicevuta(StTipoAllegatoRicevuta value) {
        this.tipoAllegatoRicevuta = value;
    }

    /**
     * Gets the value of the testoAllegato property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getTestoAllegato() {
        return testoAllegato;
    }

    /**
     * Sets the value of the testoAllegato property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setTestoAllegato(byte[] value) {
        this.testoAllegato = ((byte[]) value);
    }

}
