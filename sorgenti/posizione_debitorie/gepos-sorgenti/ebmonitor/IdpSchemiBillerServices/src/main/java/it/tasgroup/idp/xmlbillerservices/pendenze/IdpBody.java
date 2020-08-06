
package it.tasgroup.idp.xmlbillerservices.pendenze;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IdpBody complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IdpBody">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Pendenza" type="{http://idp.tasgroup.it/xmlbillerservices/Pendenze}Pendenza" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IdpBody", propOrder = {
    "pendenza"
})
public class IdpBody {

    @XmlElement(name = "Pendenza", required = true)
    protected List<Pendenza> pendenza;

    /**
     * Gets the value of the pendenza property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pendenza property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPendenza().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Pendenza }
     * 
     * 
     */
    public List<Pendenza> getPendenza() {
        if (pendenza == null) {
            pendenza = new ArrayList<Pendenza>();
        }
        return this.pendenza;
    }

}
