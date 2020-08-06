
package it.toscana.rete.cart.servizi.iris_1_1.idpallineamentopendenze;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IdpAllineamentoPendenzeMultiOTFParametriType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IdpAllineamentoPendenzeMultiOTFParametriType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AnnullaPagamentiInCorso" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IdpAllineamentoPendenzeMultiOTFParametriType", propOrder = {
    "annullaPagamentiInCorso"
})
public class IdpAllineamentoPendenzeMultiOTFParametriType {

    @XmlElement(name = "AnnullaPagamentiInCorso")
    protected Boolean annullaPagamentiInCorso;

    /**
     * Gets the value of the annullaPagamentiInCorso property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAnnullaPagamentiInCorso() {
        return annullaPagamentiInCorso;
    }

    /**
     * Sets the value of the annullaPagamentiInCorso property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAnnullaPagamentiInCorso(Boolean value) {
        this.annullaPagamentiInCorso = value;
    }

}
