package it.tasgroup.iris.dto.query.support;

import it.tasgroup.idp.rs.enums.EnumStatoIncasso;
import it.tasgroup.idp.rs.enums.EnumStatoPagamento;
import it.tasgroup.services.util.enumeration.EnumUtils;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 */
public class StatisticheTestataQueryDTO implements Serializable {
    private EnumStatoPagamento statoPagamento;
    private EnumStatoIncasso statoIncasso;
    private long numeroPagamenti;
    private BigDecimal totale;

    public StatisticheTestataQueryDTO(String statoPagamento, String statoIncasso, long numeroPagamenti, BigDecimal totale) {
        this.statoPagamento = EnumUtils.byChiave(statoPagamento,EnumStatoPagamento.class);
        this.statoIncasso = EnumUtils.byChiave(statoIncasso,EnumStatoIncasso.class);
        this.numeroPagamenti = numeroPagamenti;
        this.totale = totale;
    }

    public EnumStatoPagamento getStatoPagamento() {
        return statoPagamento;
    }

    public EnumStatoIncasso getStatoIncasso() {
        return statoIncasso;
    }

    public long getNumeroPagamenti() {
        return numeroPagamenti;
    }

    public BigDecimal getTotale() {
        return totale;
    }
}
