package it.tasgroup.idp.rs.model.creditore;

import it.tasgroup.rest.utils.RESTResource;
import it.tasgroup.services.util.enumeration.EnumTipoAccredito;
import it.tasgroup.services.util.enumeration.EnumTipoAnomaliaNDP;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Movimenti di accredito
 */
@XmlRootElement
public class MovimentoAccredito extends RESTResource {

    public MovimentoAccredito(Long id, Timestamp dataValutaAccredito, Timestamp dataContabile, String TRN, BigDecimal importo, EnumTipoAnomaliaNDP tipoAnomalia,
                              EnumTipoAccredito tipoAccredito, String IBAN,  String idRiconciliazione) {
        this.id = id;
        this.idRiconciliazione = idRiconciliazione;
        this.dataValutaAccredito = dataValutaAccredito;
        this.TRN = TRN;
        this.importo = importo;
        this.tipoAnomalia = tipoAnomalia;
        this.tipoAccredito = tipoAccredito;
        this.IBAN = IBAN;
        this.dataContabile = dataContabile;
    }

    public MovimentoAccredito() {
    }

    private Long id;

    private String idRiconciliazione;

    private Timestamp dataValutaAccredito;

    private String TRN;

    private BigDecimal importo;

    private EnumTipoAnomaliaNDP tipoAnomalia;

    private EnumTipoAccredito tipoAccredito;

    private String IBAN;

    private Timestamp dataContabile;


    /**
     * Id di riconciliazione
     * @return
     */
    @XmlElement(required = true)
    public String getIdRiconciliazione() {
        return idRiconciliazione;
    }

    public void setIdRiconciliazione(String idRiconciliazione) {
        this.idRiconciliazione = idRiconciliazione;
    }

    /**
     * Data contabile
     * @return
     */
    @XmlElement(required = true)
    public Timestamp getDataContabile() {
        return dataContabile;
    }

    public void setDataContabile(Timestamp dataContabile) {
        this.dataContabile = dataContabile;
    }

    /**
     * Data valuta accredito
     * @return
     */
    @XmlElement(required = true)
    public Timestamp getDataValutaAccredito() {
        return dataValutaAccredito;
    }

    public void setDataValutaAccredito(Timestamp dataValutaAccredito) {
        this.dataValutaAccredito = dataValutaAccredito;
    }

    /**
     * Transaction Reference Number (TRN) del bonifico di accredito
     *
     * @return
     */
    @XmlElement(required = true)
    public String getTRN() {
        return TRN;
    }

    public void setTRN(String TRN) {
        this.TRN = TRN;
    }

    /**
     * Importo del bonifico
     *
     * @return
     */
    @XmlElement(required = true)
    public BigDecimal getImporto() {
        return importo;
    }

    public void setImporto(BigDecimal importo) {
        this.importo = importo;
    }

    /**
     * Tipologia di anomalia riscontrata
     *
     * @return
     */
    @XmlElement(required = true)
    public EnumTipoAnomaliaNDP getTipoAnomalia() {
        return tipoAnomalia;
    }

    public void setTipoAnomalia(EnumTipoAnomaliaNDP tipoAnomalia) {
        this.tipoAnomalia = tipoAnomalia;
    }

    /**
     * Tipo di accredito S (singolo), C (cumulativo)
     *
     * @return
     */
    @XmlElement(required = true)
    public EnumTipoAccredito getTipoAccredito() {
        return tipoAccredito;
    }

    public void setTipoAccredito(EnumTipoAccredito tipoAccredito) {
        this.tipoAccredito = tipoAccredito;
    }

    /**
     * IBAN sul quale viene accreditato il movimento
     *
     * @return
     */
    @XmlElement(required = true)
    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }


    @XmlElement
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
