
package it.veneto.regione.schemas._2012.pagamenti;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per ctDatiVersamentoRP complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ctDatiVersamentoRP"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="dataEsecuzionePagamento" type="{http://www.regione.veneto.it/schemas/2012/Pagamenti/}stISODate"/&gt;
 *         &lt;element name="importoTotaleDaVersare" type="{http://www.regione.veneto.it/schemas/2012/Pagamenti/}stImporto"/&gt;
 *         &lt;element name="tipoVersamento" type="{http://www.regione.veneto.it/schemas/2012/Pagamenti/}stTipoVersamento"/&gt;
 *         &lt;element name="identificativoUnivocoVersamento" type="{http://www.regione.veneto.it/schemas/2012/Pagamenti/}stText35"/&gt;
 *         &lt;element name="codiceContestoPagamento" type="{http://www.regione.veneto.it/schemas/2012/Pagamenti/}stText35"/&gt;
 *         &lt;element name="ibanAddebito" type="{http://www.regione.veneto.it/schemas/2012/Pagamenti/}stIBANIdentifier" minOccurs="0"/&gt;
 *         &lt;element name="bicAddebito" type="{http://www.regione.veneto.it/schemas/2012/Pagamenti/}stBICIdentifier" minOccurs="0"/&gt;
 *         &lt;element name="datiSingoloVersamento" type="{http://www.regione.veneto.it/schemas/2012/Pagamenti/}ctDatiSingoloVersamentoRP" maxOccurs="5"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ctDatiVersamentoRP", propOrder = {
    "dataEsecuzionePagamento",
    "importoTotaleDaVersare",
    "tipoVersamento",
    "identificativoUnivocoVersamento",
    "codiceContestoPagamento",
    "ibanAddebito",
    "bicAddebito",
    "datiSingoloVersamento"
})
public class CtDatiVersamentoRP {

    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataEsecuzionePagamento;
    @XmlElement(required = true)
    protected BigDecimal importoTotaleDaVersare;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected StTipoVersamento tipoVersamento;
    @XmlElement(required = true)
    protected String identificativoUnivocoVersamento;
    @XmlElement(required = true)
    protected String codiceContestoPagamento;
    protected String ibanAddebito;
    protected String bicAddebito;
    @XmlElement(required = true)
    protected List<CtDatiSingoloVersamentoRP> datiSingoloVersamento;

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
     * Recupera il valore della proprietà importoTotaleDaVersare.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImportoTotaleDaVersare() {
        return importoTotaleDaVersare;
    }

    /**
     * Imposta il valore della proprietà importoTotaleDaVersare.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImportoTotaleDaVersare(BigDecimal value) {
        this.importoTotaleDaVersare = value;
    }

    /**
     * Recupera il valore della proprietà tipoVersamento.
     * 
     * @return
     *     possible object is
     *     {@link StTipoVersamento }
     *     
     */
    public StTipoVersamento getTipoVersamento() {
        return tipoVersamento;
    }

    /**
     * Imposta il valore della proprietà tipoVersamento.
     * 
     * @param value
     *     allowed object is
     *     {@link StTipoVersamento }
     *     
     */
    public void setTipoVersamento(StTipoVersamento value) {
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
     * Recupera il valore della proprietà ibanAddebito.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIbanAddebito() {
        return ibanAddebito;
    }

    /**
     * Imposta il valore della proprietà ibanAddebito.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIbanAddebito(String value) {
        this.ibanAddebito = value;
    }

    /**
     * Recupera il valore della proprietà bicAddebito.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBicAddebito() {
        return bicAddebito;
    }

    /**
     * Imposta il valore della proprietà bicAddebito.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBicAddebito(String value) {
        this.bicAddebito = value;
    }

    /**
     * Gets the value of the datiSingoloVersamento property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the datiSingoloVersamento property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDatiSingoloVersamento().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CtDatiSingoloVersamentoRP }
     * 
     * 
     */
    public List<CtDatiSingoloVersamentoRP> getDatiSingoloVersamento() {
        if (datiSingoloVersamento == null) {
            datiSingoloVersamento = new ArrayList<CtDatiSingoloVersamentoRP>();
        }
        return this.datiSingoloVersamento;
    }

}
