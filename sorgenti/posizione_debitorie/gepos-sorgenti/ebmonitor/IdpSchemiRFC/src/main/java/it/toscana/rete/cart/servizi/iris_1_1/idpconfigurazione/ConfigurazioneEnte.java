
package it.toscana.rete.cart.servizi.iris_1_1.idpconfigurazione;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ConfigurazioneEnte complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ConfigurazioneEnte">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DatiAnagraficiEnte" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpConfigurazione}DatiAnagraficiEnte"/>
 *         &lt;element name="ParametriGenerazioneIUV" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpConfigurazione}ParametriGenerazioneIUVEnteType" minOccurs="0"/>
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
@XmlType(name = "ConfigurazioneEnte", propOrder = {
    "datiAnagraficiEnte",
    "parametriGenerazioneIUV"
})
public class ConfigurazioneEnte {

    @XmlElement(name = "DatiAnagraficiEnte", required = true)
    protected DatiAnagraficiEnte datiAnagraficiEnte;
    @XmlElement(name = "ParametriGenerazioneIUV")
    protected ParametriGenerazioneIUVEnteType parametriGenerazioneIUV;
    @XmlAttribute(name = "TipoOperazione", required = true)
    protected TipoOperazione tipoOperazione;

    /**
     * Gets the value of the datiAnagraficiEnte property.
     * 
     * @return
     *     possible object is
     *     {@link DatiAnagraficiEnte }
     *     
     */
    public DatiAnagraficiEnte getDatiAnagraficiEnte() {
        return datiAnagraficiEnte;
    }

    /**
     * Sets the value of the datiAnagraficiEnte property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiAnagraficiEnte }
     *     
     */
    public void setDatiAnagraficiEnte(DatiAnagraficiEnte value) {
        this.datiAnagraficiEnte = value;
    }

    /**
     * Gets the value of the parametriGenerazioneIUV property.
     * 
     * @return
     *     possible object is
     *     {@link ParametriGenerazioneIUVEnteType }
     *     
     */
    public ParametriGenerazioneIUVEnteType getParametriGenerazioneIUV() {
        return parametriGenerazioneIUV;
    }

    /**
     * Sets the value of the parametriGenerazioneIUV property.
     * 
     * @param value
     *     allowed object is
     *     {@link ParametriGenerazioneIUVEnteType }
     *     
     */
    public void setParametriGenerazioneIUV(ParametriGenerazioneIUVEnteType value) {
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
