
package it.gov.digitpa.schemas._2011.pagamenti;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ctIstitutoAttestante complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ctIstitutoAttestante">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="identificativoUnivocoAttestante" type="{http://www.digitpa.gov.it/schemas/2011/Pagamenti/}ctIdentificativoUnivoco"/>
 *         &lt;element name="denominazioneAttestante" type="{http://www.digitpa.gov.it/schemas/2011/Pagamenti/}stText70"/>
 *         &lt;element name="codiceUnitOperAttestante" type="{http://www.digitpa.gov.it/schemas/2011/Pagamenti/}stText35" minOccurs="0"/>
 *         &lt;element name="denomUnitOperAttestante" type="{http://www.digitpa.gov.it/schemas/2011/Pagamenti/}stText70" minOccurs="0"/>
 *         &lt;element name="indirizzoAttestante" type="{http://www.digitpa.gov.it/schemas/2011/Pagamenti/}stText70" minOccurs="0"/>
 *         &lt;element name="civicoAttestante" type="{http://www.digitpa.gov.it/schemas/2011/Pagamenti/}stText16" minOccurs="0"/>
 *         &lt;element name="capAttestante" type="{http://www.digitpa.gov.it/schemas/2011/Pagamenti/}stText16" minOccurs="0"/>
 *         &lt;element name="localitaAttestante" type="{http://www.digitpa.gov.it/schemas/2011/Pagamenti/}stText35" minOccurs="0"/>
 *         &lt;element name="provinciaAttestante" type="{http://www.digitpa.gov.it/schemas/2011/Pagamenti/}stText35" minOccurs="0"/>
 *         &lt;element name="nazioneAttestante" type="{http://www.digitpa.gov.it/schemas/2011/Pagamenti/}stNazioneProvincia" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ctIstitutoAttestante", propOrder = {
    "identificativoUnivocoAttestante",
    "denominazioneAttestante",
    "codiceUnitOperAttestante",
    "denomUnitOperAttestante",
    "indirizzoAttestante",
    "civicoAttestante",
    "capAttestante",
    "localitaAttestante",
    "provinciaAttestante",
    "nazioneAttestante"
})
public class CtIstitutoAttestante {

    @XmlElement(required = true)
    protected CtIdentificativoUnivoco identificativoUnivocoAttestante;
    @XmlElement(required = true)
    protected String denominazioneAttestante;
    protected String codiceUnitOperAttestante;
    protected String denomUnitOperAttestante;
    protected String indirizzoAttestante;
    protected String civicoAttestante;
    protected String capAttestante;
    protected String localitaAttestante;
    protected String provinciaAttestante;
    protected String nazioneAttestante;

    /**
     * Gets the value of the identificativoUnivocoAttestante property.
     * 
     * @return
     *     possible object is
     *     {@link CtIdentificativoUnivoco }
     *     
     */
    public CtIdentificativoUnivoco getIdentificativoUnivocoAttestante() {
        return identificativoUnivocoAttestante;
    }

    /**
     * Sets the value of the identificativoUnivocoAttestante property.
     * 
     * @param value
     *     allowed object is
     *     {@link CtIdentificativoUnivoco }
     *     
     */
    public void setIdentificativoUnivocoAttestante(CtIdentificativoUnivoco value) {
        this.identificativoUnivocoAttestante = value;
    }

    /**
     * Gets the value of the denominazioneAttestante property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDenominazioneAttestante() {
        return denominazioneAttestante;
    }

    /**
     * Sets the value of the denominazioneAttestante property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDenominazioneAttestante(String value) {
        this.denominazioneAttestante = value;
    }

    /**
     * Gets the value of the codiceUnitOperAttestante property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceUnitOperAttestante() {
        return codiceUnitOperAttestante;
    }

    /**
     * Sets the value of the codiceUnitOperAttestante property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceUnitOperAttestante(String value) {
        this.codiceUnitOperAttestante = value;
    }

    /**
     * Gets the value of the denomUnitOperAttestante property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDenomUnitOperAttestante() {
        return denomUnitOperAttestante;
    }

    /**
     * Sets the value of the denomUnitOperAttestante property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDenomUnitOperAttestante(String value) {
        this.denomUnitOperAttestante = value;
    }

    /**
     * Gets the value of the indirizzoAttestante property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndirizzoAttestante() {
        return indirizzoAttestante;
    }

    /**
     * Sets the value of the indirizzoAttestante property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndirizzoAttestante(String value) {
        this.indirizzoAttestante = value;
    }

    /**
     * Gets the value of the civicoAttestante property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCivicoAttestante() {
        return civicoAttestante;
    }

    /**
     * Sets the value of the civicoAttestante property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCivicoAttestante(String value) {
        this.civicoAttestante = value;
    }

    /**
     * Gets the value of the capAttestante property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCapAttestante() {
        return capAttestante;
    }

    /**
     * Sets the value of the capAttestante property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCapAttestante(String value) {
        this.capAttestante = value;
    }

    /**
     * Gets the value of the localitaAttestante property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocalitaAttestante() {
        return localitaAttestante;
    }

    /**
     * Sets the value of the localitaAttestante property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocalitaAttestante(String value) {
        this.localitaAttestante = value;
    }

    /**
     * Gets the value of the provinciaAttestante property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProvinciaAttestante() {
        return provinciaAttestante;
    }

    /**
     * Sets the value of the provinciaAttestante property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProvinciaAttestante(String value) {
        this.provinciaAttestante = value;
    }

    /**
     * Gets the value of the nazioneAttestante property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNazioneAttestante() {
        return nazioneAttestante;
    }

    /**
     * Sets the value of the nazioneAttestante property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNazioneAttestante(String value) {
        this.nazioneAttestante = value;
    }

}
