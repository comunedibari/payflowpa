/**
*
* Interfaccia generata
*
*/

package it.nch.is.fo.profilo;

import it.nch.fwk.fo.dto.business.Pojo;


public interface IntestatarioperatoriPojo extends Pojo{

    public IntestatariCommon getIntestatariobj();
    public void setIntestatariobj(IntestatariCommon intestatariobj);

    public OperatoriCommon getOperatoriobj();
    public void setOperatoriobj(OperatoriCommon operatoriobj);

    public String getTipoOperatore();
    public void setTipoOperatore(String tipoOperatore);
    
    public Integer getLocked();
    public void setLocked(Integer lock);

}
