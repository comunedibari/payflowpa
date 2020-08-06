
package it.veneto.regione.pagamenti.ente;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per paaSILVerificaAvviso complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="paaSILVerificaAvviso"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="password" type="{http://www.regione.veneto.it/pagamenti/ente/}stPassword" minOccurs="0"/&gt;
 *         &lt;element name="identificativoUnivocoVersamento" type="{http://www.regione.veneto.it/pagamenti/ente/}stText35"/&gt;
 *         &lt;element name="enteSILInviaRispostaPagamentoUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "paaSILVerificaAvviso", propOrder = {
    "password",
    "identificativoUnivocoVersamento",
    "enteSILInviaRispostaPagamentoUrl"
})
public class PaaSILVerificaAvviso {

    protected String password;
    @XmlElement(required = true)
    protected String identificativoUnivocoVersamento;
    @XmlElement(defaultValue = "")
    protected String enteSILInviaRispostaPagamentoUrl;

    /**
     * Recupera il valore della proprietà password.
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
     * Imposta il valore della proprietà password.
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
     * Recupera il valore della proprietà identificativoUnivocoVersamento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativoUnivocoVersamento() {
        return identificativoUnivocoVersamento;
    }

    /**
     * Imposta il valore della proprietà identificativoUnivocoVersamento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativoUnivocoVersamento(String value) {
        this.identificativoUnivocoVersamento = value;
    }

    /**
     * Recupera il valore della proprietà enteSILInviaRispostaPagamentoUrl.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnteSILInviaRispostaPagamentoUrl() {
        return enteSILInviaRispostaPagamentoUrl;
    }

    /**
     * Imposta il valore della proprietà enteSILInviaRispostaPagamentoUrl.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnteSILInviaRispostaPagamentoUrl(String value) {
        this.enteSILInviaRispostaPagamentoUrl = value;
    }

}
