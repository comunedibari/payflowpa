package it.nch.is.fo.enti;

import it.nch.fwk.fo.common.CommonBusinessObject;

public interface TipologiaEntiCommon extends CommonBusinessObject{
	
	public String getTipoIForm();
	public String getDescrizioneIForm();
	public void setTipoIForm(String tipoIForm);
	public void setDescrizioneIForm(String descrizioneIForm);

}
