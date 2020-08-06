/**
*
* Interfaccia generata
*
*/

package it.nch.is.fo.profilo;

import it.nch.fwk.fo.dto.business.Pojo;
import it.nch.is.fo.profilo.PasswordCommon;
import java.util.Date;

public interface PasswordPojo extends Pojo{

    public String getPwdId();

    public void setPwdId(String pwdId);

    public String getOperator_id();

    public void setOperator_id(String operator_id);

    public String getPassword();

    public void setPassword(String password);

    public Date getChangeDate();

    public void setChangeDate(Date changeDate);


}
