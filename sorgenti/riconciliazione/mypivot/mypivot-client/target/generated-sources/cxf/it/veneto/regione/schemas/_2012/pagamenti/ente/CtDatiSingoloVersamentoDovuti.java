
package it.veneto.regione.schemas._2012.pagamenti.ente;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ctDatiSingoloVersamentoDovuti complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ctDatiSingoloVersamentoDovuti"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="identificativoUnivocoDovuto" type="{http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/}stText35"/&gt;
 *         &lt;element name="importoSingoloVersamento" type="{http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/}stImporto"/&gt;
 *         &lt;element name="commissioneCaricoPA" type="{http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/}stImporto" minOccurs="0"/&gt;
 *         &lt;element name="identificativoTipoDovuto" type="{http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/}stText35"/&gt;
 *         &lt;element name="causaleVersamento" type="{http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/}stText1024"/&gt;
 *         &lt;element name="datiSpecificiRiscossione" type="{http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/}stDatiSpecificiRiscossione"/&gt;
 *         &lt;element name="datiMarcaBolloDigitale" type="{http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/}ctDatiMarcaBolloDigitale" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ctDatiSingoloVersamentoDovuti", propOrder = {
    "identificativoUnivocoDovuto",
    "importoSingoloVersamento",
    "commissioneCaricoPA",
    "identificativoTipoDovuto",
    "causaleVersamento",
    "datiSpecificiRiscossione",
    "datiMarcaBolloDigitale"
})
public class CtDatiSingoloVersamentoDovuti {

    @XmlElement(required = true)
    protected String identificativoUnivocoDovuto;
    @XmlElement(required = true)
    protected BigDecimal importoSingoloVersamento;
    protected BigDecimal commissioneCaricoPA;
    @XmlElement(required = true)
    protected String identificativoTipoDovuto;
    @XmlElement(required = true)
    protected String causaleVersamento;
    @XmlElement(required = true)
    protected String datiSpecificiRiscossione;
    protected CtDatiMarcaBolloDigitale datiMarcaBolloDigitale;

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
     * Recupera il valore della proprietà importoSingoloVersamento.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImportoSingoloVersamento() {
        return importoSingoloVersamento;
    }

    /**
     * Imposta il valore della proprietà importoSingoloVersamento.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImportoSingoloVersamento(BigDecimal value) {
        this.importoSingoloVersamento = value;
    }

    /**
     * Recupera il valore della proprietà commissioneCaricoPA.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCommissioneCaricoPA() {
        return commissioneCaricoPA;
    }

    /**
     * Imposta il valore della proprietà commissioneCaricoPA.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCommissioneCaricoPA(BigDecimal value) {
        this.commissioneCaricoPA = value;
    }

    /**
     * Recupera il valore della proprietà identificativoTipoDovuto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativoTipoDovuto() {
        return identificativoTipoDovuto;
    }

    /**
     * Imposta il valore della proprietà identificativoTipoDovuto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativoTipoDovuto(String value) {
        this.identificativoTipoDovuto = value;
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
     * Recupera il valore della proprietà datiMarcaBolloDigitale.
     * 
     * @return
     *     possible object is
     *     {@link CtDatiMarcaBolloDigitale }
     *     
     */
    public CtDatiMarcaBolloDigitale getDatiMarcaBolloDigitale() {
        return datiMarcaBolloDigitale;
    }

    /**
     * Imposta il valore della proprietà datiMarcaBolloDigitale.
     * 
     * @param value
     *     allowed object is
     *     {@link CtDatiMarcaBolloDigitale }
     *     
     */
    public void setDatiMarcaBolloDigitale(CtDatiMarcaBolloDigitale value) {
        this.datiMarcaBolloDigitale = value;
    }

}
