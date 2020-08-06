
package it.veneto.regione.pagamenti.pivot.ente;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per ctRichiestaPerData complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ctRichiestaPerData"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="dataDa" type="{http://www.regione.veneto.it/pagamenti/pivot/ente/}stISODateTime"/&gt;
 *         &lt;element name="dataA" type="{http://www.regione.veneto.it/pagamenti/pivot/ente/}stISODateTime"/&gt;
 *         &lt;element name="filtroTipiDovuto" type="{http://www.regione.veneto.it/pagamenti/pivot/ente/}ctFiltroTipiDovuto" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ctRichiestaPerData", propOrder = {
    "dataDa",
    "dataA",
    "filtroTipiDovuto"
})
public class CtRichiestaPerData {

    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataDa;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataA;
    protected CtFiltroTipiDovuto filtroTipiDovuto;

    /**
     * Recupera il valore della proprietà dataDa.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataDa() {
        return dataDa;
    }

    /**
     * Imposta il valore della proprietà dataDa.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataDa(XMLGregorianCalendar value) {
        this.dataDa = value;
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

    /**
     * Recupera il valore della proprietà filtroTipiDovuto.
     * 
     * @return
     *     possible object is
     *     {@link CtFiltroTipiDovuto }
     *     
     */
    public CtFiltroTipiDovuto getFiltroTipiDovuto() {
        return filtroTipiDovuto;
    }

    /**
     * Imposta il valore della proprietà filtroTipiDovuto.
     * 
     * @param value
     *     allowed object is
     *     {@link CtFiltroTipiDovuto }
     *     
     */
    public void setFiltroTipiDovuto(CtFiltroTipiDovuto value) {
        this.filtroTipiDovuto = value;
    }

}
