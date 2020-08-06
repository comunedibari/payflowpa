
package it.tasgroup.idp.loader;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for processSpecificationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="processSpecificationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="fileType" type="{http://idp.tasgroup.it/Loader/}stText35"/>
 *         &lt;element name="smartReplace" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="generatedIdPagamento" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "processSpecificationType", propOrder = {
    "fileType",
    "smartReplace",
    "generatedIdPagamento"
})
public class ProcessSpecificationType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected String fileType;
    protected boolean smartReplace;
    protected Boolean generatedIdPagamento;

    /**
     * Gets the value of the fileType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * Sets the value of the fileType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFileType(String value) {
        this.fileType = value;
    }

    /**
     * Gets the value of the smartReplace property.
     * 
     */
    public boolean isSmartReplace() {
        return smartReplace;
    }

    /**
     * Sets the value of the smartReplace property.
     * 
     */
    public void setSmartReplace(boolean value) {
        this.smartReplace = value;
    }

    /**
     * Gets the value of the generatedIdPagamento property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isGeneratedIdPagamento() {
        return generatedIdPagamento;
    }

    /**
     * Sets the value of the generatedIdPagamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setGeneratedIdPagamento(Boolean value) {
        this.generatedIdPagamento = value;
    }

}
