
package it.tasgroup.idp.xmlbillerservices.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CoordinateBancarie complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CoordinateBancarie">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CodiceIBAN" type="{http://idp.tasgroup.it/xmlbillerservices/Common}IBANIdentifier"/>
 *         &lt;element name="Beneficiario" type="{http://idp.tasgroup.it/xmlbillerservices/Common}Max70Text"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CoordinateBancarie", propOrder = {
    "codiceIBAN",
    "beneficiario"
})
public class CoordinateBancarie {

    @XmlElement(name = "CodiceIBAN", required = true)
    protected String codiceIBAN;
    @XmlElement(name = "Beneficiario", required = true)
    protected String beneficiario;

    /**
     * Gets the value of the codiceIBAN property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceIBAN() {
        return codiceIBAN;
    }

    /**
     * Sets the value of the codiceIBAN property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceIBAN(String value) {
        this.codiceIBAN = value;
    }

    /**
     * Gets the value of the beneficiario property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBeneficiario() {
        return beneficiario;
    }

    /**
     * Sets the value of the beneficiario property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBeneficiario(String value) {
        this.beneficiario = value;
    }

}
