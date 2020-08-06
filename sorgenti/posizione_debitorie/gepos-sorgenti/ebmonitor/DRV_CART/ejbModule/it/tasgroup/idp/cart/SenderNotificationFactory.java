package it.tasgroup.idp.cart;

import it.tasgroup.idp.bean.BuildNotificationFactory;
import it.tasgroup.idp.timer.AbstractNotificationTimer.NotificationMethod;

import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SenderNotificationFactory {

	private final static Log LOGGER = LogFactory.getLog(SenderNotificationFactory.class);
	private static Properties notificationFactoryProperties = null;
	
	/** Metodo chiamato da NotificaItemSenderBean. Serve a discriminare il tipo di consegna tra:
	 * PULL (notifica disponibile sulla tabella NOTIFICHE_CART.
	 * PUT su code CART 
	 * PUT su coda interna (InformativaPagamentoPendenzeOutputInternal) (Default per tutti i consegnatori custom ordinari).
	 * @param type
	 * @return
	 */
	public static ISenderNotification createSenderNotification(String type) {
		try {
			//NotificationMethod notifMethod =NotificationMethod.valueOf(type);
			if (type.equals(NotificationMethod.PUSH_CART.getNotifMethod()))  
				return new JmsNotification();
			if (type.equals(NotificationMethod.PULL_WS.getNotifMethod()))  
				return new PullNotificationWs();
			if (type.equals(NotificationMethod.PUSH_WS_GTART.getNotifMethod()))  
				return new GTARTWS_JmsDemuxNotification();  // Alla fine decide o per JmsNotification o JmsInternalNotification
			
			// Else use default
			return new JmsNotificationInternal();

			
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			return null;
		}
		
	}
	
	/**
	 * Metodo chiamato dal listener sulla coda interna (InformativaPagamentoPendenzeOutputInternal)
	 * per istanziare il consegnatore specifico.
	 * 
	 * @param type
	 * @return
	 */
	public static ISenderNotificationQueueDemux createSenderNotificationQueueDemux(String type) {
		try {
						
			if (type.equals(NotificationMethod.PUSH_WS_GTART.getNotifMethod()))  
				return new GTARTWSNotification();
			if (type.equals(NotificationMethod.PUSH_WS_RFC145.getNotifMethod()))
				return new RFC145DirectWSNotification(); 	
		   
			// If not already found in built in notification, search for an extension plugin
			// Loading notification-factory.properties
			loadProperties();
			
			String builderClassName = notificationFactoryProperties.getProperty("senderNotificationFactory.sender."+type);
			
			if (builderClassName==null) {
				throw new Exception(type+" not found in notification-factory.properties in classpath. Error looking up property: senderNotificationFactory.sender."+type);
			}
			
			ISenderNotificationQueueDemux sender = (ISenderNotificationQueueDemux)Class.forName(builderClassName).newInstance();

			return sender;
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			return null;
		}
		
	}
	
	// First time only properties loading
	static void loadProperties() throws Exception {
		if (notificationFactoryProperties==null) {
				InputStream propertyFile = Thread.currentThread().getContextClassLoader().getResourceAsStream("notification-factory.properties");
				Properties props = new Properties();			
				props.load(propertyFile);
				notificationFactoryProperties=props;
		}
	}	

	
}
