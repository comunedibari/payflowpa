
package it.veneto.regione.pagamenti.pa;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per tipoElencoFlussiSPC complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="tipoElencoFlussiSPC"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="totRestituiti" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="idSPC" type="{http://www.regione.veneto.it/pagamenti/pa/}tipoIdSPC" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoElencoFlussiSPC", propOrder = {
    "totRestituiti",
    "idSPC"
})
public class TipoElencoFlussiSPC {

    protected int totRestituiti;
    @XmlElement(nillable = true)
    protected List<TipoIdSPC> idSPC;

    /**
     * Recupera il valore della proprietà totRestituiti.
     * 
     */
    public int getTotRestituiti() {
        return totRestituiti;
    }

    /**
     * Imposta il valore della proprietà totRestituiti.
     * 
     */
    public void setTotRestituiti(int value) {
        this.totRestituiti = value;
    }

    /**
     * Gets the value of the idSPC property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the idSPC property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIdSPC().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TipoIdSPC }
     * 
     * 
     */
    public List<TipoIdSPC> getIdSPC() {
        if (idSPC == null) {
            idSPC = new ArrayList<TipoIdSPC>();
        }
        return this.idSPC;
    }

}
