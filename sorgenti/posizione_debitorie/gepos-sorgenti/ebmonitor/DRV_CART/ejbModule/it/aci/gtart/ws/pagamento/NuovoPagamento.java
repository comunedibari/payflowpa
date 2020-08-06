
package it.aci.gtart.ws.pagamento;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for nuovoPagamento complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="nuovoPagamento">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="tipoIntermediario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IDIntermediario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MSGID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="idBollo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IUV" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TipoVeicolo" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="Targa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="decorrenza" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mesiValidita" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="dataPagamento" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="tassa" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="sanzioni" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="interessi" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="totale" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="RegBeneficiaria" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="RegVersamento" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="codiceFiscale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ModPagamento" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="Note" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "nuovoPagamento", propOrder = {
    "tipoIntermediario",
    "idIntermediario",
    "msgid",
    "idBollo",
    "iuv",
    "tipoVeicolo",
    "targa",
    "decorrenza",
    "mesiValidita",
    "dataPagamento",
    "tassa",
    "sanzioni",
    "interessi",
    "totale",
    "regBeneficiaria",
    "regVersamento",
    "codiceFiscale",
    "modPagamento",
    "note"
})
public class NuovoPagamento
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected String tipoIntermediario;
    @XmlElement(name = "IDIntermediario")
    protected String idIntermediario;
    @XmlElement(name = "MSGID")
    protected String msgid;
    protected String idBollo;
    @XmlElement(name = "IUV")
    protected String iuv;
    @XmlElement(name = "TipoVeicolo")
    protected Integer tipoVeicolo;
    @XmlElement(name = "Targa")
    protected String targa;
    protected String decorrenza;
    protected Integer mesiValidita;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataPagamento;
    protected BigDecimal tassa;
    protected BigDecimal sanzioni;
    protected BigDecimal interessi;
    protected BigDecimal totale;
    @XmlElement(name = "RegBeneficiaria")
    protected Integer regBeneficiaria;
    @XmlElement(name = "RegVersamento")
    protected Integer regVersamento;
    protected String codiceFiscale;
    @XmlElement(name = "ModPagamento")
    protected Integer modPagamento;
    @XmlElement(name = "Note")
    protected String note;

    /**
     * Gets the value of the tipoIntermediario property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoIntermediario() {
        return tipoIntermediario;
    }

    /**
     * Sets the value of the tipoIntermediario property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoIntermediario(String value) {
        this.tipoIntermediario = value;
    }

    /**
     * Gets the value of the idIntermediario property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDIntermediario() {
        return idIntermediario;
    }

    /**
     * Sets the value of the idIntermediario property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDIntermediario(String value) {
        this.idIntermediario = value;
    }

    /**
     * Gets the value of the msgid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMSGID() {
        return msgid;
    }

    /**
     * Sets the value of the msgid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMSGID(String value) {
        this.msgid = value;
    }

    /**
     * Gets the value of the idBollo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdBollo() {
        return idBollo;
    }

    /**
     * Sets the value of the idBollo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdBollo(String value) {
        this.idBollo = value;
    }

    /**
     * Gets the value of the iuv property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIUV() {
        return iuv;
    }

    /**
     * Sets the value of the iuv property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIUV(String value) {
        this.iuv = value;
    }

    /**
     * Gets the value of the tipoVeicolo property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTipoVeicolo() {
        return tipoVeicolo;
    }

    /**
     * Sets the value of the tipoVeicolo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTipoVeicolo(Integer value) {
        this.tipoVeicolo = value;
    }

    /**
     * Gets the value of the targa property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTarga() {
        return targa;
    }

    /**
     * Sets the value of the targa property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTarga(String value) {
        this.targa = value;
    }

    /**
     * Gets the value of the decorrenza property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDecorrenza() {
        return decorrenza;
    }

    /**
     * Sets the value of the decorrenza property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDecorrenza(String value) {
        this.decorrenza = value;
    }

    /**
     * Gets the value of the mesiValidita property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMesiValidita() {
        return mesiValidita;
    }

    /**
     * Sets the value of the mesiValidita property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMesiValidita(Integer value) {
        this.mesiValidita = value;
    }

    /**
     * Gets the value of the dataPagamento property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataPagamento() {
        return dataPagamento;
    }

    /**
     * Sets the value of the dataPagamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataPagamento(XMLGregorianCalendar value) {
        this.dataPagamento = value;
    }

    /**
     * Gets the value of the tassa property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTassa() {
        return tassa;
    }

    /**
     * Sets the value of the tassa property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTassa(BigDecimal value) {
        this.tassa = value;
    }

    /**
     * Gets the value of the sanzioni property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSanzioni() {
        return sanzioni;
    }

    /**
     * Sets the value of the sanzioni property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSanzioni(BigDecimal value) {
        this.sanzioni = value;
    }

    /**
     * Gets the value of the interessi property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getInteressi() {
        return interessi;
    }

    /**
     * Sets the value of the interessi property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setInteressi(BigDecimal value) {
        this.interessi = value;
    }

    /**
     * Gets the value of the totale property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotale() {
        return totale;
    }

    /**
     * Sets the value of the totale property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotale(BigDecimal value) {
        this.totale = value;
    }

    /**
     * Gets the value of the regBeneficiaria property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getRegBeneficiaria() {
        return regBeneficiaria;
    }

    /**
     * Sets the value of the regBeneficiaria property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setRegBeneficiaria(Integer value) {
        this.regBeneficiaria = value;
    }

    /**
     * Gets the value of the regVersamento property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getRegVersamento() {
        return regVersamento;
    }

    /**
     * Sets the value of the regVersamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setRegVersamento(Integer value) {
        this.regVersamento = value;
    }

    /**
     * Gets the value of the codiceFiscale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    /**
     * Sets the value of the codiceFiscale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceFiscale(String value) {
        this.codiceFiscale = value;
    }

    /**
     * Gets the value of the modPagamento property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getModPagamento() {
        return modPagamento;
    }

    /**
     * Sets the value of the modPagamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setModPagamento(Integer value) {
        this.modPagamento = value;
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

}
