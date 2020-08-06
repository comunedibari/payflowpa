package it.nch.iris.gateway;

import java.sql.Timestamp;

import it.nch.fwk.fo.dto.business.Pojo;

public interface StrumentoPagamentoPojo extends Pojo {
	
	public abstract String getStrPagamento();
	public abstract void setStrPagamento(String strPagamento);
	public abstract String getDeStrPagamento();
	public abstract void setDeStrPagamento(String deStrPagamento);
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
	public String getTipoStrumento();
	public void setTipoStrumento(String tipoStrumento);
}
