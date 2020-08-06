/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package it.tasgroup.iris.dto.anonymous.payment;

/**
 *
 * @author emanuela
 */
public class AnonymousEsitoDto {
    private String codice;
    private String policy;
    private String descrizione;
    private String dettaglioErrore;
    private String tipoErrore;

    public AnonymousEsitoDto() {
        
    }
    
    public AnonymousEsitoDto(String codice, String policy, String descrizione, String dettaglioErrore, String tipoErrore) {
        this.codice = codice;
        this.policy = policy;
        this.descrizione = descrizione;
        this.dettaglioErrore = dettaglioErrore;
        this.tipoErrore = tipoErrore;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getDettaglioErrore() {
        return dettaglioErrore;
    }

    public void setDettaglioErrore(String dettaglioErrore) {
        this.dettaglioErrore = dettaglioErrore;
    }

    public String getTipoErrore() {
        return tipoErrore;
    }

    public void setTipoErrore(String tipoErrore) {
        this.tipoErrore = tipoErrore;
    }
    
    
    
    
}
