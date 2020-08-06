
package it.tasgroup.idp.generazioneiuv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GeneraLottoIUVResponseBodyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GeneraLottoIUVResponseBodyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IdentificativoDominio" type="{http://idp.tasgroup.it/GenerazioneIUV/}stText35"/>
 *         &lt;element name="TipoDebito" type="{http://idp.tasgroup.it/GenerazioneIUV/}stText50"/>
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
@XmlType(name = "GeneraLottoIUVResponseBodyType", propOrder = {
    "identificativoDominio",
    "tipoDebito",
    "dimensioneLotto",
    "elencoIdentificativi"
})
public class GeneraLottoIUVResponseBodyType {

    @XmlElement(name = "IdentificativoDominio", required = true)
    protected String identificativoDominio;
    @XmlElement(name = "TipoDebito", required = true)
    protected String tipoDebito;
    @XmlElement(name = "DimensioneLotto")
    protected int dimensioneLotto;
    @XmlElement(name = "ElencoIdentificativi", required = true)
    protected List<ElencoIdentificativiType> elencoIdentificativi;

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
     * Gets the value of the tipoDebito property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoDebito() {
        return tipoDebito;
    }

    /**
     * Sets the value of the tipoDebito property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoDebito(String value) {
        this.tipoDebito = value;
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
