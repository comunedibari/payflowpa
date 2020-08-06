package it.nch.iris.gateway;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Set;

import it.nch.fwk.fo.dto.business.Pojo;
import it.nch.is.fo.gestionePagamenti.CircuitoPagamentoCommon;
import it.nch.is.fo.gestionePagamenti.CommissionePagamentoCommon;
import it.nch.is.fo.gestionePagamenti.ICircuitoPagamento;
import it.nch.is.fo.gestionePagamenti.ICommissionePagamento;
import it.nch.is.fo.gestionePagamenti.GatewayCommon;
import it.nch.is.fo.gestionePagamenti.IStrumentoPagamento;
import it.nch.is.fo.gestionePagamenti.ScaglioneCommissioneCommon;
import it.nch.is.fo.gestionePagamenti.StrumentoPagamentiCommon;

public interface ConfigurazionePagamentiPojo extends Pojo{
	
	
	public CommissionePagamentoCommon getCommissionePagamento();
	public void setCommissionePagamento(CommissionePagamentoCommon commissionePagamento);
	public StrumentoPagamentiCommon getStrumentoPagamento();
	public void setStrumentoPagamento(StrumentoPagamentiCommon strumentoPagamento);
	public ScaglioneCommissioneCommon getScaglioneNewRow();
	public void setScaglioneNewRow(ScaglioneCommissioneCommon scaglioneNewRow);
	public ScaglioneCommissioneCommon getOltreScaglioneRow();
	public void setOltreScaglioneRow(ScaglioneCommissioneCommon oltreScaglioneRow);	
	public GatewayCommon getGateway_conf();
	public void setGateway_conf(GatewayCommon gateway_conf);
	public String getConfPagamento();

	public void setConfPagamento(String confPagamento);
	
	public BigDecimal getMaxImporto();

	public void setMaxImporto(BigDecimal maxImporto);
	
	public String getFlStato();

	public void setFlStato(String flStato);
	
	public String getGateway();

	public void setGateway(String gateway);
	
	public void setStrPagamento(String strPagamento) ;
	
	public String getStrPagamento();
	
	public String getCircuito();
	
	public void setCircuito(String circuito);
	
	public String getStRiga();

	public void setStRiga(String stRiga);
	
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


