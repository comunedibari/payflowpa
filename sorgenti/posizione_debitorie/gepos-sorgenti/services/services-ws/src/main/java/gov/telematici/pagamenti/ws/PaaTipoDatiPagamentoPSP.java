
package gov.telematici.pagamenti.ws;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import it.gov.digitpa.schemas._2011.pagamenti.CtSoggettoPagatore;
import it.gov.digitpa.schemas._2011.pagamenti.CtSoggettoVersante;


/**
 * <p>Java class for paaTipoDatiPagamentoPSP complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="paaTipoDatiPagamentoPSP">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="importoSingoloVersamento" type="{http://www.digitpa.gov.it/schemas/2011/Pagamenti/}stImporto"/>
 *         &lt;element name="ibanAppoggio" type="{http://www.digitpa.gov.it/schemas/2011/Pagamenti/}stIBANIdentifier" minOccurs="0"/>
 *         &lt;element name="bicAppoggio" type="{http://www.digitpa.gov.it/schemas/2011/Pagamenti/}stBICIdentifier" minOccurs="0"/>
 *         &lt;element name="soggettoVersante" type="{http://www.digitpa.gov.it/schemas/2011/Pagamenti/}ctSoggettoVersante" minOccurs="0"/>
 *         &lt;element name="ibanAddebito" type="{http://www.digitpa.gov.it/schemas/2011/Pagamenti/}stIBANIdentifier" minOccurs="0"/>
 *         &lt;element name="bicAddebito" type="{http://www.digitpa.gov.it/schemas/2011/Pagamenti/}stBICIdentifier" minOccurs="0"/>
 *         &lt;element name="soggettoPagatore" type="{http://www.digitpa.gov.it/schemas/2011/Pagamenti/}ctSoggettoPagatore" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "paaTipoDatiPagamentoPSP", propOrder = {
    "importoSingoloVersamento",
    "ibanAppoggio",
    "bicAppoggio",
    "soggettoVersante",
    "ibanAddebito",
    "bicAddebito",
    "soggettoPagatore"
})
public class PaaTipoDatiPagamentoPSP {

    @XmlElement(required = true)
    protected BigDecimal importoSingoloVersamento;
    protected String ibanAppoggio;
    protected String bicAppoggio;
    protected CtSoggettoVersante soggettoVersante;
    protected String ibanAddebito;
    protected String bicAddebito;
    protected CtSoggettoPagatore soggettoPagatore;

    /**
     * Gets the value of the importoSingoloVersamento property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImportoSingoloVersamento() {
        return importoSingoloVersamento;
    }

    /**
     * Sets the value of the importoSingoloVersamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImportoSingoloVersamento(BigDecimal value) {
        this.importoSingoloVersamento = value;
    }

    /**
     * Gets the value of the ibanAppoggio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIbanAppoggio() {
        return ibanAppoggio;
    }

    /**
     * Sets the value of the ibanAppoggio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIbanAppoggio(String value) {
        this.ibanAppoggio = value;
    }

    /**
     * Gets the value of the bicAppoggio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBicAppoggio() {
        return bicAppoggio;
    }

    /**
     * Sets the value of the bicAppoggio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBicAppoggio(String value) {
        this.bicAppoggio = value;
    }

    /**
     * Gets the value of the soggettoVersante property.
     * 
     * @return
     *     possible object is
     *     {@link CtSoggettoVersante }
     *     
     */
    public CtSoggettoVersante getSoggettoVersante() {
        return soggettoVersante;
    }

    /**
     * Sets the value of the soggettoVersante property.
     * 
     * @param value
     *     allowed object is
     *     {@link CtSoggettoVersante }
     *     
     */
    public void setSoggettoVersante(CtSoggettoVersante value) {
        this.soggettoVersante = value;
    }

    /**
     * Gets the value of the ibanAddebito property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIbanAddebito() {
        return ibanAddebito;
    }

    /**
     * Sets the value of the ibanAddebito property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIbanAddebito(String value) {
        this.ibanAddebito = value;
    }

    /**
     * Gets the value of the bicAddebito property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBicAddebito() {
        return bicAddebito;
    }

    /**
     * Sets the value of the bicAddebito property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBicAddebito(String value) {
        this.bicAddebito = value;
    }

    /**
     * Gets the value of the soggettoPagatore property.
     * 
     * @return
     *     possible object is
     *     {@link CtSoggettoPagatore }
     *     
     */
    public CtSoggettoPagatore getSoggettoPagatore() {
        return soggettoPagatore;
    }

    /**
     * Sets the value of the soggettoPagatore property.
     * 
     * @param value
     *     allowed object is
     *     {@link CtSoggettoPagatore }
     *     
     */
    public void setSoggettoPagatore(CtSoggettoPagatore value) {
        this.soggettoPagatore = value;
    }

}
