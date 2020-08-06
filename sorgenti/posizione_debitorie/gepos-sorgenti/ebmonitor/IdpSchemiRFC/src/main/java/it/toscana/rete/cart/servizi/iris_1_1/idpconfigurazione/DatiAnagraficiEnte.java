
package it.toscana.rete.cart.servizi.iris_1_1.idpconfigurazione;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DatiAnagraficiEnte complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DatiAnagraficiEnte">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CodiceEnte" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max50Text"/>
 *         &lt;element name="TipoEnte" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max50Text"/>
 *         &lt;element name="RagioneSociale" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max100Text"/>
 *         &lt;element name="CodiceFiscale" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}IdentificativoIdp"/>
 *         &lt;element name="PartitaIVA" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}IdentificativoIdp" minOccurs="0"/>
 *         &lt;element name="Indirizzo" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpConfigurazione}Indirizzo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatiAnagraficiEnte", propOrder = {
    "codiceEnte",
    "tipoEnte",
    "ragioneSociale",
    "codiceFiscale",
    "partitaIVA",
    "indirizzo"
})
public class DatiAnagraficiEnte {

    @XmlElement(name = "CodiceEnte", required = true)
    protected String codiceEnte;
    @XmlElement(name = "TipoEnte", required = true)
    protected String tipoEnte;
    @XmlElement(name = "RagioneSociale", required = true)
    protected String ragioneSociale;
    @XmlElement(name = "CodiceFiscale", required = true)
    protected String codiceFiscale;
    @XmlElement(name = "PartitaIVA")
    protected String partitaIVA;
    @XmlElement(name = "Indirizzo")
    protected Indirizzo indirizzo;

    /**
     * Gets the value of the codiceEnte property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceEnte() {
        return codiceEnte;
    }

    /**
     * Sets the value of the codiceEnte property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceEnte(String value) {
        this.codiceEnte = value;
    }

    /**
     * Gets the value of the tipoEnte property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoEnte() {
        return tipoEnte;
    }

    /**
     * Sets the value of the tipoEnte property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoEnte(String value) {
        this.tipoEnte = value;
    }

    /**
     * Gets the value of the ragioneSociale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRagioneSociale() {
        return ragioneSociale;
    }

    /**
     * Sets the value of the ragioneSociale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRagioneSociale(String value) {
        this.ragioneSociale = value;
    }

    /**
     * Gets the value of the codiceFiscale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    /**
     * Sets the value of the codiceFiscale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceFiscale(String value) {
        this.codiceFiscale = value;
    }

    /**
     * Gets the value of the partitaIVA property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartitaIVA() {
        return partitaIVA;
    }

    /**
     * Sets the value of the partitaIVA property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartitaIVA(String value) {
        this.partitaIVA = value;
    }

    /**
     * Gets the value of the indirizzo property.
     * 
     * @return
     *     possible object is
     *     {@link Indirizzo }
     *     
     */
    public Indirizzo getIndirizzo() {
        return indirizzo;
    }

    /**
     * Sets the value of the indirizzo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Indirizzo }
     *     
     */
    public void setIndirizzo(Indirizzo value) {
        this.indirizzo = value;
    }

}
