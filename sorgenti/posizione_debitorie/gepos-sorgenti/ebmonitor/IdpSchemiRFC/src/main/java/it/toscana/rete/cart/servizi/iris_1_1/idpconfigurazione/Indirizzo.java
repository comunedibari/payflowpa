
package it.toscana.rete.cart.servizi.iris_1_1.idpconfigurazione;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Indirizzo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Indirizzo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Indirizzo" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max100Text" minOccurs="0"/>
 *         &lt;element name="Comune" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max35Text" minOccurs="0"/>
 *         &lt;element name="Provincia" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max2Text" minOccurs="0"/>
 *         &lt;element name="CAP" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max5Text" minOccurs="0"/>
 *         &lt;element name="Nazione" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max20Text" minOccurs="0"/>
 *         &lt;element name="NumeroTelefono" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max20Text" minOccurs="0"/>
 *         &lt;element name="NumeroFAX" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max20Text" minOccurs="0"/>
 *         &lt;element name="CasellaPostale" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max5Text" minOccurs="0"/>
 *         &lt;element name="Email" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max256Text" minOccurs="0"/>
 *         &lt;element name="EmailPec" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max256Text" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Indirizzo", propOrder = {
    "indirizzo",
    "comune",
    "provincia",
    "cap",
    "nazione",
    "numeroTelefono",
    "numeroFAX",
    "casellaPostale",
    "email",
    "emailPec"
})
public class Indirizzo {

    @XmlElement(name = "Indirizzo")
    protected String indirizzo;
    @XmlElement(name = "Comune")
    protected String comune;
    @XmlElement(name = "Provincia")
    protected String provincia;
    @XmlElement(name = "CAP")
    protected String cap;
    @XmlElement(name = "Nazione")
    protected String nazione;
    @XmlElement(name = "NumeroTelefono")
    protected String numeroTelefono;
    @XmlElement(name = "NumeroFAX")
    protected String numeroFAX;
    @XmlElement(name = "CasellaPostale")
    protected String casellaPostale;
    @XmlElement(name = "Email")
    protected String email;
    @XmlElement(name = "EmailPec")
    protected String emailPec;

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
     * Gets the value of the comune property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComune() {
        return comune;
    }

    /**
     * Sets the value of the comune property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComune(String value) {
        this.comune = value;
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

    /**
     * Gets the value of the numeroTelefono property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    /**
     * Sets the value of the numeroTelefono property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroTelefono(String value) {
        this.numeroTelefono = value;
    }

    /**
     * Gets the value of the numeroFAX property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroFAX() {
        return numeroFAX;
    }

    /**
     * Sets the value of the numeroFAX property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroFAX(String value) {
        this.numeroFAX = value;
    }

    /**
     * Gets the value of the casellaPostale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCasellaPostale() {
        return casellaPostale;
    }

    /**
     * Sets the value of the casellaPostale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCasellaPostale(String value) {
        this.casellaPostale = value;
    }

    /**
     * Gets the value of the email property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail() {
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
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * Gets the value of the emailPec property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmailPec() {
        return emailPec;
    }

    /**
     * Sets the value of the emailPec property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmailPec(String value) {
        this.emailPec = value;
    }

}
