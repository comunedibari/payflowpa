
package it.veneto.regione.pagamenti.pivot.ente;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import it.veneto.regione.schemas._2012.pagamenti.CtDominio;
import it.veneto.regione.schemas._2012.pagamenti.CtEnteBeneficiario;
import it.veneto.regione.schemas._2012.pagamenti.CtIstitutoAttestante;
import it.veneto.regione.schemas._2012.pagamenti.CtSoggettoPagatore;
import it.veneto.regione.schemas._2012.pagamenti.CtSoggettoVersante;


/**
 * <p>Classe Java per ctExport complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ctExport"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="versioneOggetto" type="{http://www.regione.veneto.it/schemas/2012/Pagamenti/}stText16"/&gt;
 *         &lt;element name="dominio" type="{http://www.regione.veneto.it/schemas/2012/Pagamenti/}ctDominio"/&gt;
 *         &lt;element name="identificativoMessaggioRicevuta" type="{http://www.regione.veneto.it/schemas/2012/Pagamenti/}stText35"/&gt;
 *         &lt;element name="dataOraMessaggioRicevuta" type="{http://www.regione.veneto.it/schemas/2012/Pagamenti/}stISODateTime"/&gt;
 *         &lt;element name="riferimentoMessaggioRichiesta" type="{http://www.regione.veneto.it/schemas/2012/Pagamenti/}stText35"/&gt;
 *         &lt;element name="riferimentoDataRichiesta" type="{http://www.regione.veneto.it/schemas/2012/Pagamenti/}stISODate"/&gt;
 *         &lt;element name="istitutoAttestante" type="{http://www.regione.veneto.it/schemas/2012/Pagamenti/}ctIstitutoAttestante"/&gt;
 *         &lt;element name="enteBeneficiario" type="{http://www.regione.veneto.it/schemas/2012/Pagamenti/}ctEnteBeneficiario"/&gt;
 *         &lt;element name="soggettoVersante" type="{http://www.regione.veneto.it/schemas/2012/Pagamenti/}ctSoggettoVersante" minOccurs="0"/&gt;
 *         &lt;element name="soggettoPagatore" type="{http://www.regione.veneto.it/schemas/2012/Pagamenti/}ctSoggettoPagatore"/&gt;
 *         &lt;element name="datiPagamento" type="{http://www.regione.veneto.it/pagamenti/pivot/ente/}ctDatiVersamentoExport"/&gt;
 *         &lt;element name="identificativoUnivocoDovuto" type="{http://www.regione.veneto.it/pagamenti/pivot/ente/}stText35"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ctExport", propOrder = {
    "versioneOggetto",
    "dominio",
    "identificativoMessaggioRicevuta",
    "dataOraMessaggioRicevuta",
    "riferimentoMessaggioRichiesta",
    "riferimentoDataRichiesta",
    "istitutoAttestante",
    "enteBeneficiario",
    "soggettoVersante",
    "soggettoPagatore",
    "datiPagamento",
    "identificativoUnivocoDovuto"
})
public class CtExport {

    @XmlElement(required = true)
    protected String versioneOggetto;
    @XmlElement(required = true)
    protected CtDominio dominio;
    @XmlElement(required = true)
    protected String identificativoMessaggioRicevuta;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataOraMessaggioRicevuta;
    @XmlElement(required = true)
    protected String riferimentoMessaggioRichiesta;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar riferimentoDataRichiesta;
    @XmlElement(required = true)
    protected CtIstitutoAttestante istitutoAttestante;
    @XmlElement(required = true)
    protected CtEnteBeneficiario enteBeneficiario;
    protected CtSoggettoVersante soggettoVersante;
    @XmlElement(required = true)
    protected CtSoggettoPagatore soggettoPagatore;
    @XmlElement(required = true)
    protected CtDatiVersamentoExport datiPagamento;
    @XmlElement(required = true)
    protected String identificativoUnivocoDovuto;

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
     * Recupera il valore della proprietà identificativoMessaggioRicevuta.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativoMessaggioRicevuta() {
        return identificativoMessaggioRicevuta;
    }

    /**
     * Imposta il valore della proprietà identificativoMessaggioRicevuta.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativoMessaggioRicevuta(String value) {
        this.identificativoMessaggioRicevuta = value;
    }

    /**
     * Recupera il valore della proprietà dataOraMessaggioRicevuta.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataOraMessaggioRicevuta() {
        return dataOraMessaggioRicevuta;
    }

    /**
     * Imposta il valore della proprietà dataOraMessaggioRicevuta.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataOraMessaggioRicevuta(XMLGregorianCalendar value) {
        this.dataOraMessaggioRicevuta = value;
    }

    /**
     * Recupera il valore della proprietà riferimentoMessaggioRichiesta.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRiferimentoMessaggioRichiesta() {
        return riferimentoMessaggioRichiesta;
    }

    /**
     * Imposta il valore della proprietà riferimentoMessaggioRichiesta.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRiferimentoMessaggioRichiesta(String value) {
        this.riferimentoMessaggioRichiesta = value;
    }

    /**
     * Recupera il valore della proprietà riferimentoDataRichiesta.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRiferimentoDataRichiesta() {
        return riferimentoDataRichiesta;
    }

    /**
     * Imposta il valore della proprietà riferimentoDataRichiesta.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setRiferimentoDataRichiesta(XMLGregorianCalendar value) {
        this.riferimentoDataRichiesta = value;
    }

    /**
     * Recupera il valore della proprietà istitutoAttestante.
     * 
     * @return
     *     possible object is
     *     {@link CtIstitutoAttestante }
     *     
     */
    public CtIstitutoAttestante getIstitutoAttestante() {
        return istitutoAttestante;
    }

    /**
     * Imposta il valore della proprietà istitutoAttestante.
     * 
     * @param value
     *     allowed object is
     *     {@link CtIstitutoAttestante }
     *     
     */
    public void setIstitutoAttestante(CtIstitutoAttestante value) {
        this.istitutoAttestante = value;
    }

    /**
     * Recupera il valore della proprietà enteBeneficiario.
     * 
     * @return
     *     possible object is
     *     {@link CtEnteBeneficiario }
     *     
     */
    public CtEnteBeneficiario getEnteBeneficiario() {
        return enteBeneficiario;
    }

    /**
     * Imposta il valore della proprietà enteBeneficiario.
     * 
     * @param value
     *     allowed object is
     *     {@link CtEnteBeneficiario }
     *     
     */
    public void setEnteBeneficiario(CtEnteBeneficiario value) {
        this.enteBeneficiario = value;
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
     * Recupera il valore della proprietà datiPagamento.
     * 
     * @return
     *     possible object is
     *     {@link CtDatiVersamentoExport }
     *     
     */
    public CtDatiVersamentoExport getDatiPagamento() {
        return datiPagamento;
    }

    /**
     * Imposta il valore della proprietà datiPagamento.
     * 
     * @param value
     *     allowed object is
     *     {@link CtDatiVersamentoExport }
     *     
     */
    public void setDatiPagamento(CtDatiVersamentoExport value) {
        this.datiPagamento = value;
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

}
