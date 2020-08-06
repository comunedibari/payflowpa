
package it.veneto.regione.pagamenti.pivot.ente;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ctCapitolo complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ctCapitolo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="codCapitolo" type="{http://www.regione.veneto.it/pagamenti/pivot/ente/}stText12"/&gt;
 *         &lt;element name="accertamento" type="{http://www.regione.veneto.it/pagamenti/pivot/ente/}ctAccertamento" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ctCapitolo", propOrder = {
    "codCapitolo",
    "accertamento"
})
public class CtCapitolo {

    @XmlElement(required = true)
    protected String codCapitolo;
    @XmlElement(required = true)
    protected List<CtAccertamento> accertamento;

    /**
     * Recupera il valore della proprietà codCapitolo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodCapitolo() {
        return codCapitolo;
    }

    /**
     * Imposta il valore della proprietà codCapitolo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodCapitolo(String value) {
        this.codCapitolo = value;
    }

    /**
     * Gets the value of the accertamento property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the accertamento property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAccertamento().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CtAccertamento }
     * 
     * 
     */
    public List<CtAccertamento> getAccertamento() {
        if (accertamento == null) {
            accertamento = new ArrayList<CtAccertamento>();
        }
        return this.accertamento;
    }

}
