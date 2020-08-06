package it.tasgroup.iris.web;

import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;

import java.util.Locale;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;


/**
 * HttpSessionListener che legge il Locale da file di configurazione e lo memorizza nella Web Session 
 * dell'utente, dove può venire sovrascritto se l'utente decide di cambiarlo.
 * 
 * 
 * @author pazzik
 *
 */
public class LocaleListener implements HttpSessionListener {
    
	public static final String STRUTS_LOCALE_KEY = "org.apache.struts.action.LOCALE";
	
	public static final String JSF_LOCALE_KEY = "LOCALEJSF";
	
	final static public Logger LOG = Logger.getLogger(LocaleListener.class);
	
	private static final ConfigurationPropertyLoader cpl = ConfigurationPropertyLoader.getInstance("iris-fe.properties");
	
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        
        String language = cpl.getProperty("struts.locale.language");
        
        String country = cpl.getProperty("struts.locale.country");
        
        String variant = cpl.getProperty("struts.locale.variant");
        
        LOG.debug("[LocaleListener::sessionCreated] " + "language="+language+", country="+country+", variant="+variant);
        
        if (language != null && country != null && variant != null) {
        	
        	Locale locale = new Locale(language, country, variant);
        	
            String contextPath = httpSessionEvent.getSession().getServletContext().getContextPath();
            
            LOG.debug("[LocaleListener::sessionCreated] " + "contextPath="+contextPath);
            
            
          //  if (contextPath.contains("public"))
            	httpSessionEvent.getSession().setAttribute(JSF_LOCALE_KEY, locale);
            
          //  else
            	httpSessionEvent.getSession().setAttribute(STRUTS_LOCALE_KEY, locale);        
        }  
        
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {

    }
}
