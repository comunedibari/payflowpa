package it.nch.is.fo.profilo;

import java.sql.Timestamp;
import java.util.Date;

import it.nch.fwk.fo.dto.business.Pojo;


public interface IbanEntiPojo  extends Pojo{
	
	public Long getId();
	
	public void setId(Long id);
	
	
	public String getIdEnte();
	
	public void setIdEnte(String idEnte);
	
	public String getIban();
	
	public void setIban(String iban);
	
	public Date getDataCensimento();
	
	public void setDataCensimento(Date dataCensimento);
	
	public Date getDataAttivazione();
	
	public void setDataAttivazione(Date dataAttivazione);
	
	public String getStRiga();
	
	public void setStRiga(String stRiga);
	
	public Timestamp getTsAggiornamento();
	
	public void setTsAggiornamento(Timestamp tsAggiornamento);
	
	public Timestamp getTsInserimento();
	
	public void setTsInserimento(Timestamp tsInserimento);
	
	public String getOpAggiornamento();
	
	public void setOpAggiornamento(String opAggiornamento);
	
	public void setOpInserimento(String opInserimento);
	
	public int getPrVersione();
	
	public void setPrVersione(int prVersione);
	
	

 
}