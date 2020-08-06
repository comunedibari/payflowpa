
package it.veneto.regione.pagamenti.pivot.ente;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ctRichiestaPerIUVIUF complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ctRichiestaPerIUVIUF"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="riversamentiPuntuali" type="{http://www.regione.veneto.it/pagamenti/pivot/ente/}ctRiversamentiPuntuali" minOccurs="0"/&gt;
 *         &lt;element name="riversamentiCumulativi" type="{http://www.regione.veneto.it/pagamenti/pivot/ente/}ctRiversamentiCumulativi" minOccurs="0"/&gt;
 *         &lt;element name="filtroTipiDovuto" type="{http://www.regione.veneto.it/pagamenti/pivot/ente/}ctFiltroTipiDovuto" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ctRichiestaPerIUVIUF", propOrder = {
    "riversamentiPuntuali",
    "riversamentiCumulativi",
    "filtroTipiDovuto"
})
public class CtRichiestaPerIUVIUF {

    protected CtRiversamentiPuntuali riversamentiPuntuali;
    protected CtRiversamentiCumulativi riversamentiCumulativi;
    protected CtFiltroTipiDovuto filtroTipiDovuto;

    /**
     * Recupera il valore della proprietà riversamentiPuntuali.
     * 
     * @return
     *     possible object is
     *     {@link CtRiversamentiPuntuali }
     *     
     */
    public CtRiversamentiPuntuali getRiversamentiPuntuali() {
        return riversamentiPuntuali;
    }

    /**
     * Imposta il valore della proprietà riversamentiPuntuali.
     * 
     * @param value
     *     allowed object is
     *     {@link CtRiversamentiPuntuali }
     *     
     */
    public void setRiversamentiPuntuali(CtRiversamentiPuntuali value) {
        this.riversamentiPuntuali = value;
    }

    /**
     * Recupera il valore della proprietà riversamentiCumulativi.
     * 
     * @return
     *     possible object is
     *     {@link CtRiversamentiCumulativi }
     *     
     */
    public CtRiversamentiCumulativi getRiversamentiCumulativi() {
        return riversamentiCumulativi;
    }

    /**
     * Imposta il valore della proprietà riversamentiCumulativi.
     * 
     * @param value
     *     allowed object is
     *     {@link CtRiversamentiCumulativi }
     *     
     */
    public void setRiversamentiCumulativi(CtRiversamentiCumulativi value) {
        this.riversamentiCumulativi = value;
    }

    /**
     * Recupera il valore della proprietà filtroTipiDovuto.
     * 
     * @return
     *     possible object is
     *     {@link CtFiltroTipiDovuto }
     *     
     */
    public CtFiltroTipiDovuto getFiltroTipiDovuto() {
        return filtroTipiDovuto;
    }

    /**
     * Imposta il valore della proprietà filtroTipiDovuto.
     * 
     * @param value
     *     allowed object is
     *     {@link CtFiltroTipiDovuto }
     *     
     */
    public void setFiltroTipiDovuto(CtFiltroTipiDovuto value) {
        this.filtroTipiDovuto = value;
    }

}
