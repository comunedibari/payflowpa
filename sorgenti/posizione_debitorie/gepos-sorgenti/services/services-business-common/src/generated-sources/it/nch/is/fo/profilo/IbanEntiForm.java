package it.nch.is.fo.profilo;

import it.nch.fwk.fo.common.IBaseForm;

public interface IbanEntiForm extends IBaseForm {

	

	public void reset();
	
	public String getIdEnteIForm();
	
	public void setIdEnteIForm(String idEnteIForm);
	
	public String getIbanIForm();
	
	public void setIbanIForm(String ibanIForm);
	
	public String getDataCensimentoIForm() ;
	
	public void setDataCensimentoIForm(String dataCensimentoIForm);
	
	
	public String getDataAttivazioneIForm();
	
	
	public void setDataAttivazioneIForm(String dataAttivazioneIForm);

	
	
	public String getIdIForm();
	
	public void setIdIForm(String idIForm) ;

}