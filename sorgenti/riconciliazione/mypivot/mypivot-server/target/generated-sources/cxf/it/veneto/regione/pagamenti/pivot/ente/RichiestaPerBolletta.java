
package it.veneto.regione.pagamenti.pivot.ente;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per richiestaPerBolletta complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="richiestaPerBolletta"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="annoBolletta" type="{http://www.regione.veneto.it/pagamenti/pivot/ente/}stText4"/&gt;
 *         &lt;element name="numeroBolletta" type="{http://www.regione.veneto.it/pagamenti/pivot/ente/}stText12"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "richiestaPerBolletta", propOrder = {
    "annoBolletta",
    "numeroBolletta"
})
public class RichiestaPerBolletta {

    @XmlElement(required = true)
    protected String annoBolletta;
    @XmlElement(required = true)
    protected String numeroBolletta;

    /**
     * Recupera il valore della proprietà annoBolletta.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnnoBolletta() {
        return annoBolletta;
    }

    /**
     * Imposta il valore della proprietà annoBolletta.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnnoBolletta(String value) {
        this.annoBolletta = value;
    }

    /**
     * Recupera il valore della proprietà numeroBolletta.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroBolletta() {
        return numeroBolletta;
    }

    /**
     * Imposta il valore della proprietà numeroBolletta.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroBolletta(String value) {
        this.numeroBolletta = value;
    }

}
