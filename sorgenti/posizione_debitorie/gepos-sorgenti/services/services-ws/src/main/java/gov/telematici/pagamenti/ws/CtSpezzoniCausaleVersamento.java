
package gov.telematici.pagamenti.ws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ctSpezzoniCausaleVersamento complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ctSpezzoniCausaleVersamento">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence maxOccurs="6">
 *         &lt;choice>
 *           &lt;element name="spezzoneCausaleVersamento" type="{http://ws.pagamenti.telematici.gov/}stText35"/>
 *           &lt;element name="spezzoneStrutturatoCausaleVersamento" type="{http://ws.pagamenti.telematici.gov/}ctSpezzoneStrutturatoCausaleVersamento"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ctSpezzoniCausaleVersamento", propOrder = {
    "spezzoneCausaleVersamentoOrSpezzoneStrutturatoCausaleVersamento"
})
public class CtSpezzoniCausaleVersamento {

    @XmlElements({
        @XmlElement(name = "spezzoneStrutturatoCausaleVersamento", type = CtSpezzoneStrutturatoCausaleVersamento.class),
        @XmlElement(name = "spezzoneCausaleVersamento", type = String.class)
    })
    protected List<Object> spezzoneCausaleVersamentoOrSpezzoneStrutturatoCausaleVersamento;

    /**
     * Gets the value of the spezzoneCausaleVersamentoOrSpezzoneStrutturatoCausaleVersamento property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the spezzoneCausaleVersamentoOrSpezzoneStrutturatoCausaleVersamento property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSpezzoneCausaleVersamentoOrSpezzoneStrutturatoCausaleVersamento().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CtSpezzoneStrutturatoCausaleVersamento }
     * {@link String }
     * 
     * 
     */
    public List<Object> getSpezzoneCausaleVersamentoOrSpezzoneStrutturatoCausaleVersamento() {
        if (spezzoneCausaleVersamentoOrSpezzoneStrutturatoCausaleVersamento == null) {
            spezzoneCausaleVersamentoOrSpezzoneStrutturatoCausaleVersamento = new ArrayList<Object>();
        }
        return this.spezzoneCausaleVersamentoOrSpezzoneStrutturatoCausaleVersamento;
    }

}
