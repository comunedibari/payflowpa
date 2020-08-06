package it.nch.is.fo.gestionePagamenti;

import it.nch.fwk.fo.common.CommonBusinessObject;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Set;


public interface ConfigurazionePagamentiCommon extends CommonBusinessObject {
	
	public final static String CONF_ATTIVA = "A";
	public final static String CONF_DISATTIVA = "D";
	public final static String CONF_ATTIVA_TEXT = "Attivo";
	public final static String CONF_DISATTIVA_TEXT = "Disattivo";
	public final static String TESTO_ATTIVO = "testonormale";
	public final static String TESTO_DISATTIVO = "testodisattivo";
	public final static String COMM_PERCENTUALE = "P";
	public final static String COMM_DIVISA = "V";
	
	public abstract CommissionePagamentoCommon getCommissionePagamento();
	public abstract void setCommissionePagamento(CommissionePagamentoCommon commissionePagamento);	
	
	public abstract StrumentoPagamentiCommon getStrumentoPagamento();
	public abstract void setStrumentoPagamento(StrumentoPagamentiCommon strumentoPagamento);
	public abstract GatewayCommon getGateway_conf();
	public abstract void setGateway_conf(GatewayCommon gateway_conf);
	public abstract String getConfPagamento();

	public abstract void setConfPagamento(String confPagamento);
	
	public ScaglioneCommissioneCommon getOltreScaglioneRow();

	public void setOltreScaglioneRow(ScaglioneCommissioneCommon oltreScaglioneRow);	
	
	public ScaglioneCommissioneCommon getScaglioneNewRow();

	public void setScaglioneNewRow(ScaglioneCommissioneCommon scaglioneCommissione);
	
	public abstract BigDecimal getMaxImporto();

	public abstract void setMaxImporto(BigDecimal maxImporto);
	
	public String getFlStato();

	public void setFlStato(String flStato);
	
	public abstract String getGateway();

	public abstract void setGateway(String gateway);
	
	public abstract void setStrPagamento(String strPagamento) ;
	
	public abstract String getStrPagamento();
	
	public abstract String getCircuito();
	
	public abstract void setCircuito(String circuito);
	
	public abstract String getStRiga();

	public abstract void setStRiga(String stRiga);
	
	public Integer getPrVersione();
	
	public void setPrVersione(Integer prVersione);
	
	public String getOpInserimento();
	
	public void setOpInserimento(String opInserimento);
	
	public Timestamp getTsInserimento();

	public void setTsInserimento(Timestamp tsInserimento);
	
	public String getOpAggiornamento();
	
	public void setOpAggiornamento(String opAggiornamento);
	
	public Timestamp getTsAggiornamento();

	public void setTsAggiornamento(Timestamp tsAggiornamento);
	
	public String getTipoCommissione();
	public void setTipoCommissione(String tipoCommissione);
	
	public Collection getScaglioniCommissioni();
	public void setScaglioniCommissioni(Set scaglioniCommissioni);
	public String getDescrTipoPagamento();
	public void setDescrTipoPagamento(String descrTipoPagamento);
}
