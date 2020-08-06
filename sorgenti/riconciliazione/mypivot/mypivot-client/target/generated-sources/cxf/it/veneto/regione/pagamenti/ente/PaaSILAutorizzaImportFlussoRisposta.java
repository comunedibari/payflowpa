
package it.veneto.regione.pagamenti.ente;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per paaSILAutorizzaImportFlussoRisposta complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="paaSILAutorizzaImportFlussoRisposta"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.regione.veneto.it/pagamenti/ente/}risposta"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="uploadUrl" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="authorizationToken" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="requestToken" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="importPath" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "paaSILAutorizzaImportFlussoRisposta", propOrder = {
    "uploadUrl",
    "authorizationToken",
    "requestToken",
    "importPath"
})
public class PaaSILAutorizzaImportFlussoRisposta
    extends Risposta
{

    @XmlElement(required = true)
    protected String uploadUrl;
    @XmlElement(required = true)
    protected String authorizationToken;
    @XmlElement(required = true)
    protected String requestToken;
    @XmlElement(required = true)
    protected String importPath;

    /**
     * Recupera il valore della proprietà uploadUrl.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUploadUrl() {
        return uploadUrl;
    }

    /**
     * Imposta il valore della proprietà uploadUrl.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUploadUrl(String value) {
        this.uploadUrl = value;
    }

    /**
     * Recupera il valore della proprietà authorizationToken.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthorizationToken() {
        return authorizationToken;
    }

    /**
     * Imposta il valore della proprietà authorizationToken.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthorizationToken(String value) {
        this.authorizationToken = value;
    }

    /**
     * Recupera il valore della proprietà requestToken.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestToken() {
        return requestToken;
    }

    /**
     * Imposta il valore della proprietà requestToken.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestToken(String value) {
        this.requestToken = value;
    }

    /**
     * Recupera il valore della proprietà importPath.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImportPath() {
        return importPath;
    }

    /**
     * Imposta il valore della proprietà importPath.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImportPath(String value) {
        this.importPath = value;
    }

}
