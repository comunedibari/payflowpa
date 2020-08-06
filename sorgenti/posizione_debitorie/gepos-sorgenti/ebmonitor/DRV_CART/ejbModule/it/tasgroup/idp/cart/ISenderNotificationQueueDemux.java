package it.tasgroup.idp.cart;

import it.tasgroup.idp.domain.messaggi.NotificheCart;
import javax.persistence.EntityManager;

public interface ISenderNotificationQueueDemux {

	public Object delivery(NotificheCart nc,EntityManager manager) throws Exception;

	
}
