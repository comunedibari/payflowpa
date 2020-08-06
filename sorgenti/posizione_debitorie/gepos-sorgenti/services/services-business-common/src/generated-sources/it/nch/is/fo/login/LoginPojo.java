/**
*
* Interfaccia generata
*
*/

package it.nch.is.fo.login;

import it.nch.fwk.fo.dto.business.Pojo;

/**
 * Pojo utilizzato per contenere i dati necessari all'autenticazione della coppia operatore/intestatario.
 *
 * @author TODO
 *
 */
public interface LoginPojo extends Pojo{

    public String getCorporateCode();

    public void setCorporateCode(String corporateCode);

    public String getUsername();

    public void setUsername(String username);

    public String getPassword();

    public void setPassword(String password);

    public String getCriptedPassword();

    public void setCriptedPassword(String criptedPassword);

    public String getCorporateState();

    public void setCorporateState(String corporateState);

    public String getToBeActivated();

    public void setToBeActivated(String toBeActivated);

    public String getRedirectUrl();

    public void setRedirectUrl(String redirectUrl);


}
