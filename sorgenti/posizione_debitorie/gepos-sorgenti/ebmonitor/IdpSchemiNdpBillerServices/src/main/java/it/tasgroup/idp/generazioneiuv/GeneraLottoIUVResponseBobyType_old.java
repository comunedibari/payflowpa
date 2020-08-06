
package it.tasgroup.idp.generazioneiuv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for GeneraLottoIUVResponseBobyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GeneraLottoIUVResponseBobyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IdRichiesta" type="{http://idp.tasgroup.it/GenerazioneIUV/}MsgId"/>
 *         &lt;element name="DataOraRichiesta" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="identificativoDominio" type="{http://idp.tasgroup.it/GenerazioneIUV/}stText35"/>
 *         &lt;element name="TipoPendenza" type="{http://idp.tasgroup.it/GenerazioneIUV/}stText50"/>
 *         &lt;element name="DimensioneLotto" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ElencoIdentificativi" type="{http://idp.tasgroup.it/GenerazioneIUV/}ElencoIdentificativiType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GeneraLottoIUVResponseBobyType", propOrder = {
    "idRichiesta",
    "dataOraRichiesta",
    "identificativoDominio",
    "tipoPendenza",
    "dimensioneLotto",
    "elencoIdentificativi"
})
public class GeneraLottoIUVResponseBobyType_old {

    @XmlElement(name = "IdRichiesta", required = true)
    protected String idRichiesta;
    @XmlElement(name = "DataOraRichiesta", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataOraRichiesta;
    @XmlElement(required = true)
    protected String identificativoDominio;
    @XmlElement(name = "TipoPendenza", required = true)
    protected String tipoPendenza;
    @XmlElement(name = "DimensioneLotto")
    protected int dimensioneLotto;
    @XmlElement(name = "ElencoIdentificativi", required = true)
    protected List<ElencoIdentificativiType> elencoIdentificativi;

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
     * Gets the value of the dimensioneLotto property.
     * 
     */
    public int getDimensioneLotto() {
        return dimensioneLotto;
    }

    /**
     * Sets the value of the dimensioneLotto property.
     * 
     */
    public void setDimensioneLotto(int value) {
        this.dimensioneLotto = value;
    }

    /**
     * Gets the value of the elencoIdentificativi property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the elencoIdentificativi property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getElencoIdentificativi().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ElencoIdentificativiType }
     * 
     * 
     */
    public List<ElencoIdentificativiType> getElencoIdentificativi() {
        if (elencoIdentificativi == null) {
            elencoIdentificativi = new ArrayList<ElencoIdentificativiType>();
        }
        return this.elencoIdentificativi;
    }

}
