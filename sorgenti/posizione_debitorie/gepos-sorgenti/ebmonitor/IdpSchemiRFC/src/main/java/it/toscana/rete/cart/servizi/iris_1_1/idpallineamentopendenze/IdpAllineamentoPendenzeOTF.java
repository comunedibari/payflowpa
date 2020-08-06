
package it.toscana.rete.cart.servizi.iris_1_1.idpallineamentopendenze;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import it.toscana.rete.cart.servizi.iris_1_1.idpheader.IdpHeader;
import it.toscana.rete.cart.servizi.iris_1_1.idpheader.IdpOTF;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpHeader}IdpHeader"/>
 *         &lt;element ref="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpHeader}IdpOTF" minOccurs="0"/>
 *         &lt;element name="IdpBody" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpAllineamentoPendenze}IdpBody"/>
 *       &lt;/sequence>
 *       &lt;attribute name="Versione" use="required" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpAllineamentoPendenze}Versione" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "idpHeader",
    "idpOTF",
    "idpBody"
})
@XmlRootElement(name = "IdpAllineamentoPendenzeOTF")
public class IdpAllineamentoPendenzeOTF {

    @XmlElement(name = "IdpHeader", namespace = "http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpHeader", required = true)
    protected IdpHeader idpHeader;
    @XmlElement(name = "IdpOTF", namespace = "http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpHeader")
    protected IdpOTF idpOTF;
    @XmlElement(name = "IdpBody", required = true)
    protected IdpBody idpBody;
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
     * Presente solo in caso di pendenze di tipo INSERT. Contiene le informazioni per il pagamento immediato.
     * 
     * @return
     *     possible object is
     *     {@link IdpOTF }
     *     
     */
    public IdpOTF getIdpOTF() {
        return idpOTF;
    }

    /**
     * Sets the value of the idpOTF property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdpOTF }
     *     
     */
    public void setIdpOTF(IdpOTF value) {
        this.idpOTF = value;
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
