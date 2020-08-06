
package it.tasgroup.idp.xmlbillerservices.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DatiMBD complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DatiMBD">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ProvinciaResidenzaDebitore" type="{http://idp.tasgroup.it/xmlbillerservices/Common}stProvincia"/>
 *         &lt;element name="TipoBollo" type="{http://idp.tasgroup.it/xmlbillerservices/Common}Max2Text"/>
 *         &lt;element name="HashDocumento" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatiMBD", propOrder = {
    "provinciaResidenzaDebitore",
    "tipoBollo",
    "hashDocumento"
})
public class DatiMBD {

    @XmlElement(name = "ProvinciaResidenzaDebitore", required = true)
    protected String provinciaResidenzaDebitore;
    @XmlElement(name = "TipoBollo", required = true)
    protected String tipoBollo;
    @XmlElement(name = "HashDocumento", required = true)
    protected byte[] hashDocumento;

    /**
     * Gets the value of the provinciaResidenzaDebitore property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProvinciaResidenzaDebitore() {
        return provinciaResidenzaDebitore;
    }

    /**
     * Sets the value of the provinciaResidenzaDebitore property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProvinciaResidenzaDebitore(String value) {
        this.provinciaResidenzaDebitore = value;
    }

    /**
     * Gets the value of the tipoBollo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoBollo() {
        return tipoBollo;
    }

    /**
     * Sets the value of the tipoBollo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoBollo(String value) {
        this.tipoBollo = value;
    }

    /**
     * Gets the value of the hashDocumento property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getHashDocumento() {
        return hashDocumento;
    }

    /**
     * Sets the value of the hashDocumento property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setHashDocumento(byte[] value) {
        this.hashDocumento = ((byte[]) value);
    }

}
