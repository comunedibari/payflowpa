package it.nch.is.fo.enti;

import it.nch.fwk.fo.common.IBaseForm;

public interface TipologiaEntiForm extends TipologiaEntiCommon, IBaseForm {

	public String getAdminPasswordIForm();

	public void setAdminPasswordIForm(String adminPasswordIForm);

	public void reset();

}