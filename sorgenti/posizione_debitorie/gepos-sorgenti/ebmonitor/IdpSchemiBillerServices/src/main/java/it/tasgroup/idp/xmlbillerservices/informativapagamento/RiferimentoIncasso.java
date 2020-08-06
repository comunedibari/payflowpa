
package it.tasgroup.idp.xmlbillerservices.informativapagamento;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for RiferimentoIncasso complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RiferimentoIncasso">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IdentificativoPSP" type="{http://idp.tasgroup.it/xmlbillerservices/Common}Max35Text" minOccurs="0"/>
 *         &lt;element name="IdentificativoUnivocoRegolamento" type="{http://idp.tasgroup.it/xmlbillerservices/Common}Max35Text" minOccurs="0"/>
 *         &lt;element name="DataRegolamento" type="{http://idp.tasgroup.it/xmlbillerservices/Common}IdPDate" minOccurs="0"/>
 *         &lt;element name="TotaleTransazione" type="{http://idp.tasgroup.it/xmlbillerservices/Common}Importo" minOccurs="0"/>
 *         &lt;element name="IdentificativoFlusso" type="{http://idp.tasgroup.it/xmlbillerservices/Common}Max35Text" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RiferimentoIncasso", propOrder = {
    "identificativoPSP",
    "identificativoUnivocoRegolamento",
    "dataRegolamento",
    "totaleTransazione",
    "identificativoFlusso"
})
public class RiferimentoIncasso {

    @XmlElement(name = "IdentificativoPSP")
    protected String identificativoPSP;
    @XmlElement(name = "IdentificativoUnivocoRegolamento")
    protected String identificativoUnivocoRegolamento;
    @XmlElement(name = "DataRegolamento")
    protected XMLGregorianCalendar dataRegolamento;
    @XmlElement(name = "TotaleTransazione")
    protected BigDecimal totaleTransazione;
    @XmlElement(name = "IdentificativoFlusso")
    protected String identificativoFlusso;

    /**
     * Gets the value of the identificativoPSP property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativoPSP() {
        return identificativoPSP;
    }

    /**
     * Sets the value of the identificativoPSP property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativoPSP(String value) {
        this.identificativoPSP = value;
    }

    /**
     * Gets the value of the identificativoUnivocoRegolamento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativoUnivocoRegolamento() {
        return identificativoUnivocoRegolamento;
    }

    /**
     * Sets the value of the identificativoUnivocoRegolamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativoUnivocoRegolamento(String value) {
        this.identificativoUnivocoRegolamento = value;
    }

    /**
     * Gets the value of the dataRegolamento property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataRegolamento() {
        return dataRegolamento;
    }

    /**
     * Sets the value of the dataRegolamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataRegolamento(XMLGregorianCalendar value) {
        this.dataRegolamento = value;
    }

    /**
     * Gets the value of the totaleTransazione property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotaleTransazione() {
        return totaleTransazione;
    }

    /**
     * Sets the value of the totaleTransazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotaleTransazione(BigDecimal value) {
        this.totaleTransazione = value;
    }

    /**
     * Gets the value of the identificativoFlusso property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativoFlusso() {
        return identificativoFlusso;
    }

    /**
     * Sets the value of the identificativoFlusso property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativoFlusso(String value) {
        this.identificativoFlusso = value;
    }

}
