package it.nch.is.fo.login.rt;

import it.nch.fwk.fo.common.IBaseForm;

public interface LoginRTForm extends LoginRTCommon, IBaseForm {

    public String getLanguageIForm();

    public void setLanguageIForm(String languageIForm);


	  public void reset();

}
