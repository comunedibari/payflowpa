/*
 * Created on 30-nov-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package it.nch.fwk.fo.web.mapping;

import org.apache.struts.action.ActionMapping;

/**
* <br>
* Estensione della classe ActionMapping di Struts per riuscire
* ad indicare nello struts-config quali sono le Action dove
* anche in caso di "clone switching" posso evitare di interrompere
* la navigazione utente
*
**/
public class FrameworkActionMapping extends ActionMapping{
	private static final long	serialVersionUID	= - 2409812469140334337L;

	private boolean isNavigationalBase = false;

	/**
	 * Indica, con il valore 'true', che la action e' di login, ovvero accessibile anche dalla parte pubblica.
	 * @deprecated utilizza getNavigationalBase
	 * @return
	 */
	public String setIsLoginUrl() {
		return (isNavigationalBase?"true":"false");
	}

	/**
	 * @param boolean
	 * @deprecated utilizza setNavigationalBase
	 */
	public void setIsLoginUrl(String isLoginUrl) {
		this.isNavigationalBase = "true".equals(isLoginUrl);
	}


    /**
     * Indica se l'azione associata e' un punto base di navigazione,
     * ovvero se e' un "punto di partenza della navigazione" (ad esempio
     * home page principale oppure home page di sezione o prima pagina di un
     * percorso navigazionale). Serve per gestire il meccanismo di
     * "clone switching"
     *
     * @return
     */
    public boolean isNavigationalBase() {
        return isNavigationalBase;
    }

    /**
     * Metodo per impostare per l'azione associata se si tratta di un
     * "punto di partenza della navigazione". Comunque normalmente questa
     * configurazione e' gestita dallo struts-config.xml
     *
     * @param isNavigationalBase
     */
    public void setNavigationalBase(boolean isNavigationalBase) {
        this.isNavigationalBase = isNavigationalBase;
    }
}
