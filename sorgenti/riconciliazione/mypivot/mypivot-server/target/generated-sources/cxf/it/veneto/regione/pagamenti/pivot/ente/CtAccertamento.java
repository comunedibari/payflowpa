
package it.veneto.regione.pagamenti.pivot.ente;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ctAccertamento complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ctAccertamento"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="codAccertamento" type="{http://www.regione.veneto.it/pagamenti/pivot/ente/}stText35"/&gt;
 *         &lt;element name="importo" type="{http://www.digitpa.gov.it/schemas/2011/Pagamenti/}stImporto"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ctAccertamento", propOrder = {
    "codAccertamento",
    "importo"
})
public class CtAccertamento {

    @XmlElement(required = true)
    protected String codAccertamento;
    @XmlElement(required = true)
    protected BigDecimal importo;

    /**
     * Recupera il valore della proprietà codAccertamento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodAccertamento() {
        return codAccertamento;
    }

    /**
     * Imposta il valore della proprietà codAccertamento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodAccertamento(String value) {
        this.codAccertamento = value;
    }

    /**
     * Recupera il valore della proprietà importo.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImporto() {
        return importo;
    }

    /**
     * Imposta il valore della proprietà importo.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImporto(BigDecimal value) {
        this.importo = value;
    }

}
