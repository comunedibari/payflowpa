/**
*
* Interfaccia generata
*
*/

package it.nch.is.fo.profilo;

import java.util.Set;

import it.nch.fwk.fo.common.CommonBusinessObject;
import it.nch.is.fo.profilo.IntestatariCommon;

public interface OperatoriCommon extends CommonBusinessObject {

    public String getOperatoreIForm();

    public void setOperatoreIForm(String operatoreIForm);

    public String getUsernameIForm();

    public void setUsernameIForm(String usernameIForm);

    public String getCorporateIForm();

    public void setCorporateIForm(String corporateIForm);

    public String getLockedIForm();

    public void setLockedIForm(String lockedIForm);

    public String getMobileIForm();

    public void setMobileIForm(String mobileIForm);

    public String getSignerCodeIForm();

    public void setSignerCodeIForm(String signerCodeIForm);

    public String getDescriptionIForm();

    public void setDescriptionIForm(String descriptionIForm);

//    public String getEmailIForm();

//    public void setEmailIForm(String emailIForm);

    public String getNameIForm();

    public void setNameIForm(String nameIForm);

    public String getNumFailedlogonIForm();

    public void setNumFailedlogonIForm(String numFailedlogonIForm);

    public String getPasswordIForm();

    public void setPasswordIForm(String passwordIForm);

    public String getPlainPasswordIForm();

    public void setPlainPasswordIForm(String plainPasswordIForm);

    public String getLastLogonIForm();

    public void setLastLogonIForm(String lastLogonIForm);

    public String getExpirationDateIForm();

    public void setExpirationDateIForm(String expirationDateIForm);

    public String getLockDateIForm();

    public void setLockDateIForm(String lockDateIForm);

    public String getFailedLogonDateIForm();

    public void setFailedLogonDateIForm(String failedLogonDateIForm);

    public IntestatariCommon getIntestatariobjIForm();

    public void setIntestatariobjIForm(IntestatariCommon intestatariobjIForm);

    public Set<IntestatariCommon> getIntestatariIForm();

    public void setIntestatariIForm(Set<IntestatariCommon> intestatariIForm);

    public String getCodiceFiscaleIForm();

    public void setCodiceFiscaleIForm(String codiceFiscaleIForm);

    public String getSurnameIForm();

    public void setSurnameIForm(String surnameIForm);
    
    public Set<IntestatarioperatoriCommon> getIntestatarioperatoriIForm();

    public void setIntestatarioperatoriIForm(Set<IntestatarioperatoriCommon> intestatarioperatori);

	void setFlagOperatorTypeIForm(String flagOperatorTypeIForm);

	String getFlagOperatorTypeIForm();
	
	void setFlagTributiAmmessi(String flagTributiAmmessi);

	String getFlagTributiAmmessi();
	
	void setTributiAmmessiCollection(Set<TributiOperatoriCommon> tributiOperatori);
	
	Set<TributiOperatoriCommon> getTributiAmmessiCollection();
	
    public String getEnteIForm();

    public void setEnteIForm(String enteIForm);

	
}