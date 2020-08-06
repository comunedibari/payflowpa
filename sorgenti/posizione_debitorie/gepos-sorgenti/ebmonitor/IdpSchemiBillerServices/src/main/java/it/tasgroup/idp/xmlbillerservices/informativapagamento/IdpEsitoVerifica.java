
package it.tasgroup.idp.xmlbillerservices.informativapagamento;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IdpBodyEsitoVerifica">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="InformazioniPagamento" type="{http://idp.tasgroup.it/xmlbillerservices/InformativaPagamento}InformazioniPagamentoType" maxOccurs="unbounded" minOccurs="0"/>
 *                   &lt;element name="Esito" type="{http://idp.tasgroup.it/xmlbillerservices/InformativaPagamento}Esito" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "idpBodyEsitoVerifica"
})
@XmlRootElement(name = "IdpEsitoVerifica")
public class IdpEsitoVerifica {

    @XmlElement(name = "IdpBodyEsitoVerifica", required = true)
    protected IdpEsitoVerifica.IdpBodyEsitoVerifica idpBodyEsitoVerifica;

    /**
     * Gets the value of the idpBodyEsitoVerifica property.
     * 
     * @return
     *     possible object is
     *     {@link IdpEsitoVerifica.IdpBodyEsitoVerifica }
     *     
     */
    public IdpEsitoVerifica.IdpBodyEsitoVerifica getIdpBodyEsitoVerifica() {
        return idpBodyEsitoVerifica;
    }

    /**
     * Sets the value of the idpBodyEsitoVerifica property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdpEsitoVerifica.IdpBodyEsitoVerifica }
     *     
     */
    public void setIdpBodyEsitoVerifica(IdpEsitoVerifica.IdpBodyEsitoVerifica value) {
        this.idpBodyEsitoVerifica = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="InformazioniPagamento" type="{http://idp.tasgroup.it/xmlbillerservices/InformativaPagamento}InformazioniPagamentoType" maxOccurs="unbounded" minOccurs="0"/>
     *         &lt;element name="Esito" type="{http://idp.tasgroup.it/xmlbillerservices/InformativaPagamento}Esito" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "informazioniPagamento",
        "esito"
    })
    public static class IdpBodyEsitoVerifica {

        @XmlElement(name = "InformazioniPagamento")
        protected List<InformazioniPagamentoType> informazioniPagamento;
        @XmlElement(name = "Esito")
        protected Esito esito;

        /**
         * Gets the value of the informazioniPagamento property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the informazioniPagamento property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getInformazioniPagamento().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link InformazioniPagamentoType }
         * 
         * 
         */
        public List<InformazioniPagamentoType> getInformazioniPagamento() {
            if (informazioniPagamento == null) {
                informazioniPagamento = new ArrayList<InformazioniPagamentoType>();
            }
            return this.informazioniPagamento;
        }

        /**
         * Gets the value of the esito property.
         * 
         * @return
         *     possible object is
         *     {@link Esito }
         *     
         */
        public Esito getEsito() {
            return esito;
        }

        /**
         * Sets the value of the esito property.
         * 
         * @param value
         *     allowed object is
         *     {@link Esito }
         *     
         */
        public void setEsito(Esito value) {
            this.esito = value;
        }

    }

}
