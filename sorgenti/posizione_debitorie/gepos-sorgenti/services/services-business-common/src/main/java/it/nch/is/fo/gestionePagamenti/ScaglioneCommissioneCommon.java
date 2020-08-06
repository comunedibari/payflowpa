package it.nch.is.fo.gestionePagamenti;

import java.math.BigDecimal;

import it.nch.fwk.fo.common.CommonBusinessObject;


public interface ScaglioneCommissioneCommon extends CommonBusinessObject {
	
	public String getCommissione();
	public void setCommissione(String commissione);
	public String getConfPagamento();
	public void setConfPagamento(String confPagamento);
	public String getCodScaglione();
	public void setCodScaglione(String codScaglione);
	public String getDaScaglioneIForm();
	public void setDaScaglioneIForm(String daScaglioneIForm);
	public String getScaglioneAIForm();
	public void setScaglioneAIForm(String scaglioneAIForm);
	public String getCommissioneDescr();
	public void setCommissioneDescr(String commissioneDescr);
}