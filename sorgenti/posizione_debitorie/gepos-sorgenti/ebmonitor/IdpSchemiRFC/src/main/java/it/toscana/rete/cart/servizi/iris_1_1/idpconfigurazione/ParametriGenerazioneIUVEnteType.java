
package it.toscana.rete.cart.servizi.iris_1_1.idpconfigurazione;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ParametriGenerazioneIUVEnteType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ParametriGenerazioneIUVEnteType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AuxDigit" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpConfigurazione}AuxDigitType"/>
 *         &lt;element name="CodiceStazione" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max2Text"/>
 *         &lt;element name="CodiceSegregazione" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max2Text"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ParametriGenerazioneIUVEnteType", propOrder = {
    "auxDigit",
    "codiceStazione",
    "codiceSegregazione"
})
public class ParametriGenerazioneIUVEnteType {

    @XmlElement(name = "AuxDigit", required = true)
    protected String auxDigit;
    @XmlElement(name = "CodiceStazione", required = true)
    protected String codiceStazione;
    @XmlElement(name = "CodiceSegregazione", required = true)
    protected String codiceSegregazione;

    /**
     * Gets the value of the auxDigit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuxDigit() {
        return auxDigit;
    }

    /**
     * Sets the value of the auxDigit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuxDigit(String value) {
        this.auxDigit = value;
    }

    /**
     * Gets the value of the codiceStazione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceStazione() {
        return codiceStazione;
    }

    /**
     * Sets the value of the codiceStazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceStazione(String value) {
        this.codiceStazione = value;
    }

    /**
     * Gets the value of the codiceSegregazione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceSegregazione() {
        return codiceSegregazione;
    }

    /**
     * Sets the value of the codiceSegregazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceSegregazione(String value) {
        this.codiceSegregazione = value;
    }

}
