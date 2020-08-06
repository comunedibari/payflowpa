
package it.tasgroup.idp.generazioneiuv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for GeneraIUVResponseBobyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GeneraIUVResponseBobyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IdRichiesta" type="{http://idp.tasgroup.it/GenerazioneIUV/}MsgId"/>
 *         &lt;element name="DataOraRichiesta" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="identificativoDominio" type="{http://idp.tasgroup.it/GenerazioneIUV/}stText35"/>
 *         &lt;element name="TipoPendenza" type="{http://idp.tasgroup.it/GenerazioneIUV/}stText50"/>
 *         &lt;element name="ElencoIdentificativi" type="{http://idp.tasgroup.it/GenerazioneIUV/}ElencoIdentificativiType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GeneraIUVResponseBobyType", propOrder = {
    "idRichiesta",
    "dataOraRichiesta",
    "identificativoDominio",
    "tipoPendenza",
    "elencoIdentificativi"
})
public class GeneraIUVResponseBobyType {

    @XmlElement(name = "IdRichiesta", required = true)
    protected String idRichiesta;
    @XmlElement(name = "DataOraRichiesta", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataOraRichiesta;
    @XmlElement(required = true)
    protected String identificativoDominio;
    @XmlElement(name = "TipoPendenza", required = true)
    protected String tipoPendenza;
    @XmlElement(name = "ElencoIdentificativi", required = true)
    protected ElencoIdentificativiType elencoIdentificativi;

    /**
     * Gets the value of the idRichiesta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdRichiesta() {
        return idRichiesta;
    }

    /**
     * Sets the value of the idRichiesta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdRichiesta(String value) {
        this.idRichiesta = value;
    }

    /**
     * Gets the value of the dataOraRichiesta property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataOraRichiesta() {
        return dataOraRichiesta;
    }

    /**
     * Sets the value of the dataOraRichiesta property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataOraRichiesta(XMLGregorianCalendar value) {
        this.dataOraRichiesta = value;
    }

    /**
     * Gets the value of the identificativoDominio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativoDominio() {
        return identificativoDominio;
    }

    /**
     * Sets the value of the identificativoDominio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativoDominio(String value) {
        this.identificativoDominio = value;
    }

    /**
     * Gets the value of the tipoPendenza property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoPendenza() {
        return tipoPendenza;
    }

    /**
     * Sets the value of the tipoPendenza property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoPendenza(String value) {
        this.tipoPendenza = value;
    }

    /**
     * Gets the value of the elencoIdentificativi property.
     * 
     * @return
     *     possible object is
     *     {@link ElencoIdentificativiType }
     *     
     */
    public ElencoIdentificativiType getElencoIdentificativi() {
        return elencoIdentificativi;
    }

    /**
     * Sets the value of the elencoIdentificativi property.
     * 
     * @param value
     *     allowed object is
     *     {@link ElencoIdentificativiType }
     *     
     */
    public void setElencoIdentificativi(ElencoIdentificativiType value) {
        this.elencoIdentificativi = value;
    }

}
