
package it.veneto.regione.schemas._2012.pagamenti.ente;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per ctDatiVersamento complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ctDatiVersamento"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="dataEsecuzionePagamento" type="{http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/}stISODate"/&gt;
 *         &lt;element name="tipoVersamento" type="{http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/}stText32" minOccurs="0"/&gt;
 *         &lt;element name="identificativoUnivocoVersamento" type="{http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/}stText35" minOccurs="0"/&gt;
 *         &lt;element name="identificativoUnivocoDovuto" type="{http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/}stText35" minOccurs="0"/&gt;
 *         &lt;element name="importoSingoloVersamento" type="{http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/}stImporto"/&gt;
 *         &lt;element name="commissioneCaricoPA" type="{http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/}stImporto" minOccurs="0"/&gt;
 *         &lt;element name="identificativoTipoDovuto" type="{http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/}stText35" minOccurs="0"/&gt;
 *         &lt;element name="causaleVersamento" type="{http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/}stText1024" minOccurs="0"/&gt;
 *         &lt;element name="datiSpecificiRiscossione" type="{http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/}stDatiSpecificiRiscossione" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ctDatiVersamento", propOrder = {
    "dataEsecuzionePagamento",
    "tipoVersamento",
    "identificativoUnivocoVersamento",
    "identificativoUnivocoDovuto",
    "importoSingoloVersamento",
    "commissioneCaricoPA",
    "identificativoTipoDovuto",
    "causaleVersamento",
    "datiSpecificiRiscossione"
})
public class CtDatiVersamento {

    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataEsecuzionePagamento;
    protected String tipoVersamento;
    protected String identificativoUnivocoVersamento;
    protected String identificativoUnivocoDovuto;
    @XmlElement(required = true)
    protected BigDecimal importoSingoloVersamento;
    protected BigDecimal commissioneCaricoPA;
    protected String identificativoTipoDovuto;
    protected String causaleVersamento;
    protected String datiSpecificiRiscossione;

    /**
     * Recupera il valore della proprietà dataEsecuzionePagamento.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataEsecuzionePagamento() {
        return dataEsecuzionePagamento;
    }

    /**
     * Imposta il valore della proprietà dataEsecuzionePagamento.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataEsecuzionePagamento(XMLGregorianCalendar value) {
        this.dataEsecuzionePagamento = value;
    }

    /**
     * Recupera il valore della proprietà tipoVersamento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoVersamento() {
        return tipoVersamento;
    }

    /**
     * Imposta il valore della proprietà tipoVersamento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoVersamento(String value) {
        this.tipoVersamento = value;
    }

    /**
     * Recupera il valore della proprietà identificativoUnivocoVersamento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativoUnivocoVersamento() {
        return identificativoUnivocoVersamento;
    }

    /**
     * Imposta il valore della proprietà identificativoUnivocoVersamento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativoUnivocoVersamento(String value) {
        this.identificativoUnivocoVersamento = value;
    }

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

}
