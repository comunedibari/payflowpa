
package it.tasgroup.idp.xmlbillerservices.pendenze;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for CondizionePagamento complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CondizionePagamento">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IdPagamento" type="{http://idp.tasgroup.it/xmlbillerservices/Common}IdentificativoIdp"/>
 *         &lt;element name="DataScadenza" type="{http://idp.tasgroup.it/xmlbillerservices/Common}IdPDate"/>
 *         &lt;element name="DataInizioValidita" type="{http://idp.tasgroup.it/xmlbillerservices/Common}IdPDate"/>
 *         &lt;element name="DataFineValidita" type="{http://idp.tasgroup.it/xmlbillerservices/Common}IdPDate"/>
 *         &lt;element name="ImportoPagamento" type="{http://idp.tasgroup.it/xmlbillerservices/Common}Importo"/>
 *         &lt;element name="CausalePagamento" type="{http://idp.tasgroup.it/xmlbillerservices/Common}Max256Text" minOccurs="0"/>
 *         &lt;element name="VociPagamento" type="{http://idp.tasgroup.it/xmlbillerservices/Pendenze}VociPagamento" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CondizionePagamento", propOrder = {
    "idPagamento",
    "dataScadenza",
    "dataInizioValidita",
    "dataFineValidita",
    "importoPagamento",
    "causalePagamento",
    "vociPagamento"
})
public class CondizionePagamento {

    @XmlElement(name = "IdPagamento", required = true)
    protected String idPagamento;
    @XmlElement(name = "DataScadenza", required = true)
    protected XMLGregorianCalendar dataScadenza;
    @XmlElement(name = "DataInizioValidita", required = true)
    protected XMLGregorianCalendar dataInizioValidita;
    @XmlElement(name = "DataFineValidita", required = true)
    protected XMLGregorianCalendar dataFineValidita;
    @XmlElement(name = "ImportoPagamento", required = true)
    protected BigDecimal importoPagamento;
    @XmlElement(name = "CausalePagamento")
    protected String causalePagamento;
    @XmlElement(name = "VociPagamento")
    protected VociPagamento vociPagamento;

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
     * Gets the value of the dataScadenza property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataScadenza() {
        return dataScadenza;
    }

    /**
     * Sets the value of the dataScadenza property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataScadenza(XMLGregorianCalendar value) {
        this.dataScadenza = value;
    }

    /**
     * Gets the value of the dataInizioValidita property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataInizioValidita() {
        return dataInizioValidita;
    }

    /**
     * Sets the value of the dataInizioValidita property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataInizioValidita(XMLGregorianCalendar value) {
        this.dataInizioValidita = value;
    }

    /**
     * Gets the value of the dataFineValidita property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataFineValidita() {
        return dataFineValidita;
    }

    /**
     * Sets the value of the dataFineValidita property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataFineValidita(XMLGregorianCalendar value) {
        this.dataFineValidita = value;
    }

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
     * Gets the value of the vociPagamento property.
     * 
     * @return
     *     possible object is
     *     {@link VociPagamento }
     *     
     */
    public VociPagamento getVociPagamento() {
        return vociPagamento;
    }

    /**
     * Sets the value of the vociPagamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link VociPagamento }
     *     
     */
    public void setVociPagamento(VociPagamento value) {
        this.vociPagamento = value;
    }

}
