
package it.tasgroup.idp.xmlbillerservices.pendenze;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import it.tasgroup.idp.xmlbillerservices.common.DatiMBD;
import it.tasgroup.idp.xmlbillerservices.common.Divisa;
import it.tasgroup.idp.xmlbillerservices.common.TipoOperazione;


/**
 * <p>Java class for Pendenza complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Pendenza">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IdDebito" type="{http://idp.tasgroup.it/xmlbillerservices/Common}IdentificativoIdp"/>
 *         &lt;element name="DatiMBD" type="{http://idp.tasgroup.it/xmlbillerservices/Common}DatiMBD" minOccurs="0"/>
 *         &lt;element name="Mittente" type="{http://idp.tasgroup.it/xmlbillerservices/Pendenze}Mittente"/>
 *         &lt;element name="Debitori" type="{http://idp.tasgroup.it/xmlbillerservices/Pendenze}Debitori"/>
 *         &lt;element name="RiferimentoDebito" type="{http://idp.tasgroup.it/xmlbillerservices/Common}Max35Text" minOccurs="0"/>
 *         &lt;element name="NoteDebito" type="{http://idp.tasgroup.it/xmlbillerservices/Common}Max2000Text" minOccurs="0"/>
 *         &lt;element name="CausaleDebito" type="{http://idp.tasgroup.it/xmlbillerservices/Common}Max512Text"/>
 *         &lt;element name="DataCreazione" type="{http://idp.tasgroup.it/xmlbillerservices/Common}IdPDateTime"/>
 *         &lt;element name="DataEmissione" type="{http://idp.tasgroup.it/xmlbillerservices/Common}IdPDateTime"/>
 *         &lt;element name="AnnoRiferimento" type="{http://www.w3.org/2001/XMLSchema}gYear"/>
 *         &lt;element name="ImportoTotale" type="{http://idp.tasgroup.it/xmlbillerservices/Common}Importo"/>
 *         &lt;element name="Divisa" type="{http://idp.tasgroup.it/xmlbillerservices/Common}Divisa"/>
 *         &lt;element name="InfoPagamento">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="CondizionePagamento" type="{http://idp.tasgroup.it/xmlbillerservices/Pendenze}CondizionePagamento" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="TipoDebito" use="required" type="{http://idp.tasgroup.it/xmlbillerservices/Common}Max50Text" />
 *       &lt;attribute name="TipoOperazione" use="required" type="{http://idp.tasgroup.it/xmlbillerservices/Common}TipoOperazione" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Pendenza", propOrder = {
    "idDebito",
    "datiMBD",
    "mittente",
    "debitori",
    "riferimentoDebito",
    "noteDebito",
    "causaleDebito",
    "dataCreazione",
    "dataEmissione",
    "annoRiferimento",
    "importoTotale",
    "divisa",
    "infoPagamento"
})
public class Pendenza {

    @XmlElement(name = "IdDebito", required = true)
    protected String idDebito;
    @XmlElement(name = "DatiMBD")
    protected DatiMBD datiMBD;
    @XmlElement(name = "Mittente", required = true)
    protected Mittente mittente;
    @XmlElement(name = "Debitori", required = true)
    protected Debitori debitori;
    @XmlElement(name = "RiferimentoDebito")
    protected String riferimentoDebito;
    @XmlElement(name = "NoteDebito")
    protected String noteDebito;
    @XmlElement(name = "CausaleDebito", required = true)
    protected String causaleDebito;
    @XmlElement(name = "DataCreazione", required = true)
    protected XMLGregorianCalendar dataCreazione;
    @XmlElement(name = "DataEmissione", required = true)
    protected XMLGregorianCalendar dataEmissione;
    @XmlElement(name = "AnnoRiferimento", required = true)
    @XmlSchemaType(name = "gYear")
    protected XMLGregorianCalendar annoRiferimento;
    @XmlElement(name = "ImportoTotale", required = true)
    protected BigDecimal importoTotale;
    @XmlElement(name = "Divisa", required = true)
    protected Divisa divisa;
    @XmlElement(name = "InfoPagamento", required = true)
    protected Pendenza.InfoPagamento infoPagamento;
    @XmlAttribute(name = "TipoDebito", required = true)
    protected String tipoDebito;
    @XmlAttribute(name = "TipoOperazione", required = true)
    protected TipoOperazione tipoOperazione;

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
     * Gets the value of the datiMBD property.
     * 
     * @return
     *     possible object is
     *     {@link DatiMBD }
     *     
     */
    public DatiMBD getDatiMBD() {
        return datiMBD;
    }

    /**
     * Sets the value of the datiMBD property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiMBD }
     *     
     */
    public void setDatiMBD(DatiMBD value) {
        this.datiMBD = value;
    }

    /**
     * Gets the value of the mittente property.
     * 
     * @return
     *     possible object is
     *     {@link Mittente }
     *     
     */
    public Mittente getMittente() {
        return mittente;
    }

    /**
     * Sets the value of the mittente property.
     * 
     * @param value
     *     allowed object is
     *     {@link Mittente }
     *     
     */
    public void setMittente(Mittente value) {
        this.mittente = value;
    }

    /**
     * Gets the value of the debitori property.
     * 
     * @return
     *     possible object is
     *     {@link Debitori }
     *     
     */
    public Debitori getDebitori() {
        return debitori;
    }

    /**
     * Sets the value of the debitori property.
     * 
     * @param value
     *     allowed object is
     *     {@link Debitori }
     *     
     */
    public void setDebitori(Debitori value) {
        this.debitori = value;
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
     * Gets the value of the dataCreazione property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataCreazione() {
        return dataCreazione;
    }

    /**
     * Sets the value of the dataCreazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataCreazione(XMLGregorianCalendar value) {
        this.dataCreazione = value;
    }

    /**
     * Gets the value of the dataEmissione property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataEmissione() {
        return dataEmissione;
    }

    /**
     * Sets the value of the dataEmissione property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataEmissione(XMLGregorianCalendar value) {
        this.dataEmissione = value;
    }

    /**
     * Gets the value of the annoRiferimento property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAnnoRiferimento() {
        return annoRiferimento;
    }

    /**
     * Sets the value of the annoRiferimento property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAnnoRiferimento(XMLGregorianCalendar value) {
        this.annoRiferimento = value;
    }

    /**
     * Gets the value of the importoTotale property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImportoTotale() {
        return importoTotale;
    }

    /**
     * Sets the value of the importoTotale property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImportoTotale(BigDecimal value) {
        this.importoTotale = value;
    }

    /**
     * Gets the value of the divisa property.
     * 
     * @return
     *     possible object is
     *     {@link Divisa }
     *     
     */
    public Divisa getDivisa() {
        return divisa;
    }

    /**
     * Sets the value of the divisa property.
     * 
     * @param value
     *     allowed object is
     *     {@link Divisa }
     *     
     */
    public void setDivisa(Divisa value) {
        this.divisa = value;
    }

    /**
     * Gets the value of the infoPagamento property.
     * 
     * @return
     *     possible object is
     *     {@link Pendenza.InfoPagamento }
     *     
     */
    public Pendenza.InfoPagamento getInfoPagamento() {
        return infoPagamento;
    }

    /**
     * Sets the value of the infoPagamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link Pendenza.InfoPagamento }
     *     
     */
    public void setInfoPagamento(Pendenza.InfoPagamento value) {
        this.infoPagamento = value;
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
     * Gets the value of the tipoOperazione property.
     * 
     * @return
     *     possible object is
     *     {@link TipoOperazione }
     *     
     */
    public TipoOperazione getTipoOperazione() {
        return tipoOperazione;
    }

    /**
     * Sets the value of the tipoOperazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoOperazione }
     *     
     */
    public void setTipoOperazione(TipoOperazione value) {
        this.tipoOperazione = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="CondizionePagamento" type="{http://idp.tasgroup.it/xmlbillerservices/Pendenze}CondizionePagamento" maxOccurs="unbounded"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "condizionePagamento"
    })
    public static class InfoPagamento {

        @XmlElement(name = "CondizionePagamento", required = true)
        protected List<CondizionePagamento> condizionePagamento;

        /**
         * Gets the value of the condizionePagamento property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the condizionePagamento property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getCondizionePagamento().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link CondizionePagamento }
         * 
         * 
         */
        public List<CondizionePagamento> getCondizionePagamento() {
            if (condizionePagamento == null) {
                condizionePagamento = new ArrayList<CondizionePagamento>();
            }
            return this.condizionePagamento;
        }

    }

}
