
package it.veneto.regione.schemas._2012.pagamenti;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per ctRichiestaPagamento complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ctRichiestaPagamento"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="versioneOggetto" type="{http://www.regione.veneto.it/schemas/2012/Pagamenti/}stText16"/&gt;
 *         &lt;element name="dominio" type="{http://www.regione.veneto.it/schemas/2012/Pagamenti/}ctDominio"/&gt;
 *         &lt;element name="identificativoMessaggioRichiesta" type="{http://www.regione.veneto.it/schemas/2012/Pagamenti/}stText35"/&gt;
 *         &lt;element name="dataOraMessaggioRichiesta" type="{http://www.regione.veneto.it/schemas/2012/Pagamenti/}stISODateTime"/&gt;
 *         &lt;element name="autenticazioneSoggetto" type="{http://www.regione.veneto.it/schemas/2012/Pagamenti/}stAutenticazioneSoggetto"/&gt;
 *         &lt;element name="soggettoVersante" type="{http://www.regione.veneto.it/schemas/2012/Pagamenti/}ctSoggettoVersante" minOccurs="0"/&gt;
 *         &lt;element name="soggettoPagatore" type="{http://www.regione.veneto.it/schemas/2012/Pagamenti/}ctSoggettoPagatore"/&gt;
 *         &lt;element name="datiVersamento" type="{http://www.regione.veneto.it/schemas/2012/Pagamenti/}ctDatiVersamentoRP"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ctRichiestaPagamento", propOrder = {
    "versioneOggetto",
    "dominio",
    "identificativoMessaggioRichiesta",
    "dataOraMessaggioRichiesta",
    "autenticazioneSoggetto",
    "soggettoVersante",
    "soggettoPagatore",
    "datiVersamento"
})
public class CtRichiestaPagamento {

    @XmlElement(required = true)
    protected String versioneOggetto;
    @XmlElement(required = true)
    protected CtDominio dominio;
    @XmlElement(required = true)
    protected String identificativoMessaggioRichiesta;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataOraMessaggioRichiesta;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected StAutenticazioneSoggetto autenticazioneSoggetto;
    protected CtSoggettoVersante soggettoVersante;
    @XmlElement(required = true)
    protected CtSoggettoPagatore soggettoPagatore;
    @XmlElement(required = true)
    protected CtDatiVersamentoRP datiVersamento;

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
     * Recupera il valore della proprietà dominio.
     * 
     * @return
     *     possible object is
     *     {@link CtDominio }
     *     
     */
    public CtDominio getDominio() {
        return dominio;
    }

    /**
     * Imposta il valore della proprietà dominio.
     * 
     * @param value
     *     allowed object is
     *     {@link CtDominio }
     *     
     */
    public void setDominio(CtDominio value) {
        this.dominio = value;
    }

    /**
     * Recupera il valore della proprietà identificativoMessaggioRichiesta.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativoMessaggioRichiesta() {
        return identificativoMessaggioRichiesta;
    }

    /**
     * Imposta il valore della proprietà identificativoMessaggioRichiesta.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativoMessaggioRichiesta(String value) {
        this.identificativoMessaggioRichiesta = value;
    }

    /**
     * Recupera il valore della proprietà dataOraMessaggioRichiesta.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataOraMessaggioRichiesta() {
        return dataOraMessaggioRichiesta;
    }

    /**
     * Imposta il valore della proprietà dataOraMessaggioRichiesta.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataOraMessaggioRichiesta(XMLGregorianCalendar value) {
        this.dataOraMessaggioRichiesta = value;
    }

    /**
     * Recupera il valore della proprietà autenticazioneSoggetto.
     * 
     * @return
     *     possible object is
     *     {@link StAutenticazioneSoggetto }
     *     
     */
    public StAutenticazioneSoggetto getAutenticazioneSoggetto() {
        return autenticazioneSoggetto;
    }

    /**
     * Imposta il valore della proprietà autenticazioneSoggetto.
     * 
     * @param value
     *     allowed object is
     *     {@link StAutenticazioneSoggetto }
     *     
     */
    public void setAutenticazioneSoggetto(StAutenticazioneSoggetto value) {
        this.autenticazioneSoggetto = value;
    }

    /**
     * Recupera il valore della proprietà soggettoVersante.
     * 
     * @return
     *     possible object is
     *     {@link CtSoggettoVersante }
     *     
     */
    public CtSoggettoVersante getSoggettoVersante() {
        return soggettoVersante;
    }

    /**
     * Imposta il valore della proprietà soggettoVersante.
     * 
     * @param value
     *     allowed object is
     *     {@link CtSoggettoVersante }
     *     
     */
    public void setSoggettoVersante(CtSoggettoVersante value) {
        this.soggettoVersante = value;
    }

    /**
     * Recupera il valore della proprietà soggettoPagatore.
     * 
     * @return
     *     possible object is
     *     {@link CtSoggettoPagatore }
     *     
     */
    public CtSoggettoPagatore getSoggettoPagatore() {
        return soggettoPagatore;
    }

    /**
     * Imposta il valore della proprietà soggettoPagatore.
     * 
     * @param value
     *     allowed object is
     *     {@link CtSoggettoPagatore }
     *     
     */
    public void setSoggettoPagatore(CtSoggettoPagatore value) {
        this.soggettoPagatore = value;
    }

    /**
     * Recupera il valore della proprietà datiVersamento.
     * 
     * @return
     *     possible object is
     *     {@link CtDatiVersamentoRP }
     *     
     */
    public CtDatiVersamentoRP getDatiVersamento() {
        return datiVersamento;
    }

    /**
     * Imposta il valore della proprietà datiVersamento.
     * 
     * @param value
     *     allowed object is
     *     {@link CtDatiVersamentoRP }
     *     
     */
    public void setDatiVersamento(CtDatiVersamentoRP value) {
        this.datiVersamento = value;
    }

}
