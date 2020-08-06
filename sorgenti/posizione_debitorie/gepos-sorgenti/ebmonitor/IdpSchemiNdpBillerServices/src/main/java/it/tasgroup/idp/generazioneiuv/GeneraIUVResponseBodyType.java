
package it.tasgroup.idp.generazioneiuv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GeneraIUVResponseBodyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GeneraIUVResponseBodyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IdentificativoDominio" type="{http://idp.tasgroup.it/GenerazioneIUV/}stText35"/>
 *         &lt;element name="TipoDebito" type="{http://idp.tasgroup.it/GenerazioneIUV/}stText50"/>
 *         &lt;element name="ElencoIdentificativi" type="{http://idp.tasgroup.it/GenerazioneIUV/}ElencoIdentificativiType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GeneraIUVResponseBodyType", propOrder = {
    "identificativoDominio",
    "tipoDebito",
    "elencoIdentificativi"
})
public class GeneraIUVResponseBodyType {

    @XmlElement(name = "IdentificativoDominio", required = true)
    protected String identificativoDominio;
    @XmlElement(name = "TipoDebito", required = true)
    protected String tipoDebito;
    @XmlElement(name = "ElencoIdentificativi", required = true)
    protected ElencoIdentificativiType elencoIdentificativi;

    /**
     * Gets the value of the identificativoDominio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativoDominio() {
        return identificativoDominio;
    }

    /**
     * Sets the value of the identificativoDominio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativoDominio(String value) {
        this.identificativoDominio = value;
    }

    /**
     * Gets the value of the tipoDebito property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoDebito() {
        return tipoDebito;
    }

    /**
     * Sets the value of the tipoDebito property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoDebito(String value) {
        this.tipoDebito = value;
    }

    /**
     * Gets the value of the elencoIdentificativi property.
     * 
     * @return
     *     possible object is
     *     {@link ElencoIdentificativiType }
     *     
     */
    public ElencoIdentificativiType getElencoIdentificativi() {
        return elencoIdentificativi;
    }

    /**
     * Sets the value of the elencoIdentificativi property.
     * 
     * @param value
     *     allowed object is
     *     {@link ElencoIdentificativiType }
     *     
     */
    public void setElencoIdentificativi(ElencoIdentificativiType value) {
        this.elencoIdentificativi = value;
    }

}
