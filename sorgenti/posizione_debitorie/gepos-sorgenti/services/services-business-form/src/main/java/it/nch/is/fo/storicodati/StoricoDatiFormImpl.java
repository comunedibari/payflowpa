package it.nch.is.fo.storicodati;

import it.nch.fwk.fo.base.BaseForm;
import it.nch.fwk.fo.common.CommonBusinessObject;
import it.nch.fwk.fo.dto.DTO;

public class StoricoDatiFormImpl extends BaseForm implements IStoricoDatiForm {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String nomeProcesso;

	public String getNomeProcesso() {
		return nomeProcesso;
	}

	public void setNomeProcesso(String nomeProcesso) {
		this.nomeProcesso = nomeProcesso;
	}

	@Override
	public CommonBusinessObject copy() {
		// TODO Auto-generated method stub
		return null;
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
	public void setNativePojo(Object arg0) {
		// TODO Auto-generated method stub
		
	}


}
