
package it.tasgroup.idp.autorizzazionepagamento;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VerificaPagamentoResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VerificaPagamentoResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://idp.tasgroup.it/AutorizzazionePagamento/}ResponseBase">
 *       &lt;sequence>
 *         &lt;element name="Body" type="{http://idp.tasgroup.it/AutorizzazionePagamento/}VerificaPagamentoResponseBodyType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VerificaPagamentoResponseType", propOrder = {
    "body"
})
public class VerificaPagamentoResponseType
    extends ResponseBase
{

    @XmlElement(name = "Body")
    protected VerificaPagamentoResponseBodyType body;

    /**
     * Gets the value of the body property.
     * 
     * @return
     *     possible object is
     *     {@link VerificaPagamentoResponseBodyType }
     *     
     */
    public VerificaPagamentoResponseBodyType getBody() {
        return body;
    }

    /**
     * Sets the value of the body property.
     * 
     * @param value
     *     allowed object is
     *     {@link VerificaPagamentoResponseBodyType }
     *     
     */
    public void setBody(VerificaPagamentoResponseBodyType value) {
        this.body = value;
    }

}
