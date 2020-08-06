package it.tasgroup.idp.cart;

import it.tasgroup.idp.domain.messaggi.NotificheCart;
import it.tasgroup.idp.util.StatoEnum;

import javax.persistence.EntityManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PullNotificationWs implements ISenderNotification {

	private final Log logger = LogFactory.getLog(this.getClass());	
	
	
	@Override 
	public Object delivery(NotificheCart nc,EntityManager manager) throws Exception { 
		
		
		logger.info(this.getClass().getSimpleName() + " messaggio da rendere disponibile  , identificativo  = " 
				+ nc.getId().getE2emsgid() + " - " + nc.getId().getReceiverid() + " - " + nc.getId().getReceiversys());
		
		logger.info(this.getClass().getSimpleName() + " contenuto = \n" + nc.getNotificaXml().toString() );
		return null;
	}
	@Override
	public String getFinalState() {
		
		return StatoEnum.DISPONIBILE;
	}
	
}
