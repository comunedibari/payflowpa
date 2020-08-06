
package it.toscana.rete.cart.servizi.iris_1_1.idpesito;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import it.toscana.rete.cart.servizi.iris_1_1.idpinformativapagamento.Pagamento;


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
 *         &lt;element name="IdPagamento" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}IdentificativoIdp"/>
 *         &lt;element name="TipoPendenza" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max35Text"/>
 *         &lt;element name="Stato" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpEsito}VerificaStatoPagamentoDettagliato"/>
 *         &lt;element name="DescrizioneStato" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max256Text"/>
 *         &lt;element name="Pagamento" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInformativaPagamento}Pagamento" minOccurs="0"/>
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
    "tipoPendenza",
    "stato",
    "descrizioneStato",
    "pagamento"
})
public class InformazioniPagamentoType {

    @XmlElement(name = "IdPagamento", required = true)
    protected String idPagamento;
    @XmlElement(name = "TipoPendenza", required = true)
    protected String tipoPendenza;
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
