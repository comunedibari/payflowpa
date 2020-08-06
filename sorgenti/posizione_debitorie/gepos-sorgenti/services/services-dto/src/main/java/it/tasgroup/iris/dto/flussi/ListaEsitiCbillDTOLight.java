package it.tasgroup.iris.dto.flussi;


import java.io.Serializable;
import java.math.BigDecimal;

public class ListaEsitiCbillDTOLight extends ListaEsitiDTOLight implements Serializable {
    private String canale;
    private String codiceBiller;
    private String idVersamento;
    private String idDebito;
    private BigDecimal importoTotale;
    private BigDecimal commissioniBiller;
    private BigDecimal commissioniBanca;
    private String ibanAccredito;
    private String bancaOrdinante;
    private String bancaBeneficiario;
    private String codTransazionePSP;
    private String dettagliAtm;

    public String getCanale() {
        return canale;
    }

    public void setCanale(String canale) {
        this.canale = canale;
    }

    public String getCodiceBiller() {
        return codiceBiller;
    }

    public void setCodiceBiller(String codiceBiller) {
        this.codiceBiller = codiceBiller;
    }

    public String getIdVersamento() {
        return idVersamento;
    }

    public void setIdVersamento(String idVersamento) {
        this.idVersamento = idVersamento;
    }

    public String getIdDebito() {
        return idDebito;
    }

    public void setIdDebito(String idDebito) {
        this.idDebito = idDebito;
    }

    public BigDecimal getImportoTotale() {
        return importoTotale;
    }

    public void setImportoTotale(BigDecimal importoTotale) {
        this.importoTotale = importoTotale;
    }

    public BigDecimal getCommissioniBiller() {
        return commissioniBiller;
    }

    public void setCommissioniBiller(BigDecimal commissioniBiller) {
        this.commissioniBiller = commissioniBiller;
    }

    public BigDecimal getCommissioniBanca() {
        return commissioniBanca;
    }

    public void setCommissioniBanca(BigDecimal commissioniBanca) {
        this.commissioniBanca = commissioniBanca;
    }

    public String getIbanAccredito() {
        return ibanAccredito;
    }

    public void setIbanAccredito(String ibanAccredito) {
        this.ibanAccredito = ibanAccredito;
    }

    public String getBancaOrdinante() {
        return bancaOrdinante;
    }

    public void setBancaOrdinante(String bancaOrdinante) {
        this.bancaOrdinante = bancaOrdinante;
    }

    public String getBancaBeneficiario() {
        return bancaBeneficiario;
    }

    public void setBancaBeneficiario(String bancaBeneficiario) {
        this.bancaBeneficiario = bancaBeneficiario;
    }

    public String getCodTransazionePSP() {
        return codTransazionePSP;
    }

    public void setCodTransazionePSP(String codTransazionePSP) {
        this.codTransazionePSP = codTransazionePSP;
    }

    public String getDettagliAtm() {
        return dettagliAtm;
    }

    public void setDettagliAtm(String dettagliAtm) {
        this.dettagliAtm = dettagliAtm;
    }
    	
}
