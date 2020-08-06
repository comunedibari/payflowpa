
package it.toscana.rete.cart.servizi.iris_1_1.idpallineamentopendenze;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for DettaglioTransazione complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DettaglioTransazione">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DataPagamento" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}IdPDateTime"/>
 *         &lt;element name="CanalePagamento" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max35Text"/>
 *         &lt;element name="MezzoPagamento" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max35Text" minOccurs="0"/>
 *         &lt;element name="ImportoPagamento" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Importo" minOccurs="0"/>
 *         &lt;element name="NotePagamento" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max256Text" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DettaglioTransazione", propOrder = {
    "dataPagamento",
    "canalePagamento",
    "mezzoPagamento",
    "importoPagamento",
    "notePagamento"
})
public class DettaglioTransazione {

    @XmlElement(name = "DataPagamento", required = true)
    protected XMLGregorianCalendar dataPagamento;
    @XmlElement(name = "CanalePagamento", required = true)
    protected String canalePagamento;
    @XmlElement(name = "MezzoPagamento")
    protected String mezzoPagamento;
    @XmlElement(name = "ImportoPagamento")
    protected BigDecimal importoPagamento;
    @XmlElement(name = "NotePagamento")
    protected String notePagamento;

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
     * Gets the value of the canalePagamento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCanalePagamento() {
        return canalePagamento;
    }

    /**
     * Sets the value of the canalePagamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCanalePagamento(String value) {
        this.canalePagamento = value;
    }

    /**
     * Gets the value of the mezzoPagamento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMezzoPagamento() {
        return mezzoPagamento;
    }

    /**
     * Sets the value of the mezzoPagamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMezzoPagamento(String value) {
        this.mezzoPagamento = value;
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

}
