
package it.toscana.rete.cart.servizi.iris_1_1.idpinformativapagamento;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import it.toscana.rete.cart.servizi.iris_1_1.idpheader.IdpHeader;


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
 *         &lt;element ref="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpHeader}IdpHeader"/>
 *         &lt;element name="IdpBody">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="IdPagamento" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInformativaPagamento}idPagamento" maxOccurs="unbounded"/>
 *                   &lt;element name="richiestaInformazioniPagamento" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="Versione" use="required" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInformativaPagamento}Versione" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "idpHeader",
    "idpBody"
})
@XmlRootElement(name = "IdpVerificaStatoPagamento")
public class IdpVerificaStatoPagamento {

    @XmlElement(name = "IdpHeader", namespace = "http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpHeader", required = true)
    protected IdpHeader idpHeader;
    @XmlElement(name = "IdpBody", required = true)
    protected IdpVerificaStatoPagamento.IdpBody idpBody;
    @XmlAttribute(name = "Versione", required = true)
    protected String versione;

    /**
     * Gets the value of the idpHeader property.
     * 
     * @return
     *     possible object is
     *     {@link IdpHeader }
     *     
     */
    public IdpHeader getIdpHeader() {
        return idpHeader;
    }

    /**
     * Sets the value of the idpHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdpHeader }
     *     
     */
    public void setIdpHeader(IdpHeader value) {
        this.idpHeader = value;
    }

    /**
     * Gets the value of the idpBody property.
     * 
     * @return
     *     possible object is
     *     {@link IdpVerificaStatoPagamento.IdpBody }
     *     
     */
    public IdpVerificaStatoPagamento.IdpBody getIdpBody() {
        return idpBody;
    }

    /**
     * Sets the value of the idpBody property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdpVerificaStatoPagamento.IdpBody }
     *     
     */
    public void setIdpBody(IdpVerificaStatoPagamento.IdpBody value) {
        this.idpBody = value;
    }

    /**
     * Gets the value of the versione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersione() {
        return versione;
    }

    /**
     * Sets the value of the versione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersione(String value) {
        this.versione = value;
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
     *         &lt;element name="IdPagamento" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInformativaPagamento}idPagamento" maxOccurs="unbounded"/>
     *         &lt;element name="richiestaInformazioniPagamento" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
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
        "idPagamento",
        "richiestaInformazioniPagamento"
    })
    public static class IdpBody {

        @XmlElement(name = "IdPagamento", required = true)
        protected List<IdPagamento> idPagamento;
        protected Boolean richiestaInformazioniPagamento;

        /**
         * Gets the value of the idPagamento property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the idPagamento property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getIdPagamento().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link IdPagamento }
         * 
         * 
         */
        public List<IdPagamento> getIdPagamento() {
            if (idPagamento == null) {
                idPagamento = new ArrayList<IdPagamento>();
            }
            return this.idPagamento;
        }

        /**
         * Gets the value of the richiestaInformazioniPagamento property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isRichiestaInformazioniPagamento() {
            return richiestaInformazioniPagamento;
        }

        /**
         * Sets the value of the richiestaInformazioniPagamento property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setRichiestaInformazioniPagamento(Boolean value) {
            this.richiestaInformazioniPagamento = value;
        }

    }

}
