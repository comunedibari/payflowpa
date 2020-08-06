
package gov.telematici.pagamenti.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for paaAttivaRPT complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="paaAttivaRPT">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="identificativoPSP" type="{http://ws.pagamenti.telematici.gov/}stText35"/>
 *         &lt;element name="datiPagamentoPSP" type="{http://ws.pagamenti.telematici.gov/}paaTipoDatiPagamentoPSP"/>
 *         &lt;element name="identificativoIntermediarioPSP" type="{http://ws.pagamenti.telematici.gov/}stText35" minOccurs="0"/>
 *         &lt;element name="identificativoCanalePSP" type="{http://ws.pagamenti.telematici.gov/}stText35" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "paaAttivaRPT", propOrder = {
    "identificativoPSP",
    "datiPagamentoPSP",
    "identificativoIntermediarioPSP",
    "identificativoCanalePSP"
})
public class PaaAttivaRPT {

    @XmlElement(required = true)
    protected String identificativoPSP;
    @XmlElement(required = true)
    protected PaaTipoDatiPagamentoPSP datiPagamentoPSP;
    protected String identificativoIntermediarioPSP;
    protected String identificativoCanalePSP;

    /**
     * Gets the value of the identificativoPSP property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativoPSP() {
        return identificativoPSP;
    }

    /**
     * Sets the value of the identificativoPSP property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativoPSP(String value) {
        this.identificativoPSP = value;
    }

    /**
     * Gets the value of the datiPagamentoPSP property.
     * 
     * @return
     *     possible object is
     *     {@link PaaTipoDatiPagamentoPSP }
     *     
     */
    public PaaTipoDatiPagamentoPSP getDatiPagamentoPSP() {
        return datiPagamentoPSP;
    }

    /**
     * Sets the value of the datiPagamentoPSP property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaaTipoDatiPagamentoPSP }
     *     
     */
    public void setDatiPagamentoPSP(PaaTipoDatiPagamentoPSP value) {
        this.datiPagamentoPSP = value;
    }

    /**
     * Gets the value of the identificativoIntermediarioPSP property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativoIntermediarioPSP() {
        return identificativoIntermediarioPSP;
    }

    /**
     * Sets the value of the identificativoIntermediarioPSP property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativoIntermediarioPSP(String value) {
        this.identificativoIntermediarioPSP = value;
    }

    /**
     * Gets the value of the identificativoCanalePSP property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativoCanalePSP() {
        return identificativoCanalePSP;
    }

    /**
     * Sets the value of the identificativoCanalePSP property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativoCanalePSP(String value) {
        this.identificativoCanalePSP = value;
    }

}
