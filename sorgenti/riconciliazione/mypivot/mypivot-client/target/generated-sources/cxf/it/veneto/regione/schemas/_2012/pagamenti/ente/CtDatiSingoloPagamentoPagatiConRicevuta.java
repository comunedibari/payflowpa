
package it.veneto.regione.schemas._2012.pagamenti.ente;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per ctDatiSingoloPagamentoPagatiConRicevuta complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ctDatiSingoloPagamentoPagatiConRicevuta"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="identificativoUnivocoDovuto" type="{http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/}stText35"/&gt;
 *         &lt;element name="singoloImportoPagato" type="{http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/}stImporto"/&gt;
 *         &lt;element name="esitoSingoloPagamento" type="{http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/}stText35" minOccurs="0"/&gt;
 *         &lt;element name="dataEsitoSingoloPagamento" type="{http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/}stISODate"/&gt;
 *         &lt;element name="identificativoUnivocoRiscossione" type="{http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/}stText35"/&gt;
 *         &lt;element name="causaleVersamento" type="{http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/}stText140"/&gt;
 *         &lt;element name="datiSpecificiRiscossione" type="{http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/}stDatiSpecificiRiscossione"/&gt;
 *         &lt;element name="indiceDatiSingoloPagamento" type="{http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/}stIndice" minOccurs="0"/&gt;
 *         &lt;element name="commissioniApplicatePSP" type="{http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/}stImporto" minOccurs="0"/&gt;
 *         &lt;element name="allegatoRicevuta" type="{http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/}ctAllegatoRicevuta" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ctDatiSingoloPagamentoPagatiConRicevuta", propOrder = {
    "identificativoUnivocoDovuto",
    "singoloImportoPagato",
    "esitoSingoloPagamento",
    "dataEsitoSingoloPagamento",
    "identificativoUnivocoRiscossione",
    "causaleVersamento",
    "datiSpecificiRiscossione",
    "indiceDatiSingoloPagamento",
    "commissioniApplicatePSP",
    "allegatoRicevuta"
})
public class CtDatiSingoloPagamentoPagatiConRicevuta {

    @XmlElement(required = true)
    protected String identificativoUnivocoDovuto;
    @XmlElement(required = true)
    protected BigDecimal singoloImportoPagato;
    protected String esitoSingoloPagamento;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataEsitoSingoloPagamento;
    @XmlElement(required = true)
    protected String identificativoUnivocoRiscossione;
    @XmlElement(required = true)
    protected String causaleVersamento;
    @XmlElement(required = true)
    protected String datiSpecificiRiscossione;
    @XmlSchemaType(name = "integer")
    protected Integer indiceDatiSingoloPagamento;
    protected BigDecimal commissioniApplicatePSP;
    protected CtAllegatoRicevuta allegatoRicevuta;

    /**
     * Recupera il valore della proprietà identificativoUnivocoDovuto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativoUnivocoDovuto() {
        return identificativoUnivocoDovuto;
    }

    /**
     * Imposta il valore della proprietà identificativoUnivocoDovuto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativoUnivocoDovuto(String value) {
        this.identificativoUnivocoDovuto = value;
    }

    /**
     * Recupera il valore della proprietà singoloImportoPagato.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSingoloImportoPagato() {
        return singoloImportoPagato;
    }

    /**
     * Imposta il valore della proprietà singoloImportoPagato.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSingoloImportoPagato(BigDecimal value) {
        this.singoloImportoPagato = value;
    }

    /**
     * Recupera il valore della proprietà esitoSingoloPagamento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEsitoSingoloPagamento() {
        return esitoSingoloPagamento;
    }

    /**
     * Imposta il valore della proprietà esitoSingoloPagamento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEsitoSingoloPagamento(String value) {
        this.esitoSingoloPagamento = value;
    }

    /**
     * Recupera il valore della proprietà dataEsitoSingoloPagamento.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataEsitoSingoloPagamento() {
        return dataEsitoSingoloPagamento;
    }

    /**
     * Imposta il valore della proprietà dataEsitoSingoloPagamento.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataEsitoSingoloPagamento(XMLGregorianCalendar value) {
        this.dataEsitoSingoloPagamento = value;
    }

    /**
     * Recupera il valore della proprietà identificativoUnivocoRiscossione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativoUnivocoRiscossione() {
        return identificativoUnivocoRiscossione;
    }

    /**
     * Imposta il valore della proprietà identificativoUnivocoRiscossione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativoUnivocoRiscossione(String value) {
        this.identificativoUnivocoRiscossione = value;
    }

    /**
     * Recupera il valore della proprietà causaleVersamento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCausaleVersamento() {
        return causaleVersamento;
    }

    /**
     * Imposta il valore della proprietà causaleVersamento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCausaleVersamento(String value) {
        this.causaleVersamento = value;
    }

    /**
     * Recupera il valore della proprietà datiSpecificiRiscossione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatiSpecificiRiscossione() {
        return datiSpecificiRiscossione;
    }

    /**
     * Imposta il valore della proprietà datiSpecificiRiscossione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatiSpecificiRiscossione(String value) {
        this.datiSpecificiRiscossione = value;
    }

    /**
     * Recupera il valore della proprietà indiceDatiSingoloPagamento.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIndiceDatiSingoloPagamento() {
        return indiceDatiSingoloPagamento;
    }

    /**
     * Imposta il valore della proprietà indiceDatiSingoloPagamento.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIndiceDatiSingoloPagamento(Integer value) {
        this.indiceDatiSingoloPagamento = value;
    }

    /**
     * Recupera il valore della proprietà commissioniApplicatePSP.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCommissioniApplicatePSP() {
        return commissioniApplicatePSP;
    }

    /**
     * Imposta il valore della proprietà commissioniApplicatePSP.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCommissioniApplicatePSP(BigDecimal value) {
        this.commissioniApplicatePSP = value;
    }

    /**
     * Recupera il valore della proprietà allegatoRicevuta.
     * 
     * @return
     *     possible object is
     *     {@link CtAllegatoRicevuta }
     *     
     */
    public CtAllegatoRicevuta getAllegatoRicevuta() {
        return allegatoRicevuta;
    }

    /**
     * Imposta il valore della proprietà allegatoRicevuta.
     * 
     * @param value
     *     allowed object is
     *     {@link CtAllegatoRicevuta }
     *     
     */
    public void setAllegatoRicevuta(CtAllegatoRicevuta value) {
        this.allegatoRicevuta = value;
    }

}
