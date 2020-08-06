package it.tasgroup.iris.web;

import it.nch.is.fo.web.FrontEndConstant;
import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 
 * HttpSessionListener per la lettura 'una tantum' e la centralizzazione delle properties di configurazione
 * che rimangono immutate durante tutta la sessione utente di navigazione.
 * 
 * Ogni property di configurazione, che può variare dinamicamente, viene memorizzata nella Web Session della 
 * WebApp che utilizza questo Listener.
 * 
 * Le properties che rimangono immutate per tutta la durata della Web App 
 * vanno invece memorizzate nel ServletContext mediante il ConfigurationContextListener.
 *  
 * 
 * @author pazzik
 */
public class ConfigurationSessionListener implements HttpSessionListener {
	
	public static final String COMMUNICATION_ENABLED_KEY = "isCommunicationEnabled";
	
	public static final String DOWNLOAD_RECEIPT_KEY = "isDownloadReceiptEnabled";
	
	public static final String ENTE_READONLY_KEY = "isEnteReadOnly";
	
	public static final String PROFILO_CITTADINO_READONLY_KEY = "isProfiloCittadinoReadonly";
		
	private static final ConfigurationPropertyLoader cpl = ConfigurationPropertyLoader.getInstance("iris-fe.properties");
		

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		
		Boolean isCommunicationEnabled = cpl.getBooleanProperty("gestore.comunicazioni.presente");
		
		String isEnteReadonly = cpl.getProperty("operatore.ente.anagrafiche.readonly");
		
		String isProfiloCittadinoReadonly = cpl.getProperty("paytas.profilo.cittadino.readonly");
		 
		boolean isDownloadReceiptEnabled =  cpl.getBooleanProperty("receiptDownloadEnabled");
		
		HttpSession session = se.getSession();
	       
		session.setAttribute(COMMUNICATION_ENABLED_KEY, isCommunicationEnabled);
        
		session.setAttribute(DOWNLOAD_RECEIPT_KEY, isDownloadReceiptEnabled);
        
		session.setAttribute(ENTE_READONLY_KEY, isEnteReadonly);
		
		session.setAttribute(PROFILO_CITTADINO_READONLY_KEY, isProfiloCittadinoReadonly);
		
		session.setAttribute(FrontEndConstant.NUMERO_RIGHE_PER_PAGINA,"10");
        
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		// TODO Auto-generated method stub
		
	}
}
