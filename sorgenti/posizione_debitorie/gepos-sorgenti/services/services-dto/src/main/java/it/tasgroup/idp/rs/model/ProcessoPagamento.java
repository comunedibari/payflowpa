package it.tasgroup.idp.rs.model;

import it.tasgroup.idp.rs.enums.EnumModelloPagamento;
import it.tasgroup.idp.rs.enums.EnumStatoPagamento;
import it.tasgroup.idp.rs.enums.EnumTipoVersamento;
import it.tasgroup.services.util.enumeration.EnumStatoDistintePagamento;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Processo di pagamento
 */
@XmlRootElement
public class ProcessoPagamento implements Serializable {

    public ProcessoPagamento() {
    }

    public ProcessoPagamento(String codPagamento, Date dataEsecuzione, EnumTipoVersamento tipoVersamento, EnumModelloPagamento modelloPagamento, 
    		String codPrestatoreServizio, String systemId, String systemName, EnumStatoDistintePagamento stato, BigDecimal importoTotale, 
    		String codFiscaleVersante, String emailVersante, String descrizioneVersante, String urlRedirect, String modalitaPagamento) {
        this.codPagamento = codPagamento;
        this.dataEsecuzione = dataEsecuzione;
        this.tipoVersamento = tipoVersamento;
        this.modelloPagamento = modelloPagamento;
        this.codPrestatoreServizio = codPrestatoreServizio;
        this.systemId = systemId;
        this.systemName = systemName;
        this.stato = stato;
        this.importoTotale = importoTotale;
        this.codFiscaleVersante = codFiscaleVersante;
        this.emailVersante = emailVersante;
        this.descrizioneVersante = descrizioneVersante;
        this.urlRedirect = urlRedirect;
        this.modalitaPagamento = modalitaPagamento;
    }

    private String codPagamento;

    private Date dataEsecuzione;

    private EnumTipoVersamento tipoVersamento;

    private EnumModelloPagamento modelloPagamento;

    private String codPrestatoreServizio;

    private String systemId;

    private String systemName;

    private EnumStatoDistintePagamento stato;

    private BigDecimal importoTotale;

    private String codFiscaleVersante;

    private String emailVersante;

    private String descrizioneVersante;

    private String urlRedirect;

    private List<ProcessoPagamentoCondizione> processoPagamentoCondizioneList;

    private String modalitaPagamento;

    /**
     * Codice univoco associato dalla piattaforma al processo di pagamento
     */
    @XmlElement(required = true)
    public String getCodPagamento() {
        return codPagamento;
    }

    public void setCodPagamento(String codPagamento) {
        this.codPagamento = codPagamento;
    }

    /**
     * Timestamp (in millisecondi) di esecuzione del pagamento, presente solo se il pagamento è eseguito.
     */
    public Date getDataEsecuzione() {
        return dataEsecuzione;
    }

    public void setDataEsecuzione(Date dataEsecuzione) {
        this.dataEsecuzione = dataEsecuzione;
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
     * Modello Pagamento
     */
    @XmlElement(required = true)
    public EnumModelloPagamento getModelloPagamento() {
        return modelloPagamento;
    }

    public void setModelloPagamento(EnumModelloPagamento modelloPagamento) {
        this.modelloPagamento = modelloPagamento;
    }

    /**
     * Identificativo dello strumento di pagamento utilizzato.
     */
    @XmlElement(required = true)
    public String getCodPrestatoreServizio() {
        return codPrestatoreServizio;
    }

    public void setCodPrestatoreServizio(String codPrestatoreServizio) {
        this.codPrestatoreServizio = codPrestatoreServizio;
    }

    /**
     * Identificativo dell'istituto presso il quale avviene il pagamento (Identificativo PSP in terminologia AgId)
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
     * Ragione Sociale dell'istituto presso il quale avviene il pagamento
     */
    @XmlElement(required = true)
    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    /**
     * Stato del pagamento
     */
    @XmlElement(required = true)
    public EnumStatoDistintePagamento getStato() {
        return stato;
    }

    public void setStato(EnumStatoDistintePagamento stato) {
        this.stato = stato;
    }

    /**
     * Importo totale del pagamento
     */
    @XmlElement(required = true)
    public BigDecimal getImportoTotale() {
        return importoTotale;
    }

    public void setImportoTotale(BigDecimal importoTotale) {
        this.importoTotale = importoTotale;
    }

    /**
     * Codice fiscale del soggetto che ha effettuato il pagamento
     */
    @XmlElement(required = true)
    public String getCodFiscaleVersante() {
        return codFiscaleVersante;
    }

    public void setCodFiscaleVersante(String codFiscaleVersante) {
        this.codFiscaleVersante = codFiscaleVersante;
    }

    /**
     * Email del soggetto che ha effettuato il pagamento
     */
    @XmlElement(required = true)
    public String getEmailVersante() {
        return emailVersante;
    }

    public void setEmailVersante(String emailVersante) {
        this.emailVersante = emailVersante;
    }

    /**
     * Descrizione (e.g. Nome Cognome) del soggetto che ha effettuato il pagamento
     */
    public String getDescrizioneVersante() {
        return descrizioneVersante;
    }

    public void setDescrizioneVersante(String descrizioneVersante) {
        this.descrizioneVersante = descrizioneVersante;
    }

    /**
     * Presente solo in risposta all'apertura del pagamento e solo nel caso di pagamenti immediati.
     * E' l'URL nel portale del PSP scelto da invocare per andare ad eseguire fisicamente il pagamento.
     */
    public String getUrlRedirect() {
        return urlRedirect;
    }

    public void setUrlRedirect(String urlRedirect) {
        this.urlRedirect = urlRedirect;
    }

    /**
     * Lista (carrello) delle CondizioniPagamento pagate.
     */
    @XmlElement(required = true)
    public List<ProcessoPagamentoCondizione> getProcessoPagamentoCondizioneList() {
        return processoPagamentoCondizioneList;
    }

    public void setProcessoPagamentoCondizioneList(
            List<ProcessoPagamentoCondizione> processoPagamentoCondizioneList) {
        this.processoPagamentoCondizioneList = processoPagamentoCondizioneList;
    }

    /**
	 * Modalita' pagamento.
	 */
	@XmlElement
	public String getModalitaPagamento() {
		return modalitaPagamento;
	}


	public void setModalitaPagamento(String modalitaPagamento) {
		this.modalitaPagamento = modalitaPagamento;
	}


}
