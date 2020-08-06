package it.nch.is.fo.login.rt;

import it.nch.fwk.fo.common.CommonBusinessObject;

public interface LoginRTCommon extends CommonBusinessObject {

    public String getCodiceFiscaleIForm();

    public void setCodiceFiscaleIForm(String codiceFiscaleIForm);
    
    public String getPasswordIForm();

    public void setPasswordIForm(String passwordIForm);

    public String getCriptedPasswordIForm();

    public void setCriptedPasswordIForm(String criptedPasswordIForm);

    public String getRedirectUrlIForm();

    public void setRedirectUrlIForm(String redirectUrlIForm);


}
