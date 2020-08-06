package it.nch.is.fo.sistemienti;

import java.util.Date;

public interface ISistemaEnte {

	
	public abstract String getIdEnte();

	public abstract void setIdEnte(String idEnte);
	
	public abstract String getIdSystem();

	public abstract void setIdSystem(String idSystem);
	
	public abstract String getDeSystem();

	public abstract void setDeSystem(String deSystem);

	public int getPrVersione();

	public void setPrVersione(int prVersione);
	
	public String getOpInserimento();
	
	public void setOpInserimento(String opInserimento);

	public Date getTsInserimento();

	public void setTsInserimento(Date tsInserimento);

	public String getOpAggiornamento();

	public void setOpAggiornamento(String opAggiornamento);

	public Date getTsAggiornamento();

	public void setTsAggiornamento(Date tsAggiornamento);
	
	public String getDescEnte();
	
	public void setDescEnte(String descEnte);
	
	public abstract String getStato();
	
	public abstract void setStato(String stato);	
	
	public abstract String getResult();
	
	public abstract void setResult(String result);

	public String getsSilEnabledAsString();
	
	public void setsSilEnabledAsString(String sSilEnabledAsString);

}