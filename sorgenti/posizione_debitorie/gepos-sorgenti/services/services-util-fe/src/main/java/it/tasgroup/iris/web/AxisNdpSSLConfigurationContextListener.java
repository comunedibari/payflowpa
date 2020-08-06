package it.tasgroup.iris.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.axis.AxisProperties;

import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;

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
public class AxisNdpSSLConfigurationContextListener implements ServletContextListener {
	
	
		
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
    	
    	
    	// Configurazione dei client AXIS
    	String trustStorePath=System.getProperty("javax.net.ssl.trustStore");
    	String trustStorePwd=System.getProperty("javax.net.ssl.trustStorePassword"); 
    	
    	System.out.println("AxisNdpSSLConfigurationContextListener::AXIS web services run with '"+trustStorePath+"' keystore.");
    	//System.out.println("AXIS web services run with '"+trustStorePath+"' trustStorePwd.");
    	
    	if (trustStorePath!=null && !(trustStorePath.trim().equals("")) &&
    		trustStorePwd!=null && !(trustStorePwd.trim().equals(""))
    			) {    		
            
            System.setProperty("org.apache.axis.components.net.SecureSocketFactory","it.tasgroup.utility.ws.AxisNdpSSLSocketFactory");

            System.out.println("AxisNdpSSLConfigurationContextListener::AXIS web services run with '"+trustStorePath+"' keystore.");
            System.out.println("AxisNdpSSLConfigurationContextListener:: axis.socketSecureFactory=it.tasgroup.utility.ws.AxisNdpSSLSocketFactory");
            
    	} else {
    		
    		System.out.println("AxisNdpSSLConfigurationContextListener::INFO: properties missing: javax.net.ssl.trustStore or javax.net.ssl.trustStorePassword or both");
    		System.out.println("AxisNdpSSLConfigurationContextListener::AXIS web services run without a keystore or using a keystore set at JVM level");
    	}
    	
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    
    }
}
