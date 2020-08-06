/*
 * Created on 1-dic-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package it.nch.fwk.fo.web.action.user;

import javax.servlet.http.HttpServletRequest;

/**
 * @author finsaccanebbia
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface UserIdentifier {
    /**
     * Recupera il codice della banca. La logica di default e' la seguente. Si cerca 
     * di recuperare il parametro "Bank" dall'header (fornito tramite SSO). 
     * Laddove non ci sia questo parametro (es: ambiente di sviluppo) viene recuperato
     * dal parametro "Bank" nella richiesta HTTP (es: tramite una form di login).
     * 
     * @param request
     *            richiesta HTTP in cui devono esserci le informazioni relative
     *            alla banca alla quale appartiene l'utente
     * @return codice della banca 
     */
    public abstract String getBankId(HttpServletRequest request)
            throws NoSuchFieldException;

    /**
     * Recupera il codice cliente.
     * @param request
     * @return
     */
    public abstract String getUserId(HttpServletRequest request)
            throws NoSuchFieldException;
}