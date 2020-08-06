package it.tasgroup.iris.dto.confpagamenti;

import java.io.Serializable;

public class FilterGatewayPagamentoDTO implements Serializable{


	private static final long serialVersionUID = -3904087815157248162L;

	private String fornitoreGateway;
	private String systemName;
	private String strumentoPagamento;
	private String statoPagamento;
	private boolean isFilterEmpty;

	

	public boolean getIsFilterEmpty() {
		System.out.println("getIsFilterEmpty=" + isFilterEmpty);
		return isFilterEmpty;
	}
	
	public void setIsFilterEmpty() {
		isFilterEmpty = ((fornitoreGateway == null) || fornitoreGateway.equals("")) && 
		((systemName == null) || systemName.equals("")) &&
		((statoPagamento == null) || statoPagamento.equals("")) &&
		((strumentoPagamento == null) || strumentoPagamento.equals(""));
		System.out.println("setIsFilterEmpty with " + isFilterEmpty);
	}
	
	public String getFornitoreGateway() {
		return fornitoreGateway;
	}
	public void setFornitoreGateway(String fornitoreGateway) {
		this.fornitoreGateway = fornitoreGateway;
		setIsFilterEmpty();
	}
	public String getSystemName() {
		return systemName;
	}
	public void setSystemName(String systemName) {
		this.systemName = systemName;
		setIsFilterEmpty();
	}
	public String getStrumentoPagamento() {
		return strumentoPagamento;
	}
	public void setStrumentoPagamento(String strumentoPagamento) {
		this.strumentoPagamento = strumentoPagamento;
		setIsFilterEmpty();
	}
	
	public String getStatoPagamento() {
		return statoPagamento;
	}

	public void setStatoPagamento(String statoPagamento) {
		this.statoPagamento = statoPagamento;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	
}
