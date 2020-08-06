package it.tasgroup.idp.cart;


import javax.persistence.EntityManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.tasgroup.idp.domain.messaggi.NotificheCart;
import it.tasgroup.idp.timer.AbstractNotificationTimer.NotificationMethod;
import it.tasgroup.idp.util.StatoEnum;

public class GTARTWS_JmsDemuxNotification implements ISenderNotification {

	private final Log logger = LogFactory.getLog(this.getClass());
	
	private boolean gtartDelivery = false;
	
	@Override 
	public Object delivery(NotificheCart nc,EntityManager manager) throws Exception { 
		
		ISenderNotification jmsNotificationInternal= new JmsNotificationInternal();
		ISenderNotification jmsNotification = new JmsNotification(); 
		if (isGTARTDelivery(nc)) {
			gtartDelivery=true;
			return jmsNotificationInternal.delivery(nc,manager);
		} else {
			gtartDelivery=false;
			return jmsNotification.delivery(nc,manager);
		}
	}
    
	private boolean isGTARTDelivery(NotificheCart nc) {
		String info4WS = new String(nc.getNotificaXml());
		if (info4WS.contains("DATA_PAGAMENTO") &&  
			info4WS.contains("MSG_ID") &&
			info4WS.contains("IUV") &&
			info4WS.contains("MODALITA_PAG") &&
		    info4WS.contains("ID_BOLLO") &&
		    info4WS.contains("CF_SOGG_PASSIVO") &&
		    info4WS.contains("SANZIONI") &&
		    info4WS.contains("TASSA") &&
		    info4WS.contains("INTERESSI") 
		    ) {
			return true;
		} else {
		    return false;
		}
	}

	@Override
	public String getFinalState() {
		if (!gtartDelivery) {
			return StatoEnum.INVIATO;
		} else {
			return null; // Lo stato finale verrà settato dal listener sulla coda interna.
		}
	}
	
}
