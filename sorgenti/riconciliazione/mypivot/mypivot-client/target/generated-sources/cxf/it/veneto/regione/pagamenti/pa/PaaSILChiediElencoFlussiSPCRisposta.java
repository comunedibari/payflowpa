
package it.veneto.regione.pagamenti.pa;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per paaSILChiediElencoFlussiSPCRisposta complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="paaSILChiediElencoFlussiSPCRisposta"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.regione.veneto.it/pagamenti/pa/}risposta"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="elencoFlussiSPC" type="{http://www.regione.veneto.it/pagamenti/pa/}tipoElencoFlussiSPC" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "paaSILChiediElencoFlussiSPCRisposta", propOrder = {
    "elencoFlussiSPC"
})
public class PaaSILChiediElencoFlussiSPCRisposta
    extends Risposta
{

    protected TipoElencoFlussiSPC elencoFlussiSPC;

    /**
     * Recupera il valore della proprietà elencoFlussiSPC.
     * 
     * @return
     *     possible object is
     *     {@link TipoElencoFlussiSPC }
     *     
     */
    public TipoElencoFlussiSPC getElencoFlussiSPC() {
        return elencoFlussiSPC;
    }

    /**
     * Imposta il valore della proprietà elencoFlussiSPC.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoElencoFlussiSPC }
     *     
     */
    public void setElencoFlussiSPC(TipoElencoFlussiSPC value) {
        this.elencoFlussiSPC = value;
    }

}
