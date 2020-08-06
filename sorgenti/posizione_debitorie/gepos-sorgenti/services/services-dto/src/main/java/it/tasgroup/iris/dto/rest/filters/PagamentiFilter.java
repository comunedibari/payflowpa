package it.tasgroup.iris.dto.rest.filters;
 
import it.tasgroup.idp.rs.enums.EnumStatoIncasso;
import it.tasgroup.idp.rs.enums.EnumStatoPagamento;

import java.io.Serializable;
import java.util.Date;


public class PagamentiFilter implements Serializable {
    private final Date dataOraPagamentoA;
    private String idCreditore;
    private String tipoDebito;
    private String idDebito;
    private String idPagamento;
    private String identificativoUnivocoVersamento;
    private String codiceFiscaleDebitore;
    private EnumStatoPagamento enumStatoPagamento;
    private EnumStatoIncasso flagIncasso;
    private Date dataOraPagamentoDa;
    private String catTributo;

    public String getCatTributo() {
		return catTributo;
	}

	public PagamentiFilter(String idCreditore, String tipoDebito, String idDebito,
                           String idPagamento, String identificativoUnivocoVersamento,
                           String codiceFiscaleDebitore, EnumStatoPagamento enumStatoPagamento, EnumStatoIncasso flagIncasso, Date dataOraPagamentoDa, Date dataOraPagamentoA) {
        this.idCreditore = idCreditore;
        this.tipoDebito = tipoDebito;
        this.idDebito = idDebito;
        this.idPagamento = idPagamento;
        this.identificativoUnivocoVersamento = identificativoUnivocoVersamento;
        this.codiceFiscaleDebitore = codiceFiscaleDebitore;
        this.enumStatoPagamento = enumStatoPagamento;
        this.flagIncasso = flagIncasso;
        this.dataOraPagamentoDa = dataOraPagamentoDa;
        this.dataOraPagamentoA = dataOraPagamentoA;
    }
    
    public PagamentiFilter(String codiceFiscaleDebitore) {
    	//idCondizione = null;
    	dataOraPagamentoA = null;
    	this.codiceFiscaleDebitore = codiceFiscaleDebitore;
    }

    public PagamentiFilter(String codiceFiscaleDebitore, String catTributo) {
    	//idCondizione = null;
    	dataOraPagamentoA = null;
    	this.codiceFiscaleDebitore = codiceFiscaleDebitore;
    	this.catTributo = catTributo;
	}

    public Date getDataOraPagamentoA() {
        return dataOraPagamentoA;
    }

    public String getIdCreditore() {
        return idCreditore;
    }

    public String getTipoDebito() {
        return tipoDebito;
    }

    public String getIdDebito() {
        return idDebito;
    }

    public String getIdPagamento() {
        return idPagamento;
    }

    public String getIdentificativoUnivocoVersamento() {
        return identificativoUnivocoVersamento;
    }

    public String getCodiceFiscaleDebitore() {
        return codiceFiscaleDebitore;
    }

    public EnumStatoPagamento getEnumStatoPagamento() {
        return enumStatoPagamento;
    }

    public EnumStatoIncasso getFlagIncasso() {
        return flagIncasso;
    }

    public Date getDataOraPagamentoDa() {
        return dataOraPagamentoDa;
    }
}
