package it.nch.iris.gateway;

import it.nch.fwk.fo.dto.business.Pojo;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Set;

public interface GatewayPojo extends Pojo{
	
	public abstract Collection getConfPagamenti();
	public abstract void setConfPagamenti(Set confPagamenti);
	public abstract String getGateway();

	public abstract void setGateway(String gateway);
	
	public abstract String getDeGateway();

	public abstract void setDeGateway(String deGateway);
	
	public abstract String getFornitore();

	public abstract void setFornitore(String fornitore);

	public Timestamp getDtAttivazione();

	public void setDtAttivazione(Timestamp dtAttivazione);
	
	public String getFlStato();

	public void setFlStato(String flStato);

	public String getCommissione();

	public void setCommissione(String commissione);

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
	



}
