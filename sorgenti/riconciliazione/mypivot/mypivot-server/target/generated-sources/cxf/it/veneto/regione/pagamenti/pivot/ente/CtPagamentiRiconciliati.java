
package it.veneto.regione.pagamenti.pivot.ente;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per ctPagamentiRiconciliati complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ctPagamentiRiconciliati"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="pagamento" type="{http://www.regione.veneto.it/pagamenti/pivot/ente/}ctExport"/&gt;
 *         &lt;element name="riversamento" type="{http://www.regione.veneto.it/pagamenti/pivot/ente/}ctFlussoRiversamento" minOccurs="0"/&gt;
 *         &lt;element name="data" type="{http://www.regione.veneto.it/pagamenti/pivot/ente/}stISODateTime"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ctPagamentiRiconciliati", propOrder = {
    "pagamento",
    "riversamento",
    "data"
})
public class CtPagamentiRiconciliati {

    @XmlElement(required = true)
    protected CtExport pagamento;
    protected CtFlussoRiversamento riversamento;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar data;

    /**
     * Recupera il valore della proprietà pagamento.
     * 
     * @return
     *     possible object is
     *     {@link CtExport }
     *     
     */
    public CtExport getPagamento() {
        return pagamento;
    }

    /**
     * Imposta il valore della proprietà pagamento.
     * 
     * @param value
     *     allowed object is
     *     {@link CtExport }
     *     
     */
    public void setPagamento(CtExport value) {
        this.pagamento = value;
    }

    /**
     * Recupera il valore della proprietà riversamento.
     * 
     * @return
     *     possible object is
     *     {@link CtFlussoRiversamento }
     *     
     */
    public CtFlussoRiversamento getRiversamento() {
        return riversamento;
    }

    /**
     * Imposta il valore della proprietà riversamento.
     * 
     * @param value
     *     allowed object is
     *     {@link CtFlussoRiversamento }
     *     
     */
    public void setRiversamento(CtFlussoRiversamento value) {
        this.riversamento = value;
    }

    /**
     * Recupera il valore della proprietà data.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getData() {
        return data;
    }

    /**
     * Imposta il valore della proprietà data.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setData(XMLGregorianCalendar value) {
        this.data = value;
    }

}
