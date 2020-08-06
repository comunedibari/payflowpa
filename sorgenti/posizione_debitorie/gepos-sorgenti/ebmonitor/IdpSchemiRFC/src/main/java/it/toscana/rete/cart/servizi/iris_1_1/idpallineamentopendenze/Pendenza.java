
package it.toscana.rete.cart.servizi.iris_1_1.idpallineamentopendenze;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Pendenza complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Pendenza">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpAllineamentoPendenze}HeadPendenza">
 *       &lt;sequence>
 *         &lt;element name="Insert" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpAllineamentoPendenze}Pendenza.InsertReplace" minOccurs="0"/>
 *         &lt;element name="UpdateStatus" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpAllineamentoPendenze}Pendenza.UpdateStatus" minOccurs="0"/>
 *         &lt;element name="Replace" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpAllineamentoPendenze}Pendenza.InsertReplace" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Pendenza", propOrder = {
    "insert",
    "updateStatus",
    "replace"
})
public class Pendenza
    extends HeadPendenza
{

    @XmlElement(name = "Insert")
    protected PendenzaInsertReplace insert;
    @XmlElement(name = "UpdateStatus")
    protected PendenzaUpdateStatus updateStatus;
    @XmlElement(name = "Replace")
    protected PendenzaInsertReplace replace;

    /**
     * Gets the value of the insert property.
     * 
     * @return
     *     possible object is
     *     {@link PendenzaInsertReplace }
     *     
     */
    public PendenzaInsertReplace getInsert() {
        return insert;
    }

    /**
     * Sets the value of the insert property.
     * 
     * @param value
     *     allowed object is
     *     {@link PendenzaInsertReplace }
     *     
     */
    public void setInsert(PendenzaInsertReplace value) {
        this.insert = value;
    }

    /**
     * Gets the value of the updateStatus property.
     * 
     * @return
     *     possible object is
     *     {@link PendenzaUpdateStatus }
     *     
     */
    public PendenzaUpdateStatus getUpdateStatus() {
        return updateStatus;
    }

    /**
     * Sets the value of the updateStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link PendenzaUpdateStatus }
     *     
     */
    public void setUpdateStatus(PendenzaUpdateStatus value) {
        this.updateStatus = value;
    }

    /**
     * Gets the value of the replace property.
     * 
     * @return
     *     possible object is
     *     {@link PendenzaInsertReplace }
     *     
     */
    public PendenzaInsertReplace getReplace() {
        return replace;
    }

    /**
     * Sets the value of the replace property.
     * 
     * @param value
     *     allowed object is
     *     {@link PendenzaInsertReplace }
     *     
     */
    public void setReplace(PendenzaInsertReplace value) {
        this.replace = value;
    }

}
