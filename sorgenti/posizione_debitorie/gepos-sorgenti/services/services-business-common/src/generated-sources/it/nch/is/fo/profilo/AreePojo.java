/**
*
* Interfaccia generata
*
*/

package it.nch.is.fo.profilo;

import it.nch.fwk.fo.dto.business.Pojo;
import it.nch.is.fo.profilo.AreeCommon;
import java.util.Date;
import java.util.Set;

import it.nch.is.fo.profilo.ApplicazioniCommon;
import java.util.Collection;

public interface AreePojo extends Pojo{

    public String getAreaCode();

    public void setAreaCode(String areaCode);

    public Date getUpdateDate();

    public void setUpdateDate(Date updateDate);

    public String getUpdateUser();

    public void setUpdateUser(String updateUser);

    public String getDescription();

    public void setDescription(String description);

    public String getEnabled();

    public void setEnabled(String enabled);

    public ApplicazioniCommon getApplicazioneobj();

    public void setApplicazioneobj(ApplicazioniCommon applicazioneobj);

    public Collection getServizi();

    public void setServizi(Set servizi);


}
