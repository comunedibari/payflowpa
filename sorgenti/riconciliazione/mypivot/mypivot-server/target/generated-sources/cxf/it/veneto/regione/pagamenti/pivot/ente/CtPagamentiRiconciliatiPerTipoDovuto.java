
package it.veneto.regione.pagamenti.pivot.ente;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ctPagamentiRiconciliatiPerTipoDovuto complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ctPagamentiRiconciliatiPerTipoDovuto"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="pagamentiRiconciliati" type="{http://www.regione.veneto.it/pagamenti/pivot/ente/}ctPagamentiRiconciliati" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="identificativoTipoDovuto" type="{http://www.regione.veneto.it/pagamenti/pivot/ente/}stText64" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ctPagamentiRiconciliatiPerTipoDovuto", propOrder = {
    "pagamentiRiconciliati"
})
public class CtPagamentiRiconciliatiPerTipoDovuto {

    protected List<CtPagamentiRiconciliati> pagamentiRiconciliati;
    @XmlAttribute(name = "identificativoTipoDovuto")
    protected String identificativoTipoDovuto;

    /**
     * Gets the value of the pagamentiRiconciliati property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pagamentiRiconciliati property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPagamentiRiconciliati().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CtPagamentiRiconciliati }
     * 
     * 
     */
    public List<CtPagamentiRiconciliati> getPagamentiRiconciliati() {
        if (pagamentiRiconciliati == null) {
            pagamentiRiconciliati = new ArrayList<CtPagamentiRiconciliati>();
        }
        return this.pagamentiRiconciliati;
    }

    /**
     * Recupera il valore della proprietà identificativoTipoDovuto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativoTipoDovuto() {
        return identificativoTipoDovuto;
    }

    /**
     * Imposta il valore della proprietà identificativoTipoDovuto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativoTipoDovuto(String value) {
        this.identificativoTipoDovuto = value;
    }

}
