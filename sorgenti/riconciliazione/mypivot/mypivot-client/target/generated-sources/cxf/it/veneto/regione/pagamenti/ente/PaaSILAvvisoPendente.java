
package it.veneto.regione.pagamenti.ente;

import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per paaSILAvvisoPendente complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="paaSILAvvisoPendente"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="dtCreazioneCodIuv" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="dovuti" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "paaSILAvvisoPendente", propOrder = {
    "dtCreazioneCodIuv",
    "dovuti"
})
public class PaaSILAvvisoPendente {

    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dtCreazioneCodIuv;
    @XmlMimeType("application/octet-stream")
    protected DataHandler dovuti;

    /**
     * Recupera il valore della proprietà dtCreazioneCodIuv.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDtCreazioneCodIuv() {
        return dtCreazioneCodIuv;
    }

    /**
     * Imposta il valore della proprietà dtCreazioneCodIuv.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDtCreazioneCodIuv(XMLGregorianCalendar value) {
        this.dtCreazioneCodIuv = value;
    }

    /**
     * Recupera il valore della proprietà dovuti.
     * 
     * @return
     *     possible object is
     *     {@link DataHandler }
     *     
     */
    public DataHandler getDovuti() {
        return dovuti;
    }

    /**
     * Imposta il valore della proprietà dovuti.
     * 
     * @param value
     *     allowed object is
     *     {@link DataHandler }
     *     
     */
    public void setDovuti(DataHandler value) {
        this.dovuti = value;
    }

}
