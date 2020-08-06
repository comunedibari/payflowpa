
package it.tasgroup.idp.xmlbillerservices.pendenze;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Debitori complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Debitori">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Debitore" type="{http://idp.tasgroup.it/xmlbillerservices/Pendenze}Debitore" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Debitori", propOrder = {
    "debitore"
})
public class Debitori {

    @XmlElement(name = "Debitore", required = true)
    protected List<Debitore> debitore;

    /**
     * Gets the value of the debitore property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the debitore property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDebitore().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Debitore }
     * 
     * 
     */
    public List<Debitore> getDebitore() {
        if (debitore == null) {
            debitore = new ArrayList<Debitore>();
        }
        return this.debitore;
    }

}
