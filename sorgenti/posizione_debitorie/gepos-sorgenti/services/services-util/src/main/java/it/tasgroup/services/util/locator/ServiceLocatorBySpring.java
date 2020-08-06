package it.tasgroup.services.util.locator;


import it.nch.fwk.fo.locator.ServiceLocatorException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.access.SingletonBeanFactoryLocator;

public abstract class ServiceLocatorBySpring {
	
	private static Logger LOGGER = LogManager.getLogger(ServiceLocatorBySpring.class);

	private static final String MASTER_FACTORY = "client-beanRefFactory.xml";
	private static final String PROXY_FACTORY = "businessDelegate";
	
	private static BeanFactory beanFactory= 
		SingletonBeanFactoryLocator.getInstance(MASTER_FACTORY).useBeanFactory(PROXY_FACTORY).getFactory();

	public static final Object getSLSBProxy(String beanName) throws ServiceLocatorException {
		synchronized(beanFactory) {
			try {
				if (beanFactory.containsBean(beanName))
					return beanFactory.getBean(beanName);
					
				throw new ServiceLocatorException("No entry for " + beanName + " in bean factory proxySLSB");
				
			} catch (BeansException e) {
				
				LOGGER.error("Errore di istanziazione mediante Spring: ", e);
				throw new ServiceLocatorException("Nessun Servizio "+ beanName+ "Configurato Correttamente", e);
			
			}
		}		
	}

	public static final Object getSLSBProxyByMasterFactory(String beanName, String masterFactory) throws ServiceLocatorException {
		synchronized(beanFactory) {
			if (masterFactory!=null) {
				beanFactory= 
					SingletonBeanFactoryLocator.getInstance(masterFactory).useBeanFactory(PROXY_FACTORY).getFactory();
			}			
			return getSLSBProxy(beanName);
		}
	}
	
	public static final Object getSLSBProxyByProxyFactory(String beanName, String proxyFactory) throws ServiceLocatorException {
		synchronized(beanFactory) {
			if (proxyFactory!=null) {
				beanFactory= 
					SingletonBeanFactoryLocator.getInstance(MASTER_FACTORY).useBeanFactory(proxyFactory).getFactory();
			}
			
			return getSLSBProxy(beanName);
		}
	}
	
	public static final Object getSLSBProxy(String beanName, String masterFactory, String proxyFactory) throws ServiceLocatorException {
		synchronized(beanFactory) {
			if (masterFactory!=null) {
				if (proxyFactory!=null) {
					beanFactory= 
						SingletonBeanFactoryLocator.getInstance(masterFactory).useBeanFactory(proxyFactory).getFactory();
				}
			}			
			return getSLSBProxy(beanName);
		}
	}
	
	public static final Object getSFSBProxy(String address) {
		throw new UnsupportedOperationException("SFSB Proxy not supported by Spring");
	}
}
