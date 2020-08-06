
package it.tasgroup.iris.comunication.ws.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for elencoLogMessaggi complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="elencoLogMessaggi">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="searchLogMessaggi" type="{http://impl.ws.comunication.iris.tasgroup.it/}SearchLogMessaggiType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "elencoLogMessaggi", propOrder = {
    "searchLogMessaggi"
})
public class ElencoLogMessaggi {

    protected SearchLogMessaggiType searchLogMessaggi;

    /**
     * Gets the value of the searchLogMessaggi property.
     * 
     * @return
     *     possible object is
     *     {@link SearchLogMessaggiType }
     *     
     */
    public SearchLogMessaggiType getSearchLogMessaggi() {
        return searchLogMessaggi;
    }

    /**
     * Sets the value of the searchLogMessaggi property.
     * 
     * @param value
     *     allowed object is
     *     {@link SearchLogMessaggiType }
     *     
     */
    public void setSearchLogMessaggi(SearchLogMessaggiType value) {
        this.searchLogMessaggi = value;
    }

}
