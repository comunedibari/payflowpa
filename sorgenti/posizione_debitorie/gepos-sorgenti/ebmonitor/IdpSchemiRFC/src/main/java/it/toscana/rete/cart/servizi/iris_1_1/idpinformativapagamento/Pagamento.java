
package it.toscana.rete.cart.servizi.iris_1_1.idpinformativapagamento;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import it.toscana.rete.cart.servizi.iris_1_1.idpinclude.Allegato;


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
 *         &lt;element name="RiferimentoPagamento" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInformativaPagamento}RiferimentoPagamento"/>
 *         &lt;element name="DataOraPagamento" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}IdPDateTime"/>
 *         &lt;element name="DataScadenzaPagamento" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}IdPDateTime" minOccurs="0"/>
 *         &lt;element name="Importo" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Importo"/>
 *         &lt;element name="RiferimentoDebitore" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}IdentificativoIdp" minOccurs="0"/>
 *         &lt;element name="Esito" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInformativaPagamento}TipoNotifica"/>
 *         &lt;element name="Pagante" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInformativaPagamento}Pagante" minOccurs="0"/>
 *         &lt;element name="Transazione" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInformativaPagamento}Transazione" minOccurs="0"/>
 *         &lt;element name="FlagQuietanzaCartacea" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="Note" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max2000Text" minOccurs="0"/>
 *         &lt;element name="Allegato" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Allegato" minOccurs="0"/>
 *         &lt;element name="RiferimentoDebito" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInformativaPagamento}RiferimentoDebito" minOccurs="0"/>
 *         &lt;element name="DescrizioneCausale" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max256Text" minOccurs="0"/>
 *         &lt;element name="IdentificativoUnivocoVersamento" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}IdentificativoIdp" minOccurs="0"/>
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
    "riferimentoPagamento",
    "dataOraPagamento",
    "dataScadenzaPagamento",
    "importo",
    "riferimentoDebitore",
    "esito",
    "pagante",
    "transazione",
    "flagQuietanzaCartacea",
    "note",
    "allegato",
    "riferimentoDebito",
    "descrizioneCausale",
    "identificativoUnivocoVersamento"
})
public class Pagamento {

    @XmlElement(name = "RiferimentoPagamento", required = true)
    protected RiferimentoPagamento riferimentoPagamento;
    @XmlElement(name = "DataOraPagamento", required = true)
    protected XMLGregorianCalendar dataOraPagamento;
    @XmlElement(name = "DataScadenzaPagamento")
    protected XMLGregorianCalendar dataScadenzaPagamento;
    @XmlElement(name = "Importo", required = true)
    protected BigDecimal importo;
    @XmlElement(name = "RiferimentoDebitore")
    protected String riferimentoDebitore;
    @XmlElement(name = "Esito", required = true)
    protected TipoNotifica esito;
    @XmlElement(name = "Pagante")
    protected Pagante pagante;
    @XmlElement(name = "Transazione")
    protected Transazione transazione;
    @XmlElement(name = "FlagQuietanzaCartacea")
    protected Boolean flagQuietanzaCartacea;
    @XmlElement(name = "Note")
    protected String note;
    @XmlElement(name = "Allegato")
    protected Allegato allegato;
    @XmlElement(name = "RiferimentoDebito")
    protected RiferimentoDebito riferimentoDebito;
    @XmlElement(name = "DescrizioneCausale")
    protected String descrizioneCausale;
    @XmlElement(name = "IdentificativoUnivocoVersamento")
    protected String identificativoUnivocoVersamento;

    /**
     * Gets the value of the riferimentoPagamento property.
     * 
     * @return
     *     possible object is
     *     {@link RiferimentoPagamento }
     *     
     */
    public RiferimentoPagamento getRiferimentoPagamento() {
        return riferimentoPagamento;
    }

    /**
     * Sets the value of the riferimentoPagamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link RiferimentoPagamento }
     *     
     */
    public void setRiferimentoPagamento(RiferimentoPagamento value) {
        this.riferimentoPagamento = value;
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
     * Gets the value of the riferimentoDebitore property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRiferimentoDebitore() {
        return riferimentoDebitore;
    }

    /**
     * Sets the value of the riferimentoDebitore property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRiferimentoDebitore(String value) {
        this.riferimentoDebitore = value;
    }

    /**
     * Gets the value of the esito property.
     * 
     * @return
     *     possible object is
     *     {@link TipoNotifica }
     *     
     */
    public TipoNotifica getEsito() {
        return esito;
    }

    /**
     * Sets the value of the esito property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoNotifica }
     *     
     */
    public void setEsito(TipoNotifica value) {
        this.esito = value;
    }

    /**
     * Gets the value of the pagante property.
     * 
     * @return
     *     possible object is
     *     {@link Pagante }
     *     
     */
    public Pagante getPagante() {
        return pagante;
    }

    /**
     * Sets the value of the pagante property.
     * 
     * @param value
     *     allowed object is
     *     {@link Pagante }
     *     
     */
    public void setPagante(Pagante value) {
        this.pagante = value;
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
     * Gets the value of the flagQuietanzaCartacea property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isFlagQuietanzaCartacea() {
        return flagQuietanzaCartacea;
    }

    /**
     * Sets the value of the flagQuietanzaCartacea property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setFlagQuietanzaCartacea(Boolean value) {
        this.flagQuietanzaCartacea = value;
    }

    /**
     * Gets the value of the note property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNote() {
        return note;
    }

    /**
     * Sets the value of the note property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNote(String value) {
        this.note = value;
    }

    /**
     * Gets the value of the allegato property.
     * 
     * @return
     *     possible object is
     *     {@link Allegato }
     *     
     */
    public Allegato getAllegato() {
        return allegato;
    }

    /**
     * Sets the value of the allegato property.
     * 
     * @param value
     *     allowed object is
     *     {@link Allegato }
     *     
     */
    public void setAllegato(Allegato value) {
        this.allegato = value;
    }

    /**
     * Gets the value of the riferimentoDebito property.
     * 
     * @return
     *     possible object is
     *     {@link RiferimentoDebito }
     *     
     */
    public RiferimentoDebito getRiferimentoDebito() {
        return riferimentoDebito;
    }

    /**
     * Sets the value of the riferimentoDebito property.
     * 
     * @param value
     *     allowed object is
     *     {@link RiferimentoDebito }
     *     
     */
    public void setRiferimentoDebito(RiferimentoDebito value) {
        this.riferimentoDebito = value;
    }

    /**
     * Gets the value of the descrizioneCausale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneCausale() {
        return descrizioneCausale;
    }

    /**
     * Sets the value of the descrizioneCausale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneCausale(String value) {
        this.descrizioneCausale = value;
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

}
