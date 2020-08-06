package it.nch.is.fo.gestionePagamenti;

import it.nch.fwk.fo.common.IBaseForm;
import it.nch.is.fo.profilo.IntestatariCommon;

import java.math.BigDecimal;
import java.sql.Timestamp;

public interface IConfigurazioneForm extends ConfigurazionePagamentiCommon, IBaseForm {
	
	
	public CommissionePagamentoCommon getCommissionePagamento();
	public void setCommissionePagamento(CommissionePagamentoCommon commissionePagamento);
	public CircuitoPagamentoCommon getCircuitoPagamento();
	public void setCircuitoPagamento(CircuitoPagamentoCommon circuitoPagamento);	
	public StrumentoPagamentiCommon getStrumentoPagamento();
	public void setStrumentoPagamento(StrumentoPagamentiCommon strumentoPagamento);
	
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

	public String getCommissione();

	public void setCommissione(String commissione);
	
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
	
	public String[] getSelectedItems();
	public void setSelectedItems(String[] selectedItems);
	
	public String getTipoCommissione();
	public void setTipoCommissione(String tipoCommissione);

}
