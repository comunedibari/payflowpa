
package it.gov.digitpa.schemas._2011.pagamenti;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ctSoggettoVersante complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ctSoggettoVersante">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="identificativoUnivocoVersante" type="{http://www.digitpa.gov.it/schemas/2011/Pagamenti/}ctIdentificativoUnivocoPersonaFG"/>
 *         &lt;element name="anagraficaVersante" type="{http://www.digitpa.gov.it/schemas/2011/Pagamenti/}stText70"/>
 *         &lt;element name="indirizzoVersante" type="{http://www.digitpa.gov.it/schemas/2011/Pagamenti/}stText70" minOccurs="0"/>
 *         &lt;element name="civicoVersante" type="{http://www.digitpa.gov.it/schemas/2011/Pagamenti/}stText16" minOccurs="0"/>
 *         &lt;element name="capVersante" type="{http://www.digitpa.gov.it/schemas/2011/Pagamenti/}stText16" minOccurs="0"/>
 *         &lt;element name="localitaVersante" type="{http://www.digitpa.gov.it/schemas/2011/Pagamenti/}stText35" minOccurs="0"/>
 *         &lt;element name="provinciaVersante" type="{http://www.digitpa.gov.it/schemas/2011/Pagamenti/}stText35" minOccurs="0"/>
 *         &lt;element name="nazioneVersante" type="{http://www.digitpa.gov.it/schemas/2011/Pagamenti/}stNazioneProvincia" minOccurs="0"/>
 *         &lt;element name="e-mailVersante" type="{http://www.digitpa.gov.it/schemas/2011/Pagamenti/}stEMail" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ctSoggettoVersante", propOrder = {
    "identificativoUnivocoVersante",
    "anagraficaVersante",
    "indirizzoVersante",
    "civicoVersante",
    "capVersante",
    "localitaVersante",
    "provinciaVersante",
    "nazioneVersante",
    "eMailVersante"
})
public class CtSoggettoVersante {

    @XmlElement(required = true)
    protected CtIdentificativoUnivocoPersonaFG identificativoUnivocoVersante;
    @XmlElement(required = true)
    protected String anagraficaVersante;
    protected String indirizzoVersante;
    protected String civicoVersante;
    protected String capVersante;
    protected String localitaVersante;
    protected String provinciaVersante;
    protected String nazioneVersante;
    @XmlElement(name = "e-mailVersante")
    protected String eMailVersante;

    /**
     * Gets the value of the identificativoUnivocoVersante property.
     * 
     * @return
     *     possible object is
     *     {@link CtIdentificativoUnivocoPersonaFG }
     *     
     */
    public CtIdentificativoUnivocoPersonaFG getIdentificativoUnivocoVersante() {
        return identificativoUnivocoVersante;
    }

    /**
     * Sets the value of the identificativoUnivocoVersante property.
     * 
     * @param value
     *     allowed object is
     *     {@link CtIdentificativoUnivocoPersonaFG }
     *     
     */
    public void setIdentificativoUnivocoVersante(CtIdentificativoUnivocoPersonaFG value) {
        this.identificativoUnivocoVersante = value;
    }

    /**
     * Gets the value of the anagraficaVersante property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnagraficaVersante() {
        return anagraficaVersante;
    }

    /**
     * Sets the value of the anagraficaVersante property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnagraficaVersante(String value) {
        this.anagraficaVersante = value;
    }

    /**
     * Gets the value of the indirizzoVersante property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndirizzoVersante() {
        return indirizzoVersante;
    }

    /**
     * Sets the value of the indirizzoVersante property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndirizzoVersante(String value) {
        this.indirizzoVersante = value;
    }

    /**
     * Gets the value of the civicoVersante property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCivicoVersante() {
        return civicoVersante;
    }

    /**
     * Sets the value of the civicoVersante property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCivicoVersante(String value) {
        this.civicoVersante = value;
    }

    /**
     * Gets the value of the capVersante property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCapVersante() {
        return capVersante;
    }

    /**
     * Sets the value of the capVersante property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCapVersante(String value) {
        this.capVersante = value;
    }

    /**
     * Gets the value of the localitaVersante property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocalitaVersante() {
        return localitaVersante;
    }

    /**
     * Sets the value of the localitaVersante property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocalitaVersante(String value) {
        this.localitaVersante = value;
    }

    /**
     * Gets the value of the provinciaVersante property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProvinciaVersante() {
        return provinciaVersante;
    }

    /**
     * Sets the value of the provinciaVersante property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProvinciaVersante(String value) {
        this.provinciaVersante = value;
    }

    /**
     * Gets the value of the nazioneVersante property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNazioneVersante() {
        return nazioneVersante;
    }

    /**
     * Sets the value of the nazioneVersante property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNazioneVersante(String value) {
        this.nazioneVersante = value;
    }

    /**
     * Gets the value of the eMailVersante property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEMailVersante() {
        return eMailVersante;
    }

    /**
     * Sets the value of the eMailVersante property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEMailVersante(String value) {
        this.eMailVersante = value;
    }

}
