/**
*
* Interfaccia generata
*
*/

package it.nch.is.fo.profilo;

import it.nch.fwk.fo.common.CommonBusinessObject;
import it.nch.is.fo.profilo.ApplicazioniCommon;
import java.util.Collection;

public interface AreeCommon extends CommonBusinessObject {

    public String getAreaCodeIForm();

    public void setAreaCodeIForm(String areaCodeIForm);

    public String getUpdateDateIForm();

    public void setUpdateDateIForm(String updateDateIForm);

    public String getUpdateUserIForm();

    public void setUpdateUserIForm(String updateUserIForm);

    public String getDescriptionIForm();

    public void setDescriptionIForm(String descriptionIForm);

    public String getEnabledIForm();

    public void setEnabledIForm(String enabledIForm);

    public ApplicazioniCommon getApplicazioneobjIForm();

    public void setApplicazioneobjIForm(ApplicazioniCommon applicazioneobjIForm);

    public Collection getServiziIForm();

    public void setServiziIForm(Collection serviziIForm);


}
