
package it.toscana.rete.cart.servizi.iris_1_1.idpallineamentopendenze;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import it.toscana.rete.cart.servizi.iris_1_1.idpheader.E2ESender;


/**
 * Richiesta di Allineamento Pendenze On-The-Fly, utilizzato nel caso multi ente
 * 
 * <p>Java class for IdpAllineamentoPendenzeMultiOTFElementType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IdpAllineamentoPendenzeMultiOTFElementType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="E2ESender" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpHeader}E2ESender"/>
 *         &lt;element name="E2EMsgId" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpHeader}MsgId"/>
 *         &lt;element name="IdpBody" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpAllineamentoPendenze}IdpBody"/>
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
    "e2ESender",
    "e2EMsgId",
    "idpBody"
})
public class IdpAllineamentoPendenzeMultiOTFElementType {

    @XmlElement(name = "E2ESender", required = true)
    protected E2ESender e2ESender;
    @XmlElement(name = "E2EMsgId", required = true)
    protected String e2EMsgId;
    @XmlElement(name = "IdpBody", required = true)
    protected IdpBody idpBody;

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
