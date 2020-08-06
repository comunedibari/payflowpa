package it.tasgroup.iris.web;

import it.tasgroup.iris.cache.IrisCacheSingleton;
import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.axis.AxisProperties;

/**
 * 
 * ServletContextListener per la lettura 'una tantum' e la centralizzazione delle properties di configurazione
 * 
 * che rimangono immutate durante tutta l'installazione dell'applicazione.
 * 
 * Ogni property di configurazione viene memorizzata nel ServletContext della webapp che utilizza questo Listener.
 * 
 * Per le property che devono essere lette a caldo per aggiornarsi dinamicamente, occorre memorizzarle in Web Session
 * 
 * mediante il ConfigurationSessionListener.
 *  
 * 
 * @author pazzik
 */
public class ConfigurationContextListener implements ServletContextListener {
	
	public static final String LISTA_PROVINCE_KEY = "listaProvince";
	
	public static final String CONTO_TECNICO_KEY = "contoTecnico";
	
		
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
    	
    	ServletContext context = servletContextEvent.getServletContext();
        
    	context.setAttribute(LISTA_PROVINCE_KEY, IrisCacheSingleton.getProvinceItaliane());
    	
    	context.setAttribute(CONTO_TECNICO_KEY, IrisCacheSingleton.getContoTecnico());
        
    	// Configurazione dei client AXIS
    	
    	ConfigurationPropertyLoader cpl = ConfigurationPropertyLoader.getInstance("ws-client.properties");
    	
    	String trustStorePath=cpl.getProperty("ws.clients.truststore.path");
    	String trustStorePwd=cpl.getProperty("ws.clients.truststore.pwd");
    	
    	if (trustStorePath!=null && ! trustStorePath.trim().equals("") &&
    		trustStorePwd!=null && ! trustStorePwd.trim().equals("")	
    			) {
    		
            AxisProperties.setProperty("axis.socketSecureFactory", "it.tasgroup.utility.ws.LoaderSSLSocketFactory");
            System.out.println("ConfigurationContextListener::AXIS web services run with '"+trustStorePath+"' keystore.");
            
    	} else {
    		
    		System.out.println("ConfigurationContextListener::INFO: properties missing: ws.clients.truststore.path or ws.clients.truststore.pwd or both");
    		System.out.println("ConfigurationContextListener::AXIS web services run without a keystore or using a keystore set at JVM level");
    	}
    	
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    	
    }
}
