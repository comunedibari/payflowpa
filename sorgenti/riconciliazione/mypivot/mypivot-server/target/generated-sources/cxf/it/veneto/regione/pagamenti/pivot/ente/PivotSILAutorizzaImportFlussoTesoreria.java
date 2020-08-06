
package it.veneto.regione.pagamenti.pivot.ente;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per pivotSILAutorizzaImportFlussoTesoreria complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="pivotSILAutorizzaImportFlussoTesoreria"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="password" type="{http://www.regione.veneto.it/pagamenti/pivot/ente/}stPassword" minOccurs="0"/&gt;
 *         &lt;element name="tipoFlusso" type="{http://www.regione.veneto.it/pagamenti/pivot/ente/}stText1" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pivotSILAutorizzaImportFlussoTesoreria", propOrder = {
    "password",
    "tipoFlusso"
})
public class PivotSILAutorizzaImportFlussoTesoreria {

    protected String password;
    protected String tipoFlusso;

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
     * Recupera il valore della proprietà tipoFlusso.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoFlusso() {
        return tipoFlusso;
    }

    /**
     * Imposta il valore della proprietà tipoFlusso.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoFlusso(String value) {
        this.tipoFlusso = value;
    }

}
