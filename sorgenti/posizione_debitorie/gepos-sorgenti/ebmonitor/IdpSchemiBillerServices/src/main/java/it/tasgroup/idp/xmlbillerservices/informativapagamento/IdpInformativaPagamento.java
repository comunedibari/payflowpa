
package it.tasgroup.idp.xmlbillerservices.informativapagamento;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import it.tasgroup.idp.xmlbillerservices.header.OutboundIdpHeader;


/**
 * <p>Java class for IdpInformativaPagamento complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IdpInformativaPagamento">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IdpHeader" type="{http://idp.tasgroup.it/xmlbillerservices/Header}OutboundIdpHeader"/>
 *         &lt;element name="IdpBody" type="{http://idp.tasgroup.it/xmlbillerservices/InformativaPagamento}IdpBody"/>
 *       &lt;/sequence>
 *       &lt;attribute name="Versione" use="required" type="{http://idp.tasgroup.it/xmlbillerservices/InformativaPagamento}Versione" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IdpInformativaPagamento", propOrder = {
    "idpHeader",
    "idpBody"
})
public class IdpInformativaPagamento {

    @XmlElement(name = "IdpHeader", required = true)
    protected OutboundIdpHeader idpHeader;
    @XmlElement(name = "IdpBody", required = true)
    protected IdpBody idpBody;
    @XmlAttribute(name = "Versione", required = true)
    protected String versione;

    /**
     * Gets the value of the idpHeader property.
     * 
     * @return
     *     possible object is
     *     {@link OutboundIdpHeader }
     *     
     */
    public OutboundIdpHeader getIdpHeader() {
        return idpHeader;
    }

    /**
     * Sets the value of the idpHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link OutboundIdpHeader }
     *     
     */
    public void setIdpHeader(OutboundIdpHeader value) {
        this.idpHeader = value;
    }

    /**
     * Gets the value of the idpBody property.
     * 
     * @return
     *     possible object is
     *     {@link IdpBody }
     *     
     */
    public IdpBody getIdpBody() {
        return idpBody;
    }

    /**
     * Sets the value of the idpBody property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdpBody }
     *     
     */
    public void setIdpBody(IdpBody value) {
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
