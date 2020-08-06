
package it.toscana.rete.cart.servizi.iris_1_1.idpallineamentopendenze;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import it.toscana.rete.cart.servizi.iris_1_1.idpinclude.Allegato;
import it.toscana.rete.cart.servizi.iris_1_1.idpinclude.CIP;
import it.toscana.rete.cart.servizi.iris_1_1.idpinclude.CoordinateBancarie;
import it.toscana.rete.cart.servizi.iris_1_1.idpinclude.StatoPagamento;


/**
 * <p>Java class for DettaglioPagamento.InsertReplace complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DettaglioPagamento.InsertReplace">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IdPagamento" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}IdentificativoIdp"/>
 *         &lt;element name="CIP" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}CIP" minOccurs="0"/>
 *         &lt;element name="DataScadenza" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}IdPDate"/>
 *         &lt;element name="DataInizioValidita" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}IdPDate" minOccurs="0"/>
 *         &lt;element name="DataFineValidita" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}IdPDate"/>
 *         &lt;element name="Stato" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}StatoPagamento"/>
 *         &lt;element name="Importo" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Importo"/>
 *         &lt;element name="DettaglioImporto" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpAllineamentoPendenze}DettaglioImporto" minOccurs="0"/>
 *         &lt;element name="DettaglioTransazione" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpAllineamentoPendenze}DettaglioTransazione" minOccurs="0"/>
 *         &lt;element name="Allegato" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Allegato" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="CausalePagamento" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max256Text" minOccurs="0"/>
 *         &lt;element name="AccreditoPagamento" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}CoordinateBancarie" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DettaglioPagamento.InsertReplace", propOrder = {
    "idPagamento",
    "cip",
    "dataScadenza",
    "dataInizioValidita",
    "dataFineValidita",
    "stato",
    "importo",
    "dettaglioImporto",
    "dettaglioTransazione",
    "allegato",
    "causalePagamento",
    "accreditoPagamento"
})
public class DettaglioPagamentoInsertReplace {

    @XmlElement(name = "IdPagamento", required = true)
    protected String idPagamento;
    @XmlElement(name = "CIP")
    protected CIP cip;
    @XmlElement(name = "DataScadenza", required = true)
    protected XMLGregorianCalendar dataScadenza;
    @XmlElement(name = "DataInizioValidita")
    protected XMLGregorianCalendar dataInizioValidita;
    @XmlElement(name = "DataFineValidita", required = true)
    protected XMLGregorianCalendar dataFineValidita;
    @XmlElement(name = "Stato", required = true)
    protected StatoPagamento stato;
    @XmlElement(name = "Importo", required = true)
    protected BigDecimal importo;
    @XmlElement(name = "DettaglioImporto")
    protected DettaglioImporto dettaglioImporto;
    @XmlElement(name = "DettaglioTransazione")
    protected DettaglioTransazione dettaglioTransazione;
    @XmlElement(name = "Allegato")
    protected List<Allegato> allegato;
    @XmlElement(name = "CausalePagamento")
    protected String causalePagamento;
    @XmlElement(name = "AccreditoPagamento")
    protected CoordinateBancarie accreditoPagamento;

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
     * Gets the value of the cip property.
     * 
     * @return
     *     possible object is
     *     {@link CIP }
     *     
     */
    public CIP getCIP() {
        return cip;
    }

    /**
     * Sets the value of the cip property.
     * 
     * @param value
     *     allowed object is
     *     {@link CIP }
     *     
     */
    public void setCIP(CIP value) {
        this.cip = value;
    }

    /**
     * Gets the value of the dataScadenza property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataScadenza() {
        return dataScadenza;
    }

    /**
     * Sets the value of the dataScadenza property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataScadenza(XMLGregorianCalendar value) {
        this.dataScadenza = value;
    }

    /**
     * Gets the value of the dataInizioValidita property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataInizioValidita() {
        return dataInizioValidita;
    }

    /**
     * Sets the value of the dataInizioValidita property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataInizioValidita(XMLGregorianCalendar value) {
        this.dataInizioValidita = value;
    }

    /**
     * Gets the value of the dataFineValidita property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataFineValidita() {
        return dataFineValidita;
    }

    /**
     * Sets the value of the dataFineValidita property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataFineValidita(XMLGregorianCalendar value) {
        this.dataFineValidita = value;
    }

    /**
     * Gets the value of the stato property.
     * 
     * @return
     *     possible object is
     *     {@link StatoPagamento }
     *     
     */
    public StatoPagamento getStato() {
        return stato;
    }

    /**
     * Sets the value of the stato property.
     * 
     * @param value
     *     allowed object is
     *     {@link StatoPagamento }
     *     
     */
    public void setStato(StatoPagamento value) {
        this.stato = value;
    }

    /**
     * Gets the value of the importo property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImporto() {
        return importo;
    }

    /**
     * Sets the value of the importo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImporto(BigDecimal value) {
        this.importo = value;
    }

    /**
     * Gets the value of the dettaglioImporto property.
     * 
     * @return
     *     possible object is
     *     {@link DettaglioImporto }
     *     
     */
    public DettaglioImporto getDettaglioImporto() {
        return dettaglioImporto;
    }

    /**
     * Sets the value of the dettaglioImporto property.
     * 
     * @param value
     *     allowed object is
     *     {@link DettaglioImporto }
     *     
     */
    public void setDettaglioImporto(DettaglioImporto value) {
        this.dettaglioImporto = value;
    }

    /**
     * Gets the value of the dettaglioTransazione property.
     * 
     * @return
     *     possible object is
     *     {@link DettaglioTransazione }
     *     
     */
    public DettaglioTransazione getDettaglioTransazione() {
        return dettaglioTransazione;
    }

    /**
     * Sets the value of the dettaglioTransazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link DettaglioTransazione }
     *     
     */
    public void setDettaglioTransazione(DettaglioTransazione value) {
        this.dettaglioTransazione = value;
    }

    /**
     * Gets the value of the allegato property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the allegato property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAllegato().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Allegato }
     * 
     * 
     */
    public List<Allegato> getAllegato() {
        if (allegato == null) {
            allegato = new ArrayList<Allegato>();
        }
        return this.allegato;
    }

    /**
     * Gets the value of the causalePagamento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCausalePagamento() {
        return causalePagamento;
    }

    /**
     * Sets the value of the causalePagamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCausalePagamento(String value) {
        this.causalePagamento = value;
    }

    /**
     * Gets the value of the accreditoPagamento property.
     * 
     * @return
     *     possible object is
     *     {@link CoordinateBancarie }
     *     
     */
    public CoordinateBancarie getAccreditoPagamento() {
        return accreditoPagamento;
    }

    /**
     * Sets the value of the accreditoPagamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link CoordinateBancarie }
     *     
     */
    public void setAccreditoPagamento(CoordinateBancarie value) {
        this.accreditoPagamento = value;
    }

}
