package it.nch.iris.gateway;

import java.math.BigDecimal;
import java.sql.Timestamp;

import it.nch.fwk.fo.dto.business.Pojo;

public interface ScaglioneCommissionePojo extends Pojo {
	
	public abstract String getCommissione();
	public abstract void setCommissione(String commissione);
	public String getConfPagamento();
	public void setConfPagamento(String confPagamento);
	public String getCodScaglione();
	public void setCodScaglione(String codScaglione);
	public BigDecimal getDaScaglione();
	public void setDaScaglione(BigDecimal daScaglione);
	public BigDecimal getScaglioneA();
	public void setScaglioneA(BigDecimal scaglioneA);
	public ConfigurazionePagamentiPojo getConfigobj();
	public void setConfigobj(ConfigurazionePagamentiPojo configobj);

}
