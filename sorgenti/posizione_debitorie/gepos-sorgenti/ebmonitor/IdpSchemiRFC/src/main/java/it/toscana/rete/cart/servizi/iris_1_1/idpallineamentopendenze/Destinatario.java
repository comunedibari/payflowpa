
package it.toscana.rete.cart.servizi.iris_1_1.idpallineamentopendenze;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import it.toscana.rete.cart.servizi.iris_1_1.idpinclude.TipoDestinatario;


/**
 * <p>Java class for Destinatario complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Destinatario">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Id" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}IdentificativoIdp"/>
 *         &lt;element name="Descrizione" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max70Text" minOccurs="0"/>
 *         &lt;element name="DatiDestinatario" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpAllineamentoPendenze}DatiDestinatarioType" minOccurs="0"/>
 *         &lt;element name="IdentificativoAlternativo" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpAllineamentoPendenze}IdentificativoAlternativoType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="Tipo" use="required" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}TipoDestinatario" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Destinatario", propOrder = {
    "id",
    "descrizione",
    "datiDestinatario",
    "identificativoAlternativo"
})
public class Destinatario {

    @XmlElement(name = "Id", required = true)
    protected String id;
    @XmlElement(name = "Descrizione")
    protected String descrizione;
    @XmlElement(name = "DatiDestinatario")
    protected DatiDestinatarioType datiDestinatario;
    @XmlElement(name = "IdentificativoAlternativo")
    protected IdentificativoAlternativoType identificativoAlternativo;
    @XmlAttribute(name = "Tipo", required = true)
    protected TipoDestinatario tipo;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the descrizione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Sets the value of the descrizione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizione(String value) {
        this.descrizione = value;
    }

    /**
     * Gets the value of the datiDestinatario property.
     * 
     * @return
     *     possible object is
     *     {@link DatiDestinatarioType }
     *     
     */
    public DatiDestinatarioType getDatiDestinatario() {
        return datiDestinatario;
    }

    /**
     * Sets the value of the datiDestinatario property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiDestinatarioType }
     *     
     */
    public void setDatiDestinatario(DatiDestinatarioType value) {
        this.datiDestinatario = value;
    }

    /**
     * Gets the value of the identificativoAlternativo property.
     * 
     * @return
     *     possible object is
     *     {@link IdentificativoAlternativoType }
     *     
     */
    public IdentificativoAlternativoType getIdentificativoAlternativo() {
        return identificativoAlternativo;
    }

    /**
     * Sets the value of the identificativoAlternativo property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdentificativoAlternativoType }
     *     
     */
    public void setIdentificativoAlternativo(IdentificativoAlternativoType value) {
        this.identificativoAlternativo = value;
    }

    /**
     * Gets the value of the tipo property.
     * 
     * @return
     *     possible object is
     *     {@link TipoDestinatario }
     *     
     */
    public TipoDestinatario getTipo() {
        return tipo;
    }

    /**
     * Sets the value of the tipo property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoDestinatario }
     *     
     */
    public void setTipo(TipoDestinatario value) {
        this.tipo = value;
    }

}
