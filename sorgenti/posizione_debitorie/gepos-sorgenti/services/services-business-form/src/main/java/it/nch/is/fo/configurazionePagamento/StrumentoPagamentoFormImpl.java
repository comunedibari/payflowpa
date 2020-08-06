package it.nch.is.fo.configurazionePagamento;

import it.nch.fwk.fo.base.BaseForm;
import it.nch.fwk.fo.common.CommonBusinessObject;
import it.nch.fwk.fo.dto.DTO;
import it.nch.is.fo.gestionePagamenti.IStrumentoPagamento;

public class StrumentoPagamentoFormImpl extends BaseForm implements IStrumentoPagamento {

	private String strPagamento;
	private String deStrPagamento;
	private String flStato;
	private String stRiga;
	private String tipoStrumento;
	
	private String [] selectedItems;
	private String isNew;
	
	
	public String getIsNew() {
		return isNew;
	}
	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}
	


	public String[] getSelectedItems() {
		return selectedItems;
	}


	public void setSelectedItems(String[] selectedItems) {
		this.selectedItems = selectedItems;
	}
	
	

	public String getStrPagamento() {
		return strPagamento;
	}
	public void setStrPagamento(String strPagamento) {
		this.strPagamento = strPagamento;
	}
	public String getDeStrPagamento() {
		return deStrPagamento;
	}
	public void setDeStrPagamento(String deStrPagamento) {
		this.deStrPagamento = deStrPagamento;
	}
	public String getFlStato() {
		return flStato;
	}
	public void setFlStato(String flStato) {
		this.flStato = flStato;
	}
	public String getStRiga() {
		return stRiga;
	}
	public void setStRiga(String stRiga) {
		this.stRiga = stRiga;
	}
	@Override
	public void setNativePojo(Object nativePojo) {
		// TODO Auto-generated method stub
		
	}	
	public String getTipoStrumento() {
		return tipoStrumento;
	}
	public void setTipoStrumento(String tipoStrumento) {
		this.tipoStrumento = tipoStrumento;
	}
	
	@Override
	public DTO incapsulateBO() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public CommonBusinessObject copy() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
