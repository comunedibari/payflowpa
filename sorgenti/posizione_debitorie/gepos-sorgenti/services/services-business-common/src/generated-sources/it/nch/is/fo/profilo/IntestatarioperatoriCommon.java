/**
*
* Interfaccia generata
*
*/

package it.nch.is.fo.profilo;

import it.nch.fwk.fo.common.CommonBusinessObject;

import java.util.Set;

public interface IntestatarioperatoriCommon extends CommonBusinessObject,Comparable<IntestatarioperatoriCommon> {

    public IntestatariCommon getIntestatariobjIForm();
    public void setIntestatariobjIForm(IntestatariCommon intestatariobjIForm);

    public OperatoriCommon getOperatoriobjIForm();
    public void setOperatoriobjIForm(OperatoriCommon intestatariobjIForm);

    public String getTipoOperatoreIForm();
    public void setTipoOperatoreIForm(String tipoOperatoreIForm);
    
    public void setLockedIForm(String locked);
    public String getLockedIForm();
    
    public Set<TributiOperatoriCommon> getTributiOperatoreForm();
    public void setTributiOperatoreForm(Set<TributiOperatoriCommon> tributiOperatore);

}
