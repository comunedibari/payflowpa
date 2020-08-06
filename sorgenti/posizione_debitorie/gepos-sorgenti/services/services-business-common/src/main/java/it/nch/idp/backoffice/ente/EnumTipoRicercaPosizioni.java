package it.nch.idp.backoffice.ente;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum EnumTipoRicercaPosizioni {
    PAGATA,
    DA_PAGARE,
    DA_PAGARE_RT_NEGATIVA,
    DA_PAGARE_RT_ASSENTE,
    DA_PAGARE_NO_RPT
}
