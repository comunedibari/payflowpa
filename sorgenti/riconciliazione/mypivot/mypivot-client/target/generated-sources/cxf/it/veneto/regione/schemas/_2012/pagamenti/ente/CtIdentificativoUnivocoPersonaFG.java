
package it.veneto.regione.schemas._2012.pagamenti.ente;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ctIdentificativoUnivocoPersonaFG complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ctIdentificativoUnivocoPersonaFG"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="tipoIdentificativoUnivoco" type="{http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/}stTipoIdentificativoUnivocoPersFG"/&gt;
 *         &lt;element name="codiceIdentificativoUnivoco" type="{http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/}stText35"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ctIdentificativoUnivocoPersonaFG", propOrder = {
    "tipoIdentificativoUnivoco",
    "codiceIdentificativoUnivoco"
})
public class CtIdentificativoUnivocoPersonaFG {

    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected StTipoIdentificativoUnivocoPersFG tipoIdentificativoUnivoco;
    @XmlElement(required = true)
    protected String codiceIdentificativoUnivoco;

    /**
     * Recupera il valore della proprietà tipoIdentificativoUnivoco.
     * 
     * @return
     *     possible object is
     *     {@link StTipoIdentificativoUnivocoPersFG }
     *     
     */
    public StTipoIdentificativoUnivocoPersFG getTipoIdentificativoUnivoco() {
        return tipoIdentificativoUnivoco;
    }

    /**
     * Imposta il valore della proprietà tipoIdentificativoUnivoco.
     * 
     * @param value
     *     allowed object is
     *     {@link StTipoIdentificativoUnivocoPersFG }
     *     
     */
    public void setTipoIdentificativoUnivoco(StTipoIdentificativoUnivocoPersFG value) {
        this.tipoIdentificativoUnivoco = value;
    }

    /**
     * Recupera il valore della proprietà codiceIdentificativoUnivoco.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceIdentificativoUnivoco() {
        return codiceIdentificativoUnivoco;
    }

    /**
     * Imposta il valore della proprietà codiceIdentificativoUnivoco.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceIdentificativoUnivoco(String value) {
        this.codiceIdentificativoUnivoco = value;
    }

}
