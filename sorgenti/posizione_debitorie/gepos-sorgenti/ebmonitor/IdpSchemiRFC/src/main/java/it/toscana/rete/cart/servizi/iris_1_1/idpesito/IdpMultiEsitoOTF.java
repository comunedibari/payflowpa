
package it.toscana.rete.cart.servizi.iris_1_1.idpesito;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IdpMultiEsitoOTF complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IdpMultiEsitoOTF">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IdpOTF" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpEsito}IdpOTFType" minOccurs="0"/>
 *         &lt;element name="IdpEsitoOTF" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpEsito}IdpMultiEsitoOTFElement" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "IdpMultiEsitoOTF", propOrder = {
    "idpOTF",
    "idpEsitoOTF"
})
public class IdpMultiEsitoOTF {

    @XmlElement(name = "IdpOTF")
    protected IdpOTFType idpOTF;
    @XmlElement(name = "IdpEsitoOTF")
    protected List<IdpMultiEsitoOTFElement> idpEsitoOTF;
    @XmlAttribute(name = "Versione", required = true)
    protected String versione;

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
     * Gets the value of the idpEsitoOTF property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the idpEsitoOTF property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIdpEsitoOTF().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IdpMultiEsitoOTFElement }
     * 
     * 
     */
    public List<IdpMultiEsitoOTFElement> getIdpEsitoOTF() {
        if (idpEsitoOTF == null) {
            idpEsitoOTF = new ArrayList<IdpMultiEsitoOTFElement>();
        }
        return this.idpEsitoOTF;
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
