
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
import it.toscana.rete.cart.servizi.iris_1_1.idpinclude.StatoPendenza;


/**
 * <p>Java class for Pendenza.UpdateStatus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Pendenza.UpdateStatus">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Riscossore" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpAllineamentoPendenze}Riscossore" minOccurs="0"/>
 *         &lt;element name="DataModificaEnte" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}IdPDateTime" minOccurs="0"/>
 *         &lt;element name="Stato" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}StatoPendenza" minOccurs="0"/>
 *         &lt;element name="ImportoTotale" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Importo" minOccurs="0"/>
 *         &lt;element name="InfoPagamento" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpAllineamentoPendenze}InfoPagamento.UpdateStatus">
 *                 &lt;sequence>
 *                   &lt;element name="DettaglioPagamento" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpAllineamentoPendenze}DettaglioPagamento.UpdateStatus" maxOccurs="unbounded"/>
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
@XmlType(name = "Pendenza.UpdateStatus", propOrder = {
    "riscossore",
    "dataModificaEnte",
    "stato",
    "importoTotale",
    "infoPagamento",
    "allegato"
})
public class PendenzaUpdateStatus {

    @XmlElement(name = "Riscossore")
    protected Riscossore riscossore;
    @XmlElement(name = "DataModificaEnte")
    protected XMLGregorianCalendar dataModificaEnte;
    @XmlElement(name = "Stato")
    protected StatoPendenza stato;
    @XmlElement(name = "ImportoTotale")
    protected BigDecimal importoTotale;
    @XmlElement(name = "InfoPagamento", required = true)
    protected List<PendenzaUpdateStatus.InfoPagamento> infoPagamento;
    @XmlElement(name = "Allegato")
    protected Allegato allegato;

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
     * {@link PendenzaUpdateStatus.InfoPagamento }
     * 
     * 
     */
    public List<PendenzaUpdateStatus.InfoPagamento> getInfoPagamento() {
        if (infoPagamento == null) {
            infoPagamento = new ArrayList<PendenzaUpdateStatus.InfoPagamento>();
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
     *     &lt;extension base="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpAllineamentoPendenze}InfoPagamento.UpdateStatus">
     *       &lt;sequence>
     *         &lt;element name="DettaglioPagamento" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpAllineamentoPendenze}DettaglioPagamento.UpdateStatus" maxOccurs="unbounded"/>
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
        extends InfoPagamentoUpdateStatus
    {

        @XmlElement(name = "DettaglioPagamento", required = true)
        protected List<DettaglioPagamentoUpdateStatus> dettaglioPagamento;

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
         * {@link DettaglioPagamentoUpdateStatus }
         * 
         * 
         */
        public List<DettaglioPagamentoUpdateStatus> getDettaglioPagamento() {
            if (dettaglioPagamento == null) {
                dettaglioPagamento = new ArrayList<DettaglioPagamentoUpdateStatus>();
            }
            return this.dettaglioPagamento;
        }

    }

}
