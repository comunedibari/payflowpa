
package it.veneto.regione.pagamenti.pivot.ente;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per pivotSILChiediAccertamentoRisposta complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="pivotSILChiediAccertamentoRisposta"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.regione.veneto.it/pagamenti/pivot/ente/}risposta"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="bilancio" type="{http://www.regione.veneto.it/pagamenti/pivot/ente/}ctBilancio" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pivotSILChiediAccertamentoRisposta", propOrder = {
    "bilancio"
})
public class PivotSILChiediAccertamentoRisposta
    extends Risposta
{

    protected List<CtBilancio> bilancio;

    /**
     * Gets the value of the bilancio property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the bilancio property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBilancio().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CtBilancio }
     * 
     * 
     */
    public List<CtBilancio> getBilancio() {
        if (bilancio == null) {
            bilancio = new ArrayList<CtBilancio>();
        }
        return this.bilancio;
    }

}
