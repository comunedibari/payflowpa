
package it.tasgroup.idp.generazioneiuv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GeneraIUVResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GeneraIUVResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://idp.tasgroup.it/GenerazioneIUV/}ResponseBase">
 *       &lt;sequence>
 *         &lt;element name="Body" type="{http://idp.tasgroup.it/GenerazioneIUV/}GeneraIUVResponseBodyType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GeneraIUVResponseType", propOrder = {
    "body"
})
public class GeneraIUVResponseType
    extends ResponseBase
{

    @XmlElement(name = "Body")
    protected GeneraIUVResponseBodyType body;

    /**
     * Gets the value of the body property.
     * 
     * @return
     *     possible object is
     *     {@link GeneraIUVResponseBodyType }
     *     
     */
    public GeneraIUVResponseBodyType getBody() {
        return body;
    }

    /**
     * Sets the value of the body property.
     * 
     * @param value
     *     allowed object is
     *     {@link GeneraIUVResponseBodyType }
     *     
     */
    public void setBody(GeneraIUVResponseBodyType value) {
        this.body = value;
    }

}
