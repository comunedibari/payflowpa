/**
*
* Interfaccia generata
*
*/

package it.nch.is.fo.profilo;

import it.nch.is.fo.profilo.IntestatariCommon;
import it.nch.fwk.fo.common.IBaseForm;

public interface IntestatariForm extends IntestatariCommon, IBaseForm {

    public String getAdminPasswordIForm();

    public void setAdminPasswordIForm(String adminPasswordIForm);

    public String getNameIForm();
	
    public void setNameIForm(String nameIForm);
	
    public String getSurnameIForm();
	
    public void setSurnameIForm(String surnameIForm);

	public void reset();

}
