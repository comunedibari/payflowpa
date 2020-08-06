
package it.toscana.rete.cart.servizi.iris_1_1.idpconfigurazione;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import it.toscana.rete.cart.servizi.iris_1_1.idpheader.IdpHeader;


/**
 * <p>Java class for IdpConfigurazioneEnte complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IdpConfigurazioneEnte">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpHeader}IdpHeader" minOccurs="0"/>
 *         &lt;element name="IdpBody" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpConfigurazione}IdpBodyEnte"/>
 *       &lt;/sequence>
 *       &lt;attribute name="Versione" use="required" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpConfigurazione}Versione" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IdpConfigurazioneEnte", propOrder = {
    "idpHeader",
    "idpBody"
})
public class IdpConfigurazioneEnte {

    @XmlElement(name = "IdpHeader", namespace = "http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpHeader")
    protected IdpHeader idpHeader;
    @XmlElement(name = "IdpBody", required = true)
    protected IdpBodyEnte idpBody;
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
     * Gets the value of the idpBody property.
     * 
     * @return
     *     possible object is
     *     {@link IdpBodyEnte }
     *     
     */
    public IdpBodyEnte getIdpBody() {
        return idpBody;
    }

    /**
     * Sets the value of the idpBody property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdpBodyEnte }
     *     
     */
    public void setIdpBody(IdpBodyEnte value) {
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
