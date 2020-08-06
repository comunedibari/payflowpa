/**
*
* Interfaccia generata
*
*/

package it.nch.is.fo.profilo;

import it.nch.fwk.fo.common.CommonBusinessObject;

import java.util.Collection;

public interface ServiziCommon extends CommonBusinessObject {

    public String getServiceCodeIForm();

    public void setServiceCodeIForm(String serviceCodeIForm);

    public String getEnabledIForm();

    public void setEnabledIForm(String enabledIForm);

    public String getDescriptionIForm();

    public void setDescriptionIForm(String descriptionIForm);

    public Collection getFunzioniIForm();

    public void setFunzioniIForm(Collection funzioniIForm);

}
