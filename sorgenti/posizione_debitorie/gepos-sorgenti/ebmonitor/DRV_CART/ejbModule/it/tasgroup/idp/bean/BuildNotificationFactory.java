package it.tasgroup.idp.bean;

import it.tasgroup.idp.timer.AbstractNotificationTimer.NotificationFormat;

import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BuildNotificationFactory { 
    
	private final static Log LOGGER = LogFactory.getLog(BuildNotificationFactory.class);
	private static Properties notificationFactoryProperties = null;
	
	public static IBuildNotification createNotificationBuilder(String type) throws Exception {
		try {
//			NotificationFormat notifFormat =NotificationFormat.valueOf(type);
			if (type.equals(NotificationFormat.RFC_145_V010300.getNotifFormat()))  
				return new BuildRFC145v010300Notification();
			if (type.equals(NotificationFormat.RFC_145_V010302.getNotifFormat()))  
				return new BuildRFC145v010302Notification();
			if (type.equals(NotificationFormat.RFC_145_V010303.getNotifFormat()))  
				return new BuildRFC145v010303Notification();
			if (type.equals(NotificationFormat.GTART.getNotifFormat()))
				return new BuildGART_RFC145DemuxNotification();
			if (type.equals(NotificationFormat.GTART_DIRECT.getNotifFormat()))
				return new BuildGTARTNotification();
			if (type.equals(NotificationFormat.CSV_BASIC_V2.getNotifFormat())) // stile SmartProxy
				return new BuildCSVBasicV2Notification();

//			if (notifFormat.equals(NotificationFormat.CSV_BASIC_V3))
//				return null; // TODO 			
//			if (notifFormat.equals(NotificationFormat.RECUP_PAGAMENTO))
//		       return new BuildReCUPPagamentoWSNotification();

			// If not already found in built in notification, search for an extension plugin
			// Loading notification-factory.properties
			
			loadProperties();
			
			String builderClassName = notificationFactoryProperties.getProperty("buildNotificationFactory.format."+type);
			
			if (builderClassName==null) {
				throw new Exception(type+" not found in notification-factory.properties in classpath. Error looking up property: buildNotificationFactory.format."+type);
			}
			 
			IBuildNotification builder = (IBuildNotification)Class.forName(builderClassName).newInstance();
			
			return builder;
			
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
