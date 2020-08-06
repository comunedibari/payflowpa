
package it.veneto.regione.pagamenti.ente.ppthead;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="codIpaEnte" type="{http://www.regione.veneto.it/pagamenti/ente/ppthead}stText35"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "codIpaEnte"
})
@XmlRootElement(name = "intestazionePPT")
public class IntestazionePPT {

    @XmlElement(required = true)
    protected String codIpaEnte;

    /**
     * Recupera il valore della proprietà codIpaEnte.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodIpaEnte() {
        return codIpaEnte;
    }

    /**
     * Imposta il valore della proprietà codIpaEnte.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodIpaEnte(String value) {
        this.codIpaEnte = value;
    }

}
