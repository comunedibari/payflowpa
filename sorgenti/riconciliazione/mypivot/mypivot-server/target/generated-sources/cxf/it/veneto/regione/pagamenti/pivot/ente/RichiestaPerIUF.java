
package it.veneto.regione.pagamenti.pivot.ente;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per richiestaPerIUF complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="richiestaPerIUF"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="identificativoUnivocoFlusso" type="{http://www.regione.veneto.it/pagamenti/pivot/ente/}stText35"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "richiestaPerIUF", propOrder = {
    "identificativoUnivocoFlusso"
})
public class RichiestaPerIUF {

    @XmlElement(required = true)
    protected String identificativoUnivocoFlusso;

    /**
     * Recupera il valore della proprietà identificativoUnivocoFlusso.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativoUnivocoFlusso() {
        return identificativoUnivocoFlusso;
    }

    /**
     * Imposta il valore della proprietà identificativoUnivocoFlusso.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativoUnivocoFlusso(String value) {
        this.identificativoUnivocoFlusso = value;
    }

}
