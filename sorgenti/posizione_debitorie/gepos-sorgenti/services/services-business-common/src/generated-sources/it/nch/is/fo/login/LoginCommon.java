/**
*
* Interfaccia generata
*
*/

package it.nch.is.fo.login;

import it.nch.fwk.fo.common.CommonBusinessObject;

public interface LoginCommon extends CommonBusinessObject {

    public String getCorporateCodeIForm();

    public void setCorporateCodeIForm(String corporateCodeIForm);

    public String getUsernameIForm();

    public void setUsernameIForm(String usernameIForm);

    public String getPasswordIForm();

    public void setPasswordIForm(String passwordIForm);

    public String getCriptedPasswordIForm();

    public void setCriptedPasswordIForm(String criptedPasswordIForm);

    public String getCorporateStateIForm();

    public void setCorporateStateIForm(String corporateStateIForm);

    public String getToBeActivatedIForm();

    public void setToBeActivatedIForm(String toBeActivatedIForm);

    public String getRedirectUrlIForm();

    public void setRedirectUrlIForm(String redirectUrlIForm);


}
