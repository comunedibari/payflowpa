package it.nch.pagamenti;

import java.math.BigDecimal;
import java.util.Date;


public abstract interface IMulteForm{

	public String getCdEnte();
	
	public void setCdEnte(String cdEnte);

	public String getDeEnte();
	
	public void setDeEnte(String deEnte);
	
	public String getCdTributo();
	
	public void setCdTributo(String cdTributo);
	
	public String getDeTributo();
	
	public void setDeTributo(String deTributo);
	
	public String getIdPagamento();
	
	public void setIdPagamento(String idPagamento);
	
	public String getTarga();
	
	public void setTarga(String targa);
	
	public String getNumeroVerbale();
	
	public void setNumeroVerbale(String numeroVerbale);
	
	public String getImportoSanzione();
	
	public void setImportoSanzione(String importoSanzione);
	
	public String getDataVerbaleYY();
	
	public void setDataVerbaleYY(String dataVerbaleAA);
	
	public String getDataVerbaleMM();
	
	public void setDataVerbaleMM(String dataVerbaleMM);
	
	public String getDataVerbaleGG();
	
	public void setDataVerbaleGG(String dataVerbaleGG);
	
	
	public String getSerie();
	
	public void setSerie(String serie);
	
	
	public String getAnnoVerbale();
	
	public void setAnnoVerbale(String annoVerbale);
	
	public String getNote();
	
	public void setNote(String note);
	
	public String getCommand();
	
	public void setCommand(String command);
	
	public String getTipoVerbale();
	
	public void setTipoVerbale(String tipoVerbale);
	
	public String getTipoTributo();
	
	public void setTipoTributo(String tipoTributo);
	
}
