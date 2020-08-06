
package it.toscana.rete.cart.servizi.iris_1_1.idpinformativapagamento;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DettaglioImportoTransato complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DettaglioImportoTransato">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Voce" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInformativaPagamento}Voce" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DettaglioImportoTransato", propOrder = {
    "voce"
})
public class DettaglioImportoTransato {

    @XmlElement(name = "Voce", required = true)
    protected List<Voce> voce;

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
     * {@link Voce }
     * 
     * 
     */
    public List<Voce> getVoce() {
        if (voce == null) {
            voce = new ArrayList<Voce>();
        }
        return this.voce;
    }

}
