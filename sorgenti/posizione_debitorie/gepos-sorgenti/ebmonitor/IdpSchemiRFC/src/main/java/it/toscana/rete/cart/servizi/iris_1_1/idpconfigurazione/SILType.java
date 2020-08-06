
package it.toscana.rete.cart.servizi.iris_1_1.idpconfigurazione;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SILType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SILType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CodiceSIL" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max35Text"/>
 *         &lt;element name="DescrizioneSIL" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max50Text"/>
 *         &lt;element name="CodiceSILIntermediario" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max35Text"/>
 *         &lt;element name="DescrizioneSILIntermediario" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max50Text"/>
 *         &lt;element name="UserId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SILCredential" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SILType", propOrder = {
    "codiceSIL",
    "descrizioneSIL",
    "codiceSILIntermediario",
    "descrizioneSILIntermediario",
    "userId",
    "silCredential"
})
public class SILType {

    @XmlElement(name = "CodiceSIL", required = true)
    protected String codiceSIL;
    @XmlElement(name = "DescrizioneSIL", required = true)
    protected String descrizioneSIL;
    @XmlElement(name = "CodiceSILIntermediario", required = true)
    protected String codiceSILIntermediario;
    @XmlElement(name = "DescrizioneSILIntermediario", required = true)
    protected String descrizioneSILIntermediario;
    @XmlElement(name = "UserId", required = true)
    protected String userId;
    @XmlElement(name = "SILCredential", required = true)
    protected String silCredential;

    /**
     * Gets the value of the codiceSIL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceSIL() {
        return codiceSIL;
    }

    /**
     * Sets the value of the codiceSIL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceSIL(String value) {
        this.codiceSIL = value;
    }

    /**
     * Gets the value of the descrizioneSIL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneSIL() {
        return descrizioneSIL;
    }

    /**
     * Sets the value of the descrizioneSIL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneSIL(String value) {
        this.descrizioneSIL = value;
    }

    /**
     * Gets the value of the codiceSILIntermediario property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceSILIntermediario() {
        return codiceSILIntermediario;
    }

    /**
     * Sets the value of the codiceSILIntermediario property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceSILIntermediario(String value) {
        this.codiceSILIntermediario = value;
    }

    /**
     * Gets the value of the descrizioneSILIntermediario property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneSILIntermediario() {
        return descrizioneSILIntermediario;
    }

    /**
     * Sets the value of the descrizioneSILIntermediario property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneSILIntermediario(String value) {
        this.descrizioneSILIntermediario = value;
    }

    /**
     * Gets the value of the userId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the value of the userId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserId(String value) {
        this.userId = value;
    }

    /**
     * Gets the value of the silCredential property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSILCredential() {
        return silCredential;
    }

    /**
     * Sets the value of the silCredential property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSILCredential(String value) {
        this.silCredential = value;
    }

}
