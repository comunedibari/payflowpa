/**
*
* Interfaccia generata
*
*/

package it.nch.is.fo.profilo;

import it.nch.fwk.fo.common.CommonBusinessObject;
import it.nch.is.fo.tributi.ITributoEnte;

public interface TributiOperatoriCommon extends CommonBusinessObject, Comparable<TributiOperatoriCommon> {

	public IntestatarioperatoriCommon getIntestatarioperatoriobjIForm();
    public void setIntestatarioperatoriobjIForm(IntestatarioperatoriCommon intestatarioperatoribjIForm);
    
    public ITributoEnte getTributoenteobj();
	public void setTributoenteobj(ITributoEnte tributoenteobj);
	
	public IntestatarioperatoriCommon getIntestatarioperatori();
	public void setIntestatarioperatori(IntestatarioperatoriCommon intestatarioperatoriCommon);
	
	public String getCdTrbEntePk();
	public void setCdTrbEntePk(String cdTrbEntePk);
	
	public String getIdEntePk();
	public void setIdEntePk(String idEntePk);
	
	public String getIntestatarioPk();
	public void setIntestatarioPk(String intestatarioPk);

	public String getOperatorePk();
	public void setOperatorePk(String operatorePk);

	public String getTipoOperatore();
	public void setTipoOperatore(String tipoOperatore);

	public void setLockedIForm(String locked);
    public String getLockedIForm();
    

}