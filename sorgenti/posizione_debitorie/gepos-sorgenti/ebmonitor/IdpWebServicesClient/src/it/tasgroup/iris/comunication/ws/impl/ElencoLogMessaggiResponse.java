
package it.tasgroup.iris.comunication.ws.impl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for elencoLogMessaggiResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="elencoLogMessaggiResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="LogMessaggiResponse" type="{http://impl.ws.comunication.iris.tasgroup.it/}LogMessaggioType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "elencoLogMessaggiResponse", propOrder = {
    "logMessaggiResponse"
})
public class ElencoLogMessaggiResponse {

    @XmlElement(name = "LogMessaggiResponse")
    protected List<LogMessaggioType> logMessaggiResponse;

    /**
     * Gets the value of the logMessaggiResponse property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the logMessaggiResponse property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLogMessaggiResponse().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LogMessaggioType }
     * 
     * 
     */
    public List<LogMessaggioType> getLogMessaggiResponse() {
        if (logMessaggiResponse == null) {
            logMessaggiResponse = new ArrayList<LogMessaggioType>();
        }
        return this.logMessaggiResponse;
    }

}
