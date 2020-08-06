
package it.veneto.regione.pagamenti.pivot.ente;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import it.veneto.regione.schemas._2012.pagamenti.CtDatiSingoloPagamentoEsito;


/**
 * <p>Classe Java per ctDatiVersamentoExport complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ctDatiVersamentoExport"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="codiceEsitoPagamento" type="{http://www.regione.veneto.it/schemas/2012/Pagamenti/}stCodiceEsitoPagamento"/&gt;
 *         &lt;element name="importoTotalePagato" type="{http://www.regione.veneto.it/schemas/2012/Pagamenti/}stImporto"/&gt;
 *         &lt;element name="identificativoUnivocoVersamento" type="{http://www.regione.veneto.it/schemas/2012/Pagamenti/}stText35"/&gt;
 *         &lt;element name="CodiceContestoPagamento" type="{http://www.regione.veneto.it/schemas/2012/Pagamenti/}stText35"/&gt;
 *         &lt;element name="datiSingoloPagamento" type="{http://www.regione.veneto.it/schemas/2012/Pagamenti/}ctDatiSingoloPagamentoEsito" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ctDatiVersamentoExport", propOrder = {
    "codiceEsitoPagamento",
    "importoTotalePagato",
    "identificativoUnivocoVersamento",
    "codiceContestoPagamento",
    "datiSingoloPagamento"
})
public class CtDatiVersamentoExport {

    @XmlElement(required = true)
    protected String codiceEsitoPagamento;
    @XmlElement(required = true)
    protected BigDecimal importoTotalePagato;
    @XmlElement(required = true)
    protected String identificativoUnivocoVersamento;
    @XmlElement(name = "CodiceContestoPagamento", required = true)
    protected String codiceContestoPagamento;
    protected CtDatiSingoloPagamentoEsito datiSingoloPagamento;

    /**
     * Recupera il valore della proprietà codiceEsitoPagamento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceEsitoPagamento() {
        return codiceEsitoPagamento;
    }

    /**
     * Imposta il valore della proprietà codiceEsitoPagamento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceEsitoPagamento(String value) {
        this.codiceEsitoPagamento = value;
    }

    /**
     * Recupera il valore della proprietà importoTotalePagato.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImportoTotalePagato() {
        return importoTotalePagato;
    }

    /**
     * Imposta il valore della proprietà importoTotalePagato.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImportoTotalePagato(BigDecimal value) {
        this.importoTotalePagato = value;
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
     * Recupera il valore della proprietà codiceContestoPagamento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceContestoPagamento() {
        return codiceContestoPagamento;
    }

    /**
     * Imposta il valore della proprietà codiceContestoPagamento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceContestoPagamento(String value) {
        this.codiceContestoPagamento = value;
    }

    /**
     * Recupera il valore della proprietà datiSingoloPagamento.
     * 
     * @return
     *     possible object is
     *     {@link CtDatiSingoloPagamentoEsito }
     *     
     */
    public CtDatiSingoloPagamentoEsito getDatiSingoloPagamento() {
        return datiSingoloPagamento;
    }

    /**
     * Imposta il valore della proprietà datiSingoloPagamento.
     * 
     * @param value
     *     allowed object is
     *     {@link CtDatiSingoloPagamentoEsito }
     *     
     */
    public void setDatiSingoloPagamento(CtDatiSingoloPagamentoEsito value) {
        this.datiSingoloPagamento = value;
    }

}
