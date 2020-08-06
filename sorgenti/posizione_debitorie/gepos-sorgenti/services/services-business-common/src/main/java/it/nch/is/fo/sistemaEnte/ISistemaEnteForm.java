package it.nch.is.fo.sistemaEnte;

import java.util.Date;


public interface ISistemaEnteForm {
	public String getIdEnte();
	public void setIdEnte(String idEnte);
	public String getIdSystem();
	public void setIdSystem(String idSystem);
	public String getDeSystem();
	public void setDeSystem(String deSystem);
	public String getStato();
	public void setStato(String stato);
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
	public String[] getSelectedItems();
	public void setSelectedItems(String[] selectedItems);
	public String getDescEnte();
	public void setDescEnte(String descEnte);
	public String getIsSSilEnabled();
	public void setIsSSilEnabled(String isSSilEnabled);

}
