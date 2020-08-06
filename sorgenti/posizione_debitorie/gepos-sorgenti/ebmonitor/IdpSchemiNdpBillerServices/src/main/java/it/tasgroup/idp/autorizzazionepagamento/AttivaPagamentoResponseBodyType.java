
package it.tasgroup.idp.autorizzazionepagamento;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AttivaPagamentoResponseBodyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AttivaPagamentoResponseBodyType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://idp.tasgroup.it/AutorizzazionePagamento/}RequestBase">
 *       &lt;sequence>
 *         &lt;element name="ImportoPagamento" type="{http://idp.tasgroup.it/AutorizzazionePagamento/}Importo12.2"/>
 *         &lt;element name="TipoIdentificativoUnivocoDebitore" type="{http://idp.tasgroup.it/AutorizzazionePagamento/}IdentificativoUnivocoDebitoreType"/>
 *         &lt;element name="CodiceIdentificativoUnivocoDebitore" type="{http://idp.tasgroup.it/AutorizzazionePagamento/}stText35"/>
 *         &lt;element name="AnagraficaDebitore" type="{http://idp.tasgroup.it/AutorizzazionePagamento/}stText70"/>
 *         &lt;element name="CausaleVersamento" type="{http://idp.tasgroup.it/AutorizzazionePagamento/}stText140"/>
 *         &lt;element name="TipoDebito" type="{http://idp.tasgroup.it/AutorizzazionePagamento/}stText50"/>
 *         &lt;element name="IdDebito" type="{http://idp.tasgroup.it/AutorizzazionePagamento/}stText50"/>
 *         &lt;element name="AnnoRiferimento" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="IdMittente" type="{http://idp.tasgroup.it/AutorizzazionePagamento/}stText35"/>
 *         &lt;element name="DescrizioneMittente" type="{http://idp.tasgroup.it/AutorizzazionePagamento/}stText70" minOccurs="0"/>
 *         &lt;element name="IdRiscossore" type="{http://idp.tasgroup.it/AutorizzazionePagamento/}stText35" minOccurs="0"/>
 *         &lt;element name="RiferimentoRiscossore" type="{http://idp.tasgroup.it/AutorizzazionePagamento/}stText35" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AttivaPagamentoResponseBodyType", propOrder = {
    "importoPagamento",
    "tipoIdentificativoUnivocoDebitore",
    "codiceIdentificativoUnivocoDebitore",
    "anagraficaDebitore",
    "causaleVersamento",
    "tipoDebito",
    "idDebito",
    "annoRiferimento",
    "idMittente",
    "descrizioneMittente",
    "idRiscossore",
    "riferimentoRiscossore"
})
public class AttivaPagamentoResponseBodyType
    extends RequestBase
{

    @XmlElement(name = "ImportoPagamento", required = true)
    protected BigDecimal importoPagamento;
    @XmlElement(name = "TipoIdentificativoUnivocoDebitore", required = true)
    protected IdentificativoUnivocoDebitoreType tipoIdentificativoUnivocoDebitore;
    @XmlElement(name = "CodiceIdentificativoUnivocoDebitore", required = true)
    protected String codiceIdentificativoUnivocoDebitore;
    @XmlElement(name = "AnagraficaDebitore", required = true)
    protected String anagraficaDebitore;
    @XmlElement(name = "CausaleVersamento", required = true)
    protected String causaleVersamento;
    @XmlElement(name = "TipoDebito", required = true)
    protected String tipoDebito;
    @XmlElement(name = "IdDebito", required = true)
    protected String idDebito;
    @XmlElement(name = "AnnoRiferimento", required = true)
    protected String annoRiferimento;
    @XmlElement(name = "IdMittente", required = true)
    protected String idMittente;
    @XmlElement(name = "DescrizioneMittente")
    protected String descrizioneMittente;
    @XmlElement(name = "IdRiscossore")
    protected String idRiscossore;
    @XmlElement(name = "RiferimentoRiscossore")
    protected String riferimentoRiscossore;

    /**
     * Gets the value of the importoPagamento property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImportoPagamento() {
        return importoPagamento;
    }

    /**
     * Sets the value of the importoPagamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImportoPagamento(BigDecimal value) {
        this.importoPagamento = value;
    }

    /**
     * Gets the value of the tipoIdentificativoUnivocoDebitore property.
     * 
     * @return
     *     possible object is
     *     {@link IdentificativoUnivocoDebitoreType }
     *     
     */
    public IdentificativoUnivocoDebitoreType getTipoIdentificativoUnivocoDebitore() {
        return tipoIdentificativoUnivocoDebitore;
    }

    /**
     * Sets the value of the tipoIdentificativoUnivocoDebitore property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdentificativoUnivocoDebitoreType }
     *     
     */
    public void setTipoIdentificativoUnivocoDebitore(IdentificativoUnivocoDebitoreType value) {
        this.tipoIdentificativoUnivocoDebitore = value;
    }

    /**
     * Gets the value of the codiceIdentificativoUnivocoDebitore property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceIdentificativoUnivocoDebitore() {
        return codiceIdentificativoUnivocoDebitore;
    }

    /**
     * Sets the value of the codiceIdentificativoUnivocoDebitore property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceIdentificativoUnivocoDebitore(String value) {
        this.codiceIdentificativoUnivocoDebitore = value;
    }

    /**
     * Gets the value of the anagraficaDebitore property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnagraficaDebitore() {
        return anagraficaDebitore;
    }

    /**
     * Sets the value of the anagraficaDebitore property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnagraficaDebitore(String value) {
        this.anagraficaDebitore = value;
    }

    /**
     * Gets the value of the causaleVersamento property.
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
     * Sets the value of the causaleVersamento property.
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
     * Gets the value of the annoRiferimento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnnoRiferimento() {
        return annoRiferimento;
    }

    /**
     * Sets the value of the annoRiferimento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnnoRiferimento(String value) {
        this.annoRiferimento = value;
    }

    /**
     * Gets the value of the idMittente property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdMittente() {
        return idMittente;
    }

    /**
     * Sets the value of the idMittente property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdMittente(String value) {
        this.idMittente = value;
    }

    /**
     * Gets the value of the descrizioneMittente property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneMittente() {
        return descrizioneMittente;
    }

    /**
     * Sets the value of the descrizioneMittente property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneMittente(String value) {
        this.descrizioneMittente = value;
    }

    /**
     * Gets the value of the idRiscossore property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdRiscossore() {
        return idRiscossore;
    }

    /**
     * Sets the value of the idRiscossore property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdRiscossore(String value) {
        this.idRiscossore = value;
    }

    /**
     * Gets the value of the riferimentoRiscossore property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRiferimentoRiscossore() {
        return riferimentoRiscossore;
    }

    /**
     * Sets the value of the riferimentoRiscossore property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRiferimentoRiscossore(String value) {
        this.riferimentoRiscossore = value;
    }

}
