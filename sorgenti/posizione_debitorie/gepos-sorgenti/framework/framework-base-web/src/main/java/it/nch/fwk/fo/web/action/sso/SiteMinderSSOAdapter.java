/*
 * Created on 1-dic-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package it.nch.fwk.fo.web.action.sso;


import it.nch.fwk.fo.base.config.Configurations;
import it.nch.fwk.fo.base.constants.BaseConfigSources;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SiteMinderSSOAdapter implements SSOAdapter {
    
	private static final String SM_USER = "SM_USER";
    private static final String CODICE_CLIENTE = "CODICE_CLIENTE";
    private static final String BANK = "Bank";
    private static Logger logger =
		Logger.getLogger(SiteMinderSSOAdapter.class);
    
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
	public String getBankId(HttpServletRequest request) throws NoSuchFieldException {
	
	    String bankId = null;
	    
	    if(isSSOActive()) {
	        
			bankId = request.getHeader(BANK);
	
			/* Per sviluppo locale nel caso in cui non sia configurato il proxy. */
			if (bankId == null) {
				bankId = request.getParameter(BANK);
			}
	
			/* Se il parametro Banca e' null imposto banca intesa come default. */
			if (bankId != null)	{
				bankId=bankId.trim();
			} else {
			    //logger.warn("Non e' possibile recuperare dalla chiamata HTTP il parametro 'Bank'");
			    throw new NoSuchFieldException("Non e' possibile recuperare dalla chiamata HTTP il parametro 'Bank'");
			}
	    } else {
		    //logger.warn("Tentativo di recuperare il bankId dal SSO quando questo non e' attivo. Verra' restituito 'null'");
	    }
		
		return bankId;
	}

	/**
	 * Recupera il codice cliente.
	 * @param request
	 * @return
	 */
	public String getUserId(HttpServletRequest request) throws NoSuchFieldException {
	    
		String smUserId = null; 
		    
		if(isSSOActive()) {		    
			request.getHeader(CODICE_CLIENTE);	
			
			/* 
			 * Se codice cliente e' nullo, leggo l'altro parametro di login.
			 * Puo' essere sia il codice cliente, sia il nickname. 
			 */
			if (StringUtils.isEmpty(smUserId)) {
				smUserId = request.getHeader(SM_USER);
			}
	
			/* Trim di eventuali spazi. */		
			if (smUserId != null) {
				smUserId = smUserId.trim();
			} else {
			    //logger.warn("Non e' possibile recuperare dalla chiamata HTTP il parametro 'CODICE_CLIENTE' o 'SM_USER'");
			    throw new NoSuchFieldException("Non e' possibile recuperare dalla chiamata HTTP il parametro 'CODICE_CLIENTE' o 'SM_USER'");
			}
		} else {
		    //logger.warn("Tentativo di recuperare lo userId dal SSO quando questo non e' attivo. Verra' restituito 'null'");
		}
		return smUserId;
	}

    /* (non-Javadoc)
     * @see it.nch.fwk.fo.web.action.SSOAdapter#isSSOActive()
     */
    public boolean isSSOActive() {
        return Configurations.getBooleanProperty(BaseConfigSources.WEB, "SITEMINDER_AUTHENTICATION");
    }
}
