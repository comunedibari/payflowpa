
package it.toscana.rete.cart.servizi.iris_1_1.idpheader;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import it.toscana.rete.cart.servizi.iris_1_1.idpinclude.StTipoSoggetto;


/**
 * <p>Java class for DATI_VERSANTE_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DATI_VERSANTE_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TIPO_SOGGETTO" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}stTipoSoggetto"/>
 *         &lt;element name="ID_FISCALE" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}IdentificativoIdp"/>
 *         &lt;element name="ANAGRAFICA" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max70Text"/>
 *         &lt;element name="E_MAIL" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max256Text" minOccurs="0"/>
 *         &lt;element name="INDIRIZZO" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max70Text" minOccurs="0"/>
 *         &lt;element name="NUMERO_CIVICO" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max16Text" minOccurs="0"/>
 *         &lt;element name="CAP" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max16Text" minOccurs="0"/>
 *         &lt;element name="LOCALITA" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max35Text" minOccurs="0"/>
 *         &lt;element name="PROVINCIA" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max35Text" minOccurs="0"/>
 *         &lt;element name="NAZIONE" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}stNazione" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DATI_VERSANTE_Type", propOrder = {
    "tiposoggetto",
    "idfiscale",
    "anagrafica",
    "email",
    "indirizzo",
    "numerocivico",
    "cap",
    "localita",
    "provincia",
    "nazione"
})
public class DATIVERSANTEType {

    @XmlElement(name = "TIPO_SOGGETTO", required = true)
    protected StTipoSoggetto tiposoggetto;
    @XmlElement(name = "ID_FISCALE", required = true)
    protected String idfiscale;
    @XmlElement(name = "ANAGRAFICA", required = true)
    protected String anagrafica;
    @XmlElement(name = "E_MAIL")
    protected String email;
    @XmlElement(name = "INDIRIZZO")
    protected String indirizzo;
    @XmlElement(name = "NUMERO_CIVICO")
    protected String numerocivico;
    @XmlElement(name = "CAP")
    protected String cap;
    @XmlElement(name = "LOCALITA")
    protected String localita;
    @XmlElement(name = "PROVINCIA")
    protected String provincia;
    @XmlElement(name = "NAZIONE")
    protected String nazione;

    /**
     * Gets the value of the tiposoggetto property.
     * 
     * @return
     *     possible object is
     *     {@link StTipoSoggetto }
     *     
     */
    public StTipoSoggetto getTIPOSOGGETTO() {
        return tiposoggetto;
    }

    /**
     * Sets the value of the tiposoggetto property.
     * 
     * @param value
     *     allowed object is
     *     {@link StTipoSoggetto }
     *     
     */
    public void setTIPOSOGGETTO(StTipoSoggetto value) {
        this.tiposoggetto = value;
    }

    /**
     * Gets the value of the idfiscale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDFISCALE() {
        return idfiscale;
    }

    /**
     * Sets the value of the idfiscale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDFISCALE(String value) {
        this.idfiscale = value;
    }

    /**
     * Gets the value of the anagrafica property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getANAGRAFICA() {
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
    public void setANAGRAFICA(String value) {
        this.anagrafica = value;
    }

    /**
     * Gets the value of the email property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEMAIL() {
        return email;
    }

    /**
     * Sets the value of the email property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEMAIL(String value) {
        this.email = value;
    }

    /**
     * Gets the value of the indirizzo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getINDIRIZZO() {
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
    public void setINDIRIZZO(String value) {
        this.indirizzo = value;
    }

    /**
     * Gets the value of the numerocivico property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNUMEROCIVICO() {
        return numerocivico;
    }

    /**
     * Sets the value of the numerocivico property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNUMEROCIVICO(String value) {
        this.numerocivico = value;
    }

    /**
     * Gets the value of the cap property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCAP() {
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
    public void setCAP(String value) {
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
    public String getLOCALITA() {
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
    public void setLOCALITA(String value) {
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
    public String getPROVINCIA() {
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
    public void setPROVINCIA(String value) {
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
    public String getNAZIONE() {
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
    public void setNAZIONE(String value) {
        this.nazione = value;
    }

}
