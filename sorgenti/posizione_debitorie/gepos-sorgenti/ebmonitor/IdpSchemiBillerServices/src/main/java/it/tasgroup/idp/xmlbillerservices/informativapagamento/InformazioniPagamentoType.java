
package it.tasgroup.idp.xmlbillerservices.informativapagamento;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InformazioniPagamentoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InformazioniPagamentoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IdPagamento" type="{http://idp.tasgroup.it/xmlbillerservices/Common}IdentificativoIdp"/>
 *         &lt;element name="TipoDebito" type="{http://idp.tasgroup.it/xmlbillerservices/Common}Max50Text"/>
 *         &lt;element name="Stato" type="{http://idp.tasgroup.it/xmlbillerservices/InformativaPagamento}VerificaStatoPagamentoDettagliato"/>
 *         &lt;element name="DescrizioneStato" type="{http://idp.tasgroup.it/xmlbillerservices/Common}Max256Text"/>
 *         &lt;element name="Pagamento" type="{http://idp.tasgroup.it/xmlbillerservices/InformativaPagamento}Pagamento" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InformazioniPagamentoType", propOrder = {
    "idPagamento",
    "tipoDebito",
    "stato",
    "descrizioneStato",
    "pagamento"
})
public class InformazioniPagamentoType {

    @XmlElement(name = "IdPagamento", required = true)
    protected String idPagamento;
    @XmlElement(name = "TipoDebito", required = true)
    protected String tipoDebito;
    @XmlElement(name = "Stato", required = true)
    protected VerificaStatoPagamentoDettagliato stato;
    @XmlElement(name = "DescrizioneStato", required = true)
    protected String descrizioneStato;
    @XmlElement(name = "Pagamento")
    protected Pagamento pagamento;

    /**
     * Gets the value of the idPagamento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdPagamento() {
        return idPagamento;
    }

    /**
     * Sets the value of the idPagamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdPagamento(String value) {
        this.idPagamento = value;
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
     * Gets the value of the stato property.
     * 
     * @return
     *     possible object is
     *     {@link VerificaStatoPagamentoDettagliato }
     *     
     */
    public VerificaStatoPagamentoDettagliato getStato() {
        return stato;
    }

    /**
     * Sets the value of the stato property.
     * 
     * @param value
     *     allowed object is
     *     {@link VerificaStatoPagamentoDettagliato }
     *     
     */
    public void setStato(VerificaStatoPagamentoDettagliato value) {
        this.stato = value;
    }

    /**
     * Gets the value of the descrizioneStato property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneStato() {
        return descrizioneStato;
    }

    /**
     * Sets the value of the descrizioneStato property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneStato(String value) {
        this.descrizioneStato = value;
    }

    /**
     * Gets the value of the pagamento property.
     * 
     * @return
     *     possible object is
     *     {@link Pagamento }
     *     
     */
    public Pagamento getPagamento() {
        return pagamento;
    }

    /**
     * Sets the value of the pagamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link Pagamento }
     *     
     */
    public void setPagamento(Pagamento value) {
        this.pagamento = value;
    }

}
