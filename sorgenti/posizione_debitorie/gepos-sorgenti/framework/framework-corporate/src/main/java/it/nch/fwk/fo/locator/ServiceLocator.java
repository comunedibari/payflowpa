/*
 * Created on 31-ott-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package it.nch.fwk.fo.locator;

import it.nch.fwk.fo.util.Tracer;

import java.util.Hashtable;

import javax.ejb.EJBHome;
import javax.ejb.EJBLocalHome;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
/**
 * @author EE10056
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ServiceLocator {

	  private static ServiceLocator serviceLocator;
	  private static Context context;
	  private static boolean bIsLocal = false;
	  private static Hashtable htJndiTable;


	/**
	 * Costruttore senza parametri che effettua la inizializzazione del contesto JNDI
	 * di default (senza parametri). 
	 *  @since 1.0
	 *
	 */
	  protected ServiceLocator() throws ServiceLocatorException {

	   }

	   /**
	      * Il metodo restituisce una interfaccia EJBHome corrispondente al bean
	      * il cui nome � passato come parametro.
	      *
	      * @exception ServiceLocatorException eccezione rilanciata al verificarsi di una NamingException
	      * @return    EJBLocalHome la interfaccia home del bean il cui nome � passato come parametro
	      * @since 1.0
	      * @see javax.ejb.EJBLocalHome
	   */
	  public EJBHome getEjbHome(String ejbName, Class ejbClass) throws ServiceLocatorException {
	    
	    try {
	    
	      Hashtable ht = context.getEnvironment();
	      Tracer.info("ServiceLocator","getEjbHome"," ","param " + ht);
	      Tracer.info("ServiceLocator","getEjbHome"," ","ejbName " + ejbName);
	      Object object = context.lookup(ejbName);
	      Tracer.info("ServiceLocator","getEjbObject"," ","Object found");
	      Tracer.info("ServiceLocator","getEjbHome"," ","===LOOKUP REMOTA OK,NO NARROW===");
	      Tracer.info("ServiceLocator","getEjbHome"," ","===" + object.toString() + " " + ejbClass);
	      EJBHome ejbHome = null;
	      ejbHome = (EJBHome) PortableRemoteObject.narrow(object, ejbClass);
	      Tracer.info("ServiceLocator","getEjbHome"," ","===OK NARROW===");
	      if (ejbHome == null) {
	        
	        throw new ServiceLocatorException("Could not get home for " + ejbName);
	      }
	      return ejbHome;
	    }
	    catch (NamingException ne) {
	      throw new ServiceLocatorException(ne.getMessage());
	    }
	  }

	  /**
	   * Il metodo restituisce una interfaccia EJBLocalHome corrispondente al bean
	   * il cui nome � passato come parametro.
	   *
	   * @exception ServiceLocatorException eccezione rilanciata al verificarsi di una NamingException
	   * @return    EJBLocalHome la interfaccia home del bean il cui nome � passato come parametro
	   * @since 1.0
	   * @see javax.ejb.EJBLocalHome
	   */
	  public EJBLocalHome getEjbLocalHome(String ejbName) throws ServiceLocatorException {
	      
	      try { 
	          Object object = context.lookup(ejbName);
	          EJBLocalHome ejbLocalHome = null;
	          ejbLocalHome = (EJBLocalHome) object;
	          if (ejbLocalHome == null) {
	              throw new ServiceLocatorException("Could not get local home for " + ejbName);
	          }
	          return ejbLocalHome;
	      }
	      catch (NamingException ne) {
	          
	          throw new ServiceLocatorException(ne.getMessage());
	      }
	  }
	  
	  public Object getEjbObject(String ejbName, Class ejbClass) throws ServiceLocatorException {
	    
	    try {
	    
	      Tracer.info("ServiceLocator","getEjbObject"," ","ejbName " + ejbName);
	      Object object = context.lookup(ejbName);
	      Tracer.info("ServiceLocator","getEjbObject"," ","Object found");
	      Tracer.info("ServiceLocator","getEjbObject"," ","===LOOKUP REMOTA OK,NO NARROW===");
	      Tracer.info("ServiceLocator","getEjbObject"," ","===" + object.toString() + " " + ejbClass);
	      Object ejbHome = null;
	      ejbHome = PortableRemoteObject.narrow(object, ejbClass);
	      Tracer.info("ServiceLocator","getObject"," ","===OK NARROW===");
	      if (ejbHome == null) {
	        
	        throw new ServiceLocatorException("Could not get home for " + ejbName);
	      }
	      return ejbHome;
	    }
	    catch (NamingException ne) {
	      throw new ServiceLocatorException(ne.getMessage());
	    }
	  }	  

	  /**
	   * Restituisce un oggetto JNDI generico che sia stato registrato presso il JNDI tree
	   * dell'application server in uso
	   *
	   * @param objectName il nome dell'oggetto da ricavare
	   * @return l'oggetto ricavato
	   * @since 1.0
	   */
	  public Object getJNDIObject(String objectName) throws ServiceLocatorException {
	    
	    Object object;
	    try {
			NamingEnumeration fff = context.list(context.getNameInNamespace());
			int c = 0;
			while (fff.hasMoreElements()) {

				Tracer.debug(getClass().getName(), "getJNDIObject", c++ + " " + fff.nextElement().toString());
			}
			Tracer.info("ServiceLocator","getEjbObject"," ","JndiName : " + objectName);
	      object = context.lookup(objectName);
	      if (object == null) {
	        String msg = "ATTENZIONE: non si e' potuto ricavare l'oggetto "+object;
	        
	        throw new ServiceLocatorException(msg);
	      }
	      return object;
	    }
	    catch (NamingException ne) {
	      throw new ServiceLocatorException(ne.getMessage());
	    }
	  }

	  /**
	   * Metodo di factory per la creazione di una istanza di locator secondo il pattern
	   * singleton.
	   * In questo caso viene invocato senza parametri di inizializzazione JNDI
	   * nel caso in cui si faccia riferimento al tree di default (es. localhost).
	   * Il metodo invoca il construttore di ServiceLocator senza parametri
	   *
	   *
	   * @return il ServiceLocator che istanziato la prima volta della invocazione del metodo
	   * @since 1.0
	   */
	  public static synchronized ServiceLocator getInstance() throws ServiceLocatorException {
	    
	  	Tracer.info("ServiceLocator","getInstance"," ","===CONTESTO LOCALE - ENTRY===");
	    if (serviceLocator == null) {
	    	Tracer.info("ServiceLocator","getInstance"," ","===CONTESTO LOCALE - NULL!===");
	      serviceLocator = new ServiceLocator();
	    }
	    try {
	    	context = getInitialContext();
	    } catch (Exception e) {
	    	throw new ServiceLocatorException(e.getMessage());
	    }
	    Tracer.info("ServiceLocator","getInstance"," ","===CONTESTO LOCALE - EXIT===");
	    bIsLocal = true;
	    return serviceLocator;

	  }


	  /**
	   * Metodo di factory per la creazione di una istanza di locator secondo il pattern
	   * singleton.
	   * In questo caso viene invocato con le properties di inizializzazione JNDI
	   * nel caso in cui si faccia riferimento ad un tree non di default.
	   * Il metodo invoca il construttore di ServiceLocator con le JNDI properties come
	   * parametri.
	   *
	   * @param jndiProperties le propriet� di connessione al tree JNDI
	   * @return il ServiceLocator che istanziato la prima volta della invocazione del metodo
	   * @since 1.0
	   */
	  public static synchronized ServiceLocator getInstance(Hashtable jndiTable) throws ServiceLocatorException {
		  Tracer.info("ServiceLocator","getInstance"," ","===CONTESTO REMOTO - ENTRY===");
	  	
	    if (serviceLocator == null) {
	    	Tracer.info("ServiceLocator","getInstance"," ","===CONTESTO REMOTO - NULL!===");
	    	serviceLocator = new ServiceLocator();
	    }
	    htJndiTable = jndiTable;
	    try {
	    	context = getInitialContext(jndiTable);
	    } catch (Exception e) {
	    	throw new ServiceLocatorException(e.getMessage());
	    }
	    bIsLocal = false;
	    Tracer.info("ServiceLocator","getInstance"," ","===CONTESTO REMOTO - EXIT===");
	    return serviceLocator;
	  }

	  /**
	   * Esegue la inizializzazione verso il JNDI tree. Sono utilizzate le properties JNDI
	   * passate al costruttore.
	   *
	   * @param jndiProperties le propriet� di connessione al tree JNDI
	   * @return Context il context di connessione al tree JNDI
	   * @see javax.naming.Context
	   */
	  private static Context getInitialContext(Hashtable jndiTable) throws NamingException {
	    
		  Tracer.info("ServiceLocator","getInitialContext"," ","===CONTESTO REMOTO===");
	    if (jndiTable == null){
	    
	      throw new NamingException("Impossibile inizializzare il context JNDI se jndiTable e' nullo");

	    }
	    
	    Context context = new InitialContext(jndiTable);
	    return context;
	  }


	  /**
	     * Esegue la inizializzazione di default (senza properties JNDI)  verso il
	     * JNDI tree.
	     *
	     * @param jndiProperties le propriet� di connessione al tree JNDI
	     * @return Context il context di default connessione al tree JNDI
	     * @see javax.naming.Context
	   */
	  private static Context getInitialContext() throws NamingException {
		  Tracer.info("ServiceLocator","getInitialContext"," ","===CONTESTO LOCALE===");
	    Context context = new InitialContext();
	    return context;
	  }
	  
	  public boolean isLocalService() {
	  	return bIsLocal;
	  }

	}

