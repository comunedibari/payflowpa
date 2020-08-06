package it.tasgroup.iris.dto.flussi;


import it.tasgroup.services.util.enumeration.EnumBusinessReturnCodes;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class ListaOperazioniPerEsitoDTOLight extends ListaEsitiDTOLight implements Serializable {
    private String esito;
    private Number numeroOperazioni;
    private BigDecimal percSuTotOperazioni;
    private String codErrore;
    
    //PagamentiOnLine
    private Timestamp tsOperazione;
    private Timestamp tsInserimento;
    private Timestamp tsAggiornamento;
    private String tiOperazione;
    private String sessionIdToken;
    private String sessionIdTimbro;
    private String sessionIdTerminale;
    private String sessionIdSistema;
    private String opInserimento;
    private String opAggiornamento;
    private String numOperazione;
    private String idOperazione;
    private String deOperazione;
    private String codAutorizzazione;
    private String applicationId;
    private String systemId;
    
    DistintePagamentoDTO distintaPagamento;

    public Timestamp getTsOperazione() {
        return tsOperazione;
    }

    public void setTsOperazione(Timestamp tsOperazione) {
        this.tsOperazione = tsOperazione;
    }

    public Timestamp getTsInserimento() {
        return tsInserimento;
    }

    public void setTsInserimento(Timestamp tsInserimento) {
        this.tsInserimento = tsInserimento;
    }

    public Timestamp getTsAggiornamento() {
        return tsAggiornamento;
    }

    public void setTsAggiornamento(Timestamp tsAggiornamento) {
        this.tsAggiornamento = tsAggiornamento;
    }

    public String getTiOperazione() {
        return tiOperazione;
    }

    public void setTiOperazione(String tiOperazione) {
        this.tiOperazione = tiOperazione;
    }

    public String getSessionIdToken() {
        return sessionIdToken;
    }

    public void setSessionIdToken(String sessionIdToken) {
        this.sessionIdToken = sessionIdToken;
    }

    public String getSessionIdTimbro() {
        return sessionIdTimbro;
    }

    public void setSessionIdTimbro(String sessionIdTimbro) {
        this.sessionIdTimbro = sessionIdTimbro;
    }

    public String getSessionIdTerminale() {
        return sessionIdTerminale;
    }

    public void setSessionIdTerminale(String sessionIdTerminale) {
        this.sessionIdTerminale = sessionIdTerminale;
    }

    public String getSessionIdSistema() {
        return sessionIdSistema;
    }

    public void setSessionIdSistema(String sessionIdSistema) {
        this.sessionIdSistema = sessionIdSistema;
    }

    public String getOpInserimento() {
        return opInserimento;
    }

    public void setOpInserimento(String opInserimento) {
        this.opInserimento = opInserimento;
    }

    public String getOpAggiornamento() {
        return opAggiornamento;
    }

    public void setOpAggiornamento(String opAggiornamento) {
        this.opAggiornamento = opAggiornamento;
    }

    public String getNumOperazione() {
        return numOperazione;
    }

    public void setNumOperazione(String numOperazione) {
        this.numOperazione = numOperazione;
    }

    public String getIdOperazione() {
        return idOperazione;
    }

    public void setIdOperazione(String idOperazione) {
        this.idOperazione = idOperazione;
    }

    public String getDeOperazione() {
        return deOperazione;
    }

    public void setDeOperazione(String deOperazione) {
        this.deOperazione = deOperazione;
    }

    public String getCodAutorizzazione() {
        return codAutorizzazione;
    }

    public void setCodAutorizzazione(String codAutorizzazione) {
        this.codAutorizzazione = codAutorizzazione;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getEsito() {
        return esito;
    }

    public void setEsito(String esito) {
        this.esito = esito;
    }

    public Number getNumeroOperazioni() {
        return numeroOperazioni;
    }

    public void setNumeroOperazioni(Number numeroOperazioni) {
        this.numeroOperazioni = numeroOperazioni;
    }
    public DistintePagamentoDTO getDistintaPagamento() {
        return distintaPagamento;
    }

    public void setDistintaPagamento(DistintePagamentoDTO distintaPagamento) {
        this.distintaPagamento = distintaPagamento;
    }

    public BigDecimal getPercSuTotOperazioni() {
        return percSuTotOperazioni;
    }

    public void setPercSuTotOperazioni(BigDecimal perctotOperazioni) {
        this.percSuTotOperazioni = perctotOperazioni;
    }

    public String getCodErrore() {
        return codErrore;
    }

    public void setCodErrore(String codErrore) {
        this.codErrore = codErrore;
    }
    
    public String getCodPagamento(){
        if(getDistintaPagamento() != null){
            return getDistintaPagamento().getCodPagamento();
        }
        
        return null;
    }
    
    public String getCodTransazione(){
        if(getDistintaPagamento() != null){
            return getDistintaPagamento().getCodTransazione();
        }
        
        return null;
    }
    
    public String getCodTransazionePSP(){
        if(getDistintaPagamento() != null){
            return getDistintaPagamento().getCodTransazionePSP();
        }
        
        return null;
    }
    
    public String getUtenteCreatore(){
        if(getDistintaPagamento() != null){
            return getDistintaPagamento().getUtenteCreatore();
        }
        
        return null;
    }
    
    public String getEmailVersante(){
        if(getDistintaPagamento() != null){
            return getDistintaPagamento().getEmailVersante();
        }
        
        return null;
    }
    
    public String getEsitoCompleto(){
        if(EnumBusinessReturnCodes.OK.getDescrizione().equals(getEsito())){
            return getEsito();
        } else {
            return getEsito() + "/" + getCodErrore();
        }
    }
}
