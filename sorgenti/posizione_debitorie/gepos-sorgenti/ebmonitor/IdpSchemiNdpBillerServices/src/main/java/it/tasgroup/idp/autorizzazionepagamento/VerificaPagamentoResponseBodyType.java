
package it.tasgroup.idp.autorizzazionepagamento;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VerificaPagamentoResponseBodyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VerificaPagamentoResponseBodyType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://idp.tasgroup.it/AutorizzazionePagamento/}RequestBase">
 *       &lt;sequence>
 *         &lt;element name="ImportoPagamento" type="{http://idp.tasgroup.it/AutorizzazionePagamento/}Importo12.2"/>
 *         &lt;element name="CausaleVersamento" type="{http://idp.tasgroup.it/AutorizzazionePagamento/}stText140"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VerificaPagamentoResponseBodyType", propOrder = {
    "importoPagamento",
    "causaleVersamento"
})
public class VerificaPagamentoResponseBodyType
    extends RequestBase
{

    @XmlElement(name = "ImportoPagamento", required = true)
    protected BigDecimal importoPagamento;
    @XmlElement(name = "CausaleVersamento", required = true)
    protected String causaleVersamento;

    /**
     * Gets the value of the importoPagamento property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImportoPagamento() {
        return importoPagamento;
    }

    /**
     * Sets the value of the importoPagamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImportoPagamento(BigDecimal value) {
        this.importoPagamento = value;
    }

    /**
     * Gets the value of the causaleVersamento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCausaleVersamento() {
        return causaleVersamento;
    }

    /**
     * Sets the value of the causaleVersamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCausaleVersamento(String value) {
        this.causaleVersamento = value;
    }

}
