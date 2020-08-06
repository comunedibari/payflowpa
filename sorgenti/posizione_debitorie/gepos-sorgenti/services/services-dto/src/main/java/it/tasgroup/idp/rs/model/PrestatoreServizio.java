package it.tasgroup.idp.rs.model;

import it.tasgroup.idp.rs.enums.EnumModelloPagamento;
import it.tasgroup.idp.rs.enums.EnumTipoVersamento;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

/**
 * Prestatore di Servizio pagamento. Ogni riga rappresenta uno
 * strumento di pagamento utilizzabile per pagare.<br>
 * Ogni istituto finanziario (caratterizzato da un certo <em>systemId</em>) può esporre più record, corrispondenti a diversi
 * strumenti offerti (<em>applicationId</em>).
 */
@XmlRootElement
public class PrestatoreServizio implements Serializable {

    public PrestatoreServizio() {
    }

    public PrestatoreServizio(String codPrestatoreServizio, String applicationId, String descrizione, String systemId, String systemName, String condizioniEconomiche, String urlInfoPsp, String urlInfoCanale, String disponibilitaServizio, EnumTipoVersamento tipoVersamento, EnumModelloPagamento modelloVersamento, String priorita, Boolean disponibilePerPagamentoCorrente, String descrizioneIndisponibilitaPerPagamentoCorrente) {
        this.codPrestatoreServizio = codPrestatoreServizio;
        this.applicationId = applicationId;
        this.descrizione = descrizione;
        this.systemId = systemId;
        this.systemName = systemName;
        this.condizioniEconomiche = condizioniEconomiche;
        this.urlInfoPsp = urlInfoPsp;
        this.urlInfoCanale = urlInfoCanale;
        this.disponibilitaServizio = disponibilitaServizio;
        this.tipoVersamento = tipoVersamento;
        this.modelloVersamento = modelloVersamento;
        this.priorita = priorita;
        this.disponibilePerPagamentoCorrente = disponibilePerPagamentoCorrente;
        this.descrizioneIndisponibilitaPerPagamentoCorrente = descrizioneIndisponibilitaPerPagamentoCorrente;
    }

    private String codPrestatoreServizio;

    private String applicationId;

    private String descrizione;

    private String systemId;

    private String systemName;

    private String condizioniEconomiche;

    private String urlInfoPsp;

    private String urlInfoCanale;

    private String disponibilitaServizio;

    private EnumTipoVersamento tipoVersamento;

    private EnumModelloPagamento modelloVersamento;

    private String priorita;

    private Boolean disponibilePerPagamentoCorrente;

    private String descrizioneIndisponibilitaPerPagamentoCorrente = "";

    /**
     * Codice identificativo univoco dello strumento di pagamento, è un alfanumerico del tipo: BPPIITRRXXX-97103880585_01-CP
     */
    @XmlElement(required = true)
    public String getCodPrestatoreServizio() {
        return codPrestatoreServizio;
    }

    public void setCodPrestatoreServizio(String codPrestatoreServizio) {
        this.codPrestatoreServizio = codPrestatoreServizio;
    }

    /**
     * Identificativo dello strumento di pagamento esposto (in terminologia AgId "Canale")
     */
    @XmlElement(required = true)
    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }


    /**
     * Descrizione dello strumento di pagamento
     */
    @XmlElement(required = true)
    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    /**
     * Identificativo dell'istituto che espone lo strumento di pagamento (Identificativo PSP in terminologia AgId)
     * (e.g. il codice BIC : BPPIITRRXXX )
     */
    @XmlElement(required = true)
    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }


    /**
     * Ragione sociale dell'istituto che espone lo strumento di pagamento
     */
    @XmlElement(required = true)
    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    /**
     * Descrizione delle condizioni economiche applicate (per trasparenza e per guidare la scelta dell'utente)
     */
    @XmlElement(required = true)
    public String getCondizioniEconomiche() {
        return condizioniEconomiche;
    }

    public void setCondizioniEconomiche(String condizioniEconomiche) {
        this.condizioniEconomiche = condizioniEconomiche;
    }

    /**
     * Eventuale URL messo a disposizione dell'istituto finanziario per linkare un proprio sito istituzionali
     */
    public String getUrlInfoPsp() {
        return urlInfoPsp;
    }

    public void setUrlInfoPsp(String urlInfoPsp) {
        this.urlInfoPsp = urlInfoPsp;
    }

    /**
     * Eventuale URL messo a disposizione dell'istituto finanziario, per linkare un manuale di istruzioni, condizioni,  termini etc.
     * riguardo l'utilizzo dello strumento di pagamento.
     */
    public String getUrlInfoCanale() {
        return urlInfoCanale;
    }

    public void setUrlInfoCanale(String urlInfoCanale) {
        this.urlInfoCanale = urlInfoCanale;
    }

    /**
     * Descrizione testuale dei tempi di disponibilità del servizio
     */
    @XmlElement(required = true)
    public String getDisponibilitaServizio() {
        return disponibilitaServizio;
    }

    public void setDisponibilitaServizio(String disponibilitaServizio) {
        this.disponibilitaServizio = disponibilitaServizio;
    }

    /**
     * Tipo Versamento
     */
    @XmlElement(required = true)
    public EnumTipoVersamento getTipoVersamento() {
        return tipoVersamento;
    }

    public void setTipoVersamento(EnumTipoVersamento tipoVersamento) {
        this.tipoVersamento = tipoVersamento;
    }

    /**
     * Modello Versamento
     */
    @XmlElement(required = true)
    public EnumModelloPagamento getModelloVersamento() {
        return modelloVersamento;
    }

    public void setModelloVersamento(EnumModelloPagamento modelloVersamento) {
        this.modelloVersamento = modelloVersamento;
    }

    /**
     * Priorità, campo informativo
     */
    public String getPriorita() {
        return priorita;
    }

    public void setPriorita(String priorita) {
        this.priorita = priorita;
    }

    /**
     * Vale true se lo strumento di pagamento è disponibile per pagare il carrello
     * passato come input, false in caso contrario. Se assente è come se fosse true.
     */
    public Boolean isDisponibilePerPagamentoCorrente() {
        return disponibilePerPagamentoCorrente;
    }

    public void setDisponibilePerPagamentoCorrente(Boolean disponibilePerPagamentoCorrente) {
        this.disponibilePerPagamentoCorrente = disponibilePerPagamentoCorrente;
    }

    /**
     * Presente se isDisponibilePerPagamentoCorrente = false, in tal caso descrive il motivo dell'indisponibilità:
     * (e.g. "lo strumento non è utilizzabile perché non supporta il carrello")
     */
    public String getDescrizioneIndisponibilitaPerPagamentoCorrente() {
        return descrizioneIndisponibilitaPerPagamentoCorrente;
    }

    public void setDescrizioneIndisponibilitaPerPagamentoCorrente(String descrizioneIndisponibilitaPerPagamentoCorrente) {
        this.descrizioneIndisponibilitaPerPagamentoCorrente = descrizioneIndisponibilitaPerPagamentoCorrente;
    }
}
