
package it.veneto.regione.pagamenti.pivot.ente;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per pivotSILChiediAccertamento complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="pivotSILChiediAccertamento"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="password" type="{http://www.regione.veneto.it/pagamenti/pivot/ente/}stPassword" minOccurs="0"/&gt;
 *         &lt;choice&gt;
 *           &lt;element name="richiestaPerBolletta" type="{http://www.regione.veneto.it/pagamenti/pivot/ente/}richiestaPerBolletta"/&gt;
 *           &lt;element name="richiestaPerIUF" type="{http://www.regione.veneto.it/pagamenti/pivot/ente/}richiestaPerIUF"/&gt;
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
@XmlType(name = "pivotSILChiediAccertamento", propOrder = {
    "password",
    "richiestaPerBolletta",
    "richiestaPerIUF"
})
public class PivotSILChiediAccertamento {

    protected String password;
    protected RichiestaPerBolletta richiestaPerBolletta;
    protected RichiestaPerIUF richiestaPerIUF;

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
     * Recupera il valore della proprietà richiestaPerBolletta.
     * 
     * @return
     *     possible object is
     *     {@link RichiestaPerBolletta }
     *     
     */
    public RichiestaPerBolletta getRichiestaPerBolletta() {
        return richiestaPerBolletta;
    }

    /**
     * Imposta il valore della proprietà richiestaPerBolletta.
     * 
     * @param value
     *     allowed object is
     *     {@link RichiestaPerBolletta }
     *     
     */
    public void setRichiestaPerBolletta(RichiestaPerBolletta value) {
        this.richiestaPerBolletta = value;
    }

    /**
     * Recupera il valore della proprietà richiestaPerIUF.
     * 
     * @return
     *     possible object is
     *     {@link RichiestaPerIUF }
     *     
     */
    public RichiestaPerIUF getRichiestaPerIUF() {
        return richiestaPerIUF;
    }

    /**
     * Imposta il valore della proprietà richiestaPerIUF.
     * 
     * @param value
     *     allowed object is
     *     {@link RichiestaPerIUF }
     *     
     */
    public void setRichiestaPerIUF(RichiestaPerIUF value) {
        this.richiestaPerIUF = value;
    }

}
