
package it.tasgroup.idp.xmlbillerservices.informativapagamento;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for Transazione complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Transazione">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CanalePagamento" type="{http://idp.tasgroup.it/xmlbillerservices/InformativaPagamento}CanalePagamento"/>
 *         &lt;element name="IdentificativoUnivocoRiscossione" type="{http://idp.tasgroup.it/xmlbillerservices/Common}Max35Text"/>
 *         &lt;element name="DataOraTransazione" type="{http://idp.tasgroup.it/xmlbillerservices/Common}IdPDateTime"/>
 *         &lt;element name="Importo" type="{http://idp.tasgroup.it/xmlbillerservices/Common}Importo"/>
 *         &lt;element name="ImportoCommissioni" type="{http://idp.tasgroup.it/xmlbillerservices/Common}Importo" minOccurs="0"/>
 *         &lt;element name="NumeroPagamenti" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Transazione", propOrder = {
    "canalePagamento",
    "identificativoUnivocoRiscossione",
    "dataOraTransazione",
    "importo",
    "importoCommissioni",
    "numeroPagamenti"
})
public class Transazione {

    @XmlElement(name = "CanalePagamento", required = true)
    protected CanalePagamento canalePagamento;
    @XmlElement(name = "IdentificativoUnivocoRiscossione", required = true)
    protected String identificativoUnivocoRiscossione;
    @XmlElement(name = "DataOraTransazione", required = true)
    protected XMLGregorianCalendar dataOraTransazione;
    @XmlElement(name = "Importo", required = true)
    protected BigDecimal importo;
    @XmlElement(name = "ImportoCommissioni")
    protected BigDecimal importoCommissioni;
    @XmlElement(name = "NumeroPagamenti")
    protected long numeroPagamenti;

    /**
     * Gets the value of the canalePagamento property.
     * 
     * @return
     *     possible object is
     *     {@link CanalePagamento }
     *     
     */
    public CanalePagamento getCanalePagamento() {
        return canalePagamento;
    }

    /**
     * Sets the value of the canalePagamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link CanalePagamento }
     *     
     */
    public void setCanalePagamento(CanalePagamento value) {
        this.canalePagamento = value;
    }

    /**
     * Gets the value of the identificativoUnivocoRiscossione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativoUnivocoRiscossione() {
        return identificativoUnivocoRiscossione;
    }

    /**
     * Sets the value of the identificativoUnivocoRiscossione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativoUnivocoRiscossione(String value) {
        this.identificativoUnivocoRiscossione = value;
    }

    /**
     * Gets the value of the dataOraTransazione property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataOraTransazione() {
        return dataOraTransazione;
    }

    /**
     * Sets the value of the dataOraTransazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataOraTransazione(XMLGregorianCalendar value) {
        this.dataOraTransazione = value;
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
     * Gets the value of the importoCommissioni property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImportoCommissioni() {
        return importoCommissioni;
    }

    /**
     * Sets the value of the importoCommissioni property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImportoCommissioni(BigDecimal value) {
        this.importoCommissioni = value;
    }

    /**
     * Gets the value of the numeroPagamenti property.
     * 
     */
    public long getNumeroPagamenti() {
        return numeroPagamenti;
    }

    /**
     * Sets the value of the numeroPagamenti property.
     * 
     */
    public void setNumeroPagamenti(long value) {
        this.numeroPagamenti = value;
    }

}
