
package it.tasgroup.idp.xmlbillerservices.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Allegato complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Allegato">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Titolo" type="{http://idp.tasgroup.it/xmlbillerservices/Common}Max70Text" minOccurs="0"/>
 *         &lt;element name="Codifica" type="{http://idp.tasgroup.it/xmlbillerservices/Common}MIMETypeCode"/>
 *         &lt;element name="Contenuto" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         &lt;element name="IdAntifalsificazione" type="{http://idp.tasgroup.it/xmlbillerservices/Common}Max4096Text" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="Tipo" use="required" type="{http://idp.tasgroup.it/xmlbillerservices/Common}ContentType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Allegato", propOrder = {
    "titolo",
    "codifica",
    "contenuto",
    "idAntifalsificazione"
})
public class Allegato {

    @XmlElement(name = "Titolo")
    protected String titolo;
    @XmlElement(name = "Codifica", required = true)
    protected MIMETypeCode codifica;
    @XmlElement(name = "Contenuto", required = true)
    protected byte[] contenuto;
    @XmlElement(name = "IdAntifalsificazione")
    protected String idAntifalsificazione;
    @XmlAttribute(name = "Tipo", required = true)
    protected ContentType tipo;

    /**
     * Gets the value of the titolo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitolo() {
        return titolo;
    }

    /**
     * Sets the value of the titolo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitolo(String value) {
        this.titolo = value;
    }

    /**
     * Gets the value of the codifica property.
     * 
     * @return
     *     possible object is
     *     {@link MIMETypeCode }
     *     
     */
    public MIMETypeCode getCodifica() {
        return codifica;
    }

    /**
     * Sets the value of the codifica property.
     * 
     * @param value
     *     allowed object is
     *     {@link MIMETypeCode }
     *     
     */
    public void setCodifica(MIMETypeCode value) {
        this.codifica = value;
    }

    /**
     * Gets the value of the contenuto property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getContenuto() {
        return contenuto;
    }

    /**
     * Sets the value of the contenuto property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setContenuto(byte[] value) {
        this.contenuto = ((byte[]) value);
    }

    /**
     * Gets the value of the idAntifalsificazione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdAntifalsificazione() {
        return idAntifalsificazione;
    }

    /**
     * Sets the value of the idAntifalsificazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdAntifalsificazione(String value) {
        this.idAntifalsificazione = value;
    }

    /**
     * Gets the value of the tipo property.
     * 
     * @return
     *     possible object is
     *     {@link ContentType }
     *     
     */
    public ContentType getTipo() {
        return tipo;
    }

    /**
     * Sets the value of the tipo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContentType }
     *     
     */
    public void setTipo(ContentType value) {
        this.tipo = value;
    }

}
