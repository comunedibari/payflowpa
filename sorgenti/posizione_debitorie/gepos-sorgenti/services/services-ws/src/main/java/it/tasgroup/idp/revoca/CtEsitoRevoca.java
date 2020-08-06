//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-833 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.12.17 at 04:55:28 PM CET 
//


package it.tasgroup.idp.revoca;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ctEsitoRevoca complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ctEsitoRevoca">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="versioneOggetto" type="{http://www.digitpa.gov.it/schemas/2011/Pagamenti/Revoche/}stText16"/>
 *         &lt;element name="dominio" type="{http://www.digitpa.gov.it/schemas/2011/Pagamenti/Revoche/}ctDominio"/>
 *         &lt;element name="identificativoMessaggioEsito" type="{http://www.digitpa.gov.it/schemas/2011/Pagamenti/Revoche/}stText35"/>
 *         &lt;element name="dataOraMessaggioEsito" type="{http://www.digitpa.gov.it/schemas/2011/Pagamenti/Revoche/}stISODateTime"/>
 *         &lt;element name="riferimentoMessaggioRevoca" type="{http://www.digitpa.gov.it/schemas/2011/Pagamenti/Revoche/}stText35"/>
 *         &lt;element name="riferimentoDataRevoca" type="{http://www.digitpa.gov.it/schemas/2011/Pagamenti/Revoche/}stISODate"/>
 *         &lt;element name="istitutoAttestante" type="{http://www.digitpa.gov.it/schemas/2011/Pagamenti/Revoche/}ctIstitutoAttestante"/>
 *         &lt;element name="soggettoVersante" type="{http://www.digitpa.gov.it/schemas/2011/Pagamenti/Revoche/}ctSoggettoVersante" minOccurs="0"/>
 *         &lt;element name="soggettoPagatore" type="{http://www.digitpa.gov.it/schemas/2011/Pagamenti/Revoche/}ctSoggettoPagatore"/>
 *         &lt;element name="datiRevoca" type="{http://www.digitpa.gov.it/schemas/2011/Pagamenti/Revoche/}ctDatiEsitoRevoca"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ctEsitoRevoca", propOrder = {
    "versioneOggetto",
    "dominio",
    "identificativoMessaggioEsito",
    "dataOraMessaggioEsito",
    "riferimentoMessaggioRevoca",
    "riferimentoDataRevoca",
    "istitutoAttestante",
    "soggettoVersante",
    "soggettoPagatore",
    "datiRevoca"
})
public class CtEsitoRevoca {

    @XmlElement(required = true)
    protected String versioneOggetto;
    @XmlElement(required = true)
    protected CtDominio dominio;
    @XmlElement(required = true)
    protected String identificativoMessaggioEsito;
    @XmlElement(required = true)
    protected XMLGregorianCalendar dataOraMessaggioEsito;
    @XmlElement(required = true)
    protected String riferimentoMessaggioRevoca;
    @XmlElement(required = true)
    protected XMLGregorianCalendar riferimentoDataRevoca;
    @XmlElement(required = true)
    protected CtIstitutoAttestante istitutoAttestante;
    protected CtSoggettoVersante soggettoVersante;
    @XmlElement(required = true)
    protected CtSoggettoPagatore soggettoPagatore;
    @XmlElement(required = true)
    protected CtDatiEsitoRevoca datiRevoca;

    /**
     * Gets the value of the versioneOggetto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersioneOggetto() {
        return versioneOggetto;
    }

    /**
     * Sets the value of the versioneOggetto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersioneOggetto(String value) {
        this.versioneOggetto = value;
    }

    /**
     * Gets the value of the dominio property.
     * 
     * @return
     *     possible object is
     *     {@link CtDominio }
     *     
     */
    public CtDominio getDominio() {
        return dominio;
    }

    /**
     * Sets the value of the dominio property.
     * 
     * @param value
     *     allowed object is
     *     {@link CtDominio }
     *     
     */
    public void setDominio(CtDominio value) {
        this.dominio = value;
    }

    /**
     * Gets the value of the identificativoMessaggioEsito property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativoMessaggioEsito() {
        return identificativoMessaggioEsito;
    }

    /**
     * Sets the value of the identificativoMessaggioEsito property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativoMessaggioEsito(String value) {
        this.identificativoMessaggioEsito = value;
    }

    /**
     * Gets the value of the dataOraMessaggioEsito property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataOraMessaggioEsito() {
        return dataOraMessaggioEsito;
    }

    /**
     * Sets the value of the dataOraMessaggioEsito property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataOraMessaggioEsito(XMLGregorianCalendar value) {
        this.dataOraMessaggioEsito = value;
    }

    /**
     * Gets the value of the riferimentoMessaggioRevoca property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRiferimentoMessaggioRevoca() {
        return riferimentoMessaggioRevoca;
    }

    /**
     * Sets the value of the riferimentoMessaggioRevoca property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRiferimentoMessaggioRevoca(String value) {
        this.riferimentoMessaggioRevoca = value;
    }

    /**
     * Gets the value of the riferimentoDataRevoca property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRiferimentoDataRevoca() {
        return riferimentoDataRevoca;
    }

    /**
     * Sets the value of the riferimentoDataRevoca property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setRiferimentoDataRevoca(XMLGregorianCalendar value) {
        this.riferimentoDataRevoca = value;
    }

    /**
     * Gets the value of the istitutoAttestante property.
     * 
     * @return
     *     possible object is
     *     {@link CtIstitutoAttestante }
     *     
     */
    public CtIstitutoAttestante getIstitutoAttestante() {
        return istitutoAttestante;
    }

    /**
     * Sets the value of the istitutoAttestante property.
     * 
     * @param value
     *     allowed object is
     *     {@link CtIstitutoAttestante }
     *     
     */
    public void setIstitutoAttestante(CtIstitutoAttestante value) {
        this.istitutoAttestante = value;
    }

    /**
     * Gets the value of the soggettoVersante property.
     * 
     * @return
     *     possible object is
     *     {@link CtSoggettoVersante }
     *     
     */
    public CtSoggettoVersante getSoggettoVersante() {
        return soggettoVersante;
    }

    /**
     * Sets the value of the soggettoVersante property.
     * 
     * @param value
     *     allowed object is
     *     {@link CtSoggettoVersante }
     *     
     */
    public void setSoggettoVersante(CtSoggettoVersante value) {
        this.soggettoVersante = value;
    }

    /**
     * Gets the value of the soggettoPagatore property.
     * 
     * @return
     *     possible object is
     *     {@link CtSoggettoPagatore }
     *     
     */
    public CtSoggettoPagatore getSoggettoPagatore() {
        return soggettoPagatore;
    }

    /**
     * Sets the value of the soggettoPagatore property.
     * 
     * @param value
     *     allowed object is
     *     {@link CtSoggettoPagatore }
     *     
     */
    public void setSoggettoPagatore(CtSoggettoPagatore value) {
        this.soggettoPagatore = value;
    }

    /**
     * Gets the value of the datiRevoca property.
     * 
     * @return
     *     possible object is
     *     {@link CtDatiEsitoRevoca }
     *     
     */
    public CtDatiEsitoRevoca getDatiRevoca() {
        return datiRevoca;
    }

    /**
     * Sets the value of the datiRevoca property.
     * 
     * @param value
     *     allowed object is
     *     {@link CtDatiEsitoRevoca }
     *     
     */
    public void setDatiRevoca(CtDatiEsitoRevoca value) {
        this.datiRevoca = value;
    }

}
