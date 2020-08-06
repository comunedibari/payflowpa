
package it.veneto.regione.pagamenti.pivot.ente;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ctTipoDovuto complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ctTipoDovuto"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="codTipoDovuto" type="{http://www.regione.veneto.it/pagamenti/pivot/ente/}stText64"/&gt;
 *         &lt;element name="capitolo" type="{http://www.regione.veneto.it/pagamenti/pivot/ente/}ctCapitolo" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ctTipoDovuto", propOrder = {
    "codTipoDovuto",
    "capitolo"
})
public class CtTipoDovuto {

    @XmlElement(required = true)
    protected String codTipoDovuto;
    @XmlElement(required = true)
    protected List<CtCapitolo> capitolo;

    /**
     * Recupera il valore della proprietà codTipoDovuto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodTipoDovuto() {
        return codTipoDovuto;
    }

    /**
     * Imposta il valore della proprietà codTipoDovuto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodTipoDovuto(String value) {
        this.codTipoDovuto = value;
    }

    /**
     * Gets the value of the capitolo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the capitolo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCapitolo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CtCapitolo }
     * 
     * 
     */
    public List<CtCapitolo> getCapitolo() {
        if (capitolo == null) {
            capitolo = new ArrayList<CtCapitolo>();
        }
        return this.capitolo;
    }

}
