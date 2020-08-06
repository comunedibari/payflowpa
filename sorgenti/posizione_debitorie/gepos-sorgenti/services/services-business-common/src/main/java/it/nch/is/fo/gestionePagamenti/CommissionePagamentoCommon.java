package it.nch.is.fo.gestionePagamenti;

import java.math.BigDecimal;
import java.sql.Timestamp;

import it.nch.fwk.fo.common.CommonBusinessObject;

public interface CommissionePagamentoCommon extends CommonBusinessObject {
	
	public abstract String getCommissione();
	public abstract void setCommissione(String commissione);
	public abstract String getTipo();
	public abstract void setTipo(String tipo);
	public String getValoreIForm();
	public void setValoreIForm(String valoreIForm);
	public abstract String getDivisa();
	public abstract void setDivisa(String divisa);
	public abstract String getFlStato();
	public abstract void setFlStato(String flStato);
	public abstract String getStRiga();
	public abstract void setStRiga(String stRiga);
	public abstract Integer getPrVersione();
	public abstract void setPrVersione(Integer prVersione);
	public abstract String getOpInserimento();
	public abstract void setOpInserimento(String opInserimento);
	public abstract Timestamp getTsInserimento();
	public abstract void setTsInserimento(Timestamp tsInserimento);
	public abstract String getOpAggiornamento();
	public abstract void setOpAggiornamento(String opAggiornamento);
	public abstract Timestamp getTsAggiornamento();
	public abstract void setTsAggiornamento(Timestamp tsAggiornamento);
}