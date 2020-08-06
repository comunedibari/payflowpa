
package it.tasgroup.idp.generazioneiuv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GeneraLottoIUVResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GeneraLottoIUVResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://idp.tasgroup.it/GenerazioneIUV/}ResponseBase">
 *       &lt;sequence>
 *         &lt;element name="Body" type="{http://idp.tasgroup.it/GenerazioneIUV/}GeneraLottoIUVResponseBodyType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GeneraLottoIUVResponseType", propOrder = {
    "body"
})
public class GeneraLottoIUVResponseType
    extends ResponseBase
{

    @XmlElement(name = "Body")
    protected GeneraLottoIUVResponseBodyType body;

    /**
     * Gets the value of the body property.
     * 
     * @return
     *     possible object is
     *     {@link GeneraLottoIUVResponseBodyType }
     *     
     */
    public GeneraLottoIUVResponseBodyType getBody() {
        return body;
    }

    /**
     * Sets the value of the body property.
     * 
     * @param value
     *     allowed object is
     *     {@link GeneraLottoIUVResponseBodyType }
     *     
     */
    public void setBody(GeneraLottoIUVResponseBodyType value) {
        this.body = value;
    }

}
