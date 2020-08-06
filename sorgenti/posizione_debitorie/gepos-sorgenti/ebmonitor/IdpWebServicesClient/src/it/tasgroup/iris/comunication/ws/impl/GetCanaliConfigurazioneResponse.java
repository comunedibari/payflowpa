
package it.tasgroup.iris.comunication.ws.impl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getCanaliConfigurazioneResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getCanaliConfigurazioneResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CanaleResponse" type="{http://impl.ws.comunication.iris.tasgroup.it/}CanaleType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getCanaliConfigurazioneResponse", propOrder = {
    "canaleResponse"
})
public class GetCanaliConfigurazioneResponse {

    @XmlElement(name = "CanaleResponse")
    protected List<CanaleType> canaleResponse;

    /**
     * Gets the value of the canaleResponse property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the canaleResponse property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCanaleResponse().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CanaleType }
     * 
     * 
     */
    public List<CanaleType> getCanaleResponse() {
        if (canaleResponse == null) {
            canaleResponse = new ArrayList<CanaleType>();
        }
        return this.canaleResponse;
    }

}
