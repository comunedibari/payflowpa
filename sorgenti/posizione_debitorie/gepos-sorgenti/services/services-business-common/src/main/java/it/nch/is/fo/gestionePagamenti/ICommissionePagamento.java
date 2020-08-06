package it.nch.is.fo.gestionePagamenti;

import java.sql.Timestamp;

public interface ICommissionePagamento {
	
	public abstract String getCommissione();

	public abstract void setCommissione(String commissione);
	
	public abstract String getTipo();

	public void setTipo(String tipo);

	public Double getValore();
	public void setValore(Double valore);
	public String getDivisa();
	public void setDivisa(String divisa);
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
