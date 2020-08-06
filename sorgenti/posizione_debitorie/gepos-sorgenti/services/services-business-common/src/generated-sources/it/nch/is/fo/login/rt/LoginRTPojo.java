package it.nch.is.fo.login.rt;

import java.util.List;

import it.nch.fwk.fo.dto.business.Pojo;
import it.nch.is.fo.profilo.OperatoriCommon;
import it.nch.is.fo.profilo.OperatoriForm;

/**
 * Pojo utilizzato per contenere i dati necessari all'autenticazione del solo operatore.
 *
 * @author TODO
 *
 */
public interface LoginRTPojo extends Pojo{

    public String getCodiceFiscale();

    public void setCodiceFiscale(String codiceFiscale);

    public String getPassword();

    public void setPassword(String password);

    public String getCriptedPassword();

    public void setCriptedPassword(String criptedPassword);

    public String getCorporateState();

    public String getRedirectUrl();

    public void setRedirectUrl(String redirectUrl);
    
    public void setNome(String nome);
    public void setCognome(String cognome);
//    public void setEmail(String email);
    public String getNome();
    public String getCognome();
//    public String getEmail();
    
    public List<OperatoriForm> getDeleganti();
    public void setDeleganti(List<OperatoriForm> deleganti);

}
