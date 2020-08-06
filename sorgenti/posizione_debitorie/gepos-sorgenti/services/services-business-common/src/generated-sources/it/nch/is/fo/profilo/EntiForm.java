
package it.nch.is.fo.profilo;

import it.nch.fwk.fo.common.IBaseForm;

public interface EntiForm extends EntiCommon, IBaseForm {

    public String getAdminPasswordIForm();

    public void setAdminPasswordIForm(String adminPasswordIForm);
    
	public void reset();

}
