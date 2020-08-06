
package it.toscana.rete.cart.servizi.iris_1_1.idpinformativapagamento;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import it.toscana.rete.cart.servizi.iris_1_1.idpinclude.DettaglioCanalePagamento;


/**
 * <p>Java class for Transazione complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Transazione">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CanalePagamento" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInformativaPagamento}CanalePagamento"/>
 *         &lt;element name="MezzoPagamento" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInformativaPagamento}MezzoPagamento"/>
 *         &lt;element name="DettaglioCanalePagamento" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}DettaglioCanalePagamento" minOccurs="0"/>
 *         &lt;element name="IdTransazione" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max35Text"/>
 *         &lt;element name="DataOraTransazione" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}IdPDateTime"/>
 *         &lt;element name="CodiceAutorizzazione" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max35Text" minOccurs="0"/>
 *         &lt;element name="DataOraAutorizzazione" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}IdPDateTime" minOccurs="0"/>
 *         &lt;element name="TipoSicurezza" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max35Text" minOccurs="0"/>
 *         &lt;element name="ImportoTransato" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Importo"/>
 *         &lt;element name="DettaglioImportoTransato" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInformativaPagamento}DettaglioImportoTransato" minOccurs="0"/>
 *         &lt;element name="Descrizione" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max256Text" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Transazione", propOrder = {
    "canalePagamento",
    "mezzoPagamento",
    "dettaglioCanalePagamento",
    "idTransazione",
    "dataOraTransazione",
    "codiceAutorizzazione",
    "dataOraAutorizzazione",
    "tipoSicurezza",
    "importoTransato",
    "dettaglioImportoTransato",
    "descrizione"
})
public class Transazione {

    @XmlElement(name = "CanalePagamento", required = true)
    protected CanalePagamento canalePagamento;
    @XmlElement(name = "MezzoPagamento", required = true)
    protected MezzoPagamento mezzoPagamento;
    @XmlElement(name = "DettaglioCanalePagamento")
    protected DettaglioCanalePagamento dettaglioCanalePagamento;
    @XmlElement(name = "IdTransazione", required = true)
    protected String idTransazione;
    @XmlElement(name = "DataOraTransazione", required = true)
    protected XMLGregorianCalendar dataOraTransazione;
    @XmlElement(name = "CodiceAutorizzazione")
    protected String codiceAutorizzazione;
    @XmlElement(name = "DataOraAutorizzazione")
    protected XMLGregorianCalendar dataOraAutorizzazione;
    @XmlElement(name = "TipoSicurezza")
    protected String tipoSicurezza;
    @XmlElement(name = "ImportoTransato", required = true)
    protected BigDecimal importoTransato;
    @XmlElement(name = "DettaglioImportoTransato")
    protected DettaglioImportoTransato dettaglioImportoTransato;
    @XmlElement(name = "Descrizione")
    protected String descrizione;

    /**
     * Gets the value of the canalePagamento property.
     * 
     * @return
     *     possible object is
     *     {@link CanalePagamento }
     *     
     */
    public CanalePagamento getCanalePagamento() {
        return canalePagamento;
    }

    /**
     * Sets the value of the canalePagamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link CanalePagamento }
     *     
     */
    public void setCanalePagamento(CanalePagamento value) {
        this.canalePagamento = value;
    }

    /**
     * Gets the value of the mezzoPagamento property.
     * 
     * @return
     *     possible object is
     *     {@link MezzoPagamento }
     *     
     */
    public MezzoPagamento getMezzoPagamento() {
        return mezzoPagamento;
    }

    /**
     * Sets the value of the mezzoPagamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link MezzoPagamento }
     *     
     */
    public void setMezzoPagamento(MezzoPagamento value) {
        this.mezzoPagamento = value;
    }

    /**
     * Gets the value of the dettaglioCanalePagamento property.
     * 
     * @return
     *     possible object is
     *     {@link DettaglioCanalePagamento }
     *     
     */
    public DettaglioCanalePagamento getDettaglioCanalePagamento() {
        return dettaglioCanalePagamento;
    }

    /**
     * Sets the value of the dettaglioCanalePagamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link DettaglioCanalePagamento }
     *     
     */
    public void setDettaglioCanalePagamento(DettaglioCanalePagamento value) {
        this.dettaglioCanalePagamento = value;
    }

    /**
     * Gets the value of the idTransazione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdTransazione() {
        return idTransazione;
    }

    /**
     * Sets the value of the idTransazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdTransazione(String value) {
        this.idTransazione = value;
    }

    /**
     * Gets the value of the dataOraTransazione property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataOraTransazione() {
        return dataOraTransazione;
    }

    /**
     * Sets the value of the dataOraTransazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataOraTransazione(XMLGregorianCalendar value) {
        this.dataOraTransazione = value;
    }

    /**
     * Gets the value of the codiceAutorizzazione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceAutorizzazione() {
        return codiceAutorizzazione;
    }

    /**
     * Sets the value of the codiceAutorizzazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceAutorizzazione(String value) {
        this.codiceAutorizzazione = value;
    }

    /**
     * Gets the value of the dataOraAutorizzazione property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataOraAutorizzazione() {
        return dataOraAutorizzazione;
    }

    /**
     * Sets the value of the dataOraAutorizzazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataOraAutorizzazione(XMLGregorianCalendar value) {
        this.dataOraAutorizzazione = value;
    }

    /**
     * Gets the value of the tipoSicurezza property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoSicurezza() {
        return tipoSicurezza;
    }

    /**
     * Sets the value of the tipoSicurezza property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoSicurezza(String value) {
        this.tipoSicurezza = value;
    }

    /**
     * Gets the value of the importoTransato property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImportoTransato() {
        return importoTransato;
    }

    /**
     * Sets the value of the importoTransato property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImportoTransato(BigDecimal value) {
        this.importoTransato = value;
    }

    /**
     * Gets the value of the dettaglioImportoTransato property.
     * 
     * @return
     *     possible object is
     *     {@link DettaglioImportoTransato }
     *     
     */
    public DettaglioImportoTransato getDettaglioImportoTransato() {
        return dettaglioImportoTransato;
    }

    /**
     * Sets the value of the dettaglioImportoTransato property.
     * 
     * @param value
     *     allowed object is
     *     {@link DettaglioImportoTransato }
     *     
     */
    public void setDettaglioImportoTransato(DettaglioImportoTransato value) {
        this.dettaglioImportoTransato = value;
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

}
