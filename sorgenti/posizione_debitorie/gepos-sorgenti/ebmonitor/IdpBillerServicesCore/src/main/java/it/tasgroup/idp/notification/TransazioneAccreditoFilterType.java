
package it.tasgroup.idp.notification;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for transazioneAccreditoFilterType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="transazioneAccreditoFilterType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idOrdinante" type="{http://idp.tasgroup.it/Notification/}stText35" minOccurs="0"/>
 *         &lt;element name="idFlusso" type="{http://idp.tasgroup.it/Notification/}stText35"/>
 *         &lt;element name="trn" type="{http://idp.tasgroup.it/Notification/}stText35" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "transazioneAccreditoFilterType", propOrder = {
    "idOrdinante",
    "idFlusso",
    "trn"
})
public class TransazioneAccreditoFilterType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected String idOrdinante;
    @XmlElement(required = true)
    protected String idFlusso;
    protected String trn;

    /**
     * Gets the value of the idOrdinante property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdOrdinante() {
        return idOrdinante;
    }

    /**
     * Sets the value of the idOrdinante property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdOrdinante(String value) {
        this.idOrdinante = value;
    }

    /**
     * Gets the value of the idFlusso property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdFlusso() {
        return idFlusso;
    }

    /**
     * Sets the value of the idFlusso property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdFlusso(String value) {
        this.idFlusso = value;
    }

    /**
     * Gets the value of the trn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTrn() {
        return trn;
    }

    /**
     * Sets the value of the trn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTrn(String value) {
        this.trn = value;
    }

}
