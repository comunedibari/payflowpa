package it.nch.is.fo.gestionePagamenti;

import it.nch.fwk.fo.common.IBaseForm;

import java.sql.Timestamp;
import java.util.Date;


public interface IGatewayForm extends GatewayCommon, IBaseForm {
	public String getGateway();
	public void setGateway(String gateway);
	public String getDeGateway();
	public void setDeGateway(String deGateway);
	public String getFornitore();
	public void setFornitore(String fornitore);
	public String getFlStato();
	public void setFlStato(String flStato);
	public String getCommissione();
	public void setCommissione(String commissione);
	public String getStRiga();
	public void setStRiga(String stRiga);
	public Integer getPrVersione();
	public void setPrVersione(Integer prVersione);
	public String getOpInserimento();
	public String getTsInserimentoCommon();

	public void setTsInserimentoCommon(String tsInserimento);
	
	public String getOpAggiornamento();
	
	public void setOpAggiornamento(String opAggiornamento);
	
	public String getTsAggiornamentoCommon();

	public void setTsAggiornamentoCommon(String tsAggiornamento);
	
	public String[] getSelectedItems();
	public void setSelectedItems(String[] selectedItems);


}
