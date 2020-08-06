
package it.tasgroup.idp.generazioneiuv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ElencoIdentificativiType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ElencoIdentificativiType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IdentificativoUnivocoVersamento" type="{http://idp.tasgroup.it/GenerazioneIUV/}stText35"/>
 *         &lt;element name="NumeroAvviso" type="{http://idp.tasgroup.it/GenerazioneIUV/}stText18"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ElencoIdentificativiType", propOrder = {
    "identificativoUnivocoVersamento",
    "numeroAvviso"
})
public class ElencoIdentificativiType {

    @XmlElement(name = "IdentificativoUnivocoVersamento", required = true)
    protected String identificativoUnivocoVersamento;
    @XmlElement(name = "NumeroAvviso", required = true)
    protected String numeroAvviso;

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

    /**
     * Gets the value of the numeroAvviso property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroAvviso() {
        return numeroAvviso;
    }

    /**
     * Sets the value of the numeroAvviso property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroAvviso(String value) {
        this.numeroAvviso = value;
    }

}
