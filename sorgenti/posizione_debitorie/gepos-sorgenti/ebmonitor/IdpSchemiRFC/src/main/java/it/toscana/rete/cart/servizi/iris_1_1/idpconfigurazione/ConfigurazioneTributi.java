
package it.toscana.rete.cart.servizi.iris_1_1.idpconfigurazione;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ConfigurazioneTributi complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ConfigurazioneTributi">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DatiAnagraficiTributo" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpConfigurazione}Tributo"/>
 *         &lt;element name="ParametriGenerazioneIUV" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpConfigurazione}ParametriGenerazioneIUVTributoType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="TipoOperazione" use="required" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpConfigurazione}TipoOperazione" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConfigurazioneTributi", propOrder = {
    "datiAnagraficiTributo",
    "parametriGenerazioneIUV"
})
public class ConfigurazioneTributi {

    @XmlElement(name = "DatiAnagraficiTributo", required = true)
    protected Tributo datiAnagraficiTributo;
    @XmlElement(name = "ParametriGenerazioneIUV")
    protected ParametriGenerazioneIUVTributoType parametriGenerazioneIUV;
    @XmlAttribute(name = "TipoOperazione", required = true)
    protected TipoOperazione tipoOperazione;

    /**
     * Gets the value of the datiAnagraficiTributo property.
     * 
     * @return
     *     possible object is
     *     {@link Tributo }
     *     
     */
    public Tributo getDatiAnagraficiTributo() {
        return datiAnagraficiTributo;
    }

    /**
     * Sets the value of the datiAnagraficiTributo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Tributo }
     *     
     */
    public void setDatiAnagraficiTributo(Tributo value) {
        this.datiAnagraficiTributo = value;
    }

    /**
     * Gets the value of the parametriGenerazioneIUV property.
     * 
     * @return
     *     possible object is
     *     {@link ParametriGenerazioneIUVTributoType }
     *     
     */
    public ParametriGenerazioneIUVTributoType getParametriGenerazioneIUV() {
        return parametriGenerazioneIUV;
    }

    /**
     * Sets the value of the parametriGenerazioneIUV property.
     * 
     * @param value
     *     allowed object is
     *     {@link ParametriGenerazioneIUVTributoType }
     *     
     */
    public void setParametriGenerazioneIUV(ParametriGenerazioneIUVTributoType value) {
        this.parametriGenerazioneIUV = value;
    }

    /**
     * Gets the value of the tipoOperazione property.
     * 
     * @return
     *     possible object is
     *     {@link TipoOperazione }
     *     
     */
    public TipoOperazione getTipoOperazione() {
        return tipoOperazione;
    }

    /**
     * Sets the value of the tipoOperazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoOperazione }
     *     
     */
    public void setTipoOperazione(TipoOperazione value) {
        this.tipoOperazione = value;
    }

}
