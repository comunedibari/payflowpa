
package it.toscana.rete.cart.servizi.iris_1_1.idpallineamentopendenze;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IdentificativoAlternativoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IdentificativoAlternativoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TipoCodiceIdenficativoAlternativo" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max10Text"/>
 *         &lt;element name="CodiceIdenficativoAlternativo" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max50Text"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IdentificativoAlternativoType", propOrder = {
    "tipoCodiceIdenficativoAlternativo",
    "codiceIdenficativoAlternativo"
})
public class IdentificativoAlternativoType {

    @XmlElement(name = "TipoCodiceIdenficativoAlternativo", required = true)
    protected String tipoCodiceIdenficativoAlternativo;
    @XmlElement(name = "CodiceIdenficativoAlternativo", required = true)
    protected String codiceIdenficativoAlternativo;

    /**
     * Gets the value of the tipoCodiceIdenficativoAlternativo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoCodiceIdenficativoAlternativo() {
        return tipoCodiceIdenficativoAlternativo;
    }

    /**
     * Sets the value of the tipoCodiceIdenficativoAlternativo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoCodiceIdenficativoAlternativo(String value) {
        this.tipoCodiceIdenficativoAlternativo = value;
    }

    /**
     * Gets the value of the codiceIdenficativoAlternativo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceIdenficativoAlternativo() {
        return codiceIdenficativoAlternativo;
    }

    /**
     * Sets the value of the codiceIdenficativoAlternativo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceIdenficativoAlternativo(String value) {
        this.codiceIdenficativoAlternativo = value;
    }

}
