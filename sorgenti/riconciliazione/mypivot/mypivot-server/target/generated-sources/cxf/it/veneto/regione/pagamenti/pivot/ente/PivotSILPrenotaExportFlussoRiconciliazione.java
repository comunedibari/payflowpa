
package it.veneto.regione.pagamenti.pivot.ente;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per pivotSILPrenotaExportFlussoRiconciliazione complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="pivotSILPrenotaExportFlussoRiconciliazione"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="password" type="{http://www.regione.veneto.it/pagamenti/pivot/ente/}stPassword" minOccurs="0"/&gt;
 *         &lt;element name="codiceClassificazione" type="{http://www.regione.veneto.it/pagamenti/pivot/ente/}codiceClassificazioneType"/&gt;
 *         &lt;element name="tipoDovuto" type="{http://www.regione.veneto.it/pagamenti/pivot/ente/}tipoDovutoType"/&gt;
 *         &lt;element name="idUnivocoVersamento" type="{http://www.regione.veneto.it/pagamenti/pivot/ente/}idUnivocoVersamentoType"/&gt;
 *         &lt;element name="idUnivocoRendicontazione" type="{http://www.regione.veneto.it/pagamenti/pivot/ente/}idUnivocoRendicontazioneType"/&gt;
 *         &lt;element name="dataUltimoAggiornamentoDa" type="{http://www.regione.veneto.it/pagamenti/pivot/ente/}stISODateTime" minOccurs="0"/&gt;
 *         &lt;element name="dataUltimoAggiornamentoA" type="{http://www.regione.veneto.it/pagamenti/pivot/ente/}stISODateTime" minOccurs="0"/&gt;
 *         &lt;element name="dataEsecuzioneDa" type="{http://www.regione.veneto.it/pagamenti/pivot/ente/}stISODateTime" minOccurs="0"/&gt;
 *         &lt;element name="dataEsecuzioneA" type="{http://www.regione.veneto.it/pagamenti/pivot/ente/}stISODateTime" minOccurs="0"/&gt;
 *         &lt;element name="dataEsitoDa" type="{http://www.regione.veneto.it/pagamenti/pivot/ente/}stISODateTime" minOccurs="0"/&gt;
 *         &lt;element name="dataEsitoA" type="{http://www.regione.veneto.it/pagamenti/pivot/ente/}stISODateTime" minOccurs="0"/&gt;
 *         &lt;element name="dataRegolamentoDa" type="{http://www.regione.veneto.it/pagamenti/pivot/ente/}stISODateTime" minOccurs="0"/&gt;
 *         &lt;element name="dataRegolamentoA" type="{http://www.regione.veneto.it/pagamenti/pivot/ente/}stISODateTime" minOccurs="0"/&gt;
 *         &lt;element name="dataContabileDa" type="{http://www.regione.veneto.it/pagamenti/pivot/ente/}stISODateTime" minOccurs="0"/&gt;
 *         &lt;element name="dataContabileA" type="{http://www.regione.veneto.it/pagamenti/pivot/ente/}stISODateTime" minOccurs="0"/&gt;
 *         &lt;element name="dataValutaDa" type="{http://www.regione.veneto.it/pagamenti/pivot/ente/}stISODateTime" minOccurs="0"/&gt;
 *         &lt;element name="dataValutaA" type="{http://www.regione.veneto.it/pagamenti/pivot/ente/}stISODateTime" minOccurs="0"/&gt;
 *         &lt;element name="idUnivocoDovuto" type="{http://www.regione.veneto.it/pagamenti/pivot/ente/}stText35" minOccurs="0"/&gt;
 *         &lt;element name="idUnivocoRiscossione" type="{http://www.regione.veneto.it/pagamenti/pivot/ente/}stText35" minOccurs="0"/&gt;
 *         &lt;element name="idUnivocoPagatore" type="{http://www.regione.veneto.it/pagamenti/pivot/ente/}stText35" minOccurs="0"/&gt;
 *         &lt;element name="anagraficaPagatore" type="{http://www.regione.veneto.it/pagamenti/pivot/ente/}stText70" minOccurs="0"/&gt;
 *         &lt;element name="idUnivocoVersante" type="{http://www.regione.veneto.it/pagamenti/pivot/ente/}stText35" minOccurs="0"/&gt;
 *         &lt;element name="anagraficaVersante" type="{http://www.regione.veneto.it/pagamenti/pivot/ente/}stText70" minOccurs="0"/&gt;
 *         &lt;element name="denominazioneAttestante" type="{http://www.regione.veneto.it/pagamenti/pivot/ente/}stText70" minOccurs="0"/&gt;
 *         &lt;element name="ordinante" type="{http://www.regione.veneto.it/pagamenti/pivot/ente/}stText1024" minOccurs="0"/&gt;
 *         &lt;element name="idRegolamento" type="{http://www.regione.veneto.it/pagamenti/pivot/ente/}stText35" minOccurs="0"/&gt;
 *         &lt;element name="contoTesoreria" type="{http://www.regione.veneto.it/pagamenti/pivot/ente/}stText12" minOccurs="0"/&gt;
 *         &lt;element name="importoTesoreria" type="{http://www.regione.veneto.it/pagamenti/pivot/ente/}stText35" minOccurs="0"/&gt;
 *         &lt;element name="causale" type="{http://www.regione.veneto.it/pagamenti/pivot/ente/}stText1024" minOccurs="0"/&gt;
 *         &lt;element name="versioneTracciato" type="{http://www.regione.veneto.it/pagamenti/pivot/ente/}stText35"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pivotSILPrenotaExportFlussoRiconciliazione", propOrder = {
    "password",
    "codiceClassificazione",
    "tipoDovuto",
    "idUnivocoVersamento",
    "idUnivocoRendicontazione",
    "dataUltimoAggiornamentoDa",
    "dataUltimoAggiornamentoA",
    "dataEsecuzioneDa",
    "dataEsecuzioneA",
    "dataEsitoDa",
    "dataEsitoA",
    "dataRegolamentoDa",
    "dataRegolamentoA",
    "dataContabileDa",
    "dataContabileA",
    "dataValutaDa",
    "dataValutaA",
    "idUnivocoDovuto",
    "idUnivocoRiscossione",
    "idUnivocoPagatore",
    "anagraficaPagatore",
    "idUnivocoVersante",
    "anagraficaVersante",
    "denominazioneAttestante",
    "ordinante",
    "idRegolamento",
    "contoTesoreria",
    "importoTesoreria",
    "causale",
    "versioneTracciato"
})
public class PivotSILPrenotaExportFlussoRiconciliazione {

    protected String password;
    @XmlElement(required = true)
    protected CodiceClassificazioneType codiceClassificazione;
    @XmlElement(required = true)
    protected TipoDovutoType tipoDovuto;
    @XmlElement(required = true)
    protected IdUnivocoVersamentoType idUnivocoVersamento;
    @XmlElement(required = true)
    protected IdUnivocoRendicontazioneType idUnivocoRendicontazione;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataUltimoAggiornamentoDa;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataUltimoAggiornamentoA;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataEsecuzioneDa;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataEsecuzioneA;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataEsitoDa;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataEsitoA;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataRegolamentoDa;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataRegolamentoA;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataContabileDa;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataContabileA;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataValutaDa;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataValutaA;
    protected String idUnivocoDovuto;
    protected String idUnivocoRiscossione;
    protected String idUnivocoPagatore;
    protected String anagraficaPagatore;
    protected String idUnivocoVersante;
    protected String anagraficaVersante;
    protected String denominazioneAttestante;
    protected String ordinante;
    protected String idRegolamento;
    protected String contoTesoreria;
    protected String importoTesoreria;
    protected String causale;
    @XmlElement(required = true)
    protected String versioneTracciato;

    /**
     * Recupera il valore della proprietà password.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassword() {
        return password;
    }

    /**
     * Imposta il valore della proprietà password.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * Recupera il valore della proprietà codiceClassificazione.
     * 
     * @return
     *     possible object is
     *     {@link CodiceClassificazioneType }
     *     
     */
    public CodiceClassificazioneType getCodiceClassificazione() {
        return codiceClassificazione;
    }

    /**
     * Imposta il valore della proprietà codiceClassificazione.
     * 
     * @param value
     *     allowed object is
     *     {@link CodiceClassificazioneType }
     *     
     */
    public void setCodiceClassificazione(CodiceClassificazioneType value) {
        this.codiceClassificazione = value;
    }

    /**
     * Recupera il valore della proprietà tipoDovuto.
     * 
     * @return
     *     possible object is
     *     {@link TipoDovutoType }
     *     
     */
    public TipoDovutoType getTipoDovuto() {
        return tipoDovuto;
    }

    /**
     * Imposta il valore della proprietà tipoDovuto.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoDovutoType }
     *     
     */
    public void setTipoDovuto(TipoDovutoType value) {
        this.tipoDovuto = value;
    }

    /**
     * Recupera il valore della proprietà idUnivocoVersamento.
     * 
     * @return
     *     possible object is
     *     {@link IdUnivocoVersamentoType }
     *     
     */
    public IdUnivocoVersamentoType getIdUnivocoVersamento() {
        return idUnivocoVersamento;
    }

    /**
     * Imposta il valore della proprietà idUnivocoVersamento.
     * 
     * @param value
     *     allowed object is
     *     {@link IdUnivocoVersamentoType }
     *     
     */
    public void setIdUnivocoVersamento(IdUnivocoVersamentoType value) {
        this.idUnivocoVersamento = value;
    }

    /**
     * Recupera il valore della proprietà idUnivocoRendicontazione.
     * 
     * @return
     *     possible object is
     *     {@link IdUnivocoRendicontazioneType }
     *     
     */
    public IdUnivocoRendicontazioneType getIdUnivocoRendicontazione() {
        return idUnivocoRendicontazione;
    }

    /**
     * Imposta il valore della proprietà idUnivocoRendicontazione.
     * 
     * @param value
     *     allowed object is
     *     {@link IdUnivocoRendicontazioneType }
     *     
     */
    public void setIdUnivocoRendicontazione(IdUnivocoRendicontazioneType value) {
        this.idUnivocoRendicontazione = value;
    }

    /**
     * Recupera il valore della proprietà dataUltimoAggiornamentoDa.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataUltimoAggiornamentoDa() {
        return dataUltimoAggiornamentoDa;
    }

    /**
     * Imposta il valore della proprietà dataUltimoAggiornamentoDa.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataUltimoAggiornamentoDa(XMLGregorianCalendar value) {
        this.dataUltimoAggiornamentoDa = value;
    }

    /**
     * Recupera il valore della proprietà dataUltimoAggiornamentoA.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataUltimoAggiornamentoA() {
        return dataUltimoAggiornamentoA;
    }

    /**
     * Imposta il valore della proprietà dataUltimoAggiornamentoA.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataUltimoAggiornamentoA(XMLGregorianCalendar value) {
        this.dataUltimoAggiornamentoA = value;
    }

    /**
     * Recupera il valore della proprietà dataEsecuzioneDa.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataEsecuzioneDa() {
        return dataEsecuzioneDa;
    }

    /**
     * Imposta il valore della proprietà dataEsecuzioneDa.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataEsecuzioneDa(XMLGregorianCalendar value) {
        this.dataEsecuzioneDa = value;
    }

    /**
     * Recupera il valore della proprietà dataEsecuzioneA.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataEsecuzioneA() {
        return dataEsecuzioneA;
    }

    /**
     * Imposta il valore della proprietà dataEsecuzioneA.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataEsecuzioneA(XMLGregorianCalendar value) {
        this.dataEsecuzioneA = value;
    }

    /**
     * Recupera il valore della proprietà dataEsitoDa.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataEsitoDa() {
        return dataEsitoDa;
    }

    /**
     * Imposta il valore della proprietà dataEsitoDa.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataEsitoDa(XMLGregorianCalendar value) {
        this.dataEsitoDa = value;
    }

    /**
     * Recupera il valore della proprietà dataEsitoA.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataEsitoA() {
        return dataEsitoA;
    }

    /**
     * Imposta il valore della proprietà dataEsitoA.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataEsitoA(XMLGregorianCalendar value) {
        this.dataEsitoA = value;
    }

    /**
     * Recupera il valore della proprietà dataRegolamentoDa.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataRegolamentoDa() {
        return dataRegolamentoDa;
    }

    /**
     * Imposta il valore della proprietà dataRegolamentoDa.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataRegolamentoDa(XMLGregorianCalendar value) {
        this.dataRegolamentoDa = value;
    }

    /**
     * Recupera il valore della proprietà dataRegolamentoA.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataRegolamentoA() {
        return dataRegolamentoA;
    }

    /**
     * Imposta il valore della proprietà dataRegolamentoA.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataRegolamentoA(XMLGregorianCalendar value) {
        this.dataRegolamentoA = value;
    }

    /**
     * Recupera il valore della proprietà dataContabileDa.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataContabileDa() {
        return dataContabileDa;
    }

    /**
     * Imposta il valore della proprietà dataContabileDa.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataContabileDa(XMLGregorianCalendar value) {
        this.dataContabileDa = value;
    }

    /**
     * Recupera il valore della proprietà dataContabileA.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataContabileA() {
        return dataContabileA;
    }

    /**
     * Imposta il valore della proprietà dataContabileA.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataContabileA(XMLGregorianCalendar value) {
        this.dataContabileA = value;
    }

    /**
     * Recupera il valore della proprietà dataValutaDa.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataValutaDa() {
        return dataValutaDa;
    }

    /**
     * Imposta il valore della proprietà dataValutaDa.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataValutaDa(XMLGregorianCalendar value) {
        this.dataValutaDa = value;
    }

    /**
     * Recupera il valore della proprietà dataValutaA.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataValutaA() {
        return dataValutaA;
    }

    /**
     * Imposta il valore della proprietà dataValutaA.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataValutaA(XMLGregorianCalendar value) {
        this.dataValutaA = value;
    }

    /**
     * Recupera il valore della proprietà idUnivocoDovuto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdUnivocoDovuto() {
        return idUnivocoDovuto;
    }

    /**
     * Imposta il valore della proprietà idUnivocoDovuto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdUnivocoDovuto(String value) {
        this.idUnivocoDovuto = value;
    }

    /**
     * Recupera il valore della proprietà idUnivocoRiscossione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdUnivocoRiscossione() {
        return idUnivocoRiscossione;
    }

    /**
     * Imposta il valore della proprietà idUnivocoRiscossione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdUnivocoRiscossione(String value) {
        this.idUnivocoRiscossione = value;
    }

    /**
     * Recupera il valore della proprietà idUnivocoPagatore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdUnivocoPagatore() {
        return idUnivocoPagatore;
    }

    /**
     * Imposta il valore della proprietà idUnivocoPagatore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdUnivocoPagatore(String value) {
        this.idUnivocoPagatore = value;
    }

    /**
     * Recupera il valore della proprietà anagraficaPagatore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnagraficaPagatore() {
        return anagraficaPagatore;
    }

    /**
     * Imposta il valore della proprietà anagraficaPagatore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnagraficaPagatore(String value) {
        this.anagraficaPagatore = value;
    }

    /**
     * Recupera il valore della proprietà idUnivocoVersante.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdUnivocoVersante() {
        return idUnivocoVersante;
    }

    /**
     * Imposta il valore della proprietà idUnivocoVersante.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdUnivocoVersante(String value) {
        this.idUnivocoVersante = value;
    }

    /**
     * Recupera il valore della proprietà anagraficaVersante.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnagraficaVersante() {
        return anagraficaVersante;
    }

    /**
     * Imposta il valore della proprietà anagraficaVersante.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnagraficaVersante(String value) {
        this.anagraficaVersante = value;
    }

    /**
     * Recupera il valore della proprietà denominazioneAttestante.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDenominazioneAttestante() {
        return denominazioneAttestante;
    }

    /**
     * Imposta il valore della proprietà denominazioneAttestante.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDenominazioneAttestante(String value) {
        this.denominazioneAttestante = value;
    }

    /**
     * Recupera il valore della proprietà ordinante.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrdinante() {
        return ordinante;
    }

    /**
     * Imposta il valore della proprietà ordinante.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrdinante(String value) {
        this.ordinante = value;
    }

    /**
     * Recupera il valore della proprietà idRegolamento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdRegolamento() {
        return idRegolamento;
    }

    /**
     * Imposta il valore della proprietà idRegolamento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdRegolamento(String value) {
        this.idRegolamento = value;
    }

    /**
     * Recupera il valore della proprietà contoTesoreria.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContoTesoreria() {
        return contoTesoreria;
    }

    /**
     * Imposta il valore della proprietà contoTesoreria.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContoTesoreria(String value) {
        this.contoTesoreria = value;
    }

    /**
     * Recupera il valore della proprietà importoTesoreria.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImportoTesoreria() {
        return importoTesoreria;
    }

    /**
     * Imposta il valore della proprietà importoTesoreria.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImportoTesoreria(String value) {
        this.importoTesoreria = value;
    }

    /**
     * Recupera il valore della proprietà causale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCausale() {
        return causale;
    }

    /**
     * Imposta il valore della proprietà causale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCausale(String value) {
        this.causale = value;
    }

    /**
     * Recupera il valore della proprietà versioneTracciato.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersioneTracciato() {
        return versioneTracciato;
    }

    /**
     * Imposta il valore della proprietà versioneTracciato.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersioneTracciato(String value) {
        this.versioneTracciato = value;
    }

}
