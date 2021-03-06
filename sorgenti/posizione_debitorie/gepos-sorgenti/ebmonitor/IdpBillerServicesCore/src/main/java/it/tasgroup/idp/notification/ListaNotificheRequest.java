
package it.tasgroup.idp.notification;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="senderId" type="{http://idp.tasgroup.it/Notification/}stText35"/>
 *         &lt;element name="senderSys" type="{http://idp.tasgroup.it/Notification/}stText35" minOccurs="0"/>
 *         &lt;element name="tipoDebito" type="{http://idp.tasgroup.it/Notification/}stText35" minOccurs="0"/>
 *         &lt;element name="tipoNotifica" type="{http://idp.tasgroup.it/Notification/}tipoNotificaType" minOccurs="0"/>
 *         &lt;element name="dataCreazione" type="{http://idp.tasgroup.it/Notification/}dataCreazioneFilterType" minOccurs="0"/>
 *         &lt;element name="offset" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="limit" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "senderId",
    "senderSys",
    "tipoDebito",
    "tipoNotifica",
    "dataCreazione",
    "offset",
    "limit"
})
@XmlRootElement(name = "listaNotificheRequest")
public class ListaNotificheRequest
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected String senderId;
    protected String senderSys;
    protected String tipoDebito;
    protected TipoNotificaType tipoNotifica;
    protected DataCreazioneFilterType dataCreazione;
    protected Integer offset;
    protected Integer limit;

    /**
     * Gets the value of the senderId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSenderId() {
        return senderId;
    }

    /**
     * Sets the value of the senderId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSenderId(String value) {
        this.senderId = value;
    }

    /**
     * Gets the value of the senderSys property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSenderSys() {
        return senderSys;
    }

    /**
     * Sets the value of the senderSys property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSenderSys(String value) {
        this.senderSys = value;
    }

    /**
     * Gets the value of the tipoDebito property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoDebito() {
        return tipoDebito;
    }

    /**
     * Sets the value of the tipoDebito property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoDebito(String value) {
        this.tipoDebito = value;
    }

    /**
     * Gets the value of the tipoNotifica property.
     * 
     * @return
     *     possible object is
     *     {@link TipoNotificaType }
     *     
     */
    public TipoNotificaType getTipoNotifica() {
        return tipoNotifica;
    }

    /**
     * Sets the value of the tipoNotifica property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoNotificaType }
     *     
     */
    public void setTipoNotifica(TipoNotificaType value) {
        this.tipoNotifica = value;
    }

    /**
     * Gets the value of the dataCreazione property.
     * 
     * @return
     *     possible object is
     *     {@link DataCreazioneFilterType }
     *     
     */
    public DataCreazioneFilterType getDataCreazione() {
        return dataCreazione;
    }

    /**
     * Sets the value of the dataCreazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataCreazioneFilterType }
     *     
     */
    public void setDataCreazione(DataCreazioneFilterType value) {
        this.dataCreazione = value;
    }

    /**
     * Gets the value of the offset property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getOffset() {
        return offset;
    }

    /**
     * Sets the value of the offset property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setOffset(Integer value) {
        this.offset = value;
    }

    /**
     * Gets the value of the limit property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getLimit() {
        return limit;
    }

    /**
     * Sets the value of the limit property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setLimit(Integer value) {
        this.limit = value;
    }

}
