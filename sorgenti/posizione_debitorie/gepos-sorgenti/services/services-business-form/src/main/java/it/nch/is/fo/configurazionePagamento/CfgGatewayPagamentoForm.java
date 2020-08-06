package it.nch.is.fo.configurazionePagamento;


import java.util.Collection;

import org.apache.struts.validator.ValidatorActionForm;

public class CfgGatewayPagamentoForm extends ValidatorActionForm{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String [] selectedItems;
//	private CfgGatewayPagamentoDTO dtoelemnt;

	protected Collection<?> listaStrumentoPagamento;
	private String strumentoPagamento;
	private String statoPagamento;
	private String fornitoreGateway;
	private String systemName; //Psp
	protected Collection<?> listaCfgGatewayPagamentoDistinctSystemName;
	protected Collection<?> listaCfgFornitoreGateway;
	
	
	public String [] getSelectedItems() {
		return selectedItems;
	}

	public void setSelectedItems(String [] selectedItems) {
		this.selectedItems = selectedItems;
	}



	public Collection<?> getListaStrumentoPagamento() {
		return listaStrumentoPagamento;
	}

	public void setListaStrumentoPagamento(Collection<?> listaStrumentoPagamento) {
		this.listaStrumentoPagamento = listaStrumentoPagamento;
	}

	public String getStrumentoPagamento() {
		return strumentoPagamento;
	}

	public void setStrumentoPagamento(String strumentoPagamento) {
		this.strumentoPagamento = strumentoPagamento;
	}

	public String getFornitoreGateway() {
		return fornitoreGateway;
	}

	public void setFornitoreGateway(String fornitoreGateway) {
		this.fornitoreGateway = fornitoreGateway;
	}

	public String getStatoPagamento() {
		return statoPagamento;
	}

	public void setStatoPagamento(String statoPagamento) {
		this.statoPagamento = statoPagamento;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public void setListaCfgGatewayPagamentoDistinctSystemName(
			Collection<?> listaCfgGatewayPagamento) {
		this.listaCfgGatewayPagamentoDistinctSystemName=listaCfgGatewayPagamento;
		
	}

	public Collection<?> getListaCfgGatewayPagamentoDistinctSystemName() {
		return listaCfgGatewayPagamentoDistinctSystemName;
	}

	public void setListaCfgFornitoreGateway(
			Collection<?> listaCfgFornitoreGateway) {
		this.listaCfgFornitoreGateway=listaCfgFornitoreGateway;
		
	}

	public Collection<?> getListaCfgFornitoreGateway() {
		return listaCfgFornitoreGateway;
	}


}
