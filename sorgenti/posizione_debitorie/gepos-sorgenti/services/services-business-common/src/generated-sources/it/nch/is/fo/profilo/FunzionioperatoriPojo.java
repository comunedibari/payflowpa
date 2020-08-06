/**
*
* Interfaccia generata
*
*/

package it.nch.is.fo.profilo;

import it.nch.fwk.fo.dto.business.Pojo;
import it.nch.is.fo.profilo.FunzionioperatoriCommon;
import it.nch.is.fo.profilo.FunzioniCommon;

public interface FunzionioperatoriPojo extends Pojo{

    public String getFunctionCode();

    public void setFunctionCode(String functionCode);

    public String getCorporate();

    public void setCorporate(String corporate);

    public String getOperatore();

    public void setOperatore(String operatore);

    public FunzioniCommon getFunzioniobj();

    public void setFunzioniobj(FunzioniCommon funzioniobj);


}
