/**
*
* Interfaccia generata
*
*/

package it.nch.is.fo.profilo;

import it.nch.fwk.fo.common.IBaseForm;

public interface TributiOperatoriForm extends TributiOperatoriCommon, IBaseForm {
	
	public String getCdTrbEnte() ;
	public void setCdTrbEnte(String cdTrbEnte);
	
	public String getIdEnte();
	public void setIdEnte(String idEnte);
	
	public String getDeTrb();
	public void setDeTrb(String deTrb);

    public void reset();

}
