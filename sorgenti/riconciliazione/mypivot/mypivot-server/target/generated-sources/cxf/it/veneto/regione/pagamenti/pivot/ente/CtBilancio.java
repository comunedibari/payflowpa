
package it.veneto.regione.pagamenti.pivot.ente;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ctBilancio complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ctBilancio"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ufficio" type="{http://www.regione.veneto.it/pagamenti/pivot/ente/}stText12"/&gt;
 *         &lt;element name="tipoDovuto" type="{http://www.regione.veneto.it/pagamenti/pivot/ente/}ctTipoDovuto" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ctBilancio", propOrder = {
    "ufficio",
    "tipoDovuto"
})
public class CtBilancio {

    @XmlElement(required = true)
    protected String ufficio;
    @XmlElement(required = true)
    protected List<CtTipoDovuto> tipoDovuto;

    /**
     * Recupera il valore della proprietà ufficio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUfficio() {
        return ufficio;
    }

    /**
     * Imposta il valore della proprietà ufficio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUfficio(String value) {
        this.ufficio = value;
    }

    /**
     * Gets the value of the tipoDovuto property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tipoDovuto property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTipoDovuto().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CtTipoDovuto }
     * 
     * 
     */
    public List<CtTipoDovuto> getTipoDovuto() {
        if (tipoDovuto == null) {
            tipoDovuto = new ArrayList<CtTipoDovuto>();
        }
        return this.tipoDovuto;
    }

}
