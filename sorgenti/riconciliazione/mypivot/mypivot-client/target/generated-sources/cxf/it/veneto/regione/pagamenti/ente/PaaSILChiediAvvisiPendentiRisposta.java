
package it.veneto.regione.pagamenti.ente;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per paaSILChiediAvvisiPendentiRisposta complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="paaSILChiediAvvisiPendentiRisposta"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.regione.veneto.it/pagamenti/ente/}risposta"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="paaSILAvvisoPendente" type="{http://www.regione.veneto.it/pagamenti/ente/}paaSILAvvisoPendente" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "paaSILChiediAvvisiPendentiRisposta", propOrder = {
    "paaSILAvvisoPendente"
})
public class PaaSILChiediAvvisiPendentiRisposta
    extends Risposta
{

    protected List<PaaSILAvvisoPendente> paaSILAvvisoPendente;

    /**
     * Gets the value of the paaSILAvvisoPendente property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the paaSILAvvisoPendente property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPaaSILAvvisoPendente().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PaaSILAvvisoPendente }
     * 
     * 
     */
    public List<PaaSILAvvisoPendente> getPaaSILAvvisoPendente() {
        if (paaSILAvvisoPendente == null) {
            paaSILAvvisoPendente = new ArrayList<PaaSILAvvisoPendente>();
        }
        return this.paaSILAvvisoPendente;
    }

}
