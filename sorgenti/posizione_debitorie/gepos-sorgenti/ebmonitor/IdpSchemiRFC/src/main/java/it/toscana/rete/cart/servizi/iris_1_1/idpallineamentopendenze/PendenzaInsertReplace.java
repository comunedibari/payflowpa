
package it.toscana.rete.cart.servizi.iris_1_1.idpallineamentopendenze;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import it.toscana.rete.cart.servizi.iris_1_1.idpinclude.Allegato;
import it.toscana.rete.cart.servizi.iris_1_1.idpinclude.Divisa;
import it.toscana.rete.cart.servizi.iris_1_1.idpinclude.StatoPendenza;


/**
 * <p>Java class for Pendenza.InsertReplace complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Pendenza.InsertReplace">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DescrizioneCausale" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max512Text"/>
 *         &lt;element name="Riscossore" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpAllineamentoPendenze}Riscossore" minOccurs="0"/>
 *         &lt;element name="DataCreazione" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}IdPDateTime"/>
 *         &lt;element name="DataEmissione" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}IdPDateTime"/>
 *         &lt;element name="DataPrescrizione" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}IdPDateTime"/>
 *         &lt;element name="AnnoRiferimento" type="{http://www.w3.org/2001/XMLSchema}gYear"/>
 *         &lt;element name="DataModificaEnte" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}IdPDateTime" minOccurs="0"/>
 *         &lt;element name="Stato" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}StatoPendenza"/>
 *         &lt;element name="ImportoTotale" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Importo"/>
 *         &lt;element name="Divisa" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Divisa"/>
 *         &lt;element name="InfoPagamento" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpAllineamentoPendenze}InfoPagamento.InsertReplace">
 *                 &lt;sequence>
 *                   &lt;element name="DettaglioPagamento" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpAllineamentoPendenze}DettaglioPagamento.InsertReplace" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/extension>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Allegato" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Allegato" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Pendenza.InsertReplace", propOrder = {
    "descrizioneCausale",
    "riscossore",
    "dataCreazione",
    "dataEmissione",
    "dataPrescrizione",
    "annoRiferimento",
    "dataModificaEnte",
    "stato",
    "importoTotale",
    "divisa",
    "infoPagamento",
    "allegato"
})
public class PendenzaInsertReplace {

    @XmlElement(name = "DescrizioneCausale", required = true)
    protected String descrizioneCausale;
    @XmlElement(name = "Riscossore")
    protected Riscossore riscossore;
    @XmlElement(name = "DataCreazione", required = true)
    protected XMLGregorianCalendar dataCreazione;
    @XmlElement(name = "DataEmissione", required = true)
    protected XMLGregorianCalendar dataEmissione;
    @XmlElement(name = "DataPrescrizione", required = true)
    protected XMLGregorianCalendar dataPrescrizione;
    @XmlElement(name = "AnnoRiferimento", required = true)
    @XmlSchemaType(name = "gYear")
    protected XMLGregorianCalendar annoRiferimento;
    @XmlElement(name = "DataModificaEnte")
    protected XMLGregorianCalendar dataModificaEnte;
    @XmlElement(name = "Stato", required = true)
    protected StatoPendenza stato;
    @XmlElement(name = "ImportoTotale", required = true)
    protected BigDecimal importoTotale;
    @XmlElement(name = "Divisa", required = true)
    protected Divisa divisa;
    @XmlElement(name = "InfoPagamento", required = true)
    protected List<PendenzaInsertReplace.InfoPagamento> infoPagamento;
    @XmlElement(name = "Allegato")
    protected Allegato allegato;

    /**
     * Gets the value of the descrizioneCausale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneCausale() {
        return descrizioneCausale;
    }

    /**
     * Sets the value of the descrizioneCausale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneCausale(String value) {
        this.descrizioneCausale = value;
    }

    /**
     * Gets the value of the riscossore property.
     * 
     * @return
     *     possible object is
     *     {@link Riscossore }
     *     
     */
    public Riscossore getRiscossore() {
        return riscossore;
    }

    /**
     * Sets the value of the riscossore property.
     * 
     * @param value
     *     allowed object is
     *     {@link Riscossore }
     *     
     */
    public void setRiscossore(Riscossore value) {
        this.riscossore = value;
    }

    /**
     * Gets the value of the dataCreazione property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataCreazione() {
        return dataCreazione;
    }

    /**
     * Sets the value of the dataCreazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataCreazione(XMLGregorianCalendar value) {
        this.dataCreazione = value;
    }

    /**
     * Gets the value of the dataEmissione property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataEmissione() {
        return dataEmissione;
    }

    /**
     * Sets the value of the dataEmissione property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataEmissione(XMLGregorianCalendar value) {
        this.dataEmissione = value;
    }

    /**
     * Gets the value of the dataPrescrizione property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataPrescrizione() {
        return dataPrescrizione;
    }

    /**
     * Sets the value of the dataPrescrizione property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataPrescrizione(XMLGregorianCalendar value) {
        this.dataPrescrizione = value;
    }

    /**
     * Gets the value of the annoRiferimento property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAnnoRiferimento() {
        return annoRiferimento;
    }

    /**
     * Sets the value of the annoRiferimento property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAnnoRiferimento(XMLGregorianCalendar value) {
        this.annoRiferimento = value;
    }

    /**
     * Gets the value of the dataModificaEnte property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataModificaEnte() {
        return dataModificaEnte;
    }

    /**
     * Sets the value of the dataModificaEnte property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataModificaEnte(XMLGregorianCalendar value) {
        this.dataModificaEnte = value;
    }

    /**
     * Gets the value of the stato property.
     * 
     * @return
     *     possible object is
     *     {@link StatoPendenza }
     *     
     */
    public StatoPendenza getStato() {
        return stato;
    }

    /**
     * Sets the value of the stato property.
     * 
     * @param value
     *     allowed object is
     *     {@link StatoPendenza }
     *     
     */
    public void setStato(StatoPendenza value) {
        this.stato = value;
    }

    /**
     * Gets the value of the importoTotale property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImportoTotale() {
        return importoTotale;
    }

    /**
     * Sets the value of the importoTotale property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImportoTotale(BigDecimal value) {
        this.importoTotale = value;
    }

    /**
     * Gets the value of the divisa property.
     * 
     * @return
     *     possible object is
     *     {@link Divisa }
     *     
     */
    public Divisa getDivisa() {
        return divisa;
    }

    /**
     * Sets the value of the divisa property.
     * 
     * @param value
     *     allowed object is
     *     {@link Divisa }
     *     
     */
    public void setDivisa(Divisa value) {
        this.divisa = value;
    }

    /**
     * Gets the value of the infoPagamento property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the infoPagamento property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInfoPagamento().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PendenzaInsertReplace.InfoPagamento }
     * 
     * 
     */
    public List<PendenzaInsertReplace.InfoPagamento> getInfoPagamento() {
        if (infoPagamento == null) {
            infoPagamento = new ArrayList<PendenzaInsertReplace.InfoPagamento>();
        }
        return this.infoPagamento;
    }

    /**
     * Gets the value of the allegato property.
     * 
     * @return
     *     possible object is
     *     {@link Allegato }
     *     
     */
    public Allegato getAllegato() {
        return allegato;
    }

    /**
     * Sets the value of the allegato property.
     * 
     * @param value
     *     allowed object is
     *     {@link Allegato }
     *     
     */
    public void setAllegato(Allegato value) {
        this.allegato = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;extension base="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpAllineamentoPendenze}InfoPagamento.InsertReplace">
     *       &lt;sequence>
     *         &lt;element name="DettaglioPagamento" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpAllineamentoPendenze}DettaglioPagamento.InsertReplace" maxOccurs="unbounded"/>
     *       &lt;/sequence>
     *     &lt;/extension>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "dettaglioPagamento"
    })
    public static class InfoPagamento
        extends InfoPagamentoInsertReplace
    {

        @XmlElement(name = "DettaglioPagamento", required = true)
        protected List<DettaglioPagamentoInsertReplace> dettaglioPagamento;

        /**
         * Gets the value of the dettaglioPagamento property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the dettaglioPagamento property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDettaglioPagamento().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link DettaglioPagamentoInsertReplace }
         * 
         * 
         */
        public List<DettaglioPagamentoInsertReplace> getDettaglioPagamento() {
            if (dettaglioPagamento == null) {
                dettaglioPagamento = new ArrayList<DettaglioPagamentoInsertReplace>();
            }
            return this.dettaglioPagamento;
        }

    }

}
