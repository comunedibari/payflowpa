
package it.tasgroup.idp.xmlbillerservices.pendenze;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VociPagamento complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VociPagamento">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Voce" type="{http://idp.tasgroup.it/xmlbillerservices/Pendenze}VoceImporto" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VociPagamento", propOrder = {
    "voce"
})
public class VociPagamento {

    @XmlElement(name = "Voce", required = true)
    protected List<VoceImporto> voce;

    /**
     * Gets the value of the voce property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the voce property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVoce().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VoceImporto }
     * 
     * 
     */
    public List<VoceImporto> getVoce() {
        if (voce == null) {
            voce = new ArrayList<VoceImporto>();
        }
        return this.voce;
    }

}
