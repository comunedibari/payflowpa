/**
*
* Interfaccia generata
*
*/

package it.nch.is.fo.profilo;

import it.nch.fwk.fo.dto.business.Pojo;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

public interface OperatoriPojo extends Pojo , Comparable<OperatoriPojo>{

    public String getOperatore();

    public void setOperatore(String operatore);

    public String getUsername();

    public void setUsername(String username);

    public String getCorporate();

    public void setCorporate(String corporate);

    public Integer getLocked();

    public void setLocked(Integer locked);

    public String getMobile();

    public void setMobile(String mobile);

    public String getSignerCode();

    public void setSignerCode(String signerCode);

    public String getDescription();

    public void setDescription(String description);

//    public String getEmail();

//    public void setEmail(String email);

    public String getName();

    public void setName(String name);

    public Integer getNumFailedlogon();

    public void setNumFailedlogon(Integer numFailedlogon);

    public String getPassword();

    public void setPassword(String password);

    public String getPlainPassword();

    public void setPlainPassword(String plainPassword);

    public Timestamp getLastLogon();

    public void setLastLogon(Timestamp lastLogon);

    public Date getExpirationDate();

    public void setExpirationDate(Date expirationDate);

    public Timestamp getLockDate();

    public void setLockDate(Timestamp lockDate);

    public Timestamp getFailedLogonDate();

    public void setFailedLogonDate(Timestamp failedLogonDate);

    public IntestatariCommon getIntestatariobj();

    public void setIntestatariobj(IntestatariCommon intestatariobj);

    public IntestatarioperatoriCommon getIntestatarioperatoriobj();

	public void setIntestatarioperatoriobj(IntestatarioperatoriCommon intestatarioperatoriobj);

    public Set<IntestatarioperatoriCommon> getIntestatarioperatori();

    public void setIntestatarioperatori(Set<IntestatarioperatoriCommon> intestatarioperatori);

    public Set<IntestatariCommon> getIntestatari();

    public void setIntestatari(Set<IntestatariCommon> intestatari);

    public void setCodiceFiscale(String codiceFiscale);
    
    public String getCodiceFiscale();

    public String getSurname();
    
    public void setSurname(String cognome);
    
    public Collection<OperatoriPojo> getDeleganti();
    
    public void setDeleganti(Collection<OperatoriPojo> deleganti); 

}
