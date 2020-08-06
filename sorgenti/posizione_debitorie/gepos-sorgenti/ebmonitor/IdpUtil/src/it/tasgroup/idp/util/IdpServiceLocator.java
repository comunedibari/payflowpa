package it.tasgroup.idp.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class IdpServiceLocator {
	private Context initialContext;  // InitialContext standard che non ha visibilita' sul cluster
	private Context initialContextClusterAware;  // InitialContext con visibilita' sul cluster
	private boolean isCtxClusterAwareDefined;  // True se definito InitialContext con visibilita' sul cluster
	private Map<String, Object> cache;	// Cache di oggetti ricavati da InitialContext (sia standard che cluster-aware): la cache e' unica  
	
	private static final Log logger = LogFactory.getLog(IdpServiceLocator.class);
	private static final IdpServiceLocator locatorInstance = new IdpServiceLocator();

	public static IdpServiceLocator getInstance() {
		return locatorInstance;
	}
	
	private static final String CLUSTER_CTX_ENABLED = "cluster.ctx.enabled";
	private static final String CLUSTER_CTX_ = "cluster.ctx.";
	
	private IdpServiceLocator() {
		try {
			// initialContext 'standard'
			this.initialContext = new InitialContext();
			// initialContext, in modalità test con openEjb
            //Properties props = new Properties();	
			//props.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.openejb.client.LocalInitialContextFactory"); 
			//this.initialContext = new InitialContext(props); 
            
			// initialContextClusterAware
			String clusterCtxEnabledPropertyValue = IrisProperties.getProperty(CLUSTER_CTX_ENABLED);
			if (isDefined(clusterCtxEnabledPropertyValue) && Boolean.TRUE.toString().equalsIgnoreCase(clusterCtxEnabledPropertyValue.trim())) {
				Properties clusterSpecificProps = IrisProperties.getPropertiesHavingPrefix(CLUSTER_CTX_);
				Properties ICCAProps = null;
				final int CLUSTER_CTX_LENGTH = CLUSTER_CTX_.length();
				for (Object keyAsObject : clusterSpecificProps.keySet()) {
					String key = (String) keyAsObject;
					if (!key.equals(CLUSTER_CTX_ENABLED)) {
						String shortKey = key.substring(CLUSTER_CTX_LENGTH);
						String value = clusterSpecificProps.getProperty(key);
						if (isDefined(shortKey) && isDefined(value)) {
							if (ICCAProps == null) ICCAProps = new Properties();
							ICCAProps.setProperty(shortKey, value);
						}
					}
				}
				this.initialContextClusterAware = ICCAProps == null ? new InitialContext() : new InitialContext(ICCAProps);
			} else {
				this.initialContextClusterAware = null;
			}
			// isCtxClusterAwareDefined
			this.isCtxClusterAwareDefined = initialContextClusterAware != null;
			// cache
			this.cache = Collections.synchronizedMap(new HashMap<String, Object>());
		} catch (NamingException ex) {
			System.err.printf("Error in CTX looking up " + ex.getMessage(), ex.getRemainingName(),
					ex.getCause(), ex.getExplanation());
			logger.info("Error in CTX looking up " + ex.getMessage());
		}
	}

	/**
	 * Dato un service name, si ricava il nome JNDI ad esso associato e si restituisce l'oggetto corrispondente
	 * ricercato in initialContext. 
	 * 
	 * Usato per i timers (nome da db - mapping jndiName su costanti/properties) 
	 * 
	 * @param name nome del servizio
	 * @return oggetto corrispontente al nome del servizio
	 */
	public Object getServiceByName(String name) {
		logger.debug("[IdpServiceLocator::getServiceByName] name: " + name);
		String jndiName = ServiceLocalMapper.getJndiName(name);
		return getServiceByJndiName(jndiName);
	}
	
	/**
	 * Sono forniti due nomi di servizi: uno da utilizzare quando initialContextClusterAware non e' definito,
	 * l'altro quando e' definito. Da questi si ricavano nomi JNDI associati e si restituisce l'oggetto
	 * corrispondente  
	 * 
	 * @param name nome del servizio da utilizzare quando initialContextClusterAware non e' definito
	 * @param nameClusterAware nome del servizio da utilizzare quando initialContextClusterAware e' definito
	 * @return oggetto corrispondente al nome del servizio
	 */
	public Object getServiceByName(String name, String nameClusterAware) {
		logger.debug("[IdpServiceLocator::getServiceByName] name: " + name + " - nameClusterAware: " + nameClusterAware);
		String jndiName = ServiceLocalMapper.getJndiName(name);
		String jndiNameClusterAware = ServiceLocalMapper.getJndiName(nameClusterAware);
		return getServiceByJndiName(jndiName, jndiNameClusterAware);
	}

	/**
	 * Dato un nome JNDI, questo viene ricercato nell'initialContext e si restituisce l'oggetto corrispondente
	 * 
	 * @param jndiName nome JNDI da utilizzare per la ricerca su initialContext
	 * @return oggetto acquisito da initialContext
	 */
	public Object getServiceByJndiName(String jndiName) {
		logger.debug("[IdpServiceLocator::getServiceByJndiName] jndiName: " + jndiName);
		return getServiceByJndiName(jndiName, initialContext);
	}
	
	/**
	 * Sono forniti due nomi JNDI: uno da utilizzare quando initialContextClusterAware non e' definito
	 * l'altro quando e' definito. Il metodo restituisce l'oggetto cercato su initialContext / initialContextClusterAware
	 * 
	 * @param jndiName nome JNDI da utilizzare nel caso in cui non sia definito initialContextClusterAware
	 * @param jndiNameClusterAware nome JNDI da utilizzare nel caso in cui sia definito initialContextClusterAware
	 * @return oggetto acquisito da initialContext o initialContextClusterAware
	 */
	public Object getServiceByJndiName(String jndiName, String jndiNameClusterAware) {
		logger.debug("[IdpServiceLocator::getServiceByJndiName] jndiName: " + jndiName + " - jndiNameClusterAware: " + jndiNameClusterAware);
		if (isCtxClusterAwareDefined) 
			return getServiceByJndiName(jndiNameClusterAware, initialContextClusterAware);
		else 
			return getServiceByJndiName(jndiName, initialContext);
	}
	
	
	private Object getServiceByJndiName(String jndiName, Context ctx) {
		Object service = null;
		try {
			//lista JNDI Names (sempre utile)
//			NamingEnumeration<NameClassPair> list = ctx.list("");
//			while (list.hasMore()) {
//			  System.out.println("JNDI NAME = " + list.next().getName());
//			}
			
			if (this.cache.containsKey(jndiName)) {
				service = this.cache.get(jndiName);
			} else {
				service = ctx.lookup(jndiName);
				this.cache.put(jndiName, service);
			}
		} catch (NamingException ex) {
			System.err.printf("Error in CTX looking up " + ex.getMessage(), ex.getRemainingName(),
					ex.getCause(), ex.getExplanation());
			logger.info("Error in CTX looking up " + ex.getMessage());
		}
		return service;
	}
	
	private boolean isDefined(String s) {
		return s != null && !"".equals(s.trim());
	}
}
