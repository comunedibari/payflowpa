package it.tasgroup.idp.rs.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;


/**
 *
 */
@XmlRootElement
public class ProcessoPagamentoCondizione implements Serializable {
    public ProcessoPagamentoCondizione() {
    }

    public ProcessoPagamentoCondizione(String idDominio, String identificativoUnivocoVersamento, String codiceContestoPagamento, String codiceUnivocoRiscossione, String urlDownloadRicevuta, CondizionePagamento condizionePagamento) {
        this.idDominio = idDominio;
        this.identificativoUnivocoVersamento = identificativoUnivocoVersamento;
        this.codiceContestoPagamento = codiceContestoPagamento;
        this.codiceUnivocoRiscossione = codiceUnivocoRiscossione;
        this.urlDownloadRicevuta = urlDownloadRicevuta;
        this.condizionePagamento = condizionePagamento;
    }

    private String idDominio;
    private String identificativoUnivocoVersamento;
    private String codiceContestoPagamento;
    private String codiceUnivocoRiscossione;
    private String urlDownloadRicevuta;
    private CondizionePagamento condizionePagamento;

    /**
     * Codice Fiscale Ente creditore (idDominio in terminologia AgID). Assieme ai campi: <em>identificativoUnivocoVersamento</em> e <em>codiceContestoPagamento</em> rappresentano la chiave della transazione nel nodo SPC.
     */
    @XmlElement(required = true)
    public String getIdDominio() {
        return idDominio;
    }

    public void setIdDominio(String idDominio) {
        this.idDominio = idDominio;
    }

    /**
     * IUV del versamento (in terminologia AgID)
     */
    @XmlElement(required = true)
    public String getIdentificativoUnivocoVersamento() {
        return identificativoUnivocoVersamento;
    }

    public void setIdentificativoUnivocoVersamento(
            String identificativoUnivocoVersamento) {
        this.identificativoUnivocoVersamento = identificativoUnivocoVersamento;
    }

    /**
     * Codice Contesto pagamento (in terminologia AgID)
     */
    @XmlElement(required = true)
    public String getCodiceContestoPagamento() {
        return codiceContestoPagamento;
    }

    public void setCodiceContestoPagamento(String codiceContestoPagamento) {
        this.codiceContestoPagamento = codiceContestoPagamento;
    }

    /**
     * Codice univoco riscossione (in terminologia AgID), presente solo se il pagamento è eseguito.
     * Codice della transazione assegnato dal PSP.
     */
    public String getCodiceUnivocoRiscossione() {
        return codiceUnivocoRiscossione;
    }

    public void setCodiceUnivocoRiscossione(String codiceUnivocoRiscossione) {
        this.codiceUnivocoRiscossione = codiceUnivocoRiscossione;
    }

    /**
     * URL che permette il download della ricevuta PDF, presente solo se il pagamento è eseguito.
     */
    public String getUrlDownloadRicevuta() {
        return urlDownloadRicevuta;
    }

    public void setUrlDownloadRicevuta(String urlDownloadRicevuta) {
        this.urlDownloadRicevuta = urlDownloadRicevuta;
    }

    /**
     * Dati completi della condizione di pagamento.
     */
    @XmlElement(required = true)
    public CondizionePagamento getCondizionePagamento() {
        return condizionePagamento;
    }

    public void setCondizionePagamento(CondizionePagamento condizionePagamento) {
        this.condizionePagamento = condizionePagamento;
    }


}
