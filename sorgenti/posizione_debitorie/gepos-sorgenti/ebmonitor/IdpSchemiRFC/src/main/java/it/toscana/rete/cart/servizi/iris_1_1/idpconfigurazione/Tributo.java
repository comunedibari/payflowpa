
package it.toscana.rete.cart.servizi.iris_1_1.idpconfigurazione;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Tributo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Tributo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CodiceEnte" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max50Text"/>
 *         &lt;element name="CodiceTributo" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max50Text"/>
 *         &lt;element name="Descrizione" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max50Text"/>
 *         &lt;element name="Categoria" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max50Text"/>
 *         &lt;element name="CodiceSilEnte" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max35Text"/>
 *         &lt;element name="IBANAccreditoBancario" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}IBANIdentifier"/>
 *         &lt;element name="IBANAccreditoPostale" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}IBANIdentifier"/>
 *         &lt;element name="Stato" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpConfigurazione}Stato"/>
 *         &lt;element name="PagamentoPressoPSP" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="IuvGenerato" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Tributo", propOrder = {
    "codiceEnte",
    "codiceTributo",
    "descrizione",
    "categoria",
    "codiceSilEnte",
    "ibanAccreditoBancario",
    "ibanAccreditoPostale",
    "stato",
    "pagamentoPressoPSP",
    "iuvGenerato"
})
public class Tributo {

    @XmlElement(name = "CodiceEnte", required = true)
    protected String codiceEnte;
    @XmlElement(name = "CodiceTributo", required = true)
    protected String codiceTributo;
    @XmlElement(name = "Descrizione", required = true)
    protected String descrizione;
    @XmlElement(name = "Categoria", required = true)
    protected String categoria;
    @XmlElement(name = "CodiceSilEnte", required = true)
    protected String codiceSilEnte;
    @XmlElement(name = "IBANAccreditoBancario", required = true)
    protected String ibanAccreditoBancario;
    @XmlElement(name = "IBANAccreditoPostale", required = true)
    protected String ibanAccreditoPostale;
    @XmlElement(name = "Stato", required = true)
    protected Stato stato;
    @XmlElement(name = "PagamentoPressoPSP")
    protected boolean pagamentoPressoPSP;
    @XmlElement(name = "IuvGenerato")
    protected boolean iuvGenerato;

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
     * Gets the value of the codiceTributo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceTributo() {
        return codiceTributo;
    }

    /**
     * Sets the value of the codiceTributo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceTributo(String value) {
        this.codiceTributo = value;
    }

    /**
     * Gets the value of the descrizione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Sets the value of the descrizione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizione(String value) {
        this.descrizione = value;
    }

    /**
     * Gets the value of the categoria property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * Sets the value of the categoria property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCategoria(String value) {
        this.categoria = value;
    }

    /**
     * Gets the value of the codiceSilEnte property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceSilEnte() {
        return codiceSilEnte;
    }

    /**
     * Sets the value of the codiceSilEnte property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceSilEnte(String value) {
        this.codiceSilEnte = value;
    }

    /**
     * Gets the value of the ibanAccreditoBancario property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIBANAccreditoBancario() {
        return ibanAccreditoBancario;
    }

    /**
     * Sets the value of the ibanAccreditoBancario property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIBANAccreditoBancario(String value) {
        this.ibanAccreditoBancario = value;
    }

    /**
     * Gets the value of the ibanAccreditoPostale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIBANAccreditoPostale() {
        return ibanAccreditoPostale;
    }

    /**
     * Sets the value of the ibanAccreditoPostale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIBANAccreditoPostale(String value) {
        this.ibanAccreditoPostale = value;
    }

    /**
     * Gets the value of the stato property.
     * 
     * @return
     *     possible object is
     *     {@link Stato }
     *     
     */
    public Stato getStato() {
        return stato;
    }

    /**
     * Sets the value of the stato property.
     * 
     * @param value
     *     allowed object is
     *     {@link Stato }
     *     
     */
    public void setStato(Stato value) {
        this.stato = value;
    }

    /**
     * Gets the value of the pagamentoPressoPSP property.
     * 
     */
    public boolean isPagamentoPressoPSP() {
        return pagamentoPressoPSP;
    }

    /**
     * Sets the value of the pagamentoPressoPSP property.
     * 
     */
    public void setPagamentoPressoPSP(boolean value) {
        this.pagamentoPressoPSP = value;
    }

    /**
     * Gets the value of the iuvGenerato property.
     * 
     */
    public boolean isIuvGenerato() {
        return iuvGenerato;
    }

    /**
     * Sets the value of the iuvGenerato property.
     * 
     */
    public void setIuvGenerato(boolean value) {
        this.iuvGenerato = value;
    }

}
