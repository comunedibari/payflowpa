/**
 * 
 */
package it.tasgroup.iris.dto.confpagamenti;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author PazziK
 *
 */
public class ScaglioneCommissioniDTO implements Serializable{
	
	/*** Persistent Properties ***/
	private String commissione;
	private String confPagamento;
	private String codScaglione;
	private BigDecimal daScaglione;
	private BigDecimal scaglioneA;	
	private String commissioneDescr;
	
	public String getCommissione() {
		return commissione;
	}
	public void setCommissione(String commissione) {
		this.commissione = commissione;
	}
	public String getConfPagamento() {
		return confPagamento;
	}
	public void setConfPagamento(String confPagamento) {
		this.confPagamento = confPagamento;
	}
	public String getCodScaglione() {
		return codScaglione;
	}
	public void setCodScaglione(String codScaglione) {
		this.codScaglione = codScaglione;
	}
	public BigDecimal getDaScaglione() {
		return daScaglione;
	}
	public void setDaScaglione(BigDecimal daScaglione) {
		this.daScaglione = daScaglione;
	}
	public BigDecimal getScaglioneA() {
		return scaglioneA;
	}
	public void setScaglioneA(BigDecimal scaglioneA) {
		this.scaglioneA = scaglioneA;
	}
	public String getCommissioneDescr() {
		return commissioneDescr;
	}
	public void setCommissioneDescr(String commissioneDescr) {
		this.commissioneDescr = commissioneDescr;
	}

}
