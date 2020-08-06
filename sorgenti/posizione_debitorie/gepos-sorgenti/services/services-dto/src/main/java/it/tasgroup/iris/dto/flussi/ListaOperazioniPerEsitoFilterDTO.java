package it.tasgroup.iris.dto.flussi;

import it.tasgroup.services.util.enumeration.EnumBusinessReturnCodes;
import java.io.Serializable;
import java.sql.Timestamp;

public class ListaOperazioniPerEsitoFilterDTO implements Serializable {
    
    private Timestamp tsInserimentoDa;
    private Timestamp tsInserimentoA;
    
    private String systemId; //SenderId
    private String applicationId;
    private String tiOperazione;
    private String codicePagamento;
    private String sessionIdToken;
    private String esito;
    private String utenteCreatore; //DISTINTE_PAGAMENTO.UTENTE_CREATORE
    private String codErrore;

    public String getCodErrore() {
        return codErrore;
    }

    public void setCodErrore(String codErrore) {
        this.codErrore = codErrore;
    }
    
    public String getUtenteCreatore() {
        return utenteCreatore;
    }

    public void setUtenteCreatore(String utenteCreatore) {
        this.utenteCreatore = utenteCreatore;
    }
    
    public Timestamp getTsInserimentoDa() {
        return tsInserimentoDa;
    }

    public void setTsInserimentoDa(Timestamp tsInserimentoDa) {
        this.tsInserimentoDa = tsInserimentoDa;
    }

    public Timestamp getTsInserimentoA() {
        return tsInserimentoA;
    }

    public void setTsInserimentoA(Timestamp tsInserimentoA) {
        this.tsInserimentoA = tsInserimentoA;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getTiOperazione() {
        return tiOperazione;
    }

    public void setTiOperazione(String tiOperazione) {
        this.tiOperazione = tiOperazione;
    }

    public String getCodicePagamento() {
        return codicePagamento;
    }

    public void setCodicePagamento(String codicePagamento) {
        this.codicePagamento = codicePagamento;
    }

    public String getSessionIdToken() {
        return sessionIdToken;
    }

    public void setSessionIdToken(String sessionIdToken) {
        this.sessionIdToken = sessionIdToken;
    }

    public String getEsito() {
        return esito;
    }

    public void setEsito(String esito) {
        this.esito = esito;
    }
    
    public String getEsitoCompleto(){
        if(EnumBusinessReturnCodes.OK.getDescrizione().equals(getEsito())){
            return getEsito();
        } else {
            return getEsito() + "/" + getCodErrore();
        }
    }
}
