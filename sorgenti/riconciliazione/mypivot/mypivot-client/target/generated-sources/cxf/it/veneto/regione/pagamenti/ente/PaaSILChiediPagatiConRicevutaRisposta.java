
package it.veneto.regione.pagamenti.ente;

import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per paaSILChiediPagatiConRicevutaRisposta complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="paaSILChiediPagatiConRicevutaRisposta"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.regione.veneto.it/pagamenti/ente/}risposta"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="pagati" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/&gt;
 *         &lt;element name="tipoFirma" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="rt" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "paaSILChiediPagatiConRicevutaRisposta", propOrder = {
    "pagati",
    "tipoFirma",
    "rt"
})
public class PaaSILChiediPagatiConRicevutaRisposta
    extends Risposta
{

    @XmlMimeType("application/octet-stream")
    protected DataHandler pagati;
    protected String tipoFirma;
    @XmlMimeType("application/octet-stream")
    protected DataHandler rt;

    /**
     * Recupera il valore della proprietà pagati.
     * 
     * @return
     *     possible object is
     *     {@link DataHandler }
     *     
     */
    public DataHandler getPagati() {
        return pagati;
    }

    /**
     * Imposta il valore della proprietà pagati.
     * 
     * @param value
     *     allowed object is
     *     {@link DataHandler }
     *     
     */
    public void setPagati(DataHandler value) {
        this.pagati = value;
    }

    /**
     * Recupera il valore della proprietà tipoFirma.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoFirma() {
        return tipoFirma;
    }

    /**
     * Imposta il valore della proprietà tipoFirma.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoFirma(String value) {
        this.tipoFirma = value;
    }

    /**
     * Recupera il valore della proprietà rt.
     * 
     * @return
     *     possible object is
     *     {@link DataHandler }
     *     
     */
    public DataHandler getRt() {
        return rt;
    }

    /**
     * Imposta il valore della proprietà rt.
     * 
     * @param value
     *     allowed object is
     *     {@link DataHandler }
     *     
     */
    public void setRt(DataHandler value) {
        this.rt = value;
    }

}
