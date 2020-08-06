package it.nch.is.fo;

import java.util.Locale;
import java.util.Set;

import it.nch.fwk.fo.core.ProxyContextImpl;
import it.nch.fwk.fo.core.StatelessSessionManager;
import it.nch.fwk.fo.das.exception.DasUncheckedException;
import it.nch.fwk.fo.dto.DTO;
import it.nch.fwk.fo.dto.DTOImpl;
import it.nch.fwk.fo.util.Tracer;
import it.nch.is.fo.profilo.Intestatarioperatori;
import it.nch.is.fo.profilo.Operatori;
import it.nch.is.fo.profilo.TributiOperatoriCommon;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.beans.factory.access.BeanFactoryReference;
import org.springframework.beans.factory.access.SingletonBeanFactoryLocator;

/**
 * Specializzazione del ProxyContextImpl usata e IDP.
 * E' sia FrontEndContext che BackEndContext.
 *
 * @author TODO
 *
 */
public class IrisContextImpl extends ProxyContextImpl {
	  private transient BeanFactoryReference bfr;
	  private transient BeanFactoryLocator bfl;
	  private transient BeanFactory bf;


	/** Restituisce lo StatelessSessionManager costruito con Spring.
	 *
	 * @see it.nch.fwk.fo.core.ProxyContextImpl#getStatelessSessionManager()
	 */
	public StatelessSessionManager getStatelessSessionManager() throws DasUncheckedException {
		if(statelessSM==null){
		    bfl = SingletonBeanFactoryLocator.getInstance("server-beanRefFactory.xml");
		    bfr = bfl.useBeanFactory("it.nch.business");
		    Tracer.info(" PROXY ", "costruttore","BeanFactory per oggetti business.........", null);
		    bf = bfr.getFactory();
		    return  (StatelessSessionManager) bf.getBean("sessionManager");
	    }else{
	    	return this.statelessSM;
	    }
	  }


	/**
	 * Restituisce un DTO avente come pojo un oggetto di tipo Intestatarioperatori
	 * corrispondente alla coppia intestatario/operatore che si � loggata e lo setta anche
	 * come variabile di istanza con lazy loading.
	 *
	 * @see it.nch.fwk.fo.interfaces.FrontEndContext#getIntestatarioperatoriCorrente()
	 */
	@Override
	public DTO<Intestatarioperatori,?,?> getIntestatarioperatoriCorrente() {

		if (intestatarioperatoriCorrente == null && getOperatore() != null){
			Operatori operatore = (Operatori) getOperatore().getPojo();
			Intestatarioperatori intestatarioperatore = operatore.getIntestatarioperatoriCorrente();
			if(intestatarioperatore != null){
				Set<TributiOperatoriCommon> setTr = intestatarioperatore.getTributiOperatori();
			
				DTO dto = new DTOImpl();
				dto.setPojo(intestatarioperatore);
				setIntestatarioperatoriCorrente(dto);
			}

		}

		return intestatarioperatoriCorrente;
	}

	/**
	 * Shortcut per identificare se l'operatore che si � loggato
     * funge da operatore generico per l'intestatario con cui  si � loggato.
     * Se non � stata ancora recuperata da DB la coppia intestatario/operatore,
     * restituisce true cio� considera operatore generico un operatore che non ha ancora
     * scelto l'intestatario.
     *
	 * @return true se l'operatore che si � loggato funge da operatore generico per l'intestatario
	 * con cui  si � loggato.
	 */
	public boolean isGenericOperatorLoggedIn(){
		if (getIntestatarioperatoriCorrente() != null)
			return ((Intestatarioperatori)intestatarioperatoriCorrente.getPojo()).isGenericOperator();
		return true;
	}

    /**
     * Shortcut per identificare se l'operatore che si � loggato
     * funge da amministratore per l'intestatario con cui  si � loggato.
     * Se non � stata ancora recuperata da DB la coppia intestatario/operatore,
     * restituisce false cio� considera operatore generico un operatore che non ha ancora
     * scelto l'intestatario.
     *
     * @return true se l'operatore che si � loggato funge da amministratore per l'intestatario
     * con cui  si � loggato.
     */
    public boolean isAdminitratorLoggedIn(){
    	if (getIntestatarioperatoriCorrente() == null)
			return false;
  	  return ((Intestatarioperatori)intestatarioperatoriCorrente.getPojo()).isAdminitrator();
    }

    /**
     * Shortcut per restituire il tipo della coppia intestatario/operatore che si � loggata.
     *
     * @return il tipo della coppia intestatario/operatore che si � loggata.
     */
    public String getTipoIntestatarioperatore(){
    	if (getIntestatarioperatoriCorrente() != null)
    		return ((Intestatarioperatori)intestatarioperatoriCorrente.getPojo()).getTipoOperatore();
    	return null;
    }


	@Override
	public Locale userLocale() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
