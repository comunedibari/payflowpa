
package it.toscana.rete.cart.servizi.iris_1_1.idpesito;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Esiti complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Esiti">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Esito" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpEsito}Esito" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Esiti", propOrder = {
    "esito"
})
public class Esiti {

    @XmlElement(name = "Esito", required = true)
    protected List<Esito> esito;

    /**
     * Gets the value of the esito property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the esito property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEsito().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Esito }
     * 
     * 
     */
    public List<Esito> getEsito() {
        if (esito == null) {
            esito = new ArrayList<Esito>();
        }
        return this.esito;
    }

}
