
package it.tasgroup.idp.loader;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for fileType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="fileType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="base64File" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         &lt;element name="textFile" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fileType", propOrder = {
    "base64File",
    "textFile"
})
public class FileType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected byte[] base64File;
    protected String textFile;

    /**
     * Gets the value of the base64File property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getBase64File() {
        return base64File;
    }

    /**
     * Sets the value of the base64File property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setBase64File(byte[] value) {
        this.base64File = ((byte[]) value);
    }

    /**
     * Gets the value of the textFile property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTextFile() {
        return textFile;
    }

    /**
     * Sets the value of the textFile property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTextFile(String value) {
        this.textFile = value;
    }

}
