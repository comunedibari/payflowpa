package it.nch.is.fo.gestionePagamenti;

import java.sql.Timestamp;

public interface IMulte{
	
	public String getIdPagamento();
	public void setIdPagamento(String idPagamento);
	public String getTarga();
	public void setTarga(String targa);
	public String getNumeroVerbale();
	public void setNumeroVerbale(String numeroVerbale);
	public Timestamp getDataVerbale();
	public void setDataVerbale(Timestamp dataVerbale);
	public String getAnnoVerbale();
	public void setAnnoVerbale(String annoVerbale);
	public String getSerie();
	public void setSerie(String serie);
	public String getNote();
	public void setNote(String note);
	public String getOpInserimento();
	public void setOpInserimento(String opInserimento);
	public Timestamp getTsInserimento();
	public void setTsInserimento(Timestamp tsInserimento);
	public String getOpAggiornamento();
	public void setOpAggiornamento(String opAggiornamento);
	public Timestamp getTsAggiornamento();
	public void setTsAggiornamento(Timestamp tsAggiornamento);
    
}