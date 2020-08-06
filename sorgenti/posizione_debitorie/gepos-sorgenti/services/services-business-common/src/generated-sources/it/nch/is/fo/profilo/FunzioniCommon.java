/**
*
* Interfaccia generata
*
*/

package it.nch.is.fo.profilo;

import it.nch.fwk.fo.common.CommonBusinessObject;

import java.util.Collection;

public interface FunzioniCommon extends CommonBusinessObject {

    public String getFunctionCodeIForm();

    public void setFunctionCodeIForm(String functionCodeIForm);

    public String getDescriptionIForm();

    public void setDescriptionIForm(String descriptionIForm);

    public String getEnabledIForm();

    public void setEnabledIForm(String enabledIForm);

    public String getAccessedIForm();

    public void setAccessedIForm(String accessedIForm);

    public String getPriorityIForm();

    public void setPriorityIForm(String priorityIForm);

    public String getOperatorTypeIForm();

    public void setOperatorTypeIForm(String operatorTypeIForm);

    public ServiziCommon getServiziobjIForm();

    public void setServiziobjIForm(ServiziCommon serviziobjIForm);

    public Collection getFunzionioperatoriIForm();

    public void setFunzionioperatoriIForm(Collection funzionioperatoriIForm);

}
