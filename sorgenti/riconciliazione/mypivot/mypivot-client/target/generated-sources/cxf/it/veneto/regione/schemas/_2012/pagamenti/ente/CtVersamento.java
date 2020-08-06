
package it.veneto.regione.schemas._2012.pagamenti.ente;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ctVersamento complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ctVersamento"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="versioneOggetto" type="{http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/}stText16"/&gt;
 *         &lt;element name="soggettoPagatore" type="{http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/}ctSoggettoPagatore" minOccurs="0"/&gt;
 *         &lt;element name="datiVersamento" type="{http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/}ctDatiVersamento"/&gt;
 *         &lt;element name="azione" type="{http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/}stText1"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ctVersamento", propOrder = {
    "versioneOggetto",
    "soggettoPagatore",
    "datiVersamento",
    "azione"
})
public class CtVersamento {

    @XmlElement(required = true)
    protected String versioneOggetto;
    protected CtSoggettoPagatore soggettoPagatore;
    @XmlElement(required = true)
    protected CtDatiVersamento datiVersamento;
    @XmlElement(required = true)
    protected String azione;

    /**
     * Recupera il valore della proprietà versioneOggetto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersioneOggetto() {
        return versioneOggetto;
    }

    /**
     * Imposta il valore della proprietà versioneOggetto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersioneOggetto(String value) {
        this.versioneOggetto = value;
    }

    /**
     * Recupera il valore della proprietà soggettoPagatore.
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
     * Imposta il valore della proprietà soggettoPagatore.
     * 
     * @param value
     *     allowed object is
     *     {@link CtSoggettoPagatore }
     *     
     */
    public void setSoggettoPagatore(CtSoggettoPagatore value) {
        this.soggettoPagatore = value;
    }

    /**
     * Recupera il valore della proprietà datiVersamento.
     * 
     * @return
     *     possible object is
     *     {@link CtDatiVersamento }
     *     
     */
    public CtDatiVersamento getDatiVersamento() {
        return datiVersamento;
    }

    /**
     * Imposta il valore della proprietà datiVersamento.
     * 
     * @param value
     *     allowed object is
     *     {@link CtDatiVersamento }
     *     
     */
    public void setDatiVersamento(CtDatiVersamento value) {
        this.datiVersamento = value;
    }

    /**
     * Recupera il valore della proprietà azione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAzione() {
        return azione;
    }

    /**
     * Imposta il valore della proprietà azione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAzione(String value) {
        this.azione = value;
    }

}
