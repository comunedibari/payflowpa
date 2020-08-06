package it.tasgroup.iris.shared.util.locator;

import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public abstract class ServiceLocator {

	private static InitialContext ctx;
	private static Properties jndiProperties; 
	private static Properties jndiNames; 
	private static Properties jndiLookups = new Properties();
	
	private static Logger LOGGER = LogManager.getLogger(ServiceLocator.class);
		
	private static final synchronized void initProperties() throws ServiceLocatorException {
		
		LOGGER.info("inizializzazione ServiceLocator");
		
		ConfigurationPropertyLoader cpl = ConfigurationPropertyLoader.getInstance("iris-be.properties");
		String enablePortableNames = cpl.getProperty("enable.portable.ejb.jndi.names");
		LOGGER.info("inizializzazione ServiceLocator - enable.portable.ejb.jndi.names: " + enablePortableNames);
		boolean isPortable = "true".equals(enablePortableNames);
		
		if(isPortable) {
			jndiNames = ConfigurationPropertyLoader.getProperties("jndi_names_portable.properties");
			LOGGER.info("inizializzazione ServiceLocator - caricato il file: jndi_names_portable.properties");
		} else {
			jndiNames = ConfigurationPropertyLoader.getProperties("jndi_names.properties");
			LOGGER.info("inizializzazione ServiceLocator - caricato il file: jndi_names.properties");
		}
		jndiProperties = ConfigurationPropertyLoader.getProperties("jndi_context.properties");

	}

	private static final synchronized void initContext() throws ServiceLocatorException{
		
		try {
			
			if (jndiProperties==null) initProperties();
			
			ctx = new InitialContext();
			
			for (Object jndiProperty : jndiProperties.keySet()) {
				
				LOGGER.debug("Ctx: " + jndiProperty + " " + jndiProperties.get(jndiProperty));
				
				ctx.addToEnvironment((String)jndiProperty, jndiProperties.get(jndiProperty));
			}
		} catch (NamingException e) {
			
			LOGGER.error("Errore di inizializzazione del context: ", e);
			
			throw new ServiceLocatorException("Errore di inizializzazione del context ", e);
		}		
	}
	
	public static final Object getSLSBProxyDirectlyNoCache(String address, Context ctx) throws ServiceLocatorException{
		
		try {
			
			return ctx.lookup(address);
			
		} catch (NamingException e) {
			
			LOGGER.error("Errore nel recupero diretto del proxy: ", e);
			
			throw new ServiceLocatorException("Errore nel recupero diretto del proxy ", e);
		}	
		
	}	
	public static final Object getSLSBProxyDirectlyNoCache(String address) throws ServiceLocatorException{
		if (ctx==null) initContext();
		
		try {
			return ctx.lookup(address);
			
		} catch (NamingException e) {
			
			LOGGER.error("Errore nel recupero diretto del proxy: ", e);
			
			throw new ServiceLocatorException("Errore nel recupero diretto del proxy ", e);
		}	
		
	}

	public static final Object getSLSBProxyNoCache(String name, Context ctx) throws ServiceLocatorException{
		
		try {
			
			return ctx.lookup((String)jndiNames.getProperty(name));
			
		} catch (NamingException e) {
			
			LOGGER.error("Errore nel recupero diretto del proxy: ", e);
			
			throw new ServiceLocatorException("Errore nel recupero diretto del proxy ", e);
		}
		
	}	
	public static final Object getSLSBProxyNoCache(String address) throws ServiceLocatorException{
		
		if (ctx==null) initContext();
		
		try {
			
			return ctx.lookup((String)jndiNames.getProperty(address));
			
		} catch (NamingException e) {
			
			LOGGER.error("Errore nel recupero diretto del proxy: ", e);
			
			throw new ServiceLocatorException("Errore nel recupero diretto del proxy ", e);
		}
		
	}
		
			
	public static final synchronized Object getSLSBProxy(String address) throws ServiceLocatorException{
				
		try {
			
			if (ctx==null) initContext();
			
			if (!(jndiLookups.containsKey(address))) {
				/*** On demand caching***/
				
					jndiLookups.put(address, ctx.lookup((String)jndiNames.getProperty(address)));
							
				}	
			
			return jndiLookups.get(address);
			
		} catch (NamingException e) {
			
			LOGGER.error("Errore nel recupero del proxy dalla cache: ", e);
			
			throw new ServiceLocatorException("Errore nel recupero del proxy dalla cache ", e);
		}
		
	}
	
	public static final synchronized Object getSFSBProxy(String address) throws ServiceLocatorException{
		
		try {
			if (ctx==null) initContext();
			
			return ctx.lookup((String)jndiNames.get(address));
			
		} catch (NamingException e) {
			
			LOGGER.error("Errore nel recupero del proxy dalla cache: ", e);
			
			throw new ServiceLocatorException("Errore nel recupero del proxy dalla cache ", e);
		}
		
	}
	
}
