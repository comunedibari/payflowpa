
package it.tasgroup.idp.xmlbillerservices.informativapagamento;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Java class for idPagamento complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="idPagamento">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://idp.tasgroup.it/xmlbillerservices/Common>IdentificativoIdp">
 *       &lt;attribute name="TipoDebito" use="required" type="{http://idp.tasgroup.it/xmlbillerservices/Common}Max50Text" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "idPagamento", propOrder = {
    "value"
})
public class IdPagamento {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "TipoDebito", required = true)
    protected String tipoDebito;

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
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

}
