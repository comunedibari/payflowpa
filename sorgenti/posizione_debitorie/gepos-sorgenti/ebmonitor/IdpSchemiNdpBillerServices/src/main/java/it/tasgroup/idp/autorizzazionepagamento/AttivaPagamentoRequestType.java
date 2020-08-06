
package it.tasgroup.idp.autorizzazionepagamento;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AttivaPagamentoRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AttivaPagamentoRequestType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://idp.tasgroup.it/AutorizzazionePagamento/}RequestBase">
 *       &lt;sequence>
 *         &lt;element name="ImportoVersamento" type="{http://idp.tasgroup.it/AutorizzazionePagamento/}Importo12.2"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AttivaPagamentoRequestType", propOrder = {
    "importoVersamento"
})
public class AttivaPagamentoRequestType
    extends RequestBase
{

    @XmlElement(name = "ImportoVersamento", required = true)
    protected BigDecimal importoVersamento;

    /**
     * Gets the value of the importoVersamento property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImportoVersamento() {
        return importoVersamento;
    }

    /**
     * Sets the value of the importoVersamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImportoVersamento(BigDecimal value) {
        this.importoVersamento = value;
    }

}
