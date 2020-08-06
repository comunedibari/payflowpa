package it.nch.is.fo.gestionePagamenti;

import java.sql.Timestamp;

public interface ICircuitoPagamento {
	
	public abstract String getCircuito();

	public abstract void setCircuito(String circuito);
	
	public abstract String getDeCircuito();

	public void setDeCircuito(String deCircuito);
	
	public String getCommissione();
	
	public void setCommissione(String commissione);
	
	public String getFlStato();

	public void setFlStato(String flStato);

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