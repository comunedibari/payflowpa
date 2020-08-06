package it.nch.is.fo.configurazionePagamento;

import org.apache.struts.validator.ValidatorActionForm;

public class CfgStrumentoPagamentoForm extends ValidatorActionForm{
	private String [] selectedItems;

	public String [] getSelectedItems() {
		return selectedItems;
	}

	public void setSelectedItems(String [] selectedItems) {
		this.selectedItems = selectedItems;
	}
	

}
