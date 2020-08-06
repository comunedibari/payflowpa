
package gov.telematici.pagamenti.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for esitoVerificaRPT complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="esitoVerificaRPT">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.pagamenti.telematici.gov/}risposta">
 *       &lt;sequence>
 *         &lt;element name="esito" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="datiPagamentoPA" type="{http://ws.pagamenti.telematici.gov/}paaTipoDatiPagamentoPA" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "esitoVerificaRPT", propOrder = {
    "esito",
    "datiPagamentoPA"
})
public class EsitoVerificaRPT
    extends Risposta
{

    protected String esito;
    protected PaaTipoDatiPagamentoPA datiPagamentoPA;

    /**
     * Gets the value of the esito property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEsito() {
        return esito;
    }

    /**
     * Sets the value of the esito property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEsito(String value) {
        this.esito = value;
    }

    /**
     * Gets the value of the datiPagamentoPA property.
     * 
     * @return
     *     possible object is
     *     {@link PaaTipoDatiPagamentoPA }
     *     
     */
    public PaaTipoDatiPagamentoPA getDatiPagamentoPA() {
        return datiPagamentoPA;
    }

    /**
     * Sets the value of the datiPagamentoPA property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaaTipoDatiPagamentoPA }
     *     
     */
    public void setDatiPagamentoPA(PaaTipoDatiPagamentoPA value) {
        this.datiPagamentoPA = value;
    }

}
