
package it.tasgroup.idp.autorizzazionepagamento;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AttivaPagamentoResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AttivaPagamentoResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://idp.tasgroup.it/AutorizzazionePagamento/}ResponseBase">
 *       &lt;sequence>
 *         &lt;element name="Body" type="{http://idp.tasgroup.it/AutorizzazionePagamento/}AttivaPagamentoResponseBodyType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AttivaPagamentoResponseType", propOrder = {
    "body"
})
public class AttivaPagamentoResponseType
    extends ResponseBase
{

    @XmlElement(name = "Body")
    protected AttivaPagamentoResponseBodyType body;

    /**
     * Gets the value of the body property.
     * 
     * @return
     *     possible object is
     *     {@link AttivaPagamentoResponseBodyType }
     *     
     */
    public AttivaPagamentoResponseBodyType getBody() {
        return body;
    }

    /**
     * Sets the value of the body property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttivaPagamentoResponseBodyType }
     *     
     */
    public void setBody(AttivaPagamentoResponseBodyType value) {
        this.body = value;
    }

}
