
package it.tasgroup.idp.xmlbillerservices.informativapagamento;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CanalePagamento complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CanalePagamento">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IdentificativoPSP" type="{http://idp.tasgroup.it/xmlbillerservices/Common}Max35Text"/>
 *         &lt;element name="IdentificativoCanale" type="{http://idp.tasgroup.it/xmlbillerservices/Common}Max35Text"/>
 *         &lt;element name="TipoVersamento" type="{http://idp.tasgroup.it/xmlbillerservices/Common}Max4Text"/>
 *         &lt;element name="Descrizione" type="{http://idp.tasgroup.it/xmlbillerservices/Common}Max256Text"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CanalePagamento", propOrder = {
    "identificativoPSP",
    "identificativoCanale",
    "tipoVersamento",
    "descrizione"
})
public class CanalePagamento {

    @XmlElement(name = "IdentificativoPSP", required = true)
    protected String identificativoPSP;
    @XmlElement(name = "IdentificativoCanale", required = true)
    protected String identificativoCanale;
    @XmlElement(name = "TipoVersamento", required = true)
    protected String tipoVersamento;
    @XmlElement(name = "Descrizione", required = true)
    protected String descrizione;

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
     * Gets the value of the identificativoCanale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativoCanale() {
        return identificativoCanale;
    }

    /**
     * Sets the value of the identificativoCanale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativoCanale(String value) {
        this.identificativoCanale = value;
    }

    /**
     * Gets the value of the tipoVersamento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoVersamento() {
        return tipoVersamento;
    }

    /**
     * Sets the value of the tipoVersamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoVersamento(String value) {
        this.tipoVersamento = value;
    }

    /**
     * Gets the value of the descrizione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Sets the value of the descrizione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizione(String value) {
        this.descrizione = value;
    }

}
