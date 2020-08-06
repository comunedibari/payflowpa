
package it.tasgroup.iris.comunication.ws.impl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for sendMessage complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="sendMessage">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MessaggioLogico" type="{http://impl.ws.comunication.iris.tasgroup.it/}MessaggioLogicoType"/>
 *         &lt;element name="Utenti" type="{http://impl.ws.comunication.iris.tasgroup.it/}UtenteType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sendMessage", propOrder = {
    "messaggioLogico",
    "utenti"
})
public class SendMessage {

    @XmlElement(name = "MessaggioLogico", required = true)
    protected MessaggioLogicoType messaggioLogico;
    @XmlElement(name = "Utenti", required = true)
    protected List<UtenteType> utenti;

    /**
     * Gets the value of the messaggioLogico property.
     * 
     * @return
     *     possible object is
     *     {@link MessaggioLogicoType }
     *     
     */
    public MessaggioLogicoType getMessaggioLogico() {
        return messaggioLogico;
    }

    /**
     * Sets the value of the messaggioLogico property.
     * 
     * @param value
     *     allowed object is
     *     {@link MessaggioLogicoType }
     *     
     */
    public void setMessaggioLogico(MessaggioLogicoType value) {
        this.messaggioLogico = value;
    }

    /**
     * Gets the value of the utenti property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the utenti property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUtenti().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UtenteType }
     * 
     * 
     */
    public List<UtenteType> getUtenti() {
        if (utenti == null) {
            utenti = new ArrayList<UtenteType>();
        }
        return this.utenti;
    }

}
