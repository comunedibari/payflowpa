package it.tasgroup.iris.web;

import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;

/**
 * Inserisce il theme a livello di application context (proprietà theme, file iris-fe.properties)
 */
public class ThemeContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
    	
        ConfigurationPropertyLoader cpl = ConfigurationPropertyLoader.getInstance("iris-fe.properties");
        
        try {
        	
            servletContextEvent.getServletContext().setAttribute(Theme.DEFAULT_ATTRIBUTE_NAME, new Theme(cpl.getProperty("theme")));
        
        } catch (IOException e) {
        	
            e.printStackTrace();
            
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    	
    }
}
