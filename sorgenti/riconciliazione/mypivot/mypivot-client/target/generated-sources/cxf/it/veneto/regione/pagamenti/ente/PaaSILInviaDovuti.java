
package it.veneto.regione.pagamenti.ente;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per paaSILInviaDovuti complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="paaSILInviaDovuti"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="password" type="{http://www.regione.veneto.it/pagamenti/ente/}stPassword" minOccurs="0"/&gt;
 *         &lt;element name="dovuti" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/&gt;
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
@XmlType(name = "paaSILInviaDovuti", propOrder = {
    "password",
    "dovuti",
    "enteSILInviaRispostaPagamentoUrl"
})
public class PaaSILInviaDovuti {

    protected String password;
    @XmlElement(required = true)
    protected byte[] dovuti;
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
     * Recupera il valore della proprietà dovuti.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getDovuti() {
        return dovuti;
    }

    /**
     * Imposta il valore della proprietà dovuti.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setDovuti(byte[] value) {
        this.dovuti = value;
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
