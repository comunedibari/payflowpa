package it.tasgroup.iris.dto.anonymous.payment;

import java.io.Serializable;

public class AnonymousTributoDTO implements Serializable{
	
	private int index;
	private String idTributo;
	private String deTrb;
	private String flPredeterm;
	private String istruzioniPagamento;
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getIdTributo() {
		return idTributo;
	}
	public void setIdTributo(String idTributo) {
		this.idTributo = idTributo;
	}
	public String getDeTrb() {
		return deTrb;
	}
	public void setDeTrb(String deTrb) {
		this.deTrb = deTrb;
	}
	public String getFlPredeterm() {
		return flPredeterm;
	}
	
	public void setFlPredeterm(String flPredeterm) {
		this.flPredeterm = flPredeterm;
		
	}

	public boolean isPredeterminato() {
		return "Y".equals(this.flPredeterm);
	}
	
	public String getIstruzioniPagamento() {
		return istruzioniPagamento;
	}
	public void setIstruzioniPagamento(String istruzioniPagamento) {
		this.istruzioniPagamento = istruzioniPagamento;
	}

}