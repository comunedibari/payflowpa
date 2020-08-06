
package it.tasgroup.idp.xmlbillerservices.pendenze;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import it.tasgroup.idp.xmlbillerservices.header.E2ESender;


/**
 * <p>Java class for IdpAllineamentoPendenzeMultiOTFElementType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IdpAllineamentoPendenzeMultiOTFElementType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="E2EMsgId" type="{http://idp.tasgroup.it/xmlbillerservices/Header}MsgId"/>
 *         &lt;element name="E2ESender" type="{http://idp.tasgroup.it/xmlbillerservices/Header}E2ESender"/>
 *         &lt;element name="IdpBody" type="{http://idp.tasgroup.it/xmlbillerservices/Pendenze}IdpBody"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IdpAllineamentoPendenzeMultiOTFElementType", propOrder = {
    "e2EMsgId",
    "e2ESender",
    "idpBody"
})
public class IdpAllineamentoPendenzeMultiOTFElementType {

    @XmlElement(name = "E2EMsgId", required = true)
    protected String e2EMsgId;
    @XmlElement(name = "E2ESender", required = true)
    protected E2ESender e2ESender;
    @XmlElement(name = "IdpBody", required = true)
    protected IdpBody idpBody;

    /**
     * Gets the value of the e2EMsgId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getE2EMsgId() {
        return e2EMsgId;
    }

    /**
     * Sets the value of the e2EMsgId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setE2EMsgId(String value) {
        this.e2EMsgId = value;
    }

    /**
     * Gets the value of the e2ESender property.
     * 
     * @return
     *     possible object is
     *     {@link E2ESender }
     *     
     */
    public E2ESender getE2ESender() {
        return e2ESender;
    }

    /**
     * Sets the value of the e2ESender property.
     * 
     * @param value
     *     allowed object is
     *     {@link E2ESender }
     *     
     */
    public void setE2ESender(E2ESender value) {
        this.e2ESender = value;
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

}
