
package it.veneto.regione.pagamenti.pivot.ente;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import it.gov.digitpa.schemas._2011.pagamenti.CtDatiSingoliPagamenti;
import it.gov.digitpa.schemas._2011.pagamenti.CtIstitutoMittente;
import it.gov.digitpa.schemas._2011.pagamenti.CtIstitutoRicevente;


/**
 * <p>Classe Java per ctFlussoRiversamento complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ctFlussoRiversamento"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="versioneOggetto" type="{http://www.digitpa.gov.it/schemas/2011/Pagamenti/}stVersioneOggetto"/&gt;
 *         &lt;element name="identificativoFlusso" type="{http://www.digitpa.gov.it/schemas/2011/Pagamenti/}stText35"/&gt;
 *         &lt;element name="dataOraFlusso" type="{http://www.digitpa.gov.it/schemas/2011/Pagamenti/}stISODateTime"/&gt;
 *         &lt;element name="identificativoUnivocoRegolamento" type="{http://www.digitpa.gov.it/schemas/2011/Pagamenti/}stText35"/&gt;
 *         &lt;element name="dataRegolamento" type="{http://www.digitpa.gov.it/schemas/2011/Pagamenti/}stISODate"/&gt;
 *         &lt;element name="istitutoMittente" type="{http://www.digitpa.gov.it/schemas/2011/Pagamenti/}ctIstitutoMittente"/&gt;
 *         &lt;element name="istitutoRicevente" type="{http://www.digitpa.gov.it/schemas/2011/Pagamenti/}ctIstitutoRicevente"/&gt;
 *         &lt;element name="numeroTotalePagamenti" type="{http://www.digitpa.gov.it/schemas/2011/Pagamenti/}stNumeroTotalePagamenti"/&gt;
 *         &lt;element name="importoTotalePagamenti" type="{http://www.digitpa.gov.it/schemas/2011/Pagamenti/}stImportoTotalePagamenti"/&gt;
 *         &lt;element name="datiSingoliPagamenti" type="{http://www.digitpa.gov.it/schemas/2011/Pagamenti/}ctDatiSingoliPagamenti"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ctFlussoRiversamento", propOrder = {
    "versioneOggetto",
    "identificativoFlusso",
    "dataOraFlusso",
    "identificativoUnivocoRegolamento",
    "dataRegolamento",
    "istitutoMittente",
    "istitutoRicevente",
    "numeroTotalePagamenti",
    "importoTotalePagamenti",
    "datiSingoliPagamenti"
})
public class CtFlussoRiversamento {

    @XmlElement(required = true)
    protected String versioneOggetto;
    @XmlElement(required = true)
    protected String identificativoFlusso;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataOraFlusso;
    @XmlElement(required = true)
    protected String identificativoUnivocoRegolamento;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataRegolamento;
    @XmlElement(required = true)
    protected CtIstitutoMittente istitutoMittente;
    @XmlElement(required = true)
    protected CtIstitutoRicevente istitutoRicevente;
    @XmlElement(required = true)
    protected BigDecimal numeroTotalePagamenti;
    @XmlElement(required = true)
    protected BigDecimal importoTotalePagamenti;
    @XmlElement(required = true)
    protected CtDatiSingoliPagamenti datiSingoliPagamenti;

    /**
     * Recupera il valore della proprietà versioneOggetto.
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
     * Imposta il valore della proprietà versioneOggetto.
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
     * Recupera il valore della proprietà identificativoFlusso.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativoFlusso() {
        return identificativoFlusso;
    }

    /**
     * Imposta il valore della proprietà identificativoFlusso.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativoFlusso(String value) {
        this.identificativoFlusso = value;
    }

    /**
     * Recupera il valore della proprietà dataOraFlusso.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataOraFlusso() {
        return dataOraFlusso;
    }

    /**
     * Imposta il valore della proprietà dataOraFlusso.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataOraFlusso(XMLGregorianCalendar value) {
        this.dataOraFlusso = value;
    }

    /**
     * Recupera il valore della proprietà identificativoUnivocoRegolamento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativoUnivocoRegolamento() {
        return identificativoUnivocoRegolamento;
    }

    /**
     * Imposta il valore della proprietà identificativoUnivocoRegolamento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativoUnivocoRegolamento(String value) {
        this.identificativoUnivocoRegolamento = value;
    }

    /**
     * Recupera il valore della proprietà dataRegolamento.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataRegolamento() {
        return dataRegolamento;
    }

    /**
     * Imposta il valore della proprietà dataRegolamento.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataRegolamento(XMLGregorianCalendar value) {
        this.dataRegolamento = value;
    }

    /**
     * Recupera il valore della proprietà istitutoMittente.
     * 
     * @return
     *     possible object is
     *     {@link CtIstitutoMittente }
     *     
     */
    public CtIstitutoMittente getIstitutoMittente() {
        return istitutoMittente;
    }

    /**
     * Imposta il valore della proprietà istitutoMittente.
     * 
     * @param value
     *     allowed object is
     *     {@link CtIstitutoMittente }
     *     
     */
    public void setIstitutoMittente(CtIstitutoMittente value) {
        this.istitutoMittente = value;
    }

    /**
     * Recupera il valore della proprietà istitutoRicevente.
     * 
     * @return
     *     possible object is
     *     {@link CtIstitutoRicevente }
     *     
     */
    public CtIstitutoRicevente getIstitutoRicevente() {
        return istitutoRicevente;
    }

    /**
     * Imposta il valore della proprietà istitutoRicevente.
     * 
     * @param value
     *     allowed object is
     *     {@link CtIstitutoRicevente }
     *     
     */
    public void setIstitutoRicevente(CtIstitutoRicevente value) {
        this.istitutoRicevente = value;
    }

    /**
     * Recupera il valore della proprietà numeroTotalePagamenti.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getNumeroTotalePagamenti() {
        return numeroTotalePagamenti;
    }

    /**
     * Imposta il valore della proprietà numeroTotalePagamenti.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setNumeroTotalePagamenti(BigDecimal value) {
        this.numeroTotalePagamenti = value;
    }

    /**
     * Recupera il valore della proprietà importoTotalePagamenti.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImportoTotalePagamenti() {
        return importoTotalePagamenti;
    }

    /**
     * Imposta il valore della proprietà importoTotalePagamenti.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImportoTotalePagamenti(BigDecimal value) {
        this.importoTotalePagamenti = value;
    }

    /**
     * Recupera il valore della proprietà datiSingoliPagamenti.
     * 
     * @return
     *     possible object is
     *     {@link CtDatiSingoliPagamenti }
     *     
     */
    public CtDatiSingoliPagamenti getDatiSingoliPagamenti() {
        return datiSingoliPagamenti;
    }

    /**
     * Imposta il valore della proprietà datiSingoliPagamenti.
     * 
     * @param value
     *     allowed object is
     *     {@link CtDatiSingoliPagamenti }
     *     
     */
    public void setDatiSingoliPagamenti(CtDatiSingoliPagamenti value) {
        this.datiSingoliPagamenti = value;
    }

}
