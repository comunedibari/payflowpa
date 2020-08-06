package it.nch.is.fo.profilo;

import it.nch.fwk.fo.base.BaseForm;
import it.nch.fwk.fo.common.CommonBusinessObject;
import it.nch.fwk.fo.dto.DTO;

public class IbanEntiFormImpl extends BaseForm implements IbanEntiForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3547539259647395054L;
	private String idEnteIForm;
	
	private String[] selectedItems;
	
	private String ibanIForm;
	private String dataAttivazioneIForm;
	private String dataCensimentoIForm;
	private String idIForm;
	
	private String dataCensimentoGG;
	private String dataCensimentoMM;
	private String dataCensimentoYY;
	
	private String dataAttivazioneGG;
    private String dataAttivazioneMM;
    private String dataAttivazioneYY;
    private String corporateIForm;
    
	@Override
	public String getIdEnteIForm() {
		return idEnteIForm;
	}

	@Override
	public void setIdEnteIForm(String idEnteIForm) {
		this.idEnteIForm = idEnteIForm;
		
	}

	@Override
	public String getIbanIForm() {
		return ibanIForm;
	}

	@Override
	public void setIbanIForm(String ibanIForm) {
		this.ibanIForm = ibanIForm;
		
	}

	@Override
	public String getDataCensimentoIForm() {
		return dataCensimentoIForm;
	}

	@Override
	public void setDataCensimentoIForm(String dataCensimentoIForm) {
		this.dataCensimentoIForm =dataCensimentoIForm;
		
	}

	@Override
	public String getDataAttivazioneIForm() {
		return dataAttivazioneIForm;
	}

	@Override
	public void setDataAttivazioneIForm(String dataAttivazioneIForm) {
		this.dataAttivazioneIForm=dataAttivazioneIForm;
		
	}

	@Override
	public String getIdIForm() {
		return idIForm;
	}

	@Override
	public void setIdIForm(String idIForm) {
		this.idIForm=idIForm;
		
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
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

	public String[] getSelectedItems() {
		return selectedItems;
	}

	public void setSelectedItems(String[] selectedItems) {
		this.selectedItems = selectedItems;
	}

	public String getDataCensimentoGG() {
		return dataCensimentoGG;
	}

	public void setDataCensimentoGG(String dataCensimentoGG) {
		this.dataCensimentoGG = dataCensimentoGG;
	}

	public String getDataCensimentoMM() {
		return dataCensimentoMM;
	}

	public void setDataCensimentoMM(String dataCensimentoMM) {
		this.dataCensimentoMM = dataCensimentoMM;
	}

	public String getDataCensimentoYY() {
		return dataCensimentoYY;
	}

	public void setDataCensimentoYY(String dataCensimentoYY) {
		this.dataCensimentoYY = dataCensimentoYY;
	}

	public String getDataAttivazioneGG() {
		return dataAttivazioneGG;
	}

	public void setDataAttivazioneGG(String dataAttivazioneGG) {
		this.dataAttivazioneGG = dataAttivazioneGG;
	}

	public String getDataAttivazioneMM() {
		return dataAttivazioneMM;
	}

	public void setDataAttivazioneMM(String dataAttivazioneMM) {
		this.dataAttivazioneMM = dataAttivazioneMM;
	}

	public String getDataAttivazioneYY() {
		return dataAttivazioneYY;
	}

	public void setDataAttivazioneYY(String dataAttivazioneYY) {
		this.dataAttivazioneYY = dataAttivazioneYY;
	}

	public String getCorporateIForm() {
		return corporateIForm;
	}

	public void setCorporateIForm(String corporateIForm) {
		this.corporateIForm = corporateIForm;
	}

	
	
}
