
package gov.telematici.pagamenti.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for nodoInviaCarrelloRPT complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="nodoInviaCarrelloRPT">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="password" type="{http://ws.pagamenti.telematici.gov/}stPassword"/>
 *         &lt;element name="identificativoPSP" type="{http://ws.pagamenti.telematici.gov/}stText35"/>
 *         &lt;element name="identificativoIntermediarioPSP" type="{http://ws.pagamenti.telematici.gov/}stText35"/>
 *         &lt;element name="identificativoCanale" type="{http://ws.pagamenti.telematici.gov/}stText35"/>
 *         &lt;element name="listaRPT" type="{http://ws.pagamenti.telematici.gov/}tipoListaRPT"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "nodoInviaCarrelloRPT", propOrder = {
    "password",
    "identificativoPSP",
    "identificativoIntermediarioPSP",
    "identificativoCanale",
    "listaRPT"
})
public class NodoInviaCarrelloRPT {

    @XmlElement(required = true)
    protected String password;
    @XmlElement(required = true)
    protected String identificativoPSP;
    @XmlElement(required = true)
    protected String identificativoIntermediarioPSP;
    @XmlElement(required = true)
    protected String identificativoCanale;
    @XmlElement(required = true)
    protected TipoListaRPT listaRPT;

    /**
     * Gets the value of the password property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the value of the password property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * Gets the value of the identificativoPSP property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativoPSP() {
        return identificativoPSP;
    }

    /**
     * Sets the value of the identificativoPSP property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativoPSP(String value) {
        this.identificativoPSP = value;
    }

    /**
     * Gets the value of the identificativoIntermediarioPSP property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativoIntermediarioPSP() {
        return identificativoIntermediarioPSP;
    }

    /**
     * Sets the value of the identificativoIntermediarioPSP property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativoIntermediarioPSP(String value) {
        this.identificativoIntermediarioPSP = value;
    }

    /**
     * Gets the value of the identificativoCanale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativoCanale() {
        return identificativoCanale;
    }

    /**
     * Sets the value of the identificativoCanale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativoCanale(String value) {
        this.identificativoCanale = value;
    }

    /**
     * Gets the value of the listaRPT property.
     * 
     * @return
     *     possible object is
     *     {@link TipoListaRPT }
     *     
     */
    public TipoListaRPT getListaRPT() {
        return listaRPT;
    }

    /**
     * Sets the value of the listaRPT property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoListaRPT }
     *     
     */
    public void setListaRPT(TipoListaRPT value) {
        this.listaRPT = value;
    }

}
