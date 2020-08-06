package it.nch.iris.business.custom.supplier.servizi;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OnlineSearchCriteria implements Serializable {
	
    private Date dataDa;

    private Date dataA;

    private BigDecimal importoDa;

    private BigDecimal importoA;

    private int numOperazioniPerPagina;

    private String contoCorrenteBeneficiario;

    public Date getDataA() {
        return dataA;
    }

    public void setDataA(Date dataA) {
        this.dataA = dataA;
    }

    public Date getDataDa() {
        return dataDa;
    }

    public void setDataDa(Date dataDa) {
        this.dataDa = dataDa;
    }

    public BigDecimal getImportoA() {
        return importoA;
    }

    public void setImportoA(BigDecimal importoA) {
        this.importoA = importoA;
    }

    public BigDecimal getImportoDa() {
        return importoDa;
    }

    public void setImportoDa(BigDecimal importoDa) {
        this.importoDa = importoDa;
    }

    public int getNumOperazioniPerPagina() {
        return numOperazioniPerPagina;
    }

    public void setNumOperazioniPerPagina(int numOperazioniPerPagina) {
        this.numOperazioniPerPagina = numOperazioniPerPagina;
    }

    public java.lang.String getContoCorrenteBeneficiario() {
        return contoCorrenteBeneficiario;
    }

    public void setContoCorrenteBeneficiario(
            java.lang.String contoCorrenteBeneficiario) {
        this.contoCorrenteBeneficiario = contoCorrenteBeneficiario;
    }

}
