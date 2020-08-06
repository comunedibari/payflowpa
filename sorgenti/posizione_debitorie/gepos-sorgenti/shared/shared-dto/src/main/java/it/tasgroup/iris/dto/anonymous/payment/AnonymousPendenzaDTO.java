package it.tasgroup.iris.dto.anonymous.payment;


import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;


public class AnonymousPendenzaDTO implements Serializable {
    
    private String id;
    private String idente;
    private String tipoDebito;
    private String causaleDebito;
    private String idDebitoEnte;
    private String note;
    private List<String[]> destinatari;
    private String codEnte;
    private String sil;
    private String idPagamento;
    private Double totalePagamentiParziali;
    
    private Timestamp tsEmissioneEnte;
    private Timestamp tsPrescrizione;
    private Integer anno;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdente() {
        return idente;
    }

    public void setIdente(String idente) {
        this.idente = idente;
    }

    public String getTipoDebito() {
        return tipoDebito;
    }

    public void setTipoDebito(String tipoDebito) {
        this.tipoDebito = tipoDebito;
    }

    public String getCausaleDebito() {
        return causaleDebito;
    }

    public void setCausaleDebito(String causaleDebito) {
        this.causaleDebito = causaleDebito;
    }

    public String getIdDebitoEnte() {
        return idDebitoEnte;
    }

    public void setIdDebitoEnte(String idDebitoEnte) {
        this.idDebitoEnte = idDebitoEnte;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Timestamp getTsEmissioneEnte() {
        return tsEmissioneEnte;
    }

    public void setTsEmissioneEnte(Timestamp tsEmissioneEnte) {
        this.tsEmissioneEnte = tsEmissioneEnte;
    }

    public Timestamp getTsPrescrizione() {
        return tsPrescrizione;
    }

    public void setTsPrescrizione(Timestamp tsPrescrizione) {
        this.tsPrescrizione = tsPrescrizione;
    }

    public Integer getAnno() {
        return anno;
    }

    public void setAnno(Integer anno) {
        this.anno = anno;
    }

    public List<String[]> getDestinatari() {
        return destinatari;
    }

    public void setDestinatari(List<String[]> destinatari) {
        this.destinatari = destinatari;
    }

    public String getCodEnte() {
        return codEnte;
    }

    public void setCodEnte(String codEnte) {
        this.codEnte = codEnte;
    }

    public String getSil() {
        return sil;
    }

    public void setSil(String sil) {
        this.sil = sil;
    }

    public String getIdPagamento() {
        return idPagamento;
    }

    public void setIdPagamento(String idPagamento) {
        this.idPagamento = idPagamento;
    }

    public Double getTotalePagamentiParziali() {
        return totalePagamentiParziali;
    }

    public void setTotalePagamentiParziali(Double totalePagamentiParziali) {
        this.totalePagamentiParziali = totalePagamentiParziali;
    }

}
