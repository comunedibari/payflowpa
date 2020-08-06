/**
*
* Interfaccia generata
*
*/

package it.nch.is.fo.profilo;

import it.nch.fwk.fo.dto.business.Pojo;

import java.util.Collection;
import java.util.Set;

public interface FunzioniPojo extends Pojo{

    public String getFunctionCode();

    public void setFunctionCode(String functionCode);

    public String getDescription();

    public void setDescription(String description);

    public String getEnabled();

    public void setEnabled(String enabled);

    public String getAccessed();

    public void setAccessed(String accessed);

    public Integer getPriority();

    public void setPriority(Integer priority);

    public String getOperatorType();

    public void setOperatorType(String operatorType);

    public ServiziCommon getServiziobj();

    public void setServiziobj(ServiziCommon serviziobj);

    public Collection getFunzionioperatori();

    public void setFunzionioperatori(Set funzionioperatori);

}
