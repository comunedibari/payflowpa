package it.tasgroup.iris.dto.query.support;

import it.tasgroup.idp.rs.enums.EnumTipoVersamento;
import it.tasgroup.services.util.enumeration.EnumUtils;

import java.math.BigDecimal;

/**
 *
 */
public class StatistichePerCanaleStatoQueryDTO extends StatisticheTestataQueryDTO {
    private String systemId;
    private String systemName;
    private String applicationId;
    private EnumTipoVersamento tipoVersamento;
    
    public StatistichePerCanaleStatoQueryDTO(String systemId, String systemName, String applicationId, String tipoVersamento, String statoPagamento, String statoIncasso, long numeroPagamenti, BigDecimal totale) {
        super(statoPagamento, statoIncasso, numeroPagamenti, totale);
        this.systemId = systemId;
        this.systemName = systemName;
        this.applicationId = applicationId;
        this.tipoVersamento = EnumUtils.findByChiave(tipoVersamento, EnumTipoVersamento.class);
    }

    public String getSystemId() {
        return systemId;
    }

    public String getSystemName() {
        return systemName;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public EnumTipoVersamento getTipoVersamento() {
        return tipoVersamento;
    }
}
