
package it.veneto.regione.pagamenti.pivot.ente;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ctFiltroTipiDovuto complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ctFiltroTipiDovuto"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="identificativoTipoDovuto" type="{http://www.regione.veneto.it/pagamenti/pivot/ente/}stText35" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ctFiltroTipiDovuto", propOrder = {
    "identificativoTipoDovuto"
})
public class CtFiltroTipiDovuto {

    @XmlElement(required = true)
    protected List<String> identificativoTipoDovuto;

    /**
     * Gets the value of the identificativoTipoDovuto property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the identificativoTipoDovuto property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIdentificativoTipoDovuto().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getIdentificativoTipoDovuto() {
        if (identificativoTipoDovuto == null) {
            identificativoTipoDovuto = new ArrayList<String>();
        }
        return this.identificativoTipoDovuto;
    }

}
