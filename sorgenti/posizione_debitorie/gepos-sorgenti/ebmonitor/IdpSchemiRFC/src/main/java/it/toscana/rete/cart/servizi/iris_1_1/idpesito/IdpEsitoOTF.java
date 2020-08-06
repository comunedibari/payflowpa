
package it.toscana.rete.cart.servizi.iris_1_1.idpesito;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import it.toscana.rete.cart.servizi.iris_1_1.idpheader.IdpHeader;


/**
 * <p>Java class for IdpEsitoOTF complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IdpEsitoOTF">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpHeader}IdpHeader"/>
 *         &lt;element name="IdpOTF" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpEsito}IdpOTFType" minOccurs="0"/>
 *         &lt;element name="IdpBody" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpEsito}IdpBodyType"/>
 *       &lt;/sequence>
 *       &lt;attribute name="Versione" use="required" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpEsito}Versione" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IdpEsitoOTF", propOrder = {
    "idpHeader",
    "idpOTF",
    "idpBody"
})
public class IdpEsitoOTF {

    @XmlElement(name = "IdpHeader", namespace = "http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpHeader", required = true)
    protected IdpHeader idpHeader;
    @XmlElement(name = "IdpOTF")
    protected IdpOTFType idpOTF;
    @XmlElement(name = "IdpBody", required = true)
    protected IdpBodyType idpBody;
    @XmlAttribute(name = "Versione", required = true)
    protected String versione;

    /**
     * Gets the value of the idpHeader property.
     * 
     * @return
     *     possible object is
     *     {@link IdpHeader }
     *     
     */
    public IdpHeader getIdpHeader() {
        return idpHeader;
    }

    /**
     * Sets the value of the idpHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdpHeader }
     *     
     */
    public void setIdpHeader(IdpHeader value) {
        this.idpHeader = value;
    }

    /**
     * Gets the value of the idpOTF property.
     * 
     * @return
     *     possible object is
     *     {@link IdpOTFType }
     *     
     */
    public IdpOTFType getIdpOTF() {
        return idpOTF;
    }

    /**
     * Sets the value of the idpOTF property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdpOTFType }
     *     
     */
    public void setIdpOTF(IdpOTFType value) {
        this.idpOTF = value;
    }

    /**
     * Gets the value of the idpBody property.
     * 
     * @return
     *     possible object is
     *     {@link IdpBodyType }
     *     
     */
    public IdpBodyType getIdpBody() {
        return idpBody;
    }

    /**
     * Sets the value of the idpBody property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdpBodyType }
     *     
     */
    public void setIdpBody(IdpBodyType value) {
        this.idpBody = value;
    }

    /**
     * Gets the value of the versione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersione() {
        return versione;
    }

    /**
     * Sets the value of the versione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersione(String value) {
        this.versione = value;
    }

}
