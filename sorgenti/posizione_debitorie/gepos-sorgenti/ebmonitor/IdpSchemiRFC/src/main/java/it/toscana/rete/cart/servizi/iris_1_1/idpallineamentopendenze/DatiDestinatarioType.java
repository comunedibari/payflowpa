
package it.toscana.rete.cart.servizi.iris_1_1.idpallineamentopendenze;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import it.toscana.rete.cart.servizi.iris_1_1.idpinclude.StTipoSoggetto;


/**
 * <p>Java class for DatiDestinatarioType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DatiDestinatarioType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TipoSoggetto" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}stTipoSoggetto"/>
 *         &lt;element name="Anagrafica" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max70Text"/>
 *         &lt;element name="EMail" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max256Text" minOccurs="0"/>
 *         &lt;element name="Indirizzo" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max70Text" minOccurs="0"/>
 *         &lt;element name="NumeroCivico" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max16Text" minOccurs="0"/>
 *         &lt;element name="Cap" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max16Text" minOccurs="0"/>
 *         &lt;element name="Localita" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max35Text" minOccurs="0"/>
 *         &lt;element name="Provincia" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max35Text" minOccurs="0"/>
 *         &lt;element name="Nazione" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}stNazione" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatiDestinatarioType", propOrder = {
    "tipoSoggetto",
    "anagrafica",
    "eMail",
    "indirizzo",
    "numeroCivico",
    "cap",
    "localita",
    "provincia",
    "nazione"
})
public class DatiDestinatarioType {

    @XmlElement(name = "TipoSoggetto", required = true)
    protected StTipoSoggetto tipoSoggetto;
    @XmlElement(name = "Anagrafica", required = true)
    protected String anagrafica;
    @XmlElement(name = "EMail")
    protected String eMail;
    @XmlElement(name = "Indirizzo")
    protected String indirizzo;
    @XmlElement(name = "NumeroCivico")
    protected String numeroCivico;
    @XmlElement(name = "Cap")
    protected String cap;
    @XmlElement(name = "Localita")
    protected String localita;
    @XmlElement(name = "Provincia")
    protected String provincia;
    @XmlElement(name = "Nazione")
    protected String nazione;

    /**
     * Gets the value of the tipoSoggetto property.
     * 
     * @return
     *     possible object is
     *     {@link StTipoSoggetto }
     *     
     */
    public StTipoSoggetto getTipoSoggetto() {
        return tipoSoggetto;
    }

    /**
     * Sets the value of the tipoSoggetto property.
     * 
     * @param value
     *     allowed object is
     *     {@link StTipoSoggetto }
     *     
     */
    public void setTipoSoggetto(StTipoSoggetto value) {
        this.tipoSoggetto = value;
    }

    /**
     * Gets the value of the anagrafica property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnagrafica() {
        return anagrafica;
    }

    /**
     * Sets the value of the anagrafica property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnagrafica(String value) {
        this.anagrafica = value;
    }

    /**
     * Gets the value of the eMail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEMail() {
        return eMail;
    }

    /**
     * Sets the value of the eMail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEMail(String value) {
        this.eMail = value;
    }

    /**
     * Gets the value of the indirizzo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndirizzo() {
        return indirizzo;
    }

    /**
     * Sets the value of the indirizzo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndirizzo(String value) {
        this.indirizzo = value;
    }

    /**
     * Gets the value of the numeroCivico property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroCivico() {
        return numeroCivico;
    }

    /**
     * Sets the value of the numeroCivico property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroCivico(String value) {
        this.numeroCivico = value;
    }

    /**
     * Gets the value of the cap property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCap() {
        return cap;
    }

    /**
     * Sets the value of the cap property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCap(String value) {
        this.cap = value;
    }

    /**
     * Gets the value of the localita property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocalita() {
        return localita;
    }

    /**
     * Sets the value of the localita property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocalita(String value) {
        this.localita = value;
    }

    /**
     * Gets the value of the provincia property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProvincia() {
        return provincia;
    }

    /**
     * Sets the value of the provincia property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProvincia(String value) {
        this.provincia = value;
    }

    /**
     * Gets the value of the nazione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNazione() {
        return nazione;
    }

    /**
     * Sets the value of the nazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNazione(String value) {
        this.nazione = value;
    }

}
