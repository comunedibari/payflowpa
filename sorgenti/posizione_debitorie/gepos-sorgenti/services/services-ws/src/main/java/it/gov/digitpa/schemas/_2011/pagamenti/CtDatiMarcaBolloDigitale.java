
package it.gov.digitpa.schemas._2011.pagamenti;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ctDatiMarcaBolloDigitale complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ctDatiMarcaBolloDigitale">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="tipoBollo" type="{http://www.digitpa.gov.it/schemas/2011/Pagamenti/}stTipoBollo"/>
 *         &lt;element name="hashDocumento" type="{http://www.digitpa.gov.it/schemas/2011/Pagamenti/}stText70"/>
 *         &lt;element name="provinciaResidenza" type="{http://www.digitpa.gov.it/schemas/2011/Pagamenti/}stNazioneProvincia"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ctDatiMarcaBolloDigitale", propOrder = {
    "tipoBollo",
    "hashDocumento",
    "provinciaResidenza"
})
public class CtDatiMarcaBolloDigitale {

    @XmlElement(required = true)
    protected String tipoBollo;
    @XmlElement(required = true)
    protected String hashDocumento;
    @XmlElement(required = true)
    protected String provinciaResidenza;

    /**
     * Gets the value of the tipoBollo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoBollo() {
        return tipoBollo;
    }

    /**
     * Sets the value of the tipoBollo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoBollo(String value) {
        this.tipoBollo = value;
    }

    /**
     * Gets the value of the hashDocumento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHashDocumento() {
        return hashDocumento;
    }

    /**
     * Sets the value of the hashDocumento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHashDocumento(String value) {
        this.hashDocumento = value;
    }

    /**
     * Gets the value of the provinciaResidenza property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProvinciaResidenza() {
        return provinciaResidenza;
    }

    /**
     * Sets the value of the provinciaResidenza property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProvinciaResidenza(String value) {
        this.provinciaResidenza = value;
    }

}
