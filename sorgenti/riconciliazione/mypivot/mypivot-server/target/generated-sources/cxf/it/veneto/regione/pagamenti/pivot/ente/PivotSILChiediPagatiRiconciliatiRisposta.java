
package it.veneto.regione.pagamenti.pivot.ente;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per pivotSILChiediPagatiRiconciliatiRisposta complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="pivotSILChiediPagatiRiconciliatiRisposta"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.regione.veneto.it/pagamenti/pivot/ente/}risposta"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="pagamentiRiconciliatiPerTipoDovuto" type="{http://www.regione.veneto.it/pagamenti/pivot/ente/}ctPagamentiRiconciliatiPerTipoDovuto" maxOccurs="1000" minOccurs="0"/&gt;
 *         &lt;element name="numeroTotale" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="dataA" type="{http://www.regione.veneto.it/pagamenti/pivot/ente/}stISODateTime" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pivotSILChiediPagatiRiconciliatiRisposta", propOrder = {
    "pagamentiRiconciliatiPerTipoDovuto",
    "numeroTotale",
    "dataA"
})
public class PivotSILChiediPagatiRiconciliatiRisposta
    extends Risposta
{

    protected List<CtPagamentiRiconciliatiPerTipoDovuto> pagamentiRiconciliatiPerTipoDovuto;
    @XmlElement(required = true)
    protected BigInteger numeroTotale;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataA;

    /**
     * Gets the value of the pagamentiRiconciliatiPerTipoDovuto property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pagamentiRiconciliatiPerTipoDovuto property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPagamentiRiconciliatiPerTipoDovuto().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CtPagamentiRiconciliatiPerTipoDovuto }
     * 
     * 
     */
    public List<CtPagamentiRiconciliatiPerTipoDovuto> getPagamentiRiconciliatiPerTipoDovuto() {
        if (pagamentiRiconciliatiPerTipoDovuto == null) {
            pagamentiRiconciliatiPerTipoDovuto = new ArrayList<CtPagamentiRiconciliatiPerTipoDovuto>();
        }
        return this.pagamentiRiconciliatiPerTipoDovuto;
    }

    /**
     * Recupera il valore della proprietà numeroTotale.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNumeroTotale() {
        return numeroTotale;
    }

    /**
     * Imposta il valore della proprietà numeroTotale.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNumeroTotale(BigInteger value) {
        this.numeroTotale = value;
    }

    /**
     * Recupera il valore della proprietà dataA.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataA() {
        return dataA;
    }

    /**
     * Imposta il valore della proprietà dataA.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataA(XMLGregorianCalendar value) {
        this.dataA = value;
    }

}
