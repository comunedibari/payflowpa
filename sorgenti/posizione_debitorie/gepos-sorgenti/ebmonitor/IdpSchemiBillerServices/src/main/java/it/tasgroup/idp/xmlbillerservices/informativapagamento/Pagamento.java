
package it.tasgroup.idp.xmlbillerservices.informativapagamento;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for Pagamento complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Pagamento">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TipoNotifica" type="{http://idp.tasgroup.it/xmlbillerservices/InformativaPagamento}TipoNotificaType"/>
 *         &lt;element name="IdPagamento" type="{http://idp.tasgroup.it/xmlbillerservices/Common}IdentificativoIdp"/>
 *         &lt;element name="IdDebito" type="{http://idp.tasgroup.it/xmlbillerservices/Common}IdentificativoIdp"/>
 *         &lt;element name="TipoDebito" type="{http://idp.tasgroup.it/xmlbillerservices/Common}Max50Text"/>
 *         &lt;element name="CausaleDebito" type="{http://idp.tasgroup.it/xmlbillerservices/Common}Max256Text"/>
 *         &lt;element name="RiferimentoDebito" type="{http://idp.tasgroup.it/xmlbillerservices/Common}Max35Text" minOccurs="0"/>
 *         &lt;element name="CausalePagamento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NotePagamento" type="{http://idp.tasgroup.it/xmlbillerservices/Common}Max256Text" minOccurs="0"/>
 *         &lt;element name="DataScadenzaPagamento" type="{http://idp.tasgroup.it/xmlbillerservices/Common}IdPDateTime"/>
 *         &lt;element name="Importo" type="{http://idp.tasgroup.it/xmlbillerservices/Common}Importo"/>
 *         &lt;element name="Debitore" type="{http://idp.tasgroup.it/xmlbillerservices/InformativaPagamento}RiferimentoSoggetto"/>
 *         &lt;element name="IdentificativoUnivocoVersamento" type="{http://idp.tasgroup.it/xmlbillerservices/Common}IdentificativoIdp" minOccurs="0"/>
 *         &lt;element name="DataOraPagamento" type="{http://idp.tasgroup.it/xmlbillerservices/Common}IdPDateTime"/>
 *         &lt;element name="Versante" type="{http://idp.tasgroup.it/xmlbillerservices/InformativaPagamento}RiferimentoSoggetto" minOccurs="0"/>
 *         &lt;element name="Transazione" type="{http://idp.tasgroup.it/xmlbillerservices/InformativaPagamento}Transazione" minOccurs="0"/>
 *         &lt;element name="RiferimentoIncasso" type="{http://idp.tasgroup.it/xmlbillerservices/InformativaPagamento}RiferimentoIncasso" minOccurs="0"/>
 *         &lt;element name="NoteDebito" type="{http://idp.tasgroup.it/xmlbillerservices/Common}Max2000Text" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Pagamento", propOrder = {
    "tipoNotifica",
    "idPagamento",
    "idDebito",
    "tipoDebito",
    "causaleDebito",
    "riferimentoDebito",
    "causalePagamento",
    "notePagamento",
    "dataScadenzaPagamento",
    "importo",
    "debitore",
    "identificativoUnivocoVersamento",
    "dataOraPagamento",
    "versante",
    "transazione",
    "riferimentoIncasso",
    "noteDebito"
})
public class Pagamento {

    @XmlElement(name = "TipoNotifica", required = true)
    protected TipoNotificaType tipoNotifica;
    @XmlElement(name = "IdPagamento", required = true)
    protected String idPagamento;
    @XmlElement(name = "IdDebito", required = true)
    protected String idDebito;
    @XmlElement(name = "TipoDebito", required = true)
    protected String tipoDebito;
    @XmlElement(name = "CausaleDebito", required = true)
    protected String causaleDebito;
    @XmlElement(name = "RiferimentoDebito")
    protected String riferimentoDebito;
    @XmlElement(name = "CausalePagamento")
    protected String causalePagamento;
    @XmlElement(name = "NotePagamento")
    protected String notePagamento;
    @XmlElement(name = "DataScadenzaPagamento", required = true)
    protected XMLGregorianCalendar dataScadenzaPagamento;
    @XmlElement(name = "Importo", required = true)
    protected BigDecimal importo;
    @XmlElement(name = "Debitore", required = true)
    protected RiferimentoSoggetto debitore;
    @XmlElement(name = "IdentificativoUnivocoVersamento")
    protected String identificativoUnivocoVersamento;
    @XmlElement(name = "DataOraPagamento", required = true)
    protected XMLGregorianCalendar dataOraPagamento;
    @XmlElement(name = "Versante")
    protected RiferimentoSoggetto versante;
    @XmlElement(name = "Transazione")
    protected Transazione transazione;
    @XmlElement(name = "RiferimentoIncasso")
    protected RiferimentoIncasso riferimentoIncasso;
    @XmlElement(name = "NoteDebito")
    protected String noteDebito;

    /**
     * Gets the value of the tipoNotifica property.
     * 
     * @return
     *     possible object is
     *     {@link TipoNotificaType }
     *     
     */
    public TipoNotificaType getTipoNotifica() {
        return tipoNotifica;
    }

    /**
     * Sets the value of the tipoNotifica property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoNotificaType }
     *     
     */
    public void setTipoNotifica(TipoNotificaType value) {
        this.tipoNotifica = value;
    }

    /**
     * Gets the value of the idPagamento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdPagamento() {
        return idPagamento;
    }

    /**
     * Sets the value of the idPagamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdPagamento(String value) {
        this.idPagamento = value;
    }

    /**
     * Gets the value of the idDebito property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdDebito() {
        return idDebito;
    }

    /**
     * Sets the value of the idDebito property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdDebito(String value) {
        this.idDebito = value;
    }

    /**
     * Gets the value of the tipoDebito property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoDebito() {
        return tipoDebito;
    }

    /**
     * Sets the value of the tipoDebito property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoDebito(String value) {
        this.tipoDebito = value;
    }

    /**
     * Gets the value of the causaleDebito property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCausaleDebito() {
        return causaleDebito;
    }

    /**
     * Sets the value of the causaleDebito property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCausaleDebito(String value) {
        this.causaleDebito = value;
    }

    /**
     * Gets the value of the riferimentoDebito property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRiferimentoDebito() {
        return riferimentoDebito;
    }

    /**
     * Sets the value of the riferimentoDebito property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRiferimentoDebito(String value) {
        this.riferimentoDebito = value;
    }

    /**
     * Gets the value of the causalePagamento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCausalePagamento() {
        return causalePagamento;
    }

    /**
     * Sets the value of the causalePagamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCausalePagamento(String value) {
        this.causalePagamento = value;
    }

    /**
     * Gets the value of the notePagamento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNotePagamento() {
        return notePagamento;
    }

    /**
     * Sets the value of the notePagamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNotePagamento(String value) {
        this.notePagamento = value;
    }

    /**
     * Gets the value of the dataScadenzaPagamento property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataScadenzaPagamento() {
        return dataScadenzaPagamento;
    }

    /**
     * Sets the value of the dataScadenzaPagamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataScadenzaPagamento(XMLGregorianCalendar value) {
        this.dataScadenzaPagamento = value;
    }

    /**
     * Gets the value of the importo property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImporto() {
        return importo;
    }

    /**
     * Sets the value of the importo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImporto(BigDecimal value) {
        this.importo = value;
    }

    /**
     * Gets the value of the debitore property.
     * 
     * @return
     *     possible object is
     *     {@link RiferimentoSoggetto }
     *     
     */
    public RiferimentoSoggetto getDebitore() {
        return debitore;
    }

    /**
     * Sets the value of the debitore property.
     * 
     * @param value
     *     allowed object is
     *     {@link RiferimentoSoggetto }
     *     
     */
    public void setDebitore(RiferimentoSoggetto value) {
        this.debitore = value;
    }

    /**
     * Gets the value of the identificativoUnivocoVersamento property.
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
     * Sets the value of the identificativoUnivocoVersamento property.
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
     * Gets the value of the dataOraPagamento property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataOraPagamento() {
        return dataOraPagamento;
    }

    /**
     * Sets the value of the dataOraPagamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataOraPagamento(XMLGregorianCalendar value) {
        this.dataOraPagamento = value;
    }

    /**
     * Gets the value of the versante property.
     * 
     * @return
     *     possible object is
     *     {@link RiferimentoSoggetto }
     *     
     */
    public RiferimentoSoggetto getVersante() {
        return versante;
    }

    /**
     * Sets the value of the versante property.
     * 
     * @param value
     *     allowed object is
     *     {@link RiferimentoSoggetto }
     *     
     */
    public void setVersante(RiferimentoSoggetto value) {
        this.versante = value;
    }

    /**
     * Gets the value of the transazione property.
     * 
     * @return
     *     possible object is
     *     {@link Transazione }
     *     
     */
    public Transazione getTransazione() {
        return transazione;
    }

    /**
     * Sets the value of the transazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link Transazione }
     *     
     */
    public void setTransazione(Transazione value) {
        this.transazione = value;
    }

    /**
     * Gets the value of the riferimentoIncasso property.
     * 
     * @return
     *     possible object is
     *     {@link RiferimentoIncasso }
     *     
     */
    public RiferimentoIncasso getRiferimentoIncasso() {
        return riferimentoIncasso;
    }

    /**
     * Sets the value of the riferimentoIncasso property.
     * 
     * @param value
     *     allowed object is
     *     {@link RiferimentoIncasso }
     *     
     */
    public void setRiferimentoIncasso(RiferimentoIncasso value) {
        this.riferimentoIncasso = value;
    }

    /**
     * Gets the value of the noteDebito property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNoteDebito() {
        return noteDebito;
    }

    /**
     * Sets the value of the noteDebito property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNoteDebito(String value) {
        this.noteDebito = value;
    }

}
