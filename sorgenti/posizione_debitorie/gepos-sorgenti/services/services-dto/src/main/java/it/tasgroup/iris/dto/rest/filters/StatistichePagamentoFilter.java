package it.tasgroup.iris.dto.rest.filters;

import java.io.Serializable;
import java.util.Date;


public class StatistichePagamentoFilter implements Serializable {
    private final Date dataOraPagamentoDa;
    private final Date dataOraPagamentoA;
    private final String tipoDebito;
    private final boolean statistichePerCanale;
    private final boolean statistichePerStato;
    private String creditore;
    private String idEnte;

    public StatistichePagamentoFilter(String creditore, Date dataOraPagamentoDa, Date dataOraPagamentoA, String tipoDebito, boolean statistichePerCanale, boolean statistichePerStato) {
        this.creditore = creditore;
        this.dataOraPagamentoDa = dataOraPagamentoDa;
        this.dataOraPagamentoA = dataOraPagamentoA;
        this.tipoDebito = tipoDebito;
        this.statistichePerCanale = statistichePerCanale;
        this.statistichePerStato = statistichePerStato;
    }

    public Date getDataOraPagamentoDa() {
        return dataOraPagamentoDa;
    }

    public Date getDataOraPagamentoA() {
        return dataOraPagamentoA;
    }

    public String getTipoDebito() {
        return tipoDebito;
    }

    public boolean isStatistichePerCanale() {
        return statistichePerCanale;
    }

    public boolean isStatistichePerStato() {
        return statistichePerStato;
    }

    public String getCreditore() {
        return creditore;
    }


    public String getIdEnte() {
        return idEnte;
    }

    public void setIdEnte(String idEnte) {
        this.idEnte = idEnte;
    }
}
