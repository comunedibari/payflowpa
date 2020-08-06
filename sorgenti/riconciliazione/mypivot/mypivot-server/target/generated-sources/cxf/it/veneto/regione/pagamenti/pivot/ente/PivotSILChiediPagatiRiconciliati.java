
package it.veneto.regione.pagamenti.pivot.ente;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per pivotSILChiediPagatiRiconciliati complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="pivotSILChiediPagatiRiconciliati"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="codIpaEnte" type="{http://www.regione.veneto.it/pagamenti/pivot/ente/}stText35"/&gt;
 *         &lt;element name="password" type="{http://www.regione.veneto.it/pagamenti/pivot/ente/}stPassword" minOccurs="0"/&gt;
 *         &lt;choice&gt;
 *           &lt;element name="richiestaPerIUVIUF" type="{http://www.regione.veneto.it/pagamenti/pivot/ente/}ctRichiestaPerIUVIUF"/&gt;
 *           &lt;element name="richiestaPerData" type="{http://www.regione.veneto.it/pagamenti/pivot/ente/}ctRichiestaPerData"/&gt;
 *         &lt;/choice&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pivotSILChiediPagatiRiconciliati", propOrder = {
    "codIpaEnte",
    "password",
    "richiestaPerIUVIUF",
    "richiestaPerData"
})
public class PivotSILChiediPagatiRiconciliati {

    @XmlElement(required = true)
    protected String codIpaEnte;
    protected String password;
    protected CtRichiestaPerIUVIUF richiestaPerIUVIUF;
    protected CtRichiestaPerData richiestaPerData;

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

    /**
     * Recupera il valore della proprietà password.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassword() {
        return password;
    }

    /**
     * Imposta il valore della proprietà password.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * Recupera il valore della proprietà richiestaPerIUVIUF.
     * 
     * @return
     *     possible object is
     *     {@link CtRichiestaPerIUVIUF }
     *     
     */
    public CtRichiestaPerIUVIUF getRichiestaPerIUVIUF() {
        return richiestaPerIUVIUF;
    }

    /**
     * Imposta il valore della proprietà richiestaPerIUVIUF.
     * 
     * @param value
     *     allowed object is
     *     {@link CtRichiestaPerIUVIUF }
     *     
     */
    public void setRichiestaPerIUVIUF(CtRichiestaPerIUVIUF value) {
        this.richiestaPerIUVIUF = value;
    }

    /**
     * Recupera il valore della proprietà richiestaPerData.
     * 
     * @return
     *     possible object is
     *     {@link CtRichiestaPerData }
     *     
     */
    public CtRichiestaPerData getRichiestaPerData() {
        return richiestaPerData;
    }

    /**
     * Imposta il valore della proprietà richiestaPerData.
     * 
     * @param value
     *     allowed object is
     *     {@link CtRichiestaPerData }
     *     
     */
    public void setRichiestaPerData(CtRichiestaPerData value) {
        this.richiestaPerData = value;
    }

}
