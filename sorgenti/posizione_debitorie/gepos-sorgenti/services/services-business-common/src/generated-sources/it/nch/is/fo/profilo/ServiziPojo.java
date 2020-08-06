/**
*
* Interfaccia generata
*
*/

package it.nch.is.fo.profilo;

import it.nch.fwk.fo.dto.business.Pojo;

import java.util.Collection;
import java.util.Set;

public interface ServiziPojo extends Pojo{

    public String getServiceCode();

    public void setServiceCode(String serviceCode);

    public String getEnabled();

    public void setEnabled(String enabled);

    public String getDescription();

    public void setDescription(String description);

    public AreePojo getAreaobj();

    public void setAreaobj(AreePojo areaobj);

    public Collection getFunzioni();

    public void setFunzioni(Set funzioni);

}
