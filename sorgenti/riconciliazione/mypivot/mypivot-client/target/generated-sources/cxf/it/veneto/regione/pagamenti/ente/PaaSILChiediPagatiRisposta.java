
package it.veneto.regione.pagamenti.ente;

import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per paaSILChiediPagatiRisposta complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="paaSILChiediPagatiRisposta"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.regione.veneto.it/pagamenti/ente/}risposta"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="pagati" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "paaSILChiediPagatiRisposta", propOrder = {
    "pagati"
})
public class PaaSILChiediPagatiRisposta
    extends Risposta
{

    @XmlMimeType("application/octet-stream")
    protected DataHandler pagati;

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

}
