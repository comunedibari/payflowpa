package it.tasgroup.iris.dto.flussi;

import java.io.Serializable;
import java.sql.Timestamp;

public class DettaglioCasellarioDTOLight implements Serializable{
    
    private Long id;
    private Timestamp dataCreazione;
    private Timestamp dataRicezione;
    private Timestamp dataSpedizione;
    private String tipoFlusso;
    private String nomeSupporto;
    private Integer numeroRecord;
    private int numeroDisposizioni;
    private Integer numeroEsiti;
    private String utenteCreatore;
    private Integer numEsitiInsoluto;
    private Integer numEsitiPagato;
    private String codTransazione;
    private String flagElaborazione;
    private Timestamp dataElaborazione;
    private Object tipoErrore;
    private String descErrore;
    private Long idRendicontazione;
    private Long idDistintaPagamento;
    private String mittente;
    private String ricevente;
    
    public Long getIdRendicontazione() {
        return idRendicontazione;
    }
    public void setIdRendicontazione(Long idRendicontazione) {
        this.idRendicontazione = idRendicontazione;
    }
    public Long getIdDistintaPagamento() {
        return idDistintaPagamento;
    }
    public void setIdDistintaPagamento(Long idDistintaPagamento) {
        this.idDistintaPagamento = idDistintaPagamento;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Timestamp getDataCreazione() {
        return dataCreazione;
    }
    public void setDataCreazione(Timestamp dataCreazione) {
        this.dataCreazione = dataCreazione;
    }
    public Timestamp getDataRicezione() {
        return dataRicezione;
    }
    public void setDataRicezione(Timestamp dataRicezione) {
        this.dataRicezione = dataRicezione;
    }
    public Timestamp getDataSpedizione() {
        return dataSpedizione;
    }
    public void setDataSpedizione(Timestamp dataSpedizione) {
        this.dataSpedizione = dataSpedizione;
    }
    public String getTipoFlusso() {
        return tipoFlusso;
    }
    public void setTipoFlusso(String tipoFlusso) {
        this.tipoFlusso = tipoFlusso;
    }
    public String getNomeSupporto() {
        return nomeSupporto;
    }
    public void setNomeSupporto(String nomeSupporto) {
        this.nomeSupporto = nomeSupporto;
    }
    public Integer getNumeroRecord() {
        return numeroRecord;
    }
    public void setNumeroRecord(Integer numeroRecord) {
        this.numeroRecord = numeroRecord;
    }
    public int getNumeroDisposizioni() {
        return numeroDisposizioni;
    }
    public void setNumeroDisposizioni(int numeroDisposizioni) {
        this.numeroDisposizioni = numeroDisposizioni;
    }
    public Integer getNumeroEsiti() {
        return numeroEsiti;
    }
    public void setNumeroEsiti(Integer numeroEsiti) {
        this.numeroEsiti = numeroEsiti;
    }
    public String getUtenteCreatore() {
        return utenteCreatore;
    }
    public void setUtenteCreatore(String utenteCreatore) {
        this.utenteCreatore = utenteCreatore;
    }
    public Integer getNumEsitiInsoluto() {
        return numEsitiInsoluto;
    }
    public void setNumEsitiInsoluto(Integer numEsitiInsoluto) {
        this.numEsitiInsoluto = numEsitiInsoluto;
    }
    public Integer getNumEsitiPagato() {
        return numEsitiPagato;
    }
    public void setNumEsitiPagato(Integer numEsitiPagato) {
        this.numEsitiPagato = numEsitiPagato;
    }
    public String getCodTransazione() {
        return codTransazione;
    }
    public void setCodTransazione(String codTransazione) {
        this.codTransazione = codTransazione;
    }
    public String getFlagElaborazione() {
        return flagElaborazione;
    }
    public void setFlagElaborazione(String flagElaborazione) {
        this.flagElaborazione = flagElaborazione;
    }
    public Timestamp getDataElaborazione() {
        return dataElaborazione;
    }
    public void setDataElaborazione(Timestamp dataElaborazione) {
        this.dataElaborazione = dataElaborazione;
    }
    public Object getTipoErrore() {
        return tipoErrore;
    }
    public void setTipoErrore(Object tipoErrore) {
        this.tipoErrore = tipoErrore;
    }
    public String getDescErrore() {
        return descErrore;
    }
    public void setDescErrore(String descErrore) {
        this.descErrore = descErrore;
    }
    
    public String getMittente() {
        return mittente;
    }
    
    public void setMittente(String mittente) {
        this.mittente = mittente;
    }
    
    public String getRicevente() {
        return ricevente;
    }
    
    public void setRicevente(String ricevente) {
        this.ricevente = ricevente;
    }
    
}
